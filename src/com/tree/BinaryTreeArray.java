package com.tree;

import java.util.ArrayList;

public class BinaryTreeArray {
    private ArrayList<String> arr;
    private int lastIndex;
    private int root;

    BinaryTreeArray(){
        this.arr = new ArrayList<>();
        this.lastIndex = 0;
    }

    public void insert(String value){
        if (lastIndex == 0)
            arr.add("NULL");
        arr.add(value);
        lastIndex++;
        if (lastIndex == 1)
            root = lastIndex;
    }

    // preOrder traversal
    public void preOrder(){
        preOrder(arr, 1, lastIndex);
    }

    private static void preOrder(ArrayList<String> arr, int rootIndex, int lastIndex){
        if (rootIndex > lastIndex)
            return;
        System.out.print(arr.get(rootIndex) + " ");
        preOrder(arr, rootIndex * 2, lastIndex);
        preOrder(arr, rootIndex * 2 + 1, lastIndex);
    }

    // inOrder traversal
    public void inOrder(){
        inOrder(arr,1,lastIndex);
    }

    private static void inOrder(ArrayList<String> arr, int rootIndex, int lastIndex) {
        if (rootIndex > lastIndex)
            return;
        inOrder(arr, rootIndex * 2, lastIndex);
        System.out.print(arr.get(rootIndex) + " ");
        inOrder(arr, rootIndex * 2 + 1, lastIndex);
    }

    public void postOrder(){
        postOrder(arr,1,lastIndex);
    }

    private static void postOrder(ArrayList<String> arr, int rootIndex, int lastIndex) {
        if (rootIndex > lastIndex)
            return;
        postOrder(arr, rootIndex * 2, lastIndex);
        postOrder(arr, rootIndex * 2 + 1, lastIndex);
        System.out.print(arr.get(rootIndex) + " ");

    }

    public void leverOrder(){
        for (int i = 1; i <= lastIndex; i++) {
            System.out.print(arr.get(i) + " ");
        }
    }

    public boolean search(String value){
        return arr.contains(value);
    }

    public boolean delete(String value){
        int index = arr.indexOf(value);
        if (index != -1){
            arr.set(index, arr.get(lastIndex--));
            return true;
        }
        return false;
    }

    public void deleteBinaryTree(){
        arr = null;
    }

}
