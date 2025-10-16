# Reverse Linked List - Cheat Sheet

## Quick Summary
- **Problem**: Reverse a singly linked list in-place
- **Pattern**: Pointer Reversal
- **Return**: Head of the reversed list
- **Difficulty**: Easy

## Core Iterative Template
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;

    while (current != null) {
        ListNode next = current.next; // Store next node
        current.next = prev;          // Reverse pointer
        prev = current;               // Move prev forward
        current = next;               // Move to next node
    }

    return prev; // New head
}
```

**Time**: O(n) | **Space**: O(1)

## Recursive Template
```java
public ListNode reverseListRecursive(ListNode head) {
    if (head == null || head.next == null) return head;

    ListNode reversedHead = reverseListRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return reversedHead;
}
```

**Time**: O(n) | **Space**: O(n) (call stack)

## Step-by-Step (Iterative)
```
prev   current   next   list
null   1         2      1 → 2 → 3 → null
1      2         3      2 → 1 → null
2      3         null   3 → 2 → 1 → null
return prev = 3 → 2 → 1 → null
```

## Key Checks
- ✅ Handle `head == null` (empty list)
- ✅ Track `next` before breaking links
- ✅ Return `prev` (iterative) or recursive head
- ✅ Set `head.next = null` in recursion to avoid cycles

## Spotting the Pattern
- Mentions “reverse” or “invert” list
- Need to mutate original list, not create new nodes (unless stated)
- Often precursor to more advanced linked list problems

## Common Mistakes
- ❌ Forgetting to store `next` reference
- ❌ Returning `head` instead of `prev`
- ❌ Leaving dangling pointers in recursion
- ❌ Mixing up doubly vs singly linked list methods

## Useful Variations
| Problem | Trick |
|---------|-------|
| Reverse Linked List II | Reverse only between positions `m` and `n` |
| Reverse Nodes in k-Group | Reverse sublists of size `k` repeatedly |
| Palindrome Linked List | Reverse second half to compare halves |
| Reorder List | Reverse second half then merge alternately |

## Interview Sound Bites
- "We reverse pointers one node at a time while keeping track of the next node."
- "Iterative uses three pointers: prev, current, next."
- "Recursive solution unwinds the call stack to reverse links."
- "Both approaches visit each node once, so O(n) time."
- "Iterative is O(1) space; recursion trades elegance for O(n) stack."

