package com.pc.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * list测试
 *
 * @author pc
 * @Date 2020/10/21
 **/
public class ListTest {

	public static void main(String[] args) {
		//listIterator使用
		listIterator();
	}

	private static void listIterator() {
		List<String> peoples = new ArrayList<>();
		peoples.add("aaa");
		peoples.add("bbb");
		peoples.add("ccc");
		peoples.add("ddd");

		//创建一个迭代器
		ListIterator<String> stringListIterator = peoples.listIterator();
		while (stringListIterator.hasNext()) {
			System.out.println("上一个元素索引：" + stringListIterator.previousIndex());
			System.out.println("当前元素：" + stringListIterator.next());
			System.out.println("上一个元素索引：" + stringListIterator.previousIndex());
			System.out.println("下一个元素索引：" + stringListIterator.nextIndex());
		}
		System.out.println();
		//往前循环回去
		while (stringListIterator.hasPrevious()) {
			System.out.println("当前元素值为：" + stringListIterator.previous());
		}
		System.out.println();
		//指定迭代的开始位置
		stringListIterator = peoples.listIterator(1);
		while (stringListIterator.hasNext()) {
			System.out.println("当前元素值：" + stringListIterator.next());
		}
	}

}
