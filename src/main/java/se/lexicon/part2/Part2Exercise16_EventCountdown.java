package se.lexicon.part2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Part2Exercise16_EventCountdown {

    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        System.out.print("Enter event date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();

        LocalDate eventDate = LocalDate.parse(dateInput);
        LocalDate today = LocalDate.now();

        long daysDifference = ChronoUnit.DAYS.between(today, eventDate);

        if (daysDifference > 0) {
            System.out.println(eventName + " is in " + daysDifference + " days!");
        } else if (daysDifference < 0) {
            System.out.println(eventName + " was " + Math.abs(daysDifference) + " days ago.");
        } else {
            System.out.println(eventName + " is today.");
        }
    }
}
