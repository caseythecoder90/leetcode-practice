# Combination Sum III

## Problem Description

Find all valid combinations of **k** numbers that sum up to **n** such that the following conditions are true:
- Only numbers 1 through 9 are used
- Each number is used **at most once**
- Return a list of all possible valid combinations

The list of combinations should not contain the same combination twice, and the combinations may be returned in any order.

## Examples

**Example 1:**
```
Input: k = 3, n = 7
Output: [[1,2,4]]
Explanation: 1 + 2 + 4 = 7. There are no other valid combinations.
```

**Example 2:**
```
Input: k = 3, n = 9
Output: [[1,2,6],[1,3,5],[2,3,4]]
Explanation: 
1 + 2 + 6 = 9
1 + 3 + 5 = 9
2 + 3 + 4 = 9
There are no other valid combinations.
```

**Example 3:**
```
Input: k = 4, n = 1
Output: []
Explanation: There are no valid combinations.
The minimum sum you can get with 4 different positive integers is 1+2+3+4 = 10.
```

## Constraints
- `2 <= k <= 9`
- `1 <= n <= 60`

## Solution Approach

This is a classic **backtracking** problem where we need to:
1. Generate all possible combinations of k numbers from digits 1-9
2. Filter combinations that sum to exactly n
3. Avoid duplicates by maintaining order (always choose next number > current)

### Key Insights

1. **Ordering prevents duplicates**: By only choosing numbers greater than the last chosen number, we naturally avoid duplicate combinations like [1,2,4] and [2,1,4]

2. **Early pruning optimizes performance**:
    - If current sum > target, abandon this path
    - If we've chosen too many numbers, abandon this path
    - If current number > remaining sum needed, skip it

3. **Backtracking template**: Choose → Explore → Unchoose

## Algorithm Steps

1. Start with empty combination and try each number 1-9 as potential first choice
2. For each choice, recursively try all valid next choices (numbers greater than current)
3. When we have exactly k numbers, check if sum equals n
4. Use backtracking to explore all possibilities systematically

## Time & Space Complexity

- **Time Complexity**: O(2^9) = O(512) in worst case
    - We're essentially exploring subsets of digits 1-9
    - In practice, much faster due to pruning

- **Space Complexity**: O(k) for recursion depth
    - Maximum recursion depth is k (number of elements in combination)
    - Additional space for storing results

## Implementation Notes

```java
// Key parameters in recursive function:
// - result: stores all valid combinations found
// - current: current combination being built
// - k: target number of elements
// - remainingSum: how much sum we still need
// - start: ensures we only pick numbers > last chosen (avoids duplicates)

private void backtrack(List<List<Integer>> result, List<Integer> current, 
                      int k, int remainingSum, int start)
```

## Common Pitfalls

1. **Reference bug**: Adding `current` directly to result instead of `new ArrayList<>(current)`
2. **Wrong pruning conditions**: Not checking bounds properly
3. **Duplicate handling**: Forgetting to maintain order with `start` parameter
4. **Base case confusion**: Checking sum before checking if we have k elements

## Related Problems

- Combination Sum I (allows repeated use of numbers)
- Combination Sum II (handles duplicates in input array)
- Combinations (generates all k-combinations without sum constraint)
- Subsets (generates all possible subsets)

## Tags
`Backtracking` `Array` `Combinatorics`