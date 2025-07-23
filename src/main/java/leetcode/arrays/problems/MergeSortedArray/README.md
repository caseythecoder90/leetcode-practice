# README.md

# Merge Sorted Array (LeetCode 88)

**Pattern**: Arrays & Two Pointers | **Difficulty**: Easy | **Type**: In-Place Array Merge

## Problem Statement

Given two integer arrays `nums1` and `nums2`, sorted in **non-decreasing order**, and two integers `m` and `n`, representing the number of elements in `nums1` and `nums2` respectively.

**Merge** `nums1` and `nums2` into a single array sorted in **non-decreasing order**.

**Important Constraints:**
- The final sorted array should **not be returned** by the function, but instead be **stored inside the array `nums1`**
- To accommodate this, `nums1` has a length of `m + n`, where the first `m` elements denote the elements that should be merged, and the last `n` elements are set to `0` and should be ignored
- `nums2` has a length of `n`

### Examples

**Example 1:**
```
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
The result of the merge is [1,2,2,3,5,6].
```

**Example 2:**
```
Input: nums1 = [1], m = 1, nums2 = [], n = 0
Output: [1]
Explanation: The arrays we are merging are [1] and [].
The result of the merge is [1].
```

**Example 3:**
```
Input: nums1 = [0], m = 0, nums2 = [1], n = 1
Output: [1]
Explanation: The arrays we are merging are [] and [1].
The result of the merge is [1].
```

## Solution Approaches

### Approach 1: Right-to-Left Merge (Optimal) 🎯
- **Algorithm**: Merge from the end of both arrays backward
- **Time**: O(m+n) | **Space**: O(1)
- **Key Insight**: Use the extra space at the end of `nums1` to avoid overwriting
- **Pros**: Optimal space complexity, clean implementation
- **Cons**: Slightly counterintuitive direction

### Approach 2: Extra Space Merge (Standard)
- **Algorithm**: Classic merge step from merge sort
- **Time**: O(m+n) | **Space**: O(m+n)
- **Key Insight**: Standard two-pointer merge with temporary storage
- **Pros**: Easy to understand and implement
- **Cons**: Uses extra space when problem asks for in-place

### Approach 3: Left-to-Right with Shifting (Suboptimal)
- **Algorithm**: Insert elements and shift others
- **Time**: O(m×n) | **Space**: O(1)
- **Key Insight**: Insert each element in correct position
- **Pros**: In-place solution
- **Cons**: Poor time complexity due to shifting operations

## Key Pattern Recognition

This problem teaches several important concepts:

### 1. **Reverse Iteration for In-Place Operations**
When you have extra space at the end of an array, consider working backward to avoid overwriting elements you still need.

### 2. **Two-Pointer Technique Variations**
- **Converging pointers**: Move toward each other
- **Same direction**: Both move left to right
- **Reverse direction**: Both move right to left (this problem!)

### 3. **Space-Time Trade-offs**
Different approaches show the trade-off between space complexity and implementation simplicity.

### 4. **Leveraging Problem Constraints**
The fact that `nums1` has built-in extra space is not accidental—it's a hint toward the optimal solution.

## Edge Cases to Consider

1. **Empty arrays**: One or both arrays have no elements (`m=0` or `n=0`)
2. **No overlap**: All elements in one array are smaller/larger than the other
3. **Identical elements**: Arrays contain duplicate values
4. **Single elements**: Arrays with only one element each
5. **All zeros**: Edge case with the padding zeros

## Common Mistakes

### 1. **Wrong Direction**
```java
// ❌ WRONG - This overwrites nums1 data we still need
int i = 0, j = 0, k = 0;
while (i < m && j < n) {
    // This will overwrite nums1[0], nums1[1], etc.
}
```

### 2. **Boundary Errors**
```java
// ❌ Missing the >= condition
while (i > 0 && j > 0) // Should be >= 0

// ❌ Wrong initialization
int k = m + n; // Should be m + n - 1
```

### 3. **Incomplete Cleanup**
```java
// ❌ Forgetting to handle remaining elements
// Must handle case where one array is exhausted first
while (j >= 0) {
    nums1[k--] = nums2[j--];
}
```

## Related Problems

This problem is excellent preparation for:

1. **21. Merge Two Sorted Lists** - Same concept for linked lists
2. **23. Merge k Sorted Lists** - Extension to multiple arrays/lists
3. **977. Squares of a Sorted Array** - Similar reverse merge pattern
4. **283. Move Zeroes** - In-place array manipulation with similar pointer technique

## Complexity Analysis

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Right-to-Left | O(m+n) | O(1) | ✅ Optimal for interviews |
| Extra Space | O(m+n) | O(m+n) | Good to mention first |
| Insertion Sort | O(m×n) | O(1) | Educational only |

## Interview Strategy

1. **Start with understanding**: Clarify the problem constraints and examples
2. **Mention simpler approach**: Explain the extra space solution first
3. **Optimize**: "Can we do this in-place?" → Lead to reverse merge
4. **Code the optimal solution**: Right-to-left merge
5. **Test with examples**: Walk through the algorithm step by step
6. **Discuss edge cases**: Empty arrays, duplicates, etc.

