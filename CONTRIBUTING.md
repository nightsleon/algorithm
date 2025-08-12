# 🤝 贡献指南

感谢您对算法学习项目的关注！我们欢迎所有形式的贡献，包括但不限于：

- 🐛 报告 Bug
- 💡 提出新功能建议
- 📝 改进文档
- 🔧 添加新算法
- 🧪 添加测试用例
- 🌍 翻译文档

## 📋 贡献前准备

### 1. 环境设置
确保您的开发环境满足以下要求：
- Java JDK 8 或更高版本
- Git
- 您喜欢的 IDE (推荐 IntelliJ IDEA)

### 2. Fork 项目
1. 访问项目主页
2. 点击右上角的 "Fork" 按钮
3. 将项目 Fork 到您的 GitHub 账户

### 3. 克隆项目
```bash
git clone https://github.com/nightsleon/algorithm.git
cd algorithm
```

## 🔧 开发流程

### 1. 创建分支
```bash
# 确保在 main 分支
git checkout main
git pull origin main

# 创建新分支
git checkout -b feature/your-feature-name
# 或者
git checkout -b fix/your-bug-fix
```

### 2. 开发规范

#### 代码规范
- 遵循 Java 编码规范
- 使用有意义的变量名和函数名
- 添加适当的注释
- 确保代码可读性

#### 算法文件规范
每个算法文件应包含：

```java
/**
 * 题目编号. 题目名称
 * 
 * 题目描述：
 * 详细描述题目要求和约束条件
 * 
 * 示例：
 * 输入: [示例输入]
 * 输出: [示例输出]
 * 
 * 难度：Easy/Medium/Hard
 * 标签：相关标签
 * 
 * @author 您的名字
 */
public class AlgorithmName {
    
    /**
     * 方法一：解题思路名称
     * 
     * 解题思路：
     * 1. 第一步
     * 2. 第二步
     * 3. 第三步
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public ReturnType methodName(Parameters params) {
        // 实现代码
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        AlgorithmName solution = new AlgorithmName();
        
        // 测试用例1
        // 测试用例2
        // 边界条件测试
    }
}

/**
 * 相关题目推荐：
 * 1. 相关题目1 - 简要描述
 * 2. 相关题目2 - 简要描述
 * 
 * 学习要点：
 * 1. 核心思想
 * 2. 关键技巧
 * 3. 注意事项
 */
```

#### 目录结构规范
- 新算法应放在正确的难度级别和分类目录下
- 文件名应使用 PascalCase 命名
- 类名应与文件名一致

### 3. 提交代码
```bash
# 添加修改的文件
git add .

# 提交更改
git commit -m "feat: add new algorithm for two sum problem"
# 或者
git commit -m "fix: correct time complexity analysis in binary search"
```

#### 提交信息规范
使用 [Conventional Commits](https://www.conventionalcommits.org/) 规范：

- `feat:` 新功能
- `fix:` 修复 Bug
- `docs:` 文档更新
- `style:` 代码格式调整
- `refactor:` 代码重构
- `test:` 添加测试
- `chore:` 构建过程或辅助工具的变动

### 4. 推送代码
```bash
git push origin feature/your-feature-name
```

### 5. 创建 Pull Request
1. 访问您的 GitHub 仓库
2. 点击 "Compare & pull request"
3. 填写 PR 描述

#### PR 描述模板
```markdown
## 📝 描述
简要描述您的更改

## 🔧 更改类型
- [ ] Bug 修复
- [ ] 新功能
- [ ] 文档更新
- [ ] 代码重构
- [ ] 测试添加

## 📋 检查清单
- [ ] 代码遵循项目规范
- [ ] 添加了必要的注释
- [ ] 更新了相关文档
- [ ] 添加了测试用例
- [ ] 通过了所有测试

## 🧪 测试
描述您如何测试这些更改

## 📸 截图 (如果适用)
添加相关截图

## 🔗 相关问题
关闭相关问题 #123
```

## 📝 文档贡献

### README 更新
- 更新算法统计信息
- 添加新算法到分类列表
- 更新学习路径建议

### 算法文档
- 确保每个算法都有详细的注释
- 添加时间复杂度和空间复杂度分析
- 提供相关题目推荐

## 🧪 测试贡献

### 测试用例要求
- 包含正常情况测试
- 包含边界条件测试
- 包含异常情况测试
- 测试用例应覆盖所有代码路径

### 测试示例
```java
public static void main(String[] args) {
    Solution solution = new Solution();
    
    // 正常情况测试
    int[] nums1 = {2, 7, 11, 15};
    int target1 = 9;
    int[] result1 = solution.twoSum(nums1, target1);
    System.out.println("测试1: " + Arrays.toString(result1));
    
    // 边界条件测试
    int[] nums2 = {3, 3};
    int target2 = 6;
    int[] result2 = solution.twoSum(nums2, target2);
    System.out.println("测试2: " + Arrays.toString(result2));
    
    // 异常情况测试
    int[] nums3 = {};
    int target3 = 0;
    int[] result3 = solution.twoSum(nums3, target3);
    System.out.println("测试3: " + Arrays.toString(result3));
}
```

## 🐛 Bug 报告

如果您发现了 Bug，请创建一个 Issue 并包含以下信息：

### Bug 报告模板
```markdown
## 🐛 Bug 描述
详细描述 Bug 的表现

## 🔄 重现步骤
1. 第一步
2. 第二步
3. 第三步

## 📱 预期行为
描述您期望看到的行为

## 💻 实际行为
描述实际发生的行为

## 📋 环境信息
- Java 版本：
- 操作系统：
- IDE：

## 📸 截图
如果适用，请添加截图

## 📝 其他信息
任何其他相关信息
```

## 💡 功能建议

如果您有新功能建议，请创建一个 Issue 并包含：

### 功能建议模板
```markdown
## 💡 功能描述
详细描述您建议的功能

## 🎯 使用场景
描述这个功能的使用场景

## 🔧 实现建议
如果有的话，提供实现建议

## 📋 相关资源
任何相关的链接或资源
```

## 📞 联系我们

如果您有任何问题或需要帮助，请：

1. 查看 [Issues](https://github.com/your-username/algorithm/issues) 页面
2. 创建新的 Issue
3. 发送邮件到 [your-email@example.com]

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者！您的贡献让这个项目变得更好。

---

⭐ 如果这个贡献指南对您有帮助，请给项目一个星标！
