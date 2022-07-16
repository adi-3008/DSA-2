package com.stack;

import java.util.EmptyStackException;

public class ThreeInOne {
    private int numberOfStacks = 3;
    private int stackCapacity;
    private int[] values;
    private int[] sizes;

    public ThreeInOne(int stackSize) {
        this.stackCapacity = stackSize;
        this.values = new int[numberOfStacks*stackSize];
        this.sizes = new int[numberOfStacks];
    }

    // isFull
    public boolean isFull(int stackNum) {
        return sizes[stackNum-1] == stackCapacity;
    }

    // isEmpty
    public boolean isEmpty(int stackNum) {
        return sizes[stackNum-1] == 0;
    }

    // indexOfTop - this is helper method for push, pop and peek methods

    private int indexOfTop(int stackNum) {
        int size = sizes[stackNum-1];
        int indexTop = 3*(stackNum-1)+size-1;
        return indexTop;
    }

    // push
    public void push(int stackNum, int value){
        if(isFull(stackNum)){
            return;
        }
        int index = indexOfTop(stackNum) + 1;
        values[index] = value;
        sizes[stackNum-1]++;
    }

    // pop
    public int pop(int stackNum) throws EmptyStackException{
        if(isEmpty(stackNum)){
            throw new EmptyStackException();
        }
        int index = indexOfTop(stackNum);
        int value = values[index];
        sizes[stackNum-1]--;
        return value;
    }

    // peek

    public int peek(int stackNum) {
        return values[indexOfTop(stackNum)];
    }

    public static void main(String[] args) throws EmptyStackException {
        ThreeInOne stack = new ThreeInOne(3);
        stack.push(1,1);
        stack.push(1,2);
        stack.push(1,3);
        System.out.println(stack.pop(1));
    }
}
