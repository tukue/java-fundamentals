package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise04_InitialsGenerator {

    public static String generateInitials(String fullName) {
        var parts = fullName.trim().split("\\s+");
        var sb = new StringBuilder();
        for (var part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0))).append('.');
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter full name: ");
        var fullName = scanner.nextLine();
        System.out.println("Initials: " + generateInitials(fullName));
    }
}
