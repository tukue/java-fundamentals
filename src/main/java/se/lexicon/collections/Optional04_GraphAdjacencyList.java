/*
 * Collection choice: HashMap<String, List<String>>
 *
 * - Each key is a node, each value is that node's neighbour list.
 * - Undirected: addEdge() appends to BOTH lists, so "A -> B" and "B -> A"
 *   are the same edge.
 * - Edges are guarded with contains(), so adding one twice is harmless.
 */
package se.lexicon.collections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Optional04_GraphAdjacencyList {

    private final Map<String, List<String>> graph = new LinkedHashMap<>();

    public void addNode(String node) {
        graph.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        addNode(from);
        addNode(to);
        if (!graph.get(from).contains(to)) {
            graph.get(from).add(to);
        }
        if (!graph.get(to).contains(from)) {
            graph.get(to).add(from);
        }
    }

    public List<String> neighbors(String node) {
        var list = graph.get(node);
        return list == null ? List.of() : List.copyOf(list);
    }

    public boolean areConnected(String first, String second) {
        return neighbors(first).contains(second);
    }

    public int nodeCount() {
        return graph.size();
    }

    public int edgeCount() {
        return graph.values().stream().mapToInt(List::size).sum() / 2;
    }

    public void printNeighbors(String node) {
        if (!graph.containsKey(node)) {
            System.out.println("Node not found: " + node);
            return;
        }
        System.out.println("Neighbours of " + node + ": " + graph.get(node));
    }

    public static Optional04_GraphAdjacencyList sample() {
        var map = new Optional04_GraphAdjacencyList();
        map.addEdge("Stockholm", "Uppsala");
        map.addEdge("Stockholm", "Gothenburg");
        map.addEdge("Stockholm", "Linköping");
        map.addEdge("Gothenburg", "Malmö");
        map.addEdge("Malmö", "Linköping");
        map.addEdge("Linköping", "Norrköping");
        return map;
    }

    public static void main(String[] args) {
        var map = sample();
        System.out.println("Nodes: " + map.nodeCount() + ", edges: " + map.edgeCount());
        map.printNeighbors("Stockholm");
        map.printNeighbors("Norrköping");
        map.printNeighbors("Berlin");
        System.out.println("Stockholm - Uppsala connected? " + map.areConnected("Stockholm", "Uppsala"));
        System.out.println("Uppsala - Malmö connected?    " + map.areConnected("Uppsala", "Malmö"));
    }
}
