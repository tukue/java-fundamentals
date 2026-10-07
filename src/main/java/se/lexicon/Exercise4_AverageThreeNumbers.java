package se.lexicon;

import java.util.Scanner;

public class Exercise4_AverageThreeNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first number: ");
        var num1 = scanner.nextInt();
        System.out.print("Enter second number: ");
        var num2 = scanner.nextInt();
        System.out.print("Enter third number: ");
        var num3 = scanner.nextInt();

        var average = (num1 + num2 + num3) / 3.0;
        System.out.println("Average: " + average);
    }
}