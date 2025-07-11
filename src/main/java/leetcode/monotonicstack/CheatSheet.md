# Monotonic Stack - Cheat Sheet

## Quick Problem Recognition

| Problem Type | Keywords | Stack Type | What to Store |
|-------------|----------|------------|---------------|
| Next Greater Element | "next greater", "warmer", "higher" | Decreasing | Indices |
| Next Smaller Element | "next smaller", "shorter", "lower" | Increasing | Indices |
| Previous Greater | "previous greater", "left boundary" | Increasing | Indices |
| Previous Smaller | "previous smaller", "span", "consecutive" | Decreasing | Indices |
| Histogram/Rectangle | "largest rectangle", "maximum area" | Increasing | Indices |
| Subarray Optimization | "subarray minimum/maximum" | Both | Indices |

## Core Templates

### 1. Next Greater Element (NGE) Template
```java
public int[] nextGreaterElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);  // Default: no greater element found
    Stack<Integer> stack = new Stack<>();  // Decreasing stack
    
    for (int i = 0; i < n; i++) {
        // Pop elements while current is greater
        while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    
    return result;
}
```

### 2. Next Smaller Element (NSE) Template
```java
public int[] nextSmallerElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);  // Default: no smaller element found
    Stack<Integer> stack = new Stack<>();  // Increasing stack
    
    for (int i = 0; i < n; i++) {
        // Pop elements while current is smaller
        while (!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }
    
    return result;
}
```

### 3. Previous Greater Element (PGE) Template
```java
public int[] previousGreaterElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);  // Default: no greater element found
    Stack<Integer> stack = new Stack<>();  // Increasing stack
    
    for (int i = 0; i < n; i++) {
        // Pop elements while they're <= current
        while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
            stack.pop();
        }
        // If stack not empty, top is the previous greater
        if (!stack.isEmpty()) {
            result[i] = nums[stack.peek()];
        }
        stack.push(i);
    }
    
    return result;
}
```

### 4. Previous Smaller Element (PSE) Template
```java
public int[] previousSmallerElement(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);  // Default: no smaller element found
    Stack<Integer> stack = new Stack<>();  // Decreasing stack
    
    for (int i = 0; i < n; i++) {
        // Pop elements while they're >= current
        while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
            stack.pop();
        }
        // If stack not empty, top is the previous smaller
        if (!stack.isEmpty()) {
            result[i] = nums[stack.peek()];
        }
        stack.push(i);
    }
    
    return result;
}
```

## Problem-Specific Templates

### Daily Temperatures (Distance to Next Greater)
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];  // Default 0 (no warmer day)
    Stack<Integer> stack = new Stack<>();
    
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int index = stack.pop();
            result[index] = i - index;  // Distance
        }
        stack.push(i);
    }
    
    return result;
}
```

### Online Stock Span (Count Previous Smaller/Equal)
```java
class StockSpanner {
    private Stack<int[]> stack;  // [price, span]
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        // Pop elements while current price >= stack price
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];  // Add span of popped element
        }
        
        stack.push(new int[]{price, span});
        return span;
    }
}
```

### Largest Rectangle in Histogram
```java
public int largestRectangleArea(int[] heights) {
    int maxArea = 0;
    Stack<Integer> stack = new Stack<>();  // Increasing stack
    
    for (int i = 0; i <= heights.length; i++) {
        int h = (i == heights.length) ? 0 : heights[i];
        
        while (!stack.isEmpty() && h < heights[stack.peek()]) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        stack.push(i);
    }
    
    return maxArea;
}
```

### Circular Array (Next Greater Element II)
```java
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Stack<Integer> stack = new Stack<>();
    
    // Process array twice to handle circular nature
    for (int i = 0; i < 2 * n; i++) {
        int index = i % n;
        
        while (!stack.isEmpty() && nums[index] > nums[stack.peek()]) {
            result[stack.pop()] = nums[index];
        }
        
        if (i < n) {  // Only push indices from first pass
            stack.push(index);
        }
    }
    
    return result;
}
```

## Quick Decision Tree

```
What are you looking for?
├─ Next Greater Element
│   ├─ Distance/Index? → Store indices, return i - index
│   └─ Value? → Store indices, return nums[i]
├─ Next Smaller Element
│   ├─ Distance/Index? → Store indices, return i - index
│   └─ Value? → Store indices, return nums[i]
├─ Previous Greater/Smaller
│   ├─ Just existence? → Check if stack is empty after popping
│   └─ Value/Index? → Return stack.peek() after popping
└─ Count/Span Problems
    ├─ Stock Span → Count consecutive smaller/equal
    └─ Subarray Count → Use contribution technique
