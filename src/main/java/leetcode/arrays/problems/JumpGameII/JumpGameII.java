package leetcode.arrays.problems.JumpGameII;

import java.util.Arrays;

public class JumpGameII {
    
    public int jump(int[] nums) {
        return jumpDP(nums);
    }
    
    public int jumpDP(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 0; i < n; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            
            int maxReach = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= maxReach; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
            
            if (dp[n - 1] != Integer.MAX_VALUE) break;
        }
        
        return dp[n - 1];
    }
    
    public int jumpGreedy(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.min(Math.max(farthest, i + nums[i]), n - 1);
            
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                
                if (currentEnd >= n - 1) break;
            }
        }
        
        return jumps;
    }
    
    public int jumpDPOptimized(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 0; i < n - 1; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            
            int reach = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= reach; j++) {
                if (dp[j] > dp[i] + 1) {
                    dp[j] = dp[i] + 1;
                }
            }
        }
        
        return dp[n - 1];
    }
    
    private void printDPTable(int[] nums, int[] dp, int step, String description) {
        System.out.println("\n=== " + description + " ===");
        System.out.println("Step " + step + ":");
        System.out.print("Index:  ");
        for (int i = 0; i < nums.length; i++) {
            System.out.printf("%3d ", i);
        }
        System.out.println();
        
        System.out.print("Value:  ");
        for (int i = 0; i < nums.length; i++) {
            System.out.printf("%3d ", nums[i]);
        }
        System.out.println();
        
        System.out.print("DP:     ");
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] == Integer.MAX_VALUE) {
                System.out.print("inf ");
            } else {
                System.out.printf("%3d ", dp[i]);
            }
        }
        System.out.println();
    }
    
    public int jumpDPWithTrace(int[] nums) {
        System.out.println("\n🔍 JUMP GAME II - DYNAMIC PROGRAMMING DETAILED TRACE");
        System.out.println("==================================================");
        System.out.println("Problem: Find minimum jumps to reach last index");
        System.out.println("Input: " + Arrays.toString(nums));
        
        int n = nums.length;
        if (n <= 1) {
            System.out.println("Array has 0 or 1 element - no jumps needed");
            return 0;
        }
        
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        printDPTable(nums, dp, 0, "Initial DP Setup");
        
        for (int i = 0; i < n; i++) {
            if (dp[i] == Integer.MAX_VALUE) {
                System.out.println("\nSkipping index " + i + " (unreachable)");
                continue;
            }
            
            System.out.println("\n📍 Processing index " + i + " (value=" + nums[i] + ", jumps_to_reach=" + dp[i] + ")");
            int maxReach = Math.min(i + nums[i], n - 1);
            System.out.println("   Can reach positions " + (i + 1) + " to " + maxReach);
            
            boolean updated = false;
            for (int j = i + 1; j <= maxReach; j++) {
                int newJumps = dp[i] + 1;
                if (dp[j] > newJumps) {
                    System.out.println("   ✅ Update dp[" + j + "] from " + 
                                     (dp[j] == Integer.MAX_VALUE ? "∞" : dp[j]) + 
                                     " to " + newJumps);
                    dp[j] = newJumps;
                    updated = true;
                }
            }
            
            if (updated) {
                printDPTable(nums, dp, i + 1, "After processing index " + i);
            }
            
            if (dp[n - 1] != Integer.MAX_VALUE) {
                System.out.println("🎯 Reached final position! Early termination.");
                break;
            }
        }
        
        System.out.println("\n🏁 FINAL RESULT: " + dp[n - 1] + " jumps needed");
        return dp[n - 1];
    }
    
    public static void main(String[] args) {
        JumpGameII solution = new JumpGameII();
        
        System.out.println("=".repeat(60));
        System.out.println("         JUMP GAME II - COMPREHENSIVE TESTING");
        System.out.println("=".repeat(60));
        
        int[][] testCases = {
            {2, 3, 1, 1, 4},
            {2, 3, 0, 1, 4},
            {1, 1, 1, 1},
            {2, 1},
            {1},
            {3, 2, 1, 0, 4}
        };
        
        int[] expected = {2, 2, 3, 1, 0, 3};
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            System.out.println("\n" + "=".repeat(40));
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(nums));
            System.out.println("Expected: " + expected[i]);
            
            long startTime = System.nanoTime();
            int resultDP = solution.jumpDP(nums.clone());
            long dpTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int resultGreedy = solution.jumpGreedy(nums.clone());
            long greedyTime = System.nanoTime() - startTime;
            
            System.out.println("\n📊 RESULTS:");
            System.out.println("DP Solution:     " + resultDP + " (Time: " + dpTime/1000 + "μs)");
            System.out.println("Greedy Solution: " + resultGreedy + " (Time: " + greedyTime/1000 + "μs)");
            System.out.println("Expected:        " + expected[i]);
            
            boolean correct = (resultDP == expected[i] && resultGreedy == expected[i]);
            System.out.println("Status: " + (correct ? "✅ PASS" : "❌ FAIL"));
            
            if (nums.length <= 6) {
                solution.jumpDPWithTrace(nums.clone());
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎓 ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("Dynamic Programming:");
        System.out.println("  • Time Complexity: O(n²) in worst case");
        System.out.println("  • Space Complexity: O(n)");
        System.out.println("  • Pros: Intuitive, easy to understand and debug");
        System.out.println("  • Cons: Less efficient for large inputs");
        System.out.println();
        System.out.println("Greedy (BFS-style):");
        System.out.println("  • Time Complexity: O(n)");
        System.out.println("  • Space Complexity: O(1)");
        System.out.println("  • Pros: Optimal time and space complexity");
        System.out.println("  • Cons: Requires deeper insight, harder to derive");
        System.out.println();
        System.out.println("💡 For interviews: Start with DP to show understanding,");
        System.out.println("   then optimize to greedy if time permits!");
    }
}