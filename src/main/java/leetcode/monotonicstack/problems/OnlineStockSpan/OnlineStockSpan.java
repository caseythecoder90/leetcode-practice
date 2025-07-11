package leetcode.monotonicstack.problems.OnlineStockSpan;

import java.util.*;

/**
 * LeetCode 901: Online Stock Span
 * 
 * Design an algorithm that collects daily price quotes for some stock and returns 
 * the span of that stock's price for the current day.
 * 
 * The span of the stock's price today is defined as the maximum number of consecutive days 
 * (starting from today and going backward) for which the stock price was less than or equal 
 * to today's price.
 * 
 * Multiple Solution Approaches:
 * 1. Monotonic Stack - O(n) total time across all operations (OPTIMAL)
 * 2. Your Current Approach - O(n²) worst case (SIMPLE but SLOW)
 * 3. Optimized Linear Scan - O(n²) worst case but better average (IMPROVED SIMPLE)
 */

/**
 * OPTIMAL SOLUTION: Monotonic Stack Approach
 * 
 * Key Insight: Use a monotonic decreasing stack to efficiently track the span.
 * The stack stores [price, span] pairs where each element represents a "block"
 * of consecutive days with the same or smaller prices.
 * 
 * Why this works:
 * 1. When current price >= stack.top() price, we can "consume" that entire block
 * 2. The span of the consumed block gets added to our current span
 * 3. Elements that can't contribute to future spans are eliminated
 * 
 * Time Complexity: O(n) total across all operations - each price is pushed and popped once
 * Space Complexity: O(n) - stack can contain up to n elements
 */
class StockSpannerOptimal_01 {
    private Stack<int[]> stack;  // [price, span] pairs
    
    public StockSpannerOptimal_01() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;  // Current day always counts
        
        // Pop elements while current price >= stack price
        // Each popped element represents a block of days we can "consume"
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];  // Add span of consumed block
        }
        
        // Push current price with its total span
        stack.push(new int[]{price, span});
        return span;
    }
}

// StockSpannerSimple is implemented in a separate file: StockSpannerSimple.java

/**
 * IMPROVED SIMPLE APPROACH: With Early Termination
 * 
 * Still O(n²) worst case, but with some optimizations to reduce average case.
 * Shows progression from simple to optimal.
 * 
 * Time Complexity: O(n²) worst case, better average case
 * Space Complexity: O(n)
 */
class StockSpannerImproved {
    private List<Integer> stockPrices;
    private List<Integer> spans;  // Cache spans for potential future use

    public StockSpannerImproved() {
        stockPrices = new ArrayList<>();
        spans = new ArrayList<>();
    }
    
    public int next(int price) {
        stockPrices.add(price);
        
        int span = 1;  // Current day
        int i = stockPrices.size() - 2;  // Start from previous day
        
        // Scan backward with some optimizations
        while (i >= 0 && stockPrices.get(i) <= price) {
            span++;
            i--;
            
            // Early termination: if we've seen this pattern before
            // (This doesn't actually help much, but shows thinking process)
            if (i >= 0 && stockPrices.get(i) > price) {
                break;
            }
        }
        
        spans.add(span);
        return span;
    }
}

/**
 * Demo and Comparison Class
 */
public class OnlineStockSpan {
    
    /**
     * Detailed trace of the optimal monotonic stack approach
     */
    public static void traceOptimalApproach(int[] prices) {
        System.out.println("=== MONOTONIC STACK TRACE ===");
        System.out.println("Input prices: " + Arrays.toString(prices));
        System.out.println("Goal: Find span (consecutive days ≤ current price) for each day\n");
        
        Stack<int[]> stack = new Stack<>();
        
        for (int day = 0; day < prices.length; day++) {
            int price = prices[day];
            int span = 1;
            
            System.out.printf("Day %d: price = %d%n", day, price);
            System.out.printf("  Stack before: %s%n", stackToString(stack));
            
            // Process stack
            while (!stack.isEmpty() && stack.peek()[0] <= price) {
                int[] popped = stack.pop();
                span += popped[1];
                System.out.printf("  Pop [price=%d, span=%d] → add %d to span (total: %d)%n", 
                                popped[0], popped[1], popped[1], span);
            }
            
            stack.push(new int[]{price, span});
            System.out.printf("  Push [price=%d, span=%d]%n", price, span);
            System.out.printf("  Stack after: %s%n", stackToString(stack));
            System.out.printf("  Result: span = %d%n%n", span);
        }
    }
    
