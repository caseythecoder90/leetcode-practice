# Odd Even Linked List - Flashcards

**Q:** What are "odd" and "even" positions in this problem?  
**A:** 1-based indices: node 1 is odd, node 2 is even, etc.

**Q:** Which pointers do you maintain?  
A:** `odd` for the current odd node, `even` for the current even node, and `evenHead` to remember the start of the even sequence.

**Q:** What is the loop condition?  
A:** `while (even != null && even.next != null)` — ensures both groups have nodes to link.

**Q:** How do you maintain order within the odd group?  
A:** Always link `odd.next` to `even.next`, skipping over even nodes while traversing in order.

**Q:** What happens after the loop?  
A:** Connect the tail of the odd list to `evenHead` to append the even list.

**Q:** Why does this run in O(1) space?  
A:** It only rearranges existing `next` pointers without allocating new nodes or arrays.

**Q:** When would you consider a dummy node solution?  
A:** For clarity in implementation, though it provides the same complexity.

