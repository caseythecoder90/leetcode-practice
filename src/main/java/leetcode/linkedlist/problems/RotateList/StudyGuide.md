# Rotate List - Comprehensive Study Guide

## Learning Objectives

By the end of this study guide, you should be able to:
1. ✅ Explain why k % n is necessary and when it matters
2. ✅ Implement the two-pointer solution from memory
3. ✅ Identify the new tail position given n and k
4. ✅ Recognize and handle all edge cases
5. ✅ Apply the same pattern to related problems

## Deep Dive: Understanding the Problem

### What Is List Rotation Really?

List rotation is about **rearranging the connection order** of nodes. When we "rotate right by k," we're:
1. **Breaking** the list at a specific position
2. **Moving** a section from the end to the front
3. **Reconnecting** everything in the new order

**Key insight**: We're not moving data, we're changing pointers!

### Visual Understanding

#### Example: [1,2,3,4,5], k=2

**Original connections:**
```
1 → 2 → 3 → 4 → 5 → null
```

**After rotation (conceptual):**
```
Take: [4,5] from end
Move to front: [4,5] + [1,2,3]
Result: 4 → 5 → 1 → 2 → 3 → null
```

**Pointer changes needed:**
```
Before:
1 → 2 → 3 → 4 → 5 → null
        ↑

After:
4 → 5 → 1 → 2 → 3 → null
        ↑           ↑
    break here    reconnect here
```

### Mathematical Foundation

#### Why k % n?

Consider list of length n = 5 and k = 12:
- After 5 rotations, we're back to the original list
- After 10 rotations, we're back to the original list
- After 12 rotations = 2 full cycles + 2 extra rotations
- So k = 12 is equivalent to k = 2

**Formula**: `effective_k = k % n`

**Proof by example**:
```
[1,2,3,4,5], k=7 (where n=5)
k % n = 7 % 5 = 2

Rotating by 7:
1. [5,1,2,3,4]
2. [4,5,1,2,3]
3. [3,4,5,1,2]
4. [2,3,4,5,1]
5. [1,2,3,4,5] ← back to original
6. [5,1,2,3,4]
7. [4,5,1,2,3] ← same as rotating by 2!
```

#### Finding New Tail Position

**Goal**: Find which node becomes the new tail (last node before new head)

**Formula**: `newTailPosition = n - k - 1` (for 0-indexed)

**Why**:
- Original tail is at position `n - 1`
- We're moving k nodes from the end to the front
- The new tail is k positions before the old tail
- `(n - 1) - k = n - k - 1`

**Example**: n=5, k=2
- New tail position = 5 - 2 - 1 = 2
- That's the node at index 2 (node with value 3)

```
[1, 2, 3, 4, 5]
 0  1  2  3  4  ← indices
        ↑
    new tail (position 2)
```

## Algorithm Deep Dive: Two-Pointer Solution

### Why Two Pointers?

**Problem**: We need to find position `n - k - 1` but we don't know positions until we traverse.

**Solution**: Use two pointers with a fixed gap of k positions between them.

**Key insight**: When the first pointer reaches position n-1 (the end), the second pointer is at position (n-1) - k = n - k - 1 (exactly where we need it!).

### Step-by-Step Algorithm Breakdown

#### Step 1: Count Length

**Purpose**: We need n to compute `k % n`

```java
int n = 0;
ListNode curr = head;
while (curr != null) {
    n++;
    curr = curr.next;
}
```

**After**: n = 5, curr = null (fell off the list)

#### Step 2: Normalize k

**Purpose**: Handle k > n cases

```java
k = k % n;
if (k == 0) return head;
```

**Why check k == 0?**
- If k % n == 0, the list returns to its original state
- No point in doing all the pointer manipulation
- Example: [1,2,3], k=6 → 6%3=0 → no change

#### Step 3: Position Last Pointer k Steps Ahead

**Purpose**: Create the k-step gap between pointers

```java
ListNode last = head;
for (int i = 0; i < k; i++) {
    last = last.next;
}
```

**After this** (for k=2):
```
1 → 2 → 3 → 4 → 5 → null
↑       ↑
head   last
```

Gap = 2 positions

#### Step 4: Move Both Pointers Together

**Purpose**: Find the new tail position

```java
curr = head;
while (last.next != null) {
    curr = curr.next;
    last = last.next;
}
```

