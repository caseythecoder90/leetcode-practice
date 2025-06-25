# Min Cost Climbing Stairs - Cheat Sheet

## Problem Pattern Recognition

| Keyword | Indicator | DP Type |
|---------|-----------|---------|
| "minimum cost" | Optimization | Decision Making DP |
| "climb 1 or 2 steps" | Choices at each step | Pattern 2 |
| "start at 0 or 1" | Multiple starting points | Base case variation |

## Quick Solution Template

### Standard Bottom-Up DP
```java
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int[] dp = new int[n + 1];
    
    // Base cases: can start at step 0 or 1 for free
    dp[0] = 0;
    dp[1] = 0;
    
    // Fill DP table
    for (int i = 2; i <= n; i++) {
        dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
    }
    
    return dp[n]; // Top is at position n
}
```

### Space-Optimized O(1)
```java
public int minCostClimbingStairs(int[] cost) {
    int prev2 = 0;  // dp[i-2]
    int prev1 = 0;  // dp[i-1]
    
    for (int i = 2; i <= cost.length; i++) {
        int current = Math.min(prev1 + cost[i-1], prev2 + cost[i-2]);
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

### Recursive + Memoization
```java
public int minCostClimbingStairs(int[] cost) {
    Map<Integer, Integer> memo = new HashMap<>();
    return helper(cost, cost.length, memo);
}

private int helper(int[] cost, int step, Map<Integer, Integer> memo) {
    if (step <= 1) return 0;
    if (memo.containsKey(step)) return memo.get(step);
    
    int result = Math.min(
        helper(cost, step - 1, memo) + cost[step - 1],
        helper(cost, step - 2, memo) + cost[step - 2]
    );
    memo.put(step, result);
    return result;
}
```

## Core Components

### State Definition
```java
dp[i] = minimum cost to reach position i
```

### Recurrence Relation
```java
dp[i] = min(
    dp[i-1] + cost[i-1],  // Come from step i-1, pay cost[i-1]
    dp[i-2] + cost[i-2]   // Come from step i-2, pay cost[i-2]
)
```

### Base Cases
```java
dp[0] = 0;  // Can start at step 0 for free
dp[1] = 0;  // Can start at step 1 for free
```

### Final Answer
```java
return dp[n];  // n = cost.length (top is beyond last step)
```

## Key Insights

### Mental Model
- **Think**: "Cost to REACH each position"
- **Not**: "Cost to LEAVE each position"

### Cost Array Understanding
```java
cost[i] = what you pay to LEAVE step i (not to arrive at it)
```

### Position vs Step
```java
// If cost.length = 3:
Steps:     [0] [1] [2]   TOP
Positions:  0   1   2    3
```

## Common Mistakes & Fixes

| Mistake | Wrong Code | Correct Code |
|---------|------------|--------------|
| Wrong cost indexing | `dp[i-1] + cost[i]` | `dp[i-1] + cost[i-1]` |
| Wrong base cases | `dp[0] = cost[0]` | `dp[0] = 0` |
| Wrong final answer | `return dp[n-1]` | `return dp[n]` |
| Off-by-one in loop | `i < n` | `i <= n` |

## Debugging Checklist

### Quick Verification
```java
// For cost = [10, 15, 20], answer should be 15
// DP table should be: [0, 0, 10, 15]

// For cost = [1, 100, 1, 1], answer should be 3  
// DP table should be: [0, 0, 1, 2, 2]
```

### Trace Template
```java
System.out.println("Step " + i + ": min(" + 
    (dp[i-1] + cost[i-1]) + ", " + (dp[i-2] + cost[i-2]) + 
    ") = " + dp[i]);
```

## Time/Space Complexity

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Bottom-up DP | O(n) | O(n) | Standard approach |
| Space-optimized | O(n) | O(1) | Interview favorite |
| Top-down + memo | O(n) | O(n) | Recursion stack + memo |

## Edge Cases

```java
// Minimum input
cost = [1, 2] → answer = 1

// All same values
cost = [5, 5, 5] → answer = 5

// Alternating high/low
cost = [1, 100, 1, 100] → answer = 2

// Large jumps beneficial
cost = [100, 1, 1, 100] → answer = 2
```

## Related Problems

| Problem | Similarity | Key Difference |
|---------|------------|----------------|
| Climbing Stairs #70 | Same recurrence | Count ways vs minimize cost |
| House Robber #198 | Decision making | Adjacent constraint vs step limit |
| Fibonacci #509 | Linear recurrence | No array input |

## Pattern Variations

### If you could climb 1, 2, or 3 steps:
```java
dp[i] = Math.min(
    dp[i-1] + cost[i-1],
    Math.min(dp[i-2] + cost[i-2], dp[i-3] + cost[i-3])
);
```

### If you had to start at step 0:
```java
dp[0] = 0;     // Only one starting point
dp[1] = cost[0]; // Must come from step 0
```

### If different step costs:
```java
dp[i] = Math.min(
    dp[i-1] + stepCost[1],    // Cost to take 1 step
    dp[i-2] + stepCost[2]     // Cost to take 2 steps  
);
```

## Interview Tips

### What to Say
1. **"This is a decision-making DP problem"**
2. **"At each step, I can climb 1 or 2 steps"**
3. **"I'll track minimum cost to reach each position"**
4. **"Base cases: can start at step 0 or 1 for free"**

### Code Interview Template
```java
public int minCostClimbingStairs(int[] cost) {
    // State: dp[i] = min cost to reach position i
    int n = cost.length;
    
    // Base cases: can start at 0 or 1 for free
    int prev2 = 0, prev1 = 0;
    
    // Fill from position 2 to n (top)
    for (int i = 2; i <= n; i++) {
        int current = Math.min(
            prev1 + cost[i-1],  // From previous step
            prev2 + cost[i-2]   // From two steps back
        );
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

### Time/Space Analysis
- **Time**: O(n) - single pass through positions
- **Space**: O(1) - only tracking last 2 values
- **Optimization**: Space optimized from O(n) to O(1)

## Quick Memory Aids

### The REACH Acronym
- **R**ecurrence: min of two options
- **E**ach position represents cost to reach it
- **A**dd cost of step you're leaving from
- **C**onsider 1-step and 2-step paths
- **H**andle free starting positions

### Visual Memory
```
Position:  0   1   2   3   TOP
Cost:     10  15  20   ?    ?
DP:        0   0  10  15   ← Answer

Think: "What's cheapest way to GET HERE?"
```

### Code Structure Memory
```
1. Initialize base cases (free starts)
2. Loop from position 2 to n  
3. Take min of two previous positions + their costs
4. Return final position (the top)
```

## Final Checklist

Before submitting:
- [ ] Using correct state definition (cost to reach)
- [ ] Base cases are dp[0] = dp[1] = 0
- [ ] Adding cost[i-1] when coming from position i-1
- [ ] Loop goes to i <= n (not i < n)
- [ ] Returning dp[n] (the top position)
- [ ] Handling edge case where cost.length >= 2

## One-Liner Summary
**"Find minimum cost to reach the top by deciding at each position whether to come from 1 or 2 steps back, considering the cost to leave the previous step."**