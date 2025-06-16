# Backtracking Pattern

## Overview

Backtracking is a systematic method for solving problems by exploring all possible solutions. It builds solutions incrementally and abandons ("backtracks") partial solutions that cannot lead to a valid complete solution.

## When to Use Backtracking

- **Generate all possible solutions**: Permutations, combinations, subsets
- **Find one valid solution**: Sudoku, N-Queens, maze solving
- **Optimization problems**: Where you need to explore all paths to find the best
- **Constraint satisfaction**: Problems with specific rules/constraints

## Key Characteristics

1. **Decision Tree**: Each step represents a choice/decision
2. **Incremental Building**: Build solution step by step
3. **Pruning**: Abandon paths that can't lead to valid solutions
4. **Backtrack**: Undo choices when they don't work out

## The Backtracking Template

```java
public void backtrack(parameters) {
    // Base case - solution is complete
    if (isComplete(parameters)) {
        processSolution(parameters);
        return;
    }
    
    // Try all possible choices at current step
    for (Choice choice : getPossibleChoices(parameters)) {
        // Make choice
        makeChoice(choice, parameters);
        
        // Recurse with updated state
        backtrack(updatedParameters);
        
        // Undo choice (backtrack)
        undoChoice(choice, parameters);
    }
}
```

## Common Problem Types

### 1. **Combination/Permutation Generation**
- Letter Combinations of Phone Number
- Permutations
- Combinations
- Generate Parentheses

### 2. **Subset Generation**
- Subsets
- Subsets II (with duplicates)
- Combination Sum

### 3. **Grid/Board Problems**
- N-Queens
- Sudoku Solver
- Word Search
- Rat in a Maze

### 4. **String/Sequence Problems**
- Palindrome Partitioning
- IP Address Restoration
- Word Break II

## Key Tips

### 1. **Identify the Choices**
What decisions do you make at each step? These become your recursive branches.

### 2. **Define Base Case**
When is your solution complete? When do you stop recursing?

### 3. **State Management**
- What information do you need to pass between recursive calls?
- Do you need to explicitly undo changes, or does the recursion handle it?

### 4. **Pruning Optimization**
- Can you detect invalid partial solutions early?
- Use constraints to avoid exploring impossible paths

### 5. **Common Patterns**
```java
// Pattern 1: Build solution incrementally (most common)
void backtrack(int index, List<Type> current) {
    if (index == target) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (Type choice : choices) {
        current.add(choice);
        backtrack(index + 1, current);
        current.remove(current.size() - 1); // backtrack
    }
}

// Pattern 2: Use boolean array for visited/used
void backtrack(boolean[] used, List<Type> current) {
    if (current.size() == target) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int i = 0; i < choices.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        current.add(choices[i]);
        backtrack(used, current);
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```

## Time Complexity Patterns

- **Permutations**: O(N! × N) - N! permutations, N time to build each
- **Combinations**: O(2^N × N) - 2^N subsets, N time to build each
- **Grid traversal**: O(4^(M×N)) - 4 directions at each cell
- **String partitioning**: O(2^N × N) - 2^N ways to partition, N to check each

## Common Mistakes to Avoid

1. **Forgetting to backtrack**: Not undoing state changes
2. **Reference vs Copy**: Adding references instead of copies to result
3. **Base case errors**: Incorrect stopping conditions
4. **Duplicate handling**: Not properly handling duplicate elements
5. **State pollution**: Modifying global state without restoration

## Resources

- [Backtracking Algorithm - GeeksforGeeks](https://www.geeksforgeeks.org/backtracking-algorithms/)
- [LeetCode Backtracking Problems](https://leetcode.com/tag/backtracking/)
- [Backtracking Template - LeetCode Discuss](https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning))