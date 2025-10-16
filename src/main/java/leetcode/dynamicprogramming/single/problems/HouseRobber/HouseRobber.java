package leetcode.dynamicprogramming.single.problems.HouseRobber;

/**
 * LeetCode #198: House Robber
 * Difficulty: Medium
 *
 * Problem: Rob houses in a line to maximize money without robbing adjacent houses.
 *
 * Algorithm: Dynamic Programming - Decision Making Pattern
 * Time Complexity: O(n)
 * Space Complexity: O(1) - space optimized
 */
public class HouseRobber {

    /**
     * Space-optimized solution using O(1) space
     *
     * State Definition: dp[i] = maximum money from houses 0 to i
     * Recurrence: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
     * Base Cases: dp[0] = nums[0], dp[1] = max(nums[0], nums[1])
     */
    public int rob(int[] nums) {

        // Edge case: single house
        if (nums.length == 1) {
            return nums[0];
        }

        // Base cases - use variables instead of array for O(1) space
        int prev2 = nums[0];                        // dp[i-2]: best from house 0
        int prev1 = Math.max(nums[0], nums[1]);     // dp[i-1]: best from houses 0,1

        // Fill DP table from house 2 onwards
        for (int i = 2; i < nums.length; i++) {
            // Two choices: rob current house or skip it
            int robCurrent = prev2 + nums[i];       // Rob house i + best from i-2
            int skipCurrent = prev1;                // Skip house i, keep best from i-1

            int current = Math.max(robCurrent, skipCurrent);

            // Update for next iteration
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    /**
     * Alternative: DP Array solution (easier to understand)
     * Space Complexity: O(n)
     */
    public int robWithArray(int[] nums) {
        if (nums.length == 1) return nums[0];

        int[] dp = new int[nums.length];

        // Base cases
        dp[0] = nums[0];                           // Only house 0: rob it
        dp[1] = Math.max(nums[0], nums[1]);        // Houses 0,1: rob the better one

        // Fill the rest of the table
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }

        return dp[nums.length - 1];
    }

    /**
     * Recursive solution with memoization (for understanding)
     * This helps see the problem structure before optimization
     */
    public int robRecursive(int[] nums) {
        Integer[] memo = new Integer[nums.length];
        return robHelper(nums, nums.length - 1, memo);
    }

    private int robHelper(int[] nums, int i, Integer[] memo) {
        // Base cases
        if (i < 0) return 0;
        if (i == 0) return nums[0];

        // Check memoization
        if (memo[i] != null) return memo[i];

        // Two choices: rob house i or skip it
        int robCurrent = nums[i] + robHelper(nums, i - 2, memo);
        int skipCurrent = robHelper(nums, i - 1, memo);

        memo[i] = Math.max(robCurrent, skipCurrent);
        return memo[i];
    }

    /**
     * Test the solution with example cases
     */
    public static void main(String[] args) {
        HouseRobber solution = new HouseRobber();

        // Test Case 1: [1,2,3,1] → Expected: 4
        int[] test1 = {1, 2, 3, 1};
        System.out.println("Test 1: " + solution.rob(test1)); // Should output 4

        // Test Case 2: [2,7,9,3,1] → Expected: 12
        int[] test2 = {2, 7, 9, 3, 1};
        System.out.println("Test 2: " + solution.rob(test2)); // Should output 12

        // Test Case 3: Single house [5] → Expected: 5
        int[] test3 = {5};
        System.out.println("Test 3: " + solution.rob(test3)); // Should output 5

        // Test Case 4: Two houses [1,2] → Expected: 2
        int[] test4 = {1, 2};
        System.out.println("Test 4: " + solution.rob(test4)); // Should output 2

        // Trace through example [2,7,9,3,1] step by step
        traceExample();
    }

    /**
     * Trace through the algorithm step by step for learning
     */
    private static void traceExample() {
        System.out.println("\n--- Tracing [2,7,9,3,1] ---");
        int[] nums = {2, 7, 9, 3, 1};
        int[] dp = new int[nums.length];

        // Base cases
        dp[0] = nums[0];
        System.out.println("dp[0] = " + dp[0] + " (only house 0, rob it)");

        dp[1] = Math.max(nums[0], nums[1]);
        System.out.println("dp[1] = max(" + nums[0] + "," + nums[1] + ") = " + dp[1]);

        // Fill the rest
        for (int i = 2; i < nums.length; i++) {
            int robCurrent = dp[i-2] + nums[i];
            int skipCurrent = dp[i-1];
            dp[i] = Math.max(robCurrent, skipCurrent);

            System.out.println("dp[" + i + "] = max(rob house " + i +
                    ": " + dp[i-2] + "+" + nums[i] + "=" + robCurrent +
                    ", skip house " + i + ": " + skipCurrent + ") = " + dp[i]);
        }

        System.out.println("Final answer: " + dp[nums.length - 1]);
    }
}