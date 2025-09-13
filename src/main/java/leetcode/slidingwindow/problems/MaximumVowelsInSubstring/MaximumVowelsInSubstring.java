package leetcode.slidingwindow.problems.MaximumVowelsInSubstring;

import java.util.Set;

public class MaximumVowelsInSubstring {
    
    public int maxVowels(String s, int k) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
        
        int maxVowelCount = 0;
        for (int i = 0; i < k; i++) {
            if (vowels.contains(s.charAt(i))) {
                maxVowelCount++;
            }
        }
        
        if (maxVowelCount == k) {
            return k;
        }
        
        int currentVowelCount = maxVowelCount;
        
        for (int i = k; i < s.length(); i++) {
            if (vowels.contains(s.charAt(i))) {
                currentVowelCount++;
            }
            
            if (vowels.contains(s.charAt(i - k))) {
                currentVowelCount--;
            }
            
            maxVowelCount = Math.max(maxVowelCount, currentVowelCount);
            
            if (maxVowelCount == k) {
                return k;
            }
        }
        
        return maxVowelCount;
    }
    
    public int maxVowelsOptimized(String s, int k) {
        int vowelBits = 0;
        vowelBits |= 1 << ('a' - 'a');
        vowelBits |= 1 << ('e' - 'a');
        vowelBits |= 1 << ('i' - 'a');
        vowelBits |= 1 << ('o' - 'a');
        vowelBits |= 1 << ('u' - 'a');
        
        int maxVowelCount = 0;
        for (int i = 0; i < k; i++) {
            if ((vowelBits & (1 << (s.charAt(i) - 'a'))) != 0) {
                maxVowelCount++;
            }
        }
        
        if (maxVowelCount == k) return k;
        
        int currentVowelCount = maxVowelCount;
        
        for (int i = k; i < s.length(); i++) {
            if ((vowelBits & (1 << (s.charAt(i) - 'a'))) != 0) {
                currentVowelCount++;
            }
            if ((vowelBits & (1 << (s.charAt(i - k) - 'a'))) != 0) {
                currentVowelCount--;
            }
            
            if (currentVowelCount > maxVowelCount) {
                maxVowelCount = currentVowelCount;
                if (maxVowelCount == k) return k;
            }
        }
        
        return maxVowelCount;
    }
    
    public int maxVowelsArray(String s, int k) {
        boolean[] isVowel = new boolean[128];
        isVowel['a'] = isVowel['e'] = isVowel['i'] = isVowel['o'] = isVowel['u'] = true;
        
        int maxVowelCount = 0;
        for (int i = 0; i < k; i++) {
            if (isVowel[s.charAt(i)]) {
                maxVowelCount++;
            }
        }
        
        if (maxVowelCount == k) return k;
        
        int currentVowelCount = maxVowelCount;
        
        for (int i = k; i < s.length(); i++) {
            currentVowelCount += isVowel[s.charAt(i)] ? 1 : 0;
            currentVowelCount -= isVowel[s.charAt(i - k)] ? 1 : 0;
            
            if (currentVowelCount > maxVowelCount) {
                maxVowelCount = currentVowelCount;
                if (maxVowelCount == k) return k;
            }
        }
        
        return maxVowelCount;
    }
    
    public static void main(String[] args) {
        MaximumVowelsInSubstring solution = new MaximumVowelsInSubstring();
        
        System.out.println("=== Testing Standard Solution ===");
        testSolution(solution::maxVowels);
        
        System.out.println("\n=== Testing Bit Manipulation Solution ===");
        testSolution(solution::maxVowelsOptimized);
        
        System.out.println("\n=== Testing Array Lookup Solution ===");
        testSolution(solution::maxVowelsArray);
        
        System.out.println("\n=== Performance Test ===");
        performanceTest(solution);
    }
    
    private static void testSolution(java.util.function.BiFunction<String, Integer, Integer> method) {
        String[] testStrings = {
            "abciiidef",
            "aeiou", 
            "leetcode",
            "rhythms",
            "aaaaaa",
            "a",
            "aeiouaeiou"
        };
        int[] kValues = {3, 2, 3, 4, 2, 1, 5};
        int[] expected = {3, 2, 2, 0, 2, 1, 5};
        
        for (int i = 0; i < testStrings.length; i++) {
            int result = method.apply(testStrings[i], kValues[i]);
            String status = result == expected[i] ? "✓" : "✗";
            System.out.printf("%s Test %d: s=\"%s\", k=%d, Expected=%d, Got=%d\n",
                status, i+1, testStrings[i], kValues[i], expected[i], result);
        }
    }
    
    private static void performanceTest(MaximumVowelsInSubstring solution) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append((char)('a' + (i % 26)));
        }
        String largeString = sb.toString();
        int k = 10000;
        
        long start = System.nanoTime();
        int result1 = solution.maxVowels(largeString, k);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = solution.maxVowelsOptimized(largeString, k);
        long time2 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result3 = solution.maxVowelsArray(largeString, k);
        long time3 = System.nanoTime() - start;
        
        System.out.printf("Set-based:     %d vowels in %.2f ms\n", result1, time1/1_000_000.0);
        System.out.printf("Bit-based:     %d vowels in %.2f ms\n", result2, time2/1_000_000.0);
        System.out.printf("Array-based:   %d vowels in %.2f ms\n", result3, time3/1_000_000.0);
    }
}