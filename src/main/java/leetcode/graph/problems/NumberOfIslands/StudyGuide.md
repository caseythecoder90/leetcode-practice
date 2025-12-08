# Number of Islands - Study Guide

## Understanding the Problem

### What Are We Really Being Asked?

Think of the grid as a map viewed from above:
- `'1'` = land (shown as solid blocks)
- `'0'` = water (empty space)

We need to count how many **separate landmasses** (islands) exist.

**Key Definition**: An island is a group of `'1'`s connected **horizontally or vertically** (NOT diagonally).

### Visual Understanding

```
Grid:                          Visualization:
["1","1","0","0","0"]         ██ ██ ·  ·  ·
["1","1","0","0","0"]    →    ██ ██ ·  ·  ·
["0","0","1","0","0"]         ·  ·  ██ ·  ·
["0","0","0","1","1"]         ·  ·  ·  ██ ██

Island 1: Top-left 2x2 block (4 connected cells)
Island 2: Center cell (1 cell)
Island 3: Bottom-right 2 cells (2 connected cells)
Answer: 3 islands
```

### Why Is This a Graph Problem?

We can model this as a graph:
- **Nodes**: Each cell in the grid
- **Edges**: Between adjacent cells (up/down/left/right)
- **Question**: How many connected components of '1's exist?

## The Core Algorithm (DFS Approach)

### High-Level Strategy

```
FOR each cell in grid:
    IF cell is land ('1'):
        1. Start exploring from this cell
        2. Mark all connected land as visited ("sink" it)
        3. Increment island count
RETURN count
```

### Why "Sink" the Island?

We change visited `'1'`s to `'0'`s to:
1. **Mark as visited** - won't count the same island twice
2. **No extra space** - don't need a separate visited array
3. **Simple check** - just look for `'1'` to find unvisited land

### The DFS Function Explained

```java
private void dfs(char[][] grid, int row, int col) {
    // Step 1: Check boundaries and if water
    if (row < 0 || row >= grid.length ||
        col < 0 || col >= grid[0].length ||
        grid[row][col] == '0') {
        return;  // Stop: out of bounds or water
    }

    // Step 2: Mark current cell as visited (sink it)
    grid[row][col] = '0';

    // Step 3: Explore all 4 directions
    dfs(grid, row + 1, col);  // Down
    dfs(grid, row - 1, col);  // Up
    dfs(grid, row, col + 1);  // Right
    dfs(grid, row, col - 1);  // Left
}
```

## Detailed Execution Trace

### Example: 3 Islands

```
Initial Grid:
    0   1   2   3   4
  +---+---+---+---+---+
0 | 1 | 1 | 0 | 0 | 0 |
  +---+---+---+---+---+
1 | 1 | 1 | 0 | 0 | 0 |
  +---+---+---+---+---+
2 | 0 | 0 | 1 | 0 | 0 |
  +---+---+---+---+---+
3 | 0 | 0 | 0 | 1 | 1 |
  +---+---+---+---+---+
```

**Step-by-Step Execution:**

```
Main loop iteration: i=0, j=0
  grid[0][0] = '1' → Found land! Start DFS, count = 1

  DFS Call Stack:
  ┌─────────────────────────────────────────────────┐
  │ dfs(0,0): grid[0][0]='1' → sink to '0'          │
  │   → dfs(1,0): grid[1][0]='1' → sink to '0'      │
  │      → dfs(2,0): grid[2][0]='0' → return        │
  │      → dfs(0,0): grid[0][0]='0' → return        │
  │      → dfs(1,1): grid[1][1]='1' → sink to '0'   │
  │         → dfs(2,1): grid[2][1]='0' → return     │
  │         → dfs(0,1): grid[0][1]='1' → sink       │
  │            → ... continues until all connected  │
  │         → dfs(1,2): grid[1][2]='0' → return     │
  │         → dfs(1,0): grid[1][0]='0' → return     │
  │      → dfs(1,-1): out of bounds → return        │
  │   → dfs(-1,0): out of bounds → return           │
  │   → dfs(0,1): already '0' → return              │
  │   → dfs(0,-1): out of bounds → return           │
  └─────────────────────────────────────────────────┘

Grid after sinking Island 1:
    0   1   2   3   4
  +---+---+---+---+---+
0 | 0 | 0 | 0 | 0 | 0 |  ← All connected '1's now '0'
  +---+---+---+---+---+
1 | 0 | 0 | 0 | 0 | 0 |
  +---+---+---+---+---+
2 | 0 | 0 | 1 | 0 | 0 |  ← Island 2 still here
  +---+---+---+---+---+
3 | 0 | 0 | 0 | 1 | 1 |  ← Island 3 still here
  +---+---+---+---+---+
```

