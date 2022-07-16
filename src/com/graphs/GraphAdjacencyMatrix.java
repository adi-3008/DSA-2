package com.graphs;

import java.util.ArrayList;
import java.util.Arrays;

// directed weighted graph.
public class GraphAdjacencyMatrix {
    public int V;
    public ArrayList<Vertex> vertexList;
    public int[][] adjacencyMatrix;
    public int stages;

    public GraphAdjacencyMatrix(int v){
        this.vertexList = new ArrayList<>(v);
        this.adjacencyMatrix = new int[v][v];
        for (int[] matrix : adjacencyMatrix) {
            Arrays.fill(matrix, Integer.MAX_VALUE);
        }
    }

    public GraphAdjacencyMatrix(int v, int stages){
        this.vertexList = new ArrayList<>(v);
        this.adjacencyMatrix = new int[v][v];
        this.stages = stages;
        for (int[] matrix : adjacencyMatrix) {
            Arrays.fill(matrix, Integer.MAX_VALUE);
        }
    }

    public static class Vertex implements Comparable<Vertex>{
        public char ch;
        public int index;
        public int d;

        public Vertex(char ch) {
            this.ch = ch;
        }

        @Override
        public int compareTo(Vertex v) {
            return this.d-v.d;
        }

        @Override
        public String toString() {
            return this.ch + "";
        }
    }

    public void addVertex(Vertex vertex){
        vertex.index = V++;
        vertexList.add(vertex);
    }

    public void addEdge(Vertex u, Vertex v, int weight){
        int i = u.index;
        int j = v.index;
        adjacencyMatrix[i][j] = weight;
    }

    // find if there is an Edge between two vertex or not.
    public boolean isConnected(Vertex u, Vertex v){
        int i = u.index;
        int j = v.index;
        return adjacencyMatrix[i][j] < Integer.MAX_VALUE;
    }

    // find neighbour
    public ArrayList<Vertex> getNeighbour(Vertex u){
        ArrayList<Vertex> list = new ArrayList<>();
        int i = u.index;
        for (int j = 0; j < adjacencyMatrix[i].length; j++) {
            if (adjacencyMatrix[i][j] < Integer.MAX_VALUE)
                list.add(vertexList.get(j));
        }
        return list;
    }

    public int weight(int i, int j){
        return adjacencyMatrix[i][j];
    }
}
