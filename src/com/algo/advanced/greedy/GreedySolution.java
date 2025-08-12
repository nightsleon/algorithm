package com.algo.advanced.greedy;

import java.util.Arrays;
/**
 * 难度级别: Advanced
 * 分类: Greedy
 * 
 * @author liangjun
 **/
public class GreedySolution {
    public static void main(String[] args) {
        int[] coins = new int[]{2, 5, 10, 1};
        GreedySolution solution = new GreedySolution();
        int cnt = solution.coinChange(coins, 11);
//        System.out.println(cnt);
        int[] ht = {3, 8, 5, 2, 7, 7, 3, 4};
        int capacity = solution.maxCapacityDoublePoint(ht);
        System.out.println(capacity);
    }

    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        if (amount == 0) return 0;
        int cnt = 0;
        int i = coins.length - 1;
        while (i >= 0) {
            // 当前硬币面值大于 amount 跳过
            if (coins[i] > amount) {
                i--;
                continue;
            }
            amount -= coins[i];
            cnt++;
            if (amount == 0) return cnt;
        }
        return -1;
    }

    // 最大容量，暴力破解
    public int maxCapacity(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
            }
        }
        return max;
    }

    // 双指针
    public int maxCapacityDoublePoint(int[] height) {
        // 定义左右指针
        int left = 0, right = height.length - 1;
        int max = 0;
        while (left < right) {
            // 计算当前容量
            int currCapacity = (right - left) * Math.min(height[left], height[right]);
            max = Math.max(max, currCapacity);
            // 左侧小，右移动
            if (height[left] < height[right]) {
                left++;
            } else {
                // 右侧小，左移动
                right--;
            }
        }
        return max;
    }

    // 接雨水
    public int trap(int[] height) {
        // 双指针
        int l = 0, r = height.length - 1;
        int maxLH = height[l], maxRH = height[r];
        int res = 0;
        while (l < r) {
            // 获取左右最高值
            maxLH = Math.max(maxLH, height[l]);
            maxRH = Math.max(maxRH, height[r]);
            // 左侧小，右移动
            if (height[l] < height[r]) {
                res += maxLH - height[l];
                l++;
            } else {
                // 右侧小，左移动
                res += maxRH - height[r];
                r--;
            }
        }
        return res;
    }
}
