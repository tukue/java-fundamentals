package se.lexicon.part2;

import java.util.Random;
import java.util.Scanner;

public class Part2Exercise11_DiceStatistics {

    public static int rollDie(Random random) {
        return random.nextInt(6) + 1;
    }

    public static boolean isDouble(int die1, int die2) {
        return die1 == die2;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("How many rolls? ");
        var rolls = scanner.nextInt();
        var random = new Random();
        var doubles = 0;
        for (var i = 1; i <= rolls; i++) {
            var die1 = rollDie(random);
            var die2 = rollDie(random);
            var sum = die1 + die2;
            if (isDouble(die1, die2)) {
                doubles++;
                System.out.println("Roll " + i + ": " + die1 + " + " + die2 + " = " + sum + "  DOUBLE!");
            } else {
                System.out.println("Roll " + i + ": " + die1 + " + " + die2 + " = " + sum);
            }
        }
        System.out.println("Doubles rolled: " + doubles + " out of " + rolls);
    }
}
