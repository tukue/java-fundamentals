package se.lexicon.part2;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Part2Exercise16_EventCountdown {

    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static String countdownMessage(String eventName, String dateInput, LocalDate today) {
        try {
            LocalDate eventDate = LocalDate.parse(dateInput);
            long daysDifference = daysBetween(today, eventDate);

            if (daysDifference > 0) {
                return eventName + " is in " + daysDifference + " days!";
            } else if (daysDifference < 0) {
                return eventName + " was " + Math.abs(daysDifference) + " days ago.";
            }
            return eventName + " is today.";
        } catch (DateTimeParseException exception) {
            return "Invalid date format. Please use yyyy-MM-dd.";
        }
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        System.out.print("Enter event date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();

        System.out.println(countdownMessage(eventName, dateInput, LocalDate.now()));
    }
}
