package com.algo.beginner.math;
/**
 * 难度级别: Beginner
 * 分类: Math
 * 
 * @author liangjun
 **/
public class MathSolution {
    /**
     * 66. 加一
     * 我们从数组的末尾开始遍历，逐位加 1：
     * 1.	如果当前位加 1 后小于 10，则直接返回数组。
     * 2.	如果当前位加 1 后等于 10，则该位变为 0，并将进位传递到更高位。
     * 3.	如果遍历到最高位时仍有进位，则需要在数组前插入一个 1。
     *
     * @param digits 数组
     * @return 加一后的信数组
     */
    public int[] plusOne(int[] digits) {
        // 处理边界
        if (digits == null || digits.length == 0) {
            return new int[0];
        }
        // 从尾到头遍历数组，逐位加 1
        for (int i = digits.length - 1; i >= 0; i--) {
            // 加一取 10 的模（加一来自于进位或者初始加一）
            digits[i]++;
            digits[i] %= 10;
            // 如果当前位加 1 后小于 10(10取模是0，其他取模大于0)，则直接返回数组
            if (digits[i] > 0) {
                return digits;
            }
        }
        // 遍历到头部仍有进位，数组插入一个 1
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    /**
     * 172. 阶乘后的零
     * 尾随零的数量等于  n!  中因子 5 的数量，可以用以下方法高效计算：
     * 1.	初始化 count 为 0。
     * 2.	不断用  n  除以 5，累加商的值到 count。
     * 3.	当  n  小于 5 时停止。
     * 举例： 25!  中有  5, 10, 15, 20, 25  共 6 个因子 5（其中 25 提供两个因子 5）。
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }

    /**
     * 69. x 的平方根
     * 二分查找
     *
     * @param x 指定值
     * @return 平方根
     */
    public int mySqrt(int x) {
        int left = 1;
        int right = x;
        int res = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 计算目标值
            int target = x / mid;
            if (target == mid) {
                return mid;
            } else if (target < mid) {
                // 目标值在左侧
                right = mid - 1;
            } else {
                // 目标值在右侧
                left = mid + 1;
                // 记录左侧较小值，mid 不匹配时，返回 res
                res = mid;
            }
        }
        return res;
    }

    /**
     * 50. Pow(x, n)
     *
     * @param x 数值
     * @param n 次幂
     * @return 计算结果
     */
    public double myPow(double x, int n) {
        // 避免 -2 的 31 转换正整数超界
        long N = n;
        // N < 0，则取 -N 的倒数
        return N >= 0 ? this.quickMul(x, n) : 1.0 / this.quickMul(x, -n);
    }

    /**
     * result = y*y*x(奇数) or y*y
     * xxxxx -> xx * xx * x
     */
    private double quickMul(double x, int n) {
        // 设置终止条件
        if (n == 0) {
            return 1;
        }
        // 递归分治
        double y = quickMul(x, n / 2);
        // result = y*y*x(奇数) or y*y
        return n % 2 == 0 ? y * y : y * y * x;
    }
}
