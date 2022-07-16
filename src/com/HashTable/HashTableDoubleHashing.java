package com.HashTable;

import java.math.BigInteger;

public class HashTableDoubleHashing<K extends SecondaryHash, V> extends HashTableOpenAddressingBase<K, V> {

    private int hash2;

    public HashTableDoubleHashing() {
        super();
    }

    public HashTableDoubleHashing(int capacity) {
        super(capacity);
    }

    // Designated constructor
    public HashTableDoubleHashing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    protected void setupProbing(K key) {
        // Cache second hash value.
        hash2 = normalizedIndex(key.hashCode2());

        // Fail safe to avoid infinite loop.
        if (hash2 == 0) hash2 = 1;
    }

    @Override
    protected int probe(int x) {
        return x * (hash2 % capacity);
    }

    // Adjust the capacity until it is a prime number. The reason for
    // doing this is to help ensure that the GCD(hash, capacity) = 1 when
    // probing so that all the cells can be reached.
    @Override
    protected void adjustCapacity() {
        while (!(new BigInteger(String.valueOf(capacity)).isProbablePrime(20))) {
            capacity++;
        }
    }

}