# Spiral Matrix - Flashcards

## Core Concept

**Q: What is the Spiral Matrix problem asking you to do?**
A: Traverse an m×n matrix in spiral (clockwise) order, starting from the top-left corner and moving inward layer by layer, returning all elements in the order visited.

**Q: What's the key insight for solving Spiral Matrix efficiently?**
A: Think of the matrix as concentric layers (like an onion). Process each layer by traversing its four sides in clockwise order (right → down → left → up), then shrink the boundaries and repeat.

---

## Algorithm Pattern

**Q: What are the four boundaries used in the boundary-shrinking approach?**
A:
- `top` - top boundary (starts at 0)
- `bottom` - bottom boundary (starts at rows - 1)
- `left` - left boundary (starts at 0)
- `right` - right boundary (starts at cols - 1)

**Q: What is the traversal order for each layer?**
A:
1. **RIGHT**: Traverse top row from left to right
2. **DOWN**: Traverse right column from top to bottom
3. **LEFT**: Traverse bottom row from right to left
4. **UP**: Traverse left column from bottom to top

**Q: How do you update boundaries after each traversal direction?**
A:
- After **RIGHT**: `top++` (move top boundary down)
- After **DOWN**: `right--` (move right boundary left)
- After **LEFT**: `bottom--` (move bottom boundary up)
- After **UP**: `left++` (move left boundary right)

**Q: What's the loop condition for the boundary-shrinking approach?**
A: `while (top <= bottom && left <= right)` - continue while boundaries haven't crossed.

---

## The Critical Bug

**Q: What's the most common bug in Spiral Matrix solutions?**
A: Adding duplicate elements when processing the last layer. This happens when you traverse LEFT or UP without checking if there's still a valid row or column remaining.

**Q: What checks must you add before the LEFT and UP traversals?**
A:
- Before **LEFT**: Check `if (top <= bottom)` - ensures we still have a row to traverse
- Before **UP**: Check `if (left <= right)` - ensures we still have a column to traverse

**Q: Why do we need boundary checks before LEFT and UP but not RIGHT and DOWN?**
A: Because RIGHT and DOWN are the "primary" directions that start each layer. By the time we reach LEFT or UP, we might have only a single row or column remaining, which would have already been processed by RIGHT or DOWN. Without checks, we'd add those elements twice.

**Q: Walk through why a single row `[[1,2,3]]` needs the boundary check:**
A:
1. **RIGHT**: Adds 1, 2, 3; then `top++` → `top=1`
2. **DOWN**: Skips (no rows since `top=1 > bottom=0`)
3. **LEFT**: WITHOUT check, would traverse bottom row again, adding 3, 2, 1 (WRONG!)
4. WITH check `if (top <= bottom)` = `if (1 <= 0)` = FALSE, so skip ✓
5. Final result: `[1, 2, 3]` without duplicates!

---

## Complexity

**Q: What's the time complexity of Spiral Matrix?**
A: O(m × n) where m = rows, n = columns. We visit each element exactly once.

**Q: What's the space complexity of the boundary-shrinking approach?**
A: O(1) - only using 4 boundary variables. The output list doesn't count toward space complexity.

**Q: What's the space complexity of the direction-vector approach?**
A: O(m × n) for the `visited` boolean array that tracks which cells we've processed.

---

## Implementation Details

**Q: Write the RIGHT traversal code:**
A:
```java
// Traverse top row from left to right
for (int col = left; col <= right; col++) {
    result.add(matrix[top][col]);
}
top++;
```

**Q: Write the DOWN traversal code:**
A:
```java
// Traverse right column from top to bottom
for (int row = top; row <= bottom; row++) {
    result.add(matrix[row][right]);
}
right--;
```

**Q: Write the LEFT traversal code (with boundary check):**
A:
```java
// Traverse bottom row from right to left (if still valid)
if (top <= bottom) {
    for (int col = right; col >= left; col--) {
        result.add(matrix[bottom][col]);
    }
    bottom--;
}
```

**Q: Write the UP traversal code (with boundary check):**
A:
```java
// Traverse left column from bottom to top (if still valid)
if (left <= right) {
    for (int row = bottom; row >= top; row--) {
        result.add(matrix[row][left]);
    }
    left++;
}
```

---

## Edge Cases

