# Binary Tree DFS - Cheat Sheet

## Quick Reference Guide

### When to Use DFS
- ✅ Root-to-leaf paths, path sums
- ✅ Tree height, depth calculations  
- ✅ Tree validation, property checking
- ✅ Node counting, tree transformation
- ✅ Ancestor-descendant relationships

## Core DFS Templates

### 1. Basic Recursive DFS
```java
public int dfs(TreeNode root) {
    // Base case
    if (root == null) {
        return 0; // or appropriate base value
    }
    
    // Recurse on children
    int left = dfs(root.left);
    int right = dfs(root.right);
    
    // Combine results with current node
    return Math.max(left, right) + 1; // Example: height calculation
}
```

### 2. DFS with Additional Parameters
```java
public void dfs(TreeNode root, int targetSum, List<Integer> path) {
    if (root == null) return;
    
    // Process current node
    path.add(root.val);
    
    // Check if leaf and condition met
    if (root.left == null && root.right == null && targetSum == root.val) {
        // Found valid path
        processPath(path);
    }
    
    // Recurse with updated parameters
    dfs(root.left, targetSum - root.val, path);
    dfs(root.right, targetSum - root.val, path);
    
    // Backtrack
    path.remove(path.size() - 1);
}
```

### 3. DFS with Global State
```java
private int maxPath = 0; // Global variable
public int findMaxPath(TreeNode root) {
    dfs(root);
    return maxPath;
}

private int dfs(TreeNode root) {
    if (root == null) return 0;
    
    int left = Math.max(0, dfs(root.left));  // Only positive contributions
    int right = Math.max(0, dfs(root.right));
    
    // Update global maximum
    maxPath = Math.max(maxPath, left + right + root.val);
    
    // Return best path through current node
    return Math.max(left, right) + root.val;
}
```

## Essential DFS Patterns

### 1. Maximum Depth/Height
```java
// Maximum Depth of Binary Tree - LeetCode 104
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    
    return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
}
```

### 2. Path Sum (Root to Leaf)
```java
// Path Sum - LeetCode 112
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    
    // Leaf node check
    if (root.left == null && root.right == null) {
        return targetSum == root.val;
    }
    
    // Recurse with reduced sum
    return hasPathSum(root.left, targetSum - root.val) || 
           hasPathSum(root.right, targetSum - root.val);
}
```

### 3. Count Good Nodes
```java
// Count Good Nodes in Binary Tree - LeetCode 1448
public int goodNodes(TreeNode root) {
    return dfs(root, Integer.MIN_VALUE);
}

private int dfs(TreeNode root, int maxSoFar) {
    if (root == null) return 0;
    
    int count = (root.val >= maxSoFar) ? 1 : 0;
    int newMax = Math.max(maxSoFar, root.val);
    
    count += dfs(root.left, newMax);
    count += dfs(root.right, newMax);
    
    return count;
}
```

### 4. Leaf-Similar Trees
```java
// Leaf-Similar Trees - LeetCode 872
public boolean leafSimilar(TreeNode root1, TreeNode root2) {
    List<Integer> leaves1 = new ArrayList<>();
    List<Integer> leaves2 = new ArrayList<>();
    
    getLeaves(root1, leaves1);
    getLeaves(root2, leaves2);
    
    return leaves1.equals(leaves2);
}

private void getLeaves(TreeNode root, List<Integer> leaves) {
    if (root == null) return;
    
    if (root.left == null && root.right == null) {
        leaves.add(root.val);
    }
    
    getLeaves(root.left, leaves);
    getLeaves(root.right, leaves);
}
```

### 5. Path Sum III (Any Path)
```java
// Path Sum III - LeetCode 437
public int pathSum(TreeNode root, int targetSum) {
    if (root == null) return 0;
    
    // Paths starting from current + paths in subtrees
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

### 6. Lowest Common Ancestor
```java
// Lowest Common Ancestor - LeetCode 236
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) {
        return root;
    }
    
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    if (left != null && right != null) {
        return root; // Current node is LCA
    }
    
    return left != null ? left : right;
}
```

### 7. Longest ZigZag Path
```java
// Longest ZigZag Path - LeetCode 1372
private int maxZigZag = 0;

public int longestZigZag(TreeNode root) {
    dfs(root, true, 0);  // Start as left child
    dfs(root, false, 0); // Start as right child
    return maxZigZag;
}

