package com.levin.concurrent.practice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSafeTest {
    public static class UnsafeSequence {
        private int value;

        public int getNext() {
            return value++;
        }
    }

    public static class SafeSequence {
        private int value;

        public synchronized int getNext() {
            return value++;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        SafeSequence safeSequence = new SafeSequence();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        executor.submit(() -> {
            for (int i = 0; i < 100000; i++) {
                safeSequence.getNext();
                unsafeSequence.getNext();
            }
            countDownLatch.countDown();
        });
        executor.submit(() -> {
            for (int i = 0; i < 100000; i++) {
                safeSequence.getNext();
                unsafeSequence.getNext();
            }
            countDownLatch.countDown();
        });
        countDownLatch.await();
        executor.shutdown();
        System.out.println(String.format("The Thread Safe sequence value should be 200000," +
                " the actual value is:[%d]", safeSequence.value));
        System.out.println(String.format("The Not Thread Safe sequence value is unknown," +
                " the actual value is:[%d]", unsafeSequence.value));
    }
}
