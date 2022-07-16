package com.tree;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree {
    private Node root;

    AVLTree(){
        this.root = null;
    }

    static class Node{
        int data;
        int height;
        int bf;
        Node left;
        Node right;

        Node(int data){
            this.data = data;
        }
    }

    public void insert(int data){
        root = insert(root, data);
    }

    private static Node insert(Node root, int data){
        if (root == null){
            Node newNode = new Node(data);
            newNode.height = 0;
            return newNode;
        }
        else if (data <= root.data)
            root.left = insert(root.left, data);
        else
            root.right = insert(root.right, data);

        root.height = updateHeight(root);
        root.bf = updateBalanceFactor(root);

        return balanceNode(root);
    }

    private static Node balanceNode(Node root) {
        if (root.bf == -2){
            if (root.left.bf <= 0){
                return leftLeftCase(root);
            }else {
                return leftRightCase(root);
            }
        }else if (root.bf == 2){
            if (root.right.bf >= 0){
                return rightRightCase(root);
            }else {
                return rightLeftCase(root);
            }
        }
        return root;
    }



    private static Node leftLeftCase(Node root) {
        return rightRotation(root);
    }

    private static Node leftRightCase(Node root) {
        root.left = leftRotation(root.left);
        return leftLeftCase(root);
    }

    private static Node rightRightCase(Node root) {
        return leftRotation(root);
    }

    private static Node rightLeftCase(Node root) {
        root.right = rightRotation(root.right);
        return rightRightCase(root);
    }

    private static Node rightRotation(Node root) {
        Node newNode = root.left;
        root.left = root.left.right;
        newNode.right = root;

        root.height = updateHeight(root);
        root.bf = updateBalanceFactor(root);

        newNode.height = updateHeight(newNode);
        newNode.bf = updateBalanceFactor(newNode);

        return newNode;
    }

    private static Node leftRotation(Node root) {
        Node newNode = root.right;
        root.right = root.right.left;
        newNode.left = root;

        root.height = updateHeight(root);
        root.bf = updateBalanceFactor(root);

        newNode.height = updateHeight(newNode);
        newNode.bf = updateBalanceFactor(newNode);

        return newNode;
    }


    private static int updateHeight(Node root) {
        int l = -1;
        int r = -1;
        if (root.left != null) l = root.left.height;
        if (root.right != null) r = root.right.height;
        return 1 + Math.max(l,r);
    }

    private static int updateBalanceFactor(Node root) {
        int l = -1;
        int r = -1;
        if (root.left != null) l = root.left.height;
        if (root.right != null) r = root.right.height;
        return r - l;
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

    public void delete(int data){
        root = delete(root, data);
    }

    private static Node delete(Node root, int data){
        if (root == null)
            return root;
        else if (root.data > data)
            root.left = delete(root.left, data);
        else if (root.data < data)
            root.right = delete(root.right, data);
        else{
            // we have found the node to be deleted

            // case 1: node to be deleted has no child.
            if (root.left == null && root.right == null)
                root = null;

            // case 2: node to be deleted has only one child.
            else if (root.right == null)
                root = root.left;
            else if (root.left == null)
                root = root.right;

            // case 3: node to be deleted have both left and right child.
            else {
                Node maxRight = findMax(root.right);
                root.data = maxRight.data;
                root.right = delete(root.right, maxRight.data);
            }
        }

        if (root != null){
            root.height = updateHeight(root);
            root.bf = updateBalanceFactor(root);
            return balanceNode(root);
        }

        return root;
    }

    private static Node findMax(Node root) {
        if (root.left == null)
            return root;
        return findMax(root.left);
    }


}
