package com.stack;

public class StackMinimum {

    private Node min;
    private Node top;

    StackMinimum(){
        this.min = null;
        this.top = null;
    }

    public int getMin(){
        return min.val;
    }

    public void push(int val){
        top = new Node(val, top);
        if (min == null || min.val >= val){
            min = new Node(val, min);
        }else {
            min = new Node(min.val, min);
        }
    }

    public int pop(){
        min = min.next;
        int val = top.val;
        top = top.next;
        return val;
    }

    static class Node{
        int val;
        Node next;

        Node(int val){
            this.val =  val;
        }

        Node(int val, Node next){
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        StackMinimum stack = new StackMinimum();
        stack.push(1);
        stack.push(2);
        stack.push(-2);
        stack.pop();
        System.out.println(stack.getMin());
    }
}
