package com.greedy;

import com.disjointset.DisjointSet;
import java.util.ArrayList;
import java.util.Comparator;

public class Kruskal {
    private int V;
    private ArrayList<String> vertexList;
    private ArrayList<Edge> edgeList;

    public Kruskal(){
        this.V = 0;
        this.vertexList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
    }

    public class Edge{
        String from;
        String to;
        int weight;

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return this.from + " -- " + this.to + " " + this.weight;
        }
    }

    public void addVertex(String vertex){
        vertexList.add(vertex);
        V++;
    }

    public void addEdge(String v1, String v2, int weight){
        edgeList.add(new Edge(v1,v2,weight));
    }

    public ArrayList<Edge> MSTKruskal(){
        DisjointSet<String> set = new DisjointSet<>();
        ArrayList<Edge> mst = new ArrayList<>();

        // O(v) time
        for(String vertex : vertexList){
            set.makeSet(vertex);
        }

        // sort the edges
        // O(ElogE) time.
        edgeList.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight- o2.weight;
            }
        });

        // O(E) time.
        for(Edge edge : edgeList){
            String u = edge.from;
            String v = edge.to;

            if (set.findSet(u) != set.findSet(v)){
                mst.add(edge);
                set.union(u,v);
            }

            if (mst.size() == V-1)
                return mst;
        }
        return null;
    }

    public static void main(String[] args) {
        Kruskal graph = new Kruskal();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");


        graph.addEdge("A","B",28);
        graph.addEdge("A","F",10);
        graph.addEdge("B","C",16);
        graph.addEdge("B","G",14);
        graph.addEdge("C","D",12);
        graph.addEdge("D","E",22);
        graph.addEdge("D","G",18);
        graph.addEdge("E","F",25);
        graph.addEdge("E","G",24);

        System.out.println(graph.MSTKruskal());

    }
}
