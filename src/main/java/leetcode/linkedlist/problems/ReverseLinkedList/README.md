# Reverse Linked List (LeetCode 206) - Study Guide

**Pattern**: Linked List Reversal | **Difficulty**: Easy | **Type**: In-place Transformation

## Problem Statement

Given the head of a singly linked list, reverse the list and return the new head. You must reverse the links between the nodes so the list is traversed in the opposite direction.

### Examples

**Example 1**
```
Input:  head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

**Example 2**
```
Input:  head = [1,2]
Output: [2,1]
```

**Example 3**
```
Input:  head = []
Output: []
```

### Constraints
- `0 <= n <= 5000`
- `-5000 <= Node.val <= 5000`
- Follow up: Solve both iteratively and recursively.

## Problem Analysis

### Pattern Recognition
- Keywords such as **"reverse linked list"**, **"in-place"**, or **"reverse pointers"**
- Head pointer changes, so you must return the new head
- No random access available (unlike arrays), so pointer manipulation is required

### Why It Matters
- Foundation for many linked list problems (palindrome check, k-group reversal, copying lists)
- Builds intuition around pointer updates and safe traversal
- Often the first linked list problem asked in interviews

## Solution Approaches

### Approach 1: Iterative In-Place Reversal (Optimal)

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;

    while (current != null) {
        ListNode next = current.next; // Store next node before breaking link
        current.next = prev;          // Reverse current pointer
        prev = current;               // Advance prev
        current = next;               // Move to next node
    }

    return prev; // New head of the reversed list
}
```

**Intuition**
1. Walk through the list once
2. For each node, redirect its `next` pointer to the previous node
3. Keep track of `prev` and `current` pointers while storing `next`

**Complexity**
- Time: `O(n)` – each node visited once
- Space: `O(1)` – only constant extra pointers

### Approach 2: Recursive Reversal (Elegant)

```java
public ListNode reverseListRecursive(ListNode head) {
    if (head == null || head.next == null) return head;

    ListNode reversedHead = reverseListRecursive(head.next);
    head.next.next = head; // Place current node after its successor
    head.next = null;      // Prevent cycles

    return reversedHead;
}
```

**Intuition**
1. Recursively reverse the sublist starting at `head.next`
2. Once the recursive call returns the reversed sublist, attach the current node to the tail
3. Set `head.next` to `null` to terminate the list

**Complexity**
- Time: `O(n)`
- Space: `O(n)` due to recursion call stack

### Approach 3: Using a Stack (Educational)

```java
public ListNode reverseListWithStack(ListNode head) {
    if (head == null) return null;
    java.util.Stack<ListNode> stack = new java.util.Stack<>();

    while (head != null) {
        stack.push(head);
        head = head.next;
    }

    ListNode newHead = stack.pop();
    ListNode current = newHead;

    while (!stack.isEmpty()) {
        current.next = stack.pop();
        current = current.next;
    }

    current.next = null;
    return newHead;
}
```

**Complexity**
- Time: `O(n)`
- Space: `O(n)` due to storing nodes in the stack
- Useful when recursion is restricted and iterative pointer manipulation feels tricky (but not optimal)

## Algorithm Walkthrough (Iterative)

Consider `head = [1 → 2 → 3 → 4 → null]`

```
prev = null
current = 1

Iteration 1:
  next = 2
  current.next = prev (1 → null)
  prev = 1
  current = 2

Iteration 2:
  next = 3
  current.next = prev (2 → 1 → null)
  prev = 2
  current = 3
...
Final state: prev = 4 → 3 → 2 → 1 → null
```

Return `prev` as the new head.

## Edge Cases
- **Empty list** (`null`) → return `null`
- **Single node** (`[x]`) → same node returned
- **Two nodes** (`[a,b]`) → `[b,a]` after one iteration
- **List with duplicates** – reversal does not require unique values

## Common Pitfalls
1. **Forgetting to store `next`** before reversing the pointer, which loses the rest of the list
2. **Not updating `head.next = null`** in recursion, causing cycles
3. **Returning the wrong pointer** – `prev` (iterative) or `reversedHead` (recursive) is the new head
4. **Assuming doubly linked list operations** – this problem is strictly singly linked

## Pattern Variations
| Problem | Variation | Key Adjustment |
|---------|-----------|----------------|
| Reverse Linked List II | Reverse sublist `m` to `n` | Use dummy node + partial reversal |
| Reverse Nodes in k-Group | Reverse blocks of size `k` | Reverse sublists conditionally |
| Palindrome Linked List | Reverse second half | Reverse only second half and compare |
| Reorder List | Reverse second half + merge | Combine reversal with interleaving |

## Interview Tips
1. **Narrate the pointer movements** – interviewers want to see safe manipulation
2. **Start with iterative** (constant space), then discuss recursive follow-up
3. **Clarify return value** – the new head is required
4. **Draw diagrams** while explaining; it prevents logical mistakes
5. **Mention complexity trade-offs** when comparing iterative vs recursive

## Practice Checklist
- [ ] Implement iterative reversal fluently
- [ ] Explain pointer updates without code
- [ ] Trace through arbitrary list manually
- [ ] Implement recursive version with correct base case
- [ ] Understand how this pattern extends to more complex linked list problems

