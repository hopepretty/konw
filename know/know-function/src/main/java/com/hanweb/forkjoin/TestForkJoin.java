package com.hanweb.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 测试
 */
public class TestForkJoin {

    /**
     * fork/join框架用起来并不是很友好
     */
    @Test
    public void test1() {
        Instant start = Instant.now();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinCalculate(0L,100000000L);
        Instant end = Instant.now();
        System.out.println(forkJoinPool.invoke(forkJoinTask));
        System.out.println("耗时：" + Duration.between(start, end).toMillis());
    }

    /**
     * 提供号的工具类
     */
    @Test
    public void test2() {
        Instant start = Instant.now();
        System.out.println(LongStream.range(0, 100000000).parallel().reduce(0, Long::sum));
        Instant end = Instant.now();
        System.out.println("耗时： " + Duration.between(start, end));
    }

}
