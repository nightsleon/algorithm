package com.algo.advanced.dynamicProgramming;

import java.util.Arrays;
import java.util.List;
/**
 * 难度级别: Advanced
 * 分类: Dynamicprogramming
 * 
 * @author liangjun
 **/
public class DynamicProgressingSolution {
    public static void main(String[] args) {
        DynamicProgressingSolution solution = new DynamicProgressingSolution();
        int n = 5;
        int stairs = solution.climbingStairsDFS(n);
//        System.out.println(stairs);
        int[] mem = new int[n + 1];
        stairs = solution.climbingStairsDFSMem(n, mem);
//        System.out.println(stairs);
        stairs = solution.climbingStairsDP(n);
//        System.out.println(stairs);
        stairs = solution.climbingStairsCompact(n);
//        System.out.println(stairs);
        int[][] grid = {{1, 2, 3}, {4, 5, 6}};
        int minPathSum = solution.minPathSum(grid);
//        System.out.println(minPathSum);
        int[] wgt = {10, 20, 30, 40, 50};
        int[] val = {50, 120, 150, 210, 240};
        int cap = 50;
        int knapsackMaxValue = solution.knapsackDFS(wgt, val, n, cap);
//        System.out.println(knapsackMaxValue);
        int[][] knapsackMem = new int[n + 1][cap + 1];
        knapsackMaxValue = solution.knapsackDFSMem(wgt, val, knapsackMem, n, cap);
//        System.out.println(knapsackMaxValue);
        knapsackMaxValue = solution.knapsackDP(wgt, val, cap);
        System.out.println(knapsackMaxValue);
    }

    /**
     * 递归自上而下
     */
    public int climbingStairsDFS(int n) {
        if (n < 0) return -1;
        if (n == 1 || n == 2) {
            return n;
        }
        // 每次只能爬 1 或 2 台阶，dp[i] = dp[i - 1] + dp[i - 2]，
        return climbingStairsDFS(n - 1) + climbingStairsDFS(n - 2);
    }

    /**
     * 记忆优化搜索，剪枝减掉重复计算
     */
    public int climbingStairsDFSMem(int n, int[] mem) {
        if (n < 0) return -1;
        if (n == 1 || n == 2) {
            return n;
        }
        // 优先获取缓存结果
        if (mem[n] != 0) return mem[n];
        else mem[n] = climbingStairsDFSMem(n - 1, mem) + climbingStairsDFSMem(n - 2, mem);
        // 记忆 dp[i]，缓存计算结果
        return mem[n];
    }

    /**
     * 动态规划，自下而上，知道最小子问题的解迭代构建更大的子问题解，底层 1 2 的结果，层层递推最顶层 n 的结果
     */
    public int climbingStairsDP(int n) {
        if (n == 1 || n == 2) {
            return 2;
        }
        int[] dp = new int[n + 1];
        // i = 1,2 的解
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 动态规划，节省空间解法
     */
    public int climbingStairsCompact(int n) {
        if (n == 1 || n == 2) {
            return 2;
        }
        int dp1 = 1;
        int dp2 = 2;
        int dpn = 0;
        for (int i = 3; i <= n; i++) {
            dpn = dp1 + dp2;
            dp1 = dp2;
            dp2 = dpn;
        }
        return dpn;
    }

    public int minPathSum(int[][] grid) {
        /*int il = grid.length, jl = grid[0].length;
        int[][] mem = new int[il][jl];
        return this.minPathDFSMem(grid, mem, il - 1, jl - 1);*/
        // 动态规划
        int r = grid.length, c = grid[0].length;
        // 首行状态，表格当前值 + dp[i - 1]
        for (int i = 1; i < c; i++) {
            grid[0][i] = grid[0][i] + grid[0][i - 1];
        }
        // 首列状态，表格当前值 + dp[j - 1]
        for (int j = 1; j < r; j++) {
            grid[j][0] = grid[j][0] + grid[j - 1][0];
        }
        for (int row = 1; row < r; row++) {
            for (int col = 1; col < c; col++) {
                // 上方值，左方值取最小 + 当前值
                grid[row][col] = Math.min(grid[row - 1][col], grid[row][col - 1]) + grid[row][col];
            }
        }
        return grid[r - 1][c - 1];
    }

    // 暴力搜索,力扣超时。。。
    private int minPathDFS(int[][] grid, int i, int j) {
        // 处理边界
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }

        // 设置终止条件
        if (i == 0 && j == 0) {
            return grid[0][0];
        }

        // 当前坐标只能右上方/右方而来，计算上方路径和
        int up = minPathDFS(grid, i - 1, j);
        // 计算左方路径和
        int left = minPathDFS(grid, i, j - 1);
        // 最小路径和 = 左方或上方最小 + 当前
        int min = Math.min(up, left) + grid[i][j];

        return min;
    }

