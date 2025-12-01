package leetcode.trees.problems.AverageOfLevels;

import java.util.*;

public class AverageOfLevels {

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
     * Solution 1: BFS Level Order Traversal (Recommended)
     *
     * Key Insight: Use BFS to traverse level by level, calculate sum of nodes
     * at each level, then compute average = sum / count.
     *
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(w) where w is maximum width of tree (queue size)
     *
     * Pattern: Level-order traversal with aggregation per level
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) return result;

        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // Capture level size BEFORE processing
            int levelSize = queue.size();
            long levelSum = 0; // Use long to prevent overflow

            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;

                // Add children for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            // Calculate and store average for this level
            result.add((double) levelSum / levelSize);
        }

        return result;
    }

    /**
     * Solution 2: DFS with Level Tracking (Alternative)
     *
     * Key Insight: Use DFS to traverse the tree, tracking sum and count for each
     * level separately, then compute averages at the end.
     *
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(h) where h is height of tree (recursion stack + storage)
     *
     * Pattern: DFS with level-based data aggregation
     */
    public List<Double> averageOfLevelsDFS(TreeNode root) {
        List<Long> sums = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        // Collect sums and counts for each level
        dfsHelper(root, 0, sums, counts);

        // Compute averages
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < sums.size(); i++) {
            result.add((double) sums.get(i) / counts.get(i));
        }

        return result;
    }

    private void dfsHelper(TreeNode node, int level, List<Long> sums, List<Integer> counts) {
        if (node == null) return;

        // If this is the first time we're visiting this level, initialize
        if (level == sums.size()) {
            sums.add(0L);
            counts.add(0);
        }

        // Add current node's value to this level's sum
        sums.set(level, sums.get(level) + node.val);
        counts.set(level, counts.get(level) + 1);

        // Recursively process left and right subtrees
        dfsHelper(node.left, level + 1, sums, counts);
        dfsHelper(node.right, level + 1, sums, counts);
    }

    public static void main(String[] args) {
        AverageOfLevels solution = new AverageOfLevels();

        // Test Case 1: [3,9,20,null,null,15,7]
        //       3
        //      / \
        //     9   20
        //        /  \
        //       15   7
        // Expected: [3.00000, 14.50000, 11.00000]
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);

        System.out.println("Test Case 1:");
        System.out.println("Expected: [3.00000, 14.50000, 11.00000]");
        System.out.println("BFS Result: " + solution.averageOfLevels(root1));
        System.out.println("DFS Result: " + solution.averageOfLevelsDFS(root1));
        System.out.println();

        // Test Case 2: [3,9,20,15,7]
        //       3
        //      / \
        //     9   20
        //    / \
        //   15  7
        // Expected: [3.00000, 14.50000, 11.00000]
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.left.left = new TreeNode(15);
        root2.left.right = new TreeNode(7);

        System.out.println("Test Case 2:");
        System.out.println("Expected: [3.00000, 14.50000, 11.00000]");
        System.out.println("BFS Result: " + solution.averageOfLevels(root2));
        System.out.println("DFS Result: " + solution.averageOfLevelsDFS(root2));
        System.out.println();

        // Test Case 3: Single node [1]
        // Expected: [1.00000]
        TreeNode root3 = new TreeNode(1);
        System.out.println("Test Case 3:");
        System.out.println("Expected: [1.00000]");
        System.out.println("BFS Result: " + solution.averageOfLevels(root3));
        System.out.println("DFS Result: " + solution.averageOfLevelsDFS(root3));
        System.out.println();

        // Test Case 4: Unbalanced tree
        //     1
        //    /
        //   2
        //  /
        // 3
        // Expected: [1.00000, 2.00000, 3.00000]
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.left = new TreeNode(3);

        System.out.println("Test Case 4:");
        System.out.println("Expected: [1.00000, 2.00000, 3.00000]");
        System.out.println("BFS Result: " + solution.averageOfLevels(root4));
        System.out.println("DFS Result: " + solution.averageOfLevelsDFS(root4));
        System.out.println();

        // Test Case 5: Wide tree with large values
        //       2147483647
        //      /          \
        // 2147483647  2147483647
        // Expected: [2147483647.0, 2147483647.0]
        TreeNode root5 = new TreeNode(2147483647);
        root5.left = new TreeNode(2147483647);
        root5.right = new TreeNode(2147483647);

        System.out.println("Test Case 5 (Large values - testing overflow handling):");
        System.out.println("Expected: [2.147483647E9, 2.147483647E9]");
        System.out.println("BFS Result: " + solution.averageOfLevels(root5));
        System.out.println("DFS Result: " + solution.averageOfLevelsDFS(root5));
    }
}
