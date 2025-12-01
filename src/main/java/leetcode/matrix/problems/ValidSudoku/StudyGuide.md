# Valid Sudoku - Study Guide

## Problem Insight
- We need to validate THREE separate constraints: rows, columns, and 3x3 boxes
- Key realization: We don't need to solve the Sudoku, just check if current state is valid
- Empty cells ('.') are ignored - they don't violate any rules
- This is a **validation problem**, not an optimization or search problem

## The Critical Bug in Your Code 🐛

Your code had this line in the box validation:
```java
int col = boxRow * 3 + i;  // ❌ WRONG!
```

**Why it's wrong:** You're using `boxRow` and `i` for BOTH row AND column calculations. This means you're only checking diagonal cells within each box!

**The fix:**
```java
int row = boxRow * 3 + i;  // ✓ Correct for rows
int col = boxCol * 3 + j;  // ✓ Correct for columns - use boxCol and j!
```

## Understanding 3x3 Box Indexing

Think of the Sudoku board as a 3x3 grid of boxes:

```
Box Layout (boxRow, boxCol):
┌─────┬─────┬─────┐
│(0,0)│(0,1)│(0,2)│
├─────┼─────┼─────┤
│(1,0)│(1,1)│(1,2)│
├─────┼─────┼─────┤
│(2,0)│(2,1)│(2,2)│
└─────┴─────┴─────┘

To iterate through box (1,1) - the center box:
- boxRow = 1, boxCol = 1
- For each position (i,j) in 0..2:
  - row = boxRow * 3 + i = 1*3 + i = 3,4,5
  - col = boxCol * 3 + j = 1*3 + j = 3,4,5
- This covers cells [3,3] through [5,5]
```

## Three Solution Approaches

### Approach 1: Three-Pass (Most Intuitive)
**How it works:** Check rows, then columns, then boxes separately
- Loop 1: Check all 9 rows
- Loop 2: Check all 9 columns  
- Loop 3: Check all 9 boxes

**When to use:** When explaining your thought process first in an interview

### Approach 2: One-Pass with String Encoding (Clever)
**How it works:** Visit each cell once, store unique identifiers in a single HashSet
- For cell (i,j) with value '5':
  - Store: "5 in row 2"
  - Store: "5 in col 4"
  - Store: "5 in box 1-1"

**When to use:** When you want to impress with a clean, optimal solution

### Approach 3: Boolean Arrays (Interview Gold)
**How it works:** Use 2D boolean arrays to track seen digits
- `rows[i][num]`: digit num seen in row i?
- `cols[j][num]`: digit num seen in column j?
- `boxes[b][num]`: digit num seen in box b?

**When to use:** This is usually the best interview answer - clear, efficient, no string overhead

## Dry Run: Box Index Calculation

Let's trace through checking box (1,1) - the center box:

```
Board positions for center box:
┌─────┬─────┬─────┐
│(3,3)│(3,4)│(3,5)│
├─────┼─────┼─────┤
│(4,3)│(4,4)│(4,5)│
├─────┼─────┼─────┤
│(5,3)│(5,4)│(5,5)│
└─────┴─────┴─────┘

boxRow = 1, boxCol = 1

i=0, j=0: row = 1*3 + 0 = 3, col = 1*3 + 0 = 3 → (3,3) ✓
i=0, j=1: row = 1*3 + 0 = 3, col = 1*3 + 1 = 4 → (3,4) ✓
i=0, j=2: row = 1*3 + 0 = 3, col = 1*3 + 2 = 5 → (3,5) ✓
i=1, j=0: row = 1*3 + 1 = 4, col = 1*3 + 0 = 3 → (4,3) ✓
i=1, j=1: row = 1*3 + 1 = 4, col = 1*3 + 1 = 4 → (4,4) ✓
i=1, j=2: row = 1*3 + 1 = 4, col = 1*3 + 2 = 5 → (4,5) ✓
i=2, j=0: row = 1*3 + 2 = 5, col = 1*3 + 0 = 3 → (5,3) ✓
i=2, j=1: row = 1*3 + 2 = 5, col = 1*3 + 1 = 4 → (5,4) ✓
i=2, j=2: row = 1*3 + 2 = 5, col = 1*3 + 2 = 5 → (5,5) ✓
```

