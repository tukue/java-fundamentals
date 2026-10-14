package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise19_UnitConverter {

    public static double convert(double km) {
        return km * 0.621371;
    }

    public static double convert(double kg, String unit) {
        if ("lbs".equalsIgnoreCase(unit)) {
            return kg * 2.20462;
        }
        throw new IllegalArgumentException("Unsupported unit: " + unit);
    }

    public static double toFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    public static double toKelvin(double celsius) {
        return celsius + 273.15;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Choose conversion:");
        System.out.println("1. km -> miles");
        System.out.println("2. kg -> lbs");
        System.out.println("3. °C -> °F");
        System.out.println("4. °C -> K");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter value in km: ");
                double km = scanner.nextDouble();
                double miles = convert(km);
                System.out.printf("%.1f km = %.2f miles%n", km, miles);
            }
            case 2 -> {
                System.out.print("Enter value in kg: ");
                double kg = scanner.nextDouble();
                double pounds = convert(kg, "lbs");
                System.out.printf("%.1f kg = %.2f lbs%n", kg, pounds);
            }
            case 3, 4 -> {
                System.out.print("Enter temperature in °C: ");
                double celsius = scanner.nextDouble();
                if (choice == 3) {
                    System.out.printf("%.1f °C = %.2f °F%n", celsius, toFahrenheit(celsius));
                } else {
                    System.out.printf("%.1f °C = %.2f K%n", celsius, toKelvin(celsius));
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}
