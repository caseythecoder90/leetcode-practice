# Backtracking CheatSheet

## Quick Template

```java
class Solution {
    List<ResultType> result = new ArrayList<>();
    
    public List<ResultType> solve(InputType input) {
        backtrack(startParameters);
        return result;
    }
    
    private void backtrack(parameters) {
        // Base case
        if (isComplete()) {
            result.add(new ArrayList<>(currentSolution));
            return;
        }
        
        // Try all choices
        for (Choice choice : getChoices()) {
            // Make choice
            makeChoice(choice);
            
            // Recurse
            backtrack(updatedParameters);
            
            // Undo choice
            undoChoice(choice);
        }
    }
}
```

## Common Patterns

### 1. Index-Based Iteration
```java
void backtrack(int index, List<Integer> current) {
    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Include current element
    current.add(nums[index]);
    backtrack(index + 1, current);
    current.remove(current.size() - 1);
    
    // Exclude current element  
    backtrack(index + 1, current);
}
```

### 2. For-Loop Choices
```java
void backtrack(int start, List<Integer> current) {
    result.add(new ArrayList<>(current));
    
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);
        backtrack(i + 1, current);
        current.remove(current.size() - 1);
    }
}
```

### 3. Boolean Visited Array
```java
void backtrack(boolean[] used, List<Integer> current) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        
        used[i] = true;
        current.add(nums[i]);
        backtrack(used, current);
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```

### 4. String Building
```java
void backtrack(int index, StringBuilder current) {
    if (index == target.length()) {
        result.add(current.toString());
        return;
    }
    
    for (char c : getChoices(index)) {
        current.append(c);
        backtrack(index + 1, current);
        current.deleteCharAt(current.length() - 1);
    }
}
```

## Optimization Techniques

### 1. Early Pruning
```java
if (isInvalidPartialSolution()) {
    return; // Don't continue this path
}
```

### 2. Duplicate Handling
```java
Arrays.sort(nums); // Sort first
for (int i = start; i < nums.length; i++) {
    if (i > start && nums[i] == nums[i-1]) {
        continue; // Skip duplicates
    }
    // ... rest of logic
}
```

### 3. Target Optimization
```java
if (currentSum > target) {
    return; // No point continuing if we exceeded target
}
```

## Quick Problem Identification

| Problem Type | Key Indicators | Template |
|--------------|----------------|----------|
| **Subsets/Combinations** | "all possible", "combinations of size k" | Index-based or For-loop |
| **Permutations** | "all arrangements", "different orders" | Boolean visited array |
| **Partition Problems** | "split into groups", "palindrome partition" | Index-based with validation |
| **Grid Problems** | "path finding", "word search" | DFS with coordinate tracking |
| **String Generation** | "generate valid", "parentheses" | String building with constraints |

## Time Complexity Quick Reference

- **Subsets**: O(2^N)
- **Permutations**: O(N!)
- **Combinations**: O(C(N,K) × K)
- **Grid DFS**: O(4^cells)
- **String Generation**: Varies by constraints

## Common Bugs & Fixes

| Bug | Fix |
|-----|-----|
| Adding reference instead of copy | `result.add(new ArrayList<>(current))` |
| Forgetting to backtrack | Add `undo()` after recursive call |
| Wrong base case | Check termination condition carefully |
| Duplicate results | Sort input + skip duplicates |
| Stack overflow | Check for infinite recursion |

## Must-Know Problems

1. **Letter Combinations of Phone Number** (17) - Basic backtracking
2. **Generate Parentheses** (22) - String generation with constraints
3. **Combination Sum** (39) - Target sum with unlimited use
4. **Permutations** (46) - Classic permutation generation
5. **Subsets** (78) - Classic subset generation
6. **N-Queens** (51) - Grid-based with constraints
7. **Word Search** (79) - Grid DFS with backtracking
8. **Palindrome Partitioning** (131) - String partitioning

## Pro Tips

1. **Always make a copy** when adding to result list
2. **Sort input** when dealing with duplicates
3. **Use StringBuilder** for string manipulation
4. **Visualize the decision tree** before coding
5. **Start with brute force**, then optimize with pruning