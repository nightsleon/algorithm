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
     * <p>
     * 给定一个表示 大整数 的整数数组 digits，其中 digits[i] 是整数的第 i 位数字。这些数字按从左到右，从最高位到最低位排列。这个大整数不包含任何前导 0。
     * 将大整数加 1，并返回结果的数字数组。
     * <p>
     * 输入：digits = [1,2,3]
     * 输出：[1,2,4]
     * <p>
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
     * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
     * <p>
     * 输入：n = 5
     * 输出：1
     * 解释：5! = 120 ，有一个尾随 0
     * <p>
     * 尾随零的数量 = 因子5的数量（因为2的因子总是比5多）：
     * 阶乘的尾随零（即末尾连续的 0），本质是由乘法中的 2×5=10 产生的 —— 每一对 “2 和 5” 的质因子相乘，就会贡献 1 个尾随零。
     * 例如：5! = 5×4×3×2×1 = (5)×(2×2)×3×(2)×1，其中包含 3 个 2 因子 和 1 个 5 因子，只能组成 1 对 “2×5”，因此尾随零数量为 1（与题目示例一致）。
     * 在 n! 的质因子分解中，2 的因子个数一定比 5 多。原因很简单：
     * 从 1 到 n 的整数中，偶数的数量远多于能被 5 整除的数（每 2 个数就有 1 个偶数，每 5 个数才有 1 个能被 5 整除的数）。
     * <p>
     * 尾随零的数量等于  n!  中因子 5 的数量，可以用以下方法高效计算：
     * 1.	初始化 count 为 0。
     * 2.	不断用  n  除以 5，累加商的值到 count。
     * 3.	当  n  小于 5 时停止。
     * <p>
     * 举例：数学公式计算 150! 末尾零的数量。公式为：零的数量 = ⌊n/5⌋ + ⌊n/25⌋ + ⌊n/125⌋ + ...
     * 总和 = 30 + 6 + 1 = 37。
     * 循环次数	算法中加到count的值	    对应的数学公式项
     * 1	150 / 5 = 30	            ⌊n/5⌋
     * 2	(150 / 5) / 5 = 6	        ⌊n/25⌋
     * 3	((150 / 5) / 5) / 5 = 1	    ⌊n/125⌋
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
     * <p>
     * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
     * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
     * <p>
     * 输入：x = 8
     * 输出：2
     * 解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
     * <p>
     * 二分查找
     *
     * @param x 指定值
     * @return 平方根
     */
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int left = 1;
        int right = x;
        int res = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 计算目标值(如果 mid² ≤ x < (mid+1)²，那么 mid 就是 x 的平方根)
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
