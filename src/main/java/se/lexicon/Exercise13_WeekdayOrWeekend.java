package se.lexicon;

import java.util.Scanner;

public class Exercise13_WeekdayOrWeekend {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter day: ");
        var day = scanner.next();

        var result = switch (day) {
            case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Weekday";
            case "Saturday", "Sunday" -> "Weekend";
            default -> "Hmm, I'm not sure about that day";
        };

        System.out.println(result);
    }
}