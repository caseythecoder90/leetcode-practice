package leetcode.trees.problems.treeconstruction;

import java.util.*;

/**
 * LeetCode 105: Construct Binary Tree from Preorder and Inorder Traversal
 * Difficulty: Medium
 *
 * Given two integer arrays preorder and inorder where preorder is the preorder
 * traversal of a binary tree and inorder is the inorder traversal of the same tree,
 * construct and return the binary tree.
 *
 * Example 1:
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 *
 * Example 2:
 * Input: preorder = [-1], inorder = [-1]
 * Output: [-1]
 *
 * Constraints:
 * - 1 <= preorder.length <= 3000
 * - inorder.length == preorder.length
 * - -3000 <= preorder[i], inorder[i] <= 3000
 * - preorder and inorder consist of unique values
 * - Each value of inorder also appears in preorder
 * - preorder is guaranteed to be the preorder traversal of the tree
 * - inorder is guaranteed to be the inorder traversal of the tree
 */

class TreeNode {
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

public class ConstructBinaryTreeFromPreorderAndInorder {

    /**
     * APPROACH: Recursive Construction with HashMap Optimization
     *
     * Time Complexity: O(n) where n is number of nodes
     * Space Complexity: O(n) for HashMap and recursion stack
     *
     * KEY INSIGHTS:
     * 1. Preorder traversal: Root → Left → Right
     *    - First element is always the root
     *    - Next elements are left subtree, then right subtree
     *
     * 2. Inorder traversal: Left → Root → Right
     *    - Root position divides left and right subtrees
     *    - Everything before root = left subtree
     *    - Everything after root = right subtree
     *
     * 3. Algorithm:
     *    a) Take first element from preorder as root
     *    b) Find root in inorder to determine left/right boundaries
     *    c) Recursively build left subtree (LEFT FIRST)
     *    d) Recursively build right subtree (RIGHT SECOND)
     *
     * EXAMPLE WALKTHROUGH:
     * preorder = [3, 9, 20, 15, 7]
     * inorder  = [9, 3, 15, 20, 7]
     *
     * Step 1: Root = 3 (first in preorder)
     * Step 2: Find 3 in inorder at index 1
     *         Left subtree: [9] (inorder[0:0])
     *         Right subtree: [15, 20, 7] (inorder[2:4])
     *
     * Step 3: Build left subtree
     *         Root = 9, no children
     *
     * Step 4: Build right subtree
     *         Root = 20 (next in preorder)
     *         Left child = 15, Right child = 7
     *
     * Final tree:
     *       3
     *      / \
     *     9   20
     *        /  \
     *       15   7
     */

    // Global index to track current position in preorder array
    private int preorderIndex = 0;

    // HashMap for O(1) lookup of inorder indices
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Build HashMap: value -> index in inorder array
        // This optimization reduces time from O(n²) to O(n)
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        // Start recursive construction with full range
        return helper(preorder, 0, inorder.length - 1);
    }


    /**
     * Recursive helper to construct tree
     *
     * @param preorder - the preorder traversal array
     * @param inStart - start index of current subtree in inorder
     * @param inEnd - end index of current subtree in inorder
     * @return root of constructed subtree
     */
    private TreeNode helper(int[] preorder, int inStart, int inEnd) {
        // Base case: no elements to construct tree
        if (inStart > inEnd) {
            return null;
        }

        // Step 1: Get root value from preorder (first unprocessed element)
        // Post-increment: use current value, then move to next
        int rootVal = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootVal);

        // Step 2: Find root position in inorder array
        // This divides the tree into left and right subtrees
        int inorderIndex = inorderMap.get(rootVal);

        // Step 3: Recursively build LEFT subtree
        // Range: from inStart to one before root position
        // IMPORTANT: Build left BEFORE right (matches preorder)
        root.left = helper(preorder, inStart, inorderIndex - 1);

        // Step 4: Recursively build RIGHT subtree
        // Range: from one after root position to inEnd
        root.right = helper(preorder, inorderIndex + 1, inEnd);

