package leetcode.backtracking.problems.CombinationSumThree;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum3 {

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), k, n, 1);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> current,
                           int k, int remainingSum, int start) {

        // Base cases
        if (current.size() == k ) {
            if (remainingSum == 0) {
                result.add(new ArrayList<>(current)); // Important: create new list!
            }
            return;
        }

        // Pruning: if we can't possibly reach the target
        if (remainingSum < 0 || current.size() > k) {
            return;
        }

        // Try each number from start to 9
        for (int i = start; i <= 9; i++) {
            // Skip if this number is too big for remaining sum
            if (i > remainingSum) break;

            current.add(i);
            backtrack(result, current, k, remainingSum - i, i + 1);
            current.remove(current.size() - 1); // backtrack
        }
    }

    public static void main(String[] args) {
        CombinationSum3 solution = new CombinationSum3();

        // Test case: k=3, n=7 should return [[1,2,4]]
        System.out.println("k=3, n=7: " + solution.combinationSum3(3, 7));

        // Test case: k=3, n=9 should return [[1,2,6], [1,3,5], [2,3,4]]
        System.out.println("k=3, n=9: " + solution.combinationSum3(3, 9));

        // Test case: k=4, n=1 should return [] (impossible)
        System.out.println("k=4, n=1: " + solution.combinationSum3(4, 1));
    }
}