---

# StudyGuide.md

# Merge Sorted Array - Detailed Study Guide

## Understanding the Problem Deep Dive

### What Makes This Problem Tricky?

The challenge isn't the merging itself—any developer familiar with merge sort can merge two sorted arrays. The real challenge is doing it **in-place** without extra space, while avoiding overwriting elements we still need.

### Problem Setup Visualization

```
Before merge:
nums1 = [1, 2, 3, 0, 0, 0]  (m = 3, actual data: [1,2,3])
         ^     ^  ^        ^
         |     |  |        |
    actual data  |        end of array
                 |
            padding zeros

nums2 = [2, 5, 6]  (n = 3)

Goal: nums1 = [1, 2, 2, 3, 5, 6]
```

### The Key Insight

The "aha moment" is realizing that **direction matters**:
- **Left-to-right**: Overwrites data we need → requires extra space
- **Right-to-left**: Uses the padding zeros first → perfect for in-place!

## Algorithm Walkthrough: Right-to-Left Merge

### Mental Model

Think of it as **"always place the largest available element in the rightmost available position"**.

### Step-by-Step Execution

Let's trace through: `nums1 = [1,2,3,0,0,0], m=3, nums2 = [2,5,6], n=3`

**Initial Setup:**
```
nums1 = [1, 2, 3, 0, 0, 0]
         0  1  2  3  4  5   (indices)
              i        k

nums2 = [2, 5, 6]
         0  1  2   (indices)
              j

Where:
i = 2 (last actual element in nums1)
j = 2 (last element in nums2)  
k = 5 (last position in nums1)
```

**Iteration 1: Compare nums1[2]=3 vs nums2[2]=6**
```
6 > 3, so place 6 at position k=5

nums1 = [1, 2, 3, 0, 0, 6]
              i     k

nums2 = [2, 5, 6]
           j

New state: i=2, j=1, k=4
```

**Iteration 2: Compare nums1[2]=3 vs nums2[1]=5**
```
5 > 3, so place 5 at position k=4

nums1 = [1, 2, 3, 0, 5, 6]
              i  k

nums2 = [2, 5, 6]
        j

New state: i=2, j=0, k=3
```

**Iteration 3: Compare nums1[2]=3 vs nums2[0]=2**
```
3 > 2, so place 3 at position k=3

nums1 = [1, 2, 3, 3, 5, 6]
           i  k

nums2 = [2, 5, 6]
        j

New state: i=1, j=0, k=2
```

**Iteration 4: Compare nums1[1]=2 vs nums2[0]=2**
```
Equal! Let's choose nums2[0]=2 (either works)

nums1 = [1, 2, 2, 3, 5, 6]
        i  k

nums2 = [2, 5, 6]
        j becomes -1

New state: i=1, j=-1, k=1
```

**Cleanup Phase:**
Since `j < 0`, nums2 is exhausted. The remaining elements in nums1 (just `nums1[0]=1`) are already in their correct positions!

**Final Result:** `nums1 = [1, 2, 2, 3, 5, 6]` ✅

### Why This Algorithm Works

1. **No overwrites**: We only write to positions that are either padding zeros or already processed
2. **Largest-first ordering**: By placing largest elements first, we maintain sorted order
3. **Natural termination**: When one array is exhausted, we handle remaining elements appropriately
4. **Space efficiency**: Uses only the existing space in nums1

