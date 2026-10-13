package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise10_NumberCruncherTest {
    @Test
    void findMax_42_7_19_returns42() {
        assertEquals(42, Part2Exercise10_NumberCruncher.findMax(42, 7, 19));
    }

    @Test
    void findMin_42_7_19_returns7() {
        assertEquals(7, Part2Exercise10_NumberCruncher.findMin(42, 7, 19));
    }

    @Test
    void average_42_7_19_returns2267() {
        assertEquals(22.6667, Part2Exercise10_NumberCruncher.average(42, 7, 19), 0.001);
    }

    @Test
    void maxMin_EqualNumbers_returnsSame() {
        assertEquals(5, Part2Exercise10_NumberCruncher.findMax(5, 5, 5));
        assertEquals(5, Part2Exercise10_NumberCruncher.findMin(5, 5, 5));
        assertEquals(5.0, Part2Exercise10_NumberCruncher.average(5, 5, 5), 0.0001);
    }

    @Test
    void maxMin_Negatives_returnsCorrectly() {
        assertEquals(-1, Part2Exercise10_NumberCruncher.findMax(-3, -1, -2));
        assertEquals(-3, Part2Exercise10_NumberCruncher.findMin(-3, -1, -2));
    }
}
