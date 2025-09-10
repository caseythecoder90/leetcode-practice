# 6. Zigzag Conversion

**Difficulty:** Medium  
**Topics:** String, Simulation, Pattern Recognition  

## Problem Statement

The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows and then read line by line.

Write a function to convert a string following this zigzag pattern:

```
Input: s = "PAYPALISHIRING", numRows = 3
Zigzag pattern:
P   A   H   N
A P L S I I G  
Y   I   R

Output: "PAHNAPLSIIGYIR" (read line by line)
```

## Visual Understanding

### Example 1: numRows = 3
```
PAYPALISHIRING → 

P   A   H   N      (Row 0: P, A, H, N)
A P L S I I G      (Row 1: A, P, L, S, I, I, G)
Y   I   R          (Row 2: Y, I, R)

Result: PAHNAPLSIIGYIR
```

### Example 2: numRows = 4  
```
PAYPALISHIRING →

P     I     N      (Row 0: P, I, N)
A   L S   I G      (Row 1: A, L, S, I, G)  
Y A   H R          (Row 2: Y, A, H, R)
P     I            (Row 3: P, I)

Result: PINALSIGYAHRPI
```

## Key Insights

### 1. Movement Pattern
The zigzag creates a **cyclic movement**:
- **Down**: Fill rows 0 → 1 → 2 → ... → (numRows-1)
- **Up**: Fill rows (numRows-1) → (numRows-2) → ... → 0
- **Repeat cycle**

### 2. Cycle Length Formula
```
cycleLength = 2 × numRows - 2

For numRows = 3: cycle = 2×3-2 = 4 → pattern: 0,1,2,1
For numRows = 4: cycle = 2×4-2 = 6 → pattern: 0,1,2,3,2,1
```

### 3. Direction Changes
Direction changes when we hit the **top or bottom boundary**:
```java
if (currentRow == 0 || currentRow == numRows - 1) {
    goingDown = !goingDown;
}
```

## Solution Approaches

### Approach 1: StringBuilder Array (Intuitive)

**Strategy**: Create a StringBuilder for each row, simulate the zigzag movement.

```java
public String convert(String s, int numRows) {
    if (numRows == 1 || s.length() <= numRows) return s;
    
    StringBuilder[] rows = new StringBuilder[numRows];
    for (int i = 0; i < numRows; i++) {
        rows[i] = new StringBuilder();
    }
    
    int currentRow = 0;
    boolean goingDown = false;
    
    for (char c : s.toCharArray()) {
        rows[currentRow].append(c);
        
        if (currentRow == 0 || currentRow == numRows - 1) {
            goingDown = !goingDown;
        }
        
        currentRow += goingDown ? 1 : -1;
    }
    
    StringBuilder result = new StringBuilder();
    for (StringBuilder row : rows) {
        result.append(row);
    }
    
    return result.toString();
}
```

**Time**: O(n), **Space**: O(n)

### Approach 2: Mathematical Pattern (Optimal)

**Strategy**: Calculate exact character positions without creating the zigzag structure.

**Key Insight**: Each row follows a mathematical pattern:
- **Row 0**: Characters at positions 0, cycleLength, 2×cycleLength, ...
- **Middle rows**: Two patterns - vertical drops and diagonal connections
- **Last row**: Similar to Row 0 but offset

```java
public String convertMath(String s, int numRows) {
    if (numRows == 1 || s.length() <= numRows) return s;
    
    StringBuilder result = new StringBuilder();
    int cycleLength = 2 * numRows - 2;
    
    for (int row = 0; row < numRows; row++) {
        for (int i = 0; i + row < s.length(); i += cycleLength) {
            // Vertical drop characters
            result.append(s.charAt(i + row));
            
            // Diagonal characters (only for middle rows)
            if (row != 0 && row != numRows - 1 && i + cycleLength - row < s.length()) {
                result.append(s.charAt(i + cycleLength - row));
            }
        }
    }
    
    return result.toString();
}
```

