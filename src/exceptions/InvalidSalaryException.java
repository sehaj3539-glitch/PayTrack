package exceptions;

// InvalidSalaryException.java - Custom Exception for invalid salary inputs or calculation bounds
public class InvalidSalaryException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidSalaryException() {
        super("Invalid salary amount or working days provided!");
    }

    public InvalidSalaryException(String message) {
        super(message);
    }
}
