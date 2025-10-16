# Delete the Middle Node of a Linked List - Study Guide

## Core Intuition
- You cannot index directly into a linked list, so the middle must be discovered by traversal.
- Using fast/slow pointers lets the list be scanned once while keeping O(1) extra space.
- The node to delete is the one **after** the `slow` pointer when `fast` reaches the end.

## Detailed Steps (Fast & Slow)
1. **Handle list of length 1**
   - Only node is the middle → return `null`.

2. **Initialize pointers**
   - `slow = head`
   - `fast = head.next.next`
   - `prev = head`

3. **Traverse**
   - Move `slow` one step, `fast` two steps.
   - Maintain `prev` so we know node before `slow`.

4. **Stop Condition**
   - When `fast` is `null` or `fast.next` is `null`, `slow` points at middle.

5. **Delete Middle**
   - `prev.next = slow.next`

## Walkthrough Example
```
List: 1 → 3 → 4 → 7 → 1 → 2 → 6

Initial:
 slow=1, fast=4, prev=1

Iter 1:
 slow=3, fast=1 (second last), prev=3

Iter 2:
 slow=4, fast=6 (tail), prev=4

Iter 3:
 slow=7, fast=null → stop

Delete slow (7):
 4.next = 1

Result: 1 → 3 → 4 → 1 → 2 → 6
```

## Complexity Analysis
- **Time:** O(n)
- **Space:** O(1)
- Only single pass and constant extra pointers

## Pitfalls & Safeguards
- Forgetting to check `head.next == null` (single node)
- Off-by-one when initializing `fast`
- Losing connection to remainder of list when skipping middle
- Using recursion can overflow stack for `1e5` length

## Alternative Thoughts
- **Two-pass counting**: count nodes, delete at `count/2`
- **Array copy**: not memory-efficient but good for validation

## Interview Talking Points
- “This uses the classic fast/slow pointer technique to find the middle in O(n).”
- “We keep a pointer to the node before the middle to relink after deletion.”
- “Edge cases for length 1 or 2 determine initial pointer setup.”

