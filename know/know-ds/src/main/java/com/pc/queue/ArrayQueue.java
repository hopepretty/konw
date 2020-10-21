package com.pc.queue;

/**
 * 循环队列的实现
 * @author pc
 * @Date 2020/7/15
 **/
public class ArrayQueue<T> {

    //定义容量为10的队列
    private Object[] array = null;

    /**
     * 队首
     */
    private int front = 0;

    /**
     * 队尾
     */
    private int rear = 0;

    /**
     * 初始队列大小
     * @param capacity
     */
    public ArrayQueue(int capacity) {
        array = new Object[capacity];
    }

    /**
     * 入队列
     * @param obj
     * @return
     */
    public boolean enQueue(T obj) {
        if ((rear + 1) % array.length == front) {
            return false;
        }
        array[rear] = obj;
        rear = (rear + 1) % array.length;
        return true;
    }

    /**
     * 出队列
     * @return
     */
    @SuppressWarnings("unchecked")
    public T deQueue() {
        if (front == rear) {
            return null;
        }
        T object = (T) array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        return object;
    }

    public static void main(String[] args) {
        ArrayQueue<String> queueCyclic = new ArrayQueue<String>(15);
        System.out.println(queueCyclic.deQueue());
        System.out.println(queueCyclic.enQueue("222"));
        System.out.println(queueCyclic.deQueue());
    }

}
