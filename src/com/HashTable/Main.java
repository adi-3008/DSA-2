package com.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTableDoubleHashing<DoubleHashingTestObject, String> map = new HashTableDoubleHashing<>();
        map.insert(new DoubleHashingTestObject(23),"aditya");
        map.insert(new DoubleHashingTestObject(56),"thor");
        System.out.println(map);
    }
}