**Q: What edge cases should you test for Spiral Matrix?**
A:
1. Empty matrix: `[]` or `[[]]`
2. Single element: `[[5]]`
3. Single row: `[[1,2,3,4]]`
4. Single column: `[[1],[2],[3],[4]]`
5. Square matrix: `[[1,2],[3,4]]`
6. Wide rectangle: `[[1,2,3,4],[5,6,7,8]]`
7. Tall rectangle: `[[1,2],[3,4],[5,6],[7,8]]`

**Q: How do you handle an empty matrix?**
A: Check at the start: `if (matrix == null || matrix.length == 0) return new ArrayList<>();`

**Q: What happens when you process a single element matrix `[[5]]`?**
A:
1. RIGHT: Adds 5; `top++` → `top=1`
2. DOWN: Skips (`top=1 > bottom=0`)
3. LEFT: Check fails (`top=1 > bottom=0`)
4. UP: Check fails (`left=0 > right=-1`)
5. Result: `[5]` ✓

---

## Alternative Approach

**Q: Describe the direction-vector approach:**
A:
- Use direction vectors: `dr = [0, 1, 0, -1]`, `dc = [1, 0, -1, 0]` for right, down, left, up
- Keep a `visited[m][n]` boolean array
- Start at (0,0), direction index 0 (right)
- Move in current direction until hitting boundary or visited cell
- When blocked, turn clockwise: `dir = (dir + 1) % 4`
- Continue for m×n iterations

**Q: When would you choose the direction-vector approach over boundary shrinking?**
A: When:
- You find boundary management confusing
- The shape is non-rectangular or irregular
- You need to generalize to other movement patterns
- Memory isn't a constraint (it uses O(m×n) space)

**Q: When would you choose boundary shrinking over direction vectors?**
A: When:
- You want optimal O(1) space
- The problem is specifically about rectangular matrices
- You want to demonstrate strong boundary management skills in an interview
- Performance matters (no visited array overhead)

---

## Common Mistakes

**Q: What's wrong with this code?**
```java
for (int col = left; col < right; col++)  // Bug!
    result.add(matrix[top][col]);
```
A: Using `col < right` instead of `col <= right` misses the last element. Should be `col <= right`.

**Q: What's wrong with this sequence?**
```java
top++;
for (int col = left; col <= right; col++)
    result.add(matrix[top][col]);
```
A: Updating the boundary BEFORE traversal instead of AFTER. This skips the actual top row and processes the second row instead.

**Q: What's the problem here?**
```java
// LEFT traversal without check
for (int col = right; col >= left; col--)
    result.add(matrix[bottom][col]);
bottom--;
```
A: Missing the `if (top <= bottom)` check before LEFT traversal. This will add duplicate elements when processing a single-row remainder.

**Q: Identify the bug:**
```java
// DOWN traversal
for (int col = top; col <= bottom; col++)  // Bug!
    result.add(matrix[col][right]);
```
A: Using `col` for row iteration. Should be `row`:
```java
for (int row = top; row <= bottom; row++)
    result.add(matrix[row][right]);
```

---

## Interview Strategy

**Q: How should you explain your approach in an interview?**
A:
1. Clarify: "I need to traverse clockwise starting from outside, spiraling inward"
2. Approach: "I'll maintain 4 boundaries and process one layer at a time"
3. Key insight: "The crucial part is checking boundaries before LEFT and UP to avoid duplicates"
4. Complexity: "O(m×n) time visiting each cell once, O(1) space for boundaries"

**Q: What should you trace through during an interview?**
A: Pick an edge case like a single row or column and trace through to show how the boundary checks prevent duplicates:
> "Let me verify with `[[1,2,3]]`: RIGHT adds all three, then `top=1 > bottom=0`, so LEFT's check `if (top <= bottom)` fails and correctly skips to avoid re-adding them."

**Q: How do you explain the boundary update order?**
A:
> "After traversing in each direction, I immediately update the corresponding boundary to 'shrink' the working area. For example, after going RIGHT along the top row, I increment `top` to move that boundary down, excluding the row we just processed."

---

## Visual Understanding

**Q: Draw the boundary state after processing the outer layer of a 4×4 matrix:**
A:
```
Initial:                After outer layer:
top=0, bottom=3         top=1, bottom=2
left=0, right=3         left=1, right=2

 1   2   3   4          ×   ×   ×   ×
 5   6   7   8          ×   6   7   ×
 9  10  11  12    →     ×  10  11   ×
13  14  15  16          ×   ×   ×   ×

Next iteration processes: 6, 7, 11, 10
```

