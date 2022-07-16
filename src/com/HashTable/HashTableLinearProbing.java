package com.HashTable;

public class HashTableLinearProbing<K, V> extends HashTableOpenAddressingBase<K, V> {

    // this is the linear constant used in linear probing, it can be
    // any positive number. The table capacity is adjusted so that
    // GCD(capacity, LINEAR_CAPACITY) = 1 so that all buckets can be probed
    private static final int LINEAR_CONSTANT = 17;

    public HashTableLinearProbing() {
        super();
    }

    public HashTableLinearProbing(int capacity) {
        super(capacity);
    }

    public HashTableLinearProbing(int capacity, double maxLoadFactor) {
        super(capacity, maxLoadFactor);
    }

    @Override
    protected void setupProbing(K key) {

    }

    @Override
    protected int probe(int x) {
        return LINEAR_CONSTANT * x;
    }

    @Override
    protected void adjustCapacity() {
        while (gcd(LINEAR_CONSTANT, capacity) != 1)
            capacity++;
    }
}
