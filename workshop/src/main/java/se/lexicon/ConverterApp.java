package se.lexicon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConverterApp {

    // --- Currency conversion rates (fixed constants for this workshop) ---
    private static final double SEK_PER_USD = 10.87;
    private static final double SEK_PER_EUR = 11.32;

    // --- Data storage units (binary: 1 unit = 1024 of the smaller) ---
    private static final String[] STORAGE_UNITS = {"Bytes", "KB", "MB", "GB", "TB"};
    private static final double BYTES_PER_UNIT_STEP = 1024.0;

    // --- Menu constants ---
    private static final String BORDER = "==================================";
    private static final int OPTION_CURRENCY = 1;
    private static final int OPTION_BMI = 2;
    private static final int OPTION_STORAGE = 3;
    private static final int OPTION_EXIT = 4;
    private static final int MIN_MENU_OPTION = 1;
    private static final int MAX_MENU_OPTION = 4;

    // --- Session history (Challenge 2) ---
    private static final List<String> history = new ArrayList<>();

    // --- Timestamp formatter (Challenge 3) ---
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        history.clear();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readChoice(scanner, "Choose an option: ", MIN_MENU_OPTION, MAX_MENU_OPTION);

            switch (choice) {
                case OPTION_CURRENCY -> currencyConverter(scanner);
                case OPTION_BMI -> bmiCalculator(scanner);
                case OPTION_STORAGE -> storageConverter(scanner);
                case OPTION_EXIT -> running = false;
            }

            if (running) {
                running = askToContinue(scanner);
            }
        }

        printHistory();
        System.out.println("Goodbye For now!");
    }

    private static void printMenu() {
        System.out.println(BORDER);
        System.out.println("      LEXICON CONVERTER APP");
        System.out.println(BORDER);
        System.out.println(OPTION_CURRENCY + ". Currency Converter");
        System.out.println(OPTION_BMI + ". BMI Calculator");
        System.out.println(OPTION_STORAGE + ". Data Storage Converter");
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

        String left;
        String right;
        if (direction == 1) {
            double usd = sekToUsd(sek);
            left = String.format(Locale.ROOT, "%.2f SEK", sek);
            right = String.format(Locale.ROOT, "%.2f USD", usd);
        } else {
            double eur = sekToEur(sek);
            left = String.format(Locale.ROOT, "%.2f SEK", sek);
            right = String.format(Locale.ROOT, "%.2f EUR", eur);
        }
        printResult(String.format(Locale.ROOT, "Result: %s = %s", left, right), left, right);
    }

    static double sekToUsd(double sek) {
        return sek / SEK_PER_USD;
    }

    static double sekToEur(double sek) {
        return sek / SEK_PER_EUR;
    }

    // --- BMI Calculator ---

    private static void bmiCalculator(Scanner scanner) {
        System.out.println("--- BMI Calculator ---");
        double weightKg = readDouble(scanner, "Enter weight in kg: ", 0.1,
                "Invalid value. Weight must be greater than zero.");
        System.out.println("Convert height in:");
        System.out.println("  1. Centimetres");
        System.out.println("  2. Metres");
        int unit = readChoice(scanner, "Your choice: ", 1, 2);

        double heightMetres;
        if (unit == 1) {
            double heightCm = readDouble(scanner, "Enter height in cm: ", 1.0,
                    "Invalid value. Height must be greater than zero.");
            heightMetres = heightCm / 100.0;
        } else {
            heightMetres = readDouble(scanner, "Enter height in metres: ", 0.1,
                    "Invalid value. Height must be greater than zero.");
        }

        double bmi = bmi(weightKg, heightMetres);
        String category = bmiCategory(bmi);
        String left = String.format(Locale.ROOT, "BMI %.2f", bmi);
        printResult(String.format(Locale.ROOT, "BMI: %.2f  ->  %s", bmi, category), left, category);
    }

    static double bmi(double weightKg, double heightMetres) {
        return weightKg / (heightMetres * heightMetres);
    }

    static String bmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        }
        return "Elevated";
    }

    // --- Data Storage Converter ---

    private static void storageConverter(Scanner scanner) {
        System.out.println("--- Data Storage Converter ---");
        printStorageUnits();
        int fromUnit = readChoice(scanner, "From unit: ", 1, STORAGE_UNITS.length);
        printStorageUnits();
        int toUnit = readChoice(scanner, "To unit: ", 1, STORAGE_UNITS.length);

        double value = readDouble(scanner, "Enter value in " + STORAGE_UNITS[fromUnit - 1] + ": ", 0,
                "Invalid value. Storage amount cannot be negative.");

        double converted = convertStorage(value, fromUnit, toUnit);
        String fromName = STORAGE_UNITS[fromUnit - 1];
        String toName = STORAGE_UNITS[toUnit - 1];
        String left = String.format(Locale.ROOT, "%.2f %s", value, fromName);
        String right = String.format(Locale.ROOT, "%.2f %s", converted, toName);
        printResult(String.format(Locale.ROOT, "Result: %s = %s", left, right), left, right);
    }

    private static void printStorageUnits() {
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < STORAGE_UNITS.length; i++) {
            options.append(i + 1).append(". ").append(STORAGE_UNITS[i]);
            if (i < STORAGE_UNITS.length - 1) {
                options.append("  ");
            }
        }
        System.out.println(options);
    }

    static double convertStorage(double value, int fromUnit, int toUnit) {
        int steps = fromUnit - toUnit;
        if (steps == 0) {
            return value;
        }
        return value * Math.pow(BYTES_PER_UNIT_STEP, steps);
    }

    // --- Result output with timestamp + history (Challenges 2 & 3) ---

    private static void printResult(String resultLine, String left, String right) {
        System.out.println(resultLine);
        System.out.println("Converted at: " + LocalDateTime.now().format(TIMESTAMP));
        history.add(String.format("%d.  %-20s = %-20s", history.size() + 1, left, right));
    }

    private static void printHistory() {
        System.out.println("\n=== Conversion History ===");
        if (history.isEmpty()) {
            System.out.println("(no conversions performed)");
        } else {
            for (String entry : history) {
                System.out.println(entry);
            }
        }
        System.out.println("==========================");
        System.out.println("Total conversions: " + history.size());
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
            if (!Double.isFinite(value)) {
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
