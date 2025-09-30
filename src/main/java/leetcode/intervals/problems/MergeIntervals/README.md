# Merge Intervals

**Difficulty**: Medium
**LeetCode Problem**: [56. Merge Intervals](https://leetcode.com/problems/merge-intervals/)

## Problem Description

Given an array of `intervals` where `intervals[i] = [starti, endi]`, merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

### Examples

**Example 1:**
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
```

**Example 2:**
```
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
```

### Constraints
- `1 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 10^4`

## Approach

### Key Insight
Two intervals `[a, b]` and `[c, d]` overlap if: `a <= d && c <= b`

However, by **sorting intervals by start time first**, we simplify the overlap check to just: `c <= b` (since we know `a <= c` after sorting).

### Algorithm Steps

1. **Sort** intervals by start time
2. **Initialize** result list with first interval
3. **Iterate** through remaining intervals:
   - If current interval overlaps with last merged interval: **merge** by updating end time
   - Otherwise: **add** current interval to result
4. **Return** merged intervals

### Why Sorting Works
- After sorting by start time, we only need to compare with the **last** merged interval
- No need to backtrack or compare with earlier intervals
- Linear scan becomes possible after O(n log n) sort

## Complexity Analysis

- **Time Complexity**: O(n log n)
  - Sorting: O(n log n)
  - Merging: O(n)
  - Overall: O(n log n)

- **Space Complexity**: O(n)
  - Result list: O(n) in worst case (no overlaps)
  - Sorting: O(log n) to O(n) depending on implementation

## Edge Cases

1. **Single interval**: Return as-is
2. **No overlaps**: All intervals remain separate
3. **Complete overlap**: All intervals merge into one
4. **Adjacent intervals** (e.g., [1,4] and [4,5]): Should merge (touching counts as overlap)
5. **Identical intervals**: Merge into one
6. **Unsorted input**: Must sort first

## Pattern Recognition

This problem uses the **Sort + Merge** pattern:
- Sort by start time
- Linear scan to merge overlapping intervals
- Compare only with last merged interval

Similar problems:
- Insert Interval
- Meeting Rooms II
- Non-overlapping Intervals