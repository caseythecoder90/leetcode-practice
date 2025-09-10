public class TwoSumII {

    /**
     * Finds two numbers in a sorted array that add up to a target sum using two pointers.
     *
     * Uses converging two-pointers approach: start from both ends and move based on sum comparison.
     *
     * Time Complexity: O(n) where n is the length of numbers array
     * Space Complexity: O(1) extra space (as required by constraints)
     *
     * @param numbers the sorted array of numbers (1-indexed in problem, but 0-indexed in solution)
     * @param target the target sum to find
     * @return array containing the 1-based indices [index1, index2] where numbers[index1-1] + numbers[index2-1] = target
     */
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length < 2) {
            return new int[]{-1, -1};
        }

        // Initialize two pointers: one at start, one at end
        int left = 0;
        int right = numbers.length - 1;

        // Move pointers until they meet
        while (left < right) {
            int currentSum = numbers[left] + numbers[right];

            if (currentSum == target) {
                // Found the solution! Return 1-based indices
                return new int[]{left + 1, right + 1};
            } else if (currentSum < target) {
                // Sum is too small, need larger numbers → move left pointer right
                left++;
            } else {
                // Sum is too large, need smaller numbers → move right pointer left
                right--;
            }
        }

        // This point should never be reached due to problem constraints
        // ("The tests are generated such that there is exactly one solution")
        return new int[]{-1, -1};
    }
}
