# Zigzag Conversion - Cheat Sheet

## Problem Pattern: String Simulation + Pattern Recognition

### Quick Recognition Signs
- "Zigzag pattern" or "written in rows"
- Need to rearrange string based on 2D layout
- Read "line by line" after arrangement
- Given number of rows parameter

## Core Pattern Analysis

### The Zigzag Movement
```
Direction alternates: Down → Up → Down → Up...
Change direction when hitting top (row 0) or bottom (row numRows-1)

Example with 3 rows:
0 → 1 → 2 → 1 → 0 → 1 → 2 → 1 → 0 ...
```

### Mathematical Formula
```java
cycleLength = 2 × numRows - 2

For numRows = 3: cycle = 4, pattern: [0,1,2,1]
For numRows = 4: cycle = 6, pattern: [0,1,2,3,2,1]  
For numRows = 5: cycle = 8, pattern: [0,1,2,3,4,3,2,1]
```

## Template Solutions

### Template 1: StringBuilder Array (Most Common)
```java
public String convert(String s, int numRows) {
    // Edge cases
    if (numRows == 1 || s.length() <= numRows) return s;
    
    // Create array of StringBuilders
    StringBuilder[] rows = new StringBuilder[numRows];
    for (int i = 0; i < numRows; i++) {
        rows[i] = new StringBuilder();
    }
    
    // Simulate zigzag movement
    int currentRow = 0;
    boolean goingDown = false;
    
    for (char c : s.toCharArray()) {
        rows[currentRow].append(c);
        
        // Change direction at boundaries
        if (currentRow == 0 || currentRow == numRows - 1) {
            goingDown = !goingDown;
        }
        
        currentRow += goingDown ? 1 : -1;
    }
    
    // Concatenate all rows
    StringBuilder result = new StringBuilder();
    for (StringBuilder row : rows) {
        result.append(row);
    }
    
    return result.toString();
}
```

### Template 2: Mathematical Pattern (Space Optimal)
```java
public String convertMath(String s, int numRows) {
    if (numRows == 1 || s.length() <= numRows) return s;
    
    StringBuilder result = new StringBuilder();
    int cycleLength = 2 * numRows - 2;
    
    for (int row = 0; row < numRows; row++) {
        for (int i = 0; i + row < s.length(); i += cycleLength) {
            // Add character from vertical line
            result.append(s.charAt(i + row));
            
            // Add diagonal character (middle rows only)
            if (row != 0 && row != numRows - 1 && 
                i + cycleLength - row < s.length()) {
                result.append(s.charAt(i + cycleLength - row));
            }
        }
    }
    
    return result.toString();
}
```

## Key Implementation Patterns

### 1. Direction Toggle Pattern
```java
// Standard boundary-based direction change
if (currentRow == 0 || currentRow == numRows - 1) {
    goingDown = !goingDown;
}
currentRow += goingDown ? 1 : -1;
```

### 2. Row Collection Pattern
```java
// Collect characters by row, then concatenate
StringBuilder[] rows = new StringBuilder[numRows];
// ... populate rows ...

StringBuilder result = new StringBuilder();
for (StringBuilder row : rows) {
    result.append(row);
}
```

### 3. Mathematical Index Pattern
```java
// For each row, calculate character positions
for (int row = 0; row < numRows; row++) {
    for (int i = row; i < s.length(); i += cycleLength) {
        // Process character at position i
        // Check for diagonal characters in middle rows
    }
}
```

## Algorithm Selection Guide

| Scenario | Use This Approach | Reason |
|----------|------------------|--------|
| Interview | StringBuilder Array | Easier to explain and code |
| Optimal Space | Mathematical | O(1) extra space |
| Learning | Both | Understand trade-offs |
| Complex Variations | StringBuilder Array | More flexible |

## Common Pitfalls & Solutions

### ❌ Wrong Direction Logic
```java
// WRONG: Separate conditions
if (currentRow == 0) goingDown = true;
if (currentRow == numRows - 1) goingDown = false;

// RIGHT: Toggle at boundaries
if (currentRow == 0 || currentRow == numRows - 1) {
    goingDown = !goingDown;
}
```

### ❌ Missing Edge Cases
```java
// WRONG: Not handling single row
public String convert(String s, int numRows) {
    StringBuilder[] rows = new StringBuilder[numRows]; // Error if numRows = 1
}

// RIGHT: Handle edge cases first
if (numRows == 1 || s.length() <= numRows) return s;
```

### ❌ Array Index Bounds
```java
// WRONG: Not checking bounds in math approach
result.append(s.charAt(i + cycleLength - row)); // May be out of bounds

// RIGHT: Always validate indices
if (i + cycleLength - row < s.length()) {
    result.append(s.charAt(i + cycleLength - row));
}
```

### ❌ Incorrect Cycle Calculation
```java
// WRONG: Off-by-one error
int cycleLength = 2 * numRows; // Too long

// RIGHT: Proper cycle length
int cycleLength = 2 * numRows - 2;
```

## Step-by-Step Problem Solving

### 1. Pattern Recognition
- [ ] Identify zigzag movement (down then up)
- [ ] Calculate cycle length: `2 × numRows - 2`
- [ ] Understand boundary conditions

### 2. Choose Approach
- [ ] **Simulation**: StringBuilder array for clarity
- [ ] **Mathematical**: Pattern-based for efficiency
- [ ] Consider space/time trade-offs

### 3. Implementation Checklist
- [ ] Handle edge cases (numRows = 1, short string)
- [ ] Implement direction changes correctly
- [ ] Validate array bounds
- [ ] Test with provided examples

### 4. Verification Steps
```java
// Test with standard examples
convert("PAYPALISHIRING", 3) → "PAHNAPLSIIGYIR"
convert("PAYPALISHIRING", 4) → "PINALSIGYAHRPI"
convert("A", 1) → "A"
```

## Visual Debugging Technique

### Create Debug Visualization
```java
public void visualizeZigzag(String s, int numRows) {
    char[][] grid = new char[numRows][s.length()];
    // Fill grid with spaces initially
    
    int row = 0, col = 0;
    boolean goingDown = false;
    
    for (char c : s.toCharArray()) {
        grid[row][col] = c;
        
        if (row == 0 || row == numRows - 1) {
            goingDown = !goingDown;
        }
        
        if (goingDown) {
            row++;
        } else {
            row--;
            col++;
        }
        if (row == 0) col++;
    }
    
    // Print grid for visualization
}
```

## Complexity Quick Reference

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| StringBuilder Array | O(n) | O(n) | Easy to understand |
| Mathematical | O(n) | O(1) | Space optimal |
| List-based | O(n) | O(n) | Alternative implementation |

## Interview Strategy

### 1. Start Simple
```java
// Begin with simulation approach
"I'll create a StringBuilder for each row and simulate the movement"
```

### 2. Explain the Pattern  
```java
// Walk through the zigzag movement
"Characters move down until bottom, then up until top, repeating"
```

### 3. Show Optimization
```java  
// If time allows, mention mathematical approach
"We can optimize space by calculating positions directly"
```

## Related Problems
- **54. Spiral Matrix** - Similar 2D traversal patterns
- **59. Spiral Matrix II** - Pattern-based matrix filling
- **48. Rotate Image** - 2D array manipulation
- **73. Set Matrix Zeroes** - Matrix transformation

## Quick Implementation Steps
1. Handle edge cases (numRows = 1)
2. Create StringBuilder array or use math approach
3. Implement zigzag movement with direction toggles
4. Concatenate results from all rows
5. Test with given examples