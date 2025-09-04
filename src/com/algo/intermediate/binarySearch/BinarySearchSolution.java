package com.algo.intermediate.binarysearch;

/**
 * 难度级别: Intermediate
 * 分类: Binary search
 *
 * @author liangjun
 **/
public class BinarySearchSolution {
    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置
     * <p>
     * 输入: nums = [1,3,5,6], target = 5
     * 输出: 2
     * <p>
     * 输入: nums = [1,3,5,6], target = 2
     * 输出: 1
     *
     * @param nums   数组
     * @param target 目标值
     * @return 目标值下标
     */
    public int searchInsert(int[] nums, int target) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return -1;
        }
        // 超出数组范围，直接返回 0 or length - 1
        if (target < nums[0]) {
            return 0;
        }
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }

        int left = 0;
        int right = nums.length - 1;
        // 二分查找
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // 没找到返回 left
        return left;
    }

    /**
     * 74. 搜索二维矩阵
     * <p>
     * 给你一个满足下述两条属性的 m x n 整数矩阵：
     * 每行中的整数从左到右按非严格递增顺序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false
     *
     * @param matrix 二维矩阵
     * @param target 目标值
     * @return 目标值在二维矩阵中，返回 true，否则返回 false
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int currRow = -1;
        // 找到 target 所在行
        for (int i = 0; i < rows; i++) {
            if (target <= matrix[i][cols - 1]) {
                currRow = i;
                break;
            }
        }
        if (currRow == -1) {
            return false;
        }

        int left = 0;
        int right = cols - 1;
        // target 所在行中，二分查找 target
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == matrix[currRow][mid]) {
                return true;
            } else if (target < matrix[currRow][mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    public boolean searchMatrixII(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;

        // 将二维数组打平，看做一行
        int left = 0;
        int right = rows * cols - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 当前行 = mid/cols，当前列 = mid%cols
            int midVal = matrix[mid / cols][mid % cols];
            if (target == midVal) {
                return true;
            } else if (target < midVal) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    /**
     * 162. 寻找峰值
     * <p>
     * 峰值元素是指其值严格大于左右相邻值的元素。
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * 对于所有有效的 i 都有 nums[i] != nums[i + 1]
     *
     * @param nums 数组
     * @return 峰值下标
     */
    public int findPeakElement(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return -1;
        }
        // 前提条件 nums[i] != nums[i + 1]，则最大值就是峰值
        int maxIdx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    public int findPeakElementBs(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        // nums[i] != nums[i + 1]
        while (left < right) {
            int mid = left + (right - left) / 2;
            // mid < mid + 1，峰值在右侧
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                // 否则，峰值可能是 mid 或在左侧
                right = mid;
            }
        }
        // 直到 left right 相遇，找到峰值所在下标
        return left;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * <p>
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     *
     * @param nums   数组
     * @param target 目标值
     * @return 目标值的起始下标
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = 0;
        int right = nums.length - 1;
        int idx = -1;
        // 二分查找到目标值
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                idx = mid;
                break;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // 没找到则返回 -1
        if (idx == -1) {
            return new int[]{-1, -1};
        }
        // 根据目标值向左右两侧扩展
        int l = idx, r = idx;
        while (l > 0 && nums[l - 1] == target) {
            l--;
        }
        while (r < nums.length - 1 && nums[r + 1] == target) {
            r++;
        }
        return new int[]{l, r};
    }

    public int[] searchRangeII(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        int left = findFirst(nums, target);
        if (left == -1) {
            return new int[]{-1, -1};
        }

        int right = findLast(nums, target);

        return new int[]{left, right};
    }

    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    /**
     * 153. 寻找旋转排序数组中的最小值
     * 举例：3 4 5 0 1 2，345 为左侧线，012 为右侧线
     * <p>
     * y
     * ↑
     * 5 |           ●
     * 4 |      ●
     * 3 | ●
     * 2 |                          ●
     * 1 |                     ●
     * 0 +-----------------●---------------------------------
     * 1    2    3    4    5    6    x
     * <p>
     * 原数组: [1,2,3,4,5]
     * 旋转后: [3,4,5,1,2]
     * ↑     ↑
     * 左侧线  右侧线
     *
     * @param nums 旋转的数组
     * @return 数组中最小值
     */
    public int findMin(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // mid 大于 right，最小值在右侧
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // mid 小于等于right，最小值在左侧
                right = mid;
            }
        }
        return nums[left];
    }

}
