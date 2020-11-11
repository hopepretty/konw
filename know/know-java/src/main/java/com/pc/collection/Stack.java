package com.pc.collection;

import java.util.LinkedList;

/**
 * 通过LinkedList实现栈结构
 *
 * @author pc
 * @Date 2020/10/21
 **/
public class Stack<T> {

	/**
	 * 缓存
	 */
	private LinkedList<T> cache = new LinkedList<>();

	/**
	 * 放进一个元素
	 *
	 * @param t
	 */
	public void push(T t) {
		cache.addFirst(t);
	}

	/**
	 * 检测元素
	 *
	 * @return
	 */
	public T peek() {
		return cache.getFirst();
	}

	/**
	 * 弹出一个元素
	 *
	 * @return
	 */
	public T pop() {
		return cache.removeFirst();
	}

	public boolean empty() {
		return cache.isEmpty();
	}

}
