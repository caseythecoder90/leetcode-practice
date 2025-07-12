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

## 🧠 Intuition: The "Conference Room" Analogy

### Think of it as Scheduling Meetings
Imagine you're scheduling meetings in a conference room. Each interval represents a meeting with a start and end time. You want to **fit as many meetings as possible** without any overlaps.

**Key Question:** When you have multiple overlapping meetings, which one should you choose?

**Answer:** **The one that ends earliest!** 

**Why?** Because it leaves the most "room" (time) for future meetings.

### Visual Example - Why End Time Matters
```
Meetings: [1,4], [2,3], [3,5]
Timeline: 1----2----3----4----5

❌ Choose [1,4] (longer meeting):
   [=====[1,4]=====]
   1----2----3----4----5
   Remaining space: none → Can't fit any other meetings

✅ Choose [2,3] (ends earliest):
        [=[2,3]=]
   1----2----3----4----5
   Remaining space: 1-2 and 3-5 → Room for more meetings!
```

**The meeting that ends earliest leaves the most opportunities for future meetings.**

## 🎯 Core Strategy: Reframe the Problem

**Instead of:** "Which intervals should I remove?" (hard to think about)
**Think:** "Which intervals should I keep?" (easier to optimize)

**Goal:** Keep the maximum number of non-overlapping intervals
**Answer:** Total intervals - Maximum kept intervals = Minimum removals

## 🔑 Why Greedy Works: The "Earliest End" Rule

### The Greedy Choice Property
**Claim:** When facing overlapping intervals, it's always optimal to choose the one that ends earliest.

**Proof Intuition:**
1. Suppose there's an optimal solution that doesn't pick the earliest-ending interval
2. We can always "swap" that choice with the earliest-ending interval  
3. This swap won't make the solution worse (actually might improve it)
4. Therefore, there exists an optimal solution that includes the earliest-ending interval

### Why Other Strategies Fail

**❌ What if we sorted by START time?**
```
Input: [[1,4], [2,3], [3,5]]
Sorted by start: [[1,4], [2,3], [3,5]]

Greedy by start time:
1. Keep [1,4] (first by start)
2. [2,3] overlaps with [1,4] → skip
3. [3,5] overlaps with [1,4] → skip
Result: Keep only 1 interval ❌

Optimal solution: Keep [2,3] and [3,5] = 2 intervals ✅
```

**The start-time strategy is suboptimal!**

## 📋 Algorithm Steps

### Step 1: Sort by End Time
```java
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
```
**Why?** Process intervals in order of when they end, so we always consider the earliest-ending option first.

### Step 2: Greedy Selection
```java
int kept = 1; // Always keep first interval (ends earliest)
int lastEnd = intervals[0][1];

for (int i = 1; i < intervals.length; i++) {
    if (intervals[i][0] >= lastEnd) {
        // No overlap - keep this interval
        kept++;
        lastEnd = intervals[i][1];
    }
    // If overlap, skip (implicitly remove)
}
```

### Step 3: Calculate Removals
```java
return intervals.length - kept;
```

## 📝 Detailed Example Walkthrough

**Input:** `[[1,2], [2,3], [3,4], [1,3]]`

### Step 1: Sort by End Time
```
Original: [[1,2], [2,3], [3,4], [1,3]]
Sorted:   [[1,2], [2,3], [1,3], [3,4]]
          end=2   end=2   end=3   end=4
```

### Step 2: Greedy Selection Process
```
Timeline: 1----2----3----4

1. Keep [1,2] (first interval, ends at 2)
   kept = 1, lastEnd = 2
   Selected: [[1,2]]

2. Check [2,3]: starts at 2 >= 2 ✓ (no overlap)
   kept = 2, lastEnd = 3
   Selected: [[1,2], [2,3]]

3. Check [1,3]: starts at 1 < 3 ✗ (overlaps!)
   Skip this interval
   Selected: [[1,2], [2,3]]

4. Check [3,4]: starts at 3 >= 3 ✓ (no overlap)
   kept = 3, lastEnd = 4
   Selected: [[1,2], [2,3], [3,4]]

Final: Keep 3 intervals → Remove 4-3 = 1 interval
```

## 🧪 Mental Model for Understanding

### The "Parking Lot" Analogy
Think of intervals as **cars that need to park for different time periods:**
- You want to serve the **maximum number of customers**
- **Strategy:** Always prioritize the customer who will **leave earliest**
- **Why?** This frees up the parking spot **soonest** for the next customer

### Key Decision Rule
When facing overlapping intervals, ask yourself:
- ❌ "Which one starts earliest?" 
- ❌ "Which one is shortest?"
- ✅ **"Which one ends earliest?"** ← This leaves the most room for others!

## 🎯 Essential Insights to Remember

1. **"End early, leave room"** - Choose intervals that end earliest to maximize future opportunities

2. **Greedy is safe here** - The earliest-ending interval is always part of some optimal solution

3. **Reframe the problem** - Think "maximum keeps" not "minimum removes"

4. **Sort by end time is magical** - Ensures we always have the optimal choice available

5. **It's activity selection** - Classic greedy algorithm pattern

## ⚡ Complexity Analysis

- **Time Complexity**: O(n log n) - dominated by sorting
- **Space Complexity**: O(1) - only using constant extra space

## 🔍 Edge Cases to Consider

- **Single interval**: return 0 (nothing to remove)
- **No overlaps**: return 0 (already non-overlapping)
- **All identical intervals**: return length - 1 (keep one, remove rest)
- **Adjacent intervals** `[1,2], [2,3]`: return 0 (touching ≠ overlapping)

## 🎭 Alternative Approaches (Why They're Inferior)

1. **Sort by start time**: Doesn't guarantee optimal selection
2. **Dynamic Programming**: O(n²) complexity, unnecessarily complex
3. **Brute force**: O(2^n) exponential time complexity

## 💡 Interview Tips

1. **Start with the analogy**: "This is like scheduling meetings in a conference room"
2. **Explain the key insight**: "Always pick the meeting that ends earliest"
3. **Show why greedy works**: "Ending early leaves maximum room for future meetings"
4. **Trace through an example**: Walk through the step-by-step process
5. **Mention the complexity**: "O(n log n) due to sorting, then O(n) for selection"

## 🔗 Related Problems
- **452. Minimum Number of Arrows to Burst Balloons** - Same greedy pattern
- **56. Merge Intervals** - Different approach (sort by start time)
- **253. Meeting Rooms II** - Similar but counts resources needed

**Remember**: The magic is in sorting by END time and always choosing the interval that ends earliest!