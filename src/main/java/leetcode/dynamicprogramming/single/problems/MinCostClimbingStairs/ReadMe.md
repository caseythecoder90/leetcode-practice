# Min Cost Climbing Stairs

**LeetCode Problem #746** | **Easy**

## Problem Description

You are given an integer array `cost` where `cost[i]` is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.

You can either start from the step with index 0, or the step with index 1.

Return the minimum cost to reach the top of the floor.

### Examples

**Example 1:**
```
Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1.
- Pay 15 and climb two steps to reach the top.
The total cost is 15.
```

**Example 2:**
```
Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0.
- Pay 1 and climb two steps to reach index 2.
- Pay 1 and climb two steps to reach index 4.
- Pay 1 and climb two steps to reach index 6.
- Pay 1 and climb one step to reach index 7.
- Pay 1 and climb two steps to reach index 9.
- Pay 1 and climb one step to reach the top.
The total cost is 6.
```

### Constraints
- `2 <= cost.length <= 1000`
- `0 <= cost[i] <= 999`

## Pattern Recognition

This is a **Pattern 2: Decision Making** dynamic programming problem.

**Key Indicators:**
- ✅ **"Minimum cost"** → Optimization problem
- ✅ **Choices at each step** → From any step, can climb 1 or 2 steps
- ✅ **Optimal substructure** → Optimal way to reach step i depends on optimal way to reach previous steps
- ✅ **Overlapping subproblems** → Same steps visited multiple times in different paths

## Solution Approaches

### Approach 1: Bottom-Up DP (Recommended)

**Core Insight**: At each position, we want to know the minimum cost to reach that position.

**State Definition**: `dp[i] = minimum cost to reach step i`

**Key Understanding**:
- The "top" is position `n` (beyond the last step)
- We can start at step 0 or step 1 for free
- From step i, we pay `cost[i]` to climb 1 or 2 steps

**Recurrence Relation**:
```
dp[i] = min(
    dp[i-1] + cost[i-1],  // Come from step i-1, pay its cost
    dp[i-2] + cost[i-2]   // Come from step i-2, pay its cost  
)
```

**Base Cases**:
- `dp[0] = 0` (can start at step 0 for free)
- `dp[1] = 0` (can start at step 1 for free)

**Time Complexity**: O(n)
**Space Complexity**: O(n), can be optimized to O(1)

### Approach 2: Top-Down DP (Recursive + Memoization)

**Recursive Insight**: To find minimum cost to reach the top, consider coming from step n-1 or n-2.

**Recurrence**:
```
minCost(step) = 0                               if step <= 1 (base case)
              = min(minCost(step-1) + cost[step-1], 
                    minCost(step-2) + cost[step-2])  otherwise
```

**Time Complexity**: O(n)
**Space Complexity**: O(n) for memoization + O(n) for recursion stack

### Approach 3: Space-Optimized DP

Since we only need the last 2 values, we can optimize space to O(1).

## Detailed Walkthrough

### Example: cost = [10, 15, 20]

Let's trace through the bottom-up approach:

**Step 1: Initialize**
```
cost = [10, 15, 20]
n = 3, so we need dp[0] through dp[3]
dp = [?, ?, ?, ?]
```

**Step 2: Base Cases**
```
dp[0] = 0  (can start at step 0 for free)
dp[1] = 0  (can start at step 1 for free)
dp = [0, 0, ?, ?]
```

**Step 3: Fill DP Table**

**For dp[2]** (cost to reach step 2):
- Option 1: Come from step 1 → `dp[1] + cost[1] = 0 + 15 = 15`
- Option 2: Come from step 0 → `dp[0] + cost[0] = 0 + 10 = 10`
- `dp[2] = min(15, 10) = 10`

**For dp[3]** (cost to reach top):
- Option 1: Come from step 2 → `dp[2] + cost[2] = 10 + 20 = 30`
- Option 2: Come from step 1 → `dp[1] + cost[1] = 0 + 15 = 15`
- `dp[3] = min(30, 15) = 15`

**Final DP table**: `[0, 0, 10, 15]`
**Answer**: `dp[3] = 15`

### Why This Works

**Visual representation**:
```
Steps:    [0]  [1]  [2]   TOP
Cost:     10   15   20     
Minimum:   0    0   10    15

Path taken: Start at step 1 (free) → pay 15 → climb 2 steps → reach TOP
Total cost: 15
```

## Common Mistakes and How to Avoid Them

### Mistake 1: Wrong State Definition
❌ **Wrong**: `dp[i] = minimum cost starting from step i`
✅ **Correct**: `dp[i] = minimum cost to reach step i`

The difference is crucial - we want to know the cost to GET TO a position, not the cost to start FROM it.

### Mistake 2: Confusing Array Indexing
❌ **Wrong**: `dp[i] = min(dp[i-1] + cost[i], dp[i-2] + cost[i-1])`
✅ **Correct**: `dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])`

When coming from step `i-1`, we pay `cost[i-1]` (the cost of that step).

### Mistake 3: Wrong Base Cases
❌ **Wrong**: `dp[0] = cost[0], dp[1] = cost[1]`
✅ **Correct**: `dp[0] = 0, dp[1] = 0`

We can start at either step 0 or step 1 for free.

### Mistake 4: Off-by-One in Final Answer
❌ **Wrong**: Return `dp[n-1]`
✅ **Correct**: Return `dp[n]`

The "top" is beyond the last step, so it's at position `n`.

## Edge Cases to Consider

1. **Minimum input**: `cost = [1, 2]` → Answer: 1
2. **All same costs**: `cost = [5, 5, 5, 5]` → Answer: 10
3. **Alternating high/low**: `cost = [1, 100, 1, 100]` → Answer: 2
4. **Decreasing costs**: `cost = [100, 50, 25, 10]` → Answer: 35

## Space Optimization

Since we only need the last 2 DP values, we can optimize space:

```java
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int prev2 = 0;  // dp[i-2]
    int prev1 = 0;  // dp[i-1]
    
    for (int i = 2; i <= n; i++) {
        int current = Math.min(prev1 + cost[i-1], prev2 + cost[i-2]);
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

## Related Problems

This problem teaches the fundamental **decision-making DP pattern**. Once you master it, these become easier:

1. **House Robber** (LeetCode #198) - Similar decision pattern
2. **Climbing Stairs** (LeetCode #70) - Counting instead of optimization
3. **Fibonacci Numbers** (LeetCode #509) - Basic recurrence relation
4. **Decode Ways** (LeetCode #91) - More complex decision making

## Key Takeaways

1. **State Definition is Critical**: Spend time getting `dp[i]` definition exactly right
2. **Think "Cost to Reach" not "Cost to Leave"**: This mental model prevents confusion
3. **Base Cases Matter**: We can start at step 0 or 1 for free
4. **The Top is Beyond**: Position `n` represents reaching beyond the last step
5. **Decision Pattern**: `min(option1, option2)` where options are different ways to reach current state

## Practice Strategy

1. **Start with the recursive solution** to understand the problem structure
2. **Add memoization** to see how subproblems overlap
3. **Convert to bottom-up** for optimal space/time
4. **Trace small examples by hand** to build intuition
5. **Practice the space-optimized version** for interviews

Master this problem and you'll have a solid foundation for all decision-making DP problems!