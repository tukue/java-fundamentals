package se.lexicon.exceptionhandling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameListTest {
    @Test
    void addAndFindName_returnsAddedName() throws Exception {
        NameList names = new NameList();
        names.addName("Ada Lovelace");

        assertEquals("Ada Lovelace", names.findName("ada lovelace"));
    }

    @Test
    void findName_whenMissing_throwsNameNotFoundException() {
        NameList names = new NameList();

        assertThrows(NameNotFoundException.class, () -> names.findName("Alex"));
    }

    @Test
    void addName_whenDuplicate_throwsDuplicateNameException() throws Exception {
        NameList names = new NameList();
        names.addName("Ada");

        assertThrows(DuplicateNameException.class, () -> names.addName("ada"));
        assertEquals(1, names.getNames().size());
    }
}
