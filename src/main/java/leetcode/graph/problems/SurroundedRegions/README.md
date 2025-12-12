# 130. Surrounded Regions

## Problem Description

**Difficulty**: Medium
**Topics**: Array, Depth-First Search, Breadth-First Search, Union Find, Matrix
**Link**: [LeetCode 130](https://leetcode.com/problems/surrounded-regions/)

You are given an `m x n` matrix `board` containing letters `'X'` and `'O'`. **Capture** regions that are **surrounded**:

- **Connect**: A cell is connected to adjacent cells horizontally or vertically
- **Region**: To form a region, connect every `'O'` cell
- **Surround**: The region is surrounded with `'X'` cells if you can connect the region with `'X'` cells and **none of the region cells are on the edge** of the board

To capture a surrounded region, replace all `'O'`s with `'X'`s **in-place**. You do not need to return anything.

## Examples

### Example 1:
```
Input:
X X X X          X X X X
X O O X    →     X X X X
X X O X          X X X X
X O X X          X O X X

Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
```

**Explanation**:
- The `O`s in positions (1,1), (1,2), (2,2) are surrounded - they get captured (→ `X`)
- The `O` at position (3,1) is on the bottom edge - it CANNOT be surrounded, so it stays `O`

### Example 2:
```
Input: [["X"]]
Output: [["X"]]

Just one cell with 'X', nothing to capture.
```

## Visual Understanding

```
BEFORE:                    AFTER:
X X X X                    X X X X
X O O X   Surrounded →     X X X X   (captured!)
X X O X   region           X X X X
X O X X   ↑ edge O         X O X X   (kept - touches edge)

The key insight:
- Middle O's are completely surrounded by X's → CAPTURE
- Bottom O touches the edge → CANNOT be captured (escapes!)
```

## Constraints

- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 200`
- `board[i][j]` is `'X'` or `'O'`

## The Key Insight: Think in Reverse!

**Direct approach (hard)**: Find all surrounded `O`s → Complex! How do you know if a region is fully surrounded?

**Reverse approach (easy)**: Find all `O`s that **CANNOT** be captured (touch the edge) → Mark them → Everything else gets captured!

```
Instead of: "Which O's are surrounded?"
Think:      "Which O's can ESCAPE to the edge?"
```

## Solution Approaches

### Approach 1: DFS from Border (Recommended)

**Strategy**:
1. Start from all `'O'`s on the border (edges of the board)
2. DFS to mark all `'O'`s connected to the border as "safe" (use temporary marker like `'S'`)
3. Iterate through entire board:
   - `'O'` → `'X'` (these were surrounded, capture them)
   - `'S'` → `'O'` (these were safe, restore them)

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;

    int m = board.length, n = board[0].length;

    // Step 1: Mark all 'O's connected to border as safe ('S')
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') dfs(board, i, 0);
        if (board[i][n-1] == 'O') dfs(board, i, n-1);
    }
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') dfs(board, 0, j);
        if (board[m-1][j] == 'O') dfs(board, m-1, j);
    }

    // Step 2: Capture surrounded regions and restore safe cells
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X';      // Capture
            else if (board[i][j] == 'S') board[i][j] = 'O'; // Restore
        }
    }
}

private void dfs(char[][] board, int i, int j) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
    if (board[i][j] != 'O') return;

    board[i][j] = 'S';  // Mark as safe

    dfs(board, i + 1, j);
    dfs(board, i - 1, j);
    dfs(board, i, j + 1);
    dfs(board, i, j - 1);
}
```

**Complexity**:
- Time: O(m × n) - visit each cell at most once
- Space: O(m × n) - recursion stack in worst case

### Approach 2: BFS from Border

Same logic but using a queue instead of recursion.

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;

    int m = board.length, n = board[0].length;
    Queue<int[]> queue = new LinkedList<>();

    // Add all border 'O's to queue
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') { queue.offer(new int[]{i, 0}); board[i][0] = 'S'; }
        if (board[i][n-1] == 'O') { queue.offer(new int[]{i, n-1}); board[i][n-1] = 'S'; }
    }
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') { queue.offer(new int[]{0, j}); board[0][j] = 'S'; }
        if (board[m-1][j] == 'O') { queue.offer(new int[]{m-1, j}); board[m-1][j] = 'S'; }
    }

    // BFS to mark all connected 'O's as safe
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : dirs) {
            int ni = cell[0] + d[0], nj = cell[1] + d[1];
            if (ni >= 0 && ni < m && nj >= 0 && nj < n && board[ni][nj] == 'O') {
                board[ni][nj] = 'S';
                queue.offer(new int[]{ni, nj});
            }
        }
    }

    // Capture and restore
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X';
            else if (board[i][j] == 'S') board[i][j] = 'O';
        }
    }
}
```

### Approach 3: Union-Find

Union all `'O'`s with a virtual "border" node. Any `'O'` connected to the border node is safe.

**Complexity**: Same as DFS/BFS but with Union-Find overhead.

## Which Approach to Choose?

| Approach | Pros | Cons | Best For |
|----------|------|------|----------|
| **DFS** | Simple, intuitive | Stack overflow on large boards | Most interviews |
| **BFS** | No stack overflow | More verbose | Very large boards |
| **Union-Find** | Elegant for connectivity | Overkill for this problem | When asked specifically |

**Recommendation**: Use DFS - it's the most intuitive and commonly expected.

## The Three-Step Algorithm

```
Step 1: MARK SAFE
        Start from border 'O's
        DFS/BFS to mark all connected 'O's as 'S' (safe)

Step 2: CAPTURE
        Any remaining 'O' must be surrounded
        Change 'O' → 'X'

Step 3: RESTORE
        Change 'S' back to 'O'
```

## Common Mistakes

1. **Trying to find surrounded regions directly**: Much harder than the reverse approach
2. **Forgetting corners**: Border includes all 4 edges, including corners
3. **Modifying while traversing**: Mark as 'S' instead of deleting to track what's visited
4. **Not handling empty board**: Always check for null/empty input

## Key Insights

1. **Reverse thinking**: Find what CAN'T be captured instead of what can
2. **Border-connected = Safe**: Any `'O'` reachable from the border escapes capture
3. **Temporary marker**: Use `'S'` to distinguish "safe" from "to be captured"
4. **In-place modification**: No need for extra visited array - use the board itself

## Related Problems

- [200. Number of Islands](https://leetcode.com/problems/number-of-islands/) - Basic connected components
- [417. Pacific Atlantic Water Flow](https://leetcode.com/problems/pacific-atlantic-water-flow/) - Similar border DFS
- [1020. Number of Enclaves](https://leetcode.com/problems/number-of-enclaves/) - Count surrounded cells
- [733. Flood Fill](https://leetcode.com/problems/flood-fill/) - Basic DFS on grid

## Interview Tips

1. **Explain the insight**: "Instead of finding surrounded regions, I'll find regions that escape to the border"
2. **Walk through the steps**: Mark safe → Capture → Restore
3. **Mention in-place**: "I'll use 'S' as a temporary marker to avoid extra space"
4. **Edge cases**: Empty board, all X's, all O's, single cell
