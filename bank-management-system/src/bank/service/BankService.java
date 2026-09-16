package bank.service;

import bank.Account;
import bank.CurrentAccount;
import bank.SavingsAccount;
import bank.Transaction;
import bank.db.DBConnection;
import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAccountException;
import bank.exceptions.InvalidAmountException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Core business logic layer. Keeps an in-memory cache (HashMap) of
 * accounts for fast lookup during a session, and persists every
 * change to MySQL via JDBC so data survives across runs.
 *
 * Transfers are wrapped in a JDBC transaction (commit/rollback) so
 * that a failure on the second leg cannot leave money "created" or
 * "destroyed" between accounts.
 */
public class BankService {

    private static final Logger LOGGER = Logger.getLogger(BankService.class.getName());

    // In-memory cache: accountNumber -> Account, for quick lookups within a session
    private final Map<String, Account> accountCache = new HashMap<>();

    public BankService() {
        setupLogger();
        loadAccountsFromDatabase();
    }

    private void setupLogger() {
        try {
            FileHandler fh = new FileHandler("bank_transactions.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.ALL);
        } catch (Exception e) {
            System.err.println("Could not initialize file logger: " + e.getMessage());
        }
    }

    /** Loads all active/closed accounts from the database into the in-memory cache at startup. */
    private void loadAccountsFromDatabase() {
        String sql = "SELECT account_number, owner_id, owner_name, balance, account_type, active FROM accounts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account acc = buildAccountFromRow(rs);
                accountCache.put(acc.getAccountNumber(), acc);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Could not load accounts from database (is MySQL running / schema created?): {0}", e.getMessage());
        }
    }

    private Account buildAccountFromRow(ResultSet rs) throws SQLException {
        String accNum = rs.getString("account_number");
        String ownerId = rs.getString("owner_id");
        String ownerName = rs.getString("owner_name");
        double balance = rs.getDouble("balance");
        String type = rs.getString("account_type");
        boolean active = rs.getBoolean("active");

        Account acc = "SAVINGS".equalsIgnoreCase(type)
                ? new SavingsAccount(accNum, ownerId, ownerName, balance)
                : new CurrentAccount(accNum, ownerId, ownerName, balance);

        if (!active) {
            acc.deactivate();
        }
        return acc;
    }

    /**
     * Creates a new account, persists it, and returns the created object.
     */
    public Account createAccount(String accountNumber, String ownerId, String ownerName,
                                  String accountType, double openingBalance) throws InvalidAmountException, SQLException {
        if (openingBalance < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative.");
        }

        Account account = "SAVINGS".equalsIgnoreCase(accountType)
                ? new SavingsAccount(accountNumber, ownerId, ownerName, openingBalance)
                : new CurrentAccount(accountNumber, ownerId, ownerName, openingBalance);

        String sql = "INSERT INTO accounts (account_number, owner_id, owner_name, balance, account_type, active) "
                + "VALUES (?, ?, ?, ?, ?, TRUE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, ownerId);
            ps.setString(3, ownerName);
            ps.setDouble(4, openingBalance);
            ps.setString(5, accountType.toUpperCase());
            ps.executeUpdate();
        }

