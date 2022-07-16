package com.disjointset;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T>{

    // Storing all element in single array.
    private Object[] setElement;

    // get the indices of the elements in constant time so overhead to find them is negligible.
    private Map<T, Integer> hasMap;

    // Used to track the size of each of the component
    private int[] size;

    // parent[i] points to the parent of i, if parent[i] = i then i pointer to a root node
    private int[] parent;

    // The number of elements in this union find
    private int cardinality;

    private final static int DEFAULT_SIZE = 10;

    public DisjointSet(){
        this(DEFAULT_SIZE);
    }

    public DisjointSet(int size){
        this.setElement = new Object[size];
        this.size = new int[size];
        this.parent = new int[size];
        this.hasMap = new HashMap<>();
        this.cardinality = 0;
    }

    // O(1) time.
    public void makeSet(T element){
        // if there is mapping of this element as a key then that element is already present
        // in the set so do not make this set.
        if (hasMap.get(element) != null)
            return;

        if (setElement.length == cardinality){
            Object[] arr = new Object[setElement.length*2];
            System.arraycopy(setElement,0,arr,0, setElement.length);
            setElement = arr;
        }
        setElement[cardinality] = element;
        size[cardinality]++;
        parent[cardinality] = cardinality;
        hasMap.put(element,cardinality);
        cardinality++;
    }

    // O(1) Amortized time.
    public T findSet(T element){
        int i = hasMap.get(element);
        int node = i;

        // Find the root of the component/set
        while (i != parent[i]){
            i = parent[i];
        }

        // Compress the path leading back to the root.
        // Doing this operation is called "path compression"
        // and is what gives us amortized time complexity.
        int root = i;
        while (node != root){
            int p = parent[node];
            parent[node] = root;
            node = p;
        }

        return (T) setElement[i];
    }

    // This is an alternative recursive formulation for the find method
    // public int find(int p) {
    //   if (p == id[p]) return p;
    //   return id[p] = find(id[p]);
    // }


    // Return whether the elements 'p' and 'q' are in the same components/set.

    public boolean isConnected(T s1, T s2) {
        return findSet(s1) == findSet(s2);
    }

    public int getCardinality(){
        return this.cardinality;
    }

    // O(1) Amortized time
    public void union(T element1, T element2){
        if (isConnected(element1,element2))
            return;
        link(findSet(element1), findSet(element2));
        cardinality--;
    }

    private void link(T root1, T root2) {
        int i = hasMap.get(root1);
        int j = hasMap.get(root2);

        // union by rank
        if (size[i] >= size[j]){
            parent[j] = i;
            size[i] += size[j];
            size[j] = 0;
        }else {
            parent[i] = j;
            size[j] += size[i];
            size[i] = 0;
        }
    }

    public static void main(String[] args) {
        DisjointSet<Integer> set = new DisjointSet<>();
        for (int i = 0; i < 10; i++) {
            set.makeSet(i);
        }
        set.union(4,6);
        set.union(5,7);
        set.union(5,8);
        set.union(4,5);
        System.out.println(set.cardinality);
        System.out.println(set.findSet(6));
    }
}
