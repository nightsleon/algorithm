package com.algo.beginner.string;

import java.util.*;

/**
 * 难度级别: Beginner
 * 分类: String (字符串处理算法)
 * 
 * 字符串处理算法核心思想：
 * - 字符串遍历、匹配、转换操作
 * - 常用技巧：双指针、排序、字符映射、模式匹配
 *
 * @author liangjun
 **/
public class StringSolution {

    /**
     * 14. 最长公共前缀 - 从ArrayStrSolution移入
     * <p>
     * 编写一个函数来查找字符串数组中的最长公共前缀，如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 核心思想：排序 + 首尾比较
     * 1. 对字符串数组进行排序，排序后第一个元素是字典序最小的，最后一个元素是字典序最大的
     * 2. 比较第一个和最后一个字符串的公共前缀
     * 3. 如果这两个字符串有公共前缀，那么所有字符串都有这个公共前缀
     * <p>
     * 时间复杂度：O(S * log n)，S是所有字符串长度之和，空间复杂度：O(1)
     * <p>
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     */
    public String longestCommonPrefix(String[] strs) {
        // 处理边界
        if (strs == null || strs.length == 0) {
            return "";
        }
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
     * 58. 最后一个单词的长度 - 从ArrayStrSolution移入
     * <p>
     * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度
     * <p>
     * 核心思想：反向遍历
     * 1. 先去除字符串两端的空格
     * 2. 从字符串末尾开始遍历，统计字符个数
     * 3. 遇到空格时停止，返回计数
     * <p>
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * <p>
     * 输入：s = "Hello World"
     * 输出：5
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
     * 28. 找出字符串中第一个匹配项的下标 - 从ArrayStrSolution移入
     * <p>
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
     * 如果 needle 不是 haystack 的一部分，则返回  -1 。
     * <p>
     * 核心思想：暴力匹配算法（Brute Force）
     * 1. 外层循环遍历haystack的每个可能起始位置
     * 2. 内层循环比较从该位置开始的子串是否与needle匹配
     * 3. 如果完全匹配，返回起始位置；否则继续下一个位置
     * <p>
     * 时间复杂度：O(n*m)，空间复杂度：O(1)，其中n=haystack长度，m=needle长度
     * <p>
     * 注意：还有更高效的KMP算法，时间复杂度O(n+m)
     * <p>
     * 输入：haystack = "sadbutsad", needle = "sad"
     * 输出：0
     * 解释："sad" 在下标 0 和 6 处匹配。第一个匹配项的下标是 0 ，所以返回 0 。
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
     * KMP算法实现 - strStr的优化版本
     * <p>
     * 核心思想：KMP（Knuth-Morris-Pratt）算法
     * 1. 预处理模式串，构建部分匹配表（前缀函数）
     * 2. 利用已匹配的信息，避免不必要的回退
     * 3. 当匹配失败时，根据部分匹配表跳过已知不匹配的位置
     * <p>
     * 时间复杂度：O(n+m)，空间复杂度：O(m)
     */
    public int strStrKMP(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        
        // 构建部分匹配表
        int[] lps = buildLPS(needle);
        
        int i = 0; // haystack指针
        int j = 0; // needle指针
        
        while (i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            
            if (j == needle.length()) {
                return i - j; // 找到匹配
            } else if (i < haystack.length() && haystack.charAt(i) != needle.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1;
    }
    
    /**
     * 构建KMP算法的部分匹配表（LPS数组）
     * LPS[i] = needle[0...i]的最长相等前后缀长度
     */
    private int[] buildLPS(String needle) {
        int[] lps = new int[needle.length()];
        int len = 0; // 前一个LPS值
        int i = 1;
        
        while (i < needle.length()) {
            if (needle.charAt(i) == needle.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
