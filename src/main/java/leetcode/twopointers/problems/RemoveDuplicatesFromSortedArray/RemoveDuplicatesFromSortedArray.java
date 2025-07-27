package leetcode.twopointers.problems.RemoveDuplicatesFromSortedArray;

import java.util.TreeSet;

/**
 * LeetCode #26: Remove Duplicates from Sorted Array
 *
 * Problem: Given a sorted array, remove duplicates in-place and return the new length.
 * The first k elements should contain the unique elements in original order.
 *
 * Key Insights:
 * 1. Array is SORTED - duplicates are adjacent
 * 2. Need to modify in-place - use two pointers
 * 3. Fast/slow pointer pattern - fast scans, slow tracks placement
 * 4. Return count of unique elements, not the last index
 */

class RemoveDuplicatesFromSortedArray {

    /**
     * OPTIMAL SOLUTION: Two Pointers Approach
     *
     * Time Complexity: O(n) - single pass through array
     * Space Complexity: O(1) - only using two pointer variables
     *
     * Strategy: Use fast/slow pointers where:
     * - slow: points to the last unique element placed
     * - fast: scans array to find next unique element
     */
    public int removeDuplicates(int[] nums) {
        // Edge case: arrays with 0 or 1 element have no duplicates
        if (nums == null || nums.length <= 1) {
            return nums.length;
        }

        // slow points to the position of last unique element
        // Start at 0 because first element is always unique
        int slow = 0;

        // fast scans the array starting from index 1
        // We start at 1 because we need to compare with previous element
        for (int fast = 1; fast < nums.length; fast++) {

            // If current element is different from the last unique element
            // we found a new unique element
            if (nums[fast] != nums[slow]) {

                // Move slow pointer to next position
                slow++;

                // Place the new unique element at slow position
                nums[slow] = nums[fast];
            }

            // If nums[fast] == nums[slow], it's a duplicate, so we skip it
            // (fast pointer automatically advances in the for loop)
        }

        // slow is the index of last unique element
        // Return count (index + 1), not the index itself
        return slow + 1;
    }

    /**
     * ALTERNATIVE IMPLEMENTATION: Compare with Previous Element
     *
     * Same complexity, slightly different logic.
     * Instead of comparing with nums[slow], compare with nums[readIndex-1]
     */
    public int removeDuplicatesAlt1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums.length;
        }

        // writeIndex tracks where to place the next unique element
        // Start at 1 because first element is always unique
        int writeIndex = 1;

        for (int readIndex = 1; readIndex < nums.length; readIndex++) {

            // If current element is different from previous element,
            // it's a new unique element
            if (nums[readIndex] != nums[readIndex - 1]) {

                // Place it at writeIndex position
                nums[writeIndex] = nums[readIndex];

                // Move writeIndex to next available position
                writeIndex++;
            }
        }

        // writeIndex now points to the next available position,
        // which equals the count of unique elements
        return writeIndex;
    }

    /**
     * SUB-OPTIMAL SOLUTION: Using Extra Space (TreeSet)
     *
     * This is your original approach - it works but isn't optimal.
     * Time Complexity: O(n log n) - TreeSet operations
     * Space Complexity: O(n) - storing unique elements
     *
     * Why it's not preferred:
     * 1. Uses extra space when problem asks for in-place
     * 2. Slower due to TreeSet operations
     * 3. Doesn't exploit the sorted property of input
     */
    public int removeDuplicatesTreeSet(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // TreeSet automatically removes duplicates and maintains sorted order
        TreeSet<Integer> uniqueElements = new TreeSet<>();

        // Add all elements to TreeSet - O(n log n)
        for (int num : nums) {
            uniqueElements.add(num);
        }

        // Copy unique elements back to original array - O(n)
        int index = 0;
        for (int num : uniqueElements) {
            nums[index] = num;
            index++;
        }

        // Return count of unique elements
        return uniqueElements.size();
    }

    /**
     * DEMONSTRATION: Step-by-step trace for understanding
     *
     * Input: [0,0,1,1,1,2,2,3,3,4]
     * Expected: 5, nums = [0,1,2,3,4,_,_,_,_,_]
     */
    public int removeDuplicatesWithTrace(int[] nums) {
        System.out.println("Initial array: " + java.util.Arrays.toString(nums));

        if (nums == null || nums.length <= 1) {
            return nums.length;
        }

        int slow = 0;
        System.out.println("slow starts at: " + slow);

        for (int fast = 1; fast < nums.length; fast++) {
            System.out.printf("Step %d: fast=%d, slow=%d, nums[fast]=%d, nums[slow]=%d%n",
                    fast, fast, slow, nums[fast], nums[slow]);

            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
                System.out.printf("  → Found unique! New slow=%d, placed %d at position %d%n",
                        slow, nums[fast], slow);
                System.out.println("  → Array now: " + java.util.Arrays.toString(nums));
            } else {
                System.out.println("  → Duplicate, skip");
            }
        }

        System.out.println("Final array: " + java.util.Arrays.toString(nums));
        System.out.println("Count of unique elements: " + (slow + 1));
        return slow + 1;
    }
}

