package utils;

// ConsoleFormatter.java - Visual formatting helpers for console lines and banners
public class ConsoleFormatter {

    public static void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void printDoubleLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    public static void printSectionHeader(String title) {
        System.out.println("\n----------------------------------------------------");
        System.out.println("                 " + title.toUpperCase());
        System.out.println("----------------------------------------------------");
    }
}
