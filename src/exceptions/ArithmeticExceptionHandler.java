package exceptions;

// ArithmeticExceptionHandler.java - Custom Exception for Arithmetic & Calculation Errors
public class ArithmeticExceptionHandler extends Exception {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public ArithmeticExceptionHandler() {
        super("Arithmetic calculation error encountered!");
    }

    // Parameterized constructor allowing custom descriptive error message
    public ArithmeticExceptionHandler(String message) {
        super(message);
    }

    // Helper validation for working days to prevent division by zero
    public static void validateWorkingDays(int totalWorkingDays) throws ArithmeticExceptionHandler {
        if (totalWorkingDays <= 0) {
            throw new ArithmeticExceptionHandler("Total working days must be greater than zero! (Division by zero prevented)");
        }
    }

    // Helper validation for present days bounds
    public static void validatePresentDays(int presentDays, int totalWorkingDays) throws ArithmeticExceptionHandler {
        if (presentDays < 0) {
            throw new ArithmeticExceptionHandler("Present days cannot be negative!");
        }
        if (presentDays > totalWorkingDays) {
            throw new ArithmeticExceptionHandler("Present days (" + presentDays + ") cannot exceed total working days (" + totalWorkingDays + ")!");
        }
    }
}
