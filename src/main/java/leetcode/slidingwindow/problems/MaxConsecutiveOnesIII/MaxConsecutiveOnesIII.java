package leetcode.slidingwindow.problems.MaxConsecutiveOnesIII;

public class MaxConsecutiveOnesIII {
    
    public int longestOnes(int[] nums, int k) {
        int left = 0;
        int zeroCount = 0;
        int maxLength = 0;
        
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public int longestOnesOptimized(int[] nums, int k) {
        int left = 0;
        int right = 0;
        
        while (right < nums.length) {
            if (nums[right] == 0) {
                k--;
            }
            
            if (k < 0) {
                if (nums[left] == 0) {
                    k++;
                }
                left++;
            }
            
            right++;
        }
        
        return right - left;
    }
    
    public int longestOnesVerbose(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int zeroCount = 0;
        int maxLength = 0;
        int bestStart = 0;
        int bestEnd = -1;
        
        while (right < nums.length) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            
            while (zeroCount > k && left <= right) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestStart = left;
                bestEnd = right;
            }
            
            right++;
        }
        
        if (bestEnd >= 0) {
            System.out.print("Best subarray [" + bestStart + ", " + bestEnd + "]: [");
            for (int i = bestStart; i <= bestEnd; i++) {
                System.out.print(nums[i]);
                if (i < bestEnd) System.out.print(",");
            }
            System.out.println("]");
        }
        
        return maxLength;
    }
    
    public static void main(String[] args) {
        MaxConsecutiveOnesIII solution = new MaxConsecutiveOnesIII();
        
        System.out.println("=== Testing Standard Solution ===\n");
        runTests(solution::longestOnes, false);
        
        System.out.println("\n=== Testing Optimized Solution ===\n");
        runTests(solution::longestOnesOptimized, false);
        
        System.out.println("\n=== Testing Verbose Solution (with subarray details) ===\n");
        runTests(solution::longestOnesVerbose, true);
        
        System.out.println("\n=== Edge Cases ===\n");
        testEdgeCases(solution);
        
        System.out.println("\n=== Step-by-Step Trace for Example 1 ===\n");
        traceExample(solution);
    }
    
    private static void runTests(java.util.function.BiFunction<int[], Integer, Integer> method, boolean verbose) {
        int[][] testArrays = {
            {1,1,1,0,0,0,1,1,1,1,0},
            {0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1},
            {1,1,1,1,1},
            {0,0,0,0,0},
            {1,0,1,0,1,0,1}
        };
        
        int[] kValues = {2, 3, 2, 3, 2};
        int[] expected = {6, 10, 5, 3, 5};
        String[] descriptions = {
            "Example 1: Mixed array with k=2",
            "Example 2: Longer array with k=3", 
            "All ones",
            "All zeros with k=3",
            "Alternating pattern"
        };
        
        for (int i = 0; i < testArrays.length; i++) {
            if (!verbose) System.out.print(descriptions[i] + ": ");
            else System.out.println(descriptions[i] + ":");
            
            int result = method.apply(testArrays[i], kValues[i]);
            String status = result == expected[i] ? "✓" : "✗";
            
            if (!verbose) {
                System.out.println(status + " (Expected: " + expected[i] + ", Got: " + result + ")");
            } else {
                System.out.println("Result: " + result + " " + status + " (Expected: " + expected[i] + ")\n");
            }
        }
    }
    
    private static void testEdgeCases(MaxConsecutiveOnesIII solution) {
        System.out.println("k = 0 (no flips allowed):");
        int[] nums1 = {1,1,0,1,1,1,0,1};
        System.out.println("Array: [1,1,0,1,1,1,0,1], k=0");
        System.out.println("Result: " + solution.longestOnes(nums1, 0) + " (Expected: 3)\n");
        
        System.out.println("k >= array length:");
        int[] nums2 = {0,0,0,0};
        System.out.println("Array: [0,0,0,0], k=5");
        System.out.println("Result: " + solution.longestOnes(nums2, 5) + " (Expected: 4)\n");
        
        System.out.println("Single element:");
        int[] nums3 = {0};
        System.out.println("Array: [0], k=1");
        System.out.println("Result: " + solution.longestOnes(nums3, 1) + " (Expected: 1)");
        
        int[] nums4 = {1};
        System.out.println("Array: [1], k=0");
        System.out.println("Result: " + solution.longestOnes(nums4, 0) + " (Expected: 1)");
    }
    
    private static void traceExample(MaxConsecutiveOnesIII solution) {
        int[] nums = {1,1,1,0,0,0,1,1,1,1,0};
        int k = 2;
        
        System.out.println("Array: [1,1,1,0,0,0,1,1,1,1,0], k=2\n");
        System.out.println("Step-by-step window expansion:\n");
        
        int left = 0, zeroCount = 0, maxLength = 0;
        
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
            
            System.out.printf("Right=%d, Left=%d, Window=[", right, left);
            for (int i = left; i <= right; i++) {
                System.out.print(nums[i]);
                if (i < right) System.out.print(",");
            }
            System.out.printf("], Zeros=%d, Length=%d, Max=%d\n", 
                            zeroCount, currentLength, maxLength);
        }
        
        System.out.println("\nFinal result: " + maxLength);
    }
}