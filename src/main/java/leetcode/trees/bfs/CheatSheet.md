# Binary Tree BFS - Cheat Sheet

## Quick Reference Guide

### When to Use BFS
- ✅ Level-by-level processing
- ✅ Tree width, level statistics  
- ✅ Shortest path to leaf
- ✅ Tree views (right/left side)
- ✅ Level order output

## Core BFS Template

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size(); // ⭐ Critical: capture before loop
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

## Pattern Variations

### 1. Level Statistics (Sum/Max/Min/Average)
```java
// Maximum Level Sum - LeetCode 1161
public int maxLevelSum(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    int maxSum = Integer.MIN_VALUE;
    int maxLevel = 1, currentLevel = 1;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        int levelSum = 0;
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            levelSum += node.val; // Calculate statistic
            
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

### 2. Right Side View
```java
// Binary Tree Right Side View - LeetCode 199
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            
            // Last node in level = rightmost
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

### 3. Minimum Depth (Shortest Path)
```java
// Minimum Depth of Binary Tree - LeetCode 111
public int minDepth(TreeNode root) {
    if (root == null) return 0;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    int depth = 1;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            
            // First leaf found = minimum depth
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

### 4. ZigZag Level Order
```java
// Binary Tree ZigZag Level Order - LeetCode 103
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

### 5. Level Order Bottom-Up  
```java
// Binary Tree Level Order Traversal II - LeetCode 107
public List<List<Integer>> levelOrderBottom(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    // Use standard BFS
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
        
        result.add(0, currentLevel); // Add to front for bottom-up
    }
    
    return result;
}
```

### 6. Average of Levels
```java
// Average of Levels in Binary Tree - LeetCode 637
public List<Double> averageOfLevels(TreeNode root) {
    List<Double> result = new ArrayList<>();
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        long levelSum = 0; // Use long to prevent overflow
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            levelSum += node.val;
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add((double) levelSum / levelSize);
    }
    
    return result;
}
```

## Key Patterns Summary

| Pattern | When to Use | Key Implementation Detail |
|---------|------------|---------------------------|
| **Basic Level Order** | Need all nodes level by level | Standard template with level lists |
| **Level Statistics** | Sum/max/min/average per level | Accumulate values during level processing |
| **Tree Views** | Rightmost/leftmost nodes | Check position in level (first/last) |
| **Shortest Path** | Minimum depth to leaf | Early termination on first leaf |
| **ZigZag** | Alternating direction | Toggle flag and add to front/back |
| **Bottom-Up** | Reverse level order | Add levels to front of result |

## Quick Implementation Checklist

### Essential Steps
1. ✅ **Null check**: `if (root == null) return appropriateDefault;`
2. ✅ **Queue setup**: `Queue<TreeNode> queue = new LinkedList<>();`
3. ✅ **Add root**: `queue.offer(root);`
4. ✅ **Main loop**: `while (!queue.isEmpty())`
5. ✅ **Capture level size**: `int levelSize = queue.size();`
6. ✅ **Process level**: `for (int i = 0; i < levelSize; i++)`
7. ✅ **Add children**: Check null before adding to queue

### Common Mistakes to Avoid
- ❌ **Using `queue.size()` in loop condition** - size changes during iteration
- ❌ **Forgetting null checks** before adding children
- ❌ **Wrong queue operations** - use `offer()`/`poll()`, not `add()`/`remove()`
- ❌ **Not handling empty tree** - always check root == null first

## Time & Space Complexity

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| **Level Order** | O(n) | O(w) | w = max width of tree |
| **Level Statistics** | O(n) | O(w) | Same as level order |
| **Tree Views** | O(n) | O(w) | Only store one node per level |
| **Min Depth** | O(n)* | O(w) | *Best case: early termination |

**Space Breakdown:**
- Queue space: O(w) where w is maximum width
- Complete binary tree: w = n/2, so O(n) space
- Skewed tree: w = 1, so O(1) space

## LeetCode Problems Quick Reference

### Easy
- **102. Binary Tree Level Order Traversal** → Basic template
- **107. Binary Tree Level Order Traversal II** → Add to front
- **111. Minimum Depth of Binary Tree** → Early termination

### Medium
- **103. Binary Tree Zigzag Level Order** → Direction toggle
- **199. Binary Tree Right Side View** → Last in level
- **1161. Maximum Level Sum** → Level statistics
- **515. Find Largest Value in Each Tree Row** → Max per level
- **637. Average of Levels** → Average per level

### Advanced Variations
- **314. Binary Tree Vertical Order** → Coordinate tracking
- **297. Serialize/Deserialize** → Level order encoding

## Interview Tips

### Template Recognition (30 seconds)
1. **Level processing?** → BFS
2. **Statistics per level?** → Level sum/max/min pattern
3. **Tree view?** → First/last in level pattern
4. **Shortest path?** → Early termination pattern

### Code Structure (2 minutes)
1. Start with basic template
2. Modify level processing loop based on problem
3. Add result collection as needed
4. Handle edge cases (null root, empty levels)

### Optimization Opportunities
- **Early termination** for min depth problems
- **Single pass** for multiple statistics
- **Space optimization** when only current level matters

## Practice Progression

1. **Master basic level order** (LeetCode 102)
2. **Learn level statistics** (LeetCode 1161)  
3. **Practice tree views** (LeetCode 199)
4. **Try early termination** (LeetCode 111)
5. **Challenge with ZigZag** (LeetCode 103)