/**
 * EXTENDED SOLUTIONS: Related Problems Using Same Pattern
 */
class RelatedProblems {

    /**
     * LeetCode #80: Remove Duplicates from Sorted Array II
     * Allow each unique element to appear at most twice
     *
     * Key insight: Compare with element 2 positions back
     */
    public int removeDuplicatesII(int[] nums) {
        if (nums.length <= 2) return nums.length;

        // First 2 elements are always kept
        int slow = 2;

        for (int fast = 2; fast < nums.length; fast++) {
            // If current element is different from element 2 positions back,
            // we can include it (allows at most 2 duplicates)
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    /**
     * LeetCode #27: Remove Element
     * Remove all instances of a specific value
     *
     * Same fast/slow pattern, different condition
     */
    public int removeElement(int[] nums, int val) {
        int slow = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            // If current element is not the target value, keep it
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    /**
     * LeetCode #283: Move Zeroes
     * Move all zeros to the end while maintaining relative order
     *
     * Two-phase approach: compact non-zeros, then fill zeros
     */
    public void moveZeroes(int[] nums) {
        // Phase 1: Compact all non-zero elements to front
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        // Phase 2: Fill remaining positions with zeros
        while (slow < nums.length) {
            nums[slow] = 0;
            slow++;
        }
    }
}

/**
 * TEST CASES AND EDGE CASES
 */
class TestCases {

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArray solution = new RemoveDuplicatesFromSortedArray();

        // Test Case 1: Normal case with duplicates
        int[] test1 = {1, 1, 2};
        System.out.println("Test 1 result: " + solution.removeDuplicates(test1));
        System.out.println("Array: " + java.util.Arrays.toString(test1));
        // Expected: 2, [1, 2, _]

        // Test Case 2: Multiple duplicates
        int[] test2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println("Test 2 result: " + solution.removeDuplicates(test2));
        System.out.println("Array: " + java.util.Arrays.toString(test2));
        // Expected: 5, [0, 1, 2, 3, 4, _, _, _, _, _]

        // Edge Case 1: Empty array
        int[] test3 = {};
        System.out.println("Empty array result: " + solution.removeDuplicates(test3));

        // Edge Case 2: Single element
        int[] test4 = {1};
        System.out.println("Single element result: " + solution.removeDuplicates(test4));

        // Edge Case 3: No duplicates
        int[] test5 = {1, 2, 3, 4, 5};
        System.out.println("No duplicates result: " + solution.removeDuplicates(test5));
        System.out.println("Array: " + java.util.Arrays.toString(test5));

        // Edge Case 4: All same elements
        int[] test6 = {1, 1, 1, 1};
        System.out.println("All same result: " + solution.removeDuplicates(test6));
        System.out.println("Array: " + java.util.Arrays.toString(test6));
    }
}

/**
 * COMPLEXITY ANALYSIS SUMMARY:
 *
 * Two Pointers Approach:
 * - Time: O(n) - single pass through array
 * - Space: O(1) - only using pointer variables
 *
 * TreeSet Approach:
 * - Time: O(n log n) - TreeSet add operations
 * - Space: O(n) - storing unique elements
 *
 * Why Two Pointers is Better:
 * 1. Exploits sorted property (duplicates are adjacent)
 * 2. Modifies array in-place as required
 * 3. Single pass vs multiple operations
 * 4. Constant space vs linear space
 *
 * KEY TAKEAWAY:
 * When you see "sorted array + in-place modification",
 * think Two Pointers FIRST, not data structures!
 */