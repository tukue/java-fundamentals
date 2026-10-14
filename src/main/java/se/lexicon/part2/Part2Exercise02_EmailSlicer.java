package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise02_EmailSlicer {

    public static String getUsername(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex < 0) {
            throw new IllegalArgumentException("Email must contain '@'");
        }
        return email.substring(0, atIndex);
    }

    public static String getDomain(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex < 0) {
            throw new IllegalArgumentException("Email must contain '@'");
        }
        return email.substring(atIndex + 1);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter email: ");
        var email = scanner.nextLine().trim();
        System.out.println("Username: " + getUsername(email));
        System.out.println("Domain  : " + getDomain(email));
    }
}
