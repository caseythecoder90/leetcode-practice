package leetcode.hashmap.problems.LongestConsecutiveSequence;

import java.util.*;

public class LongestConsecutiveSequence {

    /**
     * O(n) solution using HashSet
     * Key insight: Only start counting from the beginning of each sequence
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        // Store all numbers in HashSet for O(1) lookup
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestSequence = 0;

        for (int num : numSet) {
            // Only start counting if this is the beginning of a sequence
            // (i.e., num - 1 is not in the set)
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentSequence = 1;

                // Count consecutive numbers
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentSequence++;
                }

                longestSequence = Math.max(longestSequence, currentSequence);
            }
        }

        return longestSequence;
    }

    /**
     * Your original O(n log n) solution using sorting
     * Included for comparison
     */
    public int longestConsecutiveSorting(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return 1;

        Arrays.sort(nums);

        int count = 1;
        int maxConsecutive = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) {
                // Skip duplicates
                continue;
            } else if (nums[i] == nums[i - 1] + 1) {
                // Consecutive number found
                count++;
                maxConsecutive = Math.max(maxConsecutive, count);
            } else {
                // Reset count for new sequence
                count = 1;
            }
        }

        return maxConsecutive;
    }



    public static void main(String[] args) {
        LongestConsecutiveSequence solution = new LongestConsecutiveSequence();

        // Test case 1
        int[] nums1 = {100, 4, 200, 1, 3, 2};
        System.out.println("Test 1: " + Arrays.toString(nums1));
        System.out.println("Expected: 4, Got: " + solution.longestConsecutive(nums1));
        System.out.println();

        // Test case 2
        int[] nums2 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println("Test 2: " + Arrays.toString(nums2));
        System.out.println("Expected: 9, Got: " + solution.longestConsecutive(nums2));
        System.out.println();

        // Test case 3
        int[] nums3 = {1, 0, 1, 2};
        System.out.println("Test 3: " + Arrays.toString(nums3));
        System.out.println("Expected: 3, Got: " + solution.longestConsecutive(nums3));
        System.out.println();

        // Test case 4: Empty array
        int[] nums4 = {};
        System.out.println("Test 4: Empty array");
        System.out.println("Expected: 0, Got: " + solution.longestConsecutive(nums4));
        System.out.println();

        // Test case 5: Single element
        int[] nums5 = {1};
        System.out.println("Test 5: " + Arrays.toString(nums5));
        System.out.println("Expected: 1, Got: " + solution.longestConsecutive(nums5));
        System.out.println();

        // Test case 6: No consecutive numbers
        int[] nums6 = {10, 5, 100};
        System.out.println("Test 6: " + Arrays.toString(nums6));
        System.out.println("Expected: 1, Got: " + solution.longestConsecutive(nums6));
        System.out.println();

        // Performance comparison for larger input
        System.out.println("=== Performance Comparison ===");
        int[] largeArray = new int[10000];
        Random rand = new Random(42);
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = rand.nextInt(100000);
        }

        long start = System.nanoTime();
        int result1 = solution.longestConsecutive(largeArray);
        long hashSetTime = System.nanoTime() - start;

        start = System.nanoTime();
        int result2 = solution.longestConsecutiveSorting(largeArray);
        long sortingTime = System.nanoTime() - start;

        System.out.println("HashSet O(n) approach: " + hashSetTime / 1000000.0 + " ms, Result: " + result1);
        System.out.println("Sorting O(n log n) approach: " + sortingTime / 1000000.0 + " ms, Result: " + result2);
        System.out.println("Speed improvement: " + (sortingTime / (double) hashSetTime) + "x");
    }
}