package com.pc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * java8新特性
 * 安全不可变的对象
 */
public class TestNewTime {

    public static void main(String[] args) throws Exception {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        /**
         * 创建一个带返回值的线程任务
         */
        Callable<LocalDate> task = () -> LocalDate.parse("20160707", dtf);

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
