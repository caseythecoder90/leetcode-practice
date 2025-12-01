# Valid Sudoku - Flashcards

## Core Concept

**Q: What are the three validation rules for Valid Sudoku?**
A: 
1. Each row must contain digits 1-9 without repetition
2. Each column must contain digits 1-9 without repetition  
3. Each 3x3 sub-box must contain digits 1-9 without repetition
(Empty cells '.' are ignored)

---

## Box Indexing

**Q: How do you calculate which 3x3 box a cell (row, col) belongs to?**
A: `boxIndex = (row / 3) * 3 + (col / 3)`
- `row / 3` gives the box row (0, 1, or 2)
- `col / 3` gives the box column (0, 1, or 2)
- Multiply box row by 3 and add box column to get linear index 0-8

**Q: How do you convert from box coordinates to cell coordinates?**
A: Given `boxRow`, `boxCol`, and local positions `i`, `j` (0-2):
- `row = boxRow * 3 + i`
- `col = boxCol * 3 + j`

**Q: What cells belong to box (1, 1) - the center box?**
A: Rows 3-5, Columns 3-5
- Formula: boxRow=1, boxCol=1
- Cells: (3,3) through (5,5)

---

## Common Bugs

**Q: What's wrong with this box validation code?**
```java
int row = boxRow * 3 + i;
int col = boxRow * 3 + i;  // Bug!
```
A: The column calculation uses `boxRow` and `i` instead of `boxCol` and `j`. This only checks diagonal cells! Should be: `col = boxCol * 3 + j`

**Q: Why can't we share a single HashSet across all rows?**
A: Each row needs its own HashSet to detect duplicates WITHIN that row. A shared set would detect duplicates ACROSS rows, which is incorrect. Reset the set for each new row/column/box.

**Q: When converting char digit to array index (0-8), what's the formula?**
A: `int index = currentChar - '1'`
- '1' → 0, '2' → 1, ..., '9' → 8
- NOT `currentChar - '0'` which would give 1-9!

---

## Implementation Approaches

**Q: What are the three main approaches to solve Valid Sudoku?**
A:
1. **Three-Pass:** Validate rows, then columns, then boxes separately (most intuitive)
2. **One-Pass with String Encoding:** Use unique strings like "5 in row 2" in a single HashSet
3. **Boolean Arrays:** Use 2D arrays `rows[9][9]`, `cols[9][9]`, `boxes[9][9]` (best for interviews)

**Q: Why is the boolean array approach often preferred in interviews?**
A: 
- Clear and easy to understand
- No string creation overhead
- Better cache locality than HashSet
- Still O(1) space complexity
- Shows understanding of memory-efficient tracking

**Q: In the one-pass approach, how many strings do we store per non-empty cell?**
A: Three strings:
1. One for row validation: `"5 in row 2"`
2. One for column validation: `"5 in col 4"`  
3. One for box validation: `"5 in box 1-1"`

---

## Complexity

**Q: What's the time complexity of Valid Sudoku?**
A: O(1) - we always check exactly 81 cells (9×9 board)
*Alternative answer for n×n board: O(n²)*

**Q: What's the space complexity?**
A: O(1) - at most 9 elements in each HashSet or boolean array
- Three approaches use either:
  - HashSets: max 9 elements each
  - Boolean arrays: 9×9 = 81 booleans total
  - Single HashSet: max 27 strings (9 rows + 9 cols + 9 boxes)

**Q: Why is this considered O(1) space even with HashSets?**
A: The board size is fixed at 9×9, so the maximum size of our data structures is bounded by a constant (81 cells, digits 1-9). The space doesn't grow with input size.

---

## Algorithm Pattern

**Q: What's the general pattern for the three-pass solution?**
A:
```java
// 1. Check all rows
for (int i = 0; i < 9; i++) {
    Set<Character> seen = new HashSet<>();
    for (int j = 0; j < 9; j++) {
        // validate board[i][j]
    }
}
// 2. Check all columns (swap i and j)
// 3. Check all boxes (nested box loops + cell loops)
```

