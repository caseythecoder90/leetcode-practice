package leetcode.monotonicstack.problems.DailyTemperatures;

import java.util.*;

/**
 * LeetCode 739: Daily Temperatures - Brute Force Approach
 * 
 * This is the SIMPLE solution that's easy to understand but not optimal.
 * Good for understanding the problem before learning advanced techniques.
 * 
 * Time Complexity: O(n²) - Nested loops
 * Space Complexity: O(1) - Only result array (not counting output)
 * 
 * When to use: Only for very small arrays or when simplicity is more important than efficiency.
 */
public class BruteForceSolution {
    
    /**
     * Brute Force Approach - Simple but Inefficient
     * 
     * Key Insight: For each day, search forward to find the next warmer temperature.
     * This is the most straightforward approach that directly follows the problem description.
     * 
     * Algorithm:
     * 1. For each temperature at index i:
     *    - Search forward from i+1 to end of array
     *    - Find first temperature > temperatures[i]
     *    - If found, store distance (j - i)
     *    - If not found, leave as 0 (default value)
     * 
     * Pros:
     * - Very easy to understand and implement
     * - No additional data structures needed
     * - Minimal space usage
     * 
     * Cons:
     * - O(n²) time complexity - too slow for large inputs
     * - Redundant comparisons - same elements checked multiple times
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];  // Defaults to 0
        
        // For each day, search for next warmer day
        for (int i = 0; i < n; i++) {
            // Search forward from next day
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;  // Distance to warmer day
                    break;  // Found next warmer day, stop searching
                }
            }
            // If no warmer day found, result[i] remains 0
        }
        
        return result;
    }
    
    /**
     * Enhanced version with detailed tracing to show the search process
     */
    public int[] dailyTemperaturesWithTrace(int[] temperatures) {
        System.out.println("=== BRUTE FORCE DETAILED TRACE ===");
        System.out.println("Input temperatures: " + Arrays.toString(temperatures));
        System.out.println("Strategy: For each day, search forward until we find a warmer day\n");
        
        int n = temperatures.length;
        int[] result = new int[n];
        int totalComparisons = 0;
        
        for (int i = 0; i < n; i++) {
            System.out.printf("Day %d (temp = %d): Searching for next warmer day...%n", 
                            i, temperatures[i]);
            
            boolean found = false;
            for (int j = i + 1; j < n; j++) {
                totalComparisons++;
                System.out.printf("  Check day %d (temp = %d): ", j, temperatures[j]);
                
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    System.out.printf("✓ WARMER! Distance = %d%n", result[i]);
                    found = true;
                    break;
                } else {
                    System.out.printf("✗ Not warmer (≤ %d)%n", temperatures[i]);
                }
            }
            
            if (!found) {
                System.out.printf("  No warmer day found, result[%d] = 0%n", i);
            }
            
            System.out.printf("  Result so far: %s%n%n", Arrays.toString(result));
        }
        
        System.out.printf("Total comparisons made: %d%n", totalComparisons);
        System.out.printf("Theoretical maximum: %d (n²/2 for n=%d)%n", n*n/2, n);
        System.out.println("Final result: " + Arrays.toString(result));
        
        return result;
    }
    
    /**
     * Optimized brute force - early termination when temperature is very high
     * Still O(n²) worst case, but can be faster in practice
     */
    public int[] dailyTemperaturesOptimized(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            // Early termination: if current temp is already very high,
            // unlikely to find much warmer temperatures
            if (temperatures[i] >= 100) {  // Assuming temperature range 0-100
                result[i] = 0;
                continue;
            }
            
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;
                }
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        BruteForceSolution solution = new BruteForceSolution();
        
        // Test Case 1: Small example for tracing
        System.out.println("=== Test Case 1: Small Example for Tracing ===");
        int[] temperatures1 = {73, 74, 75, 71};
        solution.dailyTemperaturesWithTrace(temperatures1);
        System.out.println();
        
        // Test Case 2: Classic example
        System.out.println("=== Test Case 2: Classic Example ===");
        int[] temperatures2 = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Input: " + Arrays.toString(temperatures2));
        System.out.println("Expected: [1, 1, 4, 2, 1, 1, 0, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures2)));
        System.out.println();
        
        // Test Case 3: Worst case scenario (decreasing sequence)
        System.out.println("=== Test Case 3: Worst Case (Decreasing) ===");
        int[] temperatures3 = {90, 80, 70, 60, 50};
        System.out.println("Input: " + Arrays.toString(temperatures3));
        System.out.println("Expected: [0, 0, 0, 0, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures3)));
        System.out.println("Note: This requires maximum comparisons (n²/2)");
        System.out.println();
        
        // Test Case 4: Best case scenario (increasing sequence)
        System.out.println("=== Test Case 4: Best Case (Increasing) ===");
        int[] temperatures4 = {50, 60, 70, 80, 90};
        System.out.println("Input: " + Arrays.toString(temperatures4));
        System.out.println("Expected: [1, 1, 1, 1, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures4)));
        System.out.println("Note: This requires minimum comparisons (n-1)");
        System.out.println();
        
        // Performance analysis
        System.out.println("=== PERFORMANCE ANALYSIS ===");
        
        // Measure time for different input sizes
        int[] sizes = {10, 100, 1000};
        
        for (int size : sizes) {
            // Create test array (worst case - decreasing)
            int[] testArray = new int[size];
            for (int i = 0; i < size; i++) {
                testArray[i] = size - i;  // Decreasing sequence
            }
            
            long startTime = System.nanoTime();
            solution.dailyTemperatures(testArray);
            long endTime = System.nanoTime();
            
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.printf("Array size %d: %.2f ms%n", size, timeMs);
        }
        
        System.out.println("\n=== WHEN TO USE BRUTE FORCE ===");
        System.out.println("✓ Good for:");
        System.out.println("  - Very small arrays (n < 100)");
        System.out.println("  - When simplicity is more important than efficiency");
        System.out.println("  - When memory is extremely limited");
        System.out.println("  - For understanding the problem before optimization");
        System.out.println();
        System.out.println("✗ Avoid for:");
        System.out.println("  - Large arrays (n > 1000)");
        System.out.println("  - Production code with performance requirements");
        System.out.println("  - When O(n) solutions are available");
        
        System.out.println("\n=== COMPLEXITY ANALYSIS ===");
        System.out.println("Time Complexity: O(n²)");
        System.out.println("  - Outer loop: n iterations");
        System.out.println("  - Inner loop: up to n iterations each");
        System.out.println("  - Total: n × n = n² operations");
        System.out.println();
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only using result array (required for output)");
        System.out.println("  - No additional data structures");
        System.out.println("  - Constant extra space");
    }
}