# Sum Root to Leaf Numbers - Quick Reference Cheat Sheet

## Problem Summary
Given a binary tree with digits 0-9, where each root-to-leaf path represents a number, return the sum of all such numbers.

**Example:** Tree [1,2,3] has paths 1→2 (12) and 1→3 (13), sum = 25

---

## Core Pattern: DFS Path Accumulation

**Pattern Type:** Binary Tree DFS with state accumulation
**Key Technique:** Build numbers using `num * 10 + digit`
**When to Use:** Problems requiring root-to-leaf path traversal with accumulated state

---

## The Essential Formula

### Number Building
```java
newNumber = currentNumber * 10 + node.val
```

**Example:** Building 123
```
0 * 10 + 1 = 1
1 * 10 + 2 = 12
12 * 10 + 3 = 123
```

---

## Standard Template

### Recursive Solution (Recommended)

```java
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

private int dfs(TreeNode node, int currentNum) {
    // Base case: null node
    if (node == null) {
        return 0;
    }

    // Build the number
    currentNum = currentNum * 10 + node.val;

    // Base case: leaf node
    if (node.left == null && node.right == null) {
        return currentNum;
    }

    // Recursive case: sum both subtrees
    return dfs(node.left, currentNum) + dfs(node.right, currentNum);
}
```

**Time:** O(n) | **Space:** O(h)

---

## Alternative: Iterative with Stack

```java
public int sumNumbers(TreeNode root) {
    if (root == null) return 0;

    int totalSum = 0;
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(root, root.val));

    while (!stack.isEmpty()) {
        Pair current = stack.pop();
        TreeNode node = current.node;
        int num = current.num;

        // If leaf, add to sum
        if (node.left == null && node.right == null) {
            totalSum += num;
            continue;
        }

        // Push children with updated numbers
        if (node.right != null) {
            stack.push(new Pair(node.right, num * 10 + node.right.val));
        }
        if (node.left != null) {
            stack.push(new Pair(node.left, num * 10 + node.left.val));
        }
    }

    return totalSum;
}

class Pair {
    TreeNode node;
    int num;
    Pair(TreeNode node, int num) {
        this.node = node;
        this.num = num;
    }
}
```

**Time:** O(n) | **Space:** O(h)

---

## Key Code Snippets

### Leaf Node Detection
```java
// Check if node is a leaf (both children are null)
if (node.left == null && node.right == null) {
    // This is a leaf node
}

// Common mistake - using OR instead of AND
if (node.left == null || node.right == null) {  // ❌ WRONG!
    // This includes nodes with one child
}
```

### Building Numbers vs Returning Sum
```java
// At EVERY node: build the number
currentNum = currentNum * 10 + node.val;

// ONLY at LEAVES: return the number
if (isLeaf(node)) {
    return currentNum;
}

// At INTERNAL nodes: return sum of subtrees
return dfs(node.left, currentNum) + dfs(node.right, currentNum);
```

---

## Pattern Variations

### Collect All Path Numbers
```java
public List<Integer> getAllPathNumbers(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    dfsCollect(root, 0, result);
    return result;
}

private void dfsCollect(TreeNode node, int num, List<Integer> result) {
    if (node == null) return;

    num = num * 10 + node.val;

    if (node.left == null && node.right == null) {
        result.add(num);
        return;
    }

    dfsCollect(node.left, num, result);
    dfsCollect(node.right, num, result);
}
```

### Return Paths with Numbers
```java
public List<Pair<List<Integer>, Integer>> getPathsWithNumbers(TreeNode root) {
    List<Pair<List<Integer>, Integer>> result = new ArrayList<>();
    dfsWithPath(root, 0, new ArrayList<>(), result);
    return result;
}

private void dfsWithPath(TreeNode node, int num, List<Integer> path,
                         List<Pair<List<Integer>, Integer>> result) {
    if (node == null) return;

    path.add(node.val);
    num = num * 10 + node.val;

    if (node.left == null && node.right == null) {
        result.add(new Pair<>(new ArrayList<>(path), num));
    } else {
        dfsWithPath(node.left, num, path, result);
        dfsWithPath(node.right, num, path, result);
    }

    path.remove(path.size() - 1);  // Backtrack
}
```

### Count Paths with Target Sum
```java
public int countPathsWithSum(TreeNode root, int targetSum) {
    return dfsCount(root, 0, targetSum);
}

private int dfsCount(TreeNode node, int num, int target) {
    if (node == null) return 0;

    num = num * 10 + node.val;

    if (node.left == null && node.right == null) {
        return num == target ? 1 : 0;
    }

    return dfsCount(node.left, num, target) + dfsCount(node.right, num, target);
}
```

---

## Common Mistakes Checklist

### ❌ Mistake 1: Adding at Every Node
```java
// WRONG - adds at every node
private int dfs(TreeNode node, int num) {
    if (node == null) return 0;
    num = num * 10 + node.val;
    int sum = num;  // ❌ Wrong!
    sum += dfs(node.left, num);
    sum += dfs(node.right, num);
    return sum;
}

// CORRECT - only returns at leaves
private int dfs(TreeNode node, int num) {
    if (node == null) return 0;
    num = num * 10 + node.val;
    if (node.left == null && node.right == null) {
        return num;  // ✅ Only at leaves
    }
    return dfs(node.left, num) + dfs(node.right, num);
}
```

