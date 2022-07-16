package com.graphs;

import com.disjointset.DisjointSet;

import java.util.*;


// adjacency list representation of unweighted directed graph.

public class Graph{
    private final int V;
    private final ArrayList<Integer> vertexList;
    private final LinkedList<AdjListNode>[] adjList;

    public Graph(int v) {
        this.V = v;
        this.vertexList = new ArrayList<>(v);
        this.adjList = new LinkedList[v];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public static class AdjListNode {
        int index;
        AdjListNode next;

        AdjListNode(int index, AdjListNode next) {
            this.index = index;
            this.next = next;
        }
    }

    public void addVertex(Integer val) {
        vertexList.add(val);
    }

    public void addEdge(int u, int v) {
        LinkedList<AdjListNode> list = adjList[u];
        AdjListNode node = new AdjListNode(v, null);
        list.add(node);
    }

    public void depthFirstSearch() {
        depthFirstSearch(0, new boolean[V]);
    }

    private void depthFirstSearch(int i, boolean[] visited) {
        if (visited[i])
            return;
        visited[i] = true;
        System.out.print(vertexList.get(i) + " ");

        LinkedList<AdjListNode> list = adjList[i];
        for (AdjListNode node : list) {
            depthFirstSearch(node.index, visited);
        }
    }

    public void breadthFirstSearch() {
        breadthFirstSearch(0, new boolean[V]);
    }

    // 'i' is the index of the vertex in the vertexList
    private void breadthFirstSearch(int i, boolean[] visited) {
        Deque<Integer> queue = new ArrayDeque<>();
        visited[i] = true;
        queue.addLast(i);

        while (!queue.isEmpty()) {
            i = queue.peek();
            System.out.print(vertexList.get(i) + " ");

            // add all discovered vertex adjacent to it to the queue.
            LinkedList<AdjListNode> list = adjList[i];
            for (AdjListNode node : list) {
                if (!visited[node.index]) {
                    visited[node.index] = true;
                    queue.addLast(node.index);
                }
            }
            queue.removeFirst();
        }

    }

    public ArrayList<Integer> getNeighbour(int u) {
        ArrayList<Integer> neighbour = new ArrayList<>();
        LinkedList<AdjListNode> list = adjList[u];
        for (AdjListNode node : list) {
            neighbour.add(node.index);
        }
        return neighbour;
    }

    public boolean isConnected(int u, int v) {
        LinkedList<AdjListNode> list = adjList[u];
        for (AdjListNode node : list) {
            if (node.index == v)
                return true;
        }
        return false;
    }

    public void topologicalSort() {
        int[] inDegree = new int[V];
        Queue<Integer> queue = new ArrayDeque<>();

        // O(V+E)
        for (int i = 0; i < vertexList.size(); i++) {
            LinkedList<AdjListNode> list = adjList[i];
            for (AdjListNode node : list) {
                inDegree[node.index]++;
            }
        }

        // O(V)
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // O(V + E)
        while (!queue.isEmpty()) {
            int remove = queue.remove();
            System.out.print(vertexList.get(remove) + " ");
            inDegree[remove] = -1;

            LinkedList<AdjListNode> list = adjList[remove];
            for (AdjListNode node : list) {
                inDegree[node.index]--;
                if (inDegree[node.index] == 0)
                    queue.add(node.index);
            }
        }
    }

    // find the no of connected component in the graph.
    public int connectedComp() {
        // O(n) space.
        DisjointSet<Integer> set = new DisjointSet<>();

        // O(v) time
        for (Integer vertex : vertexList) {
            set.makeSet(vertex);
        }

        // for each Edge (u,v) unify vertices u and v.
        // O(E) time
        for (int i = 0; i < adjList.length; i++) {
            int u = vertexList.get(i);
            LinkedList<AdjListNode> list = adjList[u];
            for (AdjListNode node : list) {
                int v = vertexList.get(node.index);
                if (!Objects.equals(set.findSet(u), set.findSet(v)))
                    set.union(u, v);
            }
        }

        return set.getCardinality();
    }
}