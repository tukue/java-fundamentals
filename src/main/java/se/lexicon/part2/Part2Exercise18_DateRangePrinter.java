package se.lexicon.part2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Part2Exercise18_DateRangePrinter {

    public static java.util.List<String> generateDateRange(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy (eeee)");
        var dates = new java.util.ArrayList<String>();

        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            dates.add(current.format(outputFormatter));
            current = current.plusDays(1);
        }
        return dates;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        LocalDate startDate = readDate(scanner, "Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = readDate(scanner, "Enter end date   (yyyy-MM-dd): ");

        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date.");
            return;
        }

        for (String dateText : generateDateRange(startDate, endDate)) {
            System.out.println(dateText);
        }
    }

    private static LocalDate readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please use yyyy-MM-dd format.");
            }
        }
    }
}
