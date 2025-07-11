# Online Stock Span (LeetCode 901) - Study Guide

**Pattern**: Monotonic Stack | **Difficulty**: Medium | **Type**: Previous Smaller Element with Counting

## Problem Statement

Design an algorithm that collects daily price quotes for some stock and returns the **span** of that stock's price for the current day.

The **span** of the stock's price today is defined as the maximum number of consecutive days (starting from today and going backward) for which the stock price was **less than or equal to** today's price.

### Examples

**Example 1:**
```
Input: ["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
       [[], [100], [80], [60], [70], [60], [75], [85]]
Output: [null, 1, 1, 1, 2, 1, 4, 6]

Explanation:
StockSpanner stockSpanner = new StockSpanner();
stockSpanner.next(100); // return 1 (just today)
stockSpanner.next(80);  // return 1 (just today, 80 < 100)
stockSpanner.next(60);  // return 1 (just today, 60 < 80)
stockSpanner.next(70);  // return 2 (today + yesterday: 70 >= 60)
stockSpanner.next(60);  // return 1 (just today, 60 < 70)
stockSpanner.next(75);  // return 4 (today + 3 previous: 75 >= 60,70,60)
stockSpanner.next(85);  // return 6 (today + 5 previous: 85 >= all previous)
```

## Problem Analysis

### Pattern Recognition
- **Keywords**: "consecutive days", "less than or equal", "going backward"
- **Pattern**: Previous Smaller Element with **counting/span calculation**
- **Relationship**: For each element, count how many previous elements are ≤ current
- **Output**: Count of consecutive elements (span)

### Key Insights
1. **Previous Smaller/Equal Element**: We need to find where the consecutive sequence breaks
2. **Span Calculation**: Count consecutive days where price ≤ current price
3. **Efficient Counting**: Can't use brute force O(n) per operation - need amortized O(1)
4. **Monotonic Stack**: Perfect use case for decreasing stack with span aggregation

### Why Your Current Solution is O(n²)

```java
// Your current approach - O(n²) worst case
public int next(int price) {
    stockPrices.add(price);           // O(1)
    
    int span = 0;
    for (int i = stockPrices.size() - 1; i >= 0; i--) {  // O(n) each call
        if (stockPrices.get(i) <= price) {
            span++;
        } else {
            break;
        }
    }
    return span;
}
```

**Worst Case**: Increasing sequence `[1, 2, 3, 4, 5...]`
- Call 1: Check 1 element
- Call 2: Check 2 elements
- Call 3: Check 3 elements
- ...
- Call n: Check n elements
- **Total**: 1 + 2 + 3 + ... + n = **O(n²)**

## Optimal Solution: Monotonic Stack

### Step 1: Identify the Pattern
This is a **Previous Smaller Element** problem where we need **counts** instead of just finding the element.

### Step 2: Choose Stack Strategy
- **Decreasing Stack**: Bottom to top prices decrease (with equal allowed)
- **Pop When**: `current price >= stack.top() price`
- **Store**: `[price, span]` pairs (need both value and aggregated count)

### Step 3: Key Insight - Block Aggregation
Instead of counting individual days, think in terms of **blocks**:
- Each stack element represents a "block" of consecutive days
- When we pop a block, we can add its entire span to our current span
- This eliminates redundant counting

### Step 4: Algorithm Design
1. Use stack to store `[price, span]` pairs
2. For each new price:
   - Start with span = 1 (current day)
   - While stack not empty and current price >= stack.top() price:
     - Pop the block and add its span to current span
   - Push `[current price, total span]` to stack
3. Return the calculated span

### Step 5: Implementation

```java
class StockSpanner {
    private Stack<int[]> stack;  // [price, span] pairs
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;  // Current day always counts
        
        // Pop blocks while current price >= stack price
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];  // Add span of consumed block
        }
        
        // Push current price with its total span
        stack.push(new int[]{price, span});
        return span;
    }
}
```

## Detailed Trace Example

**Input Sequence**: `[100, 80, 60, 70, 60, 75, 85]`

### Step-by-Step Execution

**Day 0: price = 100**
- Stack is empty, no pops
- span = 1
- Push [100, 1]
- Stack: `[[100,1]]`
- **Return: 1**

**Day 1: price = 80**
- 80 < 100, no pops  
- span = 1
- Push [80, 1]
- Stack: `[[100,1], [80,1]]`
- **Return: 1**

**Day 2: price = 60**
- 60 < 80, no pops
- span = 1
- Push [60, 1]
- Stack: `[[100,1], [80,1], [60,1]]`
- **Return: 1**

**Day 3: price = 70**
- 70 >= 60, pop [60,1], span = 1 + 1 = 2
- 70 < 80, no more pops
- Push [70, 2]
- Stack: `[[100,1], [80,1], [70,2]]`
- **Return: 2**

