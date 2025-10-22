# Sum Root to Leaf Numbers (LeetCode 129) - Study Guide

## Pattern: Binary Tree DFS with Path Accumulation

This is a **fundamental DFS path problem** that teaches you how to accumulate state as you traverse from root to leaf. The key skill is building numbers digit-by-digit using mathematical operations rather than string manipulation.

## Problem Statement

Given the root of a binary tree containing digits from 0-9, where each root-to-leaf path represents a number, return the total sum of all root-to-leaf numbers.

**Example:**
```
Tree: [1,2,3]
    1
   / \
  2   3

Paths: 1->2 (12) and 1->3 (13)
Sum: 12 + 13 = 25
```

## Why You Might Struggle With This Problem

### Common Mental Blocks:
1. **"How do I build a number from digits?"** - Use `num * 10 + digit`
2. **"When do I add to the sum?"** - Only at leaf nodes, not every node
3. **"How do I pass the accumulated number down?"** - Use function parameters
4. **"What if the tree is empty or has one node?"** - Handle base cases carefully

---

## The Core Algorithm: Number Building Mathematics

### Understanding Number Building

When you see digits `1`, `2`, `3` in sequence, to build 123:
```
Start: 0
See 1: 0 * 10 + 1 = 1
See 2: 1 * 10 + 2 = 12
See 3: 12 * 10 + 3 = 123
```

**Formula:** `newNumber = oldNumber * 10 + currentDigit`

This is the **key insight** of the entire problem!

---

## Visual Understanding

### Example 1: Simple Tree [1,2,3]

```
Tree Structure:
       1
      / \
     2   3

Path Analysis:
Path 1: Root(1) -> Left(2)
  - At node 1: num = 0 * 10 + 1 = 1
  - At node 2: num = 1 * 10 + 2 = 12
  - Leaf reached! Return 12

Path 2: Root(1) -> Right(3)
  - At node 1: num = 0 * 10 + 1 = 1
  - At node 3: num = 1 * 10 + 3 = 13
  - Leaf reached! Return 13

Total Sum: 12 + 13 = 25
```

### Example 2: Complex Tree [4,9,0,5,1]

```
Tree Structure:
       4
      / \
     9   0
    / \
   5   1

Path Analysis:

Path 1: 4 -> 9 -> 5
  At 4: num = 0 * 10 + 4 = 4
  At 9: num = 4 * 10 + 9 = 49
  At 5: num = 49 * 10 + 5 = 495
  Leaf! Return 495

Path 2: 4 -> 9 -> 1
  At 4: num = 0 * 10 + 4 = 4
  At 9: num = 4 * 10 + 9 = 49
  At 1: num = 49 * 10 + 1 = 491
  Leaf! Return 491

Path 3: 4 -> 0
  At 4: num = 0 * 10 + 4 = 4
  At 0: num = 4 * 10 + 0 = 40
  Leaf! Return 40

Total Sum: 495 + 491 + 40 = 1026
```

---

## Detailed Execution Trace

### Full Call Stack for [4,9,0,5,1]

```
dfs(4, 0)  ← Initial call
│
├─ currentNum = 0 * 10 + 4 = 4
├─ Not a leaf (has children)
│
├─ Call dfs(9, 4)  ← Left subtree
│  │
│  ├─ currentNum = 4 * 10 + 9 = 49
│  ├─ Not a leaf (has children)
│  │
│  ├─ Call dfs(5, 49)  ← Left child
│  │  │
│  │  ├─ currentNum = 49 * 10 + 5 = 495
│  │  ├─ IS A LEAF! Return 495 ✓
│  │
│  ├─ Call dfs(1, 49)  ← Right child
│  │  │
│  │  ├─ currentNum = 49 * 10 + 1 = 491
│  │  ├─ IS A LEAF! Return 491 ✓
│  │
│  └─ Return: 495 + 491 = 986
│
├─ Call dfs(0, 4)  ← Right subtree
│  │
│  ├─ currentNum = 4 * 10 + 0 = 40
│  ├─ IS A LEAF! Return 40 ✓
│
└─ Return: 986 + 40 = 1026 ✓

Final Answer: 1026
```

