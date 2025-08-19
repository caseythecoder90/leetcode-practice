package leetcode.arrays.problems.JumpGame;

/**
 * LeetCode 55: Jump Game
 * 
 * Pattern: Greedy Algorithm (Reachability)
 * Difficulty: Medium
 * 
 * Problem: Given an array where each element represents maximum jump length,
 * determine if you can reach the last index starting from the first index.
 * 
 * Key Insight: Track the farthest reachable position greedily
 */
public class JumpGame {
    
    /**
     * OPTIMAL SOLUTION: Greedy Algorithm
     * 
     * Approach: Track farthest reachable position
     * Time: O(n), Space: O(1)
     */
    public boolean canJump(int[] nums) {
        int farthest = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // If current position is unreachable, return false
            if (i > farthest) return false;
            
            // Update the farthest reachable position
            farthest = Math.max(farthest, i + nums[i]);
            
            // Early termination: if we can reach the end
            if (farthest >= nums.length - 1) return true;
        }
        
        return true;
    }
    
    /**
     * ALTERNATIVE: Greedy with Different Logic
     * 
     * Approach: Track remaining reach from current position
     * Time: O(n), Space: O(1)
     */
    public boolean canJumpAlternative(int[] nums) {
        int reach = 0;
        
        for (int i = 0; i < nums.length; i++) {
            if (reach < i) return false;  // Can't reach position i
            reach = Math.max(reach, i + nums[i]);
        }
        
        return reach >= nums.length - 1;
    }
    
    /**
     * BACKWARDS GREEDY: Start from End
     * 
     * Approach: Work backwards, track leftmost good position
     * Time: O(n), Space: O(1)
     */
    public boolean canJumpBackwards(int[] nums) {
        int lastGoodIndex = nums.length - 1;
        
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= lastGoodIndex) {
                lastGoodIndex = i;
            }
        }
        
        return lastGoodIndex == 0;
    }
    
    /**
     * DYNAMIC PROGRAMMING (Inefficient but Educational)
     * 
     * Approach: DP[i] = true if position i is reachable
     * Time: O(n²), Space: O(n)
     */
    public boolean canJumpDP(int[] nums) {
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;  // First position is always reachable
        
        for (int i = 0; i < nums.length; i++) {
            if (!dp[i]) continue;  // Position i is not reachable
            
            // From position i, we can jump to positions i+1 to i+nums[i]
            for (int j = i + 1; j <= Math.min(i + nums[i], nums.length - 1); j++) {
                dp[j] = true;
                if (j == nums.length - 1) return true;  // Reached the end
            }
        }
        
        return dp[nums.length - 1];
    }
    
    /**
     * RECURSIVE SOLUTION (Exponential - For Understanding Only)
     * 
     * Approach: Try all possible jumps from each position
     * Time: O(2^n), Space: O(n) for recursion stack
     */
    public boolean canJumpRecursive(int[] nums) {
        return canJumpFromPosition(0, nums);
    }
    
    private boolean canJumpFromPosition(int position, int[] nums) {
        if (position == nums.length - 1) return true;
        
        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (canJumpFromPosition(nextPosition, nums)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * DEMONSTRATION AND TESTING
     */
    public static void main(String[] args) {
        JumpGame solution = new JumpGame();
        
        System.out.println("=== JUMP GAME TEST CASES ===");
        
        // Test Case 1: Can reach end
        int[] test1 = {2, 3, 1, 1, 4};
        System.out.println("Test 1: " + java.util.Arrays.toString(test1));
        System.out.println("Expected: true");
        System.out.println("Greedy:     " + solution.canJump(test1));
        System.out.println("Alternative: " + solution.canJumpAlternative(test1));
        System.out.println("Backwards:  " + solution.canJumpBackwards(test1));
        System.out.println("DP:         " + solution.canJumpDP(test1));
        System.out.println();
        
        // Test Case 2: Cannot reach end
        int[] test2 = {3, 2, 1, 0, 4};
        System.out.println("Test 2: " + java.util.Arrays.toString(test2));
        System.out.println("Expected: false");
        System.out.println("Greedy:     " + solution.canJump(test2));
        System.out.println("Alternative: " + solution.canJumpAlternative(test2));
        System.out.println("Backwards:  " + solution.canJumpBackwards(test2));
        System.out.println("DP:         " + solution.canJumpDP(test2));
        System.out.println();
        
        // Test Case 3: Single element
        int[] test3 = {0};
        System.out.println("Test 3: " + java.util.Arrays.toString(test3));
        System.out.println("Expected: true");
        System.out.println("Greedy: " + solution.canJump(test3));
        System.out.println();
        
        // Test Case 4: All zeros except first
        int[] test4 = {1, 0, 0, 0};
        System.out.println("Test 4: " + java.util.Arrays.toString(test4));
        System.out.println("Expected: false");
        System.out.println("Greedy: " + solution.canJump(test4));
        System.out.println();
        
        // Test Case 5: Can just barely reach
        int[] test5 = {1, 1, 1, 1};
        System.out.println("Test 5: " + java.util.Arrays.toString(test5));
        System.out.println("Expected: true");
        System.out.println("Greedy: " + solution.canJump(test5));
        System.out.println();
        
        // Detailed trace
        System.out.println("=== DETAILED TRACE ===");
        traceAlgorithm(test1);
        System.out.println();
        traceAlgorithm(test2);
        
        // Performance comparison
        performanceTest();
        
        // Key insights
        explainWhy();
    }
    
    private static void traceAlgorithm(int[] nums) {
        System.out.println("Tracing: " + java.util.Arrays.toString(nums));
        int farthest = 0;
        
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) {
                System.out.println("  i=" + i + ": UNREACHABLE (i > farthest=" + farthest + ")");
                return;
            }
            
            int newFarthest = Math.max(farthest, i + nums[i]);
            System.out.println("  i=" + i + ": farthest = max(" + farthest + ", " + 
                              i + "+" + nums[i] + ") = " + newFarthest);
            farthest = newFarthest;
            
            if (farthest >= nums.length - 1) {
                System.out.println("  SUCCESS: Can reach end (farthest=" + farthest + 
                                 " >= " + (nums.length - 1) + ")");
                return;
            }
        }
        System.out.println("  Final result: " + (farthest >= nums.length - 1));
    }
    
    private static void performanceTest() {
        System.out.println("=== PERFORMANCE COMPARISON ===");
        
        // Create large test case
        int[] largeTest = new int[1000];
        for (int i = 0; i < largeTest.length; i++) {
            largeTest[i] = 1;  // Each position can jump 1 step
        }
        
        JumpGame solution = new JumpGame();
        
        // Test greedy approach
        long startTime = System.nanoTime();
        boolean greedyResult = solution.canJump(largeTest);
        long greedyTime = System.nanoTime() - startTime;
        
        // Test DP approach
        startTime = System.nanoTime();
        boolean dpResult = solution.canJumpDP(largeTest);
        long dpTime = System.nanoTime() - startTime;
        
        System.out.printf("Large test (n=1000):%n");
        System.out.printf("Greedy: %b (%.2f ms)%n", greedyResult, greedyTime/1_000_000.0);
        System.out.printf("DP:     %b (%.2f ms)%n", dpResult, dpTime/1_000_000.0);
        System.out.printf("Speedup: %.1fx%n", (double)dpTime/greedyTime);
        System.out.println();
    }
    
    private static void explainWhy() {
        System.out.println("=== WHY GREEDY WORKS ===");
        System.out.println("Key insight: We only need to know IF we can reach the end,");
        System.out.println("not HOW to reach it optimally.");
        System.out.println();
        System.out.println("Greedy choice: Always track the farthest reachable position.");
        System.out.println("Why optimal: If we can't reach position i, we definitely");
        System.out.println("can't reach any position beyond i.");
        System.out.println();
        System.out.println("This is different from Jump Game II (minimum jumps),");
        System.out.println("where we need the actual optimal path.");
        System.out.println();
        System.out.println("Time: O(n) - single pass through array");
        System.out.println("Space: O(1) - only tracking one variable");
    }
}