```
Continue main loop... skip all '0's until:

Main loop iteration: i=2, j=2
  grid[2][2] = '1' → Found land! Start DFS, count = 2

  DFS from (2,2):
    → sink (2,2) to '0'
    → all neighbors are '0' → return immediately

Grid after sinking Island 2:
    0   1   2   3   4
  +---+---+---+---+---+
2 | 0 | 0 | 0 | 0 | 0 |  ← (2,2) now '0'
  +---+---+---+---+---+
3 | 0 | 0 | 0 | 1 | 1 |  ← Island 3 still here
  +---+---+---+---+---+
```

```
Continue main loop... skip all '0's until:

Main loop iteration: i=3, j=3
  grid[3][3] = '1' → Found land! Start DFS, count = 3

  DFS from (3,3):
    → sink (3,3) to '0'
    → dfs(3,4): grid[3][4]='1' → sink to '0'
       → all neighbors are '0' or out of bounds → return

Grid after sinking Island 3:
    0   1   2   3   4
  +---+---+---+---+---+
3 | 0 | 0 | 0 | 0 | 0 |  ← All '1's now '0'
  +---+---+---+---+---+

Main loop completes. Return count = 3
```

## Understanding the DFS Recursion

### Base Case vs Recursive Case

```java
// BASE CASES (when to stop):
if (row < 0 || row >= grid.length)     // Out of bounds vertically
if (col < 0 || col >= grid[0].length)  // Out of bounds horizontally
if (grid[row][col] == '0')              // Water or already visited

// RECURSIVE CASE (when to continue):
if (grid[row][col] == '1')              // Unvisited land - explore!
```

### Order of Exploration

The order we explore (down, up, right, left) doesn't affect correctness:

```java
// These are all equivalent - we visit all connected cells regardless
dfs(grid, row + 1, col);  // Down
dfs(grid, row - 1, col);  // Up
dfs(grid, row, col + 1);  // Right
dfs(grid, row, col - 1);  // Left
```

### Alternative: Using a Directions Array

```java
private int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

private void dfs(char[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length ||
        col < 0 || col >= grid[0].length ||
        grid[row][col] == '0') {
        return;
    }

    grid[row][col] = '0';

    for (int[] dir : directions) {
        dfs(grid, row + dir[0], col + dir[1]);
    }
}
```

This is cleaner and easier to modify (e.g., add diagonal directions).

## BFS Alternative

### When to Use BFS Instead of DFS?

- **Deep recursion risk**: DFS can cause stack overflow on very large grids
- **Level-by-level processing**: BFS naturally processes in "waves"
- **Interview preference**: Some interviewers prefer explicit queue over recursion

### BFS Execution Trace

