# Surrounded Regions - Study Guide

## Understanding the Problem

### What Are We Really Being Asked?

Imagine the board is a battlefield:
- `'X'` = walls/barriers
- `'O'` = enemy troops

We want to **capture** (convert to `'X'`) all enemy troops that are **completely surrounded** by walls. But troops on the **edge of the board** can "escape" and cannot be captured.

```
X X X X
X O O X   ← These O's are trapped inside walls → CAPTURE!
X X O X
X O X X   ← This O is on the edge → CAN'T capture (it escaped!)
```

### The Definition of "Surrounded"

A region of `'O'`s is surrounded if:
1. It's completely enclosed by `'X'`s on all sides
2. **AND** no cell in the region touches the border of the board

If even ONE cell in a connected region touches the border, the ENTIRE region escapes!

### Visual Example

```
Board:           Analysis:
X X X X          X X X X
X O O X          X ● ● X   ● = surrounded (no path to edge)
X X O X          X X ● X
X O X X          X ○ X X   ○ = on edge (escapes!)

The three ●'s are connected but NONE touch the edge → Capture all!
The one ○ touches the edge → Escapes!
```

## The Key Insight: Think in Reverse!

### Why Direct Approach is Hard

If we try to find "surrounded" regions directly:
```
For each O:
  Check if it can reach the edge...
  But wait, it might reach edge through OTHER O's...
  So we need to check ALL connected O's...
  And if ANY of them touches the edge, NONE are captured...

This gets complicated fast!
```

### The Reverse Approach (Much Easier!)

Instead of asking "which O's are surrounded?", ask "which O's can ESCAPE?"

```
Key insight: An O can escape if and only if it's connected to a border O.

So:
1. Find all O's on the border
2. Find all O's connected to border O's (they all escape together!)
3. Everything else must be surrounded → CAPTURE!
```

## The Algorithm: Three Simple Steps

```
╔════════════════════════════════════════════════════════════════╗
║  Step 1: MARK SAFE                                             ║
║  - Start from every 'O' on the border (edges)                  ║
║  - DFS/BFS to find all connected 'O's                          ║
║  - Mark them as 'S' (safe/escape)                              ║
╠════════════════════════════════════════════════════════════════╣
║  Step 2: CAPTURE                                               ║
║  - Scan entire board                                           ║
║  - Any remaining 'O' is surrounded → change to 'X'             ║
╠════════════════════════════════════════════════════════════════╣
║  Step 3: RESTORE                                               ║
║  - Change all 'S' back to 'O'                                  ║
║  - These were the safe cells that escaped                      ║
╚════════════════════════════════════════════════════════════════╝
```

## Detailed Execution Trace

### Example Board
```
Initial:
    0   1   2   3
  +---+---+---+---+
0 | X | X | X | X |
  +---+---+---+---+
1 | X | O | O | X |
  +---+---+---+---+
2 | X | X | O | X |
  +---+---+---+---+
3 | X | O | X | X |
  +---+---+---+---+
```

### Step 1: Mark Safe (DFS from Border)

```
Scan border cells:

Row 0 (top edge):    X X X X  → No O's
Row 3 (bottom edge): X O X X  → Found O at (3,1)!
Col 0 (left edge):   X X X X  → No O's
Col 3 (right edge):  X X X X  → No O's

Start DFS from (3,1):

dfs(3,1): board[3][1] = 'O' → Mark as 'S'
  → dfs(4,1): out of bounds, return
  → dfs(2,1): board[2][1] = 'X', return
  → dfs(3,2): board[3][2] = 'X', return
  → dfs(3,0): board[3][0] = 'X', return

After Step 1:
    0   1   2   3
  +---+---+---+---+
0 | X | X | X | X |
  +---+---+---+---+
1 | X | O | O | X |   ← These O's are NOT marked (not connected to border)
  +---+---+---+---+
2 | X | X | O | X |   ← This O is NOT marked
  +---+---+---+---+
3 | X | S | X | X |   ← Marked as Safe!
  +---+---+---+---+
```

### Step 2: Capture (O → X)

```
Scan entire board, change any remaining 'O' to 'X':

(1,1): 'O' → 'X'  (captured!)
(1,2): 'O' → 'X'  (captured!)
(2,2): 'O' → 'X'  (captured!)

After Step 2:
    0   1   2   3
  +---+---+---+---+
0 | X | X | X | X |
  +---+---+---+---+
1 | X | X | X | X |   ← Captured!
  +---+---+---+---+
2 | X | X | X | X |   ← Captured!
  +---+---+---+---+
3 | X | S | X | X |
  +---+---+---+---+
```

### Step 3: Restore (S → O)

```
Scan entire board, change any 'S' back to 'O':

(3,1): 'S' → 'O'  (restored!)

Final Result:
    0   1   2   3
  +---+---+---+---+
0 | X | X | X | X |
  +---+---+---+---+
1 | X | X | X | X |
  +---+---+---+---+
2 | X | X | X | X |
  +---+---+---+---+
3 | X | O | X | X |   ← This O survived (was on edge)
  +---+---+---+---+
```

## Understanding the Code

