package com.algo.advanced.greedy;

import java.util.Arrays;
/**
 * 难度级别: Advanced
 * 分类: Greedy (贪心算法)
 * 
 * 贪心算法核心思想：
 * 每一步都选择当前最优解，相信局部最优能导向全局最优
 * 
 * @author liangjun
 **/
public class GreedySolution {
    public static void main(String[] args) {
        int[] coins = new int[]{2, 5, 10, 1};
        GreedySolution solution = new GreedySolution();
        int cnt = solution.coinChange(coins, 11);
        System.out.println("Coin change result: " + cnt);
        int[] ht = {3, 8, 5, 2, 7, 7, 3, 4};
        int capacity = solution.maxCapacityDoublePoint(ht);
        System.out.println("Max capacity: " + capacity);
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

    /**
     * 121. 买卖股票的最佳时机 - 从ArrayStrSolution移入
     * <p>
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * <p>
     * 核心思想：贪心策略 - 维护历史最低价
     * 1. 遍历价格数组，维护到当前为止的最低价格
     * 2. 计算当前价格减去最低价格的利润
     * 3. 更新最大利润
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5
     */
    public int maxProfit(int[] prices) {
        // 处理边界
        if (prices == null || prices.length < 2) {
            // 至少需要两天才能买卖
            return 0;
        }
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            // 更新历史最低价
            minPrice = Math.min(minPrice, prices[i]);
            // 更新最大利润
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }

    /**
     * 55. 跳跃游戏 - 从ArrayStrSolution移入
     * <p>
     * 给你一个非负整数数组 nums ，你最初位于数组的第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
     * <p>
     * 核心思想：贪心策略 - 维护最大可达距离
     * 1. 维护一个变量maxReach表示当前能到达的最远位置
     * 2. 遍历数组，如果当前位置可达（i <= maxReach），则更新maxReach
     * 3. 如果maxReach能覆盖最后一个位置，则返回true
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入：nums = [2,3,1,1,4]
     * 输出：true
     * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
     */
    public boolean canJump(int[] nums) {
        int maxReach = 0;
        // 如果能跳到 maxReach，则 [0, maxReach - 1] 都可以跳到，尝试此区间所有可能
        for (int i = 0; i <= maxReach && i < nums.length; i++) {
            // 找到 [0, maxReach - 1] 的 maxReach
            maxReach = Math.max(i + nums[i], maxReach);
            // maxReach 大于等于最后一个下标，则可达
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 45. 跳跃游戏 II - 从ArrayStrSolution移入
     * <p>
     * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     * <p>
     * 核心思想：贪心策略 - 每次跳跃都选择能到达最远位置的方案
     * 1. 维护当前跳跃能到达的边界end
     * 2. 在当前边界内选择下一跳能到达最远的位置
     * 3. 到达边界时，必须进行一次跳跃，更新边界
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     */
    public int jump(int[] nums) {
        // 处理边界
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int len = nums.length;
        // step: 当前跳跃步数，maxReach: 当前能达到的最远位置，end: 当前段终点
        int step = 0, maxReach = 0, end = 0;
        // 遍历到倒数第二个位置，因为到达最后一个位置就完成任务了
        for (int i = 0; i < len - 1; i++) {
            // 更新从当前位置能跳到的最远位置
            maxReach = Math.max(maxReach, i + nums[i]);
            // 到达当前段的终点，需要增加一步跳跃
            if (i == end) {
                // 增加一步跳跃
                step++;
                // 更新下一段的终点
                end = maxReach;
                // 如果已经可以到达目标，提前返回
                if (end >= len - 1) {
                    return step;
                }
            }
        }
        return -1;
    }

    /**
     * 134. 加油站 - 从ArrayStrSolution移入
     * <p>
     * 在环形公路上有n个加油站，每个加油站提供一定量的汽油，到达下一个加油站需要消耗一定量的汽油。
     * 问从哪个加油站出发可以完成一圈？
     * <p>
     * 核心思想：贪心策略
     * 1. 总油量和总花费的比较：如果总油量小于总花费，说明无论从哪个加油站开始，车都无法完成一圈，返回 -1。
     * 2. 贪心策略：从左到右遍历，如果某个起点到某个加油站油量不足以继续行驶，我们直接跳到下一个加油站作为新的起点，重新开始计算。
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * 输出: 3
     * 解释: 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油...因此，3 可为起始索引。
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 声明总剩余油量，当前起点所剩余油量，当前起点
        int totalSurplus = 0, currSurplus = 0, start = 0;
        int n = gas.length;
        for (int i = 0; i < n; i++) {
            int surplus = gas[i] - cost[i];
            totalSurplus += surplus;
            currSurplus += surplus;
            // 当前起点算起，剩余油量小于零，则无法完成一圈，currSurplus 清零，start = i + 1
            if (currSurplus < 0) {
                currSurplus = 0;
                start = i + 1;
            }
        }
        return totalSurplus >= 0 ? start : -1;
    }

    /**
     * 53. 最大子数组和 - 从ArrayStrSolution移入
     * <p>
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组是数组中的一个连续部分。
     * <p>
     * 核心思想：贪心策略 - Kadane算法
     * 1. 维护当前子数组和currSum和全局最大和maxSum
     * 2. 对于每个元素，选择：要么加入当前子数组，要么重新开始一个子数组
     * 3. 贪心选择：如果当前和加上新元素比新元素本身还小，就重新开始
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     */
    public int maxSubArray(int[] nums) {
        // 定义当前和、最大和
        int currSum = nums[0], maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 计算当前和：要么加入当前子数组，要么重新开始
            currSum = Math.max(currSum + nums[i], nums[i]);
            // 计算最大和
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }
}
