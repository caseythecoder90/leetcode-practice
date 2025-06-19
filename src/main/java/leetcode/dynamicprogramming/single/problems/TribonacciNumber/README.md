# N-th Tribonacci Number

**LeetCode Problem #1137** | **Easy**

## Problem Description

The Tribonacci sequence Tn is defined as follows:

- T0 = 0
- T1 = 1  
- T2 = 1
- Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0

Given `n`, return the value of Tn.

### Examples

**Example 1:**
```
Input: n = 4
Output: 4
Explanation:
T_0 = 0, T_1 = 1, T_2 = 1, T_3 = 2, T_4 = 4
```

**Example 2:**
```
Input: n = 25
Output: 1389537
```

### Constraints
- `0 <= n <= 37`
- The answer is guaranteed to fit within a 32-bit integer

## Understanding the Problem

### What is Tribonacci?
Tribonacci is similar to Fibonacci, but instead of summing the previous 2 numbers, we sum the previous **3 numbers**.

**Comparison**:
- **Fibonacci**: F(n) = F(n-1) + F(n-2)
- **Tribonacci**: T(n) = T(n-1) + T(n-2) + T(n-3)

### Sequence Walkthrough
Let's build the first few terms:
```
T(0) = 0                           (given)
T(1) = 1                           (given)
T(2) = 1                           (given)
T(3) = T(2) + T(1) + T(0) = 1+1+0 = 2
T(4) = T(3) + T(2) + T(1) = 2+1+1 = 4
T(5) = T(4) + T(3) + T(2) = 4+2+1 = 7
T(6) = T(5) + T(4) + T(3) = 7+4+2 = 13
```

## Solution Approaches

### Approach 1: Recursive (Naive)
**Idea**: Directly implement the recurrence relation.

**Problems**: 
- Exponential time complexity O(3^n)
- Recalculates same subproblems many times

```java
public int tribonacci(int n) {
    if (n == 0) return 0;
    if (n == 1 || n == 2) return 1;
    return tribonacci(n-1) + tribonacci(n-2) + tribonacci(n-3);
}
```

**Why this is inefficient**: For T(5), we calculate T(2) multiple times:
```
T(5)
├── T(4)
│   ├── T(3)
│   │   ├── T(2) ← calculated here
│   │   ├── T(1)
│   │   └── T(0)
│   ├── T(2) ← calculated again
│   └── T(1)
├── T(3)
│   ├── T(2) ← calculated again!
│   ├── T(1)
│   └── T(0)
└── T(2) ← calculated again!
```

### Approach 2: Top-Down DP (Memoization)
**Idea**: Use recursion but store results to avoid recalculation.

**Time Complexity**: O(n)
**Space Complexity**: O(n) for recursion stack + memoization

```java
class Solution {
    private Map<Integer, Integer> memo = new HashMap<>();
    
    public int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        int result = tribonacci(n-1) + tribonacci(n-2) + tribonacci(n-3);
        memo.put(n, result);
        return result;
    }
}
```

### Approach 3: Bottom-Up DP (Tabulation)
**Idea**: Build up the solution from base cases.

**Time Complexity**: O(n)
**Space Complexity**: O(n)

```java
public int tribonacci(int n) {
    if (n == 0) return 0;
    if (n == 1 || n == 2) return 1;
    
    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    dp[2] = 1;
    
    for (int i = 3; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
    }
    
    return dp[n];
}
```

### Approach 4: Space-Optimized DP (Recommended)
**Idea**: Only keep track of the last 3 values since that's all we need.

**Time Complexity**: O(n)
**Space Complexity**: O(1)

```java
public int tribonacci(int n) {
    if (n == 0) return 0;
    if (n == 1 || n == 2) return 1;
    
    int a = 0;  // T(n-3)
    int b = 1;  // T(n-2)  
    int c = 1;  // T(n-1)
    
    for (int i = 3; i <= n; i++) {
        int next = a + b + c;  // T(n)
        a = b;     // Update T(n-3) for next iteration
        b = c;     // Update T(n-2) for next iteration
        c = next;  // Update T(n-1) for next iteration
    }
    
    return c;
}
```

## Step-by-Step Trace Example

Let's trace through `tribonacci(5)` using the space-optimized approach:

**Initial State**:
```
n = 5
a = 0 (T0), b = 1 (T1), c = 1 (T2)
```

**Iteration 1 (i = 3)**:
```
next = a + b + c = 0 + 1 + 1 = 2  (This is T3)
a = b = 1  (T1 becomes T0 for next iteration)
b = c = 1  (T2 becomes T1 for next iteration)  
c = next = 2  (T3 becomes T2 for next iteration)
```

