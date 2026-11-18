package se.lexicon.exceptionhandling;

import java.util.Scanner;

public class Exercise2_MultipleExceptionTypes {
    public static class OutOfRangeException extends Exception {
        public OutOfRangeException(String message) {
            super(message);
        }
    }

    static int validate(String input) throws OutOfRangeException {
        int number = Integer.parseInt(input);
        if (number < 1 || number > 100) {
            throw new OutOfRangeException("Value must be between 1 and 100.");
        }
        return number;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an integer from 1 to 100: ");
        String input = scanner.nextLine();
        try {
            System.out.println("Accepted value: " + validate(input));
        } catch (NumberFormatException exception) {
            System.out.println("Invalid input: please enter a whole number.");
        } catch (OutOfRangeException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
