# House Robber (LeetCode #198)

## Problem Understanding

**Core Challenge**: Rob houses in a line to maximize money, but can't rob adjacent houses (security system alert).

**Input**: Array of integers representing money in each house
**Output**: Maximum money that can be robbed
**Constraint**: Cannot rob two adjacent houses

## Step-by-Step Analysis

### Step 1: Understand the Problem
- **What are we optimizing?** Maximum money (optimization problem)
- **What choices do we have at each step?** Rob current house OR skip it
- **What information do we need?** The best solution up to previous houses

### Step 2: Define the State
**Template**: `dp[i]` represents the maximum money that can be robbed from houses 0 to i (inclusive)

**Key insight**: `dp[i]` is NOT about whether we rob house i, but about the best we can do considering all houses from 0 to i.

### Step 3: Find the Recurrence Relation

At house i, we have two choices:
1. **Rob house i**: We get `nums[i] + dp[i-2]` (can't rob adjacent house i-1)
2. **Skip house i**: We get `dp[i-1]` (best we could do without house i)

**Recurrence**: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`

### Step 4: Identify Base Cases

**Base cases are the smallest subproblems we can solve directly:**

- `dp[0] = nums[0]`
    - **Why?** With only one house, the best we can do is rob it
    - **Subproblem**: "What's the max money from just house 0?"

- `dp[1] = max(nums[0], nums[1])`
    - **Why?** With two houses, rob the one with more money
    - **Subproblem**: "What's the max money from houses 0 and 1?"

**Why these are base cases:**
- They don't depend on smaller subproblems
- They can be computed directly
- All other solutions build from these

### Step 5: Determine Fill Order
Bottom-up: `i` from 2 to n-1 (we already handled 0 and 1)

## Visual Example: [2, 7, 9, 3, 1]

```
Houses:    [2, 7, 9, 3, 1]
Indices:    0  1  2  3  4

dp[0] = 2                    // Only house 0: rob it
dp[1] = max(2, 7) = 7        // Houses 0,1: rob house 1 (better)
dp[2] = max(7, 2+9) = 11     // Houses 0,1,2: rob 0 and 2
dp[3] = max(11, 7+3) = 11    // Houses 0,1,2,3: keep previous best
dp[4] = max(11, 11+1) = 12   // Houses 0,1,2,3,4: rob 0,2,4
```

**Decision trace:**
- At house 2: Rob it (9) + best from house 0 (2) = 11 vs. skip it and keep 7 → Choose 11
- At house 3: Rob it (3) + best from house 1 (7) = 10 vs. skip it and keep 11 → Choose 11
- At house 4: Rob it (1) + best from house 2 (11) = 12 vs. skip it and keep 11 → Choose 12

## Implementation Options

### Option 1: Basic DP Array
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    int[] dp = new int[nums.length];
    dp[0] = nums[0];                           // Base case 1
    dp[1] = Math.max(nums[0], nums[1]);        // Base case 2
    
    for (int i = 2; i < nums.length; i++) {
        dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);  // Recurrence
    }
    
    return dp[nums.length - 1];
}
```

### Option 2: Space-Optimized O(1)
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    int prev2 = nums[0];                       // dp[i-2]
    int prev1 = Math.max(nums[0], nums[1]);    // dp[i-1]
    
    for (int i = 2; i < nums.length; i++) {
        int current = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

## Common Mistakes and Solutions

### Mistake 1: Wrong State Definition
❌ **Wrong**: `dp[i]` = maximum money if we rob house i
✅ **Correct**: `dp[i]` = maximum money from houses 0 to i

### Mistake 2: Incorrect Base Cases
❌ **Wrong**: `dp[0] = 0` or `dp[1] = nums[1]`
✅ **Correct**: `dp[0] = nums[0]`, `dp[1] = max(nums[0], nums[1])`

### Mistake 3: Missing Edge Case
❌ **Problem**: Not handling single house case
✅ **Solution**: `if (nums.length == 1) return nums[0];`

## Edge Cases to Test

1. **Single house**: `[5]` → Answer: 5
2. **Two houses**: `[1, 2]` → Answer: 2
3. **All same values**: `[3, 3, 3, 3]` → Answer: 6
4. **Decreasing values**: `[5, 4, 3, 2, 1]` → Answer: 9 (rob 5,3,1)
5. **One large value**: `[1, 20, 1, 1]` → Answer: 20

## Pattern Recognition

**This is a "Decision DP" problem:**
- At each position, make an optimal choice
- Choice affects what we can do next (adjacency constraint)
- Recurrence: `dp[i] = max(take_current + best_compatible, skip_current)`

**Similar Problems:**
- House Robber II (#213) - Houses in circle
- House Robber III (#337) - Houses in binary tree
- Min Cost Climbing Stairs (#746) - Similar decision pattern

## Time/Space Complexity

- **Time**: O(n) - single pass through array
- **Space**: O(n) for DP array, O(1) for optimized version

## Key Takeaways

1. **Base cases handle the smallest solvable subproblems**
2. **State definition is crucial** - be precise about what dp[i] represents
3. **Decision pattern** - consider all choices and their consequences
4. **Adjacency constraints** create dependencies between positions
5. **Space optimization** possible when only recent states matter