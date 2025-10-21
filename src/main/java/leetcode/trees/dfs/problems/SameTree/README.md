# 100. Same Tree

**Difficulty:** Easy  
**Pattern:** Binary Tree DFS / Tree Comparison  
**Companies:** Amazon, Facebook, Microsoft, Google, Bloomberg

## Problem Description

Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.

Two binary trees are considered the same if they are **structurally identical**, and the nodes have the **same value**.

### Examples

**Example 1:**
```
Tree p:     Tree q:
    1           1
   / \         / \
  2   3       2   3

Input: p = [1,2,3], q = [1,2,3]
Output: true
```

**Example 2:**
```
Tree p:     Tree q:
    1           1
   /             \
  2               2

Input: p = [1,2], q = [1,null,2]
Output: false
```

**Example 3:**
```
Tree p:     Tree q:
    1           1
   / \         / \
  2   1       1   2

Input: p = [1,2,1], q = [1,1,2]
Output: false
```

## Constraints

* The number of nodes in both trees is in the range `[0, 100]`.
* `-10^4 <= Node.val <= 10^4`

---

## Approach Analysis

### Pattern Recognition

This is a **Binary Tree DFS problem** with the following characteristics:
- Need to compare two trees node-by-node ✓
- Structural comparison (not just values) ✓
- Recursive nature matches tree structure ✓
- Early termination possible ✓

### Why DFS Works Perfectly

**Recursive Structure**: Trees are naturally recursive structures, and comparing two trees recursively is intuitive and elegant.

**Three Comparison Cases**: At each node, we have three cases to handle:
1. Both nodes are null → trees are same at this point
2. One node is null, other isn't → trees are different
3. Both nodes exist → compare values and recurse on children

**Early Termination**: We can immediately return `false` as soon as we find a mismatch.

---

## Solution: Recursive DFS

### Core Algorithm

The fundamental insight: **Two trees are the same if:**
1. Their root values are equal, AND
2. Their left subtrees are the same, AND
3. Their right subtrees are the same

```
isSameTree(p, q):
    1. If both p and q are null → return true (base case)
    2. If one is null and other isn't → return false
    3. If values don't match → return false
    4. Recursively check left subtrees AND right subtrees
```

### Step-by-Step Walkthrough

**Example: p = [1,2,3], q = [1,2,3]**

```
Step 1: Compare roots
        p: 1        q: 1
       / \          / \
      2   3        2   3
    
    ✓ Both exist, values equal (1 == 1)
    → Continue to children

Step 2: Compare left subtrees
    p.left: 2 (leaf)    q.left: 2 (leaf)
    
    ✓ Both exist, values equal (2 == 2)
    ✓ Both left children are null
    ✓ Both right children are null
    → Left subtrees are same

Step 3: Compare right subtrees
    p.right: 3 (leaf)    q.right: 3 (leaf)
    
    ✓ Both exist, values equal (3 == 3)
    ✓ Both left children are null
    ✓ Both right children are null
    → Right subtrees are same

Result: true (all nodes match)
```

**Example: p = [1,2], q = [1,null,2]**

```
Step 1: Compare roots
        p: 1        q: 1
       /              \
      2                2
    
    ✓ Both exist, values equal (1 == 1)
    → Continue to children

Step 2: Compare left subtrees
    p.left: 2        q.left: null
    
    ✗ One is null, other exists
    → Return false immediately

Result: false (structure mismatch)
```

### Implementation

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    // Base case: both null → trees are same
    if (p == null && q == null) {
        return true;
    }
    
    // One is null, other isn't → trees are different
    if (p == null || q == null) {
        return false;
    }
    
    // Values don't match → trees are different
    if (p.val != q.val) {
        return false;
    }
    
    // Recursively check both subtrees
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

