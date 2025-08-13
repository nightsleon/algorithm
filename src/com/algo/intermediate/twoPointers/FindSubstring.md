## 📄 FindSubstring.md 完整内容

# 30. 串联所有单词的子串 (Substring with Concatenation of All Words)

## �� 题目描述

给定一个字符串 `s` 和一个字符串数组 `words`，找出 `s` 中所有串联子串的起始位置。

**串联子串**：由 `words` 中所有单词按任意顺序连接而成。

### 示例

```
输入：s = "barfoothefoobarman", words = ["foo","bar"]
输出：[0,9]

解释：
- 位置 0：子串 "barfoo" 是 ["bar","foo"] 的串联
- 位置 9：子串 "foobar" 是 ["foo","bar"] 的串联
```

## �� 算法核心思想

这是一个**滑动窗口 + 哈希表**的经典应用，需要找到字符串中所有单词的串联子串。

### 核心策略
1. **多起点滑动窗口**：从不同起点开始检查
2. **哈希表计数**：统计单词出现次数
3. **动态窗口调整**：根据匹配情况调整窗口大小

## �� 算法思路详解

### 1. 预处理阶段

```java
// 计算关键参数
int wordLen = words[0].length();        // 单词长度
int wordCount = words.length;           // 单词数量  
int wordTotalLen = wordLen * wordCount; // 总长度

// 统计 words 中每个单词的出现次数
Map<String, Integer> wordCountMap = new HashMap<>();
for (String word : words) {
    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
}
```

### 2. 多起点策略

**为什么需要多个起点？**

考虑字符串 `"abcdef"`，单词长度 3：

```
起点 0: a b c | d e f | g h i | j k
起点 1: a | b c d | e f g | h i j | k  
起点 2: a b | c d e | f g h | i j k
```

不同起点会得到不同的单词分割方式，必须都检查！

### 3. 滑动窗口过程

以起点 `i = 0` 为例：

```
字符串: "barfoothefoobarman"
words: ["foo","bar"]
wordLen: 3, wordCount: 2

初始状态:
left = 0, right = 0
seenCountMap = {}

步骤1: right = 0, 提取 "bar"
- rightWord = "bar"
- wordCountMap 包含 "bar"
- seenCountMap["bar"] = 1
- right = 3
- 检查: (3-0)/3 = 1 < 2，继续

步骤2: right = 3, 提取 "foo"  
- rightWord = "foo"
- wordCountMap 包含 "foo"
- seenCountMap["foo"] = 1
- right = 6
- 检查: (6-0)/3 = 2 == 2，找到匹配！
- 添加 left = 0 到结果

步骤3: right = 6, 提取 "the"
- rightWord = "the" 
- wordCountMap 不包含 "the"
- 清空 seenCountMap
- left = right = 6
```

### 4. 关键优化技巧

#### **提前跳过不匹配**
```java
if (!wordCountMap.containsKey(rightWord)) {
    seenCountMap.clear();  // 清空计数
    left = right;          // 跳过不可能匹配的部分
}
```

#### **处理重复单词**
```java
while (seenCountMap.get(rightWord) > wordCountMap.get(rightWord)) {
    String leftWord = s.substring(left, left + wordLen);
    seenCountMap.put(leftWord, seenCountMap.get(leftWord) - 1);
    left += wordLen;  // 收缩窗口
}
```

## 🧠 算法正确性证明

### 1. 为什么多起点是必要的？

考虑字符串 `"abcdef"`，单词长度 3：
- 起点 0：`"abc"`, `"def"`
- 起点 1：`"bcd"`, `"efa"` (如果字符串更长)
- 起点 2：`"cde"`, `"fab"` (如果字符串更长)

不同起点会产生不同的单词分割，必须都检查。

### 2. 为什么滑动窗口是正确的？

- **扩展条件**：当前单词在 words 中且未超过预期次数
- **收缩条件**：当前单词出现次数超过预期
- **匹配条件**：窗口内单词数量等于 words 数量

### 3. 为什么不会遗漏解？

- 所有可能的起点都被检查
- 滑动窗口保证了所有可能的窗口大小都被考虑
- 哈希表保证了单词匹配的准确性

