package com.tree;

public class BinarySearchMain {
    public static void main(String[] args) {
        var bst = new BinarySearchTree();
        bst.insert(70);
        bst.insert(50);
        bst.insert(90);
        bst.insert(30);
        bst.insert(60);
        bst.insert(80);
        bst.insert(100);
        bst.insert(20);
        bst.insert(40);
        bst.insert(40);




//        bst.preOrder();
//        System.out.println();
//        bst.inOrder();
//        System.out.println();
//        bst.postOrder();
//        System.out.println();
//        bst.levelOrder();

//        System.out.println(bst.search(340));

//        System.out.println("Min : " + bst.findMin() + ", Max : " + bst.findMax());
//        System.out.println("Min : " + bst.findMinRec() + ", Max : " + bst.findMaxRec());
//        System.out.println(bst.findHeight());
        System.out.println(bst.isBST3());
//        bst.inOrder();
//        System.out.println(bst.getInorderPredecessor(20));
//        bst.inOrder();
    }
}