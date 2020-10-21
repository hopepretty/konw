package com.pc.thread;

/**
 *
 *
 * @author pc
 */
public class ThreadYieldLiftOff implements Runnable {

    protected int countDown = 10;

    private static int taskCount = 0;

    /**
     * 区分任务的多个实例
     */
    private final int id = taskCount ++;

    public ThreadYieldLiftOff(int countDown) {
        this.countDown = countDown;
    }

    /**
     * 获取线程状态
     * @return
     */
    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "Loftoff!") + "),";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            //对线程调度器的一种建议（java线程机制的一部分，可以将CPU从一个线程转移给另一个线程）
            //它在声明：我已经执行完生命周期中最重要的部分了，此刻正是切换给其他任务执行一段时间的大好时机
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new ThreadYieldLiftOff(10));
            t.start();
        }
        System.out.println("Waiting for liftoff");
    }
}
