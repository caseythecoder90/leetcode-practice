# Surrounded Regions - CheatSheet

## Problem at a Glance

```
Input:  m x n board of 'X' and 'O'
Output: Capture (flip to 'X') all 'O' regions NOT connected to the border
Key:    Think in REVERSE - find what ESCAPES, capture the rest!
```

## Quick Solution Template (DFS)

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;

    int m = board.length, n = board[0].length;

    // Step 1: Mark border-connected O's as Safe ('S')
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') dfs(board, i, 0);        // Left edge
        if (board[i][n-1] == 'O') dfs(board, i, n-1);    // Right edge
    }
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') dfs(board, 0, j);        // Top edge
        if (board[m-1][j] == 'O') dfs(board, m-1, j);    // Bottom edge
    }

    // Step 2 & 3: Capture surrounded O's, Restore safe O's
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X';       // Capture
            else if (board[i][j] == 'S') board[i][j] = 'O';  // Restore
        }
    }
}

private void dfs(char[][] board, int i, int j) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
    if (board[i][j] != 'O') return;

    board[i][j] = 'S';  // Mark safe

    dfs(board, i + 1, j);
    dfs(board, i - 1, j);
    dfs(board, i, j + 1);
    dfs(board, i, j - 1);
}
```

## Alternative: BFS Template

```java
public void solve(char[][] board) {
    if (board == null || board.length == 0) return;

    int m = board.length, n = board[0].length;
    Queue<int[]> queue = new LinkedList<>();

    // Add all border O's to queue and mark as safe
    for (int i = 0; i < m; i++) {
        if (board[i][0] == 'O') { board[i][0] = 'S'; queue.offer(new int[]{i, 0}); }
        if (board[i][n-1] == 'O') { board[i][n-1] = 'S'; queue.offer(new int[]{i, n-1}); }
    }
    for (int j = 0; j < n; j++) {
        if (board[0][j] == 'O') { board[0][j] = 'S'; queue.offer(new int[]{0, j}); }
        if (board[m-1][j] == 'O') { board[m-1][j] = 'S'; queue.offer(new int[]{m-1, j}); }
    }

    // BFS to mark all connected O's
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

## The Algorithm in 3 Steps

```
Step 1: MARK SAFE     - DFS/BFS from all border O's, mark as 'S'
Step 2: CAPTURE       - Change remaining 'O' → 'X'
Step 3: RESTORE       - Change 'S' → 'O'
```

## Key Points to Remember

| Aspect | Details |
|--------|---------|
| **Pattern** | Border DFS/BFS + In-place marking |
| **Key Insight** | Find ESCAPING cells, not surrounded cells |
| **Marker** | Use 'S' for safe cells (temporary) |
| **Border** | All 4 edges: rows 0 & m-1, cols 0 & n-1 |
| **Time** | O(m × n) |
| **Space** | O(m × n) worst case for DFS stack |

## Visual Summary

```
X X X X        X X X X        X X X X
X O O X   →    X O O X   →    X X X X
X X O X        X X O X        X X X X
X O X X        X S X X        X O X X
  │              │              │
  │              │              └─ Restore S→O
  │              └─ Mark border O as S
  └─ Original
```

## Common Mistakes

| Mistake | Fix |
|---------|-----|
| Starting DFS from ALL O's | Only start from BORDER O's |
| Forgetting an edge | Check all 4 edges (top, bottom, left, right) |
| Wrong order | Mark safe FIRST, then capture |
| Checking `!= 'X'` in DFS | Check `!= 'O'` (skips both X and S) |
| Forgetting null check | `if (board == null || board.length == 0)` |

## Quick Debugging Checklist

- [ ] Null/empty board check
- [ ] All 4 borders scanned for O's
- [ ] DFS marks 'O' → 'S' (not 'O' → 'X')
- [ ] DFS checks `board[i][j] != 'O'` to return
- [ ] Final pass: 'O' → 'X', 'S' → 'O'

## Interview Tips

1. **Explain reverse thinking**: "Instead of finding surrounded regions, I'll find regions that escape to the border"
2. **State the 3 steps**: Mark safe → Capture → Restore
3. **Why 'S'?**: "Temporary marker to distinguish safe from surrounded"
4. **Edge cases**: Empty board, all X's, all O's, single cell
