# Rotate Image

**Difficulty**: Medium
**Topics**: Array, Matrix, Math
**LeetCode Link**: [48. Rotate Image](https://leetcode.com/problems/rotate-image/)

## Problem Description

You are given an `n x n` 2D matrix representing an image. Rotate the image by 90 degrees (clockwise).

You have to rotate the image **in-place**, which means you have to modify the input 2D matrix directly. **DO NOT** allocate another 2D matrix and do the rotation.

### Examples

**Example 1:**
```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [[7,4,1],[8,5,2],[9,6,3]]

Visual:
1 2 3       7 4 1
4 5 6  =>   8 5 2
7 8 9       9 6 3
```

**Example 2:**
```
Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

Visual:
5  1  9  11       15 13  2  5
2  4  8  10  =>   14  3  4  1
13 3  6   7       12  6  8  9
15 14 12 16       16  7 10 11
```

### Constraints
- `n == matrix.length == matrix[i].length`
- `1 <= n <= 20`
- `-1000 <= matrix[i][j] <= 1000`

## Approach

### Key Insight
Rotating a matrix 90 degrees clockwise can be achieved through two steps:
1. **Transpose** the matrix (swap rows and columns)
2. **Reverse** each row

### Why This Works

Consider the transformation for element at position `[i][j]`:
- After 90-degree clockwise rotation, `matrix[i][j]` goes to `matrix[j][n-1-i]`

This can be decomposed as:
1. **Transpose**: `matrix[i][j]` → `matrix[j][i]`
2. **Reverse rows**: `matrix[j][i]` → `matrix[j][n-1-i]`

### Visual Example
```
Original:        Transpose:       Reverse Rows:
1 2 3            1 4 7            7 4 1
4 5 6    =>      2 5 8     =>     8 5 2
7 8 9            3 6 9            9 6 3
```

### Algorithm Steps

1. **Transpose the matrix**:
   - Swap `matrix[i][j]` with `matrix[j][i]` for all `i < j`
   - This converts rows to columns

2. **Reverse each row**:
   - For each row, swap elements from start and end moving towards center
   - This completes the 90-degree rotation

### Alternative Approach: Layer-by-Layer Rotation

Instead of transpose + reverse, you can rotate elements in concentric layers:
- Process the matrix in layers (outer to inner)
- For each layer, rotate 4 elements at a time in a cycle
- More complex but achieves rotation in one pass

## Complexity Analysis

### Time Complexity: O(n²)
- Transpose: O(n²) - visit each element once
- Reverse rows: O(n²) - visit each element once
- Total: O(n²) where n is the dimension of the matrix

### Space Complexity: O(1)
- Rotation is done in-place
- Only constant extra space for swap operations

## Pattern Recognition

This problem demonstrates:
- **Matrix manipulation** pattern
- **In-place transformation** technique
- **Decomposition** strategy (complex operation → simpler steps)
- Understanding **coordinate transformations**

## Common Pitfalls

1. **Trying to allocate new matrix**: Problem explicitly forbids this
2. **Wrong transpose bounds**: Must use `i < j` to avoid double-swapping
3. **Incorrect rotation direction**: Transpose + reverse rows = clockwise; transpose + reverse columns = counter-clockwise
4. **Off-by-one errors** in reversing rows

## Related Problems

- **73. Set Matrix Zeroes** - In-place matrix modification
- **54. Spiral Matrix** - Matrix traversal patterns
- **240. Search a 2D Matrix II** - Matrix coordinate relationships
- **867. Transpose Matrix** - First step of this problem

## Key Takeaways

- Complex matrix operations can often be decomposed into simpler steps
- Understanding coordinate transformations is crucial
- In-place operations require careful consideration of modification order
- Visualizing the transformation helps identify the pattern
