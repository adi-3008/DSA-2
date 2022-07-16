package com.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {
    private Node root;

    static class Node{
        int data;
        Node left;
        Node right;

        Node(int value){
            this.data = value;
        }
    }

    public void insert(int value){
        root = insert(root, value);
    }

    private static Node insert(Node root, int value){
        if (root == null){
            return new Node(value);
        }
        if (value <= root.data){
            root.left = insert(root.left, value);
        }else {
            root.right = insert(root.right, value);
        }
        return root;
    }

    public void preOrder(){
        preOrder(root);
    }

    private static void preOrder(Node root){
        if (root == null)
            return;
        System.out.print(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    public void inOrder(){
        inOrder(root);
    }

    private static void inOrder(Node root){
        if (root == null)
            return;
        inOrder(root.left);
        System.out.print(root.data + " ");
        inOrder(root.right);
    }

    public void postOrder(){
        postOrder(root);
    }

    private static void postOrder(Node root){
        if (root == null)
            return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.data + " ");
    }

    public void levelOrder(){
        if (root == null)
            return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node currentNode = queue.remove();
            System.out.print(currentNode.data + " ");
            if (currentNode.left != null)
                queue.add(currentNode.left);
            if (currentNode.right != null)
                queue.add(currentNode.right);
        }
    }

    public boolean search(int data){
        return search(root, data);
    }

    private static boolean search(Node root, int data){
        if (root == null)
            return false;

        if (root.data == data)
            return true;
        else if (root.data > data)
            return search(root.left, data);
        else
            return search(root.right, data);
    }

    public int findMin(){
        if (root == null)
            return -1;
        Node temp = root;
        while (temp.left != null){
            temp = temp.left;
        }
        return temp.data;
    }

    public int findMinRec(){
        return findMinRec(root);
    }

    private static int findMinRec(Node root){
        if (root == null)
            return -1;
        else if (root.left == null)
            return root.data;
        return findMinRec(root.left);
    }

    public int findMax(){
        if (root == null)
            return -1;
        Node temp = root;
        while(temp.right != null)
            temp = temp.right;
        return temp.data;
    }

    public int findMaxRec(){
        return findMaxRec(root);
    }

    private static int findMaxRec(Node root){
        if (root == null)
            return -1;
        else if (root.right == null)
            return root.data;
        return findMaxRec(root.right);
    }

    public int findHeight(){
        return findHeight(root);
    }

    private static int findHeight(Node root){
        if (root == null)
            return -1;
        return Math.max(findHeight(root.left), findHeight(root.right)) + 1;
    }


    // method 1: not time and space efficient
    public boolean isBST(){
        return isBST(root);
    }

    private static boolean isBST(Node root){
        if (root == null)
            return true;
        if (isSubTreeLesser(root.left,root.data)
                && isSubTreeGreater(root.right,root.data)
                && isBST(root.left)
                && isBST(root.right))
            return true;
        else
            return false;
    }

    private static boolean isSubTreeLesser(Node root, int value){
        if (root == null)
            return true;
        return root.data < value && isSubTreeLesser(root.left, value) && isSubTreeLesser(root.right, value);
    }

    private static boolean isSubTreeGreater(Node root, int value){
        if (root == null)
            return true;
        return root.data > value && isSubTreeGreater(root.left, value) && isSubTreeGreater(root.right, value);
    }


    // method 2: time and space efficient but not allow duplicate values.
    // time complexity  : O(N)
    // space complexity : O(N)
    public boolean isBST2(){
        return isBST2(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean isBST2(Node root, int minValue, int maxValue){
        if (root == null)
            return true;
        if (root.data > minValue && root.data < maxValue
                && isBST2(root.left, minValue, root.data)
                && isBST2(root.right, root.data, maxValue))
            return true;
        else
            return false;
    }



    // method 3: O(n) solution with distinct values
    // time complexity  : O(N)
    // space complexity : O(N)

    public boolean isBST3(){
        return inOrderBST(root, null);
    }

    private static boolean inOrderBST(Node root, Node prev){
        if (root == null)
            return true;
        if (!inOrderBST(root.left, prev) && prev != null && root.data <= prev.data)
            return false;
        prev = root;
        return inOrderBST(root.right, prev);
    }

    public void delete(int data){
        root = delete(root, data);
    }

    private static Node delete(Node root, int data){
        if (root == null)
            return root;
        else if (data < root.data)
            root.left = delete(root.left, data);
        else if (data > root.data)
            root.right = delete(root.right, data);
        else {
            // case 1:
            if (root.left == null && root.right == null)
                root = null;

            // case 2:
            else if (root.left == null)
                root = root.right;
            else if (root.right == null)
                root = root.left;

            // case 3:
            else {
                root.data = findMinRec(root.right);
                root.right = delete(root.right, root.data);
            }
        }
        return root;
    }

    // find the inOrder successor of BST
    public int getInorderSuccessor(int data){

        // finding node will take O(H) time
        Node current = findNode(root, data);
        if (current == null) return -1;

        // finding minimum will take O(H) time.
        else if (current.right != null){
            return findMinRec(root.right);
        }

        // traversing to the current node will take O(H) time
        else {
            Node successor = null;
            Node ancestor = root;
            while (ancestor != current){
                if (ancestor.data > current.data){
                    successor = ancestor;
                    ancestor = ancestor.left;
                }else {
                    ancestor = ancestor.right;
                }
            }
            return (successor != null) ? successor.data : -1;
        }
    }

    private Node findNode(Node root, int data) {
        if (root == null)
            return root;
        else if (root.data == data)
            return root;
        else if (root.data > data)
            return findNode(root.left, data);
        return findNode(root.right, data);
    }

    public int getInorderPredecessor(int data){
        Node current = findNode(root, data);
        if (current == null)
            return -1;
        else if (current.left != null){
            return current.left.data;
        }else{
            Node predecessor = null;
            Node temp = root;

            while (temp != current){
                if (temp.data < current.data){
                    predecessor = temp;
                    temp = temp.right;
                }else {
                    temp = temp.left;
                }
            }
            return predecessor != null ? predecessor.data : -1;
        }
    }
}
