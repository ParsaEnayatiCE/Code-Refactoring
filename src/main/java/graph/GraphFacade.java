package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphFacade {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    public Node addNode(String name) {
        Node node = new Node(name);
        nodes.add(node);
        return node;
    }

    public Edge addEdge(Node a, Node b, boolean directed, int weight) {
        Edge.createEdge(a, b, directed, weight);
        // Assuming Edge.createEdge adds the edge to nodes, but we want to keep a list here too
        Edge edge = new Edge(a, b, directed, weight); // This line may need adjustment if Edge constructor is private
        edges.add(edge);
        return edge;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
} 