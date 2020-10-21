package com.pc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程安全操作
 */
public class TestConSimpleDateFormat {

    public static void main(String[] args) throws Exception {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        /**
         * 创建一个带返回值的线程任务
         */
        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20161209");
            }
        };

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < 10; i++) {
            list.add(executorService.submit(task));
        }
        for (Future future : list) {
            System.out.println(future.get());
        }

        executorService.shutdown();
    }

}
