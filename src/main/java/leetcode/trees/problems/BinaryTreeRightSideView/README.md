# 199. Binary Tree Right Side View

**Difficulty:** Medium  
**Pattern:** Binary Tree BFS/Level Order Traversal  
**Companies:** Amazon, Facebook, Microsoft, Google

## Problem Description

Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

### Examples

**Example 1:**
```
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]

Tree visualization:
    1
   / \
  2   3
   \   \
    5   4

Right side view: 1 → 3 → 4
```

**Example 2:**
```
Input: root = [1,null,3]
Output: [1,3]

Tree visualization:
  1
   \
    3

Right side view: 1 → 3
```

**Example 3:**
```
Input: root = []
Output: []
```

## Approach Analysis

### Solution 1: BFS Level Order Traversal (Recommended)

**Key Insight:** Use BFS to process nodes level by level, capturing only the rightmost node at each level.

**Algorithm:**
1. Use a queue to perform level-order traversal
2. For each level, track the number of nodes to process
3. Only add the last node of each level to the result
4. Continue until all levels are processed

**Time Complexity:** O(n) - visit each node exactly once  
**Space Complexity:** O(w) - where w is the maximum width of the tree (queue size)

**Why This Works:**
- BFS naturally processes nodes level by level
- By tracking level size, we can identify the rightmost node
- The rightmost node is the last one processed in each level

### Solution 2: DFS with Level Tracking (Alternative)

**Key Insight:** Use DFS but prioritize right subtree first, adding nodes to result only when visiting a level for the first time.

**Algorithm:**
1. Perform DFS with level parameter
2. Visit right subtree before left subtree
3. Add node value only if it's the first time visiting this level
4. The first node visited at each level (coming from right) is the rightmost visible

**Time Complexity:** O(n) - visit each node exactly once  
**Space Complexity:** O(h) - where h is the height of the tree (recursion stack)

## Pattern Recognition

This problem is a **classic BFS level-order traversal** with a twist:
- Instead of collecting all nodes per level, we only want the last one
- The "right side view" concept maps directly to "rightmost node per level"
- Can also be solved with DFS using level tracking for variety

## Key Learning Points

1. **Level Processing in BFS:** Track `levelSize` to know when a level ends
2. **Index-based Selection:** Use loop index to identify the last element
3. **Alternative Thinking:** Same problem can be solved with DFS using different logic
4. **Pattern Flexibility:** Understanding both BFS and DFS approaches for tree problems

## Implementation Notes

- BFS approach is more intuitive for this specific problem
- DFS approach demonstrates different thinking about "rightmost visible"
- Both solutions handle edge cases (null root, single node) naturally
- Test with various tree shapes: balanced, skewed, single-sided

## Related Problems

- **102. Binary Tree Level Order Traversal** - Direct application of BFS pattern
- **107. Binary Tree Level Order Traversal II** - Bottom-up level order
- **515. Find Largest Value in Each Tree Row** - Similar level processing logic
- **637. Average of Levels in Binary Tree** - Another level-based aggregation

## Study Notes

- **Pattern Mastery:** This is a fundamental BFS tree traversal problem
- **Next Practice:** Try solving with DFS approach for algorithm variety
- **Time to Solve:** ~13 minutes (first attempt with BFS pattern recognition)
- **Confidence Level:** High - pattern recognition led to quick solution