**Q: Visualize the traversal order for a 3×4 matrix:**
A:
```
Matrix:                 Order:
 1   2   3   4          1→  2→  3→  4
 5   6   7   8                      ↓
 9  10  11  12          9← 10← 11←  8
                        ↓           ↑
                        5→  6→  7→

Sequence: 1,2,3,4 → 8,12 → 11,10,9 → 5 → 6,7
Result: [1,2,3,4,8,12,11,10,9,5,6,7]
```

---

## Pattern Recognition

**Q: What keywords indicate a Spiral Matrix-type problem?**
A: "spiral order", "clockwise traversal", "layer-by-layer", "inward spiral", "concentric traversal"

**Q: What other problems use similar boundary-shrinking technique?**
A:
- Spiral Matrix II (LeetCode 59) - fill instead of read
- Rotate Image (LeetCode 48) - layer-by-layer rotation
- Matrix operations with concentric layers
- Any "onion peeling" algorithm

**Q: When should you consider the boundary-shrinking pattern?**
A: When the problem involves:
1. Processing a 2D grid in layers from outside to inside
2. Traversing in a specific directional pattern (spiral, circular)
3. Needing to track rectangular regions that shrink over time
4. "Peeling" operations on matrices

---

## Debugging Tips

**Q: You're getting duplicate elements. What should you check first?**
A:
1. Verify you have `if (top <= bottom)` before the LEFT traversal
2. Verify you have `if (left <= right)` before the UP traversal
3. Test with single row `[[1,2,3]]` and single column `[[1],[2],[3]]`

**Q: You're missing elements. What should you check?**
A:
1. Ensure loop conditions use `<=` not `<`
2. Verify boundaries update AFTER (not before) each traversal
3. Check you're not accidentally updating wrong boundary

**Q: How can you trace/debug the boundary states?**
A: Add print statements after each direction:
```java
System.out.println("After RIGHT: top=" + top + " bottom=" + bottom +
                   " left=" + left + " right=" + right);
```

---

## Related Concepts

**Q: How does Spiral Matrix relate to Rotate Image?**
A: Both use layer-by-layer processing with boundary shrinking. Rotate Image rotates each layer in place, while Spiral Matrix reads each layer into a list.

**Q: How is this different from Diagonal Traverse?**
A: Diagonal Traverse moves along diagonals, alternating direction. Spiral Matrix moves along rectangular boundaries. Different traversal patterns require different techniques.

**Q: What matrix fundamentals does this problem teach?**
A:
1. Boundary management in 2D arrays
2. Row vs column iteration (when to use which)
3. Directional traversal patterns
4. Layer-by-layer processing
5. Edge case handling (single row/column)

---

## Memory Aids

**Q: What's a good mnemonic for remembering the traversal order?**
A: **"RDLU"** - Right, Down, Left, Up (clockwise starting from right)

**Q: How can you remember which boundaries need checks?**
A: **"Look before you go backward"** - LEFT and UP are the "backward" directions that need checks to avoid revisiting.

**Q: How to remember boundary update direction?**
A:
- **T**op moves **D**own (increases)
- **R**ight moves **L**eft (decreases)
- **B**ottom moves **U**p (decreases)
- **L**eft moves **R**ight (increases)

Or simply: "After going somewhere, close the door behind you"

---

## Quick Reference

**Q: Give the complete solution template:**
A:
```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    if (matrix == null || matrix.length == 0) return result;

    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;

    while (top <= bottom && left <= right) {
        // RIGHT
        for (int col = left; col <= right; col++)
            result.add(matrix[top][col]);
        top++;

        // DOWN
        for (int row = top; row <= bottom; row++)
            result.add(matrix[row][right]);
        right--;

        // LEFT (check)
        if (top <= bottom) {
            for (int col = right; col >= left; col--)
                result.add(matrix[bottom][col]);
            bottom--;
        }

        // UP (check)
        if (left <= right) {
            for (int row = bottom; row >= top; row--)
                result.add(matrix[row][left]);
            left++;
        }
    }
    return result;
}
```

**Q: What's the one thing you absolutely cannot forget?**
A: The boundary checks before LEFT (`if (top <= bottom)`) and UP (`if (left <= right)`) traversals. Without these, single-row or single-column cases will have duplicate elements!

---

**Final Reminder:** Spiral Matrix is fundamentally about disciplined boundary management. Master the pattern: traverse → update boundary → check before opposite direction. The checks are what make it correct!
