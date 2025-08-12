package com.algo.intermediate.linkedlist;

import java.util.*;
/**
 * 难度级别: Intermediate
 * 分类: Linkedlist
 * 
 * @author liangjun
 **/
public class LinkedListSolution {
    /**
     * 141. 环形链表
     * 方法1：map 记录已经遍历过的点
     * 方法2：快慢指针，最终相遇，说明有环（类似于操场跑圈套圈现象）
     */
    public boolean hasCycle(ListNode head) {
        // 处理边界
        if (head == null || head.next == null) {
            return false;
        }
        // 定义快慢指针
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指针相遇，则返回 true
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2. 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 处理边界
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        // 定义哑结点
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        // 定义是否进位
        boolean carry = false;

        // 遍历两个链表
        while (l1 != null || l2 != null) {
            // 定义当前和（两个节点和）
            int sum = 0;
            // 计算当前和并移动链表
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            // 处理进位
            if (carry) {
                sum += 1;
            }

            // 设置哑结点 next 为当前和%10，并移动当前节点
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            // 判断是否要进位
            carry = sum >= 10 ? true : false;
        }

        //检查最后一位是否进位
        if (carry) {
            curr.next = new ListNode(1);
        }
        // 返回哑结点的 next
        return dummy.next;
    }

    /**
     * 21. 合并两个有序链表
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 处理边界
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        // 声明哑结点
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        // 合并有序链表
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            // 移动当前节点指针
            curr = curr.next;
        }
        curr.next = list1 == null ? list2 : list1;
        return dummy.next;
    }

    /**
     * 92. 反转链表 II
     * <p>
     * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
     * 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
     * <p>
     * 方法一，用preNode和rightNode定位节点，然后截断并翻转指定区间，再重新拼接链表。
     * 方法二，穿针引线法：
     * 先将 curr 的下一个节点记录为 next；
     * 执行操作 ①：把 curr 的下一个节点指向 next 的下一个节点；
     * 执行操作 ②：把 next 的下一个节点指向 pre 的下一个节点；
     * 执行操作 ③：把 pre 的下一个节点指向 next。
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 处理边界
        if (head == null || left == right) {
            return head;
        }
        // 声明哑结点，并初始化
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        // 找到 left 的前置节点
        ListNode prev = dummy;
        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }
        ListNode curr = prev.next;
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = curr.next;
            // curr 指向 next.next
            curr.next = next.next;
            // next 指向 prev.next
            next.next = prev.next;
            // prev 指向 next
            prev.next = next;
        }
        return dummy.next;
    }

    private void reverseListNode(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            // 移动 pre curr 指针
            pre = curr;
            curr = next;
        }
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     * <p>
     * 方法一：两次遍历
     * <p>
     * 方法二：
     * 1.	初始化一个虚拟节点 dummy 指向链表头，用于处理边界情况（如删除第一个节点）。
     * 2.	设置两个指针 fast 和 slow 都指向 dummy。
     * 3.	先将 fast 指针向前移动  n + 1  步，使得 fast 和 slow 间距为  n 。
     * 4.	同时移动 fast 和 slow 直到 fast 到达链表末尾。
     * 5.	slow 的下一个节点即为倒数第  n  个节点，将其删除。
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 处理边界
        if (head == null || n == 0) {
            return head;
        }
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        ListNode fast = dummy;
        // 快慢指针保证n个节点距离
        for (int i = 0; i < n + 1; i++) {
            fast = fast.next;
        }
        // 同步移动快慢指针，直到快指针到达尾部
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // 删除倒数第N个节点
        slow.next = slow.next.next;
        return dummy.next;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     * <p>
     * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
     * 双指针，
     * 1. 快指针判断是否重复
     * 2. 重复则跳过所有重复元素，slow.next = fast.next
     * 3. 不重复则移动慢指针 slow = slow.next
     * 4. 始终移动快指针 fast
     */
    public ListNode deleteDuplicates(ListNode head) {
        // 处理边界
        if (head == null || head.next == null) {
            return head;
        }

        // 定义哑结点和快慢指针
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            // 存在相同元素
            if (fast.val == fast.next.val) {
                // 跳过相同元素
                while (fast.next != null && fast.val == fast.next.val) {
                    fast = fast.next;
                }
                // 移动慢指针下个节点到 fast 下个节点，跳过相同元素
                slow.next = fast.next;
            } else {
                // 不存在相同元素，移动慢指针到下个节点
                slow = slow.next;
            }

            // 始终移动快指针
            fast = fast.next;
        }

