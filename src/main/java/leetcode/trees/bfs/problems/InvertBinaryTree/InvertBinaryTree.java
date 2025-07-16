package leetcode.binarytree.bfs.problems.InvertBinaryTree;

import java.util.*;

/**
 * LeetCode 226: Invert Binary Tree (BFS Solution)
 *
 * Pattern: Binary Tree BFS (Level Order Traversal)
 *
 * Problem: Given the root of a binary tree, invert the tree, and return its root.
 * Invert means to swap the left and right children of every node.
 *
 * Key Insights:
 * - "Invert" = swap left and right children at every node
 * - Can be solved with either BFS (level-order) or DFS
 * - BFS processes nodes level by level and swaps children
 * - No need to build a new tree - modify existing tree in-place
 *
 * Time: O(n) - visit each node exactly once
 * Space: O(w) where w is maximum width of tree (queue space)
 */
public class InvertBinaryTree {

    /**
     * Definition for a binary tree node.
     */
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
     * BFS Solution: Level-order traversal with child swapping
     *
     * Algorithm:
     * 1. Use queue for BFS traversal
     * 2. For each node, swap its left and right children
     * 3. Add children to queue for next level processing
     * 4. Continue until all levels processed
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            // Swap left and right children (this is the "invert" operation)
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            // Add children to queue for next level processing
            // Note: We add the children AFTER swapping, so we're adding
            // what were originally the right and left children
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }

        return root;
    }

    /**
     * Alternative BFS Solution: Level-by-level processing
     * This version explicitly processes one level at a time.
     * Same time/space complexity but shows the level structure more clearly.
     */
    public TreeNode invertTreeLevelByLevel(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();  // Process one level at a time

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                // Swap children
                TreeNode temp = current.left;
                current.left = current.right;
                current.right = temp;

                // Add children for next level
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
        }

        return root;
    }

    /**
     * DFS Solution (for comparison): Recursive approach
     * Often considered more elegant and uses less memory for balanced trees.
     */
    public TreeNode invertTreeDFS(TreeNode root) {
        if (root == null) {
            return null;
        }

        // Recursively invert left and right subtrees
        TreeNode left = invertTreeDFS(root.left);
        TreeNode right = invertTreeDFS(root.right);

        // Swap the children
        root.left = right;
        root.right = left;

        return root;
    }

    /**
     * Helper method to print tree in level order (for testing)
     */
    public List<List<Integer>> levelOrderPrint(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                currentLevel.add(current.val);

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }

            result.add(currentLevel);
        }

        return result;
    }

    /**
     * Helper method to build tree from array representation
     * Uses level-order construction where null represents empty nodes
     */
    public TreeNode buildTreeFromArray(Integer[] vals) {
        if (vals == null || vals.length == 0 || vals[0] == null) {
            return null;
        }

        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            TreeNode current = queue.poll();

            // Add left child
            if (i < vals.length && vals[i] != null) {
                current.left = new TreeNode(vals[i]);
                queue.offer(current.left);
            }
            i++;

            // Add right child
            if (i < vals.length && vals[i] != null) {
                current.right = new TreeNode(vals[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    public static void main(String[] args) {
        InvertBinaryTree solution = new InvertBinaryTree();

        System.out.println("=== Invert Binary Tree - BFS Solution ===\n");

        // Test Case 1: Standard example from LeetCode
        System.out.println("Test Case 1: Standard Tree");
        Integer[] tree1 = {4, 2, 7, 1, 3, 6, 9};
        TreeNode root1 = solution.buildTreeFromArray(tree1);

        System.out.println("Original tree (level order): " + solution.levelOrderPrint(root1));
        TreeNode inverted1 = solution.invertTree(root1);
        System.out.println("Inverted tree (level order): " + solution.levelOrderPrint(inverted1));
        System.out.println("Expected: [[4], [7, 2], [9, 6, 3, 1]]\n");

        // Test Case 2: Small tree
        System.out.println("Test Case 2: Small Tree");
        Integer[] tree2 = {2, 1, 3};
        TreeNode root2 = solution.buildTreeFromArray(tree2);

        System.out.println("Original tree (level order): " + solution.levelOrderPrint(root2));
        TreeNode inverted2 = solution.invertTree(root2);
        System.out.println("Inverted tree (level order): " + solution.levelOrderPrint(inverted2));
        System.out.println("Expected: [[2], [3, 1]]\n");

        // Test Case 3: Single node
        System.out.println("Test Case 3: Single Node");
        Integer[] tree3 = {1};
        TreeNode root3 = solution.buildTreeFromArray(tree3);

        System.out.println("Original tree (level order): " + solution.levelOrderPrint(root3));
        TreeNode inverted3 = solution.invertTree(root3);
        System.out.println("Inverted tree (level order): " + solution.levelOrderPrint(inverted3));
        System.out.println("Expected: [[1]]\n");

        // Test Case 4: Empty tree
        System.out.println("Test Case 4: Empty Tree");
        TreeNode root4 = null;
        TreeNode inverted4 = solution.invertTree(root4);
        System.out.println("Result: " + (inverted4 == null ? "null" : "not null"));
        System.out.println("Expected: null\n");

        // Test Case 5: Unbalanced tree (only left children)
        System.out.println("Test Case 5: Left-skewed Tree");
        Integer[] tree5 = {1, 2, null, 3, null, null, null};
        TreeNode root5 = solution.buildTreeFromArray(tree5);

        System.out.println("Original tree (level order): " + solution.levelOrderPrint(root5));
        TreeNode inverted5 = solution.invertTree(root5);
        System.out.println("Inverted tree (level order): " + solution.levelOrderPrint(inverted5));
        System.out.println("Expected: [[1], [null, 2], [null, null, null, 3]]\n");

        // Demonstrate both BFS approaches
        System.out.println("=== Comparing BFS Approaches ===");
        Integer[] testTree = {1, 2, 3, 4, 5, 6, 7};

        // Standard BFS
        TreeNode testRoot1 = solution.buildTreeFromArray(testTree);
        System.out.println("Standard BFS approach:");
        System.out.println("Before: " + solution.levelOrderPrint(testRoot1));
        solution.invertTree(testRoot1);
        System.out.println("After: " + solution.levelOrderPrint(testRoot1));

        // Level-by-level BFS
        TreeNode testRoot2 = solution.buildTreeFromArray(testTree);
        System.out.println("\nLevel-by-level BFS approach:");
        System.out.println("Before: " + solution.levelOrderPrint(testRoot2));
        solution.invertTreeLevelByLevel(testRoot2);
        System.out.println("After: " + solution.levelOrderPrint(testRoot2));

        // DFS comparison
        TreeNode testRoot3 = solution.buildTreeFromArray(testTree);
        System.out.println("\nDFS approach (for comparison):");
        System.out.println("Before: " + solution.levelOrderPrint(testRoot3));
        solution.invertTreeDFS(testRoot3);
        System.out.println("After: " + solution.levelOrderPrint(testRoot3));

        System.out.println("\n=== Key Insights ===");
        System.out.println("1. BFS processes nodes level by level using a queue");
        System.out.println("2. The 'invert' operation is simply swapping left and right children");
        System.out.println("3. We modify the tree in-place - no need to build a new tree");
        System.out.println("4. Both BFS and DFS work equally well for this problem");
        System.out.println("5. BFS space complexity: O(width), DFS space complexity: O(height)");

        System.out.println("\n=== Complexity Analysis ===");
        System.out.println("Time Complexity: O(n) - visit each node exactly once");
        System.out.println("Space Complexity: O(w) where w is maximum width of tree");
        System.out.println("- Best case: O(1) for completely unbalanced tree");
        System.out.println("- Worst case: O(n/2) = O(n) for complete binary tree");
    }
}