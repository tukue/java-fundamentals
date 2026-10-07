package se.lexicon;

import java.util.Scanner;

public class Exercise7_ConvertSeconds {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter seconds: ");
        int seconds = scanner.nextInt();

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        System.out.printf("%02d:%02d:%02d%n", hours, minutes, remainingSeconds);
    }
}