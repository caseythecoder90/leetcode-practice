# Rotate Image - Detailed Study Guide

## Problem Understanding

**Goal**: Rotate an n×n matrix 90 degrees clockwise **in-place**

**Key Constraints**:
- Must modify the original matrix (no extra matrix allowed)
- Must be 90 degrees clockwise
- Matrix is always square (n×n)

## Conceptual Understanding

### What Does 90° Clockwise Rotation Mean?

```
Position mapping after rotation:
matrix[i][j] → matrix[j][n-1-i]

Examples with 3×3 matrix (n=3):
[0,0] → [0,2]    (first row, first col → first row, last col)
[0,1] → [1,2]    (first row, middle col → middle row, last col)
[0,2] → [2,2]    (first row, last col → last row, last col)
[1,0] → [0,1]    (middle row, first col → first row, middle col)
[2,0] → [0,0]    (last row, first col → first row, first col)
```

### Visual Transformation

```
Indices:                After 90° rotation:
[0,0] [0,1] [0,2]       [2,0] [1,0] [0,0]
[1,0] [1,1] [1,2]   =>  [2,1] [1,1] [0,1]
[2,0] [2,1] [2,2]       [2,2] [1,2] [0,2]

With values:
1 2 3                    7 4 1
4 5 6        =>          8 5 2
7 8 9                    9 6 3
```

## Solution Approach: Transpose + Reverse

### Why This Works

The transformation `matrix[i][j] → matrix[j][n-1-i]` can be decomposed:

1. **Transpose**: `matrix[i][j] → matrix[j][i]`
2. **Reverse each row**: `matrix[j][i] → matrix[j][n-1-i]`

### Step-by-Step Execution Trace

Let's trace through Example 1: `[[1,2,3],[4,5,6],[7,8,9]]`

#### Initial State
```
matrix =
1 2 3
4 5 6
7 8 9

n = 3
```

#### Phase 1: Transpose the Matrix

**Goal**: Swap `matrix[i][j]` with `matrix[j][i]` for all `i < j`

```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        swap(matrix[i][j], matrix[j][i]);
    }
}
```

**Iteration Details**:

```
i=0, j=1: Swap matrix[0][1] (2) with matrix[1][0] (4)
1 4 3
2 5 6
7 8 9

i=0, j=2: Swap matrix[0][2] (3) with matrix[2][0] (7)
1 4 7
2 5 6
3 8 9

i=1, j=2: Swap matrix[1][2] (6) with matrix[2][1] (8)
1 4 7
2 5 8
3 6 9

After transpose:
1 4 7
2 5 8
3 6 9
```

**Key Points**:
- We only iterate where `j > i` to avoid swapping twice
- The diagonal elements `[0,0], [1,1], [2,2]` stay in place
- Rows become columns, columns become rows

#### Phase 2: Reverse Each Row

**Goal**: Reverse each row to complete the rotation

```java
for (int i = 0; i < n; i++) {
    reverseRow(matrix[i]);
}
```

**Row 0**: `[1, 4, 7]` → `[7, 4, 1]`
```
left=0, right=2: swap(1, 7) → [7, 4, 1]
left=1, right=1: stop (left >= right)
```

**Row 1**: `[2, 5, 8]` → `[8, 5, 2]`
```
left=0, right=2: swap(2, 8) → [8, 5, 2]
left=1, right=1: stop
```

**Row 2**: `[3, 6, 9]` → `[9, 6, 3]`
```
left=0, right=2: swap(3, 9) → [9, 6, 3]
left=1, right=1: stop
```

**Final Result**:
```
7 4 1
8 5 2
9 6 3
```

## Detailed Example 2: 4×4 Matrix

Input: `[[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]`

### Phase 1: Transpose

```
Original:           After Transpose:
5  1  9  11         5  2  13 15
2  4  8  10   =>    1  4  3  14
13 3  6  7          9  8  6  12
15 14 12 16         11 10 7  16
```

**Swap sequence**:
```
[0,1]↔[1,0]: 1↔2    [0,2]↔[2,0]: 9↔13   [0,3]↔[3,0]: 11↔15
[1,2]↔[2,1]: 8↔3    [1,3]↔[3,1]: 10↔14
[2,3]↔[3,2]: 7↔12
```

### Phase 2: Reverse Each Row

```
After Transpose:    After Reverse:
5  2  13 15         15 13 2  5
1  4  3  14   =>    14 3  4  1
9  8  6  12         12 6  8  9
11 10 7  16         16 7  10 11
```

## Alternative Approach: Layer-by-Layer Rotation

### Concept

Rotate elements in concentric layers, moving 4 elements at a time in a cycle.

