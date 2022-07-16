package com.graphs;

import java.util.ArrayList;

public class GraphEdgeList {

    private int V;
    private int E;
    private ArrayList<String> vertex_list;
    private ArrayList<Edge> edge_list;

    public GraphEdgeList(){
        this.edge_list = new ArrayList<>();
        this.vertex_list = new ArrayList<>();
        this.V = 0;
        this.E = 0;
    }

    // for directed graph
    class Edge {
        int origin;
        int dest;

        Edge(int from, int to){
            this.origin = from;
            this.dest = to;
        }

        @Override
        public String toString() {
            return "[" +
                    vertex_list.get(origin) + " " +
                    vertex_list.get(dest) +
                    ']';
        }
    }

    public int getV(){
        return V;
    }

    public int getE(){
        return E;
    }

    public void addVertices(String s){
        vertex_list.add(s);
        V++;
    }

    public void addEdge(String v1, String v2){
        int i = search(v1);
        int j = search(v2);
        addEdge(i,j);
    }

    private void addEdge(int from, int to){
        edge_list.add(new Edge(from, to));
        E++;
    }

    // get the neighbour of a giver vertex
    public ArrayList<String> getNeighbour(String s){
        ArrayList<String> list = new ArrayList<>();
        int i = search(s);
        for (Edge edgeList : edge_list) {
            if (edgeList.origin == i) {
                list.add(vertex_list.get(edgeList.dest));
            }
        }
        return list;
    }

    // whether two node connected or not ?
    public boolean isConnected(String vertexVal1, String vertexVal2){
        int i = search(vertexVal1);
        int j = search(vertexVal2);

        for(Edge edgeList : edge_list){
            if (edgeList.origin == i && edgeList.dest == j)
                return true;
        }
        return false;
    }

    // Searching in the vertex list, O(|v|) time complexity.
    private int search(String s){
        for (int i = 0; i < vertex_list.size(); i++) {
            if (vertex_list.get(i).equals(s))
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return edge_list.toString();
    }
}
