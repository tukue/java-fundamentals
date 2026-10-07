package se.lexicon;

import java.util.Scanner;

public class Exercise15_ReverseNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        var number = scanner.nextInt();

        var reversed = 0;
        var original = number;

        while (number != 0) {
            var digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }

        System.out.println("Reversed: " + reversed);
    }
}