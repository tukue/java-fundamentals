package se.lexicon;

import java.util.Locale;
import java.util.Scanner;

public class ConverterApp {

    // --- Currency conversion rates (fixed constants for this workshop) ---
    private static final double SEK_PER_USD = 10.87;
    private static final double SEK_PER_EUR = 11.32;

    // --- Menu constants ---
    private static final String BORDER = "==================================";
    private static final int OPTION_CURRENCY = 1;
    private static final int OPTION_EXIT = 2;
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readChoice(scanner, "Choose an option: ", MIN_MENU_OPTION, MAX_MENU_OPTION);

            switch (choice) {
                case OPTION_CURRENCY -> currencyConverter(scanner);
                case OPTION_EXIT -> running = false;
            }

            if (running) {
                running = askToContinue(scanner);
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println(BORDER);
        System.out.println("      LEXICON CONVERTER APP");
        System.out.println(BORDER);
        System.out.println(OPTION_CURRENCY + ". Currency Converter");
        System.out.println(OPTION_EXIT + ". Exit");
        System.out.println(BORDER);
    }

    // --- Currency Converter ---

    private static void currencyConverter(Scanner scanner) {
        System.out.println("--- Currency Converter ---");
        System.out.println("Convert:");
        System.out.println("  1. SEK to USD");
        System.out.println("  2. SEK to EUR");
        int direction = readChoice(scanner, "Your choice: ", 1, 2);

        double sek = readDouble(scanner, "Enter amount in SEK: ", 0,
                "Invalid value. Currency amount cannot be negative.");

        if (direction == 1) {
            System.out.printf(Locale.ROOT, "Result: %.2f SEK = %.2f USD%n", sek, sekToUsd(sek));
        } else {
            System.out.printf(Locale.ROOT, "Result: %.2f SEK = %.2f EUR%n", sek, sekToEur(sek));
        }
    }

    static double sekToUsd(double sek) {
        return sek / SEK_PER_USD;
    }

    static double sekToEur(double sek) {
        return sek / SEK_PER_EUR;
    }

    // --- Shared input helpers ---

    private static int readChoice(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            int value;
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            if (value < min || value > max) {
                System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                continue;
            }
            return value;
        }
    }

    private static double readDouble(Scanner scanner, String prompt, double min, String belowMinMessage) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            double value;
            try {
                value = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            if (value < min) {
                System.out.println(belowMinMessage);
                continue;
            }
            return value;
        }
    }

    private static boolean askToContinue(Scanner scanner) {
        while (true) {
            System.out.print("Continue? (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            switch (answer) {
                case "yes", "y" -> {
                    System.out.println();
                    return true;
                }
                case "no", "n" -> {
                    return false;
                }
                default -> System.out.println("Please enter yes or no.");
            }
        }
    }
}
