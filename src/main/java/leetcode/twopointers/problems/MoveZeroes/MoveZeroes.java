package leetcode.twopointers.problems.MoveZeroes;

/**
 * LeetCode 283: Move Zeroes
 * Difficulty: Easy
 *
 * Problem: Given an integer array nums, move all 0's to the end of it while maintaining
 * the relative order of the non-zero elements.
 *
 * Note: You must do this in-place without making a copy of the array.
 *
 * Pattern: Fast/Slow Pointers for In-Place Array Modification
 * Time Complexity: O(n) - single pass through array
 * Space Complexity: O(1) - only using two pointers
 */
public class MoveZeroes {

    /**
     * Optimal Solution: Two-Pass Approach (Cleaner Logic)
     * 
     * Algorithm:
     * 1. First pass: Move all non-zero elements to the front
     * 2. Second pass: Fill remaining positions with zeros
     * 
     * @param nums integer array to modify in-place
     */
    public void moveZeroes(int[] nums) {
        int writeIndex = 0;
        
        // First pass: move all non-zero elements to front
        for (int readIndex = 0; readIndex < nums.length; readIndex++) {
            if (nums[readIndex] != 0) {
                nums[writeIndex] = nums[readIndex];
                writeIndex++;
            }
        }
        
        // Second pass: fill remaining positions with zeros
        while (writeIndex < nums.length) {
            nums[writeIndex] = 0;
            writeIndex++;
        }
    }

    /**
     * Alternative Solution: Single-Pass with Swap
     * 
     * This approach swaps elements but performs more operations when zeros are encountered.
     * 
     * @param nums integer array to modify in-place
     */
    public void moveZeroesSwap(int[] nums) {
        int slow = 0;
        
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // Swap elements at slow and fast positions
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
    }

    /**
     * Less Optimal Solution: Bubble Sort Style
     * 
     * Time Complexity: O(n²) in worst case
     * Only shown for educational purposes - demonstrates why two pointers is better
     * 
     * @param nums integer array to modify in-place
     */
    public void moveZeroesBubble(int[] nums) {
        int n = nums.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (nums[j] == 0 && nums[j + 1] != 0) {
                    // Swap zero with next non-zero element
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Helper method to print array for testing
     */
    private void printArray(int[] nums) {
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Test method to verify solutions
     */
    public static void main(String[] args) {
        MoveZeroes solution = new MoveZeroes();

        // Test Case 1: [0,1,0,3,12] -> [1,3,12,0,0]
        int[] nums1 = {0, 1, 0, 3, 12};
        System.out.println("Test 1:");
        System.out.print("Input:  ");
        solution.printArray(nums1);
        solution.moveZeroes(nums1);
        System.out.print("Output: ");
        solution.printArray(nums1);
        System.out.println();

        // Test Case 2: [0] -> [0]
        int[] nums2 = {0};
        System.out.println("Test 2:");
        System.out.print("Input:  ");
        solution.printArray(nums2);
        solution.moveZeroes(nums2);
        System.out.print("Output: ");
        solution.printArray(nums2);
        System.out.println();

        // Test Case 3: [1,2,3,4,5] -> [1,2,3,4,5] (no zeros)
        int[] nums3 = {1, 2, 3, 4, 5};
        System.out.println("Test 3:");
        System.out.print("Input:  ");
        solution.printArray(nums3);
        solution.moveZeroes(nums3);
        System.out.print("Output: ");
        solution.printArray(nums3);
        System.out.println();

        // Test Case 4: [0,0,0,1,2] -> [1,2,0,0,0]
        int[] nums4 = {0, 0, 0, 1, 2};
        System.out.println("Test 4:");
        System.out.print("Input:  ");
        solution.printArray(nums4);
        solution.moveZeroes(nums4);
        System.out.print("Output: ");
        solution.printArray(nums4);
        System.out.println();

        // Test swap approach
        System.out.println("Testing swap approach:");
        int[] nums5 = {0, 1, 0, 3, 12};
        System.out.print("Input:  ");
        solution.printArray(nums5);
        solution.moveZeroesSwap(nums5);
        System.out.print("Output: ");
        solution.printArray(nums5);
    }
}

/*
DETAILED ALGORITHM WALKTHROUGH:

Example: nums = [0,1,0,3,12]

APPROACH 1: Two-Pass Solution
----------------------------
Initial: [0, 1, 0, 3, 12], writeIndex = 0

First Pass - Move non-zeros to front:
  readIndex=0: nums[0]=0 (zero, skip)
  readIndex=1: nums[1]=1 (non-zero) → nums[0]=1, writeIndex=1
  readIndex=2: nums[2]=0 (zero, skip)  
  readIndex=3: nums[3]=3 (non-zero) → nums[1]=3, writeIndex=2
  readIndex=4: nums[4]=12 (non-zero) → nums[2]=12, writeIndex=3
  
  After first pass: [1, 3, 12, 3, 12], writeIndex = 3

Second Pass - Fill with zeros:
  nums[3] = 0
  nums[4] = 0
  
  Final result: [1, 3, 12, 0, 0]

APPROACH 2: Single-Pass with Swap
----------------------------------
Initial: [0, 1, 0, 3, 12], slow = 0

  fast=0: nums[0]=0 (zero, skip), slow stays 0
  fast=1: nums[1]=1 (non-zero) → swap nums[0] and nums[1], slow=1
          Result: [1, 0, 0, 3, 12]
  fast=2: nums[2]=0 (zero, skip), slow stays 1  
  fast=3: nums[3]=3 (non-zero) → swap nums[1] and nums[3], slow=2
          Result: [1, 3, 0, 0, 12]
  fast=4: nums[4]=12 (non-zero) → swap nums[2] and nums[4], slow=3
          Result: [1, 3, 12, 0, 0]

KEY INSIGHTS:
- Both approaches use the fast/slow pointer pattern
- Two-pass is cleaner and easier to understand
- Single-pass with swap performs more operations but completes in one loop
- The slow pointer always tracks "where to place the next non-zero element"
- The pattern maintains relative order of non-zero elements naturally

TIME COMPLEXITY: O(n) for both approaches
SPACE COMPLEXITY: O(1) - only using constant extra space
*/