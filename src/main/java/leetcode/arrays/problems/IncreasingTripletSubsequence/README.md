# 334. Increasing Triplet Subsequence

**Difficulty**: Medium  
**Topics**: Array, Greedy  
**Companies**: Google, Facebook, Microsoft, Amazon

## Problem Statement

Given an integer array `nums`, return `true` if there exists a triple of indices `(i, j, k)` such that `i < j < k` and `nums[i] < nums[j] < nums[k]`. If no such indices exist, return `false`.

## Examples

### Example 1:
```
Input: nums = [1,2,3,4,5]
Output: true
Explanation: Any triplet where i < j < k is valid.
```

### Example 2:
```
Input: nums = [5,4,3,2,1]
Output: false
Explanation: No triplet exists.
```

### Example 3:
```
Input: nums = [2,1,5,0,4,6]
Output: true
Explanation: One valid triplet is (3,4,5), because nums[3] == 0 < nums[4] == 4 < nums[5] == 6.
```

## Constraints
- `1 <= nums.length <= 5 * 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`

## Follow-up
Could you implement a solution that runs in O(n) time complexity and O(1) space complexity?

## Approach: Greedy Algorithm

### Key Insight
We don't need to track actual indices - just maintain two "candidate" values:
- `first`: Smallest number seen so far
- `second`: Smallest number greater than `first`

If we find any number greater than `second`, we have our triplet!

### Why This Works
The greedy approach works because:
1. We're looking for existence, not specific indices
2. Keeping smallest possible candidates maximizes chances of finding a third element
3. When we update `first` or `second`, we don't lose potential triplets because the relative ordering is preserved

### Algorithm Steps
1. Initialize `first` and `second` to `Integer.MAX_VALUE`
2. For each number in the array:
   - If `num <= first`: Update `first = num`
   - Else if `num <= second`: Update `second = num`
   - Else: Return `true` (found triplet!)
3. Return `false` if no triplet found

### Complexity Analysis
- **Time Complexity**: O(n) - Single pass through the array
- **Space Complexity**: O(1) - Only two variables used

### Example Walkthrough: [2,1,5,0,4,6]
```
num=2: first=2, second=MAX_VALUE
num=1: first=1, second=MAX_VALUE  (1 < 2, update first)
num=5: first=1, second=5          (5 > 1, update second)
num=0: first=0, second=5          (0 < 1, update first)
num=4: first=0, second=4          (4 > 0 but 4 < 5, update second)
num=6: return true                (6 > 4, triplet found!)
```

The valid triplet corresponds to values (1,4,6) where 1 < 4 < 6.

## Common Mistakes
1. **Sorting the array**: This destroys the index relationships required by the problem
2. **Tracking actual indices**: Not necessary for this problem - we only need existence
3. **Using O(n) space**: The greedy approach eliminates need for additional data structures

## Interview Tips
- Explain why sorting doesn't work
- Walk through the greedy logic clearly
- Emphasize that we're finding existence, not actual triplet values
- Mention this is a classic greedy algorithm pattern