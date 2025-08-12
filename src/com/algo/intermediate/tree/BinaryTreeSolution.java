package com.algo.intermediate.tree;

import com.algo.common.TreeNode;

import java.util.*;
/**
 * 难度级别: Intermediate
 * 分类: Tree
 * 
 * @author liangjun
 **/
public class BinaryTreeSolution {

    /**
     * 94. 二叉树的中序遍历
     * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // 处理边界
        if (root == null) {
            return result;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            // 遍历左子树，放到栈中
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // root 节点
            root = stack.pop();
            result.add(root.val);
            // right 节点
            root = root.right;
        }
        return result;
    }

    /**
     * 104. 二叉树的最大深度
     * 给定一个二叉树 root ，返回其最大深度。
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            // 遍历一层
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            // 遍历完一层后深度加一
            depth++;
        }
        return depth;
    }

    /**
     * 543.二叉树的直径
     * 给你一棵二叉树的根节点，返回该树的 直径 。
     * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
     * 两节点之间路径的 长度 由它们之间边数表示。
     * <p>
     * 优先搜索（DFS）来计算每个节点的左右子树深度，并更新最长路径（左右深度之和）
     */
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        depth(root);
        return maxDiameter;
    }

    private int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        // 计算左右子树深度
        int leftDepth = depth(node.left);
        int rightDepth = depth(node.right);
        // 更新最大深度，左右子树深度之和
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);
        // 返回当前节点为跟的最大深度
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 100.相同的树，深度优先
     * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     */

    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 设置终止条件，p/q 都遍历到最后说明之前节点都相同
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        // 递归比较左右 p/q 的左右子树
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 226.翻转二叉树
     * 深度优先 or 广度优先
     * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
     * <p>
     * 1
     * / \
     * 2   3
     * <p>
     * 递归流程：
     * 1.	invertTree(1)
     * •	invertTree(2) → 返回 2
     * •	invertTree(3) → 返回 3
     * •	交换左右 → 3 成为左子树，2 成为右子树
     * <p>
     * 1
     * / \
     * 3   2
     */

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 递归旋转左右子树
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        // 交换左右节点
        root.left = right;
        root.right = left;

        return root;
    }

    /**
     * 先交换再递归
     */
    private TreeNode dfs(TreeNode curr) {
        // 设置终止条件
        if (curr == null) {
            return null;
        }
        // 交换当前节点的左右子树
        TreeNode temp = curr.left;
        curr.left = curr.right;
        curr.right = temp;
        //递归翻转左右子树
        dfs(curr.left);
        dfs(curr.right);

        // 返回根节点
        return curr;
    }

    /**
     * 101. 对称二叉树
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        // left queue 从左到右遍历
        Queue<TreeNode> lq = new LinkedList<>();
        // right queue 从右到左遍历
        Queue<TreeNode> rq = new LinkedList<>();
        lq.add(root);
        rq.add(root);

        while (!lq.isEmpty() || !rq.isEmpty()) {
            TreeNode left = lq.poll();
            TreeNode right = rq.poll();
            // 左右节点空对称，继续检查
            if (left == null && right == null) {
                continue;
            }
            // 不对称：一个节点空，或者两个节点值不同
            if (left == null || right == null || left.val != right.val) {
                return false;
            }

            // 左右节点非空对称，添加节点到队列继续遍历，对称添加，left queue 左左、左右；right queue 右右、右左
            lq.add(left.left);
            lq.add(left.right);
            rq.add(right.right);
            rq.add(right.left);
        }

        return true;
    }

    private boolean isSymmetric(TreeNode left, TreeNode right) {
        // 设置终止条件
        if (left == null && right == null) {
            return true;
        }
        // 一个节点空，另一个节点非空，不对称
        if (left == null || right == null) {
            return false;
        }
        // 节点非空，值不同，不对称
        if (left.val != right.val) {
            return false;
        }
        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }

    /**
     * 1.	根节点：先序遍历的第一个元素是当前树的根节点。
     * 2.	分割中序数组：在中序遍历中找到根节点的位置，左边的部分是左子树，右边的部分是右子树。
     * 3.	递归构建：递归地构建左子树和右子树。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) {
            return null;
        }
        // 中序遍历，key：值，value：数组下标
        Map<Integer, Integer> integerMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            integerMap.put(inorder[i], i);
        }
        return this.buildTree(preorder, 0, integerMap, 0, inorder.length - 1);
    }

    /**
     * 递归构建左右子树
     * <href>https://www.hello-algo.com/chapter_divide_and_conquer/build_binary_tree_problem/#3</href>
     *
     * @param preOrder   前序遍历数组
     * @param i          根节点在 preorder 中的下标
     * @param inorderMap 中序遍历map，key：值，value：下标
     * @param l          子树在 inorder 的起点
     * @param r          子树在 inorder 中的终点
     * @return
     */
    private TreeNode buildTree(int[] preOrder, int i, Map<Integer, Integer> inorderMap, int l, int r) {
        // 设置终止条件
        if (l > r) {
            return null;
        }
        TreeNode root = new TreeNode(preOrder[i]);
        // 根节点在 inorder 中的下标 m
        int m = inorderMap.get(preOrder[i]);
        root.left = buildTree(preOrder, i + 1, inorderMap, l, m - 1);
        // m - l 是左子树长度
        root.right = buildTree(preOrder, i + 1 + (m - l), inorderMap, m + 1, r);
        return root;
    }

    // 根据中序遍历、后序遍历数组构建树
    public TreeNode buildTreea(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length == 0 || postorder.length == 0) {
            return null;
        }
        return this.build(postorder, postorder.length - 1, 0, inorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] postOrder, int postStart, int postEnd, int[] inOrder, int inStart, int inEnd) {
        // 设置终止条件
        if (inStart > inEnd || postStart < postEnd) {
            return null;
        }

        // 中序遍历数组找到根节点
        TreeNode root = new TreeNode(postOrder[postStart]);
        int inRootIndex = inStart;
        for (int i = inStart; i <= inEnd; i++) {
            if (root.val == inOrder[i]) {
                inRootIndex = i;
                break;
            }
        }

        // 计算右子树数量
        int rightSize = inEnd - inRootIndex;

        // 递归构建左右子树；后序遍历顺序为 左右根，注意计算后序遍历数组左右子树起始点的逻辑
        root.left = build(postOrder, postStart - rightSize - 1, postEnd, inOrder, inStart, inRootIndex - 1);
        root.right = build(postOrder, postStart - 1, postStart - rightSize, inOrder, inRootIndex + 1, inEnd);

        return root;
    }

    /**
     * 114.二叉树展开为链表：官方 dfs，
     * 前序遍历：1 2 3 4 5 6
     * 变形的后序遍历，右 -> 左 -> 根，6 5 4 3 2 1，倒着挂 null <- 6 <- 5 <- 4 <- 3 <- 2 <- 1
     * 1
     * / \
     * 2   5
     * / \   \
     * 3   4   6
     */
    private TreeNode pre = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        // 右
        flatten(root.right);
        // 左
        flatten(root.left);
        // 根
        root.right = pre;
        root.left = null;
        pre = root;
    }

    /**
     * •	若当前节点有左子树，就把左子树调整到右侧；
     * •	再把原来的右子树接到新右子树的最末端；
     * •	然后继续向右走。
     */
    public void flattenBfs(TreeNode root) {
        while (root != null) {
            // 左子树非空
            if (root.left != null) {
                // 将左子树挂到右子树，左子树置空
                TreeNode tempRight = root.right;
                root.right = root.left;
                root.left = null;

                // 找到新右子树的最后一个 right 节点
                TreeNode last = root.right;
                while (last.right != null) {
                    last = last.right;
                }
                // 将原右子树挂到新右子树的最后一个 right 节点
                last.right = tempRight;
            }
            // 继续向右走
            root = root.right;
        }
    }

    /**
     * 222. 完全二叉树的节点个数
     * left == right。这说明，左子树一定是满二叉树，因为节点已经填充到右子树了，左子树必定已经填满了。所以左子树的节点总数我们可以直接得到，是 2^left - 1，加上当前这个 root 节点，则正好是 2^left。再对右子树进行递归统计。
     * left != right。说明此时最后一层不满，但倒数第二层已经满了，可以直接得到右子树的节点个数。同理，右子树节点 +root 节点，总数为 2^right。再对左子树进行递归查找。
     */
    public int countNodes(TreeNode root) {
        // 设置终止条件
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        if (leftHeight == rightHeight) {
            // 左子树是满的，节点数 2^leftHeight - 1，加上根节点 1
            return (1 << leftHeight) + countNodes(root.right);
        } else {
            // 右子树是满的，节点数 2^rightHeight - 1，加上根节点 1
            return (1 << rightHeight) + countNodes(root.left);
        }
    }

    private int getHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            node = node.left;
            height++;
        }
        return height;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * 1.	层序遍历：我们使用一个队列 queue 进行层序遍历。
     * 2.	逐层连接：对于每一层节点，将其 next 指针指向该层的下一个节点，最后一个节点的 next 指针设置为 null。
     * 3.	递进操作：将每层节点的左右子节点加入队列，然后重复操作。
     */
    public Node connect(Node root) {
        // 处理边界
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 上一个节点，当前节点，当前层队列长度
            Node pre = null;
            Node curr = null;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                curr = queue.poll();
                // 上个节点非空，则将上一个节点 next 指针指向当前节点
                if (pre != null) {
                    pre.next = curr;
                }
                pre = curr;

                // 将左右子节点加入队列，递进操作
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            // 将这一层最后一个节点 next 指针设置为 null
            curr.next = null;
        }
        return root;
    }

    /**
     * 路径总和，根节点到叶子节点的和
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        // 若当前节点就是叶子节点，那么我们直接判断 sum 是否等于 val 即可
        if (root.left == null && root.right == null && targetSum - root.val == 0) {
            return true;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<Integer> path = new ArrayList<>();
        List<List<Integer>> paths = new ArrayList<>();
        this.dfsPath(root, path, paths);
        int result = 0;
        for (List<Integer> p : paths) {
            int num = 0;
            for (int d : p) {
                num = num * 10 + d;
            }
            result += num;
        }

        return result;
    }

    /**
     * 根节点到叶子节点数字之和
     */
    private int sumNumbers(TreeNode node, int pre) {
        // 设置终止条件
        if (node == null) {
            return 0;
        }
        int sum = 10 * pre + node.val;
        if (node.left == null && node.right == null) {
            return sum;
        }
        return sumNumbers(node.left, sum) + sumNumbers(node.right, sum);
    }

    private void dfsPath(TreeNode node, List<Integer> path, List<List<Integer>> paths) {
        // 设置终止条件
        if (node == null) {
            return;
        }

        // 添加当前节点
        path.add(node.val);
        // 当前节点是叶子节点，收集路径
        if (node.left == null && node.right == null) {
            paths.add(new ArrayList<>(path));
        }

        // 递归左右子树
        dfsPath(node.left, path, paths);
        dfsPath(node.right, path, paths);

        // 回溯删除节点
        path.remove(path.size() - 1);
    }

    /**
     * 二叉树迭代器（中序）
     */
    class BSTIterator {

        private List<TreeNode> list;
        Iterator<TreeNode> it;

        public BSTIterator(TreeNode root) {
            list = new ArrayList<>();
            this.dfs(root);
            it = list.iterator();
        }

        private void dfs(TreeNode node) {
            if (node == null) {
                return;
            }
            dfs(node.left);
            list.add(node);
            dfs(node.right);
        }

        public int next() {
            return it.next().val;

        }

        public boolean hasNext() {
            return it.hasNext();
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 设置终止条件
        if (root == null) {
            return null;
        }
        if (p.val == root.val || q.val == root.val) {
            return root;
        }

        TreeNode left = this.lowestCommonAncestor(root.left, p, q);
        TreeNode right = this.lowestCommonAncestor(root.right, p, q);
        // 左子树空，说明 pq 都在右子树，返回右子树
        if (left == null) {
            return right;
        }
        // 右子树空，返回左子树
        if (right == null) {
            return left;
        }
        // pq 分布在 root 两侧
        return root;
    }

    /**
     * 二叉树的右视图，层序遍历实现
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 层序遍历，将每层最后节点加入结果集
        while (!queue.isEmpty()) {
            TreeNode node = null;
            // 当前层级的节点数量
            int currSize = queue.size();
            for (int i = 0; i < currSize; i++) {
                node = queue.poll();
                // 将左右节点加入队列
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            // 每层的最后一个节点加入结果集
            if (node != null) {
                res.add(node.val);
            }
        }

        return res;
    }

    /**
     * 二叉树的层平均值
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 当前层的节点数量、总和
            int levelSize = queue.size();
            double levelSum = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;
                // 将左右子节点加入队列
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            // 计算层平均值并加入结果集
            res.add(levelSum / levelSize);
        }

        return res;
    }

    /**
     * 102.二叉树的层序遍历
     * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 添加当前层元素到 leve 列表
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            // 把每层的列表加入到结果集 res
            res.add(level);
        }

        return res;
    }

    /**
     * 二叉树锯齿形遍历
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        // 处理边界
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        // 初始化双端队列
        Deque<TreeNode> dq = new LinkedList<>();
        dq.push(root);

        // 是否从左侧遍历
        boolean left2Right = true;
        // 层序遍历，锯齿形遍历
        while (!dq.isEmpty()) {
            int levelSize = dq.size();
            List<Integer> levelRes = new ArrayList<>();
            // 读取当前层节点加入当前层结果集
            for (int i = 0; i < levelSize; i++) {
                if (left2Right) {
                    // 从左往右遍历，读取队列头部
                    TreeNode node = dq.pollFirst();
                    levelRes.add(node.val);
                    // 左右节点一次加入队列尾部
                    if (node.left != null) {
                        dq.addLast(node.left);
                    }
                    if (node.right != null) {
                        dq.addLast(node.right);
                    }
                } else {
                    // 从右往左遍历，读取队列尾部
                    TreeNode node = dq.pollLast();
                    levelRes.add(node.val);
                    // 右左节点一次加入队列头部
                    if (node.right != null) {
                        dq.addFirst(node.right);
                    }
                    if (node.left != null) {
                        dq.addFirst(node.left);
                    }
                }
            }
            // 将当前层结果加入总结果集
            res.add(levelRes);
            left2Right = !left2Right;
        }
        return res;
    }

    int minDiff = Integer.MAX_VALUE;
    Integer preVal = null;

    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        minDiffDfs(root);
        return minDiff;
    }

    /**
     * 利用二叉搜索树特性，中序遍历为天然递增顺序，node.val - pre 计算最小差值
     */
    private void minDiffDfs(TreeNode node) {
        // 设置终止条件
        if (node == null) {
            return;
        }
        minDiffDfs(node.left);
        // 根节点
        if (preVal != null) {
            minDiff = Math.min(minDiff, node.val - preVal);
        }
        preVal = node.val;
        minDiffDfs(node.right);
    }

    long preValue = Long.MIN_VALUE;

    /**
     * 98. 验证二叉搜索树
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 中序遍历
     */
    public boolean isValidBST(TreeNode root) {
        // 设置终止条件
        if (root == null) {
            return true;
        }
        // 左节点
        if (!isValidBST(root.left)) {
            return false;
        }

        // 根节点
        if (root.val <= preValue) {
            return false;
        }
        preValue = root.val;

        // 右节点
        return isValidBST(root.right);
    }

    /**
     * bfs 模拟 dfs
     */
    public boolean isValidBSTBfs(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        long preVal = Long.MIN_VALUE;
        while (!stack.isEmpty() || root != null) {
            // 左节点
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            // 根节点
            root = stack.pop();
            if (root.val <= preVal) {
                return false;
            }
            preVal = root.val;

            // 右节点
            root = root.right;
        }

        return true;
    }

    public boolean isValidBST(TreeNode root, long lower, long upper) {
        // 设置终止条件
        if (root == null) {
            return true;
        }
        // root.val 超出区间 (lower, upper)，则不合法
        if (root.val <= lower || root.val >= upper) {
            return false;
        }
        // 递归判断，左子树更新 upper，右子树更新 lower
        return isValidBST(root.left, lower, root.val) && isValidBST(root.right, root.val, upper);
    }

    /**
     * 二叉搜索树中第 k 小的元素，bfs 中序遍历
     */
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new LinkedList<>();
        int count = 0;
        while (!stack.isEmpty() || root != null) {
            // 左节点
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 根节点
            root = stack.pop();
            count++;
            if (count >= k) {
                return root.val;
            }
            // 右节点
            root = root.right;
        }
        return -1;
    }

}


