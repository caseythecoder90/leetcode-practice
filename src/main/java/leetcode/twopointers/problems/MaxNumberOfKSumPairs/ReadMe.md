# Max Number of K-Sum Pairs

**LeetCode Problem #1679** | **Medium**

## Problem Description

You are given an integer array `nums` and an integer `k`.

In one operation, you can pick two numbers from the array whose sum equals `k` and remove them from the array.

Return the maximum number of operations you can perform on the array.

### Examples

**Example 1:**
```
Input: nums = [1,2,3,4], k = 5
Output: 2
Explanation: Starting with nums = [1,2,3,4]:
- Remove numbers 1 and 4, then nums = [2,3]
- Remove numbers 2 and 3, then nums = []
There are no more pairs that sum up to 5, hence a total of 2 operations.
```

**Example 2:**
```
Input: nums = [3,1,3,4,3], k = 6
Output: 1
Explanation: Starting with nums = [3,1,3,4,3]:
- Remove the first two 3's, then nums = [1,4,3]
There are no more pairs that sum up to k = 6, hence a total of 1 operation.
```

### Constraints
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^9`
- `1 <= k <= 10^9`

## Solution Approaches

### Approach 1: Two Pointers (Optimal)
**Key Insight**: After sorting the array, use two pointers to efficiently find all pairs that sum to k.

**Algorithm**:
1. Sort the array in ascending order
2. Use two pointers: `left` starting at 0, `right` starting at end
3. For each pair:
    - If `nums[left] + nums[right] == k`: Found a pair! Increment count and move both pointers
    - If `nums[left] + nums[right] < k`: Sum too small, move left pointer right
    - If `nums[left] + nums[right] > k`: Sum too large, move right pointer left
4. Continue until pointers meet

**Why this works**: In a sorted array, if the current sum is too small, we need to increase it by moving the left pointer right. If it's too large, we decrease it by moving the right pointer left.

**Time Complexity**: O(n log n) for sorting + O(n) for two pointers = O(n log n)
**Space Complexity**: O(1) if in-place sort, O(n) otherwise

### Approach 2: Hash Map
**Key Insight**: For each number, check if its complement (k - number) exists in the map.

**Algorithm**:
1. Create a frequency map of all numbers
2. For each unique number `num`:
    - If `num + num == k`: Can pair `freq[num] / 2` times
    - Otherwise: Can pair `min(freq[num], freq[k-num])` times
3. Sum all possible pairs

**Time Complexity**: O(n)
**Space Complexity**: O(n)

## When to Use Each Approach

- **Two Pointers**: When you want to minimize space usage and don't mind the O(n log n) sort
- **Hash Map**: When you need optimal time complexity and have space available

## Detailed Walkthrough: Two Pointers Approach

### Example: nums = [1,2,3,4], k = 5

**Step 1**: Sort the array
```
Original: [1,2,3,4]
Sorted:   [1,2,3,4] (already sorted)
```

**Step 2**: Initialize pointers
```
nums = [1,2,3,4]
        ↑     ↑
      left  right
```

**Step 3**: Process pairs
```
Iteration 1:
nums[0] + nums[3] = 1 + 4 = 5 == k ✓
Count = 1, left = 1, right = 2

nums = [1,2,3,4]
          ↑ ↑
        left right

Iteration 2:  
nums[1] + nums[2] = 2 + 3 = 5 == k ✓
Count = 2, left = 2, right = 1

Now left >= right, so we stop.
Final count = 2
```

### Example: nums = [3,1,3,4,3], k = 6

**Step 1**: Sort the array
```
Original: [3,1,3,4,3]
Sorted:   [1,3,3,3,4]
```

**Step 2**: Process with two pointers
```
Initial:
nums = [1,3,3,3,4]
        ↑       ↑
      left    right

Iteration 1:
1 + 4 = 5 < 6, need larger sum → left++

nums = [1,3,3,3,4]
          ↑     ↑
        left  right

Iteration 2:
3 + 4 = 7 > 6, need smaller sum → right--

nums = [1,3,3,3,4]
          ↑   ↑
        left right

Iteration 3:
3 + 3 = 6 == k ✓
Count = 1, left++, right--

nums = [1,3,3,3,4]
            ↑ ↑
          left right

Now left >= right, so we stop.
Final count = 1
```

## Key Implementation Details

### Edge Cases to Handle
1. **Empty array or single element**: Return 0
2. **No valid pairs**: Return 0
3. **All elements the same**: Handle `2*nums[i] == k` case
4. **Large arrays**: Ensure no integer overflow

### Common Mistakes
1. **Not sorting first**: Two pointers only works on sorted arrays
2. **Wrong pointer movement**: Moving wrong pointer when sum doesn't match
3. **Off-by-one errors**: Using `<=` instead of `<` in while condition
4. **Not handling duplicates**: Algorithm naturally handles duplicates correctly

### Optimization Notes
- **Early termination**: If `nums[left] + nums[right] < k` and `nums[right]` is the largest element, we can break early
- **Skip impossible values**: If `nums[left] > k/2` and we haven't found pairs, we can stop

## Complexity Analysis Deep Dive

### Why O(n log n) is Optimal for Two Pointers
- Sorting: O(n log n) - necessary for two pointers to work
- Two pointers traversal: O(n) - each element visited at most once
- Total: O(n log n) dominated by sorting

### Space Complexity Considerations
- **In-place sorting**: O(1) additional space
- **External sorting**: O(n) additional space
- **Two pointers**: O(1) additional space for pointers and counter

## Alternative Problem Variations

This problem is a great foundation for understanding:
1. **Two Sum** - Finding if a pair exists (instead of counting all pairs)
2. **3Sum** - Finding triplets that sum to target
3. **4Sum** - Finding quadruplets that sum to target
4. **Two Sum with multiplicity** - When duplicates have different meanings

## Practice Extensions

After mastering this problem, try:
1. **Modified constraint**: What if you could reuse elements?
2. **Multiple targets**: What if you had multiple target sums?
3. **Weighted pairs**: What if different pairs had different values?
4. **Online version**: What if elements arrived one at a time?

## Key Takeaways

1. **Two pointers is powerful for sorted data**: When you need to find pairs/combinations
2. **Sorting trade-off**: O(n log n) sorting enables O(n) pair finding
3. **Pointer movement logic**: Smaller sum → move left, larger sum → move right
4. **Natural duplicate handling**: Algorithm works correctly with duplicates without special cases

This problem excellently demonstrates the two pointers pattern and is a stepping stone to more complex multi-pointer problems.