# Best Time to Buy and Sell Stock II (LeetCode 122) - Study Guide

**Pattern**: Greedy Algorithm | **Difficulty**: Medium | **Type**: Optimization with Unlimited Transactions

## Problem Statement

You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

On each day, you may decide to buy and/or sell the stock. You can only hold **at most one** share of the stock at any time. However, you can buy it then immediately sell it on the **same day**.

Find and return the **maximum profit** you can achieve.

### Examples

**Example 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.
```

**Example 2:**
```
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit is 4.
```

**Example 3:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
```

## Problem Analysis

### Pattern Recognition
- **Keywords**: "maximum profit", "unlimited transactions", "buy and sell on same day"
- **Pattern**: **Greedy Algorithm** - make locally optimal choices
- **Key Difference from Stock I**: Unlimited transactions vs single transaction
- **Constraint**: At most one share at any time

### Why Greedy Works Here
Unlike many optimization problems, this one has a special property: **every profitable opportunity should be taken** because:

1. **No transaction limit**: Unlike Stock I, we can make as many transactions as we want
2. **Same-day trading allowed**: We can buy and sell on the same day (this is crucial!)
3. **No holding cost**: No penalty for multiple transactions
4. **Independent opportunities**: Taking one profit doesn't prevent us from taking another

**The same-day trading rule is what makes greedy optimal!** Without it, we'd need to consider holding periods and timing, making it a more complex DP problem.

### The Greedy Insight

**Key Realization**: Instead of thinking about "when to buy and sell", think about **capturing every price increase**.

If prices go up from day i to day i+1, we should:
- "Buy" on day i
- "Sell" on day i+1
- Profit = prices[i+1] - prices[i]

## Solution Approaches

### Approach 1: Greedy (Optimal) - O(n)

```java
public int maxProfit(int[] prices) {
    int profit = 0;
    
    for (int i = 1; i < prices.length; i++) {
        // Capture every positive price difference
        if (prices[i] > prices[i-1]) {
            profit += prices[i] - prices[i-1];
        }
    }
    
    return profit;
}
```

**Time Complexity**: O(n) - single pass through array  
**Space Complexity**: O(1) - only using profit variable

### Approach 2: Transaction Tracking - O(n)

```java
public int maxProfit(int[] prices) {
    int profit = 0;
    int buyPrice = prices[0];
    
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > buyPrice) {
            // Sell and immediately rebuy
            profit += prices[i] - buyPrice;
        }
        buyPrice = prices[i];  // Always update to current price
    }
    
    return profit;
}
```

This approach simulates actual trading but produces the same result.

### Approach 3: Dynamic Programming (Overkill) - O(n)

```java
public int maxProfit(int[] prices) {
    int n = prices.length;
    // dp[i][0] = max profit on day i when not holding stock
    // dp[i][1] = max profit on day i when holding stock
    int[][] dp = new int[n][2];
    
    dp[0][0] = 0;           // Day 0, no stock
    dp[0][1] = -prices[0];  // Day 0, bought stock
    
    for (int i = 1; i < n; i++) {
        dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);  // Sell today
        dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);  // Buy today
    }
    
    return dp[n-1][0];  // End without holding stock
}
```

**Note**: DP approach works but is unnecessarily complex for this problem.

## Algorithm Walkthrough

### Example: prices = [7,1,5,3,6,4]

**Greedy Approach:**
```
Day 0 → Day 1: 7 → 1 (decrease, no profit)
Day 1 → Day 2: 1 → 5 (increase, profit = 4)
Day 2 → Day 3: 5 → 3 (decrease, no profit) 
Day 3 → Day 4: 3 → 6 (increase, profit = 3)
Day 4 → Day 5: 6 → 4 (decrease, no profit)

Total profit = 4 + 3 = 7
```

**Transaction View:**
- Buy at price 1, sell at price 5: profit = 4
- Buy at price 3, sell at price 6: profit = 3
- Total: 7

Both views give the same answer!

### Example: prices = [1,2,3,4,5]

**Greedy Approach:**
```
1→2: +1, 2→3: +1, 3→4: +1, 4→5: +1
Total = 4
```

**Transaction View:**
- Could buy at 1, sell at 5: profit = 4
- Or buy at 1, sell at 2, buy at 2, sell at 3, etc.: profit = 1+1+1+1 = 4

Same result!

## Why Your Intuition About Missing the Best Path is Wrong

You mentioned: "we also may miss the best path if we do greedy"

This is actually **not true** for this specific problem! Here's why:

### Mathematical Proof
Consider any sequence of prices [p₀, p₁, p₂, ..., pₙ].

**Claim**: The greedy approach (sum all positive differences) gives the optimal profit.

**Proof**: 
- Any valid transaction sequence can be represented as buying at some prices and selling at some higher prices
- The total profit is: Σ(sell_prices) - Σ(buy_prices)
- To maximize this, we want to:
  - Buy at every local minimum
  - Sell at every local maximum
- This is exactly what the greedy approach does!

### Why Greedy Fails in Other Problems But Not Here

**Stock I (single transaction)**: Greedy fails because we can only make one transaction. The globally optimal buy/sell pair might not be locally optimal.

**Stock II (unlimited transactions)**: Greedy works because every profitable opportunity should be taken. There's no reason to skip a profitable trade.

## Common Mistakes

### Mistake 1: Overthinking the Problem
```java
// DON'T do this - too complex
public int maxProfit(int[] prices) {
    List<Integer> buyDays = new ArrayList<>();
    List<Integer> sellDays = new ArrayList<>();
    // Complex logic to find buy/sell days...
}
```

### Mistake 2: Using DP When Greedy Suffices
The DP solution works but adds unnecessary complexity. Save DP for Stock problems with transaction limits.

### Mistake 3: Missing Edge Cases
```java
// Handle arrays with < 2 elements
if (prices.length < 2) return 0;
```

## Pattern Recognition for Stock Problems

| Problem | Transactions | Algorithm | Key Insight |
|---------|-------------|-----------|-------------|
| **Stock I** | 1 | DP/Greedy | Track min price seen so far |
| **Stock II** | Unlimited | **Greedy** | Sum all positive differences |
| **Stock III** | 2 | DP | Track profits for transaction 1 and 2 |
| **Stock IV** | k | DP | Generalize Stock III |
| **Stock with Fee** | Unlimited + Fee | DP | Account for transaction cost |
| **Stock with Cooldown** | Unlimited + Wait | DP | Add cooldown state |

## Key Takeaways

1. **Greedy Algorithm**: This is a pure greedy problem - always take every profitable opportunity
2. **Mathematical Equivalence**: Sum of positive differences = optimal transaction sequence
3. **No Complex State Tracking**: Unlike other stock problems, this doesn't need DP
4. **Pattern Recognition**: Unlimited transactions + no constraints = greedy algorithm
5. **Interview Tip**: Start with the simple greedy solution, then mention DP as an alternative if asked

## Practice Problems

**Similar Greedy Problems:**
- LeetCode 121: Best Time to Buy and Sell Stock I (but use different algorithm)
- LeetCode 455: Assign Cookies
- LeetCode 435: Non-overlapping Intervals

**Related Stock Problems:**
- LeetCode 123: Best Time to Buy and Sell Stock III
- LeetCode 188: Best Time to Buy and Sell Stock IV
- LeetCode 714: Best Time to Buy and Sell Stock with Transaction Fee
- LeetCode 309: Best Time to Buy and Sell Stock with Cooldown
