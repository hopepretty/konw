package com.pc.innerclass;

/**
 * 测试内部类
 */
public class Parce5 {

    /**
     * 局部内部类必须去实现一个接口，然后所在方法返回此接口
     *
     * 这里注意，局部内部类不属于外部类
     */
    interface P {

        void test();

    }

    public P pDest() {
        class PDest implements P{
            public void test() {
                System.out.println("这是一个内部类方法中的局部内部类");
            }
        }
        return new PDest();
    }

    public static void main(String[] args) {
        Parce5 parce5 = new Parce5();
        parce5.pDest().test();
    }

}
