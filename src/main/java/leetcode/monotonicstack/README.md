# Monotonic Stack Pattern

## What is a Monotonic Stack?

A **monotonic stack** is a stack data structure where elements are maintained in either **strictly increasing** or **strictly decreasing** order. It's a powerful technique for solving problems that require finding the **next greater element**, **next smaller element**, or **previous greater/smaller element** in an array.

**Key Insight**: The monotonic property allows us to efficiently find relationships between elements that wouldn't be possible with a regular stack.

## Core Principles

### 1. **Monotonic Property**
- **Increasing Stack**: Elements from bottom to top are in increasing order
- **Decreasing Stack**: Elements from bottom to top are in decreasing order
- When adding an element violates the property, we pop elements until it's restored

### 2. **When to Pop Elements**
- **For increasing stack**: Pop when `current > stack.top()`
- **For decreasing stack**: Pop when `current < stack.top()`
- The popped elements have found their "next greater/smaller" element

### 3. **What to Store**
- **Indices**: When you need position information (most common)
- **Values**: When you only need the actual values
- **Pairs**: When you need both index and value

## When to Use Monotonic Stack

### Problem Recognition Keywords
- "Next greater element"
- "Next smaller element"
- "Previous greater/smaller element"
- "Largest rectangle"
- "Maximum area"
- "Daily temperatures"
- "Stock span"
- "Trapping rain water"

### Problem Characteristics
1. **Array/sequence processing**: Working with arrays or sequences
2. **Comparative relationships**: Need to find relationships between elements
3. **Optimization over subarrays**: Finding optimal subarrays with certain properties
4. **Time-based problems**: Problems involving time series or sequences

## Problem Categories

### 1. **Next Greater/Smaller Element (NGE/NSE)**
- **739. Daily Temperatures** - Find next warmer day
- **496. Next Greater Element I** - Basic NGE problem
- **503. Next Greater Element II** - Circular array variation
- **556. Next Greater Element III** - Number manipulation

### 2. **Stock Span Pattern**
- **901. Online Stock Span** - Count consecutive smaller/equal prices
- **1063. Number of Valid Subarrays** - Count valid subarrays ending at each position

### 3. **Histogram/Rectangle Problems**
- **84. Largest Rectangle in Histogram** - Maximum area rectangle
- **85. Maximal Rectangle** - 2D histogram extension
- **42. Trapping Rain Water** - Water trapped between bars

### 4. **Subarray Optimization**
- **907. Sum of Subarray Minimums** - Sum of minimums across all subarrays
- **1019. Next Greater Node In Linked List** - NGE in linked list

## Common Time/Space Complexities

- **Time**: O(n) - Each element is pushed and popped at most once
- **Space**: O(n) - In worst case, all elements are in the stack

## Core Insights

### Why Monotonic Stack Works
1. **Elimination of Impossible Solutions**: Elements that can't be the answer are removed
2. **Efficient Comparisons**: We only compare with relevant elements
3. **Amortized Analysis**: Each element is processed exactly once

### The "Aha!" Moment
When processing element `arr[i]`:
- All elements in the stack smaller than `arr[i]` have found their "next greater element"
- These elements will never be the answer for future elements
- We can safely remove them and process them now

## General Approach to Solve Monotonic Stack Problems

### Step 1: Identify the Pattern
- What relationship are we looking for? (next greater, previous smaller, etc.)
- What direction should we process the array? (left to right, right to left)

### Step 2: Choose Stack Type
- **Increasing Stack**: For finding next smaller or previous greater
- **Decreasing Stack**: For finding next greater or previous smaller

### Step 3: Define What to Store
- **Indices**: When you need position information
- **Values**: When you only need the values
- **Custom Objects**: When you need multiple pieces of information

