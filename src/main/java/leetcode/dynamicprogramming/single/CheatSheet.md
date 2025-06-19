# Single-Dimensional DP - Cheat Sheet

## Quick Problem Recognition

| Problem Type | Keywords | State Definition | Recurrence Pattern |
|-------------|----------|------------------|-------------------|
| Fibonacci-like | "nth term", "sequence" | `dp[i]` = ith term | `dp[i] = dp[i-1] + dp[i-2]` |
| Counting Paths | "number of ways", "how many" | `dp[i]` = ways to reach i | `dp[i] = sum(ways_from_prev)` |
| Optimization | "maximum", "minimum", "best" | `dp[i]` = optimal value at i | `dp[i] = opt(choice1, choice2)` |
| Decision | "can/cannot", "possible" | `dp[i]` = best decision at i | `dp[i] = decide(take, skip)` |

## Core Templates

### 1. Basic Linear DP Template
```java
public int solve(int[] nums) {
    int n = nums.length;
    if (n == 0) return 0;
    if (n == 1) return baseCase1;
    
    int[] dp = new int[n];
    dp[0] = baseCase0;
    dp[1] = baseCase1;
    
    for (int i = 2; i < n; i++) {
        dp[i] = recurrenceFunction(dp, nums, i);
    }
    
    return dp[n-1];
}
```

### 2. Space-Optimized Template (O(1) space)
```java
public int solve(int[] nums) {
    int n = nums.length;
    if (n <= 1) return baseCase;
    
    int prev2 = baseCase0;  // dp[i-2]
    int prev1 = baseCase1;  // dp[i-1]
    
    for (int i = 2; i < n; i++) {
        int current = recurrenceFunction(prev1, prev2, nums[i]);
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

## Common Recurrence Patterns

### Pattern 1: Fibonacci-like (Linear Combination)
```java
// Basic Fibonacci
dp[i] = dp[i-1] + dp[i-2];

// Tribonacci  
dp[i] = dp[i-1] + dp[i-2] + dp[i-3];

// Climbing Stairs (1 or 2 steps)
dp[i] = dp[i-1] + dp[i-2];

// Climbing Stairs (1, 2, or 3 steps)
dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
```

### Pattern 2: Max/Min Decision
```java
// House Robber
dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);

// Min Cost Climbing Stairs
dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);

// Maximum Subarray (Kadane's)
dp[i] = Math.max(nums[i], dp[i-1] + nums[i]);
```

### Pattern 3: Counting with Conditions
```java
// Decode Ways
if (s.charAt(i-1) != '0') {
    dp[i] += dp[i-1];
}
if (i >= 2 && isValid(s.substring(i-2, i))) {
    dp[i] += dp[i-2];
}

// Word Break (boolean DP)
for (int j = 0; j < i; j++) {
    if (dp[j] && wordDict.contains(s.substring(j, i))) {
        dp[i] = true;
        break;
    }
}
```

## Problem-Specific Templates

### Fibonacci/Tribonacci Numbers
```java
public int fibonacci(int n) {
    if (n <= 1) return n;
    
    int prev2 = 0, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}

public int tribonacci(int n) {
    if (n == 0) return 0;
    if (n <= 2) return 1;
    
    int prev3 = 0, prev2 = 1, prev1 = 1;
    for (int i = 3; i <= n; i++) {
        int current = prev1 + prev2 + prev3;
        prev3 = prev2;
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}
```

### House Robber Pattern
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    int prev2 = nums[0];
    int prev1 = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++) {
        int current = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}
```

### Climbing Stairs Pattern
```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    
    int prev2 = 1, prev1 = 2;
    for (int i = 3; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}
```

### Coin Change (Unbounded Knapsack in 1D)
```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);  // Initialize with impossible value
    dp[0] = 0;  // Base case
    
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    
    return dp[amount] > amount ? -1 : dp[amount];
}
```

## Base Case Patterns

### Common Base Cases
```java
// Fibonacci-like problems
dp[0] = 0; dp[1] = 1;

// Climbing stairs  
dp[0] = 1; dp[1] = 1;  // or dp[0] = 1; dp[1] = 2;

// House robber
dp[0] = nums[0]; dp[1] = Math.max(nums[0], nums[1]);

// Decode ways
dp[0] = 1; dp[1] = s.charAt(0) != '0' ? 1 : 0;

// Word break
dp[0] = true;  // Empty string can always be segmented
```

## Debugging Checklist

### 1. State Definition ✓
- [ ] Is `dp[i]` clearly defined?
- [ ] Does it represent what you think it represents?

### 2. Base Cases ✓
- [ ] Are all base cases covered?
- [ ] Do base cases handle edge inputs (n=0, n=1)?

### 3. Recurrence Relation ✓
- [ ] Are all possible transitions considered?
- [ ] Is the logic correct for each transition?

### 4. Loop Bounds ✓
- [ ] Does the loop start from the right index?
- [ ] Does it end at the right index?
- [ ] Are array accesses within bounds?

## Space Optimization Guide

### When Can You Optimize?
✅ **Can optimize** when `dp[i]` only depends on constant number of previous states
❌ **Cannot optimize** when you need to access arbitrary previous states

### How to Optimize
1. **Identify dependencies**: What previous states does `dp[i]` need?
2. **Use variables**: Replace array with individual variables
3. **Update pattern**: Update variables in correct order

### Example: Fibonacci O(n) → O(1)
```java
// Before: O(n) space
int[] dp = new int[n+1];
dp[0] = 0; dp[1] = 1;
for (int i = 2; i <= n; i++) {
    dp[i] = dp[i-1] + dp[i-2];
}

// After: O(1) space  
int prev2 = 0, prev1 = 1;
for (int i = 2; i <= n; i++) {
    int current = prev1 + prev2;
    prev2 = prev1;
    prev1 = current;
}
```

## Time Complexity Reference

| Problem Type | Time Complexity | Space Complexity | Space Optimized |
|-------------|-----------------|------------------|-----------------|
| Fibonacci | O(n) | O(n) | O(1) |
| Climbing Stairs | O(n) | O(n) | O(1) |
| House Robber | O(n) | O(n) | O(1) |
| Coin Change | O(n × m) | O(n) | O(n) |
| Word Break | O(n² × m) | O(n) | O(n) |
| Decode Ways | O(n) | O(n) | O(1) |

*n = input size, m = number of coins/words*

## Problem Solving Steps

1. **Identify Pattern** 
   - Linear sequence? → Fibonacci-like
   - Optimization? → Max/Min decision
   - Counting? → Sum of ways

2. **Define State**
   - `dp[i]` = ?
   - Be specific and clear

3. **Find Recurrence**
   - What choices at position i?
   - How do choices affect previous states?

4. **Set Base Cases**
   - What are the simplest subproblems?
   - Handle edge cases (empty, single element)

5. **Implement & Test**
   - Start with array version
   - Optimize space if possible
   - Test with small examples

## Quick Templates by Problem Count

### Depends on 1 Previous State
```java
dp[i] = f(dp[i-1], nums[i]);
```

### Depends on 2 Previous States  
```java
dp[i] = f(dp[i-1], dp[i-2], nums[i]);
```

### Depends on 3 Previous States
```java
dp[i] = f(dp[i-1], dp[i-2], dp[i-3], nums[i]);
```

### Depends on All Previous States
```java
for (int j = 0; j < i; j++) {
    dp[i] = opt(dp[i], f(dp[j], nums, i, j));
}
```