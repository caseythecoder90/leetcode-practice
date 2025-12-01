package leetcode.trees.bst.problems.MinimumAbsoluteDifferenceBST;

/**
 * LeetCode 530: Minimum Absolute Difference in BST
 * 
 * Problem: Given the root of a Binary Search Tree (BST), return the minimum absolute
 * difference between the values of any two different nodes in the tree.
 * 
 * Key Insights:
 * 1. In-order traversal of BST produces sorted sequence
 * 2. Minimum difference MUST be between consecutive nodes in sorted order
 * 3. Use instance variable to track previous node value across recursive calls
 * 
 * Time Complexity: O(n) - visit each node once
 * Space Complexity: O(h) - recursion stack depth (h = height of tree)
 */
public class MinimumAbsoluteDifferenceBST {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    // Instance variables persist across all recursive calls
    private int minimum = Integer.MAX_VALUE;
    private Integer prev = null;  // Previous node value in in-order traversal
    
    /**
     * Approach: In-Order Traversal with Previous Value Tracking
     * 
     * Why it works:
     * - In-order traversal visits nodes in sorted order
     * - Minimum difference is between consecutive sorted values
     * - Track prev value to compare with current node
     */
    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return minimum;
    }
    
    private void inorder(TreeNode node) {
        if (node == null) return;
        
        // Visit left subtree first (smaller values)
        inorder(node.left);
        
        // Process current node (in sorted order)
        if (prev != null) {
            // Compare with previous value in sorted sequence
            minimum = Math.min(minimum, node.val - prev);
        }
        prev = node.val;  // Update for next node
        
        // Visit right subtree (larger values)
        inorder(node.right);
    }
    
    /**
     * Alternative Approach: Collect all values then sort
     * Less efficient but demonstrates another way to think about it
     * 
     * Time: O(n log n) - sorting
     * Space: O(n) - storing all values
     */
    public int getMinimumDifference_Alternative(TreeNode root) {
        java.util.List<Integer> values = new java.util.ArrayList<>();
        collectValues(root, values);
        java.util.Collections.sort(values);
        
        int minimum = Integer.MAX_VALUE;
        for (int i = 1; i < values.size(); i++) {
            minimum = Math.min(minimum, values.get(i) - values.get(i - 1));
        }
        return minimum;
    }
    
    private void collectValues(TreeNode node, java.util.List<Integer> values) {
        if (node == null) return;
        collectValues(node.left, values);
        values.add(node.val);
        collectValues(node.right, values);
    }
}
