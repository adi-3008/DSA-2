package com.greedy;
import java.util.PriorityQueue;

public class HuffmanCode {

    static class Node implements Comparable<Node>{
        int f;
        char c;
        Node left;
        Node right;

        public Node(int frequency, Node left, Node right){
            this.f = frequency;
            this.left = left;
            this.right = right;
        }

        public Node(int frequency, char c, Node left, Node right){
            this.f = frequency;
            this.c = c;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node n) {
            return this.f-n.f;
        }
    }

    private static void printHuffmanTree(Node root, String p) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            System.out.println(p + "-" + root.c);
        }
        printHuffmanTree(root.left, p + '0');
        printHuffmanTree(root.right ,p + '1');
    }

    private static Node huffman(char[] charArr, int[] frequencyArr, int n) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            Node node = new Node(frequencyArr[i], charArr[i], null, null);
            queue.add(node);
        }

        for (int i = 0; i < n-1; i++) {
            Node min1 = queue.poll();
            Node min2 = queue.poll();
            Node node = new Node(min1.f + min2.f, min1, min2);
            queue.add(node);
        }
        return queue.remove();
    }

    public static void main(String[] args) {
        char[] charArr = {'a', 'e', 'i', 'o', 'u','s','t'};
        int[] frequencyArr = {10,15,12,3,4,13,1};
        Node root = huffman(charArr, frequencyArr, 7);
        printHuffmanTree(root, "");
    }
}
