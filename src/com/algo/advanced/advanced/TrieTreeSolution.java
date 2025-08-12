package com.algo.advanced.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 难度级别: Advanced
 * 分类: Advanced
 * 
 * @author liangjun
 **/
public class TrieTreeSolution {
    class Trie {
        /**
         * 下一层 trie
         */
        Trie[] nextLevel;
        /**
         * 是否最后一层的节点
         */
        boolean isOver;

        public Trie() {
            this.nextLevel = new Trie[26];
            this.isOver = false;
        }

        public void insert(String word) {
            Trie node = this;
            char[] chars = word.toCharArray();
            // 遍历字符，加入每一层
            for (char c : chars) {
                // 计算当前字符的索引
                int index = c - 'a';
                if (node.nextLevel[index] == null) {
                    // 设置下一层的值，索引：index，值：c
                    node.nextLevel[index] = new Trie();
                }
                // 继续遍历下一个字符
                node = node.nextLevel[index];
            }
            // 已经遍历到末尾了
            node.isOver = true;
        }

        public boolean search(String word) {
            Trie node = this.prefix(word);
            return node != null && node.isOver;
        }

        public boolean startsWith(String prefix) {
            return this.prefix(prefix) != null;
        }

        private Trie prefix(String word) {
            Trie node = this;
            // 遍历字符，查找是否每一层都存在
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                // 找不到直接返回 null
                if (node.nextLevel[index] == null) {
                    return null;
                }
                node = node.nextLevel[index];
            }
            return node;
        }
    }

    class WordDictionary {
        Trie node;

        public WordDictionary() {
            node = new Trie();
        }

        public void addWord(String word) {
            node.insert(word);
        }

        public boolean search(String word) {
            return dfs(node, word, 0);
        }

        private boolean dfs(Trie node, String word, int index) {
            // 设置终止条件
            if (index == word.length()) {
                return node.isOver;
            }

            char ch = word.charAt(index);
            // 处理通配符.
            if ('.' == ch) {
                // 通配符的情况下递归尝试所有子节点
                for (int i = 0; i < 26; i++) {
                    Trie child = node.nextLevel[i];
                    if (child != null && dfs(child, word, index + 1)) {
                        return true;
                    }
                }
                // 所有路径都不匹配返回 false
                return false;
            } else {
                int idx = ch - 'a';
                Trie child = node.nextLevel[idx];
                if (child != null && dfs(child, word, index + 1)) {
                    return true;
                }
            }

            return false;
        }
    }

}
