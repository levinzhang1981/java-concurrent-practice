package com.levin.concurrent.practice.map;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImprovedHashMap<K,V> extends HashMap<K,V> {

    private Lock lock = new ReentrantLock();

    public ImprovedHashMap() {
        super();
    }
    public ImprovedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public V get(Object key) {
        lock.lock();
        V result = super.get(key);
        lock.unlock();
        return result;
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        V result = super.put(key, value);
        lock.unlock();
        return result;
    }
}
