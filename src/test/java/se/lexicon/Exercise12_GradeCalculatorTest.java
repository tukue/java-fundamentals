package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise12_GradeCalculatorTest {
    @Test
    void grade_90_returnA() {
        assertEquals("A", grade(90));
    }

    @Test
    void grade_85_returnB() {
        assertEquals("B", grade(85));
    }

    @Test
    void grade_72_returnC() {
        assertEquals("C", grade(72));
    }

    @Test
    void grade_60_returnD() {
        assertEquals("D", grade(60));
    }

    @Test
    void grade_50_returnF() {
        assertEquals("F", grade(50));
    }

    @Test
    void grade_100_returnA() {
        assertEquals("A", grade(100));
    }

    @Test
    void grade_0_returnF() {
        assertEquals("F", grade(0));
    }

    @Test
    void grade_101_returnsInvalid() {
        assertEquals("Invalid score", grade(101));
    }

    @Test
    void grade_minus1_returnsInvalid() {
        assertEquals("Invalid score", grade(-1));
    }

    private String grade(int score) {
        return score < 0 || score > 100 ? "Invalid score"
                : score >= 90 ? "A"
                : score >= 80 ? "B"
                : score >= 70 ? "C"
                : score >= 60 ? "D"
                : "F";
    }
}