package com.levin.concurrent.practice;

import java.util.List;
import java.util.concurrent.*;

public class TimingThreadPool extends ThreadPoolExecutor {

    public TimingThreadPool(int corePoolSize, boolean shouldStartAllCoreThreads) {
        super(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        if (shouldStartAllCoreThreads) {
            startAllCoreThreads(corePoolSize);
        }
    }


    private void startAllCoreThreads(int corePoolSize){
        CountDownLatch warnUpTaskCountDown = new CountDownLatch(corePoolSize);
        for(int i=0;i<corePoolSize;i++){
            submit(() -> {
                warnUpTaskCountDown.countDown();
                try {
                    warnUpTaskCountDown.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("There is error when try to start the Thread");
                    throw new RuntimeException(e);
                }
            });
        }
    }

}

