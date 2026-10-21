package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Optional01_RotateListTest {

    @Test
    void rotateRight_sampleInputRotatesByTwo() {
        var input = Optional01_RotateList.sample();
        assertEquals(List.of(4, 5, 1, 2, 3), Optional01_RotateList.rotateRight(input, 2));
    }

    @Test
    void rotateRight_kLargerThanList_wrapsAround() {
        var input = List.of(1, 2, 3, 4, 5);
        assertEquals(List.of(4, 5, 1, 2, 3), Optional01_RotateList.rotateRight(input, 7));
        assertEquals(List.of(1, 2, 3, 4, 5), Optional01_RotateList.rotateRight(input, 10));
    }

    @Test
    void rotateRight_negativeK_rotatesLeft() {
        assertEquals(List.of(2, 3, 4, 5, 1), Optional01_RotateList.rotateRight(List.of(1, 2, 3, 4, 5), -1));
    }

    @Test
    void rotateRight_zeroKeepsOrder() {
        assertEquals(List.of(1, 2, 3), Optional01_RotateList.rotateRight(List.of(1, 2, 3), 0));
    }

    @Test
    void rotateRight_doesNotModifyOriginalList() {
        var input = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        Optional01_RotateList.rotateRight(input, 2);
        assertEquals(List.of(1, 2, 3, 4, 5), input);
    }

    @Test
    void rotateRight_emptyOrSingleElement_returnsSameContent() {
        assertEquals(List.of(), Optional01_RotateList.rotateRight(List.of(), 3));
        assertEquals(List.of(9), Optional01_RotateList.rotateRight(List.of(9), 5));
    }

    @Test
    void rotateRight_preservesSizeAndElements() {
        var input = List.of(1, 2, 3, 4, 5);
        var rotated = Optional01_RotateList.rotateRight(input, 2);
        assertEquals(input.size(), rotated.size());
        assertTrue(rotated.containsAll(input));
    }
}
