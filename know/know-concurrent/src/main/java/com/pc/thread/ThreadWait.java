package com.pc.thread;

/**
 * 阻塞恢复线程
 */
public class ThreadWait extends Thread{

    //无意义
    private final Object lock = new Object();

    //标志线程阻塞情况
    private boolean pause = false;

    /**
     * 设置线程是否阻塞
     */
    public void pauseThread() {
        this.pause = true;
    }

    /**
     * 调用该方法实现恢复线程的运行
     */
    public void resumeThread() {
        this.pause = false;
        synchronized (lock) {
            //唤醒线程
            lock.notify();
        }
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                //线程 等待/阻塞
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        //标志线程开启
        //一直循环
        while (true) {
            if (pause) {
                //线程 阻塞/等待
                onPause();
            }
            try {
                //程序每50毫秒执行一次 值可更改
                Thread.sleep(50);
                System.out.println("我正在执行");
                //这里写你的代码 你的代码  你的代码  重要的事情说三遍
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        ThreadWait pauseThread = new ThreadWait();
        pauseThread.start();

        try {
            //让线程执行5秒钟进行终止
            Thread.sleep(5000);
            pauseThread.pauseThread();
            //终止5秒钟后，开启线程
            Thread.sleep(5000);
            pauseThread.resumeThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
