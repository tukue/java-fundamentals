package se.lexicon;

import java.util.Scanner;

public class Exercise5_GreetUser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();

        System.out.println("Hello, " + firstName + " " + lastName + "! Welcome aboard.");
    }
}