# Backtracking Pattern - Flashcards

## Core Concepts

**Q: What is backtracking and when should you use it?**
A: Backtracking is a systematic method for solving problems by exploring all possible solutions. Use it when you need to:
- Generate all possible solutions (permutations, combinations, subsets)
- Find one valid solution (Sudoku, N-Queens, maze solving)
- Solve optimization problems by exploring all paths
- Handle constraint satisfaction problems

**Q: What are the key characteristics of backtracking problems?**
A:
1. Decision Tree: Each step represents a choice/decision
2. Incremental Building: Build solution step by step
3. Pruning: Abandon paths that can't lead to valid solutions
4. Backtrack: Undo choices when they don't work out

**Q: What is the standard backtracking template?**
A:
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

## Problem Categories

**Q: What are the four main types of backtracking problems?**
A:
1. **Combination/Permutation Generation**: Letter combinations, permutations, combinations, generate parentheses
2. **Subset Generation**: Subsets, subsets with duplicates, combination sum
3. **Grid/Board Problems**: N-Queens, Sudoku solver, word search, rat in maze
4. **String/Sequence Problems**: Palindrome partitioning, IP address restoration, word break II

**Q: What's the difference between combinations and permutations in backtracking?**
A:
- **Combinations**: Order doesn't matter, use start index to avoid duplicates
- **Permutations**: Order matters, use visited array to track used elements

## Implementation Patterns

**Q: What are the two most common backtracking patterns?**
A:
**Pattern 1 - Build solution incrementally:**
```java
void backtrack(int index, List<Type> current) {
    if (index == target) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (Type choice : choices) {
        current.add(choice);
        backtrack(index + 1, current);
        current.remove(current.size() - 1);
    }
}
```

**Pattern 2 - Use boolean array for visited/used:**
```java
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

## Time Complexity

**Q: What are the typical time complexities for backtracking problems?**
A:
- **Permutations**: O(N! × N) - N! permutations, N time to build each
- **Combinations**: O(2^N × N) - 2^N subsets, N time to build each
- **Grid traversal**: O(4^(M×N)) - 4 directions at each cell
- **String partitioning**: O(2^N × N) - 2^N ways to partition, N to check each

## Common Mistakes

**Q: What are the top 5 common mistakes in backtracking and how to avoid them?**
A:
1. **Forgetting to backtrack**: Always undo state changes after recursive call
2. **Reference vs Copy**: Add `new ArrayList<>(current)` not just `current` to results
3. **Base case errors**: Carefully define when solution is complete
4. **Duplicate handling**: Sort array and skip duplicates properly when needed
5. **State pollution**: Don't modify global state without proper restoration

## Problem-Solving Strategy

**Q: What are the key steps to solve a backtracking problem?**
A:
1. **Identify the Choices**: What decisions do you make at each step?
2. **Define Base Case**: When is your solution complete?
3. **State Management**: What information needs to pass between recursive calls?
4. **Pruning Optimization**: Can you detect invalid partial solutions early?

**Q: How do you handle duplicates in backtracking problems?**
A:
1. Sort the input array first
2. Skip duplicates at the same level:
```java
if (i > start && nums[i] == nums[i-1]) continue;
```
3. Use the element first before skipping to allow duplicates in different branches

**Q: When should you use pruning in backtracking?**
A: Use pruning when you can detect early that a partial solution cannot lead to a valid complete solution. This significantly improves performance by avoiding unnecessary recursive calls.