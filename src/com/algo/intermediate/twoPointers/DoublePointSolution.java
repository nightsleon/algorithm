package com.algo.intermediate.twoPointers;

import java.util.*;
/**
 * 难度级别: Intermediate
 * 分类: Twopointers
 * 
 * @author liangjun
 **/
public class DoublePointSolution {

    /**
     * LCR 018. 验证回文串
     * 给定一个字符串 s ，验证 s 是否是 回文串 ，只考虑字母和数字字符，可以忽略字母的大小写。
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
     * <p>
     * s = "abc", t = "ahbgdc" : true
     * s = "axc", t = "ahbgdc" : false
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
     * 两数和：双指针，numbers 需要是排序数组
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
     * <p>
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
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
            if (height[left] < height[right]) {
                max = Math.max(max, height[left] * (right - left));
                // 左侧柱子低，左指针右移动
                left++;
            } else {
                max = Math.max(max, height[right] * (right - left));
                // 右侧柱子低，右指针左移动
                right--;
            }
        }
        return max;
    }

    /**
     * 42. 接雨水
     * 1.	双指针法：左右指针从数组的两端向中间移动。
     * 2.	记录左右最大高度：highestL 和 highestR 分别记录左侧和右侧柱子的最大高度。
     * 3.	雨水计算：当前柱子能够接到的雨水量 = 左/右侧最大高度 - 当前柱子的高度。
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
     * 1.	排序：
     * •	先对数组进行排序，便于后续去重和双指针操作。
     * 2.	遍历数组：
     * •	固定一个数 nums[i]，然后用双指针 left 和 right 在剩余部分查找满足条件的两个数。
     * 3.	双指针查找：
     * •	如果三数之和小于 0，移动左指针增加总和。
     * •	如果三数之和大于 0，移动右指针减小总和。
     * •	如果等于 0，则记录当前三元组，并移动指针跳过重复元素。
     * 4.	去重：
     * •	排序后可以直接跳过重复的数以避免结果重复。
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
     * 209. 长度最小的子数组
     * <p>
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其总和大于等于 target 的长度最小的子数组
     * [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     * <p>
     * 1.	滑动窗口：右指针不断移动来扩大窗口，左指针在满足条件时缩小窗口以找到最短的子数组。
     * 2.	累加和：不断累加当前窗口内的元素，判断其是否大于等于目标值。
     * 3.	条件收缩：当窗口的累加和大于或等于目标值时，计算子数组长度，并尝试通过移动左边界缩小窗口。
     * 4.	返回值：如果没有找到符合条件的子数组，返回 0，否则返回最小长度。
     */
    public int minSubArrayLen(int target, int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        int min = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        // 滑动窗口
        while (right < nums.length) {
            // 右指针扩大窗口
            sum += nums[right++];
            // sum 大于等于 target，左指针缩小窗口，同时sum减去left值
            while (sum >= target) {
                min = Math.min(min, right - left);
                sum -= nums[left];
                left++;
            }
        }
        // 没有找到大于等于 target 的数组返回0，找到则返回 min
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * 3. 无重复字符的最长子串
     * a.变量说明：
     * <ul>
     *   <li><b>left：</b>左指针</li>
     *   <li><b>right：</b>右指针</li>
     *   <li><b>set：</b>遍历过的元素集合</li>
     *   <li><b>max：</b>最长字符串</li>
     * </ul>
     * <p>
     * b.核心逻辑：
     * <p><b>对字符串进行遍历</b></p>
     * <ul>
     *   <li><b>情况 1：不存在重复元素</b>
     *     <ul>
     *       <li>1.1 将已遍历过的元素添加到集合中</li>
     *       <li>1.2 向右扩展窗口，即 right 指针递增</li>
     *       <li>1.3 计算最大长度，更新为 max = Math.max(max, right - left)</li>
     *     </ul>
     *   </li>
     *   <li><b>情况 2：存在重复元素</b>
     *     <ul>
     *       <li>2.1 从集合中删除已遍历过的元素，直到不存在重复元素</li>
     *       <li>2.2 缩小窗口，即 left 指针递减</li>
     *     </ul>
     *   </li>
     * </ul>
     */
    public int lengthOfLongestSubstring(String s) {
        // 处理边界
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 生命变量
        int left = 0;
        int right = 0;
        int max = 0;
        Set<Character> seen = new HashSet<>(64);
        // 遍历字符串
        while (right < s.length()) {
            if (!seen.contains(s.charAt(right))) {
                // 扩展窗口
                seen.add(s.charAt(right));
                max = Math.max(max, right - left + 1);
                right++;
            } else {
                // 收缩窗口
                seen.remove(s.charAt(left));
                left++;
            }
        }
        return max;
    }

    /**
     * 9. 回文数
     * <p>
     * <ul>
     *     <li>1. 负数和尾数为 0 的数： 首先，如果一个数是负数，或者除了 0 本身，末尾为 0 的数（如 10，120 等），不可能是回文数。</li>
     *     <li>2. 逐步反转： 将整数的后半部分逐步反转并存储在 reversedHalf 中。每次都通过 x % 10 提取当前的最低位数字，并将其添加到 reversedHalf 的末尾。同时，x 去掉最低位。</li>
     *     <li>3. 终止条件： 当 x 变得小于或等于 reversedHalf 时，说明已经处理了整数的一半。如果原始数字是偶数长度，则 x 应等于 reversedHalf；如果是奇数长度，则 x 应等于 reversedHalf / 10（因为中间的一位数字不影响回文性质）。</li>
     * </ul>
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
     * 30. 串联所有单词的子串
     * <p>
     * 1.	滑动窗口：使用滑动窗口的方式，不断移动 right 指针，同时动态地更新窗口中的内容。每次发现单词匹配时，只调整窗口大小，而不是重新遍历。
     * 2.	左指针调整：如果某个单词出现次数超过应有次数时，左指针 left 向右移动，缩小窗口直到窗口内的子串符合要求。
     * 3.	提前跳过不匹配：如果在遍历过程中，发现某个子串不属于 words，则立即清空 seenCount，并将 left 更新到 right，避免继续检查已经不可能匹配的子串。
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        // 处理边界
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return res;
        }
        // 变量声明，word 中单词长度，word 中单词数量
        int wordLen = words[0].length();
        int wordCount = words.length;
        int wordTotalLen = wordLen * wordCount;
        // 单词总长度大于字符串长度，直接返回
        if (wordTotalLen > s.length()) {
            return res;
        }
        // word 中单词出现次数
        Map<String, Integer> wordCountMap = new HashMap<>(64);
        for (String word : words) {
            wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        }

        // 1.遍历所有可能的起始位置(为什么是 wordLength? 如果 wordLen = 3，对于字符串 "abcdef", i 只能是 0 1 2; i = 3 等效于 i = 0，i = 0 right起点是字母 a/d，i = 3时，起点是字母d，)
        for (int i = 0; i < wordLen; i++) {
            int left = i;
            int right = i;
            // 当前窗口内(字符串)单次出现次数
            Map<String, Integer> seenCountMap = new HashMap<>(64);

            // 2.滑动窗口
            while (right + wordLen <= s.length()) {
                // 提取字符串右侧单词并扩展窗口
                String rightWord = s.substring(right, right + wordLen);
                right += wordLen;

                // 2.1 wordCountMap 中包含当前单词
                if (wordCountMap.containsKey(rightWord)) {
                    // seenCount 加一
                    seenCountMap.put(rightWord, seenCountMap.getOrDefault(rightWord, 0) + 1);
                    // 次数超出预期（字符串s中的单词出现次数大于word中单词出现次数）
                    while (seenCountMap.get(rightWord) > wordCountMap.get(rightWord)) {
                        // seenCount 减一
                        String leftWord = s.substring(left, left + wordLen);
                        seenCountMap.put(leftWord, seenCountMap.get(leftWord) - 1);
                        // 收缩窗口，直到去除掉多余 rightWord 为止
                        left += wordLen;
                    }
                    // 字符串中的匹配数量等于单词数量(字符串起始位置除以单词长度，就是匹配数量)
                    if ((right - left) / wordLen == wordCount) {
                        res.add(left);
                    }
                }
                // 2.2 wordCountMap 中不包含当前单词
                else {
                    // seenCount 清零
                    seenCountMap.clear();
                    // 收缩窗口
                    left = right;
                }
            }
        }

        return res;
    }

    /**
     * 283. 移动零
     * <p>
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
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
     * 438. 找到字符串中所有字母异位词
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 输入: s = "cbaebabacd", p = "abc" 输出: [0,6]
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        // 处理边界
        if (s == null || s.length() == 0 || p == null || p.length() == 0) {
            return res;
        }

        // 初始化 p 字符频次表
        int[] pCount = new int[26];
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }
        // 初始化滑动窗口频次表
        int[] windowCount = new int[26];

        // 滑动窗口左右指针
        int left = 0, right = 0;
        // 遍历 s 的所有字符
        while (right < s.length()) {
            // 扩展窗口
            char currChar = s.charAt(right);
            windowCount[currChar - 'a']++;
            right++;

            // 收缩窗口，保证窗口等于p的长度
            if (right - left > p.length()) {
                char leftChar = s.charAt(left);
                windowCount[leftChar - 'a']--;
                left++;
            }
            // 比较两个频次表
            if (Arrays.equals(pCount, windowCount)) {
                res.add(left);
            }
        }
        return res;
    }

    /**
     * 239. 滑动窗口最大值
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。
     * 滑动窗口每次只向右移动一位。返回 滑动窗口中的最大值 。
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        // 定义双端队列，存储数组下标
        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 收缩滑动窗口（当前窗口大于 k ）
            int end = i;
            if (!deque.isEmpty() && end - deque.peekFirst() + 1 > k) {
                deque.pollFirst();
            }
            // 删除小于当前元素的下标，保证队列头部元素最大
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                deque.pollLast();
            }
            // 直到当前元素小于等于队列末位元素（或队列空），将元素下标压入队列
            deque.offerLast(i);
            // 当前下标大于等于 k - 1，将队列头部下标加入结果集
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return res;
    }

    /**
     * 76. 最小覆盖子串
     * <p>
     * 1.	目标字符计数：
     * •	统计字符串 t 中每个字符的频率，存储在 tCount 中。
     * •	使用 windowCount 动态记录当前窗口内的字符频率。
     * <p>
     * 2.	双指针滑动窗口：
     * •	右指针扩展窗口，将字符加入窗口并更新频率。
     * •	左指针收缩窗口，当窗口内的字符覆盖 t 时，尝试找到更短的窗口。
     * <p>
     * 3.	窗口合法性：
     * •	使用 valid 变量记录窗口中完全匹配的字符个数。
     * •	当 valid == tCount.size() 时，说明窗口内的字符覆盖了 t。
     * <p>
     * 4.	更新最小窗口：
     * •	每次窗口满足条件时，记录当前窗口的起始位置和长度。
     */
    public String minWindow(String s, String t) {
        // 处理边界
        if (s == null || s.length() == 0 || t == null || t.length() == 0) {
            return "";
        }
        // 1.目标字符统计
        Map<Character, Integer> tCount = new HashMap<>(32);
        for (char c : t.toCharArray()) {
            tCount.put(c, tCount.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> windowCount = new HashMap<>(32);

        int left = 0, right = 0;
        int start = 0;
        int validCount = 0;
        int minLen = Integer.MAX_VALUE;
        // 2.双指针滑动窗口
        while (right < s.length()) {
            // 右指针扩展窗口，将字符加入窗口并更新频率、有效字符数量
            char c = s.charAt(right);
            right++;

            // t 包含当前字符 c
            if (tCount.containsKey(c)) {
                windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);
                // 窗口与目标字符数量相同，有效数量加一
                if (windowCount.get(c).equals(tCount.get(c))) {
                    validCount++;
                }
            }

            // 左指针收缩窗口，窗口内字符覆盖 t 时，尝试找到更短的窗口
            while (validCount == tCount.size()) {
                // 更新最小窗口
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }
                // t 包含左侧字符
                char leftChar = s.charAt(left);
                if (tCount.containsKey(leftChar)) {
                    // 窗口与目标字符数量相同（可能会有重复字符：s = aaab, t = ab）
                    if (windowCount.get(leftChar).equals(tCount.get(leftChar))) {
                        validCount--;
                    }
                    // 更新字符频率
                    windowCount.put(leftChar, windowCount.get(leftChar) - 1);
                }
                // 收缩窗口
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
