# Intervals Pattern Flashcards

## Core Concepts

### Card 1: Overlap Detection
**Q:** How do you check if two intervals `[a, b]` and `[c, d]` overlap?

**A:** `a < d && c < b`
- Alternative: `!(a >= d || c >= b)` (negation of non-overlapping)
- Key insight: Neither interval ends before the other starts

---

### Card 2: Sorting Strategy
**Q:** When should you sort intervals by START time vs END time?

**A:** 
- **Sort by START**: When merging intervals, inserting intervals
- **Sort by END**: When maximizing count (activity selection, greedy)
- **Memory aid**: "START for merge, END for count"

---

### Card 3: Merge Pattern
**Q:** What's the basic algorithm for merging overlapping intervals?

**A:** 
1. Sort by start time
2. Take first interval as current
3. For each subsequent interval:
   - If overlaps with current: merge (extend end)
   - If doesn't overlap: add current to result, make this interval current
4. Don't forget to add the last interval

---

### Card 4: Non-overlapping Greedy
**Q:** How do you find the maximum number of non-overlapping intervals?

**A:**
1. Sort by END time
2. Greedily select intervals that start after the last selected interval ends
3. Always pick the interval that ends earliest (greedy choice)

---

### Card 5: Activity Selection Intuition
**Q:** Why does sorting by end time work for activity selection?

**A:** 
- By choosing intervals that end earliest, we leave the most room for future intervals
- This maximizes our choices and leads to optimal solution
- Greedy proof: If we chose a different interval, it would end later and potentially block more future intervals

---

## Pattern Recognition

### Card 6: Problem Type Identification
**Q:** What keywords indicate an intervals problem?

**A:**
- "overlapping intervals"
- "merge intervals" 
- "schedule meetings"
- "activity selection"
- "non-overlapping"
- "calendar booking"
- "time ranges"

---

### Card 7: Greedy vs Non-Greedy
**Q:** When can you use greedy algorithms for interval problems?

**A:**
- ✅ Maximizing count of non-overlapping intervals
- ✅ Minimum resources to cover all intervals
- ✅ Activity selection problems
- ❌ Finding specific arrangements or permutations
- ❌ When you need all possible solutions

---

### Card 8: Edge Cases
**Q:** What edge cases should you always test for interval problems?

**A:**
- Empty input `[]`
- Single interval `[[1,3]]`
- No overlaps `[[1,2], [3,4]]`
- All overlap `[[1,4], [2,5], [3,6]]`
- Adjacent intervals `[[1,2], [2,3]]`
- Same start/end times

---

## Implementation Details

### Card 9: Comparator Syntax
**Q:** How do you sort intervals by start time and end time in Java?

**A:**
```java
// By start time
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// By end time  
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

// By start, then end
Arrays.sort(intervals, (a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0];
    return a[1] - b[1];
});
```

---

### Card 10: Merge Implementation
**Q:** Write the key part of the merge intervals algorithm.

**A:**
```java
for (int i = 1; i < intervals.length; i++) {
    if (current[1] >= intervals[i][0]) {
        // Overlapping - merge
        current[1] = Math.max(current[1], intervals[i][1]);
    } else {
        // Non-overlapping - save and move
        result.add(current);
        current = intervals[i];
    }
}
result.add(current); // Don't forget last interval!
```

---

### Card 11: Non-overlapping Count
**Q:** Write the key part of counting maximum non-overlapping intervals.

**A:**
```java
Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // Sort by end
int count = 0;
int lastEnd = Integer.MIN_VALUE;

for (int[] interval : intervals) {
    if (interval[0] >= lastEnd) { // No overlap
        count++;
        lastEnd = interval[1];
    }
}
```

---

### Card 12: Point Coverage
**Q:** How do you find minimum points to cover all intervals?

**A:**
1. Sort by end time
2. Place point at end of first interval
3. For each subsequent interval:
   - If point doesn't cover it (point < interval start)
   - Place new point at end of this interval
4. This is same as "minimum arrows" pattern

---

## Complexity Analysis

### Card 13: Time Complexity
**Q:** What's the time complexity of most interval problems?

