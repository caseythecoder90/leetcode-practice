# Reverse Linked List II (LeetCode 92) - Flashcards

## Core Concepts

### Card 1: Problem Pattern
**Q:** What pattern does "Reverse Linked List II" belong to?

**A:** **Linked List - Partial In-Place Reversal**
- Reverses only a sublist from position `left` to `right`
- Requires reconnection of three sections
- Uses dummy node to handle edge cases

---

### Card 2: Why Dummy Node?
**Q:** Why do we need a dummy node in this problem?

**A:** **To handle the edge case when left = 1**
- When reversing from the head, we need a node before the head
- Without dummy: `prev = null` → `prev.next = newHead` causes NullPointerException
- With dummy: `prev = dummy` → always safe to do `prev.next = newHead`

---

### Card 3: Three Critical Pointers
**Q:** What are the three most important pointers to track?

**A:**
1. **beforeReverse** - Node BEFORE the reversal section
2. **startReverse** - First node to be reversed (becomes tail)
3. **prev** - New head of the reversed section (after reversal completes)

---

### Card 4: Iteration Count
**Q:** How many iterations needed to reverse from position `left` to `right`?

**A:** **`right - left + 1` iterations**

Example: reverse positions 2 to 4
- Nodes: 2, 3, 4 (3 nodes)
- Count: 4 - 2 + 1 = 3 ✓

Loop: `for (int i = 0; i <= right - left; i++)`

---

### Card 5: Reconnection
**Q:** What are the two reconnection steps after reversing?

**A:**
1. `beforeReverse.next = prev` - Connect to new head of reversed section
2. `startReverse.next = curr` - Connect tail of reversed section to rest

**Remember:** TWO connections, not one!

---

## Common Mistakes

### Card 6: Loop Variable Error
**Q:** What's wrong with this loop?
```java
for (int i = left; left <= right; left++)
```

**A:** **Infinite loop!**
- You're using `left` as both loop variable AND incrementing it
- `left` keeps growing and never exceeds `right`
- **Fix:** Use separate counter: `for (int i = 0; i <= right - left; i++)`

---

### Card 7: Null Pointer Bug
**Q:** When does NullPointerException occur without a dummy node?

**A:** **When left = 1**
```java
ListNode prev = null;
// Navigate to position left-1
for (int i = 1; i < left; i++) {  // Doesn't execute when left = 1
    prev = curr;
}
// prev is still null!
prev.next = newHead;  // ❌ NullPointerException
```

---

### Card 8: Off-by-One Error
**Q:** What's wrong with this navigation loop?
```java
for (int i = 1; i <= left; i++) {
    prev = curr;
    curr = curr.next;
}
```

**A:** **Goes one position too far!**
- Should stop at position `left - 1`
- **Fix:** `for (int i = 1; i < left; i++)`

---

## Algorithm Steps

### Card 9: Step-by-Step Algorithm
**Q:** What are the 5 main steps of the algorithm?

**A:**
1. **Create dummy node** and point it to head
2. **Navigate** to node before position `left`
3. **Save** `startReverse` pointer (first node to reverse)
4. **Reverse** the sublist using standard reversal
5. **Reconnect** both sides of the reversed section

---

### Card 10: Standard Reversal Template
**Q:** What's the standard linked list reversal code?

**A:**
```java
ListNode prev = null;
ListNode curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev;  // New head
```

---

## Complexity

### Card 11: Time Complexity
**Q:** What's the time complexity and why?

**A:** **O(n)**
- Navigate to position: O(left)
- Reverse sublist: O(right - left)
- Total worst case: O(n) when reversing entire list
- Each node visited at most once

---

### Card 12: Space Complexity
**Q:** What's the space complexity and why?

**A:** **O(1)**
- Only using fixed number of pointers
- No recursion (no stack space)
- No additional data structures
- Constant extra space regardless of input size

---

## Edge Cases

### Card 13: Edge Case 1
**Q:** What happens when `left = 1`?

**A:** **Reversing from the head**
```
Input: [1,2,3,4,5], left=1, right=3
Output: [3,2,1,4,5]
```
Handled by dummy node approach.

---

### Card 14: Edge Case 2
**Q:** What happens when `left = right`?

**A:** **No reversal needed**
```
Input: [1,2,3], left=2, right=2
Output: [1,2,3]  (unchanged)
```
Usually handled by early return check.

---

### Card 15: Edge Case 3
**Q:** What happens when `right = n` (end of list)?

**A:** **Reversing to the tail**
```
Input: [1,2,3,4,5], left=3, right=5
Output: [1,2,5,4,3]
```
After reversal, `curr = null`, which is correct for `startReverse.next = curr`

---

### Card 16: Edge Case 4
**Q:** What happens when reversing entire list?

**A:** **left = 1, right = n**
```
Input: [1,2,3,4,5], left=1, right=5
Output: [5,4,3,2,1]
```
Works exactly like reversing entire list.

---

## Visual Understanding

### Card 17: Before and After Reversal
**Q:** Visualize reversing positions 2-4 in [1,2,3,4,5]

