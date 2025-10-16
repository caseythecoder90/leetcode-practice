# Rotate List - Cheat Sheet

## Quick Pattern Recognition

| Keywords | Pattern | This Problem |
|----------|---------|--------------|
| "rotate list", "shift right/left" | List Rotation | ✅ |
| "kth from end" | Two Pointers (Fast/Slow) | ✅ |
| "reconnect nodes" | Pointer Manipulation | ✅ |
| "circular" | Make Circular + Break | ✅ |

## Essential Formula

### Rotation Normalization
```java
k = k % n;  // ALWAYS do this first!
```

### Finding New Tail Position
```java
// If rotating right by k:
newTailPosition = n - k - 1

// If rotating left by k:
newTailPosition = k - 1
```

## Core Templates

### Template 1: Two-Pointer (Fast/Slow) ⭐ Most Common
```java
public ListNode rotateRight(ListNode head, int k) {
    // Edge cases
    if (head == null || head.next == null) return head;
    
    // Step 1: Count length
    int n = 0;
    ListNode curr = head;
    while (curr != null) {
        n++;
        curr = curr.next;
    }
    
    // Step 2: Normalize k
    k = k % n;
    if (k == 0) return head;  // No rotation needed
    
    // Step 3: Position fast pointer k steps ahead
    ListNode last = head;
    for (int i = 0; i < k; i++) {
        last = last.next;
    }
    
    // Step 4: Move both pointers until last reaches end
    curr = head;
    while (last.next != null) {
        curr = curr.next;
        last = last.next;
    }
    
    // Step 5: Reconnect
    ListNode newHead = curr.next;
    last.next = head;        // Connect end to original head
    curr.next = null;        // Break at new tail
    
    return newHead;
}
```

### Template 2: Circular List + Break
```java
public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) return head;
    
    // Step 1: Find length and tail
    int n = 1;
    ListNode tail = head;
    while (tail.next != null) {
        tail = tail.next;
        n++;
    }
    
    // Step 2: Make circular
    tail.next = head;
    
    // Step 3: Find new tail position
    k = k % n;
    int stepsToNewTail = n - k - 1;
    
    ListNode newTail = head;
    for (int i = 0; i < stepsToNewTail; i++) {
        newTail = newTail.next;
    }
    
    // Step 4: Break circle
    ListNode newHead = newTail.next;
    newTail.next = null;
    
    return newHead;
}
```

## Critical Edge Cases

### Edge Case Checklist
```java
// 1. Empty list
if (head == null) return head;

// 2. Single node
if (head.next == null) return head;

// 3. No rotation needed (k % n == 0)
if (k == 0 || k % n == 0) return head;

// 4. Full rotation (k == n)
// Handled by k % n == 0

// 5. k > n (multiple full rotations)
k = k % n;  // This handles it
```

## Pointer Manipulation Patterns

### Pattern 1: Finding kth from End (Two-Pointer)
```java
// Position fast pointer k steps ahead
ListNode fast = head;
for (int i = 0; i < k; i++) {
    fast = fast.next;
}

// Move both until fast reaches end
ListNode slow = head;
while (fast.next != null) {
    slow = slow.next;
    fast = fast.next;
}
// slow is now at (n-k-1)th position
```

### Pattern 2: Three-Step Reconnection
```java
// Step 1: Identify new head
ListNode newHead = newTail.next;

// Step 2: Connect old tail to old head
oldTail.next = head;

// Step 3: Break at new tail
newTail.next = null;
```

## Visual Memorization Aid

### Rotation Right by k=2 on [1,2,3,4,5]
```
Original:
1 → 2 → 3 → 4 → 5 → null

Step 1: Find new tail (at position n-k-1 = 5-2-1 = 2)
1 → 2 → 3 → 4 → 5 → null
        ↑ new tail

Step 2: Identify sections
[1 → 2 → 3] → [4 → 5] → null
 keep here    move to front

Step 3: Reconnect
4 → 5 → 1 → 2 → 3 → null
```

