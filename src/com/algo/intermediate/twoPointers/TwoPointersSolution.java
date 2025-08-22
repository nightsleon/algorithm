package com.algo.intermediate.twopointers;

import java.util.*;

/**
 * 难度级别: Intermediate
 * 分类: Two Pointers (双指针)
 * 
 * 包含算法类型：
 * - 对撞指针：从两端向中间移动
 * - 快慢指针：一个快一个慢
 * 
 * @author liangjun
 **/
public class TwoPointersSolution {

    /**
     * LCR 018. 验证回文串
     * 给定一个字符串 s ，验证 s 是否是 回文串 ，只考虑字母和数字字符，可以忽略字母的大小写。
     * 
     * 类型：对撞指针
     */
    public boolean isPalindrome(String s) {
        // 处理边界
        if (s == null) {
            return false;
        }
        char[] charArray = s.toCharArray();

        // 左右指针遍历所有字符
        int left = 0;
        int right = charArray.length - 1;
        while (left < right) {
            // 跳过左侧不是字母和数字的字符
            while (left < right && !Character.isLetterOrDigit(charArray[left])) {
                left++;
            }
            // 跳过右侧不是字母和数字的字符
            while (left < right && !Character.isLetterOrDigit(charArray[right])) {
                right--;
            }
            // 左右指针数字不相同，返回 false
            if (Character.toLowerCase(charArray[left]) != Character.toLowerCase(charArray[right])) {
                return false;
            }
            // 移动左右指针
            left++;
            right--;
        }
        return true;
    }

    /**
     * 392. 判断子序列
     * s = "abc", t = "ahbgdc" : true
     * s = "axc", t = "ahbgdc" : false
     * 
     * 类型：快慢指针
     */
    public boolean isSubsequence(String s, String t) {
        // 处理边界
        if (s == null || t == null) {
            return false;
        }
        // 遍历字符串，直到 s 或 t 遍历完成（注意&&判断）
        int slow = 0;
        int fast = 0;
        while (slow < s.length() && fast < t.length()) {
            // 找到相同的字符，slow 向前移动
            if (s.charAt(slow) == t.charAt(fast)) {
                slow++;
            }
            // fast 始终向前移动
            fast++;
        }
        // slow 走到s 末尾，则 s 是 t 的子序列
        return slow == s.length();
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 两数和：双指针，numbers 需要是排序数组
     * 
     * 类型：对撞指针
     */
    public int[] twoSum(int[] numbers, int target) {
        // 处理边界
        if (numbers.length < 2) {
            return new int[]{-1, -1};
        }
        // 排序数组
        Arrays.sort(numbers);
        // 双指针
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            // 两数之和
            int sum = numbers[left] + numbers[right];
            // 小于目标值，左指针右移动
            if (sum < target) {
                left++;
            } else if (sum > target) {
                // 大于目标值，右指针左移动
                right--;
            } else {
                // 找到匹配的两个数
                return new int[]{left + 1, right + 1};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 11. 盛最多水的容器
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 
     * 类型：对撞指针
     */
    public int maxArea(int[] height) {
        // 处理边界
        if (height == null || height.length < 2) {
            return 0;
        }
        // 初始化
        int max = 0;
        int left = 0, right = height.length - 1;
        // 木桶效应，接到的雨水是按照低的来计算
        while (left < right) {
            // 计算当前容器的面积
            int area = Math.min(height[left], height[right]) * (right - left);
            // 更新最大面积
            max = Math.max(max, area);
            // 移动较短的柱子（木桶效应）
            if (height[left] < height[right]) {
                // 左侧柱子低，左指针右移动
                left++;
            } else {
                // 右侧柱子低，右指针左移动
                right--;
            }
        }
        return max;
    }

    /**
     * 42. 接雨水
     * 1. 双指针法：左右指针从数组的两端向中间移动。
     * 2. 记录左右最大高度：highestL 和 highestR 分别记录左侧和右侧柱子的最大高度。
     * 3. 雨水计算：当前柱子能够接到的雨水量 = 左/右侧最大高度 - 当前柱子的高度。
     * 
     * 类型：对撞指针
     */
    public int trap(int[] height) {
        // 处理边界
        if (height == null || height.length < 2) {
            return 0;
        }
        // 声明接雨水总量 sum，左右指针
        int sum = 0;
        int left = 0, right = height.length - 1;
        // 声明左右最高的柱子
        int highestL = 0, highestR = 0;
        while (left < right) {
            if (height[left] <= height[right]) {
                // 左指针高度小于等于右指针高度：更新左侧最高的柱子并累加雨水量，移动指针
                highestL = Math.max(highestL, height[left]);
                sum += highestL - height[left];
                left++;
            } else {
                // 右指针小于左指针高度：更新右侧最高柱子并累加雨水，移动指针
                highestR = Math.max(highestR, height[right]);
                sum += highestR - height[right];
                right--;
            }
        }
        return sum;
    }

    /**
     * 15. 三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
     * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     * 
     * 类型：对撞指针
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // 处理边界
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        // 1.排序数组
        int len = nums.length;
        Arrays.sort(nums);

        // 2.遍历数组[0, len - 2]
        for (int i = 0; i < len - 2; i++) {
            // 跳过相同元素
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 3.双指针查找
            int left = i + 1;
            int right = len - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    // 三数之和等于零，加入结果集
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 跳过左侧相同元素（注意：比较当前元素和右侧下一个元素）
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 跳过右侧相同元素（注意：比较当前元素和左侧前一个元素）
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    // 移动左右指针，继续查找
                    left++;
                    right--;
                } else if (sum < 0) {
                    // 三数之和小于零，移动左指针
                    left++;
                } else {
                    // 三数之和大于零，移动右指针
                    right--;
                }
            }
        }
        return res;
    }

    /**
     * 9. 回文数
     * 判断整数是否为回文数
     * 
     * 类型：数字双指针（反转后半部分）
     */
    public boolean isPalindrome(int x) {
        // 负数、末尾是0但本身不是0，返回 false
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        int reverseHalf = 0;
        // 逐步翻转
        while (x > reverseHalf) {
            // 提取 x 的最低位，添加到 reverseHalf
            reverseHalf = reverseHalf * 10 + x % 10;
            // 同时去掉 x 最低位
            x /= 10;
        }
        // "1221 x = 12, reverseHalf = 12; 12321 x = 12, reverseHalf = 123";
        return x == reverseHalf || x == reverseHalf / 10;
    }

    /**
     * 283. 移动零
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 
     * 类型：快慢指针
     */
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int fast = 0;
        int slow = 0;
        // 快指针非零，赋值给慢指针
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                // slow 只有在fast != 0 时才向右移动
                slow++;
            }
            // 快指针每次都向右移动
            fast++;
        }
        // 慢指针到末尾填零
        while (slow < nums.length) {
            nums[slow++] = 0;
        }
    }

