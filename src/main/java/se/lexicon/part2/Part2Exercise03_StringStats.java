package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise03_StringStats {

    // Returns {vowels, consonants, digits, spaces}
    public static int[] countStats(String word) {
        int vowels = 0, consonants = 0, digits = 0, spaces = 0;
        var vowelSet = "aeiouAEIOU";
        for (var i = 0; i < word.length(); i++) {
            var ch = word.charAt(i);
            if (ch == ' ') {
                spaces++;
            } else if (Character.isDigit(ch)) {
                digits++;
            } else if (Character.isLetter(ch)) {
                if (vowelSet.indexOf(ch) >= 0) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        return new int[]{vowels, consonants, digits, spaces};
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter a word: ");
        var word = scanner.nextLine();
        var stats = countStats(word);
        System.out.println("Vowels    : " + stats[0]);
        System.out.println("Consonants: " + stats[1]);
        System.out.println("Digits    : " + stats[2]);
        System.out.println("Spaces    : " + stats[3]);
    }
}
