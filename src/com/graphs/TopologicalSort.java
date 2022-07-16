package com.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class TopologicalSort {
    private int V;
    private ArrayList<String> vertexList;
    private AdjListNode[] adjacencyList;

    TopologicalSort(int v){
        this.V = v;
        vertexList = new ArrayList<>();
        adjacencyList = new AdjListNode[v];
    }

    static class AdjListNode{
        int data;
        AdjListNode next;

        AdjListNode(int data, AdjListNode next){
            this.data = data;
            this.next = next;
        }
    }

    public void addVertex(String v){
        vertexList.add(v);
    }

    public void addEdge(String v1, String v2){
        int i = search(v1);
        int j = search(v2);

        AdjListNode node = new AdjListNode(j,null);
        if (adjacencyList[i] == null){
            adjacencyList[i] = node;
        }else {
            AdjListNode head = adjacencyList[i];
            while (head.next != null){
                head = head.next;
            }
            head.next = node;
        }
    }

    private int search(String v){
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).equals(v))
                return i;
        }
        return -1;
    }

    public void topologicalSort(){
        int[] inDegree = new int[V];
        Queue<Integer> queue = new ArrayDeque<>();

        // O(V+E)
        for (int i = 0; i < vertexList.size(); i++) {
            AdjListNode head = adjacencyList[i];
            while (head != null){
                inDegree[head.data]++;
                head = head.next;
            }
        }

        // O(V)
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0){
                queue.add(i);
            }
        }

        // O(V + E)
        while (!queue.isEmpty()){
            int remove = queue.remove();
            System.out.print(vertexList.get(remove) + " ");
            inDegree[remove] = -1;

            AdjListNode head = adjacencyList[remove];
            while (head != null){
                inDegree[head.data]--;
                if (inDegree[head.data] == 0)
                    queue.add(head.data);
                head = head.next;
            }
        }
    }

    public static void main(String[] args) {
        TopologicalSort graph = new TopologicalSort(6);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.addVertex("v4");
        graph.addVertex("v5");
        graph.addVertex("v6");

        graph.addEdge("v2", "v1");
        graph.addEdge("v5", "v1");
        graph.addEdge("v1", "v3");
        graph.addEdge("v1", "v4");
        graph.addEdge("v4", "v3");
        graph.addEdge("v4", "v6");
        graph.addEdge("v3", "v6");

        graph.topologicalSort();
    }
}