```
Grid:
["1","1","0"]
["1","0","0"]
["0","0","1"]

BFS from (0,0):
  Queue: [(0,0)]  →  Sink (0,0)

  Process (0,0):
    Check neighbors: (1,0)='1', (-1,0)=OOB, (0,1)='1', (0,-1)=OOB
    Queue: [(1,0), (0,1)]  →  Sink both

  Process (1,0):
    Check neighbors: (2,0)='0', (0,0)='0', (1,1)='0', (1,-1)=OOB
    Queue: [(0,1)]  →  No new cells

  Process (0,1):
    Check neighbors: (1,1)='0', (-1,1)=OOB, (0,2)='0', (0,0)='0'
    Queue: []  →  Done with Island 1

Island 1 complete. count = 1
```

### Key BFS Insight: Mark BEFORE Enqueueing

```java
// CORRECT: Mark when adding to queue
if (grid[newRow][newCol] == '1') {
    grid[newRow][newCol] = '0';  // Mark NOW
    queue.offer(new int[]{newRow, newCol});
}

// WRONG: Mark when processing
int[] cell = queue.poll();
grid[cell[0]][cell[1]] = '0';  // Too late! May have duplicates
```

Marking when enqueueing prevents the same cell from being added multiple times.

## Common Mistakes and How to Avoid Them

### Mistake 1: Forgetting Boundary Checks

```java
// WRONG - will throw ArrayIndexOutOfBoundsException
private void dfs(char[][] grid, int row, int col) {
    grid[row][col] = '0';  // What if row or col is out of bounds?
    dfs(grid, row + 1, col);
    // ...
}

// CORRECT - check boundaries FIRST
private void dfs(char[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length ||
        col < 0 || col >= grid[0].length) {
        return;  // Boundary check
    }
    // Now safe to access grid[row][col]
}
```

### Mistake 2: Wrong Character Comparison

```java
// WRONG - comparing char to int
if (grid[row][col] == 0) { ... }  // Compares to integer 0 (ASCII NUL)

// CORRECT - comparing char to char
if (grid[row][col] == '0') { ... }  // Compares to character '0'
```

### Mistake 3: Not Sinking Before Recursion

```java
// WRONG - can cause infinite recursion
private void dfs(char[][] grid, int row, int col) {
    if (grid[row][col] == '0') return;

    dfs(grid, row + 1, col);  // Might come back to this cell!
    dfs(grid, row - 1, col);
    // ...
    grid[row][col] = '0';     // Too late!
}

// CORRECT - sink immediately
private void dfs(char[][] grid, int row, int col) {
    if (grid[row][col] == '0') return;

    grid[row][col] = '0';     // Sink FIRST

    dfs(grid, row + 1, col);  // Now safe
    // ...
}
```

### Mistake 4: Including Diagonal Movement

```java
// WRONG for this problem - includes diagonals
int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};

// CORRECT - only 4 directions (horizontal + vertical)
int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
```

## Complexity Analysis

### Time Complexity: O(m × n)

- We visit each cell at most once in the main loop
- When we start DFS from a cell, we mark it as '0'
- Total cells visited = m × n

### Space Complexity: O(m × n) for DFS

**Worst case**: Entire grid is land in a line pattern

```
Grid:      Recursion depth = m × n
1 0 0 0
1 0 0 0    dfs(0,0) → dfs(1,0) → dfs(2,0) → dfs(3,0)
1 0 0 0    Stack frames: 4 (one per cell in the path)
1 0 0 0
```

**Best case**: O(min(m, n)) when land forms a compact shape.

### Space Complexity: O(min(m, n)) for BFS

The queue never holds more than the perimeter of the shape being explored.

## Practice Questions

1. **What would change if diagonals counted as connections?**
2. **How would you solve this without modifying the input grid?**
3. **What's the maximum recursion depth for a 300×300 grid?**
4. **How would you find the largest island instead of counting islands?**

## Key Takeaways

1. **Grid = implicit graph**: Each cell connects to its 4 neighbors
2. **Connected components**: Each island is one component
3. **DFS/BFS for traversal**: Either works; DFS is simpler to code
4. **"Sink" to mark visited**: Change '1' to '0' to track visited cells
5. **Boundary checks first**: Always validate indices before accessing array
