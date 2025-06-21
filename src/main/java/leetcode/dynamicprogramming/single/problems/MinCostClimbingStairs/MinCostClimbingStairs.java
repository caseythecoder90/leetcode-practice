package leetcode.dynamicprogramming.single.problems.MinCostClimbingStairs;

import java.util.Arrays;

/**
 * LeetCode #746: Min Cost Climbing Stairs
 *
 * Problem: You are given an integer array cost where cost[i] is the cost of ith step.
 * Once you pay the cost, you can either climb one or two steps. You can either start
 * from the step with index 0, or the step with index 1. Return the minimum cost to
 * reach the top of the floor.
 *
 * Pattern: Decision-Making DP (Pattern 2)
 * At each step, decide whether to come from 1 step back or 2 steps back
 */
public class MinCostClimbingStairs {

    /**
     * Bottom-up DP Solution (Recommended)
     *
     * State Definition: dp[i] = minimum cost to reach step i
     * Base Cases: dp[0] = 0, dp[1] = 0 (can start at either for free)
     * Recurrence: dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
     *
     * Time: O(n), Space: O(n)
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;

        // dp[i] = minimum cost to reach step i
        int[] dp = new int[n + 1];

        // Base cases: can start at step 0 or step 1 for free
        dp[0] = 0;
        dp[1] = 0;

        // Fill dp table
        for (int i = 2; i <= n; i++) {
            // To reach step i, we can come from:
            // - step i-1 (pay cost[i-1] and climb 1 step)
            // - step i-2 (pay cost[i-2] and climb 2 steps)
            dp[i] = Math.min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2]);
        }

        // dp[n] represents minimum cost to reach the top (beyond last step)
        return dp[n];
    }

    /**
     * Space-Optimized Solution (O(1) space)
     *
     * Since we only need the last 2 values, we can optimize space
     */
    public int minCostClimbingStairsOptimized(int[] cost) {
        int n = cost.length;

        // Only track last 2 values
        int prev2 = 0;  // dp[i-2]
        int prev1 = 0;  // dp[i-1]

        for (int i = 2; i <= n; i++) {
            int current = Math.min(prev1 + cost[i-1], prev2 + cost[i-2]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    /**
     * Top-down Recursive Solution with Memoization
     *
     * This helps understand the recursive structure before optimizing to bottom-up
     */
    public int minCostClimbingStairsRecursive(int[] cost) {
        int[] memo = new int[cost.length + 1];
        Arrays.fill(memo, -1);
        return helper(cost, cost.length, memo);
    }

    private int helper(int[] cost, int step, int[] memo) {
        // Base cases: can start at step 0 or 1 for free
        if (step <= 1) {
            return 0;
        }

        // Check memo
        if (memo[step] != -1) {
            return memo[step];
        }

        // Recurrence: to reach step, come from step-1 or step-2
        int option1 = helper(cost, step - 1, memo) + cost[step - 1];
        int option2 = helper(cost, step - 2, memo) + cost[step - 2];

        memo[step] = Math.min(option1, option2);
        return memo[step];
    }

    /**
     * Alternative State Definition (for learning)
     *
     * dp[i] = minimum cost to reach the top starting from step i
     * This is a different but valid way to think about the problem
     */
    public int minCostClimbingStairsAlternative(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];

        // Base cases: cost to reach top from last two steps
        dp[n-1] = cost[n-1];
        dp[n-2] = cost[n-2];

        // Fill backwards
        for (int i = n-3; i >= 0; i--) {
            // From step i, we can go to i+1 or i+2
            dp[i] = cost[i] + Math.min(dp[i+1], dp[i+2]);
        }

        // Can start from step 0 or step 1
        return Math.min(dp[0], dp[1]);
    }

    /**
     * Detailed trace for learning - shows step-by-step calculation
     */
    public int minCostClimbingStairsWithTrace(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];

        System.out.println("Cost array: " + Arrays.toString(cost));
        System.out.println("DP array size: " + (n + 1) + " (0 to " + n + ")");

        // Base cases
        dp[0] = 0;
        dp[1] = 0;
        System.out.println("Base cases: dp[0] = 0, dp[1] = 0");

        // Fill dp table with trace
        for (int i = 2; i <= n; i++) {
            int option1 = dp[i-1] + cost[i-1];  // Come from step i-1
            int option2 = dp[i-2] + cost[i-2];  // Come from step i-2
            dp[i] = Math.min(option1, option2);

            System.out.printf("dp[%d]: min(%d + %d, %d + %d) = min(%d, %d) = %d%n",
                    i, dp[i-1], cost[i-1], dp[i-2], cost[i-2],
                    option1, option2, dp[i]);
        }

        System.out.println("Final DP array: " + Arrays.toString(dp));
        System.out.println("Answer: " + dp[n]);
        return dp[n];
    }

    public static void main(String[] args) {
        MinCostClimbingStairs solution = new MinCostClimbingStairs();

        // Test Case 1: Example from LeetCode
        System.out.println("=== Test Case 1 ===");
        int[] cost1 = {10, 15, 20};
        System.out.println("Input: " + Arrays.toString(cost1));
        System.out.println("Expected: 15");
        System.out.println("Bottom-up: " + solution.minCostClimbingStairs(cost1));
        System.out.println("Optimized: " + solution.minCostClimbingStairsOptimized(cost1));
        System.out.println("Recursive: " + solution.minCostClimbingStairsRecursive(cost1));
        System.out.println();

        // Test Case 2: Another example
        System.out.println("=== Test Case 2 ===");
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println("Input: " + Arrays.toString(cost2));
        System.out.println("Expected: 6");
        System.out.println("Bottom-up: " + solution.minCostClimbingStairs(cost2));
        System.out.println("Optimized: " + solution.minCostClimbingStairsOptimized(cost2));
        System.out.println();

        // Test Case 3: Minimum input
        System.out.println("=== Test Case 3 ===");
        int[] cost3 = {1, 2};
        System.out.println("Input: " + Arrays.toString(cost3));
        System.out.println("Expected: 1");
        System.out.println("Bottom-up: " + solution.minCostClimbingStairs(cost3));
        System.out.println();

        // Detailed trace for understanding
        System.out.println("=== DETAILED TRACE ===");
        int[] traceArray = {10, 15, 20};
        solution.minCostClimbingStairsWithTrace(traceArray);
        System.out.println();

        // Compare different approaches
        System.out.println("=== APPROACH COMPARISON ===");
        int[] testArray = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};

        long start = System.nanoTime();
        int result1 = solution.minCostClimbingStairs(testArray);
        long time1 = System.nanoTime() - start;

        start = System.nanoTime();
        int result2 = solution.minCostClimbingStairsOptimized(testArray);
        long time2 = System.nanoTime() - start;

        start = System.nanoTime();
        int result3 = solution.minCostClimbingStairsRecursive(testArray);
        long time3 = System.nanoTime() - start;

        start = System.nanoTime();
        int result4 = solution.minCostClimbingStairsAlternative(testArray);
        long time4 = System.nanoTime() - start;

        System.out.printf("Bottom-up: %d (%.2f μs)%n", result1, time1/1000.0);
        System.out.printf("Space-optimized: %d (%.2f μs)%n", result2, time2/1000.0);
        System.out.printf("Top-down recursive: %d (%.2f μs)%n", result3, time3/1000.0);
        System.out.printf("Alternative approach: %d (%.2f μs)%n", result4, time4/1000.0);

        boolean allMatch = result1 == result2 && result2 == result3 && result3 == result4;
        System.out.println("All approaches match: " + allMatch);
    }
}