# Jump Game II - Deep Learning Guide

## Problem Statement

**LeetCode 45 - Medium**

Given a 0-indexed array of integers `nums` of length `n`, you are initially positioned at index 0. Each element `nums[i]` represents the maximum length of a forward jump from index `i`.

Return the **minimum number of jumps** to reach index `n - 1`.

**Key Insight:** Unlike Jump Game I (feasibility), this is an **optimization problem** requiring the minimum number of jumps.

### Examples

**Example 1:**
```
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

**Example 2:**
```
Input: nums = [2,3,0,1,4]
Output: 2
```

## 🧠 Problem Analysis: Why Dynamic Programming?

### The Optimization Nature

This is a **classic optimization DP problem** because:

1. **Overlapping Subproblems**: Different paths can reach the same position
2. **Optimal Substructure**: Minimum jumps to position `i` depends on minimum jumps to previous positions
3. **Multiple Valid Solutions**: Many paths exist, we need the shortest

### Comparison with Jump Game I

| Aspect | Jump Game I | Jump Game II |
|--------|-------------|--------------|
| **Question** | Can we reach the end? | What's the minimum jumps? |
| **Type** | Feasibility | Optimization |
| **Output** | Boolean | Integer |
| **Best Solution** | Greedy O(n) | DP O(n²) or Advanced Greedy O(n) |

## 🎯 Dynamic Programming Approach

### Core DP Concept

**State Definition:** `dp[i] = minimum jumps needed to reach position i`

**Recurrence Relation:**
```
dp[i] = min(dp[j] + 1) for all j where j < i and j + nums[j] >= i
```

**Translation:** To reach position `i`, try jumping from all positions `j` that can reach `i`, and take the minimum.

### Step-by-Step Algorithm

```java
public int jumpDP(int[] nums) {
    int n = nums.length;
    if (n <= 1) return 0;
    
    // Step 1: Initialize DP array
    int[] dp = new int[n];
    Arrays.fill(dp, Integer.MAX_VALUE);  // All positions unreachable initially
    dp[0] = 0;  // Base case: 0 jumps to reach start
    
    // Step 2: Fill DP table
    for (int i = 0; i < n; i++) {
        if (dp[i] == Integer.MAX_VALUE) continue;  // Skip unreachable positions
        
        // Step 3: Update all reachable positions from i
        int maxReach = Math.min(i + nums[i], n - 1);
        for (int j = i + 1; j <= maxReach; j++) {
            dp[j] = Math.min(dp[j], dp[i] + 1);
        }
    }
    
    return dp[n - 1];
}
```

## 📊 Detailed Example Walkthrough

Let's trace through `nums = [2,3,1,1,4]`:

### Initial Setup
```
Index:  0  1  2  3  4
Value:  2  3  1  1  4
DP:     0  ∞  ∞  ∞  ∞
```

### Step 1: Process index 0 (value=2, dp[0]=0)
From index 0, we can jump to positions 1 and 2:
```
dp[1] = min(∞, 0 + 1) = 1
dp[2] = min(∞, 0 + 1) = 1

Index:  0  1  2  3  4
Value:  2  3  1  1  4
DP:     0  1  1  ∞  ∞
```

### Step 2: Process index 1 (value=3, dp[1]=1)
From index 1, we can jump to positions 2, 3, and 4:
```
dp[2] = min(1, 1 + 1) = 1  (no change)
dp[3] = min(∞, 1 + 1) = 2
dp[4] = min(∞, 1 + 1) = 2

Index:  0  1  2  3  4
Value:  2  3  1  1  4
DP:     0  1  1  2  2
```

### Step 3: Process index 2 (value=1, dp[2]=1)
From index 2, we can jump to position 3:
```
dp[3] = min(2, 1 + 1) = 2  (no change)

