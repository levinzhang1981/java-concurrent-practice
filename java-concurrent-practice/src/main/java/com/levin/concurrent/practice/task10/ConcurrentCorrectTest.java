package com.levin.concurrent.practice.task10;

import com.levin.concurrent.practice.task09.ReadWriteLockedGrocery;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentCorrectTest extends JSR166TestCase {

    @Test
    public void testReadWriteLockedGrocery() throws InterruptedException {
        ReadWriteLockedGrocery readWriteLockedGrocery = new ReadWriteLockedGrocery();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        int listSize = 30;
        Runnable runnable = () -> {
            for(int i=0;i<listSize;i++){
                readWriteLockedGrocery.addFruit(i,"Apple");
                readWriteLockedGrocery.addVegetable(i,"tomato");
            }
        };
        for(int i=0;i<20;i++){
            executorService.submit(runnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(100, TimeUnit.SECONDS);
        threadAssertEquals(20*30,readWriteLockedGrocery.getVegetableSize());
        threadAssertEquals(20*30,readWriteLockedGrocery.getFruitSize());

    }
}
