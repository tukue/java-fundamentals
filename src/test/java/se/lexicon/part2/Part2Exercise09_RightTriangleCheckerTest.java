package se.lexicon.part2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Part2Exercise09_RightTriangleCheckerTest {
    @Test
    void isRightTriangle_345_returnsTrue() {
        assertTrue(Part2Exercise09_RightTriangleChecker.isRightTriangle(3, 4, 5));
    }

    @Test
    void isRightTriangle_346_returnsFalse() {
        assertFalse(Part2Exercise09_RightTriangleChecker.isRightTriangle(3, 4, 6));
    }

    @Test
    void longestSide_345_returns5() {
        assertEquals(5.0, Part2Exercise09_RightTriangleChecker.longestSide(3, 4, 5), 0.0001);
    }

    @Test
    void isRightTriangle_PermutedOrder_returnsTrue() {
        assertTrue(Part2Exercise09_RightTriangleChecker.isRightTriangle(5, 3, 4));
    }

    @Test
    void isRightTriangle_51213_returnsTrue() {
        assertTrue(Part2Exercise09_RightTriangleChecker.isRightTriangle(5, 12, 13));
    }
}
