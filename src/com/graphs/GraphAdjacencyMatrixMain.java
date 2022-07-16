package com.graphs;
import com.graphs.GraphAdjacencyMatrix.Vertex;

public class GraphAdjacencyMatrixMain {
    public static void main(String[] args) {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(5);

        Vertex v1 = new Vertex('A');
        Vertex v2 = new Vertex('B');
        Vertex v3 = new Vertex('C');
        Vertex v4 = new Vertex('D');
        Vertex v5 = new Vertex('E');

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        graph.addEdge(v1, v2,2);
        graph.addEdge(v1, v3,4);
        graph.addEdge(v3, v2,6);
        graph.addEdge(v3, v4,7);
        graph.addEdge(v3, v5,7);
        graph.addEdge(v5, v1,14);
        graph.addEdge(v5, v4,56);

        System.out.println(graph.getNeighbour(v1));
        System.out.println(graph.isConnected(v1, v3));
    }
}
