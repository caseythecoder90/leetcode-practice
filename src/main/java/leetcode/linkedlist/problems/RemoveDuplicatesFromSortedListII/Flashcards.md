# Flashcards - Remove Duplicates from Sorted List II

## Core Concept Cards

### Card 1: Problem Definition
**Q:** What's the key difference between "Remove Duplicates I" vs "Remove Duplicates II"?

**A:**
- Remove Duplicates I (LC #83): Keep ONE occurrence of each value
- Remove Duplicates II (LC #82): Remove ALL occurrences if value is duplicated
- Example: `[1,1,2,3,3]`
    - Version I → `[1,2,3]`
    - Version II → `[2]`

---

### Card 2: Why Dummy Node?
**Q:** Why do we need a dummy node for this problem?

**A:** The head itself might need to be deleted!
- Example: `[1,1,2]` → `[2]`
- Without dummy: Complex logic to track new head
- With dummy: Simple `dummy.next` always points to head
- Pattern: Use dummy when head might be modified/deleted

---

### Card 3: Algorithm Overview
**Q:** What are the key steps in the algorithm?

**A:**
1. Create dummy node pointing to head
2. Maintain `prev` (last valid node) and `curr` (examining node)
3. For each node:
    - If has duplicates ahead → skip ALL with that value
    - If no duplicates → move prev forward
4. Return dummy.next

---

## Implementation Cards

### Card 4: Core Loop Structure
**Q:** Write the main while loop structure (pseudocode)?

**A:**
```
while (curr != null) {
    if (curr.next exists AND curr.val == curr.next.val) {
        // Found duplicates
        value = curr.val
        while (curr exists AND curr.val == value) {
            curr = curr.next
        }
        prev.next = curr
    } else {
        // No duplicates
        prev = curr
        curr = curr.next
    }
}
```

---

### Card 5: Duplicate Detection
**Q:** How do we detect if a node value has duplicates?

**A:**
```java
if (curr.next != null && curr.val == curr.next.val)
```
- Must check `curr.next != null` FIRST (avoid NPE)
- Compare current value with next value
- If equal, we found duplicates (remember: sorted list!)

---

### Card 6: Skipping All Duplicates
**Q:** How do we skip ALL nodes with a duplicate value?

**A:**
```java
int duplicateValue = curr.val;
while (curr != null && curr.val == duplicateValue) {
    curr = curr.next;
}
prev.next = curr;  // Connect to first different node
```
Key: Save the duplicate value, then skip ALL nodes with that value

---

## Edge Case Cards

### Card 7: Edge Cases List
**Q:** What are the main edge cases to handle?

**A:**
1. Empty list: `[]` → `[]`
2. Single node: `[1]` → `[1]`
3. All same: `[1,1,1]` → `[]`
4. No duplicates: `[1,2,3]` → `[1,2,3]`
5. Head deleted: `[1,1,2]` → `[2]`
6. Tail deleted: `[1,2,2]` → `[1]`

---

### Card 8: Handling Empty Result
**Q:** What if all nodes are removed (e.g., `[1,1,2,2]`)?

**A:**
- Result: Empty list `[]`
- Our algorithm handles this automatically!
- `prev` stays at dummy, `curr` becomes null
- Return `dummy.next` which is null
- No special case needed!

---

## Common Mistake Cards

### Card 9: Null Pointer Pitfall
**Q:** What's wrong with this condition?
```java
while (curr.val == next.val && next != null)
```

**A:** **Order is wrong!** Accessing `next.val` before checking if `next` is null.
Correct order:
```java
while (next != null && curr.val == next.val)
```
Short-circuit evaluation: If `next == null`, second part won't execute

---

### Card 10: Wrong Deletion Logic
**Q:** Why doesn't this work for removing duplicates?
```java
if (curr.val == curr.next.val) {
    curr.next = curr.next.next;
}
```

**A:** This only removes ONE duplicate, not ALL occurrences!
- If we have `[1,1,1]`, this would give `[1,1]` then `[1]`
- We need `[]` (remove ALL)
- Must track the duplicate value and skip ALL nodes with that value

---

## Pattern Recognition Cards

### Card 11: When to Use This Pattern?
**Q:** What problem characteristics suggest this solution pattern?

**A:**
- **Linked list** manipulation
- **Sorted** data (duplicates are adjacent)
- Need to **remove/modify** nodes based on a condition
- Potential **head deletion**
- **Two-pointer** technique applicable

---

### Card 12: Similar Problems
**Q:** Name 3 problems using similar techniques?

**A:**
1. **LC #203** - Remove Linked List Elements
    - Remove all nodes with specific value
    - Same dummy node pattern

2. **LC #19** - Remove Nth Node From End
    - Two pointers with different spacing
    - Dummy node for head deletion

3. **LC #86** - Partition List
    - Rearrange based on value
    - Multiple dummy nodes

---

## Complexity Cards

### Card 13: Time Complexity
**Q:** What's the time complexity and why?

**A:** **O(n)** where n = number of nodes
- Single pass through the list
- Each node visited at most twice:
    - Once by main curr pointer
    - Possibly once more when skipping duplicates
- Still linear time overall

---

### Card 14: Space Complexity
**Q:** What's the space complexity and why?

**A:** **O(1)** - constant space
- Only using pointer variables (dummy, prev, curr)
- No additional data structures
- No recursion (no call stack)
- In-place modification

---

## Interview Preparation Cards

### Card 15: Initial Clarifications
**Q:** What questions should you ask the interviewer?

**A:**
1. "Remove ALL occurrences of duplicated values, correct?"
2. "Is the list guaranteed to be sorted?"
3. "Can I modify the original list?"
4. "What should I return for empty list?"
5. "Are values integers or could they be other types?"

---

### Card 16: Walkthrough Example
**Q:** Walk through `[1,2,3,3,4,4,5]` step by step

**A:**
```
Start: dummy→[1,2,3,3,4,4,5]
Step 1: No dup for 1, prev=1
Step 2: No dup for 2, prev=2  
Step 3: Found dup 3, skip both, prev.next=4
Step 4: Found dup 4, skip both, prev.next=5
Step 5: No dup for 5, prev=5
Result: [1,2,5]
```

---

### Card 17: Optimization Discussion
**Q:** Can this be solved without a dummy node?

**A:** Yes, but messier:
```java
// Handle head deletion separately
while (head != null && head.next != null && head.val == head.next.val) {
    int val = head.val;
    while (head != null && head.val == val) {
        head = head.next;
    }
}
// Then handle rest...
```
Dummy node is cleaner and more elegant!

---

## Quick Review Cards

### Card 18: 30-Second Summary
**Q:** Explain the solution in 30 seconds

**A:** Use dummy node for potential head deletion. Keep two pointers: prev (last valid node) and curr (examining). When duplicates found, record the value and skip ALL nodes with that value. Connect prev to first different node. If no duplicates, just move prev forward. Return dummy.next.

---

### Card 19: Key Insight
**Q:** What's the ONE key insight that makes this problem easier?

**A:** **Sorted list means all duplicates are adjacent!**
- Don't need HashSet to track seen values
- Can detect duplicates by comparing neighbors
- Can skip all duplicates in one continuous sequence

---

### Card 20: Remember This!
**Q:** If you remember only ONE thing for the interview?

**A:** **"Skip ALL, not just duplicates!"**
When you find a duplicate value, remove ALL nodes with that value, not just the extra ones. This is what makes it different from the easier version.