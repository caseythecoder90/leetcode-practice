# Spiral Matrix - Cheat Sheet

## Problem Statement
Traverse an `m × n` matrix in spiral order (clockwise from outside to inside).

## Pattern Recognition
When you see: "spiral order", "clockwise traversal", "layer-by-layer"
Think: **Boundary shrinking with directional traversal**

## Quick Solution Template

```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    if (matrix == null || matrix.length == 0) return result;

    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;

    while (top <= bottom && left <= right) {
        // 1. Traverse RIGHT: top row, left to right
        for (int col = left; col <= right; col++) {
            result.add(matrix[top][col]);
        }
        top++;

        // 2. Traverse DOWN: right column, top to bottom
        for (int row = top; row <= bottom; row++) {
            result.add(matrix[row][right]);
        }
        right--;

        // 3. Traverse LEFT: bottom row, right to left (CHECK!)
        if (top <= bottom) {
            for (int col = right; col >= left; col--) {
                result.add(matrix[bottom][col]);
            }
            bottom--;
        }

        // 4. Traverse UP: left column, bottom to top (CHECK!)
        if (left <= right) {
            for (int row = bottom; row >= top; row--) {
                result.add(matrix[row][left]);
            }
            left++;
        }
    }

    return result;
}
```

## The Golden Rules

### Traversal Order (Clockwise)
```
RIGHT → DOWN → LEFT → UP
```

### Boundary Updates (After each direction)
```
RIGHT → top++
DOWN  → right--
LEFT  → bottom--
UP    → left++
```

### Critical Checks (Prevent duplicates!)
```java
// Before LEFT: check if row still exists
if (top <= bottom) { /* traverse left */ }

// Before UP: check if column still exists
if (left <= right) { /* traverse up */ }
```

## Visual Guide

```
3x4 Matrix Traversal:

Step 1 (RIGHT):        Step 2 (DOWN):         Step 3 (LEFT):
→ → → →                1 2 3 4                1 2 3 4
5 6 7 8                5 6 7 ↓                5 6 7 8
9 10 11 12             9 10 11 ↓              ← ← ← 12

Step 4 (UP):           Step 5-6 (RIGHT,DOWN): Done:
1 2 3 4                1 2 3 4                1 2 3 4
↑ 6 7 8                5 → → 8                5 6 7 8
9 10 11 12             9 10 11 12             9 10 11 12

Result: [1,2,3,4,8,12,11,10,9,5,6,7]
```