### Compact Version

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    // Handle null cases
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    
    // Check current node and recurse
    return p.val == q.val && 
           isSameTree(p.left, q.left) && 
           isSameTree(p.right, q.right);
}
```

---

## Complexity Analysis

### Time Complexity: **O(min(n, m))**
- **n** = number of nodes in tree p
- **m** = number of nodes in tree q
- We visit each node at most once
- Early termination if mismatch found
- Best case: O(1) if roots don't match
- Worst case: O(min(n,m)) if we must check all nodes

### Space Complexity: **O(min(h₁, h₂))**
- **h₁** = height of tree p
- **h₂** = height of tree q
- Space used by recursion call stack
- Balanced trees: O(log n)
- Skewed trees: O(n)

---

## Edge Cases

### 1. Both Trees Empty
```java
Input: p = null, q = null
Output: true
```
**Handling**: First condition catches this

### 2. One Tree Empty
```java
Input: p = [1], q = null
Output: false
```
**Handling**: Second condition catches this

### 3. Single Node, Same Value
```java
Input: p = [1], q = [1]
Output: true
```
**Handling**: Values match, both children null

### 4. Single Node, Different Values
```java
Input: p = [1], q = [2]
Output: false
```
**Handling**: Third condition catches value mismatch

### 5. Same Structure, Different Values
```java
Input: p = [1,2,3], q = [1,2,4]
Output: false
```
**Handling**: Recursion finds value mismatch at leaf

### 6. Different Structure, Same Values
```java
Input: p = [1,2], q = [1,null,2]
Output: false
```
**Handling**: Structure mismatch detected by null check

---

## Common Mistakes & How to Avoid

### ❌ Mistake 1: Incorrect Null Handling
```java
// WRONG - doesn't handle both null case
if (p == null || q == null) {
    return false;
}
```
**Fix**: Check both null first, then check either null

### ❌ Mistake 2: Forgetting to Compare Values
```java
// WRONG - only checks structure, not values
return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
```
**Fix**: Always compare current node values first

### ❌ Mistake 3: Using OR Instead of AND
```java
// WRONG - only one subtree needs to match
return isSameTree(p.left, q.left) || isSameTree(p.right, q.right);
```
**Fix**: BOTH subtrees must match, use AND (&&)

### ❌ Mistake 4: Wrong Order of Checks
```java
// WRONG - will cause NullPointerException
if (p.val != q.val) return false;
if (p == null && q == null) return true;
```
**Fix**: Always check null cases before accessing values

---

## Alternative Approaches

### Approach 2: Iterative BFS (Using Queues)

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    Queue<TreeNode> queue1 = new LinkedList<>();
    Queue<TreeNode> queue2 = new LinkedList<>();
    
    queue1.offer(p);
    queue2.offer(q);
    
    while (!queue1.isEmpty() && !queue2.isEmpty()) {
        TreeNode node1 = queue1.poll();
        TreeNode node2 = queue2.poll();
        
        // Both null - continue
        if (node1 == null && node2 == null) continue;
        
        // One null or values different - not same
        if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }
        
        // Add children to queues in same order
        queue1.offer(node1.left);
        queue1.offer(node1.right);
        queue2.offer(node2.left);
        queue2.offer(node2.right);
    }
    
    return true;
}
```

**Trade-offs:**
- **Pros**: Iterative (no stack overflow risk), easy to understand
- **Cons**: More code, uses O(w) extra space for queues
- **When to use**: When concerned about stack overflow with deep trees

### Approach 3: Iterative DFS (Using Stacks)

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    Stack<TreeNode> stack = new Stack<>();
    stack.push(p);
    stack.push(q);
    
    while (!stack.isEmpty()) {
        TreeNode node2 = stack.pop();
        TreeNode node1 = stack.pop();
        
        if (node1 == null && node2 == null) continue;
        if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }
        
        stack.push(node1.left);
        stack.push(node2.left);
        stack.push(node1.right);
        stack.push(node2.right);
    }
    
    return true;
}
```

**Trade-offs:**
- **Pros**: Iterative, depth-first order
- **Cons**: More complex than recursive, similar space complexity
- **When to use**: Preference for iterative solutions

---

## Key Insights

1. **Recursive Nature**: The problem's recursive definition maps perfectly to a recursive solution
2. **Three-Way Check**: Always check (1) both null, (2) one null, (3) values match
3. **AND Logic**: BOTH left and right subtrees must match (not just one)
4. **Early Termination**: Return false immediately on first mismatch
5. **Base Case**: Two null nodes are considered same (empty trees match)
6. **Order Matters**: Check null cases before accessing node values to avoid errors

---

## Pattern Application

This problem demonstrates the **Tree Comparison Pattern**, which applies to:

### Similar Problems:
- **101. Symmetric Tree** - Compare tree with its mirror
- **572. Subtree of Another Tree** - Check if one tree is subtree of another
- **951. Flip Equivalent Binary Trees** - Compare with flipping allowed

### Key Technique:
Compare two trees by:
1. Handling null cases
2. Comparing current values
3. Recursively comparing corresponding subtrees

---

## Interview Tips

### What to Say:
- "This is a tree comparison problem, perfect for recursive DFS"
- "I need to check three conditions: both null, one null, or values match"
- "I'll use AND logic because both subtrees must be identical"
- "Early termination optimizes the average case"

### Time to Solve: 3-5 minutes

### Follow-up Questions to Expect:
1. **"What if we need to check if trees are symmetric?"** → Compare left with right mirror
2. **"How would you handle very deep trees?"** → Consider iterative to avoid stack overflow
3. **"Can we optimize space?"** → Recursive uses call stack, iterative uses queue/stack
4. **"What's the space complexity?"** → O(h) for recursion stack

---

## Related Problems

### Must Practice:
- **101. Symmetric Tree** (Easy) - Tree comparison with mirroring
- **572. Subtree of Another Tree** (Easy) - Uses same tree as subroutine
- **104. Maximum Depth of Binary Tree** (Easy) - Basic recursion practice

### Similar Patterns:
- **226. Invert Binary Tree** (Easy) - Tree transformation with recursion
- **110. Balanced Binary Tree** (Easy) - Recursive tree property checking
- **617. Merge Two Binary Trees** (Easy) - Operating on two trees simultaneously

---

## Practice Tips

1. **Draw It Out**: Visualize the recursion tree for small examples
2. **Handle Nulls First**: Always check null cases before accessing values
3. **Think Recursively**: "Same tree = same root + same left + same right"
4. **Test Edge Cases**: Both null, one null, single node, large trees
5. **Master This Pattern**: It's foundational for many tree problems

This problem is an excellent introduction to recursive tree thinking and serves as a building block for more complex tree comparison problems!