```

## Common Patterns by Pop Condition

| Pop Condition | Stack Type | Finds | Example |
|---------------|------------|-------|---------|
| `current > stack.top()` | Decreasing | Next Greater | Daily Temperatures |
| `current < stack.top()` | Increasing | Next Smaller | - |
| `current >= stack.top()` | Decreasing | Next Greater/Equal | Stock Span |
| `current <= stack.top()` | Increasing | Next Smaller/Equal | - |

## Stack Contents Quick Reference

### What to Store in Stack?
- **Indices**: When you need position information (most common)
- **Values**: When you only need the actual values
- **Pairs [value, metadata]**: When you need both value and additional info

### Stack Direction
- **Increasing**: Bottom to top values increase
- **Decreasing**: Bottom to top values decrease

## Space-Time Complexity

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Push/Pop | O(1) | - | Per operation |
| Overall | O(n) | O(n) | Each element pushed/popped once |
| Result Array | - | O(n) | Usually required |

## Common Edge Cases

### Input Validation
```java
if (nums == null || nums.length == 0) {
    return new int[0];
}
```

### Stack Empty Check
```java
// Always check before accessing stack.peek()
if (!stack.isEmpty()) {
    // Safe to use stack.peek()
}
```

### Default Values
```java
int[] result = new int[n];
Arrays.fill(result, -1);  // For "not found" cases
// OR
// result array defaults to 0 for distance problems
```

## Problem-Specific Optimizations

### Histogram Problems
```java
// Add sentinel value to process all elements
for (int i = 0; i <= heights.length; i++) {
    int h = (i == heights.length) ? 0 : heights[i];
    // Process...
}
```

### Circular Arrays
```java
// Process array twice
for (int i = 0; i < 2 * n; i++) {
    int index = i % n;
    // Process...
}
```

### Custom Comparisons
```java
// For complex objects
while (!stack.isEmpty() && customCompare(current, stack.peek())) {
    // Process...
}
```

## Debugging Checklist

### 1. Stack Type ✓
- [ ] Using decreasing stack for next greater?
- [ ] Using increasing stack for next smaller?

### 2. Pop Condition ✓
- [ ] Correct comparison operator (>, <, >=, <=)?
- [ ] Matches the problem requirement?

### 3. Storage ✓
- [ ] Storing indices when position needed?
- [ ] Storing values when only values needed?

### 4. Result Processing ✓
- [ ] Correct calculation (distance, value, count)?
- [ ] Handling remaining elements in stack?

### 5. Edge Cases ✓
- [ ] Empty array handled?
- [ ] Single element handled?
- [ ] All elements equal handled?

## Memory Aid

**"GPS" Method for Monotonic Stack:**
- **G**reater → **Decreasing** stack
- **P**revious → Process **before** pushing
- **S**maller → **Increasing** stack

**Direction Memory:**
- **Next** → Process while popping
- **Previous** → Process before pushing

## Quick Templates by Problem Type

### Type 1: Find Next/Previous Element
```java
// Template structure
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && condition) {
        // Process popped element
    }
    // Process current element if needed
    stack.push(i);
}
```

### Type 2: Count/Span Problems
```java
// Template structure
int count = 1;  // Start with current element
while (!stack.isEmpty() && condition) {
    count += stack.pop()[1];  // Add span of popped element
}
stack.push(new int[]{value, count});
```

### Type 3: Histogram/Rectangle Problems
```java
// Template structure
while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
    int height = heights[stack.pop()];
    int width = stack.isEmpty() ? i : i - stack.peek() - 1;
    maxArea = Math.max(maxArea, height * width);
}
```

## Problem Mapping

| LeetCode # | Problem | Template | Key Insight |
|------------|---------|----------|-------------|
| 739 | Daily Temperatures | NGE Distance | Return i - index |
| 496 | Next Greater Element I | NGE Value | Use map for lookup |
| 503 | Next Greater Element II | NGE Circular | Process 2n elements |
| 901 | Online Stock Span | PSE Count | Count consecutive ≤ |
| 84 | Largest Rectangle | Histogram | Width = right - left - 1 |
| 42 | Trapping Rain Water | Bidirectional | Min of left/right max |