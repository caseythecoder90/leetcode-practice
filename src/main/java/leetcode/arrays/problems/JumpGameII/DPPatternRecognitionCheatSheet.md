# Dynamic Programming Pattern Recognition Cheat Sheet

## 🎯 Master DP by Recognizing These 7 Core Patterns

Jump Game II belongs to the **"Minimum Path"** pattern. Once you master this pattern, you'll recognize it instantly in dozens of problems.

## 📋 Pattern 1: Minimum/Maximum Path Problems

### Pattern Recognition Signals
- **Keywords**: "minimum cost", "maximum profit", "shortest path", "minimum steps"
- **Structure**: Multiple ways to reach the same state
- **Goal**: Find optimal (min/max) way to reach target

### Template
```java
// State: dp[i] = min/max cost to reach position/state i
int[] dp = new int[n];
Arrays.fill(dp, Integer.MAX_VALUE); // or Integer.MIN_VALUE for max
dp[0] = 0; // Base case

for (int i = 0; i < n; i++) {
    if (dp[i] == Integer.MAX_VALUE) continue; // Skip unreachable
    
    // Try all possible transitions from i
    for (each possible next state j) {
        dp[j] = Math.min(dp[j], dp[i] + cost(i, j));
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **Jump Game II** | `dp[i] = min jumps to position i` | `dp[j] = min(dp[j], dp[i] + 1)` |
| **Coin Change** | `dp[i] = min coins for amount i` | `dp[i] = min(dp[i-coin] + 1)` |
| **Perfect Squares** | `dp[i] = min squares for number i` | `dp[i] = min(dp[i-square] + 1)` |
| **Minimum Path Sum** | `dp[i][j] = min sum to cell (i,j)` | `dp[i][j] = min(up, left) + grid[i][j]` |
| **Climbing Stairs with Cost** | `dp[i] = min cost to reach step i` | `dp[i] = min(dp[i-1], dp[i-2]) + cost[i]` |

## 📋 Pattern 2: Counting Problems

### Pattern Recognition Signals
- **Keywords**: "how many ways", "number of methods", "count combinations"
- **Structure**: Multiple ways to achieve the same result
- **Goal**: Count all possible ways

### Template
```java
// State: dp[i] = number of ways to reach/achieve state i
int[] dp = new int[n];
dp[0] = 1; // Base case: one way to achieve initial state