private void dfs(TreeNode root, boolean isLeft, int length) {
    if (root == null) return;
    
    maxZigZag = Math.max(maxZigZag, length);
    
    if (isLeft) {
        dfs(root.left, true, 1);           // Reset zigzag
        dfs(root.right, false, length + 1); // Continue zigzag
    } else {
        dfs(root.left, true, length + 1);   // Continue zigzag
        dfs(root.right, false, 1);          // Reset zigzag
    }
}
```

## Traversal Orders Quick Reference

### Preorder (Root → Left → Right)
```java
public void preorder(TreeNode root) {
    if (root == null) return;
    
    process(root);        // Process current
    preorder(root.left);  // Left subtree
    preorder(root.right); // Right subtree
}
```

### Inorder (Left → Root → Right)
```java
public void inorder(TreeNode root) {
    if (root == null) return;
    
    inorder(root.left);   // Left subtree
    process(root);        // Process current
    inorder(root.right);  // Right subtree
}
```

### Postorder (Left → Right → Root)
```java
public void postorder(TreeNode root) {
    if (root == null) return;
    
    postorder(root.left);  // Left subtree
    postorder(root.right); // Right subtree
    process(root);         // Process current
}
```

## Pattern Recognition Guide

| Problem Type | Pattern | Key Indicator |
|--------------|---------|---------------|
| **Height/Depth** | Max/Min recursion | "maximum depth", "height" |
| **Path Sum** | Leaf checking + recursion | "root to leaf", "path sum" |
| **Counting** | Accumulate + recurse | "count nodes", "good nodes" |
| **Validation** | Boolean recursion | "is valid", "check property" |
| **Tree Comparison** | Dual recursion | "same tree", "leaf similar" |
| **Path Finding** | Backtracking | "all paths", "collect paths" |
| **LCA** | Bottom-up search | "common ancestor" |

## Implementation Checklist

### Essential Steps
1. ✅ **Null check**: Handle base case first
2. ✅ **Leaf check**: Identify when to stop/process
3. ✅ **Recursive calls**: Left and right subtrees
4. ✅ **Result combination**: How to merge subresults
5. ✅ **Return value**: What information flows up

### Common Patterns
```java
// Pattern 1: Calculate and return
return combineResults(root.val, left, right);

// Pattern 2: Process and continue
process(root);
dfs(root.left, newParams);
dfs(root.right, newParams);

// Pattern 3: Accumulate globally
globalVariable += processResult;
return localResult;
```

## Time & Space Complexity

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| **Basic DFS** | O(n) | O(h) | h = height of tree |
| **Path Finding** | O(n) | O(h) | May copy paths: O(n×h) |
| **Tree Validation** | O(n) | O(h) | Single pass |
| **Dual Tree** | O(min(n,m)) | O(h) | Early termination possible |

**Space Breakdown:**
- Call stack: O(h) where h is tree height
- Balanced tree: O(log n) space
- Skewed tree: O(n) space
- Additional space for path tracking: O(h) to O(n×h)

## LeetCode Problems Quick Reference

### Easy
- **104. Maximum Depth** → Basic recursion
- **100. Same Tree** → Dual recursion  
- **101. Symmetric Tree** → Mirror recursion
- **110. Balanced Binary Tree** → Height calculation
- **872. Leaf-Similar Trees** → Leaf collection

### Medium
- **98. Validate BST** → Bounds checking
- **112. Path Sum** → Target reduction
- **113. Path Sum II** → Path collection
- **1448. Count Good Nodes** → Ancestor tracking
- **437. Path Sum III** → Multiple starting points
- **1372. Longest ZigZag** → Direction tracking
- **236. Lowest Common Ancestor** → Bottom-up search

### Hard
- **124. Binary Tree Maximum Path Sum** → Global optimization
- **297. Serialize/Deserialize** → Tree reconstruction

## Interview Tips

### Template Selection (30 seconds)
1. **Simple calculation?** → Basic recursive template
2. **Path tracking?** → Backtracking template
3. **Global optimization?** → Global state template
4. **Tree validation?** → Boolean recursive template

### Common Mistakes to Avoid
- ❌ **Forgetting null checks** - always handle base case
- ❌ **Wrong leaf detection** - check both children are null
- ❌ **Not backtracking** - remove from path after recursion
- ❌ **Integer overflow** - use long for large sums
- ❌ **Wrong return position** - preorder vs postorder processing

### Optimization Opportunities
- **Early termination** for validation problems
- **Memoization** for overlapping subproblems
- **Iterative** conversion to save stack space
- **Single pass** for multiple calculations

## Practice Progression

1. **Master basic recursion** (LeetCode 104)
2. **Learn path problems** (LeetCode 112, 113)
3. **Practice counting** (LeetCode 1448)  
4. **Try complex paths** (LeetCode 437)
5. **Challenge with LCA** (LeetCode 236)