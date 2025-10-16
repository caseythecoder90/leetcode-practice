# Rotate List - Flashcards

## Problem Understanding Cards

### Card 1: Problem Definition
**Q: What does "rotate list to the right by k places" mean?**

**A:** Take the last `k` nodes from the end of the list and move them to the front.

Example: `[1,2,3,4,5]` rotate right by 2
- Last 2 nodes: `[4,5]`
- Result: `[4,5,1,2,3]`

---

### Card 2: Edge Case - k vs n
**Q: What happens when k > n (k is greater than list length)?**

**A:** Use `k = k % n` because:
- Rotating by n positions returns to the original list
- k = 7 with n = 5 is the same as k = 2
- After n rotations, we're back where we started

Example: `[1,2,3]`, k = 7
- n = 3, so k = 7 % 3 = 1
- Only rotate once

---

### Card 3: Critical Edge Case
**Q: When can we immediately return the head without rotation?**

**A:** Three cases:
1. **Empty list**: `head == null`
2. **Single node**: `head.next == null`
3. **No rotation needed**: `k % n == 0`

All three mean the list stays the same!

---

## Algorithm Cards

### Card 4: Two-Pointer Strategy
**Q: How do we find the new tail using two pointers?**

**A:** **Two-pointer separation technique:**
1. Position `last` pointer **k steps ahead**
2. Keep `curr` at head
3. Move both pointers together until `last.next == null`
4. Now `curr` points to the new tail (position n-k-1)

**Why it works**: The distance between pointers stays constant at k.

---

### Card 5: Three-Step Reconnection
**Q: What are the three steps to reconnect the list after finding the new tail?**

**A:**
```java
// Step 1: Identify new head
ListNode newHead = newTail.next;

// Step 2: Connect old tail to old head
oldTail.next = head;

// Step 3: Break at new tail
newTail.next = null;
```

**Memory aid**: "New head, connect tail, break cycle"

---

### Card 6: Algorithm Steps
**Q: What are the 5 main steps in the two-pointer solution?**

**A:**
1. **Count** the length n
2. **Normalize** k with `k = k % n`
3. **Position** last pointer k steps ahead
4. **Move** both pointers until last reaches end
5. **Reconnect** the three pointers

**Memory aid**: "Count, Normalize, Position, Move, Reconnect"

---

## Code Pattern Cards

### Card 7: Counting Length
**Q: How do you count the length of a linked list?**

**A:**
```java
int n = 0;
ListNode curr = head;
while (curr != null) {
    n++;
    curr = curr.next;
}
```

**Note**: After this, `curr` is null, so you'll need to reset it to head!

---

### Card 8: Positioning Pointer k Steps Ahead
**Q: How do you move a pointer exactly k steps forward?**

**A:**
```java
ListNode last = head;
for (int i = 0; i < k; i++) {
    last = last.next;
}
```

