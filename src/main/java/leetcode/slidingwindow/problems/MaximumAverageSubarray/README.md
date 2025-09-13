# Maximum Average Subarray I

## Problem Description
You are given an integer array `nums` consisting of `n` elements, and an integer `k`.

Find a contiguous subarray whose length is equal to `k` that has the maximum average value and return this value.

**Note**: Any answer with a calculation error less than 10⁻⁵ will be accepted.

## Approach: Fixed Size Sliding Window

### Key Insight
This is a classic **fixed-size sliding window** problem. Instead of recalculating the sum for each window from scratch (O(k) per window), we can:
1. Calculate the initial window sum
2. Slide the window by removing the leftmost element and adding the new rightmost element
3. Track the maximum sum seen
4. Return maximum sum divided by k

### Algorithm
```java
public double findMaxAverage(int[] nums, int k) {
    // 1. Build initial window
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += nums[i];
    }
    
    int maxSum = windowSum;
    
    // 2. Slide the window
    for (int i = k; i < nums.length; i++) {
        windowSum = windowSum + nums[i] - nums[i - k];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    // 3. Return average
    return (double) maxSum / k;
}
```

## Your Solution Analysis

### What You Did Well ✅
1. Correctly identified it as a sliding window problem
2. Properly calculated initial window sum
3. Got the sliding logic right (remove old, add new)
4. Correctly converted to double for the average

### Areas for Improvement 📈

#### 1. Variable Naming
```java
// Your code
int max = 0;  // Actually stores sum, not max

// Better
int windowSum = 0;  // Clear what it represents
```

#### 2. Unnecessary Complexity
```java
// Your code - tracking front/back pointers
int front = 0;
int back = k - 1;
// ... later
currentMax -= nums[front];
front++;
back++;
currentMax += nums[back];

// Cleaner - use index arithmetic
windowSum = windowSum + nums[i] - nums[i - k];
```

#### 3. Redundant Calculations
```java
// Your code
int iterations = nums.length - k;
for (int i = 0; i < iterations; i++)

// Simpler
for (int i = k; i < nums.length; i++)
```

## Complexity Analysis
- **Time Complexity**: O(n) - Single pass through the array
- **Space Complexity**: O(1) - Only using a few variables

## Common Pitfalls

### 1. Integer Overflow
For very large sums, consider using `long`:
```java
long windowSum = 0;  // Safer for large numbers
```

### 2. Edge Cases
- k = 1 (single element window)
- k = n (entire array)
- All negative numbers
- Array with one element

### 3. Precision Issues
Always cast to double before division:
```java
// Wrong - integer division
return maxSum / k;  

// Correct
return (double) maxSum / k;
```

## Pattern Recognition

This problem demonstrates the **Fixed Size Sliding Window** pattern:
- Window size is given (k)
- Slide by exactly one position each time
- Update by removing old element and adding new element
- Track optimal value (max/min) across all windows

## Similar Problems
1. **Minimum Size Subarray Sum** - Variable window size
2. **Sliding Window Maximum** - Find max element (not sum) in each window
3. **Find K-Length Substrings With No Repeated Characters** - Fixed window with constraints
4. **Grumpy Bookstore Owner** - Fixed window optimization