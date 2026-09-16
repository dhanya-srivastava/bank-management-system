package bank.exceptions;

/**
 * Thrown when an operation references an account number that does not
 * exist, or an account that has been closed/deactivated.
 */
public class InvalidAccountException extends Exception {

    public InvalidAccountException(String message) {
        super(message);
    }
}
