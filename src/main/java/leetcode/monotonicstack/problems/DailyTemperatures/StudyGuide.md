# Daily Temperatures (LeetCode 739) - Study Guide

## Problem Statement

Given an array of integers `temperatures` representing the daily temperatures, return an array `answer` such that `answer[i]` is the number of days you have to wait after the `ith` day to get a warmer temperature. If there is no future day for which this is possible, keep `answer[i] == 0`.

**Example 1:**
```
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

**Example 2:**
```
Input: temperatures = [30,40,50,60,90]
Output: [1,1,1,1,0]
```

**Example 3:**
```
Input: temperatures = [30,60,90]
Output: [1,1,0]
```

## Problem Analysis

### Pattern Recognition
- **Keywords**: "warmer temperature", "future day", "wait after"
- **Pattern**: Next Greater Element with **distance calculation**
- **Relationship**: For each element, find the next element that is greater
- **Output**: Distance to that next greater element

### Key Insights
1. **Next Greater Element**: We need to find the next temperature that is higher
2. **Distance Calculation**: We need the number of days (positions) between current and next warmer day
3. **Efficient Search**: Can't use brute force O(n²) - need O(n) solution
4. **Monotonic Stack**: Perfect use case for decreasing stack

### Why Monotonic Stack Works Here
- **Decreasing Stack**: We maintain temperatures in decreasing order
- **Pop Condition**: When we find a warmer temperature, all cooler temperatures in stack have found their answer
- **Elimination**: Cooler temperatures will never be the answer for future days
- **Efficiency**: Each temperature is pushed and popped at most once

## Step-by-Step Solution

### Step 1: Identify the Pattern
This is a **Next Greater Element** problem where we need **distances** instead of values.

### Step 2: Choose Stack Type
- **Decreasing Stack**: Bottom to top temperatures decrease
- **Pop When**: `current temperature > stack.top() temperature`
- **Store**: Indices (need position for distance calculation)

### Step 3: Algorithm Design
1. Initialize result array with zeros (default for "no warmer day")
2. Use stack to store indices of temperatures
3. For each day:
    - While stack not empty and current temp > stack.top() temp:
        - Pop index from stack (this day found its warmer day)
        - Calculate distance: `current_index - popped_index`
        - Store result
    - Push current index to stack
4. Return result array

### Step 4: Implementation

```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];  // Defaults to 0
    Stack<Integer> stack = new Stack<>();  // Store indices
    
    for (int i = 0; i < n; i++) {
        // Pop while current temperature is greater than stack top
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int index = stack.pop();
            result[index] = i - index;  // Distance to warmer day
        }
        stack.push(i);  // Push current index
    }
    
    return result;
}
```

## Detailed Trace Example

**Input**: `[73, 74, 75, 71, 69, 72, 76, 73]`

### Initial State
- `result = [0, 0, 0, 0, 0, 0, 0, 0]`
- `stack = []`

### Step-by-Step Execution

**i=0, temp=73:**
- Stack is empty, no pops
- Push 0 to stack
- `stack = [0]`, `result = [0, 0, 0, 0, 0, 0, 0, 0]`

**i=1, temp=74:**
- 74 > 73 (temp at index 0), so pop 0
- `result[0] = 1 - 0 = 1`
- Push 1 to stack
- `stack = [1]`, `result = [1, 0, 0, 0, 0, 0, 0, 0]`

**i=2, temp=75:**
- 75 > 74 (temp at index 1), so pop 1
- `result[1] = 2 - 1 = 1`
- Push 2 to stack
- `stack = [2]`, `result = [1, 1, 0, 0, 0, 0, 0, 0]`

**i=3, temp=71:**
- 71 < 75 (temp at index 2), no pops
- Push 3 to stack
- `stack = [2, 3]`, `result = [1, 1, 0, 0, 0, 0, 0, 0]`

**i=4, temp=69:**
- 69 < 71 (temp at index 3), no pops
- Push 4 to stack
- `stack = [2, 3, 4]`, `result = [1, 1, 0, 0, 0, 0, 0, 0]`

**i=5, temp=72:**
- 72 > 69 (temp at index 4), so pop 4
- `result[4] = 5 - 4 = 1`
- 72 > 71 (temp at index 3), so pop 3
- `result[3] = 5 - 3 = 2`
- 72 < 75 (temp at index 2), no more pops
- Push 5 to stack
- `stack = [2, 5]`, `result = [1, 1, 0, 2, 1, 0, 0, 0]`

**i=6, temp=76:**
- 76 > 72 (temp at index 5), so pop 5
- `result[5] = 6 - 5 = 1`
- 76 > 75 (temp at index 2), so pop 2
- `result[2] = 6 - 2 = 4`
- Stack is empty, push 6
- `stack = [6]`, `result = [1, 1, 4, 2, 1, 1, 0, 0]`

**i=7, temp=73:**
- 73 < 76 (temp at index 6), no pops
- Push 7 to stack
- `stack = [6, 7]`, `result = [1, 1, 4, 2, 1, 1, 0, 0]`

### Final Result
- Remaining elements in stack (indices 6, 7) have no warmer days
- Their result values remain 0
- **Final Answer**: `[1, 1, 4, 2, 1, 1, 0, 0]`

## Visual Representation

```
Temperatures: [73, 74, 75, 71, 69, 72, 76, 73]
Indices:       0   1   2   3   4   5   6   7