**Why `last.next != null` not `last != null`?**
- We want last to point to the actual last node (not fall off to null)
- When last is at the last node, curr is at the new tail

**After this**:
```
1 → 2 → 3 → 4 → 5 → null
        ↑       ↑
      curr    last
```

**Key observation**: Gap still = 2 positions!

#### Step 5: Reconnect the Pointers

**Purpose**: Rearrange the list with new head at front

```java
ListNode newHead = curr.next;  // Node 4
last.next = head;              // Node 5 → Node 1
curr.next = null;              // Node 3 → null
return newHead;
```

**Breakdown**:
1. `newHead = curr.next`: Node 4 will be the new first node
2. `last.next = head`: Connect old last (5) to old first (1)
3. `curr.next = null`: Break the link, making node 3 the new last node

**Result**:
```
4 → 5 → 1 → 2 → 3 → null
```

### Why This Algorithm Is Clever

**Traditional approach** (less efficient):
1. Count length: O(n)
2. Find new tail by walking n-k-1 steps: O(n)
3. Walk to old tail: O(n)
4. Reconnect: O(1)
   Total: 3 passes through the list

**Two-pointer approach**:
1. Count length: O(n)
2. Find new tail while reaching old tail: O(n)
3. Reconnect: O(1)
   Total: 2 passes through the list

Both are O(n), but two-pointer is more elegant and does less work!

## Common Mistakes and How to Avoid Them

### Mistake 1: Not Checking Edge Cases First

**Bad code**:
```java
public ListNode rotateRight(ListNode head, int k) {
    int n = 0;
    ListNode curr = head;
    while (curr != null) {  // Crashes if head is null!
        n++;
        curr = curr.next;
    }
    // ...
}
```

**Good code**:
```java
public ListNode rotateRight(ListNode head, int k) {
    // Handle edge cases FIRST
    if (head == null || head.next == null) return head;
    
    // Now safe to proceed
    int n = 0;
    // ...
}
```

**Why it matters**: Edge cases are usually the easiest to handle but the easiest to forget. Handle them first!

### Mistake 2: Forgetting k % n

**Problem scenario**: [1,2,3], k = 1000000

**Bad code**:
```java
ListNode last = head;
for (int i = 0; i < k; i++) {  // Tries to iterate 1 million times!
    last = last.next;            // NullPointerException after 3 steps
}
```

**Good code**:
```java
k = k % n;  // k becomes 1000000 % 3 = 1
if (k == 0) return head;

ListNode last = head;
for (int i = 0; i < k; i++) {  // Only iterates once!
    last = last.next;
}
```

**Lesson**: Always normalize k before using it for iteration!

### Mistake 3: Wrong Loop Termination

**Bad code**:
```java
while (last != null) {
    curr = curr.next;
    last = last.next;  // last becomes null
}
// Now curr went one step too far!
```

**What happens**:
```
Start:  1 → 2 → 3 → 4 → 5 → null
        ↑               ↑
      curr            last

After:  1 → 2 → 3 → 4 → 5 → null
                ↑           ↑
              curr       last (null!)
```

We wanted curr at position 3, but it's at position 4!

**Good code**:
```java
while (last.next != null) {
    curr = curr.next;
    last = last.next;
}
// Now last points to actual last node, curr is one step before
```

**Lesson**: Think carefully about when to stop the loop!

### Mistake 4: Forgetting to Break the Cycle

**Bad code**:
```java
ListNode newHead = curr.next;
last.next = head;
// Missing: curr.next = null;
return newHead;
```

**What happens**:
```
4 → 5 → 1 → 2 → 3 → 4 → 5 → 1 → 2 → 3 → ...
                ↑_______________↑
              Infinite cycle!
```

**Good code**:
```java
ListNode newHead = curr.next;
last.next = head;
curr.next = null;  // Break the cycle!
return newHead;
```

**Lesson**: After reconnecting, always break the old connection to prevent cycles!

### Mistake 5: Off-by-One in Loop

**Bad code**:
```java
for (int i = 0; i <= k; i++) {  // Goes k+1 steps!
    last = last.next;
}
```

**Example**: k=2, this loop runs for i=0, i=1, i=2 (3 times!)

**Good code**:
```java
for (int i = 0; i < k; i++) {  // Goes k steps
    last = last.next;
}
```

**Example**: k=2, this loop runs for i=0, i=1 (2 times!) ✓

**Lesson**: Be careful with loop conditions. `< k` for k iterations, `<= k` for k+1 iterations.