**A:** **O(n log n)** 
- Sorting dominates: O(n log n)
- Processing pass: O(n)
- Total: O(n log n)

---

### Card 14: Space Complexity
**Q:** What's the typical space complexity of interval algorithms?

**A:** **O(1) extra space** (excluding output)
- Most algorithms process intervals in single pass after sorting
- Can often reuse input array or process in-place
- Output space not counted in space complexity

---

## Problem-Specific Patterns

### Card 15: Meeting Rooms Pattern
**Q:** How do you check if a person can attend all meetings?

**A:**
1. Sort meetings by start time
2. Check if any meeting starts before previous one ends
3. Return false if overlap found, true otherwise

---

### Card 16: Meeting Rooms II Pattern  
**Q:** How do you find minimum meeting rooms needed?

**A:**
Two approaches:
1. **Event processing**: Create events for start/end, sort by time, track active count
2. **Min heap**: Sort by start, use heap to track earliest ending meetings

---

### Card 17: Insert Interval Pattern
**Q:** How do you insert a new interval into a sorted list?

**A:**
1. Add all intervals that end before new interval starts
2. Merge all intervals that overlap with new interval
3. Add all remaining intervals
4. Three distinct phases: before, merge, after

---

### Card 18: Interval Intersection
**Q:** How do you find intersection of two interval lists?

**A:**
1. Use two pointers, one for each list
2. Find intersection of current intervals from both lists
3. Advance pointer for interval that ends earlier
4. Continue until one list is exhausted

---

## Advanced Concepts

### Card 19: Sweep Line Algorithm
**Q:** When would you use a sweep line approach for intervals?

**A:**
- When you need to track active intervals at each point
- Meeting rooms II (minimum rooms needed)
- Finding all points covered by intervals
- Real-time event processing

---

### Card 20: Data Structure Choice
**Q:** When should you use TreeMap for interval problems?

**A:**
- Dynamic insertion/deletion of intervals
- Need to query intervals by range
- Calendar booking with real-time updates
- When intervals arrive online (not all at once)

---

## Debugging & Testing

### Card 21: Common Bug
**Q:** What's the most common bug in interval merging?

**A:** **Forgetting to add the last interval**
- After the loop, current interval hasn't been added to result
- Always remember: `result.add(current);` after the main loop

---

### Card 22: Boundary Conditions
**Q:** How do you handle inclusive vs exclusive interval endpoints?

**A:**
- **Inclusive `[a,b]`**: overlap if `a1 <= b2 && a2 <= b1`
- **Exclusive `[a,b)`**: overlap if `a1 < b2 && a2 < b1`  
- Always clarify with interviewer which convention to use

---

### Card 23: Testing Strategy
**Q:** What's a good testing sequence for interval problems?

**A:**
1. **Empty**: `[]`
2. **Single**: `[[1,3]]`  
3. **No overlap**: `[[1,2], [3,4]]`
4. **Complete overlap**: `[[1,4], [2,3]]`
5. **Partial overlap**: `[[1,3], [2,4]]`
6. **Adjacent**: `[[1,2], [2,3]]`

---

### Card 24: Performance Optimization
**Q:** How can you optimize interval algorithms for very large inputs?

**A:**
- Use merge sort instead of Arrays.sort() for guaranteed O(n log n)
- Process in-place when possible to save space
- For repeated queries, preprocess and use data structures like segment trees
- Consider coordinate compression for very large coordinate ranges

---

## Interview Tips

### Card 25: Problem Approach
**Q:** What's the general approach to solving interval problems?

**A:**
1. **Clarify**: Inclusive/exclusive endpoints? Input format?
2. **Identify**: Merge? Count? Coverage? Activity selection?
3. **Sort**: By start (merge) or end (count/greedy)?
4. **Process**: One pass with appropriate logic
5. **Test**: Edge cases and boundary conditions

---

### Card 26: Communication
**Q:** How should you explain your approach in an interview?

**A:**
1. "I notice this is an intervals problem..."
2. "I'll sort by [start/end] time because..."
3. "Then I'll use [merge/greedy] strategy..."
4. "The key insight is..."
5. "Let me trace through an example..."
6. "Time complexity is O(n log n) due to sorting..."