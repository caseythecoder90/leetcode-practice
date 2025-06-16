# Combination Sum III - Study Guide

## Complete Walkthrough Example

Let's trace through **k=3, n=9** step by step to understand how backtracking works.

**Goal**: Find all combinations of exactly 3 numbers from [1,2,3,4,5,6,7,8,9] that sum to 9.

### Decision Tree Visualization

```
                    []
                   /|\
                  1 2 3 ...
                 /  |  \
              [1]  [2]  [3] ...
             / | \
           [1,2][1,3][1,4]...
```

### Step-by-Step Execution

#### Initial Call: `backtrack(result=[], current=[], k=3, remainingSum=9, start=1)`

**Step 1: Try starting with 1**
- `current = [1]`
- `remainingSum = 9 - 1 = 8`
- `start = 2` (next number must be > 1)

**Step 2: From [1], try adding 2**
- `current = [1, 2]`
- `remainingSum = 8 - 2 = 6`
- `start = 3` (next number must be > 2)

**Step 3: From [1,2], try adding 3**
- `current = [1, 2, 3]`
- `remainingSum = 6 - 3 = 3`
- Size = 3 (equals k), but remainingSum = 3 ≠ 0
- **Not a valid solution**, backtrack

**Step 4: From [1,2], try adding 4**
- `current = [1, 2, 4]`
- `remainingSum = 6 - 4 = 2`
- Size = 3 (equals k), but remainingSum = 2 ≠ 0
- **Not a valid solution**, backtrack

**Step 5: From [1,2], try adding 5**
- `current = [1, 2, 5]`
- `remainingSum = 6 - 5 = 1`
- Size = 3 (equals k), but remainingSum = 1 ≠ 0
- **Not a valid solution**, backtrack

**Step 6: From [1,2], try adding 6**
- `current = [1, 2, 6]`
- `remainingSum = 6 - 6 = 0`
- Size = 3 (equals k) AND remainingSum = 0
- **✅ Valid solution found!** Add [1,2,6] to result

Continue this process...

**Step 7: From [1], try adding 3**
- `current = [1, 3]`
- `remainingSum = 8 - 3 = 5`
- `start = 4`

**Step 8: From [1,3], try adding 4**
- `current = [1, 3, 4]`
- `remainingSum = 5 - 4 = 1`
- Size = 3, but remainingSum = 1 ≠ 0
- **Not valid**, backtrack

**Step 9: From [1,3], try adding 5**
- `current = [1, 3, 5]`
- `remainingSum = 5 - 5 = 0`
- Size = 3 AND remainingSum = 0
- **✅ Valid solution found!** Add [1,3,5] to result

Continue until we try starting with 2...

**Step 10: Try starting with 2**
- `current = [2]`
- `remainingSum = 9 - 2 = 7`
- `start = 3`

**Step 11: From [2], try adding 3**
- `current = [2, 3]`
- `remainingSum = 7 - 3 = 4`
- `start = 4`

**Step 12: From [2,3], try adding 4**
- `current = [2, 3, 4]`
- `remainingSum = 4 - 4 = 0`
- Size = 3 AND remainingSum = 0
- **✅ Valid solution found!** Add [2,3,4] to result

### Final Result: `[[1,2,6], [1,3,5], [2,3,4]]`

## Key Observations from the Walkthrough

### 1. Systematic Exploration
The algorithm tries every possible combination in a systematic way:
- All combinations starting with 1: [1,2,6], [1,3,5]
- All combinations starting with 2: [2,3,4]
- Combinations starting with 3 or higher won't work (3+4+5 = 12 > 9)

### 2. Pruning in Action
Notice how we skip invalid paths early:
- If `remainingSum < 0`: current path can't lead to solution
- If `current.size() > k`: we've used too many numbers
- If `i > remainingSum`: current number is too big

### 3. No Duplicates
By always choosing `start` and higher numbers, we ensure:
- [1,2,6] is found, but [2,1,6] or [6,1,2] are never generated
- Natural ordering prevents duplicate combinations

## Memory Trace

```
Call Stack Visualization:

backtrack([], 3, 9, 1)
├── backtrack([1], 3, 8, 2)  
│   ├── backtrack([1,2], 3, 6, 3)
│   │   ├── backtrack([1,2,3], 3, 3, 4) → Invalid
│   │   ├── backtrack([1,2,4], 3, 2, 5) → Invalid  
│   │   ├── backtrack([1,2,5], 3, 1, 6) → Invalid
│   │   └── backtrack([1,2,6], 3, 0, 7) → ✅ VALID
│   ├── backtrack([1,3], 3, 5, 4)
│   │   ├── backtrack([1,3,4], 3, 1, 5) → Invalid
│   │   └── backtrack([1,3,5], 3, 0, 6) → ✅ VALID
│   └── backtrack([1,4], 3, 4, 5) → (continue...)
├── backtrack([2], 3, 7, 3)
│   ├── backtrack([2,3], 3, 4, 4)  
│   │   └── backtrack([2,3,4], 3, 0, 5) → ✅ VALID
│   └── backtrack([2,4], 3, 3, 5) → (continue...)
└── backtrack([3], 3, 6, 4) → (continue...)
```

## Practice Questions

After studying this walkthrough, test your understanding:

1. **What would be the result for k=2, n=5?**
2. **Why don't we find [4,2,3] as a solution even though 4+2+3=9?**
3. **What's the maximum possible sum for k=3? What's the minimum?**
4. **How would the algorithm behave for k=4, n=10?**

## Key Takeaways

1. **Backtracking = Choose, Explore, Unchoose**: Always remember to remove the last element after exploring
2. **Order matters for avoiding duplicates**: The `start` parameter is crucial
3. **Early pruning saves time**: Check impossible conditions before going deeper
4. **Base cases are critical**: Always check size and sum conditions properly

## Code Template to Remember

```java
private void backtrack(List<List<Integer>> result, List<Integer> current,
                      int k, int remainingSum, int start) {
    // Base case: found valid combination
    if (current.size() == k) {
        if (remainingSum == 0) {
            result.add(new ArrayList<>(current)); // Don't forget new ArrayList!
        }
        return;
    }
    
    // Pruning: impossible to succeed
    if (remainingSum < 0 || current.size() > k) {
        return;
    }
    
    // Try each valid choice
    for (int i = start; i <= 9; i++) {
        if (i > remainingSum) break;  // More pruning
        
        current.add(i);                                    // Choose
        backtrack(result, current, k, remainingSum - i, i + 1);  // Explore  
        current.remove(current.size() - 1);               // Unchoose
    }
}
```