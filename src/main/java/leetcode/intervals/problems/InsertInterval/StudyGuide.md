# Insert Interval - Detailed Study Guide

## Problem Breakdown

### Visual Understanding

```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]

Original intervals (sorted):
[1   3]
        [6   9]

New interval to insert:
  [2     5]

Phase analysis:
[1   3]  ← ends at 3, newInterval starts at 2
           3 >= 2, so OVERLAPS

  [2     5] ← merges with [1,3] to become [1,5]
        [6   9] ← starts at 6, merged interval ends at 5
                  6 > 5, so NO OVERLAP

Result:
[1       5]
          [6   9]

Output: [[1,5],[6,9]]
```

## Three-Phase Algorithm

### Phase Diagram
```
Input intervals: [1,3] [6,9] [12,16]
New interval:           [4,10]

Phase 1: Before (no overlap)
[1,3] ends at 3 < 4 ✓ → Add [1,3]

Phase 2: Overlapping
[6,9]: 6 <= 10 ✓ → Merge [4,10] with [6,9] = [4,10]
Continue checking...
[12,16]: 12 <= 10 ✗ → Stop merging

Phase 3: After (no overlap)
[12,16] starts after merged interval → Add [12,16]

Result: [[1,3], [4,10], [12,16]]
```

## Step-by-Step Execution Trace

### Example: `intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]`

**Setup:**
```
intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]
newInterval = [4,8]
result = []
i = 0
```

**Phase 1: Add intervals ending before newInterval starts**
```
i=0: [1,2]
Check: 2 < 4? YES → Add [1,2]
result = [[1,2]]
i = 1

i=1: [3,5]
Check: 5 < 4? NO → Stop Phase 1
```

**Phase 2: Merge overlapping intervals**
```
Start with newInterval = [4,8]

i=1: [3,5]
Check overlap: 3 <= 8? YES
Merge: start = min(4,3) = 3
       end = max(8,5) = 8
newInterval = [3,8]
i = 2

i=2: [6,7]
Check overlap: 6 <= 8? YES
Merge: start = min(3,6) = 3
       end = max(8,7) = 8
newInterval = [3,8]
i = 3

i=3: [8,10]
Check overlap: 8 <= 8? YES (touching)
Merge: start = min(3,8) = 3
       end = max(8,10) = 10
newInterval = [3,10]
i = 4

i=4: [12,16]
Check overlap: 12 <= 10? NO → Stop Phase 2
Add merged interval [3,10]
result = [[1,2],[3,10]]
```

**Phase 3: Add remaining intervals**
```
i=4: [12,16]
Add [12,16]
result = [[1,2],[3,10],[12,16]]
i = 5

i=5: Done (out of bounds)
```

**Final Result**: `[[1,2],[3,10],[12,16]]`

## Overlap Logic Deep Dive

### Why check `interval.start <= newInterval.end`?

After Phase 1, we know:
- All remaining intervals start at or after `newInterval.start`
- Guaranteed: `interval.start >= newInterval.start`

Overlap condition (general): `a <= d && c <= b`
Where `[a,b]` is interval, `[c,d]` is newInterval

Since `c <= a` (newInterval started earlier or same), we only need:
**`a <= d`** → `interval.start <= newInterval.end`

### Visual Examples:

```
1. Clear overlap:
New:     [4        8]
Current:     [6  7]
           ^^^overlap

6 <= 8 ✓ → Merge

2. Touching (edge case):
New:     [4     8]
Current:        [8  10]
                ^touching

8 <= 8 ✓ → Merge

3. No overlap:
New:     [4     8]
Current:              [12  16]
         no overlap

12 <= 8 ✗ → Don't merge
```

## Merging Strategy

```java
// Expand newInterval to include overlapping interval
newInterval[0] = Math.min(newInterval[0], interval[0]);
newInterval[1] = Math.max(newInterval[1], interval[1]);
```

### Why Math.min and Math.max?

**Math.min for start:**
```
New:         [6     10]
Current: [3      7]
Merged:  [3        10]

min(6,3) = 3
```
Current interval might start before newInterval.

**Math.max for end:**
```
New:     [4     8]
Current:     [6     10]
Merged:  [4        10]

max(8,10) = 10
```
Current interval might extend beyond newInterval.

## Edge Cases Walkthrough

