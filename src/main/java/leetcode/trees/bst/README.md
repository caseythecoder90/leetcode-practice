# Binary Search Trees (BST) - Core Concepts & Patterns

## Overview

A **Binary Search Tree (BST)** is a specialized binary tree with an ordering property that makes it incredibly powerful for searching, insertion, and many algorithmic problems. BSTs are fundamental to computer science and frequently appear in coding interviews.

## What Makes a BST Special?

### The BST Property
**For every node in a BST:**
- All values in the **left subtree** are **less than** the node's value
- All values in the **right subtree** are **greater than** the node's value
- This property holds recursively for **every subtree**

```
        Valid BST              Invalid BST
           8                       8
          / \                     / \
         3   10                  3   10
        / \    \                / \    \
       1   6   14              1   6   14
          / \   /                 / \   /
         4   7 13                9   7 13
                                 ↑
                            Violates: 9 > 8 but in left subtree!
```

### Why BSTs Matter

1. **Efficient Search**: O(log n) average case (O(n) worst case for unbalanced)
2. **Sorted Order**: In-order traversal produces sorted sequence
3. **Range Queries**: Easy to find all values in a range
4. **Predecessor/Successor**: Find next smaller/larger element efficiently
5. **Dynamic Ordering**: Maintain sorted data with insertions/deletions

## Core BST Properties & Invariants

### 1. In-Order Traversal = Sorted Sequence
```java
// In-order: Left → Root → Right
void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.val + " ");  // Visit in sorted order
    inorder(root.right);
}

// For BST [5,3,7,1,4,6,9]:
// In-order output: 1 3 4 5 6 7 9 (sorted!)
```

**This is the MOST important property for interview problems!**

### 2. Search Property
```java
// Search in O(h) time where h = height
TreeNode search(TreeNode root, int target) {
    if (root == null || root.val == target) return root;
    
    if (target < root.val) {
        return search(root.left, target);   // Go left
    } else {
        return search(root.right, target);  // Go right
    }
}
```

### 3. Min/Max Elements
```java
// Minimum: Go all the way left
TreeNode findMin(TreeNode root) {
    while (root.left != null) {
        root = root.left;
    }
    return root;
}

// Maximum: Go all the way right
TreeNode findMax(TreeNode root) {
    while (root.right != null) {
        root = root.right;
    }
    return root;
}
```

### 4. Range Validation
```java
// Every node must satisfy: min < node.val < max
boolean isValidBST(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;
    
    if ((min != null && root.val <= min) || 
        (max != null && root.val >= max)) {
        return false;
    }
    
    return isValidBST(root.left, min, root.val) &&
           isValidBST(root.right, root.val, max);
}
```

## Common BST Patterns

### Pattern 1: In-Order Traversal with State Tracking
**Use when:** Need to process values in sorted order, compare consecutive elements

```java
private Integer prev = null;
private int result;

private void inorder(TreeNode node) {
    if (node == null) return;
    
    inorder(node.left);
    
    // Process current node (in sorted order)
    if (prev != null) {
        // Compare with previous node
        result = compute(result, node.val, prev);
    }
    prev = node.val;
    
    inorder(node.right);
}
```

**Problems:**
- Minimum Absolute Difference in BST
- Validate Binary Search Tree
- Find Mode in BST
- Inorder Successor/Predecessor

### Pattern 2: BST Search with Pruning
**Use when:** Searching for value, range, or condition using BST property

```java
// Can prune entire subtrees!
TreeNode searchBST(TreeNode root, int val) {
    if (root == null || root.val == val) return root;
    
    // Prune: Only search relevant subtree
    if (val < root.val) {
        return searchBST(root.left, val);
    } else {
        return searchBST(root.right, val);
    }
}
```

**Problems:**
- Search in BST
- Insert into BST
- Delete Node in BST
- Closest Value in BST

### Pattern 3: BST Range Operations
**Use when:** Finding all values in a range, sum in range, k-th element

