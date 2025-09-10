package leetcode.arrays.problems.MergeStringsAlternately;

public class MergeStringsAlternately {
    
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0;
        
        while (i < word1.length() || j < word2.length()) {
            if (i < word1.length()) {
                result.append(word1.charAt(i));
                i++;
            }
            if (j < word2.length()) {
                result.append(word2.charAt(j));
                j++;
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        MergeStringsAlternately solution = new MergeStringsAlternately();
        
        // Test Case 1
        String word1_1 = "abc", word2_1 = "pqr";
        String result1 = solution.mergeAlternately(word1_1, word2_1);
        System.out.println("Test 1: " + result1); // Expected: "apbqcr"
        
        // Test Case 2
        String word1_2 = "ab", word2_2 = "pqrs";
        String result2 = solution.mergeAlternately(word1_2, word2_2);
        System.out.println("Test 2: " + result2); // Expected: "apbqrs"
        
        // Test Case 3
        String word1_3 = "abcd", word2_3 = "pq";
        String result3 = solution.mergeAlternately(word1_3, word2_3);
        System.out.println("Test 3: " + result3); // Expected: "apbqcd"
        
        // Additional Test Cases
        String word1_4 = "a", word2_4 = "b";
        String result4 = solution.mergeAlternately(word1_4, word2_4);
        System.out.println("Test 4: " + result4); // Expected: "ab"
        
        String word1_5 = "", word2_5 = "xyz";
        String result5 = solution.mergeAlternately(word1_5, word2_5);
        System.out.println("Test 5: " + result5); // Expected: "xyz"
        
        String word1_6 = "hello", word2_6 = "";
        String result6 = solution.mergeAlternately(word1_6, word2_6);
        System.out.println("Test 6: " + result6); // Expected: "hello"
    }
}