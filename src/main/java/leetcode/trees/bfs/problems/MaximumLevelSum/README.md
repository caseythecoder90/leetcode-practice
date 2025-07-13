# Maximum Level Sum of a Binary Tree

**LeetCode 1161** | **Medium** | **Binary Tree BFS**

## Problem Description

Given the root of a binary tree, return the **smallest** level number that has the maximum sum of nodes.

The root is at level 1.

### Examples

**Example 1:**
```
Input: root = [1,7,0,7,-8,null,null]
Output: 2
Explanation: 
Level 1 sum = 1
Level 2 sum = 7 + 0 = 7
Level 3 sum = 7 + (-8) = -1
So we return the level with the maximum sum which is level 2.
```

**Example 2:**
```
Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
Output: 2
Explanation:
Level 1 sum = 989
Level 2 sum = 10250
Level 3 sum = 98693 + (-89388) = 9305
Level 4 sum = -32127
Maximum sum is 10250 at level 2.
```

## Approach Analysis

### Why BFS?
This is a **classic BFS problem** because we need to:
1. Process nodes **level by level**
2. Calculate **statistics for each level** (sum)
3. Find the level with the **maximum sum**

### Key Insights
1. **Level Order Traversal**: BFS naturally processes nodes level by level
2. **Queue Size Capture**: Critical to capture `queue.size()` before the for loop to ensure we only process the current level
3. **Tie Breaking**: Return the **smallest** level number when there are ties (use `>` not `>=`)
4. **Edge Cases**: Handle empty tree and negative values properly

### Algorithm Steps
1. Initialize queue with root node
2. For each level:
   - Capture current queue size (number of nodes in this level)
   - Process exactly that many nodes, calculating level sum
   - Add children to queue for next level
   - Update maximum sum and level if current sum is greater
3. Return the level with maximum sum

## Solution Walkthrough

### Core BFS Template Application
```java
public int maxLevelSum(TreeNode root) {
    if (root == null) return 0;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    int maxSum = Integer.MIN_VALUE;
    int maxLevel = 1;
    int currentLevel = 1;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size(); // ⭐ Critical: capture before loop
        int levelSum = 0;
        
        // Process all nodes in current level
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            levelSum += node.val;
            
            // Add children for next level
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        // Update maximum (use > for smallest level in ties)
        if (levelSum > maxSum) {
            maxSum = levelSum;
            maxLevel = currentLevel;
        }
        
        currentLevel++;
    }
    
    return maxLevel;
}
```

### Step-by-Step Execution (Example 1)

**Tree: [1,7,0,7,-8,null,null]**
```
       1
      / \
     7   0
    / \
   7  -8
```

**Execution Trace:**

**Level 1:**
- Queue: [1]
- levelSize = 1
- Process node 1: levelSum = 1
- Add children: queue = [7, 0]
- levelSum = 1, maxSum = 1, maxLevel = 1

**Level 2:**
- Queue: [7, 0]
- levelSize = 2
- Process node 7: levelSum = 7
- Add children: queue = [0, 7, -8]
- Process node 0: levelSum = 7
- Add children: queue = [7, -8] (no children for 0)
- levelSum = 7 > maxSum = 1, so maxSum = 7, maxLevel = 2

**Level 3:**
- Queue: [7, -8]
- levelSize = 2
- Process node 7: levelSum = 7
- Process node -8: levelSum = -1
- levelSum = -1 < maxSum = 7, no update
- Queue empty, algorithm ends

**Result:** maxLevel = 2

## Complexity Analysis

### Time Complexity: O(n)
- Visit each node exactly once
- Each node is added to and removed from queue once
- Level sum calculation is O(1) per node

### Space Complexity: O(w)
- Where w is the maximum width of the tree
- Queue stores at most one complete level
- **Best case**: O(1) for skewed tree
- **Worst case**: O(n) for complete binary tree (last level has n/2 nodes)

## Critical Implementation Details

### 1. Queue Size Capture
```java
// ❌ WRONG - size changes during iteration
while (!queue.isEmpty()) {
    for (int i = 0; i < queue.size(); i++) { // Bug: size changes!
        TreeNode node = queue.poll();
        // Add children changes queue.size()
    }
}

// ✅ CORRECT - capture size before loop
while (!queue.isEmpty()) {
    int levelSize = queue.size(); // Fixed for current level
    for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        // Safe to add children now
    }
}
```

