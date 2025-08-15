package com.algo.beginner.array;

import java.util.*;

/**
 * 难度级别: Beginner
 * 分类: Array
 *
 * @author liangjun
 **/
public class ArrayStrSolution {

    /**
     * 1.给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
     * 你可以按任意顺序返回答案。
     * <p>
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     */
    public int[] twoSum(int[] numbers, int target) {
        // 使用哈希表存储数组元素及其索引
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            // 计算另一个数
            int another = target - numbers[i];
            // 如果另一个数在哈希表中，则返回两个数的索引
            if (map.containsKey(another)) {
                return new int[]{map.get(another), i};
            }
            // 将当前数及其索引添加到哈希表中
            map.put(numbers[i], i);
        }
        return new int[]{-1, -1};
    }

    /**
     * 88. 合并两个有序数组(逆向双指针)
     * <p>
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * <p>
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * <p>
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 定义三指针
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        // 遍历两个数组，将较大的值放到 p，并移动指针
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p--] = nums1[p1--];
            } else {
                nums1[p--] = nums2[p2--];
            }
        }
        // nums2 还没有遍历完，继续填充到nums1中
        while (p2 >= 0) {
            nums1[p--] = nums2[p2--];
        }
    }

    /**
     * 27.移除元素
     * <p>
     * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
     * 输出：5, nums = [0,1,4,0,3,_,_,_]
     * <p>
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。
     * 元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
     */
    public int removeElement(int[] nums, int val) {
        // slow 指针指向下一个要赋值的位置，fast 指针遍历数组
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            // 如果当前元素不等于 val，则保留该元素
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
            // fast 指针始终右移
            fast++;
        }
        // slow 即为新数组的长度
        return slow;
    }

    /**
     * 26. 删除有序数组中的重复项
     * <p>
     * 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，
     * 使每个元素只出现一次，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
     * <p>
     * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
     * 输出：5, nums = [0,1,2,3,4]
     */
    public int removeDuplicates(int[] nums) {
        // 检查边界
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 双指针：slow指向下一个要填充的位置，fast遍历所有元素
        int slow = 1, fast = 1;
        while (fast < nums.length) {
            // 如果当前元素与前一个不重复，则复制到slow位置
            if (nums[fast] != nums[slow - 1]) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

    /**
     * 189. 轮转数组
     * <p>
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
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
     * 14.最长公共前缀
     * <p>
     * 编写一个函数来查找字符串数组中的最长公共前缀，如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     *
     * @param strs 字符串
     * @return java.lang.String
     */
    public String longestCommonPrefix(String[] strs) {
        // 排序，第一个元素长度最短，最后一个元素最长
        Arrays.sort(strs);
        String left = strs[0], right = strs[strs.length - 1];
        StringBuilder sub = new StringBuilder();
        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) == right.charAt(i)) {
                sub.append(left.charAt(i));
            } else {
                break;
            }
        }
        return sub.toString();
    }

    /**
     * 13. 罗马数字转整数
     * <p>
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * <p>
     * 输入: s = "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     *
     * @param s 罗马数字字符串
     * @return int 整数
     */
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        int len = s.length();
        int num = 0;
        for (int i = 0; i < len; i++) {
            int val = map.get(s.charAt(i));
            // i 不是最后一位，且 i 值 小于 i+1 值，则减去 i 的值
            if (i < len - 1 && val < map.get(s.charAt(i + 1))) {
                num -= val;
            } else {
                num += val;
            }
        }
        return num;
    }

    /**
     * 58. 最后一个单词的长度
     * <p>
     * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度
     *
     * @param s 字符串
     * @return int 长度
     */
    public int lengthOfLastWord(String s) {
        s = s.trim();
        int llw = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                break;
            }
            llw++;
        }
        return llw;
    }

    /**
     * 28. 找出字符串中第一个匹配项的下标
     * <p>
     * 输入：haystack = "sadbutsad", needle = "sad"
     * 输出：0
     * 解释："sad" 在下标 0 和 6 处匹配。
     * 第一个匹配项的下标是 0 ，所以返回 0 。
     *
     * @param haystack 干草堆
     * @param needle   缝衣针
     * @return int
     */
    public int strStr(String haystack, String needle) {
        int hLen = haystack.length(), nLen = needle.length();
        for (int hi = 0; hi <= hLen - nLen; hi++) {
            boolean flag = true;
            for (int ni = 0; ni < nLen; ni++) {
                // haystack 从 hi 开始，needle 从 0 开始
                if (haystack.charAt(hi + ni) != needle.charAt(ni)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return hi;
            }
        }
        return -1;
    }

    /**
     * 121. 买卖股票的最佳时机
     * <p>
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * <p>
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5
     *
     * @param prices 下标代表日期，值代表股价
     * @return int 最大利润
     */
    public int maxProfit(int[] prices) {
        // 处理边界
        if (prices == null || prices.length < 0) {
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
     * steps: 当前跳跃步数
     * maxReach: 当前能达到的最远位置
     * end: 当前段终点
     *
     * @return 跳到终点需要几步
     */
    public int jump(int[] nums) {
        int n = nums.length;
        if (n <= 1)
            return 0;
        int steps = 0, maxReach = 0, end = 0;
        for (int i = 0; i < n - 1; i++) {
            maxReach = Math.max(maxReach, i + nums[i]);
            if (i == end) {
                steps++;
                end = maxReach;
                if (end >= n - 1) {
                    return steps;
                }
            }
        }
        return -1;
    }

    /**
     * h 指数
     */
    public int hIndex(int[] citations) {
        int n = citations.length;
        // 下标是论文被引用次数，值是被引用i次的论文数量
        int[] counter = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (citations[i] >= n) {
                counter[n] += 1;
            } else {
                counter[citations[i]] += 1;
            }
        }
        int num = 0;
        // 从右向左遍历
        for (int r = n; r >= 0; r--) {
            // 累加论文数量
            num += counter[r];
            // 论文数量 >= 下标论文引用次数，则返回论文数量
            if (num >= r)
                return r;
        }
        return 0;
    }

    class RandomizedSet {
        private List<Integer> nums;
        private Map<Integer, Integer> indices;
        private Random random;

        public RandomizedSet() {
            nums = new ArrayList<>();
            indices = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            // 已经插入过，则返回false
            if (indices.containsKey(val))
                return false;
            int index = nums.size();
            // 末尾添加元素
            nums.add(val);
            indices.put(val, index);
            return true;
        }

        public boolean remove(int val) {
            if (!indices.containsKey(val))
                return false;
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

        public int getRandom() {
            int index = random.nextInt(nums.size());
            return nums.get(index);
        }
    }

    /**
     * 238. 除自身以外数组的乘积
     * <p>
     * 左侧所有数字的乘积和右侧所有数字的乘积（即前缀与后缀）相乘得到答案。
     */
    public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        // 元素 i 最终结果
        int[] answer = new int[len];

        int[] prefix = new int[len];
        prefix[0] = 1;
        // 计算前缀乘积，没有左边元素时，左侧第一个乘积为1
        for (int i = 1; i < len; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }
        // 计算后缀乘积，没有右边元素时，右侧第一个乘积为1
        int suffix = 1;
        for (int i = len - 1; i >= 0; i--) {
            answer[i] = prefix[i] * suffix;
            // 更新后缀乘积
            suffix *= nums[i];
        }
        return answer;
    }

    /*
     * 1. 总油量和总花费的比较：如果总油量小于总花费，说明无论从哪个加油站开始，车都无法完成一圈，返回 -1。
     * 2. 贪心策略：从左到右遍历，如果某个起点到某个加油站油量不足以继续行驶，我们直接跳到下一个加油站作为新的起点，重新开始计算。
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

    /*
     * 1. 双指针法：左右指针从数组的两端向中间移动。
     * 2. 记录左右最大高度：maxL 和 maxR 分别记录左侧和右侧柱子的最大高度。
     * 3. 雨水计算：当前柱子能够接到的雨水量 = 左/右侧最大高度 - 当前柱子的高度。
     */
    public int trap(int[] height) {
        // 双指针
        int l = 0, r = height.length - 1;
        // 左/右侧最高的柱子的高度
        int maxL = 0, maxR = 0;
        // 接雨水量
        int res = 0;
        while (l < r) {
            if (height[l] < height[r]) {
                // 左侧最高柱子高度
                maxL = Math.max(height[l], maxL);
                // 计算雨量，最高柱子 - 当前柱子高度
                res += maxL - height[l];
                // 左侧小，左指针右移动
                l++;
            } else {
                // 计算右侧最高柱子高度
                maxR = Math.max(height[r], maxR);
                // 计算雨量
                res += maxR - height[r];
                // 右侧小，右指针左移动
                r--;
            }
        }
        return res;
    }

    public String reverseWords(String s) {
        // 去除字符串前后空格
        s = s.trim();
        StringBuilder result = new StringBuilder();
        // 每个单词的左右区间指针 left 和 right
        int right = s.length() - 1;
        int left = right;
        while (left >= 0) {
            // 遍历当前单词，遇到空格结束
            while (left >= 0 && s.charAt(left) != ' ')
                left--;
            // 将单词添加到结果中
            result.append(s.substring(left + 1, right + 1));
            // 添加单词间空格，最后一个单词不用加
            if (left > 0)
                result.append(" ");
            // 遍历空格，遇到非空字符结束
            while (left >= 0 && s.charAt(left) == ' ')
                left--;
            // 开始下一个单词，单词指针归位
            right = left;
        }
        return result.toString();
    }

    /*
     * 1. 创建一个 StringBuilder 数组，每个 StringBuilder 代表一行。
     * 2. 遍历字符串，将每个字符根据其应在的行号添加到相应的 StringBuilder 中。
     * 3. 根据行号的变动，调整方向（从向下变为向上或从向上变为向下）。
     * 4. 最后将每一行的字符拼接起来，得到结果字符串。
     */
    public String convert(String s, int numRows) {
        // 处理边界值
        if (numRows <= 1 || numRows >= s.length()) {
            return s;
        }
        // 每行用一个 StringBuilder 存储字符
        int len = Math.min(numRows, s.length());
        StringBuilder[] rows = new StringBuilder[len];
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

    /**
     * 560. 和为 K 的子数组
     * <p>
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
     * <p>
     * 子数组是数组中元素的连续非空序列。
     * <p>
     * 前缀和：
     * 前缀和是从数组起点到当前下标元素的累加和，用于快速计算某一段子数组的总和。
     * 假设 sum[i] 是从起点到下标 i 的前缀和，sum[j] 是从起点到下标 j 的前缀和（j > i）。
     * 子数组 nums[i+1...j] 的和为 k，则有：sum[j] - sum[i] = k
     * <p>
     * 解法：
     * • 使用一个哈希表 prefixCount 存储前缀和的出现次数，以快速查询是否存在满足条件的 sum[i]。
     * • 遍历数组时，计算当前前缀和 sum[j]，检查 sum[j] - k 是否存在于 prefixCount
     * 中，如果存在，说明找到一个或多个满足条件的子数组。
     */
    public int subarraySum(int[] nums, int k) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 53. 最大子数组和
     * <p>
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组是数组中的一个连续部分。
     *
     * @param nums 数组
     * @return 最大子数组和
     */
    public int maxSubArray(int[] nums) {
        // 定义当前和、最大和
        int currSum = nums[0], maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 计算当前和
            currSum = Math.max(currSum + nums[i], nums[i]);
            // 计算最大和
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }

}
