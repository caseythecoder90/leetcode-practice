# Intervals CheatSheet

## Quick Reference Templates

### 1. Overlap Detection
```java
// Check if two intervals overlap
public boolean overlaps(int[] a, int[] b) {
    return a[0] < b[1] && b[0] < a[1];
}

// Alternative: Check if NOT overlapping, then negate
public boolean overlaps(int[] a, int[] b) {
    return !(a[1] <= b[0] || b[1] <= a[0]);
}
```

### 2. Basic Sorting Patterns
```java
// Sort by start time (most common)
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// Sort by end time (for greedy selection)
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

// Sort by start, then by end if start is same
Arrays.sort(intervals, (a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0];
    return a[1] - b[1];
});
```

### 3. Merge Intervals Template
```java
public int[][] merge(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> result = new ArrayList<>();
    int[] current = intervals[0];
    
    for (int i = 1; i < intervals.length; i++) {
        if (current[1] >= intervals[i][0]) {
            // Overlapping - merge
            current[1] = Math.max(current[1], intervals[i][1]);
        } else {
            // Non-overlapping - save current, move to next
            result.add(current);
            current = intervals[i];
        }
    }
    result.add(current); // Don't forget the last interval
    
    return result.toArray(new int[result.size()][]);
}
```

### 4. Non-overlapping Intervals (Greedy Selection)
```java
public int maxNonOverlapping(int[][] intervals) {
    // Sort by END time for greedy selection
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 0;
    int lastEnd = Integer.MIN_VALUE;
    
    for (int[] interval : intervals) {
        if (interval[0] >= lastEnd) { // No overlap
            count++;
            lastEnd = interval[1];
        }
    }
    
    return count;
}
```

### 5. Minimum Removal for Non-overlapping
```java
public int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length <= 1) return 0;
    
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // Sort by end
    
    int keep = 1; // Always keep first interval
    int lastEnd = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= lastEnd) { // No overlap
            keep++;
            lastEnd = intervals[i][1];
        }
    }
    
    return intervals.length - keep; // Total - kept = removed
}
```

### 6. Point Coverage (Minimum Arrows)
```java
public int findMinArrowShots(int[][] points) {
    if (points.length == 0) return 0;
    
    // Sort by end position
    Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
    
    int arrows = 1;
    int arrowPos = points[0][1]; // Shoot at end of first balloon
    
    for (int i = 1; i < points.length; i++) {
        if (points[i][0] > arrowPos) { // Need new arrow
            arrows++;
            arrowPos = points[i][1];
        }
    }
    
    return arrows;
}
```

### 7. Insert Interval Template
```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0;

    // Add all intervals that end before new interval starts
    while (i < intervals.length && intervals[i][1] < newInterval[0]) {
        result.add(intervals[i]);
        i++;
    }

    // Merge overlapping intervals
    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);

    // Add remaining intervals
    while (i < intervals.length) {
        result.add(intervals[i]);
        i++;
    }

    return result.toArray(new int[result.size()][]);
}
```

### 8. Summary Ranges (Consecutive Numbers)
```java
public List<String> summaryRanges(int[] nums) {
    List<String> result = new ArrayList<>();
    if (nums.length == 0) return result;

    int start = 0;

    for (int i = 0; i < nums.length; i++) {
        // Check if at end OR next isn't consecutive
        if (i == nums.length - 1 || nums[i] + 1 != nums[i + 1]) {
            if (start == i) {
                result.add(String.valueOf(nums[start]));
            } else {
                result.add(nums[start] + "->" + nums[i]);
            }
            start = i + 1;
        }
    }

    return result;
}
```

## Pattern Recognition Guide

### When to Sort by START time:
- ✅ Merge intervals
- ✅ Insert interval
- ✅ Find overlapping regions
- ✅ Timeline problems

### When to Sort by END time:
- ✅ Activity selection (maximize count)
- ✅ Non-overlapping intervals
- ✅ Minimum arrows/points coverage
- ✅ Greedy optimization problems

### When to Use Greedy:
- ✅ Maximize/minimize count of intervals
- ✅ "Select maximum non-overlapping"
- ✅ "Minimum resources to cover all"
- ✅ Activity scheduling

## Quick Decision Tree

```
Problem involves intervals?
├─ Yes
│  ├─ Need to merge overlapping?
│  │  ├─ Yes → Sort by START, use merge template
│  │  └─ No
│  │     ├─ Maximize count (activity selection)?
│  │     │  ├─ Yes → Sort by END, use greedy template
│  │     │  └─ No
│  │     │     ├─ Insert new interval?
│  │     │     │  ├─ Yes → Use insert template
│  │     │     │  └─ No → Custom solution
└─ No → Different pattern
```

## Common Edge Cases to Test

```java
// Empty input
int[][] empty = {};

// Single interval
int[][] single = {{1, 3}};

// No overlaps
int[][] noOverlap = {{1, 2}, {3, 4}, {5, 6}};

// All overlap
int[][] allOverlap = {{1, 4}, {2, 5}, {3, 6}};

// Adjacent (touching but not overlapping)
int[][] adjacent = {{1, 2}, {2, 3}, {3, 4}};

// Same start times
int[][] sameStart = {{1, 3}, {1, 5}, {1, 2}};

// Same end times
int[][] sameEnd = {{1, 3}, {2, 3}, {0, 3}};

// Zero-length intervals
int[][] zeroLength = {{1, 1}, {2, 2}};
```

## Time/Space Complexity Quick Reference

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Sort intervals | O(n log n) | O(1) | Dominates most solutions |
| Merge pass | O(n) | O(1) | After sorting |
| Overlap check | O(1) | O(1) | Per pair |
| Greedy selection | O(n) | O(1) | After sorting |
| Insert interval | O(n) | O(1) | If already sorted |

## Interview Tips

### 1. **Clarify the Problem**
- Are endpoints inclusive or exclusive?
- Can intervals have zero length?
- Are intervals already sorted?
- What's the expected output format?

### 2. **Choose Right Approach**
- **Merging**: Sort by start time
- **Counting**: Sort by end time + greedy
- **Coverage**: Think about optimal point placement

### 3. **Code Structure**
```java
public int solution(int[][] intervals) {
    // 1. Handle edge cases
    if (intervals.length <= 1) return /* appropriate value */;
    
    // 2. Sort by appropriate criteria
    Arrays.sort(intervals, /* comparator */);
    
    // 3. Process intervals
    // (merge, count, or apply greedy strategy)
    
    // 4. Return result
}
```

### 4. **Common Bugs to Avoid**
- Off-by-one in overlap conditions
- Forgetting to add the last interval
- Wrong sorting criteria for the problem type
- Not handling equal start/end times correctly

### 5. **Test Strategy**
1. Empty input
2. Single interval
3. No overlaps
4. Complete overlap
5. Partial overlaps
6. Adjacent intervals

## Memory Aids

**"START for merge, END for count"**
- Sort by **START** when you need to **merge**
- Sort by **END** when you need to **count**

**"Greedy takes earliest end"**
- In activity selection, always pick the interval that ends earliest

**"Overlap: start1 < end2 AND start2 < end1"**
- Two intervals overlap if neither ends before the other starts