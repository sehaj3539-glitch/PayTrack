package exceptions;

// DepartmentNotFoundException.java - Custom Exception when department is not found
public class DepartmentNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public DepartmentNotFoundException() {
        super("Department not found in the records!");
    }

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