## 📊 复杂度分析

- **时间复杂度**: O(n × m × k)
  - n：字符串长度
  - m：单词数量  
  - k：单词长度
- **空间复杂度**: O(m × k)
  - 哈希表存储单词

## 🎯 算法优势

1. **避免暴力枚举**：不需要检查所有可能的单词排列
2. **滑动窗口优化**：动态调整窗口大小
3. **哈希表加速**：O(1) 时间查找单词
4. **提前剪枝**：遇到不匹配单词立即跳过

## 💻 代码实现要点

```java
public List<Integer> findSubstring(String s, String[] words) {
    List<Integer> res = new ArrayList<>();
    
    // 1. 预处理
    int wordLen = words[0].length();
    int wordCount = words.length;
    int wordTotalLen = wordLen * wordCount;
    
    // 2. 统计单词出现次数
    Map<String, Integer> wordCountMap = new HashMap<>();
    for (String word : words) {
        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
    }
    
    // 3. 多起点遍历
    for (int i = 0; i < wordLen; i++) {
        int left = i, right = i;
        Map<String, Integer> seenCountMap = new HashMap<>();
        
        // 4. 滑动窗口
        while (right + wordLen <= s.length()) {
            String rightWord = s.substring(right, right + wordLen);
            right += wordLen;
            
            if (wordCountMap.containsKey(rightWord)) {
                // 扩展窗口
                seenCountMap.put(rightWord, seenCountMap.getOrDefault(rightWord, 0) + 1);
                
                // 处理重复单词
                while (seenCountMap.get(rightWord) > wordCountMap.get(rightWord)) {
                    String leftWord = s.substring(left, left + wordLen);
                    seenCountMap.put(leftWord, seenCountMap.get(leftWord) - 1);
                    left += wordLen;
                }
                
                // 检查是否找到匹配
                if ((right - left) / wordLen == wordCount) {
                    res.add(left);
                }
            } else {
                // 重置窗口
                seenCountMap.clear();
                left = right;
            }
        }
    }
    
    return res;
}
```

## 🔍 完整示例演示

```
输入: s = "barfoothefoobarman", words = ["foo","bar"]

起点 0 的处理:
"bar" -> 匹配，seenCount["bar"]=1
"foo" -> 匹配，seenCount["foo"]=1, 找到解 [0]

起点 1 的处理:
"arf" -> 不匹配，重置
"oot" -> 不匹配，重置
...

起点 2 的处理:
"rfo" -> 不匹配，重置
"oot" -> 不匹配，重置
...

最终结果: [0, 9]
```

## 🚀 优化技巧

### 1. 边界条件优化
```java
// 单词总长度大于字符串长度，直接返回
if (wordTotalLen > s.length()) {
    return res;
}
```

### 2. 哈希表初始容量优化
```java
Map<String, Integer> wordCountMap = new HashMap<>(64);
Map<String, Integer> seenCountMap = new HashMap<>(64);
```

### 3. 提前退出条件
```java
// 如果剩余长度不足，提前退出
if (right + wordLen > s.length()) {
    break;
}
```

## �� 常见错误

1. **忘记多起点**：只从一个起点开始检查
2. **重复单词处理错误**：没有正确处理单词重复出现的情况
3. **窗口大小计算错误**：没有正确计算窗口内单词数量
4. **边界条件遗漏**：没有处理字符串长度不足的情况

## �� 相关题目

1. **3. Longest Substring Without Repeating Characters** - 无重复字符最长子串
2. **209. Minimum Size Subarray Sum** - 长度最小的子数组
3. **76. Minimum Window Substring** - 最小覆盖子串
4. **438. Find All Anagrams in a String** - 找到字符串中所有字母异位词

## 📚 学习要点

1. **滑动窗口技巧**：动态调整窗口大小
2. **哈希表应用**：统计和比较元素出现次数
3. **多起点策略**：避免遗漏可能的解
4. **字符串处理**：子串提取和比较
5. **边界条件处理**：各种异常情况的处理

---

这个算法巧妙地将复杂的排列问题转化为滑动窗口问题，通过多起点遍历确保不遗漏任何可能的解，是一个非常优秀的实现！
```