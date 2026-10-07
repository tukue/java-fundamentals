package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise19_CountVowelsTest {
    @Test
    void countVowels_HelloWorld_returns3() {
        assertEquals(3, Exercise19_CountVowels.countVowels("Hello World"));
    }

    @Test
    void countVowels_Java_returns2() {
        assertEquals(2, Exercise19_CountVowels.countVowels("Java"));
    }

    @Test
    void countVowels_rhythm_returns0() {
        assertEquals(0, Exercise19_CountVowels.countVowels("rhythm"));
    }
}