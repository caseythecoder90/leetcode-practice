package leetcode.trees.bst.problems.KthSmallestElementBST;

/**
 * LeetCode 230: Kth Smallest Element in a BST
 * 
 * Given the root of a BST and an integer k, return the kth smallest value (1-indexed).
 * 
 * Key Insights:
 * 1. In-order traversal of BST gives sorted values
 * 2. The k-th element in sorted order is the k-th smallest
 * 3. Can stop early once we find the k-th element
 * 4. Use primitives (int) not objects (Integer) for performance
 * 
 * Time Complexity: O(H + k) where H is height to reach leftmost, then k more nodes
 * Space Complexity: O(H) for recursion stack
 */
public class KthSmallestElementBST {

    public static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     * OPTIMAL SOLUTION - Fastest approach with early termination
     * 
     * Optimizations:
     * 1. Use primitive int instead of Integer (no boxing/unboxing)
     * 2. Early termination when k-th element found
     * 3. Store k as instance variable (avoid passing in every call)
     */
    private int count;
    private int result;
    private int k;
    
    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        this.count = 0;
        inorder(root);
        return result;
    }
    
    private boolean inorder(TreeNode node) {
        if (node == null) return false;
        
        // Search left subtree first (smaller values)
        if (inorder(node.left)) return true;  // Found it! Stop recursion
        
        // Process current node
        count++;
        if (count == k) {
            result = node.val;
            return true;  // Signal found to stop all recursion
        }
        
        // Search right subtree (larger values)
        return inorder(node.right);
    }
    
    /**
     * YOUR ORIGINAL SOLUTION (with minor improvements)
     * Still correct but slower due to:
     * 1. Using Integer instead of int
     * 2. No early termination signal
     */
    public int kthSmallest_YourApproach(TreeNode root, int k) {
        Integer[] countAndResult = new Integer[]{0, null};
        inorderOriginal(root, k, countAndResult);
        return countAndResult[1];
    }
    
    private void inorderOriginal(TreeNode node, int k, Integer[] state) {
        if (node == null) return;
        
        inorderOriginal(node.left, k, state);
        
        state[0]++;  // count
        if (state[0] == k) {
            state[1] = node.val;  // result
            return;
        }
        
        inorderOriginal(node.right, k, state);
    }
    
    /**
     * ALTERNATIVE: Iterative approach (slightly less memory)
     * 
     * Pros: No recursion overhead, explicit stack control
     * Cons: More code, less intuitive
     */
    public int kthSmallest_Iterative(TreeNode root, int k) {
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        TreeNode curr = root;
        int count = 0;
        
        while (curr != null || !stack.isEmpty()) {
            // Go to leftmost node
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            // Process node
            curr = stack.pop();
            count++;
            if (count == k) {
                return curr.val;
            }
            
            // Move to right subtree
            curr = curr.right;
        }
        
        return -1; // Should never reach here given constraints
    }
    
    /**
     * FOLLOW-UP SOLUTION: Augmented BST for frequent queries
     * 
     * If tree is modified often AND we need k-th smallest frequently:
     * - Store subtree size in each node
     * - Can find k-th element in O(H) without traversing k nodes
     * 
     * Trade-off: O(H) per query but O(H) overhead per insert/delete
     */
    static class AugmentedTreeNode {
        int val;
        int leftSize;  // Number of nodes in left subtree
        AugmentedTreeNode left;
        AugmentedTreeNode right;
        
        AugmentedTreeNode(int val) {
            this.val = val;
            this.leftSize = 0;
        }
    }
    
    public int kthSmallest_Augmented(AugmentedTreeNode root, int k) {
        if (root == null) return -1;
        
        int leftSize = root.leftSize;
        
        if (k <= leftSize) {
            // k-th smallest is in left subtree
            return kthSmallest_Augmented(root.left, k);
        } else if (k == leftSize + 1) {
            // Current node is the k-th smallest
            return root.val;
        } else {
            // k-th smallest is in right subtree
            // Adjust k: remove left subtree size and current node
            return kthSmallest_Augmented(root.right, k - leftSize - 1);
        }
    }
}
