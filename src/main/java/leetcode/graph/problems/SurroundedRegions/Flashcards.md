# Surrounded Regions - Flashcards

## Problem Understanding

**Q: What does the Surrounded Regions problem ask us to do?**
A: Given a board of 'X' and 'O' characters, **capture** (flip to 'X') all 'O' regions that are completely surrounded by 'X'. Regions touching the border of the board cannot be captured.

**Q: What defines a "surrounded" region?**
A: A region of connected 'O's is surrounded if:
1. It's enclosed by 'X' on all sides
2. **None** of its cells touch the border (edge) of the board

If even ONE cell in a connected 'O' region touches the border, the entire region escapes capture.

**Q: Why is the direct approach difficult?**
A: Finding surrounded regions directly is hard because:
- You need to check if a region is fully enclosed
- If ANY cell in a connected region touches the border, the WHOLE region escapes
- You'd need to trace the entire region before deciding

## The Key Insight

**Q: What is the "reverse thinking" trick for this problem?**
A: Instead of finding what's **surrounded**, find what **ESCAPES**!
- An 'O' escapes if it's connected to the border
- Start from border 'O's, mark all connected 'O's as "safe"
- Everything NOT marked must be surrounded → capture it

**Q: Why is the reverse approach easier?**
A:
- **Direct**: "Is this region surrounded?" → Need to check ALL boundaries
- **Reverse**: "Is this O connected to border?" → Just DFS from border

The reverse approach has a clear starting point (border cells) and a simple rule (connected = safe).

## Algorithm

**Q: What are the three steps of the algorithm?**
A:
```
Step 1: MARK SAFE
        - DFS/BFS from every 'O' on the border
        - Mark all connected 'O's as 'S' (safe)

Step 2: CAPTURE
        - Scan the board
        - Change any remaining 'O' to 'X' (these were surrounded)

Step 3: RESTORE
        - Change all 'S' back to 'O' (these escaped)
```

**Q: Why do we use 'S' as a temporary marker?**
A: We need to distinguish three states:
- `'X'` = wall (unchanged)
- `'O'` = not yet processed (might be captured)
- `'S'` = confirmed safe (connected to border)

Using 'S' lets us mark cells as "visited and safe" without confusing them with cells that should be captured.

**Q: What does the DFS helper function do?**
A:
```java
private void dfs(char[][] board, int i, int j) {
    // Stop if out of bounds
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
    // Stop if not an 'O' (either 'X' or already 'S')
    if (board[i][j] != 'O') return;

    board[i][j] = 'S';  // Mark as safe

    // Explore all 4 neighbors
    dfs(board, i+1, j);
    dfs(board, i-1, j);
    dfs(board, i, j+1);
    dfs(board, i, j-1);
}
```

## Border Traversal

**Q: How do you iterate over all border cells?**
A: Check all 4 edges:
```java
// Left and right edges (all rows, columns 0 and n-1)
for (int i = 0; i < m; i++) {
    if (board[i][0] == 'O') dfs(board, i, 0);        // Left
    if (board[i][n-1] == 'O') dfs(board, i, n-1);    // Right
}

// Top and bottom edges (row 0 and m-1, all columns)
for (int j = 0; j < n; j++) {
    if (board[0][j] == 'O') dfs(board, 0, j);        // Top
    if (board[m-1][j] == 'O') dfs(board, m-1, j);    // Bottom
}
```

**Q: Why are corners covered by this iteration?**
A: Corners are included twice (once in each loop), but that's fine:
- First encounter: DFS marks it and connected cells
- Second encounter: `board[i][j] != 'O'` check returns immediately (it's already 'S')

## Complexity

**Q: What is the time complexity?**
A: **O(m × n)** where m = rows, n = columns.
- Border scan: O(m + n)
- DFS visits each cell at most once
- Final scan: O(m × n)
- Total: O(m × n)

**Q: What is the space complexity?**
A: **O(m × n)** for DFS recursion stack in worst case (entire board is 'O' connected to border).

For BFS: O(m × n) for the queue in worst case.

## Common Mistakes

**Q: What happens if you start DFS from ALL 'O's instead of just border 'O's?**
A: ALL 'O's would be marked as safe, and nothing would be captured! The algorithm would do nothing.

**Q: What happens if you check `board[i][j] != 'X'` instead of `board[i][j] != 'O'` in DFS?**
A: **Infinite loop!** Once a cell is marked 'S', the check `!= 'X'` still passes, so DFS would revisit 'S' cells forever.

**Q: What happens if you capture before marking safe?**
A: All 'O's would be captured, including border-connected ones! The order must be:
1. Mark safe (preserve escaping cells)
2. Capture (flip remaining 'O's)
3. Restore (change 'S' back to 'O')

**Q: What edge case should you always check first?**
A: Null or empty board:
```java
if (board == null || board.length == 0) return;
```

## Visual Examples

**Q: Walk through a simple example.**
A:
```
Initial:     After Mark:   Final:
X X X X      X X X X       X X X X
X O O X  →   X O O X   →   X X X X   (O's captured)
X O X X      X O X X       X X X X
X O X X      X S X X       X O X X   (S restored to O)

Border O at (3,1) → marked S
Other O's not connected to border → captured
```

**Q: What if all O's are connected to the border?**
A:
```
Initial:     After Mark:   Final:
O O O        S S S         O O O
O X O   →    S X S    →    O X O    (nothing captured!)
O O O        S S S         O O O

All O's connected to border → all marked safe → all restored
```

## Interview Tips

**Q: How should you explain your approach?**
A:
1. "The key insight is to think in reverse - find O's that can ESCAPE rather than those that are surrounded"
2. "An O escapes if it's connected to the border"
3. "I'll DFS from all border O's, marking them as safe"
4. "Then capture everything not marked, and restore the safe cells"

**Q: What clarifying questions might you ask?**
A:
1. "Can I modify the board in place?" (Yes, the problem asks for in-place)
2. "What if the board is empty?" (Handle null/empty)
3. "Is the board guaranteed to be rectangular?" (Yes, m×n)

## Related Patterns

**Q: How is this similar to Number of Islands?**
A: Both use DFS/BFS on a grid to find connected components. The difference:
- **Number of Islands**: Count ALL connected components
- **Surrounded Regions**: Find components connected to a specific region (border)

**Q: What other problems use the "border DFS" pattern?**
A:
- **417. Pacific Atlantic Water Flow**: DFS from both ocean borders
- **1020. Number of Enclaves**: Count cells NOT reachable from border
- **1254. Number of Closed Islands**: Similar to Surrounded Regions
