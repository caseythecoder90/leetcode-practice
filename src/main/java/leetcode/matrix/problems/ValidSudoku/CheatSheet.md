# Valid Sudoku - Problem Cheat Sheet

## Problem Statement
Validate a 9×9 Sudoku board (only filled cells).

## Constraints to Check
1. ✓ Each row: no duplicate digits 1-9
2. ✓ Each column: no duplicate digits 1-9  
3. ✓ Each 3×3 box: no duplicate digits 1-9

## Quick Solution Template (Boolean Arrays)

```java
public boolean isValidSudoku(char[][] board) {
    boolean[][] rows = new boolean[9][9];
    boolean[][] cols = new boolean[9][9];
    boolean[][] boxes = new boolean[9][9];
    
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] != '.') {
                int num = board[i][j] - '1';
                int boxIdx = (i / 3) * 3 + (j / 3);
                
                if (rows[i][num] || cols[j][num] || boxes[boxIdx][num]) {
                    return false;
                }
                
                rows[i][num] = cols[j][num] = boxes[boxIdx][num] = true;
            }
        }
    }
    return true;
}
```

## Key Formulas

### Box Index Calculation
```java
boxIndex = (row / 3) * 3 + (col / 3)
```

**Box Layout:**
```
0 1 2
3 4 5
6 7 8
```

**Examples:**
- Cell (0,0) → box 0
- Cell (4,4) → box 4 (center)
- Cell (8,8) → box 8

### Box to Cell Coordinates
```java
row = boxRow * 3 + i  // i ∈ [0,2]
col = boxCol * 3 + j  // j ∈ [0,2]
```

### Char to Array Index
```java
int index = digit - '1';  // '1'→0, '2'→1, ..., '9'→8
```

## Three Solution Approaches

| Approach | Pros | Cons | Interview Choice |
|----------|------|------|------------------|
| **Three-Pass** | Intuitive, easy to explain | 3× iteration | Good for explaining |
| **One-Pass String** | Clever, clean code | String overhead | Shows creativity |
| **Boolean Arrays** | Fast, clear, efficient | Slightly more code | ⭐ Best overall |

## Complexity
- **Time:** O(1) - always 81 cells
- **Space:** O(1) - fixed size tracking

## Common Mistakes

| ❌ Wrong | ✓ Correct | Why |
|---------|----------|-----|
| `col = boxRow * 3 + i` | `col = boxCol * 3 + j` | Must use boxCol for columns! |
| `num = ch - '0'` | `num = ch - '1'` | Need 0-8 index, not 1-9 |
| Shared HashSet | New HashSet per row/col/box | Each region needs separate tracking |
| Forget `!= '.'` check | Always check for empty | Empty cells don't count |

## Testing Checklist
- [ ] Empty board (all '.')
- [ ] Duplicate in row
- [ ] Duplicate in column  
- [ ] Duplicate in box only
- [ ] Single digit board
- [ ] Valid partial solution

## Interview Script

**1. Clarify (30 seconds)**
> "So I need to validate three constraints: rows, columns, and 3×3 boxes. Empty cells don't matter. I won't solve the Sudoku, just check if the current state is valid."

**2. Approach (1 minute)**
> "I'll use boolean arrays to track which digits we've seen in each row, column, and box. For each non-empty cell, I'll check if that digit already appeared in its row, column, or box. The tricky part is calculating which box a cell belongs to using `(row/3)*3 + (col/3)`."

**3. Code (3-4 minutes)**
> [Write the boolean array solution]

**4. Trace (1 minute)**
> "Let me trace cell (4,7) with digit '5':
> - Row check: `rows[4][4] = true`
> - Column check: `cols[7][4] = true`  
> - Box: `(4/3)*3 + (7/3) = 5` → `boxes[5][4] = true`"

**5. Complexity (30 seconds)**
> "Time is O(1) since we always check 81 cells. Space is O(1) with three 9×9 boolean arrays."

## Visual Box Reference

```
Sudoku Board with Box Numbers:
┌─────────┬─────────┬─────────┐
│ 0  0  0 │ 1  1  1 │ 2  2  2 │
│ 0  0  0 │ 1  1  1 │ 2  2  2 │
│ 0  0  0 │ 1  1  1 │ 2  2  2 │
├─────────┼─────────┼─────────┤
│ 3  3  3 │ 4  4  4 │ 5  5  5 │
│ 3  3  3 │ 4  4  4 │ 5  5  5 │
│ 3  3  3 │ 4  4  4 │ 5  5  5 │
├─────────┼─────────┼─────────┤
│ 6  6  6 │ 7  7  7 │ 8  8  8 │
│ 6  6  6 │ 7  7  7 │ 8  8  8 │
│ 6  6  6 │ 7  7  7 │ 8  8  8 │
└─────────┴─────────┴─────────┘
```

## Quick Debug Tips

**If validation fails incorrectly:**
1. Print which constraint failed (row/col/box)
2. Print the box index for failing cells
3. Check if you're using `boxCol` for columns (not `boxRow`!)
4. Verify char-to-int conversion (`-'1'` not `-'0'`)

**Test with this simple case:**
```
Board with only one '5' at position (4, 4):
- Should be valid
- Box index should be 4 (center box)
- rows[4][4], cols[4][4], boxes[4][4] should all become true
```

## Matrix Problem Patterns

**Valid Sudoku teaches you:**
1. Sub-region indexing in matrices
2. Multiple constraint validation
3. Choosing right data structure (HashSet vs Array)
4. Row vs column iteration
5. Coordinate transformation math

**Similar problems:**
- N-Queens (board constraint validation)
- Game of Life (neighbor regions)
- Image processing (tile/block operations)
- Word Search (grid traversal with constraints)

## One-Liner Reminders

- **Box formula:** `(row / 3) * 3 + (col / 3)`
- **Always check:** `board[i][j] != '.'` before processing
- **Array indexing:** `digit - '1'` gives 0-8
- **Three validations:** rows AND columns AND boxes
- **Interview tip:** Start with explaining the box formula clearly!
