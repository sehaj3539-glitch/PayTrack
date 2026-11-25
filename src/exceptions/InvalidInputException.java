package exceptions;

// InvalidInputException.java - Custom User-Defined Exception for Input Validation Errors
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public InvalidInputException() {
        super("Invalid input provided!");
    }

    // Parameterized constructor allowing custom descriptive error message
    public InvalidInputException(String message) {
        super(message);
    }
}
