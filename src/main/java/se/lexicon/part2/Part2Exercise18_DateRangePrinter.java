package se.lexicon.part2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startInput = scanner.nextLine();

        System.out.print("Enter end date   (yyyy-MM-dd): ");
        String endInput = scanner.nextLine();

        LocalDate startDate = LocalDate.parse(startInput);
        LocalDate endDate = LocalDate.parse(endInput);

        for (String dateText : generateDateRange(startDate, endDate)) {
            System.out.println(dateText);
        }
    }
}
