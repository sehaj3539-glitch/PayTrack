package validators;

import exceptions.InvalidSalaryException;

// SalaryValidator.java - Validates salary bounds and working days
public class SalaryValidator {

    public static void validateBasicSalary(double basicSalary) throws InvalidSalaryException {
        if (basicSalary < 0.0) {
            throw new InvalidSalaryException("Basic salary cannot be negative.");
        }
    }

    public static void validateWorkingDays(int totalWorkingDays) throws InvalidSalaryException {
        if (totalWorkingDays <= 0) {
            throw new InvalidSalaryException("Total working days must be greater than zero! (Division by zero prevented)");
        }
    }

    public static void validatePresentDays(int presentDays, int totalWorkingDays) throws InvalidSalaryException {
        if (presentDays < 0) {
            throw new InvalidSalaryException("Present days cannot be negative!");
        }
        if (presentDays > totalWorkingDays) {
            throw new InvalidSalaryException("Present days (" + presentDays + ") cannot exceed total working days (" + totalWorkingDays + ")!");
        }
    }
}
