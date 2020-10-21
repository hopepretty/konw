package com.pc.testfinal;

public class TestFinal2 extends TestFinal1 {

    /**
     * 不能被重写
     */
//    public void test() {
//
//    }

    public void test2() {
        //可以被调用
        super.test();
    }

    /**
     * 可以重写同包类下的方法
     */
    @Override
    protected void test3() {
        super.test3();
    }
}
