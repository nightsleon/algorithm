package com.algo.advanced.backtracking;

import com.algo.common.TreeNode;

import java.util.*;

/**
 * https://www.hello-algo.com/chapter_backtracking/ <br/>
 * 回溯算法（backtracking algorithm）是一种通过穷举来解决问题的方法，<br/>
 * 它的核心思想是从一个初始状态出发，暴力搜索所有可能的解决方案，当遇到正确的解则将其记录，直到找到解或者尝试了所有可能的选择都无法找到解为止。
 *
 * @author liangjun
 **/
public class BacktrackSolution {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        BacktrackSolution solution = new BacktrackSolution();
//        solution.searchPath();
        List<List<Integer>> permute = solution.permute(nums);
        System.out.println(Arrays.toString(permute.toArray()));
//        List<List<Integer>> subsets = solution.subsets(nums);
//        System.out.println(Arrays.toString(subsets.toArray()));
//        List<List<Integer>> lists = solution.subsetSumII(nums, 6);
//        System.out.println(Arrays.toString(lists.toArray()));
//        List<List<String>> lists = solution.solveNQueens(4);
//        System.out.println(Arrays.toString(lists.toArray()));

    }

    private List<List<Integer>> result = new ArrayList<>();
    private List<Integer> pth = new ArrayList<>();
    private final int target = 7;
    private final int exclude = 3;

    public void searchPath() {
        TreeNode root = new TreeNode().getTree();
        // 例题2：在二叉树中搜索所有值为 7 的节点，请返回根节点到这些节点的路径。
        // this.preOrder(root);
        // 例题3：在二叉树中搜索所有值为 7 的节点，请返回根节点到这些节点的路径，并要求路径中不包含值为 3 的节点。
        this.preOrder(root, exclude);
        System.out.println(Arrays.toString(result.toArray()));
    }

    /**
     * 尝试与回退
     * 前序遍历，根左右
     */
    public void preOrder(TreeNode root) {
        // 设置终止条件
        if (root == null) {
            return;
        }
        // 尝试
        pth.add(root.val);
        // 记录解
        if (target == root.val) {
            result.add(new ArrayList<>(pth));
        }
        preOrder(root.left);
        preOrder(root.right);
        // 回退
        pth.remove(pth.size() - 1);
    }

    public void preOrder(TreeNode root, int exclude) {
        // 设置终止条件
        if (root == null) {
            return;
        }
        // 剪枝
        if (root.val == exclude) {
            return;
        }
        // 尝试
        pth.add(root.val);
        // 记录解
        if (target == root.val) {
            result.add(new ArrayList<>(pth));
        }
        preOrder(root.left, exclude);
        preOrder(root.right, exclude);
        // 回退
        pth.remove(pth.size() - 1);
    }

    /**
     * 46.全排列
     *
     * @param nums 不重复数组
     * @return 全排列结果集
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // 处理边界
        if (nums == null || nums.length == 0) {
            return res;
        }
        // 定义变量 path、selected
        List<Integer> path = new ArrayList<>();
        boolean[] selected = new boolean[nums.length];
        // 调用递归遍历所有路径
        this.backtrack(res, path, selected, nums);
        return res;
    }

    private void backtrack(List<List<Integer>> res, List<Integer> path, boolean[] selected, int[] nums) {
        // 设置终止条件
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 遍历数组元素
        for (int i = 0; i < nums.length; i++) {
            // 剪枝
            if (selected[i]) {
                continue;
            }

            // 尝试选择元素，并设置为已经选择
            selected[i] = true;
            path.add(nums[i]);

            // 递归
            backtrack(res, path, selected, nums);

            // 回溯，重置状态，删减路径
            selected[i] = false;
            path.remove(path.size() - 1);
        }
    }

    // 所有不重复子集
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        this.backtrack(path, 0, nums, res);
        return res;
    }

    private void backtrack(List<Integer> path, int begin, int[] nums, List<List<Integer>> res) {
        // 所有路径
        res.add(new ArrayList<>(path));
        // 每次跳过同一层级的左侧元素，避免重复，例如[1,2,3] 第一轮 遍历1，则第二轮跳过 1 从 2 开始
        for (int i = begin; i < nums.length; i++) {
            // 尝试：添加元素
            path.add(nums[i]);
            backtrack(path, i + 1, nums, res);
            // 回退重置 path
            path.remove(path.size() - 1);
        }
    }

    /* 求解子集和 II */
    List<List<Integer>> subsetSumII(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(nums);
        this.backtrack(path, 0, target, nums, res);
        return res;
    }

    private void backtrack(List<Integer> path, int begin, int target, int[] nums, List<List<Integer>> res) {
        // 设置终止条件
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 剪枝，跳过之前选择的元素，例如[1,2,3] 第一轮 遍历1，则第二轮跳过 1 从 2 开始
        for (int i = begin; i < nums.length; i++) {
            // 剪枝，跳过相同元素
            if (i > begin && nums[i] == nums[i - 1]) {
                continue;
            }
            // 剪枝，集合元素超过 target
            if (target - nums[i] < 0) {
                break;
            }
            // 尝试：记录路径，更新 begin target
            path.add(nums[i]);
            // 递归
            backtrack(path, begin + 1, target - nums[i], nums, res);
            // 回退
            path.remove(path.size() - 1);
        }
    }

    /**
     * 39.组合总和
     *
     * @param candidates 候选元素
     * @param target     目标值
     * @return 复合条件的所有路径
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // 处理边界
        if (candidates == null || candidates.length == 0) {
            return res;
        }
        // 排序数组
        Arrays.sort(candidates);
        // 递归所有复合条件路径
        List<Integer> path = new ArrayList<>();
        this.combinationSumDfs(res, path, candidates, target, 0);
        return res;
    }

    private void combinationSumDfs(List<List<Integer>> res, List<Integer> path, int[] candidates, int target, int start) {
        // 设置终止条件
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 在递归调用中限制下一轮循环从当前位置开始（i）
        for (int i = start; i < candidates.length; i++) {
            // 剪枝：当前候选数大于剩余目标值，直接退出
            if (target - candidates[i] < 0) {
                break;
            }
            // 尝试选择元素
            path.add(candidates[i]);
            // 递归,继续从当前数开始（允许重复选择）
            combinationSumDfs(res, path, candidates, target - candidates[i], i);
            // 回溯
            path.remove(path.size() - 1);
        }
    }

    /**
     * 电话号码字母组合 dfs
     */
    // 数字字母映射
    private Map<Character, String> numLetterMap = new HashMap<>(32);
    // 字母组合
    private StringBuilder letterCombine = new StringBuilder();
    // 结果集
    private List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        // 处理边界
        if (digits == null || digits.length() == 0) {
            return res;
        }
        numLetterMap.put('2', "abc");
        numLetterMap.put('3', "def");
        numLetterMap.put('4', "ghi");
        numLetterMap.put('5', "jkl");
        numLetterMap.put('6', "mno");
        numLetterMap.put('7', "pqrs");
        numLetterMap.put('8', "tuv");
        numLetterMap.put('9', "wxyz");
        letterDfs(0, digits);
        return res;
    }

    private void letterDfs(int level, String digits) {
        // 设置终止条件，已经遍历到最后一层
        if (level == digits.length()) {
            res.add(letterCombine.toString());
            return;
        }
        String letter = numLetterMap.get(digits.charAt(level));
        // 遍历字母
        for (int i = 0; i < letter.length(); i++) {
            // 递归所有路径
            letterCombine.append(letter.charAt(i));
            letterDfs(level + 1, digits);
            // 回溯删除路径字母（一条完整的路径已经加入到结果集后回溯）
            letterCombine.deleteCharAt(level);
        }
    }

    /**
     * 77 组合
     *
     * @param n 1-n 的数字
     * @param k k 个数
     * @return 从 1-n 中返回 k 个数的组合
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        this.combineDfs(res, path, k, n, 1);
        return res;
    }

    private void combineDfs(List<List<Integer>> res, List<Integer> path, int k, int n, int begin) {
        // 设置终止条件
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 跳过上一轮已经选择的元素（简直）
        for (int i = begin; i <= n; i++) {
            // 选择当前元素
            path.add(i);
            // 递归从下一个数字 i + 1 开始
            combineDfs(res, path, k, n, i + 1);
            // 回溯
            path.remove(path.size() - 1);
        }
    }

    // N 皇后
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        // 初始化 queen 数组，存储每行的 Q 在第几列
        int[] queen = new int[n];
        Arrays.fill(queen, -1);
        Set<Integer> pie = new HashSet<>();
        Set<Integer> na = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        backtrack(res, n, 0, queen, pie, na, col);
        return res;
    }

    private void backtrack(List<List<String>> res, int n, int row, int[] queen, Set<Integer> pie, Set<Integer> na, Set<Integer> col) {
        // 设置终止条件
        if (row == n) {
            List<String> singleRes = new ArrayList<>();
            for (int i = 0; i < row; i++) {
                char[] singleRow = new char[n];
                Arrays.fill(singleRow, '.');
                singleRow[queen[i]] = 'Q';
                singleRes.add(new String(singleRow));
            }
            res.add(singleRes);
        }
        for (int c = 0; c < n; c++) {
            // left diagonal，做对角线：row + col 相等
            int ld = row + c;
            // right diagonal，右对角线：row -col 相等
            int rd = row - c;
            // 剪枝
            if (col.contains(c)) continue;
            if (pie.contains(ld)) continue;
            if (na.contains(rd)) continue;
            // 尝试添加第 row 行 queen，设置状态
            queen[row] = c;
            col.add(c);
            pie.add(ld);
            na.add(rd);
            // 递归
            backtrack(res, n, row + 1, queen, pie, na, col);
            // 回退，重置状态
            col.remove(c);
            pie.remove(ld);
            na.remove(rd);
        }
    }

    /**
     * 52. N 皇后 II
     *
     * @param n n*n 棋盘大小
     * @return 不能相互攻击的所有排列数量
     */
    public int totalNQueens(int n) {
        Set<Integer> pie = new HashSet<>();
        Set<Integer> na = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        return this.backtrack(n, 0, pie, na, col);
    }

    private int backtrack(int n, int row, Set<Integer> pie, Set<Integer> na, Set<Integer> col) {
        // 设置终止条件
        if (n == row) {
            return 1;
        }

        // 遍历复合条件的路径
        int count = 0;
        for (int c = 0; c < n; c++) {
            // left diagonal(人字的丿，左对角线)；right diagonal(㇏，又对角线)
            int ld = row + c;
            int rd = row - c;
            // 剪枝，同一列、同一左右对角线不能有重复
            if (col.contains(c)) {
                continue;
            }
            if (pie.contains(ld)) {
                continue;
            }
            if (na.contains(rd)) {
                continue;
            }
            // 加入到对角线、列集合中
            col.add(c);
            pie.add(ld);
            na.add(rd);
            // 递归调用下一行
            count += backtrack(n, row + 1, pie, na, col);
            // 终止回溯，删除对角线、列集合
            col.remove(c);
            pie.remove(ld);
            na.remove(rd);
        }

        return count;
    }

    /**
     * 22. 括号生成
     *
     * @param n 括号的数量
     * @return 合法括号的列表
     */
    public List<String> generateParenthesis(int n) {
        // 处理边界
        List<String> res = new ArrayList<>();
        if (n <= 0) {
            return res;
        }
        StringBuilder content = new StringBuilder();
        // 调用递归函数
        this.backtrack(res, content, 0, 0, n);
        return res;
    }

    private void backtrack(List<String> res, StringBuilder content, int left, int right, int n) {
        // 设置终止条件
        if (content.length() == 2 * n) {
            res.add(content.toString());
            return;
        }
        // 添加左侧括号
        if (left < n) {
            content.append("(");
            // 递归
            this.backtrack(res, content, left + 1, right, n);
            // 回溯
            content.deleteCharAt(content.length() - 1);
        }
        // 添加右侧括号
        if (right < left) {
            content.append(")");
            // 递归
            this.backtrack(res, content, left, right + 1, n);
            // 回溯
            content.deleteCharAt(content.length() - 1);
        }
    }


    /**
     * 79. 单词搜索
     *
     * @param board 二维网格
     * @param word  单词
     * @return 匹配到路径：true，否则 false
     */
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.length() == 0) {
            return false;
        }
        // 每个单元格作为起点进行搜索
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (this.backtrack(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtrack(char[][] board, String word, int index, int row, int col) {
        // 设置终止条件
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }
        // 不匹配返回 false
        if (board[row][col] != word.charAt(index)) {
            return false;
        }
        // 遍历到 word 最后一个元素都匹配，找到完整匹配
        if (index == word.length() - 1) {
            return true;
        }
        char tmp = board[row][col];
        // 标记当前单元格访问状态
        board[row][col] = '?';
        // 递归查找相邻元素
        boolean found = backtrack(board, word, index + 1, row - 1, col) ||
                backtrack(board, word, index + 1, row + 1, col) ||
                backtrack(board, word, index + 1, row, col - 1) ||
                backtrack(board, word, index + 1, row, col + 1);
        // 回溯，还原单元格访问状态
        board[row][col] = tmp;

        return found;
    }


}

