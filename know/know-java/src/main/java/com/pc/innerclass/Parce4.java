package com.pc.innerclass;

/**
 * 测试内部类
 */
public class Parce4 {

    private class Conetents {
        public void test() {
            System.out.println("这是Contents中的方法");
        }
    }

    protected class PDestioation {
        public void test() {
            System.out.println("这是PDestioation中的方法");
        }
    }

    /**
     * 我们可以在外部类中封装外部对内部类的访问，
     * 然后外部类提供获取实例方法进行对外部屏蔽内部类的创建与操作
     * @return
     */
    public Conetents getConetentsInstance() {
        return new Conetents();
    }

    public PDestioation getPDestioationInstance() {
        return new PDestioation();
    }

    public static void main(String[] args) {
        Parce4 parce4 = new Parce4();
        parce4.getConetentsInstance().test();
        parce4.getPDestioationInstance().test();
    }

}
