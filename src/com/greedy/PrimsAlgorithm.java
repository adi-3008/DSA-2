package com.greedy;

import com.Heap.Heap;
import java.util.ArrayList;
import java.util.List;

// weighted undirected graph.
public class PrimsAlgorithm {
    private int V;
    private List<Vertex> vertexList;
    private AdjListNode[] adjList;

    public PrimsAlgorithm(int v) {
        V = v;
        this.vertexList = new ArrayList<>(v);
        this.adjList = new AdjListNode[v];
    }

    public static class Vertex implements Comparable<Vertex>{
        char ch;
        int index;
        int key;
        int parent;
        boolean isInMST;

        public Vertex(char ch) {
            this.ch = ch;
        }

        @Override
        public int compareTo(Vertex v) {
            return this.key-v.key;
        }
    }

    private static class AdjListNode{
        int vIndex;
        int weight;
        AdjListNode next;

        public AdjListNode(int vIndex, int weight, AdjListNode next) {
            this.vIndex = vIndex;
            this.weight = weight;
            this.next = next;
        }
    }

    public void addVertex(char ch){
        Vertex vertex = new Vertex(ch);
        vertexList.add(vertex);
    }

    public void addEdge(char from, char to, int weight){
        int i = search(from);
        int j = search(to);

        // add index of the vertex j in the adjacency list of vertex i.
        AdjListNode head = adjList[i];
        AdjListNode node = new AdjListNode(j,weight,null);
        if (head == null){
            adjList[i] = node;
        }else {
            while (head.next != null){
                head = head.next;
            }
            head.next = node;
        }

        // add index of the vertex i in the adjacency list of vertex j.
        head = adjList[j];
        node = new AdjListNode(i,weight,null);
        if (head == null){
            adjList[j] = node;
        }else {
            while (head.next != null){
                head = head.next;
            }
            head.next = node;
        }
    }

    public void primsMST(){
        // initialise the key and parent and add them to the queue.
        // O(v)
        for (int i = 0; i < vertexList.size(); i++) {
            Vertex vertex = vertexList.get(i);
            vertex.index = i;
            vertex.key = Integer.MAX_VALUE;
            vertex.parent = -1;
            vertex.isInMST = false;
        }

        // build heap.
        // O(v)
        Heap<Vertex> minHeap = new Heap<>(vertexList);

        // start from any random node.
        Vertex vertex = vertexList.get(0);
        vertex.key = 0;

        // while loop at least O(v)
        while (!minHeap.isEmpty()){
            Vertex u = minHeap.extractMin();  //  O(VlogV)
            AdjListNode head = adjList[u.index];

            while (head != null){
                Vertex v = vertexList.get(head.vIndex);
                if (!v.isInMST && head.weight < v.key) {
                    v.parent = u.index;
                    v.key = head.weight;

                    // decrease key
                    // O(logn)
                    // decrease key operation happens nearly O(E) time hence O(ElogV)
                    minHeap.deleteKey(v);
                    minHeap.insertKey(v);
                }
                head = head.next;
            }
            u.isInMST = true;
        }

    }

    private int search(char ch){
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).ch == ch)
                return i;
        }
        return -1;
    }

    public static void main(String[] args){
        PrimsAlgorithm graph = new PrimsAlgorithm(7);

        for (int i = 0; i < 7; i++) {
            graph.addVertex((char)('A'+i));
        }

        graph.addEdge('A','B',28);
        graph.addEdge('A','F',10);
        graph.addEdge('B','C',16);
        graph.addEdge('B','G',14);
        graph.addEdge('C','D',12);
        graph.addEdge('D','E',22);
        graph.addEdge('D','G',18);
        graph.addEdge('E','F',25);
        graph.addEdge('E','G',24);

        graph.primsMST();

        for (int i = 0; i < graph.vertexList.size(); i++) {
            Vertex vertex = graph.vertexList.get(i);
            System.out.println(vertex.index + 1 + " " + (vertex.parent + 1) + " " + vertex.key);
        }


    }
}
