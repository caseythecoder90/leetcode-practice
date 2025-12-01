# Spiral Matrix - Study Guide

## Problem Insight

The key to Spiral Matrix is understanding that we're **peeling layers like an onion**. We start from the outermost layer and work our way inward, following a consistent clockwise pattern.

**Core Realization:**
- Each layer has 4 sides: top, right, bottom, left
- We traverse each side in order: right → down → left → up
- After completing a layer, we shrink boundaries and repeat
- The spiral naturally forms by this boundary-shrinking process

## The Critical Bug to Avoid

**The most common mistake** is adding duplicate elements when processing the last layer:

```java
// ❌ WRONG - Can add duplicates!
while (top <= bottom && left <= right) {
    // Traverse right
    for (int col = left; col <= right; col++)
        result.add(matrix[top][col]);
    top++;

    // Traverse down
    for (int row = top; row <= bottom; row++)
        result.add(matrix[row][right]);
    right--;

    // Traverse left - NO CHECK!
    for (int col = right; col >= left; col--)
        result.add(matrix[bottom][col]);  // Might duplicate!
    bottom--;

    // Traverse up - NO CHECK!
    for (int row = bottom; row >= top; row--)
        result.add(matrix[row][left]);  // Might duplicate!
    left++;
}
```

**Why it's wrong:** When we have a single row or column remaining, traversing left/up will revisit elements we already added during right/down traversal.

**The fix:**
```java
// ✓ CORRECT - Check boundaries before opposite direction
while (top <= bottom && left <= right) {
    // Traverse right
    for (int col = left; col <= right; col++)
        result.add(matrix[top][col]);
    top++;

    // Traverse down
    for (int row = top; row <= bottom; row++)
        result.add(matrix[row][right]);
    right--;

    // Check if we still have a row to traverse left
    if (top <= bottom) {
        for (int col = right; col >= left; col--)
            result.add(matrix[bottom][col]);
        bottom--;
    }

    // Check if we still have a column to traverse up
    if (left <= right) {
        for (int row = bottom; row >= top; row--)
            result.add(matrix[row][left]);
        left++;
    }
}
```

## Understanding Boundary Movement

Think of boundaries as shrinking walls:

```
Initial 3x3 matrix:
top=0, bottom=2, left=0, right=2

1 2 3
4 5 6
7 8 9

After layer 1 (outer ring):
top=1, bottom=1, left=1, right=1

× × ×
× 5 ×
× × ×

After layer 2 (center):
top=2, bottom=0 (crossed!) - STOP
```

**Key Pattern:**
- After traversing **right** (top row): increment `top`
- After traversing **down** (right column): decrement `right`
- After traversing **left** (bottom row): decrement `bottom`
- After traversing **up** (left column): increment `left`

## Detailed Dry Run: 3x4 Matrix

Let's trace through: `[[1,2,3,4], [5,6,7,8], [9,10,11,12]]`

```
Initial state:
top=0, bottom=2, left=0, right=3

 1   2   3   4
 5   6   7   8
 9  10  11  12
```

**Iteration 1:**

1. **Traverse RIGHT** (top row, col: 0→3):
   ```
   Add: 1, 2, 3, 4
   top++ → top=1

    ✓   ✓   ✓   ✓
    5   6   7   8
    9  10  11  12
   ```

2. **Traverse DOWN** (right column, row: 1→2):
   ```
   Add: 8, 12
   right-- → right=2

    ✓   ✓   ✓   ✓
    5   6   7   ✓
    9  10  11   ✓
   ```

3. **Traverse LEFT** (bottom row, col: 2→0):
   ```
   Check: top(1) <= bottom(2)? YES - proceed
   Add: 11, 10, 9
   bottom-- → bottom=1

    ✓   ✓   ✓   ✓
    5   6   7   ✓
    ✓   ✓   ✓   ✓
   ```

4. **Traverse UP** (left column, row: 1→1):
   ```
   Check: left(0) <= right(2)? YES - proceed
   Add: 5
   left++ → left=1

    ✓   ✓   ✓   ✓
    ✓   6   7   ✓
    ✓   ✓   ✓   ✓
   ```

**Iteration 2:**

State: `top=1, bottom=1, left=1, right=2`

1. **Traverse RIGHT** (row 1, col: 1→2):
   ```
   Add: 6, 7
   top++ → top=2

    ✓   ✓   ✓   ✓
    ✓   ✓   ✓   ✓
    ✓   ✓   ✓   ✓
   ```

2. **Traverse DOWN** (col 2, row: 2→1):
   ```
   No rows (top=2 > bottom=1) - skip
   right-- → right=1
   ```

3. **Traverse LEFT** - Check fails!
   ```
   Check: top(2) <= bottom(1)? NO - SKIP
   (Prevents duplicating row 1 elements)
   ```

4. **Traverse UP** - Check fails!
   ```
   Check: left(1) <= right(1)? YES but no rows
   ```

**Loop condition:** `top(2) > bottom(1)` - EXIT

**Final Result:** `[1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]` ✓

## Edge Case: Single Row

Matrix: `[[1, 2, 3, 4]]`

```
Initial: top=0, bottom=0, left=0, right=3

1  2  3  4
```

**Iteration 1:**

1. **Traverse RIGHT:** Add 1, 2, 3, 4; `top++ → top=1`
2. **Traverse DOWN:** Skip (top=1 > bottom=0)
3. **Traverse LEFT:**
   - Check: `top(1) <= bottom(0)`? **NO - SKIP** ✓
   - This prevents re-adding the row!
4. **Traverse UP:** Check fails

