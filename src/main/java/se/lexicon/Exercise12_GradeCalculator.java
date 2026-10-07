package se.lexicon;

import java.util.Scanner;

public class Exercise12_GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter score: ");
        int score = scanner.nextInt();

        String grade;
        if (score >= 90 && score <= 100) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60) {
            grade = "D";
        } else if (score >= 0) {
            grade = "F";
        } else {
            grade = "Invalid score";
        }

        if (score >= 0 && score <= 100) {
            System.out.println("Grade: " + grade);
        } else {
            System.out.println("Error: Score must be between 0 and 100");
        }
    }
}