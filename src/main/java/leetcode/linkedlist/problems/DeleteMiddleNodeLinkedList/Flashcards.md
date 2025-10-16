# Delete Middle Node - Flashcards

**Q:** How do you detect the middle without counting length?  
**A:** Use slow/fast pointers; slow steps one node, fast steps two, so when fast ends slow is at middle.

**Q:** What do you return when the list has a single node?  
**A:** `null` (the only node is deleted).

**Q:** Why keep a `prev` pointer?  
**A:** To reconnect `prev.next` to `slow.next` once the middle node is identified.

**Q:** What is the loop condition?  
**A:** `while (fast != null && fast.next != null)` to avoid null pointer errors.

**Q:** Why initialize `fast` as `head.next.next`?  
**A:** So that for 2-node lists, the loop doesn't run and `slow` remains at index 0 (node before middle).

**Q:** Time and space complexity?  
**A:** O(n) time, O(1) extra space.

**Q:** Alternative approach for clarity?  
**A:** Count nodes in a first pass, delete at index `count / 2` in a second pass.

