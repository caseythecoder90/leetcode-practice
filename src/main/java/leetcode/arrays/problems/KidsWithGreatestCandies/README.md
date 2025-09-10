# 1431. Kids With the Greatest Number of Candies

## Problem Description

There are n kids with candies. You are given an integer array candies, where each candies[i] represents the number of candies the ith kid has, and an integer extraCandies, denoting the number of extra candies that you have.

Return a boolean array result of length n, where result[i] is true if, after giving the ith kid all the extraCandies, they will have the greatest number of candies among all the kids, or false otherwise.

Note that multiple kids can have the greatest number of candies.

## Examples

### Example 1:
```
Input: candies = [2,3,5,1,3], extraCandies = 3
Output: [true,true,true,false,true]

Explanation: If you give all extraCandies to:
- Kid 1: 2 + 3 = 5 candies (equals max of 5) → true
- Kid 2: 3 + 3 = 6 candies (greater than max of 5) → true  
- Kid 3: 5 + 3 = 8 candies (greater than max of 5) → true
- Kid 4: 1 + 3 = 4 candies (less than max of 5) → false
- Kid 5: 3 + 3 = 6 candies (greater than max of 5) → true
```

### Example 2:
```
Input: candies = [4,2,1,1,2], extraCandies = 1
Output: [true,false,false,false,false]

Explanation: There is only 1 extra candy.
- Kid 1: 4 + 1 = 5 candies (max is 4, so 5 > 4) → true
- All other kids: even with +1 candy, cannot exceed kid 1's total
```

### Example 3:
```
Input: candies = [12,1,12], extraCandies = 10
Output: [true,false,true]

Explanation:
- Kid 1: 12 + 10 = 22 candies (greater than max of 12) → true
- Kid 2: 1 + 10 = 11 candies (less than max of 12) → false
- Kid 3: 12 + 10 = 22 candies (greater than max of 12) → true
```

## Constraints
- n == candies.length
- 2 <= n <= 100
- 1 <= candies[i] <= 100
- 1 <= extraCandies <= 50

## Approach

### Key Insight
For a kid to have the "greatest number of candies" after receiving extra candies:
- `candies[i] + extraCandies >= maxCandies`
- Where `maxCandies` is the current maximum in the array

### Algorithm Steps
1. **Find Maximum**: Scan the array to find the current maximum number of candies
2. **Check Each Kid**: For each kid, check if `candies[i] + extraCandies >= maxCandies`
3. **Build Result**: Create boolean array with the results

### Optimization
Since we're adding the same `extraCandies` to each kid, we can rearrange the inequality:
- `candies[i] + extraCandies >= maxCandies`
- `candies[i] >= maxCandies - extraCandies`

This avoids repeated addition in the loop.

## Complexity Analysis

- **Time Complexity**: O(n)
  - One pass to find maximum: O(n)
  - One pass to build result: O(n)
- **Space Complexity**: O(1) extra space (not counting output array)

## Pattern Category
This problem belongs to the **Arrays** pattern, specifically:
- Array traversal and maximum finding
- Conditional logic based on array elements
- Simple greedy approach (checking each possibility independently)