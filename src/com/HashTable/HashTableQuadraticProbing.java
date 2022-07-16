package com.HashTable;

public class HashTableQuadraticProbing<K, V> extends HashTableOpenAddressingBase<K, V> {

    // In this implementation we are using following probing function
    // H(k,x) = H(k) + P(x)

    // Where H(k) is hash for the given key P(x) = (x^2 + x)/2 and n is natural number.
    // We are using this probing function because it is guaranteed to find an empty cell
    // it finds all the numbers in the range [0 to 2^n] without repetition of first 2^n

    public HashTableQuadraticProbing() {
        super();
    }

    public HashTableQuadraticProbing(int capacity) {
        super(capacity);
    }

    public HashTableQuadraticProbing(int capacity, double maxLoadFactor) {
        super(capacity, maxLoadFactor);
    }

    // Given a number this method find next higher power of 2 above this value.
    private int nextPowerOfTwo(int n) {
        return Integer.highestOneBit(n) << 1;
    }

    // No setup required for quadratic probing.
    @Override
    protected void setupProbing(K key) {

    }

    @Override
    protected int probe(int x) {
        return (x * x + x) >> 1;
    }

    // Increase the capacity of the hashtable to the next power of two.
    @Override
    protected void increaseCapacity() {
        capacity = nextPowerOfTwo(capacity);
    }

    // adjust the capacity of the hashtable to be power of two.

    // First capacity will increase to next power of 2.

    // Integer.highestOneBit(capacity) reset all 1's to the left of MSB
    // to 0's to give power of 2 which may less than or equal to capacity.

    @Override
    protected void adjustCapacity() {
        // this will give power of 2 less than equal to capacity.
        int pow = Integer.highestOneBit(capacity);

        // pow and capacity are same then no need to adjust power it is already
        // in the order of power of two.
        if (capacity == pow) return;

        // but it does no in the order of power of two then increase capacity in the order
        // power of two.
        increaseCapacity();
    }
}
