package com.atzyc.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class poolTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = null;
        try {
            //创建线程池
            pool = new ThreadPoolExecutor(2, 3, 1, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<>(2));
            //创建线程任务
            Runnable r = ()->{
                System.out.println(Thread.currentThread().getName());
            };
            //线程池执行线程任务
            for (int i = 0; i < 5; i++) {
                pool.execute(r);
            }
        } finally {
            if(pool!=null){
                pool.shutdown();
                if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
                    pool.shutdownNow();
                }
            }
        }
    }
}