**Time**: O(n), **Space**: O(1) extra

## Step-by-Step Trace

### "PAYPALISHIRING", numRows = 3

#### Phase 1: Assign characters to rows
```
Index:  0 1 2 3 4 5 6 7 8 9 10 11 12 13
Char:   P A Y P A L I S H I R  I  N  G
Row:    0 1 2 1 0 1 2 1 0 1 2  1  0  1

Cycle pattern: 0→1→2→1 (length 4)
```

#### Phase 2: Collect by rows
```
Row 0: P (pos 0), A (pos 4), H (pos 8), N (pos 12) → "PAHN"  
Row 1: A (pos 1), P (pos 3), L (pos 5), S (pos 7), I (pos 9), I (pos 11), G (pos 13) → "APLSIIG"
Row 2: Y (pos 2), I (pos 6), R (pos 10) → "YIR"
```

#### Phase 3: Concatenate
```
Result: "PAHN" + "APLSIIG" + "YIR" = "PAHNAPLSIIGYIR"
```

## Pattern Recognition

### Mathematical Pattern for Each Row

For **numRows = 3**, **cycleLength = 4**:
- **Row 0**: Positions 0, 4, 8, 12, ... (every cycleLength)
- **Row 1**: Positions 1, 3, 5, 7, 9, 11, 13, ... (alternating pattern)  
- **Row 2**: Positions 2, 6, 10, ... (every cycleLength, offset by 2)

### General Formula
```
For row r in a cycle starting at position i:
- Vertical drop: position = i + r
- Diagonal (if middle row): position = i + cycleLength - r
```

## Edge Cases

### 1. Single Row (numRows = 1)
```
Input: "ABCD", numRows = 1
Output: "ABCD" (no transformation needed)
```

### 2. String shorter than numRows
```
Input: "ABC", numRows = 5
Zigzag:
A
B  
C

Output: "ABC" (vertical arrangement, read top to bottom)
```

### 3. Two rows (numRows = 2)
```
Input: "ABCD", numRows = 2
Zigzag:
A C
B D

Output: "ACBD"
```

## Complexity Analysis

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| StringBuilder Array | O(n) | O(n) | Most intuitive, easy to understand |
| Mathematical | O(n) | O(1) | More efficient space-wise |
| List-based | O(n) | O(n) | Alternative to StringBuilder |

Where n = length of input string.

## Common Mistakes

### 1. Incorrect Direction Logic
```java
// WRONG: Direction change logic
if (currentRow == 0) goingDown = true;
if (currentRow == numRows - 1) goingDown = false;

// RIGHT: Toggle at boundaries
if (currentRow == 0 || currentRow == numRows - 1) {
    goingDown = !goingDown;
}
```

### 2. Off-by-One in Mathematical Approach
```java
// WRONG: Missing bounds check
result.append(s.charAt(i + cycleLength - row)); // May be out of bounds

// RIGHT: Always check bounds
if (i + cycleLength - row < s.length()) {
    result.append(s.charAt(i + cycleLength - row));
}
```

### 3. Forgetting Edge Cases
```java
// Always handle single row and short strings
if (numRows == 1 || s.length() <= numRows) return s;
```

## Interview Tips

### 1. Start with Visualization
- Draw the pattern for given examples
- Identify the movement pattern
- Understand cycle length

### 2. Choose the Right Approach
- **StringBuilder Array**: Easier to explain and code
- **Mathematical**: More efficient but harder to derive

### 3. Walk Through Examples
- Trace through at least one example completely
- Show how direction changes work
- Verify edge cases

## Key Takeaways

1. **Pattern Recognition**: Zigzag creates a predictable cycle
2. **Boundary Conditions**: Direction changes at top/bottom rows  
3. **Mathematical Insight**: Can avoid simulation with position formulas
4. **Trade-offs**: Simplicity vs. space efficiency

The problem teaches important concepts about:
- **Simulation vs. mathematical approaches**
- **2D pattern recognition in 1D strings**  
- **Cycle detection and boundary handling**