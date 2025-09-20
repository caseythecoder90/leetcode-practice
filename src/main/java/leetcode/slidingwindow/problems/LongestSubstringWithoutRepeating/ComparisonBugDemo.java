package leetcode.slidingwindow.problems.LongestSubstringWithoutRepeating;

import java.util.*;

public class ComparisonBugDemo {

    // WRONG VERSION - using > instead of >=
    public static int lengthOfLongestSubstringWrong(String s) {
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        System.out.println("\n=== WRONG VERSION (using >) ===");
        System.out.println("Input: \"" + s + "\"");

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            System.out.printf("\nStep %d: right=%d, char='%c'\n", right + 1, right, currentChar);

            if (charIndex.containsKey(currentChar) && charIndex.get(currentChar) > left) {
                int oldLeft = left;
                left = charIndex.get(currentChar) + 1;
                System.out.printf("  Duplicate found! charIndex['%c']=%d > left=%d? YES\n",
                                currentChar, charIndex.get(currentChar), oldLeft);
                System.out.printf("  Moving left: %d → %d\n", oldLeft, left);
            } else if (charIndex.containsKey(currentChar)) {
                System.out.printf("  Duplicate found! charIndex['%c']=%d > left=%d? NO (BUG!)\n",
                                currentChar, charIndex.get(currentChar), left);
                System.out.printf("  Left stays at: %d (THIS IS THE BUG!)\n", left);
            }

            charIndex.put(currentChar, right);
            int length = right - left + 1;
            maxLength = Math.max(maxLength, length);

            System.out.printf("  Window: [%d,%d] = \"%s\", length=%d\n",
                            left, right, s.substring(left, right + 1), length);
            System.out.printf("  charIndex: %s\n", charIndex);

            if (length == maxLength) {
                System.out.printf("  ✓ Max length updated to: %d\n", maxLength);
            }
        }

        return maxLength;
    }

    // CORRECT VERSION - using >=
    public static int lengthOfLongestSubstringCorrect(String s) {
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        System.out.println("\n=== CORRECT VERSION (using >=) ===");
        System.out.println("Input: \"" + s + "\"");

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            System.out.printf("\nStep %d: right=%d, char='%c'\n", right + 1, right, currentChar);

            if (charIndex.containsKey(currentChar) && charIndex.get(currentChar) >= left) {
                int oldLeft = left;
                left = charIndex.get(currentChar) + 1;
                System.out.printf("  Duplicate found! charIndex['%c']=%d >= left=%d? YES\n",
                                currentChar, charIndex.get(currentChar), oldLeft);
                System.out.printf("  Moving left: %d → %d\n", oldLeft, left);
            } else if (charIndex.containsKey(currentChar)) {
                System.out.printf("  Duplicate found but charIndex['%c']=%d < left=%d (outside window)\n",
                                currentChar, charIndex.get(currentChar), left);
                System.out.printf("  Left stays at: %d (correct - old occurrence is outside window)\n", left);
            }

            charIndex.put(currentChar, right);
            int length = right - left + 1;
            maxLength = Math.max(maxLength, length);

            System.out.printf("  Window: [%d,%d] = \"%s\", length=%d\n",
                            left, right, s.substring(left, right + 1), length);
            System.out.printf("  charIndex: %s\n", charIndex);

            if (length == maxLength) {
                System.out.printf("  ✓ Max length updated to: %d\n", maxLength);
            }
        }

        return maxLength;
    }

    public static void demonstrateBugScenario() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("THE BUG SCENARIO: When left pointer is AT the duplicate's position");
        System.out.println("=".repeat(70));

        String testCase = "abba";
        System.out.println("\nTest string: \"" + testCase + "\"");
        System.out.println("\nThe bug appears at index 3 when we see the second 'a':");
        System.out.println("- We previously saw 'a' at index 0");
        System.out.println("- After processing 'b' at index 2, left moved to index 2");
        System.out.println("- Now at index 3, we see 'a' again");
        System.out.println("- charIndex['a'] = 0, left = 2");
        System.out.println("\nThe critical comparison:");
        System.out.println("  Using >  : Is 0 > 2? NO  → left stays at 2 (WRONG!)");
        System.out.println("  Using >= : Is 0 >= 2? NO → left stays at 2 (CORRECT!)");
        System.out.println("\nWait, that seems the same? Let's look at a clearer example...\n");

        testCase = "abcb";
        System.out.println("Better example: \"" + testCase + "\"");
        System.out.println("\nWhen we hit the second 'b' at index 3:");
        System.out.println("- First 'b' was at index 1");
        System.out.println("- Current left = 1 (after no duplicates yet)");
        System.out.println("- charIndex['b'] = 1, left = 1");
        System.out.println("\nThe critical comparison:");
        System.out.println("  Using >  : Is 1 > 1? NO  → left stays at 1 (WRONG! We have duplicate 'b')");
        System.out.println("  Using >= : Is 1 >= 1? YES → left moves to 2 (CORRECT!)");
        System.out.println("\nTHIS IS THE BUG: When the duplicate is EXACTLY at the left boundary!");
    }

    public static void runComparison(String s) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPARING BOTH VERSIONS");
        System.out.println("=".repeat(70));

        int wrong = lengthOfLongestSubstringWrong(s);
        int correct = lengthOfLongestSubstringCorrect(s);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESULTS:");
        System.out.printf("Wrong version (using >):   %d\n", wrong);
        System.out.printf("Correct version (using >=): %d\n", correct);

        if (wrong != correct) {
            System.out.println("\n⚠️  BUG DETECTED! Results differ!");
            System.out.println("The bug occurs when a duplicate character is exactly at the left boundary.");
        } else {
            System.out.println("\n✓ Results match (bug didn't manifest in this case)");
        }
    }

    public static void main(String[] args) {
        // First, explain the bug scenario
        demonstrateBugScenario();

        // Test case where the bug is visible
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TEST CASE 1: \"abcb\" - Bug will be visible");
        System.out.println("=".repeat(70));
        runComparison("abcb");

        // Another test case
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TEST CASE 2: \"pwwkew\" - Another example");
        System.out.println("=".repeat(70));
        runComparison("pwwkew");

        // Edge case
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TEST CASE 3: \"aa\" - Simple duplicate");
        System.out.println("=".repeat(70));
        runComparison("aa");

        System.out.println("\n" + "=".repeat(70));
        System.out.println("KEY TAKEAWAY");
        System.out.println("=".repeat(70));
        System.out.println("\nWhen checking if a duplicate is in the current window:");
        System.out.println("✗ WRONG:   charIndex.get(char) > left");
        System.out.println("✓ CORRECT: charIndex.get(char) >= left");
        System.out.println("\nThe >= is needed because if the duplicate is EXACTLY at left,");
        System.out.println("it's still part of the current window and we need to move past it!");
    }
}