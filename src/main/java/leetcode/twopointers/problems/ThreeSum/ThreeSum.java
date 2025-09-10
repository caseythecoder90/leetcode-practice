package leetcode.twopointers.problems.ThreeSum;

import java.util.*;

public class ThreeSum {
    
    /**
     * Optimal Solution: Two Pointers Approach
     * Time: O(n²), Space: O(1) excluding output
     * Key insight: Fix first element, use two pointers for remaining two
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Sort the array to enable two-pointer technique
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left pointer
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // Skip duplicates for right pointer  
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    // CRITICAL: Move past the current values to find new combinations
                    // Without this, we'd be stuck at the same positions forever!
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;  // Need larger sum, move left pointer right
                } else {
                    right--; // Need smaller sum, move right pointer left
                }
            }
        }
        
        return result;
    }
    
    /**
     * Brute Force Approach - For Learning Purposes
     * Time: O(n³), Space: O(1) excluding output
     * Not efficient but helps understand the problem
     */
    public List<List<Integer>> threeSumBruteForce(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Set<List<Integer>> seen = new HashSet<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        seen.add(triplet);
                    }
                }
            }
        }
        
        result.addAll(seen);
        return result;
    }
    
    /**
     * Hash Set Approach - Alternative Implementation
     * Time: O(n²), Space: O(n) for the hash set
     * Fix first element, use hash set to find pair that sums to -nums[i]
     */
    public List<List<Integer>> threeSumHashSet(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // Still need sorting for duplicate handling
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            Set<Integer> seen = new HashSet<>();
            int target = -nums[i];
            
            for (int j = i + 1; j < nums.length; j++) {
                int complement = target - nums[j];
                
                if (seen.contains(complement)) {
                    result.add(Arrays.asList(nums[i], complement, nums[j]));
                    // Skip duplicates for second element
                    while (j + 1 < nums.length && nums[j] == nums[j + 1]) j++;
                }
                seen.add(nums[j]);
            }
        }
        
        return result;
    }
    
    /**
     * Your original approach - with issues highlighted
     */
    public List<List<Integer>> yourOriginalApproach(int[] nums) {
        List<List<Integer>> triplets = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = i + 2;  // ISSUE: Should start from end

            while (k < nums.length) {
                int sum = nums[i] + nums[j] + nums[k];

                if (sum == 0) {
                    triplets.add(List.of(nums[i], nums[j], nums[k]));
                    j++; k++;  // ISSUE: Should move towards each other
                } else if (sum > 0) {
                    break;  // ISSUE: Should adjust pointers, not break
                } else {
                    j++; k++;  // ISSUE: Moving both in same direction
                }
            }
        }
        return triplets; 
    }

    public static void main(String[] args) {
        ThreeSum solution = new ThreeSum();
        
        // Test Case 1: Standard case
        System.out.println("=== Test Case 1: Standard Case ===");
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("Two Pointers: " + solution.threeSum(nums1));
        System.out.println("Hash Set:     " + solution.threeSumHashSet(nums1));
        System.out.println("Brute Force:  " + solution.threeSumBruteForce(nums1));
        System.out.println("Expected: [[-1,-1,2],[-1,0,1]]");
        
        // Test Case 2: No solution
        System.out.println("\n=== Test Case 2: No Solution ===");
        int[] nums2 = {0, 1, 1};
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("Output: " + solution.threeSum(nums2));
        System.out.println("Expected: []");
        
        // Test Case 3: All zeros
        System.out.println("\n=== Test Case 3: All Zeros ===");
        int[] nums3 = {0, 0, 0};
        System.out.println("Input: " + Arrays.toString(nums3));
        System.out.println("Output: " + solution.threeSum(nums3));
        System.out.println("Expected: [[0,0,0]]");
        
        // Test Case 4: Duplicates handling
        System.out.println("\n=== Test Case 4: Many Duplicates ===");
        int[] nums4 = {-2, 0, 0, 2, 2};
        System.out.println("Input: " + Arrays.toString(nums4));
        System.out.println("Output: " + solution.threeSum(nums4));
        System.out.println("Expected: [[-2,0,2]]");
        
        // Explaining the key insight about left++/right-- after duplicate skipping
        System.out.println("\n=== Key Insight: Why left++/right-- after duplicate skipping? ===");
        System.out.println("Example: [-2, 0, 0, 2, 2] - found triplet [-2, 0, 2]");
        System.out.println("After finding triplet at indices [0, 1, 3]:");
        System.out.println("1. Skip duplicates: left stays at 1, right moves to 4");
        System.out.println("2. Without left++/right--: we'd be stuck at [1, 4] forever!");
        System.out.println("3. With left++/right--: we move to [2, 3] to find NEW combinations");
        System.out.println("This is WHY we need the final increment after duplicate skipping!");
    }
}