        return dummy.next;
    }

    /**
     * 61. 旋转链表
     * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
     * <p>
     * 输入：head = [1,2,3,4,5], k = 2
     * 输出：[4,5,1,2,3]
     * <p>
     * 1.	计算链表长度：遍历链表来获取其长度  len ，这可以帮助确定旋转的最终位置。
     * 2.	减少旋转次数：由于向右移动  k  次和向右移动  k%len  次效果相同，我们可以对  k  进行取模运算来简化操作。
     * 3.	找到新的尾节点和头节点：新头节点在链表中的位置是从链表尾部倒数第  k%len  个节点处。然后，将该节点的上一个节点设置为新的尾节点。
     * 4.	断链并重连：新尾节点的 next 设为 null，并将旧尾节点连接到旧的头部形成旋转。
     */
    public ListNode rotateRight(ListNode head, int k) {
        // 处理边界
        if (head == null || head.next == null) {
            return head;
        }

        // 遍历链表获取长度，并得到尾节点
        int len = 1;
        ListNode tail = head;
        while (tail.next != null) {
            len++;
            tail = tail.next;
        }

        // 处理 k 大于链表长度的情况
        k %= len;
        // k == 0，无需旋转
        if (k == 0) {
            return head;
        }

        // 获取新尾节点
        ListNode newTail = head;
        for (int i = 0; i < len - k - 1; i++) {
            newTail = newTail.next;
        }
        // 获取新头结点，是新尾结点的下一个节点
        ListNode newHead = newTail.next;

        // 断链重连，新尾结点置空，原尾结点指向原头结点
        newTail.next = null;
        tail.next = head;

        // 返回新头结点
        return newHead;
    }

    /**
     * 86. 分隔链表
     * <p>
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     * 你应当 保留 两个分区中每个节点的初始相对位置。
     * <p>
     * 输入：head = [1,4,3,2,5,2], x = 3
     * 输出：[1,2,2,4,3,5]
     * <p>
     * 分隔链表，穿针引线法：[1,4,3,2,5,2]
     */

    public ListNode partition(ListNode head, int x) {
        // 处理边界
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1, head);
        // 找到第一个大于等于 x 的节点(prev = 1)
        ListNode prev = dummy;
        while (prev.next != null && prev.next.val < x) {
            prev = prev.next;
        }
        // 遍历剩余节点(curr = 4)
        ListNode curr = prev.next;
        while (curr != null && curr.next != null) {
            ListNode next = curr.next;
            // 下个节点值小于 x
            if (next.val < x) {
                // 开始穿针引线 curr = 3; next = 2; prev = 1
                // 当前节点移除 next
                curr.next = next.next;
                // 插入 next 到 prev 之后
                next.next = prev.next;
                prev.next = next;

                // 移动 prev 节点，指向最新的小于 x 的节点
                prev = next;
            } else {
                // 下个节点值大于等于 x，则继续遍历
                curr = curr.next;
            }
        }
        return dummy.next;
    }

    /**
     * 160 相交链表
     * <p>
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     * <p>
     * •	pA 和 pB 遍历各自链表，当到达 null 时，切换到另一个链表。
     * •	如果有交点，pA 和 pB 会在交点相遇。
     * •	如果无交点，pA 和 pB 会同时为 null，退出循环。
     * <p>
     * 无交点为什么会同时为 null，退出循环？
     * •	A 长度 = m B 长度 = n
     * •	A 和 B 无交点
     * •	双指针遍历的路径
     * •	pA 走过 m + n 步 pB 走过 n + m 步
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        // 双指针
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }
        return pA;
    }

    /**
     * 234. 回文链表
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     * <p>
     * 1. 快慢指针找到中点
     * 2. 遍历慢指针剩余节点判断是否回文串
     */
    public boolean isPalindrome(ListNode head) {
        // 空连或单节点都是回文
        if (head == null || head.next == null) {
            return true;
        }
        LinkedList<Integer> stack = new LinkedList<>();
        // 快慢指针找到中点，并将前半部分节点加入到 stack
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }
        // 链表长度是奇数，跳过中间元素
        if (fast != null) {
            slow = slow.next;
        }

        // 链表后半部分与前半部分对比
        while (slow != null) {
            if (stack.pop() != slow.val) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    /**
     * 142. 环形链表 II
     * <p>
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * <p>
     * 不允许修改 链表。
     */
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return head;
            }
            set.add(head);
            head = head.next;
        }
        return null;
    }

    /**
     * 24. 两两交换链表中的节点
     * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
     * <p>
     * 使用 哑节点 + 指针遍历，在 prev -> a -> b -> next 这种结构中，每次交换 a 和 b，然后 prev 指向 b，继续循环。
     */
    public ListNode swapPairs(ListNode head) {
        // 处理边界
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy;
        while (prev.next != null && prev.next.next != null) {
            // 找到 a b prev 节点
            ListNode a = prev.next;
            ListNode b = a.next;
            ListNode next = b.next;

            // 交换 a b 节点
            prev.next = b;
            b.next = a;
            a.next = next;

            // 移动 prev
            prev = a;
        }
        return dummy.next;
    }
}


/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int x) {
        val = x;
        next = null;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class LRUCache {
    /**
     * 自定义双向链表
     */
    class LinkedNode {
        int key;
        int val;
        LinkedNode prev;
        LinkedNode next;

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
    LinkedNode head;
    LinkedNode tail;
    Map<Integer, LinkedNode> cache;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.head = new LinkedNode(-1, -1);
        this.tail = new LinkedNode(-1, -1);
        head.next = tail;
        tail.prev = head;
        cache = new HashMap<>(capacity * 2);
    }

    public int get(int key) {
        LinkedNode linkedNode = cache.get(key);
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
        LinkedNode linkedNode = cache.get(key);
        // 元素不存在
        if (linkedNode == null) {
            // 处理链表长度超过阈值
            if (size == capacity) {
                LinkedNode tail = this.tail.prev;
                cache.remove(tail.key);
                remove(tail);
                size--;
            }
            // 创建节点放入缓存并添加到链表头部
            linkedNode = new LinkedNode(key, value);
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
    private void add2Head(LinkedNode node) {
        // 处理当前节点
        node.prev = head;
        node.next = head.next;
        // 处理原有头节点
        head.next.prev = node;
        // 处理头哑结点
        head.next = node;

    }

    private void remove(LinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void remove2Head(LinkedNode node) {
        remove(node);
        add2Head(node);
    }
}
