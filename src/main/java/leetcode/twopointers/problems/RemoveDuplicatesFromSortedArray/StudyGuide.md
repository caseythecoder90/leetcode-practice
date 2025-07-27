# LeetCode #26: Remove Duplicates from Sorted Array - Complete Study Guide

## Problem Analysis

**What makes this problem tricky:**
- Must modify array in-place (no extra space)
- Must return the count of unique elements
- Array is already sorted (key insight!)
- Relative order must be preserved

**Why your TreeSet solution works but isn't optimal:**
- TreeSet: O(n log n) time, O(n) space
- Two pointers: O(n) time, O(1) space

## The Key Insight: Exploit the Sorted Property

Since the array is **sorted**, all duplicates are adjacent! This means:
- `[1,1,1,2,2,3]` - duplicates are grouped together
- We only need to compare `nums[i]` with `nums[i-1]`
- No need to check against all previous elements

## Two Pointers Approach: Fast/Slow Pattern

### Mental Model: "Compacting" the Array

Think of it as **compacting** unique elements to the front, not "removing" duplicates.

```
Original: [0,0,1,1,1,2,2,3,3,4]
          ↑                    
          slow (next position for unique element)

After:    [0,1,2,3,4,_,_,_,_,_]
              ↑
              slow points here (k=5)
```

### Pointer Roles

**Slow pointer (`writeIndex`):**
- Points to the next position where we should place a unique element
- Only advances when we find a new unique element
- Final value is the count of unique elements

**Fast pointer (`readIndex`):**
- Scans through the entire array
- Always advances each iteration
- Finds elements to potentially keep

## Step-by-Step Implementation

### Implementation 1: Intuitive Version

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) return nums.length;
    
    int writeIndex = 1; // Start at 1 since first element is always unique
    
    for (int readIndex = 1; readIndex < nums.length; readIndex++) {
        // If current element is different from previous, it's unique
        if (nums[readIndex] != nums[readIndex - 1]) {
            nums[writeIndex] = nums[readIndex];
            writeIndex++;
        }
    }
    
    return writeIndex; // This is k, the count of unique elements
}
```

### Implementation 2: Comparing with Slow Pointer

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) return nums.length;
    
    int slow = 0; // Points to last unique element placed
    
    for (int fast = 1; fast < nums.length; fast++) {
        // If different from last unique element, place it
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    
    return slow + 1; // +1 because slow is index, we want count
}
```

## Trace Through Example

**Input:** `nums = [0,0,1,1,1,2,2,3,3,4]`

```
Step 0: [0,0,1,1,1,2,2,3,3,4]
        slow=0, fast=1
        nums[fast]=0, nums[slow]=0 → same, skip

Step 1: [0,0,1,1,1,2,2,3,3,4]
        slow=0, fast=2
        nums[fast]=1, nums[slow]=0 → different!
        slow++, nums[1]=1 → [0,1,1,1,1,2,2,3,3,4]
        slow=1

Step 2: [0,1,1,1,1,2,2,3,3,4]
        slow=1, fast=3
        nums[fast]=1, nums[slow]=1 → same, skip

Step 3: [0,1,1,1,1,2,2,3,3,4]
        slow=1, fast=4
        nums[fast]=1, nums[slow]=1 → same, skip

Step 4: [0,1,1,1,1,2,2,3,3,4]
        slow=1, fast=5
        nums[fast]=2, nums[slow]=1 → different!
        slow++, nums[2]=2 → [0,1,2,1,1,2,2,3,3,4]
        slow=2

... continuing this pattern ...

Final: [0,1,2,3,4,_,_,_,_,_]
       slow=4, return slow+1 = 5
```

## Why This Works: Array Invariants

At any point during execution:
1. **nums[0...slow]** contains all unique elements seen so far
2. **nums[slow+1...fast-1]** contains duplicates we've skipped
3. **nums[fast...n-1]** contains unprocessed elements

**Key insight:** We only overwrite positions that either:
- Contain duplicates (which we want to eliminate), or
- Have already been copied to an earlier position

## Common Mistakes and How to Avoid Them

### Mistake 1: Wrong Starting Position
```java
// ❌ Wrong - starts checking from index 0
for (int i = 0; i < nums.length; i++) {
    if (nums[i] != nums[i-1]) // Index out of bounds!
```

**Fix:** Start from index 1, since first element is always unique.

### Mistake 2: Forgetting Edge Cases
```java
// ❌ Missing edge case handling
public int removeDuplicates(int[] nums) {
    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++) {
        // What if nums.length == 0 or 1?
```

**Fix:** Handle edge cases first.

### Mistake 3: Confusing Index vs Count
```java
// ❌ Returning wrong value
return slow; // This is an index!

// ✅ Correct
return slow + 1; // Convert index to count
```

## Complexity Analysis

**Time Complexity:** O(n)
- Single pass through the array
- Each element examined exactly once

**Space Complexity:** O(1)
- Only using two pointer variables
- Modifying array in-place

## Why Two Pointers is Better Than Your TreeSet Solution

| Approach | Time | Space | In-place? |
|----------|------|-------|-----------|
| TreeSet | O(n log n) | O(n) | No |
| Two Pointers | O(n) | O(1) | Yes |

**TreeSet approach issues:**
1. **Slower:** O(n log n) due to TreeSet operations
2. **Extra space:** O(n) for storing unique elements
3. **Two passes:** One to build set, one to copy back
4. **Overkill:** TreeSet sorts, but array is already sorted!

## Practice Problems Using Same Pattern

After mastering this, try these similar problems:

1. **Remove Element (LC #27)** - Remove specific value instead of duplicates
2. **Move Zeroes (LC #283)** - Move zeros to end using fast/slow pointers
3. **Remove Duplicates II (LC #80)** - Allow at most 2 duplicates

## Advanced Variation: Remove Duplicates II

**Problem:** Allow each unique element to appear at most twice.

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) return nums.length;
    
    int slow = 2; // First two elements are always kept
    
    for (int fast = 2; fast < nums.length; fast++) {
        // Compare with element 2 positions back
        if (nums[fast] != nums[slow - 2]) {
            nums[slow] = nums[fast];
            slow++;
        }
    }
    
    return slow;
}
```

**Key insight:** Compare with `nums[slow-2]` instead of `nums[slow-1]` to allow 2 duplicates.

## Debugging Tips

If your solution isn't working:

1. **Trace by hand** with a simple example like `[1,1,2]`
2. **Check edge cases:** `[]`, `[1]`, `[1,1,1]`
3. **Verify return value:** Are you returning count or index?
4. **Check pointer initialization:** Should slow start at 0 or 1?

## Key Takeaways for Interview

1. **Pattern recognition:** "Remove/modify in-place" → Consider fast/slow pointers
2. **Exploit sorted property:** Duplicates are adjacent
3. **Mental model:** Think "compacting" not "removing"
4. **Invariant:** Slow pointer tracks next position for valid element
5. **Edge cases:** Always handle empty and single-element arrays

## Your Growth Path

Your TreeSet solution shows you understand:
- ✅ The problem requirements
- ✅ How to eliminate duplicates
- ✅ How to maintain order

**Next level:** Recognize that the **sorted property** makes this much simpler. When you see "sorted array + in-place modification," think two pointers first!

**Interview tip:** If you think of TreeSet first, mention it but explain why two pointers is better: "I could use a TreeSet, but since the array is already sorted, two pointers gives us O(n) time and O(1) space instead of O(n log n) time and O(n) space."