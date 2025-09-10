package leetcode.arrays.problems.IntegerToRoman;

import java.util.List;
import java.util.Map;

/**
 * LeetCode 12: Integer to Roman
 * Difficulty: Medium
 *
 * Problem: Convert an integer to a roman numeral.
 * Integer range: 1 <= num <= 3999
 * 
 * Roman numerals are formed by combining symbols and adding the values:
 * I = 1, V = 5, X = 10, L = 50, C = 100, D = 500, M = 1000
 * 
 * Subtractive cases: IV (4), IX (9), XL (40), XC (90), CD (400), CM (900)
 *
 * Pattern: String Building, Greedy Algorithm
 * Time Complexity: O(1) - maximum 13 symbols possible
 * Space Complexity: O(1) - fixed size mappings
 */
public class IntegerToRoman {
    
    /**
     * User's Original Solution: Separate Mappings with First Digit Check
     * 
     * Great working solution! Key insights:
     * - Separates basic symbols from subtractive cases
     * - Uses first digit to determine which mapping to use
     * - Correctly handles all edge cases
     * 
     * This solution demonstrates good problem understanding and works correctly.
     */
    public String intToRomanOriginal(int num) {
        Map<Integer, String> stepOneMapping = Map.ofEntries(
                Map.entry(1, "I"),
                Map.entry(5, "V"),
                Map.entry(10, "X"),
                Map.entry(50, "L"),
                Map.entry(100, "C"),
                Map.entry(500, "D"),
                Map.entry(1000, "M")
        );

        Map<Integer, String> stepTwoMapping = Map.ofEntries(
                Map.entry(4, "IV"),
                Map.entry(9, "IX"),
                Map.entry(40, "XL"),
                Map.entry(90, "XC"),
                Map.entry(400, "CD"),
                Map.entry(900, "CM")
        );

        List<Integer> stepOneNumbers = List.of(1000, 500, 100, 50, 10, 5, 1);
        List<Integer> stepTwoNumbers = List.of(900, 400, 90, 40, 9, 4);

        StringBuilder myBuilder = new StringBuilder();

        while (num != 0) {
            String numString = String.valueOf(num);

            if (numString.charAt(0) == '4' || numString.charAt(0) == '9') {
                for (Integer stepTwoNumber : stepTwoNumbers) {
                    if (stepTwoNumber <= num) {
                        String appendValue = stepTwoMapping.get(stepTwoNumber);
                        myBuilder.append(appendValue);
                        num -= stepTwoNumber;
                        break;
                    }
                }
            } else {
                for (Integer stepOneNumber : stepOneNumbers) {
                    if (stepOneNumber <= num) {
                        String appendValue = stepOneMapping.get(stepOneNumber);
                        myBuilder.append(appendValue);
                        num -= stepOneNumber;
                        break;
                    }
                }
            }
        }

        return myBuilder.toString();
    }
    
    /**
     * Optimized Solution 1: Combined Mapping (Recommended)
     * 
     * Key Improvement: Combine all values into single ordered list
     * - Eliminates need for first digit check
     * - Simpler logic with single loop
     * - Greedy approach: always use largest possible value
     * 
     * @param num integer to convert (1 <= num <= 3999)
     * @return roman numeral string
     */
    public String intToRoman(int num) {
        // Combined mapping in descending order - greedy approach
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < values.length; i++) {
            // Use as many of current symbol as possible
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }
        
        return result.toString();
    }
    
    /**
     * Alternative Optimized Solution: Using Map.of() for cleaner code
     * Same performance as array approach but more readable
     */
    public String intToRomanMapVersion(int num) {
        // All values in descending order including subtractive cases
        List<Integer> values = List.of(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1);
        Map<Integer, String> mapping = Map.ofEntries(
            Map.entry(1000, "M"),
            Map.entry(900, "CM"),
            Map.entry(500, "D"),
            Map.entry(400, "CD"),
            Map.entry(100, "C"),
            Map.entry(90, "XC"),
            Map.entry(50, "L"),
            Map.entry(40, "XL"),
            Map.entry(10, "X"),
            Map.entry(9, "IX"),
            Map.entry(5, "V"),
            Map.entry(4, "IV"),
            Map.entry(1, "I")
        );
        
        StringBuilder result = new StringBuilder();
        
        for (int value : values) {
            while (num >= value) {
                result.append(mapping.get(value));
                num -= value;
            }
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        IntegerToRoman solution = new IntegerToRoman();
        
        // Test Case 1: Basic conversion
        int num1 = 3;
        System.out.println("Input: " + num1);
        System.out.println("Output: \"" + solution.intToRoman(num1) + "\"");
        System.out.println("Expected: \"III\"\n");
        
        // Test Case 2: Subtractive case
        int num2 = 4;
        System.out.println("Input: " + num2);
        System.out.println("Output: \"" + solution.intToRoman(num2) + "\"");
        System.out.println("Expected: \"IV\"\n");
        
        // Test Case 3: Another subtractive case
        int num3 = 9;
        System.out.println("Input: " + num3);
        System.out.println("Output: \"" + solution.intToRoman(num3) + "\"");
        System.out.println("Expected: \"IX\"\n");
        
        // Test Case 4: Mixed case
        int num4 = 58;
        System.out.println("Input: " + num4);
        System.out.println("Output: \"" + solution.intToRoman(num4) + "\"");
        System.out.println("Expected: \"LVIII\" (L=50, V=5, III=3)\n");
        
        // Test Case 5: Complex with multiple subtractive cases
        int num5 = 1994;
        System.out.println("Input: " + num5);
        System.out.println("Output: \"" + solution.intToRoman(num5) + "\"");
        System.out.println("Expected: \"MCMXCIV\" (M=1000, CM=900, XC=90, IV=4)\n");
        
        // Test Case 6: Maximum value
        int num6 = 3999;
        System.out.println("Input: " + num6);
        System.out.println("Output: \"" + solution.intToRoman(num6) + "\"");
        System.out.println("Expected: \"MMMCMXCIX\"\n");
        
        // Test Case 7: Edge cases
        int num7 = 1;
        System.out.println("Input: " + num7);
        System.out.println("Output: \"" + solution.intToRoman(num7) + "\"");
        System.out.println("Expected: \"I\"\n");
        
        // Test original solution
        System.out.println("=== Testing Original Solution ===");
        System.out.println("Input: " + num5);
        System.out.println("Original Output: \"" + solution.intToRomanOriginal(num5) + "\"");
        System.out.println("Expected: \"MCMXCIV\"\n");
        
        // Test map version
        System.out.println("=== Testing Map Version ===");
        System.out.println("Input: " + num4);
        System.out.println("Map Version Output: \"" + solution.intToRomanMapVersion(num4) + "\"");
        System.out.println("Expected: \"LVIII\"");
    }
}