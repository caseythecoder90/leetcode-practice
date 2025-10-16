# Rotate List

**LeetCode Problem #61** | **Medium** | **Linked List + Two Pointers**

## Problem Description

Given the `head` of a linked list, rotate the list to the right by `k` places.

### Examples

**Example 1:**
```
Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]

Visual:
Original: 1 → 2 → 3 → 4 → 5 → null
After rotating right by 2: 4 → 5 → 1 → 2 → 3 → null
```

**Example 2:**
```
Input: head = [0,1,2], k = 4
Output: [2,0,1]

Visual:
Original: 0 → 1 → 2 → null
After rotating right by 4: 2 → 0 → 1 → null
(k=4 with n=3 means k%3=1, so rotate once)
```

**Example 3:**
```
Input: head = [1], k = 1
Output: [1]
```

### Constraints
- The number of nodes in the list is in the range `[0, 500]`
- `-100 <= Node.val <= 100`
- `0 <= k <= 2 * 10^9`

## Key Insights

### Understanding the Problem
Rotating a list to the right by `k` places means:
1. Take the last `k` nodes
2. Move them to the front of the list
3. The node that was at position `n-k` becomes the new head

### Critical Edge Cases
1. **Empty or single node**: Return as is
2. **k % n == 0**: List doesn't change (full rotations)
3. **k > n**: Use `k = k % n` to handle large k values

### Why k % n Matters
```
If list = [1,2,3,4,5] (n=5) and k=7:
- Rotating by 7 = rotating by 2 (since 7%5=2)
- After 5 rotations, we're back to the original
- So we only need to perform 2 actual rotations
```

## Solution Approaches

### Approach 1: Two-Pointer (Fast/Slow) ⭐ Optimal
**Key Insight**: Use two pointers separated by `k` positions to find the new tail in one pass.

**Algorithm**:
1. Count the list length `n`
2. Normalize: `k = k % n`
3. If `k == 0`, return head (no rotation needed)
4. Use two pointers:
    - `last` pointer: Move k steps ahead
    - `curr` pointer: Start at head
5. Move both pointers together until `last.next == null`
6. Now `curr` points to the new tail
7. Reconnect: `newHead = curr.next`, `last.next = head`, `curr.next = null`

**Why this works**: When `last` reaches the end, `curr` is exactly at position `n-k-1` (the new tail).

```java
public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) return head;
    
    // Count length
    int n = 0;
    ListNode curr = head;
    while (curr != null) {
        n++;
        curr = curr.next;
    }
    
    // Normalize k
    k = k % n;
    if (k == 0) return head;
    
    // Position last pointer k steps ahead
    ListNode last = head;
    for (int i = 0; i < k; i++) {
        last = last.next;
    }
    
    // Move both pointers until last reaches end
    curr = head;
    while (last.next != null) {
        curr = curr.next;
        last = last.next;
    }
    
    // Reconnect
    ListNode newHead = curr.next;
    last.next = head;
    curr.next = null;
    
    return newHead;
}
```

**Time Complexity**: O(n)
- First pass to count length: O(n)
- Second pass to find new tail: O(n)
- Total: O(n)

**Space Complexity**: O(1)
- Only using pointer variables

### Approach 2: Connect to Circular + Break
**Key Insight**: Make the list circular, then break it at the right position.

**Algorithm**:
1. Count length `n` while finding the tail
2. Connect tail to head (make circular)
3. Calculate new tail position: `n - k % n - 1`
4. Walk to new tail position
5. Break the circle: `newHead = newTail.next`, `newTail.next = null`

```java
public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) return head;
    
    // Find length and tail
    int n = 1;
    ListNode tail = head;
    while (tail.next != null) {
        tail = tail.next;
        n++;
    }
    
    // Make circular
    tail.next = head;
    
    // Find new tail position
    k = k % n;
    int stepsToNewTail = n - k - 1;
    
    ListNode newTail = head;
    for (int i = 0; i < stepsToNewTail; i++) {
        newTail = newTail.next;
    }
    
    // Break circle
    ListNode newHead = newTail.next;
    newTail.next = null;
    
    return newHead;
}
```

