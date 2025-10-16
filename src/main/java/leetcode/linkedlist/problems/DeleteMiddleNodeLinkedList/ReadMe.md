# LeetCode 2095 - Delete the Middle Node of a Linked List

## Problem Classification
- **Difficulty:** Medium
- **Pattern:** Linked List - Middle Removal
- **Topics:** Two Pointers, Pointer Manipulation
- **Companies:** Apple, Amazon, Microsoft

## Problem Statement

Given the head of a singly linked list, delete the middle node and return the modified list. For a list with `n` nodes, the middle node is the `⌊n / 2⌋`-th node (0-indexed). If the list has only one node, return `null`.

### Examples

**Example 1**
```
Input:  head = [1,3,4,7,1,2,6]
Output: [1,3,4,1,2,6]
```

**Example 2**
```
Input:  head = [1]
Output: []
```

**Example 3**
```
Input:  head = [1,2,3,4]
Output: [1,2,4]
```

### Constraints
- `1 <= n <= 10^5`
- `1 <= Node.val <= 10^5`

## Approaches

### Approach 1: Slow/Fast Pointers
1. Handle single-node edge case
2. Use two pointers:
   - `fast` moves two steps at a time
   - `slow` moves one step behind the node to delete
3. When `fast` reaches end, `slow.next` is the middle node
4. Skip the middle by `slow.next = slow.next.next`

**Time:** O(n) **Space:** O(1)

### Approach 2: Length Counting
1. Traverse once to count nodes
2. Compute index of middle
3. Traverse again to delete

**Time:** O(n) **Space:** O(1) (But requires two passes)

### Approach 3: Array/Vector Copy
1. Copy values into array
2. Remove middle value logically

**Time:** O(n) **Space:** O(n) — used for conceptual clarity only

## When to Use This Pattern
- Need to delete nodes relative to length without explicit indexing
- When constant extra space is required
- Building foundation for cycle detection and advanced pointer problems

## Practice Checklist
- [ ] Implement slow/fast pointer correctly
- [ ] Handle single-node and two-node lists safely
- [ ] Understand floor behavior for determining the middle
- [ ] Rehearse edge cases (n = 1, n = 2, all equal values)

