package leetcode.slidingwindow.problems.MinimumSizeSubarraySum;

public class MinimumSizeSubarraySum {
    
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            
            while (sum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    public int minSubArrayLenOptimized(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            
            while (sum >= target) {
                int currentLength = right - left + 1;
                minLength = Math.min(minLength, currentLength);
                
                if (minLength == 1) {
                    return 1;
                }
                
                sum -= nums[left++];
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    public int minSubArrayLenVerbose(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        int bestStart = -1;
        int bestEnd = -1;
        
        System.out.println("Target: " + target);
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("\nWindow progression:");
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            System.out.printf("Right=%d: Added nums[%d]=%d, sum=%d\n", 
                            right, right, nums[right], sum);
            
            while (sum >= target) {
                int currentLength = right - left + 1;
                
                if (currentLength < minLength) {
                    minLength = currentLength;
                    bestStart = left;
                    bestEnd = right;
                    System.out.printf("  ✓ New minimum! Window [%d,%d], length=%d, sum=%d\n", 
                                    left, right, currentLength, sum);
                }
                
                sum -= nums[left];
                System.out.printf("  Shrinking: Removed nums[%d]=%d, sum=%d\n", 
                                left, nums[left], sum);
                left++;
            }
        }
        
        if (bestStart != -1) {
            System.out.print("\nBest subarray: [");
            for (int i = bestStart; i <= bestEnd; i++) {
                System.out.print(nums[i]);
                if (i < bestEnd) System.out.print(",");
            }
            System.out.println("] with length " + minLength);
        } else {
            System.out.println("\nNo valid subarray found!");
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    public int minSubArrayLenBinarySearch(int target, int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        int minLength = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int toFind = target + prefixSum[i];
            int bound = binarySearch(prefixSum, toFind);
            
            if (bound <= n) {
                minLength = Math.min(minLength, bound - i);
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    private int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    public static void main(String[] args) {
        MinimumSizeSubarraySum solution = new MinimumSizeSubarraySum();
        
        System.out.println("=== Testing Standard Solution ===\n");
        runTests(solution::minSubArrayLen);
        
        System.out.println("\n=== Testing Optimized Solution (Early Exit) ===\n");
        runTests(solution::minSubArrayLenOptimized);
        
        System.out.println("\n=== Testing Binary Search Solution ===\n");
        runTests(solution::minSubArrayLenBinarySearch);
        
        System.out.println("\n=== Verbose Trace Example ===\n");
        int[] example = {2, 3, 1, 2, 4, 3};
        solution.minSubArrayLenVerbose(7, example);
        
        System.out.println("\n=== Edge Cases ===\n");
        testEdgeCases(solution);
    }
    
    private static void runTests(java.util.function.BiFunction<Integer, int[], Integer> method) {
        int[][] arrays = {
            {2, 3, 1, 2, 4, 3},
            {1, 4, 4},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 3, 4, 5},
            {5, 1, 3, 5, 10, 7, 4, 9, 2, 8}
        };
        
        int[] targets = {7, 4, 11, 11, 15};
        int[] expected = {2, 1, 0, 3, 2};
        
        for (int i = 0; i < arrays.length; i++) {
            int result = method.apply(targets[i], arrays[i]);
            String status = result == expected[i] ? "✓" : "✗";
            System.out.printf("Test %d: target=%d, result=%d %s (expected=%d)\n", 
                            i + 1, targets[i], result, status, expected[i]);
        }
    }
    
    private static void testEdgeCases(MinimumSizeSubarraySum solution) {
        System.out.println("Single element equals target:");
        int[] test1 = {5};
        System.out.println("Array: [5], target=5, Result: " + 
                         solution.minSubArrayLen(5, test1) + " (Expected: 1)");
        
        System.out.println("\nSingle element less than target:");
        int[] test2 = {3};
        System.out.println("Array: [3], target=5, Result: " + 
                         solution.minSubArrayLen(5, test2) + " (Expected: 0)");
        
        System.out.println("\nAll elements needed:");
        int[] test3 = {1, 2, 3, 4};
        System.out.println("Array: [1,2,3,4], target=10, Result: " + 
                         solution.minSubArrayLen(10, test3) + " (Expected: 4)");
        
        System.out.println("\nLarge numbers:");
        int[] test4 = {10000, 10000, 10000};
        System.out.println("Array: [10000,10000,10000], target=10001, Result: " + 
                         solution.minSubArrayLen(10001, test4) + " (Expected: 2)");
    }
}