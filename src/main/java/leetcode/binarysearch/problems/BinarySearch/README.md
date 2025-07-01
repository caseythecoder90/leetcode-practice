# 704. Binary Search

## Problem Description

Given an array of integers `nums` which is sorted in ascending order, and an integer `target`, write a function to search `target` in `nums`. If `target` exists, then return its index. Otherwise, return `-1`.

You must write an algorithm with `O(log n)` runtime complexity.

## Examples

**Example 1:**
```
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4
```

**Example 2:**
```
Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 < nums[i], target < 10^4`
- All the integers in `nums` are **unique**
- `nums` is sorted in ascending order

## Approach Analysis

### Pattern Recognition

This is the **fundamental binary search problem** - the template that all other binary search problems are based on:
- **Sorted array** ✓
- **Find exact element** ✓
- **Return index or -1** ✓
- **O(log n) requirement** ✓

### Why Binary Search?

**Linear Search**: Check each element → O(n) time
**Binary Search**: Eliminate half each comparison → O(log n) time

**Example**: For array of 1000 elements:
- Linear search: up to 1000 comparisons
- Binary search: at most 10 comparisons (log₂(1000) ≈ 10)

### Core Algorithm

The fundamental insight: In a sorted array, comparing with the middle element tells us which half contains our target (if it exists).

```
Array: [-1, 0, 3, 5, 9, 12], target = 9

Step 1: Check middle (index 2, value 3)
        3 < 9, so search right half [5, 9, 12]

Step 2: Check middle of right half (index 4, value 9)  
        9 == 9, found it! Return index 4
```

## Template: Classic Binary Search

This is the **foundational template** for all binary search problems:

```java
public int search(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;  // Found target
        } else if (nums[mid] < target) {
            left = mid + 1;  // Search right half
        } else {
            right = mid - 1; // Search left half
        }
    }
    
    return -1; // Target not found
}
```

### Template Breakdown

1. **Initialization**: `left = 0, right = nums.length - 1`
    - Search the entire array initially

2. **Loop Condition**: `left <= right`
    - Continue while there are elements to check
    - When `left > right`, we've exhausted all possibilities

3. **Mid Calculation**: `left + (right - left) / 2`
    - Avoids integer overflow
    - Always gives us a valid index between left and right

4. **Three Cases**:
    - **Found**: `nums[mid] == target` → return mid
    - **Too small**: `nums[mid] < target` → search right half
    - **Too large**: `nums[mid] > target` → search left half

5. **Not Found**: Return -1 when loop exits

## Step-by-Step Example

**Input**: nums = [-1,0,3,5,9,12], target = 9

### Visual Representation:
```
Index:  0  1  2  3  4  5
Array: [-1, 0, 3, 5, 9, 12]
```

### Iteration 1:
- **left = 0, right = 5**
- **mid = 2** (value = 3)
- **3 < 9** → search right half
- **Update**: left = 3, right = 5

### Iteration 2:
- **left = 3, right = 5**
- **mid = 4** (value = 9)
- **9 == 9** → found!
- **Return**: 4

## Edge Cases Analysis

### Case 1: Target not in array
```java
nums = [-1,0,3,5,9,12], target = 2

Iteration 1: left=0, right=5, mid=2 (value=3)
             3 > 2 → search left half
             Update: left=0, right=1

Iteration 2: left=0, right=1, mid=0 (value=-1)  
             -1 < 2 → search right half
             Update: left=1, right=1

Iteration 3: left=1, right=1, mid=1 (value=0)
             0 < 2 → search right half  
             Update: left=2, right=1

Now left > right, exit loop and return -1
```

### Case 2: Single element array
```java
nums = [5], target = 5
- left=0, right=0, mid=0
- nums[0] == 5 → return 0

nums = [5], target = 3  
- left=0, right=0, mid=0
- nums[0] > 3 → right = -1
- left > right → return -1
```

