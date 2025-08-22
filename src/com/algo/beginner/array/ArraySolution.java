package com.algo.beginner.array;

import java.util.*;

/**
 * 难度级别: Beginner
 * 分类: Array
 * <p>
 * 保留算法类型：
 * - 数组操作算法 (Array Manipulation)
 * - 计数排序算法 (Counting Sort)
 * - 数据结构设计算法 (Data Structure Design)
 * - 模拟算法 (Simulation)
 *
 * @author liangjun
 **/
public class ArraySolution {

    /**
     * 189. 轮转数组 - 数组操作算法
     * <p>
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * <p>
     * 核心思想：通过三次翻转实现数组轮转
     * 1. 翻转整个数组前n-k个元素
     * 2. 翻转整个数组后k个元素
     * 3. 翻转整个数组
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入：nums = [1,2,3,4,5,6,7], k = 3
     * 输出：[5,6,7,1,2,3,4]
     */
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        // 1234 56 k=2 -> 56 1234
        k %= len;
        // 翻转[0, len- k -1] 4321 56
        this.reverse(nums, 0, len - k - 1);
        // 翻转[len- k, len-1] 4321 65
        reverse(nums, len - k, len - 1);
        // 整体翻转 56 1234 done
        reverse(nums, 0, len - 1);
    }

    /**
     * 数组翻转辅助方法 - 数组操作算法
     * <p>
     * 使用双指针从两端向中间交换元素
     * 时间复杂度：O(n)，空间复杂度：O(1)
     */
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 238. 除自身以外数组的乘积 - 数组操作算法
     * <p>
     * 给你一个整数数组 nums，返回 数组 answer，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
     * <p>
     * 核心思想：前缀乘积 × 后缀乘积
     * 1. 先计算每个位置左侧所有数字的乘积（前缀乘积）
     * 2. 再从右向左计算每个位置右侧所有数字的乘积（后缀乘积）
     * 3. 两者相乘得到最终结果
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1) - 不算输出数组
     * <p>
     * 输入：nums = [1,2,3,4]
     * 输出：[24,12,8,6]
     */
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        // 元素 i 最终结果
        int[] answer = new int[len];

        answer[0] = 1;
        // 计算前缀乘积，没有左边元素时，左侧第一个乘积为1
        for (int i = 1; i < len; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        // 计算后缀乘积，没有右边元素时，右侧第一个乘积为1
        int right = 1;
        for (int i = len - 1; i >= 0; i--) {
            answer[i] = answer[i] * right;
            // 更新后缀乘积
            right *= nums[i];
        }
        return answer;
    }

    /**
     * 274. H 指数 - 计数排序算法
     * <p>
     * 一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，并且 至少 有 h 篇论文被引用次数大于等于 h 。
     * 如果 h 有多种可能的值，h 指数 是其中最大的那个。
     * <p>
     * 核心思想：计数排序 + 后缀和
     * 1. 统计引用次数分布：统计每个引用次数对应的论文数量
     * 2. 从大到小查找：从最高引用次数开始，累加论文数量
     * 3. 找到临界点：当累加的论文数量≥当前引用次数时，即为H指数
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(n)
     * <p>
     * 输入：citations = [3,0,6,1,5]
     * 输出：3
     * 解释：有 3 篇论文每篇至少被引用了 3 次，其余两篇论文每篇被引用不多于 3 次，所以她的 h 指数是 3
     */
    public int hIndex(int[] citations) {
        int n = citations.length;
        // 下标是论文被引用次数，值是被引用i次的论文数量
        int[] counter = new int[n + 1];
        for (int c : citations) {
            // 次数大于n次，依然记录到n次上，其余则记录到c次上
            counter[Math.min(c, n)]++;
        }

        // 总数量
        int total = 0;
        // 从高到低查找最大的h值
        for (int r = n; r >= 0; r--) {
            // 累加论文数量
            total += counter[r];
            // 论文数量 >= 下标论文引用次数，则返回论文数量
            if (total >= r) {
                return r;
            }
        }
        return 0;
    }

    /**
     * 380. O(1) 时间插入、删除和获取随机元素 - 数据结构设计算法
     * <p>
     * 实现RandomizedSet 类：
     * - insert(val)：当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
     * - remove(val)：当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
     * - getRandom()：随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
     * <p>
     * 核心思想：HashMap + ArrayList 组合
     * 1. HashMap 存储 值→索引 的映射，实现O(1)查找
     * 2. ArrayList 存储实际值，实现O(1)随机访问
     * 3. 删除时将待删除元素与最后一个元素交换，避免数组移动
     * <p>
     * 时间复杂度：所有操作均为O(1)，空间复杂度：O(n)
     */
    class RandomizedSet {
        private List<Integer> nums;
        private Map<Integer, Integer> indices;
        private Random random;

        public RandomizedSet() {
            nums = new ArrayList<>();
            indices = new HashMap<>();
            random = new Random();
        }

        /**
         * 插入元素
         * 如果元素不存在，添加到ArrayList末尾并在HashMap中记录索引
         */
        public boolean insert(int val) {
            // 已经插入过，则返回false
            if (indices.containsKey(val)) {
                return false;
            }
            int index = nums.size();
            // 末尾添加元素
            nums.add(val);
            indices.put(val, index);
            return true;
        }

        /**
         * 删除元素
         * 将待删除元素与最后一个元素交换，然后删除最后一个元素
         */
        public boolean remove(int val) {
            if (!indices.containsKey(val)) {
                return false;
            }
            int index = indices.get(val);
            int last = nums.get(nums.size() - 1);
            // 将最后一个元素赋值到当前索引
            nums.set(index, last);
            indices.put(last, index);
            // 然后删除最后一个元素，避免数组移动
            nums.remove(nums.size() - 1);
            indices.remove(val);
            return true;
        }

        /**
         * 获取随机元素
         * 利用ArrayList的随机访问特性
         */
        public int getRandom() {
            int index = random.nextInt(nums.size());
            return nums.get(index);
        }
    }

    /**
     * 6. Z 字形变换 - 模拟算法
     * <p>
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     * <p>
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     * <p>
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * <p>
     * 核心思想：模拟Z字形变换过程
     * 1. 创建一个 StringBuilder 数组，每个 StringBuilder 代表一行
     * 2. 遍历字符串，将每个字符根据其应在的行号添加到相应的 StringBuilder 中
     * 3. 根据行号的变动，调整方向（从向下变为向上或从向上变为向下）
     * 4. 最后将每一行的字符拼接起来，得到结果字符串
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(n)
     * <p>
     * 输入：s = "PAYPALISHIRING", numRows = 3
     * 输出："PAHNAPLSIIGYIR"
     */
    public String convert(String s, int numRows) {
        // 处理边界值
        if (numRows <= 1 || numRows >= s.length()) {
            return s;
        }
        // 每行用一个 StringBuilder 存储字符
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        // 方向：初始值向上
        boolean goingDown = false;
        // 初始行号
        int currRow = 0;

        // 遍历字符
        for (char c : s.toCharArray()) {
            rows[currRow].append(c);
            // 首行或者尾行，调整方向
            if (currRow == 0 || currRow == numRows - 1) {
                goingDown = !goingDown;
            }
            // 计算下一个字符所在行，向下则加一，向上则减一
            currRow += goingDown ? 1 : -1;
        }

        // 拼接结果
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

}