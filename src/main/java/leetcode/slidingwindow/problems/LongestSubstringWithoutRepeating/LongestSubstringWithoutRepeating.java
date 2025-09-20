package leetcode.slidingwindow.problems.LongestSubstringWithoutRepeating;

import java.util.*;

public class LongestSubstringWithoutRepeating {
    
    public int lengthOfLongestSubstring(String s) {
        Set<Character> charSet = new HashSet<>();
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            while (charSet.contains(currentChar)) {
                charSet.remove(s.charAt(left));
                left++;
            }
            
            charSet.add(currentChar);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public int lengthOfLongestSubstringOptimized(String s) {
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            if (charIndex.containsKey(currentChar) && charIndex.get(currentChar) >= left) {
                left = charIndex.get(currentChar) + 1;
            }
            
            charIndex.put(currentChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public int lengthOfLongestSubstringArray(String s) {
        int[] charIndex = new int[128];
        Arrays.fill(charIndex, -1);
        
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            if (charIndex[currentChar] >= left) {
                left = charIndex[currentChar] + 1;
            }
            
            charIndex[currentChar] = right;
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public int lengthOfLongestSubstringVerbose(String s) {
        Set<Character> charSet = new HashSet<>();
        int left = 0;
        int maxLength = 0;
        int bestStart = 0;
        int bestEnd = -1;
        
        System.out.println("Input string: \"" + s + "\"");
        System.out.println("\nWindow progression:");
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            System.out.printf("\nRight=%d, char='%c'\n", right, currentChar);
            
            if (charSet.contains(currentChar)) {
                System.out.printf("  Duplicate found! Shrinking window...\n");
                while (charSet.contains(currentChar)) {
                    char removedChar = s.charAt(left);
                    charSet.remove(removedChar);
                    System.out.printf("    Removed '%c' at index %d\n", removedChar, left);
                    left++;
                }
            }
            
            charSet.add(currentChar);
            int currentLength = right - left + 1;
            
            System.out.printf("  Window: [%d,%d] = \"%s\"\n", 
                            left, right, s.substring(left, right + 1));
            System.out.printf("  Characters in set: %s\n", charSet);
            System.out.printf("  Current length: %d\n", currentLength);
            
            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestStart = left;
                bestEnd = right;
                System.out.printf("  ✓ New maximum length: %d\n", maxLength);
            }
        }
        
        if (bestEnd >= 0) {
            System.out.printf("\nLongest substring: \"%s\" (length %d)\n", 
                            s.substring(bestStart, bestEnd + 1), maxLength);
        } else {
            System.out.println("\nEmpty string - no substring found");
        }
        
        return maxLength;
    }
    
    public String actualLongestSubstring(String s) {
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        int resultStart = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            if (charIndex.containsKey(currentChar) && charIndex.get(currentChar) >= left) {
                left = charIndex.get(currentChar) + 1;
            }
            
            charIndex.put(currentChar, right);
            
            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                resultStart = left;
            }
        }
        
        return maxLength == 0 ? "" : s.substring(resultStart, resultStart + maxLength);
    }
    
    public static void main(String[] args) {
        LongestSubstringWithoutRepeating solution = new LongestSubstringWithoutRepeating();
        
        System.out.println("=== Testing HashSet Solution ===\n");
        runTests(solution::lengthOfLongestSubstring);
        
        System.out.println("\n=== Testing HashMap Optimized Solution ===\n");
        runTests(solution::lengthOfLongestSubstringOptimized);
        
        System.out.println("\n=== Testing Array Solution (Fastest) ===\n");
        runTests(solution::lengthOfLongestSubstringArray);
        
        System.out.println("\n=== Verbose Trace Example ===\n");
        solution.lengthOfLongestSubstringVerbose("abcabcbb");
        
        System.out.println("\n=== Actual Substring Examples ===\n");
        testActualSubstrings(solution);
        
        System.out.println("\n=== Edge Cases ===\n");
        testEdgeCases(solution);
        
        System.out.println("\n=== Performance Comparison ===\n");
        performanceTest(solution);
    }
    
    private static void runTests(java.util.function.Function<String, Integer> method) {
        String[] testStrings = {
            "abcabcbb",
            "bbbbb",
            "pwwkew",
            "",
            " ",
            "au",
            "dvdf",
            "abba"
        };
        
        int[] expected = {3, 1, 3, 0, 1, 2, 3, 2};
        
        for (int i = 0; i < testStrings.length; i++) {
            int result = method.apply(testStrings[i]);
            String status = result == expected[i] ? "✓" : "✗";
            System.out.printf("Test %d: \"%s\" → %d %s (expected %d)\n", 
                            i + 1, testStrings[i], result, status, expected[i]);
        }
    }
    
    private static void testActualSubstrings(LongestSubstringWithoutRepeating solution) {
        String[] tests = {"abcabcbb", "bbbbb", "pwwkew", "dvdf"};
        
        for (String test : tests) {
            String result = solution.actualLongestSubstring(test);
            System.out.printf("\"%s\" → longest substring: \"%s\" (length %d)\n", 
                            test, result, result.length());
        }
    }
    
    private static void testEdgeCases(LongestSubstringWithoutRepeating solution) {
        System.out.println("Empty string:");
        System.out.println("Result: " + solution.lengthOfLongestSubstring("") + " (Expected: 0)");
        
        System.out.println("\nSingle character:");
        System.out.println("Result: " + solution.lengthOfLongestSubstring("a") + " (Expected: 1)");
        
        System.out.println("\nAll same characters:");
        System.out.println("Result: " + solution.lengthOfLongestSubstring("aaaa") + " (Expected: 1)");
        
        System.out.println("\nAll unique characters:");
        System.out.println("Result: " + solution.lengthOfLongestSubstring("abcdef") + " (Expected: 6)");
        
        System.out.println("\nSpecial characters and spaces:");
        System.out.println("Result: " + solution.lengthOfLongestSubstring("a b!c@d#") + " (Expected: 8)");
    }
    
    private static void performanceTest(LongestSubstringWithoutRepeating solution) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(42);
        
        for (int i = 0; i < 10000; i++) {
            sb.append((char)('a' + rand.nextInt(26)));
        }
        String testString = sb.toString();
        
        long start = System.nanoTime();
        int result1 = solution.lengthOfLongestSubstring(testString);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = solution.lengthOfLongestSubstringOptimized(testString);
        long time2 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result3 = solution.lengthOfLongestSubstringArray(testString);
        long time3 = System.nanoTime() - start;
        
        System.out.println("Test string length: 10,000 characters");
        System.out.printf("HashSet:  Result=%d, Time=%.2f ms\n", result1, time1/1_000_000.0);
        System.out.printf("HashMap:  Result=%d, Time=%.2f ms\n", result2, time2/1_000_000.0);
        System.out.printf("Array:    Result=%d, Time=%.2f ms\n", result3, time3/1_000_000.0);
    }
}