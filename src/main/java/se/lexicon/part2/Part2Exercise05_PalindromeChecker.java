package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise05_PalindromeChecker {

    public static boolean isPalindrome(String word) {
        var lower = word.toLowerCase();
        return lower.equals(new StringBuilder(lower).reverse().toString());
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter a word: ");
        var word = scanner.nextLine();
        if (isPalindrome(word)) {
            System.out.println(word + " is a palindrome.");
        } else {
            System.out.println(word + " is NOT a palindrome.");
        }
    }
}
