package com.algo.advanced.graph;

import java.util.*;
/**
 * 难度级别: Advanced
 * 分类: Graph
 * 
 * @author liangjun
 **/
public class GraphSolution {

    /**
     * 岛屿数量，dfs
     */
    public int numIslands(char[][] grid) {
        // 处理边界
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // 搜索 1 走过的路径
                if (grid[i][j] == '1') {
                    res++;
                    islandDfs(grid, i, j);
                }
            }
        }

        return res;
    }

    /**
     * 岛屿数量，bfs
     */
    public int numIslandsBfs(char[][] grid) {
        // 处理边界
        if (grid == null || grid.length == 0) {
            return 0;
        }
        // 矩阵的行、列高
        int rows = grid.length;
        int cols = grid[0].length;
        // 初始化队列、岛屿数量
        Queue<Integer> queue = new LinkedList<>();
        int res = 0;

        // 遍历矩阵
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // 遇见岛屿则层序递进
                if (grid[r][c] == '1') {
                    res++;
                    // 当前行索引✖️总列数 + 当前列索引
                    int index = cols * r + c;
                    queue.add(index);

                    while (!queue.isEmpty()) {
                        Integer currIndex = queue.poll();
                        // 当前层坐标
                        int cr = currIndex / cols;
                        int cc = currIndex % cols;
                        // 标记已经访问过此节点
                        grid[cr][cc] = '0';
                        // 上
                        if (cr + 1 < rows && grid[cr + 1][c] == '1') {
                            // 标记已经访问过下一层节点
                            grid[cr + 1][cc] = '0';
                            // 将下一层节点加入队列
                            queue.add(cols * (cr + 1) + cc);
                        }
                        // 下
                        if (cr - 1 >= 0 && grid[cr - 1][cc] == '1') {
                            grid[cr - 1][cc] = '0';
                            queue.add(cols * (cr - 1) + cc);
                        }
                        // 左
                        if (cc - 1 >= 0 && grid[cr][cc - 1] == '1') {
                            grid[cr][cc - 1] = '0';
                            queue.add(cols * cr + cc - 1);
                        }
                        // 右
                        if (cc + 1 < cols && grid[cr][cc + 1] == '1') {
                            grid[cr][cc + 1] = '0';
                            queue.add(cols * cr + cc + 1);
                        }
                    }

                }
            }
        }

        return res;
    }

    private void islandDfs(char[][] grid, int r, int c) {
        int rows = grid.length;
        int clos = grid[0].length;

        // 设置终止条件
        if (r < 0 || r >= rows || c < 0 || c >= clos || grid[r][c] == '0') {
            return;
        }

        // 走过的路设置为0
        grid[r][c] = '0';

        // 递归搜索路径
        islandDfs(grid, r + 1, c);
        islandDfs(grid, r - 1, c);
        islandDfs(grid, r, c - 1);
        islandDfs(grid, r, c + 1);
    }

    /**
     * 被围绕的区域：
     * 对于每一个边界上的 O，我们以它为起点，标记所有与它直接或间接相连的字母 O；
     * 最后我们遍历这个矩阵，对于每一个字母：
     * - 如果该字母被标记过，则该字母为没有被字母 X 包围的字母 O，我们将其还原为字母 O；
     * - 如果该字母没有被标记过，则该字母为被字母 X 包围的字母 O，我们将其修改为字母 X。
     */
    public void solve(char[][] board) {
        // 处理边界
        if (board == null || board.length == 0) {
            return;
        }

        int rows = board.length;
        int cols = board[0].length;
        // 上下边搜索 O 的路径
        for (int c = 0; c < cols; c++) {
            // 上边
            solveDfs(board, 0, c);
            // 下边
            solveDfs(board, rows - 1, c);
        }
        // 左右边搜索 O 的路径
        for (int r = 0; r < rows; r++) {
            // 左边
            solveDfs(board, r, 0);
            // 右边
            solveDfs(board, r, cols - 1);
        }

        // 还原不匹配的标记 S -> O, 覆盖 O -> X
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'S') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }

    }

    private void solveDfs(char[][] board, int r, int c) {
        // 设置终止条件
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != 'O') {
            return;
        }

        // 搜索过的路径设置为 S
        board[r][c] = 'S';

        // 递归搜索
        solveDfs(board, r + 1, c);
        solveDfs(board, r - 1, c);
        solveDfs(board, r, c + 1);
        solveDfs(board, r, c - 1);
    }

    /**
     * 课程表，拓扑排序 bfs
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 设置边，入度
        List<List<Integer>> edges = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        // 初始化顶点对应的所有边、每个顶点的入度
        for (int[] req : prerequisites) {
            edges.get(req[1]).add(req[0]);
            // 入度加一
            inDegree[req[0]]++;
        }

        // 初始化队列，将入度为 0 的顶点加入
        Queue<Integer> queue = new LinkedList<>();
        for (int vertex = 0; vertex < inDegree.length; vertex++) {
            if (inDegree[vertex] == 0) {
                queue.add(vertex);
            }
        }

        // 层序遍历
        int visitedCount = 0;
        while (!queue.isEmpty()) {
            visitedCount++;
            Integer vertex = queue.poll();
            // 遍历此顶点所有的边入度减一，入度为 0 则加入队列
            for (int vertx : edges.get(vertex)) {
                inDegree[vertx]--;
                if (inDegree[vertx] == 0) {
                    queue.add(vertx);
                }
            }
        }

        return numCourses == visitedCount;
    }

    /**
     * 课程表II，拓扑排序 bfs
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 处理边界
        int[] orderedCourse = new int[numCourses];
        if (prerequisites == null || prerequisites.length == 0) {
            for (int i = 0; i < numCourses; i++) {
                orderedCourse[i] = i;
            }
            return orderedCourse;
        }
        // 设置边、入度
        List<List<Integer>> edges = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }

        // 初始化边、入度
        for (int[] req : prerequisites) {
            edges.get(req[1]).add(req[0]);
            inDegree[req[0]]++;
        }

        // 初始化队列，将入度为 0 的顶点加入队列
        Queue<Integer> queue = new LinkedList<>();
        for (int course = 0; course < numCourses; course++) {
            if (inDegree[course] == 0) {
                queue.add(course);
            }
        }

        // 层序遍历
        int index = 0;
        while (!queue.isEmpty()) {
            // 取出将入度为 0 的顶点加入数组
            int currCourse = queue.poll();
            orderedCourse[index] = currCourse;
            index++;
            // 遍历下一层，入度减一，入度为 0 则加入队列
            for (int nextCourse : edges.get(currCourse)) {
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    queue.add(nextCourse);
                }
            }

        }
        return index == numCourses ? orderedCourse : new int[]{};
    }

}
