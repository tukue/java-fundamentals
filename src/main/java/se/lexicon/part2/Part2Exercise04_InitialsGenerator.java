package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise04_InitialsGenerator {

    public static String generateInitials(String fullName) {
        String[] parts = fullName.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0))).append('.');
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.println("Initials: " + generateInitials(fullName));
    }
}
