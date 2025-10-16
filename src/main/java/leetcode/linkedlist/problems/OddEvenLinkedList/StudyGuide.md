# Odd Even Linked List - Study Guide

## Problem Insight
- We group nodes by index parity (1-based): positions 1,3,5… then 2,4,6…
- The most efficient approach rewires pointers to form two chains in-place.
- Key idea: maintain heads and tails for odd and even sequences simultaneously.

## Execution Plan

1. **Edge Cases**
   - Empty list → return `null`
   - Single node → already satisfies order

2. **Initialize Pointers**
   - `odd = head`
   - `even = head.next`
   - `evenHead = even` to connect at the end

3. **Iterate and Rewire**
   ```
   while (even != null && even.next != null):
       odd.next = even.next
       odd = odd.next

       even.next = odd.next
       even = even.next
   ```

4. **Connect Chains**
   - `odd.next = evenHead`

5. **Return**
   - Return original `head`, now wired as odd list followed by even list

## Dry Run Example
```
Input: 1 → 2 → 3 → 4 → 5

Initial:
 odd=1, even=2, evenHead=2

Loop Iteration 1:
 odd.next = 3
 odd = 3
 even.next = 4
 even = 4

Loop Iteration 2:
 odd.next = 5
 odd = 5
 even.next = null
 even = null → stop

Final connect:
 odd.next = evenHead (2)

Result: 1 → 3 → 5 → 2 → 4
```

## Complexity
- **Time:** O(n) — each node visited once
- **Space:** O(1) — constant extra pointers

## Pitfalls
- Forgetting to save `evenHead`
- Updating odd pointer before rewiring even pointer (or vice versa) incorrectly
- Failing to guard the loop with `even != null && even.next != null`

## Alternative Thoughts
- Dummy node approach may feel clearer: build separate odd/even lists and merge
- Arrays or vectors can be used but break O(1) space requirement

## Interview Highlights
- Mention 1-based indices (LeetCode definition)
- Emphasize preserving relative order within odd and even groups
- Point out that pointer rewiring keeps O(1) space and linear time

