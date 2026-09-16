package bank;

import bank.exceptions.InsufficientFundsException;
import bank.exceptions.InvalidAmountException;

/**
 * Savings account: enforces a minimum balance and earns interest.
 */
public class SavingsAccount extends Account {

    public static final double MIN_BALANCE = 500.0;
    public static final double INTEREST_RATE = 0.035; // 3.5% p.a., for reporting/demo purposes

    public SavingsAccount(String accountNumber, String ownerId, String ownerName, double openingBalance) {
        super(accountNumber, ownerId, ownerName, openingBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (getBalance() - amount < MIN_BALANCE) {
            throw new InsufficientFundsException(
                    String.format("Withdrawal denied: balance cannot fall below minimum balance of %.2f", MIN_BALANCE));
        }
        setBalance(getBalance() - amount);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}
