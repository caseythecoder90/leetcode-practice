# LeetCode 530: Minimum Absolute Difference in BST

## Problem Statement

Given the root of a Binary Search Tree (BST), return the minimum absolute difference between the values of any two different nodes in the tree.

**Constraints:**
- The number of nodes in the tree is in the range `[2, 10^4]`
- `0 <= Node.val <= 10^5`

## Examples

### Example 1
```
Input: root = [4,2,6,1,3]
Tree:       4
           / \
          2   6
         / \
        1   3

Output: 1
Explanation: The minimum difference is between node 2 and node 3 (or 1), which equals 1.
In-order: 1, 2, 3, 4, 6 → differences: 1, 1, 1, 2
```

### Example 2
```
Input: root = [1,0,48,null,null,12,49]
Tree:           1
               / \
              0   48
                 /  \
                12  49

Output: 1
Explanation: The minimum difference is between 48 and 49.
In-order: 0, 1, 12, 48, 49 → differences: 1, 11, 36, 1
```

## Key Insights

### 🔑 Critical Insight: BST + In-Order = Sorted!
The **most important** property to recognize:
- In-order traversal of a BST produces values in **sorted order**
- Minimum difference MUST be between **consecutive values** in sorted sequence
- No need to compare all pairs - only adjacent values matter!

### Why Your Original Code Failed
```java
// ❌ WRONG: Passing parent value as parameter
private void inorder(TreeNode node, Integer value) {
    inorder(node.left, node.val);   // Compares with parent
    if (value != null) {
        minimum = Math.min(minimum, Math.abs(value - node.val));
    }
    inorder(node.right, node.val);  // Compares with parent
}
```

**Problem:** This compares each node with its **parent**, not with the **previous node in sorted order**.

Example breakdown:
```
Tree:    4
        / \
       2   6
      / \
     1   3

Your code compares:
- Node 1 with parent 2
- Node 2 with parent 4
- Node 3 with parent 2
- Node 6 with parent 4

But we need to compare consecutive in-order values:
- 1 with 2
- 2 with 3
- 3 with 4
- 4 with 6
```

## Solution Approaches

### Approach 1: In-Order with Previous Value (Optimal)
**Time:** O(n) | **Space:** O(h) where h is tree height

```java
class Solution {
    private int minimum = Integer.MAX_VALUE;
    private Integer prev = null;  // ✅ Instance variable persists!
    
    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return minimum;
    }
    
    private void inorder(TreeNode node) {
        if (node == null) return;
        
        inorder(node.left);          // Visit left (smaller values)
        
        if (prev != null) {
            minimum = Math.min(minimum, node.val - prev);
        }
        prev = node.val;             // ✅ Update for NEXT node
        
        inorder(node.right);         // Visit right (larger values)
    }
}
```

