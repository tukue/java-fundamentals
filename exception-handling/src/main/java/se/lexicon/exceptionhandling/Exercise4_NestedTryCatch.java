package se.lexicon.exceptionhandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercise4_NestedTryCatch {
    static List<Integer> readIntegers(Path path) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            try {
                for (String token : line.trim().split("\\s+")) {
                    if (!token.isEmpty()) {
                        numbers.add(Integer.parseInt(token));
                    }
                }
            } catch (NumberFormatException exception) {
                System.out.println("Skipping invalid integer in line: " + line);
            }
        }
        return numbers;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to a text file containing integers: ");
        Path path = Path.of(scanner.nextLine());

        try {
            System.out.println("Integers: " + readIntegers(path));
        } catch (NoSuchFileException exception) {
            System.out.println("File not found: " + path);
        } catch (IOException exception) {
            System.out.println("Could not read the file: " + exception.getMessage());
        }
    }
}
