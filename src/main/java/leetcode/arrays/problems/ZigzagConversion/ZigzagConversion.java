package leetcode.arrays.problems.ZigzagConversion;

import java.util.ArrayList;
import java.util.List;

public class ZigzagConversion {
    
    /**
     * SOLUTION 1: StringBuilder Array Approach (Most Intuitive)
     * 
     * Strategy: Create an array of StringBuilder, one for each row.
     * Simulate the zigzag movement and append characters to appropriate rows.
     * 
     * Time: O(n), Space: O(n)
     */
    public String convert(String s, int numRows) {
        // Edge case: if only 1 row or string length <= numRows, return original string
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        // Create StringBuilder for each row
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        
        int currentRow = 0;
        boolean goingDown = false;
        
        // Process each character
        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);
            
            // Change direction when we hit top or bottom row
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            // Move to next row
            currentRow += goingDown ? 1 : -1;
        }
        
        // Concatenate all rows
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
        
        return result.toString();
    }
    
    /**
     * SOLUTION 2: Mathematical Pattern Approach (More Efficient)
     * 
     * Strategy: Calculate the exact positions where characters should appear
     * without actually creating the zigzag structure.
     * 
     * Time: O(n), Space: O(1) extra space
     */
    public String convertMath(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        StringBuilder result = new StringBuilder();
        int cycleLength = 2 * numRows - 2;
        
        for (int row = 0; row < numRows; row++) {
            for (int i = 0; i + row < s.length(); i += cycleLength) {
                // Add character from main vertical line
                result.append(s.charAt(i + row));
                
                // Add character from diagonal (if it exists and isn't a duplicate)
                if (row != 0 && row != numRows - 1 && i + cycleLength - row < s.length()) {
                    result.append(s.charAt(i + cycleLength - row));
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * SOLUTION 3: List-based Approach (Alternative Implementation)
     */
    public String convertList(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        
        List<List<Character>> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new ArrayList<>());
        }
        
        int currentRow = 0;
        boolean goingDown = false;
        
        for (char c : s.toCharArray()) {
            rows.get(currentRow).add(c);
            
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            currentRow += goingDown ? 1 : -1;
        }
        
        StringBuilder result = new StringBuilder();
        for (List<Character> row : rows) {
            for (char c : row) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Visualization helper method for understanding the pattern
     */
    public void visualizeZigzag(String s, int numRows) {
        System.out.println("=== ZIGZAG VISUALIZATION ===");
        System.out.println("String: " + s);
        System.out.println("NumRows: " + numRows);
        System.out.println();
        
        if (numRows == 1) {
            System.out.println(s);
            return;
        }
        
        // Create 2D array to visualize the pattern
        int cols = s.length(); // Over-allocate columns for safety
        char[][] grid = new char[numRows][cols];
        
        // Fill with spaces
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = ' ';
            }
        }
        
        // Place characters in zigzag pattern
        int currentRow = 0, currentCol = 0;
        boolean goingDown = false;
        
        for (char c : s.toCharArray()) {
            grid[currentRow][currentCol] = c;
            
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            if (goingDown) {
                currentRow++;
            } else {
                currentRow--;
                currentCol++;
            }
            
            if (currentRow == 0) {
                currentCol++;
            }
        }
        
        // Print the grid
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < cols && j < s.length() + numRows; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println("Result: " + convert(s, numRows));
        System.out.println();
    }

    public static void main(String[] args) {
        ZigzagConversion solution = new ZigzagConversion();
        
        // Test Case 1: Example from problem
        System.out.println("=== TEST CASE 1 ===");
        String s1 = "PAYPALISHIRING";
        int numRows1 = 3;
        solution.visualizeZigzag(s1, numRows1);
        System.out.println("StringBuilder: " + solution.convert(s1, numRows1));
        System.out.println("Mathematical:  " + solution.convertMath(s1, numRows1));
        System.out.println("Expected:      PAHNAPLSIIGYIR");
        System.out.println();
        
        // Test Case 2: 4 rows
        System.out.println("=== TEST CASE 2 ===");
        String s2 = "PAYPALISHIRING";
        int numRows2 = 4;
        solution.visualizeZigzag(s2, numRows2);
        System.out.println("StringBuilder: " + solution.convert(s2, numRows2));
        System.out.println("Mathematical:  " + solution.convertMath(s2, numRows2));
        System.out.println("Expected:      PINALSIGYAHRPI");
        System.out.println();
        
        // Test Case 3: Single row
        System.out.println("=== TEST CASE 3 ===");
        String s3 = "A";
        int numRows3 = 1;
        solution.visualizeZigzag(s3, numRows3);
        System.out.println("Result: " + solution.convert(s3, numRows3));
        System.out.println("Expected: A");
        System.out.println();
        
        // Test Case 4: Edge case - numRows > string length
        System.out.println("=== TEST CASE 4 ===");
        String s4 = "ABC";
        int numRows4 = 5;
        solution.visualizeZigzag(s4, numRows4);
        System.out.println("Result: " + solution.convert(s4, numRows4));
        System.out.println("Expected: ABC");
        System.out.println();
        
        // Test Case 5: Understanding the cycle
        System.out.println("=== CYCLE PATTERN ANALYSIS ===");
        System.out.println("For numRows = 3, cycle length = 2*3-2 = 4");
        System.out.println("Pattern: 0→1→2→1 (repeat)");
        System.out.println("For numRows = 4, cycle length = 2*4-2 = 6");
        System.out.println("Pattern: 0→1→2→3→2→1 (repeat)");
        
        // Demonstrate cycle with simple string
        String cycle = "0123456789";
        System.out.println("\nCycle demo with '" + cycle + "', numRows=3:");
        solution.visualizeZigzag(cycle, 3);
    }
}