### The Main Function

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;

    int m = board.length;
    int n = board[0].length;

    // Step 1: Mark safe cells (DFS from all border O's)
    // Check left and right edges
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') dfs(board, i, 0);      // Left edge
        if (board[i][n-1] == 'O') dfs(board, i, n-1);  // Right edge
    }
    // Check top and bottom edges
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') dfs(board, 0, j);      // Top edge
        if (board[m-1][j] == 'O') dfs(board, m-1, j);  // Bottom edge
    }

    // Steps 2 & 3: Capture and Restore
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == 'O') {
                board[i][j] = 'X';  // Step 2: Capture surrounded O's
            } else if (board[i][j] == 'S') {
                board[i][j] = 'O';  // Step 3: Restore safe O's
            }
        }
    }
}
```

### The DFS Helper

```java
private void dfs(char[][] board, int i, int j) {
    // Boundary check
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
        return;
    }

    // Only process 'O' cells (skip X and already-marked S)
    if (board[i][j] != 'O') {
        return;
    }

    // Mark as safe
    board[i][j] = 'S';

    // Explore all 4 directions
    dfs(board, i + 1, j);  // Down
    dfs(board, i - 1, j);  // Up
    dfs(board, i, j + 1);  // Right
    dfs(board, i, j - 1);  // Left
}
```

## Why Use 'S' as a Marker?

We need THREE states during the algorithm:

| Original | During Algorithm | Final |
|----------|------------------|-------|
| 'X' | 'X' (unchanged) | 'X' |
| 'O' (surrounded) | 'O' (not marked) → 'X' | 'X' |
| 'O' (on border) | 'O' → 'S' → 'O' | 'O' |

Using 'S' lets us distinguish between:
- `'O'` = not yet visited, might be surrounded
- `'S'` = visited, confirmed safe (connected to border)

## Common Mistakes and How to Avoid Them

### Mistake 1: Starting DFS from Interior Instead of Border

```java
// WRONG - starts from any O
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        if (board[i][j] == 'O') {
            dfs(board, i, j);  // This marks ALL O's, not just border-connected!
        }
    }
}

// CORRECT - only start from border O's
for (int i = 0; i < m; i++) {
    if (board[i][0] == 'O') dfs(board, i, 0);        // Left edge only
    if (board[i][n-1] == 'O') dfs(board, i, n-1);    // Right edge only
}
// ... same for top and bottom
```

### Mistake 2: Forgetting to Check All Four Borders

```java
// WRONG - only checks left and top
for (int i = 0; i < m; i++) {
    if (board[i][0] == 'O') dfs(board, i, 0);
}
for (int j = 0; j < n; j++) {
    if (board[0][j] == 'O') dfs(board, 0, j);
}
// Missing right edge (col n-1) and bottom edge (row m-1)!

// CORRECT - check all four edges
for (int i = 0; i < m; i++) {
    if (board[i][0] == 'O') dfs(board, i, 0);        // Left
    if (board[i][n-1] == 'O') dfs(board, i, n-1);    // Right
}
for (int j = 0; j < n; j++) {
    if (board[0][j] == 'O') dfs(board, 0, j);        // Top
    if (board[m-1][j] == 'O') dfs(board, m-1, j);    // Bottom
}
```

### Mistake 3: Wrong Order of Operations

```java
// WRONG - captures before marking safe!
for (int i = 0; i < m; i++) {
    for (int j = 0; j < n; j++) {
        if (board[i][j] == 'O') board[i][j] = 'X';  // Captures everything!
    }
}
// Now there are no O's left to mark as safe!

// CORRECT - mark safe FIRST, then capture
// Step 1: Mark safe
for (int i = 0; i < m; i++) {
    if (board[i][0] == 'O') dfs(board, i, 0);
    // ...
}
// Step 2: Then capture and restore
```

### Mistake 4: Using the Wrong Character Check in DFS

```java
// WRONG - will infinite loop!
private void dfs(char[][] board, int i, int j) {
    if (board[i][j] == 'X') return;  // Only checks for X

    board[i][j] = 'S';
    dfs(board, i + 1, j);  // If already S, still recurses! Infinite loop!
}

// CORRECT - check for exactly 'O'
private void dfs(char[][] board, int i, int j) {
    if (board[i][j] != 'O') return;  // Skips X AND already-marked S

    board[i][j] = 'S';
    dfs(board, i + 1, j);  // S cells will return immediately
}
```

## Visualization: Another Example

```
Initial Board:            After Marking Safe:       Final Result:
O O O O X                 S S S S X                 O O O O X
O X O X O                 S X S X S                 O X O X O
O O X O X       →         S S X S X       →         O O X O X
X O X O O                 X S X S S                 X O X O O
X X O X O                 X X S X S                 X X O X O

All O's are connected to border → ALL are safe → No captures!
```

```
Initial Board:            After Marking Safe:       Final Result:
X X X X X                 X X X X X                 X X X X X
X O O O X                 X O O O X                 X X X X X
X O X O X       →         X O X O X       →         X X X X X
X O O O X                 X O O O X                 X X X X X
X X X X X                 X X X X X                 X X X X X

No O touches border → None are safe → ALL captured!
```

## Practice Questions

1. **Why do we mark with 'S' instead of just using a visited array?**
   - Answer: Using 'S' in the board itself means O(1) extra space (vs O(m×n) for visited array), and we can distinguish safe vs surrounded in the final pass.

2. **What if we started DFS from all O's, not just border O's?**
   - Answer: All O's would be marked as safe, and nothing would be captured!

3. **Could we do the opposite: mark surrounded regions instead?**
   - Answer: Yes, but it's harder. You'd have to detect if ANY cell in a region touches the border, which requires tracking the entire region first.

4. **What's the time complexity if we had nested loops calling DFS on every cell?**
   - Answer: Still O(m×n) because each cell is visited at most once (marked as 'S' and never revisited).

## Key Takeaways

1. **Reverse thinking**: Find what ESCAPES, not what's captured
2. **Border-first**: Start DFS/BFS from border cells only
3. **Three states**: Use temporary marker ('S') for safe cells
4. **In-place**: Modify board directly to track state
5. **Order matters**: Mark safe → Capture → Restore
