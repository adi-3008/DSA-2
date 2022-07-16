package com.priorityQueue;

public class PQueueMain {
    public static void main(String[] args) throws EmptyQueueException {
        PQueue queue = new PQueue(new int[]{6,3,5,34,76,23,4,12});
        System.out.println(queue);
        queue.add(23,566);
        System.out.println(queue);
    }
}
