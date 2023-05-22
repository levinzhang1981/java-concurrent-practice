package com.levin.concurrent.practice.task11;

import com.levin.concurrent.practice.task09.Grocery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockGrocery implements Grocery {
    private final List<String> fruits = new ArrayList<>();
    private final List<String> vegetables = new ArrayList<>();
    private  ReadWriteLock fruitLock;
    private  ReadWriteLock vegetableLock;

    public ReentrantLockGrocery(boolean isFair){
        fruitLock = new ReentrantReadWriteLock(isFair);
        vegetableLock = new ReentrantReadWriteLock(isFair);
    }

    public void addFruit(int index, String fruit) {
        fruitLock.writeLock().lock();
        try {
            fruits.add(index, fruit);
        } finally {
            fruitLock.writeLock().unlock();
        }
    }

    public void addVegetable(int index, String vegetable) {
        vegetableLock.writeLock().lock();
        try {
            vegetables.add(index, vegetable);
        } finally {
            vegetableLock.writeLock().unlock();
        }
    }


}
