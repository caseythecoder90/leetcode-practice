# BST Cheat Sheet - Quick Reference

## BST Definition
```
For every node:
  Left subtree values < node.val < Right subtree values
  
This property holds RECURSIVELY for all subtrees!
```

## The Golden Rule
```
BST + In-Order Traversal = Sorted Sequence
```

## Core BST Operations

### Search - O(h)
```java
TreeNode search(TreeNode root, int val) {
    if (root == null || root.val == val) return root;
    return val < root.val ? 
        search(root.left, val) : 
        search(root.right, val);
}
```

### Insert - O(h)
```java
TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.right = insert(root.right, val);
    }
    return root;
}
```

### Find Min/Max - O(h)
```java
// Min: Go all left
TreeNode findMin(TreeNode root) {
    while (root.left != null) root = root.left;
    return root;
}

// Max: Go all right
TreeNode findMax(TreeNode root) {
    while (root.right != null) root = root.right;
    return root;
}
```

### Delete - O(h)
```java
TreeNode delete(TreeNode root, int val) {
    if (root == null) return null;
    
    if (val < root.val) {
        root.left = delete(root.left, val);
    } else if (val > root.val) {
        root.right = delete(root.right, val);
    } else {
        // Found it! Three cases:
        if (root.left == null) return root.right;      // No left
        if (root.right == null) return root.left;      // No right
        
        // Two children: Replace with inorder successor
        TreeNode successor = findMin(root.right);
        root.val = successor.val;
        root.right = delete(root.right, successor.val);
    }
    return root;
}
```

## Essential BST Patterns

### Pattern 1: In-Order with Previous Value
```java
private Integer prev = null;
private int result;

private void inorder(TreeNode node) {
    if (node == null) return;
    
    inorder(node.left);
    
    if (prev != null) {
        result = compute(node.val, prev);  // Consecutive values
    }
    prev = node.val;
    
    inorder(node.right);
}
```
**Use for:** Min difference, validate BST, find mode, kth element

### Pattern 2: BST Search with Pruning
```java
void search(TreeNode root, int target) {
    if (root == null) return;
    
    // Smart pruning!
    if (target < root.val) {
        search(root.left, target);   // Only left
    } else if (target > root.val) {
        search(root.right, target);  // Only right
    } else {
        // Found it!
    }
}
```
**Use for:** Search, insert, delete, closest value

### Pattern 3: Range Operations
```java
void rangeQuery(TreeNode root, int low, int high) {
    if (root == null) return;
    
    if (root.val >= low) {
        rangeQuery(root.left, low, high);  // May have >= low
    }
    
    if (root.val >= low && root.val <= high) {
        process(root);  // In range!
    }
    
    if (root.val <= high) {
        rangeQuery(root.right, low, high);  // May have <= high
    }
}
```
**Use for:** Range sum, range count, range search

### Pattern 4: Validate BST
```java
boolean isValidBST(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;
    
    // Check range constraints
    if ((min != null && root.val <= min) || 
        (max != null && root.val >= max)) {
        return false;
    }
    
    // Recursively check subtrees with updated bounds
    return isValidBST(root.left, min, root.val) &&
           isValidBST(root.right, root.val, max);
}
```

### Pattern 5: Build Balanced BST
```java
TreeNode sortedArrayToBST(int[] nums, int left, int right) {
    if (left > right) return null;
    
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    
    root.left = sortedArrayToBST(nums, left, mid - 1);
    root.right = sortedArrayToBST(nums, mid + 1, right);
    
    return root;
}
```

## Problem Recognition Table

| Keywords | Pattern | Complexity |
|----------|---------|------------|
| "minimum/maximum" | Go left/right all way | O(h) |
| "k-th smallest/largest" | In-order + count | O(n) |
| "difference between values" | In-order + prev | O(n) |
| "validate BST" | Range validation | O(n) |
| "search" / "find" | BST search | O(h) |
| "range sum" / "in range" | Range query + prune | O(log n + k) |
| "insert" / "delete" | BST modification | O(h) |
| "build from sorted" | Divide & conquer | O(n) |

## Common Mistakes

| Mistake | Problem | Fix |
|---------|---------|-----|
| Only check immediate children | Misses violations in subtrees | Use range validation |
| Use parent as comparison | Wrong reference point | Use prev in in-order |
| Search both subtrees | Waste time | Prune based on value |
| Collect & sort | Ignore BST property | Use in-order directly |
| Use parameters for state | Resets each call | Use instance variables |

## Time Complexity Summary

### Balanced BST (h = log n):
- Search/Insert/Delete: **O(log n)**
- Find Min/Max: **O(log n)**
- In-Order Traversal: **O(n)**
- Range Query: **O(log n + k)**

### Skewed BST (h = n):
- All operations degrade to **O(n)**

## Interview Decision Tree

```
Is it a BST?
    ├─ No  → Use general tree algorithms
    └─ Yes → 
        │
        Need sorted values?
        ├─ Yes → In-order traversal pattern
        └─ No  →
            │
            Searching for value/range?
            ├─ Yes → BST search pattern
            └─ No  →
                │
                Validating structure?
                ├─ Yes → Range validation pattern
                └─ No  → Building/modifying?
                    ├─ Yes → Insert/delete pattern
                    └─ No  → Consider other patterns
```

## Key Properties Checklist

When solving BST problems, remember:
- ✅ **Left < Root < Right** (recursively)
- ✅ **In-order = Sorted**
- ✅ **Can prune search** (don't search both subtrees!)
- ✅ **Instance variables** for persistent state
- ✅ **O(h) operations** possible (vs O(n) for regular trees)

## Common Edge Cases

```java
// 1. Empty tree
if (root == null) return defaultValue;

// 2. Single node
if (root.left == null && root.right == null) { ... }

// 3. Skewed tree (all left or all right)
//    1          1
//     \        /
//      2      2
//       \    /
//        3  3

// 4. Duplicate values (depends on problem)
// Usually: left <= root < right OR left < root <= right

// 5. First node in traversal
if (prev == null) { /* skip comparison */ }
```

## Memory Aid: BST-SORT

- **B**inary property: Left < Root < Right
- **S**orted: In-order gives sorted output
- **T**raversal: Use in-order for most problems
- **S**earch: Can prune entire subtrees
- **O**perations: O(h) for balanced trees
- **R**ange: Easy to query ranges efficiently
- **T**rack state: Use instance variables

## Quick Problem Solver

1. **See "BST"** → Think: Can I prune? Can I use in-order?
2. **See "minimum/maximum"** → Go left/right
3. **See "difference/consecutive"** → In-order + prev
4. **See "validate"** → Range check
5. **See "k-th element"** → In-order + counter
6. **See "range"** → Prune based on bounds

## Related Structures

```
BST
 ├─ Self-Balancing BST
 │   ├─ AVL Tree (strict balancing)
 │   ├─ Red-Black Tree (less strict)
 │   └─ Splay Tree (self-adjusting)
 │
 ├─ B-Tree (generalized BST)
 └─ Treap (BST + Heap)
```
