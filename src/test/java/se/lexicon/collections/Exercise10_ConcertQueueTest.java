package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Exercise10_ConcertQueueTest {

    @Test
    void joinQueue_keepsInsertionOrder() {
        var queue = Exercise10_ConcertQueue.joinQueue(List.of("Alice", "Bob", "Joe"));
        assertEquals(List.of("Alice", "Bob", "Joe"), List.copyOf(queue));
    }

    @Test
    void firstInLine_removesFromFront() {
        var queue = Exercise10_ConcertQueue.joinQueue(List.of("Alice", "Bob", "Joe", "Olivia"));
        var letIn = Exercise10_ConcertQueue.firstInLine(queue, 2);
        assertEquals(List.of("Alice", "Bob"), letIn);
        assertEquals(List.of("Joe", "Olivia"), List.copyOf(queue));
    }

    @Test
    void firstInLine_moreThanQueueSize_emptiesQueue() {
        var queue = Exercise10_ConcertQueue.joinQueue(List.of("Alice"));
        var letIn = Exercise10_ConcertQueue.firstInLine(queue, 3);
        assertEquals(List.of("Alice"), letIn);
        assertTrue(queue.isEmpty());
    }
}
