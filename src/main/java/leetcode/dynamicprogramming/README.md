# Dynamic Programming

## What is Dynamic Programming?

Dynamic Programming (DP) is a method for solving complex problems by breaking them down into simpler subproblems. It's particularly powerful for **optimization problems** and **counting problems** where we need to find the best solution or count the number of ways to achieve something.

**Key Insight**: Instead of solving the same subproblems repeatedly, DP stores the results of subproblems to avoid redundant calculations. This transforms exponential time complexity into polynomial time.

## Core Principles

### 1. **Optimal Substructure**
The optimal solution to a problem contains optimal solutions to its subproblems.

**Example**: To find the shortest path from A to C through B, we need:
- Shortest path from A to B
- Shortest path from B to C

### 2. **Overlapping Subproblems**
The problem can be broken down into subproblems which are reused several times.

**Example**: Computing Fibonacci numbers
- F(5) = F(4) + F(3)
- F(4) = F(3) + F(2)
- Notice F(3) is computed twice

### 3. **Memoization vs Tabulation**
- **Memoization (Top-Down)**: Store results of expensive function calls
- **Tabulation (Bottom-Up)**: Build up solutions from smallest subproblems

## How to Identify DP Problems

### Keywords and Patterns
- **Optimization**: "maximum", "minimum", "shortest", "longest", "best"
- **Counting**: "number of ways", "how many", "count"
- **Decision Making**: "can we achieve", "is it possible"

### Problem Characteristics
1. **Choices at each step**: At each position, you have multiple options
2. **Optimal substructure**: Optimal solution depends on optimal solutions to subproblems  
3. **Overlapping subproblems**: Same subproblems appear multiple times

### Common DP Problem Types
- **Fibonacci-like sequences**: Each term depends on previous terms
- **Path counting**: Number of ways to reach a destination
- **Optimization**: Finding maximum/minimum value
- **Subsequence problems**: Longest increasing subsequence, etc.
- **Knapsack variants**: Choosing items with constraints
- **String matching**: Edit distance, pattern matching

## General Approach to Solve DP Problems

### Step 1: Define the Problem Recursively
- Identify the base case(s)
- Express the problem in terms of smaller subproblems
- Write the recurrence relation

### Step 2: Identify State and Transitions
- **State**: What information do we need to solve a subproblem?
- **Transition**: How do we move from one state to another?

### Step 3: Choose Implementation Strategy
- **Top-Down (Memoization)**: Natural recursive approach with caching
- **Bottom-Up (Tabulation)**: Iterative approach building from base cases

### Step 4: Optimize Space (if needed)
- Often we only need the last few states, not all previous states
- Can reduce space complexity from O(n) to O(1) in many cases

## Single vs Multi-Dimensional DP

### Single-Dimensional DP
**Use when**: The state can be represented by a single parameter

**Examples**:
- Fibonacci numbers: `dp[i]` = ith Fibonacci number
- Climbing stairs: `dp[i]` = ways to reach step i
- House robber: `dp[i]` = max money from houses 0 to i

**Typical Form**: `dp[i] = f(dp[i-1], dp[i-2], ...)`

### Multi-Dimensional DP  
**Use when**: The state requires multiple parameters

**Examples**:
- Grid path problems: `dp[i][j]` = ways to reach cell (i,j)
- Knapsack: `dp[i][w]` = max value using first i items with weight limit w
- Edit distance: `dp[i][j]` = min operations to transform string1[0:i] to string2[0:j]

**Typical Form**: `dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1], ...)`

## Common Beginner Mistakes and How to Avoid Them

### 1. **Not Identifying the Optimal Substructure**
❌ **Mistake**: Trying to use DP when the problem doesn't have optimal substructure
✅ **Solution**: Verify that optimal solution contains optimal solutions to subproblems

### 2. **Incorrect State Definition**
❌ **Mistake**: Defining state that doesn't capture all necessary information
✅ **Solution**: Ensure your state contains all information needed to make decisions

### 3. **Wrong Base Cases**
❌ **Mistake**: Incorrect or missing base cases
✅ **Solution**: Carefully identify the smallest subproblems and their solutions

### 4. **Index Confusion**
❌ **Mistake**: Off-by-one errors in array indexing
✅ **Solution**: Be clear about what each index represents (0-indexed vs 1-indexed)

### 5. **Not Considering All Transitions**
❌ **Mistake**: Missing some ways to reach a state
✅ **Solution**: Systematically consider all possible previous states

## Tips for Mastering DP

### 1. **Start with the Recursive Solution**
- First, write a simple recursive solution
- This helps you understand the problem structure
- Then optimize with memoization

### 2. **Draw the Recursion Tree**
- Visualize how subproblems overlap
- Identify which results you're computing multiple times

### 3. **Work Through Small Examples**
- Trace through the algorithm step by step
- Understand how the state transitions work

### 4. **Practice State Space Design**
- The hardest part is often defining the right state
- Practice identifying what information you need to store

### 5. **Master the Patterns**
- Learn common DP patterns (see CheatSheet.md)
- Many problems are variations of standard patterns

## Problem Categories

### Linear DP (1D)
- **Fibonacci sequence**: F(n) = F(n-1) + F(n-2)
- **Climbing stairs**: Ways to reach nth step
- **House robber**: Maximum money without robbing adjacent houses
- **Decode ways**: Number of ways to decode a string

### Grid DP (2D)
- **Unique paths**: Number of paths in a grid
- **Minimum path sum**: Minimum sum path from top-left to bottom-right
- **Longest common subsequence**: LCS of two strings
- **Edit distance**: Minimum operations to transform one string to another

### Interval DP
- **Matrix chain multiplication**: Optimal way to multiply matrices
- **Palindrome partitioning**: Minimum cuts for palindrome partitioning

### Tree DP
- **Binary tree maximum path sum**: Maximum sum path in a tree
- **House robber III**: House robber problem on a tree

## Resources for Further Learning

### Essential Problems to Master
1. **Fibonacci Numbers** - Learn basic memoization
2. **Climbing Stairs** - Understand state transitions
3. **Coin Change** - Practice unbounded knapsack
4. **Longest Increasing Subsequence** - Master O(n log n) optimization
5. **Edit Distance** - Understand 2D DP with strings

### Recommended Learning Path
1. Start with single-dimensional problems
2. Master top-down approach first
3. Learn to convert to bottom-up
4. Practice space optimization
5. Move to multi-dimensional problems

---

## Directory Structure

```
dynamicprogramming/
├── README.md (this file)
├── CheatSheet.md
├── single/
│   ├── README.md
│   ├── CheatSheet.md
│   └── problems/
└── multidimensional/
    ├── README.md
    ├── CheatSheet.md
    └── problems/
```

Start with the `single/` directory if you're new to dynamic programming!