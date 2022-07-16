package com.disjointset;

import java.util.*;

// Disjoint Set using linked list.
public class DisjointSetLL<T>{
    private ArrayList<IndexNode> setList;
    private Map<T, Node> hashMap;
    private int cardinality;

    public DisjointSetLL() {
        this.setList = new ArrayList<>();
        this.hashMap = new HashMap<>();
        this.cardinality = 0;
    }

    private class IndexNode{
        Node head;
        Node tail;
        int size;
        int index;

        public IndexNode(Node head, Node tail, int size, int index) {
            this.head = head;
            this.tail = tail;
            this.size = size;
            this.index = index;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            Node head = this.head;
            while (head != null){
                sb.append(head.setMember + " ");
                head = head.next;
                if (head != null)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        }
    }

    private class Node{
        IndexNode setObject;
        T setMember;
        Node next;

        public Node(IndexNode setObject, T setMember, Node next) {
            this.setObject = setObject;
            this.setMember = setMember;
            this.next = next;
        }
    }

    // O(1) time
    public boolean makeSet(T x){
        // check if any element present already in the set.
        if (hashMap.get(x) != null)
            return false;

        // create set element.
        Node node = new Node(null,x,null);

        // create index node and make its head and tail point to node
        IndexNode iNode = new IndexNode(node,node,1,this.cardinality);

        // make set element point to the index node.
        node.setObject = iNode;
        setList.add(iNode);

        hashMap.put(x,node);
        this.cardinality++;
        return true;
    }

    // O(1) time
    public Node findSet(T x){
        return hashMap.get(x).setObject.head;
    }

    // O(n) Amortized time.
    public boolean union(T x1, T x2){
        IndexNode i1 = hashMap.get(x1).setObject;
        IndexNode i2 = hashMap.get(x2).setObject;

        if (i1 == i2)
            return false;

        // update next part of tail of the set 1 to the head of the min size set.
        Node tail;
        Node head;
        Node newTail;
        if (i1.size >= i2.size){
            tail = i1.tail;
            head = i2.head;
            newTail = i2.tail;
            setList.remove(i2.index);
        }else {
            tail = i2.tail;
            head = i1.head;
            newTail = i1.tail;
            setList.remove(i1.index);
        }

        tail.next = head;
        tail = newTail;
        while (head != null){
            head.setObject = tail.setObject;
            head = head.next;
        }
        this.cardinality--;
        return true;
    }

    public static void main(String[] args) {
        DisjointSetLL<Integer> set = new DisjointSetLL<>();
        for (int i = 0; i < 10; i++) {
            set.makeSet(i);
        }

        // next time set will not allow these elements to add, because this will violate the property of the disjoint set

        for (int i = 0; i < 10; i++) {
            set.makeSet(i);
        }
        set.union(3,4);
        set.union(2,3);
        System.out.println(set.setList);
        System.out.println(set.cardinality);
    }
}
