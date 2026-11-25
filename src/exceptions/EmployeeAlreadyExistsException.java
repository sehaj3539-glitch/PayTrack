package exceptions;

// EmployeeAlreadyExistsException.java - Custom Exception when employee record already exists
public class EmployeeAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmployeeAlreadyExistsException() {
        super("Employee already exists in the system!");
    }

    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
