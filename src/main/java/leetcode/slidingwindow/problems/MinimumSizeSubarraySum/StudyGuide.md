# Study Guide: Minimum Size Subarray Sum

## Core Concept

This problem asks: **"What's the shortest contiguous subarray with sum ≥ target?"**

The key insight: Since all numbers are positive, we can use a **shrinkable sliding window**.

## Visual Walkthrough

Let's trace through `nums = [2,3,1,2,4,3]`, `target = 7`:

```
Initial: left = 0, sum = 0, minLength = ∞

Step 1: right = 0
[2],3,1,2,4,3
 ↑
 LR
sum = 2 < 7 → Keep expanding

Step 2: right = 1
[2,3],1,2,4,3
 ↑ ↑
 L R
sum = 5 < 7 → Keep expanding

Step 3: right = 2
[2,3,1],2,4,3
 ↑   ↑
 L   R
sum = 6 < 7 → Keep expanding

Step 4: right = 3
[2,3,1,2],4,3
 ↑     ↑
 L     R
sum = 8 ≥ 7 → Valid window! Length = 4

Now SHRINK while maintaining validity:
- Remove 2: sum = 6 < 7 → Stop shrinking
- minLength = 4

Step 5: right = 4
[2,3,1,2,4],3
 ↑       ↑
 L       R
sum = 10 ≥ 7 → Valid window! Length = 5

SHRINK:
- Remove 2: sum = 8 ≥ 7 → Still valid! Length = 4
  2,[3,1,2,4],3
     ↑     ↑
     L     R
     
- Remove 3: sum = 5 < 7 → Stop shrinking
- minLength stays 4

Step 6: right = 5
2,[3,1,2,4,3]
   ↑       ↑
   L       R
sum = 8 ≥ 7 → Valid window! Length = 5

SHRINK:
- Remove 3: sum = 5 < 7 → Stop shrinking

Continue until we find:
2,3,1,2,[4,3]
         ↑ ↑
         L R
sum = 7 ≥ 7 → Valid! Length = 2 ← MINIMUM!
```

## The Shrinking Strategy

The magic happens in the shrinking phase:

```java
while (sum >= target) {
    minLength = Math.min(minLength, right - left + 1);
    sum -= nums[left];
    left++;
}
```

**Why shrink?** 
- We've found a valid window
- But maybe we can make it smaller!
- Keep shrinking while still valid
- This guarantees we find the minimum

## Common Thought Process

### Step 1: Recognize the Pattern
"Find minimum/shortest subarray" + "positive numbers" → Sliding Window!

### Step 2: Choose Window Type
- Fixed size? No, we're finding the optimal size
- Variable expanding? No, we need to minimize
- **Variable shrinking? YES!**

### Step 3: Define Valid Window
- Window is valid when sum ≥ target
- When valid, try to shrink to find minimum
- When invalid, expand to become valid again

## Alternative Approaches

### 1. Brute Force (O(n²))
```java
for (int start = 0; start < n; start++) {
    for (int end = start; end < n; end++) {
        // Check sum from start to end
    }
}
```
❌ Too slow for large arrays

### 2. Binary Search (O(n log n))
```java
// Build prefix sum array
// For each starting position, binary search for ending position
```
✓ Works but more complex than sliding window

### 3. Sliding Window (O(n)) - Optimal
✓ Simple, efficient, and intuitive

## Common Variations

### Variation 1: Sum Equals Target (Not ≥)
```java
// Can't use sliding window directly!
// Use HashMap with prefix sums instead
Map<Integer, Integer> prefixSumIndex = new HashMap<>();
```

### Variation 2: Array Contains Negative Numbers
```java
// Sliding window breaks down
// Sum isn't monotonic anymore
// Need different approach (deque or binary search)
```

### Variation 3: Maximum Size with Sum ≤ Target
```java
// Flip the logic!
while (sum > target) {  // Shrink when exceeding
    sum -= nums[left++];
}
maxLength = Math.max(maxLength, right - left + 1);
```

## Edge Cases Checklist

✅ **No valid subarray exists**
- Example: `[1,1,1]`, target = 10
- Return 0

✅ **Single element sufficient**
- Example: `[5]`, target = 5
- Return 1 immediately

✅ **Need entire array**
- Example: `[1,2,3,4]`, target = 10
- Return array length

✅ **Multiple valid windows of same length**
- Example: `[1,2,3,1,2,3]`, target = 6
- Return the minimum length (doesn't matter which)

## Interview Communication

### Initial Approach
"Since we need a contiguous subarray and all numbers are positive, I'll use a sliding window approach. The key insight is that adding elements increases the sum and removing elements decreases it, making the sum monotonic."

### Algorithm Explanation
"I'll maintain a window with two pointers. I'll expand the window by moving the right pointer until the sum is at least the target. Then I'll try to shrink from the left while maintaining validity to find the minimum size."

### Complexity Analysis
"Time complexity is O(n) because each element is added once and removed at most once. Space complexity is O(1) as we only use a few variables."

### Optimization Discussion
"We could add early termination if we find a subarray of length 1, since that's the minimum possible. We could also use binary search with prefix sums for O(n log n) solution, but sliding window is simpler and optimal."

## Common Mistakes

### Mistake 1: Wrong Initial Value
```java
// WRONG
int minLength = 0;  // Will always return 0!

// CORRECT
int minLength = Integer.MAX_VALUE;
```

### Mistake 2: Updating After Shrinking
```java
// WRONG
while (sum >= target) {
    sum -= nums[left++];
    minLength = Math.min(...);  // Might be invalid here!
}

// CORRECT
while (sum >= target) {
    minLength = Math.min(...);  // Update while valid
    sum -= nums[left++];
}
```

### Mistake 3: Forgetting No-Solution Case
```java
// WRONG
return minLength;  // Returns MAX_VALUE if no solution

// CORRECT
return minLength == Integer.MAX_VALUE ? 0 : minLength;
```

## Practice Problems Progression

1. **This Problem (209)** - Basic shrinkable window
2. **3. Longest Substring Without Repeating** - Shrinkable with HashSet
3. **76. Minimum Window Substring** - Complex shrinkable with pattern
4. **862. Shortest Subarray with Sum at Least K** - With negatives (hard!)

## Key Takeaways

1. **Positive numbers enable sliding window** - Monotonic property is key
2. **Shrink to optimize, expand to satisfy** - The shrinkable window pattern
3. **Update result while valid** - Not after becoming invalid
4. **Each element touched at most twice** - Guarantees O(n)
5. **Initialize carefully** - MAX_VALUE for minimum, 0 for maximum