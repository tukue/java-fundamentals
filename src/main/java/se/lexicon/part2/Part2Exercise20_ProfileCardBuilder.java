package se.lexicon.part2;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Part2Exercise20_ProfileCardBuilder {

    public static int calculateAge(LocalDate birthDate, LocalDate today) {
        return Period.between(birthDate, today).getYears();
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter name      : ");
        String name = scanner.nextLine();

        System.out.print("Enter birth date: ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter city      : ");
        String city = scanner.nextLine();

        System.out.print("Enter email     : ");
        String email = scanner.nextLine();

        int age = calculateAge(birthDate, LocalDate.now());

        var card = new StringBuilder();
        card.append("╔══════════════════════════════╗\n");
        card.append("║         Profile Card         ║\n");
        card.append("╠══════════════════════════════╣\n");
        card.append("║ Name  : ").append(String.format("%-20s", name)).append("║\n");
        card.append("║ Age   : ").append(String.format("%-20d", age)).append("║\n");
        card.append("║ City  : ").append(String.format("%-20s", city)).append("║\n");
        card.append("║ Email : ").append(String.format("%-20s", email)).append("║\n");
        card.append("╚══════════════════════════════╝");

        System.out.println(card);
    }
}
