package com.algo.beginner.string;

import java.util.Arrays;

/**
 * 242. 有效的字母异位词
 * 
 * 题目描述：
 * 给定两个字符串 s 和 t，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 * 
 * 示例：
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 
 * 输入: s = "rat", t = "car"
 * 输出: false
 * 
 * 难度：Easy
 * 标签：哈希表、字符串、排序
 * 
 * @author liangjun
 */
public class ValidAnagram {
    
    /**
     * 方法一：字符计数法（推荐）
     * 
     * 解题思路：
     * 1. 使用长度为26的数组记录每个字符的出现次数
     * 2. 遍历字符串s，增加字符计数
     * 3. 遍历字符串t，减少字符计数
     * 4. 如果所有字符计数都为0，则为字母异位词
     * 
     * 时间复杂度：O(n)，其中 n 是字符串长度
     * 空间复杂度：O(1)，使用固定大小的数组
     */
    public boolean isAnagram(String s, String t) {
        // 边界条件检查
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        
        // 字符计数数组，index = 字符 - 'a'，value = 出现次数
        int[] charCount = new int[26];
        
        // 统计字符串s中每个字符的出现次数
        for (char c : s.toCharArray()) {
            charCount[c - 'a']++;
        }
        
        // 检查字符串t中的字符
        for (char c : t.toCharArray()) {
            charCount[c - 'a']--;
            // 如果某个字符计数变为负数，说明t中该字符出现次数过多
            if (charCount[c - 'a'] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 方法二：排序法
     * 
     * 解题思路：
     * 1. 将两个字符串转换为字符数组
     * 2. 对字符数组进行排序
     * 3. 比较排序后的数组是否相等
     * 
     * 时间复杂度：O(n log n)，排序的时间复杂度
     * 空间复杂度：O(n)，需要额外的字符数组空间
     */
    public boolean isAnagramBySort(String s, String t) {
        // 边界条件检查
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        
        // 转换为字符数组并排序
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        
        // 比较排序后的数组
        return Arrays.equals(sChars, tChars);
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        ValidAnagram solution = new ValidAnagram();
        
        // 测试用例1：有效的字母异位词
        String s1 = "anagram";
        String t1 = "nagaram";
        System.out.println("测试1: " + solution.isAnagram(s1, t1)); // 期望输出: true
        
        // 测试用例2：无效的字母异位词
        String s2 = "rat";
        String t2 = "car";
        System.out.println("测试2: " + solution.isAnagram(s2, t2)); // 期望输出: false
        
        // 测试用例3：空字符串
        String s3 = "";
        String t3 = "";
        System.out.println("测试3: " + solution.isAnagram(s3, t3)); // 期望输出: true
    }
}

/**
 * 相关题目推荐：
 * 1. 49. 字母异位词分组 - 将字母异位词分组
 * 2. 438. 找到字符串中所有字母异位词 - 滑动窗口应用
 * 3. 242. 有效的字母异位词 - 本题
 * 
 * 学习要点：
 * 1. 字符计数是处理字符串问题的常用技巧
 * 2. 固定大小的数组比HashMap更高效（当字符集较小时）
 * 3. 排序法虽然简单，但时间复杂度较高
 * 4. 边界条件检查很重要（null、长度不等）
 */
