package com.hanweb.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream流三个操作步骤
 *
 * 1、创建一个流
 * 2、中间操作
 * 3、终止操作（终端操作）
 * @author pc
 */
public class TestStream {

    /**
     * 创建Stream
     */
    @Test
    public void test1() {
        //1、集合的方式创建liu
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        //2、通过Arrays中的静态方法创建一个数组的流
        String[] strings = new String[10];
        Stream<String> stream1 = Arrays.stream(strings);
        //3、通过Stream的of方法创建流
        Stream<String> stream2 = Stream.of("aa", "bb", "cc");
        //4、创建无限流
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 2);
        iterate.limit(10).forEach(System.out::println);
        //5、创建无限流
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    /***
     * 中间操作
     * 1、filter
     * 2、limit
     * 3、skip(n) -> 跳过  与limit互补
     * 4、distinct -> 筛选，通过流所生成的元素的hashCode()和equals去除重复元素
     */
    @Test
    public void test2() {
        //内部迭代 流就是内部迭代
    }

    /**
     * 映射
     * map:将元素转化成其他形式或者提取信息。接收一个函数作为参数，接收每个参数并将其映射成另外一个元素
     * flatMap: 接收一个函数作为参数，将流中的每个值换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test3() {
        //map映射
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
        list.stream().map((str) -> str.toUpperCase()).forEach(System.out::println);
        //map映射产生Stream对象
        Stream<Stream<Character>> streamStream = list.stream().map(TestStream::split);
        streamStream.forEach((sm) -> {
            sm.forEach(System.out::println);
        });
        //使用flatmap扁平化处理的方式处理上述问题
        list.stream().flatMap(TestStream::split).forEach(System.out::println);
    }

    /**
     * 获取流
     * @param str
     * @return
     */
    public static Stream<Character> split(String str) {
        List<Character> list = new ArrayList<>();
        //String转化成字符数组
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    public void testFx() {
        new TestFX<List>();
    }

    class TestFX<T extends Collection> {

    }

}
