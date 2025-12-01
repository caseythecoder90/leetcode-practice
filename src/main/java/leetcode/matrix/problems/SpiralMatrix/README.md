# Spiral Matrix

**LeetCode Problem:** [54. Spiral Matrix](https://leetcode.com/problems/spiral-matrix/)

**Difficulty:** Medium

## Problem Description

Given an `m x n` matrix, return all elements of the matrix in spiral order.

### Examples

**Example 1:**
```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]

Visualization:
1 → 2 → 3
        ↓
4 → 5   6
↑       ↓
7 ← 8 ← 9
```

**Example 2:**
```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]

Visualization:
1  →  2  →  3  →  4
                  ↓
5  →  6  →  7     8
↑                 ↓
9  ← 10 ← 11 ← 12
```

### Constraints

- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 10`
- `-100 <= matrix[i][j] <= 100`

## Approach

### Strategy: Layer-by-Layer Traversal with Boundary Shrinking

Think of the matrix like an onion - we peel off one layer at a time, moving from the outermost layer toward the center.

**Key Insight:** Each layer is traversed in a fixed pattern:
1. **Right:** Traverse top row from left to right
2. **Down:** Traverse right column from top to bottom
3. **Left:** Traverse bottom row from right to left
4. **Up:** Traverse left column from bottom to top

After completing each layer, we shrink the boundaries and repeat.

### Algorithm Steps

1. Initialize four boundaries:
   - `top` = 0 (top boundary)
   - `bottom` = rows - 1 (bottom boundary)
   - `left` = 0 (left boundary)
   - `right` = cols - 1 (right boundary)

2. While boundaries haven't crossed (`top <= bottom && left <= right`):
   - Traverse **right**: from `left` to `right` along `top` row, then move `top` down
   - Traverse **down**: from `top` to `bottom` along `right` column, then move `right` left
   - Traverse **left**: from `right` to `left` along `bottom` row (if valid), then move `bottom` up
   - Traverse **up**: from `bottom` to `top` along `left` column (if valid), then move `left` right

3. Continue until all elements are visited

### Critical Edge Cases

**Single Row or Column:**
- When processing the last layer, we might have only a row or only a column left
- Must check `top <= bottom` before traversing left
- Must check `left <= right` before traversing up
- Otherwise, we'll add the same elements twice!

### Complexity Analysis

- **Time Complexity:** O(m × n)
  - We visit each element exactly once
  - m = number of rows, n = number of columns

- **Space Complexity:** O(1)
  - Only using a fixed number of variables for boundaries
  - Output array doesn't count toward space complexity

## Alternative Approach: Direction-Based Traversal

Instead of tracking boundaries, we can:
1. Use direction vectors: `[(0,1), (1,0), (0,-1), (-1,0)]` for right, down, left, up
2. Keep a `visited` array to mark processed cells
3. Move in current direction until hitting a boundary or visited cell
4. Turn clockwise (next direction) when blocked

**Trade-off:** More intuitive for some, but requires O(m × n) extra space for the visited array.

## Common Pitfalls

1. **Forgetting boundary checks:** Not checking if `top <= bottom` before left traversal or `left <= right` before up traversal leads to duplicate elements
2. **Incorrect boundary updates:** Forgetting to increment/decrement boundaries after each traversal
3. **Off-by-one errors:** Using `<` instead of `<=` in loop conditions
4. **Wrong traversal order:** Not following the exact right → down → left → up pattern

## When to Use This Pattern

- Any problem involving spiral or clockwise/counter-clockwise matrix traversal
- Matrix rotation problems
- Matrix layer manipulation
- Problems requiring boundary-shrinking technique

## Related Problems

- **Spiral Matrix II (LeetCode 59):** Generate a spiral matrix given n
- **Rotate Image (LeetCode 48):** Rotate matrix 90 degrees
- **Diagonal Traverse (LeetCode 498):** Traverse matrix diagonally
- **Matrix Diagonal Sum (LeetCode 1572):** Sum of diagonal elements

## Key Takeaways

- Boundary management is crucial for spiral traversal
- Always check if boundaries are valid before traversing opposite direction
- The pattern is: process layer → shrink boundaries → repeat
- Edge cases: single row, single column, single element - all handled by boundary checks
