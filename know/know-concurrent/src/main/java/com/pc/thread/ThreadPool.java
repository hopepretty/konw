package com.pc.thread;

import java.util.concurrent.*;

/**
 * 测试二
 */
public class ThreadPool {

    public static void main(String[] args) {
        //无限线程池,预先不进行创建核心线程数
//        ExecutorService executorService = Executors.newCachedThreadPool();

        //就是线程数量为1的fixedThreadExecutor
        //如果向这个线程池推送多个任务，那么这些任务将排队，每个任务都会进行等待
        //所有的任务都会使用相同的线程
        //使用单任务可以不用考虑访问资源问题
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //指定线程池内的线程数（核心线程数与非核心线程数），预先创建好指定核心线程数，并且最大线程数就这么多
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //max = corePoolSize + other 下面最多接受 10 + 8 = 18个线程
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                10,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(8),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            executorService.execute(new ThreadYieldLiftOff(10));
        }
        //可以防止新任务被提交给这个Executor
        //当执行完此方法后，新任务就不能再继续提交了
        executorService.shutdown();
    }

}
