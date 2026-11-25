package validators;

import exceptions.InvalidAttendanceException;
import exceptions.InvalidDateException;

// AttendanceValidator.java - Validates attendance dates and status values
public class AttendanceValidator {

    public static String validateAndFormatStatus(String statusInput) throws InvalidAttendanceException {
        if (statusInput == null) {
            throw new InvalidAttendanceException("Attendance status cannot be empty.");
        }
        String s = statusInput.trim();
        if (s.equalsIgnoreCase("P") || s.equalsIgnoreCase("Present")) {
            return "Present";
        } else if (s.equalsIgnoreCase("A") || s.equalsIgnoreCase("Absent")) {
            return "Absent";
        } else {
            throw new InvalidAttendanceException("Invalid status! Please enter 'Present' or 'Absent'.");
        }
    }

    public static void validateDateFormat(String dateStr) throws InvalidDateException {
        if (dateStr == null || !dateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new InvalidDateException("Invalid date format: " + dateStr + ". Expected format is YYYY-MM-DD.");
        }
    }
}
