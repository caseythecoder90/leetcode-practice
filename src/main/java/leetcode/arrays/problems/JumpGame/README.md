# Jump Game (LeetCode 55) - Study Guide

**Pattern**: Greedy Algorithm (Reachability) | **Difficulty**: Medium | **Type**: Decision Problem

## Problem Statement

You are given an integer array `nums`. You are initially positioned at the array's **first index**, and each element in the array represents your maximum jump length at that position.

Return `true` if you can reach the last index, or `false` otherwise.

### Examples

**Example 1:**
```
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

**Example 2:**
```
Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
```

## Problem Analysis

### Pattern Recognition
- **Keywords**: "can reach", "maximum jump length", "return true/false"
- **Pattern**: **Greedy Algorithm** - reachability problem
- **Key Insight**: We only need to know IF we can reach the end, not HOW
- **Constraint**: Each element represents maximum jump, not exact jump

### Why Greedy Works
Unlike optimization problems where greedy might miss the optimal solution, this is a **feasibility problem**:

1. **Goal**: Determine if the last index is reachable
2. **Greedy Choice**: Always track the farthest position we can reach
3. **Why Optimal**: If we can't reach position `i`, we definitely can't reach any position beyond `i`

**Key Difference**: This is **not** about finding the minimum number of jumps (that would require DP), just about whether it's possible.

## Solution Approaches

### Approach 1: Forward Greedy (Optimal)

```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    
    for (int i = 0; i < nums.length; i++) {
        // If current position is unreachable, return false
        if (i > farthest) return false;
        
        // Update the farthest reachable position
        farthest = Math.max(farthest, i + nums[i]);
        
        // Early termination: if we can reach the end
        if (farthest >= nums.length - 1) return true;
    }
    
    return true;
}
```

**Time Complexity**: O(n) - single pass  
**Space Complexity**: O(1) - only tracking farthest position

### Approach 2: Backward Greedy

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

**Intuition**: Work backwards to find the leftmost position that can reach the end.

### Approach 3: Dynamic Programming (Inefficient)

```java
public boolean canJumpDP(int[] nums) {
    boolean[] dp = new boolean[nums.length];
    dp[0] = true;  // First position is always reachable
    
    for (int i = 0; i < nums.length; i++) {
        if (!dp[i]) continue;  // Position i is not reachable
        
        // From position i, mark all reachable positions
        for (int j = i + 1; j <= Math.min(i + nums[i], nums.length - 1); j++) {
            dp[j] = true;
        }
    }
    
    return dp[nums.length - 1];
}
```

**Time Complexity**: O(n²) - nested loops  
**Space Complexity**: O(n) - DP array

**Note**: DP works but is unnecessarily complex for this problem.

## Algorithm Walkthrough

### Example 1: nums = [2,3,1,1,4]

```
i=0: farthest = max(0, 0+2) = 2
     Can reach positions: [0, 1, 2]

i=1: farthest = max(2, 1+3) = 4
     Can reach positions: [0, 1, 2, 3, 4] ✓
     Since 4 >= 4 (last index), return true
```

### Example 2: nums = [3,2,1,0,4]

```
i=0: farthest = max(0, 0+3) = 3
     Can reach positions: [0, 1, 2, 3]

i=1: farthest = max(3, 1+2) = 3
     Can reach positions: [0, 1, 2, 3]

i=2: farthest = max(3, 2+1) = 3
     Can reach positions: [0, 1, 2, 3]

i=3: farthest = max(3, 3+0) = 3
     Can reach positions: [0, 1, 2, 3]

i=4: 4 > 3 (farthest), so position 4 is unreachable
     Return false
```

## Key Insights

### Why This Problem is Greedy
1. **Local Choice**: At each position, we make the locally optimal choice (jump as far as possible)
2. **Global Optimum**: If any strategy can reach the end, the greedy strategy can too
3. **No Need to Backtrack**: If we can't reach a position greedily, no other strategy can reach it

### The "Blocked by Zero" Pattern
The main obstacle is encountering a `0` that we can't jump over:

```
nums = [1, 0, 1, 1]
       0  1  2  3

From 0: can reach 1
From 1: nums[1]=0, stuck at position 1
Cannot reach positions 2 or 3
```

### Edge Cases
1. **Single element**: `[0]` → `true` (already at the end)
2. **All zeros except first**: `[1,0,0,0]` → `false` (blocked immediately)
3. **Large jumps**: `[9]` → `true` (can jump way past the end)

## Common Mistakes

### Mistake 1: Overthinking the Problem
```java
// DON'T try to find the actual path
List<Integer> path = new ArrayList<>();
// Complex path-finding logic...
```

**Fix**: Remember, we only need to determine reachability, not the optimal path.

### Mistake 2: Missing Early Termination
```java
// Inefficient: continues even after reaching the end
for (int i = 0; i < nums.length; i++) {
    farthest = Math.max(farthest, i + nums[i]);
}
return farthest >= nums.length - 1;
```

**Fix**: Add early termination when `farthest >= nums.length - 1`.

### Mistake 3: Off-by-One Errors
```java
// Wrong: should be >= nums.length - 1
if (farthest >= nums.length) return true;
```

**Fix**: Remember that array indices are 0-based, so the last index is `nums.length - 1`.

## Pattern Variations

### Related Problems
| Problem | Key Difference | Approach |
|---------|---------------|----------|
| **Jump Game I** | Can you reach the end? | Greedy (reachability) |
| **Jump Game II** | Minimum jumps to reach end | Greedy (BFS-like) |
| **Jump Game III** | Can reach value 0? | DFS/BFS |
| **Jump Game IV** | Minimum steps with equal values | BFS |

### When to Use Each Approach
- **Reachability problems**: Use greedy tracking
- **Minimum path problems**: Use BFS or DP
- **All paths problems**: Use DFS with backtracking

## Interview Tips

### What to Say
1. **"This is a reachability problem, not an optimization problem"**
2. **"I'll use greedy approach to track the farthest reachable position"**
3. **"Time O(n), Space O(1) with early termination"**

### Code Template
```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;
        farthest = Math.max(farthest, i + nums[i]);
        if (farthest >= nums.length - 1) return true;
    }
    
    return true;
}
```

### Complexity Analysis
- **Time**: O(n) - single pass through array
- **Space**: O(1) - only one variable to track farthest position
- **Optimization**: Early termination when end is reachable

## Practice Problems

**Similar Greedy Problems:**
- LeetCode 45: Jump Game II
- LeetCode 134: Gas Station
- LeetCode 435: Non-overlapping Intervals

**Reachability Problems:**
- LeetCode 1306: Jump Game III
- LeetCode 1345: Jump Game IV
- LeetCode 1871: Jump Game VII
