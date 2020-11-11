package com.pc.collection;

import java.util.LinkedList;

/**
 * 通过linkedList实现队列
 *
 *  当然完全可以将LinkedList向上转型为queue直接使用队列特性
 *
 * @author pc
 * @Date 2020/10/21
 **/
public class Queue<T> {

	/**
	 * 缓存
	 *
	 */
	private LinkedList<T> cache = new LinkedList<>();

	/**
	 * 推进一个元素
	 *
	 * @param t
	 */
	public void push(T t) {
		cache.addFirst(t);
	}

	/**
	 * 检测
	 *
	 * @return
	 */
	public T peek() {
		return cache.getLast();
	}

	/**
	 * 弹出
	 *
	 * @return
	 */
	public T pop() {
		return cache.removeLast();
	}

}
