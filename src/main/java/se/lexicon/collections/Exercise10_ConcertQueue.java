/*
 * Collection choice: Queue<String>, implemented with ArrayDeque
 *
 * - The scenario is classic FIFO: people join at the back (offer/add) and are
 *   let in from the front (poll/remove), so a Queue is the natural fit.
 * - ArrayDeque gives O(1) add and remove at both ends, stores no nulls,
 *   and is faster than LinkedList in practice.
 */
package se.lexicon.collections;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Exercise10_ConcertQueue {

    public static Queue<String> joinQueue(List<String> people) {
        var queue = new ArrayDeque<String>();
        queue.addAll(people);
        return queue;
    }

    public static List<String> firstInLine(Queue<String> queue, int howMany) {
        var letIn = new java.util.ArrayList<String>();
        for (var i = 0; i < howMany && !queue.isEmpty(); i++) {
            letIn.add(queue.poll());
        }
        return letIn;
    }

    public static void main(String[] args) {
        var queue = joinQueue(List.of("Alice", "Bob", "Joe", "Olivia", "Erik", "Fatima"));

        var letIn = firstInLine(queue, 2);
        for (var person : letIn) {
            System.out.println("Let in: " + person);
        }

        System.out.println("Remaining queue: " + queue);
    }
}
