# Binary Tree BFS (Breadth-First Search) - Complete Study Guide

## Core Concept: Level-Order Traversal

BFS processes nodes **level by level**, from left to right within each level. Think of it as exploring the tree "horizontally" rather than "vertically" (like DFS).

```
Example Tree:        BFS Order: 3, 9, 20, 15, 7
      3              Level 0: [3]
     / \             Level 1: [9, 20]  
    9   20           Level 2: [15, 7]
       /  \
      15   7
```

## The Standard BFS Template

```java
public void bfs(TreeNode root) {
    if (root == null) return;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TreeNode current = queue.poll();
        
        // Process current node
        System.out.print(current.val + " ");
        
        // Add children to queue (left first, then right)
        if (current.left != null) queue.offer(current.left);
        if (current.right != null) queue.offer(current.right);
    }
}
```

## Level-by-Level Processing Template

When you need to process nodes level by level (most common for BFS problems):

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();  // Important: capture size before loop
        List<Integer> currentLevel = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode current = queue.poll();
            currentLevel.add(current.val);
            
            // Add children for next level
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        
        result.add(currentLevel);
    }
    
    return result;
}
```

## Key Insight: Why BFS Works for Tree Problems

**Queue Behavior:**
1. **FIFO (First In, First Out):** Nodes are processed in the order they were added
2. **Level Ordering:** Adding left child before right child ensures left-to-right processing
3. **Natural Breadth:** All nodes at level N are processed before any node at level N+1

## Common BFS Problem Patterns

### 1. **Right Side View** (LeetCode 199)
**Goal:** See the rightmost node at each level
**Key:** Take the last node from each level

```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode current = queue.poll();
            
            // If this is the last node in the level, add to result
            if (i == levelSize - 1) {
                result.add(current.val);
            }
            
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
    }
    
    return result;
}
```

### 2. **Level Sum** (LeetCode 1161)
**Goal:** Find maximum sum among all levels
**Key:** Sum all nodes in each level, track maximum

```java
public int maxLevelSum(TreeNode root) {
    if (root == null) return 0;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    int maxSum = Integer.MIN_VALUE;
    int resultLevel = 1;
    int currentLevel = 1;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        int levelSum = 0;
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode current = queue.poll();
            levelSum += current.val;
            
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
        
        if (levelSum > maxSum) {
            maxSum = levelSum;
            resultLevel = currentLevel;
        }
        
        currentLevel++;
    }
    
    return resultLevel;
}
```

### 3. **Tree Inversion** (LeetCode 226) - Your Problem!
**Goal:** Swap left and right children of every node
**Key:** Swap children as you process each node

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
        
        // Add children to continue processing
        if (current.left != null) queue.offer(current.left);
        if (current.right != null) queue.offer(current.right);
    }
    
    return root;
}
```

## Common Mistakes & How to Avoid Them

### ❌ Mistake 1: Forgetting to Capture Level Size
```java
// WRONG - infinite loop!
while (!queue.isEmpty()) {
    for (int i = 0; i < queue.size(); i++) {  // queue.size() changes!
        TreeNode current = queue.poll();
        // ... add children to queue
    }
}

// CORRECT
while (!queue.isEmpty()) {
    int levelSize = queue.size();  // Capture size first!
    for (int i = 0; i < levelSize; i++) {
        // ...
    }
}
```

### ❌ Mistake 2: Adding Null Nodes
```java
// WRONG - will cause NullPointerException
queue.offer(current.left);   // Don't add if null!
queue.offer(current.right);

// CORRECT
if (current.left != null) queue.offer(current.left);
if (current.right != null) queue.offer(current.right);
```

### ❌ Mistake 3: Processing Wrong Order
```java
// WRONG - right-to-left processing
if (current.right != null) queue.offer(current.right);
if (current.left != null) queue.offer(current.left);

// CORRECT - left-to-right processing
if (current.left != null) queue.offer(current.left);
if (current.right != null) queue.offer(current.right);
```

## BFS vs DFS: When to Use Which?

### Use BFS When:
- **Level-based processing:** Need to process nodes level by level
- **Shortest path:** Need minimum depth/distance
- **Width-related:** Working with tree width or level properties
- **Memory allows:** Tree is very deep but not very wide

### Use DFS When:
- **Path-based:** Need to explore paths from root to leaves
- **Height-related:** Working with tree height or depth
- **Memory constrained:** Tree is very wide but not very deep
- **Simple recursion:** Problem naturally fits recursive thinking

## Complexity Analysis

**Time Complexity:** O(n) - visit each node exactly once
**Space Complexity:** O(w) where w is maximum width of tree
- **Best case:** O(1) for completely unbalanced tree
- **Worst case:** O(n/2) = O(n) for complete binary tree (last level has ~n/2 nodes)

## Practice Problems for BFS Mastery

### Easy Level
1. **Binary Tree Level Order Traversal** (LeetCode 102) - Foundation
2. **Average of Levels** (LeetCode 637) - Level processing
3. **Minimum Depth** (LeetCode 111) - Shortest path

### Medium Level
4. **Binary Tree Right Side View** (LeetCode 199) - Your current list
5. **Maximum Level Sum** (LeetCode 1161) - Your current list
6. **Binary Tree Zigzag Traversal** (LeetCode 103) - Direction changes

### For Interview Prep
7. **Invert Binary Tree** (LeetCode 226) - Your current problem!
8. **Symmetric Tree** (LeetCode 101) - Level comparison
9. **Connect Next Right Pointers** (LeetCode 116) - Level linking

## Step-by-Step Problem Solving Approach

1. **Identify if BFS is needed:** Look for level-based keywords
2. **Choose template:** Simple BFS vs level-by-level processing
3. **Handle edge cases:** Null root, single node
4. **Process nodes:** Define what to do with each node
5. **Manage children:** Always check for null before adding to queue
6. **Track level data:** If needed, maintain level-specific information

## Quick Reference - BFS Checklist

- [ ] Handle null root case
- [ ] Initialize queue with root
- [ ] Capture level size before inner loop (if processing levels)
- [ ] Process current node (poll from queue)
- [ ] Add non-null children to queue (left first, then right)
- [ ] Return appropriate result

Remember: **BFS is about exploring the tree horizontally, level by level. The queue ensures we process all nodes at level N before moving to level N+1.**