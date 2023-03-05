package com.levin.concurrent.practice.map;

import com.levin.concurrent.practice.TestHarness;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PerformanceTest {
    private static  int SMALL_THREAD_COUNT = 5;
    private static  int MEDIUM_THREAD_COUNT = 20;
    private static  int LARGE_THREAD_COUNT = 50;
    private static TestHarness testHarness = new TestHarness();
    public static void main(String[] args) throws InterruptedException {

        Map<String,String> standardMap = new HashMap();
        Map<String,String> improvedMap = new ImprovedHashMap<>();
        Map<String,String> concurrentMap = new ConcurrentHashMap<>();
        System.out.println("This is the test for standard Map based on small thread account. The time spent is:");
        System.out.println(testMapPerformance(SMALL_THREAD_COUNT,standardMap));
        System.out.println("This is the test for improved HashMap based on small thread account. The time spent is:");
        System.out.println(testMapPerformance(SMALL_THREAD_COUNT,improvedMap));
        System.out.println("This is the test for concurrent Map based on small thread account. The time spent is:");
        System.out.println(testMapPerformance(SMALL_THREAD_COUNT,concurrentMap));
        standardMap.clear();
        improvedMap.clear();
        concurrentMap.clear();
        System.out.println("This is the test for standard Map based on medium thread account. The time spent is:");
        System.out.println(testMapPerformance(MEDIUM_THREAD_COUNT,standardMap));
        System.out.println("This is the test for improved HashMap based on medium thread account. The time spent is:");
        System.out.println(testMapPerformance(MEDIUM_THREAD_COUNT,improvedMap));
        System.out.println("This is the test for concurrent Map based on medium thread account. The time spent is:");
        System.out.println(testMapPerformance(MEDIUM_THREAD_COUNT,concurrentMap));
        standardMap.clear();
        improvedMap.clear();
        concurrentMap.clear();
        System.out.println("This is the test for standard Map based on large thread account. The time spent is:");
        System.out.println(testMapPerformance(LARGE_THREAD_COUNT,standardMap));
        System.out.println("This is the test for improved HashMap based on large thread account. The time spent is:");
        System.out.println(testMapPerformance(LARGE_THREAD_COUNT,improvedMap));
        System.out.println("This is the test for concurrent Map based on large thread account. The time spent is:");
        System.out.println(testMapPerformance(LARGE_THREAD_COUNT,concurrentMap));


    }

    private static long testMapPerformance(int threadCount, Map<String,String> map) throws InterruptedException {
       return  testHarness.timeTasks(threadCount,()->{
            for(int i=0;i<5000;i++){
                String id = UUID.randomUUID().toString();
                map.put(id,id);
            }
        });
    }
}
