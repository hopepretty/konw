package com.hanweb;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8内置的四大核心函数式接口
 *
 * 1、Consumer<T>：消费型接口（有去无回，对参数进行处理就行）
 *     void accept(T t)
 * 2、Supplier<T>：供给型接口
 *     T get();
 * 3、Function<T，R>：函数型接口（对参数进行处理后，返回我们自己的结果）
 *      R apply(T t);
 * 4、Predicate<T>：断言型接口
 *     boolean test(T t);
 * @author pc
 */
public class TestLamda1 {

    /**
     * 消费型接口使用
     */
    @Test
    public void consumer() {
        happy(10, a -> System.out.println("消费：" + a));
    }

    public void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    /**
     * 供给型,内部调get方法获取我们返回的数据
     */
    @Test
    public void supplier() {
        //产生n个数，产生的这n个数由我自己指定
        List<Integer> num = getNum(10, () -> (int) (Math.random() * 100));
        num.forEach(System.out::println);
    }

    public List<Integer> getNum(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 函数式处理
     */
    @Test
    public void function() {
        String value = strHandler("你好呀", (a) -> a.substring(0, 1));
        System.out.println(value);
    }

    public String strHandler(String str, Function<String, String> function) {
        return function.apply(str);
    }

    /**
     * 断言
     */
    @Test
    public void predicate() {
        List<String> strings = filterStr(Arrays.asList("aa", "bbbb", "cccc", "dddddd"), a -> a.length() > 2);
        strings.forEach(System.out::println);
    }

    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> newList = new ArrayList<>();
        list.forEach((a) -> {
            if (predicate.test(a)) {
                newList.add(a);
            }
        });
        return newList;
    }

}
