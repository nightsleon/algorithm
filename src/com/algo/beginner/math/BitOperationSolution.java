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
     *
     * @param n 整数
     * @return 二进制翻转之后的整数
     */
    public int reverseBits(int n) {
        int res = 0;
        // n 的末尾加入到 res 的尾部，res 左移、n 右移，使得 n 翻转为 res
        for (int i = 0; i < 32; i++) {
            // 取n的最后一位 n & 1；res 左移一位 res << 1；
            res = res << 1 | (n & 1);
            // n 右移一位
            n >>= 1;
        }
        return res;
    }

    /**
     * 191. 位1的个数
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
            singleNum = singleNum ^ num;
        }
        return singleNum;
    }
}
