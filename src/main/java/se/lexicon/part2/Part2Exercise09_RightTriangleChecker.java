package se.lexicon.part2;

import java.util.Arrays;
import java.util.Scanner;

public class Part2Exercise09_RightTriangleChecker {

    public static double longestSide(double a, double b, double c) {
        return Math.max(a, Math.max(b, c));
    }

    public static boolean isRightTriangle(double a, double b, double c) {
        var sides = new double[]{a, b, c};
        Arrays.sort(sides);
        return Math.abs(sides[0] * sides[0] + sides[1] * sides[1] - sides[2] * sides[2]) < 1e-9;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter side a: ");
        var a = scanner.nextDouble();
        System.out.print("Enter side b: ");
        var b = scanner.nextDouble();
        System.out.print("Enter side c: ");
        var c = scanner.nextDouble();
        System.out.println("Longest side: " + longestSide(a, b, c));
        if (isRightTriangle(a, b, c)) {
            System.out.println("This IS a right triangle.");
        } else {
            System.out.println("This is NOT a right triangle.");
        }
    }
}
