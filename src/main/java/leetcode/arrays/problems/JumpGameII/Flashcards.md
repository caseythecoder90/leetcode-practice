# Jump Game II - DP Mastery Flashcards

## 🃏 Core Concept Cards

### Card 1: Problem Type Recognition
**Q:** How do you recognize Jump Game II as a DP problem?
**A:** 
- **Keywords**: "minimum number of jumps" (optimization)
- **Structure**: Multiple paths to reach same position (overlapping subproblems)
- **Goal**: Find optimal solution among many possibilities
- **Pattern**: Minimum Path DP

### Card 2: State Definition
**Q:** What is the DP state for Jump Game II?
**A:** 
- **State**: `dp[i] = minimum number of jumps to reach position i`
- **Type**: 1D array of integers
- **Size**: `n` (length of input array)
- **Initial**: All `Integer.MAX_VALUE` except `dp[0] = 0`

### Card 3: Recurrence Relation
**Q:** What is the recurrence relation for Jump Game II?
**A:**
```
dp[j] = min(dp[j], dp[i] + 1)
for all j where i < j <= i + nums[i]
```
**Meaning**: To reach position j, try jumping from all positions i that can reach j, and take minimum.

### Card 4: Base Case
**Q:** What is the base case for Jump Game II DP?
**A:**
- `dp[0] = 0` (0 jumps to stay at starting position)
- All other positions: `dp[i] = Integer.MAX_VALUE` (initially unreachable)

## 🎯 Algorithm Cards

### Card 5: DP Algorithm Template
**Q:** Write the DP algorithm structure for Jump Game II.
**A:**
```java
int[] dp = new int[n];
Arrays.fill(dp, Integer.MAX_VALUE);
dp[0] = 0;

for (int i = 0; i < n; i++) {
    if (dp[i] == Integer.MAX_VALUE) continue;
    for (int j = i + 1; j <= Math.min(i + nums[i], n - 1); j++) {
        dp[j] = Math.min(dp[j], dp[i] + 1);
    }
}
return dp[n - 1];
```

### Card 6: Time and Space Complexity
**Q:** What are the time and space complexities of the DP solution?
**A:**
- **Time**: O(n²) - for each position, check all reachable positions
- **Space**: O(n) - DP array of size n
- **Can optimize**: Early termination when target reached

### Card 7: Greedy Alternative
**Q:** What is the greedy approach for Jump Game II?
**A:**
- **Concept**: BFS-like level traversal
- **Time**: O(n), **Space**: O(1)
- **Variables**: `jumps`, `currentEnd`, `farthest`
- **Insight**: Always extend reach as far as possible at each level

## 🔍 Tracing Cards

### Card 8: Example Trace - Setup
**Q:** For `nums = [2,3,1,1,4]`, what is the initial DP setup?
**A:**
```
Index: [0] [1] [2] [3] [4]
Value: [2] [3] [1] [1] [4]
DP:    [0] [∞] [∞] [∞] [∞]
```

### Card 9: Example Trace - Step 1
**Q:** After processing index 0 in `[2,3,1,1,4]`, what is the DP state?
**A:**
```
From index 0 (value=2): can reach indices 1, 2
dp[1] = min(∞, 0+1) = 1
dp[2] = min(∞, 0+1) = 1

DP: [0] [1] [1] [∞] [∞]
```

### Card 10: Example Trace - Final
**Q:** What is the final DP state for `[2,3,1,1,4]` and the answer?
**A:**
```
Final DP: [0] [1] [1] [2] [2]
Answer: dp[4] = 2 jumps
Optimal path: 0 → 1 → 4 (or 0 → 2 → 4)
```

## 🎓 Pattern Recognition Cards

### Card 11: DP vs Greedy Decision
**Q:** When should you use DP vs Greedy for Jump Game II?
**A:**
- **Use DP when**:
  - Learning the concept
  - Need to understand problem structure
  - Showing systematic thinking in interviews
- **Use Greedy when**:
  - Optimizing for performance
  - Working with large datasets
  - After demonstrating DP understanding

### Card 12: Pattern Family
**Q:** What other problems belong to the same DP pattern as Jump Game II?
**A:**
- **Coin Change**: `dp[i] = min coins for amount i`
- **Perfect Squares**: `dp[i] = min squares for number i`
- **Minimum Path Sum**: `dp[i][j] = min sum to cell (i,j)`
- **Pattern**: All are "Minimum Path" optimization problems

## 🚨 Common Mistakes Cards

### Card 13: Initialization Error
**Q:** What is a common initialization mistake in Jump Game II DP?
**A:**
- **Wrong**: Initializing dp array with 0s
- **Right**: Initialize with `Integer.MAX_VALUE` (unreachable)
- **Exception**: `dp[0] = 0` (base case)

### Card 14: Boundary Error
**Q:** What boundary condition must you check when updating DP states?
**A:**
```java
// Wrong: j <= i + nums[i] (can go out of bounds)
// Right: j <= Math.min(i + nums[i], n - 1)
```

### Card 15: Unreachable States
**Q:** How do you handle unreachable states in DP?
**A:**
```java
if (dp[i] == Integer.MAX_VALUE) continue;
// Skip processing unreachable positions
```

## 💡 Interview Strategy Cards

### Card 16: Interview Approach - Phase 1
**Q:** How do you start explaining Jump Game II in an interview?
**A:**
*"This is an optimization problem where I need to find the minimum number of jumps. I can see this has optimal substructure - the minimum jumps to position i depends on minimum jumps to previous positions. Let me start with a DP approach..."*

### Card 17: Interview Approach - Phase 2
**Q:** After coding DP, what should you mention next?
**A:**
*"Now, this problem actually has a special property where we can use a greedy approach. The insight is that we can think of this as BFS levels where each jump represents moving to the next level..."*

### Card 18: Complexity Discussion
**Q:** How do you discuss the complexity trade-offs?
**A:**
- **DP**: O(n²) time, O(n) space - systematic and intuitive
- **Greedy**: O(n) time, O(1) space - optimal but requires insight
- **Choice**: Start with DP for understanding, optimize to greedy for performance

## 🔧 Debugging Cards

### Card 19: Common Debug Question
**Q:** Your DP solution returns `Integer.MAX_VALUE`. What's wrong?
**A:**
- **Likely cause**: Target position is unreachable
- **Check**: Are you handling boundary conditions correctly?
- **Verify**: Problem guarantees solution exists - check input validity

### Card 20: Optimization Checks
**Q:** How can you optimize the basic DP solution?
**A:**
1. **Early termination**: Break when `dp[n-1] != Integer.MAX_VALUE`
2. **Pruning**: Skip unreachable states with continue
3. **Space optimization**: Not applicable here (need all previous states)

## 🎯 Master Level Cards

### Card 21: Algorithm Intuition
**Q:** Explain the core intuition behind Jump Game II DP in one sentence.
**A:**
*"For each position, try jumping from all positions that can reach it, and remember the path that took the fewest jumps to get there."*

### Card 22: Real-World Analogy
**Q:** Give a real-world analogy for Jump Game II.
**A:**
*"Like planning the shortest route through a city where from each intersection, you can see how many blocks you can travel, and you want to reach your destination in the minimum number of moves."*

**Practice Tip**: Review these cards daily for one week to master Jump Game II DP patterns!