package validators;

import exceptions.DepartmentNotFoundException;
import exceptions.InvalidInputException;

// DepartmentValidator.java - Validates department names and selection choices
public class DepartmentValidator {

    public static void validateDepartmentName(String deptName) throws InvalidInputException {
        if (deptName == null || deptName.trim().isEmpty()) {
            throw new InvalidInputException("Department name cannot be empty.");
        }
    }

    public static void validateDepartmentChoice(int choice, int maxDepartments) throws DepartmentNotFoundException {
        if (choice < 1 || choice > maxDepartments) {
            throw new DepartmentNotFoundException("Invalid department choice: " + choice + ". Must be between 1 and " + maxDepartments + ".");
        }
    }
}
