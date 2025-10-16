# LeetCode 92 - Reverse Linked List II

## Problem Classification
- **Difficulty:** Medium
- **Pattern:** Linked List - Partial In-Place Reversal
- **Topics:** Linked List, Pointer Manipulation
- **Companies:** Microsoft, Amazon, Facebook, Apple, Google

## Problem Statement

Given the `head` of a singly linked list and two integers `left` and `right` where `left <= right`, reverse the nodes of the list from position `left` to position `right`, and return the reversed list.

**Note:** Do this in one pass if possible.

### Examples

**Example 1:**
```
Input: head = [1,2,3,4,5], left = 2, right = 4
Output: [1,4,3,2,5]

Visual:
Before: 1 → 2 → 3 → 4 → 5
             ↑_________↑
After:  1 → 4 → 3 → 2 → 5
```

**Example 2:**
```
Input: head = [5], left = 1, right = 1
Output: [5]
```

### Constraints
- The number of nodes in the list is `n`
- `1 <= n <= 500`
- `-500 <= Node.val <= 500`
- `1 <= left <= right <= n`

## Approaches

### Approach 1: Dummy Node + Standard Reversal (Recommended)

**Intuition:** The list has three sections - before, reverse, and after. We reverse the middle section and reconnect all three.

**Key Insight:** Use a dummy node to handle the edge case where `left = 1` (reversing from the head).

**Algorithm:**
1. Create dummy node pointing to head
2. Navigate to node before position `left`
3. Save the starting node (becomes tail after reversal)
4. Reverse the sublist using standard reversal technique
5. Reconnect both sides of the reversed section

**Implementation:**
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

**Complexity:**
- Time: O(n) - single pass through the list
- Space: O(1) - constant extra space

**Pros:**
- ✅ Clean, elegant solution
- ✅ Handles all edge cases uniformly
- ✅ Single pass through the list
- ✅ Interview-friendly (easy to explain and code)

**Cons:**
- ❌ Requires understanding of dummy node pattern

---

### Approach 2: Without Dummy Node

**Intuition:** Handle `left = 1` as a special case, use standard approach for other cases.

**When to use:** If interviewer specifically asks to avoid dummy node.

**Complexity:**
- Time: O(n)
- Space: O(1)

**Pros:**
- ✅ Slightly less memory overhead (one less node)

**Cons:**
- ❌ More complex with special case handling
- ❌ Harder to implement correctly under pressure
- ❌ Not recommended for interviews

---

### Approach 3: Recursive (Not Recommended)

**Complexity:**
- Time: O(n)
- Space: O(n) - recursion stack

**Cons:**
- ❌ Uses O(n) space instead of O(1)
- ❌ More complex to implement correctly
- ❌ No advantage over iterative approach

## Visual Walkthrough

### Example: Reverse positions 2-4 in [1,2,3,4,5]

```
Step 1: Initial state with dummy
dummy → 1 → 2 → 3 → 4 → 5 → null

Step 2: Navigate to position before left
dummy → 1 → 2 → 3 → 4 → 5 → null
        ↑
   beforeReverse

Step 3: Mark starting point of reversal
dummy → 1 → 2 → 3 → 4 → 5 → null
        ↑   ↑
beforeReverse startReverse

Step 4: Reverse the sublist (2 → 3 → 4)
Result: 4 → 3 → 2

Step 5: Reconnect all sections
dummy → 1 → 4 → 3 → 2 → 5 → null
        ↑   ↑         ↑   ↑
beforeReverse |   startReverse |
          (prev)          (curr)

Final: [1, 4, 3, 2, 5]
```

## Key Concepts

### The Three Pointers
1. **beforeReverse** - Node before the reversal section
2. **startReverse** - First node to reverse (becomes tail)
3. **prev** - New head of reversed section after reversal

### The Two Reconnections
```java
beforeReverse.next = prev;    // Connect to new head
startReverse.next = curr;     // Connect to remaining list
```

### Iteration Count Formula
```
Number of iterations = right - left + 1

Example: Reverse positions 2 to 4
Nodes to reverse: 2, 3, 4 (3 nodes)
Calculation: 4 - 2 + 1 = 3 ✓
```

## Common Mistakes

### ❌ Mistake 1: Using loop variable incorrectly
```java
// WRONG - modifies left parameter
for (int i = left; left <= right; left++) { }

// CORRECT
        for (int i = 0; i <= right - left; i++) { }
```

### ❌ Mistake 2: Forgetting dummy node for left = 1
Without dummy, you get NullPointerException when `left = 1`.

### ❌ Mistake 3: Wrong iteration count
```java
// WRONG - only reverses (right - left) nodes
for (int i = left; i < right; i++) { }

// CORRECT - reverses (right - left + 1) nodes
        for (int i = 0; i <= right - left; i++) { }
```

