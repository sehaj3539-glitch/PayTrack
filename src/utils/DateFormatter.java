package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// DateFormatter.java - Utilities for handling and validating application dates
public class DateFormatter {

    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateStr.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
