# Monotonic Stack Pattern - Flashcards

## Core Concepts

**Q: What is a monotonic stack and when should you use it?**
A: A monotonic stack is a stack where elements maintain either **increasing** or **decreasing** order from bottom to top. Use it when you need to find **next/previous greater/smaller elements** in an array, or when looking for **relationships between elements** that require efficient comparison.

**Q: What are the two types of monotonic stacks?**
A:
- **Increasing Stack**: Elements increase from bottom to top
- **Decreasing Stack**: Elements decrease from bottom to top

**Q: What's the key insight behind why monotonic stacks work?**
A: When an element violates the monotonic property, all elements that get popped have found their "answer" (next greater/smaller element). Elements that can't be part of the optimal solution are efficiently eliminated.

## Problem Recognition

**Q: What keywords indicate a monotonic stack problem?**
A:
- **"Next greater element"**
- **"Next smaller element"**
- **"Previous greater/smaller"**
- **"Daily temperatures"**
- **"Stock span"**
- **"Largest rectangle"**
- **"Trapping rain water"**

**Q: What are the main problem categories for monotonic stack?**
A:
1. **Next Greater/Smaller Element (NGE/NSE)**: Find next element with certain property
2. **Stock Span Pattern**: Count consecutive elements with certain property
3. **Histogram/Rectangle Problems**: Find maximum area rectangles
4. **Subarray Optimization**: Optimize over all subarrays

## Stack Type Selection

**Q: How do you choose between increasing and decreasing stack?**
A:
- **Decreasing Stack**: Use for **next greater** or **previous smaller** problems
- **Increasing Stack**: Use for **next smaller** or **previous greater** problems

**Q: What's the "GPS" memory aid for monotonic stacks?**
A:
- **G**reater → **Decreasing** stack
- **P**revious → Process **before** pushing
- **S**maller → **Increasing** stack

## Pop Conditions

**Q: What pop condition do you use for each problem type?**
A:
- **Next Greater**: `current > stack.top()` (decreasing stack)
- **Next Smaller**: `current < stack.top()` (increasing stack)
- **Next Greater/Equal**: `current >= stack.top()` (decreasing stack)
- **Next Smaller/Equal**: `current <= stack.top()` (increasing stack)