**Result:** `[1, 2, 3, 4]` ✓ (No duplicates!)

## Edge Case: Single Column

Matrix: `[[1], [2], [3], [4]]`

```
Initial: top=0, bottom=3, left=0, right=0

1
2
3
4
```

**Iteration 1:**

1. **Traverse RIGHT:** Add 1; `top++ → top=1`
2. **Traverse DOWN:** Add 2, 3, 4; `right-- → right=-1`
3. **Traverse LEFT:** Check fails (right=-1 < left=0)
4. **Traverse UP:**
   - Check: `left(0) <= right(-1)`? **NO - SKIP** ✓
   - This prevents re-adding the column!

**Result:** `[1, 2, 3, 4]` ✓ (No duplicates!)

## Two Solution Approaches

### Approach 1: Boundary Shrinking (Recommended)

**Pros:**
- O(1) space (excluding output)
- No extra data structures needed
- Efficient and clean

**Cons:**
- Requires careful boundary checking
- Easy to make off-by-one errors

**When to use:** Best for interviews - shows strong boundary management skills

### Approach 2: Direction Vectors with Visited Array

**How it works:**
- Direction vectors: `[(0,1), (1,0), (0,-1), (-1,0)]` for R, D, L, U
- Boolean `visited[m][n]` array to mark processed cells
- Move in current direction until blocked
- Turn clockwise when hitting boundary or visited cell

**Pros:**
- More intuitive movement logic
- Easier to understand for beginners
- Generalizes well to irregular shapes

**Cons:**
- O(m × n) extra space for visited array
- More memory usage

**When to use:** If you struggle with boundary logic or need to handle non-rectangular regions

## Complexity Analysis

**Boundary Shrinking Approach:**
- **Time:** O(m × n) - visit each cell exactly once
- **Space:** O(1) - only boundary variables (output doesn't count)

**Direction Vector Approach:**
- **Time:** O(m × n) - visit each cell exactly once
- **Space:** O(m × n) - visited array

## Common Pitfalls

1. **Forgetting boundary checks before left/up traversal**
   - Symptom: Duplicate elements in output
   - Fix: Always check `top <= bottom` before left, `left <= right` before up

2. **Updating boundaries at the wrong time**
   - Symptom: Skipping elements or infinite loop
   - Fix: Update immediately after completing each direction

3. **Using wrong comparison operators**
   - Using `col < right` instead of `col <= right`
   - Symptom: Missing the last element in each direction

4. **Mixing up row/column in nested loops**
   - RIGHT: `for (col = left; col <= right; col++)` on `matrix[top][col]`
   - DOWN: `for (row = top; row <= bottom; row++)` on `matrix[row][right]`
   - LEFT: `for (col = right; col >= left; col--)` on `matrix[bottom][col]`
   - UP: `for (row = bottom; row >= top; row--)` on `matrix[row][left]`

5. **Not handling empty matrix**
   - Always check `matrix == null || matrix.length == 0`

## Interview Strategy

**Start with clarification (30 seconds):**
> "I need to traverse the matrix in spiral order - starting from top-left, going right, then down, then left, then up, and spiraling inward. Should I handle empty matrices?"

**Explain approach (1 minute):**
> "I'll use four boundaries: top, bottom, left, right. I'll traverse each layer clockwise: right along the top, down along the right side, left along the bottom, and up along the left side. After each full layer, I shrink the boundaries inward. The key is checking that we still have valid rows before traversing left and valid columns before traversing up to avoid duplicates."

**Code (3-4 minutes):**
> [Write the boundary shrinking solution]

**Trace with edge case (1 minute):**
> "Let me verify with a single row [1,2,3,4]:
> - Traverse right adds all 4 elements, top becomes 1
> - Traverse down skips (top > bottom)
> - Traverse left checks top <= bottom (1 <= 0? No) - SKIPS correctly
> - This prevents duplicating the row!"

**Complexity (30 seconds):**
> "Time is O(m×n) since we visit each element once. Space is O(1) excluding the output array."

## Testing Edge Cases

```java
// Test 1: Square matrix
[[1,2,3],
 [4,5,6],
 [7,8,9]]
Expected: [1,2,3,6,9,8,7,4,5]

// Test 2: Wide rectangle
[[1,2,3,4]]
Expected: [1,2,3,4]

// Test 3: Tall rectangle
[[1],[2],[3],[4]]
Expected: [1,2,3,4]

// Test 4: Single element
[[5]]
Expected: [5]

// Test 5: 2x3 matrix
[[1,2,3],
 [4,5,6]]
Expected: [1,2,3,6,5,4]

// Test 6: 3x2 matrix
[[1,2],
 [3,4],
 [5,6]]
Expected: [1,2,4,6,5,3]
```

## Related Matrix Patterns

1. **Layer-by-layer processing**
   - Rotate Image, Spiral Matrix II
   - Process outer ring, shrink, repeat

2. **Direction-based traversal**
   - Robot movement problems
   - Snake game, maze navigation

3. **Boundary management**
   - Sliding window on 2D grids
   - Region growing algorithms

4. **Coordinate transformation**
   - Matrix rotation
   - Transpose, flip operations

## Related Problems

- **Spiral Matrix II (LeetCode 59):** Fill matrix in spiral order - reverse problem!
- **Rotate Image (LeetCode 48):** Layer-by-layer rotation
- **Diagonal Traverse (LeetCode 498):** Different traversal pattern
- **Game of Life (LeetCode 289):** In-place matrix updates

---

**Remember:** The essence of Spiral Matrix is boundary management. Master the pattern: traverse → update boundary → check before opposite direction. Practice until the boundary checks become second nature!
