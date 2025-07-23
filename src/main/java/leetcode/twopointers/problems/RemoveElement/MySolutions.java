package leetcode.twopointers.problems.RemoveElement;


import java.util.Arrays;

/**
 * Remove Element - Your Original Solution with Analysis
 * LeetCode Problem #27
 *
 * This file contains your original solution with detailed analysis comments
 * explaining what works well and what can be improved.
 */
public class MySolutions {

    /**
     * Your original solution using converging two pointers approach
     *
     * APPROACH ANALYSIS:
     * ✅ STRENGTHS:
     * - Correctly identified two-pointer pattern
     * - Used converging pointers (front/back) which is a valid approach
     * - Proper boundary checking with front < back
     * - Handles edge cases well
     * - Gets correct final result
     *
     * 🔧 AREAS FOR IMPROVEMENT:
     * - Unnecessary O(n) preprocessing with stream operation
     * - Complex nested while loop structure
     * - Redundant condition checking
     */
    public int removeElement(int[] nums, int val) {
        int front = 0, back = nums.length - 1;

        // 🔧 IMPROVEMENT OPPORTUNITY #1:
        // This stream operation is unnecessary - it adds O(n) time upfront
        // We can calculate the result directly from the final pointer positions
        int count = (int) Arrays.stream(nums)
                .filter(number -> number == val)
                .count();

        while (front < back) {
            // ✅ GOOD: Proper boundary checking prevents infinite loops

            // 🔧 IMPROVEMENT OPPORTUNITY #2:
            // These nested while loops work but make the logic more complex
            // A simple if-else would be cleaner
            while (front < back && nums[front] != val) {
                front++;
            }
            while (front < back && nums[back] == val) {
                back--;
            }

            // 🔧 IMPROVEMENT OPPORTUNITY #3:
            // This condition is redundant given the while loops above
            // If we reach here, either front == back (loop ends) or we need to swap
            if (front != back) {
                nums[front] = nums[back];
                front++;
                back--;
            }
        }

        // ✅ GOOD: Correct final calculation
        return nums.length - count;
    }

    /**
     * OPTIMIZED VERSION of your converging approach
     * This maintains your core idea but simplifies the implementation
     */
    public int removeElementOptimized(int[] nums, int val) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            if (nums[left] == val) {
                // Found element to remove, replace with element from right
                nums[left] = nums[right];
                right--; // Shrink valid region
            } else {
                // Good element, keep it and move left pointer
                left++;
            }
        }

        // Elements 0 to right are all non-val elements
        return right + 1;
    }

    /**
     * ALTERNATIVE: Fast/Slow approach (often easier to understand in interviews)
     */
    public int removeElementFastSlow(int[] nums, int val) {
        int slow = 0; // Next position for non-val element

        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow; // Number of non-val elements
    }

    public static void main(String[] args) {
        MySolutions solution = new MySolutions();

        // Test Case 1: Example from problem
        int[] nums1 = {3, 2, 2, 3};
        int val1 = 3;
        int[] original1 = nums1.clone();
        int result1 = solution.removeElement(nums1, val1);
        System.out.println("=== TEST CASE 1 ===");
        System.out.println("Original: " + Arrays.toString(original1) + ", val = " + val1);
        System.out.println("Your solution result: " + result1);
        System.out.println("Modified array: " + Arrays.toString(nums1));
        System.out.println("First " + result1 + " elements: " +
                Arrays.toString(Arrays.copyOf(nums1, result1)));
        System.out.println("Expected: 2 elements, should be [2, 2] in any order");
        System.out.println();

        // Test Case 2: Example from problem
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        int val2 = 2;
        int[] original2 = nums2.clone();
        int result2 = solution.removeElement(nums2, val2);
        System.out.println("=== TEST CASE 2 ===");
        System.out.println("Original: " + Arrays.toString(original2) + ", val = " + val2);
        System.out.println("Your solution result: " + result2);
        System.out.println("Modified array: " + Arrays.toString(nums2));
        System.out.println("First " + result2 + " elements: " +
                Arrays.toString(Arrays.copyOf(nums2, result2)));
        System.out.println("Expected: 5 elements, should be [0,1,4,0,3] in any order");
        System.out.println();

        // Test Case 3: Edge case - all elements equal val
        int[] nums3 = {2, 2, 2};
        int val3 = 2;
        int[] original3 = nums3.clone();
        int result3 = solution.removeElement(nums3, val3);
        System.out.println("=== TEST CASE 3 (Edge Case) ===");
        System.out.println("Original: " + Arrays.toString(original3) + ", val = " + val3);
        System.out.println("Your solution result: " + result3);
        System.out.println("Expected: 0 (no elements should remain)");
        System.out.println();

        // Test Case 4: Edge case - no elements equal val
        int[] nums4 = {1, 3, 5};
        int val4 = 2;
        int[] original4 = nums4.clone();
        int result4 = solution.removeElement(nums4, val4);
        System.out.println("=== TEST CASE 4 (Edge Case) ===");
        System.out.println("Original: " + Arrays.toString(original4) + ", val = " + val4);
        System.out.println("Your solution result: " + result4);
        System.out.println("Modified array: " + Arrays.toString(nums4));
        System.out.println("Expected: 3 (all elements should remain)");
        System.out.println();

        // Test Case 5: Single element
        int[] nums5 = {1};
        int val5 = 1;
        int[] original5 = nums5.clone();
        int result5 = solution.removeElement(nums5, val5);
        System.out.println("=== TEST CASE 5 (Edge Case) ===");
        System.out.println("Original: " + Arrays.toString(original5) + ", val = " + val5);
        System.out.println("Your solution result: " + result5);
        System.out.println("Expected: 0");
        System.out.println();

        // Compare with optimized versions
        System.out.println("=== COMPARING APPROACHES ===");
        int[] testArray = {0, 1, 2, 2, 3, 0, 4, 2};
        int testVal = 2;

        int[] arr1 = testArray.clone();
        int[] arr2 = testArray.clone();
        int[] arr3 = testArray.clone();

        int result_original = solution.removeElement(arr1, testVal);
        int result_optimized = solution.removeElementOptimized(arr2, testVal);
        int result_fastslow = solution.removeElementFastSlow(arr3, testVal);

        System.out.println("Original approach result: " + result_original);
        System.out.println("Optimized converging result: " + result_optimized);
        System.out.println("Fast/slow approach result: " + result_fastslow);
        System.out.println("All should give same result: " +
                (result_original == result_optimized && result_optimized == result_fastslow));
    }
}

/*
PERFORMANCE ANALYSIS:

Time Complexity:
- Your solution: O(n) for stream + O(n) for main loop = O(n) total
- Optimized: O(n) for main loop only

Space Complexity:
- All approaches: O(1) - only using a few extra variables

INTERVIEW FEEDBACK:

✅ What you did great:
1. Recognized this as a two-pointer problem immediately
2. Chose a valid approach (converging pointers)
3. Handled edge cases properly with boundary checking
4. Got the correct answer

🎯 What to focus on for next time:
1. Start with simpler approach first (fast/slow is more intuitive)
2. Avoid unnecessary preprocessing when possible
3. Keep the logic as simple as possible - fewer nested conditions
4. Practice explaining your thought process step by step

PATTERN RECOGNITION:
This problem type ("remove elements in-place") should immediately trigger:
1. Two pointers pattern
2. Consider if order matters (it doesn't here!)
3. Choose between fast/slow (order preservation) vs converging (efficiency)

Keep practicing! You're showing good pattern recognition skills.
*/
