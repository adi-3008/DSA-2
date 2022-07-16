package com.DynamicProgramming;

import com.graphs.GraphAdjacencyMatrix;
import com.graphs.GraphAdjacencyMatrix.*;

public class MultiStageGraph {
    public static void main(String[] args){
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(8,4);

        Vertex v1 = new Vertex('S');
        Vertex v2 = new Vertex('A');
        Vertex v3 = new Vertex('B');
        Vertex v4 = new Vertex('C');
        Vertex v5 = new Vertex('D');
        Vertex v6 = new Vertex('E');
        Vertex v7 = new Vertex('F');
        Vertex v8 = new Vertex('T');


        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);


        graph.addEdge(v1,v2,1);
        graph.addEdge(v1,v3,2);
        graph.addEdge(v1,v4,5);
        graph.addEdge(v2,v5,4);
        graph.addEdge(v2,v6,11);
        graph.addEdge(v3,v5,9);
        graph.addEdge(v3,v6,5);
        graph.addEdge(v3,v7,16);
        graph.addEdge(v4,v7,2);
        graph.addEdge(v5,v8,18);
        graph.addEdge(v6,v8,13);
        graph.addEdge(v7,v8,2);

        System.out.println(findShortestPathTopDown(graph));
    }

    // bottom up
    public static int findShortestPath(GraphAdjacencyMatrix graph) {
        int[] T = new int[graph.V];

        T[graph.V - 1] = 0;

        for (int i = 0; i < T.length-1; i++) {
            T[i] = Integer.MAX_VALUE;
        }

        for (int i = graph.V - 2; i >= 0; i--) {
            for (int j = i+1; j < T.length; j++) {
                // if there is edge then only calculate min distance considering that edge.
                if (graph.weight(i,j) < Integer.MAX_VALUE){
                    T[i] = Math.min(T[i],graph.weight(i,j) + T[j]);
                }
            }
        }

        return T[0];
    }

    public static int findShortestPathTopDown(GraphAdjacencyMatrix graph){

        int[] T = new int[graph.V];

        T[graph.V - 1] = 0;

        for (int i = 0; i < T.length-1; i++) {
            T[i] = Integer.MAX_VALUE;
        }

        return helper(graph, T, 1, 0);
    }

    public static int helper(GraphAdjacencyMatrix graph, int[] T, int stage, int index){
        if (stage == graph.stages - 1)
            return graph.weight(index, graph.V-1);
        else if (T[index] < Integer.MAX_VALUE)
            return T[index];
        else {
            for (int i = index + 1; i < graph.V; i++) {
                // if there is edge then only calculate min distance considering that edge.
                if (graph.weight(index, i) < Integer.MAX_VALUE)
                    T[index] = Math.min(T[index],graph.weight(index, i) + helper(graph,T,stage + 1,i));
            }
            return T[index];
        }
    }

}