**Time Complexity**: O(n)
**Space Complexity**: O(1)

## Visual Walkthrough: Your Solution

### Example: [1,2,3,4,5], k = 2

**Step 1: Count length**
```
1 → 2 → 3 → 4 → 5 → null
n = 5
```

**Step 2: Normalize k**
```
k = 2 % 5 = 2 (stays 2)
```

**Step 3: Position last pointer k=2 steps ahead**
```
curr: 1 → 2 → 3 → 4 → 5 → null
last:      ↑ (at position 2)
```

**Step 4: Move both until last.next == null**
```
After moving:
curr:      1 → 2 → 3 → 4 → 5 → null
last:                      ↑
                    ↑ (curr points to node 3)
```

**Step 5: Reconnect**
```
newHead = curr.next = node 4
last.next = head (node 5 points to node 1)
curr.next = null (node 3 points to null)

Result: 4 → 5 → 1 → 2 → 3 → null
```

## Common Mistakes and How You Avoided Them ✅

### Mistake 1: Forgetting k % n
**Your Solution**: ✅ Correctly included `k = k % n`
```java
k = k % n;
if (k == 0) return head;  // Great edge case handling!
```

### Mistake 2: Not Handling k == 0 After Modulo
**Your Solution**: ✅ Explicitly checks for this
```java
if (k == 0) return head;  // Avoids unnecessary work
```

### Mistake 3: Off-by-One Errors
**Your Solution**: ✅ Correct pointer positioning
- Moving `last` exactly `k` steps
- Checking `last.next != null` (not `last != null`)

### Mistake 4: Forgetting Edge Cases
**Your Solution**: ✅ Handles empty and single-node lists
```java
if (head == null || head.next == null) return head;
```

## Pattern Recognition

This problem combines two fundamental linked list patterns:
1. **Finding kth node from end** (fast/slow pointer)
2. **List reconnection** (pointer manipulation)

### Related Problems
- **19. Remove Nth Node From End** - Same two-pointer technique
- **143. Reorder List** - List reconnection pattern
- **206. Reverse Linked List** - Pointer manipulation
- **189. Rotate Array** - Same rotation concept, different data structure

## Complexity Analysis

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Two-Pointer (Your Solution) | O(n) | O(1) | Most intuitive |
| Circular + Break | O(n) | O(1) | Slightly fewer passes |
| Brute Force (k rotations) | O(n*k) | O(1) | Don't use this! |

## Interview Tips

### 1. Clarify Requirements
- "Should I handle k > n?" (Yes, use modulo)
- "What about empty lists?" (Return as is)
- "Can k be negative?" (Usually no, but good to ask)

### 2. Explain Your Approach
- "I'll use two pointers separated by k positions"
- "This finds the new tail in one pass after counting"
- "Then I'll reconnect the pointers"

### 3. Walk Through Edge Cases
- Empty list
- Single node
- k = 0
- k = n (full rotation)
- k > n

### 4. Code Incrementally
1. Handle edge cases first
2. Count length
3. Normalize k
4. Find new tail
5. Reconnect

## Key Takeaways

1. **Always normalize k**: Use `k = k % n` to handle large k
2. **Check k == 0**: After modulo, avoid unnecessary work
3. **Two-pointer separation**: Keep pointers k steps apart
4. **Three-step reconnection**: newHead, connect tail to head, break at new tail
5. **Edge cases matter**: Empty, single node, and k==0 are critical

Your solution demonstrates strong understanding of:
- ✅ Edge case handling
- ✅ Two-pointer technique
- ✅ List manipulation
- ✅ Modulo arithmetic

Great work on solving this problem!