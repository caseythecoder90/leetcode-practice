package leetcode.monotonicstack.problems.DailyTemperatures;

import java.util.*;

/**
 * LeetCode 739: Daily Temperatures - Monotonic Stack Approach
 * 
 * This is the OPTIMAL solution using the monotonic stack pattern.
 * Perfect for learning the "Next Greater Element" pattern.
 * 
 * Time Complexity: O(n) - Each element is pushed and popped at most once
 * Space Complexity: O(n) - Stack can contain up to n elements in worst case
 */
public class MonotonicStackSolution {
    
    /**
     * Monotonic Stack Approach - The Standard Solution
     * 
     * Key Insight: Use a decreasing stack to efficiently find the next greater element.
     * The stack maintains indices of temperatures in decreasing order from bottom to top.
     * 
     * Why this works:
     * 1. When we encounter a warmer temperature, all cooler temperatures in the stack
     *    have found their "next warmer day"
     * 2. Cooler temperatures will never be the answer for future days
     * 3. We can safely remove them and process them immediately
     * 
     * Algorithm:
     * 1. Initialize result array with zeros (default for "no warmer day")
     * 2. Use stack to store indices of temperatures (need positions for distance)
     * 3. For each day:
     *    - While stack not empty AND current temp > stack.top() temp:
     *      - Pop index from stack (this day found its warmer day)
     *      - Calculate distance: current_index - popped_index
     *      - Store result
     *    - Push current index to stack
     * 4. Return result array (remaining elements already have default value 0)
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];  // Defaults to 0 for "no warmer day"
        Stack<Integer> stack = new Stack<>();  // Store indices, not values!
        
        for (int i = 0; i < n; i++) {
            // Pop all indices where current temperature is warmer
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                result[prevIndex] = i - prevIndex;  // Distance to warmer day
            }
            
            // Push current index to maintain decreasing stack property
            stack.push(i);
        }
        
        // Elements remaining in stack have no warmer days (already 0 in result)
        return result;
    }
    
    /**
     * Enhanced version with step-by-step tracing for learning
     */
    public int[] dailyTemperaturesWithTrace(int[] temperatures) {
        System.out.println("=== MONOTONIC STACK DETAILED TRACE ===");
        System.out.println("Input temperatures: " + Arrays.toString(temperatures));
        System.out.println("Goal: Find distance to next warmer temperature for each day\n");
        
        int n = temperatures.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        System.out.println("Initial state:");
        System.out.printf("  result = %s%n", Arrays.toString(result));
        System.out.printf("  stack = %s%n%n", stack);
        
        for (int i = 0; i < n; i++) {
            System.out.printf("Day %d: temperature = %d%n", i, temperatures[i]);
            System.out.printf("  Stack before processing: %s%n", stack);
            
            // Show what we're comparing against
            if (!stack.isEmpty()) {
                System.out.printf("  Comparing with stack top: index %d (temp = %d)%n", 
                                stack.peek(), temperatures[stack.peek()]);
            }
            
            // Process all days that found their warmer day
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                int distance = i - prevIndex;
                result[prevIndex] = distance;
                
                System.out.printf("  ✓ Found warmer day for index %d: distance = %d%n", 
                                prevIndex, distance);
                System.out.printf("    (Day %d temp %d < Day %d temp %d)%n", 
                                prevIndex, temperatures[prevIndex], i, temperatures[i]);
            }
            
            // Push current index
            stack.push(i);
            System.out.printf("  Push index %d to stack%n", i);
            System.out.printf("  Stack after processing: %s%n", stack);
            System.out.printf("  Result so far: %s%n%n", Arrays.toString(result));
        }
        
        System.out.println("Final result: " + Arrays.toString(result));
        System.out.println("Remaining stack elements have no warmer days: " + stack);
        
        return result;
    }
    
    public static void main(String[] args) {
        MonotonicStackSolution solution = new MonotonicStackSolution();
        
        // Test Case 1: Classic example
        System.out.println("=== Test Case 1: Classic Example ===");
        int[] temperatures1 = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Input: " + Arrays.toString(temperatures1));
        System.out.println("Expected: [1, 1, 4, 2, 1, 1, 0, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures1)));
        System.out.println();
        
        // Test Case 2: Increasing sequence
        System.out.println("=== Test Case 2: Increasing Sequence ===");
        int[] temperatures2 = {30, 40, 50, 60, 90};
        System.out.println("Input: " + Arrays.toString(temperatures2));
        System.out.println("Expected: [1, 1, 1, 1, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures2)));
        System.out.println();
        
        // Test Case 3: Decreasing sequence
        System.out.println("=== Test Case 3: Decreasing Sequence ===");
        int[] temperatures3 = {90, 60, 50, 40, 30};
        System.out.println("Input: " + Arrays.toString(temperatures3));
        System.out.println("Expected: [0, 0, 0, 0, 0]");
        System.out.println("Output: " + Arrays.toString(solution.dailyTemperatures(temperatures3)));
        System.out.println();
        
        // Detailed trace for small example
        System.out.println("=== DETAILED TRACE FOR LEARNING ===");
        int[] smallExample = {73, 74, 75, 71};
        solution.dailyTemperaturesWithTrace(smallExample);
        
        // Key insights
        System.out.println("\n=== KEY INSIGHTS ===");
        System.out.println("1. Stack maintains indices in decreasing temperature order");
        System.out.println("2. When current temp > stack.top() temp, stack.top() found its answer");
        System.out.println("3. Each element is pushed and popped at most once → O(n) time");
        System.out.println("4. We store indices (not values) because we need distance calculation");
        System.out.println("5. This pattern works for any 'next greater element' problem");
        
        System.out.println("\n=== PATTERN TEMPLATE ===");
        System.out.println("for (int i = 0; i < n; i++) {");
        System.out.println("    while (!stack.isEmpty() && condition_met) {");
        System.out.println("        // Process element that found its answer");
        System.out.println("        int index = stack.pop();");
        System.out.println("        // Store result");
        System.out.println("    }");
        System.out.println("    stack.push(i);");
        System.out.println("}");
    }
}