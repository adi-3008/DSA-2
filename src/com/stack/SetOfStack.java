package com.stack;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class SetOfStack {
    ArrayList<Stack> stacks = new ArrayList<>();
    int capacity = 3;

    public Stack getLast(){
        if (stacks.size() == 0)
            return null;
        return stacks.get(stacks.size()-1);
    }

    public void push(int val){
        Stack last = getLast();
        if (last != null && !last.isFull()){
            last.push(val);
        }else {
            Stack stack = new Stack(capacity);
            stack.push(val);
            stacks.add(stack);
        }
    }

    // shift left
    public int leftShift(int index, boolean removeTop){
        Stack stack = stacks.get(index);
        int removed;
        if (removeTop) removed = stack.pop();
        else removed = stack.removeBottom();

        if (stack.size == 0){
            stacks.remove(index);
        }else if (stacks.size() > index + 1){
            int v = leftShift(index + 1, false);
            stack.push(v);
        }
        return removed;
    }

    public int popAt(int index){
        return leftShift(index, true);
    }

    public int pop(){
        Stack last = getLast();
        if (last == null) throw new EmptyStackException();
        int val = last.pop();
        if (last.size == 0)
            stacks.remove(stacks.size()-1);
        return val;
    }
}

class Stack{

    int capacity;
    Node top;
    Node bottom;
    int size = 0;

    Stack(int capacity){
        this.capacity = capacity;
    }

    public boolean isFull(){
        return capacity == size;
    }

    public void join(Node above, Node below){
        if (above != null) above.below = below;
        if (below != null) below.above = above;
    }

    public boolean push(int val){
        if (isFull()) return false;
        Node newNode = new Node(val);
        size++;
        if (size == 1) bottom = newNode;
        join(newNode, top);
        top = newNode;
        return true;
    }

    public int pop(){
        if (top == null) throw new EmptyStackException();
        int result = top.val;
        top = top.below;
        size--;
        return result;
    }

    public int removeBottom(){
        Node b = bottom;
        bottom = bottom.above;
        if (bottom != null)
            bottom.below = null;
        size--;
        return b.val;
    }

    static class Node{
        Node above;
        Node below;
        int val;

        Node(int val){
            this.val = val;
        }
    }

    public static void main(String[] args) {
        SetOfStack stack = new SetOfStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack.popAt(0));
        System.out.println(stack.popAt(0));

    }
}
