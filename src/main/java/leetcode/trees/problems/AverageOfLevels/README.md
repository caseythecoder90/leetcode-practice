# 637. Average of Levels in Binary Tree

**Difficulty:** Easy
**Pattern:** Binary Tree BFS/Level Order Traversal, DFS with Level Tracking
**Companies:** Facebook, Amazon, Microsoft

## Problem Description

Given the root of a binary tree, return the average value of the nodes on each level in the form of an array. Answers within 10^-5 of the actual answer will be accepted.

### Examples

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [3.00000,14.50000,11.00000]

Tree visualization:
    3
   / \
  9   20
     /  \
    15   7

Level 0: avg = 3 / 1 = 3.00000
Level 1: avg = (9 + 20) / 2 = 14.50000
Level 2: avg = (15 + 7) / 2 = 11.00000
```

**Example 2:**
```
Input: root = [3,9,20,15,7]
Output: [3.00000,14.50000,11.00000]
```

### Constraints

- The number of nodes in the tree is in the range [1, 10^4]
- -2^31 <= Node.val <= 2^31 - 1

## Approach Analysis

### Solution 1: BFS Level Order Traversal (Recommended)

**Key Insight:** Use BFS to process nodes level by level, calculating the sum and count of nodes at each level, then computing the average.

**Algorithm:**
1. Use a queue to perform level-order traversal
2. For each level, track the number of nodes to process
3. Calculate the sum of all node values at that level
4. Compute average = sum / count and add to result
5. Continue until all levels are processed

**Time Complexity:** O(n) - visit each node exactly once
**Space Complexity:** O(w) - where w is the maximum width of the tree (queue size)

**Why This Works:**
- BFS naturally processes nodes level by level
- By tracking level size with `int levelSize = queue.size()`, we know exactly how many nodes belong to the current level
- We can sum all values in the level and divide by count to get the average
- Using `long` for sum prevents integer overflow for large node values

**Implementation Details:**
- Use `Deque<TreeNode>` or `Queue<TreeNode>` for the queue
- Track level size before processing to avoid counting children
- Use `long` for levelSum to handle potential overflow
- Cast to `double` when computing average

### Solution 2: DFS with Level Tracking (Alternative)

**Key Insight:** Use DFS with level parameter, tracking sums and counts for each level separately, then compute averages at the end.

**Algorithm:**
1. Create two lists: one for sums, one for counts per level
2. Perform DFS traversal with current level as parameter
3. At each node, add value to sum[level] and increment count[level]
4. After traversal, compute averages: sum[i] / count[i] for each level
5. Return the list of averages

**Time Complexity:** O(n) - visit each node exactly once
**Space Complexity:** O(h) - where h is the height of the tree (recursion stack + storage)

**Why This Works:**
- DFS visits all nodes but we track which level each belongs to
- By maintaining separate sums and counts, we can aggregate level data
- No queue needed, uses recursion stack instead
- Can be slightly more space-efficient for wide trees

## Approach Comparison

| Aspect | BFS Approach | DFS Approach |
|--------|-------------|--------------|
| **Traversal** | Level-order (queue) | Depth-first (recursion) |
| **Space** | O(w) - queue width | O(h) - recursion depth |
| **Intuition** | More natural for level problems | Alternative perspective |
| **Implementation** | Single pass, compute averages on the fly | Two phases: collect data, then compute |
| **Wide trees** | May use more space | More efficient |
| **Deep trees** | More efficient | May use more space |

## Pattern Recognition

This problem is a **classic BFS level-order traversal** with aggregation:
- Process nodes level by level
- Perform calculation on each level (sum, then average)
- Similar to other level-based problems (max value per level, right side view, etc.)
- Can also be solved with DFS by tracking level information

## Key Learning Points

1. **Level Processing in BFS:** Capture `queue.size()` before loop to know level boundaries
2. **Integer Overflow Prevention:** Use `long` for sum when dealing with many large values
3. **Floating Point Division:** Cast to `double` to ensure accurate average calculation
4. **DFS Alternative:** Same tree traversal can be approached differently with level tracking
5. **Pattern Flexibility:** Understanding both BFS and DFS approaches enriches problem-solving

## Implementation Notes

- BFS approach is more intuitive for level-based problems
- DFS approach shows alternative thinking about aggregating level data
- Both handle edge cases (single node, null root) naturally
- Test with various tree shapes: balanced, skewed, single-sided
- Consider integer overflow when node values are at limits

## Related Problems

- **102. Binary Tree Level Order Traversal** - Core level-order pattern
- **107. Binary Tree Level Order Traversal II** - Bottom-up variation
- **199. Binary Tree Right Side View** - Select specific node per level
- **515. Find Largest Value in Each Tree Row** - Similar level aggregation
- **103. Binary Tree Zigzag Level Order Traversal** - Level order with alternation

## Common Pitfalls

1. **Forgetting to capture level size:** Don't use `queue.size()` in loop condition as it changes
2. **Integer overflow:** Use `long` for sum when values can be large
3. **Integer division:** Cast to `double` before division to get accurate averages
4. **Empty tree:** Handle null root case explicitly

## Study Notes

- **Pattern Mastery:** Fundamental BFS tree traversal with aggregation
- **Time to Solve:** ~5-8 minutes with BFS pattern recognition
- **Confidence Level:** High - straightforward application of level-order traversal
- **Interview Tip:** Mention both approaches to show versatility