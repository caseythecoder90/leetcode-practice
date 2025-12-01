package leetcode.matrix.problems.SpiralMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 54 - Spiral Matrix
 *
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 *
 * Example 1:
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,2,3,6,9,8,7,4,5]
 *
 * Example 2:
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 */
public class Solution {

    /**
     * Approach: Layer-by-layer traversal with boundary shrinking
     *
     * Key insight: Process the matrix like peeling an onion
     * - Start from outermost layer, move inward
     * - Each layer is traversed: right → down → left → up
     * - After each complete layer, shrink boundaries
     *
     * Time: O(m * n) - visit each element exactly once
     * Space: O(1) - excluding output array
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return result;
        }

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // Traverse right: top row from left to right
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++; // Move top boundary down

            // Traverse down: right column from top to bottom
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--; // Move right boundary left

            // Traverse left: bottom row from right to left (if still valid)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--; // Move bottom boundary up
            }

            // Traverse up: left column from bottom to top (if still valid)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++; // Move left boundary right
            }
        }

        return result;
    }

    /**
     * Alternative Approach: Direction-based traversal
     *
     * Uses direction vectors and visited array to track position
     * More intuitive but requires O(m*n) extra space
     */
    public List<Integer> spiralOrderWithDirections(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return result;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];

        // Direction vectors: right, down, left, up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};

        int row = 0, col = 0, dir = 0;

        for (int i = 0; i < rows * cols; i++) {
            result.add(matrix[row][col]);
            visited[row][col] = true;

            // Calculate next position
            int nextRow = row + dr[dir];
            int nextCol = col + dc[dir];

            // Check if we need to turn (hit boundary or visited cell)
            if (nextRow < 0 || nextRow >= rows ||
                nextCol < 0 || nextCol >= cols ||
                visited[nextRow][nextCol]) {
                // Turn clockwise (change direction)
                dir = (dir + 1) % 4;
                nextRow = row + dr[dir];
                nextCol = col + dc[dir];
            }

            row = nextRow;
            col = nextCol;
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test case 1: 3x3 matrix
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test 1 - 3x3 matrix:");
        System.out.println("Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]");
        System.out.println("Result:   " + solution.spiralOrder(matrix1));
        System.out.println();

        // Test case 2: 3x4 matrix
        int[][] matrix2 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        System.out.println("Test 2 - 3x4 matrix:");
        System.out.println("Expected: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]");
        System.out.println("Result:   " + solution.spiralOrder(matrix2));
        System.out.println();

        // Test case 3: Single row
        int[][] matrix3 = {{1, 2, 3, 4}};
        System.out.println("Test 3 - Single row:");
        System.out.println("Expected: [1, 2, 3, 4]");
        System.out.println("Result:   " + solution.spiralOrder(matrix3));
        System.out.println();

        // Test case 4: Single column
        int[][] matrix4 = {{1}, {2}, {3}, {4}};
        System.out.println("Test 4 - Single column:");
        System.out.println("Expected: [1, 2, 3, 4]");
        System.out.println("Result:   " + solution.spiralOrder(matrix4));
        System.out.println();

        // Test case 5: Single element
        int[][] matrix5 = {{5}};
        System.out.println("Test 5 - Single element:");
        System.out.println("Expected: [5]");
        System.out.println("Result:   " + solution.spiralOrder(matrix5));
        System.out.println();

        // Test case 6: 4x4 matrix
        int[][] matrix6 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        System.out.println("Test 6 - 4x4 matrix:");
        System.out.println("Expected: [1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10]");
        System.out.println("Result:   " + solution.spiralOrder(matrix6));
        System.out.println();

        // Test direction-based approach
        System.out.println("Test 7 - Direction approach on 3x3:");
        System.out.println("Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]");
        System.out.println("Result:   " + solution.spiralOrderWithDirections(matrix1));
    }
}
