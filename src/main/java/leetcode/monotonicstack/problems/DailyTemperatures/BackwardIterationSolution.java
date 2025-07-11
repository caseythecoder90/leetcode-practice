package leetcode.monotonicstack.problems.DailyTemperatures;

import java.util.*;

/**
 * LeetCode 739: Daily Temperatures - Backward Iteration Approach
 * 
 * This is the SPACE-OPTIMIZED solution with clever use of previously computed results.
 * Achieves O(n) time complexity with O(1) extra space.
 * 
 * Time Complexity: O(n) - Amortized analysis shows each element is visited constant times
 * Space Complexity: O(1) - Only result array (not counting output)
 * 
 * When to use: When you need O(n) time but want to minimize space usage.
 */
public class BackwardIterationSolution {
    
    /**
     * Backward Iteration Approach - Space-Optimized Genius
     * 
     * Key Insight: Process from right to left, using previously computed results
     * to jump ahead efficiently when searching for next warmer temperature.
     * 
     * Why this works:
     * 1. If temperatures[j] <= temperatures[i], then temperatures[j] can't be the answer
     * 2. If result[j] == 0, then no day after j is warmer than j, so no day after j can be warmer than i
     * 3. If result[j] > 0, we can jump directly to j + result[j] (the next warmer day after j)
     * 4. This jumping eliminates redundant comparisons
     * 
     * Algorithm:
     * 1. Start from second-to-last element, moving backward (rightmost already has answer = 0)
     * 2. For each element at index i:
     *    - Start checking from i+1
     *    - If temperatures[j] <= temperatures[i]:
     *      - If result[j] == 0, no warmer day exists beyond j
     *      - Otherwise, jump to j + result[j] (next potential warmer day)
     *    - If temperatures[j] > temperatures[i], found the answer
     * 3. Continue until all elements processed
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];  // Last element already 0
        
        // Process from right to left (second-to-last element)
        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;  // Start checking from next day
            
            // Use previously computed results to jump ahead efficiently
            while (j < n && temperatures[j] <= temperatures[i]) {
                if (result[j] == 0) {
                    j = n;  // No warmer day found beyond j, exit loop
                } else {
                    j += result[j];  // Jump to next potential warmer day
                }
            }
            
            // If we found a warmer day within bounds
            if (j < n) {
                result[i] = j - i;
            }
            // Otherwise result[i] remains 0 (no warmer day)
        }
        
        return result;
    }
    
    /**
     * Enhanced version with detailed tracing to show the jumping mechanism
     */
    public int[] dailyTemperaturesWithTrace(int[] temperatures) {
        System.out.println("=== BACKWARD ITERATION DETAILED TRACE ===");
        System.out.println("Input temperatures: " + Arrays.toString(temperatures));
        System.out.println("Strategy: Process right-to-left, use previous results to jump ahead\n");
        
        int n = temperatures.length;
        int[] result = new int[n];
        int totalJumps = 0;
        int totalComparisons = 0;
        
        System.out.printf("Starting with result[%d] = 0 (last element has no next day)%n", n-1);
        System.out.printf("Initial result: %s%n%n", Arrays.toString(result));
        
        for (int i = n - 2; i >= 0; i--) {
            System.out.printf("Day %d (temp = %d): Looking for next warmer day...%n", 
                            i, temperatures[i]);
            
            int j = i + 1;
            System.out.printf("  Starting search from day %d%n", j);
            
            while (j < n && temperatures[j] <= temperatures[i]) {
                totalComparisons++;
                System.out.printf("  Day %d (temp = %d) ≤ %d: ", j, temperatures[j], temperatures[i]);
                
                if (result[j] == 0) {
                    System.out.printf("result[%d] = 0, no warmer days beyond → stop search%n", j);
                    j = n;  // Exit loop
                } else {
                    int nextJ = j + result[j];
                    System.out.printf("result[%d] = %d → jump to day %d%n", j, result[j], nextJ);
                    j = nextJ;
                    totalJumps++;
                }
            }
            
            if (j < n) {
                result[i] = j - i;
                System.out.printf("  ✓ Found warmer day %d (temp = %d): distance = %d%n", 
                                j, temperatures[j], result[i]);
            } else {
                System.out.printf("  ✗ No warmer day found: result[%d] = 0%n", i);
            }
            
            System.out.printf("  Result: %s%n%n", Arrays.toString(result));
        }
        
        System.out.printf("Total comparisons: %d%n", totalComparisons);
        System.out.printf("Total jumps: %d%n", totalJumps);
        System.out.printf("Efficiency gain: avoided %d comparisons through jumping%n", totalJumps);
        System.out.println("Final result: " + Arrays.toString(result));
        
        return result;
    }
    
