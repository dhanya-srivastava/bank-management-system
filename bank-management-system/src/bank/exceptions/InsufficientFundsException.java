package bank.exceptions;

/**
 * Thrown when a withdrawal or transfer would violate the account's
 * minimum balance / overdraft rules.
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
