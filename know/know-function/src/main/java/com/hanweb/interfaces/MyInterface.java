package com.hanweb.interfaces;

/**
 * 接口
 */
public interface MyInterface {

    public static final String TTT = "你好";

    public void test();

    default void test2() {
        System.out.println("这是默认实现");
    }

    public static void test3() {
        System.out.println("这是一个静态方法");
    }

}
