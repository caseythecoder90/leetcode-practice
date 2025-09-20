package leetcode.hashmap.problems.RansomNote;

import java.util.*;

public class RansomNote {

    public boolean canConstructOriginal(String ransomNote, String magazine) {
        Map<Character, Integer> frequencies = new HashMap<>();

        for (char curr : ransomNote.toCharArray()) {
            frequencies.put(curr, frequencies.getOrDefault(curr, 0) + 1);
        }

        for (char curr : magazine.toCharArray()) {
            if (frequencies.isEmpty()) {
                return true;
            }

            if (frequencies.containsKey(curr)) {
                int currValue = frequencies.get(curr);
                if (currValue <= 1) {
                    frequencies.remove(curr);
                } else {
                    frequencies.put(curr, currValue - 1);
                }
            }
        }

        return frequencies.isEmpty();
    }

    public boolean canConstructOptimized(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        Map<Character, Integer> magazineCount = new HashMap<>();

        for (char c : magazine.toCharArray()) {
            magazineCount.put(c, magazineCount.getOrDefault(c, 0) + 1);
        }

        for (char c : ransomNote.toCharArray()) {
            int count = magazineCount.getOrDefault(c, 0);
            if (count == 0) {
                return false;
            }
            magazineCount.put(c, count - 1);
        }

        return true;
    }

