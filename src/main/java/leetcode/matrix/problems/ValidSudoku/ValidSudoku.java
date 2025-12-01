package leetcode.matrix.problems.ValidSudoku;

import java.util.*;

/**
 * LeetCode 36: Valid Sudoku
 * Difficulty: Medium
 * 
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated.
 * Rules:
 * 1. Each row must contain digits 1-9 without repetition
 * 2. Each column must contain digits 1-9 without repetition  
 * 3. Each 3x3 sub-box must contain digits 1-9 without repetition
 * 
 * Time Complexity: O(1) - fixed 9x9 board = constant 81 cells
 * Space Complexity: O(1) - fixed size HashSets (max 9 elements each)
 */
public class ValidSudoku {
    
    /**
     * APPROACH 1: Three-Pass Solution (Most Intuitive)
     * Separate validation for rows, columns, and boxes
     * Clear and easy to understand, good for interviews
     */
    public boolean isValidSudoku_ThreePass(char[][] board) {
        // Validate all rows
        for (int i = 0; i < 9; i++) {
            Set<Character> seen = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                char current = board[i][j];
                if (current != '.') {
                    if (!seen.add(current)) {
                        return false;
                    }
                }
            }
        }
        
        // Validate all columns
        for (int j = 0; j < 9; j++) {
            Set<Character> seen = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                char current = board[i][j];
                if (current != '.') {
                    if (!seen.add(current)) {
                        return false;
                    }
                }
            }
        }
        
        // Validate all 3x3 boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                Set<Character> seen = new HashSet<>();
                
                // Iterate through each cell in the 3x3 box
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        // Calculate actual board coordinates
                        int row = boxRow * 3 + i;
                        int col = boxCol * 3 + j;  // KEY: Use boxCol and j, not boxRow!
                        
                        char current = board[row][col];
                        if (current != '.') {
                            if (!seen.add(current)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * APPROACH 2: One-Pass Solution (Most Efficient)
     * Validate all constraints in a single iteration
     * Uses clever encoding to track all positions simultaneously
     */
    public boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char current = board[i][j];
                
                if (current != '.') {
                    // Create unique strings for row, column, and box
                    String row = current + " in row " + i;
                    String col = current + " in col " + j;
                    String box = current + " in box " + (i/3) + "-" + (j/3);
                    
                    // Try to add all three - if any fail, we have a duplicate
                    if (!seen.add(row) || !seen.add(col) || !seen.add(box)) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * APPROACH 3: Array-Based Solution (Best for Interviews)
     * Uses boolean arrays instead of HashSets for tracking
     * Often faster due to better cache locality
     */
    public boolean isValidSudoku_Arrays(char[][] board) {
        // 9 rows, 9 columns, 9 boxes - each can have digits 1-9
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][] boxes = new boolean[9][9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char current = board[i][j];
                
                if (current != '.') {
                    int num = current - '1';  // Convert '1'-'9' to 0-8 index
                    int boxIndex = (i / 3) * 3 + (j / 3);  // Calculate which box
                    
                    // Check if already seen
                    if (rows[i][num] || cols[j][num] || boxes[boxIndex][num]) {
                        return false;
                    }
                    
                    // Mark as seen
                    rows[i][num] = true;
                    cols[j][num] = true;
                    boxes[boxIndex][num] = true;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Helper: Calculate box index from row and column
     * Box numbering (left-to-right, top-to-bottom):
     * 0 1 2
     * 3 4 5
     * 6 7 8
     */
    private int getBoxIndex(int row, int col) {
        return (row / 3) * 3 + (col / 3);
    }
}
