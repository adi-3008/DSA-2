package com.tree;

import java.util.LinkedList;
import java.util.Queue;

public class ConstructBST {
    public static void main(String[] args) {
        int[] levelOrder = {3, 2, 5, 1, 6, 7};
        TreeNode root = constructBSTFromLevelOrder(levelOrder);
        System.out.println(root);
    }

    private static TreeNode constructBSTFromLevelOrder(int[] levelOrder) {
        Queue<Helper> q = new LinkedList<>();
        TreeNode root = new TreeNode(levelOrder[0]);
        q.add(new Helper(root, Integer.MIN_VALUE, root.val - 1));
        q.add(new Helper(root, root.val + 1, Integer.MAX_VALUE));
        int index = 1;

        while(!q.isEmpty()){
            Helper curr = q.remove();

            // no more element in the queue
            if (index == levelOrder.length) continue;

            // element in levelOrder at index 'index' is not in the range so this can not be the child
            if (levelOrder[index] < curr.minRange || levelOrder[index] > curr.maxRange) continue;

            // now element at index is definitely child of the
            TreeNode child = new TreeNode(levelOrder[index]);
            index++;

            if (child.val < curr.parent.val)
                curr.parent.left = child;
            else
                curr.parent.right = child;

            q.add(new Helper(child, curr.minRange, child.val - 1));
            q.add(new Helper(child, child.val + 1, curr.maxRange));

        }
        return root;
    }
}

class Helper{
    TreeNode parent;
    int minRange;
    int maxRange;

    Helper(TreeNode parent, int minRange, int maxRange){
        this.parent = parent;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }
}

class TreeNode {
    int val;

    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }
}
