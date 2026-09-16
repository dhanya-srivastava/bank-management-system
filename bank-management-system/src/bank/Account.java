package bank;

import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAmountException;

/**
 * Abstract base class representing a bank account.
 * SavingsAccount and CurrentAccount extend this class and override
 * withdraw() to enforce their own rules (minimum balance / overdraft).
 */
public abstract class Account {

    private final String accountNumber;
    private final String ownerId;
    private String ownerName;
    private double balance;
    private boolean active;

    public Account(String accountNumber, String ownerId, String ownerName, double openingBalance) {
        this.accountNumber = accountNumber;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    /**
     * Deposits an amount into the account. Common to every account type,
     * so it is implemented once here rather than duplicated in subclasses.
     */
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    /**
     * Withdraws an amount from the account. Each account type enforces
     * its own minimum balance / overdraft rule, so this is abstract.
     */
    public abstract void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException;

    /** Returns a short label used in reports, e.g. "Savings" or "Current". */
    public abstract String getAccountType();

    /** Package-visible helper so subclasses can adjust the raw balance. */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - Owner: %s - Balance: %.2f - %s",
                accountNumber, getAccountType(), active ? "ACTIVE" : "CLOSED",
                ownerName, balance, active ? "" : "CLOSED");
    }
}
