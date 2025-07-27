# Product of Array Except Self

**LeetCode Problem**: [238. Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/)

## Problem Description

Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all the elements of `nums` except `nums[i]`.

The product of any prefix or suffix of `nums` is **guaranteed** to fit in a **32-bit** integer.

You must write an algorithm that runs in **O(n)** time and without using the **division** operation.

### Example 1:
```
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
```

### Example 2:
```
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
```

### Constraints:
- `2 <= nums.length <= 10^5`
- `-30 <= nums[i] <= 30`
- The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.

### Follow up:
Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)

## Approaches

### Approach 1: Left and Right Products Arrays (Optimal)

**Key Insight**: For each position `i`, the result is the product of all elements to the left of `i` multiplied by the product of all elements to the right of `i`.

**Algorithm**:
1. **First pass**: Calculate left products and store in result array
2. **Second pass**: Calculate right products on-the-fly and multiply with left products

**Mental Model**: Think of it as "what's to my left" × "what's to my right"

### Approach 2: Boyer-Moore Majority Vote (Alternative for Majority Element)

**Note**: This approach is for the Majority Element problem, not Product of Array Except Self. Included for completeness since both problems were solved in the same session.

## Time & Space Complexity

### Left/Right Products Approach:
- **Time Complexity**: O(n) - two passes through the array
- **Space Complexity**: O(1) extra space (not counting output array)

### Alternative Approaches (Educational):
- **Brute Force**: O(n²) time, O(1) space - calculate product for each position
- **Division Method**: O(n) time, O(1) space - but doesn't handle zeros properly and uses division

## Key Patterns

1. **Prefix/Suffix Products**: Computing cumulative products from both directions
2. **Two-Pass Algorithm**: Separate concerns into left and right computations
3. **Space Optimization**: Using output array to store intermediate results

## Common Pitfalls

1. **Variable Mix-ups**: Confusing loop indices (i vs n) in update statements
2. **Zero Handling**: Division approach fails with zeros in the array
3. **Edge Cases**: Arrays with length 2, all zeros, single zero

## Interview Tips

1. **Start with brute force** to show understanding
2. **Explain the insight** about left × right products
3. **Walk through small example** to demonstrate the algorithm
4. **Mention space optimization** using the output array
5. **Discuss alternative approaches** (division method and its limitations)

## Study Notes

- **Review Success**: Problem was solved successfully on review after brief note refresh
- **Common Mistake**: Variable mix-up (i vs n) in update statement cost 5 minutes
- **Improvement Goal**: Learn alternative approaches for more robust understanding
- **Pattern Strength**: Good with prefix/suffix product computations
- **Time Management**: 20 minutes including debug time is reasonable