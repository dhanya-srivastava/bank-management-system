package bank;

import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAmountException;

/**
 * Current account: allows the balance to go negative up to an
 * overdraft limit, aimed at business/frequent-transaction customers.
 */
public class CurrentAccount extends Account {

    public static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(String accountNumber, String ownerId, String ownerName, double openingBalance) {
        super(accountNumber, ownerId, ownerName, openingBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException(
                    String.format("Withdrawal denied: exceeds overdraft limit of %.2f", OVERDRAFT_LIMIT));
        }
        setBalance(getBalance() - amount);
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}