    // 记忆化搜索
    private int minPathDFSMem(int[][] grid, int[][] mem, int i, int j) {
        // 处理边界
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (i == 0 && j == 0) {
            return grid[0][0];
        }
        // 优先获取缓存结果
        if (mem[i][j] > 0) {
            return mem[i][j];
        }
        // 当前坐标只能右上方/右方而来，计算上方路径和，计算左方路径和
        int up = minPathDFSMem(grid, mem, i - 1, j);
        int left = minPathDFSMem(grid, mem, i, j - 1);
        // 最小路径和 = 左方或上方最小 + 当前
        int min = Math.min(up, left) + grid[i][j];
        // 记忆已经计算过的结果
        mem[i][j] = min;
        return min;
    }

    /**
     * 0-1 背包问题，暴力搜索
     *
     * @param wgt 每个物品重量数组
     * @param val 每个物品价值数组
     * @param i   当前第 i 个物品
     * @param c   背包剩余容量
     * @return 背包里物品的价值（最大价值）
     */
    int knapsackDFS(int[] wgt, int[] val, int i, int c) {
        // 设置终止条件，没有物品可选 || 背包容量已满
        if (i == 0 || c == 0) {
            return 0;
        }
        // 当前物品重量大于背包剩余容量，跳过当前物品继续下一个
        if (wgt[i - 1] > c) {
            return knapsackDFS(wgt, val, i - 1, c);
        }
        // 不放入当前物品，继续递归下一个
        int no = knapsackDFS(wgt, val, i - 1, c);
        // 放入当前物品，背包容量减去当前物品重量，加上当前物品价值
        int yes = knapsackDFS(wgt, val, i - 1, c - wgt[i - 1]) + val[i - 1];
        // 取物品入包和不入包的最大值
        int max = Math.max(no, yes);
        return max;
    }

    // 0-1 背包问题，记忆化搜索
    int knapsackDFSMem(int[] wgt, int[] val, int[][] mem, int i, int c) {
        if (i == 0 || c == 0) return 0;
        // 有缓存则返回
        if (mem[i][c] != 0) return mem[i][c];

        // 无缓存则计算
        if (wgt[i - 1] > c) return knapsackDFSMem(wgt, val, mem, i - 1, c);
        int no = knapsackDFSMem(wgt, val, mem, i - 1, c);
        int yes = knapsackDFSMem(wgt, val, mem, i - 1, c - wgt[i - 1]) + val[i - 1];
        int max = Math.max(no, yes);
        // 设置缓存
        mem[i][c] = max;
        return max;
    }

    /**
     * 动态规划求解
     * dp[i][c] = Math.max(不放入物品的价值，放入物品的价值)
     * 不放入物品的价值 = dp[i - 1][c]
     * 放入物品的价值 = dp[i - 1][c - wgt[i - 1]] + val[i - 1]
     * 当前物品的价值 val[i - 1]，上一个物品的价值 dp[i - 1][c - wgt[i - 1]] ，当前容量 wgt[i - 1]，那留给上一个的容量 c-wgt[i - 1]
     *
     * @param wgt 每个物品重量数组
     * @param val 每个物品价值数组
     * @param cap 包的容量
     * @return
     */
    int knapsackDP(int[] wgt, int[] val, int cap) {
        // 物品数量
        int n = wgt.length;
        // 一维：物品，二维：容量
        int[][] dp = new int[n + 1][cap + 1];
        // 第一层遍历物品
        for (int i = 1; i < n + 1; i++) {
            // 第二层遍历容量
            for (int c = 0; c < cap + 1; c++) {
                // 当前物品重量大于容量，不入包
                if (wgt[i - 1] > c) {
                    dp[i][c] = dp[i - 1][c];
                } else {
                    // 入包，则取入包、不入包的较大值
                    dp[i][c] = Math.max(dp[i - 1][c], dp[i - 1][c - wgt[i - 1]] + val[i - 1]);
                }
            }
        }
        return dp[n][cap];
    }

