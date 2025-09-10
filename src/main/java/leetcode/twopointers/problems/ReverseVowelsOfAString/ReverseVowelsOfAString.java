package leetcode.twopointers.problems.ReverseVowelsOfAString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 345: Reverse Vowels of a String
 * Difficulty: Easy
 *
 * Problem: Given a string s, reverse only all the vowels in the string and return it.
 * The vowels are 'a', 'e', 'i', 'o', 'u', and they can appear in both lower and upper cases.
 *
 * Constraints:
 * - 1 <= s.length <= 3 * 10^5
 * - s consists of printable ASCII characters
 *
 * Pattern: Two Pointers
 * Time Complexity: O(n)
 * Space Complexity: O(n) for char array
 */
public class ReverseVowelsOfAString {
    
    /**
     * Your Original Solution: Two Pointers with Set (Optimal!)
     * 
     * Excellent implementation! This is the standard optimal approach.
     * Key strengths:
     * - Perfect two-pointer technique
     * - Efficient vowel lookup with Set
     * - Clean swap logic
     * - Handles both cases correctly
     * 
     * This solution is already optimal and follows best practices.
     */
    public String reverseVowels(String s) {
        Set<Character> vowels = Set.of(
            'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'
        );

        char[] characters = s.toCharArray();
        int i = 0; 
        int j = characters.length - 1;

        while (i < j) {
            // Find vowel from left
            while (i < j && !vowels.contains(characters[i])) {
                i++;
            }
            
            // Find vowel from right
            while (i < j && !vowels.contains(characters[j])) {
                j--;
            }
            
            // Swap vowels
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
            i++; 
            j--;
        }

        return new String(characters);
    }
    
    /**
     * Alternative 1: Using String for Vowel Check
     * Slightly less efficient than Set but more readable for some
     */
    public String reverseVowelsString(String s) {
        String vowels = "aeiouAEIOU";
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        
        while (left < right) {
            // Find vowel from left
            while (left < right && vowels.indexOf(chars[left]) == -1) {
                left++;
            }
            
            // Find vowel from right  
            while (left < right && vowels.indexOf(chars[right]) == -1) {
                right--;
            }
            
            // Swap
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Alternative 2: Boolean Array for Vowel Check (Fastest)
     * Uses ASCII values for O(1) lookup - most efficient for large inputs
     */
    public String reverseVowelsBoolArray(String s) {
        boolean[] isVowel = new boolean[256]; // ASCII table size
        String vowelStr = "aeiouAEIOU";
        
        // Mark vowels in boolean array
        for (char c : vowelStr.toCharArray()) {
            isVowel[c] = true;
        }
        
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        
        while (left < right) {
            // Find vowel from left
            while (left < right && !isVowel[chars[left]]) {
                left++;
            }
            
            // Find vowel from right
            while (left < right && !isVowel[chars[right]]) {
                right--;
            }
            
            // Swap
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Alternative 3: Stack Approach (Less Efficient but Educational)
     * Collect vowels, then replace them in reverse order
     * Good for understanding the problem but not optimal
     */
    public String reverseVowelsStack(String s) {
        Set<Character> vowelSet = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        List<Character> vowels = new ArrayList<>();
        
        // Collect all vowels
        for (char c : s.toCharArray()) {
            if (vowelSet.contains(c)) {
                vowels.add(c);
            }
        }
        
        // Replace vowels in reverse order
        StringBuilder result = new StringBuilder();
        int vowelIndex = vowels.size() - 1;
        
        for (char c : s.toCharArray()) {
            if (vowelSet.contains(c)) {
                result.append(vowels.get(vowelIndex--));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Alternative 4: Recursive Approach (Educational)
     * Not practical for large inputs due to call stack, but shows different thinking
     */
    public String reverseVowelsRecursive(String s) {
        return reverseVowelsHelper(s.toCharArray(), 0, s.length() - 1);
    }
    
    private String reverseVowelsHelper(char[] chars, int left, int right) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        
        if (left >= right) {
            return new String(chars);
        }
        
        // Find vowel from left
        while (left < right && !vowels.contains(chars[left])) {
            left++;
        }
        
        // Find vowel from right
        while (left < right && !vowels.contains(chars[right])) {
            right--;
        }
        
        if (left < right) {
            // Swap
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            
            // Recurse with next positions
            return reverseVowelsHelper(chars, left + 1, right - 1);
        }
        
        return new String(chars);
    }

    public static void main(String[] args) {
        ReverseVowelsOfAString solution = new ReverseVowelsOfAString();
        
        // Test Case 1: Basic case
        String s1 = "hello";
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Output: \"" + solution.reverseVowels(s1) + "\"");
        System.out.println("Expected: \"holle\"\n");
        
        // Test Case 2: Mixed case vowels
        String s2 = "leetcode";
        System.out.println("Input: \"" + s2 + "\"");
        System.out.println("Output: \"" + solution.reverseVowels(s2) + "\"");
        System.out.println("Expected: \"leotcede\"\n");
        
        // Test Case 3: Upper and lower case mix
        String s3 = "Aa";
        System.out.println("Input: \"" + s3 + "\"");
        System.out.println("Output: \"" + solution.reverseVowels(s3) + "\"");
        System.out.println("Expected: \"aA\"\n");
        
        // Test Case 4: No vowels
        String s4 = "bcdfg";
        System.out.println("Input: \"" + s4 + "\"");
        System.out.println("Output: \"" + solution.reverseVowels(s4) + "\"");
        System.out.println("Expected: \"bcdfg\"\n");
        
        // Test Case 5: All vowels
        String s5 = "aeiou";
        System.out.println("Input: \"" + s5 + "\"");
        System.out.println("Output: \"" + solution.reverseVowels(s5) + "\"");
        System.out.println("Expected: \"uoiea\"\n");
        
        // Test Case 6: Single character
        String s6 = "a";
        System.out.println("Input: \"" + s6 + "\"");
        System.out.println("Output: \"" + solution.reverseVowels(s6) + "\"");
        System.out.println("Expected: \"a\"\n");
        
        // Test alternative approaches
        System.out.println("=== Alternative Approaches ===");
        
        System.out.println("String approach: \"" + solution.reverseVowelsString(s2) + "\"");
        System.out.println("Boolean array approach: \"" + solution.reverseVowelsBoolArray(s2) + "\"");
        System.out.println("Stack approach: \"" + solution.reverseVowelsStack(s2) + "\"");
        System.out.println("Recursive approach: \"" + solution.reverseVowelsRecursive(s2) + "\"");
        System.out.println("Expected for all: \"leotcede\"");
    }
}