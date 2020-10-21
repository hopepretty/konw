package com.pc.juc1;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 计数器使用
 *
 *  它们就像是屏障
 *
 *  countDownLath：当达到设置的计数时，执行相关的线程
 *  cyclicBarrier：当阻塞的线程数达到指定数的时候才继续往下走
 * @author pc
 * @Date 2020/7/10
 **/
public class CountDownLatchAndCyclicBarrier {

    /**
     * 模拟高并发,设置1000为临界点 也就是模拟了1000个并发
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1000);

    /**
     * 当1000个并发线程执行完之后执行主线程,注意了这里要加1 因为还有个主线程
     */
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(1001);

    /**
     * 小锁
     */
    private static final Object LOCK = new Object();

    /**
     * 一千人秒杀
     */
    private static final int USER_NUM = 1000;

    /**
     * 总共多少库存
     */
    private static int goods_left = 100;

    /**
     * 成功多少人
     */
    private static int successPerson = 0;

    /**
     * 卖出多少
     */
    private static int saleOutNum = 0;

    public static void main(String[] args) {
        Instant now = Instant.now();
        //模拟出一千个用户并发
        for (int i = 1; i <= USER_NUM; i++) {
            new Thread(new UserRequest(i)).start();
            if (i == USER_NUM) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //减一，当减为0时将释放所有阻塞的线程
            countDownLatch.countDown();
        }
        System.out.println("耗时：" + Duration.between(now, Instant.now()).getSeconds());
        try {
//            Thread.sleep(2000);
            cyclicBarrier.await();//换成cyclicBarrier阻塞当前线程（阻塞）
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功购买商品的顾客人数：" + successPerson);
        System.out.println("已售出商品个数：" + saleOutNum);
        System.out.println("剩余商品个数为：" + goods_left);
    }

    /**
     * 用户请求
     */
    public static class UserRequest implements Runnable {

        private boolean flag = false;

        private int i = 0;

        public UserRequest(int i) {
            i = i;
        }
        
        @Override
        public void run() {
            try {
                //所有的子线程运行到此处后进行阻塞，等待发令枪指令
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //先判断是否有库存，然后再进行加锁
            if (goods_left > 0) {
                synchronized(LOCK) {
                    flag = true;
                    //如果商品售空，则返回警告提示
                    if (goods_left > 0) {
                        successPerson ++;
                        goods_left --;
                        saleOutNum ++;
                    }
                }
            }
            try {
                //告诉计数器，我已经完成了
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
