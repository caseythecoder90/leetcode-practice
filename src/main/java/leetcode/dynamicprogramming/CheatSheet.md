# Dynamic Programming - Cheat Sheet

## Problem Recognition Quick Guide

| Keywords | DP Type | Example Problem |
|----------|---------|-----------------|
| "maximum/minimum", "optimal" | Optimization DP | Max subarray, Min path sum |
| "number of ways", "count" | Counting DP | Climbing stairs, Coin change |
| "can we achieve", "is possible" | Decision DP | Partition equal subset |
| "longest/shortest" sequence | Sequence DP | LIS, LCS |
| Grid traversal, paths | Grid DP | Unique paths, Min path sum |

## Core DP Templates

### 1. Top-Down (Memoization) Template
```java
class Solution {
    private Map<String, Integer> memo = new HashMap<>();
    
    public int solve(int[] arr, int param1, int param2) {
        return dp(arr, param1, param2);
    }
    
    private int dp(int[] arr, int param1, int param2) {
        // Base case
        if (param1 < 0 || param1 >= arr.length) {
            return baseValue;
        }
        
        // Check memo
        String key = param1 + "," + param2;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Recurrence relation
        int option1 = dp(arr, param1 + 1, param2);
        int option2 = dp(arr, param1 + 2, param2 - arr[param1]);
        int result = Math.max(option1, option2); // or min, or sum, etc.
        
        // Store in memo
        memo.put(key, result);
        return result;
    }
}
```

### 2. Bottom-Up (Tabulation) Template
```java
class Solution {
    public int solve(int[] arr, int target) {
        int n = arr.length;
        
        // Initialize DP table
        int[][] dp = new int[n + 1][target + 1];
        
        // Base cases
        for (int i = 0; i <= n; i++) {
            dp[i][0] = baseValue;
        }
        
        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                // Recurrence relation
                int option1 = dp[i-1][j];
                int option2 = (j >= arr[i-1]) ? dp[i-1][j-arr[i-1]] : 0;
                dp[i][j] = Math.max(option1, option2);
            }
        }
        
        return dp[n][target];
    }
}
```

## Common DP Patterns and Recurrence Relations

### 1. Linear DP (Fibonacci-like)
```java
// Pattern: dp[i] depends on previous states
dp[i] = dp[i-1] + dp[i-2];                    // Fibonacci
dp[i] = Math.max(dp[i-1], dp[i-2] + arr[i]);  // House Robber
dp[i] = dp[i-1] + dp[i-2] + dp[i-3];          // Tribonacci
```

### 2. Grid DP
```java
// Pattern: dp[i][j] depends on dp[i-1][j] and dp[i][j-1]
dp[i][j] = dp[i-1][j] + dp[i][j-1];           // Unique Paths
dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j]; // Min Path Sum
```

### 3. Knapsack Pattern
```java
// 0/1 Knapsack: dp[i][w] = max value using first i items, weight limit w
for (int i = 1; i <= n; i++) {
    for (int w = 1; w <= capacity; w++) {
        if (weight[i-1] <= w) {
            dp[i][w] = Math.max(dp[i-1][w], 
                               dp[i-1][w-weight[i-1]] + value[i-1]);
        } else {
            dp[i][w] = dp[i-1][w];
        }
    }
}

// Unbounded Knapsack (coins): Can use each item multiple times
for (int i = 1; i <= n; i++) {
    for (int w = weight[i-1]; w <= capacity; w++) {
        dp[w] = Math.max(dp[w], dp[w-weight[i-1]] + value[i-1]);
    }
}
```

### 4. String DP
```java
// Longest Common Subsequence
if (s1.charAt(i-1) == s2.charAt(j-1)) {
    dp[i][j] = dp[i-1][j-1] + 1;
} else {
    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
}

// Edit Distance
if (s1.charAt(i-1) == s2.charAt(j-1)) {
    dp[i][j] = dp[i-1][j-1];
} else {
    dp[i][j] = 1 + Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]);
}
```

## Space Optimization Techniques

