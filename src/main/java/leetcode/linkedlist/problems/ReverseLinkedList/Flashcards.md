# Reverse Linked List - Flashcards

## Problem Recognition

**Q: What clues tell you this is a pointer reversal problem?**  
A: Any prompt that says “reverse the linked list” or “return the reversed list” requires in-place pointer manipulation with a new head.

**Q: Why can’t we use indices like arrays?**  
A: Linked lists lack random access. We must traverse sequentially and update `next` pointers manually.

## Iterative Approach

**Q: What three pointers do we track iteratively?**  
A: `prev` (already reversed part), `current` (node being processed), `next` (original next node stored before breaking the link).

**Q: How do you prevent losing the rest of the list?**  
A: Store `current.next` in `next` before reassigning it to `prev`.

**Q: When do you update `prev` and `current`?**  
A: After reversing the link: set `prev = current`, then `current = next`.

**Q: What is the return value?**  
A: `prev`, which points to the new head once `current` becomes `null`.

## Recursive Approach

**Q: What is the base case for recursion?**  
A: When `head` is `null` or `head.next` is `null`, return `head`.

**Q: How does the recursion rebuild the reversed list?**  
A: After reversing the rest, make `head.next.next = head` to place the current node at the tail of the reversed sublist, then set `head.next = null`.

**Q: What makes recursion more expensive?**  
A: The call stack stores up to `n` frames, so space complexity becomes `O(n)`.

## Edge Cases & Pitfalls

**Q: What happens if the list is empty?**  
A: Both iterative and recursive approaches return `null` immediately.

**Q: Why must `head.next = null` in recursion?**  
A: To terminate the reversed list; otherwise, pointers form a cycle.

**Q: How do you verify correctness quickly?**  
A: Trace a small list `[1,2,3]` by hand or print nodes while reversing.

## Extensions & Variations

**Q: Which problems build directly on this technique?**  
A: Reverse Linked List II (partial reversal), Reverse Nodes in k-Group (block reversal), Palindrome Linked List (reverse second half), Reorder List (reverse + merge).

**Q: How does reversing help with Palindrome Linked List?**  
A: Reverse the second half, compare halves, then optionally restore original order.

## Interview Tips

**Q: How should you pitch the solution to the interviewer?**  
A: "We walk the list once, flipping `next` pointers while preserving the remaining nodes via a temporary variable."

**Q: What follow-up question often appears?**  
A: Implement the recursive solution and explain the space trade-off.

**Q: How do you debug pointer bugs?**  
A: Draw the nodes, show `prev/current/next` at each step, and ensure no arrows are lost.

