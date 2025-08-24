# H-Index Complete Study Package

## 🧠 Mental Models for H-Index

### Mental Model 1: The Academic Achievement Ladder
Think of h-index like climbing an achievement ladder:
- **Rung h**: You need h papers with ≥h citations each to reach this rung
- **Goal**: Climb as high as possible
- **The catch**: Higher rungs need both more papers AND higher citation requirements

```
Rung 5: Need 5 papers with ≥5 citations each
Rung 4: Need 4 papers with ≥4 citations each  
Rung 3: Need 3 papers with ≥3 citations each
Rung 2: Need 2 papers with ≥2 citations each
Rung 1: Need 1 paper with ≥1 citations
```

### Mental Model 2: The Citation Filter
Imagine you have a filter that only lets through papers with ≥h citations:
- **Set filter to h=1**: How many papers pass through?
- **Set filter to h=2**: How many papers pass through?
- **Keep increasing h until**: Papers passing through < h (then h-1 is your answer)

### Mental Model 3: The Research Impact Threshold
You're setting a "minimum impact threshold" for your research:
- **If I set my threshold at h**: Do I have at least h papers that meet this threshold?
- **Find the highest threshold** where this is still true

## 🎯 Pattern Recognition Guide

### When You See H-Index Type Problems

**Trigger Words:**
- "at least X items with at least Y property"
- "maximum value such that condition holds"
- "threshold-based counting"
- "ranking with dual criteria"

**Problem Structure:**
```
Find max k such that:
- At least k items
- Each item has property ≥ k
```

**Other Examples:**
- **Kth Largest Element**: Find kth largest in unsorted array
- **Top K Frequent**: Find k most frequent elements
- **Meeting Rooms**: Maximum meetings without conflict

## 🔍 Deep Dive: Why Sorting Is The Key

### The Unsorted Challenge
```
Citations: [3,0,6,1,5]
Question: How many papers have ≥3 citations?
Answer: Need to scan entire array → [3,6,5] = 3 papers
```

**Problem**: For each potential h-value, we'd need to scan the entire array.
**Time Complexity**: O(n²) for brute force

### The Sorted Solution
```
Sorted: [0,1,3,5,6]
Position i: All papers from i onwards have ≥citations[i] citations
Count: n - i papers
```

**Benefit**: At each position, we instantly know the count without scanning.
**Time Complexity**: O(n log n) for sorting + O(n) for scanning = O(n log n)

### Visual Representation
```
Sorted: [0, 1, 3, 5, 6]
Index:   0  1  2  3  4
Count:   5  4  3  2  1  ← Papers with ≥citations[i] citations

Position 2: citations[2] = 3, count = 3
Check: 3 ≥ 3? ✅ → H-Index = 3
```

## 📚 Comprehensive Examples

### Example 1: Standard Case
```
Input: [3,0,6,1,5]
Sorted: [0,1,3,5,6]

Analysis:
Position 0: 0 ≥ 5? No (need 5 papers with ≥5 citations, but first paper has 0)
Position 1: 1 ≥ 4? No (need 4 papers with ≥4 citations, but second paper has 1)  
Position 2: 3 ≥ 3? Yes! → H-Index = 3

Verification: Papers [3,5,6] have ≥3 citations each ✓
```

### Example 2: High Achiever
```
Input: [10,8,5,4,3]
Sorted: [3,4,5,8,10]

Analysis:
Position 0: 3 ≥ 5? No
Position 1: 4 ≥ 4? Yes! → H-Index = 4

Verification: Papers [4,5,8,10] have ≥4 citations each ✓
```

### Example 3: Edge Case - All Zeros
```
Input: [0,0,0]
Sorted: [0,0,0]

Analysis:
Position 0: 0 ≥ 3? No
Position 1: 0 ≥ 2? No
Position 2: 0 ≥ 1? No
Result: H-Index = 0
```

### Example 4: Single High Paper
```
Input: [100]
Sorted: [100]

Analysis:
Position 0: 100 ≥ 1? Yes! → H-Index = 1

Key Insight: H-Index can't exceed number of papers!
```

## 🎓 Advanced Optimization: Bucket Sort Approach

### Why Bucket Sort Works
**Key Insight**: H-Index can never exceed n (total number of papers).
**Therefore**: We only need to track citation counts from 0 to n.

### Bucket Sort Algorithm
```java
public int hIndex(int[] citations) {
    int n = citations.length;
    int[] buckets = new int[n + 1];  // buckets[i] = papers with exactly i citations
    
    // Fill buckets (cap citations at n since h-index can't exceed n)
    for (int citation : citations) {
        buckets[Math.min(citation, n)]++;
    }
    
    // Count from highest to lowest
    int count = 0;
    for (int i = n; i >= 0; i--) {
        count += buckets[i];
        if (count >= i) {
            return i;  // Found h papers with ≥i citations
        }
    }
    return 0;
}
```

