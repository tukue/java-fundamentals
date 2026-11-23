package se.lexicon.exceptionhandling;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Exercise8_TryWithResources {
    static void writeText(Path path, String text) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the output file path: ");
        Path path = Path.of(scanner.nextLine());
        System.out.print("Enter the text to write: ");
        String text = scanner.nextLine();

        try {
            writeText(path, text);
            System.out.println("Text written to " + path);
        } catch (IOException exception) {
            System.out.println("Could not write the file: " + exception.getMessage());
        }
    }
}
