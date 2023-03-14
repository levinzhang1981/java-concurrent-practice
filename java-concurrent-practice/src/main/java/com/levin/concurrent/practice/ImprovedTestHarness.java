package com.levin.concurrent.practice;

import java.util.concurrent.*;

public class ImprovedTestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        FutureTask<Long> startTask = new FutureTask<>(() -> System.nanoTime());
        FutureTask<Long> endTask = new FutureTask<>(() -> System.nanoTime());
        final CyclicBarrier startGate = new CyclicBarrier(nThreads, startTask);
        final CyclicBarrier endGate = new CyclicBarrier(nThreads,endTask);
        for (int i = 0; i < nThreads; i++){
            executorService.submit(()->{
                try {
                    startGate.await();
                    task.run();
                    endGate.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
        return endTask.get()-startTask.get();
    }
}
