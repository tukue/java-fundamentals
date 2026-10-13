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
}
