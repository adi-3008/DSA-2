package com.tree;

public class AVLTreeMain {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(30);
        tree.insert(25);
        tree.insert(35);
        tree.insert(20);
        tree.insert(15);
        tree.insert(5);
        tree.insert(10);
        tree.insert(50);
        tree.insert(60);
        tree.insert(70);
        tree.insert(65);
        tree.levelOrder();
        tree.delete(15);
        System.out.println();
        tree.levelOrder();


    }
}