Day 0 (73): Next warmer is day 1 (74) → distance = 1
Day 1 (74): Next warmer is day 2 (75) → distance = 1  
Day 2 (75): Next warmer is day 6 (76) → distance = 4
Day 3 (71): Next warmer is day 5 (72) → distance = 2
Day 4 (69): Next warmer is day 5 (72) → distance = 1
Day 5 (72): Next warmer is day 6 (76) → distance = 1
Day 6 (76): No warmer day → distance = 0
Day 7 (73): No warmer day → distance = 0
```

## Alternative Approaches

### Brute Force (O(n²))
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (temperatures[j] > temperatures[i]) {
                result[i] = j - i;
                break;
            }
        }
    }
    
    return result;
}
```

**Problems with Brute Force:**
- Time: O(n²) - nested loops
- Space: O(1) - only result array
- Inefficient for large inputs

### Backward Iteration (O(n))
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    
    for (int i = n - 2; i >= 0; i--) {
        int j = i + 1;
        while (j < n && temperatures[j] <= temperatures[i]) {
            if (result[j] == 0) {
                j = n;  // No warmer day found
            } else {
                j += result[j];  // Jump to next potential warmer day
            }
        }
        if (j < n) {
            result[i] = j - i;
        }
    }
    
    return result;
}
```

**Analysis:**
- Time: O(n) - amortized
- Space: O(1) - only result array
- Clever but less intuitive than stack approach

## Common Mistakes

### Mistake 1: Wrong Stack Type
❌ **Wrong**: Using increasing stack
```java
// This would find next smaller elements, not greater!
while (!stack.isEmpty() && temperatures[i] < temperatures[stack.peek()]) {
```

✅ **Correct**: Using decreasing stack for next greater
```java
while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
```

### Mistake 2: Storing Values Instead of Indices
❌ **Wrong**: Storing temperature values
```java
Stack<Integer> stack = new Stack<>();
stack.push(temperatures[i]);  // Can't calculate distance!
```

✅ **Correct**: Storing indices
```java
Stack<Integer> stack = new Stack<>();
stack.push(i);  // Can calculate distance as i - popped_index
```

### Mistake 3: Wrong Distance Calculation
❌ **Wrong**: Distance calculation
```java
result[index] = index - i;  // Negative distance!
```

✅ **Correct**: Distance calculation
```java
result[index] = i - index;  // Current position - previous position
```

### Mistake 4: Forgetting Stack Empty Check
❌ **Wrong**: Accessing stack without checking
```java
while (temperatures[i] > temperatures[stack.peek()]) {  // Can throw exception!
```

✅ **Correct**: Always check stack is not empty
```java
while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
```

## Edge Cases and Testing

### Edge Case 1: Empty Array
```java
Input: []
Output: []
```

### Edge Case 2: Single Element
```java
Input: [30]
Output: [0]
```

### Edge Case 3: All Elements Equal
```java
Input: [70, 70, 70, 70]
Output: [0, 0, 0, 0]
```

### Edge Case 4: Strictly Increasing
```java
Input: [30, 40, 50, 60, 70]
Output: [1, 1, 1, 1, 0]
```

### Edge Case 5: Strictly Decreasing
```java
Input: [70, 60, 50, 40, 30]
Output: [0, 0, 0, 0, 0]
```

## Performance Analysis

### Time Complexity: O(n)
- **Reason**: Each element is pushed and popped at most once
- **Amortized**: Even though we have nested loops, the inner while loop's total iterations across all i is bounded by n

### Space Complexity: O(n)
- **Result Array**: O(n) space
- **Stack**: O(n) space in worst case (strictly decreasing sequence)
- **Total**: O(n) space

### Comparison with Brute Force
| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n²) | O(1) | Simple, intuitive | Too slow for large inputs |
| Monotonic Stack | O(n) | O(n) | Efficient, scalable | Requires understanding of pattern |

## Variations and Extensions

### 1. Next Smaller Element
```java
// Change pop condition
while (!stack.isEmpty() && temperatures[i] < temperatures[stack.peek()]) {
```

### 2. Previous Greater Element
```java
// Process before pushing
while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
    stack.pop();
}
if (!stack.isEmpty()) {
    result[i] = stack.peek();  // Previous greater index
}
stack.push(i);
```

### 3. Circular Array Version
```java
// Process array twice
for (int i = 0; i < 2 * n; i++) {
    int index = i % n;
    // Same logic but only push indices from first pass
}
```

## Tips for Success

### 1. Pattern Recognition
- Look for "next greater/smaller" keywords
- Recognize need for distance vs value
- Identify monotonic stack opportunity

### 2. Template Mastery
- Memorize the basic template
- Practice with different variations
- Understand when to use each pattern

### 3. Debugging Strategy
- Trace through small examples by hand
- Print stack contents at each step
- Verify monotonic property is maintained

### 4. Common Pitfalls to Avoid
- Wrong stack type selection
- Storing values instead of indices
- Incorrect distance calculation
- Missing empty stack checks

## Practice Problems

### Similar Problems
1. **496. Next Greater Element I** - Basic next greater element
2. **503. Next Greater Element II** - Circular array version
3. **901. Online Stock Span** - Previous smaller element pattern
4. **1019. Next Greater Node In Linked List** - Adapt to linked list

### Progression Order
1. Start with **Daily Temperatures** (this problem)
2. Practice **Next Greater Element I** for value-based results
3. Try **Next Greater Element II** for circular array handling
4. Move to **Online Stock Span** for previous element pattern

## Key Takeaways

1. **Monotonic Stack Pattern**: Perfect for next/previous greater/smaller problems
2. **Decreasing Stack**: Use for next greater element problems
3. **Store Indices**: When you need position information for distance calculation
4. **O(n) Efficiency**: Each element processed exactly once
5. **Template Recognition**: Once you see the pattern, solution follows predictable structure

Remember: The key to mastering this problem is understanding **why** the monotonic stack works and **when** to apply this pattern. Practice recognizing these patterns in different contexts!