    /**
     * 198. 打家劫舍
     * 假如打劫第 k 间，dp[k] = dp[k- 2] + nums[k]
     * 假如不打劫第 k 间，dp[k] = dp[k - 1]
     * 那打劫的最大金额 dp[k] = Math.max(dp[k - 2] + nums[k], dp[k - 1])
     * dp[0] = nums[0];
     * dp[1] = Math.max(nums[0], nums[1])
     *
     * @param nums 每个房间及金钱
     * @return 打劫的最大金额
     */
    public int rob(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[nums.length - 1];
    }

    /**
     * 322. 零钱兑换
     * 1.   定义一个数组 dp，其中  dp[i]  表示凑成金额  i  所需的最少硬币数。
     * 2.   初始化： dp[0] = 0 （凑成金额 0 不需要硬币），其他  dp[i]  初始化为一个很大的值（表示暂时无法凑成）。
     * 3.   转移方程：对于每种硬币  c ，如果当前金额  i  减去  c  的结果  i - c  是有效的，
     * 则：dp[i] = Math.min(dp[i], dp[i - c] + 1) 其中，dp[i - c] + 1 表示用  dp[i - c]  个硬币加上当前硬币  c 。
     * 4.   遍历所有金额和硬币，填充 dp 数组。
     * 5.   最终， dp[amount]  就是答案。如果  dp[amount]  仍是初始值，说明无法凑成，返回 -1。
     * 举例：
     * F(3)=min(F(3−c1),F(3−c2),F(3−c3))+1
     * =min(F(3−1),F(3−2),F(3−3))+1
     * =min(F(2),F(1),F(0))+1
     * =min(1,1,0)+1
     * =1
     *
     * @param coins  硬币面值数组
     * @param amount 目标金额
     * @return 凑成目标金额的硬币的数量
     */
    public int coinChange(int[] coins, int amount) {
        // 处理边界
        if (coins == null || coins.length == 0) {
            return 0;
        }
        if (amount == 0) {
            return 0;
        }
        // 动态方程初始化
        int bigAmount = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, bigAmount);
        // 填充值后初始化dp[0]
        dp[0] = 0;
        // 遍历金额
        for (int i = 1; i <= amount; i++) {
            // 遍历面值
            for (int c : coins) {
                // 当前金额 i 可以减去硬币面值 c
                if (c <= i) {
                    // dp[i - c] 是 dp[i] 前一个有效dp，加一之后到达dp[i]，取dp[i]，dp[i-c]+1 最小值
                    dp[i] = Math.min(dp[i], dp[i - c] + 1);
                }
            }
        }
        // dp[amount]  仍是初始值，说明无法凑成，返回 -1
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 300. 最长递增子序列
     * dp[i] = max(dp[i], dp[j] + 1) for j in [0, i)
     * 其中0≤j<i且num[j]<num[i]，才能将 nums[i] 放在 nums[j] 后面以形成更长的上升子序列。
     *
     * @param nums 数组
     * @return 最长递增子序列长度
     */
    public int lengthOfLIS(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }
        // 状态定义，当前元素结尾的最长递增子序列长度
        int[] dp = new int[nums.length];
        // 初始值
        Arrays.fill(dp, 1);

