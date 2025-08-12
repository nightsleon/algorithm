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
     * 338.救赎信：
     * 判断 ransomNote 能不能由 magazine 里面的字符构成。magazine 中的每个字符只能在 ransomNote 中使用一次
     * 都是小写字母组成
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
     * paper -> title 是同构字符串，paper -> tttle 不是同构字符串
     */
    public boolean isIsomorphic(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        // s -> t 和 t -> s 映射
        Map<Character, Character> stMap = new HashMap<>(64);
        Map<Character, Character> tsMap = new HashMap<>(64);
        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);
            boolean notEq = (stMap.containsKey(cs) && stMap.get(cs) != ct) || (tsMap.containsKey(ct) && tsMap.get(ct) != cs);
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
     * 290.单次规律
     * <p>
     * "abba" -> "dog cat cat dog" : true
     * "abba" -> "dog cat cat fish" : false
     * "aaaa" -> "dog cat cat dog" : false
     * "abba" ->  "dog dog dog dog" : false
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
     * anagram -> nagaram : true
     * rat - > car : false
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
     * 1.两数和：优先考虑哈希表
     */
    public int[] twoSum(int[] nums, int target) {
        // 值、下标的map
        Map<Integer, Integer> valIndexMap = new HashMap<>(64);
        for (int i = 0; i < nums.length; i++) {
            // 当前值 nums[i]，anotherVal = 目标值 - 当前值
            int anotherVal = target - nums[i];
            // 找到另一个值，则直接返回另一个元素的下标和当前下标
            if (valIndexMap.containsKey(anotherVal)) {
                return new int[]{valIndexMap.get(anotherVal), i};
            }
            // 将遍历过的元素放入map
            valIndexMap.put(nums[i], i);
        }
        return new int[]{-1, -1,};
    }

    /**
     * 219.存在重复元素II
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
     * 如果存在，返回 true ；否则，返回 false 。
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
     * 找到连续序列起点，向后遍历
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
     * 49. 字母异位词分组
     * <p>
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
}
