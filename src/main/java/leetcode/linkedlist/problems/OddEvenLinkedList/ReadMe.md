# LeetCode 328 - Odd Even Linked List

## Problem Classification
- **Difficulty:** Medium
- **Pattern:** Linked List - Partition/Re-link
- **Topics:** Pointer Manipulation
- **Companies:** Amazon, Microsoft, Google

## Problem Statement

Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices (indices are 1-based). Preserve relative order within each group. Return the reordered list.

### Examples

**Example 1**
```
Input:  head = [1,2,3,4,5]
Output: [1,3,5,2,4]
Explanation: Odd indices (1,3,5) stay in order, followed by even (2,4)
```

**Example 2**
```
Input:  head = [2,1,3,5,6,4,7]
Output: [2,3,6,7,1,5,4]
```

**Example 3**
```
Input:  head = [1]
Output: [1]
```

### Constraints
- `n` is in `[0, 10^4]`
- `-10^6 <= Node.val <= 10^6`

## Approaches

### Approach 1: Two Pointer Chains (Optimal)
1. Create two pointers `odd` and `even`
2. `evenHead` keeps start of even chain
3. Rewire odd nodes to skip over evens, and vice versa
4. Attach `odd` tail to `evenHead` after traversal

**Time:** O(n) **Space:** O(1)

### Approach 2: Dummy Nodes (Alternative)
1. Build new odd and even chains using dummy heads
2. Attach odd tail to even head

**Time:** O(n) **Space:** O(1) extra nodes (same complexity, but more verbose)

### Approach 3: Array Reconstruction
1. Copy values into odd/even arrays
2. Rewrite list in order

**Time:** O(n) **Space:** O(n) — good for conceptual clarity only

## When to Use This Pattern
- Reordering nodes by position or parity while preserving relative order
- Problems requiring two concurrent traversal streams
- Prepares for more advanced re-linking tasks such as partition lists

## Practice Checklist
- [ ] Handle empty and single-node lists gracefully
- [ ] Maintain references so no nodes are lost during rewiring
- [ ] Remember to connect odd tail to even head at the end
- [ ] Discuss stability (order preservation) when presenting solution

