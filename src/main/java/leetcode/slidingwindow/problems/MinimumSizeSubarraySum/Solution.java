package leetcode.slidingwindow.problems.MinimumSizeSubarraySum;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;


class Solution {
    public int minSubArrayLen(int target, int[] nums) {

        int left = 0;
        List<Integer> subArrayLens = new ArrayList<>();
        int subArraySum = 0;

        for (int right = 0; right < nums.length; right++) {
            subArraySum += nums[right];
            if (subArraySum == target)
                subArrayLens.add(right - left + 1);
            while (subArraySum > target) {
                subArraySum -= nums[left];
                left++;
            }
        }

        if (subArrayLens.isEmpty()) return 0;
        return subArrayLens.stream()
                .min(Integer::compareTo)
                .orElseThrow(() -> new NoSuchElementException("List is empty"));

    }


}