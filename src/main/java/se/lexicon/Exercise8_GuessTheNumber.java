package se.lexicon;

import java.util.Random;
import java.util.Scanner;

public class Exercise8_GuessTheNumber {
    public static void main(String[] args) {
        var random = new Random();
        var secretNumber = random.nextInt(500) + 1;

        var scanner = new Scanner(System.in);
        var guesses = 0;

        do {
            System.out.print("Enter your guess: ");
            var guess = scanner.nextInt();
            guesses++;

            if (guess < secretNumber) {
                System.out.println("Too small!");
            } else if (guess > secretNumber) {
                System.out.println("Too big!");
            }
        } while (guess != secretNumber);

        System.out.println("Correct! You got it in " + guesses + " guesses.");
    }
}