### Case 3: Target at boundaries
```java
nums = [1,2,3,4,5], target = 1 (first element)
- First iteration finds it at mid=2? No.
- Eventually narrows down to find at index 0

nums = [1,2,3,4,5], target = 5 (last element)  
- Similar process, finds at index 4
```

### Case 4: All elements same
```java
nums = [3,3,3,3,3], target = 3
- Any comparison will find a match
- Returns some valid index (could be any of them)
```

## Common Mistakes & Solutions

### Mistake 1: Wrong Loop Condition
```java
// WRONG: while (left < right)
// This might miss the case where target is at the last remaining element

// CORRECT: while (left <= right)  
// Checks all elements including the last one
```

### Mistake 2: Integer Overflow
```java
// WRONG: int mid = (left + right) / 2;
// Can overflow when left + right > Integer.MAX_VALUE

// CORRECT: int mid = left + (right - left) / 2;
// Never overflows
```

### Mistake 3: Infinite Loop
```java
// WRONG: 
if (nums[mid] < target) left = mid;      // No progress when left == mid
if (nums[mid] > target) right = mid;     // No progress when right == mid

// CORRECT:
if (nums[mid] < target) left = mid + 1;  // Always makes progress  
if (nums[mid] > target) right = mid - 1; // Always makes progress
```

### Mistake 4: Wrong Index Updates
```java
// Remember: we've already checked nums[mid]
// No need to include it in the next search

// When nums[mid] < target: target must be in [mid+1, right]
// When nums[mid] > target: target must be in [left, mid-1]
```

## Complexity Analysis

### Time Complexity: O(log n)
- Each iteration eliminates half the remaining elements
- After k iterations: n → n/2 → n/4 → ... → n/2^k
- When n/2^k = 1, we're done → k = log₂(n)

### Space Complexity: O(1)
- Only using constant extra space (left, right, mid variables)
- Iterative approach doesn't use recursion stack

## Variations & Extensions

### Binary Search Variants (for future learning):
1. **Find first occurrence** of target (when duplicates exist)
2. **Find last occurrence** of target
3. **Find insertion position** (Search Insert Position - Problem 35)
4. **Search in rotated sorted array**

### Template Comparison:
```java
// Template 1: Find exact match (this problem)
while (left <= right) { /* return mid or -1 */ }

// Template 2: Find minimum valid (future problems)  
while (left < right) { /* return left */ }

// Template 3: Find maximum valid (future problems)
while (left < right) { /* return left with ceiling mid */ }
```

## Implementation Tips

### 1. Always Test Edge Cases
- Empty array (if allowed)
- Single element
- Target at first/last position
- Target not in array

### 2. Trace Through Small Examples
- Use arrays of size 1-4 to verify logic
- Draw out the iterations on paper

### 3. Verify Loop Invariant
- The target (if it exists) is always in range [left, right]
- This should be true before and after each iteration

### 4. Mid Calculation Variations
```java
// Standard (preferred)
int mid = left + (right - left) / 2;

// Alternative (also correct)  
int mid = (left + right) >>> 1;  // Unsigned right shift handles overflow

// Avoid
int mid = (left + right) / 2;    // Can overflow
```

## Related Problems

**Practice Order** (easiest to hardest):
1. **704. Binary Search** (this problem) - Master the template
2. **35. Search Insert Position** - Handle "not found" case
3. **374. Guess Number Higher or Lower** - API-based binary search
4. **278. First Bad Version** - Similar to guess number
5. **33. Search in Rotated Sorted Array** - Handle modifications

## Key Takeaways

1. **Template Mastery**: This is THE fundamental binary search template
2. **Overflow Prevention**: Always use `left + (right - left) / 2`
3. **Progress Guarantee**: Always use `mid + 1` or `mid - 1` for updates
4. **Efficiency**: O(log n) is exponentially better than O(n)
5. **Foundation**: Master this before moving to advanced binary search problems

This problem is your binary search foundation - once you can implement this perfectly, all other binary search problems become variations of this template!