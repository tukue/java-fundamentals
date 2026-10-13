package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise02_EmailSlicerTest {
    @Test
    void getUsername_FullEmail_returnsUsername() {
        assertEquals("mehrdad.javan", Part2Exercise02_EmailSlicer.getUsername("mehrdad.javan@lexicon.se"));
    }

    @Test
    void getDomain_FullEmail_returnsDomain() {
        assertEquals("lexicon.se", Part2Exercise02_EmailSlicer.getDomain("mehrdad.javan@lexicon.se"));
    }
}
