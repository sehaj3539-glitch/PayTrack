package utils;

import exceptions.InvalidInputException;
import validators.EmployeeValidator;
import validators.PhoneValidator;

// InputValidator.java - Centralized Utility for Validating and Formatting User Inputs
public class InputValidator {

    public static String toTitleCase(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }
        String[] words = text.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1).toLowerCase());
                }
                if (i < words.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    public static String formatGender(String input) {
        if (input == null) return "Other";
        String s = input.trim();
        if (s.equalsIgnoreCase("MALE") || s.equalsIgnoreCase("M")) {
            return "Male";
        } else if (s.equalsIgnoreCase("FEMALE") || s.equalsIgnoreCase("F")) {
            return "Female";
        } else if (s.equalsIgnoreCase("OTHER") || s.equalsIgnoreCase("O")) {
            return "Other";
        }
        return null;
    }

    public static boolean isValidPhone(String phone) {
        return PhoneValidator.isValid(phone);
    }

    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 70;
    }

    public static boolean isValidSalary(double salary) {
        return salary >= 0.0;
    }

    public static void validateEmployeeInput(String name, int age, String phone, double basicSalary) throws InvalidInputException {
        EmployeeValidator.validateName(name);
        EmployeeValidator.validateAge(age);
        PhoneValidator.validatePhone(phone);
        if (!isValidSalary(basicSalary)) {
            throw new InvalidInputException("Basic salary cannot be negative.");
        }
    }
}
