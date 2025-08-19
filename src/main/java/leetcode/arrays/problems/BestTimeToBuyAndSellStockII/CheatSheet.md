# Best Time to Buy and Sell Stock II - Cheat Sheet

## Quick Problem Summary
**Problem**: Buy and sell stock with unlimited transactions to maximize profit  
**Pattern**: Greedy Algorithm  
**Difficulty**: Medium  
**Key Insight**: Capture every price increase

## Core Algorithm

### Greedy Solution (Optimal)
```java
public int maxProfit(int[] prices) {
    int profit = 0;
    
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i-1]) {
            profit += prices[i] - prices[i-1];
        }
    }
    
    return profit;
}
```

**Time**: O(n) | **Space**: O(1)

### Alternative: Transaction Simulation
```java
public int maxProfit(int[] prices) {
    int profit = 0;
    int buyPrice = prices[0];
    
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > buyPrice) {
            profit += prices[i] - buyPrice;
        }
        buyPrice = prices[i];
    }
    
    return profit;
}
```

## Quick Examples

### Example 1: [7,1,5,3,6,4] → Output: 7
```
Differences: [-, -6, +4, -2, +3, -2]
Positive sum: 4 + 3 = 7
```

### Example 2: [1,2,3,4,5] → Output: 4
```
Differences: [-, +1, +1, +1, +1]  
Positive sum: 1 + 1 + 1 + 1 = 4
```

### Example 3: [7,6,4,3,1] → Output: 0
```
Differences: [-, -1, -2, -1, -2]
Positive sum: 0 (no positive differences)
```

## Pattern Recognition

**Greedy Indicators:**
- ✅ Unlimited transactions
- ✅ "Maximum profit" 
- ✅ No transaction costs
- ✅ Can buy/sell same day

**When NOT to use Greedy:**
- ❌ Limited transactions (use DP)
- ❌ Transaction fees (use DP)  
- ❌ Cooldown periods (use DP)

## Mathematical Insight

**Why greedy works**: Sum of all profitable segments equals optimal transaction sequence.

```
[1, 5, 3, 6] can be:
Option 1: Buy@1, Sell@6 = profit 5
Option 2: Buy@1, Sell@5, Buy@3, Sell@6 = (5-1) + (6-3) = 4 + 3 = 7

Greedy gives: (5-1) + (6-3) = 7 ✓
```

## Common Mistakes

1. **Overthinking**: Don't track actual buy/sell days
2. **Using DP**: Greedy is simpler and optimal here
3. **Missing edge cases**: Handle arrays with < 2 elements
4. **Wrong pattern**: This is NOT the same as Stock I

## Stock Problem Family

| Problem | Transactions | Algorithm |
|---------|-------------|-----------|
| Stock I | 1 | DP/One-pass |
| **Stock II** | ∞ | **Greedy** |
| Stock III | 2 | DP |
| Stock IV | k | DP |
| Stock + Fee | ∞ + fee | DP |

## Template for Similar Problems

```java
// Template: Sum all profitable opportunities
int result = 0;
for (int i = 1; i < array.length; i++) {
    if (array[i] > array[i-1]) {
        result += array[i] - array[i-1];
    }
}
return result;
```

**Use this template when:**
- Unlimited operations allowed
- Want to maximize cumulative gain
- No additional constraints
