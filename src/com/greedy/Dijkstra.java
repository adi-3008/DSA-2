package com.greedy;

import com.Heap.Heap;

import java.util.ArrayList;
import java.util.List;

// weighted undirected graph.
public class Dijkstra {
    public int V;
    public List<Vertex> vertexList;
    public AdjListNode[] adjList;

    public Dijkstra(int v) {
        V = 0;
        this.vertexList = new ArrayList<>(v);
        this.adjList = new AdjListNode[v];
    }

    public static class Vertex implements Comparable<Vertex>{
        public char ch;
        public int index;
        public int d;
        public boolean visited;

        public Vertex(char ch) {
            this.ch = ch;
        }

        @Override
        public int compareTo(Vertex v) {
            return this.d-v.d;
        }

    }

    public static class AdjListNode{
        public int vIndex;
        public int weight;
        public AdjListNode next;

        public AdjListNode(int vIndex, int weight, AdjListNode next) {
            this.vIndex = vIndex;
            this.weight = weight;
            this.next = next;
        }

        public int getvIndex(){
            return vIndex;
        }

        public int getWeight() {
            return weight;
        }

        public AdjListNode getNext(){
            return next;
        }
    }

    public void addVertex(char ch){
        Vertex vertex = new Vertex(ch);
        vertex.index = V++;
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
    }

    public void dijkstra(char source){
        // initialise the distance of all nodes to infinity and mark them unvisited.
        // O(v)
        for (Vertex vertex : vertexList) {
            vertex.d = (vertex.ch == source) ? 0 : Integer.MAX_VALUE;
            vertex.visited = false;
        }

        // build heap.
        // O(v)
        Heap<Vertex> minHeap = new Heap<>(vertexList);

        // while loop at least O(v)
        while (!minHeap.isEmpty()){
            Vertex u = minHeap.extractMin();  //  O(VlogV)
            u.visited = true;

            AdjListNode head = adjList[u.index];
            while (head != null){
                Vertex v = vertexList.get(head.vIndex);
                relax(minHeap, u, v, head.weight);
                head = head.next;
            }
        }

    }

    private void relax(Heap<Vertex> minHeap, Vertex u, Vertex v, int weight) {
        if (!v.visited && v.d > u.d + weight) {
            v.d = u.d + weight;

            // decrease key
            // O(logn)
            // decrease key operation happens nearly O(E) time hence O(ElogV)
            minHeap.deleteKey(v);
            minHeap.insertKey(v);
        }
    }

    public int search(char ch){
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).ch == ch)
                return i;
        }
        return -1;
    }

    public static void main(String[] args){
        Dijkstra graph = new Dijkstra(5);

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

        graph.dijkstra('A');

        for (Vertex vertex : graph.vertexList){
            System.out.println('A' + " -- " + vertex.ch + " " + vertex.d);
        }


        // adding negative edge weight in graph
        Dijkstra graph1 = new Dijkstra(5);

        graph1.addVertex('A');
        graph1.addVertex('B');
        graph1.addVertex('C');
        graph1.addVertex('D');

        graph1.addEdge('A','B',1);
        graph1.addEdge('B','C',2);
        graph1.addEdge('D','B',-4);
        graph1.addEdge('C','D',3);

        graph1.dijkstra('A');

        for (Vertex vertex : graph1.vertexList){
            System.out.println('A' + " -- " + vertex.ch + " " + vertex.d);
        }


    }
}

