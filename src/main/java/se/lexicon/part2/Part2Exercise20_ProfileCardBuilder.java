package se.lexicon.part2;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Part2Exercise20_ProfileCardBuilder {

    public static int calculateAge(LocalDate birthDate, LocalDate today) {
        return Period.between(birthDate, today).getYears();
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter name      : ");
        String name = scanner.nextLine();

        LocalDate birthDate = readDate(scanner);

        System.out.print("Enter city      : ");
        String city = scanner.nextLine();

        System.out.print("Enter email     : ");
        String email = scanner.nextLine();

        int age = calculateAge(birthDate, LocalDate.now());

        var card = new StringBuilder();
        card.append("╔══════════════════════════════╗\n");
        card.append("║         Profile Card         ║\n");
        card.append("╠══════════════════════════════╣\n");
        card.append("║ Name  : ").append(String.format("%-20s", fit(name, 20))).append("║\n");
        card.append("║ Age   : ").append(String.format("%-20d", age)).append("║\n");
        card.append("║ City  : ").append(String.format("%-20s", fit(city, 20))).append("║\n");
        card.append("║ Email : ").append(String.format("%-20s", fit(email, 20))).append("║\n");
        card.append("╚══════════════════════════════╝");

        System.out.println(card);
    }

    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            System.out.print("Enter birth date: ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please use yyyy-MM-dd format.");
            }
        }
    }

    private static String fit(String text, int width) {
        if (text.length() <= width) {
            return text;
        }
        return text.substring(0, width - 3) + "...";
    }
}