        return root;
    }

    // ========================================================================
    // ALTERNATIVE SOLUTION: Passing Preorder Boundaries
    // ========================================================================

    /**
     * Alternative approach that explicitly tracks preorder boundaries
     * More intuitive but slightly more complex parameter passing
     */
    public TreeNode buildTreeAlternative(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return helperAlt(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private TreeNode helperAlt(int[] preorder, int preStart, int preEnd,
                               int[] inorder, int inStart, int inEnd,
                               Map<Integer, Integer> inMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);
        int inRoot = inMap.get(rootVal);
        int leftSize = inRoot - inStart;

        root.left = helperAlt(preorder, preStart + 1, preStart + leftSize,
                inorder, inStart, inRoot - 1, inMap);
        root.right = helperAlt(preorder, preStart + leftSize + 1, preEnd,
                inorder, inRoot + 1, inEnd, inMap);

        return root;
    }

    // ========================================================================
    // HELPER METHODS FOR TESTING
    // ========================================================================

    /**
     * Print tree structure for visualization
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

    /**
     * Level order traversal for verification
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);
        }

        return result;
    }

    // ========================================================================
    // TEST CASES
    // ========================================================================

    public static void main(String[] args) {
        ConstructBinaryTreeFromPreorderAndInorder solution =
                new ConstructBinaryTreeFromPreorderAndInorder();

        System.out.println("=".repeat(70));
        System.out.println("LeetCode 105: Construct Binary Tree from Preorder and Inorder");
        System.out.println("=".repeat(70));

        // Test Case 1: Example from problem
        System.out.println("\n--- Test Case 1: Standard Binary Tree ---");
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};

        System.out.println("Input:");
        System.out.println("  Preorder:  " + Arrays.toString(preorder1));
        System.out.println("  Inorder:   " + Arrays.toString(inorder1));

        TreeNode tree1 = solution.buildTree(preorder1, inorder1);

        System.out.println("\nConstructed Tree:");
        printTree(tree1, "", false);
        System.out.println("\nLevel Order: " + levelOrder(tree1));
        System.out.println("Expected: [[3], [9, 20], [15, 7]]");

        // Test Case 2: Single node
        System.out.println("\n" + "-".repeat(70));
        System.out.println("\n--- Test Case 2: Single Node ---");
        int[] preorder2 = {-1};
        int[] inorder2 = {-1};

        System.out.println("Input: preorder = " + Arrays.toString(preorder2) +
                ", inorder = " + Arrays.toString(inorder2));

        TreeNode tree2 = solution.buildTree(preorder2, inorder2);
        System.out.println("Output: " + (tree2 != null ? tree2.val : "null"));
        System.out.println("Expected: -1");

        // Test Case 3: Left skewed tree
        System.out.println("\n" + "-".repeat(70));
        System.out.println("\n--- Test Case 3: Left Skewed Tree ---");
        int[] preorder3 = {3, 2, 1};
        int[] inorder3 = {1, 2, 3};

        System.out.println("Input:");
        System.out.println("  Preorder:  " + Arrays.toString(preorder3));
        System.out.println("  Inorder:   " + Arrays.toString(inorder3));

        TreeNode tree3 = solution.buildTree(preorder3, inorder3);

        System.out.println("\nConstructed Tree:");
        printTree(tree3, "", false);
        System.out.println("\nExpected: Left skewed (each node only has left child)");

        // Test Case 4: Right skewed tree
        System.out.println("\n" + "-".repeat(70));
        System.out.println("\n--- Test Case 4: Right Skewed Tree ---");
        int[] preorder4 = {1, 2, 3};
        int[] inorder4 = {1, 2, 3};

        System.out.println("Input:");
        System.out.println("  Preorder:  " + Arrays.toString(preorder4));
        System.out.println("  Inorder:   " + Arrays.toString(inorder4));

        TreeNode tree4 = solution.buildTree(preorder4, inorder4);

        System.out.println("\nConstructed Tree:");
        printTree(tree4, "", false);
        System.out.println("\nExpected: Right skewed (each node only has right child)");

        // Test Case 5: Balanced tree
        System.out.println("\n" + "-".repeat(70));
        System.out.println("\n--- Test Case 5: Balanced Tree ---");
        int[] preorder5 = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder5 = {4, 2, 5, 1, 6, 3, 7};

        System.out.println("Input:");
        System.out.println("  Preorder:  " + Arrays.toString(preorder5));
        System.out.println("  Inorder:   " + Arrays.toString(inorder5));

        TreeNode tree5 = solution.buildTree(preorder5, inorder5);

        System.out.println("\nConstructed Tree:");
        printTree(tree5, "", false);
        System.out.println("\nLevel Order: " + levelOrder(tree5));

        System.out.println("\n" + "=".repeat(70));
        System.out.println("All test cases completed!");
        System.out.println("=".repeat(70));
    }
}