**Q: When do you process elements - while popping or before pushing?**
A:
- **Next problems**: Process while popping (elements have found their answer)
- **Previous problems**: Process before pushing (check what's currently in stack)

## What to Store

**Q: What should you store in the monotonic stack?**
A:
- **Indices**: When you need position information (most common)
- **Values**: When you only need the actual values
- **Pairs [value, metadata]**: When you need both value and additional info

**Q: Why do we usually store indices instead of values?**
A: Because most problems need position information to calculate:
- **Distance**: `i - index` (Daily Temperatures)
- **Width**: `right - left - 1` (Histogram problems)
- **Span**: Count of elements in range

## Template Recognition

**Q: What's the basic template for Next Greater Element?**
A:
```java
Stack<Integer> stack = new Stack<>();  // Decreasing stack
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
        int index = stack.pop();
        result[index] = nums[i];  // Found next greater
    }
    stack.push(i);
}
```

**Q: What's the basic template for Previous Greater Element?**
A:
```java
Stack<Integer> stack = new Stack<>();  // Increasing stack
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
        stack.pop();
    }
    if (!stack.isEmpty()) {
        result[i] = nums[stack.peek()];  // Found previous greater
    }
    stack.push(i);
}
```

## Common Patterns

**Q: How do you handle Daily Temperatures (distance to next greater)?**
A: Use decreasing stack, store indices, and when popping calculate distance:
```java
while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
    int index = stack.pop();
    result[index] = i - index;  // Distance
}
```

**Q: How do you handle Stock Span (count consecutive smaller/equal)?**
A: Use decreasing stack, store `[price, span]`, and accumulate spans:
```java
int span = 1;
while (!stack.isEmpty() && stack.peek()[0] <= price) {
    span += stack.pop()[1];  // Add span of popped element
}
stack.push(new int[]{price, span});
```

**Q: How do you handle circular arrays (Next Greater Element II)?**
A: Process the array twice using modulo:
```java
for (int i = 0; i < 2 * n; i++) {
    int index = i % n;
    // Process normally but only push indices from first pass
    if (i < n) {
        stack.push(index);
    }
}
```

## Time and Space Complexity

**Q: What's the time and space complexity of monotonic stack algorithms?**
A:
- **Time**: O(n) - Each element is pushed and popped at most once
- **Space**: O(n) - Stack can hold up to n elements in worst case

**Q: Why is the time complexity O(n) and not O(n²)?**
A: Because of **amortized analysis** - each element is pushed exactly once and popped at most once across the entire algorithm, so total operations = 2n = O(n).

## Advanced Techniques

**Q: How do you handle histogram problems (Largest Rectangle)?**
A: Use increasing stack and add sentinel value:
```java
for (int i = 0; i <= heights.length; i++) {
    int h = (i == heights.length) ? 0 : heights[i];
    while (!stack.isEmpty() && h < heights[stack.peek()]) {
        int height = heights[stack.pop()];
        int width = stack.isEmpty() ? i : i - stack.peek() - 1;
        maxArea = Math.max(maxArea, height * width);
    }
    stack.push(i);
}
```

**Q: How do you handle bidirectional problems (Trapping Rain Water)?**
A: Either use two passes (left-to-right, right-to-left) or use a single decreasing stack to find trapped water between bars.

## Common Mistakes

**Q: What's the most common mistake when choosing stack type?**
A: Using **increasing stack for next greater** problems. Remember: **Greater → Decreasing** stack!

**Q: What's the most common mistake in pop conditions?**
A: Forgetting to check `!stack.isEmpty()` before accessing `stack.peek()`. Always guard your stack operations.

**Q: What's the most common mistake in result calculation?**
A: Getting the **distance calculation wrong**. Remember: `result[index] = i - index` (current position - popped position).

## Edge Cases

**Q: What edge cases should you always test?**
A:
- **Empty array**: `nums.length == 0`
- **Single element**: `nums.length == 1`
- **All elements equal**: No elements get popped
- **Strictly increasing**: Stack grows to maximum size
- **Strictly decreasing**: Elements get popped immediately

**Q: How do you handle "not found" cases?**
A: Initialize result array with default values:
```java
int[] result = new int[n];
Arrays.fill(result, -1);  // For "not found" cases
// OR let it default to 0 for distance problems
```

## Problem-Solving Strategy

**Q: What are the key steps to solve any monotonic stack problem?**
A:
1. **Identify the relationship**: Next/previous, greater/smaller
2. **Choose stack type**: Increasing vs decreasing
3. **Decide what to store**: Indices, values, or pairs
4. **Implement pop condition**: When to remove elements
5. **Process correctly**: During pop or before push
6. **Handle edge cases**: Empty input, remaining elements

**Q: How do you debug monotonic stack problems?**
A:
1. **Trace small examples** by hand
2. **Print stack contents** at each step
3. **Verify monotonic property** after each operation
4. **Check pop conditions** match problem requirements
5. **Test edge cases** thoroughly

## Memory Techniques

**Q: What's a good way to remember which stack type to use?**
A: **"GPS" method**:
- **G**reater → **D**ecreasing stack
- **P**revious → Process **B**efore pushing
- **S**maller → **I**ncreasing stack

**Q: How do you remember the difference between "next" and "previous" processing?**
A:
- **Next**: Process while **popping** (elements found their answer)
- **Previous**: Process **before pushing** (check what's already there)

## Practice Progression

**Q: What's the recommended order to practice monotonic stack problems?**
A:
1. **Daily Temperatures** (739) - Basic next greater with distance
2. **Next Greater Element I** (496) - Basic next greater with values
3. **Online Stock Span** (901) - Previous smaller with counting
4. **Next Greater Element II** (503) - Circular array handling
5. **Largest Rectangle in Histogram** (84) - Advanced histogram problem

**Q: What should you focus on when starting with monotonic stack?**
A:
1. **Understanding the pattern**: Why monotonic stack works
2. **Template recognition**: Identify which template to use
3. **Pop condition mastery**: Getting the comparison right
4. **Edge case handling**: Empty stack, remaining elements
5. **Result calculation**: Distance vs value vs count

## Key Insights

**Q: What's the most important insight about monotonic stacks?**
A: They allow you to **efficiently eliminate impossible solutions**. Elements that can't be part of the optimal answer are removed, and only relevant elements remain for future comparisons.

**Q: Why is monotonic stack better than brute force for these problems?**
A: **Brute force** is O(n²) because you check all pairs. **Monotonic stack** is O(n) because each element is processed exactly once, and impossible candidates are eliminated early.

**Q: What's the key to mastering monotonic stack problems?**
A: **Pattern recognition** - once you can identify that a problem needs next/previous greater/smaller elements, the solution follows a predictable template. Practice recognizing these patterns in different problem contexts.