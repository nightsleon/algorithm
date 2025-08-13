package com.algo.intermediate.binarysearch;
/**
 * 难度级别: Intermediate
 * 分类: Binarysearch
 * 
 * @author liangjun
 **/
public class BinarySearchSolution {
    /**
     * 35. 搜索插入位置
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
        int currRow = 0;
        // 找到 target 所在行
        for (int i = 0; i < rows; i++) {
            if (target <= matrix[i][cols - 1]) {
                currRow = i;
                break;
            }
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

    /**
     * 162. 寻找峰值
     *
     * @param nums 数组
     * @return 峰值下标
     */
    public int findPeakElement(int[] nums) {
        // 处理边界
        if (nums == null || nums.length == 0) {
            return -1;
        }
        // nums[i] != nums[i + 1]，则最大值就是峰值
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
        int l = idx - 1, r = idx + 1;
        while (l >= 0 && nums[l] == target) {
            l--;
        }
        while (r < nums.length && nums[r] == target) {
            r++;
        }
        return new int[]{l + 1, r - 1};
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
            // mid 大于 right，mid 在左侧线，最小值在右侧线
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // mid 小于等于right，mid 在右侧线，最小值也在右侧线；如果只有一条线，同理；
                right = mid;
            }
        }
        return nums[left];
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 处理边界
        if(nums1 == null || nums2 == null) {
            return 0D;
        }

        // 合并数组
        int totalLength = nums1.length + nums2.length;
        int[] nums3 = new int[totalLength];


        return 0;
    }

}
