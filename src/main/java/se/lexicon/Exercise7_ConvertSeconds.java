package se.lexicon;

import java.util.Scanner;

public class Exercise7_ConvertSeconds {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter seconds: ");
        var seconds = scanner.nextInt();

        var hours = seconds / 3600;
        var minutes = (seconds % 3600) / 60;
        var remainingSeconds = seconds % 60;

        System.out.printf("%02d:%02d:%02d%n", hours, minutes, remainingSeconds);
    }
}