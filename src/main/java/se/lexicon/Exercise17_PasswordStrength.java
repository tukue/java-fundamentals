package se.lexicon;

import java.util.Scanner;

public class Exercise17_PasswordStrength {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter password: ");
        String password = scanner.next();

        int ruleCount = 0;

        if (password.length() >= 8) {
            ruleCount++;
        }

        boolean hasUppercase = false;
        boolean hasDigit = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                hasUppercase = true;
            }
            if (ch >= '0' && ch <= '9') {
                hasDigit = true;
            }
        }

        if (hasUppercase) ruleCount++;
        if (hasDigit) ruleCount++;

        String rating;
        if (ruleCount == 3) {
            rating = "Strong";
        } else if (ruleCount == 2) {
            rating = "Medium";
        } else {
            rating = "Weak";
        }

        System.out.println("Rules met: " + ruleCount + "/3");
        System.out.println("Rating: " + rating);
    }
}