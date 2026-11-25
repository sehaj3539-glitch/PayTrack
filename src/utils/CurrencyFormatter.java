package utils;

// CurrencyFormatter.java - Formats currency and monetary amounts consistently
public class CurrencyFormatter {

    public static String formatCurrency(double amount) {
        return String.format("Rs. %.2f", amount);
    }
}
