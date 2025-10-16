# Linked List Cycle

**Difficulty**: Easy
**LeetCode Problem**: [#141 Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)

## Problem Description

Given `head`, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to. **Note that `pos` is not passed as a parameter**.

Return `true` if there is a cycle in the linked list. Otherwise, return `false`.

### Examples

**Example 1:**
```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
```

**Example 2:**
```
Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
```

**Example 3:**
```
Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
```

### Constraints
- The number of the nodes in the list is in the range `[0, 10^4]`
- `-10^5 <= Node.val <= 10^5`
- `pos` is `-1` or a valid index in the linked-list

## Approach: Floyd's Cycle Detection (Tortoise and Hare)

### Key Insight
Use two pointers moving at different speeds:
- **Slow pointer**: moves one step at a time
- **Fast pointer**: moves two steps at a time

If there's a cycle, the fast pointer will eventually catch up to the slow pointer. If there's no cycle, the fast pointer will reach the end (null).

### Why This Works
Think of it like a race track:
- If the track is circular (has a cycle), the faster runner will eventually lap the slower runner
- If the track has an end (no cycle), the faster runner will reach the end first

### Algorithm
1. Initialize two pointers: `slow` and `fast`, both starting at `head`
2. Move `slow` one step and `fast` two steps in each iteration
3. If `fast` or `fast.next` becomes null, there's no cycle (return false)
4. If `slow` equals `fast`, there's a cycle (return true)

### Complexity Analysis
- **Time Complexity**: O(n)
  - If no cycle: fast pointer reaches end in n/2 iterations
  - If cycle exists: slow pointer enters cycle, fast pointer catches up within cycle length iterations
  - Overall: O(n)

- **Space Complexity**: O(1)
  - Only using two pointer variables, constant space

## Alternative Approach: Hash Set

### Algorithm
1. Use a HashSet to track visited nodes
2. Traverse the list, adding each node to the set
3. If we encounter a node already in the set, there's a cycle
4. If we reach null, there's no cycle

### Complexity
- **Time**: O(n)
- **Space**: O(n) - storing up to n nodes in the set

**Why Floyd's is preferred**: Uses O(1) space instead of O(n)

## Pattern Recognition
This is a classic **two-pointer** problem with different speeds. The Floyd's Cycle Detection algorithm is a fundamental technique used in:
- Detecting cycles in linked lists
- Finding the start of a cycle (variant problem)
- Duplicate detection in sequences
- Various graph algorithms

## Common Pitfalls
1. **Null pointer checks**: Always check if `fast` and `fast.next` are not null before moving fast pointer
2. **Starting positions**: Both pointers should start at head, not slow at head and fast at head.next
3. **Loop condition**: Check `fast != null && fast.next != null` before moving pointers

## Related Problems
- [#142 Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/) - Find the node where cycle begins
- [#287 Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/) - Uses same algorithm
- [#202 Happy Number](https://leetcode.com/problems/happy-number/) - Cycle detection in sequences
