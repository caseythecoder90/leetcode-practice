package leetcode.arrays.problems.MergeSortedArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 88: Merge Sorted Array
 * Difficulty: Easy
 * Pattern: Arrays + Two Pointers
 *
 * Problem Statement:
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
 * and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 *
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 * The final sorted array should be stored inside nums1.
 *
 * Key Constraints:
 * - nums1 has length m + n (first m elements are actual data, last n elements are 0s)
 * - nums2 has length n
 * - Must merge IN-PLACE into nums1
 *
 * Examples:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 */
public class MergeSortedArray {

    /**
     * APPROACH 1: OPTIMAL - Right-to-Left Merge (RECOMMENDED FOR INTERVIEWS)
     *
     * Time Complexity: O(m + n) - visit each element exactly once
     * Space Complexity: O(1) - only use a few pointer variables
     *
     * Key Insight: Work BACKWARD to avoid overwriting elements we still need!
     *
     * Why this works:
     * 1. nums1 has extra space at the end (those zeros)
     * 2. By starting from the largest elements and working backward,
     *    we fill the zeros first and never overwrite data we need
     * 3. Each position gets filled exactly once with the correct element
     *
     * Mental Model: Think of it as "placing the largest available element
     * into the rightmost available position" repeatedly.
     */
    public void mergeOptimal(int[] nums1, int m, int[] nums2, int n) {
        // Three pointers:
        int i = m - 1;      // Points to last ACTUAL element in nums1 (not the zeros)
        int j = n - 1;      // Points to last element in nums2
        int k = m + n - 1;  // Points to last position in nums1 (where we write results)

        // Main merge loop: continue while both arrays have elements to process
        while (i >= 0 && j >= 0) {
            // Compare the current elements from both arrays
            // Always choose the LARGER one (since we're filling from right to left)
            if (nums1[i] >= nums2[j]) {
                // nums1's element is larger (or equal), so place it at position k
                nums1[k] = nums1[i];
                i--; // Move to previous element in nums1
            } else {
                // nums2's element is larger, so place it at position k
                nums1[k] = nums2[j];
                j--; // Move to previous element in nums2
            }
            k--; // Move to previous position for next placement
        }

        // Handle remaining elements:

        // If nums2 still has elements, copy them over
        // (They must be smaller than all processed elements from nums1)
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }

