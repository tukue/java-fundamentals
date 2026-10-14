package se.lexicon.part2;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Part2Exercise15_AgeCalculator {

    public static int calculateAgeYears(LocalDate birthDate, LocalDate today) {
        return Period.between(birthDate, today).getYears();
    }

    public static long daysUntilNextBirthday(LocalDate birthDate, LocalDate today) {
        LocalDate nextBirthday = birthDate.withYear(today.getYear());

        if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
            nextBirthday = nextBirthday.plusYears(1);
        }

        return ChronoUnit.DAYS.between(today, nextBirthday);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter birth year : ");
        int year = scanner.nextInt();

        System.out.print("Enter birth month: ");
        int month = scanner.nextInt();

        System.out.print("Enter birth day  : ");
        int day = scanner.nextInt();

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();

        int age = calculateAgeYears(birthDate, today);
        long daysLeft = daysUntilNextBirthday(birthDate, today);

        System.out.println("Your age            : " + age + " years");
        System.out.println("Days until birthday : " + daysLeft);
    }
}