### ❌ Mistake 2: Wrong Leaf Detection
```java
// WRONG - uses OR instead of AND
if (node.left == null || node.right == null) {  // ❌
    return currentNum;
}

// CORRECT - uses AND
if (node.left == null && node.right == null) {  // ✅
    return currentNum;
}
```

### ❌ Mistake 3: String Manipulation
```java
// WRONG - inefficient string approach
private int dfs(TreeNode node, String num) {  // ❌
    num = num + node.val;
    // ...
    return Integer.parseInt(num);
}

// CORRECT - mathematical approach
private int dfs(TreeNode node, int num) {  // ✅
    num = num * 10 + node.val;
    return num;
}
```

### ❌ Mistake 4: Missing State Parameter
```java
// WRONG - no way to track accumulated number
private int dfs(TreeNode node) {  // ❌
    // How do we know what number we've built?
}

// CORRECT - pass state through parameter
private int dfs(TreeNode node, int currentNum) {  // ✅
    currentNum = currentNum * 10 + node.val;
    // ...
}
```

---

## Edge Cases to Test

```java
// Test Case 1: Single node
Input: [5]
Output: 5

// Test Case 2: Tree with zeros
Input: [1,0,0]
    1
   / \
  0   0
Output: 10 + 10 = 20

// Test Case 3: Skewed tree
Input: [1,2,null,3,null,4]
    1
   /
  2
   \
    3
     \
      4
Output: 1234

// Test Case 4: Simple balanced tree
Input: [1,2,3]
    1
   / \
  2   3
Output: 12 + 13 = 25

// Test Case 5: Complex tree
Input: [4,9,0,5,1]
      4
     / \
    9   0
   / \
  5   1
Output: 495 + 491 + 40 = 1026
```

---

## Complexity Analysis

### Time Complexity
- **O(n)** where n = number of nodes
- Visit each node exactly once
- Constant work per node (one multiplication, one addition)

### Space Complexity
- **O(h)** where h = height of tree
- Recursion stack depth = tree height
- Best case: O(log n) for balanced tree
- Worst case: O(n) for skewed tree

---

## Interview Template

### Step-by-Step Approach
1. **Identify Pattern:** Root-to-leaf DFS with accumulation
2. **Explain Formula:** `num * 10 + digit` for number building
3. **Define Base Cases:**
   - Null node → return 0
   - Leaf node → return accumulated number
4. **Recursive Case:** Sum results from both subtrees
5. **Code:** Write clean recursive solution
6. **Test:** Walk through simple example

### What to Say
"This is a DFS path accumulation problem. I'll traverse all root-to-leaf paths and build numbers as I go using `num * 10 + digit`. When I reach a leaf, I'll add that number to my sum."

---

## Quick Memory Aids

### The "BAL" Mnemonic
- **B**uild the number at each node
- **A**dd to sum only at leaves
- **L**eft + Right sums returned up tree

### Three Key Operations
1. **Build:** `num = num * 10 + node.val`
2. **Check:** `node.left == null && node.right == null`
3. **Sum:** `return left + right`

---

## Related Patterns

### Same Pattern
- **112. Path Sum** - Check if path sum equals target
- **113. Path Sum II** - Find all paths with target sum
- **257. Binary Tree Paths** - Return all paths as strings

### Similar Techniques
- **437. Path Sum III** - Paths starting anywhere
- **988. Smallest String Starting From Leaf** - Build strings
- **124. Binary Tree Maximum Path Sum** - Path optimization

---

## Minimal Implementation (For Quick Recall)

```java
// Most concise version
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

private int dfs(TreeNode node, int num) {
    if (node == null) return 0;
    num = num * 10 + node.val;
    if (node.left == null && node.right == null) return num;
    return dfs(node.left, num) + dfs(node.right, num);
}
```

**Remember:** This 7-line solution solves the entire problem!

---

## Decision Tree

```
Start at node with currentNum
│
├─ Is node null?
│  └─ YES → return 0
│  └─ NO → continue
│
├─ Build: num = num * 10 + node.val
│
├─ Is node a leaf?
│  └─ YES → return num
│  └─ NO → continue
│
└─ Return: dfs(left, num) + dfs(right, num)
```

---

## Performance Tips

1. **Prefer Recursion:** Cleaner and easier to understand
2. **Use Iterative:** Only if avoiding recursion stack is critical
3. **Avoid Strings:** Math operations are faster
4. **Don't Modify Tree:** Pass state through parameters
5. **Early Return:** Return at leaves, don't accumulate at every node

---

## Final Checklist Before Submitting

- [ ] Using AND (&&) for leaf detection, not OR
- [ ] Building number at EVERY node
- [ ] Returning number ONLY at leaves
- [ ] Starting with currentNum = 0
- [ ] Handling null nodes correctly
- [ ] Summing left + right results at internal nodes
- [ ] Tested with at least 3 test cases

---

## Time to Master
- **Understanding:** 15 minutes
- **First implementation:** 20 minutes
- **Interview speed:** 10-15 minutes

**Practice Goal:** Write the solution from memory in under 10 minutes! 🎯