for (int i = 0; i < n; i++) {
    // Add ways from all possible previous states
    for (each possible previous state j) {
        dp[i] += dp[j];
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **Climbing Stairs** | `dp[i] = ways to reach step i` | `dp[i] = dp[i-1] + dp[i-2]` |
| **Unique Paths** | `dp[i][j] = ways to reach cell (i,j)` | `dp[i][j] = dp[i-1][j] + dp[i][j-1]` |
| **Decode Ways** | `dp[i] = ways to decode string[0:i]` | `dp[i] = dp[i-1] + dp[i-2]` |
| **Target Sum** | `dp[i][j] = ways to get sum j using first i numbers` | `dp[i][j] = dp[i-1][j-nums[i]] + dp[i-1][j+nums[i]]` |

## 📋 Pattern 3: True/False (Feasibility) Problems

### Pattern Recognition Signals
- **Keywords**: "can we achieve", "is it possible", "exists a way"
- **Structure**: Check if something is achievable
- **Goal**: Return boolean or find if solution exists

### Template
```java
// State: dp[i] = true if state i is achievable
boolean[] dp = new boolean[n];
dp[0] = true; // Base case

for (int i = 0; i < n; i++) {
    if (!dp[i]) continue; // Skip unreachable states
    
    // Mark all reachable states from i
    for (each possible next state j) {
        dp[j] = true;
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **Jump Game I** | `dp[i] = can reach position i` | `dp[j] = true` for reachable j |
| **Partition Equal Subset Sum** | `dp[i] = can achieve sum i` | `dp[i] = dp[i] OR dp[i-num]` |
| **Word Break** | `dp[i] = can break string[0:i]` | `dp[i] = dp[j] AND dict.contains(string[j:i])` |

## 📋 Pattern 4: Subsequence/Substring Problems

### Pattern Recognition Signals
- **Keywords**: "longest subsequence", "shortest substring", "common subsequence"
- **Structure**: Compare or find patterns in sequences
- **Goal**: Find optimal subsequence/substring

### Template
```java
// State: dp[i][j] = optimal value considering sequences up to i,j
int[][] dp = new int[m+1][n+1];

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (condition_met) {
            dp[i][j] = dp[i-1][j-1] + 1; // Match case
        } else {
            dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // No match
        }
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **Longest Common Subsequence** | `dp[i][j] = LCS length of first i,j chars` | Match: `dp[i-1][j-1] + 1`, No match: `max(dp[i-1][j], dp[i][j-1])` |
| **Edit Distance** | `dp[i][j] = min edits to transform string1[0:i] to string2[0:j]` | `min(insert, delete, replace)` |
| **Longest Increasing Subsequence** | `dp[i] = length of LIS ending at i` | `dp[i] = max(dp[j] + 1)` for j < i and nums[j] < nums[i] |

## 📋 Pattern 5: Interval Problems

### Pattern Recognition Signals
- **Keywords**: "intervals", "ranges", "segments", "palindromes"
- **Structure**: Break problem into smaller intervals
- **Goal**: Find optimal way to handle intervals

### Template
```java
// State: dp[i][j] = optimal value for interval [i, j]
int[][] dp = new int[n][n];

// Fill for increasing interval lengths
for (int len = 1; len <= n; len++) {
    for (int i = 0; i <= n - len; i++) {
        int j = i + len - 1;
        
        // Base case
        if (len == 1) {
            dp[i][j] = base_value;
            continue;
        }
        
        // Try all possible splits
        for (int k = i; k < j; k++) {
            dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k+1][j] + cost);
        }
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **Matrix Chain Multiplication** | `dp[i][j] = min multiplications for matrices i to j` | Try all split points k |
| **Palindromic Substrings** | `dp[i][j] = is substring [i,j] palindrome` | `dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]` |
| **Burst Balloons** | `dp[i][j] = max coins from bursting balloons in (i,j)` | Try each balloon k as last to burst |

## 📋 Pattern 6: Knapsack Problems

### Pattern Recognition Signals
- **Keywords**: "capacity", "weight limit", "choose items", "subset with constraint"
- **Structure**: Choose subset of items with constraints
- **Goal**: Maximize/minimize value subject to constraints

### Template
```java
// State: dp[i][w] = max value using first i items with weight limit w
int[][] dp = new int[n+1][capacity+1];

for (int i = 1; i <= n; i++) {
    for (int w = 0; w <= capacity; w++) {
        // Don't take item i
        dp[i][w] = dp[i-1][w];
        
        // Take item i (if fits)
        if (weight[i-1] <= w) {
            dp[i][w] = Math.max(dp[i][w], dp[i-1][w-weight[i-1]] + value[i-1]);
        }
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **0/1 Knapsack** | `dp[i][w] = max value with first i items, weight ≤ w` | Take or don't take item i |
| **Coin Change** | `dp[i] = min coins for amount i` | Try each coin denomination |
| **Partition Equal Subset Sum** | `dp[i] = can achieve sum i` | Include or exclude each number |

## 📋 Pattern 7: State Machine Problems

### Pattern Recognition Signals
- **Keywords**: "states", "transitions", "buy/sell", "hold/rest"
- **Structure**: Multiple states with transitions between them
- **Goal**: Find optimal sequence of state transitions

### Template
```java
// State: dp[i][state] = optimal value at day i in given state
int[][] dp = new int[n][num_states];

for (int i = 0; i < n; i++) {
    for (int state = 0; state < num_states; state++) {
        // Try all possible transitions to this state
        for (int prev_state : possible_previous_states) {
            dp[i][state] = Math.max(dp[i][state], 
                                   dp[i-1][prev_state] + transition_cost);
        }
    }
}
```

### Problems in This Family
| Problem | State Definition | Transition |
|---------|------------------|------------|
| **Best Time to Buy and Sell Stock II** | `dp[i][0/1] = max profit on day i, holding 0/1 stock` | Buy, sell, or hold |
| **House Robber** | `dp[i][0/1] = max money at house i, robbed/not robbed` | Rob current house or skip |

## 🎯 Quick Problem Classification Guide

### Step 1: Read the Problem
Look for these **trigger phrases**:

| Phrase | Likely Pattern |
|--------|----------------|
| "minimum number of", "minimum cost" | **Minimum Path** |
| "maximum profit", "maximum value" | **Maximum Path** or **Knapsack** |
| "how many ways" | **Counting** |
| "can we", "is it possible" | **Feasibility** |
| "longest subsequence" | **Subsequence** |
| "intervals", "palindrome" | **Interval** |
| "capacity", "weight limit" | **Knapsack** |
| "buy/sell", "states" | **State Machine** |

### Step 2: Identify the Structure
- **1D array input** → Usually Minimum Path or Counting
- **2D grid** → Often Minimum Path with 2D states
- **String problems** → Subsequence or Interval patterns
- **Multiple constraints** → Knapsack or State Machine

### Step 3: Apply the Template
1. Choose the matching pattern from above
2. Define your state clearly
3. Write the recurrence relation
4. Implement using the template

## 💡 Pro Tips for DP Mastery

### Recognition Shortcuts
1. **Optimization + Choices** = DP candidate
2. **"All possible ways"** = Counting DP
3. **"Can we achieve"** = Feasibility DP
4. **Comparing sequences** = Subsequence DP

### Common Pitfalls to Avoid
1. **Wrong state definition** → Most common error
2. **Missing base cases** → Leads to wrong answers
3. **Wrong fill order** → Accessing uncomputed states
4. **Boundary conditions** → Index out of bounds

### Learning Strategy
1. **Master one pattern at a time** → Start with Minimum Path
2. **Practice recognition first** → Before implementing
3. **Trace through examples** → Understand the mechanics
4. **Compare different approaches** → DP vs Greedy vs others

Remember: **Jump Game II is your gateway to Minimum Path DP mastery!** Once you understand this pattern deeply, you'll recognize it everywhere.