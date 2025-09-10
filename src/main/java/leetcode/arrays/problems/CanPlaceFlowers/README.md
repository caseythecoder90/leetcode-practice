# 605. Can Place Flowers

## Problem Description

You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in adjacent plots.

Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n, return true if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule and false otherwise.

## Examples

### Example 1:
```
Input: flowerbed = [1,0,0,0,1], n = 1
Output: true

Visualization:
Index:     0 1 2 3 4
Flowerbed: 1 0 0 0 1
           ^ planted
Can plant at index 2: [1,0,1,0,1] ✓
```

### Example 2:
```
Input: flowerbed = [1,0,0,0,1], n = 2
Output: false

Explanation: Only 1 flower can be planted (at index 2)
Cannot plant 2 flowers without violating adjacency rule
```

## Constraints
- 1 <= flowerbed.length <= 2 * 10^4
- flowerbed[i] is 0 or 1
- There are no two adjacent flowers in flowerbed
- 0 <= n <= flowerbed.length

## Approach

### Key Insight
A flower can be planted at position `i` if and only if:
1. `flowerbed[i] == 0` (current position is empty)
2. `flowerbed[i-1] == 0` or `i == 0` (left neighbor is empty or doesn't exist)  
3. `flowerbed[i+1] == 0` or `i == flowerbed.length-1` (right neighbor is empty or doesn't exist)

### Algorithm Steps
1. **Greedy Approach**: Iterate through the flowerbed from left to right
2. **Check Planting Conditions**: For each empty spot, check if it's safe to plant
3. **Plant and Count**: If safe, plant the flower and increment counter
4. **Early Termination**: Return true immediately if we've planted enough flowers

### Why Greedy Works
The greedy approach (plant whenever possible) is optimal because:
- Planting a flower as early as possible never reduces future opportunities
- Each planted flower only affects its immediate neighbors
- There's no benefit to delaying a valid planting

### Edge Cases to Consider
- Empty flowerbed: `[0,0,0]`
- Single position: `[0]` or `[1]`
- All positions occupied: `[1,0,1,0,1]`
- n = 0 (need to plant 0 flowers)

## Complexity Analysis

- **Time Complexity**: O(n) where n is the length of flowerbed
  - Single pass through the array
  - Early termination possible when target is reached
- **Space Complexity**: O(1) - only using constant extra space

## Pattern Category
This problem belongs to the **Greedy Algorithms** pattern:
- Making locally optimal choices (plant whenever possible)
- Greedy choice leads to globally optimal solution
- No need to consider future states or backtrack