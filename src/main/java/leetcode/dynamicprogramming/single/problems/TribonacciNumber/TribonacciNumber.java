package leetcode.dynamicprogramming.single.problems.TribonacciNumber;

import java.util.HashMap;
import java.util.Map;

public class TribonacciNumber {

    private Map<Integer, Integer> memo = new HashMap<>();

    public int tribonacci_01(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = tribonacci_01(n-1) + tribonacci_01(n-2) + tribonacci_01(n-3);
        memo.put(n, result);
        return result;
    }
}
