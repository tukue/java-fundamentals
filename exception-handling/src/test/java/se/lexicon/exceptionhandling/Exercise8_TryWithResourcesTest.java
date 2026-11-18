package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Exercise8_TryWithResourcesTest {
    @TempDir
    Path tempDir;

    @Test
    void writeText_writesContentsAndClosesFile() throws IOException {
        Path file = tempDir.resolve("message.txt");

        Exercise8_TryWithResources.writeText(file, "Hello from try-with-resources!");

        assertEquals("Hello from try-with-resources!", Files.readString(file));
    }
}
