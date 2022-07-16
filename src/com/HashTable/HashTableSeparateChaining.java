package com.HashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HashTableSeparateChaining<K, V> {
    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private LinkedList<Entry<K, V>>[] table;

    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    // designated constructor
    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be greater than zero !");
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal Load Factor");
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        this.threshold = (int) (this.capacity * maxLoadFactor);
        table = new LinkedList[this.capacity];
    }

    // returns number of elements currently present inside the hashtable.
    public int size() {
        return this.size;
    }

    // returns true or false depending on whether hashtable is empty or not.
    public boolean isEmpty() {
        return size == 0;
    }

    // convert hash value to an index. Essentially this strips
    // negative sign and places hash value in the domain [0 to capacity]
    // this is our so-called hash function
    private int normalizedIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    // clears all the contents of the hash table.
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean containsKey(K key) {
        return hasKey(key);
    }

    // return true/false depending on the key is in hash table or not.
    public boolean hasKey(K key) {
        int bucketIndex = normalizedIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    // Insert, add and put all place a value in the hashtable.
    public void put(K key, V value) {
        insert(key, value);
    }

    public void add(K key, V value) {
        insert(key, value);
    }

    public V insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Null key");
        Entry<K, V> newEntry = new Entry<>(key, value);
        int bucketIndex = normalizedIndex(key.hashCode());
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    // Gets the key's value from the map and return it.
    // NOTE : returns null if the value is null.
    // also return null if the key does not exist so watch out....
    public V get(K key) {
        if (key == null) return null;
        int bucketIndex = normalizedIndex(key.hashCode());
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        return entry != null ? entry.value : null;
    }

    // Remove key from the map and return it.
    // Return if the value is null and also
    // null if the key does not exist.
    public V remove(K key) {
        if (key == null) return null;
        int bucketIndex = normalizedIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    private V bucketRemoveEntry(int bucketIndex, K key) {
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            LinkedList<Entry<K, V>> list = table[bucketIndex];
            list.remove(entry);
            size--;
            if (size == capacity / 4) resizeTable(capacity / 2);
            return entry.value;
        }
        return null;
    }

    // Insert an entry in given bucket if the entry does not already
    // exist in the given bucket, but if it does then update the entry value and return old value.
    // return null if bucket was empty.
    private V bucketInsertEntry(int bucketIndex, Entry<K, V> entry) {
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null) table[bucketIndex] = bucket = new LinkedList<>();

        Entry<K, V> existingEntry = bucketSeekEntry(bucketIndex, entry.key);
        if (existingEntry == null) {
            LinkedList<Entry<K, V>> list = table[bucketIndex];
            list.add(entry);
            if (++size > threshold) resizeTable(capacity * 2);
            return null;
        } else {
            V old = existingEntry.value;
            existingEntry.value = entry.value;
            return old;
        }
    }

    // find and return particular entry in a given bucket if it exists, returns null otherwise.
    private Entry<K, V> bucketSeekEntry(int bucketIndex, K key) {
        if (key == null) return null;
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null) return null;
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key))
                return entry;
        }
        return null;
    }

    private void resizeTable(int newCapacity) {
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);

        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i]) {
                    int bucketIndex = normalizedIndex(entry.key.hashCode());
                    LinkedList<Entry<K, V>> bucket = newTable[bucketIndex];
                    if (bucket == null) newTable[bucketIndex] = new LinkedList<>();
                    else bucket.add(entry);
                }

                // avoid memory leak. Help GC
                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }

    // Returns the list of the keys found within hash table.
    public List<K> keys() {
        List<K> list = new ArrayList<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    list.add(entry.key);
                }
            }
        }
        return list;
    }

    // Returns the list of the keys found within hash table.
    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    list.add(entry.value);
                }
            }
        }
        return list;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null)
                list.add(table[i].toString());
        }
        return list.toString();
    }
}

class Entry<K, V> {
    private final int hash;
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    // this does not override object equal method
    // No casting is required with this method.
    public boolean equals(Entry<K, V> other) {
        if (this.hash != other.hash) return false;
        else return key.equals(other.key);
    }

    @Override
    public String toString() {
        return this.key + " => " + value;
    }

}
