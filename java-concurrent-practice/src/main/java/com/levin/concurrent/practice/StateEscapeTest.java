package com.levin.concurrent.practice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StateEscapeTest {
    public static void main(String[] args) throws InterruptedException {
        UnsafeStates unsafeStates = new UnsafeStates();
        String[] states = unsafeStates.getStates();
        SafeStates safeStates = new SafeStates();
        String[] states2 = safeStates.getStates();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executors =  Executors.newFixedThreadPool(2);
        executors.submit(()->{
            states[states.length-1] = "AM";
            System.out.println("The original second element of UnsafeStates is: [AL]");
            System.out.println(String.format("Now, the second element of UnsafeStates is: [%s]",
                    unsafeStates.getStates()[1]));
            countDownLatch.countDown();
        });
        executors.submit(()->{
            states2[states2.length-1] = "AM";
            System.out.println("The original second element of SafeStates is: [AL]");
            System.out.println(String.format("Now, the second element of SafeStates is: [%s]",
                    safeStates.getStates()[1]));
            countDownLatch.countDown();
        });
        countDownLatch.await();
        executors.shutdown();
    }
}