    private static String stackToString(Stack<int[]> stack) {
        if (stack.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int[] pair : stack) {
            sb.append(String.format("[%d,%d]", pair[0], pair[1]));
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Performance comparison between approaches
     */
    public static void performanceComparison() {
        System.out.println("=== PERFORMANCE COMPARISON ===");
        
        // Test case: worst case scenario (increasing sequence)
        int[] worstCase = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("Test case (worst for simple approach): " + Arrays.toString(worstCase));
        System.out.println();
        
        // Test optimal approach
        StockSpannerOptimal_01 optimal = new StockSpannerOptimal_01();
        long startTime = System.nanoTime();
        int[] optimalResults = new int[worstCase.length];
        for (int i = 0; i < worstCase.length; i++) {
            optimalResults[i] = optimal.next(worstCase[i]);
        }
        long optimalTime = System.nanoTime() - startTime;
        
        // Test simple approach
        StockSpannerSimple simple = new StockSpannerSimple();
        startTime = System.nanoTime();
        int[] simpleResults = new int[worstCase.length];
        for (int i = 0; i < worstCase.length; i++) {
            simpleResults[i] = simple.next(worstCase[i]);
        }
        long simpleTime = System.nanoTime() - startTime;
        
        System.out.println("Results:");
        System.out.println("Optimal: " + Arrays.toString(optimalResults));
        System.out.println("Simple:  " + Arrays.toString(simpleResults));
        System.out.println("Results match: " + Arrays.equals(optimalResults, simpleResults));
        System.out.println();
        
        System.out.printf("Optimal approach: %.2f μs%n", optimalTime/1000.0);
        System.out.printf("Simple approach:  %.2f μs%n", simpleTime/1000.0);
        System.out.printf("Speedup: %.1fx%n", (double)simpleTime/optimalTime);
    }
    
    public static void main(String[] args) {
        // Example 1: Detailed trace
        System.out.println("=== Example 1: Understanding the Pattern ===");
        int[] example1 = {100, 80, 60, 70, 60, 75, 85};
        traceOptimalApproach(example1);
        
        // Example 2: Test all approaches
        System.out.println("=== Example 2: Testing All Approaches ===");
        int[] example2 = {31, 41, 48, 59, 79};
        
        StockSpannerOptimal_01 optimal = new StockSpannerOptimal_01();
        StockSpannerSimple simple = new StockSpannerSimple();
        
        System.out.println("Input: " + Arrays.toString(example2));
        System.out.print("Optimal spans: [");
        for (int i = 0; i < example2.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(optimal.next(example2[i]));
        }
        System.out.println("]");
        
        System.out.print("Simple spans:  [");
        for (int i = 0; i < example2.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(simple.next(example2[i]));
        }
        System.out.println("]");
        System.out.println();
        
        // Performance comparison
        performanceComparison();
        
        // Key insights
        System.out.println("\n=== KEY INSIGHTS ===");
        System.out.println("1. PROBLEM PATTERN: This is a 'Previous Smaller Element' problem");
        System.out.println("   - We need to count consecutive elements ≤ current price");
        System.out.println("   - This is equivalent to finding where the pattern breaks");
        System.out.println();
        System.out.println("2. WHY MONOTONIC STACK WORKS:");
        System.out.println("   - Stack maintains decreasing sequence of [price, span] pairs");
        System.out.println("   - When current price ≥ stack.top(), we can 'consume' that block");
        System.out.println("   - Each element is pushed and popped at most once → O(n) total");
        System.out.println();
        System.out.println("3. YOUR SOLUTION vs OPTIMAL:");
        System.out.println("   - Your approach: O(n²) - scan all previous elements each time");
        System.out.println("   - Optimal approach: O(n) - eliminate redundant comparisons");
        System.out.println("   - For n=1000, optimal is ~1000x faster in worst case!");
        System.out.println();
        System.out.println("4. WHEN YOUR APPROACH IS OK:");
        System.out.println("   - Very small inputs (< 100 operations)");
        System.out.println("   - Mostly decreasing price sequences");
        System.out.println("   - Learning/prototyping phase");
        System.out.println();
        System.out.println("5. PATTERN RECOGNITION:");
        System.out.println("   - Look for: consecutive elements, spans, previous smaller/equal");
        System.out.println("   - Solution: monotonic stack with block aggregation");
        System.out.println("   - Template: while (stack.top() ≤ current) { consume block }");
    }
}