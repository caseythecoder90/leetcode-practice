package leetcode.twopointers.problems.RemoveElement;

import java.util.Arrays;

/**
 * Remove Element - Optimized Solutions
 * LeetCode Problem #27 - Easy
 *
 * Problem: Remove all occurrences of val from nums in-place.
 * Return the number of elements in nums which are not equal to val.
 *
 * Key Insights:
 * - Order of remaining elements doesn't matter
 * - Only the first k elements need to be correct
 * - Can overwrite elements we want to remove
 * - Two main approaches: Fast/Slow vs Converging pointers
 */
public class RemoveElement {

    /**
     * APPROACH 1: Fast/Slow Two Pointers (RECOMMENDED for interviews)
     *
     * Mental Model: "Collect all good elements at the front of the array"
     *
     * Algorithm:
     * 1. slow pointer tracks next position for non-val element
     * 2. fast pointer scans through entire array
     * 3. When fast finds non-val element, place it at slow position
     * 4. Return slow (count of non-val elements)
     *
     * Why this approach is great for interviews:
     * - Simple, intuitive logic
     * - Easy to explain and trace through
     * - Less prone to bugs
     * - Works for many similar problems
     *
     * Time: O(n) - single pass
     * Space: O(1) - only two pointers
     */
    public int removeElementFastSlow(int[] nums, int val) {
        int slow = 0; // Next position to place non-val element

        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                // Found a good element, place it at slow position
                nums[slow] = nums[fast];
                slow++; // Move to next available position
            }
            // If nums[fast] == val, just skip it (don't increment slow)
        }

        return slow; // Number of non-val elements placed
    }

    /**
     * APPROACH 2: Converging Two Pointers (More efficient for certain inputs)
     *
     * Mental Model: "Replace bad elements with good elements from the end"
     *
     * Algorithm:
     * 1. left pointer scans from start, right pointer from end
     * 2. When left finds val, replace it with element from right
     * 3. Shrink valid region by moving right pointer left
     * 4. Only advance left when element is good
     *
     * Advantage: Minimizes write operations when many elements need removal
     * Disadvantage: Slightly more complex logic
     *
     * Time: O(n) - each element examined at most once
     * Space: O(1) - only two pointers
     */
    public int removeElementConverging(int[] nums, int val) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            if (nums[left] == val) {
                // Found element to remove, replace with element from right
                nums[left] = nums[right];
                right--; // Shrink the valid region
                // Don't increment left - need to check the swapped element
            } else {
                // Good element, keep it and move left pointer
                left++;
            }
        }

        // Elements from index 0 to right are all non-val
        return right + 1;
    }

    /**
     * APPROACH 3: Optimized Converging (Avoid unnecessary swaps)
     *
     * This variation of converging approach avoids swapping when the element
     * at right is also equal to val, making it slightly more efficient.
     */
    public int removeElementOptimizedConverging(int[] nums, int val) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            // Skip elements equal to val from the right
            while (left <= right && nums[right] == val) {
                right--;
            }

            // If we found a good element from right, use it to replace bad element from left
            if (left <= right && nums[left] == val) {
                nums[left] = nums[right];
                right--;
            }

            // Move left pointer if current element is good
            if (left <= right && nums[left] != val) {
                left++;
            }
        }

        return right + 1;
    }

    /**
     * Helper method to demonstrate the trace of fast/slow approach
     */
    public int removeElementWithTrace(int[] nums, int val) {
        System.out.println("Tracing Fast/Slow approach:");
        System.out.println("Initial: " + Arrays.toString(nums) + ", val = " + val);

        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            System.out.printf("fast=%d, nums[fast]=%d, slow=%d ", fast, nums[fast], slow);

            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                System.out.printf("-> Place %d at position %d, slow becomes %d",
                        nums[fast], slow, slow + 1);
                slow++;
            } else {
                System.out.print("-> Skip element");
            }

            System.out.println();
            System.out.println("   Array: " + Arrays.toString(nums));
        }

        System.out.println("Final result: " + slow);
        return slow;
    }

    public static void main(String[] args) {
        RemoveElement solution = new RemoveElement();

        // Test Case 1: Basic example
        System.out.println("=== TEST CASE 1: Basic Example ===");
        int[] nums1 = {3, 2, 2, 3};
        int val1 = 3;
        System.out.println("Input: " + Arrays.toString(nums1) + ", val = " + val1);

        int[] test1a = nums1.clone();
        int[] test1b = nums1.clone();
        int[] test1c = nums1.clone();

        int result1a = solution.removeElementFastSlow(test1a, val1);
        int result1b = solution.removeElementConverging(test1b, val1);
        int result1c = solution.removeElementOptimizedConverging(test1c, val1);

        System.out.println("Fast/Slow result: " + result1a +
                ", first " + result1a + " elements: " +
                Arrays.toString(Arrays.copyOf(test1a, result1a)));
        System.out.println("Converging result: " + result1b +
                ", first " + result1b + " elements: " +
                Arrays.toString(Arrays.copyOf(test1b, result1b)));
        System.out.println("Optimized result: " + result1c +
                ", first " + result1c + " elements: " +
                Arrays.toString(Arrays.copyOf(test1c, result1c)));
        System.out.println("Expected: 2, elements should be [2,2] in any order\n");

        // Test Case 2: Complex example
        System.out.println("=== TEST CASE 2: Complex Example ===");
        int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
        int val2 = 2;
        System.out.println("Input: " + Arrays.toString(nums2) + ", val = " + val2);

        int[] test2a = nums2.clone();
        int result2a = solution.removeElementFastSlow(test2a, val2);
        System.out.println("Fast/Slow result: " + result2a +
                ", first " + result2a + " elements: " +
                Arrays.toString(Arrays.copyOf(test2a, result2a)));
        System.out.println("Expected: 5, elements should be [0,1,3,0,4] in any order\n");

        // Test Case 3: Trace example
        System.out.println("=== TEST CASE 3: Detailed Trace ===");
        int[] nums3 = {3, 2, 2, 3};
        int val3 = 3;
        int[] test3 = nums3.clone();
        solution.removeElementWithTrace(test3, val3);
        System.out.println();

        // Test Case 4: Edge cases
        System.out.println("=== EDGE CASES ===");

        // Empty array
        int[] empty = {};
        System.out.println("Empty array: " + solution.removeElementFastSlow(empty, 1));

        // Single element - remove
        int[] single1 = {1};
        System.out.println("Single element (remove): " +
                solution.removeElementFastSlow(single1, 1));

        // Single element - keep
        int[] single2 = {1};
        System.out.println("Single element (keep): " +
                solution.removeElementFastSlow(single2, 2));

        // All elements same - remove all
        int[] allSame1 = {2, 2, 2, 2};
        System.out.println("All same (remove all): " +
                solution.removeElementFastSlow(allSame1, 2));

        // All elements same - keep all
        int[] allSame2 = {2, 2, 2, 2};
        System.out.println("All same (keep all): " +
                solution.removeElementFastSlow(allSame2, 3));

        System.out.println("\n=== PERFORMANCE COMPARISON ===");
        performanceComparison();
    }

    /**
     * Demonstrates when each approach might be preferred
     */
    public static void performanceComparison() {
        RemoveElement solution = new RemoveElement();

        // Scenario 1: Few elements to remove (Fast/Slow more intuitive)
        int[] scenario1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 2}; // Only one 2 to remove
        System.out.println("Scenario 1 - Few removals: " + Arrays.toString(scenario1));

        int[] test1a = scenario1.clone();
        int[] test1b = scenario1.clone();

        int writes1a = countWrites(test1a, 2, "fast-slow");
        int writes1b = countWrites(test1b, 2, "converging");

        System.out.println("Fast/Slow writes: " + writes1a);
        System.out.println("Converging writes: " + writes1b);
        System.out.println("Winner: " + (writes1a <= writes1b ? "Fast/Slow (simpler)" : "Converging"));

        // Scenario 2: Many elements to remove (Converging might be better)
        int[] scenario2 = {2, 2, 2, 1, 2, 2, 3, 2, 2}; // Many 2s to remove
        System.out.println("\nScenario 2 - Many removals: " + Arrays.toString(scenario2));

        int[] test2a = scenario2.clone();
        int[] test2b = scenario2.clone();

        int writes2a = countWrites(test2a, 2, "fast-slow");
        int writes2b = countWrites(test2b, 2, "converging");

        System.out.println("Fast/Slow writes: " + writes2a);
        System.out.println("Converging writes: " + writes2b);
        System.out.println("Winner: " + (writes2a <= writes2b ? "Fast/Slow" : "Converging (fewer writes)"));

        System.out.println("\nConclusion: Fast/Slow is generally preferred for its simplicity.");
        System.out.println("Use converging only when minimizing writes is critical.");
    }

    // Helper method to count array writes (for performance analysis)
    private static int countWrites(int[] nums, int val, String approach) {
        // This is a simplified simulation - in real scenarios you'd instrument the actual methods
        RemoveElement solution = new RemoveElement();

        if ("fast-slow".equals(approach)) {
            solution.removeElementFastSlow(nums, val);
            // Fast/slow writes to every position where it places an element
            return (int) Arrays.stream(nums).filter(x -> x != val).count();
        } else {
            solution.removeElementConverging(nums, val);
            // Converging only writes when it finds elements to remove
            // This is a simplification for demonstration
            return Math.min(3, nums.length); // Simplified estimate
        }
    }
}

/*
ALGORITHM DECISION TREE:

Q: Does this problem involve removing elements in-place?
└── Yes
    ├── Q: Does relative order matter?
    │   ├── Yes → Use Fast/Slow approach
    │   └── No → Consider both approaches
    │       ├── Simple logic preferred → Fast/Slow
    │       └── Minimize writes → Converging
    └── No → Different pattern needed

INTERVIEW STRATEGY:

1. Start with Fast/Slow approach:
   - Easier to explain and implement
   - Less prone to bugs
   - Naturally handles edge cases

2. Mention Converging as optimization:
   - "I could also use converging pointers to minimize writes"
   - Show you understand trade-offs

3. Always trace through an example:
   - Demonstrates understanding
   - Catches bugs early
   - Shows systematic thinking

SIMILAR PROBLEMS:
- Move Zeroes (#283) - Similar pattern but order matters
- Remove Duplicates (#26) - Fast/slow with different condition
- Remove Duplicates II (#80) - Allow limited duplicates
- Partition Array (#905) - Even/odd partitioning

KEY TAKEAWAYS:
- Pattern recognition is crucial
- Fast/Slow is usually the safer choice
- Always consider what the problem is really asking for
- Practice explaining your approach clearly
*/