### 2. Tie Breaking (Smallest Level)
```java
// ❌ WRONG - returns largest level in ties
if (levelSum >= maxSum) {
    maxLevel = currentLevel;
}

// ✅ CORRECT - returns smallest level in ties
if (levelSum > maxSum) {
    maxLevel = currentLevel;
}
```

### 3. Integer Overflow Handling
```java
// For problems with very large sums, consider:
long levelSum = 0;
long maxSum = Long.MIN_VALUE;
```

## Edge Cases to Consider

1. **Empty Tree**: `root == null` → return 0
2. **Single Node**: Only level 1 exists → return 1
3. **All Negative Values**: Find least negative sum
4. **Integer Overflow**: Large positive/negative values
5. **Tie Scenarios**: Multiple levels with same sum

## Alternative Approaches

### Approach 1: Collect All Sums First
```java
public int maxLevelSum(TreeNode root) {
    List<Integer> levelSums = new ArrayList<>();
    
    // BFS to collect all level sums
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        int sum = 0;
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            sum += node.val;
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        levelSums.add(sum);
    }
    
    // Find maximum sum level
    int maxSum = Integer.MIN_VALUE;
    int maxLevel = 1;
    
    for (int i = 0; i < levelSums.size(); i++) {
        if (levelSums.get(i) > maxSum) {
            maxSum = levelSums.get(i);
            maxLevel = i + 1; // Convert to 1-indexed
        }
    }
    
    return maxLevel;
}
```

**Trade-offs:**
- **Pros**: Clearer separation of concerns, easier to debug
- **Cons**: Extra O(h) space for level sums list
- **When to use**: When you need level sums for other purposes

### Approach 2: DFS with Level Tracking
```java
public int maxLevelSum(TreeNode root) {
    List<Integer> levelSums = new ArrayList<>();
    dfs(root, 0, levelSums);
    
    int maxSum = Integer.MIN_VALUE;
    int maxLevel = 1;
    
    for (int i = 0; i < levelSums.size(); i++) {
        if (levelSums.get(i) > maxSum) {
            maxSum = levelSums.get(i);
            maxLevel = i + 1;
        }
    }
    
    return maxLevel;
}

private void dfs(TreeNode root, int level, List<Integer> levelSums) {
    if (root == null) return;
    
    if (level >= levelSums.size()) {
        levelSums.add(0);
    }
    
    levelSums.set(level, levelSums.get(level) + root.val);
    
    dfs(root.left, level + 1, levelSums);
    dfs(root.right, level + 1, levelSums);
}
```

**Trade-offs:**
- **Pros**: Uses DFS pattern, familiar to some
- **Cons**: More complex, less intuitive for level-order problems
- **When to use**: When already using DFS for other tree operations

## Related Problems

### Similar BFS Level Processing
- **102. Binary Tree Level Order Traversal** - Basic template
- **199. Binary Tree Right Side View** - Rightmost node per level
- **515. Find Largest Value in Each Tree Row** - Maximum value per level
- **637. Average of Levels in Binary Tree** - Average per level

### Pattern Variations
- **Level statistics**: sum, average, max, min per level
- **Level filtering**: find levels meeting certain criteria
- **Level transformation**: modify tree based on level properties

## Study Notes

### Key Takeaways
1. **BFS is natural for level-order problems** - use it when you see "level", "depth", or "layer"
2. **Queue size capture is critical** - always get size before processing loop
3. **Tie-breaking matters** - read problem carefully for "smallest" vs "largest"
4. **Edge cases are important** - test with empty, single node, and all negative trees

### Common Mistakes
- Forgetting to capture queue size before loop
- Using `>=` instead of `>` for tie breaking
- Not handling negative values properly
- Forgetting null checks before adding children

### Interview Tips
- Start with the standard BFS template
- Clearly state why BFS is the right choice
- Walk through a small example step by step
- Mention time/space complexity
- Consider edge cases and alternative approaches