---

## Step-by-Step Algorithm

### Step 1: Understand the Recursive Structure

```java
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);  // Start with accumulated number = 0
}
```

**Why start with 0?** Because we haven't seen any digits yet.

### Step 2: Handle Base Cases

```java
private int dfs(TreeNode node, int currentNum) {
    // Base case 1: null node contributes nothing
    if (node == null) {
        return 0;
    }

    // Build the number by appending current digit
    currentNum = currentNum * 10 + node.val;
```

**Key Point:** We update `currentNum` at EVERY node, not just leaves.

### Step 3: Detect Leaf Nodes

```java
    // Base case 2: leaf node - this is where we have a complete number
    if (node.left == null && node.right == null) {
        return currentNum;
    }
```

**Critical:** Both children must be null. A node with only one child is NOT a leaf!

### Step 4: Recurse on Children

```java
    // Recursive case: sum the results from both subtrees
    int leftSum = dfs(node.left, currentNum);
    int rightSum = dfs(node.right, currentNum);

    return leftSum + rightSum;
}
```

**Key Insight:** We pass the SAME `currentNum` to both children because they both start with the same prefix.

---

## Complete Solution

```java
public class Solution {
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int currentNum) {
        if (node == null) {
            return 0;
        }

        currentNum = currentNum * 10 + node.val;

        if (node.left == null && node.right == null) {
            return currentNum;
        }

        return dfs(node.left, currentNum) + dfs(node.right, currentNum);
    }
}
```

---

## Common Mistakes and How to Avoid Them

### ❌ Mistake 1: Adding to Sum at Every Node

```java
// WRONG - Adds at every node, not just leaves
private int dfs(TreeNode node, int currentNum) {
    if (node == null) return 0;

    currentNum = currentNum * 10 + node.val;
    int sum = currentNum;  // ❌ Wrong!

    sum += dfs(node.left, currentNum);
    sum += dfs(node.right, currentNum);
    return sum;
}
```

**Why it fails:** This counts intermediate nodes, not just complete paths.

**Fix:** Only return `currentNum` when you reach a leaf.

### ❌ Mistake 2: Wrong Leaf Detection

```java
// WRONG - Incorrect leaf check
if (node.left == null || node.right == null) {  // ❌ Should be AND!
    return currentNum;
}
```

**Why it fails:** A node with one child would be treated as a leaf.

**Fix:** Use `&&` (AND) not `||` (OR) for leaf detection.

### ❌ Mistake 3: Using String Concatenation

```java
// WRONG - Inefficient and error-prone
private int dfs(TreeNode node, String currentNum) {
    currentNum = currentNum + node.val;  // ❌ String manipulation
    // ...
    return Integer.parseInt(currentNum);  // ❌ Parsing overhead
}
```

**Why it fails:** String operations are slower and more complex.

**Fix:** Use mathematical approach: `num * 10 + digit`

### ❌ Mistake 4: Not Passing Accumulated Value

```java
// WRONG - No way to track the number being built
private int dfs(TreeNode node) {  // ❌ Missing currentNum parameter
    if (node == null) return 0;
    // How do we know what number we've built so far?
}
```

**Fix:** Add parameter to track accumulated value through recursion.

---

## Edge Cases to Consider

### 1. Single Node Tree

```
Input: [5]
Output: 5

Explanation: Root is also a leaf, so return 5
```

### 2. Tree with Zeros

```
Input: [0]
Output: 0

Input: [1,0,0]
    1
   / \
  0   0
Output: 10 + 10 = 20
```

**Important:** Zero is a valid digit! Don't treat it specially.

### 3. Skewed Tree

```
Input: [1,2,null,3,null,4]
    1
   /
  2
   \
    3
     \
      4

Path: 1->2->3->4 = 1234
Output: 1234
```

### 4. Deep Tree

```
Given constraints, depth ≤ 10
Maximum possible number: 9999999999 (fits in 32-bit int)
```

---

## Complexity Analysis

