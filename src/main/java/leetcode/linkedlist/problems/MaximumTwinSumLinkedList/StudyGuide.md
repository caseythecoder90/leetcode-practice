# Maximum Twin Sum of a Linked List - Study Guide

## Why This Problem Matters
- Introduces the idea of pairing symmetric nodes in a singly linked list.
- Forces practice combining three core techniques: **middle finding**, **reversal**, and **parallel traversal**.
- Variants appear in problems like palindrome checks and half-list manipulations.

## Core Insight
Twins are nodes equally distant from the head and tail. Instead of using random access (impossible in linked lists), reverse the second half so the twins become adjacent for easy summation.

## Step-by-Step Plan (Reverse Second Half)

1. **Find Midpoint**
   - Use slow/fast pointers. When fast hits the end, slow points to start of second half.

2. **Reverse Second Half In-Place**
   ```
   Before: 5 → 4 → 2 → 1
                     ↑
                    slow

   After reversal: 5 → 4 → 1 → 2   (second half: 1 → 2)
   ```

3. **Walk Two Pointers Together**
   - Pointer `p1` starts at head, `p2` at reversed half.
   - At each step: `max = max(max, p1.val + p2.val)`.

4. **(Optional) Restore List**
   - Interview follow-up: reverse the second half again to keep structure unchanged.

## Complexity
- **Time:** O(n) — one pass to find middle, one to reverse, one to compute sums.
- **Space:** O(1) — in-place reversal uses constant extra pointers.

## Dry Run
```
Input: [5,4,2,1]

slow, fast start at head:
 slow=5, fast=5
 slow=4, fast=2
 slow=2, fast=null → stop (slow at 2)

Reverse starting at 2:
 2 → 1 becomes 1 → 2

Twin walk:
 p1=5, p2=1 → sum=6
 p1=4, p2=2 → sum=6
 Max = 6
```

## Common Pitfalls
- Forgetting list length is guaranteed even (but still guard against null).
- Not handling two-node list (edge case).
- Losing reference to second half during reversal (always store `next` first).
- Using recursion to reverse — exceeds call stack at 1e5 nodes.

## Alternative Approaches

| Approach | Pros | Cons |
|----------|------|------|
| Array Copy | Easy to implement | O(n) extra memory |
| Stack | One pass to push/pop | O(n) extra memory |
| Reverse Half (optimal) | O(1) extra space | Requires careful pointer handling |

## Interview Talking Points
- “We can’t access twins directly, so we bring them together by reversing the second half.”
- “Slow/fast pointer makes midpoint detection O(n) without extra space.”
- “In-place reversal keeps memory constant which matters for 1e5 nodes.”
- “Optionally mention restoring the list to show awareness of side effects.”

