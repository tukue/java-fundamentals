package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise02_EmailSlicer {

    public static String getUsername(String email) {
        return email.substring(0, email.indexOf('@'));
    }

    public static String getDomain(String email) {
        return email.substring(email.indexOf('@') + 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.println("Username: " + getUsername(email));
        System.out.println("Domain  : " + getDomain(email));
    }
}
