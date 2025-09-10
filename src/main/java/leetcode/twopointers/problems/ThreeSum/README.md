# 3Sum (LeetCode 15)

**Difficulty:** Medium  
**Pattern:** Two Pointers  
**Companies:** Amazon, Microsoft, Facebook, Google

## Problem Statement

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must not contain duplicate triplets.

### Examples

**Example 1:**
```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation: 
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
```

**Example 2:**
```
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum up to 0.
```

**Example 3:**
```
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums up to 0.
```

### Constraints
- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

## Approach Analysis

### 1. Optimal: Two Pointers (Recommended)
- **Time Complexity:** O(n²)
- **Space Complexity:** O(1) excluding output
- **Key Insight:** Fix the first element, use two pointers for the remaining two

**Algorithm:**
1. Sort the array to enable two-pointer technique
2. For each element as the first element of triplet:
   - Skip duplicates for the first element
   - Use two pointers (left and right) to find pairs that sum to -nums[i]
   - Move pointers based on current sum vs target

### 2. Hash Set Approach
- **Time Complexity:** O(n²)
- **Space Complexity:** O(n) for the hash set
- **When to use:** When you can't modify the input array (sorting not allowed)

### 3. Brute Force (Learning Only)
- **Time Complexity:** O(n³)
- **Space Complexity:** O(n) for duplicate handling
- **Use case:** Understanding the problem, not for production

## Key Implementation Details

### Critical Two-Pointer Logic
```java
if (sum == 0) {
    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
    
    // Skip duplicates
    while (left < right && nums[left] == nums[left + 1]) left++;
    while (left < right && nums[right] == nums[right - 1]) right--;
    
    // CRITICAL: Move past current values to find NEW combinations
    left++;
    right--;
}
```

**Why the final `left++; right--`?**
- The duplicate-skipping loops move to the boundary of same values
- We need to move PAST these values to find new combinations
- Without this, we'd be stuck at the same positions forever!

### Duplicate Handling Strategies
1. **First element:** Skip if `nums[i] == nums[i-1]`
2. **Left/Right pointers:** Skip consecutive duplicates after finding a valid triplet
3. **Alternative:** Use HashSet to store unique triplets (less efficient)

## Common Mistakes

1. **Wrong pointer movement:** Moving both pointers in the same direction
2. **Starting positions:** Starting right pointer at `i+2` instead of `nums.length-1`
3. **Breaking early:** Breaking when `sum > 0` instead of adjusting pointers
4. **Missing final increment:** Not moving past duplicate values after skipping
5. **Duplicate handling:** Not properly handling duplicates for all three elements

## Pattern Recognition

This problem teaches the fundamental **Two Pointers** pattern:
- **Setup:** Sort the array first
- **Outer loop:** Fix one element
- **Inner logic:** Use two pointers moving towards each other
- **Decision making:** Move pointers based on current sum vs target
- **Optimization:** Skip duplicates to avoid repeated work

## Related Problems
- [Two Sum (1)](../TwoSum/) - Foundation problem
- [Two Sum II (167)](../TwoSumII/) - Direct two-pointer application
- [4Sum (18)](../FourSum/) - Extension of this pattern
- [3Sum Closest (16)](../ThreeSumClosest/) - Variation with closest sum

## Time/Space Analysis Summary

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Two Pointers | O(n²) | O(1) | Optimal, most common in interviews |
| Hash Set | O(n²) | O(n) | Good when sorting not allowed |
| Brute Force | O(n³) | O(n) | Learning only, not practical |

## Interview Tips

1. **Start with brute force** to show understanding
2. **Explain the sorting step** - it's crucial for the two-pointer approach
3. **Handle duplicates carefully** - this is where many candidates fail
4. **Trace through an example** to show the pointer movement logic
5. **Discuss time/space tradeoffs** between different approaches