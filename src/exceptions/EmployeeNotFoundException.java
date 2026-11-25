package exceptions;

// EmployeeNotFoundException.java - Custom User-Defined Exception
public class EmployeeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public EmployeeNotFoundException() {
        super("Employee not found in the records!");
    }

    // Parameterized constructor allowing custom descriptive error message
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
