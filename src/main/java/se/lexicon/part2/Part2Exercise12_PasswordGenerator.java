package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise12_PasswordGenerator {

    public static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generatePassword(int length) {
        var sb = new StringBuilder();
        for (var i = 0; i < length; i++) {
            var index = (int) (Math.random() * CHARSET.length());
            sb.append(CHARSET.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter password length: ");
        var length = scanner.nextInt();
        System.out.println("Generated password: " + generatePassword(length));
    }
}
