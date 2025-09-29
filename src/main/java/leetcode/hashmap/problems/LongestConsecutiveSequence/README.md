# Longest Consecutive Sequence

## Problem Description
Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence.

**Constraint**: You must write an algorithm that runs in O(n) time.

## Examples

### Example 1:
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

### Example 2:
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explanation: The longest consecutive sequence is [0, 1, 2, 3, 4, 5, 6, 7, 8].
```

### Example 3:
```
Input: nums = [1,0,1,2]
Output: 3
Explanation: The longest consecutive sequence is [0, 1, 2].
```

## Approach Analysis

### Your Initial Approach (Sorting)
- **Time Complexity**: O(n log n) due to sorting
- **Space Complexity**: O(1)
- **Issue**: Doesn't meet the O(n) time requirement

### Optimized HashSet Approach
The key insight is to use a HashSet to achieve O(1) lookups:

1. **Store all numbers in a HashSet** for O(1) lookup
2. **Identify sequence starts**: A number is a sequence start if (num - 1) is not in the set
3. **Build sequences**: From each start, count consecutive numbers
4. **Track maximum length**

### Why This Works in O(n)
- Building HashSet: O(n)
- Finding sequences: Each number is visited at most twice (once to check if it's a start, once when building a sequence)
- Total: O(n)

## Algorithm Steps

1. Handle edge cases (empty array)
2. Create HashSet from array (removes duplicates automatically)
3. For each number in the set:
   - Check if it's a sequence start (num - 1 not in set)
   - If yes, count consecutive numbers from this start
   - Update maximum length
4. Return the maximum length found

## Complexity Analysis

### Time Complexity: O(n)
- Creating HashSet: O(n)
- Iterating through set: O(n)
- Inner while loop: Each element visited at most once across all iterations
- Total: O(n)

### Space Complexity: O(n)
- HashSet stores up to n unique elements

## Key Insights

1. **HashSet eliminates duplicates** automatically
2. **Sequence start identification** prevents redundant counting
3. **Each number is part of exactly one sequence**
4. **The algorithm only builds sequences from their starting points**

## Common Pitfalls

1. **Not checking for sequence starts**: Would lead to O(n²) time
2. **Not handling duplicates**: Could cause incorrect counts
3. **Using sorting**: Violates the O(n) requirement

## Related Problems

- Find All Numbers Disappeared in an Array
- First Missing Positive
- Array Nesting
- Longest Increasing Subsequence (different - requires order preservation)