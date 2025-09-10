# Can Place Flowers - Study Guide

## Greedy Algorithm Fundamentals

### What is a Greedy Algorithm?
A **greedy algorithm** makes the locally optimal choice at each step, hoping to find a global optimum. It never reconsiders previous choices.

### Key Characteristics
1. **Local Optimization**: Makes the best choice available at each step
2. **No Backtracking**: Never reconsiders previous decisions
3. **Irrevocable Choices**: Once a decision is made, it cannot be undone
4. **Greedy Choice Property**: Local optimal choices lead to global optimum

### When Greedy Works
Greedy algorithms work when the problem has:
- **Greedy Choice Property**: A locally optimal choice leads to a globally optimal solution
- **Optimal Substructure**: Optimal solution contains optimal solutions to subproblems

## Problem-Specific Analysis

### Why Greedy Works for Flower Placement

#### Greedy Choice Property
**Claim**: If we can plant a flower at position `i`, it's always optimal to do so.

**Proof**: 
- Planting at position `i` blocks positions `i-1` and `i+1` from future planting
- NOT planting at position `i` still blocks `i-1` and `i+1` if they contain flowers
- Therefore, planting when possible never reduces our future options
- The greedy choice is always safe

#### Optimal Substructure
After planting a flower at position `i`, the remaining problem splits into independent subproblems:
- Left subproblem: positions `[0, i-2]`
- Right subproblem: positions `[i+2, n-1]`
- These subproblems can be solved independently

## Step-by-Step Algorithm Walkthrough

### Example: flowerbed = [1,0,0,0,1], n = 1

```
Initial: [1, 0, 0, 0, 1], need to plant 1 flower

Step 1: Check position 0
- flowerbed[0] = 1 (occupied) → skip

Step 2: Check position 1  
- flowerbed[1] = 0 (empty) ✓
- Left neighbor: flowerbed[0] = 1 (occupied) → cannot plant

Step 3: Check position 2
- flowerbed[2] = 0 (empty) ✓  
- Left neighbor: flowerbed[1] = 0 (empty) ✓
- Right neighbor: flowerbed[3] = 0 (empty) ✓
- All conditions met → PLANT! [1, 0, 1, 0, 1]
- Planted count = 1, needed = 1 → SUCCESS!

Result: true (we can plant the required flower)
```

### Example: flowerbed = [1,0,0,0,1], n = 2

```
Same process, but after planting 1 flower at position 2:
[1, 0, 1, 0, 1]

Continue checking positions 3 and 4:
- Position 3: has neighbor at position 2 → cannot plant
- Position 4: occupied → cannot plant

Final planted count = 1, needed = 2 → FAILURE!
Result: false
```

## Edge Cases and Boundary Conditions

### 1. Single Element Array
```
[0] → can plant 1 flower
[1] → cannot plant any flowers
```

### 2. Array Boundaries
- **Left boundary** (i = 0): No left neighbor to check
- **Right boundary** (i = length-1): No right neighbor to check

### 3. All Empty Array
```
[0,0,0,0,0] → can plant at positions 0, 2, 4 (total: 3 flowers)
Pattern: every other position starting from 0
```

### 4. Alternating Pattern
```
[1,0,1,0,1] → cannot plant any additional flowers
```

### 5. n = 0 Case
Always return `true` (no flowers needed to be planted)

## Implementation Considerations

### Condition Checking
For position `i`, check if we can plant:
```java
boolean canPlant = flowerbed[i] == 0 && 
                   (i == 0 || flowerbed[i-1] == 0) &&
                   (i == length-1 || flowerbed[i+1] == 0);
```

### Early Termination
```java
if (planted >= n) {
    return true;  // Found enough spots, no need to continue
}
```

### In-Place Modification
After planting: `flowerbed[i] = 1` to prevent adjacent planting

## Common Mistakes to Avoid

1. **Forgetting boundary conditions**: Always check if `i-1` and `i+1` are valid indices
2. **Not updating the array**: Must set `flowerbed[i] = 1` after planting
3. **Wrong adjacency check**: Remember flowers cannot be adjacent
4. **Off-by-one errors**: Array indices start at 0, not 1
5. **Missing early termination**: Continue checking even after planting enough flowers

## Time and Space Complexity

### Time Complexity: O(n)
- Single pass through the array
- Each position checked at most once
- Early termination possible

### Space Complexity: O(1)
- Only using constant extra variables
- Modifying input array in-place (if allowed)
- If input modification not allowed, need O(n) space for copy

## Related Problems

1. **Jump Game**: Greedy choice of maximum reach
2. **Gas Station**: Greedy starting point selection  
3. **Activity Selection**: Greedy interval scheduling
4. **Fractional Knapsack**: Greedy value-to-weight ratio

## Interview Tips

1. **Start with brute force**: Consider all possible placements, then optimize
2. **Explain greedy choice**: Why planting early is always optimal
3. **Handle edge cases**: Boundary conditions and special inputs
4. **Trace through examples**: Walk through the algorithm step-by-step
5. **Discuss optimization**: Early termination and space efficiency