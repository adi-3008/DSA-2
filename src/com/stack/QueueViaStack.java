package com.stack;
import java.util.Stack;

public class QueueViaStack {
    private Stack<Integer> stack1, stack2;

    QueueViaStack(){
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void enqueue(int val){
        stack1.push(val);
    }

    public int dequeue(){
        shift();
        return stack2.pop();
    }

    public int peek(){
        shift();
        return stack2.peek();
    }

    private void shift(){
        if (stack2.isEmpty()){
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
    }

    public static void main(String[] args){
        QueueViaStack queue = new QueueViaStack();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println(queue.dequeue());
        System.out.println(queue.peek());


    }
}
