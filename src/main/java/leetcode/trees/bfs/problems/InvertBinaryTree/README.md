# 226. Invert Binary Tree

## Problem Description

Given the root of a binary tree, invert the tree, and return its root.

**What "Invert" Means:**
Invert means to swap the left and right children of every node in the tree. The result is a horizontally flipped (mirrored) version of the original tree.

## Examples

**Example 1:**
```
Input:      Output:
    4          4
   / \        / \
  2   7      7   2
 / \ / \    / \ / \
1  3 6  9  9  6 3  1
```

**Example 2:**
```
Input:      Output:
   2           2
  / \         / \
 1   3       3   1
```

**Example 3:**
```
Input: []
Output: []
```

## Constraints

- The number of nodes in the tree is in the range `[0, 100]`
- `-100 <= Node.val <= 100`

## Approach Analysis

### Pattern Recognition

This is a **Binary Tree BFS problem** with the following characteristics:
- Need to process all nodes in the tree ✓
- Order of processing doesn't affect the final result ✓
- Can be solved with level-order traversal ✓
- Tree modification/transformation problem ✓

### Why BFS Works

**Level-by-level processing**: BFS naturally processes nodes level by level, which makes it easy to visualize and debug the inversion process.

**Queue-based approach**: The queue ensures we process all nodes at level N before moving to level N+1, giving us a systematic way to traverse the entire tree.

**In-place modification**: We can modify the existing tree structure by swapping children as we visit each node.

### Core Algorithm (BFS)

The fundamental insight: **For each node visited, swap its left and right children.**

```
1. Initialize queue with root node
2. While queue is not empty:
   a. Remove node from front of queue
   b. Swap node's left and right children
   c. Add non-null children to back of queue
3. Return root (tree is now inverted)
```

## Solution Approaches

### Approach 1: Standard BFS (Recommended)

```java
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TreeNode current = queue.poll();
        
        // Swap children (this is the "invert" operation)
        TreeNode temp = current.left;
        current.left = current.right;
        current.right = temp;
        
        // Add children for next level
        if (current.left != null) queue.offer(current.left);
        if (current.right != null) queue.offer(current.right);
    }
    
    return root;
}
```

**Why this approach:**
- Simple and intuitive
- Easy to understand the level-by-level processing
- Consistent with other BFS tree problems

### Approach 2: Level-by-Level BFS

```java
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();  // Process one complete level
        
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
```

**When to use:**
- When you need to explicitly track which level you're processing
- When the problem requires level-specific operations
- For educational purposes to understand BFS structure better

### Approach 3: DFS (Alternative - for comparison)

```java
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);
    
    root.left = right;
    root.right = left;
    
    return root;
}
```

## Complexity Analysis

### Time Complexity: O(n)
- We visit each node in the tree exactly once
- Each visit performs a constant-time swap operation
- Total: O(n) where n is the number of nodes

### Space Complexity: O(w) where w is maximum width of tree
- **BFS Space**: Determined by queue size at any point
- **Best case**: O(1) for completely unbalanced tree (like a linked list)
- **Worst case**: O(n/2) = O(n) for complete binary tree (last level has ~n/2 nodes)
- **Average case**: O(w) where w is the maximum width

**Comparison with DFS:**
- **DFS Space**: O(h) where h is height of tree
- Choose BFS when tree is very deep but not wide
- Choose DFS when tree is very wide but not deep

## Edge Cases to Consider

### 1. Empty Tree
```java
Input: root = null
Output: null
```
**Handling**: Check for null at the beginning

### 2. Single Node
```java
Input: root = [1]
Output: [1]
```
**Handling**: Swap operation has no effect (null children)

### 3. Unbalanced Trees
```java
Input: [1,2,null,3,null]  // Left-skewed
Output: [1,null,2,null,3] // Right-skewed
```
**Handling**: BFS naturally handles any tree shape

### 4. Complete Binary Tree
```java
Input: [1,2,3,4,5,6,7]
Output: [1,3,2,7,6,5,4]
```
**Handling**: Standard case, all levels fully populated

## Common Mistakes & How to Avoid

### ❌ Mistake 1: Trying to Build New Tree
```java
// WRONG - unnecessarily complex
TreeNode newLeft = invertTree(original.right);
TreeNode newRight = invertTree(original.left);
return new TreeNode(original.val, newLeft, newRight);
```
**Fix**: Modify existing tree in-place by swapping children

### ❌ Mistake 2: Forgetting Null Checks
```java
// WRONG - will cause NullPointerException
queue.offer(current.left);   // Don't add null!
queue.offer(current.right);
```
**Fix**: Always check for null before adding to queue

### ❌ Mistake 3: Swapping After Adding to Queue
```java
// WRONG - adds wrong children to queue
if (current.left != null) queue.offer(current.left);
if (current.right != null) queue.offer(current.right);
// Swap happens too late!
```
**Fix**: Swap first, then add children to queue

## Key Insights

1. **Inversion = Swapping**: The entire operation is just swapping left/right children at each node
2. **Order Independence**: BFS and DFS both work because swap order doesn't matter
3. **In-place Modification**: No need to create new nodes or trees
4. **Queue Management**: Key to BFS is proper queue usage with null checks
5. **Pattern Recognition**: This is a tree transformation problem, perfect for BFS

## Related Problems

- **Binary Tree Level Order Traversal** (LeetCode 102) - Foundation BFS
- **Symmetric Tree** (LeetCode 101) - Tree comparison using BFS
- **Binary Tree Right Side View** (LeetCode 199) - Level-based BFS processing
- **Same Tree** (LeetCode 100) - Tree comparison
- **Maximum Depth of Binary Tree** (LeetCode 104) - Tree traversal

## Practice Tips

1. **Start with small examples**: Draw trees by hand and trace the algorithm
2. **Understand the swap**: Focus on the simple swap operation at each node
3. **Master BFS template**: This problem uses the standard BFS pattern
4. **Compare approaches**: Implement both BFS and DFS to understand trade-offs
5. **Visualize levels**: Think about how the tree looks after each level is processed

This problem is an excellent introduction to BFS tree traversal and demonstrates how simple operations (swapping) can solve seemingly complex problems (tree inversion).