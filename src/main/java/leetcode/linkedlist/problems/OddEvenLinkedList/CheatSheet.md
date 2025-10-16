# Odd Even Linked List - Cheat Sheet

## Quick Facts
- **LeetCode:** 328
- **Pattern:** Re-linking by index parity
- **Goal:** Group odd-indexed nodes, then even-indexed nodes
- **Time:** O(n)
- **Space:** O(1)

## Core Template
```java
public ListNode oddEvenList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode odd = head;
    ListNode even = head.next;
    ListNode evenHead = even;

    while (even != null && even.next != null) {
        odd.next = even.next;
        odd = odd.next;

        even.next = odd.next;
        even = even.next;
    }

    odd.next = evenHead;
    return head;
}
```

## Key Reminders
- Track `evenHead` to reconnect at the end.
- Loop guards: `even != null && even.next != null` to avoid null pointer.
- Works with 1-based indexing: first node is odd.
- Preserves original relative order within odd and within even groups.

## Edge Cases
- Length 0 → return `null`
- Length 1 → return head
- Length 2 → already grouped

## Variations
- Using dummy nodes for odd/even lists (more verbose but clearer for some)
- Array-based approach for conceptual clarity (not optimal memory)