**Q: What's the general pattern for the boolean array solution?**
A:
```java
boolean[][] rows = new boolean[9][9];
boolean[][] cols = new boolean[9][9];
boolean[][] boxes = new boolean[9][9];

for (int i = 0; i < 9; i++) {
    for (int j = 0; j < 9; j++) {
        if (board[i][j] != '.') {
            int num = board[i][j] - '1';
            int boxIdx = (i/3)*3 + (j/3);
            
            if (rows[i][num] || cols[j][num] || boxes[boxIdx][num])
                return false;
                
            rows[i][num] = cols[j][num] = boxes[boxIdx][num] = true;
        }
    }
}
```

---

## Matrix Fundamentals

**Q: How do you iterate through all cells in a matrix?**
A:
```java
for (int row = 0; row < matrix.length; row++) {
    for (int col = 0; col < matrix[0].length; col++) {
        // process matrix[row][col]
    }
}
```

**Q: How do you iterate through a specific row?**
A:
```java
int targetRow = 2;
for (int col = 0; col < matrix[targetRow].length; col++) {
    // process matrix[targetRow][col]
}
```

**Q: How do you iterate through a specific column?**
A:
```java
int targetCol = 3;
for (int row = 0; row < matrix.length; row++) {
    // process matrix[row][targetCol]
}
```

---

## Edge Cases

**Q: What edge cases should you test for Valid Sudoku?**
A:
1. Completely empty board (all '.') - should be valid
2. Duplicate in same row - should be invalid
3. Duplicate in same column - should be invalid
4. Duplicate in same box but different rows/columns - should be invalid
5. Board with only one number - should be valid
6. Valid partial solution - should be valid

**Q: Do we need to check if the Sudoku is solvable?**
A: No! We only validate the current state. A valid board may not have a solution, and that's okay.

---

## Interview Tips

**Q: How should you explain your approach in an interview?**
A:
1. Start by clarifying the three constraints (rows, columns, boxes)
2. Explain the box indexing formula clearly
3. Choose the boolean array approach for best clarity
4. Walk through how you'll track seen digits
5. Mention O(1) time and space due to fixed board size

**Q: What should you mention about the box validation?**
A: "The tricky part is the 3x3 boxes. I'll calculate which box each cell belongs to using `(row/3)*3 + (col/3)`, which maps cells to boxes numbered 0-8."

**Q: What's a good way to test your solution mentally?**
A: Pick a cell like (4, 7) and trace:
- Row check: is 4 checking row 4? ✓
- Column check: is 7 checking column 7? ✓  
- Box check: (4/3)*3 + (7/3) = 1*3 + 2 = 5 (middle-right box) ✓

---

## Related Patterns

**Q: What other problems use similar sub-region indexing?**
A:
- Image processing (blocks/tiles)
- Game of Life (neighborhoods)  
- Matrix region queries
- Chess board problems
- Any grid divided into regular sub-grids

**Q: When should you use HashSet vs boolean array for tracking?**
A:
- **HashSet:** When values are unbounded or sparse (like in general problems)
- **Boolean array:** When values are in a small, known range (like digits 1-9)
- Boolean arrays are typically faster and more memory-efficient for fixed ranges

---

## Quick Reference

**Q: What's the complete formula for the boolean array approach?**
A:
```java
// Setup
boolean[][] rows = new boolean[9][9];
boolean[][] cols = new boolean[9][9];  
boolean[][] boxes = new boolean[9][9];

// For each cell (i,j) with value v (as char):
int num = v - '1';  // Convert to 0-8 index
int boxIdx = (i / 3) * 3 + (j / 3);

// Check and mark
if (rows[i][num] || cols[j][num] || boxes[boxIdx][num])
    return false;
rows[i][num] = cols[j][num] = boxes[boxIdx][num] = true;
```

**Q: Draw the box numbering scheme:**
A:
```
Sudoku Board (9x9):
┌─────┬─────┬─────┐
│  0  │  1  │  2  │
├─────┼─────┼─────┤
│  3  │  4  │  5  │
├─────┼─────┼─────┤
│  6  │  7  │  8  │
└─────┴─────┴─────┘

Row/Col ranges for each box:
Box 0: rows 0-2, cols 0-2
Box 4: rows 3-5, cols 3-5  
Box 8: rows 6-8, cols 6-8
Formula: box = (row/3)*3 + (col/3)
```