    /**
     * 88. 合并两个有序数组 - 从ArrayStrSolution移入
     * <p>
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * <p>
     * 核心思想：逆向双指针
     * 1. 从两个数组的末尾开始比较
     * 2. 将较大的元素放到nums1的末尾
     * 3. 避免了正向合并时需要移动元素的问题
     * <p>
     * 时间复杂度：O(m+n)，空间复杂度：O(1)
     * <p>
     * 类型：对撞指针（逆向）
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
     * 27. 移除元素 - 从ArrayStrSolution移入
     * <p>
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。
     * 元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
     * <p>
     * 核心思想：快慢指针
     * 1. slow指针指向下一个要赋值的位置
     * 2. fast指针遍历数组
     * 3. 当fast指向的元素不等于val时，将其复制到slow位置
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 类型：快慢指针
     * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
     * 输出：5, nums = [0,1,4,0,3,_,_,_]
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
     * 26. 删除有序数组中的重复项 - 从ArrayStrSolution移入
     * <p>
     * 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，
     * 使每个元素只出现一次，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
     * <p>
     * 核心思想：快慢指针
     * 1. slow指向下一个要填充的位置
     * 2. fast遍历所有元素
     * 3. 当fast元素与前一个不重复时，复制到slow位置
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 类型：快慢指针
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
     * 151. 反转字符串中的单词 - 从ArrayStrSolution移入
     * <p>
     * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
     * <p>
     * 核心思想：双指针逆向遍历
     * 1. 从字符串末尾开始，使用left和right指针标记每个单词的边界
     * 2. 遇到非空格字符时，left指针向前移动到单词开始位置
     * 3. 将单词添加到结果中，然后跳过空格继续处理下一个单词
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(n)
     * <p>
     * 类型：区间指针
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     */
    public String reverseWords(String s) {
        // 去除字符串前后空格
        s = s.trim();
        StringBuilder result = new StringBuilder();
        // 每个单词的左右区间指针 left 和 right
        int right = s.length() - 1;
        int left = right;
        while (left >= 0) {
            // 遍历当前单词，遇到空格结束
            while (left >= 0 && s.charAt(left) != ' ') {
                left--;
            }
            // 将单词添加到结果中
            result.append(s.substring(left + 1, right + 1));
            // 添加单词间空格，最后一个单词不用加
            if (left > 0) {
                result.append(" ");
            }
            // 遍历空格，遇到非空字符结束
            while (left >= 0 && s.charAt(left) == ' ') {
                left--;
            }
            // 开始下一个单词，单词指针归位
            right = left;
        }
        return result.toString();
    }

    /**
     * 151. 反转字符串中的单词 - 双端队列版本（用于对比）
     * <p>
     * 利用双端队列，每次将单词添加到头部实现翻转
     * <p>
     * 类型：字符遍历 + 双端队列
     */
    public String reverseWordsDeque(String s) {
        // 处理边界
        if (s == null || s.length() == 0) {
            return "";
        }
        s = s.trim();

        StringBuilder tmpWord = new StringBuilder();
        Deque<String> deque = new ArrayDeque<>();
        for (Character c : s.toCharArray()) {
            // tmpWord 非空且当前单词结束，则加入队列头部
            if (c == ' ') {
                // 遇到空格，且 tmpWord 非空则加入队列头部
                if (tmpWord.length() > 0) {
                    deque.addFirst(tmpWord.toString());
                    tmpWord = new StringBuilder();
                }
            } else {
                // 遇到非空格则添加到 tmpWord
                tmpWord.append(c);
            }
        }
        // 最后一个单词
        if (tmpWord.length() > 0) {
            deque.addFirst(tmpWord.toString());
        }

        return String.join(" ", deque);
    }
}
