package com.algo.advanced.range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
/**
 * 难度级别: Advanced
 * 分类: Range
 * 
 * @author liangjun
 **/
public class RangeSolution {

    /*
    汇总区间
     */
    public List<String> summaryRanges(int[] nums) {
        // 处理边界
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }

        // 遍历数组，处理区间
        int left = 0;
        for (int right = 1; right <= nums.length; right++) {
            // 到达数组末尾时 or 相邻元素不连续时，结束当前区间
            if (right == nums.length || nums[right - 1] + 1 != nums[right]) {
                if (nums[left] == nums[right - 1]) {
                    res.add(String.valueOf(nums[left]));
                } else {
                    res.add(nums[left] + "->" + nums[right - 1]);
                }
                left = right;
            }
        }
        return res;
    }

    /**
     * 56.合并区间：
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     * <p>
     * 1. 排序区间：按区间的起始位置排序。这样可以保证如果两个区间重叠，它们会相邻出现。
     * 2. 合并区间：创建一个 merged 列表来存放合并的区间。遍历排序后的区间列表，如果当前区间和上一个区间重叠，则合并这两个区间；否则将当前区间加入 merged。
     */
    public int[][] merge(int[][] intervals) {
        // 按照区间起始值排序数组
        Arrays.sort(intervals, Comparator.comparingInt((int[] itv) -> itv[0]));

        // 初始化 merged，并将当前区间加入 merged
        List<int[]> merged = new ArrayList<>();
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        // 遍历区间
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            // 当前区间右值 >= 下一区间左值，更新右边界
            if (currentInterval[1] >= interval[0]) {
                currentInterval[1] = Math.max(currentInterval[1], interval[1]);
            } else {
                // 区间不重合，更新当前区间为 interval，添加到 merged 中
                currentInterval = interval;
                merged.add(currentInterval);
            }

        }
        return merged.toArray(new int[merged.size()][2]);
    }

    /*
    插入区间
    1.	分段处理：通过分段处理简化逻辑，先处理小于 newInterval 的区间，再处理重叠区间，最后处理大于 newInterval 的区间。
	2.	合并逻辑简化：合并重叠区间时，直接更新 newInterval 的范围。
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // 检查边界
        if (intervals == null || intervals.length == 0) {
            return new int[][]{newInterval};
        }
        List<int[]> result = new ArrayList<>();
        int idx = 0;
        // 先处理小于 newInterval 的区间
        while (idx < intervals.length && intervals[idx][1] < newInterval[0]) {
            result.add(intervals[idx]);
            idx++;
        }
        // 处理与 newInterval 重叠的区间
        while (idx < intervals.length && intervals[idx][0] <= newInterval[1]) {
            // 更新 newInterval 范围
            newInterval[0] = Math.min(newInterval[0], intervals[idx][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[idx][1]);
            idx++;
        }
        result.add(newInterval);
        // 处理大于 newInterval 的区间
        while (idx < intervals.length) {
            result.add(intervals[idx]);
            idx++;
        }
        return result.toArray(new int[result.size()][2]);
    }

    public static void main(String[] args) {
        RangeSolution solution = new RangeSolution();
        int[] nums = {0, 2, 3, 4, 6, 8, 9};
        System.out.println(solution.summaryRanges(nums).toString());
    }

}
