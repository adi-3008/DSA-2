package com.HashTable;

import java.util.ArrayList;
import java.util.List;

public abstract class HashTableOpenAddressingBase<K, V> {
    protected final double maxLoadFactor;
    protected int capacity, threshold;

    // 'usedBuckets' counts the total number of used buckets inside the hash table
    // (includes cells marked as deleted). while 'keyCount' tracks the number of unique
    // keys currently inside the hashtable
    private int usedBuckets = 0, keyCount = 0;

    // these arrays store key-value pair
    private K[] keyTable;
    private V[] valueTable;

    // special marker token used to indicate the deletion of key value pair
    private final K TOMBSTONE = (K) new Object();

    private static final int DEFAULT_CAPACITY = 8;
    private static final double DEFAULT_LOAD_FACTOR = 0.45;
    private boolean containsFlag = false;

    public HashTableOpenAddressingBase() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableOpenAddressingBase(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    // Designated Constructor
    public HashTableOpenAddressingBase(int capacity, double maxLoadFactor) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal LoadFactor: " + maxLoadFactor);
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        adjustCapacity();
        threshold = (int) (this.capacity * maxLoadFactor);

        keyTable = (K[]) (new Object[this.capacity]);
        valueTable = (V[]) (new Object[this.capacity]);
    }

    // These three methods are used to dictate how the probing is to actually
    // occur for whatever open addressing scheme you are implementing.
    protected abstract void setupProbing(K key);

    protected abstract int probe(int x);

    // adjusts the capacity of the hash table after it's been made larger.
    // This is important to be able to override because the size of the hashtable
    // controls the functionality of the probing function.
    protected abstract void adjustCapacity();

    // Increases the capacity of the hash table.
    protected void increaseCapacity() {
        capacity = (2 * capacity) + 1;
    }

    // Quadratic probing function (x^2+x)/2
//    private int P(int x){
//        return (x*x + x) >> 1;
//    }

