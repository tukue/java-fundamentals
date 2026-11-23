package se.lexicon.exceptionhandling;

import java.util.Scanner;

public class Exercise7_CustomListExceptions {
    public static void main(String[] args) throws DuplicateNameException {
        Scanner scanner = new Scanner(System.in);
        NameList names = new NameList();
        names.addName("Alice");
        names.addName("Bob");

        System.out.print("Enter a name to add: ");
        try {
            names.addName(scanner.nextLine());
            System.out.println("Name added.");
        } catch (DuplicateNameException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        System.out.print("Enter a name to find: ");
        try {
            System.out.println("Found: " + names.findName(scanner.nextLine()));
        } catch (NameNotFoundException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
