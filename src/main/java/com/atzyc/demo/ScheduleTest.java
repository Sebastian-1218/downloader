package com.atzyc.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTest {
    public static void main(String[] args) {
        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        //延时两秒后开始执行任务，每隔三秒在执行任务
        s.scheduleAtFixedRate(()->{
            System.out.println(System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2,3,TimeUnit.SECONDS);
    }
    public static void schedule(){
        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        //延时两秒后，执行任务
        s.schedule(()->System.out.println(Thread.currentThread().getName()),2, TimeUnit.SECONDS);
        s.shutdown();
    }
}
