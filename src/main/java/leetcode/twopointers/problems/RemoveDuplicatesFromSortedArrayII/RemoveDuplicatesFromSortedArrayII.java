package leetcode.twopointers.problems.RemoveDuplicatesFromSortedArrayII;

/**
 * LeetCode 80: Remove Duplicates from Sorted Array II
 * Difficulty: Medium
 *
 * Problem: Given an integer array nums sorted in non-decreasing order, remove some duplicates
 * in-place such that each unique element appears at most twice. The relative order of the
 * elements should be kept the same.
 *
 * Pattern: Fast/Slow Pointers for In-Place Array Modification
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(1) - only using two pointers
 */
public class RemoveDuplicatesFromSortedArrayII {

    /**
     * Optimal Solution using Two Pointers (Fast/Slow Pattern)
     *
     * Key Insight: Instead of counting duplicates explicitly, we use a clever comparison:
     * nums[fast] is valid if nums[fast] != nums[slow-2]
     * This ensures we never have more than 2 duplicates.
     *
     * @param nums sorted integer array
     * @return length of array after removing excess duplicates
     */
    public int removeDuplicates(int[] nums) {
        // Base case: arrays with <= 2 elements automatically satisfy constraint
        if (nums.length <= 2) return nums.length;

        // Start slow at index 2 since first 2 elements are always valid
        int slow = 2;

        for (int fast = 2; fast < nums.length; fast++) {
            // nums[fast] is valid if it's different from nums[slow-2]
            // This ensures at most 2 duplicates
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    /**
     * More Readable Version with Descriptive Variable Names
     */
    public int removeDuplicatesReadable(int[] nums) {
        if (nums.length <= 2) return nums.length;

        int writeIndex = 2; // Where to write next valid element

        for (int readIndex = 2; readIndex < nums.length; readIndex++) {
            // Can we include nums[readIndex]?
            // Yes, if it's different from the element 2 positions back in our result
            if (nums[readIndex] != nums[writeIndex - 2]) {
                nums[writeIndex] = nums[readIndex];
                writeIndex++;
            }
        }

        return writeIndex;
    }

    /**
     * Generalized Solution for At Most K Duplicates
     *
     * @param nums sorted integer array
     * @param k maximum allowed duplicates
     * @return length of array after removing excess duplicates
     */
    public int removeDuplicatesGeneralized(int[] nums, int k) {
        if (nums.length <= k) return nums.length;

        int slow = k;

        for (int fast = k; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow - k]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    /**
     * Alternative Solution using Frequency Counting (Less Optimal)
     * Time: O(n), Space: O(1) but uses more variables
     *
     * This approach explicitly counts duplicates but is less elegant
     */
    public int removeDuplicatesWithCounting(int[] nums) {
        if (nums.length <= 2) return nums.length;

        int slow = 1;
        int count = 1; // Count of current element

        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] == nums[fast - 1]) {
                count++;
            } else {
                count = 1; // Reset count for new element
            }

            // Only include if count <= 2
            if (count <= 2) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }

    /**
     * Test method to verify solutions
     */
    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArrayII solution = new RemoveDuplicatesFromSortedArrayII();

        // Test Case 1: [1,1,1,2,2,3] -> [1,1,2,2,3]
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k1 = solution.removeDuplicates(nums1);
        System.out.println("Test 1 - Length: " + k1);
        System.out.print("Array: ");
        for (int i = 0; i < k1; i++) {
            System.out.print(nums1[i] + " ");
        }
        System.out.println();

        // Test Case 2: [0,0,1,1,1,1,2,3,3] -> [0,0,1,1,2,3,3]
        int[] nums2 = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        int k2 = solution.removeDuplicates(nums2);
        System.out.println("Test 2 - Length: " + k2);
        System.out.print("Array: ");
        for (int i = 0; i < k2; i++) {
            System.out.print(nums2[i] + " ");
        }
        System.out.println();

        // Test Case 3: Edge case - array with <= 2 elements
        int[] nums3 = {1, 2};
        int k3 = solution.removeDuplicates(nums3);
        System.out.println("Test 3 - Length: " + k3);
        System.out.print("Array: ");
        for (int i = 0; i < k3; i++) {
            System.out.print(nums3[i] + " ");
        }
        System.out.println();
    }
}

/*
ALGORITHM EXPLANATION:

1. WHY IT WORKS:
   - We maintain a 'slow' pointer that tracks where to place the next valid element
   - The key insight: nums[fast] != nums[slow-2] ensures at most 2 duplicates
   - If nums[fast] == nums[slow-2], adding nums[fast] would create 3 duplicates

2. EXAMPLE WALKTHROUGH [1,1,1,2,2,3]:
   fast=2: nums[2]=1, nums[0]=1 → equal, skip
   fast=3: nums[3]=2, nums[1]=1 → different, place at slow=2
   fast=4: nums[4]=2, nums[2]=2 → equal, skip
   fast=5: nums[5]=3, nums[3]=2 → different, place at slow=3
   Result: [1,1,2,3], length=4

3. TIME/SPACE COMPLEXITY:
   - Time: O(n) - single pass through array
   - Space: O(1) - only using two pointers

4. PATTERN GENERALIZATION:
   - For "at most k duplicates": if (nums[fast] != nums[slow-k])
   - This pattern works for many in-place array modification problems
*/