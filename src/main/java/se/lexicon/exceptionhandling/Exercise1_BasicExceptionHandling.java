package se.lexicon.exceptionhandling;

import java.util.Scanner;

public class Exercise1_BasicExceptionHandling {
    static int divide(int numerator, int denominator) {
        return numerator / denominator;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first integer: ");
        int numerator = scanner.nextInt();
        System.out.print("Enter the second integer: ");
        int denominator = scanner.nextInt();

        try {
            System.out.println("Result: " + divide(numerator, denominator));
        } catch (ArithmeticException exception) {
            System.out.println("Cannot divide by zero.");
        }
    }
}
