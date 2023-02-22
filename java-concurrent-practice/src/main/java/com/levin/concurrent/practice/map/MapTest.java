package com.levin.concurrent.practice.map;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapTest {
    public static void main(String[] args) throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        testMap(map);
        System.out.println(String.format("The expected Map size is 200000, the actual HashMap size is:[%d]",map.size()));
        Map<String, String> map2 = new ImprovedHashMap<>();
        testMap(map2);
        System.out.println(String.format("The expected Map size is 200000, the actual ImprovedHashMap size is:[%d]",map2.size()));
    }

    private static void testMap(Map<String,String> map) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            for (int i = 0; i < 100000; i++) {
                String id = UUID.randomUUID().toString();
                map.put(id,id);
            }
            countDownLatch.countDown();
        });
        executor.submit(() -> {
            for (int i = 0; i < 100000; i++) {
                String id = UUID.randomUUID().toString();
                map.put(id,id);
            }
            countDownLatch.countDown();
        });
        countDownLatch.await();
        executor.shutdown();
    }
}
