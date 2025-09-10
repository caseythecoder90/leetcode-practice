package leetcode.arrays.problems.LongestCommonPrefix;

/**
 * LeetCode 14: Longest Common Prefix
 * Difficulty: Easy
 *
 * Problem: Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 *
 * Constraints:
 * - 1 <= strs.length <= 200
 * - 0 <= strs[i].length <= 200
 * - strs[i] consists of only lowercase English letters
 *
 * Pattern: String Processing, Vertical Scanning
 * Time Complexity: O(S) where S is the sum of all characters in all strings
 * Space Complexity: O(1) extra space
 */
public class LongestCommonPrefix {

    /**
     * Optimal Solution: Vertical Scanning
     * 
     * Key Insight: Compare characters column by column across all strings
     * Stop when we find the first mismatch or reach end of any string
     * 
     * Algorithm:
     * 1. Handle edge cases (empty array)
     * 2. Use first string as reference
     * 3. For each character position, check if all strings have the same character
     * 4. Stop at first mismatch or when any string ends
     * 
     * @param strs array of strings
     * @return longest common prefix string
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // Use first string as reference
        String firstString = strs[0];
        
        // Check each character position
        for (int i = 0; i < firstString.length(); i++) {
            char currentChar = firstString.charAt(i);
            
            // Compare with same position in all other strings
            for (int j = 1; j < strs.length; j++) {
                // If we've reached end of current string or characters don't match
                if (i >= strs[j].length() || strs[j].charAt(i) != currentChar) {
                    return firstString.substring(0, i);
                }
            }
        }
        
        // All characters match, return the entire first string
        return firstString;
    }
    
    /**
     * Alternative Solution: Horizontal Scanning
     * Compare prefix with each string one by one
     */
    public String longestCommonPrefixHorizontal(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        String prefix = strs[0];
        
        for (int i = 1; i < strs.length; i++) {
            // Shrink prefix until it matches beginning of current string
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        
        return prefix;
    }

    public static void main(String[] args) {
        LongestCommonPrefix solution = new LongestCommonPrefix();
        
        // Test Case 1: Common prefix exists
        String[] strs1 = {"flower", "flow", "flight"};
        System.out.println("Input: [\"flower\",\"flow\",\"flight\"]");
        System.out.println("Output: \"" + solution.longestCommonPrefix(strs1) + "\"");
        System.out.println("Expected: \"fl\"\n");
        
        // Test Case 2: No common prefix
        String[] strs2 = {"dog", "racecar", "car"};
        System.out.println("Input: [\"dog\",\"racecar\",\"car\"]");
        System.out.println("Output: \"" + solution.longestCommonPrefix(strs2) + "\"");
        System.out.println("Expected: \"\"\n");
        
        // Test Case 3: Single string
        String[] strs3 = {"single"};
        System.out.println("Input: [\"single\"]");
        System.out.println("Output: \"" + solution.longestCommonPrefix(strs3) + "\"");
        System.out.println("Expected: \"single\"\n");
        
        // Test Case 4: Empty string in array
        String[] strs4 = {"", "abc"};
        System.out.println("Input: [\"\",\"abc\"]");
        System.out.println("Output: \"" + solution.longestCommonPrefix(strs4) + "\"");
        System.out.println("Expected: \"\"\n");
        
        // Test Case 5: All strings are identical
        String[] strs5 = {"abc", "abc", "abc"};
        System.out.println("Input: [\"abc\",\"abc\",\"abc\"]");
        System.out.println("Output: \"" + solution.longestCommonPrefix(strs5) + "\"");
        System.out.println("Expected: \"abc\"\n");
        
        // Test horizontal scanning approach
        System.out.println("=== Horizontal Scanning Approach ===");
        System.out.println("Input: [\"flower\",\"flow\",\"flight\"]");
        System.out.println("Output: \"" + solution.longestCommonPrefixHorizontal(strs1) + "\"");
        System.out.println("Expected: \"fl\"");
    }
}