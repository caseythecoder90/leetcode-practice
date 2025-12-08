# Number of Islands - CheatSheet

## Problem at a Glance

```
Input:  2D char grid of '1' (land) and '0' (water)
Output: Count of islands (connected '1's horizontally/vertically)
```

## Quick Solution Template (DFS)

```java
public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                dfs(grid, i, j);
                count++;
            }
        }
    }
    return count;
}

private void dfs(char[][] grid, int row, int col) {
    // Boundary + water check
    if (row < 0 || row >= grid.length ||
        col < 0 || col >= grid[0].length ||
        grid[row][col] == '0') {
        return;
    }

    grid[row][col] = '0';  // Sink (mark visited)

    // Explore 4 directions
    dfs(grid, row + 1, col);
    dfs(grid, row - 1, col);
    dfs(grid, row, col + 1);
    dfs(grid, row, col - 1);
}
```

## Alternative: BFS Template

```java
public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                bfs(grid, i, j);
                count++;
            }
        }
    }
    return count;
}

private void bfs(char[][] grid, int row, int col) {
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    Queue<int[]> queue = new LinkedList<>();

    queue.offer(new int[]{row, col});
    grid[row][col] = '0';

    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : dirs) {
            int nr = cell[0] + d[0], nc = cell[1] + d[1];
            if (nr >= 0 && nr < grid.length &&
                nc >= 0 && nc < grid[0].length &&
                grid[nr][nc] == '1') {
                grid[nr][nc] = '0';
                queue.offer(new int[]{nr, nc});
            }
        }
    }
}
```

## Key Points to Remember

| Aspect | Details |
|--------|---------|
| **Pattern** | Connected Components on Grid |
| **Algorithm** | DFS or BFS |
| **Marking** | Change '1' → '0' to track visited |
| **Directions** | 4-way (up, down, left, right) |
| **Time** | O(m × n) |
| **Space** | O(m × n) worst case |

## Common Variations

### If Can't Modify Grid (Use Visited Array)

```java
boolean[][] visited = new boolean[m][n];
// Replace grid[row][col] = '0' with visited[row][col] = true
// Replace grid[row][col] == '0' with visited[row][col]
```

### If Diagonals Count (8 Directions)

```java
int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1},
                {1,1}, {1,-1}, {-1,1}, {-1,-1}};
```

### Track Island Size

```java
private int dfs(char[][] grid, int row, int col) {
    if (/* bounds/water check */) return 0;
    grid[row][col] = '0';
    return 1 + dfs(down) + dfs(up) + dfs(right) + dfs(left);
}
```

## Quick Debugging Checklist

- [ ] Boundary check BEFORE accessing `grid[row][col]`
- [ ] Using `'0'` and `'1'` (chars), not `0` and `1` (ints)
- [ ] Sinking cell BEFORE recursive calls (avoid infinite loop)
- [ ] Only 4 directions (not diagonal)
- [ ] Incrementing count AFTER completing DFS/BFS

## Interview Tips

1. **Clarify**: "Are diagonal connections considered?" (usually no)
2. **State approach**: "I'll use DFS to sink each island as I find it"
3. **Mention tradeoff**: "Modifying input vs using visited array"
4. **Edge cases**: Empty grid, all water, all land, single cell
