package se.lexicon.collections;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Optional04_GraphAdjacencyListTest {

    @Test
    void sampleGraph_hasAtLeastFiveNodesAndSixEdges() {
        var map = Optional04_GraphAdjacencyList.sample();
        assertTrue(map.nodeCount() >= 5, "expected at least 5 nodes");
        assertTrue(map.edgeCount() >= 6, "expected at least 6 edges");
    }

    @Test
    void neighbors_returnsAdjacentNodes() {
        var map = Optional04_GraphAdjacencyList.sample();
        assertEquals(List.of("Uppsala", "Gothenburg", "Linköping"), map.neighbors("Stockholm"));
        assertEquals(List.of("Stockholm", "Malmö", "Norrköping"), map.neighbors("Linköping"));
    }

    @Test
    void neighbors_unknownNode_returnsEmptyList() {
        assertEquals(List.of(), Optional04_GraphAdjacencyList.sample().neighbors("Berlin"));
    }

    @Test
    void areConnected_isTrueForDirectlyLinkedNodes() {
        var map = Optional04_GraphAdjacencyList.sample();
        assertTrue(map.areConnected("Stockholm", "Uppsala"));
        assertTrue(map.areConnected("Uppsala", "Stockholm"));
    }

    @Test
    void areConnected_isFalseWhenNoDirectEdge() {
        var map = Optional04_GraphAdjacencyList.sample();
        assertFalse(map.areConnected("Uppsala", "Malmö"));
        assertFalse(map.areConnected("Stockholm", "Berlin"));
        assertFalse(map.areConnected("Stockholm", "Stockholm"));
    }

    @Test
    void addEdge_isUndirected_andIgnoresDuplicates() {
        var map = new Optional04_GraphAdjacencyList();
        map.addEdge("A", "B");
        map.addEdge("B", "A");
        assertEquals(List.of("B"), map.neighbors("A"));
        assertEquals(List.of("A"), map.neighbors("B"));
        assertEquals(1, map.edgeCount());
        assertEquals(2, map.nodeCount());
    }

    @Test
    void printNeighbors_writesNeighbourList() {
        var out = new ByteArrayOutputStream();
        var original = System.out;
        System.setOut(new PrintStream(out));
        try {
            Optional04_GraphAdjacencyList.sample().printNeighbors("Stockholm");
        } finally {
            System.setOut(original);
        }
        assertTrue(out.toString().contains("Neighbours of Stockholm: [Uppsala, Gothenburg, Linköping]"));
    }

    @Test
    void printNeighbors_unknownNode_reportsIt() {
        var out = new ByteArrayOutputStream();
        var original = System.out;
        System.setOut(new PrintStream(out));
        try {
            Optional04_GraphAdjacencyList.sample().printNeighbors("Berlin");
        } finally {
            System.setOut(original);
        }
        assertTrue(out.toString().contains("Node not found: Berlin"));
    }
}