## Alternative Approach: Circular List Method

### Algorithm

Instead of finding the new tail directly, make the list circular then break it:

```java
public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) return head;
    
    // Step 1: Count length and find tail
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
    
    // Step 4: Break circle at new tail
    ListNode newHead = newTail.next;
    newTail.next = null;
    
    return newHead;
}
```

### Pros and Cons

**Pros**:
- Slightly fewer total pointer movements
- Only one pass to find tail (combines counting and finding tail)
- Conceptually simpler (make circle, break circle)

**Cons**:
- Less intuitive (creating a cycle intentionally feels risky)
- Still needs two passes (one to make circular, one to find break point)
- Same O(n) time complexity

**When to use**: Mention in interview to show you know multiple approaches, but stick with two-pointer for implementation.

## Pattern Recognition

### When You'll See This Pattern

**Problem indicators**:
- "rotate list" or "shift list"
- "move last k elements to front"
- "circular shift"
- Any problem involving finding "kth from end" in linked list

**Data structure**: Linked list problems

**Technique**: Two pointers with fixed gap

### Related Problems Using Same Pattern

#### 1. Remove Nth Node From End (LC 19)
**Connection**: Same two-pointer technique with k-step gap

```java
// Position fast k+1 steps ahead
for (int i = 0; i < n + 1; i++) {
    fast = fast.next;
}

// Move both until fast reaches end
while (fast != null) {
    slow = slow.next;
    fast = fast.next;
}

// slow.next is the node to remove
slow.next = slow.next.next;
```

#### 2. Middle of Linked List (LC 876)
**Connection**: Two-pointer with different speeds (fast moves 2x)

```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;  // Move 2x speed
}
// slow is at middle
```

#### 3. Linked List Cycle II (LC 142)
**Connection**: Floyd's cycle detection uses two-pointer with different speeds

### Generalizing the Pattern

**Core concept**: Two pointers with controlled separation

**Variations**:
1. **Fixed gap** (Rotate List, Remove Nth From End): Pointers stay k apart
2. **Speed difference** (Middle, Cycle Detection): Fast moves faster than slow
3. **Converging** (Merge sorted lists): Pointers move based on values

## Performance Analysis

### Time Complexity: O(n)

**Breakdown**:
1. **Count length**: O(n) - visit each node once
2. **Position last pointer**: O(k) ≤ O(n) - k is at most n after modulo
3. **Move both pointers**: O(n-k) ≤ O(n) - traverse remaining nodes
4. **Reconnect**: O(1) - constant pointer updates

**Total**: O(n) + O(n) + O(n) + O(1) = O(n)

**Why not O(3n)?** In Big-O notation, we drop constants, so O(3n) = O(n).

### Space Complexity: O(1)

**What we use**:
- `n`: 1 integer → O(1)
- `k`: 1 integer → O(1)
- `curr`: 1 pointer → O(1)
- `last`: 1 pointer → O(1)
- `newHead`: 1 pointer → O(1)

**Total**: O(1) - constant extra space regardless of input size

**Note**: We're not using any data structures that grow with n (no arrays, no HashMaps, no recursion stack).

### Can We Do Better?

**Time complexity**: No. We must visit each node at least once to:
- Count the length (need full traversal)
- Find the new tail position (need traversal)

O(n) is optimal for this problem.

