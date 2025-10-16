# LinkedList Pattern

## Overview

LinkedList problems are fundamental to coding interviews and involve manipulating nodes connected through pointers. These problems test your understanding of pointer manipulation, recursion vs iteration, and careful edge case handling. Unlike arrays, linked lists don't provide random access, making certain operations more challenging but also opening up unique solution approaches.

## Core Concepts

### What are Linked Lists?
A linked list is a linear data structure where elements (nodes) are stored in sequence, but not in contiguous memory locations. Each node contains:
- **Data**: The actual value stored
- **Next pointer**: Reference to the next node in the sequence

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

### Key Properties
- **Dynamic size**: Can grow/shrink during runtime
- **Sequential access**: Must traverse from head to reach any node
- **Efficient insertion/deletion**: O(1) when you have the node reference
- **No random access**: Can't directly access the nth element like arrays

## Essential Techniques

### 1. Two Pointers (Fast & Slow)

**Pattern**: Use two pointers moving at different speeds to detect cycles, find middle, or measure distances.

```java
// Find middle of linked list
public ListNode findMiddle(ListNode head) {
    ListNode slow = head, fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;        // Move 1 step
        fast = fast.next.next;   // Move 2 steps
    }
    
    return slow; // Slow pointer is at middle
}
```

**Applications**:
- Cycle detection (Floyd's algorithm)
- Finding middle node
- Finding nth from end
- Detecting palindromes

### 2. Iterative vs Recursive Reversal

**Iterative Approach** (Preferred for interviews):
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null, current = head;
    
    while (current != null) {
        ListNode nextTemp = current.next; // Store next
        current.next = prev;              // Reverse link
        prev = current;                   // Move prev forward
        current = nextTemp;               // Move current forward
    }
    
    return prev; // New head
}
```

**Recursive Approach**:
```java
public ListNode reverseList(ListNode head) {
    // Base case
    if (head == null || head.next == null) {
        return head;
    }
    
    // Recursively reverse rest
    ListNode newHead = reverseList(head.next);
    
    // Reverse current connection
    head.next.next = head;
    head.next = null;
    
    return newHead;
}
```

### 3. Dummy Node Pattern

**Use Case**: Simplifies edge cases when the head might change.

```java
public ListNode removeElements(ListNode head, int val) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode current = dummy;
    
    while (current.next != null) {
        if (current.next.val == val) {
            current.next = current.next.next; // Skip node
        } else {
            current = current.next;
        }
    }
    
    return dummy.next; // Return actual head
}
```

**Why Dummy Nodes Help**:
- Eliminates special handling for head removal
- Provides a stable reference point
- Simplifies pointer manipulation logic

### 4. Runner Technique (Multiple Pointers)

**Pattern**: Use multiple pointers with different starting positions or speeds.

```java
// Remove nth node from end
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode first = dummy, second = dummy;
    
    // Move first pointer n+1 steps ahead
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    // Move both pointers until first reaches end
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    // Remove the nth node
    second.next = second.next.next;
    return dummy.next;
}
```

## Common Problem Categories

### 1. **Basic Operations**
- Reverse Linked List (206)
- Delete Node in a Linked List (237)
- Remove Linked List Elements (203)

### 2. **Two Pointer Techniques**
- Middle of the Linked List (876)
- Linked List Cycle (141)
- Remove Nth Node From End (19)

### 3. **Manipulation & Rearrangement**
- Odd Even Linked List (328)
- Partition List (86)
- Swap Nodes in Pairs (24)

### 4. **Merging & Sorting**
- Merge Two Sorted Lists (21)
- Sort List (148)
- Merge k Sorted Lists (23)

### 5. **Advanced Operations**
- Copy List with Random Pointer (138)
- LRU Cache (146)
- Add Two Numbers (2)

## Essential Edge Cases

### 1. **Empty/Single Node Lists**
```java
// Always check for null and single node
if (head == null || head.next == null) {
    return head; // or appropriate action
}
```

### 2. **Operations at Head**
```java
// Removing head node requires updating head reference
if (head.val == target) {
    return head.next; // New head
}
```

### 3. **Operations at Tail**
```java
// Need to track previous node to update its next pointer
while (current.next != null) {
    if (current.next.val == target) {
        current.next = current.next.next; // Skip target
        break;
    }
    current = current.next;
}
```

### 4. **Cycle Detection**
```java
// Floyd's Cycle Detection
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) return true; // Cycle detected
}
return false;
```

## LeetCode 75 LinkedList Problems

### Core Problems to Master:
1. **206. Reverse Linked List** (Easy)
    - Pattern: Iterative/recursive reversal
    - Foundation for many other problems

2. **2095. Delete the Middle Node of a Linked List** (Medium)
    - Pattern: Two pointers to find middle

3. **328. Odd Even Linked List** (Medium)
    - Pattern: Node rearrangement with multiple pointers

4. **2130. Maximum Twin Sum of a Linked List** (Medium)
    - Pattern: Two pointers + array conversion or stack

## Time/Space Complexity Patterns

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Traversal | O(n) | O(1) | Linear scan |
| Search | O(n) | O(1) | No random access |
| Insert at head | O(1) | O(1) | Direct pointer update |
| Insert at position | O(n) | O(1) | Need to traverse |
| Delete at head | O(1) | O(1) | Direct pointer update |
| Delete at position | O(n) | O(1) | Need to find node |
| Reverse (iterative) | O(n) | O(1) | Single pass |
| Reverse (recursive) | O(n) | O(n) | Call stack |

## Common Mistakes & How to Avoid

### 1. **Null Pointer Dereference**
```java
// ❌ Dangerous
while (current.next != null) // If current is null, this crashes

