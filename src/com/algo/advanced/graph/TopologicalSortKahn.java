package com.algo.advanced.graph;

import java.util.*;
/**
 * 难度级别: Advanced
 * 分类: Graph
 * 
 * @author liangjun
 **/
public class TopologicalSortKahn {

    /**
    Kahn's algorithm for Topological Sort
     1.	计算图中每个顶点的入度。
     2.	将所有入度为 0 的顶点加入队列。
     3.	从队列中移除顶点，加入拓扑排序结果，并将该顶点的所有邻接点的入度减 1。如果邻接点的入度变为 0，加入队列。
     4.	重复上述步骤，直到队列为空。如果排序完成后还有顶点未处理，则说明图中有环，无法进行拓扑排序。
     */
    public static List<Integer> kahnTopologicalSort(int vertices, List<int[]> edges) {
        // 排序后的元素
        List<Integer> topoOrder = new ArrayList<>();
        // 图，key：顶点，value：key指向的顶点
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // 入度
        int[] degree = new int[vertices];

        // 构建图和计算入度
        for (int i = 0; i < vertices; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
            degree[v]++;
        }

        // 初始化队列并将所有入度为 0 的顶点加入队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            if (degree[i] == 0) {
                queue.offer(i);
            }
        }

        // 处理队列中的顶点并进行拓扑排序
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            topoOrder.add(node);
            // 处理邻接点
            for (int neighbor : graph.get(node)) {
                degree[neighbor]--;
                if (degree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // 检查是否有环
        if (topoOrder.size() != vertices) {
            throw new RuntimeException("Graph has a cycle, no topological ordering possible.");
        }

        return topoOrder;
    }

    public static void main(String[] args) {
        int vertices = 6;
        List<int[]> edges = Arrays.asList(
                new int[]{5, 2},
                new int[]{5, 0},
                new int[]{4, 0},
                new int[]{4, 1},
                new int[]{2, 3},
                new int[]{3, 1}
        );

        List<Integer> result = kahnTopologicalSort(vertices, edges);
        System.out.println("Topological Sort using Kahn's algorithm: " + result);

        result = dfsTopologicalSort(vertices, edges);
        System.out.println("Topological Sort using dfs search: " + result);
    }

    /* 深度优先搜索算法的拓扑排序实现
    1.	对每个未访问的顶点执行深度优先搜索。
	2.	当顶点的所有邻接点都被处理完后，将该顶点入栈。
	3.	最后输出栈中的顶点顺序即为拓扑排序。
     */
    public static List<Integer> dfsTopologicalSort(int vertices, List<int[]> edges) {
        // 存储拓扑排序的结果
        List<Integer> topoOrder = new ArrayList<>();
        // 用于存储有向图，使用邻接表表示法
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // 记录顶点是否被访问过
        boolean[] visited = new boolean[vertices];
        // 记录当前递归栈中的顶点，防止环
        boolean[] onStack = new boolean[vertices];
        // 栈用于记录拓扑排序的顺序
        Stack<Integer> stack = new Stack<>();

        // 初始化图
        for (int i = 0; i < vertices; i++) {
            graph.put(i, new ArrayList<>());
        }

        // 构建图
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
        }

        // 对每个顶点执行深度优先搜索
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                if (!dfs(i, graph, visited, onStack, stack)) {
                    throw new RuntimeException("Graph has a cycle, no topological ordering possible.");
                }
            }
        }

        // 将栈中的元素加入到结果中，栈中的顺序就是拓扑排序的逆序
        while (!stack.isEmpty()) {
            topoOrder.add(stack.pop());
        }

        return topoOrder;
    }

    // 递归的DFS函数
    private static boolean dfs(int node, Map<Integer, List<Integer>> graph, boolean[] visited, boolean[] onStack, Stack<Integer> stack) {
        visited[node] = true;
        onStack[node] = true;

        // 遍历当前节点的邻居节点
        for (int neighbor : graph.get(node)) {
            // 如果邻居节点没有被访问过，则继续递归访问
            if (!visited[neighbor]) {
                if (!dfs(neighbor, graph, visited, onStack, stack)) {
                    return false;
                }
            }
            // 如果邻居节点已经在递归栈中，说明图中存在环
            else if (onStack[neighbor]) {
                return false; // 找到环
            }
        }

        // 递归结束后，当前节点出栈
        onStack[node] = false;
        // 将当前节点入栈，记录排序顺序
        stack.push(node);
        return true;
    }
}
