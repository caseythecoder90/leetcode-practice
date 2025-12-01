# Cheat Sheet: Minimum Absolute Difference in BST

## Quick Pattern Recognition
```
Keywords: BST + "minimum difference" / "closest values"
Pattern: In-Order Traversal + Previous Value Tracking
Time: O(n) | Space: O(h)
```

## The Template
```java
class Solution {
    private int minimum = Integer.MAX_VALUE;
    private Integer prev = null;  // ← Instance variable!
    
    public int getMinimumDifference(TreeNode root) {
        inorder(root);
        return minimum;
    }
    
    private void inorder(TreeNode node) {
        if (node == null) return;
        
        inorder(node.left);              // Left
        
        if (prev != null) {              // Process
            minimum = Math.min(minimum, node.val - prev);
        }
        prev = node.val;                 // Update
        
        inorder(node.right);             // Right
    }
}
```

## Key Concepts

### Core Insight
```
BST + In-Order = Sorted Values
Minimum difference = Between consecutive sorted values
```

### Why Instance Variables?
```java
// ❌ Parameters reset each call
void inorder(TreeNode node, Integer prev) { }

// ✅ Instance variable persists
private Integer prev = null;
void inorder(TreeNode node) { }
```

### Parent vs Previous
```
Tree:    4          Compare with:        Not with:
        / \         Previous in-order    Parent node
       2   6        1→2→3→4→6            
      / \           
     1   3          
```

## Common Mistakes

| Mistake | Why Wrong | Fix |
|---------|-----------|-----|
| `inorder(node, prev)` | Parameter resets | Use instance variable |
| Compare with parent | Wrong relationship | Compare with prev in-order |
| No null check | First node crash | `if (prev != null)` |
| Use `Math.abs()` | Unnecessary | In-order is increasing |

## Execution Flow
```
Tree: [4,2,6,1,3]

In-Order: 1 → 2 → 3 → 4 → 6

┌──────┬──────┬─────────┬────────┐
│ Node │ prev │ Diff    │ Min    │
├──────┼──────┼─────────┼────────┤
│  1   │ null │ skip    │ MAX    │
│  2   │  1   │ 2-1=1   │   1    │
│  3   │  2   │ 3-2=1   │   1    │
│  4   │  3   │ 4-3=1   │   1    │
│  6   │  4   │ 6-4=2   │   1    │
└──────┴──────┴─────────┴────────┘
```

## Related Problems
- 783. Minimum Distance Between BST Nodes (identical)
- 501. Find Mode in BST (count consecutive)
- 98. Validate Binary Search Tree (check increasing)
- 285. Inorder Successor in BST (find next)

## Interview Script
1. "It's a BST, so in-order gives sorted values"
2. "Minimum must be between consecutive values"
3. "I'll track previous with instance variable"
4. "Time O(n), Space O(h) for recursion"

## Edge Cases
```java
[1,null,2]           → 1
[10,5,15]            → 5
[1,null,100000]      → 99999
```

## Complexity
- **Time:** O(n) - visit each node once
- **Space:** O(h) - recursion stack
  - Balanced: O(log n)
  - Skewed: O(n)

## Memory Aid: PINT
- **P**revious (instance variable)
- **I**norder (left → node → right)
- **N**ull-check (before using prev)
- **T**rack (update prev after process)
