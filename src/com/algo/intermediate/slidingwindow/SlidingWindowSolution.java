package com.algo.intermediate.slidingwindow;

import java.util.*;

/**
 * 难度级别: Intermediate
 * 分类: Sliding Window (滑动窗口)
 * 
 * 包含算法类型：
 * - 固定窗口：窗口大小固定不变
 * - 可变窗口：窗口大小根据条件动态调整
 * 
 * @author liangjun
 **/
public class SlidingWindowSolution {

    /**
     * 209. 长度最小的子数组
     * 找出满足其和大于等于目标值的最短子数组长度
     * 
     * 类型：可变窗口
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
     * 
     * 类型：可变窗口
     */
    public int lengthOfLongestSubstring(String s) {
        // 处理边界
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 声明变量
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
     * 30. 串联所有单词的子串
     * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"，"abefcd"，"cdabef"，"cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。
     * "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
     * 
     * 类型：固定窗口
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

        // 1.遍历所有可能的起始位置
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
                    // 字符串中的匹配数量等于单词数量
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
     * 438. 找到字符串中所有字母异位词
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 输入: s = "cbaebabacd", p = "abc" 输出: [0,6]
     * 
     * 类型：固定窗口
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
     * 
     * 类型：固定窗口 + 单调队列
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        // 定义双端队列，存储数组下标
        Deque<Integer> deque = new LinkedList<>();
        // 定义结果集
        int[] res = new int[nums.length - k + 1];
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 1.移除队列头部过期元素，收缩滑动窗口（当前窗口大于 k ）
            int end = i;
            if (!deque.isEmpty() && end - deque.peekFirst() + 1 > k) {
                deque.pollFirst();
            }
            // 2.维护单调递减队列，删除小于当前元素的下标
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                deque.pollLast();
            }
            // 3.添加当前元素，直到当前元素小于等于队列末位元素（或队列空），将元素下标压入队列
            deque.offerLast(i);
            // 4.收集结果，当前下标大于等于 k - 1，将队列头部下标加入结果集
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return res;
    }

    /**
     * 76. 最小覆盖子串
     * 返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""
     * 
     * 类型：可变窗口
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
