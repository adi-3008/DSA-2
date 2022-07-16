package com.graphs;

public class GraphAdjacencyListMain {
    public static void main(String[] args) {
        Graph graph = new Graph(7);

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
//        graph.addVertex("H");



        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 3);
        graph.addEdge(3, 2);
        graph.addEdge(4, 5);


        System.out.println(graph.getNeighbour(0));
//        System.out.println(graph.isConnected(3, 1));

//        graph.depthFirstSearch();
//        System.out.println();
//        graph.breadthFirstSearch();
        System.out.println(graph.connectedComp());

    }

}
