package leetcode.arrays.problems.IncreasingTripletSubsequence;

/**
 * 334. Increasing Triplet Subsequence
 * 
 * Given an integer array nums, return true if there exists a triple of indices 
 * (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k].
 * 
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(1) - only two variables used
 * 
 * Key Insight: Use greedy approach with two candidates:
 * - first: smallest number seen so far
 * - second: smallest number greater than first
 * If we find any number greater than second, we have our triplet!
 */
public class IncreasingTripletSubsequence {
    
    /**
     * Optimal greedy solution - O(n) time, O(1) space
     */
    public boolean increasingTriplet(int[] nums) {
        // Initialize to maximum values
        int first = Integer.MAX_VALUE;   // Smallest seen so far
        int second = Integer.MAX_VALUE;  // Smallest number > first
        
        for (int num : nums) {
            if (num <= first) {
                // Found new smallest number
                first = num;
            } else if (num <= second) {
                // Found new second smallest (greater than first)
                second = num;
            } else {
                // Found number greater than both first and second
                // This means we have first < second < num
                return true;
            }
        }
        
        // No increasing triplet found
        return false;
    }
    
    /**
     * Alternative implementation with more explicit logic
     */
    public boolean increasingTripletAlternative(int[] nums) {
        if (nums.length < 3) return false;
        
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        
        for (int num : nums) {
            // Update first if we found smaller number
            if (num <= first) {
                first = num;
            }
            // Update second if num is greater than first but <= second
            else if (num <= second) {
                second = num;
            }
            // If num > second, we have our triplet
            else {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Brute force solution for comparison - O(n³) time
     * Not recommended for large inputs but good for understanding
     */
    public boolean increasingTripletBruteForce(int[] nums) {
        int n = nums.length;
        if (n < 3) return false;
        
        // Check all possible triplets
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] < nums[j] && nums[j] < nums[k]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        IncreasingTripletSubsequence solution = new IncreasingTripletSubsequence();
        
        // Test Case 1: Basic increasing sequence
        int[] test1 = {1, 2, 3, 4, 5};
        System.out.println("Test 1 - [1,2,3,4,5]: " + solution.increasingTriplet(test1));
        // Expected: true (any triplet works, e.g., indices 0,1,2)
        
        // Test Case 2: Decreasing sequence
        int[] test2 = {5, 4, 3, 2, 1};
        System.out.println("Test 2 - [5,4,3,2,1]: " + solution.increasingTriplet(test2));
        // Expected: false (no increasing triplet exists)
        
        // Test Case 3: Complex case with reordering
        int[] test3 = {2, 1, 5, 0, 4, 6};
        System.out.println("Test 3 - [2,1,5,0,4,6]: " + solution.increasingTriplet(test3));
        // Expected: true (triplet: 1 < 4 < 6 at indices 1, 4, 5)
        
        // Test Case 4: Edge case - exactly 3 elements increasing
        int[] test4 = {1, 2, 3};
        System.out.println("Test 4 - [1,2,3]: " + solution.increasingTriplet(test4));
        // Expected: true
        
        // Test Case 5: Edge case - exactly 3 elements decreasing
        int[] test5 = {3, 2, 1};
        System.out.println("Test 5 - [3,2,1]: " + solution.increasingTriplet(test5));
        // Expected: false
        
        // Test Case 6: With negative numbers
        int[] test6 = {-1, 0, 1};
        System.out.println("Test 6 - [-1,0,1]: " + solution.increasingTriplet(test6));
        // Expected: true
        
        // Test Case 7: All equal elements
        int[] test7 = {1, 1, 1, 1};
        System.out.println("Test 7 - [1,1,1,1]: " + solution.increasingTriplet(test7));
        // Expected: false
        
        // Test Case 8: Two elements only
        int[] test8 = {1, 2};
        System.out.println("Test 8 - [1,2]: " + solution.increasingTriplet(test8));
        // Expected: false (need at least 3 elements)
        
        // Test Case 9: Triplet at the end
        int[] test9 = {20, 100, 10, 12, 5, 13};
        System.out.println("Test 9 - [20,100,10,12,5,13]: " + solution.increasingTriplet(test9));
        // Expected: true (5 < 12 < 13 or 10 < 12 < 13)
        
        // Performance demonstration
        System.out.println("\n--- Performance Comparison ---");
        int[] largeTest = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeTest[i] = i; // Increasing sequence
        }
        
        long startTime = System.nanoTime();
        boolean result1 = solution.increasingTriplet(largeTest);
        long endTime = System.nanoTime();
        System.out.println("Greedy solution: " + result1 + " (Time: " + (endTime - startTime) + " ns)");
        
        // Note: Brute force would be too slow for this size, so we skip it
        System.out.println("Brute force skipped for large input (would be ~1 billion operations)");
    }
}