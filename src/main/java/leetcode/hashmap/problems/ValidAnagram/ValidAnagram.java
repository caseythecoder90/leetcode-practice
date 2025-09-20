package leetcode.hashmap.problems.ValidAnagram;

import java.util.*;

public class ValidAnagram {

    // Approach 1: Frequency Array (Optimal for lowercase English)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] frequency = new int[26];

        for (int i = 0; i < s.length(); i++) {
            frequency[s.charAt(i) - 'a']++;
            frequency[t.charAt(i) - 'a']--;
        }

        for (int count : frequency) {
            if (count != 0) return false;
        }

        return true;
    }

    // Approach 2: Two Arrays (Clear separation)
    public boolean isAnagramTwoArrays(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] sFreq = new int[26];
        int[] tFreq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            sFreq[s.charAt(i) - 'a']++;
            tFreq[t.charAt(i) - 'a']++;
        }

        return Arrays.equals(sFreq, tFreq);
    }

    // Approach 3: HashMap (For Unicode characters)
    public boolean isAnagramHashMap(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Character, Integer> charCount = new HashMap<>();

        for (char c : s.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            int count = charCount.getOrDefault(c, 0);
            if (count == 0) {
                return false;
            }
            charCount.put(c, count - 1);
        }

        return true;
    }

    // Approach 4: Sorting
    public boolean isAnagramSorting(String s, String t) {
        if (s.length() != t.length()) return false;

        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        Arrays.sort(sArray);
        Arrays.sort(tArray);

        return Arrays.equals(sArray, tArray);
    }

    // Approach 5: Early termination with character count
    public boolean isAnagramEarlyStop(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] frequency = new int[26];
        int uniqueChars = 0;
        int completedChars = 0;

        // Count frequency in s
        for (char c : s.toCharArray()) {
            int index = c - 'a';
            if (frequency[index] == 0) {
                uniqueChars++;
            }
            frequency[index]++;
        }

        // Process t and check
        for (char c : t.toCharArray()) {
            int index = c - 'a';
            if (frequency[index] == 0) {
                return false; // Character in t not in s
            }

            frequency[index]--;

            if (frequency[index] == 0) {
                completedChars++;
                if (completedChars == uniqueChars) {
                    // Early termination possible if at end
                    return true;
                }
            }
        }

        return completedChars == uniqueChars;
    }

    // Follow-up: Unicode support
    public boolean isAnagramUnicode(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Integer, Integer> codePointCount = new HashMap<>();

        // Count code points in s
        s.codePoints().forEach(cp ->
            codePointCount.put(cp, codePointCount.getOrDefault(cp, 0) + 1)
        );

        // Subtract code points in t
        t.codePoints().forEach(cp -> {
            codePointCount.put(cp, codePointCount.getOrDefault(cp, 0) - 1);
        });

        // Check all counts are zero
        return codePointCount.values().stream().allMatch(count -> count == 0);
    }

    public static void main(String[] args) {
        ValidAnagram solution = new ValidAnagram();

        // Test case 1: Valid anagram
        System.out.println("Test 1 - s=\"anagram\", t=\"nagaram\"");
        System.out.println("Expected: true, Got: " +
            solution.isAnagram("anagram", "nagaram"));

        // Test case 2: Not anagram
        System.out.println("\nTest 2 - s=\"rat\", t=\"car\"");
        System.out.println("Expected: false, Got: " +
            solution.isAnagram("rat", "car"));

        // Test case 3: Different lengths
        System.out.println("\nTest 3 - s=\"a\", t=\"ab\"");
        System.out.println("Expected: false, Got: " +
            solution.isAnagram("a", "ab"));

        // Test case 4: Single character
        System.out.println("\nTest 4 - s=\"a\", t=\"a\"");
        System.out.println("Expected: true, Got: " +
            solution.isAnagram("a", "a"));

        // Test case 5: Same letters different frequency
        System.out.println("\nTest 5 - s=\"aaab\", t=\"aabb\"");
        System.out.println("Expected: false, Got: " +
            solution.isAnagram("aaab", "aabb"));

        // Test all approaches
        System.out.println("\n=== Testing All Approaches ===");
        String testS = "anagram";
        String testT = "nagaram";
        System.out.println("Frequency Array: " + solution.isAnagram(testS, testT));
        System.out.println("Two Arrays: " + solution.isAnagramTwoArrays(testS, testT));
        System.out.println("HashMap: " + solution.isAnagramHashMap(testS, testT));
        System.out.println("Sorting: " + solution.isAnagramSorting(testS, testT));
        System.out.println("Early Stop: " + solution.isAnagramEarlyStop(testS, testT));

        // Test Unicode support
        System.out.println("\n=== Unicode Test ===");
        String unicodeS = "你好世界";
        String unicodeT = "界世好你";
        System.out.println("Unicode Anagram: " + solution.isAnagramUnicode(unicodeS, unicodeT));
    }
}