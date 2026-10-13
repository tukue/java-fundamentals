package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise08_PowerTable {

    public static double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter base: ");
        var base = scanner.nextDouble();
        for (var i = 1; i <= 10; i++) {
            System.out.printf("%s ^ %-2d = %10.1f%n", stripTrailingZero(base), i, power(base, i));
        }
    }

    private static String stripTrailingZero(double base) {
        if (base == (long) base) {
            return String.valueOf((long) base);
        }
        return String.valueOf(base);
    }
}
