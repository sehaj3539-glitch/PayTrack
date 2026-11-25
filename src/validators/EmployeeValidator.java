package validators;

import exceptions.InvalidInputException;

// EmployeeValidator.java - Validates employee name, age, and gender
public class EmployeeValidator {

    public static void validateName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Employee name cannot be empty.");
        }
    }

    public static void validateAge(int age) throws InvalidInputException {
        if (age < 18 || age > 70) {
            throw new InvalidInputException("Age must be between 18 and 70.");
        }
    }

    public static void validateGender(String gender) throws InvalidInputException {
        if (gender == null || gender.trim().isEmpty()) {
            throw new InvalidInputException("Gender cannot be empty.");
        }
    }
}
