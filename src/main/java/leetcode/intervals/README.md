# Intervals Pattern

## Overview

Interval problems involve working with ranges of values, often represented as `[start, end]` pairs. These problems are common in scheduling algorithms, calendar applications, and resource allocation scenarios. The key insight is recognizing when to sort intervals and how to efficiently merge, overlap, or process them.

## Core Concepts

### What are Intervals?
Intervals represent continuous ranges, typically defined by start and end points:
- `[1, 3]` represents values from 1 to 3 (inclusive or exclusive depending on problem)
- `[2, 5]` represents values from 2 to 5
- Common representations: `int[][]`, `List<int[]>`, or custom `Interval` class

### Key Properties
- **Overlapping**: Two intervals overlap if they share any common points
- **Non-overlapping**: Intervals don't share any points
- **Merging**: Combining overlapping intervals into single intervals
- **Sorting**: Often the first step in interval problems

## Essential Techniques

### 1. Sorting Strategies

**By Start Time** (Most Common):
```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
// Example: [[1,3], [2,6], [8,10]] → sorted by start
```

**By End Time** (For Greedy Selection):
```java
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
// Useful for: activity selection, non-overlapping intervals
```

**Custom Sorting**:
```java
// For complex problems requiring multiple criteria
Arrays.sort(intervals, (a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0]; // First by start
    return a[1] - b[1]; // Then by end
});
```

### 2. Overlap Detection

**Standard Overlap Check**:
```java
public boolean overlaps(int[] interval1, int[] interval2) {
    return interval1[0] < interval2[1] && interval2[0] < interval1[1];
}
```

**Key Insight**: Two intervals `[a, b]` and `[c, d]` overlap if `a < d` and `c < b`

### 3. Merge Pattern

**Classic Merge Algorithm**:
```java
public int[][] merge(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> result = new ArrayList<>();
    
    int[] current = intervals[0];
    for (int i = 1; i < intervals.length; i++) {
        if (current[1] >= intervals[i][0]) {
            // Overlapping - merge
            current[1] = Math.max(current[1], intervals[i][1]);
        } else {
            // Non-overlapping - add current and move to next
            result.add(current);
            current = intervals[i];
        }
    }
    result.add(current);
    
    return result.toArray(new int[result.size()][]);
}
```

### 4. Greedy Selection Pattern

**Activity Selection (Non-overlapping Intervals)**:
```java
public int maxNonOverlapping(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // Sort by end time
    
    int count = 0;
    int lastEnd = Integer.MIN_VALUE;
    
    for (int[] interval : intervals) {
        if (interval[0] >= lastEnd) {
            count++;
            lastEnd = interval[1];
        }
    }
    
    return count;
}
```

## Common Problem Types

### 1. Merge Intervals
- **Pattern**: Sort by start time, then merge overlapping
- **Example**: `[[1,3],[2,6],[8,10]]` → `[[1,6],[8,10]]`
- **Time**: O(n log n), **Space**: O(1) excluding output

### 2. Non-overlapping Intervals
- **Pattern**: Greedy selection, sort by end time
- **Goal**: Find maximum number of non-overlapping intervals
- **Time**: O(n log n), **Space**: O(1)

### 3. Insert Interval
- **Pattern**: Find insertion point, merge if necessary
- **Challenge**: Handle edge cases at boundaries
- **Time**: O(n), **Space**: O(1) excluding output

### 4. Meeting Rooms
- **Pattern**: Sort and check for overlaps
- **Variants**: Can attend all meetings? Minimum rooms needed?
- **Time**: O(n log n), **Space**: O(1)

### 5. Point Coverage
- **Pattern**: Find minimum points to cover all intervals
- **Strategy**: Greedy, place points optimally
- **Time**: O(n log n), **Space**: O(1)

## When to Use Intervals Pattern

### Problem Indicators
✅ **Use intervals when you see:**
- Scheduling problems (meetings, tasks)
- Time ranges or date ranges
- Resource allocation with start/end times
- Overlapping/non-overlapping requirements
- Merging adjacent or overlapping ranges
- "Book a meeting" or "reserve a room" scenarios

### Key Phrases
- "overlapping intervals"
- "merge intervals"
- "non-overlapping"
- "schedule meetings"
- "minimum rooms needed"
- "activity selection"
- "calendar booking"

## Implementation Tips

### 1. **Choose Right Data Structure**
- Use `int[][]` for simple start/end pairs
- Consider custom classes for complex interval data
- Use `TreeMap` for dynamic interval insertion/removal

### 2. **Handle Edge Cases**
- Empty input arrays
- Single interval
- All intervals overlap
- No intervals overlap
- Equal start/end times

### 3. **Sorting Strategy Matters**
- **Sort by start**: For merging, insertion problems
- **Sort by end**: For greedy selection, activity selection
- **Custom sort**: For complex multi-criteria problems

### 4. **Boundary Conditions**
- Inclusive vs exclusive endpoints
- Adjacent intervals (touching but not overlapping)
- Zero-length intervals

## Common Mistakes

### 1. **Wrong Sorting Strategy**
❌ Always sorting by start time
✅ Choose sorting based on problem type

### 2. **Incorrect Overlap Logic**
❌ Using `>=` when `>` is needed (or vice versa)
✅ Carefully consider boundary conditions

### 3. **Missing Edge Cases**
❌ Not handling empty arrays or single intervals
✅ Test with minimal inputs

### 4. **Inefficient Space Usage**
❌ Creating unnecessary data structures
✅ Often can solve in-place or with minimal extra space

## Learning Path

### Beginner Level
1. **Understand interval representation and overlap detection**
2. **Master basic sorting strategies**
3. **Practice merge intervals pattern**

### Intermediate Level
4. **Learn greedy selection pattern**
5. **Practice non-overlapping interval problems**
6. **Handle complex boundary conditions**

### Advanced Level
7. **Combine intervals with other patterns (heap, two pointers)**
8. **Optimize for specific constraints (real-time, memory)**
9. **Design interval-based data structures**

## Resources for Further Learning

### Essential Problems to Master
1. **56. Merge Intervals** - Learn basic merge pattern
2. **57. Insert Interval** - Handle insertion and merging
3. **435. Non-overlapping Intervals** - Master greedy selection
4. **452. Minimum Number of Arrows to Burst Balloons** - Point coverage
5. **253. Meeting Rooms II** - Multi-resource scheduling

### LeetCode 75 Intervals Problems
- **435. Non-overlapping Intervals** (Medium)
- **452. Minimum Number of Arrows to Burst Balloons** (Medium)

### Practice Strategy
1. Start with merge intervals to understand basic patterns
2. Practice greedy selection with non-overlapping problems
3. Work on insertion and dynamic problems
4. Combine with other patterns for advanced scenarios

## Time/Space Complexity Patterns

| Problem Type | Time | Space | Notes |
|-------------|------|-------|-------|
| Merge Intervals | O(n log n) | O(1) | Sorting dominates |
| Non-overlapping | O(n log n) | O(1) | Greedy after sorting |
| Insert Interval | O(n) | O(1) | Linear scan if sorted |
| Meeting Rooms | O(n log n) | O(1) | Check overlaps after sort |
| Min Rooms Needed | O(n log n) | O(n) | Event processing or heap |

---

## Directory Structure

```
intervals/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── problems/
│   ├── NonOverlappingIntervals/
│   └── MinimumNumberOfArrowsToBurstBalloons/
└── common/
    └── IntervalUtilities.java
```

Start with understanding overlap detection and merge patterns, then progress to greedy selection strategies!