```java
// Find all values in range [low, high]
void rangeSumBST(TreeNode root, int low, int high) {
    if (root == null) return;
    
    // Smart pruning based on BST property
    if (root.val >= low) {
        rangeSumBST(root.left, low, high);   // May have values >= low
    }
    
    if (root.val >= low && root.val <= high) {
        sum += root.val;  // In range!
    }
    
    if (root.val <= high) {
        rangeSumBST(root.right, low, high);  // May have values <= high
    }
}
```

**Problems:**
- Range Sum of BST
- Count of Range Sum
- Kth Smallest Element in BST

### Pattern 4: BST Modification (Insert/Delete)
**Use when:** Building or modifying BST structure

```java
// Insert: Find correct position using BST property
TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.right = insert(root.right, val);
    }
    return root;
}

// Delete: Three cases (leaf, one child, two children)
TreeNode delete(TreeNode root, int val) {
    if (root == null) return null;
    
    if (val < root.val) {
        root.left = delete(root.left, val);
    } else if (val > root.val) {
        root.right = delete(root.right, val);
    } else {
        // Found node to delete
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;
        
        // Two children: Replace with inorder successor
        TreeNode successor = findMin(root.right);
        root.val = successor.val;
        root.right = delete(root.right, successor.val);
    }
    return root;
}
```

### Pattern 5: BST Construction
**Use when:** Building BST from preorder/inorder traversals or arrays

```java
// Build BST from sorted array (balanced BST!)
TreeNode sortedArrayToBST(int[] nums, int left, int right) {
    if (left > right) return null;
    
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    
    root.left = sortedArrayToBST(nums, left, mid - 1);
    root.right = sortedArrayToBST(nums, mid + 1, right);
    
    return root;
}
```

## BST vs Binary Tree: Key Differences

| Aspect | Binary Tree | Binary Search Tree |
|--------|-------------|-------------------|
| **Ordering** | No specific order | Left < Root < Right |
| **Search** | O(n) - must check all | O(h) - can prune |
| **In-Order** | Random sequence | Sorted sequence |
| **Insert/Delete** | Anywhere | Must maintain order |
| **Use Cases** | Hierarchies, expressions | Searching, sorting |
| **Optimization** | Cannot prune searches | Can skip entire subtrees |

## Interview Problem Keywords

### BST-Specific Keywords:
- "Binary Search Tree" or "BST" (obviously!)
- "sorted order"
- "in-order traversal"
- "search" or "find" (with tree structure)
- "k-th smallest/largest"
- "range sum"
- "validate BST"
- "insert into BST"
- "delete from BST"
- "inorder successor/predecessor"

### When BST Property Helps:
- **"minimum/maximum"** → Go all left/right
- **"difference between values"** → Use in-order for sorted
- **"closest to target"** → Binary search approach
- **"sum in range"** → Prune based on bounds
- **"validate"** → Check ordering property

## Common BST Pitfalls

### 1. ❌ Forgetting Recursive BST Property
```java
// ❌ WRONG: Only checks immediate children
boolean isValidBST(TreeNode root) {
    if (root == null) return true;
    if (root.left != null && root.left.val >= root.val) return false;
    if (root.right != null && root.right.val <= root.val) return false;
    return isValidBST(root.left) && isValidBST(root.right);
}

// Problem: Doesn't catch cases like:
//      10
//     /  \
//    5   15
//       /  \
//      6   20
// Node 6 violates: 6 < 10 but in right subtree!
```

### 2. ❌ Not Using In-Order for Sorted Sequence
```java
// ❌ Inefficient: Collecting and sorting
List<Integer> values = new ArrayList<>();
collectAllValues(root, values);
Collections.sort(values);  // Wasteful!

// ✅ Efficient: In-order already gives sorted!
void inorder(TreeNode root) {
    // Already sorted, no need to sort!
}
```

### 3. ❌ Comparing with Wrong Reference Point
```java
// ❌ WRONG: Using parent value
void inorder(TreeNode node, int parent) {
    inorder(node.left, node.val);  // Compares with parent!
}

// ✅ CORRECT: Using previous in-order value
private Integer prev = null;
void inorder(TreeNode node) {
    // Compares with previous in sorted order!
}
```

