package com.song.export.util.thread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author lakeslove
 * 多线程累加求和
 *
 */
public class SumCalculator{
    private class Sum implements Callable<Integer>{
        private int subMin;
        private int subMax;
        public Sum(int subMin,int subMax){
            this.subMin = subMin;
            this.subMax = subMax;
        }
        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for(int i = subMin;i <= subMax;i++){
                sum += i;
                System.out.println(Thread.currentThread().getName() + "-------" + sum);
            }
            return sum;
        }
    }

    /**
     * 求和范围是 min ~ max
     * @param min
     * @param max
     * @param threadNum
     * @return
     */
    public Integer getSum(int min, int max, int threadNum){
        int subMin;
        int subMax;
        List<FutureTask<Integer>> taskList = new ArrayList<>();
        int sumCounts = max - min + 1;
        int subCounts = sumCounts/threadNum;
        int remainder = sumCounts%threadNum;
        int mark = min;
        for(int i = 0;i<threadNum;i++){
            subMin = mark;
            if(remainder!=0&&remainder>i){
                subMax = subMin + subCounts;
            }else{
                subMax = mark + subCounts - 1;
            }
            mark = subMax + 1;
            System.out.println(subMin +":"+subMax);
            FutureTask<Integer> task = new FutureTask<Integer>(new Sum(subMin,subMax));
            taskList.add(task);
            new Thread(task).start();
        }
        int sum = taskListSum(taskList);
        return sum;
    }

    private Integer taskListSum(List<FutureTask<Integer>> taskList){
        int sum = 0;
        for(FutureTask<Integer> task : taskList){
            try {
                sum += task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return     sum;
    }


    /**
     * @param args
     * 测试
     */
    public static void main(String[] args){
        SumCalculator sumCalculator = new SumCalculator();
        int sum = sumCalculator.getSum(1, 100, 5);
        System.out.println(sum);
    }

}