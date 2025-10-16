# Delete Middle Node - Cheat Sheet

## Quick Facts
- **LeetCode:** 2095
- **Pattern:** Slow/Fast Pointers
- **Goal:** Remove node at index `floor(n/2)`
- **Time:** O(n)
- **Space:** O(1)

## Core Template
```java
public ListNode deleteMiddle(ListNode head) {
    if (head == null || head.next == null) {
        return null;
    }

    ListNode slow = head;
    ListNode fast = head.next.next;
    ListNode prev = head;

    while (fast != null && fast.next != null) {
        prev = slow;
        slow = slow.next;
        fast = fast.next.next;
    }

    // slow points at middle
    prev.next = slow.next;
    return head;
}
```

## Key Notes
- Length 1 → return `null`
- For length 2 → delete second node (index floor(2/2)=1)
- `prev` keeps track of node before `slow`
- When `fast` reaches end, `slow` is at middle

## Alternatives
- **Two-pass count:** simpler but O(2n)
- **Array copy:** good for testing, not optimal

## Edge Case Checklist
- `[1]` → `[]`
- `[1,2]` → `[1]`
- `[1,2,3]` → `[1,3]`
- All equal values still require pointer logic

