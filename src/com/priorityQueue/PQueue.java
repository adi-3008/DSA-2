package com.priorityQueue;

import java.util.Arrays;

public class PQueue {
    private int[] heap;
    private int heapSize;
    private int heapCapacity;
    private static final int DEFAULT_SIZE = 10;

    public PQueue(){
        this(DEFAULT_SIZE);
    }

    public PQueue(int heapCapacity){
        this.heap = new int[heapCapacity];
        this.heapSize = 0;
        this.heapCapacity = heapCapacity;
    }

    public PQueue(int[] arr){
        this.heap = arr;
        this.heapSize = arr.length;
        this.heapCapacity = arr.length;
        buildHeap();
    }

    public void add(int value){
        insertKey(value);
    }

    public boolean add(int src, int dest){
        int i = search(src);
        if (i != -1){
            if (src <= dest)
                return increaseKey(i, dest);
            return decreaseKey(i, dest);
        }
        return false;
    }

    public int remove(int value) throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException("Cannot remove from Empty Queue");
        int i = search(value);
        return i != -1 ? deleteKey(i) : -1;
    }

    public int poll() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException("Cannot remove from Empty Queue");
        return removeAt(0);
    }

    public int removeAt(int i){
        return deleteKey(i);
    }

    public void sort(){
        heapSort();
    }

    private void insertKey(int value){
        if (isFull()){
            int[] temp = new int[heapCapacity * 2];
            if (heapCapacity >= 0) System.arraycopy(heap, 0, temp, 0, heapCapacity);
            heap = temp;
            heapCapacity *= 2;
        }

        // add the element to the last of the array.
        heap[heapSize] = value;


        // maintain heap invariant.
        int i = heapSize;
        while (i > 0 && heap[(i-1)/2] < heap[i]){
            swap(heap, (i-1)/2, i);
            i = (i-1)/2;
        }

        // increase size of the heap.
        heapSize++;

    }

    private int deleteKey(int i){
        int key = heap[i];
        heap[i] = heap[--heapSize];
        maxHeapify(i);
        return key;
    }

    private boolean decreaseKey(int i, int value){
        if (heap[i] < value)
            return false;
        heap[i] = value;
        maxHeapify(i);
        return true;
    }

    private boolean increaseKey(int i, int value){
        if (heap[i] > value)
            return false;
        heap[i] = value;
        while (i > 0 && heap[(i-1)/2] < heap[i]){
            swap(heap, (i-1)/2, i);
            i = (i-1)/2;
        }
        return true;
    }

    private int extractMax(){
        int key = heap[0];
        heap[0] = heap[heapSize-1];
        heapSize--;
        maxHeapify(0);
        return key;
    }

    private void heapSort(){
        int temp = heapSize;
        for (int i = heapSize; i > 0; i--) {
            swap(heap, 0, heapSize-1);
            heapSize--;
            maxHeapify(0);
        }

        for (int i = 0; i < temp/2; i++) {
            swap(heap, i, temp-i-1);
        }
        heapSize = temp;

    }

    private boolean isFull(){
        return heapSize == heapCapacity;
    }

    private boolean isEmpty(){
        return heapSize == 0;
    }

    private void buildHeap(){
        for (int i = heapSize/2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    private void maxHeapify(int i){
        while (true){
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int largest = i;

            if (l < heapSize && heap[l] > heap[i])
                largest = l;
            if (r < heapSize && heap[r] > heap[largest])
                largest = r;

            if (largest != i){
                swap(heap, i, largest);
                i = largest;
            }else
                break;
        }
    }

    @Override
    public String toString() {
        int[] temp = new int[heapSize];
        System.arraycopy(heap, 0, temp, 0, heapSize);
        return Arrays.toString(temp);
    }

    private int search(int value){
        for (int i = 0; i < heapSize; i++) {
            if (value == heap[i])
                return i;
        }
        return -1;
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
