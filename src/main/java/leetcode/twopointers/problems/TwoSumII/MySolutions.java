public class MySolutions {

    /**
     * User's original solution - clean and efficient!
     * This is the optimal approach for this problem.
     */
    public int[] twoSum(int[] numbers, int target) {
        int i = 0;
        int j = numbers.length - 1;

        while (i < j) {

            int currentSum = numbers[i] + numbers[j];

            if (currentSum == target)
                return new int[] {i + 1, j + 1};

            else if (currentSum < target) i++;

            else j--;

        }

        return new int[] {-1, -1};
    }

    /**
     * Detailed implementation with extensive comments and bounds checking
     * Shows the thought process step by step
     */
    public int[] twoSumDetailed(int[] numbers, int target) {
        if (numbers == null || numbers.length < 2) {
            throw new IllegalArgumentException("Input array must have at least 2 elements");
        }

        // Step 1: Initialize two pointers at opposite ends
        int leftPointer = 0;
        int rightPointer = numbers.length - 1;

        // Step 2: While pointers haven't crossed each other
        while (leftPointer < rightPointer) {

            // Step 3: Calculate current sum
            int currentSum = numbers[leftPointer] + numbers[rightPointer];

            if (currentSum == target) {
                // SUCCESS: Found the target sum!
                // Return 1-indexed positions as required
                return new int[]{leftPointer + 1, rightPointer + 1};

            } else if (currentSum < target) {
                // Sum is too small, need to increase it
                // Move left pointer right to get larger number
                leftPointer++;

            } else {
                // Sum is too large, need to decrease it
                // Move right pointer left to get smaller number
                rightPointer--;
            }
        }

        // This should never happen given problem constraints
        return new int[]{-1, -1};
    }

    /**
     * Solution with additional validation and error handling
     * Useful for real-world implementations where input may be invalid
     */
    public int[] twoSumWithValidation(int[] numbers, int target) {
        // Validate input array
        if (numbers == null) return new int[]{-1, -1};
        if (numbers.length < 2) return new int[]{-1, -1};

        // Validate that array is actually sorted (problem assumes it is)
        // This is optional but good practice in real-world code
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i + 1]) {
                throw new IllegalArgumentException("Array must be sorted in non-decreasing order");
            }
        }

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            long currentSum = (long)numbers[left] + (long)numbers[right]; // Prevent overflow

            if (currentSum == target) {
                return new int[]{left + 1, right + 1};
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{-1, -1};
    }

    /**
     * Alternative solution using brick-wall search thinking
     * Shows thinking from finding pairs that sum to target
     */
    public int[] twoSumBrickWall(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            // Calculate complement needed for numbers[left]
            int needed = target - numbers[left];
            // Check if numbers[right] is our needed complement
            if (numbers[right] == needed) {
                return new int[]{left + 1, right + 1};
            }

            // If numbers[right] > needed, search farther left
            if (numbers[right] > needed) {
                right--;
            }
            // If numbers[right] < needed, need larger complement
            else {
                left++;
            }
        }

        return new int[]{-1, -1};
    }

    /**
     * Educational solution showing each step in detail
     * Useful for understanding the algorithm flow
     */
    public int[] twoSumEducational(int[] numbers, int target) {
        System.out.println("=== Two Sum II Solution ===");
        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Target: " + target);

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int currentSum = numbers[left] + numbers[right];
            System.out.printf("Checking: numbers[%d]=%d + numbers[%d]=%d = %d [%s]\n",
                            left, numbers[left], right, numbers[right],
                            currentSum, currentSum == target ? "TARGET FOUND!" :
                                         currentSum < target ? "too small" : "too large");

            if (currentSum == target) {
                System.out.println("SUCCESS! Returning indices: [" + (left+1) + ", " + (right+1) + "]");
                return new int[]{left + 1, right + 1};
            } else if (currentSum < target) {
                left++;
                System.out.println("Sum too small, moving left pointer RIGHT");
            } else {
                right--;
                System.out.println("Sum too large, moving right pointer LEFT");
            }
        }

        System.out.println("WARNING: No solution found (should not happen per problem constraints)");
        return new int[]{-1, -1};
    }

    /**
     * Solution that optionally tracks number of iterations
     * Useful for performance analysis
     */
    public int[] twoSumWithMetrics(int[] numbers, int target) {
        int iterations = 0;
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            iterations++;
            int currentSum = numbers[left] + numbers[right];

            if (currentSum == target) {
                System.out.println("Found solution in " + iterations + " iterations");
                return new int[]{left + 1, right + 1};
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{-1, -1};
    }
}