// ✅ Safe
while (current != null && current.next != null)
```

### 2. **Losing References**
```java
// ❌ Wrong - loses reference to rest of list
current.next = newNode;

// ✅ Correct - preserve the chain
ListNode temp = current.next;
current.next = newNode;
newNode.next = temp;
```

### 3. **Off-by-One Errors**
```java
// Finding nth from end requires careful counting
// Use dummy node to simplify edge cases
```

### 4. **Infinite Loops in Cycles**
```java
// Always check for cycles when traversing unknown lists
// Use Floyd's algorithm or maintain visited set
```

## Learning Path

### Beginner Level
1. **Understand ListNode structure** and basic traversal
2. **Master basic operations**: insertion, deletion, search
3. **Practice with Reverse Linked List** (fundamental skill)

### Intermediate Level
4. **Learn two-pointer techniques** for cycle detection and middle finding
5. **Practice node rearrangement** problems like Odd Even Linked List
6. **Handle edge cases** systematically

### Advanced Level
7. **Combine with other data structures** (HashMap, Stack)
8. **Optimize space complexity** where possible
9. **Design problems** involving linked lists (LRU Cache)

## Quick Decision Framework

### When to Use Dummy Node:
- Head might be removed/changed
- Simplifies edge case handling
- Building new list from scratch

### When to Use Two Pointers:
- Finding middle or specific position
- Cycle detection
- Distance-based operations

### Iterative vs Recursive:
- **Iterative**: Better space complexity O(1)
- **Recursive**: Often cleaner code but O(n) space

## Interview Tips

### 1. **Draw the Problem**
Always sketch the linked list and trace through your algorithm step by step.

### 2. **Handle Edge Cases First**
- Empty list (null head)
- Single node
- Operations at boundaries (head/tail)

### 3. **Preserve References**
Store next pointers before modifying them to avoid losing parts of the list.

### 4. **Test with Examples**
Use simple cases: [], [1], [1,2], [1,2,3] to verify your logic.

---

## Directory Structure

```
linkedlist/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── problems/
│   ├── ReverseLinkedList/
│   ├── DeleteMiddleNode/
│   ├── OddEvenLinkedList/
│   └── MaximumTwinSum/
└── common/
    └── ListNode.java
```

Start with understanding the ListNode structure and basic traversal, then master the fundamental reversal operation before moving to more complex manipulations!