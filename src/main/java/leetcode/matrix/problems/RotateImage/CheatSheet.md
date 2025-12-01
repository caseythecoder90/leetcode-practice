# Rotate Image - Quick Reference Cheat Sheet

## Problem Pattern
**Type**: Matrix Manipulation
**Technique**: In-place Transformation
**Key Insight**: Decompose 90° rotation into Transpose + Reverse

## Quick Recognition
✓ Square matrix (n × n)
✓ 90-degree rotation
✓ In-place requirement
✓ No extra space allowed
→ **Use Transpose + Reverse pattern**

## Core Algorithm Template

### Approach 1: Transpose + Reverse Rows (Recommended)

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;

    // Step 1: Transpose (swap across diagonal)
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {  // j starts at i+1
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }

    // Step 2: Reverse each row
    for (int i = 0; i < n; i++) {
        int left = 0, right = n - 1;
        while (left < right) {
            int temp = matrix[i][left];
            matrix[i][left] = matrix[i][right];
            matrix[i][right] = temp;
            left++;
            right--;
        }
    }
}
```

### Approach 2: Layer-by-Layer (Advanced)

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;

    for (int layer = 0; layer < n / 2; layer++) {
        int first = layer;
        int last = n - 1 - layer;

        for (int i = first; i < last; i++) {
            int offset = i - first;

            // Save top
            int top = matrix[first][i];

            // left → top
            matrix[first][i] = matrix[last - offset][first];

            // bottom → left
            matrix[last - offset][first] = matrix[last][last - offset];

            // right → bottom
            matrix[last][last - offset] = matrix[i][last];

            // top → right
            matrix[i][last] = top;
        }
    }
}
```

## Key Formulas

### Coordinate Transformations
```
90° Clockwise:       matrix[i][j] → matrix[j][n-1-i]
90° Counter-Clockwise: matrix[i][j] → matrix[n-1-j][i]
180° Rotation:       matrix[i][j] → matrix[n-1-i][n-1-j]
```

### Operation Decomposition
```
90° Clockwise  = Transpose + Reverse Rows
90° Counter    = Transpose + Reverse Columns
180° Rotation  = Reverse Rows + Reverse Columns
```

## Common Patterns

### Transpose Matrix
```java
// Swap matrix[i][j] with matrix[j][i]
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {  // Only upper triangle
        swap(matrix[i][j], matrix[j][i]);
    }
}
```

### Reverse Row
```java
void reverseRow(int[] row) {
    int left = 0, right = row.length - 1;
    while (left < right) {
        int temp = row[left];
        row[left] = row[right];
        row[right] = temp;
        left++;
        right--;
    }
}
```

### Reverse Column
```java
void reverseColumn(int[][] matrix, int col) {
    int top = 0, bottom = matrix.length - 1;
    while (top < bottom) {
        int temp = matrix[top][col];
        matrix[top][col] = matrix[bottom][col];
        matrix[bottom][col] = temp;
        top++;
        bottom--;
    }
}
```

## Visual Quick Reference

```
Original → Transpose → Reverse Rows → Final
1 2 3      1 4 7        7 4 1          7 4 1
4 5 6  →   2 5 8   →    8 5 2     =    8 5 2
7 8 9      3 6 9        9 6 3          9 6 3
```

## Complexity Cheat Sheet

| Approach | Time | Space | Difficulty |
|----------|------|-------|------------|
| Transpose + Reverse | O(n²) | O(1) | Easy to code |
| Layer-by-Layer | O(n²) | O(1) | Moderate |

## Edge Cases Checklist

- [ ] n = 1 (single element)
- [ ] n = 2 (minimal rotation)
- [ ] Negative numbers
- [ ] All same values
- [ ] Large values (within constraints)

## Common Mistakes

❌ **Using j = 0 in transpose** (causes double-swap)
```java
for (int j = 0; j < n; j++)  // WRONG!
```
✅ **Correct: Start from i+1**
```java
for (int j = i + 1; j < n; j++)  // RIGHT!
```

❌ **Reversing columns for clockwise**
```java
reverseColumns();  // This gives counter-clockwise!
```
✅ **Correct: Reverse rows for clockwise**
```java
reverseRows();  // Clockwise rotation
```

❌ **Including equality in reverse bounds**
```java
while (left <= right)  // Swaps middle with itself
```
✅ **Correct: Use strict inequality**
```java
while (left < right)  // Stops before middle
```

## Time-Saving Tricks

### Quick Transpose Check
After transpose, first row should equal first column of original.

### Quick Rotation Verification
```
Original[0][0] → Rotated[0][n-1]
Original[0][n-1] → Rotated[n-1][n-1]
Original[n-1][0] → Rotated[0][0]
Original[n-1][n-1] → Rotated[n-1][0]
```

## Code Snippets for Interview

### Helper: Swap Two Elements
```java
private void swap(int[][] matrix, int r1, int c1, int r2, int c2) {
    int temp = matrix[r1][c1];
    matrix[r1][c1] = matrix[r2][c2];
    matrix[r2][c2] = temp;
}
```

### Helper: Print Matrix (for debugging)
```java
private void printMatrix(int[][] matrix) {
    for (int[] row : matrix) {
        System.out.println(Arrays.toString(row));
    }
}
```

## Interview Talking Points

1. **"I'll decompose 90° rotation into transpose and reverse"**
   - Shows you understand the mathematical transformation

2. **"We need j = i+1 to avoid double-swapping"**
   - Demonstrates attention to implementation details

3. **"Time O(n²) is optimal since we must touch every element"**
   - Shows complexity analysis understanding

4. **"Layer approach is more complex but interesting to discuss"**
   - Offers alternative if interviewer wants to explore

## Related LeetCode Problems

- **73. Set Matrix Zeroes** (in-place matrix modification)
- **54. Spiral Matrix** (matrix traversal)
- **867. Transpose Matrix** (subset of this problem)
- **498. Diagonal Traverse** (matrix patterns)

## One-Liner Summary

> "Transpose the matrix (swap across diagonal), then reverse each row to achieve 90° clockwise rotation in O(n²) time and O(1) space."

## Quick Mental Model

```
Think of it like:
1. Flip the matrix along the diagonal (transpose)
2. Flip each row horizontally (reverse)
= 90° clockwise rotation!
```
