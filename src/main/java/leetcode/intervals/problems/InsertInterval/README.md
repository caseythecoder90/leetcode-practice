# Insert Interval

**Difficulty**: Medium
**LeetCode Problem**: [57. Insert Interval](https://leetcode.com/problems/insert-interval/)

## Problem Description

You are given an array of non-overlapping intervals `intervals` where `intervals[i] = [starti, endi]` represent the start and the end of the `i`th interval and `intervals` is sorted in ascending order by `starti`. You are also given an interval `newInterval = [start, end]` that represents the start and end of another interval.

Insert `newInterval` into `intervals` such that `intervals` is still sorted in ascending order by `starti` and `intervals` still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return `intervals` after the insertion.

### Examples

**Example 1:**
```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
Explanation: [2,5] overlaps with [1,3], merged to [1,5]
```

**Example 2:**
```
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: [4,8] overlaps with [3,5],[6,7],[8,10], all merged to [3,10]
```

**Example 3:**
```
Input: intervals = [], newInterval = [5,7]
Output: [[5,7]]
```

**Example 4:**
```
Input: intervals = [[1,5]], newInterval = [2,3]
Output: [[1,5]]
Explanation: [2,3] is contained within [1,5]
```

### Constraints
- `0 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 10^5`
- `intervals` is sorted by `starti` in ascending order
- `newInterval.length == 2`
- `0 <= start <= end <= 10^5`

## Approach

### Key Insight
Since `intervals` is **already sorted**, we can process in three phases:
1. Add all intervals that end **before** newInterval starts (no overlap)
2. Merge all intervals that **overlap** with newInterval
3. Add all remaining intervals (after newInterval)

### Algorithm Steps

1. **Phase 1: Add intervals ending before newInterval**
   - While `interval.end < newInterval.start`: add interval to result

2. **Phase 2: Merge overlapping intervals**
   - While `interval.start <= newInterval.end`: merge intervals
   - Update newInterval: `[min(starts), max(ends)]`
   - Add merged newInterval to result

3. **Phase 3: Add remaining intervals**
   - Add all remaining intervals after newInterval

### Overlap Detection
Two intervals `[a,b]` and `[c,d]` overlap if: `a <= d && c <= b`

In our case:
- **No overlap (before)**: `interval.end < newInterval.start`
- **No overlap (after)**: `interval.start > newInterval.end`
- **Overlap**: Neither of the above

## Complexity Analysis

- **Time Complexity**: O(n)
  - Single pass through intervals
  - No sorting needed (already sorted)

- **Space Complexity**: O(n)
  - Result list stores all intervals
  - No extra space beyond output

## Edge Cases

1. **Empty intervals**: Insert newInterval directly
2. **newInterval before all**: Add at beginning
3. **newInterval after all**: Add at end
4. **newInterval completely contained**: Absorbed by existing interval
5. **newInterval contains all**: Single merged interval
6. **Multiple overlaps**: Merge all overlapping intervals
7. **Touching intervals**: Should merge (e.g., [1,3] and [3,5])

## Pattern Recognition

This problem uses the **Three-Phase Interval Insertion** pattern:
1. Add non-overlapping intervals before
2. Merge overlapping intervals in the middle
3. Add non-overlapping intervals after

**Key advantage over Merge Intervals**:
- Already sorted → O(n) instead of O(n log n)
- Single pass algorithm

## Comparison with Merge Intervals

| Aspect | Merge Intervals | Insert Interval |
|--------|----------------|-----------------|
| Input sorted? | No | Yes |
| Need to sort? | Yes | No |
| Time complexity | O(n log n) | O(n) |
| Algorithm | Sort + Merge | Three-phase scan |

## Related Problems

1. **Merge Intervals** (56) - Similar merging logic, but needs sorting
2. **Non-overlapping Intervals** (435) - Remove fewest to eliminate overlaps
3. **Interval List Intersections** (986) - Find overlapping portions of two lists