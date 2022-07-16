package com.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BellmanFord {
    private ArrayList<Vertex> vertexList;
    private AdjListNode[] adjList;
    private Map<Character, Integer> hashMap;
    private int V;

    public BellmanFord(int v){
        this.vertexList = new ArrayList<>();
        this.adjList = new AdjListNode[v];
        this.V = 0;
        this.hashMap = new HashMap<>();
    }

    public static class Vertex{
        char ch;
        int d;

        public Vertex(char ch) {
            this.ch = ch;
        }
    }

    public static class AdjListNode {
        int data;
        int weight;
        AdjListNode next;

        AdjListNode(int data, int weight, AdjListNode next){
            this.data = data;
            this.next = next;
            this.weight = weight;
        }
    }

    public void addVertex(char ch){
        Vertex vertex = new Vertex(ch);
        vertexList.add(vertex);
        hashMap.put(ch,V);
        V++;
    }

    public void addEdge(char from, char to, int weight){
        int i = hashMap.get(from);
        int j = hashMap.get(to);

        // add index of the vertex j in the adjacency list of vertex i.
        AdjListNode head = adjList[i];
        AdjListNode node = new AdjListNode(j,weight,null);
        if (head == null){
            adjList[i] = node;
        }else {
            add(head, node);
        }
    }

    private void add(AdjListNode head, AdjListNode node) {
        while (head.next != null){
            head = head.next;
        }
        head.next = node;
    }

    public boolean bellmanFord(char source){
        // initialise single source
        // O(V)
        for (int i = 0; i < V; i++) {
            Vertex vertex = vertexList.get(i);
            vertex.d = source == vertex.ch ? 0 : Integer.MAX_VALUE;
        }

        // relax all edges v-1 times.
        // O(VE)
        for (int i = 0; i < V-1; i++) {      //  --- O(V) time

            // O(E) time
            for (int j = 0; j < V; j++) {
                Vertex u = vertexList.get(j);
                AdjListNode head = adjList[j];
                while (head != null){
                    Vertex v = vertexList.get(head.data);
                    relax(u,v,head.weight);
                    head = head.next;
                }
            }
        }

        // relax all edges exactly one time to check whether there is negative edge cycle if it is present then
        // we get better shortest path
        // O(E)
        for (int i = 0; i < 1; i++) {
            Vertex u = vertexList.get(i);
            AdjListNode head = adjList[i];
            while (head != null){
                Vertex v = vertexList.get(head.data);
                if (relax(u,v,head.weight))
                    return false;
                head = head.next;
            }
        }

        return true;
    }

    private boolean relax(Vertex u, Vertex v, int weight) {
        if (v.d > u.d + weight) {
            v.d = u.d + weight;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        BellmanFord graph = new BellmanFord(5);


        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');


        graph.addEdge('A','B',10);
        graph.addEdge('A','C',5);
        graph.addEdge('B','C',2);
        graph.addEdge('C','B',3);
        graph.addEdge('B','D',1);
        graph.addEdge('D','E',4);
        graph.addEdge('E','D',6);
        graph.addEdge('C','D',9);
        graph.addEdge('E','A',7);
        graph.addEdge('C','E',2);

        if (graph.bellmanFord('Á')){
            // shortest path possible hence print it.
            for (Vertex vertex : graph.vertexList){
                System.out.println('A' + " -- " + vertex.ch + " " + vertex.d);
            }
        }else {
            System.out.println("Negative edge cycle is present, No shortest path possible");
        }
    }
}
