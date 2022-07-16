package com.Heap;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Heap<T extends Comparable<T>> {
    private T[] heap;
    private int heapSize;
    private int heapCapacity;
    private Map<T, Integer> hashMap;
    private static final int DEFAULT_SIZE = 10;

    public Heap() {
        this(DEFAULT_SIZE);
    }

    public Heap(int heapCapacity) {
        this.heap = (T[])new Object[heapCapacity];
        this.heapSize = 0;
        this.heapCapacity = heapCapacity;
        this.hashMap = new HashMap<>();
    }

    public Heap(T[] arr) {
        this.heap = arr;
        this.heapSize = 0;
        this.heapCapacity = arr.length;
        this.hashMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            hashMap.put(arr[i], i);
        }
        buildHeap();

    }

    public Heap(Collection<T> elements) {
        this.heapCapacity = elements.size();
        this.heap = (T[])elements.toArray();
        this.heapSize = heapCapacity;
        this.hashMap = new HashMap<>();
        for (int i = 0; i < heap.length; i++) {
            hashMap.put((T) heap[i], i);
        }
        buildHeap();
    }

    // O(1)
    public boolean contains(T elems) {
        return elems != null && hashMap.containsKey(elems);
    }


    public boolean replace(T src, T dest) {
        int i = hashMap.get(src);  // O(1)
        if (i != -1) {
            if (src.compareTo(dest) >= 0)
                return increaseKey(i, dest);
            return decreaseKey(i, dest);
        }
        return false;
    }

    public void sort() {
        heapSort();
    }

    public void insertKey(T value) {
        if (isFull()) {
            T[] temp = (T[])new Object[heapCapacity * 2];
            if (heapCapacity >= 0) System.arraycopy(heap, 0, temp, 0, heapCapacity);
            heap = temp;
            heapCapacity *= 2;
        }

        // add the element to the last of the array.
        heap[heapSize] = value;
        hashMap.put(value, heapSize);


        // maintain heap invariant.
        // swim. O(logn)
        int i = heapSize;
        while (i > 0 && ((T) heap[(i - 1) / 2]).compareTo((T) heap[i]) > 0) {
            swap(heap, (i - 1) / 2, i);
            i = (i - 1) / 2;
        }

        // increase size of the heap.
        heapSize++;

    }

    public T deleteKey(T value) {
        int i = hashMap.remove(value);
        heap[i] = heap[--heapSize];
        hashMap.replace((T) heap[i], i);

        maxHeapify(i);
        return value;
    }

    private boolean decreaseKey(int i, T value) {
        T src = (T) heap[i];
        if (src.compareTo(value) > 0)
            return false;
        heap[i] = value;

        // make changes in the map also
        // O(1)
        hashMap.remove(src);
        hashMap.put(value, i);

        maxHeapify(i);


        return true;
    }

    public boolean decreaseKey(T src, T dest) {
        int i = hashMap.remove(src);
        return decreaseKey(i, dest);
    }

    private boolean increaseKey(int i, T value) {
        T src = (T) heap[i];
        if (src.compareTo(value) < 0)
            return false;
        heap[i] = value;

        // make changes in the map also
        // O(1)
        hashMap.remove(src);
        hashMap.put(value, i);

        while (i > 0 && ((T) heap[(i - 1) / 2]).compareTo((T) heap[i]) > 0) {
            swap(heap, (i - 1) / 2, i);
            i = (i - 1) / 2;
        }
        return true;
    }

    public T extractMin() {
        T key = (T) heap[0];
        heap[0] = heap[--heapSize];
        hashMap.replace((T) heap[0], 0);

        // make changes in the map also
        // O(1)
        hashMap.remove(key);

        maxHeapify(0);
        return key;
    }

    public void heapSort() {
        int temp = heapSize;
        for (int i = heapSize; i > 0; i--) {
            swap(heap, 0, heapSize - 1);
            heapSize--;
            maxHeapify(0);
        }

        for (int i = 0; i < temp / 2; i++) {
            swap(heap, i, temp - i - 1);
        }
        heapSize = temp;

    }

    public boolean isFull() {
        return heapSize == heapCapacity;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    private void buildHeap() {
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    private void maxHeapify(int i) {
        while (true) {
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int largest = i;

            if (l < heapSize && ((T) heap[l]).compareTo((T) heap[i]) < 0)
                largest = l;
            if (r < heapSize && ((T) heap[r]).compareTo((T) heap[largest]) < 0)
                largest = r;

            if (largest != i) {
                swap(heap, i, largest);
                i = largest;
            } else
                break;
        }
    }

    @Override
    public String toString() {
        Object[] temp = new Object[heapSize];
        System.arraycopy(heap, 0, temp, 0, heapSize);
        return Arrays.toString(heap);
    }

    private void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        T temp1 = (T) arr[i];
        T temp2 = (T) arr[j];
        hashMap.replace(temp1, i);
        hashMap.replace(temp2, j);

    }
}
