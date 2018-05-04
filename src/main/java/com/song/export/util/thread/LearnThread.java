package com.song.export.util.thread;

import com.sun.javafx.collections.MappingChange;
import io.swagger.models.auth.In;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LearnThread {

    private static void latchTest() throws InterruptedException {
        int poolSize = 100;
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(poolSize);
        ExecutorService exce = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        start.await();
                        testLoad(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            exce.submit(run);
        }
        start.countDown();
        end.await();
        exce.shutdown();
    }
    private static int num = 100;
    private static void testLoad(int num){
        System.out.println(num--);
    }

    /**
     * 案例：实现1-10000的和  分成10个线程去计算,并输出计算结果
     */
    public static Map<Integer,List<Integer>> getDataMap(int keySize,int listSize){
        Map<Integer,List<Integer>> dataMap = new HashMap();
        for(int key = 0; key < keySize; key++){
            dataMap.put(key,produceListData(listSize));
        }
        return dataMap;
    }
    /**
     * 生产随机数列表
     * @return
     */
    public static List<Integer> produceListData(int listSize){
        List list = new ArrayList();
        Random random = new Random();
        for(int i=0;i<listSize;i++){
            list.add(random.nextInt(100)+1);
        }
        return list;
    }
    public static int Sum(Map<Integer,List<Integer>> dataMap){
        Set<Integer> keySet = dataMap.keySet();
        int sum = 0;
        for (Integer key : keySet){
            List<Integer> listData = dataMap.get(key);
            for(Integer num : listData){
                sum += num;
            }
        }
        return sum;
    }
    public static void threadSum(Map<Integer,List<Integer>> dataMap){
        Set<Integer> keySet = dataMap.keySet();
        //线程池
        ExecutorService pool = Executors.newFixedThreadPool(keySet.size());
        for (Integer key : keySet){
            List<Integer> listData = dataMap.get(key);
            Thread thread = new MyThread(listData);
            pool.execute(thread);
        }
        pool.shutdown();
    }
    public static void main(String[] args) {
        //生成模拟数据
        Map<Integer,List<Integer>> dataMap = getDataMap(10,10000000);

        //普通求和
        Long sumBegin = System.currentTimeMillis();
        Sum(dataMap);
        Long sumEnd = System.currentTimeMillis();
        System.out.println("普通求和,耗时："+(sumEnd-sumBegin));
        //数据拆分后,多线程分别求和
        Long threadSumBegin = System.currentTimeMillis();
        threadSum(dataMap);
        Long threadSumEnd = System.currentTimeMillis();
        System.out.println("数据拆分后,多线程分别求和,耗时："+(threadSumEnd-threadSumBegin));
    }
    static class MyThread extends Thread {
        private List<Integer> listData;
        public MyThread(List listData){
            this.listData = listData;
        }
        @Override
        public void run() {
            //Do some thing
            int sum = 0;
            for(Integer num : listData){
                sum += num;
            }
            //System.out.println("线程:" + Thread.currentThread().getName()+" 合计："+sum);
            //???带返回值的怎么处理
        }
    }
    class ForkJoinThread extends RecursiveTask<Long>{
        private Map<Integer,List<Integer>> dataMap;
        private List<Integer> listData;

        public ForkJoinThread(List<Integer> listData) {
            this.listData = listData;
        }

        public Long ForkJoinSum(){
            long sum = 0;
            Set<Integer> keySet = dataMap.keySet();
            for (Integer key : keySet){
                List<Integer> listData = dataMap.get(key);
                ForkJoinThread forkJoinThread = new ForkJoinThread(listData);
                forkJoinThread.fork();
                long result = forkJoinThread.join();
                System.out.println("result="+result);
                sum += result;
            }
            return sum;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            for(Integer num : listData){
                sum += num;
            }
            return sum;
        }

    }


}