**Why Instance Variables?**
- `prev` needs to persist across ALL recursive calls
- Each node sees the previous node's value
- Parameters are reset in each recursive call (won't work!)

### Approach 2: Collect Values Then Compare
**Time:** O(n log n) | **Space:** O(n)

```java
public int getMinimumDifference(TreeNode root) {
    List<Integer> values = new ArrayList<>();
    collectValues(root, values);
    Collections.sort(values);  // Or use in-order (already sorted!)
    
    int minimum = Integer.MAX_VALUE;
    for (int i = 1; i < values.size(); i++) {
        minimum = Math.min(minimum, values.get(i) - values.get(i - 1));
    }
    return minimum;
}
```

**Trade-offs:**
- Easier to understand
- More space required
- Sorting is unnecessary if we collect via in-order

## Step-by-Step Trace

### Example: `[4,2,6,1,3]`

```
Tree:       4
           / \
          2   6
         / \
        1   3

In-order traversal: 1 → 2 → 3 → 4 → 6

State transitions:
┌──────┬──────┬──────────┬─────────────────────┐
│ Node │ prev │ minimum  │ Action              │
├──────┼──────┼──────────┼─────────────────────┤
│  1   │ null │ MAX_VAL  │ Set prev=1          │
│  2   │  1   │ MAX_VAL  │ min(MAX, 2-1)=1     │
│      │      │    1     │ Set prev=2          │
│  3   │  2   │    1     │ min(1, 3-2)=1       │
│      │      │    1     │ Set prev=3          │
│  4   │  3   │    1     │ min(1, 4-3)=1       │
│      │      │    1     │ Set prev=4          │
│  6   │  4   │    1     │ min(1, 6-4)=1       │
└──────┴──────┴──────────┴─────────────────────┘

Answer: 1
```

## Common Mistakes

### 1. Using Parameters Instead of Instance Variables ❌
```java
// ❌ WRONG: prev resets in each recursive call
private void inorder(TreeNode node, Integer prev) {
    // ...
}
```

### 2. Comparing with Parent Instead of Previous ❌
```java
// ❌ WRONG: parent != previous in in-order
private void inorder(TreeNode node, Integer parentVal) {
    inorder(node.left, node.val);  // Not the previous value!
}
```

### 3. Not Handling First Node ✅
```java
if (prev != null) {  // ✅ CORRECT: Skip first node
    minimum = Math.min(minimum, node.val - prev);
}
```

### 4. Forgetting BST Property
- Don't need `Math.abs()` - values are in increasing order
- Always use `node.val - prev` (not `prev - node.val`)

## Pattern Recognition

### When to Use This Pattern:
- Problem involves **BST** (Binary Search Tree)
- Need to find **differences, ranges, or relationships** between values
- Keywords: "minimum difference", "closest values", "k closest"

### Similar Problems:
- **783. Minimum Distance Between BST Nodes** (identical)
- **501. Find Mode in BST** (also uses in-order with prev tracking)
- **98. Validate Binary Search Tree** (compare with prev to validate)
- **285. Inorder Successor in BST** (find next larger value)

## Complexity Analysis

### Time Complexity: O(n)
- Visit each node exactly once
- Constant work per node
- Cannot do better - must check all values

### Space Complexity: O(h)
- Recursion stack depth = tree height
- Best case (balanced): O(log n)
- Worst case (skewed): O(n)
- No additional data structures needed

## Interview Tips

### What to Say:
1. "Since this is a BST, in-order traversal gives sorted values"
2. "Minimum difference must be between consecutive sorted values"
3. "I'll use an instance variable to track the previous value"
4. "Time: O(n), Space: O(h) for recursion stack"

### Follow-up Questions:
**Q: Can you do this iteratively?**
A: Yes, use a stack to simulate in-order traversal

**Q: What if it's not a BST?**
A: Would need to compare all pairs → O(n²) or use sorting

**Q: Can you find K smallest differences?**
A: Keep a min-heap of size K while traversing

## Edge Cases

1. **Minimum tree (2 nodes):**
   ```
   Input: [1,null,2]
   Output: 1
   ```

2. **All same differences:**
   ```
   Input: [1,2,3,4,5] (skewed)
   Output: 1
   ```

3. **Large differences:**
   ```
   Input: [0,null,100000]
   Output: 100000
   ```

4. **Negative handling (though constraints say non-negative):**
   - Code would still work with negatives
   - In-order still produces sorted sequence

## Related Concepts

- **In-Order Traversal:** Left → Root → Right
- **BST Property:** Left < Root < Right
- **DFS vs BFS:** DFS is natural for tree traversal
- **Instance Variables vs Parameters:** State management in recursion

## Study Notes

**Pattern:** BST + In-Order + Previous Value Tracking
**Difficulty:** Medium
**Time to Solve:** ~10-15 minutes once pattern is recognized
**Key Mistake:** Using parameters instead of instance variables
**Remember:** In-order of BST = Sorted = Compare consecutive values only!
