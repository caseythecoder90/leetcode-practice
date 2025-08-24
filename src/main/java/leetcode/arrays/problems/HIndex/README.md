# H-Index - Problem Pattern Analysis & Complete Guide

## 🎯 Problem Statement

**LeetCode 274 - Medium**

Given an array of integers `citations` where `citations[i]` is the number of citations a researcher received for their ith paper, return the researcher's **h-index**.

**H-Index Definition**: The maximum value of h such that the researcher has published **at least h papers** that have each been cited **at least h times**.

### Examples

**Example 1:**
```
Input: citations = [3,0,6,1,5]
Output: 3
Explanation: Researcher has 3 papers with ≥3 citations each ([3,6,5])
```

**Example 2:**
```
Input: citations = [1,3,1]
Output: 1
Explanation: Researcher has 1 paper with ≥1 citations (can't do better)
```

## 🧩 Pattern Recognition: What Type of Problem Is This?

### ❌ **NOT** These Common Patterns:
- **NOT Dynamic Programming** - No overlapping subproblems or optimal substructure
- **NOT Two Pointers** - We're not finding pairs or comparing elements
- **NOT Sliding Window** - We're not looking at contiguous subarrays
- **NOT Greedy** - There's no locally optimal choice leading to global optimum

### ✅ **It IS: Sorting + Counting Pattern**

**Key Characteristics:**
1. **Threshold-based counting** - Count elements meeting a criteria
2. **Order matters for optimization** - Sorting reveals structure
3. **Mathematical relationship** - h papers with ≥h citations each

**Similar Problems:**
- Finding kth largest element
- Counting elements above/below threshold
- Ranking and percentile problems

## 💡 The Core Insight: Why Sorting Works

### The "Aha!" Moment

**Before sorting**: `[3,0,6,1,5]` - Hard to see the pattern
**After sorting**: `[0,1,3,5,6]` - Now we can see structure!

**Key Insight**: After sorting, at position `i`:
- **Papers with ≥ `citations[i]` citations** = `n - i` (all papers from position i onwards)
- **If `citations[i] ≥ (n - i)`** → We found a valid h-index of `n - i`

### Why This Works

1. **Sorted order guarantees**: All papers at positions ≥ i have ≥ `citations[i]` citations
2. **Count is automatic**: Number of such papers = `n - i`
3. **First valid case is maximum**: Since we scan left to right, first valid h is the largest possible

## 🔍 Three Solution Approaches

### Approach 1: Sorting (Recommended for Interviews)

```java
public int hIndex(int[] citations) {
    Arrays.sort(citations);  // O(n log n)
    int n = citations.length;
    
    for (int i = 0; i < n; i++) {
        int papersWithAtLeast = n - i;
        if (citations[i] >= papersWithAtLeast) {
            return papersWithAtLeast;
        }
    }
    return 0;
}
```

**Complexity**: Time O(n log n), Space O(1)
**Pros**: Clear logic, easy to explain, handles all edge cases
**Cons**: Not optimal time complexity

### Approach 2: Bucket Sort (Optimal)

```java
public int hIndex(int[] citations) {
    int n = citations.length;
    int[] buckets = new int[n + 1];  // buckets[i] = papers with exactly i citations
    
    // Fill buckets
    for (int citation : citations) {
        if (citation >= n) {
            buckets[n]++;  // Cap at n since h-index can't exceed n
        } else {
            buckets[citation]++;
        }
    }
    
    // Count from highest to lowest
    int count = 0;
    for (int i = n; i >= 0; i--) {
        count += buckets[i];
        if (count >= i) {  // Found h papers with ≥i citations
            return i;
        }
    }
    return 0;
}
```

**Complexity**: Time O(n), Space O(n)
**Pros**: Optimal time complexity
**Cons**: Uses extra space, more complex logic

### Approach 3: Brute Force (Educational)

```java
public int hIndex(int[] citations) {
    int n = citations.length;
    int maxH = 0;
    
    for (int h = 1; h <= n; h++) {  // Try all possible h values
        int count = 0;
        for (int citation : citations) {
            if (citation >= h) count++;
        }
        if (count >= h) {
            maxH = h;
        }
    }
    return maxH;
}
```