### 4. ❌ Not Pruning Search Space
```java
// ❌ Inefficient: Searches both subtrees
int search(TreeNode root, int target) {
    if (root == null) return -1;
    if (root.val == target) return target;
    
    int left = search(root.left, target);   // Unnecessary!
    int right = search(root.right, target); // Unnecessary!
    return left != -1 ? left : right;
}

// ✅ Efficient: Only search relevant subtree
int search(TreeNode root, int target) {
    if (root == null) return -1;
    if (root.val == target) return target;
    
    return target < root.val ? 
        search(root.left, target) : 
        search(root.right, target);
}
```

## Time Complexities for BST Operations

### Balanced BST (Height h = log n):
- **Search**: O(log n)
- **Insert**: O(log n)
- **Delete**: O(log n)
- **Find Min/Max**: O(log n)
- **In-Order Traversal**: O(n)
- **Range Query**: O(log n + k) where k = results

### Skewed BST (Height h = n):
- **Search**: O(n) - degrades to linked list
- **Insert**: O(n)
- **Delete**: O(n)
- **Find Min/Max**: O(n)

## LeetCode BST Problems by Pattern

### In-Order Traversal Pattern:
- ✅ **530. Minimum Absolute Difference in BST** (Easy)
- **501. Find Mode in BST** (Easy)
- **98. Validate Binary Search Tree** (Medium)
- **230. Kth Smallest Element in BST** (Medium)
- **285. Inorder Successor in BST** (Medium)

### Search/Range Pattern:
- **700. Search in a Binary Search Tree** (Easy)
- **701. Insert into a Binary Search Tree** (Medium)
- **450. Delete Node in a BST** (Medium)
- **938. Range Sum of BST** (Easy)

### Construction Pattern:
- **108. Convert Sorted Array to Binary Search Tree** (Easy)
- **1008. Construct BST from Preorder Traversal** (Medium)

### Modification Pattern:
- **538. Convert BST to Greater Tree** (Medium)
- **1038. Binary Search Tree to Greater Sum Tree** (Medium)

## Study Strategy

### Phase 1: Master BST Properties (Week 1)
1. Understand BST definition and invariants
2. Practice in-order traversal (recognize sorted output)
3. Learn search/insert/delete operations
4. Master min/max finding

### Phase 2: In-Order Patterns (Week 2)
5. Minimum Absolute Difference (current problem!)
6. Validate BST
7. Find Mode in BST
8. Kth Smallest Element

### Phase 3: Advanced Operations (Week 3+)
9. Range sum queries
10. BST construction from arrays
11. BST modification (delete, convert)
12. Two-pointer techniques on BSTs

## BST Interview Checklist

Before solving a BST problem, ask yourself:

- [ ] Can I use the BST property to **prune** my search?
- [ ] Does **in-order traversal** give me sorted values?
- [ ] Do I need to maintain **BST invariants** after modification?
- [ ] Am I using **instance variables** for state that needs to persist?
- [ ] Can I solve this in **O(h)** instead of O(n)?
- [ ] Have I handled **edge cases** (null root, single node, skewed tree)?

## Memory Aids

### Acronym: **SORT**
- **S**earch efficiently (O(log n) average)
- **O**rdered traversal (in-order = sorted)
- **R**ange queries (prune based on bounds)
- **T**rack state (use instance variables)

### Visual:
```
BST = Sorted Array (via in-order)
    ↓
Left < Root < Right (recursively)
    ↓
Can prune search space!
```

## Next Steps

1. **Read through CheatSheet.md** for quick reference patterns
2. **Study completed problem**: Minimum Absolute Difference in BST
3. **Practice next problem**: Validate Binary Search Tree
4. **Build pattern recognition**: Identify BST vs Binary Tree problems

---

**Remember**: The BST property (Left < Root < Right) enables O(log n) operations and gives you sorted output via in-order traversal. Master these fundamentals and BST problems become pattern recognition!
