package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise07_CircleCalculator {

    public static double area(double radius) {
        return Math.PI * radius * radius;
    }

    public static double circumference(double radius) {
        return 2 * Math.PI * radius;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter radius: ");
        double radius = scanner.nextDouble();
        System.out.printf("Area         : %.2f%n", area(radius));
        System.out.printf("Circumference: %.2f%n", circumference(radius));
    }
}
