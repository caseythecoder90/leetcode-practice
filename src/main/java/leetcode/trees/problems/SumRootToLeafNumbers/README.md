# 129. Sum Root to Leaf Numbers

**Difficulty:** Medium
**Pattern:** Binary Tree DFS / Path Accumulation
**Companies:** Facebook, Amazon, Microsoft, Apple, Google

## Problem Description

You are given the root of a binary tree containing digits from `0` to `9` only.

Each root-to-leaf path in the tree represents a number.
- For example, the root-to-leaf path `1 -> 2 -> 3` represents the number `123`.

Return the total sum of all root-to-leaf numbers. Test cases are generated so that the answer will fit in a 32-bit integer.

A **leaf node** is a node with no children.

### Examples

**Example 1:**
```
Input: root = [1,2,3]
Output: 25

Tree visualization:
    1
   / \
  2   3

Explanation:
- Path 1->2 represents the number 12
- Path 1->3 represents the number 13
- Sum: 12 + 13 = 25
```

**Example 2:**
```
Input: root = [4,9,0,5,1]
Output: 1026

Tree visualization:
      4
     / \
    9   0
   / \
  5   1

Explanation:
- Path 4->9->5 represents the number 495
- Path 4->9->1 represents the number 491
- Path 4->0 represents the number 40
- Sum: 495 + 491 + 40 = 1026
```

**Example 3:**
```
Input: root = [0]
Output: 0
```

## Constraints

- The number of nodes in the tree is in the range `[1, 1000]`
- `0 <= Node.val <= 9`
- The depth of the tree will not exceed `10`

## Approach Analysis

### Solution: DFS with Path Accumulation (Recommended)

**Key Insight:** Use DFS to traverse all paths from root to leaves. As we traverse, build the number by multiplying the current accumulated value by 10 and adding the current node's value. When we reach a leaf, add the complete number to our sum.

**Algorithm:**
1. Start DFS from root with accumulated number = 0
2. At each node, update accumulated number: `num = num * 10 + node.val`
3. If current node is a leaf, return the accumulated number
4. Otherwise, recursively sum results from left and right subtrees
5. Return the total sum

**Building Numbers Example:**
```
Path: 1 -> 2 -> 3

At node 1: num = 0 * 10 + 1 = 1
At node 2: num = 1 * 10 + 2 = 12
At node 3: num = 12 * 10 + 3 = 123
Leaf reached: return 123
```

**Time Complexity:** O(n) - visit each node exactly once
**Space Complexity:** O(h) - where h is the height of the tree (recursion stack)

**Why This Works:**
- DFS naturally explores all root-to-leaf paths
- Building the number as we go avoids string concatenation overhead
- Mathematical approach: multiply by 10 and add digit is efficient
- Leaf nodes are where complete numbers are formed

### Alternative: Iterative DFS with Stack

**Key Insight:** Use a stack to simulate DFS while maintaining pairs of (node, accumulated number).

**Algorithm:**
1. Use stack to store (node, current_number) pairs
2. Pop from stack and check if it's a leaf
3. If leaf, add number to total sum
4. Otherwise, push children with updated numbers

**Time Complexity:** O(n) - visit each node exactly once
**Space Complexity:** O(h) - stack space for tree height

## Pattern Recognition

This problem demonstrates the **Path Accumulation** pattern:
- Need to track state (accumulated number) along the path
- Process all root-to-leaf paths
- Aggregate results from different paths
- Similar to "Path Sum" problems but with number building instead of sum checking

## Key Learning Points

1. **Path Accumulation:** Build state incrementally as you traverse
2. **Number Building:** `num * 10 + digit` is the standard way to build numbers
3. **Leaf Detection:** Check `node.left == null && node.right == null`
4. **DFS Pattern:** Perfect for exploring all root-to-leaf paths
5. **State Passing:** Pass accumulated value through recursion parameters

## Common Pitfalls

1. **Forgetting to Check for Leaf Nodes:** Don't add to sum at every node, only at leaves
2. **Integer Overflow:** Problem guarantees answer fits in 32-bit int, but be aware in general
3. **Wrong Order of Operations:** Must multiply by 10 before adding new digit
4. **Null Handling:** Handle null nodes correctly in recursion

## Implementation Notes

- Recursive solution is cleaner and more intuitive
- Iterative solution useful if you want to avoid recursion stack
- Both approaches have same time/space complexity
- Test with single node, skewed trees, and balanced trees

## Related Problems

- **112. Path Sum** - Check if path sum equals target (similar DFS pattern)
- **113. Path Sum II** - Find all root-to-leaf paths with target sum
- **257. Binary Tree Paths** - Return all root-to-leaf paths as strings
- **437. Path Sum III** - Count paths with target sum (not just root-to-leaf)
- **666. Path Sum IV** - Path sum in tree represented as array

## Study Notes

- **Pattern Category:** Tree DFS with Path Accumulation
- **Key Technique:** Building numbers digit by digit using multiplication
- **Time to Solve:** ~10-15 minutes (with DFS pattern familiarity)
- **Confidence Level:** High - straightforward DFS with simple state tracking
- **Interview Tip:** Explain how you're building numbers mathematically, not via strings
