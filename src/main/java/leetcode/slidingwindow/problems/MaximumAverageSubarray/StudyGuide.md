# Maximum Average Subarray I - Detailed Study Guide

## Understanding the Problem

### Visual Representation
```
Array: [1, 12, -5, -6, 50, 3], k = 4

Window positions:
[1, 12, -5, -6] 50, 3     → sum = 2,   avg = 0.5
1, [12, -5, -6, 50] 3     → sum = 51,  avg = 12.75  ✓ Maximum!
1, 12, [-5, -6, 50, 3]    → sum = 42,  avg = 10.5
```

### Why Sliding Window?
Without sliding window, you'd calculate each window's sum from scratch:
- Window 1: Sum of indices 0-3 → O(k)
- Window 2: Sum of indices 1-4 → O(k)
- Total: O((n-k+1) × k) = O(nk)

With sliding window:
- Initial window: O(k)
- Each slide: O(1)
- Total: O(k + n) = O(n)

## Detailed Algorithm Walkthrough

### Step-by-Step Execution Trace

```
Input: nums = [1, 12, -5, -6, 50, 3], k = 4

Step 0: Build initial window
        Window: [1, 12, -5, -6]
        windowSum = 1 + 12 + (-5) + (-6) = 2
        maxSum = 2

Step 1: Slide to position 1
        Remove: nums[0] = 1
        Add: nums[4] = 50
        Window: [12, -5, -6, 50]
        windowSum = 2 - 1 + 50 = 51
        maxSum = max(2, 51) = 51

Step 2: Slide to position 2
        Remove: nums[1] = 12
        Add: nums[5] = 3
        Window: [-5, -6, 50, 3]
        windowSum = 51 - 12 + 3 = 42
        maxSum = max(51, 42) = 51

Result: maxSum / k = 51 / 4 = 12.75
```

## Comparing Your Solution with Optimal

### Your Approach
```java
int max = 0;
int front = 0;
int back = k - 1;

// Initial sum
for (int i = front; i <= back; i++) {
    max += nums[i];
}

// Sliding
for (int i = 0; i < nums.length - k; i++) {
    currentMax -= nums[front];
    front++;
    back++;
    currentMax += nums[back];
    max = Math.max(max, currentMax);
}
```

**Pros:**
- Conceptually clear with front/back pointers
- Correct sliding logic

**Cons:**
- Extra variables (front, back, iterations)
- More complex state management
- Variable naming confusion (max stores sum)

### Optimal Approach
```java
int windowSum = 0;

// Initial sum
for (int i = 0; i < k; i++) {
    windowSum += nums[i];
}

int maxSum = windowSum;

// Sliding
for (int i = k; i < nums.length; i++) {
    windowSum = windowSum + nums[i] - nums[i - k];
    maxSum = Math.max(maxSum, windowSum);
}
```

**Improvements:**
- Clearer variable names
- Single index for sliding
- More concise logic
- Standard sliding window pattern

## Key Concept: The Sliding Window Formula

### The Magic Line
```java
windowSum = windowSum + nums[i] - nums[i - k];
```

This single line encapsulates the sliding window:
- `nums[i]`: New element entering window (right side)
- `nums[i - k]`: Old element leaving window (left side)
- Result: Window slides exactly one position

### Visual Breakdown
```
i = 4, k = 4:
nums[i - k] = nums[0] ← Remove this
nums[i] = nums[4]     ← Add this

Window at i=3: [0][1][2][3] 4  5
Window at i=4:  0 [1][2][3][4] 5
                ↑           ↑
              Remove       Add
```

## Common Mistakes and How to Avoid Them

### Mistake 1: Wrong Initial Value
```java
// Wrong - assumes all positive numbers
int maxSum = 0;

// Correct - handles negative numbers
int maxSum = windowSum;  // After calculating first window
```

### Mistake 2: Off-by-One Errors
```java
// Wrong - misses last window
for (int i = k; i < nums.length - 1; i++)

// Correct
for (int i = k; i < nums.length; i++)
```

### Mistake 3: Integer Division
```java
// Wrong - loses precision
return maxSum / k;  // If maxSum=51, k=4, returns 12 not 12.75

// Correct
return (double) maxSum / k;
```

### Mistake 4: Not Handling Edge Cases
```java
// What if k = nums.length?
// Your loop won't execute: for (int i = 0; i < 0; i++)

// Better to handle explicitly or ensure loop works
if (k == nums.length) {
    // Return average of entire array
}
```

## Advanced Insights

### Pattern Variations

#### 1. Minimum Average Instead of Maximum
```java
int minSum = windowSum;  // Just track minimum instead
// ... in loop
minSum = Math.min(minSum, windowSum);
```

#### 2. Return All Windows Meeting Criteria
```java
List<Double> result = new ArrayList<>();
for (int i = k; i < nums.length; i++) {
    windowSum = windowSum + nums[i] - nums[i - k];
    if ((double)windowSum / k >= threshold) {
        result.add((double)windowSum / k);
    }
}
```

#### 3. Variable Window Size
```java
// Find smallest window with average >= target
int left = 0;
for (int right = 0; right < nums.length; right++) {
    // Expand and contract based on average
}
```

## Interview Tips

### How to Explain Your Approach

1. **Start with Brute Force**
   - "We could calculate sum for each window: O(nk)"
   
2. **Identify Optimization**
   - "Notice overlap between consecutive windows"
   - "Only one element changes on each slide"
   
3. **Explain Sliding Window**
   - "Maintain running sum, update incrementally"
   - "Remove leftmost, add rightmost: O(1) per slide"

4. **Handle Edge Cases**
   - "Check if k equals array length"
   - "Consider negative numbers"

### Follow-up Questions

**Q: What if we need the actual subarray, not just the average?**
```java
int maxStart = 0;
// Track where max window starts
if (windowSum > maxSum) {
    maxSum = windowSum;
    maxStart = i - k + 1;
}
// Return subarray from maxStart to maxStart + k - 1
```

**Q: Can you do it without extra space for very large k?**
A: Our solution already uses O(1) space!

**Q: What about finding k non-contiguous elements with max average?**
A: Different problem - would need sorting or heap, O(n log n)

## Practice Exercises

### Exercise 1: Modify for Minimum Average
Adapt the solution to find minimum average instead of maximum.

### Exercise 2: Count Windows Above Threshold
Count how many windows have average ≥ given threshold.

### Exercise 3: Best Two Non-Overlapping Windows
Find two non-overlapping windows of size k with maximum combined average.

## Time/Space Complexity Deep Dive

### Why O(n) Time?
- Initial window: k operations
- Sliding: (n - k) operations
- Total: k + (n - k) = n operations

### Why O(1) Space?
- Fixed number of variables regardless of input size
- No data structures that grow with input

### What Would Make it O(k) Space?
- Storing the window elements in a queue
- Keeping track of all window sums

## Key Takeaways

1. **Fixed-size sliding window** = Initial calculation + incremental updates
2. **The sliding formula** is always: `new_state = old_state - leaving_element + entering_element`
3. **Clear variable names** make code self-documenting
4. **Index arithmetic** (i - k) is cleaner than tracking multiple pointers
5. **Always test** with edge cases: k=1, k=n, negative numbers