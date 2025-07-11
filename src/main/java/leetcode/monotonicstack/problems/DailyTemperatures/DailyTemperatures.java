package leetcode.monotonicstack.problems.DailyTemperatures;

import java.util.*;

/**
 * LeetCode 739: Daily Temperatures
 * 
 * Given an array of integers temperatures representing the daily temperatures,
 * return an array answer such that answer[i] is the number of days you have to wait
 * after the ith day to get a warmer temperature. If there is no future day for which
 * this is possible, keep answer[i] == 0.
 * 
 * Multiple Solution Approaches:
 * 1. Monotonic Stack - O(n) time, O(n) space (OPTIMAL)
 * 2. Brute Force - O(n²) time, O(1) space (SIMPLE)
 * 3. Backward Iteration - O(n) time, O(1) space (SPACE-OPTIMIZED)
 */
public class DailyTemperatures {
    
    /**
     * Approach 1: Monotonic Stack (RECOMMENDED)
     * 
     * Key Insight: Use a decreasing stack to efficiently find the next greater element.
     * The stack stores indices of temperatures in decreasing order.
     * 
     * Algorithm:
     * 1. Initialize result array with zeros (default for "no warmer day")
     * 2. Use stack to store indices of temperatures
     * 3. For each day:
     *    - While stack not empty and current temp > stack.top() temp:
     *      - Pop index from stack (this day found its warmer day)
     *      - Calculate distance: current_index - popped_index
     *      - Store result
     *    - Push current index to stack
     * 4. Return result array
     * 
     * Time Complexity: O(n) - Each element is pushed and popped at most once
     * Space Complexity: O(n) - Stack can contain up to n elements in worst case
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];  // Defaults to 0
        Stack<Integer> stack = new Stack<>();  // Store indices of temperatures
        
        for (int i = 0; i < n; i++) {
            // Pop elements while current temperature is greater than stack top
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                result[index] = i - index;  // Distance to next warmer day
            }
            stack.push(i);  // Push current index
        }
        
        return result;  // Remaining elements in stack already have default value 0
    }
    
    /**
     * Approach 2: Brute Force
     * 
     * Key Insight: For each day, search forward to find the next warmer temperature.
     * 
     * Algorithm:
     * 1. For each temperature at index i:
     *    - Search forward from i+1 to find first temperature > temperatures[i]
     *    - If found, store distance; otherwise leave as 0
     * 
     * Time Complexity: O(n²) - Nested loops
     * Space Complexity: O(1) - Only result array (not counting output)
     * 
     * Use Case: When memory is extremely limited and n is small
     */
    public int[] dailyTemperaturesBruteForce(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;  // Found next warmer day, stop searching
                }
            }
            // If no warmer day found, result[i] remains 0
        }
        
        return result;
    }
    
    /**
     * Approach 3: Backward Iteration (Space-Optimized)
     * 
     * Key Insight: Process from right to left, using previously computed results
     * to jump ahead efficiently when searching for next warmer temperature.
     * 
     * Algorithm:
     * 1. Start from second-to-last element, moving backward
     * 2. For each element at index i:
     *    - Start checking from i+1
     *    - If temperatures[j] <= temperatures[i]:
     *      - If result[j] == 0, no warmer day exists
     *      - Otherwise, jump to j + result[j] (next potential warmer day)
     *    - If temperatures[j] > temperatures[i], found the answer
     * 
     * Time Complexity: O(n) - Amortized, each element is visited constant times
     * Space Complexity: O(1) - Only result array (not counting output)
     * 
     * Use Case: When you need O(n) time but want to minimize space usage
     */
    public int[] dailyTemperaturesBackward(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        
        // Process from right to left (second-to-last element)
        for (int i = n - 2; i >= 0; i--) {
            int j = i + 1;  // Start checking from next day
            
            // Use previously computed results to jump ahead efficiently
            while (j < n && temperatures[j] <= temperatures[i]) {
                if (result[j] == 0) {
                    j = n;  // No warmer day found, exit loop
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
     * Helper method for tracing algorithm execution
     * Shows step-by-step execution of the monotonic stack approach
     */
    public int[] dailyTemperaturesWithTrace(int[] temperatures) {
        System.out.println("=== MONOTONIC STACK TRACE ===");
        System.out.println("Input: " + Arrays.toString(temperatures));
        
        int n = temperatures.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("\nStep-by-step execution:");
        for (int i = 0; i < n; i++) {
            System.out.printf("\ni=%d, temp=%d:", i, temperatures[i]);
            System.out.printf("\n  Stack before: %s", stack);
            
            // Pop elements while current temperature is greater
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                result[index] = i - index;
                System.out.printf("\n  Found warmer day for index %d: distance = %d", 
                                index, result[index]);
            }
            
            stack.push(i);
            System.out.printf("\n  Stack after: %s", stack);
            System.out.printf("\n  Result so far: %s", Arrays.toString(result));
        }
        
        System.out.printf("\nFinal result: %s\n", Arrays.toString(result));
        return result;
    }
    
    public static void main(String[] args) {
        DailyTemperatures solution = new DailyTemperatures();
        
        // Test Case 1: Example from problem description
        System.out.println("=== Test Case 1 ===");
        int[] temperatures1 = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Input: " + Arrays.toString(temperatures1));
        System.out.println("Expected: [1, 1, 4, 2, 1, 1, 0, 0]");
        System.out.println("Monotonic Stack: " + Arrays.toString(solution.dailyTemperatures(temperatures1)));
        System.out.println("Brute Force: " + Arrays.toString(solution.dailyTemperaturesBruteForce(temperatures1)));
        System.out.println("Backward Iteration: " + Arrays.toString(solution.dailyTemperaturesBackward(temperatures1)));
        System.out.println();
        
        // Test Case 2: Increasing sequence
        System.out.println("=== Test Case 2 ===");
        int[] temperatures2 = {30, 40, 50, 60, 90};
        System.out.println("Input: " + Arrays.toString(temperatures2));
        System.out.println("Expected: [1, 1, 1, 1, 0]");
        System.out.println("Monotonic Stack: " + Arrays.toString(solution.dailyTemperatures(temperatures2)));
        System.out.println("Brute Force: " + Arrays.toString(solution.dailyTemperaturesBruteForce(temperatures2)));
        System.out.println("Backward Iteration: " + Arrays.toString(solution.dailyTemperaturesBackward(temperatures2)));
        System.out.println();
        
        // Test Case 3: Decreasing sequence
        System.out.println("=== Test Case 3 ===");
        int[] temperatures3 = {90, 60, 50, 40, 30};
        System.out.println("Input: " + Arrays.toString(temperatures3));
        System.out.println("Expected: [0, 0, 0, 0, 0]");
        System.out.println("Monotonic Stack: " + Arrays.toString(solution.dailyTemperatures(temperatures3)));
        System.out.println("Brute Force: " + Arrays.toString(solution.dailyTemperaturesBruteForce(temperatures3)));
        System.out.println("Backward Iteration: " + Arrays.toString(solution.dailyTemperaturesBackward(temperatures3)));
        System.out.println();
        
        // Test Case 4: Single element
        System.out.println("=== Test Case 4 ===");
        int[] temperatures4 = {50};
        System.out.println("Input: " + Arrays.toString(temperatures4));
        System.out.println("Expected: [0]");
        System.out.println("Monotonic Stack: " + Arrays.toString(solution.dailyTemperatures(temperatures4)));
        System.out.println("Brute Force: " + Arrays.toString(solution.dailyTemperaturesBruteForce(temperatures4)));
        System.out.println("Backward Iteration: " + Arrays.toString(solution.dailyTemperaturesBackward(temperatures4)));
        System.out.println();
        
        // Detailed trace for understanding
        System.out.println("=== DETAILED TRACE ===");
        int[] traceArray = {73, 74, 75, 71, 69, 72, 76, 73};
        solution.dailyTemperaturesWithTrace(traceArray);
        System.out.println();
        
        // Performance comparison
        System.out.println("=== PERFORMANCE COMPARISON ===");
        int[] largeArray = {73, 74, 75, 71, 69, 72, 76, 73, 70, 68, 77, 65, 80, 75, 72};
        
        long start = System.nanoTime();
        int[] result1 = solution.dailyTemperatures(largeArray);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int[] result2 = solution.dailyTemperaturesBruteForce(largeArray);
        long time2 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int[] result3 = solution.dailyTemperaturesBackward(largeArray);
        long time3 = System.nanoTime() - start;
        
        System.out.printf("Monotonic Stack: %.2f μs%n", time1/1000.0);
        System.out.printf("Brute Force: %.2f μs%n", time2/1000.0);
        System.out.printf("Backward Iteration: %.2f μs%n", time3/1000.0);
        
        // Verify all approaches give same result
        boolean allMatch = Arrays.equals(result1, result2) && Arrays.equals(result2, result3);
        System.out.println("All approaches match: " + allMatch);
        
        // Best approach recommendation
        System.out.println("\n=== APPROACH RECOMMENDATIONS ===");
        System.out.println("1. MONOTONIC STACK - Best overall choice");
        System.out.println("   • O(n) time, O(n) space");
        System.out.println("   • Most intuitive once you understand the pattern");
        System.out.println("   • Standard solution for 'next greater element' problems");
        System.out.println();
        System.out.println("2. BACKWARD ITERATION - Space-optimized");
        System.out.println("   • O(n) time, O(1) space");
        System.out.println("   • Clever but less intuitive");
        System.out.println("   • Use when memory is very limited");
        System.out.println();
        System.out.println("3. BRUTE FORCE - Only for small inputs");
        System.out.println("   • O(n²) time, O(1) space");
        System.out.println("   • Simple to understand and implement");
        System.out.println("   • Acceptable only for very small arrays");
    }
}