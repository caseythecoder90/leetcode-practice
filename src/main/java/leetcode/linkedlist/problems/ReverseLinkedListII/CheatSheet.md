# Reverse Linked List II (LeetCode 92) - Study Guide

## Pattern: Linked List - In-place Reversal (Sublist)

This is a **critical extension** of the basic linked list reversal pattern. Unlike reversing an entire list, this problem requires you to:
1. Navigate to a specific position
2. Reverse only a portion of the list
3. Reconnect the reversed section back to the original list

## Problem Statement

Given the head of a singly linked list and two integers `left` and `right` where `left <= right`, reverse the nodes of the list from position `left` to position `right`, and return the reversed list.

**Constraints:**
- The number of nodes in the list is `n`
- `1 <= n <= 500`
- `-500 <= Node.val <= 500`
- `1 <= left <= right <= n`

**Follow-up:** Could you do it in one pass?

## Visual Understanding

### Example 1:
```
Input: head = [1,2,3,4,5], left = 2, right = 4

Before:  1 → 2 → 3 → 4 → 5 → null
              ↑_________↑
              left    right

After:   1 → 4 → 3 → 2 → 5 → null
              ↑_________↑
            reversed section
```

### Key Observation:
The list has **three sections**:
1. **Before reversal** (nodes 1 to left-1): stays unchanged
2. **Reversal section** (nodes left to right): gets reversed
3. **After reversal** (nodes right+1 to end): stays unchanged

## Core Algorithm - The "Dummy Node" Approach

### Why Use a Dummy Node?

The dummy node solves the **edge case** where `left = 1` (reversing from the head).

**Without dummy:**
```java
ListNode prev = null;  // When left = 1, prev is null
// Later: prev.next = newHead; // ❌ NullPointerException!
```

**With dummy:**
```java
ListNode dummy = new ListNode(0);
dummy.next = head;
ListNode prev = dummy;  // prev is never null!
// Later: prev.next = newHead; // ✅ Always safe!
```

### Algorithm Steps

#### Step 1: Create Dummy and Find Position
```java
ListNode dummy = new ListNode(0);
dummy.next = head;

ListNode beforeReverse = dummy;

// Move to node BEFORE left position
for (int i = 1; i < left; i++) {
    beforeReverse = beforeReverse.next;
}
```

**Example:** `left = 2`
```
Before loop:
dummy → 1 → 2 → 3 → 4 → 5
↑
beforeReverse

After loop (i goes from 1 to 1):
dummy → 1 → 2 → 3 → 4 → 5
        ↑
        beforeReverse
```

#### Step 2: Save Connection Points
```java
ListNode startReverse = beforeReverse.next;  // Node at position 'left'
```

**Why save this?** The node at position `left` will become the **tail** of the reversed section and needs to connect to the rest of the list.

```
dummy → 1 → 2 → 3 → 4 → 5
        ↑   ↑
  beforeReverse startReverse
```

#### Step 3: Reverse the Sublist
```java
ListNode prev = null;
ListNode curr = startReverse;

// Reverse exactly (right - left + 1) nodes
for (int i = 0; i <= right - left; i++) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
```

**Detailed trace for `left = 2, right = 4`:**

```
Initial state:
prev = null
curr = 2 (startReverse)

Iteration 1 (i = 0):
  next = 3
  2.next = null (was pointing to 3)
  prev = 2
  curr = 3

Iteration 2 (i = 1):
  next = 4
  3.next = 2 (was pointing to 4)
  prev = 3
  curr = 4

Iteration 3 (i = 2):
  next = 5
  4.next = 3 (was pointing to 5)
  prev = 4
  curr = 5

After loop:
  prev = 4 (new head of reversed section)
  curr = 5 (first node after reversed section)
  
Reversed section: 4 → 3 → 2 → null
```

#### Step 4: Reconnect Everything
```java
beforeReverse.next = prev;     // Connect to new head of reversed section
startReverse.next = curr;      // Connect tail of reversed section to rest
```

**Visual:**
```
Before reconnection:
dummy → 1     4 → 3 → 2 → null     5 → null
        ↑     ↑         ↑           ↑
  beforeReverse prev  startReverse curr

After reconnection:
dummy → 1 → 4 → 3 → 2 → 5 → null
```

#### Step 5: Return Result
```java
return dummy.next;  // Skip the dummy node
```

## Complete Solution

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }
        
        // Step 1: Create dummy node to handle edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Step 2: Navigate to node BEFORE left position
        ListNode beforeReverse = dummy;
        for (int i = 1; i < left; i++) {
            beforeReverse = beforeReverse.next;
        }
        
        // Step 3: Save connection points
        ListNode startReverse = beforeReverse.next;
        
        // Step 4: Reverse the sublist
        ListNode prev = null;
        ListNode curr = startReverse;
        
        for (int i = 0; i <= right - left; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        // Step 5: Reconnect
        beforeReverse.next = prev;      // Connect to new head
        startReverse.next = curr;       // Connect to remaining list
        
        return dummy.next;
    }
}
```

## Complexity Analysis

- **Time Complexity:** O(n)
    - We traverse the list once: O(left) to reach position + O(right - left) to reverse
    - In worst case: O(n) when we reverse the entire list

- **Space Complexity:** O(1)
    - Only using a constant number of pointers
    - No recursion, no extra data structures

## Common Mistakes and How to Avoid Them

### ❌ Mistake 1: Using Loop Variable Incorrectly

```java
// WRONG - Using 'left' as loop variable
for (int i = left; left <= right; left++) {  // ❌ Modifying left!
    // This creates an infinite loop
}
```

**Why it fails:** You're modifying the `left` parameter itself, causing infinite loop.

**Fix:**
```java
// CORRECT - Use separate counter
for (int i = 0; i <= right - left; i++) {  // ✅
    // Process exactly (right - left + 1) nodes
}
```

### ❌ Mistake 2: NullPointerException when left = 1

```java
// WRONG - Not using dummy node
ListNode prev = null;
ListNode curr = head;

