# Remove Duplicates from Sorted List II - Study Guide

## Problem Overview
- **LeetCode #82** (Medium)
- **Pattern**: Linked List manipulation with two pointers
- **Key Concept**: Remove ALL nodes that have duplicate values (not just the extra duplicates)

## Problem Statement
Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.

## Key Insights

### 1. **Understanding "Remove ALL Duplicates"**
This is different from "Remove Duplicates from Sorted List I" (LC #83):
- **LC #83**: Keep one occurrence of each value `[1,1,2,3,3] → [1,2,3]`
- **LC #82**: Remove ALL occurrences if duplicated `[1,1,2,3,3] → [2]`

### 2. **Why We Need a Dummy Node**
The head itself might be deleted! Consider:
- Input: `[1,1,2,3]`
- Output: `[2,3]` (head is gone!)
- Without dummy node, we'd need complex logic to track new head

### 3. **The Sorted Property**
Since the list is sorted, all duplicates are adjacent. This means:
- We can detect duplicates by comparing consecutive nodes
- Once we find a duplicate value, we know ALL occurrences are together

## Solution Approach

### Core Algorithm
1. **Use dummy node** to handle edge case where head is deleted
2. **Maintain two pointers**:
    - `prev`: Last valid node (no duplicates)
    - `curr`: Current node being examined
3. **For each node**, check if it has duplicates ahead
4. **If duplicates found**, skip ALL nodes with that value
5. **If no duplicates**, move prev pointer forward

### Visual Walkthrough

```
Example: [1,2,3,3,4,4,5]

Step 1: dummy -> [1,2,3,3,4,4,5]
        prev=dummy, curr=1
        No duplicates for 1, move forward

Step 2: dummy -> [1,2,3,3,4,4,5]
        prev=1, curr=2
        No duplicates for 2, move forward

Step 3: dummy -> [1,2,3,3,4,4,5]
        prev=2, curr=3
        Found duplicate! 3 appears twice
        Skip all 3s: curr moves to 4

Step 4: dummy -> [1,2,_,_,4,4,5]
        prev=2, curr=4
        prev.next = 4 (removing all 3s)
        Found duplicate! 4 appears twice
        Skip all 4s: curr moves to 5

Step 5: dummy -> [1,2,_,_,_,_,5]
        prev=2, curr=5
        prev.next = 5 (removing all 4s)
        No duplicates for 5, move forward

Result: [1,2,5]
```

## Common Mistakes and How to Fix Them

### Mistake 1: Only Removing Extra Duplicates
```java
// ❌ Wrong - keeps one occurrence
if (curr.val == curr.next.val) {
    curr.next = curr.next.next;
}

// ✅ Correct - removes ALL occurrences
if (curr.next != null && curr.val == curr.next.val) {
    int dupVal = curr.val;
    while (curr != null && curr.val == dupVal) {
        curr = curr.next;
    }
    prev.next = curr;
}
```

### Mistake 2: Null Pointer Issues
```java
// ❌ Wrong - accessing property before null check
while (curr.val == next.val && next != null)

// ✅ Correct - null check first
while (next != null && curr.val == next.val)
```

### Mistake 3: Forgetting to Track Previous Node
```java
// ❌ Wrong - no way to reconnect list after deletion
ListNode curr = head;
while (curr != null) {
    // How do we connect to nodes after deletion?
}

// ✅ Correct - maintain prev pointer
ListNode prev = dummy;
ListNode curr = head;
```

## Edge Cases to Consider

1. **Empty list**: `[]` → `[]`
2. **Single node**: `[1]` → `[1]`
3. **All duplicates**: `[1,1,1]` → `[]`
4. **No duplicates**: `[1,2,3]` → `[1,2,3]`
5. **Head is duplicate**: `[1,1,2]` → `[2]`
6. **Tail is duplicate**: `[1,2,2]` → `[1]`
7. **Multiple groups of duplicates**: `[1,1,2,3,3,4,4,5]` → `[2,5]`

## Time and Space Complexity

- **Time Complexity**: O(n)
    - Single pass through the list
    - Each node is visited at most twice (once by curr, once when skipping duplicates)

- **Space Complexity**: O(1)
    - Only using a few pointer variables
    - No additional data structures needed

## Related Problems

### Similar Patterns
1. **LC #83 - Remove Duplicates from Sorted List** (Easy)
    - Keep one occurrence instead of removing all
    - Simpler version of this problem

2. **LC #203 - Remove Linked List Elements** (Easy)
    - Remove all nodes with a specific value
    - Similar technique with dummy node

3. **LC #237 - Delete Node in a Linked List** (Easy)
    - Delete a specific node
    - Different approach but good practice

### Advanced Problems
4. **LC #19 - Remove Nth Node From End of List** (Medium)
    - Two-pointer technique with different spacing

5. **LC #86 - Partition List** (Medium)
    - Rearrange nodes based on value
    - Multiple dummy nodes technique

## Interview Tips

### How to Explain Your Approach
1. **Clarify the problem**: "So we remove ALL nodes that have duplicates, not just the extra ones, right?"
2. **Mention edge cases**: "The head might be deleted, so I'll use a dummy node"
3. **Explain the algorithm**: "I'll use two pointers - prev to track the last valid node, and curr to examine nodes"
4. **Walk through an example**: Use a simple case like `[1,1,2]`
5. **Analyze complexity**: "Single pass, so O(n) time, and only pointers, so O(1) space"

### What Interviewers Look For
- Understanding the difference between removing duplicates vs removing all duplicated values
- Proper handling of edge cases (empty list, all duplicates)
- Clean pointer manipulation without memory leaks
- Recognizing the need for a dummy node

## Practice Problems Progression

1. **Start with**: LC #83 (Remove Duplicates - keep one)
2. **Then try**: LC #82 (This problem - remove all)
3. **Challenge**: LC #203 (Remove specific value)
4. **Advanced**: LC #86 (Partition list)
5. **Expert**: LC #25 (Reverse Nodes in k-Group)

## Key Takeaways

1. **Dummy node pattern** is crucial for problems where head might be deleted
2. **Two-pointer technique** helps maintain connection while deleting nodes
3. **Sorted property** means duplicates are adjacent - use this!
4. **Check ahead** to detect duplicates before moving pointers
5. **Skip ALL occurrences** when duplicates are found, not just extras