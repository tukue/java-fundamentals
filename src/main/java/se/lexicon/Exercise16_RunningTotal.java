package se.lexicon;

import java.util.Scanner;

public class Exercise16_RunningTotal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int total = 0;
        int count = 0;
        int number;

        do {
            System.out.print("Enter a number (0 to stop): ");
            number = scanner.nextInt();

            if (number != 0) {
                total += number;
                count++;

                System.out.println("Total: " + total + " | Count: " + count);
            }
        } while (number != 0);

        double average = (double) total / count;
        System.out.println("--- Summary ---");
        System.out.println("Count: " + count);
        System.out.println("Total: " + total);
        System.out.println("Average: " + average);
    }
}