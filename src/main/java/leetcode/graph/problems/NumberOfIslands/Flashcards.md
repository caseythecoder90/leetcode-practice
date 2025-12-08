# Number of Islands - Flashcards

## Problem Understanding

**Q: What is the Number of Islands problem asking?**
A: Given a 2D grid of `'1'`s (land) and `'0'`s (water), count how many islands exist. An island is a group of `'1'`s connected horizontally or vertically (not diagonally).

**Q: What pattern does Number of Islands follow?**
A: **Connected Components** on a grid. Each island is one connected component. We model the grid as an implicit graph where cells are nodes and adjacent cells are edges.

**Q: Why is this considered a graph problem?**
A: The grid can be viewed as a graph where:
- Each cell is a **node**
- Adjacent cells (up/down/left/right) are connected by **edges**
- Each island is a **connected component**
- We're counting the number of connected components

## Algorithm Choice

**Q: What's the recommended approach for this problem?**
A: **DFS (Depth-First Search)** is the most common and intuitive approach:
1. Scan the grid for any unvisited `'1'`
2. When found, DFS to mark all connected `'1'`s as visited
3. Increment island count
4. Repeat until all cells are processed

**Q: Why do we "sink" the island (change '1' to '0')?**
A: Sinking serves as our visited marker:
1. **Prevents revisiting** - won't count same island twice
2. **No extra space** - don't need separate visited array
3. **Simple check** - just look for `'1'` to find unvisited land

**Q: When would you use BFS instead of DFS?**
A: Use BFS when:
- Risk of stack overflow (very deep recursion on large grids)
- Need to process level-by-level (though not required here)
- Interviewer specifically asks for iterative solution

## Code Structure

**Q: What does the main function structure look like?**
A:
```java
public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                dfs(grid, i, j);  // Sink the island
                count++;
            }
        }
    }
    return count;
}
```

**Q: What does the DFS helper function look like?**
A:
```java
private void dfs(char[][] grid, int row, int col) {
    // Base case: out of bounds or water
    if (row < 0 || row >= grid.length ||
        col < 0 || col >= grid[0].length ||
        grid[row][col] == '0') {
        return;
    }

    grid[row][col] = '0';  // Sink (mark visited)

    // Explore all 4 directions
    dfs(grid, row + 1, col);
    dfs(grid, row - 1, col);
    dfs(grid, row, col + 1);
    dfs(grid, row, col - 1);
}
```

**Q: What's the cleaner way to handle 4 directions?**
A: Use a directions array:
```java
int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

for (int[] d : dirs) {
    dfs(grid, row + d[0], col + d[1]);
}
```

## Complexity Analysis

**Q: What is the time complexity?**
A: **O(m × n)** where m = rows, n = columns.
- Each cell is visited at most once
- Once visited (sunk), we never process it again
- Total operations proportional to grid size

**Q: What is the space complexity for DFS?**
A: **O(m × n)** in the worst case.
- Recursion stack can go as deep as the number of cells
- Worst case: entire grid is land in a winding pattern
- Best case: O(min(m, n)) for compact shapes

**Q: What is the space complexity for BFS?**
A: **O(min(m, n))** typically.
- Queue holds at most the perimeter of current island
- More predictable than DFS stack depth

## Common Mistakes

**Q: What's wrong with this code?**
```java
if (grid[row][col] == 0) return;  // Bug!
```
A: Comparing char to int! Should be `'0'` (char), not `0` (int).
```java
if (grid[row][col] == '0') return;  // Correct
```

**Q: What's wrong with this DFS order?**
```java
dfs(grid, row + 1, col);
grid[row][col] = '0';  // Bug!
```
A: Must sink BEFORE recursing to prevent infinite loops. The cell should be marked visited immediately:
```java
grid[row][col] = '0';  // Sink first!
dfs(grid, row + 1, col);
```

**Q: What's wrong with this boundary check?**
```java
if (grid[row][col] == '0') return;
if (row < 0 || row >= grid.length) return;  // Bug!
```
A: Boundary check must come BEFORE accessing `grid[row][col]`:
```java
if (row < 0 || row >= grid.length ||
    col < 0 || col >= grid[0].length ||
    grid[row][col] == '0') return;
```

## Edge Cases

**Q: What edge cases should you consider?**
A:
1. **Empty grid**: Return 0
2. **All water**: Return 0
3. **All land**: Return 1
4. **Single cell**: Return 1 if `'1'`, else 0
5. **One row or column**: Still works with 4-direction DFS

## Variations and Follow-ups

**Q: How would you solve this without modifying the input?**
A: Use a separate visited array:
```java
boolean[][] visited = new boolean[m][n];
// Check visited[row][col] instead of grid[row][col] == '0'
// Set visited[row][col] = true instead of grid[row][col] = '0'
```

**Q: How would you find the largest island size?**
A: Return size from DFS:
```java
private int dfs(char[][] grid, int row, int col) {
    if (/* base case */) return 0;
    grid[row][col] = '0';
    return 1 + dfs(down) + dfs(up) + dfs(right) + dfs(left);
}
```

**Q: What if diagonal connections counted?**
A: Add 4 more directions:
```java
int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1},
                {1,1}, {1,-1}, {-1,1}, {-1,-1}};  // 8 directions
```

## Interview Tips

**Q: How should you explain your approach in an interview?**
A:
1. "This is a connected components problem on a grid"
2. "I'll iterate through each cell, and when I find unvisited land..."
3. "...I'll use DFS to explore and 'sink' all connected land cells"
4. "Each time I start a new DFS, that's a new island"
5. "Time is O(m×n), space is O(m×n) for recursion stack"

**Q: What clarifying questions should you ask?**
A:
1. "Are diagonal connections considered?" (Usually no)
2. "Can I modify the input grid?" (Usually yes)
3. "What should I return for an empty grid?" (0)
4. "Are the grid dimensions guaranteed to be at least 1?" (Check constraints)
