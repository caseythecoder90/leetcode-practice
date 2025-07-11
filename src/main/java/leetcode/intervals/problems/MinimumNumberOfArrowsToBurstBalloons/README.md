# Minimum Number of Arrows to Burst Balloons (LeetCode 452)

**Pattern**: Intervals | **Difficulty**: Medium | **Type**: Point Coverage / Greedy

## Problem Statement

There are some spherical balloons taped onto a flat wall that represents the XY-plane. The balloons are represented as a 2D integer array `points` where `points[i] = [xstart, xend]` denotes a balloon whose horizontal diameter stretches from `xstart` to `xend`. You do not know the exact y-coordinates of the balloons.

Arrows can be shot up directly vertically (in the positive y-direction) from different points along the x-axis. A balloon with `xstart` and `xend` is burst by an arrow shot at `x` if `xstart <= x <= xend`. There is no limit to the number of balloons an arrow can burst.

Return the minimum number of arrows that must be shot to burst all balloons.

### Examples

**Example 1:**
```
Input: points = [[10,16],[2,8],[1,6],[7,12]]
Output: 2
Explanation: The balloons can be burst by 2 arrows:
- Shoot an arrow at x = 6, bursting balloons [2,8] and [1,6].
- Shoot an arrow at x = 11, bursting balloons [10,16] and [7,12].
```

**Example 2:**
```
Input: points = [[1,2],[3,4],[5,6],[7,8]]
Output: 4
Explanation: One arrow needs to be shot for each balloon for a total of 4 arrows.
```

**Example 3:**
```
Input: points = [[1,2],[2,3],[3,4],[4,5]]
Output: 2
Explanation: The balloons can be burst by 2 arrows:
- Shoot an arrow at x = 2, bursting balloons [1,2] and [2,3].
- Shoot an arrow at x = 4, bursting balloons [3,4] and [4,5].
```

### Constraints
- `1 <= points.length <= 10^5`
- `points[i].length == 2`
- `-2^31 <= xstart < xend <= 2^31 - 1`

## Approach: Greedy Point Coverage

This is a **point coverage problem** - we want to find the minimum number of points that can "cover" (intersect with) all given intervals.

### Key Insight
To minimize arrows, we need to find groups of overlapping balloons and shoot one arrow through each group. The optimal strategy is to shoot each arrow at the position that can hit the maximum number of remaining balloons.

### Strategy
1. **Sort by end position** - This allows us to greedily place arrows optimally
2. **Greedy arrow placement** - Always shoot the arrow at the end of the earliest-ending balloon
3. **Group processing** - One arrow per group of overlapping balloons

### Why Sort by End Position?
When we shoot an arrow at the end position of the earliest-ending balloon, we:
- Guarantee hitting that balloon
- Maximize the chance of hitting other overlapping balloons
- Leave maximum flexibility for future arrow placements

### Algorithm Steps
1. Handle edge case: if no balloons, return 0
2. Sort balloons by end position: `(a, b) -> Integer.compare(a[1], b[1])`
3. Shoot first arrow at end of first balloon
4. For each subsequent balloon:
   - If balloon starts after current arrow position: need new arrow
   - Otherwise: current arrow can hit this balloon too
5. Return total number of arrows used

## Complexity Analysis

- **Time Complexity**: O(n log n) - dominated by sorting
- **Space Complexity**: O(1) - only using constant extra space

## Visual Example

```
Input: [[10,16],[2,8],[1,6],[7,12]]

After sorting by end position: [[1,6],[2,8],[7,12],[10,16]]

Step by step:
1. Shoot arrow at position 6 (end of [1,6])
   - Hits [1,6]: 1 <= 6 <= 6 ✓
   - Hits [2,8]: 2 <= 6 <= 8 ✓
   
2. Check [7,12]: 7 > 6, so need new arrow
   Shoot arrow at position 12 (end of [7,12])
   - Hits [7,12]: 7 <= 12 <= 12 ✓
   - Hits [10,16]: 10 <= 12 <= 16 ✓

Result: 2 arrows needed
```

## Relationship to Other Problems

This problem is essentially the same as:
- **Non-overlapping Intervals (435)** - Same greedy pattern
- **Activity Selection** - Classic greedy algorithm
- **Interval Scheduling** - Maximize non-overlapping vs minimize coverage points

### Key Difference from Non-overlapping Intervals
- **Non-overlapping**: Count maximum intervals we can select
- **Arrows**: Count minimum points needed to cover all intervals
- Both use same greedy strategy: sort by end time!

## Implementation Notes

### Integer Overflow Consideration
The problem constraints mention values up to 2^31 - 1, so we should use `Integer.compare()` instead of subtraction in our comparator to avoid overflow.

### Edge Cases to Consider
- Empty input: return 0
- Single balloon: return 1
- No overlapping balloons: return length (one arrow per balloon)
- All balloons overlap: return 1
- Adjacent balloons (touching at endpoints): can be covered by single arrow

### Alternative Approaches
1. **Interval intersection**: Find all maximal groups of overlapping intervals
2. **Sweep line**: Process events to track active balloons
3. **Dynamic programming**: Overkill for this problem

## Interview Tips
1. **Recognize the pattern**: "Minimum points to cover intervals" = Point Coverage
2. **Connect to known problems**: Same as activity selection problem
3. **Explain the greedy choice**: "Shoot at end to maximize coverage"
4. **Handle integer overflow**: Use `Integer.compare()` for large values
5. **Trace through example**: Show how arrow placement works

## Related Problems
- **435. Non-overlapping Intervals** - Identical greedy strategy
- **56. Merge Intervals** - Different approach (merge overlapping)
- **57. Insert Interval** - Insertion into sorted intervals
- **253. Meeting Rooms II** - Similar interval processing