Index:  0  1  2  3  4
Value:  2  3  1  1  4
DP:     0  1  1  2  2
```

**Final Answer:** `dp[4] = 2` jumps

### Optimal Path Reconstruction
- Start at index 0 (0 jumps)
- Jump to index 1 (1 jump total)
- Jump to index 4 (2 jumps total)

## 🚀 Advanced: Greedy Approach

While DP is great for learning, the optimal solution uses a greedy "level-by-level BFS" approach:

```java
public int jumpGreedy(int[] nums) {
    int n = nums.length;
    if (n <= 1) return 0;
    
    int jumps = 0;
    int currentEnd = 0;    // End of current "level"
    int farthest = 0;      // Farthest we can reach in next level
    
    for (int i = 0; i < n - 1; i++) {
        // Update farthest reachable position
        farthest = Math.max(farthest, i + nums[i]);
        
        // If we've reached the end of current level
        if (i == currentEnd) {
            jumps++;              // Make a jump to next level
            currentEnd = farthest; // Update level boundary
        }
    }
    
    return jumps;
}
```

### Greedy Intuition

Think of it as BFS levels:
- **Level 0**: Positions reachable in 0 jumps (just index 0)
- **Level 1**: Positions reachable in 1 jump from level 0
- **Level 2**: Positions reachable in 1 jump from level 1
- Continue until we reach the last index

## 📈 Complexity Analysis

| Approach | Time Complexity | Space Complexity | Explanation |
|----------|----------------|------------------|-------------|
| **Dynamic Programming** | O(n²) | O(n) | For each position, check all previous positions |
| **Greedy (BFS-style)** | O(n) | O(1) | Single pass with constant extra space |

### When Each Approach Matters

**Use DP when:**
- Learning the concept for the first time
- Need to understand the problem structure
- Want to see all possible states
- Dealing with variations that break greedy property

**Use Greedy when:**
- Optimizing for performance
- In a time-constrained interview after explaining DP
- Working with large datasets

## 🎓 Key Learning Points for DP Mastery

### 1. Problem Recognition
Jump Game II is a **"Minimum Path" DP problem**. Recognize this pattern:
- Multiple ways to reach each state
- Need to find optimal (minimum/maximum) way
- Current state depends on previous states

### 2. State Design
- **State**: `dp[i]` = minimum jumps to reach position `i`
- **Base Case**: `dp[0] = 0` (0 jumps to stay at start)
- **Unreachable**: `Integer.MAX_VALUE` for impossible positions

### 3. Transition Design
- For each position `i`, update all positions reachable from `i`
- Update formula: `dp[j] = min(dp[j], dp[i] + 1)`

### 4. Optimization Tricks
- **Early Termination**: Stop when target is reached
- **Pruning**: Skip unreachable positions
- **Space Optimization**: Sometimes possible with sliding window

## 🔍 Common Variations and Extensions

1. **Jump Game III**: Can jump in both directions
2. **Jump Game IV**: Can jump to same values
3. **Jump Game V**: Jump with constraints
4. **Frog Jump**: Variable jump sizes

## 💡 Interview Strategy

### Phase 1: Show Understanding (5 minutes)
1. Clarify problem requirements
2. Identify it as optimization DP
3. Explain state definition and recurrence

### Phase 2: Implement DP (10 minutes)
1. Code the DP solution
2. Walk through example
3. Analyze complexity

### Phase 3: Optimize (5 minutes)
1. Mention greedy approach exists
2. Implement if time permits
3. Compare both approaches

### Template Response
*"This is a minimum path dynamic programming problem. I'll start with DP to show the core logic, then optimize to greedy if we have time. The key insight is that we need to track the minimum jumps to reach each position..."*

## 🚨 Common Pitfalls

1. **Forgetting Base Case**: Not setting `dp[0] = 0`
2. **Wrong Initialization**: Using 0 instead of `Integer.MAX_VALUE`
3. **Index Bounds**: Not checking `j <= maxReach`
4. **Unreachable Check**: Not skipping `dp[i] == Integer.MAX_VALUE`
5. **Early Termination**: Missing optimization opportunities

This deep dive into Jump Game II should give you a solid foundation for tackling similar DP optimization problems!