# Online Stock Span (LeetCode 901)

**Pattern**: Monotonic Stack | **Difficulty**: Medium | **Type**: Previous Smaller Element with Counting
**LeetCode 75**: ✅ (Stack/Monotonic Stack section)

## Problem Statement

Design an algorithm that collects daily price quotes for some stock and returns the **span** of that stock's price for the current day.

The **span** is the maximum number of consecutive days (starting from today and going backward) for which the stock price was **≤** today's price.

### Example
```
Input: [100, 80, 60, 70, 60, 75, 85]
Output: [1, 1, 1, 2, 1, 4, 6]

Day 0: 100 → span = 1 (just today)
Day 1: 80 → span = 1 (80 < 100, just today) 
Day 2: 60 → span = 1 (60 < 80, just today)
Day 3: 70 → span = 2 (70 ≥ 60, covers 2 days: 60,70)
Day 4: 60 → span = 1 (60 < 70, just today)
Day 5: 75 → span = 4 (75 ≥ 60,70,60, covers 4 days: 60,70,60,75)
Day 6: 85 → span = 6 (85 ≥ 80,60,70,60,75, covers 6 days total)
```

## Solution Approaches

### 1. 🎯 **StockSpannerOptimal.java** (RECOMMENDED)
- **Algorithm**: Monotonic decreasing stack with `[price, span]` pairs
- **Time**: O(n) total across all operations | **Space**: O(n)
- **Best for**: Interview standard, production code
- **Key Learning**: Block aggregation technique

### 2. 📚 **StockSpannerSimple.java** (YOUR CURRENT APPROACH)
- **Algorithm**: Linear scan backward for each operation
- **Time**: O(n²) worst case | **Space**: O(n)
- **Best for**: Understanding the problem, small inputs
- **Key Learning**: Shows why optimization is needed

### 3. 📋 **OnlineStockSpan.java** (ALL-IN-ONE)
- **Algorithm**: All approaches with detailed comparisons
- **Best for**: Seeing performance differences side-by-side
- **Key Learning**: Understanding complexity trade-offs

## Why Your Solution is Only Beating 5%

Your current solution is **correct** but **O(n²) in worst case**:

```java
// Your approach - rescans previous elements each time
public int next(int price) {
    stockPrices.add(price);  // O(1)
    
    // This loop can be O(n) each call!
    for (int i = stockPrices.size() - 1; i >= 0; i--) {
        if (stockPrices.get(i) <= price) span++;
        else break;
    }
    return span;
}
```

**Worst case**: Increasing sequence `[1,2,3,4,5...]` requires O(n²) total work.

## The Optimal Insight: Your POJO Idea!

You were **absolutely right** about tracking spans in objects! The optimal solution does exactly that:

```java
// Your insight: "track the span for each node in some POJO"
Stack<int[]> stack;  // [price, span] - your POJO concept!

public int next(int price) {
    int span = 1;
    
    // Your insight: "if next node ≤ current, add its span"
    while (!stack.isEmpty() && stack.peek()[0] <= price) {
        span += stack.pop()[1];  // Add entire span - no recounting!
    }
    
    stack.push(new int[]{price, span});
    return span;
}
```

## Quick Start Guide

### Step 1: Understand Your Current Solution
Your approach is correct and shows great problem-solving intuition!

### Step 2: See Why It's O(n²)
Run the performance comparison to see the difference with large inputs.

### Step 3: Learn the Optimal Pattern
Study how the monotonic stack eliminates redundant work through "block aggregation".

### Step 4: Practice the Pattern
Apply this to similar problems like Daily Temperatures.

## Files in This Directory

```
OnlineStockSpan/
├── README.md                    # This file - problem overview
├── StudyGuide.md               # Detailed algorithm explanation
├── OnlineStockSpan.java        # All approaches with comparisons
├── StockSpannerOptimal.java    # Optimal monotonic stack solution
└── StockSpannerSimple.java     # Your current approach (for comparison)
```

## Running the Solutions

```bash
# Run the comprehensive comparison
javac OnlineStockSpan.java
java OnlineStockSpan

# Or with Maven
mvn exec:java -Dexec.mainClass="leetcode.monotonicstack.problems.OnlineStockSpan.OnlineStockSpan"
```

## Key Insights

1. **Your intuition was correct** - tracking spans in objects is the right approach
2. **The issue was recalculation** - you were recomputing spans from scratch
3. **Block aggregation** - consume entire blocks instead of individual elements
4. **Amortized analysis** - each element is processed exactly once total
5. **Pattern recognition** - this applies to many "consecutive count" problems

## Interview Tips

- **Start with your current approach** - shows problem understanding
- **Identify the bottleneck** - "This rescans previous elements each time"
- **Propose the optimization** - "We can cache spans to avoid recalculation"
- **Implement monotonic stack** - "Use a stack to track [price, span] blocks"
- **Explain amortized complexity** - "Each element is processed exactly once"

Your solution shows excellent problem-solving skills! The jump to optimal is learning to recognize this specific pattern.

---

## Related Problems
- [Daily Temperatures](../DailyTemperatures/) - Next greater element
- [Next Greater Element I](../NextGreaterElementI/) - Basic pattern
- [Largest Rectangle in Histogram](../LargestRectangle/) - Advanced application