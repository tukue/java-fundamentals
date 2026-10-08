package se.lexicon;

import java.util.Scanner;

public class Exercise17_PasswordStrength {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter password: ");
        var password = scanner.next();

        var ruleCount = 0;

        if (password.length() >= 8) {
            ruleCount++;
        }

        var hasLetter = false;
        var hasDigit = false;

        for (var i = 0; i < password.length(); i++) {
            var ch = password.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                hasLetter = true;
            }
            if (ch >= '0' && ch <= '9') {
                hasDigit = true;
            }
        }

        if (hasLetter) ruleCount++;
        if (hasDigit) ruleCount++;

        var rating = switch (ruleCount) {
            case 3 -> "Strong";
            case 2 -> "Medium";
            default -> "Weak";
        };

        System.out.println("Rules met: " + ruleCount + "/3");
        System.out.println("Rating: " + rating);
    }
}