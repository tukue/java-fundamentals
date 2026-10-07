package se.lexicon;

import java.util.Random;
import java.util.Scanner;

public class Exercise8_GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int secretNumber = random.nextInt(500) + 1; // 1 to 500
        Scanner scanner = new Scanner(System.in);

        int guesses = 0;
        int guess;

        do {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
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