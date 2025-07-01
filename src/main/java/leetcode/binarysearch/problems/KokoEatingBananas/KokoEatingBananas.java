package leetcode.binarysearch.problems.KokoEatingBananas;

import java.util.Arrays;

/**
 * 875. Koko Eating Bananas
 *
 * Pattern: Binary Search on Answer (Find Minimum Valid)
 *
 * Problem: Find the minimum eating speed such that Koko can finish all bananas within h hours.
 *
 * Key Insights:
 * - This is a "find minimum valid answer" problem
 * - Search space: [1, max(piles)] (possible eating speeds)
 * - Monotonic property: higher speed → less time needed
 * - Validation: can finish all bananas within h hours at given speed?
 *
 * Time: O(n × log(max(piles))) where n = number of piles
 * Space: O(1)
 */
public class KokoEatingBananas {

    /**
     * Main solution using binary search on answer pattern.
     * Template: Find minimum valid answer
     */
    public int minEatingSpeed(int[] piles, int h) {
        // Step 1: Define search space [1, max(piles)]
        int left = 1; // Minimum possible eating speed
        int right = Arrays.stream(piles).max().getAsInt(); // Maximum pile size

        // Step 2: Binary search for minimum valid eating speed
        while (left < right) {
            int mid = left + (right - left) / 2; // Avoid overflow

            // Step 3: Check if this eating speed allows finishing in time
            if (canFinishInTime(piles, mid, h)) {
                right = mid; // mid works, but try to find smaller speed
            } else {
                left = mid + 1; // mid doesn't work, need higher speed
            }
        }

        // When left == right, we found the minimum valid speed
        return left;
    }

    /**
     * Validation function: Can Koko finish all bananas with given eating speed within h hours?
     *
     * @param piles array of banana piles
     * @param speed bananas per hour eating speed
     * @param h maximum hours allowed
     * @return true if possible to finish within h hours
     */
    private boolean canFinishInTime(int[] piles, int speed, int h) {
        long totalHours = 0; // Use long to prevent overflow

        for (int pile : piles) {
            // Calculate hours needed for this pile using ceiling division
            // ceil(pile / speed) = (pile + speed - 1) / speed
            totalHours += (pile + speed - 1) / speed;

            // Early termination: if already exceeded time limit, return false
            if (totalHours > h) {
                return false;
            }
        }

        return totalHours <= h;
    }

    /**
     * Alternative implementation with explicit ceiling division method
     */
    public int minEatingSpeedAlternative(int[] piles, int h) {
        int left = 1;
        int right = getMaxPile(piles);

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canFinishWithSpeed(piles, mid, h)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * Helper method to find maximum pile size
     */
    private int getMaxPile(int[] piles) {
        int max = piles[0];
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        return max;
    }

    /**
     * Alternative validation function with explicit ceiling calculation
     */
    private boolean canFinishWithSpeed(int[] piles, int speed, int h) {
        long totalHours = 0;

        for (int pile : piles) {
            totalHours += ceilDivide(pile, speed);
            if (totalHours > h) return false;
        }

        return true;
    }

    /**
     * Utility method for ceiling division without floating point
     */
    private int ceilDivide(int dividend, int divisor) {
        return (dividend + divisor - 1) / divisor;
    }

    /**
     * Brute force solution for comparison (inefficient - for educational purposes only)
     * Time: O(max(piles) × n) - too slow for large inputs
     */
    public int minEatingSpeedBruteForce(int[] piles, int h) {
        int maxPile = Arrays.stream(piles).max().getAsInt();

        // Try every speed from 1 to maxPile
        for (int speed = 1; speed <= maxPile; speed++) {
            if (canFinishInTime(piles, speed, h)) {
                return speed; // First speed that works is the minimum
            }
        }

        return maxPile; // Should never reach here given constraints
    }

    /**
     * Optimized bounds version - slightly better initial bounds
     */
    public int minEatingSpeedOptimized(int[] piles, int h) {
        // Calculate total bananas
        long totalBananas = 0;
        int maxPile = 0;
        for (int pile : piles) {
            totalBananas += pile;
            maxPile = Math.max(maxPile, pile);
        }

        // Better lower bound: average speed needed
        int left = (int) Math.max(1, (totalBananas + h - 1) / h);
        int right = maxPile;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canFinishInTime(piles, mid, h)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        KokoEatingBananas solution = new KokoEatingBananas();

        // Test Case 1: Example 1
        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;
        int result1 = solution.minEatingSpeed(piles1, h1);
        System.out.println("Test 1: piles=" + Arrays.toString(piles1) + ", h=" + h1);
        System.out.println("Expected: 4, Got: " + result1);
        System.out.println("Validation: " + solution.canFinishInTime(piles1, result1, h1));
        System.out.println();

        // Test Case 2: Example 2
        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;
        int result2 = solution.minEatingSpeed(piles2, h2);
        System.out.println("Test 2: piles=" + Arrays.toString(piles2) + ", h=" + h2);
        System.out.println("Expected: 30, Got: " + result2);
        System.out.println("Validation: " + solution.canFinishInTime(piles2, result2, h2));
        System.out.println();

        // Test Case 3: Example 3
        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;
        int result3 = solution.minEatingSpeed(piles3, h3);
        System.out.println("Test 3: piles=" + Arrays.toString(piles3) + ", h=" + h3);
        System.out.println("Expected: 23, Got: " + result3);
        System.out.println("Validation: " + solution.canFinishInTime(piles3, result3, h3));
        System.out.println();

        // Test Case 4: Edge case - single pile
        int[] piles4 = {1000000000};
        int h4 = 2;
        int result4 = solution.minEatingSpeed(piles4, h4);
        System.out.println("Test 4: piles=" + Arrays.toString(piles4) + ", h=" + h4);
        System.out.println("Expected: 500000000, Got: " + result4);
        System.out.println("Validation: " + solution.canFinishInTime(piles4, result4, h4));
        System.out.println();

        // Test Case 5: Edge case - minimum speed
        int[] piles5 = {3, 6, 7, 11};
        int h5 = 100;
        int result5 = solution.minEatingSpeed(piles5, h5);
        System.out.println("Test 5: piles=" + Arrays.toString(piles5) + ", h=" + h5);
        System.out.println("Expected: 1, Got: " + result5);
        System.out.println("Validation: " + solution.canFinishInTime(piles5, result5, h5));
        System.out.println();

        // Test Case 6: All same piles
        int[] piles6 = {5, 5, 5, 5};
        int h6 = 8;
        int result6 = solution.minEatingSpeed(piles6, h6);
        System.out.println("Test 6: piles=" + Arrays.toString(piles6) + ", h=" + h6);
        System.out.println("Expected: 3, Got: " + result6);
        System.out.println("Validation: " + solution.canFinishInTime(piles6, result6, h6));
        System.out.println();

        // Demonstrate ceiling division
        System.out.println("=== Ceiling Division Examples ===");
        KokoEatingBananas demo = new KokoEatingBananas();
        System.out.println("ceil(7/3) = " + demo.ceilDivide(7, 3) + " (expected: 3)");
        System.out.println("ceil(6/3) = " + demo.ceilDivide(6, 3) + " (expected: 2)");
        System.out.println("ceil(5/3) = " + demo.ceilDivide(5, 3) + " (expected: 2)");
        System.out.println();

        // Performance comparison note
        System.out.println("=== Performance Note ===");
        System.out.println("Binary Search: O(n × log(max(piles)))");
        System.out.println("Brute Force: O(max(piles) × n)");
        System.out.println("For max(piles)=10^9: Binary search uses ~30 iterations vs 1 billion!");
    }
}