package com.algo.beginner.math;

import java.util.HashSet;
import java.util.Set;

/**
 * 难度级别: Beginner
 * 分类: Math
 *
 * @author liangjun
 **/
public class BitOperationSolution {
    /**
     * 67. 二进制求和
     * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
     * 输入:a = "11", b = "1"
     * 输出："100"
     *
     * @param a 第一个二进制字符串
     * @param b 第二个二进制字符串
     * @return a + b 的二进制字符串
     */
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;
        // a/b 没有遍历完毕，或者有进位
        while (i >= 0 || j >= 0 || carry > 0) {
            // 计算每一位上的A B 数字
            int digitA = i >= 0 ? a.charAt(i) - '0' : 0;
            int digitB = j >= 0 ? b.charAt(j) - '0' : 0;
            int sum = digitA + digitB + carry;
            sb.append(sum % 2);
            carry = sum / 2;
            // 移动指针
            i--;
            j--;
        }
        return sb.reverse().toString();
    }

    /**
     * 190. 颠倒二进制位
     * 类似于从一个队列的尾部 1 2 3 poll，然后 add 到另一个队列 3 2 1，实现翻转
     * <p>
     * 位运算操作：
     * & 1：获取最低位（0或1）
     * << 1：左移，为下一位腾位置
     * >> 1：右移，处理下一位
     * |：按位或，添加新位
     *
     * @param n 整数
     * @return 二进制翻转之后的整数
     */
    public int reverseBits(int n) {
        int res = 0;
        // n 的末尾加入到 res 的尾部，res 左移、n 右移，使得 n 翻转为 res
        for (int i = 0; i < 32; i++) {
            // 取n的最后一位 n & 1；res 左移一位 res << 1；将最后一位添加到res；
            res = res << 1 | (n & 1);
            // n 右移一位
            n >>= 1;
        }
        return res;
    }

    /**
     * 191. 位1的个数
     * <p>
     * 输入：n = 11
     * 输出：3
     * 解释：输入的二进制串 1011 中，共有 3 个设置位。
     *
     * @param n 正整数
     * @return 位1的个数
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count += n & 1;
            n >>= 1;
        }
        return count;
    }

    /**
     * 136. 只出现一次的数字
     * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * @param nums 数组
     * @return 只出现一次的数字
     */
    public int singleNumber(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }
        if (set.size() == 0) {
            return Integer.MIN_VALUE;
        } else {
            return set.iterator().next();
        }
    }

    /**
     * 任何数与0异或是本身，与本身异或是0
     * 异或运算满足交换律和结合律，即 a⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=b。
     */
    public int singleNumberBitOp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }
        int singleNum = 0;
        for (int num : nums) {
            // 任何数与0异或是本身，与本身异或是0，10^20^10 = 10^10^20 = 0^20 = 20，所以 20 是只出现一次的数
            singleNum ^= num;
        }
        return singleNum;
    }
}