        // Note: If nums1 still has elements (i >= 0), we don't need to do anything!
        // Why? Because they're already in their correct positions in nums1.
        // This is the beauty of the right-to-left approach.
    }

    /**
     * APPROACH 2: EXTRA SPACE - Standard Merge (YOUR ORIGINAL APPROACH)
     *
     * Time Complexity: O(m + n) - visit each element exactly once
     * Space Complexity: O(m + n) - extra ArrayList to store results
     *
     * This is the classic "merge step" from merge sort algorithm.
     * Easier to understand and implement, but uses extra space.
     *
     * When to use: When space isn't a concern and you want simpler logic.
     * Interview note: Mention this approach first to show you understand merging,
     * then optimize to the in-place solution.
     */
    public void mergeWithExtraSpace(int[] nums1, int m, int[] nums2, int n) {
        // Two pointers for the input arrays
        int i = 0; // Pointer for nums1's actual elements
        int j = 0; // Pointer for nums2's elements

        // Temporary storage for the merged result
        List<Integer> result = new ArrayList<>();

        // Main merge loop: compare elements and add smaller one to result
        while (i < m && j < n) {
            // Get current elements from both arrays
            int num1 = nums1[i];
            int num2 = nums2[j];

            // Choose the smaller element (or nums1's element if equal)
            if (num1 <= num2) {
                result.add(num1);
                i++; // Move to next element in nums1
            } else {
                result.add(num2);
                j++; // Move to next element in nums2
            }
        }

        // Add any remaining elements from nums1
        while (i < m) {
            result.add(nums1[i]);
            i++;
        }

        // Add any remaining elements from nums2
        while (j < n) {
            result.add(nums2[j]);
            j++;
        }

        // Copy the merged result back to nums1
        for (int k = 0; k < m + n; k++) {
            nums1[k] = result.get(k);
        }
    }

    /**
     * APPROACH 3: LEFT-TO-RIGHT WITH INSERTION (EDUCATIONAL - NOT RECOMMENDED)
     *
     * Time Complexity: O(m * n) - worst case, lots of shifting
     * Space Complexity: O(1) - in-place
     *
     * This approach shows why the optimal solution is needed.
     * We insert each element from nums2 into its correct position in nums1,
     * but this requires shifting elements, leading to poor time complexity.
     *
     * Educational value: Demonstrates the importance of choosing the right direction!
     */
    public void mergeWithInsertion(int[] nums1, int m, int[] nums2, int n) {
        // Process each element in nums2
        for (int j = 0; j < n; j++) {
            int elementToInsert = nums2[j];

            // Find the correct position to insert this element
            int insertPos = findInsertPosition(nums1, m + j, elementToInsert);

            // Shift all elements to the right to make room
            // This is the expensive part - O(m) operations per insertion
            for (int k = m + j; k > insertPos; k--) {
                nums1[k] = nums1[k - 1];
            }

            // Insert the element at its correct position
            nums1[insertPos] = elementToInsert;
        }
    }

    /**
     * Helper method to find where to insert an element in a sorted array
     */
    private int findInsertPosition(int[] nums, int length, int target) {
        // Linear search for insertion position
        for (int i = 0; i < length; i++) {
            if (nums[i] > target) {
                return i; // Found the position where target should go
            }
        }
        return length; // Target is larger than all elements, goes at the end
    }

    /**
     * APPROACH 4: USING BUILT-IN SORTING (NOT RECOMMENDED FOR INTERVIEWS)
     *
     * Time Complexity: O((m + n) log(m + n)) - due to sorting
     * Space Complexity: O(1) or O(log(m + n)) - depending on sort implementation
     *
     * This approach is included for completeness but isn't what interviewers want.
     * It shows understanding of the problem but doesn't leverage the sorted nature.
     */
    public void mergeUsingSorting(int[] nums1, int m, int[] nums2, int n) {
        // Copy nums2 elements into the zero positions of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }

        // Sort the entire array
        Arrays.sort(nums1);

        // Note: This works but doesn't leverage the fact that both input arrays
        // are already sorted, which is a waste of that valuable information!
    }

    /**
     * Helper method to print arrays for testing
     */
    private void printArray(int[] arr, String label) {
        System.out.println(label + ": " + Arrays.toString(arr));
    }

    /**
     * Helper method to create a copy of array for testing multiple approaches
     */
    private int[] copyArray(int[] original) {
        return Arrays.copyOf(original, original.length);
    }

    public static void main(String[] args) {
        MergeSortedArray solution = new MergeSortedArray();

        System.out.println("🎯 MERGE SORTED ARRAY - MULTIPLE APPROACHES DEMONSTRATION");
        System.out.println("=" .repeat(60));

        // Test Case 1: Standard case from the problem
        System.out.println("\n📝 TEST CASE 1: Standard merge");
        int[] original1 = {1, 2, 3, 0, 0, 0};
        int[] nums2_1 = {2, 5, 6};
        int m1 = 3, n1 = 3;

        solution.printArray(original1, "Original nums1");
        solution.printArray(nums2_1, "Original nums2");

        // Test optimal approach
        int[] test1 = solution.copyArray(original1);
        solution.mergeOptimal(test1, m1, nums2_1, n1);
        solution.printArray(test1, "✅ Optimal Result ");
        System.out.println("Expected: [1, 2, 2, 3, 5, 6]");

        // Test extra space approach
        int[] test2 = solution.copyArray(original1);
        solution.mergeWithExtraSpace(test2, m1, nums2_1, n1);
        solution.printArray(test2, "📦 Extra Space   ");

        // Test Case 2: Edge case - nums1 elements are all larger
        System.out.println("\n📝 TEST CASE 2: nums1 elements larger than nums2");
        int[] original2 = {4, 5, 6, 0, 0, 0};
        int[] nums2_2 = {1, 2, 3};
        int m2 = 3, n2 = 3;

        solution.printArray(original2, "Original nums1");
        solution.printArray(nums2_2, "Original nums2");

        int[] test3 = solution.copyArray(original2);
        solution.mergeOptimal(test3, m2, nums2_2, n2);
        solution.printArray(test3, "✅ Optimal Result ");
        System.out.println("Expected: [1, 2, 3, 4, 5, 6]");

        // Test Case 3: Edge case - empty nums2
        System.out.println("\n📝 TEST CASE 3: Empty nums2 (nothing to merge)");
        int[] original3 = {1, 2, 3};
        int[] nums2_3 = {};
        int m3 = 3, n3 = 0;

        solution.printArray(original3, "Original nums1");
        System.out.println("Original nums2: []");

        int[] test4 = solution.copyArray(original3);
        solution.mergeOptimal(test4, m3, nums2_3, n3);
        solution.printArray(test4, "✅ Optimal Result ");
        System.out.println("Expected: [1, 2, 3]");

        // Test Case 4: Edge case - empty nums1 (m = 0)
        System.out.println("\n📝 TEST CASE 4: Empty nums1 (only nums2 elements)");
        int[] original4 = {0, 0, 0};
        int[] nums2_4 = {1, 2, 3};
        int m4 = 0, n4 = 3;

        solution.printArray(original4, "Original nums1");
        solution.printArray(nums2_4, "Original nums2");

        int[] test5 = solution.copyArray(original4);
        solution.mergeOptimal(test5, m4, nums2_4, n4);
        solution.printArray(test5, "✅ Optimal Result ");
        System.out.println("Expected: [1, 2, 3]");

        // Test Case 5: Single elements
        System.out.println("\n📝 TEST CASE 5: Single elements");
        int[] original5 = {2, 0};
        int[] nums2_5 = {1};
        int m5 = 1, n5 = 1;

        solution.printArray(original5, "Original nums1");
        solution.printArray(nums2_5, "Original nums2");

        int[] test6 = solution.copyArray(original5);
        solution.mergeOptimal(test6, m5, nums2_5, n5);
        solution.printArray(test6, "✅ Optimal Result ");
        System.out.println("Expected: [1, 2]");

        // Test Case 6: Duplicate elements
        System.out.println("\n📝 TEST CASE 6: Arrays with duplicates");
        int[] original6 = {1, 2, 2, 0, 0, 0};
        int[] nums2_6 = {2, 2, 3};
        int m6 = 3, n6 = 3;

        solution.printArray(original6, "Original nums1");
        solution.printArray(nums2_6, "Original nums2");

        int[] test7 = solution.copyArray(original6);
        solution.mergeOptimal(test7, m6, nums2_6, n6);
        solution.printArray(test7, "✅ Optimal Result ");
        System.out.println("Expected: [1, 2, 2, 2, 2, 3]");

        // Performance comparison section
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("📊 ALGORITHM COMPARISON SUMMARY");
        System.out.println("=" .repeat(60));

        System.out.println("✅ OPTIMAL (Right-to-Left):    Time O(m+n), Space O(1)");
        System.out.println("   - Uses the insight that nums1 has built-in extra space");
        System.out.println("   - Works backward to avoid overwriting needed elements");
        System.out.println("   - This is the EXPECTED solution in interviews");

        System.out.println("\n📦 EXTRA SPACE (Merge Sort):   Time O(m+n), Space O(m+n)");
        System.out.println("   - Classic merge algorithm, easy to understand");
        System.out.println("   - Good to mention first, then optimize to in-place");

        System.out.println("\n⚠️  INSERTION SORT STYLE:      Time O(m*n), Space O(1)");
        System.out.println("   - Poor time complexity due to shifting elements");
        System.out.println("   - Shows why direction matters in algorithm design");

        System.out.println("\n❌ BUILT-IN SORTING:           Time O((m+n)log(m+n)), Space O(1)");
        System.out.println("   - Doesn't leverage pre-sorted arrays");
        System.out.println("   - Not what interviewers are looking for");

        System.out.println("\n" + "=" .repeat(60));
        System.out.println("🎓 KEY LEARNING POINTS");
        System.out.println("=" .repeat(60));
        System.out.println("1. 🔄 Direction matters: Right-to-left avoids overwrites");
        System.out.println("2. 🏠 Use existing space: nums1's zeros provide merge space");
        System.out.println("3. 🎯 Pattern recognition: This reverse merge appears often");
        System.out.println("4. ⚖️  Trade-offs: Space vs time complexity considerations");
        System.out.println("5. 🧪 Edge cases: Empty arrays, single elements, duplicates");

        System.out.println("\n🚀 NEXT STEPS:");
        System.out.println("   • Practice the optimal solution until you can code it quickly");
        System.out.println("   • Try related problems: Move Zeroes, Merge Two Sorted Lists");
        System.out.println("   • Look for this reverse merge pattern in other problems");

        System.out.println("\n🎯 INTERVIEW STRATEGY:");
        System.out.println("   1. Start by explaining the extra space approach (shows understanding)");
        System.out.println("   2. Then say 'Can we optimize for space?' and explain right-to-left");
        System.out.println("   3. Code the optimal solution with clear variable names");
        System.out.println("   4. Walk through an example step by step");
        System.out.println("   5. Discuss edge cases and test your solution");
    }
}