## Detailed Code Breakdown

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    // Initialize three pointers
    int i = m - 1;      // Last actual element in nums1
    int j = n - 1;      // Last element in nums2  
    int k = m + n - 1;  // Last position in merged array
    
    // Main merge loop
    while (i >= 0 && j >= 0) {
        if (nums1[i] >= nums2[j]) {
            nums1[k] = nums1[i];  // Place nums1 element
            i--;                  // Move nums1 pointer left
        } else {
            nums1[k] = nums2[j];  // Place nums2 element
            j--;                  // Move nums2 pointer left
        }
        k--;                     // Move result pointer left
    }
    
    // Handle remaining elements from nums2
    while (j >= 0) {
        nums1[k] = nums2[j];
        j--;
        k--;
    }
    
    // Note: No need to handle remaining i elements!
    // They're already in correct positions
}
```

### Code Analysis

**Why no cleanup for `i`?**
If nums1 still has unprocessed elements when nums2 is exhausted, those elements are already in their correct positions in the final array. This is unique to the right-to-left approach!

**Pointer movement logic:**
- Always decrement `k` (we're filling positions from right to left)
- Decrement `i` only when we use a nums1 element
- Decrement `j` only when we use a nums2 element

## Alternative Approaches Analysis

### Approach 1: Extra Space (Standard Merge)

```java
public void mergeWithExtraSpace(int[] nums1, int m, int[] nums2, int n) {
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0;
    
    // Standard merge logic
    while (i < m && j < n) {
        if (nums1[i] <= nums2[j]) {
            result.add(nums1[i++]);
        } else {
            result.add(nums2[j++]);
        }
    }
    
    // Add remaining elements
    while (i < m) result.add(nums1[i++]);
    while (j < n) result.add(nums2[j++]);
    
    // Copy back to nums1
    for (int k = 0; k < result.size(); k++) {
        nums1[k] = result.get(k);
    }
}
```

**Pros:** Easy to understand, classic merge sort approach
**Cons:** O(m+n) extra space

### Approach 2: Left-to-Right with Shifting

```java
public void mergeWithShifting(int[] nums1, int m, int[] nums2, int n) {
    for (int j = 0; j < n; j++) {
        // Find insertion position
        int pos = findInsertPos(nums1, m + j, nums2[j]);
        
        // Shift elements right
        for (int k = m + j; k > pos; k--) {
            nums1[k] = nums1[k - 1];
        }
        
        // Insert element
        nums1[pos] = nums2[j];
    }
}
```

**Pros:** In-place solution
**Cons:** O(m×n) time complexity due to shifting

## Common Pitfalls and How to Avoid Them

### 1. **Direction Confusion**
**Mistake:** Starting from the beginning
**Fix:** Always start from the end when you have extra space at the end

### 2. **Boundary Errors**
**Mistake:** Using `>` instead of `>=` in while conditions
**Fix:** Remember that array indices go from 0 to length-1

### 3. **Incomplete Edge Case Handling**
**Mistake:** Not handling remaining elements
**Fix:** Always have cleanup loops for both arrays

### 4. **Index Initialization**
**Mistake:** `int k = m + n;`
**Fix:** `int k = m + n - 1;` (arrays are 0-indexed)

## Edge Cases Deep Dive

### Case 1: Empty nums2 (n = 0)
```
nums1 = [1, 2, 3], m = 3, nums2 = [], n = 0
Result: [1, 2, 3] (no change needed)
```
The while loop never executes, perfect!

### Case 2: Empty nums1 (m = 0)
```
nums1 = [0, 0, 0], m = 0, nums2 = [1, 2, 3], n = 3
Result: [1, 2, 3]
```
Only the cleanup loop for j executes.

### Case 3: No Overlap
```
nums1 = [4, 5, 6, 0, 0, 0], m = 3, nums2 = [1, 2, 3], n = 3
Result: [1, 2, 3, 4, 5, 6]
```
nums2 elements are all smaller, so they get placed first.

### Case 4: Perfect Interleaving
```
nums1 = [1, 3, 5, 0, 0, 0], m = 3, nums2 = [2, 4, 6], n = 3
Result: [1, 2, 3, 4, 5, 6]
```
Alternating selection from both arrays.

## Time and Space Complexity Analysis

### Optimal Solution (Right-to-Left)
- **Time Complexity:** O(m + n)
   - Each element is visited exactly once
   - No element is processed more than once
- **Space Complexity:** O(1)
   - Only uses a constant number of pointer variables
   - No additional data structures

### Comparison with Alternatives
| Algorithm | Time | Space | Best For |
|-----------|------|-------|----------|
| Right-to-Left | O(m+n) | O(1) | Interviews, Production |
| Extra Space | O(m+n) | O(m+n) | Learning, Prototyping |
| Shifting | O(m×n) | O(1) | Educational Only |

## Practice Variations

Once you master the basic problem, try these variations:

### 1. **Three Arrays Merge**
Merge three sorted arrays into the first one.

### 2. **Merge with Duplicates Count**
Return how many duplicate elements were found during merge.

### 3. **Reverse Order Merge**
Merge into descending order instead of ascending.

### 4. **K-Way Merge**
Merge k sorted arrays (leads to heap-based solutions).

## Interview Performance Tips

### 1. **Start with Clarifying Questions**
- "Should I modify nums1 in-place?"
- "What if one array is empty?"
- "Can there be duplicate elements?"

### 2. **Explain Your Thought Process**
- "I could use extra space, but let me think about in-place..."
- "Since nums1 has extra space at the end, what if I work backward?"

### 3. **Code with Clear Variable Names**
```java
// Good variable names
int nums1Pointer = m - 1;
int nums2Pointer = n - 1;
int mergePosition = m + n - 1;

// vs unclear names
int i = m - 1;
int j = n - 1;
int k = m + n - 1;
```

### 4. **Test Your Solution**
Walk through at least one example step by step, showing how the pointers move.

### 5. **Discuss Trade-offs**
Mention the extra space approach first, then optimize to show problem-solving skills.

## Key Takeaways

1. **Direction matters in in-place algorithms** - working backward can avoid overwrites
2. **Leverage problem constraints** - the extra space in nums1 is a hint
3. **Two-pointer technique is versatile** - works in multiple directions
4. **Pattern recognition** - this reverse merge pattern appears in many problems
5. **Space-time trade-offs** - sometimes O(1) space requires more complex logic

## Related Patterns to Study Next

1. **Move Zeroes** - Similar in-place array manipulation
2. **Remove Duplicates from Sorted Array** - Two-pointer in-place technique
3. **Merge Intervals** - Different type of merging problem
4. **Container With Most Water** - Two-pointer optimization pattern

Remember: The beauty of this problem is in recognizing that **working backward solves the overwrite problem elegantly**. Once you see this insight, the implementation becomes straightforward!