**Iteration 2 (i = 4)**:
```
next = a + b + c = 1 + 1 + 2 = 4  (This is T4)
a = b = 1
b = c = 2
c = next = 4
```

**Iteration 3 (i = 5)**:
```
next = a + b + c = 1 + 2 + 4 = 7  (This is T5)
a = b = 2
b = c = 4  
c = next = 7
```

**Return**: c = 7

## Implementation Patterns for Tribonacci

### Pattern 1: Array-Based (Good for Learning)
```java
public int tribonacci(int n) {
    if (n < 3) return n == 0 ? 0 : 1;
    
    int[] t = new int[n + 1];
    t[0] = 0; t[1] = 1; t[2] = 1;
    
    for (int i = 3; i <= n; i++) {
        t[i] = t[i-1] + t[i-2] + t[i-3];
    }
    
    return t[n];
}
```

### Pattern 2: Three Variables (Space Efficient)
```java
public int tribonacci(int n) {
    if (n < 3) return n == 0 ? 0 : 1;
    
    int prev3 = 0, prev2 = 1, prev1 = 1;
    
    for (int i = 3; i <= n; i++) {
        int current = prev1 + prev2 + prev3;
        prev3 = prev2;
        prev2 = prev1; 
        prev1 = current;
    }
    
    return prev1;
}
```

## Common Mistakes and Edge Cases

### Mistake 1: Wrong Base Cases
❌ **Wrong**: Assuming T(2) = 2 (like Fibonacci)
✅ **Correct**: T(0)=0, T(1)=1, T(2)=1

### Mistake 2: Variable Update Order
❌ **Wrong Order**:
```java
a = b;
b = c;  
c = a + b + c;  // Wrong! 'a' and 'b' already changed
```

✅ **Correct Order**:
```java
int next = a + b + c;  // Calculate first
a = b;
b = c;
c = next;             // Update last
```

### Mistake 3: Not Handling n < 3
❌ **Wrong**: Starting loop from i=0 without proper base cases
✅ **Correct**: Handle n=0, n=1, n=2 separately

### Edge Cases to Test
- `n = 0` → Should return 0
- `n = 1` → Should return 1  
- `n = 2` → Should return 1
- `n = 3` → Should return 2
- Large n within constraints (n=37)

## Variations and Extensions

### 1. K-bonacci Numbers
Generalize to sum of previous K numbers:
```java
public int kBonacci(int n, int k) {
    if (n < k-1) return 0;
    if (n == k-1) return 1;
    
    int[] dp = new int[k];
    dp[k-1] = 1;
    
    int sum = 1;
    for (int i = k; i <= n; i++) {
        int next = sum;
        sum = sum - dp[i % k] + next;
        dp[i % k] = next;
    }
    
    return dp[n % k];
}
```

### 2. Tribonacci with Different Starting Values
What if T(0)=1, T(1)=2, T(2)=3?
```java
public int customTribonacci(int n) {
    if (n == 0) return 1;
    if (n == 1) return 2;
    if (n == 2) return 3;
    
    int a = 1, b = 2, c = 3;
    for (int i = 3; i <= n; i++) {
        int next = a + b + c;
        a = b; b = c; c = next;
    }
    return c;
}
```

## Complexity Analysis Summary

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Recursive | O(3^n) | O(n) | Simple to understand | Too slow |
| Memoization | O(n) | O(n) | Natural recursion | Extra space for call stack |
| Tabulation | O(n) | O(n) | Iterative, clear | Uses O(n) space |
| Space-Optimized | O(n) | O(1) | Most efficient | Slightly more complex |

## Tips for Success

1. **Start with the pattern**: Identify this as a Fibonacci-like sequence
2. **Write base cases first**: Handle n=0, n=1, n=2 explicitly  
3. **Trace small examples**: Walk through n=3, n=4 by hand
4. **Space optimization**: Only keep what you need (last 3 values)
5. **Test edge cases**: Don't forget n=0, n=1, n=2

## Related Problems
- **Fibonacci Number** (LeetCode #509) - Uses 2 previous terms
- **Climbing Stairs** (LeetCode #70) - Same pattern as Fibonacci
- **Min Cost Climbing Stairs** (LeetCode #746) - Optimization version
- **House Robber** (LeetCode #198) - Different recurrence but similar DP approach

## Tags
`Dynamic Programming` `Math` `Memoization` `Easy`