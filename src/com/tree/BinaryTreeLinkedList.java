package com.tree;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeLinkedList {
    private Node root;

    BinaryTreeLinkedList(){
        this.root = null;
    }

    private static class Node{
        String data;
        Node left;
        Node right;

        Node(String data){
            this.data = data;
        }

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
            Node presentNode = queue.poll();
            System.out.print(presentNode.data + " ");

            if (presentNode.left != null)
                queue.add(presentNode.left);

            if (presentNode.right != null)
                queue.add(presentNode.right);
        }
    }

    public boolean search(String data){
        if (root == null)
            return false;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node presentNode = queue.poll();
            if (presentNode.data.equals(data))
                return true;

            if (presentNode.left != null)
                queue.add(presentNode.left);
            if (presentNode.right != null)
                queue.add(presentNode.right);
        }
        return false;
    }

    public void insert(String data){
        Node newNode = new Node(data);
        if (root == null){
            root = newNode;
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()){
            Node presentNode = queue.poll();
            if (presentNode.left == null){
                presentNode.left = newNode;
                break;
            }else if (presentNode.right == null){
                presentNode.right = newNode;
                break;
            }else {
                queue.add(presentNode.left);
                queue.add(presentNode.right);
            }
        }
    }

    // Get Deepest node
    public Node getDeepestNode() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        Node presentNode = null;
        while (!queue.isEmpty()) {
            presentNode = queue.remove();
            if (presentNode.left != null) {
                queue.add(presentNode.left);
            }
            if (presentNode.right != null) {
                queue.add(presentNode.right);
            }
        }
        return presentNode;
    }

    // Delete Deepest node
    public void deleteDeepestNode(Node last) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        Node presentNode;
        while (!queue.isEmpty()) {
            presentNode = queue.remove();

            if (presentNode.left != null){
                if (presentNode.left == last){
                    presentNode.left = null;
                    return;
                }
                queue.add(presentNode.left);
            }

            if (presentNode.right != null){
                if (presentNode.right == last){
                    presentNode.right = null;
                    return;
                }
                queue.add(presentNode.right);
            }
        }
    }

    // Delete Given node
    void deleteNode(String value) {
        if (root == null)
            return;

        if (root.left == null && root.right == null && root.data.equals(value)){
            root = null;
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node target = null;
        Node presentNode = null;

        while (!queue.isEmpty()) {
            presentNode = queue.remove();
            if (presentNode.data.equals(value)) {
                target = presentNode;
            }
            if (presentNode.left != null) queue.add(presentNode.left);
            if (presentNode.right != null) queue.add(presentNode.right);

        }

        if (target != null){
            target.data = getDeepestNode().data;
            deleteDeepestNode(presentNode);
        }else {
            System.out.println("Node does not found");
        }
    }



}
