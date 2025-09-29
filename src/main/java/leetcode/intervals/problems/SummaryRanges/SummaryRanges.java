package leetcode.intervals.problems.SummaryRanges;

import java.util.ArrayList;
import java.util.List;

public class SummaryRanges {

    // Clean and intuitive solution
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        if (nums.length == 0) return result;

        int start = 0;

        for (int i = 0; i < nums.length; i++) {
            // Check if we're at the end OR next number isn't consecutive
            if (i == nums.length - 1 || nums[i] + 1 != nums[i + 1]) {
                // Found end of current range
                if (start == i) {
                    // Single number range
                    result.add(String.valueOf(nums[start]));
                } else {
                    // Multi-number range
                    result.add(nums[start] + "->" + nums[i]);
                }
                // Start new range from next position
                start = i + 1;
            }
        }

        return result;
    }

    // Alternative solution using while loop (more explicit)
    public List<String> summaryRangesWhileLoop(int[] nums) {
        List<String> result = new ArrayList<>();
        int i = 0;

        while (i < nums.length) {
            int start = nums[i];

            // Keep moving while consecutive
            while (i + 1 < nums.length && nums[i] + 1 == nums[i + 1]) {
                i++;
            }

            // Format the range
            if (start == nums[i]) {
                result.add(String.valueOf(start));
            } else {
                result.add(start + "->" + nums[i]);
            }

            i++;
        }

        return result;
    }

    // Two-pointer solution (your approach, cleaned up)
    public List<String> summaryRangesTwoPointer(int[] nums) {
        List<String> result = new ArrayList<>();
        if (nums.length == 0) return result;

        for (int i = 0; i < nums.length; ) {
            int start = i;

            // Find end of consecutive sequence
            while (i + 1 < nums.length && nums[i + 1] == nums[i] + 1) {
                i++;
            }

            // Add the range
            if (start == i) {
                result.add(String.valueOf(nums[start]));
            } else {
                result.add(nums[start] + "->" + nums[i]);
            }

            i++;
        }

        return result;
    }

    public static void main(String[] args) {
        SummaryRanges solution = new SummaryRanges();

        // Test case 1
        int[] nums1 = {0, 1, 2, 4, 5, 7};
        System.out.println("Input: [0,1,2,4,5,7]");
        System.out.println("Output: " + solution.summaryRanges(nums1));
        System.out.println("Expected: [0->2, 4->5, 7]\n");

        // Test case 2
        int[] nums2 = {0, 2, 3, 4, 6, 8, 9};
        System.out.println("Input: [0,2,3,4,6,8,9]");
        System.out.println("Output: " + solution.summaryRanges(nums2));
        System.out.println("Expected: [0, 2->4, 6, 8->9]\n");

        // Test case 3: Empty array
        int[] nums3 = {};
        System.out.println("Input: []");
        System.out.println("Output: " + solution.summaryRanges(nums3));
        System.out.println("Expected: []\n");

        // Test case 4: Single element
        int[] nums4 = {5};
        System.out.println("Input: [5]");
        System.out.println("Output: " + solution.summaryRanges(nums4));
        System.out.println("Expected: [5]\n");

        // Test case 5: All consecutive
        int[] nums5 = {1, 2, 3, 4, 5};
        System.out.println("Input: [1,2,3,4,5]");
        System.out.println("Output: " + solution.summaryRanges(nums5));
        System.out.println("Expected: [1->5]\n");

        // Test case 6: No consecutive
        int[] nums6 = {1, 3, 5, 7, 9};
        System.out.println("Input: [1,3,5,7,9]");
        System.out.println("Output: " + solution.summaryRanges(nums6));
        System.out.println("Expected: [1, 3, 5, 7, 9]\n");
    }
}