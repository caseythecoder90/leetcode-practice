package leetcode.monotonicstack.problems.OnlineStockSpan;

import java.util.*;

/**
 * LeetCode 901: Online Stock Span - Simple Linear Scan Approach
 * 
 * This matches your current implementation - simple but not optimal for large inputs.
 * Good for understanding the problem before learning advanced techniques.
 * 
 * Time Complexity: O(n²) worst case - scan all previous elements each time
 * Space Complexity: O(n) - store all prices
 * 
 * When to use: Only for very small inputs or when simplicity is more important than efficiency.
 */
public class StockSpannerSimple {
    
    private List<Integer> stockPrices;

    public StockSpannerSimple() {
        stockPrices = new ArrayList<>();
    }
    
    /**
     * Simple Linear Scan Approach - Your Current Implementation
     * 
     * Key Insight: For each day, scan backward to count consecutive days with price ≤ current.
     * This is the most straightforward approach that directly follows the problem description.
     * 
     * Algorithm:
     * 1. Add current price to the list
     * 2. Scan backward from current day:
     *    - Count days where price ≤ current price
     *    - Stop when we find a price > current price
     * 3. Return the count
     * 
     * Pros:
     * - Very easy to understand and implement
     * - Directly follows the problem description
     * - No additional data structures beyond the price list
     * 
     * Cons:
     * - O(n²) time complexity in worst case
     * - Redundant comparisons - same elements checked multiple times
     * - Gets slow quickly as input size grows
     */
    public int next(int price) {
        stockPrices.add(price);

        int span = 0;
        // Scan backward from current day until we find a price > current price
        for (int i = stockPrices.size() - 1; i >= 0; i--) {
            if (stockPrices.get(i) <= price) {
                span++;
            } else {
                break;  // Found greater price, stop scanning
            }
        }

        return span;
    }
    
    /**
     * Enhanced version with detailed tracing to show the search process
     */
    public int nextWithTrace(int price) {
        System.out.printf("\n=== Day %d: Processing price %d ===%n", stockPrices.size(), price);
        
        stockPrices.add(price);
        System.out.printf("Added price %d to list. Current list: %s%n", price, stockPrices);

        int span = 0;
        System.out.printf("Scanning backward to count consecutive days ≤ %d:%n", price);
        
        // Scan backward from current day
        for (int i = stockPrices.size() - 1; i >= 0; i--) {
            System.out.printf("  Day %d (price = %d): ", i, stockPrices.get(i));
            
            if (stockPrices.get(i) <= price) {
                span++;
                System.out.printf("✓ ≤ %d, count = %d%n", price, span);
            } else {
                System.out.printf("✗ > %d, stop scanning%n", price);
                break;  // Found greater price, stop scanning
            }
        }

        System.out.printf("Final span: %d%n", span);
        return span;
    }
    
    public static void main(String[] args) {
        // Test Case 1: Detailed trace for small example
        System.out.println("=== Test Case 1: Detailed Trace ===");
        int[] prices1 = {100, 80, 60, 70};
        System.out.println("Input prices: " + Arrays.toString(prices1));
        
        StockSpannerSimple spanner1 = new StockSpannerSimple();
        int[] results1 = new int[prices1.length];
        
        for (int i = 0; i < prices1.length; i++) {
            results1[i] = spanner1.nextWithTrace(prices1[i]);
        }
        
        System.out.println("\nFinal results: " + Arrays.toString(results1));
        System.out.println("Expected:      [1, 1, 1, 2]");
        System.out.println();
        
        // Test Case 2: Classic example
        System.out.println("=== Test Case 2: Classic Example ===");
        int[] prices2 = {100, 80, 60, 70, 60, 75, 85};
        System.out.println("Input: " + Arrays.toString(prices2));
        
        StockSpannerSimple spanner2 = new StockSpannerSimple();
        System.out.print("Output: [");
        for (int i = 0; i < prices2.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(spanner2.next(prices2[i]));
        }
        System.out.println("]");
        System.out.println("Expected: [1, 1, 1, 2, 1, 4, 6]");
        System.out.println();
        
        // Test Case 3: Worst case scenario (increasing sequence)
        System.out.println("=== Test Case 3: Worst Case (Increasing) ===");
        int[] prices3 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(prices3));
        System.out.println("This requires maximum comparisons:");
        
        StockSpannerSimple spanner3 = new StockSpannerSimple();
        int totalComparisons = 0;
        
        for (int i = 0; i < prices3.length; i++) {
            int comparisons = i + 1;  // Will check all previous elements
            totalComparisons += comparisons;
            System.out.printf("Day %d (price %d): %d comparisons%n", i, prices3[i], comparisons);
            spanner3.next(prices3[i]);
        }
        
        System.out.printf("Total comparisons: %d (O(n²) = %d²/2 = %d)%n", 
                        totalComparisons, prices3.length, prices3.length * prices3.length / 2);
        System.out.println();
        
        // Test Case 4: Best case scenario (decreasing sequence)
        System.out.println("=== Test Case 4: Best Case (Decreasing) ===");
        int[] prices4 = {90, 80, 70, 60, 50};
        System.out.println("Input: " + Arrays.toString(prices4));
        System.out.println("This requires minimum comparisons (each day just checks itself):");
        
        StockSpannerSimple spanner4 = new StockSpannerSimple();
        System.out.print("Output: [");
        for (int i = 0; i < prices4.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(spanner4.next(prices4[i]));
        }
        System.out.println("]");
        System.out.println("Expected: [1, 1, 1, 1, 1] (1 comparison each)");
        System.out.println();
        
        // Performance analysis
        System.out.println("=== COMPLEXITY ANALYSIS ===");
        System.out.println("Time Complexity: O(n²) worst case");
        System.out.println("  - Each call to next() scans up to n previous elements");
        System.out.println("  - For n calls: 1 + 2 + 3 + ... + n = n(n+1)/2 = O(n²)");
        System.out.println();
        System.out.println("Space Complexity: O(n)");
        System.out.println("  - Store all prices in ArrayList");
        System.out.println("  - No additional data structures");
        System.out.println();
        System.out.println("Best Case: O(n) - decreasing sequence (1 comparison per call)");
        System.out.println("Worst Case: O(n²) - increasing sequence (n comparisons for nth call)");
        
        System.out.println("\n=== WHEN THIS APPROACH IS APPROPRIATE ===");
        System.out.println("✓ Good for:");
        System.out.println("  - Very small inputs (< 100 operations)");
        System.out.println("  - Learning and understanding the problem");
        System.out.println("  - Quick prototyping");
        System.out.println("  - When simplicity is more important than performance");
        System.out.println();
        System.out.println("✗ Avoid for:");
        System.out.println("  - Large inputs (> 1000 operations)");
        System.out.println("  - Production code with performance requirements");
        System.out.println("  - Real-time applications");
        System.out.println("  - When O(n) solutions are available (like monotonic stack)");
        
        System.out.println("\n=== OPTIMIZATION OPPORTUNITIES ===");
        System.out.println("1. Early termination: Already implemented (break on first greater price)");
        System.out.println("2. Caching: Could cache spans, but doesn't improve worst-case complexity");
        System.out.println("3. Different data structure: Use monotonic stack for O(n) total time");
        System.out.println("4. Space optimization: Could process streaming without storing all prices");
        System.out.println("   (but would still be O(n²) time without additional optimizations)");
    }
}