# Container With Most Water

## Problem Description
You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `ith` line are `(i, 0)` and `(i, height[i])`.

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

**Notice**: You may not slant the container.

## Approach: Two Pointers

### Key Insight
The area formed between two lines is determined by:
- **Width**: The distance between the two lines (right - left)
- **Height**: The minimum of the two heights (water would overflow from the shorter side)

Formula: `Area = width × min(height[left], height[right])`

### Algorithm Strategy
1. Start with the widest container (pointers at first and last positions)
2. To potentially find a larger area, we need to move the pointer pointing to the shorter line
3. Why? Moving the shorter line might find a taller line, increasing area. Moving the taller line only decreases width without potential height gain.

### Implementation
```java
public int maxArea(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int maxArea = 0;
    
    while (left < right) {
        // Calculate current area
        int width = right - left;
        int minHeight = Math.min(height[left], height[right]);
        int currentArea = width * minHeight;
        
        // Update maximum area
        maxArea = Math.max(maxArea, currentArea);
        
        // Move the pointer pointing to shorter line
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxArea;
}
```

## Complexity Analysis
- **Time Complexity**: O(n) - Single pass through the array
- **Space Complexity**: O(1) - Only using two pointers

## Example Walkthrough

### Example 1: `height = [1,8,6,2,5,4,8,3,7]`

```
Initial: left=0 (h=1), right=8 (h=7)
Area = 8 × min(1,7) = 8 × 1 = 8

Step 1: Move left (shorter)
left=1 (h=8), right=8 (h=7)
Area = 7 × min(8,7) = 7 × 7 = 49 ✓ (Maximum!)

Step 2: Move right (shorter)
left=1 (h=8), right=7 (h=3)
Area = 6 × min(8,3) = 6 × 3 = 18

Continue until pointers meet...
```

## Key Patterns to Remember
1. **Two pointers from opposite ends** - Common for optimization problems on arrays
2. **Greedy choice** - Always move the pointer that limits the current solution
3. **Width vs Height tradeoff** - As width decreases, we need height to increase for larger area