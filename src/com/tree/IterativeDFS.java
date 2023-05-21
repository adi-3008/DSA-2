package com.tree;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IterativeDFS {

    public static void main(String[] args) {

        TreeNode n1 = new TreeNode(50);
        TreeNode n2 = new TreeNode(30);
        TreeNode n3 = new TreeNode(23);
        TreeNode n4 = new TreeNode(35);
        TreeNode n5 = new TreeNode(70);
        TreeNode n6 = new TreeNode(60);

        n1.left = n2;
        n1.right = n5;

        n2.left = n3;
        n2.right = n4;

        n5.left = n6;

        List<Integer> preOrder = new ArrayList<>();
        List<Integer> inOrder = new ArrayList<>();
        List<Integer> postOrder = new ArrayList<>();

        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(n1, 1));

        while (!stack.isEmpty()){
            Pair<TreeNode, Integer> pair = stack.peek();
            TreeNode currNode = pair.first;

            if (pair.second == 1){
                preOrder.add(currNode.val);
                pair.second++;
                if (currNode.left != null)
                    stack.push(new Pair<>(currNode.left, 1));
            } else if (pair.second == 2) {
                inOrder.add(currNode.val);
                pair.second++;
                if (currNode.right != null)
                    stack.push(new Pair<>(currNode.right, 1));
            }else{
                postOrder.add(currNode.val);
                stack.pop();
            }
        }

        System.out.println(preOrder);
        System.out.println(inOrder);
        System.out.println(postOrder);


    }

}
