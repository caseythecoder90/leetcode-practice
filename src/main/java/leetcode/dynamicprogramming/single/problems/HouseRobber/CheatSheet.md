# House Robber - CheatSheet

## Quick Problem Summary
**Problem**: Rob houses in a line to maximize money, can't rob adjacent houses
**Pattern**: Decision DP with adjacency constraint
**Difficulty**: Medium

## Core Algorithm

### State Definition
```
dp[i] = maximum money from houses 0 to i (inclusive)
```

### Recurrence Relation
```java
dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])
```
**Translation**: "Either skip house i and keep previous best, OR rob house i plus best from two houses ago"

### Base Cases
```java
dp[0] = nums[0]                    // One house: rob it
dp[1] = Math.max(nums[0], nums[1]) // Two houses: rob the better one
```

## Implementation Templates

### Template 1: O(n) Space
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++) {
        dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
    }
    
    return dp[nums.length - 1];
}
```

### Template 2: O(1) Space (Optimized)
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

## Decision Tree Visualization
```
At house i, ask: "Rob it or skip it?"

Rob house i:    nums[i] + dp[i-2]  (can't use i-1)
Skip house i:   dp[i-1]            (keep previous best)
Choose:         Math.max(rob, skip)
```

## Quick Example: [2, 7, 9, 3, 1]
```
Index:  0  1  2  3  4
Houses: 2  7  9  3  1

dp[0] = 2
dp[1] = max(2,7) = 7  
dp[2] = max(7, 2+9) = 11
dp[3] = max(11, 7+3) = 11
dp[4] = max(11, 11+1) = 12

Answer: 12 (rob houses 0,2,4)
```

## Common Mistakes & Fixes

| Mistake | Fix |
|---------|-----|
| `dp[i]` = max money IF rob house i | `dp[i]` = max money FROM houses 0 to i |
| `dp[0] = 0` | `dp[0] = nums[0]` |
| `dp[1] = nums[1]` | `dp[1] = max(nums[0], nums[1])` |
| Missing single house check | `if (nums.length == 1) return nums[0]` |
| Wrong variable update order | Update `prev2` before `prev1` |

## Edge Cases Checklist
- [ ] Single house: `[5]` → 5
- [ ] Two houses: `[1,2]` → 2
- [ ] All same: `[3,3,3,3]` → 6
- [ ] Decreasing: `[5,4,3,2,1]` → 9

## Complexity
- **Time**: O(n) - single pass
- **Space**: O(n) for array version, O(1) for optimized

## Pattern Recognition
**Use this pattern when you see:**
- "maximize/minimize"
- "can't take adjacent/consecutive"
- "optimal decision at each step"
- "constraint affects future choices"

## Related Problems
- House Robber II (#213) - circular constraint
- House Robber III (#337) - tree structure
- Delete and Earn (#740) - similar decision pattern
- Min Cost Climbing Stairs (#746) - decision with costs

## Quick Debug
```java
// Add this to trace execution:
System.out.println("dp[" + i + "] = max(" + dp[i-1] + ", " + (dp[i-2] + nums[i]) + ") = " + dp[i]);
```

## One-Liner Memory Aid
**"At each house: max(skip_and_keep_previous, rob_and_add_to_two_back)"**