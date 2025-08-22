package com.algo.intermediate.hashmap;

import java.util.*;

/**
 * 难度级别: Intermediate
 * 分类: Hashmap
 * 
 * @author liangjun
 **/
public class HashMapSolution {

    /**
     * 1.两数和：优先考虑哈希表
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * 你可以按任意顺序返回答案。
     * <p>
     * 示例 1：
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * <p>
     * 示例 2：
     * 输入：nums = [3,2,4], target = 6
     * 输出：[1,2]
     */
    public int[] twoSum(int[] nums, int target) {
        // 值、下标的map
        Map<Integer, Integer> valIndexMap = new HashMap<>(64);
        for (int i = 0; i < nums.length; i++) {
            // 当前值 nums[i]，anotherVal = 目标值 - 当前值
            int anotherVal = target - nums[i];
            // 找到另一个值，则直接返回另一个元素的下标和当前下标
            if (valIndexMap.containsKey(anotherVal)) {
                return new int[] { valIndexMap.get(anotherVal), i };
            }
            // 将遍历过的元素放入map
            valIndexMap.put(nums[i], i);
        }
        return new int[] { -1, -1, };
    }

    /**
     * 338.救赎信：
     * <p>
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * 如果可以，返回 true ；否则返回 false 。
     * magazine 中的每个字符只能在 ransomNote 中使用一次。
     * <p>
     * 示例 1：
     * 输入：ransomNote = "a", magazine = "b"
     * 输出：false
     * <p>
     * 示例 2：
     * 输入：ransomNote = "aa", magazine = "ab"
     * 输出：false
     * <p>
     * 示例 3：
     * 输入：ransomNote = "aa", magazine = "aab"
     * 输出：true
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        // magazine 有小写字母组成，总计 26 个
        int[] magCnt = new int[26];
        // 遍历 magazine，字符作为下标，出现次数作为值，记录到 cnt 数组中
        for (char c : magazine.toCharArray()) {
            magCnt[c - 'a']++;
        }
        // 遍历 ransomNote 字符，判断是否在 magazine 中出现
        for (char c : ransomNote.toCharArray()) {
            magCnt[c - 'a']--;
            // magazine 中的字符不足以构成 ransom
            if (magCnt[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 205.同构字符串
     * 给定两个字符串 s 和 t ，判断它们是否是同构的。如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
     * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。
     * 不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
     * <p>
     * 示例 1：
     * 输入：s = "egg", t = "add"
     * 输出：true
     * <p>
     * 示例 2：
     * 输入：s = "foo", t = "bar"
     * 输出：false
     * <p>
     * 示例 3：
     * 输入：s = "paper", t = "title"
     * 输出：true
     */
    public boolean isIsomorphic(String s, String t) {
        // 处理边界
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        // s -> t 和 t -> s 映射
        Map<Character, Character> stMap = new HashMap<>(64);
        Map<Character, Character> tsMap = new HashMap<>(64);
        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);
            boolean notEq = (stMap.containsKey(cs) && stMap.get(cs) != ct)
                    || (tsMap.containsKey(ct) && tsMap.get(ct) != cs);
            if (notEq) {
                return false;
            }
            // 构建映射
            tsMap.put(ct, cs);
            stMap.put(cs, ct);
        }
        return true;
    }

    /**
     * 290.单词规律
     * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。
     * <p>
     * 示例 1:
     * 输入: pattern = "abba", s = "dog cat cat dog"
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: pattern = "abba", s = "dog cat cat fish"
     * 输出: false
     * <p>
     * 示例 3:
     * 输入: pattern = "aaaa", s = "dog cat cat dog"
     * 输出: false
     */
    public boolean wordPattern(String pattern, String s) {
        // 处理边界
        String[] split = s.split(" ");
        if (split.length != pattern.length()) {
            return false;
        }
        // 定义双向映射
        Map<Character, String> p2s = new HashMap<>(64);
        Map<String, Character> s2p = new HashMap<>(64);
        for (int i = 0; i < pattern.length(); i++) {
            char pc = pattern.charAt(i);
            String ss = split[i];
            // p2s 映射非空，判断映射值是否相等
            if (p2s.containsKey(pc)) {
                if (!p2s.get(pc).equals(ss)) {
                    return false;
                }
            } else {
                // p2s 映射空，则加入映射
                p2s.put(pc, ss);
            }

            // s2p 映射非空，判断映射值是否相同
            if (s2p.containsKey(ss)) {
                if (s2p.get(ss) != pc) {
                    return false;
                }
            } else {
                s2p.put(ss, pc);
            }
        }
        // 所有映射关系都匹配，则返回 true
        return true;
    }

    /**
     * 242. 有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     * <p>
     * 示例 1:
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: s = "rat", t = "car"
     * 输出: false
     * <p>
     * 示例 3:
     * 输入: s = "a", t = "a"
     * 输出: true
     * <p>
     *
     * 由于s、t仅包含小写字母，可以考虑长度26的数组做映射，key：字符下标，value：字符出现的次数
     */
    public boolean isAnagram(String s, String t) {
        // 处理边界
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        // 初始化映射（index = 字符，value = 字符出现次数）
        int[] cnt = new int[26];
        // t 映射
        for (char c : t.toCharArray()) {
            cnt[c - 'a']++;
        }
        // 判断 s c 字符出现的次数是否一致
        for (char c : s.toCharArray()) {
            cnt[c - 'a']--;
            if (cnt[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 49. 字母异位词分组
     * 给你一个字符串数组，请你将字母异位词组合在一起。可以按任意顺序返回结果列表。
     * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母都恰好只用一次。
     * <p>
     * 示例 1:
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        // 处理边界
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        // hashMap，key = sortedStr，value = strList
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            // 排序字符串，下标是字符顺序，值是字符出现次数
            char[] chars = new char[26];
            for (char c : str.toCharArray()) {
                chars[c - 'a']++;
            }
            // sortedStr 作为 key
            String sortedKey = String.valueOf(chars);
            map.computeIfAbsent(sortedKey, k -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 219.存在重复元素II
     * <p>
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且
     * abs(i-j) <= k 。
     * 如果存在，返回 true ；否则，返回 false 。
     * <p>
     * 示例 1：
     * 输入：nums = [1,2,3,1], k = 3
     * 输出：true
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // 数组值、下标的映射
        Map<Integer, Integer> valIndexMap = new HashMap<>(64);
        for (int i = 0; i < nums.length; i++) {
            // 包含重复元素，且 abs(i - j) <= k
            if (valIndexMap.containsKey(nums[i]) && (i - valIndexMap.get(nums[i]) <= k)) {
                return true;
            }
            // 将遍历过的元素放入map，key=val,value=index
            valIndexMap.put(nums[i], i);
        }
        return false;
    }

    /**
     * 128. 最长连续序列
     * <p>
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     * 示例 1：
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     */
    public int longestConsecutive(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 将数组数字加入 set
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        // 定义最长连胜
        int longestStreak = 0;
        for (int num : set) {
            // 找到连续序列起点（只有当前数字的前一个数字不存在时，才可能是连续序列的起点）
            if (!set.contains(num - 1)) {
                // 定义当前最长连胜
                int currStreak = 1;
                int currNum = num;
                // 向右扩大连续数列
                while (set.contains(++currNum)) {
                    currStreak++;
                }
                longestStreak = Math.max(longestStreak, currStreak);
            }
        }
        return longestStreak;
    }

    /**
     * 13. 罗马数字转整数 - 从ArrayStrSolution移入
     * <p>
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * <p>
     * 核心思想：HashMap映射 + 特殊情况处理
     * 1. 使用HashMap存储每个罗马字符对应的数值
     * 2. 遍历字符串，如果当前字符值小于下一个字符值，则减去当前值（特殊情况）
     * 3. 否则加上当前值（正常情况）
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入: s = "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
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
     * 560. 和为 K 的子数组 - 从ArrayStrSolution移入
     * <p>
     * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的子数组的个数 。
     * 子数组是数组中元素的连续非空序列。
     * <p>
     * 核心思想：前缀和 + HashMap
     * 1. 前缀和是从数组起点到当前下标元素的累加和，用于快速计算某一段子数组的总和
     * 2. 假设 sum[i] 是从起点到下标 i 的前缀和，sum[j] 是从起点到下标 j 的前缀和（j > i）
     * 3. 子数组 nums[i+1...j] 的和为 k，则有：sum[j] - sum[i] = k
     * 4. 使用HashMap存储前缀和的出现次数，以快速查询是否存在满足条件的 sum[i]
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(n)
     * <p>
     * 输入：nums = [1,1,1], k = 2
     * 输出：2
     */
    public int subarraySum(int[] nums, int k) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 初始化前缀和，key=前缀和，value=频次
        Map<Integer, Integer> prefixCount = new HashMap<>();
        // 和为 0 的频次是 1
        prefixCount.put(0, 1);
        // 次数，map 中的 key
        int prefixSum = 0;
        int count = 0;
        for (int num : nums) {
            // 更新当前前缀和
            prefixSum += num;
            // sum[j] - sum[i] = k，符合条件，累加和
            if (prefixCount.containsKey(prefixSum - k)) {
                count += prefixCount.get(prefixSum - k);
            }
            // 更新前缀和及频次
            prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }

    /**
     * 560. 和为 K 的子数组 - 暴力解法（用于对比）
     * <p>
     * 时间复杂度：O(n²)，空间复杂度：O(1)
     */
    public int subarraySumBruteForce(int[] nums, int k) {
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

}
