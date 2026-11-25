package validators;

import exceptions.InvalidInputException;

// PhoneValidator.java - Validates phone numbers (10 digits, numeric only)
public class PhoneValidator {

    public static void validatePhone(String phone) throws InvalidInputException {
        if (!isValid(phone)) {
            throw new InvalidInputException("Invalid phone number! Must contain exactly 10 digits (no letters or special characters).");
        }
    }

    public static boolean isValid(String phone) {
        return phone != null && phone.matches("^[0-9]{10}$");
    }
}
