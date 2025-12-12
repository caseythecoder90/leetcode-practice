package leetcode.graph.problems.SurroundedRegions;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 130. Surrounded Regions
 *
 * Given an m x n matrix board containing 'X' and 'O', capture all regions
 * that are 4-directionally surrounded by 'X'.
 *
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 * Regions touching the border cannot be captured.
 *
 * Pattern: Border DFS/BFS with In-place Marking
 *
 * Key Insight: Instead of finding surrounded regions (hard), find regions
 * that ESCAPE to the border (easy), then capture everything else.
 *
 * Algorithm:
 * 1. DFS/BFS from all border 'O's, mark them as 'S' (safe/escape)
 * 2. Scan board: 'O' → 'X' (capture surrounded)
 * 3. Scan board: 'S' → 'O' (restore safe cells)
 */
public class SurroundedRegions {

    /**
     * Approach 1: DFS from Border (Recommended)
     *
     * Time Complexity: O(m * n) - visit each cell at most once
     * Space Complexity: O(m * n) - recursion stack in worst case
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;

        // Step 1: Mark all 'O's connected to border as safe ('S')
        // Check left and right edges
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if (board[i][n - 1] == 'O') {
                dfs(board, i, n - 1);
            }
        }

        // Check top and bottom edges
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j);
            }
            if (board[m - 1][j] == 'O') {
                dfs(board, m - 1, j);
            }
        }

        // Step 2 & 3: Capture surrounded regions and restore safe cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';  // Capture: this O was surrounded
                } else if (board[i][j] == 'S') {
                    board[i][j] = 'O';  // Restore: this O was safe (border-connected)
                }
            }
        }
    }

    /**
     * DFS helper to mark all connected 'O's as safe ('S')
     */
    private void dfs(char[][] board, int i, int j) {
        // Boundary check
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return;
        }

        // Only process 'O' cells (skip 'X' and already-marked 'S')
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

    /**
     * Approach 2: BFS from Border
     *
     * Time Complexity: O(m * n)
     * Space Complexity: O(m * n) for queue in worst case
     */
    public void solveBFS(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        int m = board.length;
        int n = board[0].length;
        Queue<int[]> queue = new LinkedList<>();

        // Step 1: Add all border 'O's to queue and mark as safe
        // Left and right edges
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                board[i][0] = 'S';
                queue.offer(new int[]{i, 0});
            }
            if (board[i][n - 1] == 'O') {
                board[i][n - 1] = 'S';
                queue.offer(new int[]{i, n - 1});
            }
        }

        // Top and bottom edges
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                board[0][j] = 'S';
                queue.offer(new int[]{0, j});
            }
            if (board[m - 1][j] == 'O') {
                board[m - 1][j] = 'S';
                queue.offer(new int[]{m - 1, j});
            }
        }

        // BFS to mark all connected 'O's as safe
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n
                        && board[newRow][newCol] == 'O') {
                    board[newRow][newCol] = 'S';
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }

        // Step 2 & 3: Capture and restore
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'S') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    /**
     * Approach 3: DFS with Directions Array (cleaner version)
     */
    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public void solveClean(char[][] board) {
        if (board == null || board.length == 0) return;

        int m = board.length, n = board[0].length;

        // Mark border-connected O's as safe
        for (int i = 0; i < m; i++) {
            dfsClean(board, i, 0);
            dfsClean(board, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            dfsClean(board, 0, j);
            dfsClean(board, m - 1, j);
        }

        // Capture and restore
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == 'S') board[i][j] = 'O';
            }
        }
    }

    private void dfsClean(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
        if (board[i][j] != 'O') return;

        board[i][j] = 'S';

        for (int[] dir : directions) {
            dfsClean(board, i + dir[0], j + dir[1]);
        }
    }

    // ==================== Test Utilities ====================

    /**
     * Print board for debugging
     */
    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    /**
     * Deep copy board for testing multiple approaches
     */
    private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        SurroundedRegions solution = new SurroundedRegions();

        // Test Case 1: Standard example with one border O
        System.out.println("=== Test 1: Standard Example ===");
        char[][] board1 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
        };
        System.out.println("Before:");
        printBoard(board1);

        solution.solve(board1);

        System.out.println("\nAfter:");
        printBoard(board1);
        System.out.println("Expected: Middle O's captured, bottom O stays");

        // Test Case 2: Single cell
        System.out.println("\n=== Test 2: Single Cell ===");
        char[][] board2 = {{'X'}};
        System.out.println("Before: " + board2[0][0]);
        solution.solve(board2);
        System.out.println("After: " + board2[0][0]);
        System.out.println("Expected: X");

        // Test Case 3: All O's connected to border (nothing captured)
        System.out.println("\n=== Test 3: All Border-Connected ===");
        char[][] board3 = {
            {'O', 'O', 'O'},
            {'O', 'X', 'O'},
            {'O', 'O', 'O'}
        };
        System.out.println("Before:");
        printBoard(board3);

        solution.solve(board3);

        System.out.println("\nAfter:");
        printBoard(board3);
        System.out.println("Expected: No change (all O's escape)");

        // Test Case 4: Completely surrounded region
        System.out.println("\n=== Test 4: Completely Surrounded ===");
        char[][] board4 = {
            {'X', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'O', 'X', 'O', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'X', 'X', 'X', 'X'}
        };
        System.out.println("Before:");
        printBoard(board4);

        solution.solve(board4);

        System.out.println("\nAfter:");
        printBoard(board4);
        System.out.println("Expected: All O's captured");

        // Test Case 5: BFS approach
        System.out.println("\n=== Test 5: BFS Approach ===");
        char[][] board5 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
        };
        System.out.println("Before:");
        printBoard(board5);

        solution.solveBFS(board5);

        System.out.println("\nAfter (BFS):");
        printBoard(board5);

        // Test Case 6: L-shaped region touching border
        System.out.println("\n=== Test 6: L-Shaped Border Region ===");
        char[][] board6 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'X', 'X'},
            {'X', 'O', 'X', 'X'},
            {'X', 'O', 'O', 'O'}
        };
        System.out.println("Before:");
        printBoard(board6);

        solution.solve(board6);

        System.out.println("\nAfter:");
        printBoard(board6);
        System.out.println("Expected: No change (L-shape touches border)");

        // Test Case 7: Multiple separate regions
        System.out.println("\n=== Test 7: Multiple Regions ===");
        char[][] board7 = {
            {'X', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'X', 'O', 'X'},
            {'X', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'X', 'O', 'X'},
            {'X', 'X', 'X', 'X', 'O'}
        };
        System.out.println("Before:");
        printBoard(board7);

        solution.solve(board7);

        System.out.println("\nAfter:");
        printBoard(board7);
        System.out.println("Expected: All inner O's captured, corner O stays");
    }
}
