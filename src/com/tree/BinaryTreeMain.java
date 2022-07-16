package com.tree;


public class BinaryTreeMain {
    public static void main(String[] args) {
        BinaryTreeLinkedList tree = new BinaryTreeLinkedList();
        for (int i = 1; i <= 9; i++) {
            tree.insert("N" + i);
        }

//        tree.preOrder();
//        tree.inOrder();
//        tree.postOrder();
//        tree.levelOrder();
       // System.out.println(tree.search("Fatu2u"));
       // tree.insert("Prajwal");
       // tree.levelOrder();

//        tree.levelOrder();
//        tree.deleteNode("N1");
//        System.out.println();
//        tree.levelOrder();


        // binary tree using array
//        BinaryTreeArray treeArray = new BinaryTreeArray();
//        for (int i = 1; i < 9; i++) {
//            treeArray.insert("N"+i);
//        }
//        treeArray.preOrder();
//        System.out.println();
//        treeArray.inOrder();
//        System.out.println();
//        treeArray.postOrder();
//        System.out.println();
//        treeArray.leverOrder();
//        System.out.println(treeArray.search("N34"));
//        treeArray.delete("N3");
//        treeArray.leverOrder();


    }
}
