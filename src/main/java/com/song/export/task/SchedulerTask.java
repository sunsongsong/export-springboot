package com.song.export.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

    private int count=0;

//    @Scheduled(cron="*/6 * * * * ?")
//    private void process(){
//        System.out.println("this is scheduler task runing  "+(count++));
//    }

    /**
     *  @Scheduled(fixedRate=2000)：上一次开始执行时间点后2秒再次执行；

        @Scheduled(fixedDelay=2000)：上一次执行完毕时间点后2秒再次执行；

        @Scheduled(initialDelay=1000, fixedDelay=2000)：第一次延迟1秒执行，然后在上一次执行完毕时间点后2秒再次执行；

        @Scheduled(cron="* * * * * ?")：按cron规则执行。
     */

}
