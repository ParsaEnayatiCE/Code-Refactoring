package graph;

import org.javatuples.Pair;
import java.util.ArrayList;

public class GraphTraversalFacade {
    public static ArrayList<Node> getAvailableNeighbors(Node node) {
        return node.getAvailableNeighbors();
    }

    public static ArrayList<Pair<Node, Integer>> getAvailableWeightedNeighbors(Node node) {
        return node.getAvailableWeightedNeighbors();
    }
} 