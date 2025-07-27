package leetcode.arrays.problems.RotateArray;

class RotateArray {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n; // Handle k > n case

        // Approach 1: Extra Array (Your approach, corrected)
        int[] copy = new int[n];

        for (int i = 0; i < n; i++) {
            int newIndex = (i + k) % n; // Correct formula
            copy[newIndex] = nums[i];
        }

        // Copy back to original array
        System.arraycopy(copy, 0, nums, 0, n);
    }

    // Alternative Approach 2: Reverse Method (O(1) space)
    public void rotateOptimal(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        // Reverse entire array
        reverse(nums, 0, n - 1);
        // Reverse first k elements
        reverse(nums, 0, k - 1);
        // Reverse remaining elements
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    // Alternative Approach 3: Cyclic Replacements
    public void rotateCyclic(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int count = 0;

        for (int start = 0; count < n; start++) {
            int current = start;
            int prev = nums[start];

            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}