### Bucket Sort Walkthrough
```
Input: [3,0,6,1,5], n = 5

Step 1: Fill buckets
buckets[0] = 1  (paper with 0 citations)
buckets[1] = 1  (paper with 1 citation)  
buckets[3] = 1  (paper with 3 citations)
buckets[5] = 2  (papers with 5,6 citations → both go to bucket[5])

Buckets: [1,1,0,1,0,2]

Step 2: Count from right to left
i=5: count = 2, is 2 ≥ 5? No
i=4: count = 2, is 2 ≥ 4? No
i=3: count = 3, is 3 ≥ 3? Yes! → H-Index = 3
```

## 🚨 Common Pitfalls & How to Avoid Them

### Pitfall 1: Misunderstanding "At Least"
**Wrong**: "Find papers with exactly h citations"
**Right**: "Find papers with **at least** h citations"

**Example**: If h=3, papers with [3,4,5,6] citations all count!

### Pitfall 2: Returning Wrong Value
**Wrong**: `return i` (the position)
**Right**: `return n - i` (the count of papers)

### Pitfall 3: Boundary Conditions
```java
// Wrong: May go out of bounds
for (int i = 0; i < n; i++) {
    if (citations[i] >= n - i) return n - i;
}

// Right: Handle all cases
for (int i = 0; i < n; i++) {
    int papers = n - i;
    if (citations[i] >= papers) return papers;
}
return 0;  // Don't forget this!
```

### Pitfall 4: Not Handling Edge Cases
```java
// Test these cases:
[0,0,0]    → Should return 0
[100]      → Should return 1 (not 100!)
[1,1,1]    → Should return 1
[]         → Should return 0 (if allowed)
```

## 💡 Interview Success Strategy

### 4-Phase Interview Approach

**Phase 1: Clarification (1 minute)**
```
"Let me confirm: h-index means the maximum h where I have at least h papers 
with at least h citations each. So for [3,0,6,1,5], I need to find the 
largest h where this condition holds."
```

**Phase 2: Approach (2 minutes)**
```
"I could try every possible h from 1 to n, but that would be O(n²). 
Instead, if I sort the array first, I can see that at position i, 
I have exactly n-i papers with at least citations[i] citations. 
So I just need citations[i] ≥ n-i for a valid h-index."
```

**Phase 3: Implementation (5-7 minutes)**
```java
// Clean, well-commented implementation
public int hIndex(int[] citations) {
    Arrays.sort(citations);
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

**Phase 4: Optimization (2 minutes)**
```
"This is O(n log n) due to sorting. If we need O(n), we could use bucket sort 
since h-index is bounded by n. We'd create n+1 buckets and count papers by 
citation count, then scan from right to left."
```

## 🎯 Practice Problems & Variations

### Level 1: Direct Applications
1. **H-Index II (LeetCode 275)**: Array is pre-sorted → Use binary search
2. **Kth Largest Element**: Similar sorting-based approach
3. **Top K Frequent Elements**: Bucket sort variation

### Level 2: Modified Versions
1. **Weighted H-Index**: Each paper has different weight
2. **Time-Based H-Index**: Only count papers from recent years
3. **Collaborative H-Index**: Multiple researchers combined

### Level 3: Similar Patterns
1. **Meeting Rooms II**: Maximum overlapping intervals
2. **Minimum Number of Arrows**: Burst balloons optimally
3. **Insert Interval**: Merge overlapping intervals

## 🔄 Quick Reference

### Algorithm Template
```java
// Sorting approach - O(n log n)
Arrays.sort(array);
for (int i = 0; i < n; i++) {
    if (condition_met(i)) return result(i);
}
return default_value;

// Bucket sort approach - O(n)  
int[] buckets = new int[n + 1];
// Fill buckets...
// Scan buckets from right to left...
```

### Key Formulas
```
After sorting at position i:
- Papers with ≥citations[i] citations = n - i
- Valid h-index condition: citations[i] ≥ (n - i)
- H-index value = n - i
```

### Complexity Summary
| Approach | Time | Space | Use Case |
|----------|------|-------|----------|
| Brute Force | O(n²) | O(1) | Learning/Small inputs |
| Sorting | O(n log n) | O(1) | Most interviews |
| Bucket Sort | O(n) | O(n) | Optimization discussions |

Remember: **H-Index is all about finding the sweet spot where quantity meets quality!**