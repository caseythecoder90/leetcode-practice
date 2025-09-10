package leetcode.arrays.problems.KidsWithGreatestCandies;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class KidsWithGreatestCandies {
    
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        // Find the maximum number of candies any kid currently has
        int maxCandies = 0;
        for (int candy : candies) {
            maxCandies = Math.max(maxCandies, candy);
        }
        
        // Check each kid to see if they can have the greatest number
        List<Boolean> result = new ArrayList<>();
        for (int candy : candies) {
            // Kid can have greatest if: candy + extraCandies >= maxCandies
            result.add(candy + extraCandies >= maxCandies);
        }
        
        return result;
    }
    
    // Alternative optimized approach - avoid addition in loop
    public List<Boolean> kidsWithCandiesOptimized(int[] candies, int extraCandies) {
        // Find maximum candies
        int maxCandies = 0;
        for (int candy : candies) {
            maxCandies = Math.max(maxCandies, candy);
        }
        
        // Calculate threshold: candy >= maxCandies - extraCandies
        int threshold = maxCandies - extraCandies;
        
        List<Boolean> result = new ArrayList<>();
        for (int candy : candies) {
            result.add(candy >= threshold);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        KidsWithGreatestCandies solution = new KidsWithGreatestCandies();
        
        // Test Case 1: Example from problem
        int[] candies1 = {2, 3, 5, 1, 3};
        int extraCandies1 = 3;
        List<Boolean> result1 = solution.kidsWithCandies(candies1, extraCandies1);
        System.out.println("Test 1: " + Arrays.toString(candies1) + ", extraCandies = " + extraCandies1);
        System.out.println("Result: " + result1); // Expected: [true, true, true, false, true]
        
        // Test Case 2: Example from problem
        int[] candies2 = {4, 2, 1, 1, 2};
        int extraCandies2 = 1;
        List<Boolean> result2 = solution.kidsWithCandies(candies2, extraCandies2);
        System.out.println("\nTest 2: " + Arrays.toString(candies2) + ", extraCandies = " + extraCandies2);
        System.out.println("Result: " + result2); // Expected: [true, false, false, false, false]
        
        // Test Case 3: Example from problem
        int[] candies3 = {12, 1, 12};
        int extraCandies3 = 10;
        List<Boolean> result3 = solution.kidsWithCandies(candies3, extraCandies3);
        System.out.println("\nTest 3: " + Arrays.toString(candies3) + ", extraCandies = " + extraCandies3);
        System.out.println("Result: " + result3); // Expected: [true, false, true]
        
        // Test Case 4: All kids can have greatest
        int[] candies4 = {1, 1, 1, 1};
        int extraCandies4 = 0;
        List<Boolean> result4 = solution.kidsWithCandies(candies4, extraCandies4);
        System.out.println("\nTest 4: " + Arrays.toString(candies4) + ", extraCandies = " + extraCandies4);
        System.out.println("Result: " + result4); // Expected: [true, true, true, true]
        
        // Test Case 5: Large extra candies
        int[] candies5 = {1, 2, 3};
        int extraCandies5 = 10;
        List<Boolean> result5 = solution.kidsWithCandies(candies5, extraCandies5);
        System.out.println("\nTest 5: " + Arrays.toString(candies5) + ", extraCandies = " + extraCandies5);
        System.out.println("Result: " + result5); // Expected: [true, true, true]
        
        // Test optimized version
        System.out.println("\n--- Testing Optimized Version ---");
        List<Boolean> result1_opt = solution.kidsWithCandiesOptimized(candies1, extraCandies1);
        System.out.println("Optimized Test 1: " + result1_opt);
        System.out.println("Same as regular? " + result1.equals(result1_opt));
        
        // Demonstrate step-by-step for Test Case 1
        System.out.println("\n--- Step-by-step for Test Case 1 ---");
        System.out.println("candies = [2, 3, 5, 1, 3], extraCandies = 3");
        System.out.println("Max candies currently = 5");
        for (int i = 0; i < candies1.length; i++) {
            int total = candies1[i] + extraCandies1;
            boolean canBeGreatest = total >= 5;
            System.out.printf("Kid %d: %d + %d = %d, can be greatest? %s%n", 
                i + 1, candies1[i], extraCandies1, total, canBeGreatest);
        }
    }
}