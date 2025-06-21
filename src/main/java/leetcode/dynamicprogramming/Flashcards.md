# Dynamic Programming Pattern - Flashcards

## Core Concepts

**Q: What is Dynamic Programming and when should you use it?**
A: Dynamic Programming (DP) is a method for solving complex problems by breaking them down into simpler subproblems. Use it for **optimization problems** and **counting problems** where you need to find the best solution or count the number of ways to achieve something.

**Q: What are the two key principles that make a problem suitable for DP?**
A:
1. **Optimal Substructure**: The optimal solution contains optimal solutions to its subproblems
2. **Overlapping Subproblems**: The problem can be broken down into subproblems which are reused several times

**Q: What's the difference between Memoization and Tabulation?**
A:
- **Memoization (Top-Down)**: Store results of expensive function calls, recursive approach with caching
- **Tabulation (Bottom-Up)**: Build up solutions from smallest subproblems, iterative approach

## Problem Identification

**Q: What keywords and patterns indicate a DP problem?**
A:
- **Optimization**: "maximum", "minimum", "shortest", "longest", "best"
- **Counting**: "number of ways", "how many", "count"
- **Decision Making**: "can we achieve", "is it possible"

**Q: What are the main characteristics of DP problems?**
A:
1. **Choices at each step**: At each position, you have multiple options
2. **Optimal substructure**: Optimal solution depends on optimal solutions to subproblems
3. **Overlapping subproblems**: Same subproblems appear multiple times

## DP Dimensions

**Q: When do you use Single-Dimensional vs Multi-Dimensional DP?**
A:
**Single-Dimensional DP**: When state can be represented by a single parameter
- Examples: Fibonacci, climbing stairs, house robber
- Form: `dp[i] = f(dp[i-1], dp[i-2], ...)`

**Multi-Dimensional DP**: When state requires multiple parameters  
- Examples: Grid paths, knapsack, edit distance
- Form: `dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1], ...)`

## Common Problem Types

**Q: What are the main categories of DP problems?**
A:
1. **Linear DP (1D)**: Fibonacci, climbing stairs, house robber, decode ways
2. **Grid DP (2D)**: Unique paths, minimum path sum, longest common subsequence, edit distance
3. **Interval DP**: Matrix chain multiplication, palindrome partitioning
4. **Tree DP**: Binary tree maximum path sum, house robber III

**Q: What are Fibonacci-like sequences and how do you solve them?**
A: Problems where each term depends on previous terms. Pattern:
```java
dp[i] = dp[i-1] + dp[i-2] + ... + dp[i-k]
```
Examples: Fibonacci numbers, climbing stairs, tribonacci

## Solution Approach

**Q: What are the 4 steps to solve a DP problem?**
A:
1. **Define the Problem Recursively**: Identify base cases and recurrence relation
2. **Identify State and Transitions**: What info needed for subproblems? How to move between states?
3. **Choose Implementation Strategy**: Top-down with memoization or bottom-up with tabulation
4. **Optimize Space**: Often can reduce from O(n) to O(1) space

**Q: How do you typically optimize space in DP problems?**
A: When you only need the last few states (not all previous states), use variables instead of arrays:
```java
// Instead of dp[i] = dp[i-1] + dp[i-2]
// Use: current = prev1 + prev2
```

## Top-Down vs Bottom-Up

**Q: When should you choose Top-Down (Memoization) vs Bottom-Up (Tabulation)?**
A:
**Top-Down (Memoization)**:
- More intuitive, follows natural recursion
- Only computes needed subproblems
- Easier to implement initially
- Has recursion overhead

**Bottom-Up (Tabulation)**:
- No recursion overhead
- Guarantees all subproblems are solved
- Usually more space efficient
- Better for very large inputs (no stack overflow)

## Common Patterns

**Q: What's the typical pattern for 1D DP problems?**
A:
```java
// 1. Initialize base cases
dp[0] = baseValue;
dp[1] = baseValue;

// 2. Fill the table
for (int i = 2; i <= n; i++) {
    dp[i] = someFunction(dp[i-1], dp[i-2], ...);
}

// 3. Return final answer
return dp[n];
```

**Q: What's the typical pattern for 2D DP problems?**
A:
```java
// 1. Initialize dp table
int[][] dp = new int[m+1][n+1];

// 2. Initialize base cases
for (int i = 0; i <= m; i++) dp[i][0] = baseValue;
for (int j = 0; j <= n; j++) dp[0][j] = baseValue;

// 3. Fill the table
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        dp[i][j] = someFunction(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]);
    }
}

return dp[m][n];
```

## Common Mistakes

**Q: What are the top 5 common mistakes in DP and how to avoid them?**
A:
1. **Not Identifying Optimal Substructure**: Verify optimal solution contains optimal subproblem solutions
2. **Incorrect State Definition**: Ensure state captures all necessary information for decisions
3. **Wrong Base Cases**: Carefully identify smallest subproblems and their solutions
4. **Index Confusion**: Be clear about 0-indexed vs 1-indexed representations
5. **Not Considering All Transitions**: Systematically consider all ways to reach a state

## Problem-Solving Tips

**Q: What's the recommended approach for beginners learning DP?**
A:
1. **Start with Recursive Solution**: Write simple recursion first
2. **Draw the Recursion Tree**: Visualize overlapping subproblems
3. **Work Through Small Examples**: Trace algorithm step by step
4. **Practice State Space Design**: Learn to identify what information to store
5. **Master Common Patterns**: Learn standard DP patterns and their variations

**Q: How do you identify the state in a DP problem?**
A: Ask yourself: "What is the minimum information I need to know at any point to make optimal decisions going forward?" This information becomes your state.

**Q: What's the difference between Knapsack variants?**
A:
- **0/1 Knapsack**: Each item can be used at most once
- **Unbounded Knapsack**: Each item can be used unlimited times  
- **Bounded Knapsack**: Each item has a specific count limit