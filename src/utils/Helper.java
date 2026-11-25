package utils;

// Helper.java - Helper Utility delegating to specialized formatters
public class Helper {

    public static String formatCurrency(double amount) {
        return CurrencyFormatter.formatCurrency(amount);
    }

    public static void printLine(int length) {
        ConsoleFormatter.printLine(length);
    }

    public static void printDoubleLine(int length) {
        ConsoleFormatter.printDoubleLine(length);
    }
}
