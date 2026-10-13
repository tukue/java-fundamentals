package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise10_NumberCruncher {

    public static int findMax(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    public static int findMin(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    public static double average(int a, int b, int c) {
        return (a + b + c) / 3.0;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter first number : ");
        var a = scanner.nextInt();
        System.out.print("Enter second number: ");
        var b = scanner.nextInt();
        System.out.print("Enter third number : ");
        var c = scanner.nextInt();
        System.out.println("Maximum : " + findMax(a, b, c));
        System.out.println("Minimum : " + findMin(a, b, c));
        System.out.printf("Average : %.2f%n", average(a, b, c));
    }
}
