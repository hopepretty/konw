package com.pc.queue;

/**
 * 简单链表
 * @author pengcheng
 * @date 2020/6/4 15:16
 */
public class LinkedQueue<T> {

    /**
     * 头节点，永远都指向下一个出队的节点，前面一步步移走的节点将会删除，gc会回收掉
     */
    private Node<T> head;

    /**
     * 尾节点，指向新加入的节点
     */
    private Node<T> tail;

    /**
     * 队列大小
     */
    private int size = 0;

    /**
     * 入队
     * @param data
     * @return
     */
    public boolean enQueue(T data) {
        Node<T> node = new Node<>(data, null);
        if (size == 0) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size ++;
        return true;
    }

    /**
     * 出队列
     * @return
     */
    public T deQueue() {
        if (size == 0) {
            return null;
        }
        T data = head.data;
        head = head.next;
        size --;
        return data;
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获取将要出队的元素，但不会删除
     * @return
     */
    public T peek() {
        if (head == null) {
            return null;
        }
        return head.data;
    }

    /**
     * 内部节点数据
     */
    class Node<T> {

        /**
         * 节点数据
         */
        private T data;

        /**
         * 指向下一个节点
         */
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LinkedQueue<String> linkedQueue = new LinkedQueue<>();
        System.out.println(linkedQueue.deQueue());
        System.out.println(linkedQueue.enQueue("你好"));
        System.out.println(linkedQueue.deQueue());
        System.out.println(linkedQueue.size);
    }

}
