package leetcode.binarysearch.problems.BinarySearch;

import java.util.Arrays;

/**
 * 704. Binary Search
 *
 * Pattern: Classic Binary Search (Find Exact Match)
 *
 * Problem: Find target in sorted array, return index or -1 if not found.
 *
 * Key Insights:
 * - This is THE fundamental binary search template
 * - Sorted array allows elimination of half the search space each iteration
 * - Template: "find exact match" with left <= right
 *
 * Time: O(log n)
 * Space: O(1)
 */
public class BinarySearch {

    /**
     * Classic binary search implementation - THE fundamental template.
     * This template is the foundation for all binary search problems.
     */
    public int search(int[] nums, int target) {
        int left = 0;                    // Start of search range
        int right = nums.length - 1;     // End of search range

        while (left <= right) {
            // Calculate middle index (avoid overflow)
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;              // Found target at index mid
            } else if (nums[mid] < target) {
                left = mid + 1;          // Target is in right half
            } else {
                right = mid - 1;         // Target is in left half
            }
        }

        return -1;                       // Target not found
    }

    /**
     * Alternative implementation with explicit bounds checking.
     * Demonstrates defensive programming practices.
     */
    public int searchWithBoundsCheck(int[] nums, int target) {
        // Handle edge case: empty array
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    /**
     * Recursive implementation for comparison.
     * Shows the recursive nature of binary search.
     * Note: Uses O(log n) space due to call stack.
     */
    public int searchRecursive(int[] nums, int target) {
        return binarySearchHelper(nums, target, 0, nums.length - 1);
    }

    private int binarySearchHelper(int[] nums, int target, int left, int right) {
        // Base case: search space exhausted
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearchHelper(nums, target, mid + 1, right);
        } else {
            return binarySearchHelper(nums, target, left, mid - 1);
        }
    }

    /**
     * Implementation with detailed step-by-step logging.
     * Educational version to understand the algorithm flow.
     */
    public int searchWithLogging(int[] nums, int target) {
        System.out.println("Searching for " + target + " in " + Arrays.toString(nums));

        int left = 0;
        int right = nums.length - 1;
        int iteration = 0;

        while (left <= right) {
            iteration++;
            int mid = left + (right - left) / 2;

            System.out.printf("Iteration %d: left=%d, right=%d, mid=%d, nums[mid]=%d\n",
                    iteration, left, right, mid, nums[mid]);

            if (nums[mid] == target) {
                System.out.println("Found target at index " + mid);
                return mid;
            } else if (nums[mid] < target) {
                System.out.println("Target is larger, searching right half");
                left = mid + 1;
            } else {
                System.out.println("Target is smaller, searching left half");
                right = mid - 1;
            }
        }

        System.out.println("Target not found");
        return -1;
    }

    /**
     * Linear search implementation for performance comparison.
     * Time: O(n) - much slower than binary search for large arrays.
     */
    public int linearSearch(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Using built-in Java binary search for comparison.
     * Returns different format (insertion point if not found).
     */
    public int searchUsingBuiltIn(int[] nums, int target) {
        int result = Arrays.binarySearch(nums, target);
        return result >= 0 ? result : -1;
    }

    /**
     * Demonstrates why we use left + (right - left) / 2 instead of (left + right) / 2
     */
    public void demonstrateOverflowPrevention() {
        System.out.println("=== Overflow Prevention Demo ===");

        // Simulate large indices that could cause overflow
        int left = Integer.MAX_VALUE - 5;
        int right = Integer.MAX_VALUE - 1;

        System.out.println("left = " + left);
        System.out.println("right = " + right);

        // This would overflow!
        // int badMid = (left + right) / 2;  // Overflow!
        System.out.println("(left + right) would overflow: " + ((long)left + right));

        // This is safe
        int goodMid = left + (right - left) / 2;
        System.out.println("left + (right - left) / 2 = " + goodMid);
    }

    public static void main(String[] args) {
        BinarySearch solution = new BinarySearch();

        System.out.println("=== 704. Binary Search ===\n");

        // Test Case 1: Example 1
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 9;
        int result1 = solution.search(nums1, target1);
        System.out.println("Test 1: nums=" + Arrays.toString(nums1) + ", target=" + target1);
        System.out.println("Expected: 4, Got: " + result1);
        System.out.println("Correct: " + (result1 == 4));
        System.out.println();

        // Test Case 2: Example 2 (target not found)
        int[] nums2 = {-1, 0, 3, 5, 9, 12};
        int target2 = 2;
        int result2 = solution.search(nums2, target2);
        System.out.println("Test 2: nums=" + Arrays.toString(nums2) + ", target=" + target2);
        System.out.println("Expected: -1, Got: " + result2);
        System.out.println("Correct: " + (result2 == -1));
        System.out.println();

        // Test Case 3: Single element (found)
        int[] nums3 = {5};
        int target3 = 5;
        int result3 = solution.search(nums3, target3);
        System.out.println("Test 3: nums=" + Arrays.toString(nums3) + ", target=" + target3);
        System.out.println("Expected: 0, Got: " + result3);
        System.out.println("Correct: " + (result3 == 0));
        System.out.println();

        // Test Case 4: Single element (not found)
        int[] nums4 = {5};
        int target4 = 3;
        int result4 = solution.search(nums4, target4);
        System.out.println("Test 4: nums=" + Arrays.toString(nums4) + ", target=" + target4);
        System.out.println("Expected: -1, Got: " + result4);
        System.out.println("Correct: " + (result4 == -1));
        System.out.println();

        // Test Case 5: Target at beginning
        int[] nums5 = {1, 2, 3, 4, 5};
        int target5 = 1;
        int result5 = solution.search(nums5, target5);
        System.out.println("Test 5: nums=" + Arrays.toString(nums5) + ", target=" + target5);
        System.out.println("Expected: 0, Got: " + result5);
        System.out.println("Correct: " + (result5 == 0));
        System.out.println();

        // Test Case 6: Target at end
        int[] nums6 = {1, 2, 3, 4, 5};
        int target6 = 5;
        int result6 = solution.search(nums6, target6);
        System.out.println("Test 6: nums=" + Arrays.toString(nums6) + ", target=" + target6);
        System.out.println("Expected: 4, Got: " + result6);
        System.out.println("Correct: " + (result6 == 4));
        System.out.println();

        // Demonstration with step-by-step logging
        System.out.println("=== Step-by-step demonstration ===");
        int[] demoNums = {-1, 0, 3, 5, 9, 12};
        solution.searchWithLogging(demoNums, 9);
        System.out.println();

        solution.searchWithLogging(demoNums, 2);
        System.out.println();

        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Even numbers: 0, 2, 4, 6, ...
        }

        int searchTarget = 999998;

        // Binary search
        long startTime = System.nanoTime();
        int binaryResult = solution.search(largeArray, searchTarget);
        long binaryTime = System.nanoTime() - startTime;

        // Linear search
        startTime = System.nanoTime();
        int linearResult = solution.linearSearch(largeArray, searchTarget);
        long linearTime = System.nanoTime() - startTime;

        System.out.println("Array size: " + largeArray.length);
        System.out.println("Target: " + searchTarget);
        System.out.println("Binary search result: " + binaryResult + " (time: " + binaryTime + " ns)");
        System.out.println("Linear search result: " + linearResult + " (time: " + linearTime + " ns)");
        System.out.println("Binary search is " + (linearTime / Math.max(binaryTime, 1)) + "x faster!");
        System.out.println();

        // Overflow prevention demo
        solution.demonstrateOverflowPrevention();
        System.out.println();

        // Template summary
        System.out.println("=== Binary Search Template ===");
        System.out.println("int left = 0, right = nums.length - 1;");
        System.out.println("while (left <= right) {");
        System.out.println("    int mid = left + (right - left) / 2;");
        System.out.println("    if (nums[mid] == target) return mid;");
        System.out.println("    else if (nums[mid] < target) left = mid + 1;");
        System.out.println("    else right = mid - 1;");
        System.out.println("}");
        System.out.println("return -1;");
        System.out.println();

        System.out.println("Key Points:");
        System.out.println("1. Use 'left <= right' for exact match problems");
        System.out.println("2. Always use 'left + (right - left) / 2' to avoid overflow");
        System.out.println("3. Update boundaries with 'mid + 1' or 'mid - 1' to ensure progress");
        System.out.println("4. This template is the foundation for all binary search variants");
    }
}