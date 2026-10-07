package se.lexicon;

import java.util.Scanner;

public class Exercise16_RunningTotal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var total = 0;
        var count = 0;
        var number = 0;

        do {
            System.out.print("Enter a number (0 to stop): ");
            number = scanner.nextInt();

            if (number != 0) {
                total += number;
                count++;

                System.out.println("Total: " + total + " | Count: " + count);
            }
        } while (number != 0);

        var average = (double) total / count;
        System.out.println("--- Summary ---");
        System.out.println("Count: " + count);
        System.out.println("Total: " + total);
        System.out.println("Average: " + average);
    }
}