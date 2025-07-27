package leetcode.arrays.problems.ProductOfArrayExceptSelf;

/**
 * LeetCode 238: Product of Array Except Self
 * Difficulty: Medium
 *
 * Problem: Given an integer array nums, return an array answer such that answer[i] 
 * is equal to the product of all the elements of nums except nums[i].
 *
 * Constraints:
 * - Must run in O(n) time
 * - Cannot use division operation
 * - Product guaranteed to fit in 32-bit integer
 *
 * Pattern: Prefix/Suffix Products, Two-Pass Algorithm
 * Time Complexity: O(n) - two passes through array
 * Space Complexity: O(1) extra space (not counting output array)
 */
public class ProductOfArrayExceptSelf {

    /**
     * Optimal Solution: Left and Right Products using Output Array
     * 
     * Key Insight: result[i] = (product of all elements left of i) × (product of all elements right of i)
     * 
     * Algorithm:
     * 1. First pass: Store left products in result array
     * 2. Second pass: Calculate right products on-the-fly and multiply with left products
     * 
     * @param nums input array
     * @return array where each element is product of all other elements
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // First pass: calculate left products
        result[0] = 1; // No elements to the left of index 0
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        
        // Second pass: calculate right products and multiply with left products
        int rightProduct = 1; // No elements to the right of last index
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightProduct;
            rightProduct = rightProduct * nums[i];
        }
        
        return result;
    }

    /**
     * Alternative Solution: Explicit Left and Right Arrays (Educational)
     * 
     * This approach uses separate arrays for left and right products.
     * Less space efficient but easier to understand.
     * 
     * @param nums input array
     * @return array where each element is product of all other elements
     */
    public int[] productExceptSelfExplicit(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] result = new int[n];
        
        // Calculate left products
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }
        
        // Calculate right products
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        
        // Combine left and right products
        for (int i = 0; i < n; i++) {
            result[i] = left[i] * right[i];
        }
        
        return result;
    }

    /**
     * Brute Force Solution (Educational - Not Optimal)
     * 
     * For each position, calculate product of all other elements.
     * Time: O(n²), Space: O(1)
     * 
     * @param nums input array
     * @return array where each element is product of all other elements
     */
    public int[] productExceptSelfBruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }
            result[i] = product;
        }
        
        return result;
    }

    /**
     * Division-Based Solution (Not Allowed by Problem Constraints)
     * 
     * This approach would work but violates the "no division" constraint.
     * Also has issues with zeros in the array.
     * 
     * @param nums input array
     * @return array where each element is product of all other elements
     */
    public int[] productExceptSelfDivision(int[] nums) {
        // This approach is shown for educational purposes only
        // It violates the problem constraints (no division allowed)
        
        int n = nums.length;
        int[] result = new int[n];
        
        // Calculate total product
        int totalProduct = 1;
        int zeroCount = 0;
        for (int num : nums) {
            if (num == 0) {
                zeroCount++;
            } else {
                totalProduct *= num;
            }
        }
        
        // Handle different cases
        for (int i = 0; i < n; i++) {
            if (zeroCount > 1) {
                result[i] = 0; // More than one zero means all results are 0
            } else if (zeroCount == 1) {
                result[i] = (nums[i] == 0) ? totalProduct : 0;
            } else {
                result[i] = totalProduct / nums[i]; // This uses division!
            }
        }
        
        return result;
    }

    /**
     * Helper method to print arrays for testing
     */
    private void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Test method with comprehensive test cases
     */
    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();

        // Test Case 1: [1,2,3,4] -> [24,12,8,6]
        int[] nums1 = {1, 2, 3, 4};
        System.out.println("Test 1:");
        System.out.print("Input:  ");
        solution.printArray(nums1);
        System.out.print("Output: ");
        solution.printArray(solution.productExceptSelf(nums1));
        System.out.println("Expected: [24, 12, 8, 6]");
        System.out.println();

        // Test Case 2: [-1,1,0,-3,3] -> [0,0,9,0,0]
        int[] nums2 = {-1, 1, 0, -3, 3};
        System.out.println("Test 2:");
        System.out.print("Input:  ");
        solution.printArray(nums2);
        System.out.print("Output: ");
        solution.printArray(solution.productExceptSelf(nums2));
        System.out.println("Expected: [0, 0, 9, 0, 0]");
        System.out.println();

        // Test Case 3: Two elements [2,3] -> [3,2]
        int[] nums3 = {2, 3};
        System.out.println("Test 3:");
        System.out.print("Input:  ");
        solution.printArray(nums3);
        System.out.print("Output: ");
        solution.printArray(solution.productExceptSelf(nums3));
        System.out.println("Expected: [3, 2]");
        System.out.println();

        // Test Case 4: Negative numbers [-1,-2,-3] -> [6,3,2]
        int[] nums4 = {-1, -2, -3};
        System.out.println("Test 4:");
        System.out.print("Input:  ");
        solution.printArray(nums4);
        System.out.print("Output: ");
        solution.printArray(solution.productExceptSelf(nums4));
        System.out.println("Expected: [6, 3, 2]");
        System.out.println();

        // Test explicit approach
        System.out.println("Testing explicit left/right arrays approach:");
        System.out.print("Output: ");
        solution.printArray(solution.productExceptSelfExplicit(nums1));
    }
}

/*
DETAILED ALGORITHM WALKTHROUGH:

Example: nums = [1, 2, 3, 4]

STEP 1: Calculate Left Products
result[0] = 1 (no elements to the left)
result[1] = result[0] * nums[0] = 1 * 1 = 1
result[2] = result[1] * nums[1] = 1 * 2 = 2  
result[3] = result[2] * nums[2] = 2 * 3 = 6

After first pass: result = [1, 1, 2, 6]

STEP 2: Calculate Right Products and Final Result
rightProduct = 1

i = 3: result[3] = result[3] * rightProduct = 6 * 1 = 6
       rightProduct = rightProduct * nums[3] = 1 * 4 = 4

i = 2: result[2] = result[2] * rightProduct = 2 * 4 = 8
       rightProduct = rightProduct * nums[2] = 4 * 3 = 12

i = 1: result[1] = result[1] * rightProduct = 1 * 12 = 12
       rightProduct = rightProduct * nums[1] = 12 * 2 = 24

i = 0: result[0] = result[0] * rightProduct = 1 * 24 = 24
       rightProduct = rightProduct * nums[0] = 24 * 1 = 24

Final result: [24, 12, 8, 6]

VERIFICATION:
result[0] = 2 * 3 * 4 = 24 ✓
result[1] = 1 * 3 * 4 = 12 ✓  
result[2] = 1 * 2 * 4 = 8 ✓
result[3] = 1 * 2 * 3 = 6 ✓

KEY INSIGHTS:
1. Left products: result[i] = product of nums[0] to nums[i-1]
2. Right products: calculated on-the-fly from right to left
3. Final result: left[i] * right[i] = product of all except nums[i]
4. Space optimization: use output array to store left products
5. Time complexity: O(n) with two passes
6. No division needed: handles zeros naturally

COMMON DEBUGGING:
- Watch for index bounds (i-1, i+1)
- Initialize boundary conditions correctly (1 for products)
- Variable naming: avoid mixing up i and n in loop updates
*/