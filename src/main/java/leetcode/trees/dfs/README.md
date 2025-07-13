# Binary Tree - DFS (Depth-First Search) Pattern

## Overview

**Depth-First Search (DFS)** in binary trees involves exploring as deep as possible before backtracking. DFS can be implemented recursively (using call stack) or iteratively (using explicit stack). There are three main DFS traversal orders: **Preorder**, **Inorder**, and **Postorder**.

## What Problems Does DFS Solve?

### Core Use Cases
1. **Tree Traversal** - Visit all nodes in specific order
2. **Path Problems** - Root-to-leaf paths, path sums
3. **Tree Validation** - Check if tree satisfies certain properties
4. **Tree Construction** - Build trees from traversal arrays
5. **Tree Modification** - Transform or manipulate tree structure
6. **Depth/Height Calculations** - Maximum/minimum depth

### Problem Keywords to Recognize DFS
- "path from root to leaf", "path sum"
- "maximum depth", "height of tree"  
- "validate", "is valid BST"
- "count nodes", "sum of nodes"
- "preorder", "inorder", "postorder"
- "leaf nodes", "good nodes"

## DFS Traversal Orders

### 1. Preorder (Root → Left → Right)
- Process root first, then left subtree, then right subtree
- **Use for**: Tree copying, serialization, prefix expressions

### 2. Inorder (Left → Root → Right)  
- Process left subtree first, then root, then right subtree
- **Use for**: BST sorted output, validation

### 3. Postorder (Left → Right → Root)
- Process left subtree first, then right subtree, then root
- **Use for**: Tree deletion, calculating heights, postfix expressions

## Core DFS Templates

### 1. Recursive DFS Template (Most Common)
```java
public void dfs(TreeNode root) {
    // Base case
    if (root == null) {
        return;
    }
    
    // Process current node (preorder position)
    process(root);
    
    // Recurse on children
    dfs(root.left);
    dfs(root.right);
    
    // Optional: post-processing (postorder position)
    postProcess(root);
}
```

### 2. DFS with Return Value
```java
public int dfsWithReturn(TreeNode root) {
    // Base case
    if (root == null) {
        return baseValue; // 0, Integer.MAX_VALUE, etc.
    }
    
    // Recurse and get results from children
    int leftResult = dfsWithReturn(root.left);
    int rightResult = dfsWithReturn(root.right);
    
    // Combine results with current node
    int currentResult = combineResults(root.val, leftResult, rightResult);
    
    return currentResult;
}
```

### 3. DFS with Path Tracking
```java
public void dfsWithPath(TreeNode root, List<Integer> path, List<List<Integer>> result) {
    if (root == null) {
        return;
    }
    
    // Add current node to path
    path.add(root.val);
    
    // Check if leaf node (end of path)
    if (root.left == null && root.right == null) {
        result.add(new ArrayList<>(path)); // Make copy of path
    }
    
    // Recurse on children
    dfsWithPath(root.left, path, result);
    dfsWithPath(root.right, path, result);
    
    // Backtrack - remove current node from path
    path.remove(path.size() - 1);
}
```

## Key DFS Patterns

### 1. Maximum Depth/Height
```java
public int maxDepth(TreeNode root) {
    if (root == null) {
        return 0;
    }
    
    int leftDepth = maxDepth(root.left);
    int rightDepth = maxDepth(root.right);
    
    return Math.max(leftDepth, rightDepth) + 1;
}
```

### 2. Path Sum (Root to Leaf)
```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) {
        return false;
    }
    
    // Leaf node - check if remaining sum equals node value
    if (root.left == null && root.right == null) {
        return targetSum == root.val;
    }
    
    // Recurse with reduced target sum
    int remaining = targetSum - root.val;
    return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
}
```

### 3. Count Good Nodes (Node ≥ All Ancestors)
```java
public int goodNodes(TreeNode root) {
    return dfsGoodNodes(root, Integer.MIN_VALUE);
}

private int dfsGoodNodes(TreeNode root, int maxSoFar) {
    if (root == null) {
        return 0;
    }
    
    int count = 0;
    if (root.val >= maxSoFar) {
        count = 1; // Current node is good
    }
    
    int newMax = Math.max(maxSoFar, root.val);
    count += dfsGoodNodes(root.left, newMax);
    count += dfsGoodNodes(root.right, newMax);
    
    return count;
}
```

### 4. Tree Validation (BST)
```java
public boolean isValidBST(TreeNode root) {
    return validateBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private boolean validateBST(TreeNode root, long minVal, long maxVal) {
    if (root == null) {
        return true;
    }
    
    // Check current node bounds
    if (root.val <= minVal || root.val >= maxVal) {
        return false;
    }
    
    // Recurse with updated bounds
    return validateBST(root.left, minVal, root.val) && 
           validateBST(root.right, root.val, maxVal);
}
```

### 5. Longest ZigZag Path
```java
public int longestZigZag(TreeNode root) {
    return Math.max(dfsZigZag(root, true, 0), dfsZigZag(root, false, 0));
}

private int dfsZigZag(TreeNode root, boolean isLeft, int length) {
    if (root == null) {
        return length;
    }
    
    int leftMax = dfsZigZag(root.left, true, isLeft ? 1 : length + 1);
    int rightMax = dfsZigZag(root.right, false, isLeft ? length + 1 : 1);
    
    return Math.max(leftMax, rightMax);
}
```

