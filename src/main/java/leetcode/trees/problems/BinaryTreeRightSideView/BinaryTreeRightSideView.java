package leetcode.trees.problems.BinaryTreeRightSideView;

import java.util.*;

public class BinaryTreeRightSideView {
    
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
     * Binary Tree Right Side View - BFS Level Order Traversal Approach
     * 
     * Key Insight: Use BFS to traverse level by level, and for each level,
     * only capture the rightmost (last) node value.
     * 
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(w) where w is maximum width of tree (queue size)
     * 
     * Pattern: Level-order traversal with modification to track last element per level
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add to result only if this is the last (rightmost) node in level
                if (i == levelSize - 1) {
                    result.add(node.val);
                }
                
                // Add children for next level (left first, then right)
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    /**
     * Alternative Approach: DFS with Level Tracking
     * NOTE: Try this approach next time for practice with different solution methods
     * 
     * Idea: Use DFS but go right-first, and only add to result if we haven't
     * seen this level before (first time visiting each level from right side)
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsHelper(root, 0, result);
        return result;
    }
    
    private void dfsHelper(TreeNode node, int level, List<Integer> result) {
        if (node == null) return;
        
        // If this is the first time we're visiting this level
        if (level == result.size()) {
            result.add(node.val);
        }
        
        // Go right first, then left (ensures rightmost node is seen first per level)
        dfsHelper(node.right, level + 1, result);
        dfsHelper(node.left, level + 1, result);
    }
    
    public static void main(String[] args) {
        BinaryTreeRightSideView solution = new BinaryTreeRightSideView();
        
        // Test Case 1: [1,2,3,null,5,null,4]
        //       1
        //      / \
        //     2   3
        //      \   \
        //       5   4
        // Expected: [1,3,4]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right = new TreeNode(5);
        root1.right.right = new TreeNode(4);
        
        System.out.println("Test Case 1:");
        System.out.println("Expected: [1, 3, 4]");
        System.out.println("BFS Result: " + solution.rightSideView(root1));
        System.out.println("DFS Result: " + solution.rightSideViewDFS(root1));
        System.out.println();
        
        // Test Case 2: [1,2,3,4,null,null,null,5]
        //     1
        //    / \
        //   2   3
        //  /
        // 4
        //  \
        //   5
        // Expected: [1,3,4,5]
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.left.right = new TreeNode(5);

        System.out.println("Test Case 2:");
        System.out.println("Expected: [1, 3, 4, 5]");
        System.out.println("BFS Result: " + solution.rightSideView(root2));
        System.out.println("DFS Result: " + solution.rightSideViewDFS(root2));
        System.out.println();

        // Test Case 3: [1,null,3]
        //   1
        //    \
        //     3
        // Expected: [1,3]
        TreeNode root3 = new TreeNode(1);
        root3.right = new TreeNode(3);

        System.out.println("Test Case 3:");
        System.out.println("Expected: [1, 3]");
        System.out.println("BFS Result: " + solution.rightSideView(root3));
        System.out.println("DFS Result: " + solution.rightSideViewDFS(root3));
        System.out.println();

        // Test Case 4: []
        // Expected: []
        System.out.println("Test Case 4:");
        System.out.println("Expected: []");
        System.out.println("BFS Result: " + solution.rightSideView(null));
        System.out.println("DFS Result: " + solution.rightSideViewDFS(null));
        System.out.println();

        // Test Case 5: Single node [1]
        // Expected: [1]
        TreeNode root5 = new TreeNode(1);
        System.out.println("Test Case 5:");
        System.out.println("Expected: [1]");
        System.out.println("BFS Result: " + solution.rightSideView(root5));
        System.out.println("DFS Result: " + solution.rightSideViewDFS(root5));
    }
}