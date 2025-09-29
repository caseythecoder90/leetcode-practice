package leetcode.hashmap.problems.GroupAnagrams;

import java.util.*;
import java.util.stream.Collectors;

public class GroupAnagrams {

    // Approach 1: Sort String as Key (Most Common)
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] charArray = str.toCharArray();
            Arrays.sort(charArray);
            String key = new String(charArray);

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

    // Approach 2: Character Frequency as Key
    public List<List<String>> groupAnagramsFrequency(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            String key = getFrequencyString(str);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

    private String getFrequencyString(String str) {
        int[] freq = new int[26];
        for (char c : str.toCharArray()) {
            freq[c - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                sb.append((char)('a' + i)).append(freq[i]);
            }
        }
        return sb.toString();
    }

    // Approach 3: Your Original Idea (Fixed) - Using Indices
    public List<List<String>> groupAnagramsIndices(String[] strs) {
        Map<String, List<Integer>> strsToIndexes = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            char[] charArray = strs[i].toCharArray();
            Arrays.sort(charArray);
            String sortedStr = new String(charArray);

            strsToIndexes.computeIfAbsent(sortedStr, k -> new ArrayList<>()).add(i);
        }

        List<List<String>> groupedAnagrams = new ArrayList<>();
        for (List<Integer> indices : strsToIndexes.values()) {
            List<String> currentGroup = new ArrayList<>();
            for (int index : indices) {
                currentGroup.add(strs[index]);
            }
            groupedAnagrams.add(currentGroup);
        }

        return groupedAnagrams;
    }

    // Approach 4: Using Stream API (Clean but potentially slower)
    public List<List<String>> groupAnagramsStream(String[] strs) {
        return new ArrayList<>(
            Arrays.stream(strs)
                .collect(Collectors.groupingBy(
                    str -> {
                        char[] chars = str.toCharArray();
                        Arrays.sort(chars);
                        return new String(chars);
                    }
                ))
                .values()
        );
    }

    // Approach 5: Prime Number Product (Mathematical approach)
    public List<List<String>> groupAnagramsPrime(String[] strs) {
        Map<Long, List<String>> map = new HashMap<>();

        // First 26 prime numbers for a-z
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
                       43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};

        for (String str : strs) {
            long key = 1;
            for (char c : str.toCharArray()) {
                key *= primes[c - 'a'];
            }
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();

        // Test case 1: Multiple groups
        String[] test1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Test 1: " + Arrays.toString(test1));
        System.out.println("Result: " + solution.groupAnagrams(test1));

        // Test case 2: Empty string
        String[] test2 = {""};
        System.out.println("\nTest 2: " + Arrays.toString(test2));
        System.out.println("Result: " + solution.groupAnagrams(test2));

        // Test case 3: Single character
        String[] test3 = {"a"};
        System.out.println("\nTest 3: " + Arrays.toString(test3));
        System.out.println("Result: " + solution.groupAnagrams(test3));

        // Test case 4: All same anagrams
        String[] test4 = {"abc", "bca", "cab", "acb", "bac", "cba"};
        System.out.println("\nTest 4: " + Arrays.toString(test4));
        System.out.println("Result: " + solution.groupAnagrams(test4));

        // Compare all approaches
        System.out.println("\n=== Comparing All Approaches ===");
        String[] testAll = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Sort approach: " + solution.groupAnagrams(testAll));
        System.out.println("Frequency approach: " + solution.groupAnagramsFrequency(testAll));
        System.out.println("Indices approach: " + solution.groupAnagramsIndices(testAll));
        System.out.println("Stream approach: " + solution.groupAnagramsStream(testAll));
        System.out.println("Prime approach: " + solution.groupAnagramsPrime(testAll));
    }
}