### 1. Rolling Array (2D → 1D)
```java
// Before: dp[i][j] depends only on dp[i-1][j] and dp[i][j-1]
int[][] dp = new int[m][n];

// After: Use 1D array
int[] dp = new int[n];
for (int i = 0; i < m; i++) {
    for (int j = 1; j < n; j++) {
        dp[j] = dp[j] + dp[j-1]; // dp[j] represents dp[i][j]
    }
}
```

### 2. Few Variables (when only need last few states)
```java
// Fibonacci: dp[i] = dp[i-1] + dp[i-2]
// Instead of array, use two variables
int prev2 = 0, prev1 = 1;
for (int i = 2; i <= n; i++) {
    int curr = prev1 + prev2;
    prev2 = prev1;
    prev1 = curr;
}
```

## Debugging DP Solutions

### 1. Trace Through Small Examples
```java
// Add print statements to see state transitions
System.out.println("dp[" + i + "][" + j + "] = " + dp[i][j]);

// Print the entire DP table
for (int i = 0; i <= n; i++) {
    System.out.println(Arrays.toString(dp[i]));
}
```

### 2. Verify Base Cases
```java
// Make sure base cases are correct
assert dp[0][0] == expectedBaseValue;
```

### 3. Check Boundary Conditions
```java
// Ensure you're not accessing out-of-bounds indices
if (i > 0) dp[i][j] += dp[i-1][j];
if (j > 0) dp[i][j] += dp[i][j-1];
```

## Time/Space Complexity Patterns

| DP Type | Time Complexity | Space Complexity | Space Optimized |
|---------|-----------------|------------------|-----------------|
| 1D Linear | O(n) | O(n) | O(1) |
| 2D Grid | O(m*n) | O(m*n) | O(min(m,n)) |
| Knapsack | O(n*W) | O(n*W) | O(W) |
| String DP | O(m*n) | O(m*n) | O(min(m,n)) |
| Tree DP | O(n) | O(h) | O(h) where h=height |

## Common Gotchas and Solutions

### 1. Index Management
```java
// ❌ Confusing 0-indexed array with 1-indexed DP
dp[i] = arr[i-1] + dp[i-1]; // WRONG if arr is 0-indexed

// ✅ Be explicit about indexing
dp[i] = arr[i-1] + dp[i-1]; // dp[i] represents solution for arr[0...i-1]
```

### 2. Base Case Initialization
```java
// ❌ Forgetting to initialize base cases
int[] dp = new int[n+1]; // All zeros by default

// ✅ Explicitly set base cases
dp[0] = 1; // or whatever the base case should be
```

### 3. State Definition
```java
// ❌ Ambiguous state meaning
// What does dp[i] represent exactly?

// ✅ Clear state definition
// dp[i] = maximum profit from houses 0 to i (inclusive)
// dp[i][j] = minimum cost to reach cell (i,j)
```

## Problem-Solving Workflow

### Step 1: Identify DP
- [ ] Has optimal substructure
- [ ] Has overlapping subproblems  
- [ ] Involves optimization/counting/decision

### Step 2: Define State
- [ ] What parameters change in recursion?
- [ ] What information needed for each subproblem?
- [ ] How many dimensions needed?

### Step 3: Find Recurrence
- [ ] What are the base cases?
- [ ] How to express problem in terms of smaller problems?
- [ ] What are all the choices at each step?

### Step 4: Implement
- [ ] Start with recursive solution
- [ ] Add memoization
- [ ] Convert to tabulation if needed
- [ ] Optimize space if possible

### Step 5: Test
- [ ] Trace through small examples
- [ ] Verify base cases
- [ ] Check edge cases

## Quick Reference: Java Collections for DP

```java
// For memoization
Map<String, Integer> memo = new HashMap<>();
Map<Integer, Integer> memo = new HashMap<>();

// For 2D DP
int[][] dp = new int[m][n];

// For variable size
List<List<Integer>> dp = new ArrayList<>();

// Initialize with specific values
Arrays.fill(dp, -1);              // 1D
for (int[] row : dp) Arrays.fill(row, -1); // 2D
```