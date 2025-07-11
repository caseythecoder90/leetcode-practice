package leetcode.monotonicstack.problems.OnlineStockSpan;

import java.util.*;

/**
 * LeetCode 901: Online Stock Span - Optimal Monotonic Stack Solution
 * 
 * This is the OPTIMAL solution using the monotonic stack pattern.
 * Perfect for learning the "Previous Smaller Element with Counting" pattern.
 * 
 * Time Complexity: O(n) total across all operations - each element is pushed and popped at most once
 * Space Complexity: O(n) - Stack can contain up to n elements in worst case
 */
public class StockSpannerOptimal {
    
    private Stack<int[]> stack;  // [price, span] pairs
    
    public StockSpannerOptimal() {
        stack = new Stack<>();
    }
    
    /**
     * Monotonic Stack Approach - The Standard Solution
     * 
     * Key Insight: Use a decreasing stack to efficiently track spans.
     * The stack maintains [price, span] pairs representing "blocks" of consecutive days.
     * 
     * Why this works:
     * 1. When current price ≥ stack.top() price, we can "consume" that entire block
     * 2. The span of the consumed block gets added to our current span
     * 3. Elements that can't contribute to future spans are eliminated
     * 4. Each element is processed exactly once across all operations
     * 
     * Algorithm:
     * 1. Start with span = 1 (current day always counts)
     * 2. While stack not empty AND current price >= stack.top() price:
     *    - Pop the block and add its span to current span
     * 3. Push [current price, total span] to stack
     * 4. Return the calculated span
     */
    public int next(int price) {
        int span = 1;  // Current day always counts
        
        // Pop all blocks where current price >= block price
        // This represents "consuming" those blocks into our current span
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];  // Add span of consumed block
        }
        
        // Push current price with its total span
        stack.push(new int[]{price, span});
        return span;
    }
    
    /**
     * Enhanced version with step-by-step tracing for learning
     */
    public int nextWithTrace(int price) {
        System.out.printf("\n=== Processing price %d ===%n", price);
        System.out.printf("Stack before: %s%n", stackToString());
        
        int span = 1;
        System.out.printf("Initial span: %d (current day)%n", span);
        
        // Show what we're comparing against
        if (!stack.isEmpty()) {
            System.out.printf("Comparing with stack top: [price=%d, span=%d]%n", 
                            stack.peek()[0], stack.peek()[1]);
        }
        
        // Process all blocks that can be consumed
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            int[] block = stack.pop();
            span += block[1];
            System.out.printf("✓ Consumed block [price=%d, span=%d] → total span now %d%n", 
                            block[0], block[1], span);
        }
        
        // Push current block
        stack.push(new int[]{price, span});
        System.out.printf("Push [price=%d, span=%d]%n", price, span);
        System.out.printf("Stack after: %s%n", stackToString());
        System.out.printf("Result: %d%n", span);
        
        return span;
    }
    
    private String stackToString() {
        if (stack.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int[] pair : stack) {
            if (!first) sb.append(", ");
            sb.append(String.format("[%d,%d]", pair[0], pair[1]));
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static void main(String[] args) {
        // Test Case 1: Classic example with trace
        System.out.println("=== Test Case 1: Classic Example with Trace ===");
        int[] prices1 = {100, 80, 60, 70, 60, 75, 85};
        System.out.println("Input prices: " + Arrays.toString(prices1));
        
        StockSpannerOptimal spanner1 = new StockSpannerOptimal();
        int[] results1 = new int[prices1.length];
        
        for (int i = 0; i < prices1.length; i++) {
            results1[i] = spanner1.nextWithTrace(prices1[i]);
        }
        
        System.out.println("\nFinal results: " + Arrays.toString(results1));
        System.out.println("Expected:      [1, 1, 1, 2, 1, 4, 6]");
        System.out.println();
        
        // Test Case 2: Increasing sequence (worst case for simple approach)
        System.out.println("=== Test Case 2: Increasing Sequence ===");
        int[] prices2 = {31, 41, 48, 59, 79};
        System.out.println("Input: " + Arrays.toString(prices2));
        
        StockSpannerOptimal_01 spanner2 = new StockSpannerOptimal_01();
        System.out.print("Output: [");
        for (int i = 0; i < prices2.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(spanner2.next(prices2[i]));
        }
        System.out.println("]");
        System.out.println("Expected: [1, 2, 3, 4, 5]");
        System.out.println();
        
        // Test Case 3: Decreasing sequence
        System.out.println("=== Test Case 3: Decreasing Sequence ===");
        int[] prices3 = {90, 80, 70, 60, 50};
        System.out.println("Input: " + Arrays.toString(prices3));
        
        StockSpannerOptimal_01 spanner3 = new StockSpannerOptimal_01();
        System.out.print("Output: [");
        for (int i = 0; i < prices3.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(spanner3.next(prices3[i]));
        }
        System.out.println("]");
        System.out.println("Expected: [1, 1, 1, 1, 1]");
        System.out.println();
        
        // Performance test
        System.out.println("=== Performance Test ===");
        int[] largeInput = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeInput[i] = i + 1;  // Increasing sequence (worst case)
        }
        
        StockSpannerOptimal_01 spannerPerf = new StockSpannerOptimal_01();
        long startTime = System.nanoTime();
        
        for (int price : largeInput) {
            spannerPerf.next(price);
        }
        
        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.printf("Processed 1000 operations in %.2f ms%n", timeMs);
        System.out.println("Each operation: O(1) amortized");
        System.out.println();
        
        // Key insights
        System.out.println("=== KEY INSIGHTS ===");
        System.out.println("1. BLOCK AGGREGATION: Instead of counting individual days,");
        System.out.println("   think in terms of 'consuming' entire blocks of consecutive days");
        System.out.println();
        System.out.println("2. AMORTIZED ANALYSIS: Each price is pushed exactly once and");
        System.out.println("   popped at most once, giving O(n) total complexity");
        System.out.println();
        System.out.println("3. STACK PROPERTY: Maintains decreasing order of prices");
        System.out.println("   (with equal prices allowed since we pop on <=)");
        System.out.println();
        System.out.println("4. PATTERN APPLICATION: This technique works for any");
        System.out.println("   'consecutive count' or 'span' problem");
        System.out.println();
        System.out.println("5. SPACE-TIME TRADEOFF: Uses O(n) space to achieve O(1) amortized time");
        
        System.out.println("\n=== TEMPLATE FOR SIMILAR PROBLEMS ===");
        System.out.println("int span = 1;  // Current element always counts");
        System.out.println("while (!stack.isEmpty() && condition_met) {");
        System.out.println("    span += stack.pop()[1];  // Consume block");
        System.out.println("}");
        System.out.println("stack.push(new int[]{value, span});");
        System.out.println("return span;");
    }
}