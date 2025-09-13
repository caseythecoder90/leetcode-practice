package leetcode.slidingwindow.problems.MaximumAverageSubarray;

public class MaximumAverageSubarray {
    
    /**
     * Clean, optimized solution using classic sliding window pattern
     * Time: O(n), Space: O(1)
     */
    public double findMaxAverage(int[] nums, int k) {
        int windowSum = 0;
        
        // Build initial window of size k
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int maxSum = windowSum;
        
        // Slide the window: add new element, remove old element
        for (int i = k; i < nums.length; i++) {
            windowSum = windowSum + nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return (double) maxSum / k;
    }
    
    /**
     * Your original solution with improvements and comments
     * Shows what could be enhanced
     */
    public double findMaxAverageImproved(int[] nums, int k) {
        // Improvement 1: Use more descriptive variable names
        int windowSum = 0;  // Better than 'max' since it's actually a sum
        
        // Improvement 2: Simpler initial sum calculation
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int maxSum = windowSum;
        
        // Improvement 3: Cleaner sliding logic without tracking front/back
        for (int i = k; i < nums.length; i++) {
            // Remove leftmost element of previous window: nums[i - k]
            // Add rightmost element of new window: nums[i]
            windowSum = windowSum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return (double) maxSum / k;
    }
    
    /**
     * Alternative solution using running sum approach
     * Shows different way to think about the problem
     */
    public double findMaxAverageAlternative(int[] nums, int k) {
        double maxAverage = Integer.MIN_VALUE;
        int windowSum = 0;
        
        for (int i = 0; i < nums.length; i++) {
            windowSum += nums[i];
            
            // When we have a complete window
            if (i >= k - 1) {
                double average = (double) windowSum / k;
                maxAverage = Math.max(maxAverage, average);
                
                // Remove the leftmost element for next iteration
                windowSum -= nums[i - k + 1];
            }
        }
        
        return maxAverage;
    }
    
    /**
     * Solution with edge case handling and validation
     */
    public double findMaxAverageWithValidation(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }
        if (k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("k must be between 1 and array length");
        }
        
        // Special case: if k equals array length, return average of entire array
        if (k == nums.length) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            return (double) sum / k;
        }
        
        // Standard sliding window approach
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int maxSum = windowSum;
        
        for (int i = k; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return (double) maxSum / k;
    }
    
    public static void main(String[] args) {
        MaximumAverageSubarray solution = new MaximumAverageSubarray();
        
        // Test case 1
        int[] nums1 = {1, 12, -5, -6, 50, 3};
        int k1 = 4;
        System.out.printf("Test 1: %.5f (Expected: 12.75000)\n", 
                         solution.findMaxAverage(nums1, k1));
        
        // Test case 2
        int[] nums2 = {5};
        int k2 = 1;
        System.out.printf("Test 2: %.5f (Expected: 5.00000)\n", 
                         solution.findMaxAverage(nums2, k2));
        
        // Test case 3: All negative numbers
        int[] nums3 = {-1, -2, -3, -4, -5};
        int k3 = 2;
        System.out.printf("Test 3: %.5f (Expected: -1.50000)\n", 
                         solution.findMaxAverage(nums3, k3));
        
        // Test case 4: k equals array length
        int[] nums4 = {1, 2, 3, 4, 5};
        int k4 = 5;
        System.out.printf("Test 4: %.5f (Expected: 3.00000)\n", 
                         solution.findMaxAverage(nums4, k4));
        
        // Test case 5: Large numbers
        int[] nums5 = {10000, 10000, 10000, 10000};
        int k5 = 2;
        System.out.printf("Test 5: %.5f (Expected: 10000.00000)\n", 
                         solution.findMaxAverage(nums5, k5));
    }
}