# Merge Intervals - Detailed Study Guide

## Problem Breakdown

### Visual Understanding

```
Input: [[1,3],[2,6],[8,10],[15,18]]

Before sorting (by start time):
[1,3]
  [2,6]
        [8,10]
                [15,18]

After sorting (already sorted in this case):
[1,3]
  [2,6]      → Overlap! Merge to [1,6]
        [8,10]    → No overlap
                [15,18] → No overlap

Result: [[1,6],[8,10],[15,18]]
```

## Step-by-Step Execution Trace

### Example: `[[1,3],[2,6],[8,10],[15,18]]`

**Step 1: Sort by start time**
```
Input: [[1,3],[2,6],[8,10],[15,18]]
Already sorted!
```

**Step 2: Initialize with first interval**
```
merged = [[1,3]]
```

**Step 3: Process [2,6]**
```
Current: [2,6]
Last merged: [1,3]

Check overlap: 2 <= 3? YES
Merge: [1, max(3,6)] = [1,6]

merged = [[1,6]]
```

**Step 4: Process [8,10]**
```
Current: [8,10]
Last merged: [1,6]

Check overlap: 8 <= 6? NO
Add new interval

merged = [[1,6], [8,10]]
```

**Step 5: Process [15,18]**
```
Current: [15,18]
Last merged: [8,10]

Check overlap: 15 <= 10? NO
Add new interval

merged = [[1,6], [8,10], [15,18]]
```

**Final Result**: `[[1,6],[8,10],[15,18]]`

## Overlap Detection Logic

### Why `currentStart <= lastEnd` works after sorting:

```
After sorting, for any two consecutive intervals:
lastStart <= currentStart (guaranteed by sorting)

Overlap condition (general): a <= d && c <= b
Where [a,b] is last interval, [c,d] is current

After sorting: a <= c (guaranteed)
So we only need to check: c <= b
Which is: currentStart <= lastEnd
```

### Visual Examples:

```
1. Overlapping:
Last:    [1      6]
Current:    [2      8]
         ^^^overlaps^^^
currentStart(2) <= lastEnd(6) ✓

2. Touching (edge case):
Last:    [1    4]
Current:        [4    7]
         ^^^^touching^^^^
currentStart(4) <= lastEnd(4) ✓

3. Not overlapping:
Last:    [1    4]
Current:              [8    10]
         no overlap
currentStart(8) <= lastEnd(4) ✗
```

## Merging Logic

When intervals overlap:
```java
newEnd = Math.max(lastEnd, currentEnd)
```

Why `Math.max`?
- Current interval might be completely contained in last interval
- Or current interval might extend beyond last interval

Examples:
```
1. Current extends beyond:
Last:    [1    6]
Current:    [2      8]
Merged:  [1        8]  → max(6, 8) = 8

2. Current contained within:
Last:    [1        10]
Current:    [2   5]
Merged:  [1        10]  → max(10, 5) = 10
```

## Common Mistakes

### Mistake 1: Forgetting to sort
```java
// ❌ WRONG: Not sorting first
for (int[] interval : intervals) {
    // Try to merge without sorting
}
```
**Why wrong?** You might miss overlaps between non-adjacent intervals in unsorted array.

### Mistake 2: Wrong overlap condition
```java
// ❌ WRONG: Using strict inequality
if (current[0] < last[1]) {
    // This misses touching intervals like [1,4] and [4,5]
}
```
**Fix:** Use `<=` to include touching intervals.

### Mistake 3: Not using Math.max for end time
```java
// ❌ WRONG: Just taking current end
last[1] = current[1];
```
**Why wrong?** Current interval might be contained in last interval.

**Fix:** `last[1] = Math.max(last[1], current[1]);`

## Complexity Deep Dive

### Time Complexity: O(n log n)

1. **Sorting**: O(n log n)
   - Java's Arrays.sort() uses Dual-Pivot Quicksort for primitives
   - TimSort for objects (our 2D array)

2. **Merging**: O(n)
   - Single pass through sorted intervals
   - Constant work per interval

3. **Total**: O(n log n) + O(n) = **O(n log n)**

### Space Complexity: O(n)

1. **Result List**: O(n)
   - Worst case: no overlaps, store all n intervals
   - Best case: all merge to 1 interval

2. **Sorting Space**: O(log n) to O(n)
   - Depends on sort implementation
   - Java's TimSort: O(n) auxiliary space

3. **Total**: **O(n)**

## Alternative Approaches

### Approach 1: No Extra Space (Modify Input)
```java
// Sort intervals in place
Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

// Merge in place, keep index of last valid interval
int index = 0;
for (int i = 1; i < intervals.length; i++) {
    if (intervals[i][0] <= intervals[index][1]) {
        intervals[index][1] = Math.max(intervals[index][1], intervals[i][1]);
    } else {
        index++;
        intervals[index] = intervals[i];
    }
}

// Return subarray [0...index]
return Arrays.copyOf(intervals, index + 1);
```
**Trade-off**: Saves space but modifies input.

### Approach 2: Using TreeMap (for streaming)
If intervals arrive one at a time, use TreeMap to maintain sorted order.

## Practice Tips

1. **Draw it out**: Visualize intervals on a timeline
2. **Check edge cases**: Single interval, all overlap, no overlap
3. **Verify sorting**: Always confirm intervals are sorted by start time
4. **Test touching intervals**: [1,4] and [4,5] should merge
5. **Consider contained intervals**: [1,10] and [2,5]

## Related Problems

1. **Insert Interval** (57) - Merging with sorted intervals
2. **Meeting Rooms II** (253) - Count overlapping intervals
3. **Non-overlapping Intervals** (435) - Remove fewest to make non-overlapping
4. **Interval List Intersections** (986) - Find overlapping portions