## Complexity Analysis

All three approaches:
- **Time:** O(1) — always checking exactly 81 cells
- **Space:** O(1) — at most 9 items in each HashSet/array

*Note: Technically O(1) because the input size is fixed (9x9), but we can also think of it as O(n²) for an n×n board*

## Common Pitfalls

1. **Using same variable for row and column** (your bug!)
   - Always use separate variables: `boxRow` with `i` for rows, `boxCol` with `j` for columns

2. **Off-by-one errors in box calculation**
   - Remember: integer division `i/3` gives box row/col (0, 1, or 2)
   - Formula: `boxIndex = (row / 3) * 3 + (col / 3)`

3. **Forgetting to skip '.' cells**
   - Always check `if (board[i][j] != '.')`

4. **Wrong HashSet scope**
   - Create new HashSet for EACH row/column/box, not shared!

5. **Array index confusion with char to int**
   - `char current = '5'`
   - `int num = current - '1'` → gives index 4 (for array 0-8)
   - NOT `current - '0'` which would give 5!

## Interview Strategy

**Start Simple:** "Let me first explain the three-pass approach..."
```java
// Validate rows
for (int i = 0; i < 9; i++) {
    Set<Character> seen = new HashSet<>();
    // check row i
}
// Similar for columns and boxes
```

**Then Optimize:** "We can actually do this in one pass..."
```java
boolean[][] rows = new boolean[9][9];
boolean[][] cols = new boolean[9][9];
boolean[][] boxes = new boolean[9][9];
// Check all three constraints simultaneously
```

**Key Points to Mention:**
- "The key insight is we have three separate constraints to validate"
- "Box indexing is the tricky part - we need to map 2D coordinates to box index"
- "I'll use boolean arrays for O(1) space with good cache locality"
- "Time is O(1) since we always check exactly 81 cells"

## Testing Edge Cases

```java
// All valid - empty board
char[][] valid = {{'.','.','.','.','.','.','.','.','.'}, ...};

// Invalid - duplicate '8' in first row
char[][] invalid1 = {{'8','3','.','.','7','.','.','.','.'},
                     {'6','.','.','1','9','5','.','.','.'},
                     // duplicate 8 at (0,0) and (3,0)
                     ...};

// Invalid - duplicate in box but different rows/cols
char[][] invalid2 = {{'5','3','.','.','.','.','.','.','.'}, 
                     {'6','.','.','1','9','5','.','.','.'},
                     {'.','9','5','.','.','.','.','.','.'}, // duplicate 5 in top-left box
                     ...};
```

## Matrix Problem Patterns (General)

1. **Row/Column Iteration**
   - Use nested loops: outer for rows `(i)`, inner for columns `(j)`
   - Access: `matrix[row][col]` or `matrix[i][j]`

2. **Sub-region Problems**
   - Divide matrix into blocks (like Sudoku boxes)
   - Calculate region index from coordinates
   - Formula pattern: `regionIndex = (row / regionSize) * numRegionsPerRow + (col / regionSize)`

3. **Using Sets vs Arrays for Tracking**
   - Sets: More flexible, handles any values
   - Arrays: Faster, but need fixed range (like digits 1-9)

4. **Coordinate Math**
   - Box to cell: `row = boxRow * size + localRow`
   - Cell to box: `boxRow = row / size`

## Related Problems

- **Matrix traversal:** spiral matrix, rotate image
- **Sub-matrix problems:** max sum rectangle, count square submatrices
- **Constraint validation:** N-Queens, word search
- **Block/region indexing:** game of life, image processing

---

**Remember:** The hardest part of Valid Sudoku is correctly implementing the 3x3 box validation. Practice the coordinate transformation until it becomes second nature!
