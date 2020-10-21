package com.hanweb.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 最终操作
 * @author pc
 */
public class TestStream1 {

    /**
     * 查找与匹配
     * allmatch 所有都要匹配
     * anymatch 匹配一个即可
     * nonematch 一个都没匹配
     * findfirst 返回第一个元素
     * findany 获取任意一个元素
     * count 元素个数
     * max 最大
     * min 最小
     */
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(9,5,2,99,6,7,8);
        //allmatch
        boolean b = list.stream().allMatch((a) -> a >= 1);
        System.out.println(b);
        //anymatch
        System.out.println(list.stream().anyMatch((a) -> a > 7));
        //nonematch 与anymatch相反
        System.out.println(list.stream().noneMatch((a) -> a > 7));
        //findfirst
        Optional<Integer> first = list.stream().sorted(Integer::compareTo).findFirst();
        System.out.println(first.get());
        //findany
        //max  比如我们要取出某个实体内它的成员变量是个实体，我们首先要进行map转化，然后进行过滤
    }

    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator)
     * 就是将流中的数据反复结合起来，得到一个值
     *
     */
    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(9,5,2,99,6,7,8);

        /**
         * 流程
         * 首先将 初始值10当作x,然后取出流中的9当作y，得到19，再将19当作x，取出流中的5当作y，依次类推
         */
        Integer reduce = list.stream().reduce(10, (x, y) -> x + y);
        System.out.println(reduce);
    }

    List<Employee> employees = Arrays.asList(
            new Employee("部门一", "张三", 1000),
            new Employee("部门二", "刘德华", 1000),
            new Employee("部门一", "李四", 5465),
            new Employee("部门二", "赵六", 6554),
            new Employee("部门三", "王五", 2323)
    );

    /**
     * 收集器
     * collect(Collector c) 将流转换成其他形式，接收一个Collector接口的实现，用于给Stream中的元素做汇总的方法
     *
     * Collector接口中方法的实现决定了如何对流执行收集操作（收集到 list、set、map）
     * 但是Collectors实现类提供了很多静态方法，可以方便的创建常见收集器实例
     */
    @Test
    public void test3() {

        //收集到set中
        List<Integer> list = Arrays.asList(9,5,2,99,9,7,8);
        Set<Integer> collect = list.stream().collect(Collectors.toSet());
        System.out.println(collect);

        //手动指定放到什么集合
        HashSet<Integer> collect1 = list.stream().collect(Collectors.toCollection(HashSet::new));
        System.out.println(collect1);

        //总数  这是针对于集合元素的个数的
        System.out.println(list.stream().collect(Collectors.counting()));

        //求平均值
        System.out.println(list.stream().collect(Collectors.averagingInt((a) -> a)));

        //总和
        Double collect2 = employees.stream().map(Employee::getGz).collect(Collectors.summingDouble(Double::doubleValue));
        System.out.println(collect2);
        System.out.println(list.stream().collect(Collectors.summingInt(Integer::intValue)));

        //最大值，先进行排序
        System.out.println(list.stream().collect(Collectors.maxBy(Integer::compareTo)));

        //分组
        Map<String, List<Employee>> groups = employees.stream().collect(Collectors.groupingBy(Employee::getGroup));
        System.out.println(groups);

        //多级分组
        Map<String, Map<Double, List<Employee>>> collect3 = employees.stream().collect(Collectors.groupingBy(Employee::getGroup, Collectors.groupingBy(Employee::getGz)));
        System.out.println(collect3);

        //分区或分片
        Map<Boolean, List<Employee>> collect4 = employees.stream().collect(Collectors.partitioningBy((a) -> a.getGz() > 2000));
        System.out.println(collect4);

        //获取summary
        DoubleSummaryStatistics dss = employees.stream().collect(Collectors.summarizingDouble(Employee::getGz));
        System.out.println(dss.getAverage());
        System.out.println(dss.getMax());

        //连接对象内的字符串
        String collect5 = employees.stream().map(Employee::getName)
                .collect(Collectors.joining(",", "====", "==="));
        System.out.println(collect5);
    }

    /**
     * 练习
     */
    @Test
    public void test7() {
        //给定  1，2，3，4，5  返回由每个数的平方组成的列表
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        System.out.println(list.stream().map((a) -> a * a).collect(Collectors.toList()));

        //使用map与reduce去看employee的数量
        Optional<Integer> reduce = employees.stream().map((a) -> 1).reduce(Integer::sum);
        System.out.println(reduce);
    }

    class Employee{
        private String group;

        private String name;

        private double gz;

        public Employee(String group, String name, double gz) {
            this.group = group;
            this.name = name;
            this.gz = gz;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getGz() {
            return gz;
        }

        public void setGz(double gz) {
            this.gz = gz;
        }
    }

}
