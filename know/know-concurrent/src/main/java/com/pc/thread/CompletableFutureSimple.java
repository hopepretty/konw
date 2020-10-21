package com.pc.thread;

import java.util.concurrent.CompletableFuture;

/**
 * 异步 + 回调
 * @author pc
 * @Date 2020/10/18
 **/
public class CompletableFutureSimple {

    public static void main(String[] args) throws Exception {
        //无返回值
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("无返回值");
        });
        //获取结果
        voidCompletableFuture.get();

        //有返回值
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行任务");
            return 111;
        }).whenComplete((t, u) -> {
            //正常的返回结果
            System.out.println("t ->" + t);
            //异常返回的结果
            System.out.println("u ->" + u);
        }).exceptionally(f -> {
            //异常会走次函数
            f.getMessage();
            return 444;
        });
        System.out.println(completableFuture.get());
    }

}
