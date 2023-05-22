package com.levin.concurrent.practice.task11;


import com.levin.concurrent.practice.task09.Grocery;
import com.levin.concurrent.practice.task09.SynchronizedGrocery;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 1, time = 1)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 1, time = 1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class FairGroceryPerformanceBenchmark {
    private static final int OPERATIONS_PER_THREAD = 10000;
    private static final String FRUIT = "Banana";
    private static final String VEGETABLE = "Broccoli";
    //@Param({"1", "2", "4", "8", "16", "32", "64"})
    @Param({"1", "2", "4", "8", "16"})
    private int concurrencyLevel;
    private Grocery syncGrocery;
    private Grocery reentrantLockGrocery;

    @Setup
    public void setup() {
        syncGrocery = new SynchronizedGrocery();
        reentrantLockGrocery = new ReentrantLockGrocery(true);
    }

    @Benchmark
    public void givenThreads_thenMeasure_SynchronizedLockedGrocery_whenAdd10000Times() {
        Thread[] threads = new Thread[concurrencyLevel];
        for (int i = 0; i < concurrencyLevel; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    syncGrocery.addFruit(j, FRUIT);
                    syncGrocery.addVegetable(j, VEGETABLE);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Benchmark
    public void givenThreads_thenMeasure_ReadWriteLockedGrocery_whenAdd10000Times() {
        Thread[] threads = new Thread[concurrencyLevel];
        for (int i = 0; i < concurrencyLevel; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    reentrantLockGrocery.addFruit(j, FRUIT);
                    reentrantLockGrocery.addVegetable(j, VEGETABLE);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
