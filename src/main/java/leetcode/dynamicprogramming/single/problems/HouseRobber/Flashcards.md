# House Robber - Flashcards

## Core Concepts

**Q: What type of DP problem is House Robber?**
A: **Decision DP** - At each position, we make an optimal choice between taking or skipping the current element, with constraints affecting future decisions.

**Q: What does dp[i] represent in House Robber?**
A: `dp[i]` = maximum money that can be robbed from houses 0 to i (inclusive). It's the BEST we can do considering all houses up to position i, not whether we rob house i specifically.

**Q: What are the base cases for House Robber and why?**
A:
- `dp[0] = nums[0]` - With only one house, rob it (no constraint)
- `dp[1] = max(nums[0], nums[1])` - With two houses, rob the one with more money

These are the smallest subproblems that can be solved directly without referencing other subproblems.

**Q: What is the recurrence relation for House Robber?**
A: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`

**Two choices:**
- Rob house i: get `nums[i] + dp[i-2]` (can't rob adjacent house i-1)
- Skip house i: get `dp[i-1]` (keep best from previous houses)

## Problem Recognition

**Q: What keywords indicate a House Robber-style problem?**
A:
- "maximize/minimize"
- "can't take adjacent/consecutive elements"
- "optimal choice at each step"
- "decision with constraints"

**Q: What's the key constraint that makes this a DP problem?**
A: **Adjacency constraint** - robbing a house prevents you from robbing the next house, creating dependencies between decisions that require optimal substructure.

**Q: Why can't we use a greedy approach for House Robber?**
A: Taking the locally optimal choice (always rob the house with most money available) doesn't guarantee the globally optimal solution. Example: `[2, 7, 9, 3, 1]` - greedy would take 7 first, but optimal is 2+9+1=12.

## Implementation

**Q: How do you optimize House Robber from O(n) to O(1) space?**
A: Since `dp[i]` only depends on `dp[i-1]` and `dp[i-2]`, use two variables:
```java
int prev2 = nums[0];                    // dp[i-2]
int prev1 = Math.max(nums[0], nums[1]); // dp[i-1]
// In loop: current = max(prev1, prev2 + nums[i])
```

**Q: What edge case must you handle in House Robber?**
A: **Single house case**: `if (nums.length == 1) return nums[0];`
Without this, accessing `nums[1]` for the base case would cause an index error.

**Q: How do you trace through House Robber step by step?**
A: For each house i, calculate:
1. **Rob option**: `dp[i-2] + nums[i]`
2. **Skip option**: `dp[i-1]`
3. **Choose max**: `dp[i] = max(rob_option, skip_option)`
4. **Track decision**: Which option was chosen?

## Common Mistakes

**Q: What's wrong with defining dp[i] as "max money if we rob house i"?**
A: This definition doesn't capture the optimal substructure. We need `dp[i]` to represent the best solution considering ALL houses 0 to i, regardless of whether house i is robbed.

**Q: What's the most common base case error?**
A: Setting `dp[0] = 0` or `dp[1] = nums[1]`.
- Correct: `dp[0] = nums[0]` (rob the only house)
- Correct: `dp[1] = max(nums[0], nums[1])` (rob the better of two)

**Q: Why might someone get wrong answers on edge cases?**
A:
- Not handling single house case
- Wrong base case for two houses
- Off-by-one errors in loop bounds
- Incorrect space optimization variable updates

## Pattern Recognition

**Q: What other problems follow the House Robber pattern?**
A:
- **House Robber II** - circular array constraint
- **House Robber III** - binary tree structure
- **Delete and Earn** - similar adjacency constraint
- **Min Cost Climbing Stairs** - decision with costs

**Q: What's the general template for "decision DP" problems?**
A:
```java
dp[i] = optimal_choice(
    take_current + best_compatible_state,
    skip_current + best_previous_state
)
```

**Q: How do you identify when a problem needs adjacency constraint handling?**
A: Look for phrases like:
- "can't take consecutive/adjacent elements"
- "must skip elements between selections"
- "security system connects adjacent..."
- "cooldown period between actions"

## Advanced Understanding

**Q: What makes House Robber have optimal substructure?**
A: The optimal solution for houses 0 to i contains optimal solutions for:
- Houses 0 to i-1 (if we skip house i)
- Houses 0 to i-2 (if we rob house i)

This allows us to build up solutions from smaller subproblems.

**Q: Why does House Robber have overlapping subproblems?**
A: When solving for house i, we need solutions for houses i-1 and i-2. When solving for house i+1, we again need the solution for house i-1. This overlap makes memoization beneficial.

**Q: How would you extend House Robber to handle additional constraints?**
A:
- **Time constraints**: Add another dimension to track time/resources
- **Multiple types**: Add dimension for item type
- **Variable skip distance**: Modify recurrence to consider variable gaps
- **Limited selections**: Add dimension to track number of houses robbed

## Quick Review

**Q: House Robber in one sentence?**
A: "At each house, choose the maximum between robbing it (plus best from two houses ago) or skipping it (keeping best from previous house)."

**Q: Most important insight for House Robber?**
A: **State definition is everything** - `dp[i]` must represent the optimal solution considering all elements up to position i, not just the decision at position i.

**Q: Key debugging technique for House Robber?**
A: **Trace small examples by hand** - manually calculate dp[0], dp[1], dp[2], etc. and verify each step matches your recurrence relation and produces the expected final answer.