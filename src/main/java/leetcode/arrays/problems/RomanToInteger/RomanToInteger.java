package leetcode.arrays.problems.RomanToInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 13: Roman to Integer
 * Difficulty: Easy
 *
 * Problem: Convert a roman numeral string to an integer.
 * Roman numerals are represented by seven symbols: I=1, V=5, X=10, L=50, C=100, D=500, M=1000
 * 
 * Special cases:
 * - I before V (4) or X (9)
 * - X before L (40) or C (90) 
 * - C before D (400) or M (900)
 *
 * Constraints:
 * - 1 <= s.length <= 15
 * - s contains only characters ('I', 'V', 'X', 'L', 'C', 'D', 'M')
 * - s is guaranteed to be a valid roman numeral in range [1, 3999]
 *
 * Pattern: String Processing, Hash Map, Look-ahead
 * Time Complexity: O(n) where n is length of string
 * Space Complexity: O(1) - fixed size hash map
 */
public class RomanToInteger {
    
    /**
     * Optimal Solution: Right-to-Left Traversal
     * 
     * Key Insight: If current symbol value < previous symbol value, subtract it (subtractive case)
     * Otherwise, add it (normal case)
     * 
     * Algorithm:
     * 1. Create map of roman symbols to values
     * 2. Traverse string from right to left
     * 3. If current value < previous value, subtract; otherwise add
     * 4. Keep track of previous value for comparison
     * 
     * @param s roman numeral string
     * @return integer value
     */
    public int romanToInt(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        
        int result = 0;
        int prevValue = 0;
        
        // Traverse from right to left
        for (int i = s.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(s.charAt(i));
            
            // If current value is less than previous, it's a subtractive case
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            
            prevValue = currentValue;
        }
        
        return result;
    }
    
    /**
     * Alternative Solution: Left-to-Right with Look-ahead
     * Check if next character forms a subtractive pair
     */
    public int romanToIntLookAhead(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        
        int result = 0;
        
        for (int i = 0; i < s.length(); i++) {
            int currentValue = romanMap.get(s.charAt(i));
            
            // Look ahead to check for subtractive cases
            if (i + 1 < s.length()) {
                int nextValue = romanMap.get(s.charAt(i + 1));
                if (currentValue < nextValue) {
                    // Subtractive case: subtract current and add next
                    result += (nextValue - currentValue);
                    i++; // Skip next character as we've processed it
                    continue;
                }
            }
            
            result += currentValue;
        }
        
        return result;
    }
    
    /**
     * Optimized Solution: Using Switch Statement (Fastest)
     * Avoids hash map overhead for better performance
     */
    public int romanToIntOptimized(String s) {
        int result = 0;
        int prevValue = 0;
        
        for (int i = s.length() - 1; i >= 0; i--) {
            int currentValue;
            char c = s.charAt(i);
            
            switch (c) {
                case 'I': currentValue = 1; break;
                case 'V': currentValue = 5; break;
                case 'X': currentValue = 10; break;
                case 'L': currentValue = 50; break;
                case 'C': currentValue = 100; break;
                case 'D': currentValue = 500; break;
                case 'M': currentValue = 1000; break;
                default: currentValue = 0; // Should never happen
            }
            
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            
            prevValue = currentValue;
        }
        
        return result;
    }

    public static void main(String[] args) {
        RomanToInteger solution = new RomanToInteger();
        
        // Test Case 1: Simple case
        String s1 = "III";
        System.out.println("Input: \"" + s1 + "\"");
        System.out.println("Output: " + solution.romanToInt(s1));
        System.out.println("Expected: 3\n");
        
        // Test Case 2: Subtractive case IV
        String s2 = "IV";
        System.out.println("Input: \"" + s2 + "\"");
        System.out.println("Output: " + solution.romanToInt(s2));
        System.out.println("Expected: 4\n");
        
        // Test Case 3: Mixed case
        String s3 = "IX";
        System.out.println("Input: \"" + s3 + "\"");
        System.out.println("Output: " + solution.romanToInt(s3));
        System.out.println("Expected: 9\n");
        
        // Test Case 4: Complex case
        String s4 = "LVIII";
        System.out.println("Input: \"" + s4 + "\"");
        System.out.println("Output: " + solution.romanToInt(s4));
        System.out.println("Expected: 58 (L=50, V=5, III=3)\n");
        
        // Test Case 5: Multiple subtractive cases
        String s5 = "MCMXC";
        System.out.println("Input: \"" + s5 + "\"");
        System.out.println("Output: " + solution.romanToInt(s5));
        System.out.println("Expected: 1990 (M=1000, CM=900, XC=90)\n");
        
        // Test Case 6: Maximum value
        String s6 = "MMMCMXCIX";
        System.out.println("Input: \"" + s6 + "\"");
        System.out.println("Output: " + solution.romanToInt(s6));
        System.out.println("Expected: 3999\n");
        
        // Test alternative approaches
        System.out.println("=== Look-ahead Approach ===");
        System.out.println("Input: \"" + s4 + "\"");
        System.out.println("Output: " + solution.romanToIntLookAhead(s4));
        System.out.println("Expected: 58\n");
        
        System.out.println("=== Optimized Switch Approach ===");
        System.out.println("Input: \"" + s5 + "\"");
        System.out.println("Output: " + solution.romanToIntOptimized(s5));
        System.out.println("Expected: 1990");
    }
}