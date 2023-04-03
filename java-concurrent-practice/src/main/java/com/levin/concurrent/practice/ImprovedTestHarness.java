package com.levin.concurrent.practice;

import java.util.concurrent.*;

public class ImprovedTestHarness {
    public long timeTasks(int nThreads, int timeoutInSeconds,final Runnable task)
            throws InterruptedException, ExecutionException {
        ExecutorService executorServiceForRealTask = new TimingThreadPool(nThreads,false);

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        FutureTask<Long> startTask = new FutureTask<>(() -> System.nanoTime());
        FutureTask<Long> endTask = new FutureTask<>(() -> System.nanoTime());
        final CyclicBarrier startGate = new CyclicBarrier(nThreads, startTask);
        final CyclicBarrier endGate = new CyclicBarrier(nThreads,endTask);
        for (int i = 0; i < nThreads; i++){
            executorService.submit(()->{
                try {
                    startGate.await();
                    Future future = executorServiceForRealTask.submit(task);
                    try {
                        future.get(timeoutInSeconds,TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (TimeoutException e) {
                        future.cancel(true);
                        System.out.println("We encounter timeout and cancel current task");
                    } finally {
                        endGate.await();
                    }
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
