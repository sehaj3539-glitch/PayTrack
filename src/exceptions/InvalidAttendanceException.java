package exceptions;

// InvalidAttendanceException.java - Custom Exception for attendance errors
public class InvalidAttendanceException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidAttendanceException() {
        super("Invalid attendance status or value provided!");
    }

    public InvalidAttendanceException(String message) {
        super(message);
    }
}
