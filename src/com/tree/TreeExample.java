package com.tree;

import java.util.ArrayList;

public class TreeExample {

    public static void main(String[] args) {
        TreeNode drink = new TreeNode("Drinks");
        TreeNode hot = new TreeNode("Hot");
        TreeNode cold = new TreeNode("Cold");
        TreeNode tea = new TreeNode("Tea");
        TreeNode cofee = new TreeNode("Cofee");
        TreeNode beer = new TreeNode("beer");
        TreeNode wine = new TreeNode("wine");

        drink.addChild(hot);
        drink.addChild(cold);
        hot.addChild(tea);
        hot.addChild(cofee);
        cold.addChild(beer);
        cold.addChild(wine);

        System.out.println(drink.print(0));

    }



    static class TreeNode{
        String data;
        ArrayList<TreeNode> child = new ArrayList<>();

        TreeNode(String data){
            this.data = data;
        }

        public void addChild(TreeNode node){
            this.child.add(node);
        }

        public String print(int level){
            String ans;

            ans = " ".repeat(level) + data + "\n";

            for(TreeNode node : child){
                ans += node.print(level+1);
            }

            return ans;
        }
    }
}
