package com.algo.common;

/**
 * @author liangjun
 **/
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int value) {
        this.val = value;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode getTree() {
        // 1 7 4 5 3 6 7
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(7);
        TreeNode l21 = new TreeNode(4);
        TreeNode l22 = new TreeNode(6);
        TreeNode r1 = new TreeNode(3);
        TreeNode r21 = new TreeNode(5);
        TreeNode r22 = new TreeNode(7);
        root.left = l1;
        root.right = r1;
        l1.left = l21;
        l1.right = r21;
        r1.left = l22;
        r1.right = r22;
        return root;
    }
}
