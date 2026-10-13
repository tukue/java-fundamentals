package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise06_WordCounter {

    public static int totalWords(String sentence) {
        var trimmed = sentence.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }

    public static int countOccurrences(String sentence, String target) {
        var trimmed = sentence.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        var count = 0;
        for (var word : trimmed.split("\\s+")) {
            if (word.equalsIgnoreCase(target)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter a sentence  : ");
        var sentence = scanner.nextLine();
        System.out.print("Enter word to find: ");
        var target = scanner.nextLine().trim();
        System.out.println("Total words : " + totalWords(sentence));
        System.out.println("\"" + target + "\" appears: " + countOccurrences(sentence, target) + " times");
    }
}