**Complexity**: Time O(n²), Space O(1)
**Pros**: Most straightforward logic
**Cons**: Inefficient, but good for understanding

## 📊 Detailed Example Walkthrough

### Example: `citations = [3,0,6,1,5]`

#### Step 1: Sort the Array
```
Original: [3, 0, 6, 1, 5]
Sorted:   [0, 1, 3, 5, 6]
```

#### Step 2: Check Each Position
```
Position | Citation | Papers ≥ Citation | Valid H-Index?
---------|----------|-------------------|---------------
    0    |    0     |        5          | ❌ (0 < 5)
    1    |    1     |        4          | ❌ (1 < 4)  
    2    |    3     |        3          | ✅ (3 ≥ 3) → H-Index = 3
```

#### Step 3: Verification
**H-Index = 3** means:
- ✅ At least 3 papers with ≥3 citations: `[3,5,6]`
- ✅ Remaining papers have ≤3 citations: `[0,1]`

**Why not H-Index = 4?**
- ❌ Only 2 papers with ≥4 citations: `[5,6]` (need at least 4)

## 🎓 Interview Strategy

### Phase 1: Understanding (2 minutes)
```
"Let me understand: h-index means h papers with at least h citations each.
So for [3,0,6,1,5], I need to find the maximum h where I have at least h 
papers with h or more citations."
```

### Phase 2: Approach (3 minutes)
```
"I think sorting will help here. After sorting, at each position i, 
I know how many papers have at least citations[i] citations - it's n-i.
So I just need to find the first position where citations[i] >= n-i."
```

### Phase 3: Implementation (5-7 minutes)
```java
// Implement sorting approach with clear variable names
// Walk through example to verify logic
```

### Phase 4: Optimization Discussion (2 minutes)
```
"This is O(n log n) due to sorting. If we want O(n), we could use bucket sort
since we only care about counts of citations, not their exact order."
```

## 🔧 Common Pitfalls & Edge Cases

### Pitfall 1: Wrong Understanding
**Wrong**: "Find papers with exactly h citations"
**Right**: "Find papers with **at least** h citations"

### Pitfall 2: Index Confusion
**Wrong**: `return i` (position)
**Right**: `return n - i` (count of papers)

### Pitfall 3: Missing Edge Cases
- **All zeros**: `[0,0,0]` → H-Index = 0
- **Single paper**: `[100]` → H-Index = 1 (not 100!)
- **Descending order**: Already handles via sorting

### Edge Case Examples
```java
[100] → h-index = 1 (1 paper with ≥1 citations)
[0,0,0] → h-index = 0 (no papers with ≥1 citations)
[1,1] → h-index = 1 (1 paper would need ≥1 citations, but we have 2 papers with ≥1)
```

## 🎯 Problem Variations & Extensions

### H-Index II (LeetCode 275)
- **Difference**: Citations array is already sorted
- **Solution**: Binary search instead of linear scan
- **Complexity**: O(log n) instead of O(n log n)

### Follow-up Questions
1. "What if we can't modify the input array?" → Use bucket sort
2. "What if citations can be very large?" → Bucket sort caps at n
3. "Can you do it in O(n) time?" → Yes, bucket sort approach

## 💡 Key Insights for Mastery

### Insight 1: The Mathematical Relationship
```
h-index = max{h : at least h papers have ≥h citations}
```

### Insight 2: Why Sorting Reveals Structure
- Sorting groups papers by citation count
- At position i, we know exactly how many papers have ≥citations[i] citations
- This transforms a counting problem into a position-checking problem

### Insight 3: The First Valid Is Maximum
- We scan left to right (low to high citations)
- First position where condition holds gives maximum valid h
- Later positions give smaller h values (fewer papers remaining)

## 🎯 Practice Strategy

1. **Master the sorting approach first** - It's most interview-friendly
2. **Understand the bucket sort optimization** - Shows algorithmic thinking
3. **Practice with edge cases** - All zeros, single elements, etc.
4. **Explain the insight clearly** - Why sorting helps solve the problem

The H-Index problem is a great example of how **sorting can reveal hidden structure** in data, transforming a complex counting problem into a simple position-checking problem!