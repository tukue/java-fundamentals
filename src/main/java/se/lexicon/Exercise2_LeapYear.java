package se.lexicon;

import java.util.Scanner;

public class Exercise2_LeapYear {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a year: ");
        int year = scanner.nextInt();

        boolean isLeap = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
        System.out.println(year + " is " + (isLeap ? "" : "NOT ") + "a leap year.");
    }
}