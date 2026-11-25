package exceptions;

// InvalidDateException.java - Custom Exception for invalid date formats or values
public class InvalidDateException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidDateException() {
        super("Invalid date format provided! Expected format: YYYY-MM-DD");
    }

    public InvalidDateException(String message) {
        super(message);
    }
}
