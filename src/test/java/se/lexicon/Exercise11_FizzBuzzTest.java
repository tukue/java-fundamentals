package se.lexicon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Exercise11_FizzBuzzTest {
    @Test
    void fizzBuzz_1_returns1() {
        assertEquals("1", fizzBuzz(1));
    }

    @Test
    void fizzBuzz_3_returnsFizz() {
        assertEquals("Fizz", fizzBuzz(3));
    }

    @Test
    void fizzBuzz_5_returnsBuzz() {
        assertEquals("Buzz", fizzBuzz(5));
    }

    @Test
    void fizzBuzz_15_returnsFizzBuzz() {
        assertEquals("FizzBuzz", fizzBuzz(15));
    }

    @Test
    void fizzBuzz_6_returnsFizz() {
        assertEquals("Fizz", fizzBuzz(6));
    }

    @Test
    void fizzBuzz_10_returnsBuzz() {
        assertEquals("Buzz", fizzBuzz(10));
    }

    @Test
    void fizzBuzz_30_returnsFizzBuzz() {
        assertEquals("FizzBuzz", fizzBuzz(30));
    }

    private String fizzBuzz(int i) {
        if (i % 3 == 0 && i % 5 == 0) {
            return "FizzBuzz";
        } else if (i % 3 == 0) {
            return "Fizz";
        } else if (i % 5 == 0) {
            return "Buzz";
        } else {
            return String.valueOf(i);
        }
    }
}