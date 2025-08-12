package com.algo.advanced.advanced;

import com.algo.common.ListNode;
import com.algo.common.TreeNode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
/**
 * 难度级别: Advanced
 * 分类: Advanced
 * 
 * @author liangjun
 **/
public class DivideSolution {

    //    @Test
    public void testBinarySearch() {
        int target = 11;
        int expected = 6;
        int[] nums = new int[]{1, 2, 4, 6, 8, 10, 11, 19};
        int actual = binarySearchDfs(nums, target);
        System.out.println(actual);
//        Assert.assertEquals(expected, actual);
    }

    //    @Test
    public void testHanota() {
        Deque<Integer> source = new LinkedList<>();
        Deque<Integer> buf = new LinkedList<>();
        Deque<Integer> target = new LinkedList<>();
        int n = 5;
        for (int i = 1; i <= n; i++) {
            source.add(i);
        }
        solveHanota(n, source, buf, target);
        System.out.println(Arrays.toString(target.toArray()));
//        Assert.assertNotNull(target);
    }

    public void solveHanota(int n, Deque<Integer> source, Deque<Integer> buf, Deque<Integer> target) {
        // 设置终止条件，只剩一个盘子直接移动 source -> target
        if (n == 1) {
            // 取最顶层的盘子移动到目标集合
            target.add(source.pollLast());
            return;
        }
        // f(n - 1) source -> buf
        solveHanota(n - 1, source, target, buf);
        // f(n) : source -> target
        target.add(source.pollLast());
        System.out.println();
        // f(n - 1) buf -> target
        solveHanota(n - 1, buf, source, target);
    }

    /**
     * deep-first search
     */
    public int binarySearchDfs(int[] nums, int target) {
        return binarySearchDfs(nums, target, 0, nums.length - 1);
    }

    static int binarySearchDfs(int[] nums, int target, int left, int right) {
        // 设置终止条件
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (target == nums[mid]) {
            // 找到目标
            return mid;
        } else if (target > nums[mid]) {
            // target 在右侧区间
            return binarySearchDfs(nums, target, mid + 1, right);
        } else {
            // target 在左侧区间
            return binarySearchDfs(nums, target, left, mid - 1);
        }
    }

    /**
     * common binary search
     */
    public int binarySearch(int[] nums, int target) {
        // 初始化区间 [i, j]
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            // 计算中点索引 m
            int m = i + (j - i) / 2;
            // target 在中点右侧 i = m + 1
            if (nums[m] < target) {
                i = m + 1;
            } else if (nums[m] > target) {
                // target 在中点左侧 j = m -1
                j = m - 1;
            } else {
                // 命中 target 返回索引
                return m;
            }
        }
        // 未命中返回 -1
        return -1;
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
     * <p>
     * 我们可以用 分治 + 递归 的方式来构建树。
     * <p>
     * 步骤：
     * 1.	找到数组的中间元素作为根节点；
     * 2.	左边部分构建左子树，右边部分构建右子树；
     * 3.	递归处理左右两边的子数组。
     * 这样就可以保证平衡。
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return null;
        }
        return this.dfs(nums, 0, nums.length - 1);
    }

    private TreeNode dfs(int[] nums, int left, int right) {
        // 设置终止条件
        if (left > right) {
            return null;
        }
        // 数组中间元素作为根节点
        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);

        // 构建左子树及右子树
        node.left = dfs(nums, left, mid - 1);
        node.right = dfs(nums, mid + 1, right);

        return node;
    }

    /**
     * 148. 排序链表
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表。
     * <p>
     * 1️⃣ 找到链表中点
     * •	使用 快慢指针 (slow 和 fast) 找到 链表的中间节点。
     * •	prev.next = null; 断开链表，分成左右两部分。
     * <p>
     * 2️⃣ 递归排序
     * •	递归调用 sortList(head) 处理左右子链表。
     * <p>
     * 3️⃣ 归并两个有序链表
     * •	使用 merge() 合并两个有序链表，类似 合并两个有序数组。
     *
     * @param head 链表
     * @return 排序后的链表
     */
    public ListNode sortList(ListNode head) {
        // 处理边界
        if (head == null || head.next == null) {
            return head;
        }
        // 快慢指针找到链表中间节点
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // 断开链表
        prev.next = null;

        // 递归排序
        ListNode left = sortList(head);
        ListNode right = sortList(slow);

        // 归并两个有序链表
        return merge(left, right);
    }


    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        while (left != null && right != null) {
            // 合并有序链表
            if (left.val < right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            // 移动当前指针
            curr = curr.next;
        }

        // 处理没有遍历完的链表
        curr.next = (left != null) ? left : right;
        return dummy.next;
    }

}
