package com.graphs;


public class Vertex<T extends Comparable<T>>{
    private T data;

    public Vertex(T data){
        this.data = data;
    }

    public int compare(Vertex<T> u){
        return this.data.compareTo(u.data);
    }
}
