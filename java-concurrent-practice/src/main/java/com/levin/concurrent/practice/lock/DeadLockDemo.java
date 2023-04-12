package com.levin.concurrent.practice.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    private final Lock lock1 =new ReentrantLock();
    private final Lock lock2 =new ReentrantLock();

    public static void main(String[] args){
        DeadLockDemo deadLockDemo = new DeadLockDemo();
        deadLockDemo.showDeadLock();
    }
    private void showDeadLock(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        executorService.submit(()->{
            try {
                System.out.println("We are trying to get the first lock from the first task.");
                lock1.lock();
                System.out.println("We get the first lock from the first task.");
                countDownLatch.countDown();
                countDownLatch.await();
                System.out.println("We are trying to get the second lock from the first task.");
                lock2.lock();
                System.out.println("We get the second lock from the first task.");
            }catch (InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        });
        executorService.submit(()->{
            try {
                System.out.println("We are trying to get the second lock from the second task.");
                lock2.lock();
                System.out.println("We get the second lock from the second task.");
                countDownLatch.countDown();
                countDownLatch.await();
                System.out.println("We are trying to get the first lock from the second task.");
                lock1.lock();
                System.out.println("We get the first lock from the second task.");
            }catch (InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }finally {
                lock1.unlock();
                lock2.unlock();
            }
        });
        executorService.shutdown();
    }
}
