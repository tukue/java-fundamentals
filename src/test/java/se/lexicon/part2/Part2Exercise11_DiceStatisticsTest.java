package se.lexicon.part2;

import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise11_DiceStatisticsTest {
    @Test
    void isDouble_SameValues_returnsTrue() {
        assertTrue(Part2Exercise11_DiceStatistics.isDouble(3, 3));
        assertTrue(Part2Exercise11_DiceStatistics.isDouble(6, 6));
    }

    @Test
    void isDouble_DifferentValues_returnsFalse() {
        assertFalse(Part2Exercise11_DiceStatistics.isDouble(5, 2));
        assertFalse(Part2Exercise11_DiceStatistics.isDouble(1, 4));
    }

    @Test
    void rollDie_AlwaysBetween1And6() {
        var random = new Random(42);
        for (var i = 0; i < 200; i++) {
            var roll = Part2Exercise11_DiceStatistics.rollDie(random);
            assertTrue(roll >= 1 && roll <= 6);
        }
    }
}
