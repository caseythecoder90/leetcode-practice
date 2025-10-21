package leetcode.trees.dfs.problems.SameTree;

/**
 * LeetCode 100: Same Tree
 * 
 * Problem: Given the roots of two binary trees p and q, write a function 
 * to check if they are the same or not. Two binary trees are considered 
 * the same if they are structurally identical, and the nodes have the same value.
 * 
 * Approach: Recursive DFS
 * - Base case: both null → return true
 * - If one null → return false
 * - If values differ → return false
 * - Recursively check both subtrees
 * 
 * Time Complexity: O(min(n,m)) where n,m are number of nodes in each tree
 * Space Complexity: O(min(h1,h2)) where h1,h2 are heights (recursion stack)
 */
public class SameTree {
    
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
     * Solution 1: Recursive DFS (Most Intuitive)
     * 
     * Key Insights:
     * 1. Two trees are same if roots match AND both subtrees match
     * 2. Handle null cases first to avoid NullPointerException
     * 3. Use AND (&&) because BOTH subtrees must be identical
     * 4. Early termination on any mismatch
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Base case: both trees are empty
        if (p == null && q == null) {
            return true;
        }
        
        // One tree is empty, the other is not
        if (p == null || q == null) {
            return false;
        }
        
        // Values at current nodes don't match
        if (p.val != q.val) {
            return false;
        }
        
        // Recursively check left and right subtrees
        // BOTH must be true for trees to be same
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    
    /**
     * Solution 2: Compact Recursive Version
     * Same logic, more concise
     */
    public boolean isSameTreeCompact(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && 
               isSameTree(p.left, q.left) && 
               isSameTree(p.right, q.right);
    }
    
    /**
     * Solution 3: Single-line Recursive (For interviews if you want to show off)
     * Same logic, extremely concise
     */
    public boolean isSameTreeOneLiner(TreeNode p, TreeNode q) {
        return (p == null && q == null) || 
               (p != null && q != null && p.val == q.val && 
                isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
    }
    
    // ======================= Helper Methods for Testing =======================
    
    /**
     * Build a binary tree from array representation
     * Uses level-order traversal (BFS) for construction
     */
    public static TreeNode buildTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            // Add left child
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            
            // Add right child
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    /**
     * Print tree structure for debugging (in-order traversal)
     */
    public static void printTree(TreeNode root, String prefix, boolean isLeft) {
        if (root == null) {
            return;
        }
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.val);
        
        if (root.left != null || root.right != null) {
            if (root.left != null) {
                printTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            } else {
                System.out.println(prefix + (isLeft ? "│   " : "    ") + "├── null");
            }
            
            if (root.right != null) {
                printTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
            } else {
                System.out.println(prefix + (isLeft ? "│   " : "    ") + "└── null");
            }
        }
    }
    
    public static void main(String[] args) {
        SameTree solution = new SameTree();
        
        System.out.println("===== LeetCode 100: Same Tree =====\n");
        
        // Test Case 1: Both trees identical
        System.out.println("Test Case 1: [1,2,3] vs [1,2,3]");
        TreeNode p1 = buildTree(new Integer[]{1, 2, 3});
        TreeNode q1 = buildTree(new Integer[]{1, 2, 3});
        System.out.println("Tree p:");
        printTree(p1, "", false);
        System.out.println("Tree q:");
        printTree(q1, "", false);
        boolean result1 = solution.isSameTree(p1, q1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: true");
        System.out.println("✓ PASS: " + (result1 == true) + "\n");
        
        // Test Case 2: Different structure
        System.out.println("Test Case 2: [1,2] vs [1,null,2]");
        TreeNode p2 = buildTree(new Integer[]{1, 2});
        TreeNode q2 = buildTree(new Integer[]{1, null, 2});
        System.out.println("Tree p:");
        printTree(p2, "", false);
        System.out.println("Tree q:");
        printTree(q2, "", false);
        boolean result2 = solution.isSameTree(p2, q2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: false");
        System.out.println("✓ PASS: " + (result2 == false) + "\n");
        
        // Test Case 3: Different values, same structure
        System.out.println("Test Case 3: [1,2,1] vs [1,1,2]");
        TreeNode p3 = buildTree(new Integer[]{1, 2, 1});
        TreeNode q3 = buildTree(new Integer[]{1, 1, 2});
        System.out.println("Tree p:");
        printTree(p3, "", false);
        System.out.println("Tree q:");
        printTree(q3, "", false);
        boolean result3 = solution.isSameTree(p3, q3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: false");
        System.out.println("✓ PASS: " + (result3 == false) + "\n");
        
        // Test Case 4: Both empty trees
        System.out.println("Test Case 4: [] vs []");
        TreeNode p4 = null;
        TreeNode q4 = null;
        System.out.println("Tree p: null");
        System.out.println("Tree q: null");
        boolean result4 = solution.isSameTree(p4, q4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: true");
        System.out.println("✓ PASS: " + (result4 == true) + "\n");
        
        // Test Case 5: One empty, one not
        System.out.println("Test Case 5: [1] vs []");
        TreeNode p5 = buildTree(new Integer[]{1});
        TreeNode q5 = null;
        System.out.println("Tree p:");
        printTree(p5, "", false);
        System.out.println("Tree q: null");
        boolean result5 = solution.isSameTree(p5, q5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: false");
        System.out.println("✓ PASS: " + (result5 == false) + "\n");
        
        // Test Case 6: Single nodes with same value
        System.out.println("Test Case 6: [5] vs [5]");
        TreeNode p6 = buildTree(new Integer[]{5});
        TreeNode q6 = buildTree(new Integer[]{5});
        System.out.println("Tree p:");
        printTree(p6, "", false);
        System.out.println("Tree q:");
        printTree(q6, "", false);
        boolean result6 = solution.isSameTree(p6, q6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: true");
        System.out.println("✓ PASS: " + (result6 == true) + "\n");
        
        // Test Case 7: Larger identical trees
        System.out.println("Test Case 7: [1,2,3,4,5,6,7] vs [1,2,3,4,5,6,7]");
        TreeNode p7 = buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        TreeNode q7 = buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println("Tree p:");
        printTree(p7, "", false);
        System.out.println("Tree q:");
        printTree(q7, "", false);
        boolean result7 = solution.isSameTree(p7, q7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: true");
        System.out.println("✓ PASS: " + (result7 == true) + "\n");
        
        // Test Case 8: Different at deep level
        System.out.println("Test Case 8: [1,2,3,4,5] vs [1,2,3,4,6]");
        TreeNode p8 = buildTree(new Integer[]{1, 2, 3, 4, 5});
        TreeNode q8 = buildTree(new Integer[]{1, 2, 3, 4, 6});
        System.out.println("Tree p:");
        printTree(p8, "", false);
        System.out.println("Tree q:");
        printTree(q8, "", false);
        boolean result8 = solution.isSameTree(p8, q8);
        System.out.println("Result: " + result8);
        System.out.println("Expected: false");
        System.out.println("✓ PASS: " + (result8 == false) + "\n");
        
        System.out.println("===== All Test Cases Complete =====");
    }
}