## Complexity
- **Time:** O(m × n) - visit each cell once
- **Space:** O(1) - only boundary variables (output doesn't count)

## Common Mistakes

| ❌ Wrong | ✓ Correct | Why |
|---------|----------|-----|
| No boundary check before LEFT | `if (top <= bottom)` | Prevents duplicate row |
| No boundary check before UP | `if (left <= right)` | Prevents duplicate column |
| `col < right` | `col <= right` | Misses last element |
| Update boundaries before traversal | Update AFTER traversal | Wrong order causes skips |
| Forget empty matrix check | `if (matrix.length == 0)` | Null pointer error |

## Direction Reference

```java
// RIGHT: traverse top row
for (int col = left; col <= right; col++)
    process(matrix[top][col]);

// DOWN: traverse right column
for (int row = top; row <= bottom; row++)
    process(matrix[row][right]);

// LEFT: traverse bottom row
for (int col = right; col >= left; col--)
    process(matrix[bottom][col]);

// UP: traverse left column
for (int row = bottom; row >= top; row--)
    process(matrix[row][left]);
```

## Edge Cases Checklist

- [ ] Empty matrix: `matrix.length == 0`
- [ ] Single element: `[[5]]`
- [ ] Single row: `[[1,2,3,4]]`
- [ ] Single column: `[[1],[2],[3]]`
- [ ] Square matrix: `[[1,2],[3,4]]`
- [ ] Wide rectangle: `3x5` matrix
- [ ] Tall rectangle: `5x3` matrix

## Interview Script (5 Minutes)

**1. Understand (20 sec)**
> "Traverse clockwise: right, down, left, up, spiraling inward."

**2. Approach (40 sec)**
> "I'll use 4 boundaries: top, bottom, left, right. For each layer, traverse all 4 sides in order, then shrink boundaries. Key: check `top <= bottom` before LEFT and `left <= right` before UP to avoid duplicates when only one row or column remains."

**3. Code (3 min)**
> [Write the template above]

**4. Trace (40 sec)**
> "Single row `[1,2,3]`: RIGHT adds all, then top > bottom so LEFT check fails - no duplicates! ✓"

**5. Complexity (20 sec)**
> "O(m×n) time visiting each cell once, O(1) space for boundaries only."

## Alternative Approach: Direction Vectors

```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    if (matrix.length == 0) return result;

    int m = matrix.length, n = matrix[0].length;
    boolean[][] visited = new boolean[m][n];

    // Direction vectors: right, down, left, up
    int[] dr = {0, 1, 0, -1};
    int[] dc = {1, 0, -1, 0};

    int r = 0, c = 0, dir = 0;

    for (int i = 0; i < m * n; i++) {
        result.add(matrix[r][c]);
        visited[r][c] = true;

        int nr = r + dr[dir];
        int nc = c + dc[dir];

        // Turn if next position is invalid or visited
        if (nr < 0 || nr >= m || nc < 0 || nc >= n || visited[nr][nc]) {
            dir = (dir + 1) % 4;  // Turn clockwise
            nr = r + dr[dir];
            nc = c + dc[dir];
        }

        r = nr;
        c = nc;
    }

    return result;
}
```

**Trade-off:** More intuitive but uses O(m×n) space for visited array.

## Comparison Table

| Approach | Space | Intuition | Interview Choice |
|----------|-------|-----------|------------------|
| **Boundary Shrinking** | O(1) | Moderate | ⭐ Best - shows skill |
| **Direction Vectors** | O(m×n) | High | Good if struggling |

## Quick Debug Tips

**If output has duplicates:**
1. Check if you have `if (top <= bottom)` before LEFT traversal
2. Check if you have `if (left <= right)` before UP traversal
3. Print boundaries after each direction to verify updates

**If output misses elements:**
1. Verify loop conditions use `<=` not `<`
2. Check boundary updates happen AFTER (not before) traversal
3. Test with single row/column cases

**Test with simplest case:**
```java
[[1, 2]]
Should output: [1, 2]
- RIGHT: adds 1, 2; top=1
- DOWN: skips (top > bottom)
- LEFT: check fails (top=1, bottom=0) - correct!
- If missing check, would add 2, 1 again!
```

## Memory Tricks

**"RDLU" = Right, Down, Left, Up** (Clockwise starting right)

**"After you go, close the door"** = Update boundary after traversing

**"Look before leaping backward"** = Check before LEFT and UP

**Boundary mnemonic:**
- **T**op goes **D**own (+) after RIGHT
- **R**ight goes **L**eft (-) after DOWN
- **B**ottom goes **U**p (-) after LEFT
- **L**eft goes **R**ight (+) after UP

## Related Problems

| Problem | Key Difference |
|---------|----------------|
| **Spiral Matrix II** | Fill matrix instead of read |
| **Rotate Image** | Layer rotation instead of traversal |
| **Diagonal Traverse** | Diagonal instead of spiral |
| **Set Matrix Zeroes** | In-place marking challenge |

## Pattern Applications

Use this boundary shrinking technique for:
1. **Spiral/circular traversal** problems
2. **Layer-by-layer** processing (onion peeling)
3. **Boundary inward** algorithms
4. **Clockwise/counter-clockwise** movement

## One-Liner Reminders

- **Order:** RIGHT → DOWN → LEFT (check) → UP (check)
- **Update:** Immediately after completing each direction
- **Check:** `top <= bottom` before LEFT, `left <= right` before UP
- **Loop:** Continue while `top <= bottom && left <= right`
- **Edge case:** Always handle empty matrix first

## Copy-Paste Template

```java
// Initialize
int top = 0, bottom = m - 1, left = 0, right = n - 1;

// Loop
while (top <= bottom && left <= right) {
    // Right
    for (int c = left; c <= right; c++) result.add(matrix[top][c]);
    top++;

    // Down
    for (int r = top; r <= bottom; r++) result.add(matrix[r][right]);
    right--;

    // Left (CHECK)
    if (top <= bottom) {
        for (int c = right; c >= left; c--) result.add(matrix[bottom][c]);
        bottom--;
    }

    // Up (CHECK)
    if (left <= right) {
        for (int r = bottom; r >= top; r--) result.add(matrix[r][left]);
        left++;
    }
}
```

---

**Key Insight:** Spiral Matrix is all about disciplined boundary management. The checks before LEFT and UP are what prevent duplicate elements!
