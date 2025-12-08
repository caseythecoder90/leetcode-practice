# 200. Number of Islands

## Problem Description

**Difficulty**: Medium
**Topics**: Array, Depth-First Search, Breadth-First Search, Union Find, Matrix
**Link**: [LeetCode 200](https://leetcode.com/problems/number-of-islands/)

Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s (water), return the number of islands.

An **island** is surrounded by water and is formed by connecting adjacent lands **horizontally or vertically**. You may assume all four edges of the grid are all surrounded by water.

## Examples

### Example 1:
```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
```

**Visual**:
```
1 1 1 1 0
1 1 0 1 0
1 1 0 0 0
0 0 0 0 0

All the 1s are connected (directly or indirectly), forming ONE island.
```

### Example 2:
```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```

**Visual**:
```
1 1 0 0 0    ← Island 1 (top-left)
1 1 0 0 0
0 0 1 0 0    ← Island 2 (center)
0 0 0 1 1    ← Island 3 (bottom-right)
```

## Constraints

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 300`
- `grid[i][j]` is `'0'` or `'1'`

## Pattern Recognition

This is a classic **Connected Components** problem on a grid. Key observations:

1. **Grid as Graph**: Each cell is a node; adjacent cells (up/down/left/right) are connected
2. **Connected Components**: Each island is a connected component of '1's
3. **Counting Components**: We need to count how many separate groups of connected '1's exist

## Solution Approaches

### Approach 1: DFS (Depth-First Search) - Recommended

**Idea**: Iterate through each cell. When we find a '1' (unvisited land), start DFS to mark all connected land cells as visited ("sink" the island), then increment count.

**Why DFS?**
- Simple recursive implementation
- Natural fit for exploring all connected cells
- No extra data structures needed (modify grid in-place)

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

private void dfs(char[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length ||
        col < 0 || col >= grid[0].length ||
        grid[row][col] == '0') {
        return;
    }

    grid[row][col] = '0';  // Mark as visited (sink)

    dfs(grid, row + 1, col);  // Down
    dfs(grid, row - 1, col);  // Up
    dfs(grid, row, col + 1);  // Right
    dfs(grid, row, col - 1);  // Left
}
```

**Complexity**:
- Time: O(m × n) - visit each cell once
- Space: O(m × n) - worst case recursion depth (all land)

### Approach 2: BFS (Breadth-First Search)

**Idea**: Same logic but use a queue instead of recursion.

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
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{row, col});
    grid[row][col] = '0';

    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] dir : directions) {
            int newRow = cell[0] + dir[0];
            int newCol = cell[1] + dir[1];
            if (newRow >= 0 && newRow < grid.length &&
                newCol >= 0 && newCol < grid[0].length &&
                grid[newRow][newCol] == '1') {
                grid[newRow][newCol] = '0';
                queue.offer(new int[]{newRow, newCol});
            }
        }
    }
}
```

**Complexity**:
- Time: O(m × n)
- Space: O(min(m, n)) - queue size bounded by smaller dimension

### Approach 3: Union-Find

**Idea**: Union all adjacent '1' cells; count final number of unique roots.

```java
public int numIslands(char[][] grid) {
    int m = grid.length, n = grid[0].length;
    UnionFind uf = new UnionFind(grid);

    int[][] directions = {{1,0}, {0,1}};  // Only right and down (avoid duplicates)

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == '1') {
                for (int[] dir : directions) {
                    int ni = i + dir[0], nj = j + dir[1];
                    if (ni < m && nj < n && grid[ni][nj] == '1') {
                        uf.union(i * n + j, ni * n + nj);
                    }
                }
            }
        }
    }
    return uf.getCount();
}
```

**Complexity**:
- Time: O(m × n × α(m × n)) ≈ O(m × n)
- Space: O(m × n) for Union-Find arrays

## Which Approach to Choose?

| Approach | Pros | Cons | Best For |
|----------|------|------|----------|
| **DFS** | Simple, intuitive, no extra space if modifying grid | Modifies input, stack overflow risk | Most interviews |
| **BFS** | Level-order traversal, no stack overflow | More verbose, queue space | When DFS stack is too deep |
| **Union-Find** | Doesn't modify input, supports dynamic queries | More complex to implement | Follow-up questions, dynamic problems |

**Recommendation**: Start with DFS in interviews - it's the most intuitive and commonly expected solution.

## Key Insights

1. **"Sink" the island**: Mark visited cells as '0' to avoid revisiting
2. **Grid traversal pattern**: Nested loops + DFS/BFS is a powerful combination
3. **4-directional movement**: Up, down, left, right (not diagonal)
4. **Boundary checking**: Essential to prevent array index out of bounds

## Related Problems

- [695. Max Area of Island](https://leetcode.com/problems/max-area-of-island/) - Similar DFS, track size
- [463. Island Perimeter](https://leetcode.com/problems/island-perimeter/) - Count borders
- [694. Number of Distinct Islands](https://leetcode.com/problems/number-of-distinct-islands/) - Track shapes
- [827. Making A Large Island](https://leetcode.com/problems/making-a-large-island/) - Union-Find variation
- [1905. Count Sub Islands](https://leetcode.com/problems/count-sub-islands/) - Two-grid comparison

## Common Follow-up Questions

1. **What if we can't modify the grid?** → Use a separate `visited` array or Union-Find
2. **What if diagonal connections count?** → Add 4 more directions to the movement array
3. **What's the maximum island size?** → Track size during DFS (Max Area of Island)
4. **Count distinct island shapes?** → Encode path directions during DFS
