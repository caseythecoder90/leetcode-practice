# Daily Temperatures (LeetCode 739)

**Pattern**: Monotonic Stack | **Difficulty**: Medium | **Type**: Next Greater Element

## Problem Statement

Given an array of integers `temperatures` representing the daily temperatures, return an array `answer` such that `answer[i]` is the number of days you have to wait after the `ith` day to get a warmer temperature. If there is no future day for which this is possible, keep `answer[i] == 0`.

### Examples

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

## Solution Approaches

This directory contains **4 different solution files** demonstrating multiple approaches to solve this problem:

### 1. 🎯 **MonotonicStackSolution.java** (RECOMMENDED)
- **Algorithm**: Decreasing monotonic stack
- **Time**: O(n) | **Space**: O(n)
- **Best for**: Interview standard, optimal solution
- **Key Learning**: Master the "Next Greater Element" pattern

### 2. 📚 **BruteForceSolution.java** (EDUCATIONAL)
- **Algorithm**: Nested loops, search forward for each element
- **Time**: O(n²) | **Space**: O(1)
- **Best for**: Understanding the problem, very small inputs
- **Key Learning**: Simple approach, shows why optimization is needed

### 3. 🚀 **BackwardIterationSolution.java** (SPACE-OPTIMIZED)
- **Algorithm**: Right-to-left processing with smart jumps
- **Time**: O(n) | **Space**: O(1)
- **Best for**: Memory-constrained environments
- **Key Learning**: Advanced optimization technique

### 4. 📋 **DailyTemperatures.java** (ALL-IN-ONE)
- **Algorithm**: All three approaches in one file
- **Best for**: Comparing implementations side-by-side
- **Key Learning**: Performance analysis and approach comparison

## Files in This Directory

```
DailyTemperatures/
├── README.md                    # This file - problem overview
├── StudyGuide.md               # Detailed walkthrough with traces
├── DailyTemperatures.java      # Main solution with all approaches
├── MonotonicStackSolution.java # Optimal monotonic stack approach
├── BruteForceSolution.java     # Simple brute force approach
└── BackwardIterationSolution.java # Space-optimized approach
```

## Quick Start Guide

### Step 1: Understand the Problem
Read this README and the problem statement. The goal is to find the distance to the next warmer temperature for each day.

### Step 2: Learn the Pattern
Start with **BruteForceSolution.java** to understand the straightforward approach.

### Step 3: Master the Optimal Solution
Study **MonotonicStackSolution.java** to learn the monotonic stack pattern - this is the interview standard.

### Step 4: Advanced Optimization
Explore **BackwardIterationSolution.java** to see space optimization techniques.

### Step 5: Deep Dive
Read **StudyGuide.md** for detailed algorithm traces and pattern explanations.

## Running the Solutions

Each solution file has a complete `main` method with test cases:

```bash
# Compile and run any solution
javac MonotonicStackSolution.java
java MonotonicStackSolution

# Or use Maven
mvn exec:java -Dexec.mainClass="leetcode.monotonicstack.problems.DailyTemperatures.MonotonicStackSolution"
```

## Complexity Comparison

| Approach | Time | Space | Best Use Case |
|----------|------|-------|---------------|
| **Monotonic Stack** | O(n) | O(n) | Interview standard, most intuitive |
| **Brute Force** | O(n²) | O(1) | Small inputs, learning |
| **Backward Iteration** | O(n) | O(1) | Memory constraints |

## Pattern Recognition

This problem is a classic **"Next Greater Element"** problem. Key indicators:
- Need to find the "next" element that satisfies a condition
- Relationship between current and future elements
- Can optimize with monotonic stack

### Similar Problems
- [496. Next Greater Element I](../NextGreaterElementI/)
- [503. Next Greater Element II](../NextGreaterElementII/)
- [901. Online Stock Span](../../OnlineStockSpan/)

## Algorithm Templates

### Monotonic Stack Template
```java
Stack<Integer> stack = new Stack<>();
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && condition_met) {
        int index = stack.pop();
        // Process element that found its answer
        result[index] = calculate_result(i, index);
    }
    stack.push(i);
}
```

### Backward Iteration Template
```java
for (int i = n - 2; i >= 0; i--) {
    int j = i + 1;
    while (j < n && !condition_met) {
        if (no_solution_beyond_j) {
            j = n; // Exit
        } else {
            j += result[j]; // Jump ahead
        }
    }
    if (j < n) result[i] = j - i;
}
```

## Key Insights

1. **Stack Choice**: Use decreasing stack for "next greater" problems
2. **Store Indices**: Need positions for distance calculation
3. **Efficiency**: Each element processed exactly once in O(n) solutions
4. **Space Trade-off**: Stack approach trades space for simplicity
5. **Pattern Application**: This technique applies to many array problems

## Practice Progression

1. **Start**: Implement brute force to understand the problem
2. **Learn**: Master the monotonic stack approach
3. **Optimize**: Try the backward iteration for space optimization
4. **Apply**: Solve similar "next greater element" problems
5. **Advanced**: Adapt the pattern to other problem types

## Interview Tips

- **Start with brute force** to show understanding
- **Optimize to monotonic stack** as the main solution
- **Mention space optimization** if asked about alternatives
- **Explain the pattern** - this shows deeper understanding
- **Trace through examples** to verify correctness

---

## See Also

- [Monotonic Stack Pattern CheatSheet](../../CheatSheet.md)
- [Monotonic Stack README](../../README.md)
- [Study Guide with Detailed Traces](StudyGuide.md)