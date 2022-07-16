package com.stack;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Animal {
    String name;
    int order;

    Animal(String n){
        this.name = n;
    }

    public void setOrder(int order){
        this.order = order;
    }

    public int getOrder(){
        return this.order;
    }

    public abstract String getName();

}

class Dog extends Animal {

    Dog(String n){
        super(n);
    }

    public String getName(){
        return "Dog: "+ this.name;
    }
}

class Cat extends Animal {

    Cat(String n){
        super(n);
    }

    public String getName(){
        return "Cat: "+ this.name;
    }
}

class AnimalShelter{
    Queue<Cat> cats = new LinkedList<>();
    Queue<Dog> dogs = new LinkedList<>();
    int order = 0;

    public void enqueue(Animal a){
        a.setOrder(order++);

        if (a instanceof Dog)
            dogs.add((Dog) a);
        else
            cats.add((Cat) a);
    }

    public Animal Dequeue(){
        Dog dog = dogs.poll();
        Cat cat = cats.poll();

        if (dog.getOrder() > cat.getOrder()){
            return dogs.remove();
        }else {
            return cats.remove();
        }
    }

    public static void main(String[] args) {
        AnimalShelter a = new AnimalShelter();
        a.enqueue(new Dog("Prajwal"));
        a.enqueue(new Dog("Prakash"));
        a.enqueue(new Dog("Sanket"));
    }
}
