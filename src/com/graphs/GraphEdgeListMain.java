package com.graphs;

public class GraphEdgeListMain {
    public static void main(String[] args) {

        GraphEdgeList graph = new GraphEdgeList();
        graph.addVertices("A");
        graph.addVertices("B");
        graph.addVertices("C");
        graph.addVertices("D");
        graph.addVertices("E");


        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "B");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("E", "A");
        graph.addEdge("E", "D");

        System.out.println(graph.getNeighbour("A"));
        System.out.println(graph.isConnected("A", "A"));
    }

}
