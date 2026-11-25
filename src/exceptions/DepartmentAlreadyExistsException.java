package exceptions;

// DepartmentAlreadyExistsException.java - Custom Exception when duplicate department is added
public class DepartmentAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public DepartmentAlreadyExistsException() {
        super("Department already exists in the records!");
    }

    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