### 6. Path Sum III (Any Path)
```java
public int pathSum(TreeNode root, int targetSum) {
    if (root == null) return 0;
    
    // Paths starting from current node + paths in left/right subtrees
    return pathSumFrom(root, targetSum) + 
           pathSum(root.left, targetSum) + 
           pathSum(root.right, targetSum);
}

private int pathSumFrom(TreeNode root, long targetSum) {
    if (root == null) return 0;
    
    int count = (root.val == targetSum) ? 1 : 0;
    
    count += pathSumFrom(root.left, targetSum - root.val);
    count += pathSumFrom(root.right, targetSum - root.val);
    
    return count;
}
```

## Iterative DFS (Using Stack)

### Preorder Iterative
```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(node.val);
        
        // Push right first (since stack is LIFO)
        if (node.right != null) stack.push(node.right);
        if (node.left != null) stack.push(node.left);
    }
    
    return result;
}
```

### Inorder Iterative
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    
    while (current != null || !stack.isEmpty()) {
        // Go to leftmost node
        while (current != null) {
            stack.push(current);
            current = current.left;
        }
        
        // Process node
        current = stack.pop();
        result.add(current.val);
        
        // Move to right subtree
        current = current.right;
    }
    
    return result;
}
```

## Time and Space Complexity

### Time Complexity
- **All DFS operations**: O(n) where n is the number of nodes
- Every node is visited exactly once

### Space Complexity
- **Recursive DFS**: O(h) where h is height of tree (call stack)
- **Iterative DFS**: O(h) for explicit stack
- **Best case**: O(log n) for balanced tree
- **Worst case**: O(n) for skewed tree

## When to Use DFS vs BFS

### Use DFS When:
- ✅ Need to explore all paths from root to leaves
- ✅ Tree height, depth, or path-related problems
- ✅ Tree validation or property checking
- ✅ Need to process nodes in specific order (pre/in/post)
- ✅ Memory is constrained (DFS uses less space than BFS)

### Use BFS When:
- ✅ Need to process nodes level by level
- ✅ Finding shortest path or minimum depth
- ✅ Level statistics or tree width problems

## Common Pitfalls and Tips

### 1. **Handle Null Nodes**
```java
// Always check for null first
if (root == null) {
    return appropriateBaseValue;
}
```

### 2. **Choose Correct Recursion Position**
```java
// Preorder: process before recursion
process(root);
dfs(root.left);
dfs(root.right);

// Postorder: process after recursion  
dfs(root.left);
dfs(root.right);
process(root);
```

### 3. **Path Tracking - Make Copies**
```java
// ❌ Wrong - all paths share same list
result.add(path);

// ✅ Correct - each path gets its own copy
result.add(new ArrayList<>(path));
```

### 4. **Backtracking - Don't Forget to Remove**
```java
path.add(root.val);
// ... recursive calls ...
path.remove(path.size() - 1); // Critical: backtrack
```

## LeetCode 75 DFS Problems

1. **104. Maximum Depth of Binary Tree** (Easy)
   - Pattern: Basic recursive depth calculation
   
2. **872. Leaf-Similar Trees** (Easy)  
   - Pattern: DFS to collect leaf values
   
3. **1448. Count Good Nodes in Binary Tree** (Medium)
   - Pattern: DFS with ancestor information
   
4. **437. Path Sum III** (Medium)
   - Pattern: DFS with path sum calculation
   
5. **1372. Longest ZigZag Path in a Binary Tree** (Medium)
   - Pattern: DFS with direction tracking
   
6. **236. Lowest Common Ancestor of a Binary Tree** (Medium)
   - Pattern: DFS with node finding

## Practice Problems by Difficulty

### Easy
- **100. Same Tree**
- **101. Symmetric Tree**  
- **110. Balanced Binary Tree**
- **111. Minimum Depth of Binary Tree**
- **226. Invert Binary Tree**

### Medium
- **98. Validate Binary Search Tree**
- **112. Path Sum**
- **113. Path Sum II**
- **114. Flatten Binary Tree to Linked List**
- **124. Binary Tree Maximum Path Sum**

### Hard
- **297. Serialize and Deserialize Binary Tree**
- **124. Binary Tree Maximum Path Sum**

## Learning Path

### Beginner  
1. Master basic tree traversals (preorder, inorder, postorder)
2. Learn recursive thinking and base cases
3. Practice simple depth/height calculations

### Intermediate
4. Learn path-related problems and backtracking
5. Practice tree validation and property checking  
6. Combine DFS with additional data structures (HashMap, etc.)

### Advanced
7. Master complex path problems (any node to any node)
8. Learn tree construction and serialization
9. Optimize space complexity for specific problems

---

## Directory Structure

```
trees/dfs/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── problems/
│   ├── MaximumDepth/
│   ├── LeafSimilarTrees/
│   ├── CountGoodNodes/
│   ├── PathSumIII/
│   ├── LongestZigZagPath/
│   └── LowestCommonAncestor/
└── common/
    └── DFSTemplates.java
```

Start with basic traversals and depth calculations, then progress to more complex path and validation problems!