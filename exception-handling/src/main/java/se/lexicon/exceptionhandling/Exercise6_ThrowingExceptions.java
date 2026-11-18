package se.lexicon.exceptionhandling;

import java.util.Scanner;

public class Exercise6_ThrowingExceptions {
    static String validateEmail(String email) {
        if (email == null || !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        return email;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an email address: ");
        String email = scanner.nextLine();
        try {
            System.out.println("Valid email: " + validateEmail(email));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
