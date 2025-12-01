package leetcode.trees.problems.treeconstruction;

import java.util.HashMap;
import java.util.Map;

class Solution {
    private int postorderIndex;
    private final Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // Start from the END of postorder
        postorderIndex = postorder.length - 1;

        // Build HashMap for O(1) inorder lookups
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return helper(postorder, 0, inorder.length - 1);
    }

    private TreeNode helper(int[] postorder, int inStart, int inEnd) {
        // Base case: invalid range
        if (inStart > inEnd) {
            return null;
        }

        // Get root from END of postorder (and move backwards)
        int rootVal = postorder[postorderIndex--];
        TreeNode root = new TreeNode(rootVal);

        // Find root in inorder
        int inorderIndex = inorderMap.get(rootVal);

        // CRITICAL: Build RIGHT before LEFT!
        root.right = helper(postorder, inorderIndex + 1, inEnd);
        root.left = helper(postorder, inStart, inorderIndex - 1);

        return root;
    }
}