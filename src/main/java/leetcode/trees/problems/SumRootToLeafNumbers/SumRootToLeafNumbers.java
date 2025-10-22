package leetcode.trees.problems.SumRootToLeafNumbers;

public class SumRootToLeafNumbers {

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
     * Sum Root to Leaf Numbers - DFS Path Accumulation Approach
     *
     * Key Insight: Use DFS to traverse from root to each leaf, building the number
     * along the path by multiplying current number by 10 and adding current node's value.
     * When we reach a leaf, add the complete number to the total sum.
     *
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(h) where h is height of tree (recursion stack)
     *
     * Pattern: DFS with path accumulation (similar to path sum problems)
     */
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    /**
     * DFS helper function that accumulates the number as we traverse
     *
     * @param node - current node being visited
     * @param currentNum - the number formed from root to parent of current node
     * @return sum of all root-to-leaf numbers in the subtree
     */
    private int dfs(TreeNode node, int currentNum) {
        // Base case: empty node contributes 0 to sum
        if (node == null) {
            return 0;
        }

        // Build the number by appending current digit
        // Example: if currentNum = 12 and node.val = 3, newNum = 123
        currentNum = currentNum * 10 + node.val;

        // If this is a leaf node, return the complete number
        if (node.left == null && node.right == null) {
            return currentNum;
        }

        // Recursively get sum from left and right subtrees
        // Each subtree will form its own root-to-leaf numbers starting with currentNum
        int leftSum = dfs(node.left, currentNum);
        int rightSum = dfs(node.right, currentNum);

        return leftSum + rightSum;
    }

    /**
     * Alternative approach: Iterative solution using stack
     *
     * Uses a stack to simulate DFS while tracking both the node and the accumulated number
     */
    public int sumNumbersIterative(TreeNode root) {
        if (root == null) return 0;

        int totalSum = 0;
        // Stack stores pairs of (node, accumulated number so far)
        java.util.Stack<Pair> stack = new java.util.Stack<>();
        stack.push(new Pair(root, root.val));

        while (!stack.isEmpty()) {
            Pair current = stack.pop();
            TreeNode node = current.node;
            int num = current.num;

            // If leaf node, add to total sum
            if (node.left == null && node.right == null) {
                totalSum += num;
            }

            // Push right child first (so left is processed first - DFS order)
            if (node.right != null) {
                stack.push(new Pair(node.right, num * 10 + node.right.val));
            }

            if (node.left != null) {
                stack.push(new Pair(node.left, num * 10 + node.left.val));
            }
        }

        return totalSum;
    }

    // Helper class for iterative approach
    private static class Pair {
        TreeNode node;
        int num;

        Pair(TreeNode node, int num) {
            this.node = node;
            this.num = num;
        }
    }

    public static void main(String[] args) {
        SumRootToLeafNumbers solution = new SumRootToLeafNumbers();

        // Test Case 1: [1,2,3]
        //       1
        //      / \
        //     2   3
        // Paths: 1->2 (12), 1->3 (13)
        // Expected: 12 + 13 = 25
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);

        System.out.println("Test Case 1:");
        System.out.println("Tree: [1,2,3]");
        System.out.println("Expected: 25");
        System.out.println("Recursive Result: " + solution.sumNumbers(root1));
        System.out.println("Iterative Result: " + solution.sumNumbersIterative(root1));
        System.out.println();

        // Test Case 2: [4,9,0,5,1]
        //       4
        //      / \
        //     9   0
        //    / \
        //   5   1
        // Paths: 4->9->5 (495), 4->9->1 (491), 4->0 (40)
        // Expected: 495 + 491 + 40 = 1026
        TreeNode root2 = new TreeNode(4);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(0);
        root2.left.left = new TreeNode(5);
        root2.left.right = new TreeNode(1);

        System.out.println("Test Case 2:");
        System.out.println("Tree: [4,9,0,5,1]");
        System.out.println("Expected: 1026");
        System.out.println("Recursive Result: " + solution.sumNumbers(root2));
        System.out.println("Iterative Result: " + solution.sumNumbersIterative(root2));
        System.out.println();

        // Test Case 3: Single node [9]
        // Expected: 9
        TreeNode root3 = new TreeNode(9);

        System.out.println("Test Case 3:");
        System.out.println("Tree: [9]");
        System.out.println("Expected: 9");
        System.out.println("Recursive Result: " + solution.sumNumbers(root3));
        System.out.println("Iterative Result: " + solution.sumNumbersIterative(root3));
        System.out.println();

        // Test Case 4: Skewed tree [1,2,null,3]
        //   1
        //  /
        // 2
        //  \
        //   3
        // Path: 1->2->3 (123)
        // Expected: 123
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.right = new TreeNode(3);

        System.out.println("Test Case 4:");
        System.out.println("Tree: [1,2,null,3]");
        System.out.println("Expected: 123");
        System.out.println("Recursive Result: " + solution.sumNumbers(root4));
        System.out.println("Iterative Result: " + solution.sumNumbersIterative(root4));
        System.out.println();

        // Test Case 5: Larger tree
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        // Paths: 1->2->4 (124), 1->2->5 (125), 1->3 (13)
        // Expected: 124 + 125 + 13 = 262
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(3);
        root5.left.left = new TreeNode(4);
        root5.left.right = new TreeNode(5);

        System.out.println("Test Case 5:");
        System.out.println("Tree: [1,2,3,4,5]");
        System.out.println("Expected: 262");
        System.out.println("Recursive Result: " + solution.sumNumbers(root5));
        System.out.println("Iterative Result: " + solution.sumNumbersIterative(root5));
    }
}
