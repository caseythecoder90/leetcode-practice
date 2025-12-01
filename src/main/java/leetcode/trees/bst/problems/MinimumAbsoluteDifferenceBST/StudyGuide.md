# Study Guide: Minimum Absolute Difference in BST

## Core Concept

**Pattern:** BST In-Order Traversal + Previous Value Tracking

**Mental Model:** Think of BST in-order traversal as walking through a sorted array. The minimum difference must be between adjacent elements.

```
BST:       4              In-Order Array: [1, 2, 3, 4, 6]
          / \             
         2   6            Min Diff = min of consecutive differences
        / \               = min(2-1, 3-2, 4-3, 6-4)
       1   3              = min(1, 1, 1, 2) = 1
```

## The "Aha!" Moment

### What Makes This Problem Unique?

**🎯 The Key Insight:**
- Normal tree: Would need to compare ALL pairs → O(n²)
- **BST**: In-order gives sorted sequence → only compare adjacent → O(n)

**Why Instance Variables?**
```java
// ❌ Parameters reset in each call
private void inorder(TreeNode node, Integer prev) {
    inorder(node.left, ???);  // What do we pass?
}

// ✅ Instance variable persists across ALL calls
private Integer prev = null;
private void inorder(TreeNode node) {
    inorder(node.left);  // prev automatically available!
}
```

## Problem Patterns to Recognize

### This Pattern Applies When:
1. ✅ Tree is a **BST** (Binary Search Tree)
2. ✅ Need to **compare consecutive values** in sorted order
3. ✅ Problem involves **differences, ranges, or relationships**
4. ✅ Looking for **minimum/maximum of something** between nodes

### Keywords That Trigger This Pattern:
- "Binary Search Tree" or "BST"
- "minimum difference"
- "closest values"
- "k-th smallest/largest"
- "validate BST"
- "in-order" (explicit or implicit)

## Implementation Template

```java
class Solution {
    // Instance variables for state across recursive calls
    private int result = Integer.MAX_VALUE;  // Or appropriate default
    private Integer prev = null;              // Previous value in in-order
    
    public int solve(TreeNode root) {
        inorder(root);
        return result;
    }
    
    private void inorder(TreeNode node) {
        if (node == null) return;
        
        // 1. Process left subtree (smaller values)
        inorder(node.left);
        
        // 2. Process current node (in sorted order)
        if (prev != null) {
            // Do your comparison/calculation here
            result = Math.min(result, node.val - prev);
        }
        prev = node.val;  // Update for next node
        
        // 3. Process right subtree (larger values)
        inorder(node.right);
    }
}
```

## Common Mistakes & How to Avoid Them

### Mistake 1: Using Parameters for Previous Value ❌
```java
// ❌ WRONG: prev resets in each recursive call
private void inorder(TreeNode node, Integer prev) {
    inorder(node.left, node.val);  // This passes CURRENT, not PREVIOUS!
    // ...
}
```

**Fix:** Use instance variable that persists across all calls.

### Mistake 2: Comparing with Parent Instead of Previous ❌
```java
// ❌ WRONG: Compares with parent, not previous in sorted order
private void inorder(TreeNode node, Integer parentVal) {
    inorder(node.left, node.val);
    if (parentVal != null) {
        minimum = Math.min(minimum, Math.abs(node.val - parentVal));
    }
}
```

**Why This Fails:**
```
Tree:    4          Your code compares:    Should compare:
        / \         1 ↔ 2 (parent)         1 ↔ 2 ✓
       2   6        2 ↔ 4 (parent)         2 ↔ 3 ✓
      / \           3 ↔ 2 (parent)         3 ↔ 4 ✓
     1   3          6 ↔ 4 (parent)         4 ↔ 6 ✓
```

### Mistake 3: Forgetting to Check if prev is null ❌
```java
// ❌ WRONG: Will throw NullPointerException on first node
minimum = Math.min(minimum, node.val - prev);
prev = node.val;
```

**Fix:** Always check `if (prev != null)` before using it.

### Mistake 4: Using Math.abs() Unnecessarily
```java
// ❌ UNNECESSARY: In-order guarantees increasing values
minimum = Math.min(minimum, Math.abs(node.val - prev));

// ✅ CORRECT: Always node.val > prev in BST in-order
minimum = Math.min(minimum, node.val - prev);
```

## Visualization: Execution Flow

### Example Tree: `[4,2,6,1,3]`

