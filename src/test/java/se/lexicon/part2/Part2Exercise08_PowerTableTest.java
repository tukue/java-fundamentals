package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise08_PowerTableTest {
    @Test
    void power_3to3_returns27() {
        assertEquals(27.0, Part2Exercise08_PowerTable.power(3, 3), 0.0001);
    }

    @Test
    void power_3to10_returns59049() {
        assertEquals(59049.0, Part2Exercise08_PowerTable.power(3, 10), 0.0001);
    }

    @Test
    void power_AnyBaseTo1_returnsBase() {
        assertEquals(5.0, Part2Exercise08_PowerTable.power(5, 1), 0.0001);
    }

    @Test
    void power_2to10_returns1024() {
        assertEquals(1024.0, Part2Exercise08_PowerTable.power(2, 10), 0.0001);
    }
}