### Step 4: Implement the Algorithm
1. Initialize result array and stack
2. Iterate through the array
3. Pop elements while monotonic property is violated
4. Process popped elements (they've found their answer)
5. Push current element to stack
6. Handle remaining elements in stack

## Detailed Examples

### Example 1: Daily Temperatures (Next Greater Element)

**Problem**: Given daily temperatures, find how many days until a warmer temperature.

**Input**: `[73, 74, 75, 71, 69, 72, 76, 73]`
**Output**: `[1, 1, 4, 2, 1, 1, 0, 0]`

#### Step-by-Step Analysis:
1. **Pattern**: Find next greater element for each position
2. **Stack Type**: Decreasing stack (pop when current > stack.top())
3. **Store**: Indices (need position information for calculating distance)
4. **Process**: Left to right

#### Trace Example:
```
i=0, temp=73: stack=[] → push 0 → stack=[0]
i=1, temp=74: 74>73, pop 0, result[0]=1-0=1 → push 1 → stack=[1]
i=2, temp=75: 75>74, pop 1, result[1]=2-1=1 → push 2 → stack=[2]
i=3, temp=71: 71<75, no pop → push 3 → stack=[2,3]
i=4, temp=69: 69<71, no pop → push 4 → stack=[2,3,4]
i=5, temp=72: 72>69, pop 4, result[4]=5-4=1
              72>71, pop 3, result[3]=5-3=2
              72<75, no more pops → push 5 → stack=[2,5]
i=6, temp=76: 76>72, pop 5, result[5]=6-5=1
              76>75, pop 2, result[2]=6-2=4
              stack=[] → push 6 → stack=[6]
i=7, temp=73: 73<76, no pop → push 7 → stack=[6,7]
End: remaining elements result[6]=result[7]=0
```

#### Implementation:
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>();  // Store indices
    
    for (int i = 0; i < n; i++) {
        // Pop elements while current temperature is greater
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int index = stack.pop();
            result[index] = i - index;  // Distance to next warmer day
        }
        stack.push(i);
    }
    
    return result;  // Remaining elements already have default value 0
}
```

### Example 2: Online Stock Span (Previous Smaller/Equal)

**Problem**: Find the span of stock prices (how many consecutive days the price was ≤ current price).

**Input**: `[100, 80, 60, 70, 60, 75, 85]`
**Output**: `[1, 1, 1, 2, 1, 4, 6]`

#### Step-by-Step Analysis:
1. **Pattern**: Find previous smaller element (or count elements ≥ current)
2. **Stack Type**: Decreasing stack (pop when current ≥ stack.top())
3. **Store**: Indices (need position information)
4. **Process**: Left to right, maintain span information

#### Implementation:
```java
class StockSpanner {
    private Stack<int[]> stack;  // [price, span]
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        // Pop elements while current price is greater or equal
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];  // Add the span of popped element
        }
        
        stack.push(new int[]{price, span});
        return span;
    }
}
```

## Common Patterns and Templates

### Pattern 1: Next Greater Element (NGE)
```java
public int[] nextGreaterElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);  // Default: no greater element
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    
    return result;
}
```

### Pattern 2: Next Smaller Element (NSE)
```java
public int[] nextSmallerElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    
    return result;
}
```

### Pattern 3: Previous Greater Element (PGE)
```java
public int[] previousGreaterElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
            stack.pop();
        }
        if (!stack.isEmpty()) {
            result[i] = nums[stack.peek()];
        }
        stack.push(i);
    }
    
    return result;
}
```

## Common Mistakes and How to Fix Them

### Mistake 1: Wrong Stack Type
❌ **Problem**: Using increasing stack for next greater element
✅ **Solution**: Use decreasing stack (pop when current > stack.top())

### Mistake 2: Storing Wrong Information
❌ **Problem**: Storing values when you need indices
✅ **Solution**: Store indices when you need position information

### Mistake 3: Wrong Pop Condition
❌ **Problem**: Using `>=` when you need `>`
✅ **Solution**: Carefully consider whether equal elements should be popped

### Mistake 4: Forgetting Edge Cases
❌ **Problem**: Not handling empty stack or remaining elements
✅ **Solution**: Always check `!stack.isEmpty()` before accessing stack.peek()

## Advanced Techniques

### 1. **Circular Array Handling**
For problems like "Next Greater Element II":
```java
for (int i = 0; i < 2 * n; i++) {
    int index = i % n;
    // Process as normal, but use index instead of i
}
```

### 2. **Bidirectional Processing**
Some problems require processing from both directions:
```java
// First pass: left to right
// Second pass: right to left
// Combine results
```

### 3. **Custom Comparisons**
For complex objects, define custom comparison logic:
```java
while (!stack.isEmpty() && customCompare(current, stack.peek())) {
    // Process
}
```

## Practice Progression

### Beginner Level (Start Here)
1. **496. Next Greater Element I** - Basic NGE
2. **739. Daily Temperatures** - NGE with distance
3. **1118. Number of Days in a Month** - Practice with indices

### Intermediate Level
4. **901. Online Stock Span** - Previous smaller pattern
5. **503. Next Greater Element II** - Circular array
6. **1019. Next Greater Node In Linked List** - Adapt to linked list

### Advanced Level
7. **84. Largest Rectangle in Histogram** - Complex histogram problem
8. **907. Sum of Subarray Minimums** - Subarray optimization
9. **42. Trapping Rain Water** - Bidirectional processing

## Key Takeaways

1. **Recognize the Pattern**: Look for "next/previous greater/smaller" relationships
2. **Choose Correct Stack Type**: Increasing vs decreasing based on what you're looking for
3. **Store Appropriate Information**: Indices when you need positions, values when you don't
4. **Understand the Pop Condition**: This determines what relationship you're finding
5. **Handle Edge Cases**: Empty stack, remaining elements, circular arrays

## Debugging Tips

### 1. Trace Small Examples by Hand
```java
// Add debug prints
System.out.println("Processing i=" + i + ", value=" + nums[i]);
System.out.println("Stack before: " + stack);
System.out.println("Stack after: " + stack);
```

### 2. Verify Stack Property
After each operation, verify the stack maintains its monotonic property.

### 3. Check Pop Conditions
Make sure you're popping the right elements based on the problem requirements.

### 4. Test Edge Cases
- Empty array
- Single element
- All elements equal
- Strictly increasing/decreasing sequence

Remember: The goal is to understand the underlying pattern of using a stack to efficiently find element relationships. Once you master this thinking, you can adapt it to solve various problems involving comparative relationships in arrays!

---

## Directory Structure

```
monotonicstack/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── problems/
│   ├── DailyTemperatures/
│   │   ├── README.md
│   │   ├── Solution.java
│   │   └── StudyGuide.md
│   └── OnlineStockSpan/
│       ├── README.md
│       ├── Solution.java
│       └── StudyGuide.md
└── common/
    └── MonotonicStackTemplates.java
```

Start with the Daily Temperatures problem to understand the basic pattern!