package com.algo.advanced.sort;

import java.util.Arrays;
/**
 * 难度级别: Advanced
 * 分类: Sort
 * 
 * @author liangjun
 **/
public class SortSolution {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 3, 1, 6, 8, 7, 9, 2, 0};
//        quickSort(nums, 0, nums.length - 1);
        countingSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 开启一个循环，每轮从右侧未排序区间选择最小的元素，将其放到左侧已排序区间的末尾。
     */
    static void selectSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                minIndex = nums[j] < nums[minIndex] ? j : minIndex;
            }
            int temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
    }

    /**
     * 在左侧未排序区间通过两两比较，较大元素右移，最终将最大元素移动右侧排序区间（遍历完成最大的泡泡浮出水面）
     */
    static void bubbleSort(int[] nums) {
        for (int j = nums.length - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (nums[i] > nums[i + 1]) {
                    swap(nums, i, i + 1);
                }
            }
        }
    }

    /**
     * 当前位置的数小于前一个数则交换，直到碰见大于等于自己的则停止；直到当前元素遍历完成；
     */
    static void insertionSort(int[] nums) {
        // 保证 0 - i 位置有序
        for (int i = 1; i < nums.length; i++) {
            // i 位置的数跟前面的数比较，直到碰到大于等于自己的则停止
            for (int j = i; j > 0 && nums[j] < nums[j - 1]; j--) {
                swap(nums, j, j - 1);
            }
        }
    }

    int getMax(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        int mid = left + ((right - left) >> 1);
        int leftMax = getMax(nums, left, mid);
        int rightMax = getMax(nums, mid + 1, right);
        return Math.max(leftMax, rightMax);
    }

    /**
     * divide：将数组分为左子数组，右子数组，直到不能再分，然后将左右子数组排序合并
     */
    static void mergeSort(int[] nums, int left, int right) {
        // 设置终止条件
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        // 排序左侧数组
        mergeSort(nums, left, mid);
        // 排序右侧数组
        mergeSort(nums, mid + 1, right);
        // 合并合并后的左右数组
        merge(nums, left, mid, right);
    }

    // conquer: 将排好序的左右子数组进行合并放到临时数组 tmp 中，然后将有序 tmp 数组元素插入到原数组
    static void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            if (nums[p1] <= nums[p2]) {
                temp[i++] = nums[p1++];
            } else {
                temp[i++] = nums[p2++];
            }
        }
        // p2 越界，复制p1剩余元素到临时数组
        while (p1 <= mid) {
            temp[i++] = nums[p1++];
        }
        // p1 越界，复制p2剩余元素到临时数组
        while (p2 <= right) {
            temp[i++] = nums[p2++];
        }
        // 将排好序的临时数组复制到原数组
        for (int k = 0; k < temp.length; k++) {
            nums[left + k] = temp[k];
        }
    }

    /**
     * 将数组拆分为左右子数组，左子数组都小于基准值，右子数组都大于基准值，直到不能再拆分为止，此时所有元素已经都排好序
     */
    static void quickSort(int[] nums, int left, int right) {
        // 设置终止条件
        if (left >= right) {
            return;
        }
        int point = partition(nums, left, right);
        quickSort(nums, left, point);
        quickSort(nums, point + 1, right);

    }

    // 选择一个基准值，将大于基准值的元素放到右侧，小于基准值的元素放到左侧，然后返回基准值下标
    static int partition(int[] nums, int left, int right) {
        int i = left, j = right;
        // i j 重合(i = j) 则退出循环，此时左侧都小于基准值，右侧都大于基准值
        while (i < j) {
            // 从右向左找首个小于基准点 nums[left] 的下标 j
            while (i < j && nums[j] >= nums[left]) {
                j--;
            }
            // 从左向右找首个大于基准点 nums[left] 的下标 i
            while (i < j && nums[i] <= nums[left]) {
                i++;
            }
            // 找到之后两者交换
            swap(nums, i, j);
        }
        // 交换基准点、分界线
        swap(nums, left, i);
        // 返回基准下标
        return i;
    }

    private static void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        nums[i] = nums[i] ^ nums[j];
        nums[j] = nums[i] ^ nums[j];
        nums[i] = nums[i] ^ nums[j];
    }

    /**
     * 计数排序
     */
    static void countingSort(int[] nums) {
        // 1.遍历数组 nums，找到最大的值m，创建长度 max+1 长度的数组
        int max = nums[0];
        for (int num : nums) {
            max = Math.max(num, max);
        }
        int[] counter = new int[max + 1];

        // 2.数组 nums 值作为 index，出现次数作为 value，插入 counter
        for (int i = 0; i < nums.length; i++) {
            counter[nums[i]] += 1;
        }

        // 3.遍历 counter（天然有序），插入 nums
        int index = 0;
        for (int value = 0; value < counter.length; value++) {
            int cnt = counter[value];
            if (cnt > 0) {
                for (int j = 0; j < cnt; j++) {
                    nums[index] = value;
                    index++;
                }
            }
        }
    }

}
