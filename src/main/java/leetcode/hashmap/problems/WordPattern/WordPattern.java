package leetcode.hashmap.problems.WordPattern;

import java.util.*;

public class WordPattern {

    // Approach 1: Two HashMaps for Bidirectional Mapping
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.trim().split("\\s+");
        if (pattern.length() != words.length) return false;

        Map<Character, String> patternToWord = new HashMap<>();
        Map<String, Character> wordToPattern = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];

            if (patternToWord.containsKey(c)) {
                if (!patternToWord.get(c).equals(word)) {
                    return false;
                }
            } else {
                patternToWord.put(c, word);
            }

            if (wordToPattern.containsKey(word)) {
                if (wordToPattern.get(word) != c) {
                    return false;
                }
            } else {
                wordToPattern.put(word, c);
            }
        }

        return true;
    }

    // Approach 2: Single HashMap with Set for tracking used words
    public boolean wordPatternSet(String pattern, String s) {
        String[] words = s.trim().split("\\s+");
        if (pattern.length() != words.length) return false;

        Map<Character, String> map = new HashMap<>();
        Set<String> usedWords = new HashSet<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];

            if (map.containsKey(c)) {
                if (!map.get(c).equals(word)) {
                    return false;
                }
            } else {
                if (usedWords.contains(word)) {
                    return false;
                }
                map.put(c, word);
                usedWords.add(word);
            }
        }

        return true;
    }

    // Approach 3: Using index mapping
    public boolean wordPatternIndex(String pattern, String s) {
        String[] words = s.split(" ");
        if (words.length != pattern.length()) return false;

        Map<Character, Integer> charIndex = new HashMap<>();
        Map<String, Integer> wordIndex = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];

            Integer charLastIndex = charIndex.put(c, i);
            Integer wordLastIndex = wordIndex.put(word, i);

            if (!Objects.equals(charLastIndex, wordLastIndex)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        WordPattern solution = new WordPattern();

        // Test case 1: Valid pattern
        System.out.println("Test 1 - pattern=\"abba\", s=\"dog cat cat dog\"");
        System.out.println("Expected: true, Got: " +
            solution.wordPattern("abba", "dog cat cat dog"));

        // Test case 2: Invalid pattern - different ending
        System.out.println("\nTest 2 - pattern=\"abba\", s=\"dog cat cat fish\"");
        System.out.println("Expected: false, Got: " +
            solution.wordPattern("abba", "dog cat cat fish"));

        // Test case 3: Invalid pattern - all same pattern but different words
        System.out.println("\nTest 3 - pattern=\"aaaa\", s=\"dog cat cat dog\"");
        System.out.println("Expected: false, Got: " +
            solution.wordPattern("aaaa", "dog cat cat dog"));

        // Test case 4: Single character/word
        System.out.println("\nTest 4 - pattern=\"a\", s=\"dog\"");
        System.out.println("Expected: true, Got: " +
            solution.wordPattern("a", "dog"));

        // Test case 5: Different lengths
        System.out.println("\nTest 5 - pattern=\"ab\", s=\"dog cat cat\"");
        System.out.println("Expected: false, Got: " +
            solution.wordPattern("ab", "dog cat cat"));

        // Test all approaches
        System.out.println("\n=== Testing All Approaches ===");
        String testPattern = "abba";
        String testS = "dog cat cat dog";
        System.out.println("Two HashMaps: " + solution.wordPattern(testPattern, testS));
        System.out.println("HashMap + Set: " + solution.wordPatternSet(testPattern, testS));
        System.out.println("Index Mapping: " + solution.wordPatternIndex(testPattern, testS));
    }
}