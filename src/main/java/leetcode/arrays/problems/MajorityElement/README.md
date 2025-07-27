# Majority Element

**LeetCode Problem**: [169. Majority Element](https://leetcode.com/problems/majority-element/)

## Problem Description

Given an array `nums` of size `n`, return the majority element.

The majority element is the element that appears **more than** `⌊n / 2⌋` times. You may assume that the majority element **always exists** in the array.

### Example 1:
```
Input: nums = [3,2,3]
Output: 3
```

### Example 2:
```
Input: nums = [2,2,1,1,1,2,2]
Output: 2
```

### Constraints:
- `n == nums.length`
- `1 <= n <= 5 * 10^4`
- `-10^9 <= nums[i] <= 10^9`

### Follow-up:
Could you solve the problem in linear time and in O(1) space?

## Approaches

### Approach 1: HashMap Frequency Counting (Straightforward)

**Key Insight**: Count frequency of each element and return the one with count > n/2.

**Algorithm**:
1. Use HashMap to count frequency of each element
2. Return the element with frequency > n/2

**Pros**: Easy to understand and implement
**Cons**: O(n) space complexity

### Approach 2: Boyer-Moore Majority Vote Algorithm (Optimal)

**Key Insight**: The majority element will "survive" a voting process where different elements cancel each other out.

**Algorithm**:
1. Maintain a candidate and count
2. If count is 0, set current element as candidate
3. If current element equals candidate, increment count; otherwise decrement
4. The final candidate is the majority element

**Pros**: O(1) space, O(n) time
**Cons**: More complex to understand

### Approach 3: Sorting (Alternative)

**Algorithm**:
1. Sort the array
2. Return the element at index n/2

**Pros**: Simple logic
**Cons**: O(n log n) time complexity

## Time & Space Complexity

### HashMap Approach:
- **Time Complexity**: O(n) - single pass through array
- **Space Complexity**: O(n) - worst case all elements are unique

### Boyer-Moore Algorithm:
- **Time Complexity**: O(n) - single pass through array
- **Space Complexity**: O(1) - only constant extra space

### Sorting Approach:
- **Time Complexity**: O(n log n) - sorting dominates
- **Space Complexity**: O(1) - if in-place sorting

## Key Patterns

1. **Frequency Counting**: Using HashMap to track element occurrences
2. **Majority Vote**: Boyer-Moore algorithm for space-optimal solution
3. **Mathematical Property**: Majority element appears > n/2 times

## Interview Tips

1. **Start with HashMap** approach for clarity
2. **Mention space optimization** with Boyer-Moore
3. **Explain the voting intuition** for Boyer-Moore
4. **Discuss trade-offs** between approaches
5. **Handle edge cases** (single element, all same elements)

## Study Notes

- **Quick Solve**: Completed in 10 minutes using HashMap
- **Pattern Strength**: Strong with HashMap frequency counting problems
- **Confidence**: Can usually get working HashMap solution quickly
- **Alternative Learning**: Should explore Boyer-Moore algorithm for space optimization
- **Application**: Good foundation for frequency-based problems

## Related Problems

- Majority Element II (229) - find elements appearing > n/3 times
- Find All Numbers Disappeared in an Array (448)
- Single Number (136) - uses XOR properties