# 🚀 算法学习项目 (Algorithm Learning Project)

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Stars](https://img.shields.io/github/stars/nightsleon/algorithm.svg)](https://github.com/nightsleon/algorithm)
[![Forks](https://img.shields.io/github/forks/nightsleon/algorithm.svg)](https://github.com/nightsleon/algorithm)

> 📚 一个专为算法初学者设计的Java算法学习项目，包含从基础到高级的完整算法实现和详细解析。

## 📖 项目简介

本项目是一个系统性的算法学习资源，旨在帮助初学者循序渐进地掌握各种算法和数据结构。项目按照难度级别和算法类型进行组织，每个算法都包含详细的解题思路、时间复杂度分析和相关题目推荐。

### ✨ 项目特色

- 🎯 **分级学习**: 按难度分为初级、中级、高级三个层次
- 📝 **详细注释**: 每个算法都有完整的解题思路和复杂度分析
- 🔗 **相关推荐**: 提供相关题目和进阶学习建议
- 🧪 **测试用例**: 包含完整的测试用例和边界条件
- 📚 **学习路径**: 提供清晰的学习路径和前置知识要求

## 🏗️ 项目结构

```
src/com/algo/
├── AlgorithmIndex.java          # 主索引文件
├── common/                      # 公共数据结构
│   ├── ListNode.java           # 链表节点
│   ├── TreeNode.java           # 二叉树节点
│   └── LRUCache.java           # LRU缓存实现
├── beginner/                    # 初级算法 (Easy)
│   ├── array/                   # 数组基础操作
│   │   ├── ArrayStrSolution.java
│   │   └── README.md
│   ├── string/                  # 字符串处理
│   │   └── README.md
│   ├── math/                    # 数学基础
│   │   ├── MathSolution.java
│   │   ├── BitOperationSolution.java
│   │   └── README.md
│   └── README.md
├── intermediate/                # 中级算法 (Medium)
│   ├── linkedlist/              # 链表操作
│   │   ├── LinkedListSolution.java
│   │   └── README.md
│   ├── tree/                    # 树结构
│   │   ├── BinaryTreeSolution.java
│   │   └── README.md
│   ├── hashmap/                 # 哈希表应用
│   │   ├── HashMapSolution.java
│   │   └── README.md
│   ├── twoPointers/             # 双指针技巧
│   │   ├── DoublePointSolution.java
│   │   └── README.md
│   ├── binarySearch/            # 二分查找
│   │   ├── BinarySearchSolution.java
│   │   └── README.md
│   ├── stack/                   # 栈和队列
│   │   ├── StackSolution.java
│   │   └── README.md
│   └── README.md
└── advanced/                    # 高级算法 (Hard)
    ├── dynamicProgramming/      # 动态规划
    │   ├── DynamicProgressingSolution.java
    │   └── README.md
    ├── backtracking/            # 回溯算法
    │   ├── BacktrackSolution.java
    │   └── README.md
    ├── graph/                   # 图算法
    │   ├── GraphSolution.java
    │   ├── SegmentSorter.java
    │   ├── TopologicalSortKahn.java
    │   └── README.md
    ├── sort/                    # 排序算法
    │   ├── SortSolution.java
    │   └── README.md
    ├── matrix/                  # 矩阵操作
    │   ├── MatrixSolution.java
    │   └── README.md
    ├── range/                   # 区间问题
    │   ├── RangeSolution.java
    │   └── README.md
    ├── greedy/                  # 贪心算法
    │   ├── GreedySolution.java
    │   └── README.md
    ├── advanced/                # 高级数据结构
    │   ├── TrieTreeSolution.java
    │   ├── DivideSolution.java
    │   └── README.md
    └── README.md
```

## 🎯 学习路径

### 📚 初级算法 (Beginner - Easy)
适合算法初学者，包含基础概念和简单算法。

**学习顺序**:
1. **数组基础** (`beginner/array/`) - 数组操作、双指针技巧
2. **数学基础** (`beginner/math/`) - 数学运算、位操作
3. **字符串处理** (`beginner/string/`) - 字符串操作、字符处理

**预计时间**: 2-3周

### 🔧 中级算法 (Intermediate - Medium)
适合有一定基础的学员，包含进阶技巧和数据结构。

**学习顺序**:
1. **哈希表应用** (`intermediate/hashmap/`) - 哈希表技巧
2. **双指针技巧** (`intermediate/twoPointers/`) - 双指针算法
3. **链表操作** (`intermediate/linkedlist/`) - 链表数据结构
4. **树结构** (`intermediate/tree/`) - 二叉树操作
5. **二分查找** (`intermediate/binarySearch/`) - 二分查找技巧
6. **栈和队列** (`intermediate/stack/`) - 栈队列应用

**预计时间**: 4-6周

### 🚀 高级算法 (Advanced - Hard)
适合有扎实基础的学员，包含复杂算法和高级技巧。

**学习顺序**:
1. **动态规划** (`advanced/dynamicProgramming/`) - DP算法
2. **回溯算法** (`advanced/backtracking/`) - 回溯技巧
3. **图算法** (`advanced/graph/`) - 图论算法
4. **排序算法** (`advanced/sort/`) - 经典排序
5. **矩阵操作** (`advanced/matrix/`) - 矩阵算法
6. **区间问题** (`advanced/range/`) - 区间处理
7. **贪心算法** (`advanced/greedy/`) - 贪心策略
8. **高级数据结构** (`advanced/advanced/`) - 高级数据结构

**预计时间**: 6-8周

## 🛠️ 环境要求

- **Java**: JDK 8 或更高版本
- **IDE**: IntelliJ IDEA, Eclipse, VS Code 等
- **构建工具**: Maven 或 Gradle (可选)

## 🚀 快速开始

### 1. 克隆项目
```bash
git clone https://github.com/nightsleon/algorithm.git
cd algorithm
```

### 2. 查看项目结构
```bash
# 编译并运行主索引
javac -cp src src/com/algo/AlgorithmIndex.java
java -cp src com.algo.AlgorithmIndex
```

### 3. 开始学习
按照学习路径，从 `beginner/` 开始，逐步学习到 `advanced/`。

## 📊 算法统计

| 难度级别 | 分类数量 | 算法数量 | 预计学习时间 |
|---------|---------|---------|-------------|
| 初级 (Easy) | 3 | 15+ | 2-3周 |
| 中级 (Medium) | 6 | 25+ | 4-6周 |
| 高级 (Hard) | 8 | 30+ | 6-8周 |

## 📝 算法分类详情

### 🔢 数组基础 (Array)
- 两数之和 (Two Sum)
- 移除元素 (Remove Element)
- 删除重复项 (Remove Duplicates)
- 轮转数组 (Rotate Array)
- 合并有序数组 (Merge Sorted Array)

### 🔤 字符串处理 (String)
- 有效字母异位词 (Valid Anagram)
- 同构字符串 (Isomorphic Strings)
- 最长公共前缀 (Longest Common Prefix)
- 验证回文串 (Valid Palindrome)
- 字符串匹配 (String Matching)

### 🧮 数学基础 (Math)
- 加一 (Plus One)
- x的平方根 (Sqrt)
- 快速幂 (Pow)
- 二进制求和 (Add Binary)
- 位操作 (Bit Operations)

### 🔗 链表操作 (LinkedList)
- 环形链表 (Linked List Cycle)
- 两数相加 (Add Two Numbers)
- 合并有序链表 (Merge Two Lists)
- 反转链表 (Reverse Linked List)
- 删除倒数第N个节点 (Remove Nth Node)

### 🌳 树结构 (Tree)
- 二叉树遍历 (Tree Traversal)
- 最大深度 (Maximum Depth)
- 二叉树直径 (Diameter of Binary Tree)
- 验证二叉搜索树 (Validate BST)
- 层序遍历 (Level Order Traversal)

### 🗃️ 哈希表应用 (HashMap)
- 字母异位词分组 (Group Anagrams)
- 最长连续序列 (Longest Consecutive Sequence)
- 两数之和 (Two Sum)
- 存在重复元素 (Contains Duplicate)

### 👆 双指针技巧 (Two Pointers)
- 三数之和 (3Sum)
- 盛最多水的容器 (Container With Most Water)
- 验证回文串 (Valid Palindrome)
- 判断子序列 (Is Subsequence)

### 🔍 二分查找 (Binary Search)
- 搜索插入位置 (Search Insert Position)
- 搜索二维矩阵 (Search a 2D Matrix)
- 寻找峰值 (Find Peak Element)
- 标准二分查找 (Binary Search)

### 📚 栈和队列 (Stack & Queue)
- 有效括号 (Valid Parentheses)
- 简化路径 (Simplify Path)
- 最小栈 (Min Stack)
- 逆波兰表达式 (Evaluate RPN)

### 🎯 动态规划 (Dynamic Programming)
- 爬楼梯 (Climbing Stairs)
- 最小路径和 (Minimum Path Sum)
- 背包问题 (Knapsack Problem)
- 最长递增子序列 (Longest Increasing Subsequence)

### 🔄 回溯算法 (Backtracking)
- 全排列 (Permutations)
- 子集 (Subsets)
- N皇后 (N-Queens)
- 组合总和 (Combination Sum)

### 🕸️ 图算法 (Graph)
- 岛屿数量 (Number of Islands)
- 广度优先搜索 (BFS)
- 深度优先搜索 (DFS)
- 拓扑排序 (Topological Sort)

### 📊 排序算法 (Sort)
- 选择排序 (Selection Sort)
- 冒泡排序 (Bubble Sort)
- 插入排序 (Insertion Sort)
- 归并排序 (Merge Sort)
- 快速排序 (Quick Sort)

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

### 贡献规范

- 添加新算法时，请包含详细的注释和测试用例
- 确保代码符合Java编码规范
- 更新相应的README文档
- 添加算法的时间复杂度和空间复杂度分析

## 📚 学习资源

### 推荐书籍
- 《算法导论》- Thomas H. Cormen
- 《算法》- Robert Sedgewick
- 《编程珠玑》- Jon Bentley
- 《剑指Offer》- 何海涛

### 在线平台
- [LeetCode](https://leetcode.com/) - 算法练习平台
- [力扣](https://leetcode.cn/) - 中文版LeetCode
- [牛客网](https://www.nowcoder.com/) - 算法练习
- [HackerRank](https://www.hackerrank.com/) - 编程挑战

### 可视化工具
- [VisuAlgo](https://visualgo.net/) - 算法可视化
- [Algorithm Visualizer](https://algorithm-visualizer.org/) - 算法可视化
- [Data Structure Visualizations](https://www.cs.usfca.edu/~galles/visualization/) - 数据结构可视化

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 👨‍💻 作者

**liangjun** - [GitHub](https://github.com/nightsleon)

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和学习者！

---

⭐ 如果这个项目对你有帮助，请给它一个星标！
