# Rotate Image - Flash Cards

## Card 1: Problem Identification
**Q: How do you recognize the Rotate Image pattern?**

A: Look for these signals:
- Square matrix (n × n)
- 90-degree rotation requirement
- In-place constraint (can't allocate new matrix)
- Matrix transformation problem

---

## Card 2: Core Algorithm
**Q: What are the two main approaches to rotate a matrix 90° clockwise in-place?**

A:
1. **Transpose + Reverse Rows** (simpler, recommended)
   - Transpose the matrix (swap across diagonal)
   - Reverse each row

2. **Layer-by-Layer Rotation** (advanced)
   - Rotate elements in concentric layers
   - Move 4 elements at a time in a cycle

---

## Card 3: Transpose Operation
**Q: How do you transpose a matrix in-place? What's the critical detail?**

A:
```java
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {  // Start j at i+1!
        swap(matrix[i][j], matrix[j][i]);
    }
}
```
**Critical**: Start `j` at `i+1` to only swap upper triangle, avoiding double-swapping.

---

## Card 4: Coordinate Transformation
**Q: After 90° clockwise rotation, where does element matrix[i][j] end up?**

A: `matrix[i][j]` → `matrix[j][n-1-i]`

Examples (n=3):
- [0,0] → [0,2] (top-left → top-right)
- [0,2] → [2,2] (top-right → bottom-right)
- [2,0] → [0,0] (bottom-left → top-left)

---

## Card 5: Why Transpose + Reverse Works
**Q: Explain mathematically why transpose + reverse rows = 90° clockwise rotation.**

A:
Breaking down the transformation:
- **Target**: `matrix[i][j] → matrix[j][n-1-i]`
- **Transpose**: `matrix[i][j] → matrix[j][i]`
- **Reverse row**: `matrix[j][i] → matrix[j][n-1-i]`
- **Combined**: Transpose + Reverse = Desired transformation ✓

---

## Card 6: Reverse Row Implementation
**Q: Write the code to reverse a single row in-place.**

A:
```java
void reverseRow(int[] row) {
    int left = 0, right = row.length - 1;
    while (left < right) {  // Not <=
        int temp = row[left];
        row[left] = row[right];
        row[right] = temp;
        left++;
        right--;
    }
}
```

---

## Card 7: Time Complexity
**Q: What's the time complexity and why is it optimal?**

A:
- **Time**: O(n²)
  - Transpose: O(n²) - touch half the elements
  - Reverse: O(n²) - touch all elements
- **Why optimal**: Must touch every element at least once to rotate, so O(n²) is the best possible for an n×n matrix.

---

## Card 8: Space Complexity
**Q: What's the space complexity and why?**

A:
- **Space**: O(1)
- Only uses a constant amount of extra space for temporary swap variables
- No additional data structures needed
- Rotation happens in-place

---

## Card 9: Common Mistake #1
**Q: What happens if you use `for (int j = 0; j < n; j++)` during transpose?**

A:
**Mistake**: Each element gets swapped twice, returning to original position!
```
[1,2]  swap once   [1,4]  swap again  [1,2]
[4,5]     →        [2,5]      →       [4,5]
```
**Fix**: Use `j = i + 1` to swap only upper triangle.

---

## Card 10: Common Mistake #2
**Q: What's the difference between reversing rows vs columns after transpose?**

A:
- **Transpose + Reverse Rows** = 90° **clockwise**
- **Transpose + Reverse Columns** = 90° **counter-clockwise**

Visual:
```
After transpose:    Reverse rows:      Reverse cols:
1 4 7               7 4 1              3 6 9
2 5 8       →       8 5 2       vs     2 5 8
3 6 9               9 6 3              1 4 7
```

---

## Card 11: Edge Cases
**Q: What edge cases should you test for Rotate Image?**

A:
1. **n = 1**: Single element (no rotation needed)
2. **n = 2**: Minimal rotation (4 elements)
3. **Negative numbers**: Ensure logic handles all integers
4. **All same values**: Verify structure changes correctly
5. **Large n**: Test performance (n up to 20)

---

## Card 12: Layer-by-Layer Concept
**Q: How does the layer-by-layer approach work?**

A:
Process matrix in concentric layers from outside to inside:
```
Layer 0:    Layer 1:
* * * *     - - - -
* - - *     - * * -
* - - *     - * * -
* * * *     - - - -
```
For each layer, rotate groups of 4 elements in cycles.
Number of layers = `n / 2`

---

## Card 13: Layer Iteration Formula
**Q: In layer-by-layer rotation, what are the formulas for first and last indices?**

A:
```java
for (int layer = 0; layer < n / 2; layer++) {
    int first = layer;          // Start of layer
    int last = n - 1 - layer;   // End of layer

    for (int i = first; i < last; i++) {
        int offset = i - first;
        // Rotate 4 elements...
    }
}
```

---

## Card 14: Different Rotation Types
**Q: How would you implement different types of rotations?**

A:
```
90° Clockwise:     transpose() + reverseRows()
90° Counter-CW:    transpose() + reverseCols()
180° Rotation:     reverseRows() + reverseCols()
Horizontal Flip:   reverseCols()
Vertical Flip:     reverseRows()
```

---

## Card 15: Interview Approach
**Q: What should you say/do when given this problem in an interview?**

A:
1. **Clarify**: "Confirm it's 90° clockwise and in-place"
2. **Example**: "Let me trace a 3×3 example"
3. **Insight**: "I can decompose rotation into transpose + reverse"
4. **Complexity**: "This will be O(n²) time, O(1) space"
5. **Code**: Implement transpose + reverse approach
6. **Optimize**: "Could also do layer-by-layer if you'd like to see"

---

## Card 16: Verification Strategy
**Q: How can you quickly verify your rotation is correct?**

A:
Check corner transformations:
```
Before:              After:
TL  -   -  TR        BL  -   -  TL
-   -   -  -         -   -   -  -
-   -   -  -    =>   -   -   -  -
BL  -   -  BR        BR  -   -  TR
```
If corners map correctly, rotation is likely correct.

---

## Card 17: Pattern Connection
**Q: What other matrix patterns are related to Rotate Image?**

A:
- **Transpose**: Core sub-operation of rotation
- **Spiral Matrix**: Different traversal pattern
- **Set Matrix Zeroes**: In-place matrix modification
- **Search 2D Matrix**: Understanding matrix coordinates
- **Diagonal Traverse**: Coordinate relationships

---

## Card 18: Debugging Tips
**Q: Your rotation isn't working. What should you check?**

A:
1. **Transpose loop**: Is `j` starting at `i+1`? (Not 0)
2. **Reverse direction**: Reversing rows for clockwise? (Not columns)
3. **Reverse bounds**: Using `left < right`? (Not <=)
4. **Index arithmetic**: Check `n-1-i` calculations
5. **Print intermediate**: Print after transpose, after reverse

---

## Card 19: Real-World Applications
**Q: Where is matrix rotation used in real applications?**

A:
- **Image processing**: Rotating photos/images
- **Game development**: Rotating game boards (Tetris, 2048)
- **Computer graphics**: Texture transformations
- **Data visualization**: Chart orientation changes
- **Document processing**: PDF/image rotation

---

## Card 20: Quick Mental Model
**Q: What's a simple way to remember the transpose + reverse approach?**

A:
**Mental model**: "Flip diagonally, then flip horizontally"
1. Flip along diagonal (↘) = transpose
2. Flip left-right (↔) = reverse rows
3. Result = 90° clockwise rotation (↻)

Visual mnemonic: Imagine rotating a physical piece of paper.

---

## Card 21: Code Template (Must Memorize)
**Q: Write the complete rotate function from memory.**

A:
```java
public void rotate(int[][] matrix) {
    int n = matrix.length;

    // Transpose
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }

    // Reverse rows
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

---

## Card 22: Follow-up Questions
**Q: What follow-up questions might an interviewer ask?**

A:
1. "Can you rotate counter-clockwise?" → Reverse columns instead
2. "What about 180°?" → Reverse rows + reverse columns
3. "What if it's rectangular?" → Can't do in-place, need new matrix
4. "Can you rotate by arbitrary angles?" → Need different algorithm (geometry/interpolation)
5. "Optimize for cache?" → Layer approach has better locality

---

## Card 23: Counter-Clockwise Modification
**Q: How do you modify the solution for counter-clockwise rotation?**

A:
**Only change**: Reverse columns instead of rows after transpose
```java
// After transpose, reverse columns instead:
for (int j = 0; j < n; j++) {
    int top = 0, bottom = n - 1;
    while (top < bottom) {
        swap(matrix[top][j], matrix[bottom][j]);
        top++;
        bottom--;
    }
}
```

---

## Card 24: Key Insight Summary
**Q: What's the ONE key insight that makes this problem easy?**

A:
**"Complex transformations can be decomposed into simpler operations."**

90° rotation seems complex, but it's just:
- Simple operation 1: Transpose (swap [i][j] ↔ [j][i])
- Simple operation 2: Reverse rows (reverse each array)

Together, they achieve the complex rotation. This decomposition principle applies to many matrix problems.

---

## Review Schedule

- **Day 1**: Cards 1-8 (Basics & Core Algorithm)
- **Day 2**: Cards 9-16 (Mistakes & Verification)
- **Day 3**: Cards 17-24 (Advanced & Interview)
- **Day 7**: All cards (Full review)
- **Day 14**: Code template from memory (Card 21)
- **Day 30**: Full problem from scratch

---

## Quick Quiz

Before your interview, can you answer these in under 30 seconds each?

1. ✓ Time and space complexity?
2. ✓ Why j = i+1 in transpose?
3. ✓ Draw 3×3 rotation by hand
4. ✓ Transpose + reverse rows vs columns?
5. ✓ Code the rotate function from memory

If yes to all → You're ready! 🚀
