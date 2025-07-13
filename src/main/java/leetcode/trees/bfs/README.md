# Binary Tree - BFS (Breadth-First Search) Pattern

## Overview

**Breadth-First Search (BFS)** in binary trees involves visiting nodes level by level, from left to right. BFS is implemented using a **queue** data structure and is perfect for problems that require level-by-level processing.

## What Problems Does BFS Solve?

### Core Use Cases
1. **Level Order Traversal** - Process nodes level by level
2. **Tree Width/Diameter** - Find maximum width at any level
3. **Level Statistics** - Min/max/average values per level
4. **Shortest Path** - In unweighted trees (closest leaf, etc.)
5. **Tree Serialization** - Level-order representation

### Problem Keywords to Recognize BFS
- "level by level", "level order"
- "width", "diameter", "maximum level sum"
- "minimum depth", "closest leaf"
- "print each level", "zigzag traversal"
- "right side view", "left side view"

## Core BFS Template

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size(); // Critical: capture size before loop
        List<Integer> currentLevel = new ArrayList<>();
        
        // Process all nodes in current level
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            currentLevel.add(node.val);
            
            // Add children for next level
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(currentLevel);
    }
    
    return result;
}
```

## Key BFS Patterns

### 1. Level Order Traversal (Basic)
**Goal**: Visit all nodes level by level
```java
// Template above - returns List<List<Integer>>
```

### 2. Level Statistics (Sum, Max, Min, Average)
**Goal**: Calculate statistics for each level
```java
public int maxLevelSum(TreeNode root) {
    if (root == null) return 0;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    int maxSum = Integer.MIN_VALUE;
    int maxLevel = 1;
    int currentLevel = 1;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        int levelSum = 0;
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            levelSum += node.val;
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        if (levelSum > maxSum) {
            maxSum = levelSum;
            maxLevel = currentLevel;
        }
        currentLevel++;
    }
    
    return maxLevel;
}
```

### 3. Tree Views (Right/Left Side View)
**Goal**: Get rightmost or leftmost node at each level
```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            
            // Rightmost node in level
            if (i == levelSize - 1) {
                result.add(node.val);
            }
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
    
    return result;
}
```

### 4. Minimum Depth (Shortest Path)
**Goal**: Find shortest path to any leaf
```java
public int minDepth(TreeNode root) {
    if (root == null) return 0;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    int depth = 1;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            
            // Found first leaf - shortest path
            if (node.left == null && node.right == null) {
                return depth;
            }
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        depth++;
    }
    
    return depth;
}
```

### 5. ZigZag Level Order
**Goal**: Alternate direction for each level
```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    boolean leftToRight = true;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> currentLevel = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            
            if (leftToRight) {
                currentLevel.add(node.val);
            } else {
                currentLevel.add(0, node.val); // Add to front
            }
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(currentLevel);
        leftToRight = !leftToRight; // Toggle direction
    }
    
    return result;
}
```

## Time and Space Complexity

### Time Complexity
- **All BFS operations**: O(n) where n is the number of nodes
- Every node is visited exactly once
- Each node is added and removed from queue once

### Space Complexity
- **Queue space**: O(w) where w is maximum width of tree
- **In worst case**: O(n) for a complete binary tree (last level has n/2 nodes)
- **Best case**: O(1) for a skewed tree (each level has 1 node)

## When to Use BFS vs DFS

### Use BFS When:
- ✅ Need to process nodes level by level
- ✅ Finding shortest path in unweighted tree
- ✅ Tree width, diameter, or level statistics
- ✅ Need to visit nodes in level order
- ✅ Early termination based on level (min depth)

### Use DFS When:
- ✅ Need to explore paths from root to leaf
- ✅ Tree height, path sum, or node-to-node paths
- ✅ Tree validation or transformation
- ✅ Memory is constrained (DFS uses less space)

## Common Pitfalls and Tips

### 1. **Capture Queue Size Before Loop**
```java
// ❌ Wrong - size changes during loop
while (!queue.isEmpty()) {
    for (int i = 0; i < queue.size(); i++) { // Size changes!
        TreeNode node = queue.poll();
        // Add children...
    }
}

// ✅ Correct - capture size first
while (!queue.isEmpty()) {
    int levelSize = queue.size(); // Fixed size for current level
    for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        // Add children...
    }
}
```

### 2. **Null Check Before Adding to Queue**
```java
// ✅ Always check before adding
if (node.left != null) queue.offer(node.left);
if (node.right != null) queue.offer(node.right);
```

### 3. **Use LinkedList for Queue**
```java
// ✅ Preferred - O(1) operations at both ends
Queue<TreeNode> queue = new LinkedList<>();

// ❌ Avoid - ArrayDeque also good but LinkedList more common in interviews
```

### 4. **Handle Edge Cases**
```java
// Always check for null root
if (root == null) return appropriateEmptyResult;
```

## LeetCode 75 BFS Problems

1. **199. Binary Tree Right Side View** (Medium)
   - Pattern: Level order traversal with rightmost node capture
   
2. **1161. Maximum Level Sum of a Binary Tree** (Medium)
   - Pattern: Level statistics calculation

## Practice Problems by Difficulty

### Easy
- **102. Binary Tree Level Order Traversal**
- **107. Binary Tree Level Order Traversal II** (bottom-up)
- **111. Minimum Depth of Binary Tree**

### Medium  
- **103. Binary Tree Zigzag Level Order Traversal**
- **199. Binary Tree Right Side View**
- **1161. Maximum Level Sum of a Binary Tree**
- **515. Find Largest Value in Each Tree Row**
- **637. Average of Levels in Binary Tree**

### Hard
- **297. Serialize and Deserialize Binary Tree**
- **314. Binary Tree Vertical Order Traversal** (requires coordinate tracking)

## Learning Path

### Beginner
1. Master basic level order traversal
2. Understand queue operations and level size capturing
3. Practice with simple level statistics problems

### Intermediate
4. Learn tree view problems (right/left side view)
5. Practice zigzag and reverse level order
6. Combine BFS with level-based conditions

### Advanced
7. Use BFS for tree serialization and complex transformations
8. Optimize space usage for specific problems
9. Combine BFS with other algorithms for hybrid solutions

---

## Directory Structure

```
trees/bfs/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── problems/
│   ├── BinaryTreeRightSideView/
│   ├── MaximumLevelSum/
│   ├── BinaryTreeLevelOrder/
│   └── MinimumDepth/
└── common/
    └── BFSTemplates.java
```

Start with basic level order traversal to build intuition, then progress to more complex level-based operations!