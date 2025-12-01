package leetcode.matrix.problems.RotateImage;

/**
 * LeetCode 48: Rotate Image
 * Difficulty: Medium
 *
 * Rotate an n x n matrix by 90 degrees clockwise in-place.
 *
 * Approach: Transpose + Reverse Rows
 * 1. Transpose the matrix (swap matrix[i][j] with matrix[j][i])
 * 2. Reverse each row
 *
 * Time Complexity: O(n²) where n is the dimension
 * Space Complexity: O(1) - in-place rotation
 */
public class Solution {

    /**
     * Rotates the matrix 90 degrees clockwise in-place.
     *
     * Algorithm:
     * 1. Transpose: Convert rows to columns
     * 2. Reverse: Flip each row horizontally
     *
     * @param matrix n x n 2D matrix to rotate
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix
        // Swap matrix[i][j] with matrix[j][i] for all i < j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Swap elements across the diagonal
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            reverseRow(matrix[i]);
        }
    }

    /**
     * Reverses a single row in-place.
     *
     * @param row array to reverse
     */
    private void reverseRow(int[] row) {
        int left = 0;
        int right = row.length - 1;

        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Alternative approach: Layer-by-layer rotation
     * Rotates elements in concentric layers from outside to inside.
     * More complex but rotates in a single pass.
     *
     * @param matrix n x n 2D matrix to rotate
     */
    public void rotateLayerByLayer(int[][] matrix) {
        int n = matrix.length;

        // Process each layer
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;

            // Rotate elements in current layer
            for (int i = first; i < last; i++) {
                int offset = i - first;

                // Save top element
                int top = matrix[first][i];

                // Left → Top
                matrix[first][i] = matrix[last - offset][first];

                // Bottom → Left
                matrix[last - offset][first] = matrix[last][last - offset];

                // Right → Bottom
                matrix[last][last - offset] = matrix[i][last];

                // Top → Right
                matrix[i][last] = top;
            }
        }
    }

    /**
     * Helper method to print matrix for visualization.
     */
    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("[");
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j]);
                if (j < row.length - 1) System.out.print(",");
            }
            System.out.print("]");
        }
        System.out.println();
    }

    /**
     * Helper method to create a deep copy of matrix for testing.
     */
    private int[][] copyMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = matrix[i][j];
            }
        }
        return copy;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: 3x3 matrix
        System.out.println("Test Case 1: 3x3 matrix");
        int[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.print("Input:  ");
        solution.printMatrix(matrix1);
        solution.rotate(matrix1);
        System.out.print("Output: ");
        solution.printMatrix(matrix1);
        System.out.println("Expected: [[7,4,1],[8,5,2],[9,6,3]]");
        System.out.println();

        // Test Case 2: 4x4 matrix
        System.out.println("Test Case 2: 4x4 matrix");
        int[][] matrix2 = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        System.out.print("Input:  ");
        solution.printMatrix(matrix2);
        solution.rotate(matrix2);
        System.out.print("Output: ");
        solution.printMatrix(matrix2);
        System.out.println("Expected: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]");
        System.out.println();

        // Test Case 3: 1x1 matrix (edge case)
        System.out.println("Test Case 3: 1x1 matrix (edge case)");
        int[][] matrix3 = {{1}};
        System.out.print("Input:  ");
        solution.printMatrix(matrix3);
        solution.rotate(matrix3);
        System.out.print("Output: ");
        solution.printMatrix(matrix3);
        System.out.println("Expected: [[1]]");
        System.out.println();

        // Test Case 4: 2x2 matrix
        System.out.println("Test Case 4: 2x2 matrix");
        int[][] matrix4 = {{1,2},{3,4}};
        System.out.print("Input:  ");
        solution.printMatrix(matrix4);
        solution.rotate(matrix4);
        System.out.print("Output: ");
        solution.printMatrix(matrix4);
        System.out.println("Expected: [[3,1],[4,2]]");
        System.out.println();

        // Test Case 5: Layer-by-layer approach
        System.out.println("Test Case 5: Layer-by-layer approach on 3x3");
        int[][] matrix5 = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.print("Input:  ");
        solution.printMatrix(matrix5);
        solution.rotateLayerByLayer(matrix5);
        System.out.print("Output: ");
        solution.printMatrix(matrix5);
        System.out.println("Expected: [[7,4,1],[8,5,2],[9,6,3]]");
        System.out.println();

        // Test Case 6: Matrix with negative numbers
        System.out.println("Test Case 6: Matrix with negative numbers");
        int[][] matrix6 = {{-1,-2},{-3,-4}};
        System.out.print("Input:  ");
        solution.printMatrix(matrix6);
        solution.rotate(matrix6);
        System.out.print("Output: ");
        solution.printMatrix(matrix6);
        System.out.println("Expected: [[-3,-1],[-4,-2]]");
    }
}
