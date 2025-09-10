# Increasing Triplet Subsequence - Study Guide

## Problem Analysis

### What We're Looking For
- Three indices `i < j < k` where `nums[i] < nums[j] < nums[k]`
- Return boolean (existence, not the actual triplet)
- Must maintain original array order (no sorting allowed)

### Key Constraints
- Up to 500,000 elements → Need O(n) solution
- Values can be negative → Use Integer.MAX_VALUE for initialization
- Index order matters → Greedy tracking of candidates

## Solution Deep Dive

### The Greedy Insight

**Why Greedy Works**: We're not looking for specific indices, just existence of the pattern. By keeping the smallest possible "first" and "second" values, we maximize our chances of finding a valid "third" value.

### Step-by-Step Algorithm

```java
public boolean increasingTriplet(int[] nums) {
    int first = Integer.MAX_VALUE;   // Smallest seen so far
    int second = Integer.MAX_VALUE;  // Smallest > first
    
    for (int num : nums) {
        if (num <= first) {
            first = num;        // New smallest
        } else if (num <= second) {
            second = num;       // New second smallest
        } else {
            return true;        // Found third > second > first
        }
    }
    return false;
}
```

### Detailed Execution Trace

Let's trace through `[2,1,5,0,4,6]`:

| Step | num | first | second | Action | Explanation |
|------|-----|-------|--------|--------|-------------|
| 0 | - | MAX | MAX | Initialize | Starting state |
| 1 | 2 | 2 | MAX | first = 2 | First number becomes first candidate |
| 2 | 1 | 1 | MAX | first = 1 | Found smaller first candidate |
| 3 | 5 | 1 | 5 | second = 5 | 5 > 1, so it becomes second candidate |
| 4 | 0 | 0 | 5 | first = 0 | Found even smaller first candidate |
| 5 | 4 | 0 | 4 | second = 4 | 4 > 0 but < 5, better second candidate |
| 6 | 6 | - | - | return true | 6 > 4, and we had valid first < second |

**Key Point**: When we updated `first` to 0 at step 4, we didn't "lose" the previous relationship. The algorithm remembers that there was a valid `first < second` pair, and any number greater than the current `second` will complete the triplet.

### Why Updates Don't Break the Logic

**Question**: When we update `first` from 1 to 0, don't we lose the relationship with `second = 5`?

**Answer**: No! Here's why:
- We had `first = 1, second = 5` (valid pair with 1 < 5)
- When we see 0, we update `first = 0`
- Now we have `first = 0, second = 5`
- Any future number > 5 still gives us a valid triplet because:
  - The original sequence had some position where value = 1
  - The original sequence had some later position where value = 5
  - Any number > 5 at an even later position completes the triplet

The key insight: **We don't need the exact indices, just the guarantee that such indices existed.**

## Edge Cases to Consider

### 1. All Decreasing: `[5,4,3,2,1]`
```
first=5, second=MAX → first=4, second=MAX → first=3, second=MAX → first=2, second=MAX → first=1, second=MAX
Result: false (second never gets set)
```

### 2. All Equal: `[1,1,1,1,1]`
```
first=1, second=MAX → first=1, second=MAX → ... (all subsequent 1's update first)
Result: false (second never gets set)
```

### 3. Only Two Increasing: `[1,2]`
```
first=1, second=MAX → first=1, second=2
Result: false (no third element)
```

### 4. Negative Numbers: `[-1,0,1]`
```
first=-1, second=MAX → first=-1, second=0 → return true (1 > 0)
```

## Alternative Approaches (Less Optimal)

### 1. Brute Force - O(n³)
```java
// Check all triplets - too slow for constraints
for (int i = 0; i < n-2; i++) {
    for (int j = i+1; j < n-1; j++) {
        for (int k = j+1; k < n; k++) {
            if (nums[i] < nums[j] && nums[j] < nums[k]) {
                return true;
            }
        }
    }
}
```

### 2. Dynamic Programming - O(n²)
```java
// For each position, find if there's smaller element before and larger after
// Still too slow for 500k elements
```

### 3. Stack-based - O(n) time, O(n) space
```java
// Maintain stack of increasing elements
// Works but uses extra space
```

## Interview Discussion Points

### 1. Why Not Sort?
"Sorting destroys the index relationships. We need `i < j < k` in the original array."

### 2. Greedy Choice Explanation
"By keeping the smallest possible candidates, we maximize our chances of finding a valid third element."

### 3. Complexity Trade-offs
"The greedy approach gives us O(n) time and O(1) space, which is optimal."

### 4. Extension Questions
- "What if we wanted the actual indices?" → Need different approach, likely O(n²)
- "What about k-increasing subsequence?" → Generalize with k-1 candidates
- "What if we wanted all such triplets?" → Need to track and backtrack

## Practice Variations

1. **Return the actual triplet values** (not just boolean)
2. **Find longest increasing subsequence** (classic DP problem)
3. **Count all increasing triplets** (harder, O(n²) or segment trees)
4. **Decreasing triplet** (reverse the logic)

## Memory Aids

**Mnemonic**: "First Second Third" - Keep the smallest first, smallest second, any third wins!

**Visual Pattern**: Think of it as setting up "stepping stones" - you want the lowest stones to make it easiest to step to a higher third stone.

**Interview Phrase**: "We're maintaining the best candidates for a future triplet completion."