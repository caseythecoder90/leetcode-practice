# 209. Minimum Size Subarray Sum

## Problem Description
Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a subarray whose sum is greater than or equal to `target`. If there is no such subarray, return 0 instead.

### Examples

**Example 1:**
- Input: `target = 7`, `nums = [2,3,1,2,4,3]`
- Output: `2`
- Explanation: The subarray [4,3] has the minimal length under the problem constraint.

**Example 2:**
- Input: `target = 4`, `nums = [1,4,4]`
- Output: `1`
- Explanation: The subarray [4] has the minimal length.

**Example 3:**
- Input: `target = 11`, `nums = [1,1,1,1,1,1,1,1]`
- Output: `0`
- Explanation: No subarray sums to at least 11.

### Constraints
- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

## Solution Approach

### Pattern: Variable-Size Sliding Window (Shrinkable)

This is a classic shrinkable sliding window problem where we:
1. **Expand** the window until we meet the condition (sum ≥ target)
2. **Shrink** the window while maintaining the condition to find minimum
3. **Track** the minimum window size that satisfies the condition

### Key Insight

Since all numbers are positive, the window sum only increases when expanding and only decreases when shrinking. This monotonic property makes sliding window perfect for this problem.

### Algorithm Steps

1. **Initialize** left pointer at 0, sum at 0, minLength at MAX_VALUE
2. **Expand window** with right pointer:
   - Add nums[right] to sum
3. **While sum ≥ target** (valid window):
   - Update minimum length
   - Shrink by removing nums[left] and incrementing left
4. **Return** minLength (or 0 if no valid window found)

### Why This Works

- We're guaranteed to find the minimum because we shrink as much as possible whenever we have a valid window
- The two-pointer approach ensures we check all possible subarrays in O(n) time
- Each element is added once and removed at most once

## Time & Space Complexity

- **Time Complexity:** O(n)
  - Each element is visited at most twice (once by right, once by left)
  - All operations inside the loop are O(1)

- **Space Complexity:** O(1)
  - Only using a few variables regardless of input size

## Common Variations & Follow-ups

### 1. What if the array contains negative numbers?
- Sliding window wouldn't work directly (sum isn't monotonic)
- Would need different approach (prefix sum + binary search or deque)

### 2. Find the exact subarray, not just length?
- Track start index when updating minimum
- Return nums[start:start+minLength]

### 3. What if we want sum exactly equal to target?
- Different problem! Would use HashMap with prefix sums
- Sliding window works for "at least" due to monotonic property

### 4. Maximum size subarray with sum ≤ target?
- Flip the problem! Expand while sum ≤ target
- Track maximum instead of minimum

## Interview Tips

### 1. Clarifying Questions
- Can the array contain negative numbers? (No, per constraints)
- Can the array be empty? (No, length ≥ 1)
- What if no subarray sums to target? (Return 0)
- Is the array sorted? (Not necessarily)

### 2. Edge Cases to Consider
- Target larger than total array sum → return 0
- Single element ≥ target → return 1
- All elements needed → return array length
- Very large target value

### 3. Common Mistakes
- Forgetting to check if any valid window exists
- Not updating minimum before shrinking
- Off-by-one errors in length calculation
- Using wrong initial value for minimum

### 4. Optimization Discussion
- Can mention O(n log n) binary search approach
- Explain why sliding window is optimal for positive numbers
- Discuss space-time tradeoffs

## Related Problems

### Similar Sliding Window Problems:
- **76. Minimum Window Substring** - Similar shrinking pattern with strings
- **904. Fruit Into Baskets** - Maximum window with constraints
- **1004. Max Consecutive Ones III** - Maximum window with modifications

### Progression:
1. **This problem (209)** - Basic shrinkable window
2. **76. Minimum Window Substring** - Complex shrinkable with pattern
3. **862. Shortest Subarray with Sum at Least K** - With negative numbers (harder)

## Key Takeaways

1. **Shrinkable window pattern** - Expand to satisfy, shrink to optimize
2. **Positive numbers enable sliding window** - Monotonic sum property
3. **Two operations per element maximum** - Guarantees O(n) time
4. **Track minimum while valid** - Not after becoming invalid
5. **Initialize min as MAX_VALUE** - Helps identify "no solution" case