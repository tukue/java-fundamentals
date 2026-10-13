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

    @Test
    void slice_SubdomainEmail_splitsCorrectly() {
        assertEquals("user", Part2Exercise02_EmailSlicer.getUsername("user@mail.example.co.uk"));
        assertEquals("mail.example.co.uk", Part2Exercise02_EmailSlicer.getDomain("user@mail.example.co.uk"));
    }

    @Test
    void slice_ShortEmail_splitsCorrectly() {
        assertEquals("a", Part2Exercise02_EmailSlicer.getUsername("a@b.co"));
        assertEquals("b.co", Part2Exercise02_EmailSlicer.getDomain("a@b.co"));
    }
}