for (int i = 1; i < left; i++) {
    prev = curr;
    curr = curr.next;
}
// When left = 1, prev is still null!
prev.next = newHead;  // ❌ NullPointerException
```

**Fix:** Use dummy node approach as shown above.

### ❌ Mistake 3: Wrong Iteration Count

```java
// WRONG - Off by one
for (int i = left; i < right; i++) {  // ❌ Only reverses (right - left) nodes
    // Should reverse (right - left + 1) nodes
}
```

**Fix:**
```java
// CORRECT
for (int i = 0; i <= right - left; i++) {  // ✅ Reverses (right - left + 1) nodes
    // ...
}
```

### ❌ Mistake 4: Forgetting to Reconnect

```java
// WRONG - Not reconnecting the list
beforeReverse.next = prev;     // ✅ Connect to reversed section
// Missing: startReverse.next = curr;  // ❌ Tail not connected to rest!
```

**Result:** You lose the rest of the list after the reversed section.

## Edge Cases to Consider

### 1. Reverse from head (left = 1)
```
Input: [1,2,3,4,5], left = 1, right = 3
Output: [3,2,1,4,5]
```
**Handled by:** Dummy node

### 2. Reverse to tail (right = n)
```
Input: [1,2,3,4,5], left = 3, right = 5
Output: [1,2,5,4,3]
```
**Handled by:** `curr` will be `null` after reversal, which is correct

### 3. Reverse single node (left = right)
```
Input: [1,2,3,4,5], left = 3, right = 3
Output: [1,2,3,4,5]  (no change)
```
**Handled by:** Early return check

### 4. Entire list (left = 1, right = n)
```
Input: [1,2,3,4,5], left = 1, right = 5
Output: [5,4,3,2,1]
```
**Handled by:** Algorithm works for full reversal

### 5. Two-node list
```
Input: [1,2], left = 1, right = 2
Output: [2,1]
```

## Interview Strategy

### 1. Clarify Requirements
- "Should I handle null input?" (Usually yes)
- "Can left = right?" (Usually yes, but no reversal needed)
- "Is it guaranteed that 1 <= left <= right <= n?" (Usually yes)

### 2. Start with Simpler Problem
"I'll first recall how to reverse an entire linked list, then adapt it for this problem."

```java
// Basic reversal reminder
ListNode prev = null;
ListNode curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev;
```

### 3. Identify the Challenge
"The main challenge is:
1. Finding the correct position to start
2. Reversing only the specified nodes
3. Reconnecting everything properly"

### 4. Explain Dummy Node
"I'll use a dummy node to handle the edge case where we reverse from the head."

### 5. Walk Through Example
Always trace through the example visually:
```
Input: [1,2,3,4,5], left = 2, right = 4

Step by step:
1. dummy → 1 → 2 → 3 → 4 → 5
2. Navigate to position 1 (before left)
3. Reverse 2 → 3 → 4 to get 4 → 3 → 2
4. Reconnect: 1 → 4 → 3 → 2 → 5
```

### 6. Code and Test
Write the solution and test with edge cases.

## Related Problems

### Prerequisite:
- **206. Reverse Linked List** (Easy)
    - Master this first! Same reversal technique but simpler.

### Similar Pattern:
- **25. Reverse Nodes in k-Group** (Hard)
    - Reverse in groups of k nodes
    - Uses same reversal logic repeatedly

- **24. Swap Nodes in Pairs** (Medium)
    - Special case of k = 2

### Advanced:
- **143. Reorder List** (Medium)
    - Combines finding middle, reversing, and merging

## Practice Tips

1. **Master the basic reversal first** (Problem 206)
2. **Draw diagrams** for each step
3. **Practice without dummy node first**, then with dummy node
4. **Trace through edge cases** (left = 1, right = n)
5. **Time yourself** - aim for 15-20 minutes including testing

## Key Takeaways

1. **Dummy node pattern** solves edge case of reversing from head
2. **Three sections:** before, reverse, after
3. **Save connection points** before reversing
4. **Iteration count** is `(right - left + 1)` nodes
5. **Two reconnections needed:** before → reversed, reversed → after
6. **One-pass solution:** Navigate and reverse in single traversal

## Memory Aid - "The Three Connections"

Remember the **3 critical pointers**:
1. `beforeReverse` - node before the reversal section
2. `startReverse` - first node to reverse (becomes tail)
3. `prev` - new head of reversed section (after reversal)

**Reconnection formula:**
```
beforeReverse.next = prev
startReverse.next = curr
```

This pattern appears in many linked list problems!