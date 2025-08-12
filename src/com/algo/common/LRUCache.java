package com.algo.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liangjun
 **/
public class LRUCache {
    /**
     * 自定义双向链表
     */
    class LinkedNode {
        int key;
        int val;
        LRUCache.LinkedNode prev;
        LRUCache.LinkedNode next;

        public LinkedNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * 设置链表长度、容量，虚拟头尾节点
     */
    int size;
    int capacity;
    /**
     * 虚拟头尾节点(哑节点)
     */
    LRUCache.LinkedNode head;
    LRUCache.LinkedNode tail;
    Map<Integer, LRUCache.LinkedNode> cache;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.head = new LRUCache.LinkedNode(-1, -1);
        this.tail = new LRUCache.LinkedNode(-1, -1);
        head.next = tail;
        tail.prev = head;
        cache = new HashMap<>(capacity * 2);
    }

    public int get(int key) {
        LRUCache.LinkedNode linkedNode = cache.get(key);
        if (linkedNode == null) {
            return -1;
        }
        // 节点不在头部则移动
        if (linkedNode.prev != head) {
            remove2Head(linkedNode);
        }
        return linkedNode.val;
    }

    public void put(int key, int value) {
        LRUCache.LinkedNode linkedNode = cache.get(key);
        // 元素不存在
        if (linkedNode == null) {
            // 处理链表长度超过阈值
            if (size == capacity) {
                LRUCache.LinkedNode tail = this.tail.prev;
                cache.remove(tail.key);
                remove(tail);
                size--;
            }
            // 创建节点放入缓存并添加到链表头部
            linkedNode = new LRUCache.LinkedNode(key, value);
            cache.put(key, linkedNode);
            add2Head(linkedNode);
            // 链表长度++
            size++;
        } else {
            // 存在，则更新至并移动到链表头部
            linkedNode.val = value;
            remove2Head(linkedNode);
        }
    }

    /**
     * 将B 插入 A C 的头部，其中 A 是虚拟节点
     * b.prev = a; b.next = c
     * c.prev = b; a.next = b
     */
    private void add2Head(LRUCache.LinkedNode node) {
        // 处理当前节点
        node.prev = head;
        node.next = head.next;
        // 处理原有头节点
        head.next.prev = node;
        // 处理头哑结点
        head.next = node;

    }

    private void remove(LRUCache.LinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void remove2Head(LRUCache.LinkedNode node) {
        remove(node);
        add2Head(node);
    }
}
