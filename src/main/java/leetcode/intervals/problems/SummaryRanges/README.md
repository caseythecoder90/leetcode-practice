# 228. Summary Ranges

## Problem Description
Given a sorted unique integer array `nums`, return the smallest sorted list of ranges that cover all the numbers in the array exactly. Each element of `nums` is covered by exactly one of the ranges.

Each range `[a,b]` in the list should be output as:
- `"a->b"` if a != b
- `"a"` if a == b

## Examples
```
Input: nums = [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]

Input: nums = [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]
```

## Solution Approaches

### Approach 1: Single Pass with Start Tracking
**Time Complexity:** O(n)
**Space Complexity:** O(1) excluding output

The key insight is to identify where consecutive sequences break:
1. Track the start of each range
2. Move through the array, checking if next element is consecutive
3. When sequence breaks (or array ends), format and add the range
4. Update start to begin next range

### Approach 2: While Loop (More Explicit)
Similar to Approach 1 but uses nested while loops for clarity:
- Outer loop processes each range
- Inner loop finds the end of consecutive sequence

## Common Mistakes to Avoid

1. **Off-by-one errors**: Be careful with array bounds when checking `nums[i+1]`
2. **Forgetting edge cases**: Empty array, single element, all consecutive numbers
3. **Complex tracking**: Using too many variables (left, right, currentNum) when simpler tracking suffices
4. **String formatting**: Remember to use `->` for ranges, not just `-`

## Why Your Solution Took Longer

Your approach was conceptually correct but had unnecessary complexity:
- Tracking `currentNum`, `left`, and `right` separately
- Updating multiple variables in the inner loop
- Complex condition checking (`right == i + 1`)

The cleaner approach:
- Just track where each range starts
- Use the current index to know where it ends
- Simpler formatting logic

## Interview Tips

1. **Start simple**: Begin with the cleanest approach before optimizing
2. **Test incrementally**: Check your logic with simple cases first
3. **Variable naming**: Use clear names like `start` instead of `left`
4. **Pattern recognition**: This is a "group consecutive elements" pattern - common in array problems

## Practice Suggestions

Don't be discouraged by the 32 minutes! This problem tests:
- Array traversal
- Boundary conditions
- String manipulation

Similar problems to practice:
- Missing Ranges (LeetCode 163)
- Merge Intervals (LeetCode 56)
- Insert Interval (LeetCode 57)

The key is recognizing the pattern: "identify consecutive sequences in sorted arrays."