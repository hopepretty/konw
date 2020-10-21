package com.pc.stack;

/**
 * 栈结构
 * @author pc
 * @Date 2020/7/15
 **/
public class LinkedStack<T> {

    /**
     * 当前节点，即栈顶节点
     */
    private Node<T> head;

    /**
     * 栈大小
     */
    private int size;

    /**
     * 栈最大存储多少个节点
     */
    private int max;

    public LinkedStack(int max) {
        this.max = max;
    }

    /**
     * 压栈
     * @param data
     * @return
     */
    public boolean push(T data) {
        if (size == max) {
            throw new RuntimeException("栈内存溢出");
        }
        head = new Node<T>(data, head);
        size ++;
        return true;
    }

    /**
     * 出栈
     * @return
     */
    public T pop() {
        if (head == null) {
            return null;
        }
        T data = head.data;
        head = head.next;
        size --;
        return data;
    }

    /**
     * 查询栈顶元素
     * @return
     */
    public T peek() {
        if (head == null) {
            return null;
        }
        return head.data;
    }

    class Node<T> {

        private T data;

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

}