### Case 1: Empty intervals
```
intervals = []
newInterval = [5,7]

Phase 1: Skip (no intervals)
Phase 2: Skip (no intervals to merge)
Phase 3: Skip (no remaining)
Add newInterval: [5,7]

Result: [[5,7]]
```

### Case 2: Insert at beginning
```
intervals = [[3,5],[6,9]]
newInterval = [1,2]

Phase 1:
[3,5]: 5 < 1? NO → Stop

Phase 2:
[3,5]: 3 <= 2? NO → Stop
Add [1,2]

Phase 3:
Add [3,5], [6,9]

Result: [[1,2],[3,5],[6,9]]
```

### Case 3: Insert at end
```
intervals = [[1,2],[3,5]]
newInterval = [6,8]

Phase 1:
[1,2]: 2 < 6 ✓ → Add
[3,5]: 5 < 6 ✓ → Add

Phase 2:
No intervals left → Add [6,8]

Phase 3:
No intervals left

Result: [[1,2],[3,5],[6,8]]
```

### Case 4: Completely contained
```
intervals = [[1,10]]
newInterval = [2,5]

Phase 1:
[1,10]: 10 < 2? NO → Stop

Phase 2:
[1,10]: 1 <= 5? YES
Merge: [min(2,1), max(5,10)] = [1,10]

Phase 3:
No intervals left

Result: [[1,10]]
```

### Case 5: Contains all intervals
```
intervals = [[2,3],[4,5],[6,7]]
newInterval = [1,10]

Phase 1:
[2,3]: 3 < 1? NO → Stop

Phase 2:
[2,3]: 2 <= 10? YES → Merge to [1,10]
[4,5]: 4 <= 10? YES → Merge to [1,10]
[6,7]: 6 <= 10? YES → Merge to [1,10]

Phase 3:
No intervals left

Result: [[1,10]]
```

## Common Mistakes

### Mistake 1: Wrong non-overlap check
```java
// ❌ WRONG
if (interval[1] <= newInterval[0]) {
    // Using <= misses edge case where they touch
}
```
**Fix:** Use strict `<` for "before" check:
```java
if (interval[1] < newInterval[0]) {
    result.add(interval);
}
```

### Mistake 2: Wrong overlap check
```java
// ❌ WRONG
if (interval[0] < newInterval[1]) {
    // Using < misses touching intervals
}
```
**Fix:** Use `<=` to include touching:
```java
if (interval[0] <= newInterval[1]) {
    // Merge logic
}
```

### Mistake 3: Forgetting to add merged interval
```java
// ❌ WRONG
while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
    // Merge but never add newInterval to result!
    i++;
}
// Missing: result.add(newInterval);
```

### Mistake 4: Modifying newInterval without caution
```java
// ❌ WRONG: Direct assignment loses previous values
newInterval[0] = interval[0];  // Should use Math.min
newInterval[1] = interval[1];  // Should use Math.max
```

## Complexity Analysis

### Time Complexity: O(n)
- **Phase 1**: O(k) where k = intervals before newInterval
- **Phase 2**: O(m) where m = overlapping intervals
- **Phase 3**: O(n-k-m) = remaining intervals
- **Total**: O(k + m + n-k-m) = **O(n)**

Each interval visited exactly once.

### Space Complexity: O(n)
- **Result list**: O(n) to store all intervals
- **No extra data structures**: constant extra space
- **Output not counted**: space complexity is **O(1)** if output doesn't count

## Optimization Notes

### Why this is better than "Merge Intervals" approach:
```
Merge Intervals approach:
1. Add newInterval to list: O(1)
2. Sort all intervals: O(n log n)
3. Merge: O(n)
Total: O(n log n)

Insert Interval approach:
1. Three-phase scan: O(n)
Total: O(n)

Speedup: From O(n log n) to O(n)
```

**Key insight**: Leverage the fact that input is already sorted!

## Practice Tips

1. **Draw the timeline**: Visualize intervals on a number line
2. **Identify phases**: Mark which intervals fall in Phase 1, 2, 3
3. **Check touching cases**: Intervals like [1,3] and [3,5]
4. **Test edge cases**: Empty, insert at start/end, complete containment
5. **Trace merging**: Track how newInterval grows during Phase 2

## Related Problems

1. **Merge Intervals** (56) - Similar merging, but unsorted input
2. **Interval List Intersections** (986) - Two sorted lists
3. **Employee Free Time** (759) - Merging across multiple schedules
4. **My Calendar I** (729) - Booking system with overlap detection