        accountCache.put(accountNumber, account);
        LOGGER.info(() -> "Created account " + accountNumber + " for " + ownerName);
        return account;
    }

    public Account getAccount(String accountNumber) throws InvalidAccountException {
        Account acc = accountCache.get(accountNumber);
        if (acc == null || !acc.isActive()) {
            throw new InvalidAccountException("No active account found for number: " + accountNumber);
        }
        return acc;
    }

    public void deposit(String accountNumber, double amount)
            throws InvalidAccountException, InvalidAmountException, SQLException {
        Account acc = getAccount(accountNumber);
        acc.deposit(amount);
        persistBalance(acc);
        recordTransaction(accountNumber, Transaction.Type.DEPOSIT, amount, acc.getBalance());
    }

    public void withdraw(String accountNumber, double amount)
            throws InvalidAccountException, InvalidAmountException, InsufficientFundsException, SQLException {
        Account acc = getAccount(accountNumber);
        acc.withdraw(amount);
        persistBalance(acc);
        recordTransaction(accountNumber, Transaction.Type.WITHDRAWAL, amount, acc.getBalance());
    }

    /**
     * Transfers money between two accounts as a single atomic operation.
     * If the withdrawal on the source succeeds but the deposit on the
     * destination fails, both in-memory and database changes are rolled back.
     */
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount)
            throws InvalidAccountException, InvalidAmountException, InsufficientFundsException, SQLException {

        Account from = getAccount(fromAccountNumber);
        Account to = getAccount(toAccountNumber);

        // Snapshot balances in case we need to roll back the in-memory state
        double fromOriginal = from.getBalance();
        double toOriginal = to.getBalance();

        Connection conn = null;
        try {
            from.withdraw(amount);
            to.deposit(amount);

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            updateBalanceInTransaction(conn, from);
            updateBalanceInTransaction(conn, to);
            insertTransactionInTransaction(conn, fromAccountNumber, Transaction.Type.TRANSFER_OUT, amount, from.getBalance());
            insertTransactionInTransaction(conn, toAccountNumber, Transaction.Type.TRANSFER_IN, amount, to.getBalance());

            conn.commit();
            LOGGER.info(() -> String.format("Transferred %.2f from %s to %s", amount, fromAccountNumber, toAccountNumber));
        } catch (SQLException e) {
            // Roll back both DB and in-memory state so nothing is left inconsistent
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    LOGGER.log(Level.SEVERE, "Rollback failed: {0}", rollbackEx.getMessage());
                }
            }
            resetBalance(from, fromOriginal);
            resetBalance(to, toOriginal);
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // nothing more we can do
                }
            }
        }
    }

    private void resetBalance(Account acc, double originalBalance) {
        // Directly restore via a fresh deposit/withdraw is messy across subclasses,
        // so we use the protected setter through a same-package-visible cast trick instead:
        // simplest safe approach here is to just re-set via reflection-free helper below.
        try {
            double diff = originalBalance - acc.getBalance();
            if (diff > 0) {
                acc.deposit(diff);
            } else if (diff < 0) {
                acc.withdraw(-diff);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not fully roll back in-memory balance for {0}", acc.getAccountNumber());
        }
    }

    public void closeAccount(String accountNumber) throws InvalidAccountException, SQLException {
        Account acc = getAccount(accountNumber);
        acc.deactivate();

        String sql = "UPDATE accounts SET active = FALSE WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        }
        LOGGER.info(() -> "Closed account " + accountNumber);
    }

    /** Returns all accounts (active and closed) currently known to the service. */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountCache.values());
    }

    /** Returns the full transaction history for an account, most recent last. */
    public List<Transaction> getStatement(String accountNumber) throws InvalidAccountException, SQLException {
        getAccount(accountNumber); // validates the account exists/active

        List<Transaction> history = new ArrayList<>();
        String sql = "SELECT account_number, type, amount, balance_after, created_at "
                + "FROM transactions WHERE account_number = ? ORDER BY created_at ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    history.add(new Transaction(
                            rs.getString("account_number"),
                            Transaction.Type.valueOf(rs.getString("type")),
                            rs.getDouble("amount"),
                            rs.getDouble("balance_after")
                    ));
                }
            }
        }
        return history;
    }

    private void persistBalance(Account acc) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, acc.getBalance());
            ps.setString(2, acc.getAccountNumber());
            ps.executeUpdate();
        }
    }

    private void updateBalanceInTransaction(Connection conn, Account acc) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, acc.getBalance());
            ps.setString(2, acc.getAccountNumber());
            ps.executeUpdate();
        }
    }

    private void recordTransaction(String accountNumber, Transaction.Type type, double amount, double balanceAfter) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, type, amount, balance_after, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, type.name());
            ps.setDouble(3, amount);
            ps.setDouble(4, balanceAfter);
            ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.executeUpdate();
        }
        LOGGER.info(() -> String.format("%s of %.2f on %s (balance after: %.2f)", type, amount, accountNumber, balanceAfter));
    }

    private void insertTransactionInTransaction(Connection conn, String accountNumber, Transaction.Type type,
                                                  double amount, double balanceAfter) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, type, amount, balance_after, created_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, type.name());
            ps.setDouble(3, amount);
            ps.setDouble(4, balanceAfter);
            ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.executeUpdate();
        }
    }
}
