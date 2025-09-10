# 167. Two Sum II - Input Array Is Sorted

## Problem Description

Given a 1-indexed array of integers `numbers` that is already **sorted in non-decreasing order**, find two numbers such that they add up to a specific target number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return the indices of the two numbers, `index1` and `index2`, **added by one** as an integer array `[index1, index2]` of length 2.

The tests are generated such that there is **exactly one solution**. You may not use the same element twice.

**Your solution must use only constant extra space.**

## Examples

### Example 1:
```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

### Example 2:
```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
```

### Example 3:
```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
```

## Constraints

- `2 <= numbers.length <= 3 * 10⁴`
- `-1000 <= numbers[i] <= 1000`
- `numbers` is sorted in **non-decreasing order**
- `-1000 <= target <= 1000`
- There is **exactly one solution**

## Approach Analysis

### Optimal Solution: Two-Pointers Technique (Your Choice!)
- Use two pointers starting from both ends
- Move based on sum comparison with target
- Time: O(n), Space: O(1)
- Perfect for this problem due to synchronization and storage constraints

### Alternative: Binary Search Approach
- For each element, binary search for the complement
- Time: O(n log n), Space: O(1) but slower
- Viable but not optimal for this problem

### Inefficient: Brute Force
- Check every pair of elements
- Time: O(n²), Space: O(1)
- Too slow for n = 3×10⁴

## Why Two-Pointers Works Here

### Key Insights:
1. **Sorted Array**: Enables efficient searching and pointer movement
2. **Constant Space**: No extra data structures needed
3. **Linear Time**: Single pass through the array
4. **Early Termination**: Can find solution quickly in some cases

### Intuitive Reasoning:
- Start with smallest + largest → get current sum
- If sum < target → need larger numbers → move left pointer right
- If sum > target → need smaller numbers → move right pointer left
- Continue until sum == target

## Algorithm Walkthrough

### Example 1: `numbers = [2,7,11,15]`, `target = 9`
```
Initial state: left=0 (2), right=3 (15)
Sum: 2 + 15 = 17 > 9 → move right left to 11

State: left=0 (2), right=2 (11)
Sum: 2 + 11 = 13 > 9 → move right left to 7

State: left=0 (2), right=1 (7)
Sum: 2 + 7 = 9 == 9 → SUCCESS! Return [1,2]
```

### Example 2: `numbers = [2,3,4]`, `target = 6`
```
Initial state: left=0 (2), right=2 (4)
Sum: 2 + 4 = 6 == 6 → SUCCESS! Return [1,3]
```

### Example 3: `numbers = [-1,0]`, `target = -1`
```
Initial state: left=0 (-1), right=1 (0)
Sum: -1 + 0 = -1 == -1 → SUCCESS! Return [1,2]
```

## Edge Cases to Test

1. **Minimum array size**: `numbers = [1,2]`, `target = 3` → `[1,2]`
2. **Negative numbers**: `numbers = [-3,-1,0,1]`, `target = -2` → `[1,3]`
3. **All positive numbers**: `numbers = [1,3,5,7]`, `target = 8` → `[2,4]`
4. **Duplicates allowed**: `numbers = [3,3]`, `target = 6` → `[1,2]`
5. **Target at edges**: `numbers = [1,2,3,4]`, `target = 5` → `[2,4]`

## Common Mistakes

1. **Off-by-one errors**: Remember to return 1-based indices, not 0-based
2. **Pointer crossing**: Make sure `left < right` in the loop condition
3. **Duplicate elements**: The problem allows duplicates, but ensure we don't use same element twice
4. **Edge case handling**: Handle minimum size arrays correctly
5. **Negative numbers**: Algorithm works perfectly with negatives

## Time Complexity Analysis

- **Best Case**: Target is sum of first and last elements → O(1)
- **Worst Case**: Target requires traversing most of array → O(n)
- **Average Case**: O(n) - we expect to find solution somewhere in the middle
- **Total**: O(n) single pass - can't get better than this!

## Space Complexity: O(1)

- Only using two integer variables for pointers
- No additional data structures
- Perfect for large arrays (n ≤ 3×10⁴)

## Why This Beats Other Approaches

| Approach | Time | Space | Why Better |
|----------|------|-------|------------|
| Brute Force | O(n²) | O(1) | Too slow |
| Hash Map | O(n) | O(n) | Uses extra space (not allowed) |
| Binary Search | O(n log n) | O(1) | Slower |
| Two Pointers | O(n) | O(1) | **Optimal!** |

## Interview Tips

1. **Emphasize constraints**: "Must use O(1) space" means no hash map
2. **Explain reasoning**: Why two-pointers works on sorted array
3. **Handle edge cases**: Always validate input array
4. **1-based indexing**: Don't forget to add 1 to indices
5. **Single solution**: Problem guarantees exactly one answer

## Related Problems

- **1. Two Sum** (original) - Can use hash map, O(n) space
- **15. 3Sum** - Multiple pointers, more complex
- **18. 4Sum** - Even more pointers
- **653. Two Sum IV - Input is a BST** - Tree traversal approach

## Code Quality Considerations

### In Production Code:
- Add input validation for null/empty arrays
- Handle integer overflow (though constraints prevent it here)
- Consider early return for invalid inputs
- Add logging for debugging (in educational versions)

### Performance Notes:
- Algorithm is extremely cache-friendly
- Simple loop with no function calls in tight loop
- Early termination possible in lucky cases
- Minimal memory usage is platform-friendly

## Visualization of Algorithm Flow

```
Target = T
Array = [A, B, C, D, E] (sorted)

Step 1: Check A + E vs T
• If A + E == T: Done! [1,5]
• If A + E < T: Check A + D vs T
• If A + E > T: Check B + E vs T

Step 2: Repeat until we find the pair
• Each step eliminates half the search space
• Guaranteed to find solution (constraint)
• Maximum steps equal to array size
```

This two-pointers approach is textbook optimal for the sorted array two-sum problem and demonstrates why understanding algorithms and data structure relationships is crucial for technical interviews.
