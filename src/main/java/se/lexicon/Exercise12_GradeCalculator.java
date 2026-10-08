package se.lexicon;

import java.util.Scanner;

public class Exercise12_GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter score: ");
        var score = scanner.nextInt();

        var grade = score < 0 || score > 100 ? "Invalid score"
                : score >= 90 ? "A"
                : score >= 80 ? "B"
                : score >= 70 ? "C"
                : score >= 60 ? "D"
                : "F";

        if (score >= 0 && score <= 100) {
            System.out.println("Grade: " + grade);
        } else {
            System.out.println("Error: Score must be between 0 and 100");
        }
    }
}