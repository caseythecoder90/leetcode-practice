package leetcode.graph.problems.NumberOfIslands;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 200. Number of Islands
 *
 * Given an m x n 2D binary grid which represents a map of '1's (land) and '0's (water),
 * return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands
 * horizontally or vertically. You may assume all four edges of the grid are
 * surrounded by water.
 *
 * Pattern: Connected Components on Grid (Graph Traversal)
 *
 * Key Insight: Each island is a connected component of '1's. We iterate through
 * the grid, and when we find an unvisited '1', we explore all connected '1's
 * (using DFS or BFS), marking them as visited ("sinking" them), then increment
 * our island count.
 */
public class NumberOfIslands {

    /**
     * Approach 1: DFS (Depth-First Search) - Recommended
     *
     * Time Complexity: O(m * n) - visit each cell at most once
     * Space Complexity: O(m * n) - worst case recursion depth
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        // Iterate through every cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // When we find unvisited land, we've found a new island
                if (grid[i][j] == '1') {
                    // Sink the entire island (mark all connected land as visited)
                    dfs(grid, i, j);
                    // Increment island count
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * DFS helper to sink an island by marking all connected '1's as '0's.
     *
     * @param grid The grid to explore
     * @param row  Current row position
     * @param col  Current column position
     */
    private void dfs(char[][] grid, int row, int col) {
        // Base case: check boundaries and if water/already visited
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length ||
            grid[row][col] == '0') {
            return;
        }

        // Mark current cell as visited (sink it)
        grid[row][col] = '0';

        // Explore all 4 directions (up, down, left, right)
        dfs(grid, row + 1, col);  // Down
        dfs(grid, row - 1, col);  // Up
        dfs(grid, row, col + 1);  // Right
        dfs(grid, row, col - 1);  // Left
    }

    /**
     * Approach 2: DFS with Directions Array (cleaner version)
     */
    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int numIslandsDfsClean(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfsClean(grid, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfsClean(char[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length ||
            grid[row][col] == '0') {
            return;
        }

        grid[row][col] = '0';

        for (int[] dir : directions) {
            dfsClean(grid, row + dir[0], col + dir[1]);
        }
    }

    /**
     * Approach 3: BFS (Breadth-First Search)
     *
     * Time Complexity: O(m * n)
     * Space Complexity: O(min(m, n)) - queue size bounded by smaller dimension
     */
    public int numIslandsBfs(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
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
        grid[row][col] = '0';  // Mark as visited immediately when adding to queue

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int currRow = cell[0];
            int currCol = cell[1];

            for (int[] dir : dirs) {
                int newRow = currRow + dir[0];
                int newCol = currCol + dir[1];

                // Check bounds and if it's land
                if (newRow >= 0 && newRow < grid.length &&
                    newCol >= 0 && newCol < grid[0].length &&
                    grid[newRow][newCol] == '1') {
                    // Mark as visited before adding to queue (prevents duplicates)
                    grid[newRow][newCol] = '0';
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }

    /**
     * Approach 4: DFS without modifying input (using visited array)
     * Use this when you cannot modify the input grid.
     */
    public int numIslandsNoModify(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    dfsWithVisited(grid, i, j, visited);
                    count++;
                }
            }
        }

        return count;
    }

    private void dfsWithVisited(char[][] grid, int row, int col, boolean[][] visited) {
        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length ||
            grid[row][col] == '0' || visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        dfsWithVisited(grid, row + 1, col, visited);
        dfsWithVisited(grid, row - 1, col, visited);
        dfsWithVisited(grid, row, col + 1, visited);
        dfsWithVisited(grid, row, col - 1, visited);
    }

    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();

        // Test Case 1: Single island
        char[][] grid1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        System.out.println("Test 1 - Expected: 1");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid1)));

        // Test Case 2: Three islands
        char[][] grid2 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        System.out.println("\nTest 2 - Expected: 3");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid2)));
        System.out.println("BFS Result: " + solution.numIslandsBfs(deepCopy(grid2)));
        System.out.println("No Modify Result: " + solution.numIslandsNoModify(deepCopy(grid2)));

        // Test Case 3: All water
        char[][] grid3 = {
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        System.out.println("\nTest 3 (All water) - Expected: 0");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid3)));

        // Test Case 4: All land
        char[][] grid4 = {
            {'1', '1'},
            {'1', '1'}
        };
        System.out.println("\nTest 4 (All land) - Expected: 1");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid4)));

        // Test Case 5: Single cell island
        char[][] grid5 = {
            {'1'}
        };
        System.out.println("\nTest 5 (Single cell) - Expected: 1");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid5)));

        // Test Case 6: Checkerboard pattern (no adjacent land)
        char[][] grid6 = {
            {'1', '0', '1'},
            {'0', '1', '0'},
            {'1', '0', '1'}
        };
        System.out.println("\nTest 6 (Checkerboard) - Expected: 5");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid6)));

        // Test Case 7: L-shaped island
        char[][] grid7 = {
            {'1', '0', '0'},
            {'1', '0', '0'},
            {'1', '1', '1'}
        };
        System.out.println("\nTest 7 (L-shape) - Expected: 1");
        System.out.println("DFS Result: " + solution.numIslands(deepCopy(grid7)));
    }

    /**
     * Helper to create a deep copy of the grid for testing multiple approaches
     */
    private static char[][] deepCopy(char[][] grid) {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }
}
