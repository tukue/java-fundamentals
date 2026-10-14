package se.lexicon.part2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class Part2Exercise14_TodaysCalendar {

    public static String buildDateText(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String fullDay = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String fullMonth = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return fullDay + ", " + date.getDayOfMonth() + " " + fullMonth + " " + date.getYear();
    }

    public static String weekdayOrWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                ? "Weekend"
                : "Weekday";
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();

        System.out.println("Today is: " + buildDateText(today));
        System.out.println("It is a " + weekdayOrWeekend(today) + ".");
    }
}
