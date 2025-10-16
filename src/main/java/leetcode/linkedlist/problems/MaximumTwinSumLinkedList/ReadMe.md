# LeetCode 2130 - Maximum Twin Sum of a Linked List

## Problem Classification
- **Difficulty:** Medium
- **Pattern:** Linked List - Twin Pair + Reversal
- **Topics:** Linked List, Two Pointers, Stack
- **Companies:** Amazon, Bloomberg, Google

## Problem Statement

In a singly linked list of even length, define the twin of the `i`-th node as the `(n - 1 - i)`-th node (0-indexed). The twin sum is the sum of a node and its twin. Your task is to return the maximum twin sum across all nodes.

### Examples

**Example 1**
```
Input:  head = [5,4,2,1]
Output: 6
Explanation: Twins are (5,1) and (4,2); max sum = 5 + 1 = 6
```

**Example 2**
```
Input:  head = [4,2,2,3]
Output: 7
Explanation: Twins are (4,3) and (2,2); max sum = 4 + 3 = 7
```

**Example 3**
```
Input:  head = [1,100000]
Output: 100001
```

### Constraints
- The number of nodes in the list is even and in `[2, 10^5]`
- `1 <= Node.val <= 10^5`

## Approaches

### Approach 1: Reverse Second Half (Recommended)
1. Use fast/slow pointers to find the middle
2. Reverse the second half of the list
3. Traverse both halves together, tracking maximum pair sum
4. Optional: Restore the list by reversing the second half again

**Time:** O(n) **Space:** O(1) extra

### Approach 2: Convert to Array
1. Traverse the list and push values into an array
2. Use two indices from both ends to compute twin sums

**Time:** O(n) **Space:** O(n)

### Approach 3: Stack
1. Traverse list pushing first half values onto a stack
2. For second half, pop and combine to compute twin sums

**Time:** O(n) **Space:** O(n)

## When to Use This Pattern
- Linked list with symmetric pairing requirements
- Even-length lists where second half is naturally paired with first half
- Problems mixing two-pointer traversal with in-place reversal

## Practice Checklist
- [ ] Identify middle using slow/fast pointers
- [ ] Reverse list segments without breaking references
- [ ] Traverse two pointers in sync to compute aggregates
- [ ] Understand when to restore original order (follow-up discussion)

