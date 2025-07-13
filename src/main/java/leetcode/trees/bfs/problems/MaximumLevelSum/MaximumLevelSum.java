package leetcode.trees.bfs.problems.MaximumLevelSum;

import java.util.*;

/**
 * LeetCode 1161: Maximum Level Sum of a Binary Tree
 * 
 * Problem: Given the root of a binary tree, return the level with the maximum sum.
 * The root is at level 1.
 * 
 * Approach: BFS Level Order Traversal
 * - Use BFS to process nodes level by level
 * - Calculate sum for each level
 * - Track the level with maximum sum
 * - Return the smallest level number in case of ties
 * 
 * Time Complexity: O(n) where n is the number of nodes
 * Space Complexity: O(w) where w is the maximum width of the tree
 */
public class MaximumLevelSum {
    
    // Definition for a binary tree node
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
    
    /**
     * Find the level with maximum sum using BFS
     * 
     * Key Insights:
     * 1. BFS naturally processes nodes level by level
     * 2. Capture queue size before processing to ensure we only process current level
     * 3. Track current level number and compare sums
     * 4. Return smallest level number in case of ties (by checking only when sum is strictly greater)
     */
    public int maxLevelSum(TreeNode root) {
        if (root == null) {
            return 0; // Edge case: empty tree
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int maxSum = Integer.MIN_VALUE;  // Track maximum sum found
        int maxLevel = 1;                // Track level with maximum sum
        int currentLevel = 1;            // Track current level being processed
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Critical: capture size before processing
            int levelSum = 0;             // Sum for current level
            
            // Process all nodes in current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;
                
                // Add children for next level processing
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            // Update maximum if current level sum is greater
            // Use > (not >=) to return smallest level in case of ties
            if (levelSum > maxSum) {
                maxSum = levelSum;
                maxLevel = currentLevel;
            }
            
            currentLevel++; // Move to next level
        }
        
        return maxLevel;
    }
    
    /**
     * Alternative approach using List to store level sums first
     * More intuitive but uses extra space
     */
    public int maxLevelSumAlternative(TreeNode root) {
        if (root == null) return 0;
        
        List<Integer> levelSums = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        // Collect all level sums
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelSum = 0;
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            levelSums.add(levelSum);
        }
        
        // Find level with maximum sum
        int maxSum = Integer.MIN_VALUE;
        int maxLevel = 1;
        
        for (int i = 0; i < levelSums.size(); i++) {
            if (levelSums.get(i) > maxSum) {
                maxSum = levelSums.get(i);
                maxLevel = i + 1; // Levels are 1-indexed
            }
        }
        
        return maxLevel;
    }
    
    // Helper method to build test trees
    public static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    // Helper method to print level sums for debugging
    public static void printLevelSums(TreeNode root) {
        if (root == null) return;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 1;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelSum = 0;
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            System.out.println("Level " + level + ": sum = " + levelSum);
            level++;
        }
    }
    
    public static void main(String[] args) {
        MaximumLevelSum solution = new MaximumLevelSum();
        
        // Test Case 1: [1,7,0,7,-8,null,null]
        // Level 1: 1 (sum = 1)
        // Level 2: 7 + 0 = 7
        // Level 3: 7 + (-8) = -1
        // Expected: 2 (level with sum 7)
        System.out.println("=== Test Case 1 ===");
        TreeNode tree1 = buildTree(new Integer[]{1, 7, 0, 7, -8, null, null});
        printLevelSums(tree1);
        int result1 = solution.maxLevelSum(tree1);
        System.out.println("Maximum level sum at level: " + result1);
        System.out.println("Expected: 2\n");
        
        // Test Case 2: [989,null,10250,98693,-89388,null,null,null,-32127]
        // Level 1: 989
        // Level 2: 10250  
        // Level 3: 98693 + (-89388) = 9305
        // Level 4: -32127
        // Expected: 2 (level with sum 10250)
        System.out.println("=== Test Case 2 ===");
        TreeNode tree2 = buildTree(new Integer[]{989, null, 10250, 98693, -89388, null, null, null, -32127});
        printLevelSums(tree2);
        int result2 = solution.maxLevelSum(tree2);
        System.out.println("Maximum level sum at level: " + result2);
        System.out.println("Expected: 2\n");
        
        // Test Case 3: Single node
        System.out.println("=== Test Case 3 ===");
        TreeNode tree3 = buildTree(new Integer[]{5});
        printLevelSums(tree3);
        int result3 = solution.maxLevelSum(tree3);
        System.out.println("Maximum level sum at level: " + result3);
        System.out.println("Expected: 1\n");
        
        // Test Case 4: All negative values
        // [-1,-2,-3,-4,-5,-6,-7]
        System.out.println("=== Test Case 4 ===");
        TreeNode tree4 = buildTree(new Integer[]{-1, -2, -3, -4, -5, -6, -7});
        printLevelSums(tree4);
        int result4 = solution.maxLevelSum(tree4);
        System.out.println("Maximum level sum at level: " + result4);
        System.out.println("Expected: 1 (least negative sum)\n");
        
        // Test Case 5: Tie scenario - should return smallest level
        // [1,1,1,1,1,1,1] - all levels sum to powers of 2
        System.out.println("=== Test Case 5 ===");
        TreeNode tree5 = buildTree(new Integer[]{4, 2, 2, 1, 1, 1, 1});
        printLevelSums(tree5);
        int result5 = solution.maxLevelSum(tree5);
        System.out.println("Maximum level sum at level: " + result5);
        System.out.println("Expected: 2 or 3 depending on which has higher sum\n");
    }
}