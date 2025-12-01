# Kth Smallest Element in BST - Cheat Sheet

## Quick Pattern Recognition
```
Keywords: "k-th smallest" + "BST"
Pattern: In-Order Traversal with Counter
Time: O(H + k) | Space: O(H)
Optimization: Early termination when found
```

## The Optimal Template
```java
class Solution {
    private int count = 0;     // ✅ Use int, not Integer!
    private int result = 0;
    private int k;
    
    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        inorder(root);
        return result;
    }
    
    private boolean inorder(TreeNode node) {
        if (node == null) return false;
        
        if (inorder(node.left)) return true;  // Early stop!
        
        count++;
        if (count == k) {
            result = node.val;
            return true;  // ✅ Stop all recursion!
        }
        
        return inorder(node.right);
    }
}
```

## Performance Comparison

| Approach | Boxing | Early Stop | Speed Percentile |
|----------|--------|------------|------------------|
| Integer count, no stop | ❌ | ❌ | ~32% |
| int count, no stop | ✅ | ❌ | ~70% |
| int count, early stop | ✅ | ✅ | ~95% |

## Common Mistakes

### ❌ Mistake 1: Using Integer
```java
Integer count = 0;  // Adds 50% overhead!
count++;            // Unbox → increment → box (3 operations!)
```

### ❌ Mistake 2: No Early Termination
```java
if (count == k) {
    result = node.val;
    return;  // Still continues parent recursion!
}
inorder(root.right);  // Still called!
```

### ❌ Mistake 3: Collecting All Values
```java
List<Integer> all = new ArrayList<>();
inorder(root, all);
return all.get(k - 1);  // Wasteful! Don't need all values
```

## Iterative Alternative
```java
public int kthSmallest(TreeNode root, int k) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    int count = 0;
    
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        
        curr = stack.pop();
        if (++count == k) return curr.val;
        curr = curr.right;
    }
    return -1;
}
```

## Follow-Up: Augmented BST
For **frequent queries** with modifications:

```java
class AugmentedNode {
    int val;
    int leftSize;  // Track left subtree size
    AugmentedNode left, right;
}

public int kthSmallest(AugmentedNode root, int k) {
    int leftSize = root.leftSize;
    
    if (k <= leftSize) {
        return kthSmallest(root.left, k);
    } else if (k == leftSize + 1) {
        return root.val;
    } else {
        return kthSmallest(root.right, k - leftSize - 1);
    }
}
```

**Trade-off:** Query: O(H) vs O(H+k), Insert/Delete: O(H) to maintain

## Edge Cases
```java
k = 1          → Smallest (leftmost)
k = n          → Largest (need full traversal)
Single node    → Return that node
Skewed tree    → O(n) time worst case
```

## Optimization Checklist
- [ ] Use `int` not `Integer`
- [ ] Early termination with boolean return
- [ ] Store `k` as instance variable
- [ ] Stop recursion immediately when found
- [ ] Consider iterative for no recursion overhead

## Key Insight
```
BST + In-Order = Sorted
K-th in sorted = K-th smallest
Stop at K-th element!
```

## Interview Script
1. "In-order traversal gives sorted values"
2. "Count elements, return when count equals k"
3. "Use primitives and early termination for speed"
4. "Time O(H+k), Space O(H)"
5. "For follow-up: augment with subtree sizes"

## Memory Aid
**K**eep **T**rack with **i**n-order
- **K**: Count to k
- **T**: Terminate early
- **i**: int not Integer
