# Non-overlapping Intervals (LeetCode 435)

**Pattern**: Intervals | **Difficulty**: Medium | **Type**: Greedy Activity Selection

## Problem Statement

Given an array of intervals `intervals` where `intervals[i] = [starti, endi]`, return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

### Examples

**Example 1:**
```
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
```

**Example 2:**
```
Input: intervals = [[1,2],[1,2],[1,2]]
Output: 2
Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.
```

**Example 3:**
```
Input: intervals = [[1,2],[2,3]]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
```

### Constraints
- `1 <= intervals.length <= 10^5`
- `intervals[i].length == 2`
- `-5 * 10^4 <= starti < endi <= 5 * 10^4`

## Approach: Greedy Activity Selection

This is a classic **activity selection problem**. The key insight is that to minimize removals, we need to maximize the number of non-overlapping intervals we can keep.

### Strategy
1. **Sort by end time** - This allows us to greedily select intervals that end earliest
2. **Greedy selection** - Always pick the interval that ends earliest among available choices
3. **Count kept intervals** - The answer is total intervals minus kept intervals

### Why Sort by End Time?
When we choose an interval that ends earliest, we leave the maximum amount of "space" for future intervals. This greedy choice leads to the optimal solution.

### Algorithm Steps
1. Sort intervals by end time: `(a, b) -> a[1] - b[1]`
2. Initialize count of kept intervals = 1 (always keep first after sorting)
3. Track the end time of the last kept interval
4. For each subsequent interval:
   - If it starts after the last kept interval ends: keep it
   - Otherwise: skip it (overlaps with previously kept interval)
5. Return total intervals - kept intervals

## Complexity Analysis

- **Time Complexity**: O(n log n) - dominated by sorting
- **Space Complexity**: O(1) - only using constant extra space

## Implementation Notes

### Key Insight
Instead of thinking "which intervals to remove", think "which intervals to keep". The greedy strategy for keeping maximum non-overlapping intervals is well-established.

### Edge Cases to Consider
- Single interval: return 0
- No overlapping intervals: return 0  
- All intervals identical: return length - 1
- Adjacent intervals (touching but not overlapping): return 0

### Alternative Approaches
1. **Sort by start time**: More complex logic, need to track multiple possible choices
2. **Dynamic Programming**: Overkill for this problem, O(n²) time complexity
3. **Interval tree**: Unnecessary complexity for this specific problem

## Visual Example

```
Input: [[1,2],[2,3],[3,4],[1,3]]

After sorting by end time: [[1,2],[2,3],[1,3],[3,4]]

Step by step:
1. Keep [1,2] (first interval) - lastEnd = 2
2. [2,3] starts at 2 >= 2, so keep it - lastEnd = 3  
3. [1,3] starts at 1 < 3, so skip (overlaps)
4. [3,4] starts at 3 >= 3, so keep it - lastEnd = 4

Kept: 3 intervals → Remove: 4 - 3 = 1 interval
```

## Related Problems
- **452. Minimum Number of Arrows to Burst Balloons** - Same greedy pattern
- **56. Merge Intervals** - Different approach (sort by start time)
- **253. Meeting Rooms II** - Similar but counts resources needed

## Interview Tips
1. **Recognize the pattern**: "Maximize non-overlapping" = Activity Selection
2. **Explain the greedy choice**: "End earliest to leave most room"
3. **Consider edge cases**: Empty, single interval, all same, no overlaps
4. **Alternative framing**: This is equivalent to "maximum non-overlapping intervals"