```
Layer 0 (outer):     Layer 1 (inner):
* * * *              - - - -
* - - *              - * * -
* - - *              - * * -
* * * *              - - - -
```

### How It Works

For each layer, rotate groups of 4 elements:
```
    top
     ↓
left→ * → right
     ↑
  bottom

Cycle: top → temp
       left → top
       bottom → left
       right → bottom
       temp → right
```

### Execution Trace for 3×3

```
Layer 0 (outer layer):
Elements to rotate: [0,0] [0,1] [0,2] [1,2] [2,2] [2,1] [2,0] [1,0]

Iteration 1 (i=0, offset=0):
  Rotate [0,0], [0,2], [2,2], [2,0]
  1→temp, 7→[0,0], 9→[2,0], 3→[2,2], temp→[0,2]
  Result: 7,2,3 / 4,5,6 / 9,8,1

Iteration 2 (i=1, offset=1):
  Rotate [0,1], [1,2], [2,1], [1,0]
  2→temp, 4→[0,1], 8→[1,0], 6→[2,1], temp→[1,2]
  Result: 7,4,3 / 8,5,2 / 9,6,1

After rotating corners: Still need final adjustment...
```

Actually, the layer approach is more complex. Let me show the correct trace:

```
Layer 0: first=0, last=2

i=0, offset=0:
  top = matrix[0][0] = 1
  [0,0] = [2,0] = 7
  [2,0] = [2,2] = 9
  [2,2] = [0,2] = 3
  [0,2] = top = 1

i=1, offset=1:
  top = matrix[0][1] = 2
  [0,1] = [1,0] = 4
  [1,0] = [2,1] = 8
  [2,1] = [1,2] = 6
  [1,2] = top = 2

After processing all elements: [7,4,1],[8,5,2],[9,6,3]
```

## Common Mistakes and How to Avoid Them

### Mistake 1: Double-swapping during transpose
```java
❌ WRONG:
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {  // Swaps twice!
        swap(matrix[i][j], matrix[j][i]);
    }
}

✅ CORRECT:
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {  // Only upper triangle
        swap(matrix[i][j], matrix[j][i]);
    }
}
```

### Mistake 2: Reversing columns instead of rows
```java
❌ WRONG (90° counter-clockwise):
transpose();
reverseColumns();  // Wrong!

✅ CORRECT (90° clockwise):
transpose();
reverseRows();
```

### Mistake 3: Off-by-one in reverse logic
```java
❌ WRONG:
while (left <= right) {  // Swaps middle element with itself
    swap(row[left++], row[right--]);
}

✅ CORRECT:
while (left < right) {  // Stops before middle
    swap(row[left++], row[right--]);
}
```

## Complexity Deep Dive

### Time Complexity: O(n²)

**Transpose phase**:
- Visits `n(n-1)/2` elements (upper triangle)
- Each swap is O(1)
- Total: O(n²)

**Reverse phase**:
- n rows, each with n elements
- Each row reversal: O(n)
- Total: O(n²)

**Combined**: O(n²) + O(n²) = O(n²)

### Space Complexity: O(1)

- Only uses temporary variables for swapping
- No additional data structures
- Truly in-place

## Pattern Recognition

This problem demonstrates:
1. **Decomposition**: Complex operation → simpler steps
2. **In-place algorithms**: Careful ordering to avoid overwriting needed data
3. **Matrix transformations**: Understanding coordinate mappings
4. **Two-pointer technique**: Used in row reversal

## Interview Tips

1. **Clarify rotation direction**: Clockwise vs counter-clockwise
2. **Ask about constraints**: Can we use extra space?
3. **Start with small example**: 2×2 or 3×3 to understand pattern
4. **Draw it out**: Visual representation helps tremendously
5. **Explain why transpose + reverse works**: Shows deep understanding

## Related Transformations

```
90° Clockwise:  Transpose + Reverse Rows
90° Counter:    Transpose + Reverse Columns
180° Rotation:  Reverse Rows + Reverse Columns
Horizontal Flip: Reverse Columns
Vertical Flip:   Reverse Rows
```

## Practice Variations

1. Rotate counter-clockwise
2. Rotate 180 degrees
3. Rotate rectangular (non-square) matrix with extra space
4. Rotate by arbitrary angles (requires different approach)

## Key Takeaways

- **Transpose + Reverse is the simplest approach** for 90° rotation
- **Layer-by-layer is more complex** but demonstrates advanced technique
- **In-place transformations require careful ordering** of operations
- **Visual understanding is crucial** for matrix problems
- **Coordinate mapping formula** helps verify correctness
