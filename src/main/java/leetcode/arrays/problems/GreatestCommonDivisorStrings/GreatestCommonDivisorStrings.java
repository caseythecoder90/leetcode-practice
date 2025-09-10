package leetcode.arrays.problems.GreatestCommonDivisorStrings;

public class GreatestCommonDivisorStrings {
    
    public String gcdOfStrings(String str1, String str2) {
        // Quick check: if no common divisor exists, str1+str2 != str2+str1
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }
        
        // Find GCD of the lengths
        int gcdLength = gcd(str1.length(), str2.length());
        
        // The GCD string is the prefix of str1 with length = gcd of lengths
        return str1.substring(0, gcdLength);
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static void main(String[] args) {
        GreatestCommonDivisorStrings solution = new GreatestCommonDivisorStrings();
        
        // Test Case 1: Example from problem
        String str1_1 = "ABCABC", str2_1 = "ABC";
        String result1 = solution.gcdOfStrings(str1_1, str2_1);
        System.out.println("Test 1: \"" + str1_1 + "\", \"" + str2_1 + "\" -> \"" + result1 + "\""); 
        // Expected: "ABC"
        
        // Test Case 2: Example from problem
        String str1_2 = "ABABAB", str2_2 = "ABAB";
        String result2 = solution.gcdOfStrings(str1_2, str2_2);
        System.out.println("Test 2: \"" + str1_2 + "\", \"" + str2_2 + "\" -> \"" + result2 + "\""); 
        // Expected: "AB"
        
        // Test Case 3: No common divisor
        String str1_3 = "LEET", str2_3 = "CODE";
        String result3 = solution.gcdOfStrings(str1_3, str2_3);
        System.out.println("Test 3: \"" + str1_3 + "\", \"" + str2_3 + "\" -> \"" + result3 + "\""); 
        // Expected: ""
        
        // Test Case 4: Identical strings
        String str1_4 = "ABCABC", str2_4 = "ABCABC";
        String result4 = solution.gcdOfStrings(str1_4, str2_4);
        System.out.println("Test 4: \"" + str1_4 + "\", \"" + str2_4 + "\" -> \"" + result4 + "\""); 
        // Expected: "ABCABC"
        
        // Test Case 5: Single character
        String str1_5 = "AAA", str2_5 = "AA";
        String result5 = solution.gcdOfStrings(str1_5, str2_5);
        System.out.println("Test 5: \"" + str1_5 + "\", \"" + str2_5 + "\" -> \"" + result5 + "\""); 
        // Expected: "A"
        
        // Test Case 6: No repeating pattern
        String str1_6 = "ABCDEF", str2_6 = "ABC";
        String result6 = solution.gcdOfStrings(str1_6, str2_6);
        System.out.println("Test 6: \"" + str1_6 + "\", \"" + str2_6 + "\" -> \"" + result6 + "\""); 
        // Expected: ""
        
        // Test Case 7: Different length patterns
        String str1_7 = "ABABABAB", str2_7 = "ABAB";
        String result7 = solution.gcdOfStrings(str1_7, str2_7);
        System.out.println("Test 7: \"" + str1_7 + "\", \"" + str2_7 + "\" -> \"" + result7 + "\""); 
        // Expected: "ABAB"
        
        // Demonstrate GCD calculation
        System.out.println("\n--- GCD Calculations ---");
        GreatestCommonDivisorStrings demo = new GreatestCommonDivisorStrings();
        System.out.println("GCD(6, 4) = " + demo.gcd(6, 4)); // Expected: 2
        System.out.println("GCD(12, 8) = " + demo.gcd(12, 8)); // Expected: 4
        System.out.println("GCD(48, 18) = " + demo.gcd(48, 18)); // Expected: 6
    }
}