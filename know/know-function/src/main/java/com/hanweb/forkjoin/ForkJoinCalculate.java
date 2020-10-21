package com.hanweb.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 *
 * fork/join模式
 * 这个框架的效率高在当处理的数据量非常庞大的时候，一般的线程池无法满足需求
 * 传统的线程池中的线程在执行任务的时候，当一个线程在执行任务时，由于某些原因导致无法继续执行
 * 那么该线程会处于等待状态
 *
 * 对于fork/join来说
 * 子问题由于等待另外一个子问题的完成而无法继续运行，那么处理该子问题的线程会主动寻找
 * 其他尚未运行的子问题执行，这种方式减少了线程等待的时间，充分利用cpu，提高了性能
 *
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    /**
     * 起始值
     */
    private Long start;

    /**
     * 终止值
     */
    private Long end;

    /**
     *拆分的临界值
     * 这里我们一半一半的递归拆
     * 直到end - satrt 小于临界值就不需要进行拆分了
     */
    private static final Long LINJIEZHI = 100000L;

    public ForkJoinCalculate(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        //如果到达临界值，则进行业务处理
        //这里进行数字的累加
        if (length <= LINJIEZHI) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end)/2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1,end);

            //拆分子任务，同时压入线程队列
            left.fork();
            right.fork();
            return left.join() + right.join();
        }
    }
}