    public boolean canConstructArray(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        int[] charCount = new int[26];

        for (char c : magazine.toCharArray()) {
            charCount[c - 'a']++;
        }

        for (char c : ransomNote.toCharArray()) {
            if (--charCount[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public boolean canConstructArrayOptimal(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        int[] charCount = new int[26];

        for (int i = 0; i < magazine.length(); i++) {
            charCount[magazine.charAt(i) - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            if (--charCount[ransomNote.charAt(i) - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public boolean canConstructVerbose(String ransomNote, String magazine) {
        System.out.println("=== Ransom Note Construction ===");
        System.out.println("Ransom Note: \"" + ransomNote + "\"");
        System.out.println("Magazine: \"" + magazine + "\"");

        if (ransomNote.length() > magazine.length()) {
            System.out.println("\nEarly exit: ransom note is longer than magazine!");
            return false;
        }

        Map<Character, Integer> magazineCount = new HashMap<>();
        System.out.println("\nStep 1: Count magazine characters");

        for (char c : magazine.toCharArray()) {
            magazineCount.put(c, magazineCount.getOrDefault(c, 0) + 1);
        }
        System.out.println("Magazine character counts: " + magazineCount);

        System.out.println("\nStep 2: Try to use characters for ransom note");
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            int count = magazineCount.getOrDefault(c, 0);

            System.out.printf("  Need '%c': available=%d", c, count);

            if (count == 0) {
                System.out.println(" -> NOT ENOUGH! Cannot construct ransom note.");
                return false;
            }

            magazineCount.put(c, count - 1);
            System.out.printf(" -> Using one, %d remaining\n", count - 1);
        }

        System.out.println("\nSuccess! All characters were available.");
        System.out.println("Remaining magazine counts: " + magazineCount);
        return true;
    }

    public static void main(String[] args) {
        RansomNote solution = new RansomNote();

        System.out.println("=== Testing Original Solution ===\n");
        runTests(solution::canConstructOriginal, "Original (Your Solution)");

        System.out.println("\n=== Testing Optimized HashMap Solution ===\n");
        runTests(solution::canConstructOptimized, "Optimized HashMap");

        System.out.println("\n=== Testing Array Solution (Fastest) ===\n");
        runTests(solution::canConstructArray, "Array Solution");

        System.out.println("\n=== Testing Array Optimal (charAt) ===\n");
        runTests(solution::canConstructArrayOptimal, "Array Optimal");

        System.out.println("\n=== Verbose Example ===\n");
        solution.canConstructVerbose("aa", "aab");

        System.out.println("\n=== Edge Cases ===\n");
        testEdgeCases(solution);

        System.out.println("\n=== Performance Comparison ===\n");
        performanceTest(solution);
    }

    private static void runTests(java.util.function.BiFunction<String, String, Boolean> method, String methodName) {
        String[][] testCases = {
            {"a", "b", "false"},
            {"aa", "ab", "false"},
            {"aa", "aab", "true"},
            {"", "", "true"},
            {"a", "a", "true"},
            {"abc", "bahc", "true"},  // "bahc" contains a, b, c, and h - can make "abc"
            {"abc", "aabbcc", "true"}
        };

        System.out.println("Testing: " + methodName);
        for (String[] test : testCases) {
            boolean result = method.apply(test[0], test[1]);
            boolean expected = Boolean.parseBoolean(test[2]);
            String status = result == expected ? "✓" : "✗";
            System.out.printf("  ransomNote=\"%s\", magazine=\"%s\" → %b %s (expected %b)\n",
                            test[0], test[1], result, status, expected);
        }
    }

    private static void testEdgeCases(RansomNote solution) {
        System.out.println("1. Empty strings:");
        System.out.println("   \"\" from \"\" → " + solution.canConstructArray("", ""));

        System.out.println("\n2. Single character match:");
        System.out.println("   \"a\" from \"a\" → " + solution.canConstructArray("a", "a"));

        System.out.println("\n3. Single character no match:");
        System.out.println("   \"a\" from \"b\" → " + solution.canConstructArray("a", "b"));

        System.out.println("\n4. All same characters:");
        System.out.println("   \"aaa\" from \"aaaa\" → " + solution.canConstructArray("aaa", "aaaa"));

        System.out.println("\n5. Ransom note longer than magazine:");
        System.out.println("   \"aaaa\" from \"aaa\" → " + solution.canConstructArray("aaaa", "aaa"));

        System.out.println("\n6. Using all magazine characters:");
        System.out.println("   \"abc\" from \"abc\" → " + solution.canConstructArray("abc", "abc"));

        System.out.println("\n7. Magazine has extra characters:");
        System.out.println("   \"abc\" from \"aabbccdd\" → " + solution.canConstructArray("abc", "aabbccdd"));
    }

    private static void performanceTest(RansomNote solution) {
        StringBuilder ransomNote = new StringBuilder();
        StringBuilder magazine = new StringBuilder();
        Random rand = new Random(42);

        for (int i = 0; i < 50000; i++) {
            char c = (char)('a' + rand.nextInt(26));
            ransomNote.append(c);
            magazine.append(c);
            if (rand.nextDouble() < 0.3) {
                magazine.append(c);
            }
        }

        String rNote = ransomNote.toString();
        String mag = magazine.toString();

        System.out.println("Test size: " + rNote.length() + " characters");

        long start = System.nanoTime();
        boolean result1 = solution.canConstructOriginal(rNote, mag);
        long time1 = System.nanoTime() - start;

        start = System.nanoTime();
        boolean result2 = solution.canConstructOptimized(rNote, mag);
        long time2 = System.nanoTime() - start;

        start = System.nanoTime();
        boolean result3 = solution.canConstructArray(rNote, mag);
        long time3 = System.nanoTime() - start;

        start = System.nanoTime();
        boolean result4 = solution.canConstructArrayOptimal(rNote, mag);
        long time4 = System.nanoTime() - start;

        System.out.printf("Original:         Result=%b, Time=%.2f ms\n", result1, time1/1_000_000.0);
        System.out.printf("Optimized Map:    Result=%b, Time=%.2f ms\n", result2, time2/1_000_000.0);
        System.out.printf("Array:            Result=%b, Time=%.2f ms\n", result3, time3/1_000_000.0);
        System.out.printf("Array Optimal:    Result=%b, Time=%.2f ms\n", result4, time4/1_000_000.0);

        if (time4 > 0) {
            System.out.printf("\nSpeedup vs Original: %.2fx faster\n", (double)time1/time4);
        }
    }
}