**Critical**: Use `i < k` NOT `i <= k` (that's k+1 steps!)

---

### Card 9: Moving Two Pointers Together
**Q: How do you move two pointers together until one reaches the end?**

**A:**
```java
ListNode curr = head;
while (last.next != null) {  // Check NEXT, not last itself!
    curr = curr.next;
    last = last.next;
}
```

**Key**: Check `last.next != null` so we stop when last points to the actual last node.

---

## Edge Case Cards

### Card 10: Why Check k == 0?
**Q: Why do we need `if (k == 0) return head;` after computing `k = k % n`?**

**A:** Because `k % n` might equal 0, meaning:
- k was a multiple of n (like k=6, n=3)
- No rotation is needed
- Saves unnecessary pointer operations

Without this check, we'd do all the work for nothing!

---

### Card 11: Empty List Handling
**Q: What happens if we don't check for empty list at the start?**

**A:** **NullPointerException**!

The very first operation `head.next` or counting length would crash.

**Always check**:
```java
if (head == null || head.next == null) return head;
```

---

### Card 12: Single Node Case
**Q: Why does a single-node list need special handling?**

**A:** With only one node:
- Rotation doesn't change anything
- No pointers to manipulate
- Would cause errors trying to find "new tail"

`head.next == null` catches this efficiently.

---

## Common Mistake Cards

### Card 13: Forgetting Modulo
**Q: What's wrong with this code?**
```java
ListNode last = head;
for (int i = 0; i < k; i++) {
    last = last.next;
}
```

**A:** **NullPointerException** if k > n!

After moving k steps, `last` goes past the end of the list.

**Fix**: Always do `k = k % n` first!

---

### Card 14: Wrong Loop Condition
**Q: What's wrong with `while (last != null)`?**

**A:** You'll go **one step too far**!

```java
// Wrong
while (last != null) {  // last becomes null
    curr = curr.next;
    last = last.next;   // NullPointerException!
}

// Correct
while (last.next != null) {  // Stops when last is at end node
    curr = curr.next;
    last = last.next;
}
```

---

### Card 15: Forgetting to Break Cycle
**Q: What happens if you forget `curr.next = null`?**

**A:** You create a **cycle** in the list!

```java
ListNode newHead = curr.next;
last.next = head;
// Missing: curr.next = null;
// Now the list loops forever!
```

Result: Infinite loop when traversing the list.

---

### Card 16: Off-by-One Error
**Q: Why use `i < k` instead of `i <= k` when positioning?**

**A:**
- `i < k`: Moves exactly k steps (0 to k-1 = k iterations)
- `i <= k`: Moves k+1 steps (0 to k = k+1 iterations)

Example: k=2
- `i < 2`: moves 2 steps (i=0, i=1) ✓
- `i <= 2`: moves 3 steps (i=0, i=1, i=2) ✗

---

## Complexity Cards

### Card 17: Time Complexity
**Q: What's the time complexity and why?**

**A:** **O(n)**

Breakdown:
- Count length: O(n)
- Position last pointer: O(k) = O(n) worst case
- Move both pointers: O(n-k) = O(n)
- Total: O(n) + O(n) + O(n) = O(n)

We traverse the list at most 2-3 times.

---

### Card 18: Space Complexity
**Q: What's the space complexity and why?**

**A:** **O(1)**

We only use:
- A few pointer variables (curr, last, newHead)
- One integer (n, k)
- No data structures that grow with input size

All operations are in-place!

---

## Pattern Recognition Cards

### Card 19: When to Use This Pattern?
**Q: What keywords indicate a "rotate list" pattern?**

**A:**
- "rotate list"
- "shift right/left by k"
- "move last k nodes to front"
- "circular shift"
- Any variation of rotation

**Key insight**: If you see rotation + linked list, think two-pointer technique!

---

### Card 20: Related Problems
**Q: What other problems use the same two-pointer (k steps apart) pattern?**

**A:**
- **Remove Nth Node From End (LC 19)** - Find nth from end
- **Middle of Linked List (LC 876)** - Use fast/slow (2x speed)
- **Linked List Cycle II (LC 142)** - Floyd's cycle detection

**Common thread**: All use pointer separation to find positions!

---

## Visualization Cards

### Card 21: Pointer Positions During Execution
**Q: Trace [1,2,3,4,5], k=2 - where are pointers after each step?**

**A:**
```
After positioning (k=2 steps):
1 → 2 → 3 → 4 → 5 → null
↑       ↑
curr   last

After moving together:
1 → 2 → 3 → 4 → 5 → null
        ↑       ↑
      curr    last

After reconnection:
4 → 5 → 1 → 2 → 3 → null
↑       ↑       ↑
new    old     new
head   head   tail
```

---

### Card 22: Why Two Passes?
**Q: Why do we need two passes through the list?**

**A:**
- **First pass**: Count length to compute `k % n`
- **Second pass**: Find the new tail position

**Why necessary**: We need to know n before we can compute the effective rotation distance!

---

## Testing Cards

### Card 23: Test Cases to Always Check
**Q: What test cases must you verify for any rotate list solution?**

**A:**
1. **Empty**: `head = null, k = 1`
2. **Single**: `[1], k = 5` (tests modulo)
3. **No rotation**: `[1,2,3], k = 3` (k % n = 0)
4. **k > n**: `[1,2], k = 100`
5. **k = 1**: `[1,2,3], k = 1` (simple rotation)
6. **Normal**: `[1,2,3,4,5], k = 2`

---

### Card 24: Debugging Strategy
**Q: Your solution fails - what should you check first?**

**A:** **The checklist**:
1. ✓ Did I normalize k with `k % n`?
2. ✓ Did I check `if (k == 0)` after modulo?
3. ✓ Am I using `last.next != null` in the while loop?
4. ✓ Did I break the cycle with `curr.next = null`?
5. ✓ Are my loop bounds correct (`< k` not `<= k`)?
6. ✓ Did I handle empty and single-node lists?

---

## Advanced Cards

### Card 25: Optimizing Further
**Q: Can we do better than two passes through the list?**

**A:** **Kind of** - the circular approach does ~1.5 passes:
1. One pass to find length AND tail
2. Half pass to find new tail position

But the time complexity is still O(n), so it's not a huge improvement.

**Interview tip**: Mention both approaches, explain trade-offs!

---

### Card 26: Rotate Left vs Right
**Q: How would you modify the solution to rotate LEFT instead of right?**

**A:** **Two options**:

1. Convert to right rotation:
```java
k = n - (k % n);  // Then use same algorithm
```

2. Change new tail position:
```java
// For left rotation by k:
newTailPosition = k - 1;
```

**Memory aid**: Right rotation moves tail forward, left moves it backward.

---

### Card 27: Interview Communication
**Q: What should you say when starting this problem in an interview?**

**A:**
"I'll use a two-pointer approach. First, I'll count the length and normalize k using modulo to handle large k values. Then I'll position two pointers k steps apart and move them together to find the new tail. Finally, I'll reconnect the pointers. This runs in O(n) time and O(1) space."

**Shows**: Pattern recognition, edge case awareness, complexity analysis.

---

### Card 28: Why This Problem Is Medium
**Q: What makes this a "Medium" difficulty problem?**

**A:** **Multiple complexities**:
1. Requires understanding of pointer manipulation
2. Must handle the k % n edge case
3. Three-step reconnection is tricky
4. Off-by-one errors are common
5. Multiple valid approaches

Not just simple traversal - requires careful thinking about positions and reconnection.

---

## Quick Review Card

### Card 29: 30-Second Summary
**Q: Explain the rotate list solution in 30 seconds.**

**A:**
"Count list length n, normalize k using k%n to handle large values. Use two pointers: position one k steps ahead, then move both together until the first reaches the end. Now we've found the new tail. Reconnect: set new head to new tail's next, connect old tail to old head, break cycle at new tail. O(n) time, O(1) space."

---

### Card 30: Most Important Insight
**Q: What's the single most important insight for this problem?**

**A:** **The k % n normalization + checking k == 0**

Without this:
- Can't handle k > n (crashes)
- Waste time on unnecessary full rotations
- Miss a major edge case

This one check saves you from multiple failure modes!