```
Step-by-step in-order traversal:

       4
      / \
     2   6
    / \
   1   3

Call Stack & State Changes:
┌────────────────────────────────────────────────────┐
│ inorder(4)                                         │
│   └─> inorder(2)                                   │
│         └─> inorder(1)                             │
│               └─> inorder(null) [left]  : return   │
│               └─> VISIT 1: prev=null → prev=1      │
│               └─> inorder(null) [right] : return   │
│         └─> VISIT 2: prev=1, min=min(MAX,2-1)=1    │
│                      prev=2                         │
│         └─> inorder(3)                             │
│               └─> inorder(null) [left]  : return   │
│               └─> VISIT 3: prev=2, min=min(1,3-2)=1│
│                              prev=3                 │
│               └─> inorder(null) [right] : return   │
│   └─> VISIT 4: prev=3, min=min(1,4-3)=1           │
│                prev=4                               │
│   └─> inorder(6)                                   │
│         └─> inorder(null) [left]  : return         │
│         └─> VISIT 6: prev=4, min=min(1,6-4)=1     │
│                      prev=6                         │
│         └─> inorder(null) [right] : return         │
└────────────────────────────────────────────────────┘

Result: 1
```

## Practice Variations

### Variation 1: Find the Mode (Most Frequent Value)
```java
// Same pattern, count consecutive duplicates
private Integer prev = null;
private int currentCount = 0;
private int maxCount = 0;

private void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    
    if (prev != null && prev == node.val) {
        currentCount++;
    } else {
        currentCount = 1;
    }
    maxCount = Math.max(maxCount, currentCount);
    prev = node.val;
    
    inorder(node.right);
}
```

### Variation 2: Validate BST
```java
// Check if values are strictly increasing
private Integer prev = null;
private boolean isValid = true;

private void inorder(TreeNode node) {
    if (node == null || !isValid) return;
    inorder(node.left);
    
    if (prev != null && prev >= node.val) {
        isValid = false;
    }
    prev = node.val;
    
    inorder(node.right);
}
```

### Variation 3: Find Inorder Successor
```java
// Find next larger value than target
private Integer prev = null;
private Integer successor = null;

private void inorder(TreeNode node, int target) {
    if (node == null) return;
    inorder(node.left, target);
    
    if (prev != null && prev == target && successor == null) {
        successor = node.val;
    }
    prev = node.val;
    
    inorder(node.right, target);
}
```

## Interview Strategy

### Time Management (15 minutes total):
1. **Understand (2 min):** Identify it's a BST, realize in-order → sorted
2. **Plan (3 min):** Explain in-order traversal + prev tracking
3. **Code (7 min):** Write solution with proper instance variables
4. **Test (3 min):** Walk through example, check edge cases

### What to Say Out Loud:
1. "This is a BST, so in-order traversal gives sorted values"
2. "The minimum difference must be between consecutive values"
3. "I'll track the previous value using an instance variable"
4. "Time complexity is O(n), space is O(h) for recursion"

### Red Flags to Avoid:
- ❌ Suggesting to compare all pairs
- ❌ Collecting all values then sorting (wastes BST property)
- ❌ Using parameters for state that needs to persist
- ❌ Forgetting to handle the first node (prev == null)

## Complexity Deep Dive

### Why O(n) Time is Optimal:
- Must visit every node to ensure we find minimum
- Each node visited exactly once
- Constant work per node
- Cannot do better than O(n)

### Why O(h) Space:
- Recursion stack depth = tree height
- Balanced BST: h = log n → O(log n) space
- Skewed BST: h = n → O(n) space
- No additional data structures needed

### Can We Do Better?
- **Time:** No, must check all values
- **Space:** Yes! Use iterative in-order with stack (still O(h) but no recursion overhead)

## Related LeetCode Problems

| Problem | Difficulty | Pattern Similarity |
|---------|-----------|-------------------|
| 783. Minimum Distance Between BST Nodes | Easy | Identical problem |
| 501. Find Mode in BST | Easy | Same pattern, count duplicates |
| 98. Validate Binary Search Tree | Medium | Use prev to validate ordering |
| 285. Inorder Successor in BST | Medium | Find next larger value |
| 230. Kth Smallest Element in BST | Medium | In-order to find k-th element |
| 538. Convert BST to Greater Tree | Medium | In-order with sum tracking |

## Memory Aids

### Acronym: **PINT** (Previous, Inorder, Null-check, Track)
- **P**revious value as instance variable
- **I**norder traversal (left → node → right)
- **N**ull-check before using prev
- **T**rack/update prev after processing node

### Visual Memory:
```
BST = Sorted Array (via in-order)
        ↓
    Adjacent pairs only
        ↓
    Instance variable for prev
```

## Final Checklist

Before submitting your solution, verify:
- [ ] Using instance variables (not parameters) for `prev` and `minimum`
- [ ] In-order traversal: `left → process → right`
- [ ] Null-check: `if (prev != null)` before comparison
- [ ] Update prev: `prev = node.val` after processing
- [ ] No Math.abs() needed (values increase)
- [ ] Handles minimum tree size (2 nodes)
- [ ] Returns Integer.MAX_VALUE if tree is empty (though won't happen per constraints)

---

**Remember:** BST + In-Order = Sorted. Previous value tracking turns an O(n²) problem into O(n)!
