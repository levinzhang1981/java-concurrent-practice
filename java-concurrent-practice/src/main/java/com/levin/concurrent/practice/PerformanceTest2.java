package com.levin.concurrent.practice;

import com.levin.concurrent.practice.map.ImprovedHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class PerformanceTest2 {
    private static  int MEDIUM_THREAD_COUNT = 20;
    private static TestHarness testHarness = new TestHarness();
    private static ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Map<String,String> standardMap = new HashMap();
        Map<String,String> improvedMap = new ImprovedHashMap<>();
        Map<String,String> concurrentMap = new ConcurrentHashMap<>();

        System.out.println("Test with TestHarness");
        System.out.println(String.format("This is the test for standard Map based on 20 thread account. The time spent is:[%d]"
                ,testMapPerformance(MEDIUM_THREAD_COUNT,standardMap)));
        System.out.println(String.format("This is the test for improved Map based on 20 thread account. The time spent is:[%d]"
                ,testMapPerformance(MEDIUM_THREAD_COUNT,improvedMap)));
        System.out.println(String.format("This is the test for concurrent Map based on 20 thread account. The time spent is:[%d]"
                ,testMapPerformance(MEDIUM_THREAD_COUNT,concurrentMap)));


        Map<String,String> standardMap2 = new HashMap();
        Map<String,String> improvedMap2 = new ImprovedHashMap<>();
        Map<String,String> concurrentMap2 = new ConcurrentHashMap<>();

        System.out.println("Test with ImprovedTestHarness");
        System.out.println(String.format("This is the test for standard Map based on 20 thread account with ImprovedTestHarness . The time spent is:[%d]"
                ,testMapPerformanceWithImprovedTestHarness(MEDIUM_THREAD_COUNT,standardMap2)));
        System.out.println(String.format("This is the test for improved Map based on 20 thread account with ImprovedTestHarness. The time spent is:[%d]"
                ,testMapPerformanceWithImprovedTestHarness(MEDIUM_THREAD_COUNT,improvedMap2)));
        System.out.println(String.format("This is the test for concurrent Map based on 20 thread account with ImprovedTestHarness. The time spent is:[%d]"
                ,testMapPerformanceWithImprovedTestHarness(MEDIUM_THREAD_COUNT,concurrentMap2)));



    }

    private static long testMapPerformance(int threadCount, Map<String,String> map) throws InterruptedException {
        return  testHarness.timeTasks(threadCount,()->{
            for(int i=0;i<5000;i++){
                String id = UUID.randomUUID().toString();
                map.put(id,id);
            }
        });
    }
    private static long testMapPerformanceWithImprovedTestHarness(int threadCount, Map<String,String> map)
            throws InterruptedException, ExecutionException {
        return  improvedTestHarness.timeTasks(threadCount,()->{
            for(int i=0;i<5000;i++){
                String id = UUID.randomUUID().toString();
                map.put(id,id);
            }
        });
    }
}
