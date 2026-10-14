package se.lexicon.part2;

import java.security.SecureRandom;
import java.util.Scanner;

public class Part2Exercise12_PasswordGenerator {

    public static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Password length cannot be negative");
        }
        var sb = new StringBuilder();
        for (var i = 0; i < length; i++) {
            var index = SECURE_RANDOM.nextInt(CHARSET.length());
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
