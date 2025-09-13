# Container With Most Water - Detailed Study Guide

## Problem Understanding

### Visual Representation
```
height = [1,8,6,2,5,4,8,3,7]

Height
  8 |    █              █
  7 |    █              █     █
  6 |    █  █           █     █
  5 |    █  █     █     █     █
  4 |    █  █     █  █  █     █
  3 |    █  █     █  █  █  █  █
  2 |    █  █  █  █  █  █  █  █
  1 | █  █  █  █  █  █  █  █  █
    +---------------------------
      0  1  2  3  4  5  6  7  8  (indices)
```

### Key Observations
1. **Container Rules**:
   - Water level is limited by the shorter of the two lines
   - Width is the distance between the two lines
   - Area = width × min(left_height, right_height)

2. **Why Two Pointers Works**:
   - Starting from widest possible container guarantees we explore maximum width
   - Moving inward reduces width, so we need to find taller lines to compensate
   - Always move the shorter line because it's the limiting factor

## Detailed Algorithm Walkthrough

### Step-by-Step Execution Trace

```
Initial Array: [1,8,6,2,5,4,8,3,7]
               L                 R

Step 0: left=0(h=1), right=8(h=7)
        width = 8-0 = 8
        height = min(1,7) = 1
        area = 8×1 = 8
        maxArea = 8
        Move left (1 < 7)

Step 1: left=1(h=8), right=8(h=7)
           L                 R
        width = 8-1 = 7
        height = min(8,7) = 7
        area = 7×7 = 49
        maxArea = 49 ✓
        Move right (8 > 7)

Step 2: left=1(h=8), right=7(h=3)
           L              R
        width = 7-1 = 6
        height = min(8,3) = 3
        area = 6×3 = 18
        maxArea = 49
        Move right (8 > 3)

Step 3: left=1(h=8), right=6(h=8)
           L           R
        width = 6-1 = 5
        height = min(8,8) = 8
        area = 5×8 = 40
        maxArea = 49
        Move right (8 = 8, either works)

Step 4: left=1(h=8), right=5(h=4)
           L        R
        width = 5-1 = 4
        height = min(8,4) = 4
        area = 4×4 = 16
        maxArea = 49
        Move right (8 > 4)

Step 5: left=1(h=8), right=4(h=5)
           L     R
        width = 4-1 = 3
        height = min(8,5) = 5
        area = 3×5 = 15
        maxArea = 49
        Move right (8 > 5)

Step 6: left=1(h=8), right=3(h=2)
           L  R
        width = 3-1 = 2
        height = min(8,2) = 2
        area = 2×2 = 4
        maxArea = 49
        Move right (8 > 2)

Step 7: left=1(h=8), right=2(h=6)
           LR
        width = 2-1 = 1
        height = min(8,6) = 6
        area = 1×6 = 6
        maxArea = 49
        Move right (8 > 6)

Pointers meet - Algorithm terminates
Final Answer: 49
```

## Why the Greedy Choice Works

### Proof by Contradiction
Consider when we have `left` and `right` pointers:
- If `height[left] < height[right]`, we move `left++`
- Why not move `right` instead?

**Proof**: Any container formed with the current `left` and any position between `left` and `right-1`:
1. Will have width < current width
2. Will have height ≤ height[left] (still limited by the shorter line)
3. Therefore, area will be smaller

By moving the shorter line, we eliminate all these guaranteed-smaller possibilities and explore potentially larger areas.

## Common Pitfalls and Edge Cases

### Pitfall 1: Moving the Wrong Pointer
```java
// WRONG - Moving the taller line
if (height[left] > height[right]) {
    left++;  // This reduces potential unnecessarily
}

// CORRECT - Move the shorter line
if (height[left] < height[right]) {
    left++;  // Explore taller lines on the left
}
```

### Pitfall 2: Incorrect Area Calculation
```java
// WRONG - Using max instead of min
int area = width * Math.max(height[left], height[right]);

// CORRECT - Water overflows from shorter side
int area = width * Math.min(height[left], height[right]);
```

### Edge Cases
1. **Two elements**: `[1,1]` → Area = 1
2. **Ascending array**: `[1,2,3,4,5]` → Best at ends
3. **Descending array**: `[5,4,3,2,1]` → Best at ends
4. **Peak in middle**: `[1,2,3,2,1]` → Explore inward

## Pattern Recognition

### When to Use This Approach
- **Optimization problems** on sorted/unsorted arrays
- Problems involving **pairs** of elements
- When you need to explore **all possible pairs** efficiently
- **Container/trapping** water problems

### Similar Problems
1. **3Sum** - Three pointers variation
2. **Trapping Rain Water** - Different calculation, similar concept
3. **Two Sum II** - Sorted array, find exact sum
4. **Valid Palindrome** - Two pointers from ends

## Interview Tips

### How to Explain Your Approach
1. **Start with brute force**: "We could check all pairs O(n²)"
2. **Identify the pattern**: "Width decreases as we move inward"
3. **Explain the insight**: "Move shorter line to find potentially taller ones"
4. **Justify correctness**: "We never miss the optimal solution"

### Follow-up Questions
- **Q: What if we can slant the container?**
  - A: Problem becomes more complex; might need different approach
  
- **Q: What if there are n containers to place?**
  - A: Dynamic programming or greedy with sorting

- **Q: Can you do better than O(n)?**
  - A: No, we need to examine each element at least once

## Practice Problems (Increasing Difficulty)
1. **Two Sum II** (Easy) - Basic two pointers
2. **3Sum** (Medium) - Extension to three pointers
3. **Trapping Rain Water** (Hard) - Complex water calculation
4. **Container With Most Water** (Medium) - This problem
5. **Largest Rectangle in Histogram** (Hard) - Stack-based approach