### ❌ Mistake 4: Incomplete reconnection
```java
// WRONG - missing second connection
beforeReverse.next = prev;
// Missing: startReverse.next = curr;
```

## Edge Cases

| Case | Input | Output | Note |
|------|-------|--------|------|
| Reverse from head | `[1,2,3,4,5], left=1, right=3` | `[3,2,1,4,5]` | Dummy node handles this |
| Reverse to tail | `[1,2,3,4,5], left=3, right=5` | `[1,2,5,4,3]` | `curr = null` after reversal |
| Single node | `[1,2,3], left=2, right=2` | `[1,2,3]` | No reversal needed |
| Entire list | `[1,2,3], left=1, right=3` | `[3,2,1]` | Full reversal |
| Two nodes | `[1,2], left=1, right=2` | `[2,1]` | Minimum case |

## Interview Tips

### What to Say
1. **Pattern Recognition:** "This is a partial linked list reversal problem with reconnection."
2. **Approach:** "I'll use a dummy node to simplify edge case handling."
3. **Complexity:** "O(n) time with single pass, O(1) space."

### How to Approach
1. Start by explaining basic linked list reversal
2. Identify the three sections of the list
3. Explain why dummy node helps
4. Walk through one example
5. Code with clear variable names
6. Test edge cases

### Common Follow-ups
- **Q:** Can you do this without dummy node?
    - **A:** Yes, but requires special handling for `left = 1`. Dummy node is cleaner.

- **Q:** What if we need to reverse multiple ranges?
    - **A:** Call this function multiple times, or extend to handle list of ranges.

- **Q:** Can this be done recursively?
    - **A:** Yes, but uses O(n) stack space. Iterative O(1) space is better.

## Related Problems

### Prerequisites
- **206. Reverse Linked List** (Easy) - Master this first!
    - Same reversal technique, simpler problem

### Similar Problems
- **25. Reverse Nodes in k-Group** (Hard)
    - Reverse in groups of k nodes
    - Uses same reversal logic repeatedly

- **24. Swap Nodes in Pairs** (Medium)
    - Special case of k = 2 groups

- **61. Rotate List** (Medium)
    - Involves finding position and reconnecting

### Advanced Applications
- **143. Reorder List** (Medium)
    - Combines finding middle, reversing, and merging

## Study Materials

### Files in This Directory
- **StudyGuide.md** - Detailed walkthrough with visual explanations
- **CheatSheet.md** - Quick reference for patterns and common mistakes
- **Flashcards.md** - Q&A format for quick review
- **Solution.java** - Complete implementation with test cases

### Practice Strategy
1. **Day 1:** Read StudyGuide.md, understand the algorithm
2. **Day 2:** Code solution from memory using CheatSheet.md
3. **Day 3:** Review Flashcards.md for quick recall
4. **Day 4:** Solve related problems (206, 25, 24)
5. **Day 5:** Mock interview practice

## Testing Strategy

### Minimum Test Cases
```java
// 1. Reverse from head
head = [1,2,3,4,5], left=1, right=3  → [3,2,1,4,5]

// 2. Reverse to tail
head = [1,2,3,4,5], left=3, right=5  → [1,2,5,4,3]

// 3. Single node reversal
head = [1,2,3], left=2, right=2  → [1,2,3]

// 4. Middle section
head = [1,2,3,4,5], left=2, right=4  → [1,4,3,2,5]

// 5. Two nodes
head = [1,2], left=1, right=2  → [2,1]
```

## Performance Characteristics

| Aspect | Complexity | Notes |
|--------|------------|-------|
| Time | O(n) | Single pass, visit each node once |
| Space | O(1) | Only constant pointers |
| Best Case | O(left) | When reversing very small range at start |
| Worst Case | O(n) | When reversing entire list |
| In-place | Yes | No extra data structures |

## Summary

**Problem Type:** Linked List Manipulation with Partial Reversal

**Key Challenge:** Correctly identifying and reconnecting the three sections

**Best Approach:** Dummy node + standard reversal (O(n) time, O(1) space)

**Critical Insight:** The dummy node elegantly handles the edge case of reversing from the head

**Interview Difficulty:** Medium (straightforward with practice)

**Success Metric:** Can code correctly in 15-20 minutes including testing

---

## Quick Reference Card

```
Pattern: Navigate → Save → Reverse → Reconnect

Complexity: O(n) time, O(1) space

Key Pointers:
  beforeReverse = node before left
  startReverse = node at left (becomes tail)
  prev = new head after reversal
  
Reconnections:
  beforeReverse.next = prev
  startReverse.next = curr
  
Iterations: right - left + 1 nodes
```