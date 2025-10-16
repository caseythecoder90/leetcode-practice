# Maximum Twin Sum - Cheat Sheet

## Quick Facts
- **LeetCode:** 2130
- **Pattern:** Half Reversal + Pairing
- **Time:** O(n)
- **Space:** O(1) extra
- **List Length:** Always even

## Template (Reverse Second Half)
```java
public int pairSum(ListNode head) {
    // Step 1: find middle
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    // Step 2: reverse second half
    ListNode second = reverse(slow);

    // Step 3: walk halves together
    int max = 0;
    ListNode first = head;
    while (second != null) {
        max = Math.max(max, first.val + second.val);
        first = first.next;
        second = second.next;
    }
    return max;
}
```

```java
private ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

## Key Reminders
- Stop fast/slow when fast reaches end — slow starts second half.
- No need to restore list unless asked (but know how).
- Works because list length is even → halves align perfectly.
- Guard against `head == null` (though not needed per constraints).

## Alternatives (Trade-offs)
- **Array:** Store values → O(n) space.
- **Stack:** Push first half → pop while traversing second half.

## Edge Cases
- Length 2 list → direct sum
- Values can be large → keep `int` (fits within limits)
- Identical values still need full traversal