    /**
     * Step-by-step manual trace for educational purposes
     */
    public void explainAlgorithm(int[] temperatures) {
        System.out.println("=== ALGORITHM EXPLANATION ===");
        System.out.println("Input: " + Arrays.toString(temperatures));
        
        System.out.println("\nKey Insights:");
        System.out.println("1. If temp[j] ≤ temp[i], then temp[j] can't be the answer for i");
        System.out.println("2. If result[j] = 0, no day after j is warmer than j");
        System.out.println("3. If result[j] > 0, we can jump to day j + result[j]");
        System.out.println("4. This jumping eliminates redundant comparisons");
        
        System.out.println("\nWhy jumps work:");
        System.out.println("- If temp[j] ≤ temp[i] and result[j] = k (k > 0)");
        System.out.println("- Then temp[j+1], temp[j+2], ..., temp[j+k-1] are all ≤ temp[j] ≤ temp[i]");
        System.out.println("- So we can skip directly to day j+k");
        
        System.out.println("\nVisual example with [73, 74, 75, 71, 69, 72]:");
        System.out.println("Indices:     0   1   2   3   4   5");
        System.out.println("Temps:      73  74  75  71  69  72");
        System.out.println();
        
        System.out.println("Process i=4 (temp=69):");
        System.out.println("  j=5: 72 > 69 ✓ → result[4] = 1");
        System.out.println();
        
        System.out.println("Process i=3 (temp=71):");
        System.out.println("  j=4: 69 ≤ 71, result[4]=1 → jump to j=4+1=5");
        System.out.println("  j=5: 72 > 71 ✓ → result[3] = 2");
        System.out.println();
        
        System.out.println("Process i=2 (temp=75):");
        System.out.println("  j=3: 71 ≤ 75, result[3]=2 → jump to j=3+2=5");
        System.out.println("  j=5: 72 ≤ 75, result[5]=0 → no warmer day");
        System.out.println("  result[2] = 0");
    }
    
    public static void main(String[] args) {
        BackwardIterationSolution solution = new BackwardIterationSolution();
        
        // Test Case 1: Detailed trace
        System.out.println("=== Test Case 1: Detailed Trace ===");
        int[] temperatures1 = {73, 74, 75, 71, 69, 72};
        solution.dailyTemperaturesWithTrace(temperatures1);
        System.out.println();
        
        // Algorithm explanation
        solution.explainAlgorithm(temperatures1);
        System.out.println();
        
        // Test Case 2: Classic example
        System.out.println("=== Test Case 2: Classic Example ===");
        int[] temperatures2 = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Input: " + Arrays.toString(temperatures2));
        System.out.println("Expected: [1, 1, 4, 2, 1, 1, 0, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures2)));
        System.out.println();
        
        // Test Case 3: Best case for jumps
        System.out.println("=== Test Case 3: Maximum Jump Efficiency ===");
        int[] temperatures3 = {100, 99, 98, 97, 96, 95, 101};
        System.out.println("Input: " + Arrays.toString(temperatures3));
        System.out.println("This shows maximum jump efficiency:");
        solution.dailyTemperaturesWithTrace(temperatures3);
        System.out.println();
        
        // Test Case 4: No jumps needed (increasing)
        System.out.println("=== Test Case 4: No Jumps Needed ===");
        int[] temperatures4 = {30, 40, 50, 60, 70};
        System.out.println("Input: " + Arrays.toString(temperatures4));
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures4)));
        System.out.println("Note: Each day immediately finds next warmer day");
        System.out.println();
        
        // Performance comparison
        System.out.println("=== PERFORMANCE COMPARISON ===");
        
        // Create worst-case scenario for both approaches
        int size = 1000;
        int[] worstCase = new int[size];
        for (int i = 0; i < size - 1; i++) {
            worstCase[i] = 50;  // All same temperature except last
        }
        worstCase[size - 1] = 51;  // Last one is warmer
        
        long startTime = System.nanoTime();
        solution.dailyTemperatures(worstCase);
        long endTime = System.nanoTime();
        
        double timeMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("Backward iteration (size %d): %.2f ms%n", size, timeMs);
        
        System.out.println("\n=== COMPLEXITY ANALYSIS ===");
        System.out.println("Time Complexity: O(n) amortized");
        System.out.println("  - Each element is visited at most a constant number of times");
        System.out.println("  - Jumps prevent redundant comparisons");
        System.out.println("  - Total work across all iterations is O(n)");
        System.out.println();
        System.out.println("Space Complexity: O(1)");
        System.out.println("  - Only result array (required for output)");
        System.out.println("  - No additional data structures like stack");
        System.out.println("  - Truly constant extra space");
        
        System.out.println("\n=== WHEN TO USE THIS APPROACH ===");
        System.out.println("✓ Perfect for:");
        System.out.println("  - Memory-constrained environments");
        System.out.println("  - When O(n) time with O(1) space is required");
        System.out.println("  - Embedded systems or competitive programming");
        System.out.println();
        System.out.println("✗ Consider alternatives when:");
        System.out.println("  - Code readability is more important than space optimization");
        System.out.println("  - Team is unfamiliar with the jumping technique");
        System.out.println("  - Monotonic stack approach is more intuitive for the problem context");
        
        System.out.println("\n=== KEY INSIGHTS FOR MASTERY ===");
        System.out.println("1. This technique can be applied to other 'next element' problems");
        System.out.println("2. The jumping mechanism is the key optimization");
        System.out.println("3. Understanding WHY jumps work is crucial for adaptation");
        System.out.println("4. This approach shows how to optimize space while maintaining time complexity");
        System.out.println("5. It demonstrates the power of using previously computed results");
    }
}