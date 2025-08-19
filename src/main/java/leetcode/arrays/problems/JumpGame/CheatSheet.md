# Jump Game - Cheat Sheet

## Quick Problem Summary
**Problem**: Determine if you can reach the last index by jumping  
**Pattern**: Greedy Algorithm (Reachability)  
**Difficulty**: Medium  
**Key Insight**: Track farthest reachable position

## Core Algorithm

### Optimal Greedy Solution
```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;  // Unreachable
        farthest = Math.max(farthest, i + nums[i]);
        if (farthest >= nums.length - 1) return true;  // Early exit
    }
    
    return true;
}
```

**Time**: O(n) | **Space**: O(1)

### Alternative: Backward Greedy
```java
public boolean canJumpBackwards(int[] nums) {
    int lastGoodIndex = nums.length - 1;
    
    for (int i = nums.length - 2; i >= 0; i--) {
        if (i + nums[i] >= lastGoodIndex) {
            lastGoodIndex = i;
        }
    }
    
    return lastGoodIndex == 0;
}
```

## Quick Examples

### Example 1: [2,3,1,1,4] → Output: true
```
i=0: farthest = max(0, 0+2) = 2
i=1: farthest = max(2, 1+3) = 4 ≥ 4 ✓ (can reach end)
```

### Example 2: [3,2,1,0,4] → Output: false
```
i=0: farthest = max(0, 0+3) = 3
i=1: farthest = max(3, 1+2) = 3  
i=2: farthest = max(3, 2+1) = 3
i=3: farthest = max(3, 3+0) = 3
i=4: 4 > 3 (unreachable) ✗
```

## Pattern Recognition

**Greedy Indicators:**
- ✅ "Can you reach" (feasibility)
- ✅ Maximum jump length given
- ✅ True/false answer
- ✅ Don't need actual path

**NOT Greedy When:**
- ❌ "Minimum jumps" (use BFS/DP)
- ❌ "Count all paths" (use DFS)
- ❌ "Find optimal path" (use DP)

## Key Insights

**Why Greedy Works:**
- This is a **reachability problem**, not optimization
- If greedy can't reach position `i`, no strategy can
- We only need to know IF possible, not HOW

**The "Zero Trap":**
```
[1, 0, 1] → Can't jump over the 0, stuck at position 1
```

## Common Mistakes

1. **Overthinking**: Don't find actual path, just check reachability
2. **Wrong termination**: Use `>= nums.length - 1`, not `>= nums.length`
3. **Missing early exit**: Check for success inside the loop
4. **Confusing with Jump Game II**: This is feasibility, not optimization

## Edge Cases

```java
[0] → true     // Already at end
[1,0,0] → false   // Blocked by zero
[2,0,1] → true    // Can jump over zero
[1] → true     // Single element
```

## Algorithm Variants

| Approach | Time | Space | When to Use |
|----------|------|-------|-------------|
| **Forward Greedy** | O(n) | O(1) | Standard interview |
| **Backward Greedy** | O(n) | O(1) | Alternative thinking |
| **DP** | O(n²) | O(n) | Educational only |

## Template for Similar Problems

```java
// Reachability template
int farthest = startPosition;
for (int i = 0; i < array.length; i++) {
    if (i > farthest) return false;
    farthest = Math.max(farthest, i + jumpDistance[i]);
    if (farthest >= target) return true;
}
return farthest >= target;
```

## Related Problems

| Problem | Pattern | Key Difference |
|---------|---------|----------------|
| Jump Game I | Greedy | Can reach end? |
| Jump Game II | Greedy/BFS | Min jumps to end |
| Jump Game III | DFS/BFS | Can reach any 0? |
| Jump Game IV | BFS | Min steps with equal values |

## Interview Talking Points

1. **"This is reachability, not optimization"**
2. **"Greedy works because we only need feasibility"**
3. **"Track farthest position, early exit when possible"**
4. **"O(n) time with O(1) space is optimal"**