        // 方程定义 dp[i] = max(dp[i] + dp[j] + 1) for j in [0, i)
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 120. 三角形最小路径和
     *
     * @param triangle 三角形集合
     * @return 最小路径和
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        // 处理边界
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        // 初始化mem
        int rows = triangle.size();
        // 三角形最底层长度
        int cols = triangle.get(triangle.size() - 1).size();
        Integer[][] mem = new Integer[rows][cols];
        // 调用递归函数
        return this.dfs(triangle, mem, 0, 0);
    }

    private int dfs(List<List<Integer>> triangle, Integer[][] mem, int r, int c) {
        // 设置终止条件：遍历到三角形最底层的下一层（越界），此时无节点处理，最小和为0
        if (r == triangle.size()) {
            return 0;
        }

        // 优先取缓存值（记忆）
        if (mem[r][c] != null) {
            return mem[r][c];
        }

        // 当前值
        int pre = triangle.get(r).get(c);
        // 下一层的左节点
        int left = dfs(triangle, mem, r + 1, c);
        // 下一层的右节点
        int right = dfs(triangle, mem, r + 1, c + 1);
        // 缓存（记忆）计算结果（最小和）
        mem[r][c] = Math.min(left, right) + pre;

        // 返回结果
        return mem[r][c];
    }

    /**
     * 动态规划：dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle[i][j]，上一行第 j-1 或 j 指向当前元素
     */
    public int minimumTotalDp(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        int rows = triangle.size();
        int cols = rows;
        int[][] dp = new int[rows][rows];
        dp[0][0] = triangle.get(0).get(0);
        for (int r = 1; r < rows; r++) {
            // 初始化每行首个dp，上一行首个dp + 当前行的值
            dp[r][0] = dp[r - 1][0] + triangle.get(r).get(0);
            for (int c = 1; c < r; c++) {
                dp[r][c] = Math.min(dp[r - 1][c - 1], dp[r - 1][c]) + triangle.get(r).get(c);
            }
            // 初始化每行的末尾dp，上一行末尾dp + 当前值
            dp[r][r] = dp[r - 1][r - 1] + triangle.get(r).get(r);
        }

        // 遍历最后一行，获取最小和路径
        int min = dp[rows - 1][0];
        for (int c = 1; c < cols; c++) {
            min = Math.min(min, dp[rows - 1][c]);
        }
        return min;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }
        // 第一个格子是障碍物，直接返回
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;
        // 初始化dp状态
        int[][] dp = new int[rows][cols];
        dp[0][0] = 1;

        // 初始化首行，遇见石头，当前及以后格子为0
        for (int c = 1; c < cols; c++) {
            if (obstacleGrid[0][c] == 1) {
                dp[0][c] = 0;
            } else {
                dp[0][c] = dp[0][c - 1];
            }
        }

        // 初始化首列，遇见石头，当前及以后各自为0
        for (int r = 1; r < rows; r++) {
            if (obstacleGrid[r][0] == 1) {
                dp[r][0] = 0;
            } else {
                dp[r][0] = dp[r - 1][0];
            }
        }

        // dp方程，路径数量 = 上方数量 + 左侧数量 dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                // 当前格子是障碍物，dp 置零
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    // 路径数量 = 上方格子路径数量 + 左侧格子路径数量
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[rows - 1][cols - 1];
    }

    private int start;
    private int length;

    /**
     * 5. 最长回文子串
     *
     * @param s 字符串
     * @return 字符串s的最长回文子串
     */
    public String longestPalindrome(String s) {
        // 处理边界
        if (s == null || s.length() <= 1) {
            return s;
        }
        for (int i = 0; i < s.length(); i++) {
            // 回文串为奇数
            this.isPalindrome(s, i, i);
            // 回文串为偶数
            this.isPalindrome(s, i, i + 1);
        }
        return s.substring(start, start + length);
    }

    private void isPalindrome(String s, int left, int right) {
        // 扩展回文串长度（注意while循环）
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 本次回文串长度大于之前，取当前长度
        // 正常计算 length = right - left + 1，left向左、right向右各自增加了1，也就是总计增加了2，那么 length = right - left - 1
        // 设置 start 同理
        if (right - left - 1 > length) {
            length = right - left - 1;
            start = left + 1;
        }
    }

    /**
     * 221.最大正方形
     * 当前位置 (i, j) 为右下角的正方形的最大边长，取决于它的上方、左方和左上方的最小边长
     * dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
     *
     * @param matrix 二维矩阵
     * @return 最大正方形
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        // 定义dp数组
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        // 最大边长
        int maxEdge = 0;
        // 遍历每一个格子，计算以格子为右下角的最大正方形边长
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 格子值为 1 才进行计算
                if (matrix[i][j] == '1') {
                    // 初始化首行、首列dp，为了减少循环次数放到了内部初始化首行列
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        // 不是首行列，取上方、左侧、左上方最小值 + 1
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                }
                maxEdge = Math.max(maxEdge, dp[i][j]);
            }
        }

        // 返回最大面积
        return maxEdge * maxEdge;
    }

}