### Time Complexity: O(n)
- **Why:** We visit each node exactly once
- **Detail:** Each node performs constant work (one multiplication, one addition)
- **Best/Worst/Average:** Always O(n) regardless of tree shape

### Space Complexity: O(h)
- **Why:** Recursion stack depth equals tree height
- **Best case:** O(log n) for balanced tree
- **Worst case:** O(n) for skewed tree (all nodes in one line)
- **Note:** Not counting output space (the final sum)

---

## Alternative Approach: Iterative DFS

### Using Stack

```java
public int sumNumbersIterative(TreeNode root) {
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

**When to use iterative:**
- Avoiding recursion stack overflow (rare in practice for this problem)
- Personal preference or team coding standards
- Same complexity as recursive approach

---

## Interview Strategy

### 1. Clarify Requirements
**Ask:**
- "Should I use recursion or iteration?" (Usually your choice)
- "Can I assume the answer fits in 32-bit integer?" (Yes, per problem statement)
- "How should I handle an empty tree?" (Return 0)

### 2. Explain Your Approach
**Say:**
"I'll use DFS to explore all root-to-leaf paths. As I traverse down, I'll build the number by multiplying by 10 and adding the current digit. When I reach a leaf, I'll add that complete number to my sum."

### 3. Discuss Number Building
**Say:**
"To build the number, I'll use `num * 10 + digit`. For example, to build 123: start with 0, see 1 → 1, see 2 → 12, see 3 → 123."

### 4. Explain Leaf Detection
**Say:**
"A leaf node is where both left and right children are null. That's where I have a complete root-to-leaf number."

### 5. Walk Through Example
Always trace through Example 1 on whiteboard:
```
    1
   / \
  2   3

Path 1: 1->2 = 12
Path 2: 1->3 = 13
Sum: 25
```

### 6. Code Carefully
- Write clean, commented code
- Test with simple case first
- Handle edge cases

### 7. Test Your Solution
Walk through:
- Single node: [5] → 5
- Your example: [1,2,3] → 25
- Edge case: [0] → 0

---

## Related Problems

### Prerequisite:
- **112. Path Sum** (Easy) - Simpler path accumulation
- **113. Path Sum II** (Medium) - Collecting all paths

### Similar Pattern:
- **257. Binary Tree Paths** (Easy) - Return paths as strings
- **988. Smallest String Starting From Leaf** (Medium) - Build strings instead of numbers
- **437. Path Sum III** (Medium) - Paths can start/end anywhere

### Advanced:
- **124. Binary Tree Maximum Path Sum** (Hard) - Path sum optimization
- **687. Longest Univalue Path** (Medium) - Tracking path properties

---

## Practice Tips

1. **Master the number building formula:** `num * 10 + digit`
2. **Practice leaf detection:** `left == null && right == null`
3. **Understand state passing:** How to carry information through recursion
4. **Draw it out:** Visualize the tree and trace through calls
5. **Time yourself:** Aim for 10-15 minutes from problem reading to working code

---

## Key Takeaways

1. **Number Building:** `num * 10 + digit` is cleaner than string manipulation
2. **Leaf Detection:** Both children must be null
3. **State Accumulation:** Pass accumulated value through recursion
4. **DFS Pattern:** Natural fit for root-to-leaf path problems
5. **When to Aggregate:** Only at leaf nodes for complete paths
6. **Base Cases:** Handle null nodes returning 0

---

## Memory Aid: "BAL" Pattern

Remember **BAL** for this type of problem:

- **B**uild the number at each node (num * 10 + digit)
- **A**dd to sum only at leaves
- **L**eft + Right sums get returned up the tree

---

## Quick Reference Card

```java
// Sum Root to Leaf Numbers - Template
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

private int dfs(TreeNode node, int num) {
    if (node == null) return 0;

    num = num * 10 + node.val;

    if (node.left == null && node.right == null) {
        return num;  // Leaf: return complete number
    }

    return dfs(node.left, num) + dfs(node.right, num);
}
```

**Remember:** Build at every node, return at leaves! 🎯
