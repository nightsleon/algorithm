package com.algo.advanced.graph;

import java.util.*;
/**
 * 难度级别: Advanced
 * 分类: Graph
 * 
 * @author liangjun
 **/

class Segment {
    int id;
    String name;
    String code;
    List<Integer> segmentIdList;  // 依赖的群组ID列表

    public Segment(int id, String name, String code, List<Integer> segmentIdList) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.segmentIdList = segmentIdList;
    }
}


public class SegmentSorter {

    /*
     拓扑排序，依赖的群组在前
     1.	计算图中每个顶点的入度。
     2.	将所有入度为 0 的顶点加入队列。
     3.	从队列中移除顶点，加入拓扑排序结果，并将该顶点的所有邻接点的入度减 1。如果邻接点的入度变为 0，加入队列。
     4.	重复上述步骤，直到队列为空。如果排序完成后还有顶点未处理，则说明图中有环，无法进行拓扑排序。
     */
    public static List<Segment> topologicalSort(List<Segment> segments) {
        // 构建依赖图和入度表
        Map<Integer, List<Integer>> dependencyGraph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        // 初始化图和入度
        for (Segment segment : segments) {
            inDegree.put(segment.id, 0); // 初始所有群组的入度为0
            dependencyGraph.put(segment.id, new ArrayList<>()); // 初始化依赖图
        }
        // 构建依赖图和入度表
        for (Segment segment : segments) {
            for (Integer depId : segment.segmentIdList) {
                // 构建依赖关系: segmentIdList 中的元素指向当前群组
                dependencyGraph.get(depId).add(segment.id);
                // 当前群组的入度+1
                inDegree.put(segment.id, inDegree.get(segment.id) + 1);
            }
        }

        // 结果集，按拓扑排序后的顺序存储
        List<Segment> sortedSegments = new ArrayList<>();
        // 用队列存储所有入度为0的群组（没有依赖的群组）
        Queue<Integer> queue = new LinkedList<>();
        for (Integer id : inDegree.keySet()) {
            if (inDegree.get(id) == 0) {
                queue.offer(id); // 入度为0的群组可以先处理
            }
        }

        // 处理所有群组，进行拓扑排序
        while (!queue.isEmpty()) {
            // 取出一个入度为0的群组ID
            int currentId = queue.poll();
            // 根据ID找到群组对象并添加到结果集
            for (Segment segment : segments) {
                if (segment.id == currentId) {
                    sortedSegments.add(segment);
                    break;
                }
            }

            // 遍历当前群组依赖的群组(邻接节点)，更新它们的入度
            for (Integer dependentId : dependencyGraph.get(currentId)) {
                // 依赖它的群组(相邻节点)入度-1
                inDegree.put(dependentId, inDegree.get(dependentId) - 1);
                if (inDegree.get(dependentId) == 0) {
                    // 入度变为0，放入队列，可以处理下一个
                    queue.offer(dependentId);
                }
            }
        }

        // 检查是否所有群组都已处理完，避免有环的情况
        if (sortedSegments.size() != segments.size()) {
            throw new RuntimeException("依赖关系有环，无法进行拓扑排序");
        }

        return sortedSegments;
    }

    public static void main(String[] args) {
        // 示例群组数据
        List<Segment> segments = Arrays.asList(
                new Segment(0, "Group0", "G1", Arrays.asList(4, 5)),
                new Segment(1, "Group1", "G2", Arrays.asList(3, 4)),
                new Segment(2, "Group2", "G3", Arrays.asList(5)),
                new Segment(3, "Group3", "G4", Arrays.asList(2)),
                new Segment(4, "Group4", "G5", Collections.emptyList()),
                new Segment(5, "Group5", "G5", Collections.emptyList())
        );

        // 进行拓扑排序
        List<Segment> sortedSegments = topologicalSort(segments);

        // 输出排序结果
        for (Segment segment : sortedSegments) {
            System.out.println("com.algo.solution.Segment ID: " + segment.id + ", Name: " + segment.name);
        }
    }

}