**Space complexity**: No. O(1) is already optimal (can't use less than constant space).

## Testing Strategy

### Comprehensive Test Suite

```java
public class RotateListTests {
    
    // Edge case 1: Empty list
    @Test
    public void testEmptyList() {
        ListNode head = null;
        ListNode result = rotateRight(head, 5);
        assertNull(result);
    }
    
    // Edge case 2: Single node
    @Test
    public void testSingleNode() {
        ListNode head = new ListNode(1);
        ListNode result = rotateRight(head, 99);
        assertEquals(1, result.val);
        assertNull(result.next);
    }
    
    // Edge case 3: k = 0
    @Test
    public void testNoRotation() {
        ListNode head = createList(new int[]{1,2,3});
        ListNode result = rotateRight(head, 0);
        assertListEquals(new int[]{1,2,3}, result);
    }
    
    // Edge case 4: k = n (full rotation)
    @Test
    public void testFullRotation() {
        ListNode head = createList(new int[]{1,2,3});
        ListNode result = rotateRight(head, 3);
        assertListEquals(new int[]{1,2,3}, result);
    }
    
    // Edge case 5: k > n
    @Test
    public void testLargeK() {
        ListNode head = createList(new int[]{1,2,3});
        ListNode result = rotateRight(head, 7);  // 7 % 3 = 1
        assertListEquals(new int[]{3,1,2}, result);
    }
    
    // Normal case 1: Small rotation
    @Test
    public void testSmallRotation() {
        ListNode head = createList(new int[]{1,2,3,4,5});
        ListNode result = rotateRight(head, 2);
        assertListEquals(new int[]{4,5,1,2,3}, result);
    }
    
    // Normal case 2: Rotate by 1
    @Test
    public void testRotateByOne() {
        ListNode head = createList(new int[]{1,2,3});
        ListNode result = rotateRight(head, 1);
        assertListEquals(new int[]{3,1,2}, result);
    }
    
    // Stress test: Large list
    @Test
    public void testLargeList() {
        int[] arr = new int[1000];
        for (int i = 0; i < 1000; i++) arr[i] = i;
        
        ListNode head = createList(arr);
        ListNode result = rotateRight(head, 50);
        
        // Expected: [950-999] + [0-949]
        int[] expected = new int[1000];
        for (int i = 0; i < 50; i++) expected[i] = 950 + i;
        for (int i = 50; i < 1000; i++) expected[i] = i - 50;
        
        assertListEquals(expected, result);
    }
}
```

### Manual Testing Walkthrough

**Test case**: [1,2,3,4,5], k=2

**Step-by-step verification**:
1. Count: n = 5 ✓
2. Normalize: k = 2 % 5 = 2 ✓
3. Check k==0: No ✓
4. Position last: 1→2→3 (last at 3) ✓
5. Move together: curr at 3, last at 5 ✓
6. Reconnect:
    - newHead = 4 ✓
    - last.next = 1 ✓
    - curr.next = null ✓
7. Result: 4→5→1→2→3→null ✓

## Interview Tips

### Communication Strategy

**Opening statement**:
"I'll solve this using a two-pointer approach. The key insight is that rotating right by k is equivalent to finding the node at position n-k-1 and making it the new tail. I'll handle the edge case where k is greater than n by using modulo."

**While coding**:
- Explain each step as you write it
- Mention edge cases as you handle them
- State complexity as you finish

### Common Interview Follow-ups

**Q1: "What if k could be negative (rotate left)?"**
**A**: I'd convert it to a right rotation: `k = n - (Math.abs(k) % n)`, then use the same algorithm.

**Q2: "Can you optimize the space complexity?"**
**A**: It's already O(1) - we're only using a few pointer variables regardless of input size.

**Q3: "What about time complexity?"**
**A**: We're at O(n) which is optimal since we must visit each node at least once to count the length.

**Q4: "How would you handle a doubly linked list?"**
**A**: Same algorithm, but potentially simpler since we can traverse backwards to find the new tail directly.

**Q5: "What if the list were circular already?"**
**A**: Much simpler! Just find the new head position and return it. No reconnection needed.

## Key Takeaways

### Must Remember

1. **Always normalize k first**: `k = k % n`
2. **Check k == 0 after modulo**: Avoid unnecessary work
3. **Two-pointer gap**: Keep pointers k steps apart
4. **Loop condition**: Use `last.next != null` not `last != null`
5. **Three-step reconnection**: newHead, connect tail, break cycle

### Pattern Recognition

When you see:
- "rotate list" → Think two-pointer with k-step gap
- "kth from end" → Same pattern
- "linked list" + "positions" → Consider two-pointer techniques

### Problem-Solving Approach

1. **Clarify**: k > n? Empty list? Single node?
2. **Plan**: Two-pointer or circular approach?
3. **Edge cases first**: Always handle null, single, k==0
4. **Code incrementally**: Count → normalize → position → move → reconnect
5. **Test**: Walk through with small example

### Your Solution Analysis

You solved this problem correctly! Key strengths:
- ✅ Handled edge cases (null, single node)
- ✅ Used k % n correctly
- ✅ Checked k == 0 after modulo
- ✅ Implemented two-pointer technique properly
- ✅ Correct reconnection logic

This demonstrates strong understanding of:
- Linked list manipulation
- Two-pointer patterns
- Edge case thinking
- Mathematical reasoning (modulo)

Excellent work! This is a solid medium-level solution that would impress in an interview.