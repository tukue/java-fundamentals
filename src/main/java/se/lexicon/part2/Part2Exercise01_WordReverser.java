package se.lexicon.part2;

import java.util.Scanner;

public class Part2Exercise01_WordReverser {

    public static String reverse(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String word = scanner.nextLine();
        System.out.println("Reversed: " + reverse(word));
    }
}