    // Converts a hash value to an index. Essentially this strips
    // negative sign and places the hash value in the domain [0 to capacity]
    protected int normalizedIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    // find the greatest common denominator of a and b
    protected static final int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // Clears all the content of the hash table
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            keyTable[i] = null;
            valueTable[i] = null;
        }
        keyCount = usedBuckets = 0;
    }

    // returns the number of keys inside the hash table
    public int size() {
        return keyCount;
    }

    // return true/false depending on hash table is empty or not.
    public boolean isEmpty() {
        return keyCount == 0;
    }

    // Insert, put and add all place a value in a hash table.
    public V add(K key, V value) {
        return insert(key, value);
    }

    public V put(K key, V value) {
        return insert(key, value);
    }

    // place a key value pair in hash table. If the value already exist
    // inside the hash table then value is updated.
    public V insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Illegal key");
        if (usedBuckets >= threshold) resizeTable();

        setupProbing(key);
        final int hash = normalizedIndex(key.hashCode());
        int i = hash, j = -1, x = 1;

        do {
            // the current slot was previously deleted
            if (keyTable[i] == TOMBSTONE) {
                // this means we have hit first TOMBSTONE then
                // save where it found
                if (j == -1) j = i;
            }

            // the current cell already contains a key
            else if (keyTable[i] != null) {

                // the key we are trying to insert is already exist in the hash table,
                // so update its value with most recent value
                if (keyTable[i].equals(key)) {
                    V oldVal = valueTable[i];
                    if (j == -1) {
                        valueTable[i] = value;
                    } else {
                        keyTable[j] = key;
                        valueTable[j] = value;
                        keyTable[i] = TOMBSTONE;
                        valueTable[i] = null;
                    }
                    return oldVal;
                }
            }
            // Current cell is null so insertion/update can occur
            else {
                // No previously encounter deleted bucket.
                if (j == -1) {
                    usedBuckets++;
                    keyCount++;
                    keyTable[i] = key;
                    valueTable[i] = value;
                }
                // previously seen deleted bucket.Instead of inserting
                // the new element at i, where the null element is insert it
                // where the deleted element is found.
                else {
                    keyCount++;
                    keyTable[j] = key;
                    valueTable[j] = value;
                }
                return null;
            }
            i = normalizedIndex(hash + probe(x++));
        } while (true);
    }

    // return true/false on whether a given key exists within hash table.
    public boolean containsKey(K key) {
        return hasKey(key);
    }

    public boolean hasKey(K key) {
        // set the 'containsFlag'
        get(key);

        return containsFlag;
    }

    // get the value associated with input key.
    // NOTE : returns null if value is null AND also
    // returns null if key does not exist.
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Null Key");

        setupProbing(key);
        final int hash = normalizedIndex(key.hashCode());
        int i = hash, j = -1, x = 1;

        // Starting at the original hash index quadratically probe until we find a
        // spot where
        // our key is, or we hit a null element in which case out element does not exist.
        do {

            // Ignore deleted cell but record where the first index
            // of a deleted is found to perform lazy relocation later.
            if (keyTable[i] == TOMBSTONE) {
                if (j == -1) j = i;
            }

            // we hit a non-null key, perhaps it's the one we are looking for.
            else if (keyTable[i] != null) {

                // the key we want is in the hash table !
                if (keyTable[i].equals(key)) {
                    containsFlag = true;

                    // if j != -1 this means we previously encountered a deleted cell
                    // we can perform an optimization by swapping the entries in the cell.
                    // i and j so that next time we search for this key it will be found faster.
                    // this is called lazy deletion/relation,.
                    if (j != -1) {

                        // copy values to where the deleted bucket is.
                        keyTable[j] = keyTable[i];
                        valueTable[j] = valueTable[i];

                        // clear the content of bucket i and mark it as deleted
                        keyTable[i] = TOMBSTONE;
                        valueTable[i] = null;
                    }
                    return valueTable[j];
                }
            }
            // element was not found in the hashtable
            // return null
            else {
                containsFlag = false;
                return null;
            }

            i = normalizedIndex(hash + probe(x++));
        } while (true);
    }

    // Removes the key from the map and return value.
    // NOTE: returns null if the value is null AND also
    // if key does not exist in the hash table.
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("Null key");

        setupProbing(key);
        final int hash = normalizedIndex(key.hashCode());
        int i = hash, x = 1;

        // Starting at the original hash index quadratically probe until we find a
        // spot where
        // our key is, or we hit a null element in which case out element does not exist.
        for (; ; i = normalizedIndex(hash + probe(x++))) {

            // Ignore deleted cell.
            if (keyTable[i] == TOMBSTONE) continue;

            // key does not exist in the hash table.
            if (keyTable[i] == null) return null;

            // we found the element to be deleted.
            if (keyTable[i].equals(key)) {
                V oldVal = valueTable[i];
                keyTable[i] = TOMBSTONE;
                valueTable[i] = null;
                keyCount--;
                return oldVal;
            }
        }
    }

    // Returns a list of keys found in the hash table.
    public List<K> keys() {
        List<K> list = new ArrayList<>();
        for (K key : keyTable) {
            if (key != null && key != TOMBSTONE)
                list.add(key);
        }
        return list;
    }

    // Returns a list of non-unique values found in the hash table.
    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (V val : valueTable) {
            if (val != null && val != TOMBSTONE)
                list.add(val);
        }
        return list;
    }

    // Double the size of the Hash-table
    private void resizeTable() {
        increaseCapacity();
        adjustCapacity();
        threshold = (int) (capacity * maxLoadFactor);

        K[] oldKeyTable = (K[]) (new Object());
        V[] oldValueTable = (V[]) (new Object());

        // perform key table pointer swap
        K[] keyTabletmp = keyTable;
        keyTable = oldKeyTable;
        oldKeyTable = keyTabletmp;

        // perform value table pointer swap.
        V[] valueTabletmp = valueTable;
        valueTable = oldValueTable;
        oldValueTable = valueTabletmp;

        // reset the key count and buckets used since we are about to
        // re-insert all the keys in the hash table.
        keyCount = usedBuckets = 0;

        for (int i = 0; i < oldKeyTable.length; i++) {
            if (oldValueTable[i] != null && oldKeyTable[i] != TOMBSTONE)
                insert(oldKeyTable[i], oldValueTable[i]);
            oldKeyTable[i] = null;
            oldValueTable[i] = null;
        }
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            StringBuilder temp = new StringBuilder();
            if (keyTable[i] != null && keyTable[i] != TOMBSTONE) {
                temp.append(keyTable[i]).append(" => ").append(valueTable[i]);
                list.add(temp.toString());
            }
        }
        return list.toString();
    }
}
