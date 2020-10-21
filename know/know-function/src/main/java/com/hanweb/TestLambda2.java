package com.hanweb;

import org.junit.After;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * 方法引用：若lambda体中的内容有方法已经实现了，我们可以使用”方法引用“（可以理解为方法引用就是Lambda表达式的另外一种表现形式）
 * 主要有三种语法格式：
 *
 *  对象::实例方法名
 *
 *  类::静态方法名
 *
 *  类::实例方法名
 * @author pc
 */
public class TestLambda2 {

    /**
     * 1、如果函数类型Consumer消费类型，我们只要满足我们对象的实例方法是接收一个参数，并且无返回值就行
     */
    @Test
    public void testConsumer() {
        //第一种方式
        Consumer consumer = (x)-> System.out.println(x);
        //第二种方式
        PrintStream printStream = System.out;
        Consumer consumer1 = (x) -> printStream.println(x);
        //第三种：方法引用
        Consumer consumer2 = System.out::println;
        //自定义静态方法引用
        Consumer consumer3 = TestConsumer::accept;
        //实例方法引用
        Consumer consumer4 = new TestConsumer()::accept1;

        consumer2.accept("sss");
    }

    /**
     * 无参数，有返回值
     */
    @Test
    public void testSupplier() {
        Supplier<String> supplier = () -> "你好";
        System.out.println(supplier.get());

        //使用方法引用
        Person person = new Person();
        person.setName("小明");
        Supplier<String> supplier1 = () -> person.getName();
        //其实方法引用很简单，只要服务supplier的接口抽象方法定义的参数与返回值格式就可以使用方法引用
        //其实方法引用就是Lambda表达式的一种表现形式
        Supplier<String> supplier2 = person::getName;
        System.out.println(supplier2.get());
    }

    /**
     * 类静态方法引用
     * 其实就是  这个类的静态方法满足这个函数式接口内的抽象方法的参数与返回值格式类型
     * 可以直接使用类::静态方法代替Lambda表达式
     */
    @After
    public void test3() {
        //一般的Lambda表达式
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        //静态方法引用代替Lambda表达式
        Comparator<Integer> comparator1 = Integer::compare;
    }

    /**
     * 类::实例方法调用
     */
    @Test
    public void test4() {
        //断言型接口
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        //类实例方法调用
        //类实例方法调用有规则， 第一个参数为调用者，第二个参数是参数
        BiPredicate<String, String> biPredicate1 = String::equals;
    }

    /**
     * 构造器引用
     */
    @Test
    public void test5() {
        Supplier<Person> supplier = () -> new Person();
        //构造器引用
        //注意了，这里是调用的无参构造器，因为supplier的get抽象方法本身是无参的，是根据其类型进行隐式匹配的
        Supplier<Person> supplier1 = Person::new;
    }

    /**
     * 构造器引用
     */
    @Test
    public void test6() {
        //自动检测类型
        Function<String, Person> function = Person::new;
        BiFunction<String, String, Person> biFunction = Person::new;
    }

    /**
     * 数组引用
     */
    @Test
    public void test7() {
        Function<Integer, String[]> function = (x) -> new String[x];
        //数组引用
        Function<Integer, String[]> function1 = String[]::new;
    }

    /**
     * 测试用例
     */
    class Person {
        private String name;

        private String address;

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }

        public Person(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