**A:**
```
Before:
1 → 2 → 3 → 4 → 5 → null
    ↑___________↑
    left      right

After:
1 → 4 → 3 → 2 → 5 → null
    ↑___________↑
  reversed section
```

---

### Card 18: Three Sections
**Q:** What are the three sections of the list?

**A:**
1. **Before section** (1 to left-1): Unchanged
2. **Reverse section** (left to right): Gets reversed
3. **After section** (right+1 to end): Unchanged

Only the middle section changes!

---

## Code Patterns

### Card 19: Navigate to Position Template
**Q:** Code to navigate to position `pos` (before it)?

**A:**
```java
ListNode curr = dummy;  // Start from dummy
for (int i = 1; i < pos; i++) {
    curr = curr.next;
}
// curr is now at position (pos - 1)
```

---

### Card 20: Reverse N Nodes Template
**Q:** Code to reverse exactly `n` nodes starting from `start`?

**A:**
```java
ListNode prev = null;
ListNode curr = start;
for (int i = 0; i < n; i++) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
// prev = new head
// curr = first node after reversed section
```

---

## Interview Strategy

### Card 21: How to Approach in Interview
**Q:** What's the step-by-step interview strategy?

**A:**
1. **Clarify:** Can left = right? Is list guaranteed valid?
2. **Recall:** Review basic linked list reversal first
3. **Identify:** Three sections problem with reconnection
4. **Plan:** Explain dummy node approach
5. **Code:** Write solution with clear variable names
6. **Test:** Walk through with example and edge cases

---

### Card 22: What to Say First
**Q:** What should you say when you see this problem?

**A:**
"This is a linked list partial reversal problem. The key challenges are:
1. Handling the edge case of reversing from the head
2. Reversing only the specified nodes
3. Properly reconnecting all three sections

I'll use a dummy node approach to simplify the edge cases."

---

## Related Problems

### Card 23: Prerequisite Problem
**Q:** What problem should you master before this one?

**A:** **206. Reverse Linked List (Easy)**
- Same reversal technique
- Simpler version without position tracking
- Master this first!

---

### Card 24: Related Hard Problem
**Q:** What's the harder version of this problem?

**A:** **25. Reverse Nodes in k-Group (Hard)**
- Reverse in groups of k nodes
- Uses same reversal logic repeatedly
- More complex reconnection logic

---

## Quick Recall

### Card 25: Complete Solution Template
**Q:** Write the complete solution from memory.

**A:**
```java
public ListNode reverseBetween(ListNode head, int left, int right) {
    if (head == null || left == right) return head;
    
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    ListNode beforeReverse = dummy;
    for (int i = 1; i < left; i++) {
        beforeReverse = beforeReverse.next;
    }
    
    ListNode startReverse = beforeReverse.next;
    ListNode prev = null;
    ListNode curr = startReverse;
    
    for (int i = 0; i <= right - left; i++) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    
    beforeReverse.next = prev;
    startReverse.next = curr;
    
    return dummy.next;
}
```

---

### Card 26: Memory Trick
**Q:** What's an easy way to remember the algorithm?

**A:** **"Navigate → Save → Reverse → Reconnect"**

1. **Navigate** to position before `left`
2. **Save** the starting point
3. **Reverse** the sublist
4. **Reconnect** both sides

---

### Card 27: Debugging Checklist
**Q:** What should you check if your solution fails?

**A:**
1. ✓ Did you use a dummy node?
2. ✓ Is iteration count `right - left + 1`?
3. ✓ Did you reconnect BOTH sides?
4. ✓ Are you using a separate loop counter?
5. ✓ Did you test with `left = 1`?
6. ✓ Did you test with `right = n`?

---

### Card 28: One-Line Summary
**Q:** Summarize the algorithm in one sentence.

**A:**
"Use a dummy node to navigate to position left-1, reverse the sublist from left to right using standard reversal, then reconnect the before and after sections."

---

## Pro Tips

### Card 29: Drawing Strategy
**Q:** How should you approach this problem on a whiteboard?

**A:**
1. **Draw the example list** with positions labeled
2. **Mark the three sections** (before, reverse, after)
3. **Draw pointers** at each step
4. **Show the reconnection** visually
5. Visual understanding → correct code!

---

### Card 30: Testing Strategy
**Q:** What's the minimum set of test cases to verify correctness?

**A:**
1. `left = 1` (reverse from head)
2. `right = n` (reverse to tail)
3. `left = right` (no reversal)
4. Middle positions (normal case)
5. Two-node list

If these pass, your solution is likely correct!

---

## Bonus: Advanced

### Card 31: Without Dummy Node
**Q:** How would you solve this without a dummy node?

**A:**
```java
if (left == 1) {
    // Special case: reverse from head
    return reverseFirstN(head, right);
} else {
    // Normal case: use prev pointer
    // More complex but possible
}
```
Not recommended - dummy node is cleaner!

---

### Card 32: Recursive Approach
**Q:** Can this be solved recursively?

**A:** **Yes, but not recommended**
- Uses O(n) stack space vs O(1) for iterative
- More complex to implement correctly
- Iterative solution is preferred in interviews

---