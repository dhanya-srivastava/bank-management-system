package bank.exceptions;

/**
 * Thrown when a transaction amount is invalid, e.g. zero, negative,
 * or exceeds a sanity limit.
 */
public class InvalidAmountException extends Exception {

    public InvalidAmountException(String message) {
        super(message);
    }
}
