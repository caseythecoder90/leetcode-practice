# Best Time to Buy and Sell Stock II - Flashcards

## Problem Identification

**Q: How do you identify this as a greedy problem vs DP?**
A: **Key indicators for greedy:**
- Unlimited transactions allowed
- Can buy and sell on same day  
- No transaction costs or constraints
- "Maximum profit" with no limits

**Q: What's the difference between Stock I and Stock II?**
A: 
- **Stock I**: Single transaction only → Use DP (track min price seen)
- **Stock II**: Unlimited transactions → Use Greedy (sum positive differences)

**Q: Why does greedy work for Stock II but not Stock I?**
A: In Stock II, every profitable opportunity should be taken because there's no transaction limit. In Stock I, we must choose the single best transaction, so local optimization might miss the global optimum.

## Core Algorithm

**Q: What is the greedy algorithm for Stock II?**
A: **Sum all positive price differences between consecutive days.**
```java
int profit = 0;
for (int i = 1; i < prices.length; i++) {
    if (prices[i] > prices[i-1]) {
        profit += prices[i] - prices[i-1];
    }
}
return profit;
```

**Q: What does each positive difference represent?**
A: Each positive difference `prices[i] - prices[i-1]` represents a profitable trade:
- Buy on day i-1 at price `prices[i-1]`
- Sell on day i at price `prices[i]`
- Profit = `prices[i] - prices[i-1]`

**Q: How do you trace through the algorithm with [7,1,5,3,6,4]?**
A: 
```
Day 0→1: 7→1 (diff = -6, skip)
Day 1→2: 1→5 (diff = +4, add to profit)
Day 2→3: 5→3 (diff = -2, skip) 
Day 3→4: 3→6 (diff = +3, add to profit)
Day 4→5: 6→4 (diff = -2, skip)
Total profit = 4 + 3 = 7
```

## Mathematical Understanding

**Q: Why is summing positive differences equivalent to optimal trading?**
A: **Mathematical equivalence**: Any sequence of profitable trades can be decomposed into capturing all upward price movements. 

Example: Buying at 1 and selling at 6 (profit=5) is less optimal than buying at 1, selling at 5, buying at 3, selling at 6 (profit=4+3=7).

**Q: What's the intuition behind "capture every price increase"?**
A: Since we can make unlimited transactions and buy/sell on the same day, there's no reason to miss any profitable opportunity. Every upward price movement should be captured.

**Q: Does the algorithm actually perform the optimal transactions?**
A: The algorithm finds the optimal profit, but the actual trading strategy might be different. However, the profit value is guaranteed to be optimal due to the mathematical equivalence.

## Edge Cases and Implementation

**Q: What edge cases should you consider?**
A: 
1. **Empty array or single element**: Return 0 (no transactions possible)
2. **Strictly decreasing prices**: Return 0 (no profitable trades)
3. **Strictly increasing prices**: Sum all differences = (last - first)

**Q: What's the time and space complexity?**
A: 
- **Time**: O(n) - single pass through the array
- **Space**: O(1) - only using a profit variable

**Q: How would you modify the algorithm if there was a transaction fee?**
A: With transaction fee, this becomes a DP problem:
```java
// Would need DP to track holding vs not holding states
// Only execute trade if profit > fee
```

## Pattern Recognition

**Q: What other problems use the "sum positive differences" pattern?**
A: 
- Array problems where you want to capture all "gains"
- Problems with unlimited operations and no constraints
- Greedy optimization problems with independent opportunities

**Q: When should you NOT use greedy for trading problems?**
A: When there are constraints:
- Limited number of transactions (Stock III, IV)
- Transaction fees (Stock with Fee)
- Cooldown periods (Stock with Cooldown)
- These require DP to optimally handle the constraints

**Q: How do you decide between greedy and DP for optimization problems?**
A: 
- **Greedy**: When local optimal choices lead to global optimum (no constraints affecting future decisions)
- **DP**: When future optimal choices depend on current state (constraints, limited resources, etc.)

## Common Mistakes

**Q: What's wrong with trying to track actual buy/sell days?**
A: It adds unnecessary complexity. The greedy approach automatically finds the optimal profit without needing to track specific transactions.

**Q: Why is using DP overkill for this problem?**
A: DP works but is unnecessarily complex. The greedy solution is simpler, more efficient, and easier to understand. Save DP for problems with actual constraints.

**Q: What's the most common implementation mistake?**
A: Forgetting to handle the single-element array case, or trying to access `prices[i-1]` when `i=0`.

## Same-Day Trading Clarification

**Q: What does "can buy and sell on same day" really mean?**
A: It means you have **complete trading flexibility** - you can:
- Sell your position and immediately buy again
- Make multiple buy-sell cycles within a single day
- Enter/exit positions without timing constraints

**Q: Why is same-day trading crucial for the greedy approach?**
A: It eliminates timing constraints. Without it, you'd need to consider holding periods and opportunity costs, making it a complex DP problem. With it, every price increase becomes an independent profitable opportunity.

**Q: What would happen if same-day trading wasn't allowed?**
A: The problem would become much more complex, requiring DP to handle the timing constraints and trade-offs between holding vs selling.
