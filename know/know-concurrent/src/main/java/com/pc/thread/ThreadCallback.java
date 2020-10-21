package com.pc.thread;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 回调任务测试类
 */
public class ThreadCallback implements Callable<String> {
    private int id;

    public ThreadCallback(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        //测试isDone()方法使用
        if (id == 8) {
            Thread.sleep(2000);
        }
        return "result of TaskWithResult " + id;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<String>> futures = new ArrayList<>();

        //塞任务
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(new ThreadCallback(i)));
        }

        //获取线程任务执行结果
        for (Future<String> future : futures) {
            try {
                //查看任务是否执行完成
//                if (future.isDone()) {
//                    System.out.println(future.get());
//                } else {
//                    System.out.println("任务正在执行");
//                }
                //如果当前任务正在执行，则会进入阻塞状态，直到任务执行完毕
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        }
    }
}
