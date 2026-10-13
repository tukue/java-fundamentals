package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise06_WordCounter {

    public static int totalWords(String sentence) {
        String trimmed = sentence.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }

    public static int countOccurrences(String sentence, String target) {
        String trimmed = sentence.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (String word : trimmed.split("\\s+")) {
            if (word.equalsIgnoreCase(target)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a sentence  : ");
        String sentence = scanner.nextLine();
        System.out.print("Enter word to find: ");
        String target = scanner.nextLine().trim();
        System.out.println("Total words : " + totalWords(sentence));
        System.out.println("\"" + target + "\" appears: " + countOccurrences(sentence, target) + " times");
    }
}
