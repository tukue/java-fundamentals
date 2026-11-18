package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Exercise4_NestedTryCatchTest {
    @TempDir
    Path tempDir;

    @Test
    void readIntegers_withValidAndInvalidTokens_parsesValidIntegers() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.writeString(file, "12 7\nnot-a-number\n-3");

        assertEquals(List.of(12, 7, -3), Exercise4_NestedTryCatch.readIntegers(file));
    }

    @Test
    void readIntegers_withMissingFile_throwsNoSuchFileException() {
        Path missingFile = tempDir.resolve("missing.txt");
        assertThrows(NoSuchFileException.class,
                () -> Exercise4_NestedTryCatch.readIntegers(missingFile));
    }
}
