package se.lexicon.exceptionhandling;

import java.io.PrintStream;
import java.util.Scanner;

public class Exercise5_FinallyBlock {
    static void divideAndReport(int numerator, int denominator, PrintStream output) {
        try {
            output.println("Result: " + numerator / denominator);
        } catch (ArithmeticException exception) {
            output.println("Cannot divide by zero.");
        } finally {
            output.println("Division attempt finished.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first integer: ");
        int numerator = scanner.nextInt();
        System.out.print("Enter the second integer: ");
        int denominator = scanner.nextInt();

        divideAndReport(numerator, denominator, System.out);
    }
}
