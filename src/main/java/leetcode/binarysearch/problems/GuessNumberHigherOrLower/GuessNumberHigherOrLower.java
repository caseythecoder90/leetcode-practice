package leetcode.binarysearch.problems.GuessNumberHigherOrLower;

/**
 * 374. Guess Number Higher or Lower
 *
 * Pattern: Classic Binary Search (Find Exact Match)
 *
 * Problem: Find the picked number using a guess API that returns:
 * -1: guess is too high, 1: guess is too low, 0: correct
 *
 * Key Insights:
 * - Classic binary search on sorted space [1, n]
 * - API gives direction to eliminate half the search space
 * - Template: "find exact match" with left <= right
 *
 * Time: O(log n)
 * Space: O(1)
 */

/**
 * Forward declaration of guess API.
 * The guess API is defined in the parent class GuessGame.
 * You can call guess(x) which returns:
 * -1 : My number is lower (your guess is too high)
 *  1 : My number is higher (your guess is too low)
 *  0 : Congratulations! You got it.
 */

// Base class for the guess game (simulates the LeetCode environment)
abstract class GuessGame {
    private int pick;

    // Constructor for testing purposes
    public GuessGame(int pick) {
        this.pick = pick;
    }

    // Simulated API - in actual LeetCode this is provided
    protected int guess(int num) {
        if (num > pick) return -1;  // Too high
        if (num < pick) return 1;   // Too low
        return 0;                   // Correct!
    }
}

public class GuessNumberHigherOrLower extends GuessGame {

    // Constructor for testing
    public GuessNumberHigherOrLower(int pick) {
        super(pick);
    }

    /**
     * Main solution using classic binary search template.
     * Template: Find exact match (left <= right)
     */
    public int guessNumber(int n) {
        int left = 1;    // Start of search range
        int right = n;   // End of search range

        while (left <= right) {
            // Calculate mid to avoid overflow
            int mid = left + (right - left) / 2;

            // Get result from guess API
            int result = guess(mid);

            if (result == 0) {
                return mid; // Found the correct number!
            } else if (result == 1) {
                // Guess too low, search right half
                left = mid + 1;
            } else { // result == -1
                // Guess too high, search left half
                right = mid - 1;
            }
        }

        // Should never reach here given problem constraints
        return -1;
    }

    /**
     * Alternative implementation using the "find minimum valid" template.
     * Shows how the same problem can be solved with different templates.
     */
    public int guessNumberAlternative(int n) {
        int left = 1, right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;
            int result = guess(mid);

            if (result <= 0) {
                // mid is correct or too high, answer is at mid or to the left
                right = mid;
            } else {
                // mid is too low, answer is to the right
                left = mid + 1;
            }
        }

        return left; // left == right at this point
    }

    /**
     * Brute force solution for comparison (inefficient - educational only).
     * Time: O(n) - too slow for large inputs
     */
    public int guessNumberBruteForce(int n) {
        for (int i = 1; i <= n; i++) {
            if (guess(i) == 0) {
                return i;
            }
        }
        return -1; // Should never reach here
    }

    /**
     * Solution with detailed logging for educational purposes.
     * Helps understand the binary search process step by step.
     */
    public int guessNumberWithLogging(int n) {
        int left = 1, right = n;
        int attempts = 0;

        System.out.println("Starting binary search for number in range [1, " + n + "]");

        while (left <= right) {
            attempts++;
            int mid = left + (right - left) / 2;
            int result = guess(mid);

            System.out.printf("Attempt %d: guess(%d) = %d ", attempts, mid, result);

            if (result == 0) {
                System.out.println("→ FOUND IT!");
                System.out.println("Total attempts: " + attempts);
                return mid;
            } else if (result == 1) {
                System.out.println("→ too low, search [" + (mid + 1) + ", " + right + "]");
                left = mid + 1;
            } else {
                System.out.println("→ too high, search [" + left + ", " + (mid - 1) + "]");
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println("=== 374. Guess Number Higher or Lower ===\n");

        // Test Case 1: Example 1
        System.out.println("Test 1: n=10, pick=6");
        GuessNumberHigherOrLower game1 = new GuessNumberHigherOrLower(6);
        int result1 = game1.guessNumber(10);
        System.out.println("Expected: 6, Got: " + result1);
        System.out.println("Correct: " + (result1 == 6));
        System.out.println();

        // Test Case 2: Example 2
        System.out.println("Test 2: n=1, pick=1");
        GuessNumberHigherOrLower game2 = new GuessNumberHigherOrLower(1);
        int result2 = game2.guessNumber(1);
        System.out.println("Expected: 1, Got: " + result2);
        System.out.println("Correct: " + (result2 == 1));
        System.out.println();

        // Test Case 3: Example 3
        System.out.println("Test 3: n=2, pick=1");
        GuessNumberHigherOrLower game3 = new GuessNumberHigherOrLower(1);
        int result3 = game3.guessNumber(2);
        System.out.println("Expected: 1, Got: " + result3);
        System.out.println("Correct: " + (result3 == 1));
        System.out.println();

        // Test Case 4: Edge case - pick at maximum
        System.out.println("Test 4: n=10, pick=10 (maximum)");
        GuessNumberHigherOrLower game4 = new GuessNumberHigherOrLower(10);
        int result4 = game4.guessNumber(10);
        System.out.println("Expected: 10, Got: " + result4);
        System.out.println("Correct: " + (result4 == 10));
        System.out.println();

        // Test Case 5: Large number test
        System.out.println("Test 5: n=100, pick=42");
        GuessNumberHigherOrLower game5 = new GuessNumberHigherOrLower(42);
        int result5 = game5.guessNumber(100);
        System.out.println("Expected: 42, Got: " + result5);
        System.out.println("Correct: " + (result5 == 42));
        System.out.println();

        // Demonstration with logging
        System.out.println("=== Step-by-step demonstration ===");
        System.out.println("Test: n=10, pick=6 (with detailed logging)");
        GuessNumberHigherOrLower gameDemo = new GuessNumberHigherOrLower(6);
        gameDemo.guessNumberWithLogging(10);
        System.out.println();

        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        System.out.println("For n = 1,000,000:");
        System.out.println("Binary Search: ~20 guesses maximum");
        System.out.println("Brute Force: up to 1,000,000 guesses");
        System.out.println("Binary search is " + (1000000 / 20) + "x faster!");
        System.out.println();

        // API explanation
        System.out.println("=== API Return Values ===");
        System.out.println("guess(num) returns:");
        System.out.println("  -1: Your guess is too HIGH (num > pick)");
        System.out.println("   1: Your guess is too LOW (num < pick)");
        System.out.println("   0: Your guess is CORRECT (num == pick)");
        System.out.println();

        // Template reminder
        System.out.println("=== Binary Search Template Used ===");
        System.out.println("while (left <= right) {");
        System.out.println("    int mid = left + (right - left) / 2;");
        System.out.println("    int result = guess(mid);");
        System.out.println("    if (result == 0) return mid;");
        System.out.println("    else if (result == 1) left = mid + 1;");
        System.out.println("    else right = mid - 1;");
        System.out.println("}");
    }
}