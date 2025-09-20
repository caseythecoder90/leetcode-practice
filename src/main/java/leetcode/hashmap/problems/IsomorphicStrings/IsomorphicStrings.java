package leetcode.hashmap.problems.IsomorphicStrings;

import java.util.*;

public class IsomorphicStrings {

    // Approach 1: Two HashMaps for Bidirectional Mapping
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Character, Character> sToT = new HashMap<>();
        Map<Character, Character> tToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            if (sToT.containsKey(charS)) {
                if (sToT.get(charS) != charT) {
                    return false;
                }
            } else {
                sToT.put(charS, charT);
            }

            if (tToS.containsKey(charT)) {
                if (tToS.get(charT) != charS) {
                    return false;
                }
            } else {
                tToS.put(charT, charS);
            }
        }

        return true;
    }

    // Approach 2: Single HashMap with Set
    public boolean isIsomorphicSet(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Character, Character> map = new HashMap<>();
        Set<Character> mappedChars = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            if (map.containsKey(charS)) {
                if (map.get(charS) != charT) {
                    return false;
                }
            } else {
                if (mappedChars.contains(charT)) {
                    return false;
                }
                map.put(charS, charT);
                mappedChars.add(charT);
            }
        }

        return true;
    }

    // Approach 3: Array mapping (for ASCII characters)
    public boolean isIsomorphicArray(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] sToT = new int[256];
        int[] tToS = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            if (sToT[charS] == 0 && tToS[charT] == 0) {
                sToT[charS] = charT;
                tToS[charT] = charS;
            } else if (sToT[charS] != charT || tToS[charT] != charS) {
                return false;
            }
        }

        return true;
    }

    // Approach 4: Index mapping
    public boolean isIsomorphicIndex(String s, String t) {
        if (s.length() != t.length()) return false;

        Map<Character, Integer> sIndex = new HashMap<>();
        Map<Character, Integer> tIndex = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            Integer sLastIndex = sIndex.put(s.charAt(i), i);
            Integer tLastIndex = tIndex.put(t.charAt(i), i);

            if (!Objects.equals(sLastIndex, tLastIndex)) {
                return false;
            }
        }

        return true;
    }

    // Approach 5: Transform to pattern
    public boolean isIsomorphicPattern(String s, String t) {
        return transformToPattern(s).equals(transformToPattern(t));
    }

    private String transformToPattern(String str) {
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder pattern = new StringBuilder();
        int nextId = 0;

        for (char c : str.toCharArray()) {
            if (!map.containsKey(c)) {
                map.put(c, nextId++);
            }
            pattern.append(map.get(c)).append(",");
        }

        return pattern.toString();
    }

    public static void main(String[] args) {
        IsomorphicStrings solution = new IsomorphicStrings();

        // Test case 1: Valid isomorphic strings
        System.out.println("Test 1 - s=\"egg\", t=\"add\"");
        System.out.println("Expected: true, Got: " +
            solution.isIsomorphic("egg", "add"));

        // Test case 2: Invalid isomorphic strings
        System.out.println("\nTest 2 - s=\"foo\", t=\"bar\"");
        System.out.println("Expected: false, Got: " +
            solution.isIsomorphic("foo", "bar"));

        // Test case 3: Valid with multiple characters
        System.out.println("\nTest 3 - s=\"paper\", t=\"title\"");
        System.out.println("Expected: true, Got: " +
            solution.isIsomorphic("paper", "title"));

        // Test case 4: Single character
        System.out.println("\nTest 4 - s=\"a\", t=\"b\"");
        System.out.println("Expected: true, Got: " +
            solution.isIsomorphic("a", "b"));

        // Test case 5: Same strings
        System.out.println("\nTest 5 - s=\"abc\", t=\"abc\"");
        System.out.println("Expected: true, Got: " +
            solution.isIsomorphic("abc", "abc"));

        // Test case 6: Invalid - different mappings
        System.out.println("\nTest 6 - s=\"badc\", t=\"baba\"");
        System.out.println("Expected: false, Got: " +
            solution.isIsomorphic("badc", "baba"));

        // Test all approaches
        System.out.println("\n=== Testing All Approaches ===");
        String testS = "paper";
        String testT = "title";
        System.out.println("Two HashMaps: " + solution.isIsomorphic(testS, testT));
        System.out.println("HashMap + Set: " + solution.isIsomorphicSet(testS, testT));
        System.out.println("Array Mapping: " + solution.isIsomorphicArray(testS, testT));
        System.out.println("Index Mapping: " + solution.isIsomorphicIndex(testS, testT));
        System.out.println("Pattern Transform: " + solution.isIsomorphicPattern(testS, testT));
    }
}