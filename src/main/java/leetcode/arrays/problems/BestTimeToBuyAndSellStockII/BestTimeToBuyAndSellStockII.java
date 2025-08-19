package leetcode.arrays.problems.BestTimeToBuyAndSellStockII;

import java.util.*;

/**
 * LeetCode 122: Best Time to Buy and Sell Stock II
 * 
 * Pattern: Greedy Algorithm
 * Difficulty: Medium
 * 
 * Problem: Given an array of stock prices, find the maximum profit you can achieve
 * with unlimited transactions (but can hold at most one share at a time).
 * 
 * Key Insight: Sum all positive price differences = optimal profit
 */
public class BestTimeToBuyAndSellStockII {
    
    /**
     * OPTIMAL SOLUTION: Greedy Algorithm
     * 
     * Approach: Capture every profitable price increase
     * Time: O(n), Space: O(1)
     */
    public int maxProfitGreedy(int[] prices) {
        if (prices.length < 2) return 0;
        
        int profit = 0;
        
        // Sum all positive differences between consecutive days
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                profit += prices[i] - prices[i-1];
            }
        }
        
        return profit;
    }
    
    /**
     * ALTERNATIVE: Transaction Simulation
     * 
     * Approach: Simulate actual buying and selling
     * Time: O(n), Space: O(1)
     * 
     * Same result as greedy, but more intuitive for some people
     */
    public int maxProfitSimulation(int[] prices) {
        if (prices.length < 2) return 0;
        
        int profit = 0;
        int buyPrice = prices[0];
        
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > buyPrice) {
                // Sell at current price and immediately rebuy
                profit += prices[i] - buyPrice;
            }
            // Always update buy price to current price
            buyPrice = prices[i];
        }
        
        return profit;
    }
    
    /**
     * DYNAMIC PROGRAMMING APPROACH (Overkill for this problem)
     * 
     * Approach: Track states of holding vs not holding stock
     * Time: O(n), Space: O(1) with optimization
     * 
     * This works but is unnecessarily complex. Use for learning DP pattern.
     */
    public int maxProfitDP(int[] prices) {
        if (prices.length < 2) return 0;
        
        // held: max profit when holding stock on current day
        // sold: max profit when not holding stock on current day
        int held = -prices[0];  // Bought stock on first day
        int sold = 0;           // No transaction on first day
        
        for (int i = 1; i < prices.length; i++) {
            int prevHeld = held;
            int prevSold = sold;
            
            // Either keep holding, or buy today
            held = Math.max(prevHeld, prevSold - prices[i]);
            
            // Either keep not holding, or sell today
            sold = Math.max(prevSold, prevHeld + prices[i]);
        }
        
        return sold;  // End without holding stock
    }
    
    /**
     * BRUTE FORCE (For Understanding Only)
     * 
     * Generate all possible transaction sequences and find maximum
     * Time: O(2^n), Space: O(n) for recursion
     * 
     * This is exponential and only useful for very small inputs
     */
    public int maxProfitBruteForce(int[] prices) {
        return bruteForceHelper(prices, 0, false, 0);
    }
    
    private int bruteForceHelper(int[] prices, int day, boolean holding, int profit) {
        if (day >= prices.length) {
            return profit;
        }
        
        int maxProfit = profit;
        
        if (holding) {
            // Option 1: Sell today
            maxProfit = Math.max(maxProfit, 
                bruteForceHelper(prices, day + 1, false, profit + prices[day]));
            
            // Option 2: Keep holding
            maxProfit = Math.max(maxProfit,
                bruteForceHelper(prices, day + 1, true, profit));
        } else {
            // Option 1: Buy today
            maxProfit = Math.max(maxProfit,
                bruteForceHelper(prices, day + 1, true, profit - prices[day]));
            
            // Option 2: Stay out of market
            maxProfit = Math.max(maxProfit,
                bruteForceHelper(prices, day + 1, false, profit));
        }
        
        return maxProfit;
    }
    
    /**
     * PERFORMANCE COMPARISON AND TESTING
     */
    public static void main(String[] args) {
        BestTimeToBuyAndSellStockII solution = new BestTimeToBuyAndSellStockII();
        
        // Test Cases
        System.out.println("=== TEST CASES ===");
        
        // Test Case 1: Mixed sequence
        int[] test1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Test 1: " + Arrays.toString(test1));
        System.out.println("Expected: 7");
        System.out.println("Greedy:   " + solution.maxProfitGreedy(test1));
        System.out.println("Simulation: " + solution.maxProfitSimulation(test1));
        System.out.println("DP:       " + solution.maxProfitDP(test1));
        System.out.println("Brute Force: " + solution.maxProfitBruteForce(test1));
        System.out.println();
        
        // Test Case 2: Strictly increasing
        int[] test2 = {1, 2, 3, 4, 5};
        System.out.println("Test 2: " + Arrays.toString(test2));
        System.out.println("Expected: 4");
        System.out.println("Greedy:   " + solution.maxProfitGreedy(test2));
        System.out.println("Simulation: " + solution.maxProfitSimulation(test2));
        System.out.println("DP:       " + solution.maxProfitDP(test2));
        System.out.println();
        
        // Test Case 3: Strictly decreasing
        int[] test3 = {7, 6, 4, 3, 1};
        System.out.println("Test 3: " + Arrays.toString(test3));
        System.out.println("Expected: 0");
        System.out.println("Greedy:   " + solution.maxProfitGreedy(test3));
        System.out.println("Simulation: " + solution.maxProfitSimulation(test3));
        System.out.println("DP:       " + solution.maxProfitDP(test3));
        System.out.println();
        
        // Test Case 4: Single element
        int[] test4 = {1};
        System.out.println("Test 4: " + Arrays.toString(test4));
        System.out.println("Expected: 0");
        System.out.println("Greedy:   " + solution.maxProfitGreedy(test4));
        System.out.println();
        
        // Test Case 5: Complex sequence
        int[] test5 = {3, 3, 5, 0, 0, 3, 1, 4};
        System.out.println("Test 5: " + Arrays.toString(test5));
        System.out.println("Greedy:   " + solution.maxProfitGreedy(test5));
        System.out.println();
        
        // Performance test
        performanceTest(solution);
        
        // Algorithm explanation
        explainAlgorithm();
    }
    
    private static void performanceTest(BestTimeToBuyAndSellStockII solution) {
        System.out.println("=== PERFORMANCE TEST ===");
        
        // Generate large test case
        int[] largeTest = new int[10000];
        Random rand = new Random(42);  // Fixed seed for reproducibility
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = rand.nextInt(1000) + 1;
        }
        
        // Test greedy approach
        long startTime = System.nanoTime();
        int greedyResult = solution.maxProfitGreedy(largeTest);
        long greedyTime = System.nanoTime() - startTime;
        
        // Test DP approach
        startTime = System.nanoTime();
        int dpResult = solution.maxProfitDP(largeTest);
        long dpTime = System.nanoTime() - startTime;
        
        System.out.printf("Large test (n=10000):%n");
        System.out.printf("Greedy result: %d (%.2f ms)%n", greedyResult, greedyTime/1_000_000.0);
        System.out.printf("DP result:     %d (%.2f ms)%n", dpResult, dpTime/1_000_000.0);
        System.out.printf("Results match: %b%n", greedyResult == dpResult);
        System.out.printf("Greedy speedup: %.1fx%n", (double)dpTime/greedyTime);
        System.out.println();
    }
    
    private static void explainAlgorithm() {
        System.out.println("=== ALGORITHM EXPLANATION ===");
        System.out.println("Why does summing positive differences work?");
        System.out.println();
        System.out.println("Key insight: Since we can make unlimited transactions and");
        System.out.println("buy/sell on the same day, every price increase should be captured.");
        System.out.println();
        System.out.println("Example: [1, 5, 3, 6]");
        System.out.println("  Option 1: Buy@1, Sell@6 = profit 5");
        System.out.println("  Option 2: Buy@1, Sell@5, Buy@3, Sell@6 = (5-1) + (6-3) = 7");
        System.out.println("  Greedy:   (5-1) + (6-3) = 4 + 3 = 7 ✓");
        System.out.println();
        System.out.println("The greedy approach automatically finds the optimal profit!");
    }
}