## Common Mistakes & Fixes

### ❌ Mistake 1: Not Normalizing k
```java
// Wrong
ListNode last = head;
for (int i = 0; i < k; i++) {  // k could be > n!
    last = last.next;
}
```

**✅ Fix:**
```java
k = k % n;  // Do this FIRST
if (k == 0) return head;
```

### ❌ Mistake 2: Wrong Loop Condition
```java
// Wrong - will cause NullPointerException
while (last != null) {  // last becomes null!
    curr = curr.next;
    last = last.next;
}
```

**✅ Fix:**
```java
while (last.next != null) {  // Stop before null
    curr = curr.next;
    last = last.next;
}
```

### ❌ Mistake 3: Forgetting to Break the List
```java
// Wrong - creates a cycle
ListNode newHead = curr.next;
last.next = head;
// Missing: curr.next = null;
```

**✅ Fix:**
```java
ListNode newHead = curr.next;
last.next = head;
curr.next = null;  // MUST break the cycle!
```

### ❌ Mistake 4: Off-by-One in Positioning
```java
// Wrong - positions last at k+1
for (int i = 0; i <= k; i++) {  // Should be i < k
    last = last.next;
}
```

**✅ Fix:**
```java
for (int i = 0; i < k; i++) {  // Exactly k steps
    last = last.next;
}
```

## Complexity Reference

| Approach | Time | Space | Passes |
|----------|------|-------|--------|
| Two-Pointer | O(n) | O(1) | 2 |
| Circular + Break | O(n) | O(1) | 1.5 |
| Brute Force | O(n*k) | O(1) | k |

## Related Problems Pattern Map

```
Rotate List (LC 61)
├── Same Pattern
│   ├── Remove Nth From End (LC 19) - Two pointers
│   └── Rotate Array (LC 189) - Same rotation concept
└── Similar Techniques
    ├── Reorder List (LC 143) - List reconnection
    ├── Reverse Linked List (LC 206) - Pointer manipulation
    └── Linked List Cycle II (LC 142) - Fast/slow pointers
```

## Interview Code Snippet

```java
// 5-Minute Interview Template
public ListNode rotateRight(ListNode head, int k) {
    // 1. Edge cases
    if (head == null || head.next == null) return head;
    
    // 2. Count & normalize
    int n = countLength(head);
    k %= n;
    if (k == 0) return head;
    
    // 3. Find new tail using two pointers
    ListNode newTail = findNewTail(head, k);
    ListNode newHead = newTail.next;
    
    // 4. Reconnect
    ListNode oldTail = findTail(head);
    oldTail.next = head;
    newTail.next = null;
    
    return newHead;
}
```

## Quick Debugging Checklist

When your solution fails, check:
- [ ] Did you normalize k with `k % n`?
- [ ] Did you check `k == 0` after normulo?
- [ ] Did you handle empty/single node?
- [ ] Are you using `last.next != null` not `last != null`?
- [ ] Did you break the cycle with `newTail.next = null`?
- [ ] Are loop iterations correct (< k not <= k)?

## Memory Aids

**"Count, Normalize, Find, Reconnect"** - The four steps

**"Modulo First, Check Zero"** - Before any pointer movement

**"K steps ahead, then together"** - Two-pointer positioning

**"New head is old tail's next"** - Reconnection logic

## Time-Saving Tips for Interviews

1. **State edge cases immediately**: "I'll handle null, single node, and k==0"
2. **Mention modulo upfront**: "I'll use k%n since k could be large"
3. **Draw it**: Quick diagram saves explanation time
4. **Walk through small example**: [1,2,3], k=1 covers most logic
5. **Test edge case**: [1], k=100 shows you remember modulo

## Pattern Extension: Rotate Left

To rotate left instead of right:
```java
// Only change: adjust k
k = n - (k % n);  // Convert left rotation to right rotation
// Then use same algorithm!
```

Or directly:
```java
// Find new tail position for left rotation
int newTailPos = (k - 1) % n;
```