# Remove Duplicates from Sorted Array II - Study Guide

## Problem Pattern: Fast/Slow Pointers for In-Place Array Modification

**Core Insight**: This is a variation of the classic "Remove Duplicates" pattern where we allow up to 2 occurrences instead of just 1.

## Key Algorithm Concepts

### The Two Pointer Strategy
- **Slow pointer**: Tracks the position where the next valid element should be placed
- **Fast pointer**: Scans through the array to find valid elements
- **Validation rule**: An element is valid if we haven't seen it more than twice

### Critical Insight
Instead of counting occurrences explicitly, we can use a clever comparison:
- `nums[fast]` is valid if `nums[fast] != nums[slow-2]`
- This works because if `slow-2` exists and equals `nums[fast]`, it means we already have 2 occurrences of this value

## Optimal Solution

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) return nums.length;
    
    int slow = 2; // Start from index 2 since first 2 elements are always valid
    
    for (int fast = 2; fast < nums.length; fast++) {
        // nums[fast] is valid if it's different from nums[slow-2]
        // This ensures we have at most 2 duplicates
        if (nums[fast] != nums[slow - 2]) {
            nums[slow] = nums[fast];
            slow++;
        }
    }
    
    return slow;
}
```

## Why This Works: Step-by-Step Logic

### 1. Base Case Handling
```java
if (nums.length <= 2) return nums.length;
```
Arrays with ≤2 elements automatically satisfy the constraint.

### 2. Initialize Pointers
```java
int slow = 2;
```
- First 2 elements are always valid (at most 2 duplicates allowed)
- `slow` points to where the next valid element should go

### 3. The Core Logic
```java
if (nums[fast] != nums[slow - 2]) {
    nums[slow] = nums[fast];
    slow++;
}
```
**Why `nums[slow-2]`?**
- If `nums[fast] == nums[slow-2]`, then we already have `nums[slow-2]` and `nums[slow-1]` as the same value
- Adding `nums[fast]` would create 3 duplicates
- If `nums[fast] != nums[slow-2]`, it's safe to add `nums[fast]`

## Example Walkthrough

**Input**: `[1,1,1,2,2,3]`

| Step | fast | slow | nums[fast] | nums[slow-2] | Valid? | Array State | slow value |
|------|------|------|------------|--------------|--------|-------------|------------|
| Init | 2    | 2    | 1          | nums[0]=1    | No     | [1,1,1,2,2,3] | 2 |
| 1    | 3    | 2    | 2          | nums[0]=1    | Yes    | [1,1,2,2,2,3] | 3 |
| 2    | 4    | 3    | 2          | nums[1]=1    | Yes    | [1,1,2,2,2,3] | 4 |
| 3    | 5    | 4    | 3          | nums[2]=2    | Yes    | [1,1,2,2,3,3] | 5 |

**Result**: `k = 5`, `nums = [1,1,2,2,3,_]`

## Key Insights

### 1. Pattern Recognition
- **Problem Type**: In-place array modification with constraints
- **Signal Words**: "sorted array", "in-place", "at most K occurrences"
- **Solution Pattern**: Fast/slow pointers

### 2. Generalization
This technique works for "at most K duplicates":
```java
if (nums[fast] != nums[slow - k]) {
    nums[slow] = nums[fast];
    slow++;
}
```

### 3. Why It's Optimal
- **Time**: O(n) - single pass through array
- **Space**: O(1) - only using two pointers
- **In-place**: Modifies input array directly

## Common Pitfalls

### 1. **Starting pointers incorrectly**
```java
// Wrong
int slow = 0;

// Correct
int slow = k; // For "at most k duplicates"
```

### 2. **Comparing with wrong index**
```java
// Wrong - only handles at most 1 duplicate
if (nums[fast] != nums[slow - 1])

// Correct - handles at most 2 duplicates  
if (nums[fast] != nums[slow - 2])
```

### 3. **Forgetting base case**
```java
// Always handle small arrays first
if (nums.length <= k) return nums.length;
```

## Practice Problems Using Same Pattern

1. **Remove Duplicates from Sorted Array I** (LeetCode 26) - at most 1 duplicate
2. **Remove Element** (LeetCode 27) - remove specific value
3. **Move Zeroes** (LeetCode 283) - move unwanted elements to end

## Time & Space Complexity

- **Time Complexity**: O(n) - single pass through the array
- **Space Complexity**: O(1) - only using two integer pointers
- **Comparison with your solution**:
    - Your solution: O(n log n) time, O(n) space
    - Optimal solution: O(n) time, O(1) space

## Interview Tips

1. **Start with the pattern**: "This looks like a fast/slow pointer problem"
2. **Identify the constraint**: "At most 2 duplicates allowed"
3. **Derive the condition**: "Compare with element 2 positions back"
4. **Handle edge cases**: "Arrays with ≤2 elements are automatically valid"
5. **Trace through example**: Always verify with given test cases

## Code Variations

### More Readable Version
```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) return nums.length;
    
    int writeIndex = 2; // Where to write next valid element
    
    for (int readIndex = 2; readIndex < nums.length; readIndex++) {
        // Can we include nums[readIndex]?
        // Yes, if it's different from the element 2 positions back in our result
        if (nums[readIndex] != nums[writeIndex - 2]) {
            nums[writeIndex] = nums[readIndex];
            writeIndex++;
        }
    }
    
    return writeIndex;
}
```

### Generalized for K Duplicates
```java
public int removeDuplicates(int[] nums, int k) {
    if (nums.length <= k) return nums.length;
    
    int slow = k;
    
    for (int fast = k; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow - k]) {
            nums[slow] = nums[fast];
            slow++;
        }
    }
    
    return slow;
}
```