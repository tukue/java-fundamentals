package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise02_EmailSlicerTest {
    @Test
    void getUsername_FullEmail_returnsUsername() {
        assertEquals("first.last", Part2Exercise02_EmailSlicer.getUsername("first.last@example.com"));
    }

    @Test
    void getDomain_FullEmail_returnsDomain() {
        assertEquals("example.com", Part2Exercise02_EmailSlicer.getDomain("first.last@example.com"));
    }
}
