package com.algo.advanced.matrix;

import java.util.ArrayList;
import java.util.List;
/**
 * 难度级别: Advanced
 * 分类: Matrix
 * 
 * @author liangjun
 **/
public class MatrixSolution {
    /**
     * 36. 有效的数独
     * <p>
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     * <p>
     * 使用2个二维、1个三维数组，分别来记录数字在行、列和子格子中的出现次数，以此来判断数独是否有效
     */
    public boolean isValidSudoku(char[][] board) {
        // 行：数字在此行上出现的次数，一维表示行，二维表示数字转换的下标（0-8，对应数字1-9），值表示此数字出现的次数
        int[][] rowVal = new int[9][9];
        // 列：数字在此列上出现的次数
        int[][] colVal = new int[9][9];
        // 子格子：数字在子格子的出现的次数，总共9个子格子[3][3]
        int[][][] subBoxVal = new int[3][3][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                char c = board[row][col];
                if (c == '.') {
                    continue;
                }
                int idx = c - '1';
                // 数字在此行上出现的次数
                rowVal[row][idx]++;
                // 数字在此列上出现的次数
                colVal[col][idx]++;
                // 数字在子格子出现的次数
                subBoxVal[row / 3][col / 3][idx]++;

                // 判断有效数独
                if (subBoxVal[row / 3][col / 3][idx] > 1 || rowVal[row][idx] > 1 || colVal[col][idx] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 48. 旋转图像
     * <p>
     * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * <p>
     * 旋转图像，水平轴线旋转，主对角线旋转(注意避免重复交换元素)
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // 水平轴线翻转
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - i][j];
                matrix[n - 1 - i][j] = temp;
            }
        }
        // 主对角线翻转
        for (int r = 0; r < n; r++) {
            // 从当前行的下一列开始，向右遍历，这样可以保证每个元素只交换一次（避免重复交换）
            for (int c = r + 1; c < n; c++) {
                int temp = matrix[r][c];
                matrix[r][c] = matrix[c][r];
                matrix[c][r] = temp;
            }
        }
    }

    /**
     * •54. 螺旋矩阵
     * <p>
     * 顺时针模拟“螺旋遍历”矩阵，使用一个方向数组控制顺时针方向变化，并通过布尔数组 visited 跟踪已访问的元素，确保每个元素只被遍历一次。
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        // 处理边界
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        // 定义行列、总数变量
        int rows = matrix.length;
        int cols = matrix[0].length;
        int total = rows * cols;
        // 被访问过的下标
        boolean[][] visited = new boolean[rows][cols];
        // 顺时针旋转方向：右、下、左、上
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int directionIndex = 0;
        int row = 0, col = 0;
        for (int i = 0; i < total; i++) {
            res.add(matrix[row][col]);
            visited[row][col] = true;
            // 计算一下一行、下一列
            int nextRow = row + directions[directionIndex][0];
            int nextCol = col + directions[directionIndex][1];
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols || visited[nextRow][nextCol]) {
                // 切换方向（右下左上）
                directionIndex = (directionIndex + 1) % directions.length;
            }
            // 移动行列
            row += directions[directionIndex][0];
            col += directions[directionIndex][1];
        }
        return res;
    }

    /**
     * 73. 矩阵置零
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
     *
     * @param matrix 矩阵
     */
    public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 标记置零的行列
        boolean[] zeroRow = new boolean[m];
        boolean[] zeroCol = new boolean[n];
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (matrix[r][c] == 0) {
                    zeroRow[r] = true;
                    zeroCol[c] = true;
                }
            }
        }
        // 置零矩阵
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (zeroRow[r] || zeroCol[c]) {
                    matrix[r][c] = 0;
                }
            }
        }
    }

    /**
     * 240. 搜索二维矩阵 II
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
     * <p>
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        // 从矩阵的右上角（matrix[0][n-1]）开始，如果当前元素小于目标，则向下移动；如果当前元素大于目标，则向左移动。
        int rows = matrix.length;
        int cols = matrix[0].length;
        int row = 0, col = cols - 1;
        while (row < rows && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // 如果当前元素大于目标，则向左移动
                col--;
            } else {
                // 如果当前元素小于目标，则向下移动
                row++;
            }
        }
        return false;
    }

}
