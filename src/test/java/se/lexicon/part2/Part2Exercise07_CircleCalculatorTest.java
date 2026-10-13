package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise07_CircleCalculatorTest {
    @Test
    void area_Radius7_returns15394() {
        assertEquals(153.938, Part2Exercise07_CircleCalculator.area(7), 0.001);
    }

    @Test
    void circumference_Radius7_returns4398() {
        assertEquals(43.982, Part2Exercise07_CircleCalculator.circumference(7), 0.001);
    }
}