**Day 4: price = 60**
- 60 < 70, no pops
- span = 1
- Push [60, 1]
- Stack: `[[100,1], [80,1], [70,2], [60,1]]`
- **Return: 1**

**Day 5: price = 75**
- 75 >= 60, pop [60,1], span = 1 + 1 = 2
- 75 >= 70, pop [70,2], span = 2 + 2 = 4
- 75 < 80, no more pops
- Push [75, 4]
- Stack: `[[100,1], [80,1], [75,4]]`
- **Return: 4**

**Day 6: price = 85**
- 85 >= 75, pop [75,4], span = 1 + 4 = 5
- 85 >= 80, pop [80,1], span = 5 + 1 = 6
- 85 < 100, no more pops
- Push [85, 6]
- Stack: `[[100,1], [85,6]]`
- **Return: 6**

### Final Result: `[1, 1, 1, 2, 1, 4, 6]`

## Visual Representation

```
Prices:  [100, 80, 60, 70, 60, 75, 85]
Days:     0    1   2   3   4   5   6
Spans:   [1,   1,  1,  2,  1,  4,  6]

Day 3 (price=70): spans 2 days (60,70) 
Day 5 (price=75): spans 4 days (60,70,60,75)
Day 6 (price=85): spans 6 days (80,60,70,60,75,85)
```

## Complexity Analysis

### Time Complexity: O(n) total
- **Amortized O(1) per operation**
- Each price is pushed exactly once and popped at most once
- Total operations across all n calls: 2n (n pushes + at most n pops)

### Space Complexity: O(n)
- Stack can contain up to n elements in worst case (strictly decreasing sequence)

## Comparison with Your Solution

| Aspect | Your Solution | Optimal Solution |
|--------|---------------|------------------|
| **Time per call** | O(n) worst case | O(1) amortized |
| **Total time** | O(n²) | O(n) |
| **Space** | O(n) | O(n) |
| **Simplicity** | Very simple | Requires pattern knowledge |
| **Performance** | 5th percentile | 95th+ percentile |

## When Each Approach is Appropriate

### Your Approach (Linear Scan)
✅ **Good for:**
- Very small inputs (< 100 operations)
- Learning and understanding the problem
- Quick prototyping
- When simplicity is more important than performance

### Optimal Approach (Monotonic Stack)
✅ **Good for:**
- Production code
- Large inputs (1000+ operations)
- Interview scenarios
- When performance matters

## Key Takeaways

1. **Pattern Recognition**: This is a "Previous Smaller Element with Counting" problem
2. **Block Thinking**: Instead of counting individual elements, think in terms of consuming blocks
3. **Amortized Analysis**: Each element is processed exactly once across all operations
4. **Stack Choice**: Decreasing stack with `[value, aggregated_info]` pairs
5. **Template Application**: This pattern applies to many span/counting problems

## Common Mistakes

### Mistake 1: Wrong Stack Content
❌ **Wrong**: Storing just prices `Stack<Integer>`
✅ **Correct**: Storing `[price, span]` pairs `Stack<int[]>`

### Mistake 2: Wrong Pop Condition  
❌ **Wrong**: `stack.peek()[0] < price` (only strictly smaller)
✅ **Correct**: `stack.peek()[0] <= price` (smaller or equal)

### Mistake 3: Forgetting to Aggregate
❌ **Wrong**: `span = 1` (only counting current day)
✅ **Correct**: `span += stack.pop()[1]` (add span of consumed block)

### Mistake 4: Not Understanding Amortized Complexity
❌ **Wrong**: Thinking each operation is O(stack_size)
✅ **Correct**: Understanding that each element is processed exactly once total

## Practice Progression

1. **Understand your current solution** - trace through examples by hand
2. **Learn the optimal pattern** - understand why monotonic stack works
3. **Practice similar problems**:
   - [739. Daily Temperatures](../DailyTemperatures/) - Next greater element
   - [496. Next Greater Element I](../NextGreaterElementI/) - Basic next greater
   - [84. Largest Rectangle in Histogram](../LargestRectangle/) - Advanced application

## Interview Strategy

1. **Start with your current approach** - shows problem understanding
2. **Identify the inefficiency** - "This is O(n²) in worst case"
3. **Optimize with monotonic stack** - "We can use a stack to avoid redundant work"
4. **Explain the key insight** - "Instead of recounting, we aggregate spans"
5. **Trace through an example** - demonstrate correctness

Remember: Your solution shows excellent problem-solving intuition! The optimization to monotonic stack is a pattern you can apply to many similar problems once you learn it.

---

## Related Problems
- [Daily Temperatures](../DailyTemperatures/) - Next greater element with distance
- [Next Greater Element I](../NextGreaterElementI/) - Basic next greater element
- [84. Largest Rectangle in Histogram](../LargestRectangle/) - Advanced monotonic stack