package leetcode.slidingwindow.problems.LongestSubstringWithoutRepeating;

public class LengthCalculationDemo {

    public static void demonstrateLengthCalculation(String s) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INPUT: \"" + s + "\"");
        System.out.println("=".repeat(60));

        System.out.println("\nShowing why we use (right - left + 1) for length:\n");

        for (int windowSize = 1; windowSize <= Math.min(s.length(), 5); windowSize++) {
            System.out.println("Window Size " + windowSize + ":");
            for (int left = 0; left <= s.length() - windowSize; left++) {
                int right = left + windowSize - 1;
                if (right < s.length()) {
                    String substring = s.substring(left, right + 1);

                    System.out.printf("  Indices [%d,%d]: \"%s\"\n", left, right, substring);
                    System.out.printf("    Using (right - left + 1): %d - %d + 1 = %d ✓ (correct)\n",
                                    right, left, right - left + 1);
                    System.out.printf("    Using (right - left):     %d - %d = %d     ✗ (wrong by 1)\n",
                                    right, left, right - left);
                    System.out.println();
                }
            }
        }
    }

    public static void visualizeIndices(String s) {
        System.out.println("\nVISUAL INDEX REPRESENTATION:");
        System.out.println("=" + "=".repeat(50));

        System.out.print("String:  ");
        for (char c : s.toCharArray()) {
            System.out.printf(" %c ", c);
        }
        System.out.println();

        System.out.print("Indices: ");
        for (int i = 0; i < s.length(); i++) {
            System.out.printf(" %d ", i);
        }
        System.out.println("\n");

        System.out.println("Examples of substring windows:");
        System.out.println("-".repeat(50));

        int[][] windows = {{0, 0}, {0, 1}, {1, 2}, {0, 2}, {2, 4}};
        for (int[] window : windows) {
            if (window[1] < s.length()) {
                int left = window[0];
                int right = window[1];
                String substring = s.substring(left, right + 1);

                System.out.print("  ");
                for (int i = 0; i < s.length(); i++) {
                    if (i >= left && i <= right) {
                        System.out.print("[" + s.charAt(i) + "]");
                    } else {
                        System.out.print(" " + s.charAt(i) + " ");
                    }
                }

                System.out.printf("  left=%d, right=%d\n", left, right);
                System.out.printf("  Substring: \"%s\"\n", substring);
                System.out.printf("  Length = right - left + 1 = %d - %d + 1 = %d\n",
                                right, left, right - left + 1);
                System.out.printf("  (If we used right - left = %d - %d = %d, we'd be off by 1!)\n\n",
                                right, left, right - left);
            }
        }
    }

    public static void compareWithOptimized() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMMON CONFUSION: Why different formulas?");
        System.out.println("=".repeat(60));

        System.out.println("\nACTUALLY, both approaches use the SAME formula!");
        System.out.println("Let's trace through an example:\n");

        String s = "abcabc";
        System.out.println("Input: \"" + s + "\"");
        System.out.println("\nStep-by-step for finding \"abc\" (indices 0-2):");

        System.out.println("\n1. HashSet approach:");
        System.out.println("   - left = 0, right = 2");
        System.out.println("   - Window contains: \"abc\"");
        System.out.println("   - Length = right - left + 1 = 2 - 0 + 1 = 3 ✓");

        System.out.println("\n2. HashMap approach (optimized):");
        System.out.println("   - left = 0, right = 2");
        System.out.println("   - Window contains: \"abc\"");
        System.out.println("   - Length = right - left + 1 = 2 - 0 + 1 = 3 ✓");

        System.out.println("\nWhen we hit duplicate 'a' at index 3:");
        System.out.println("\n1. HashSet approach:");
        System.out.println("   - Shrinks window by removing chars until 'a' is removed");
        System.out.println("   - After shrinking: left = 1, right = 3");
        System.out.println("   - Window: \"bca\"");
        System.out.println("   - Length = 3 - 1 + 1 = 3 ✓");

        System.out.println("\n2. HashMap approach:");
        System.out.println("   - Jumps directly: left = charIndex['a'] + 1 = 0 + 1 = 1");
        System.out.println("   - Now: left = 1, right = 3");
        System.out.println("   - Window: \"bca\"");
        System.out.println("   - Length = 3 - 1 + 1 = 3 ✓");

        System.out.println("\n" + "!".repeat(60));
        System.out.println("IMPORTANT: The formula is ALWAYS (right - left + 1)");
        System.out.println("If you see (right - left) alone, it would be WRONG!");
        System.out.println("!".repeat(60));
    }

    public static void explainMathematically() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MATHEMATICAL EXPLANATION");
        System.out.println("=".repeat(60));

        System.out.println("\nWhy do we need +1?");
        System.out.println("-".repeat(30));
        System.out.println("Because indices are 0-based and inclusive on both ends!");

        System.out.println("\nThink of it this way:");
        System.out.println("- From index 0 to 0: How many elements? 1 (not 0!)");
        System.out.println("- From index 0 to 1: How many elements? 2 (not 1!)");
        System.out.println("- From index 0 to 2: How many elements? 3 (not 2!)");

        System.out.println("\nGeneral formula:");
        System.out.println("Number of elements from index i to j (inclusive) = j - i + 1");

        System.out.println("\nAnalogy with counting:");
        System.out.println("- Counting from 5 to 8: {5, 6, 7, 8}");
        System.out.println("- How many numbers? 8 - 5 + 1 = 4");
        System.out.println("- NOT 8 - 5 = 3!");
    }

    public static void main(String[] args) {
        demonstrateLengthCalculation("abcde");

        visualizeIndices("abcde");

        compareWithOptimized();

        explainMathematically();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("\n✓ CORRECT: length = right - left + 1");
        System.out.println("✗ WRONG:   length = right - left");
        System.out.println("\nBoth HashSet and HashMap approaches use the SAME formula!");
        System.out.println("The +1 accounts for inclusive counting of both endpoints.");
    }
}