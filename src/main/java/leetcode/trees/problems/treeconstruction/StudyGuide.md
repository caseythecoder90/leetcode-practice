# Tree Construction from Traversals - Complete Study Guide

## Pattern Overview

**Problem Type**: Reconstructing a binary tree from two traversal sequences

**Key Insight**: You need TWO traversals to uniquely determine a tree (with one exception: if you know it's a full binary tree). One traversal must be **inorder** to separate left/right subtrees.

## Why Do We Need Two Traversals?

**Single traversal is ambiguous:**
```
Preorder: [1, 2, 3]

Could be:     1           OR        1
             /                       \
            2                         2
           /                           \
          3                             3
```

**Two traversals (including inorder) are unique:**
```
Preorder: [1, 2, 3]
Inorder:  [2, 1, 3]

Only possible tree:
        1
       / \
      2   3
```

## Core Algorithm Pattern

### The Recursive Approach

**For ANY tree construction problem:**
1. Identify the root from one traversal
2. Find root position in inorder to partition left/right
3. Recursively build left and right subtrees
4. Return constructed tree

### Template Structure
```java
TreeNode buildTree(int[] traversal1, int[] traversal2) {
    // Create HashMap for O(1) inorder lookups
    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
        inorderMap.put(inorder[i], i);
    }
    
    return helper(/* boundaries */);
}

TreeNode helper(/* parameters */) {
    // Base case: invalid range
    if (start > end) return null;
    
    // 1. Find root value
    int rootVal = /* get from preorder or postorder */;
    TreeNode root = new TreeNode(rootVal);
    
    // 2. Find root position in inorder
    int inorderIndex = inorderMap.get(rootVal);
    
    // 3. Calculate subtree sizes
    int leftSize = inorderIndex - inStart;
    
    // 4. Recursively build subtrees
    root.left = helper(/* left subtree bounds */);
    root.right = helper(/* right subtree bounds */);
    
    return root;
}
```

## Problem 105: Preorder + Inorder

### Understanding the Traversals

**Preorder: Root → Left → Right**
```
For tree:     3
             / \
            9   20
               /  \
              15   7

Preorder: [3, 9, 20, 15, 7]
           ↑  ↑   ↑  ↑   ↑
         root L   R  RL  RR
```

**Inorder: Left → Root → Right**
```
Inorder: [9, 3, 15, 20, 7]
          ↑  ↑   ↑   ↑  ↑
          L root RL  R  RR
```

### Key Observations

1. **First element of preorder is root**
2. **Find root in inorder → splits left/right subtrees**
3. **Left subtree size determines preorder boundaries**

### Step-by-Step Example

```
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]

Step 1: Root is 3 (first in preorder)
Step 2: Find 3 in inorder at index 1
        Left subtree: [9] (indices 0-0)
        Right subtree: [15, 20, 7] (indices 2-4)

Step 3: Left subtree has 1 element
        In preorder: [9] (index 1)
        In inorder: [9] (index 0)

Step 4: Right subtree has 3 elements
        In preorder: [20, 15, 7] (indices 2-4)
        In inorder: [15, 20, 7] (indices 2-4)

Step 5: Recursively build:
        Left: root=9, no children
        Right: root=20, left=15, right=7
```

### Complete Solution

```java
class Solution {
    private int preorderIndex = 0;
    private Map<Integer, Integer> inorderMap = new HashMap<>();
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Build hashmap for O(1) inorder lookups
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return helper(preorder, 0, inorder.length - 1);
    }
    
    private TreeNode helper(int[] preorder, int inStart, int inEnd) {
        // Base case: no elements to construct tree
        if (inStart > inEnd) {
            return null;
        }
        
        // 1. Pick current root from preorder
        int rootVal = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootVal);
        
        // 2. Find root position in inorder
        int inorderIndex = inorderMap.get(rootVal);
        
        // 3. Build left subtree (elements before root in inorder)
        root.left = helper(preorder, inStart, inorderIndex - 1);
        
        // 4. Build right subtree (elements after root in inorder)
        root.right = helper(preorder, inorderIndex + 1, inEnd);
        
        return root;
    }
}
```

### Why This Works

**The preorderIndex pointer moves through preorder array naturally:**
- Starts at root
- Moves to left subtree roots (all left before any right)
- Then moves to right subtree roots
- This matches the recursive call order perfectly!

## Problem 106: Inorder + Postorder

### Understanding Postorder

**Postorder: Left → Right → Root**
```
For tree:     3
             / \
            9   20
               /  \
              15   7

Postorder: [9, 15, 7, 20, 3]
            ↑  ↑   ↑  ↑   ↑
            L  RL  RR R  root
```

### Key Differences from Problem 105

1. **Last element of postorder is root** (not first)
2. **Process RIGHT subtree before LEFT** (opposite order)
3. **Decrement postorder index** (not increment)

### Step-by-Step Example

```
inorder   = [9, 3, 15, 20, 7]
postorder = [9, 15, 7, 20, 3]

Step 1: Root is 3 (LAST in postorder)
Step 2: Find 3 in inorder at index 1
        Left: [9], Right: [15, 20, 7]

Step 3: Process RIGHT first (postorder processes right before left)
        Right root is 20 (second to last before 3)
        Find 20 in inorder at index 3
        Right's left: [15], Right's right: [7]

Step 4: Then process LEFT
        Left root is 9 (first in postorder)
```

### Complete Solution

```java
class Solution {
    private int postorderIndex;
    private Map<Integer, Integer> inorderMap = new HashMap<>();
    
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postorderIndex = postorder.length - 1; // Start from END
        
        // Build hashmap for O(1) inorder lookups
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return helper(postorder, 0, inorder.length - 1);
    }
    
    private TreeNode helper(int[] postorder, int inStart, int inEnd) {
        // Base case
        if (inStart > inEnd) {
            return null;
        }
        
        // 1. Pick root from END of postorder
        int rootVal = postorder[postorderIndex--];
        TreeNode root = new TreeNode(rootVal);
        
        // 2. Find root in inorder
        int inorderIndex = inorderMap.get(rootVal);
        
        // 3. Build RIGHT first (postorder: left-right-root)
        root.right = helper(postorder, inorderIndex + 1, inEnd);
        
        // 4. Then build LEFT
        root.left = helper(postorder, inStart, inorderIndex - 1);
        
        return root;
    }
}
```

### Critical Difference

**Why RIGHT before LEFT?**

In postorder [9, 15, 7, 20, 3]:
- After removing root (3), next element is 20 (right subtree root)
- We must process right subtree completely before touching left
- This matches postorder: entire left subtree comes before entire right subtree

## Comparison Chart

| Aspect | Preorder + Inorder | Inorder + Postorder |
|--------|-------------------|---------------------|
| **Root location** | First in preorder | Last in postorder |
| **Index movement** | Increment (→) | Decrement (←) |
| **Recursion order** | Left then Right | Right then Left |
| **Starting index** | 0 | length - 1 |
| **Why different order?** | Preorder visits left before right | Postorder visits right before left (in reverse) |

## Common Patterns Across Both

### 1. HashMap Optimization
**Purpose**: O(1) lookup of inorder indices
```java
Map<Integer, Integer> inorderMap = new HashMap<>();
for (int i = 0; i < inorder.length; i++) {
    inorderMap.put(inorder[i], i);
}
```
**Impact**: Reduces time from O(n²) to O(n)

### 2. Boundary Tracking
```java
helper(int[] array, int inStart, int inEnd)
```
**Purpose**: Track which portion of inorder we're currently processing

### 3. Global Index Pointer
```java
private int preorderIndex = 0;  // or postorderIndex
```
**Purpose**: Track position in preorder/postorder array

## Edge Cases & Validation

### 1. Empty Tree
```java
Input: preorder = [], inorder = []
Output: null
```
Handle in base case: `if (inStart > inEnd) return null`

### 2. Single Node
```java
Input: preorder = [1], inorder = [1]
Output: TreeNode(1)
```
Works naturally: creates root, both recursive calls hit base case

### 3. Skewed Tree (Left)
```java
Input: preorder = [3, 2, 1], inorder = [1, 2, 3]
Output:     3
           /
          2
         /
        1
```
Each node only has left child

### 4. Skewed Tree (Right)
```java
Input: preorder = [1, 2, 3], inorder = [1, 2, 3]
Output: 1
         \
          2
           \
            3
```
Each node only has right child

## Time & Space Complexity

### Time Complexity: O(n)
- Visit each node exactly once
- HashMap lookups are O(1)
- Total: O(n) where n = number of nodes

### Space Complexity: O(n)
- HashMap: O(n)
- Recursion stack: O(h) where h = height
    - Best case (balanced): O(log n)
    - Worst case (skewed): O(n)
- Total: O(n)

## Common Mistakes & How to Avoid

### ❌ Mistake 1: Wrong Recursion Order
```java
// WRONG for postorder + inorder
root.left = helper(postorder, inStart, inorderIndex - 1);
root.right = helper(postorder, inorderIndex + 1, inEnd);
```
**Fix**: For postorder, build RIGHT before LEFT

### ❌ Mistake 2: Forgetting to Update Index
```java
// WRONG - reuses same root value
int rootVal = preorder[preorderIndex];
```
**Fix**: Increment/decrement the index: `preorder[preorderIndex++]`

### ❌ Mistake 3: Wrong Boundary Calculations
```java
// WRONG - off by one
root.left = helper(preorder, inStart, inorderIndex);
```
**Fix**: Left subtree goes up to `inorderIndex - 1`

### ❌ Mistake 4: Not Using HashMap
**Impact**: O(n²) time complexity
**Fix**: Always create HashMap for inorder indices

## Debugging Tips

### 1. Trace Example By Hand
Draw the tree and trace through each recursive call:
```
preorder = [3, 9, 20, 15, 7], index = 0
inorder  = [9, 3, 15, 20, 7]
           [    3    ]
           /         \
      [9]           [15, 20, 7]
```

### 2. Print Debug Information
```java
System.out.println("Root: " + rootVal);
System.out.println("Inorder range: [" + inStart + ", " + inEnd + "]");
System.out.println("Inorder index: " + inorderIndex);
```

### 3. Verify Base Cases
- Empty array
- Single element
- Two elements

## Related Problems & Variations

### Similar Construction Problems
- **889. Construct Binary Tree from Preorder and Postorder** (requires full binary tree)
- **297. Serialize and Deserialize Binary Tree**
- **449. Serialize and Deserialize BST**

### Why Can't We Use Other Combinations?
- **Preorder + Postorder**: Can't uniquely determine tree (without additional constraints)
- **Two Inorders**: Same tree, no new information
- **Two Preorders**: Same tree, no new information

## Interview Strategy

### Recognition (30 seconds)
Keywords: "construct", "build tree", "traversal arrays", "preorder/inorder/postorder"

### Approach (2 minutes)
1. Identify which traversal gives you the root
2. Plan how to partition using inorder
3. Decide recursion order based on traversal type

### Implementation (8-10 minutes)
1. Set up HashMap and global index
2. Write recursive helper with boundaries
3. Handle base case
4. Find root and partition
5. Make recursive calls in correct order

### Verification (2 minutes)
Test with:
- Given example
- Single node
- Skewed tree

## Key Takeaways

1. **Inorder is essential** - it's the only traversal that separates left/right subtrees
2. **HashMap optimization** - critical for O(n) solution
3. **Global index pointer** - elegantly tracks position in preorder/postorder
4. **Recursion order matters** - preorder: left first, postorder: right first
5. **Practice both problems** - understanding one helps with the other

## Practice Progression

1. **Study traversals** - Make sure you deeply understand preorder, inorder, postorder
2. **Solve 105 first** - Preorder + Inorder is slightly more intuitive
3. **Then solve 106** - Postorder + Inorder reinforces the pattern
4. **Compare solutions** - Note the small but crucial differences
5. **Draw examples** - Always visualize the tree construction process

This pattern appears frequently in interviews and tests your understanding of tree traversals, recursion, and problem-solving!