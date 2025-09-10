package leetcode.arrays.problems.CanPlaceFlowers;

import java.util.Arrays;

public class CanPlaceFlowers {
    
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0) return true;
        
        int planted = 0;
        
        for (int i = 0; i < flowerbed.length; i++) {
            // Check if we can plant a flower at position i
            if (flowerbed[i] == 0 && 
                (i == 0 || flowerbed[i - 1] == 0) && 
                (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                
                // Plant the flower
                flowerbed[i] = 1;
                planted++;
                
                // Early termination: if we've planted enough flowers
                if (planted >= n) {
                    return true;
                }
            }
        }
        
        return planted >= n;
    }
    
    // Alternative implementation without modifying input array
    public boolean canPlaceFlowersNoModify(int[] flowerbed, int n) {
        if (n == 0) return true;
        
        int[] flowers = flowerbed.clone(); // Create copy to avoid modifying input
        int planted = 0;
        
        for (int i = 0; i < flowers.length; i++) {
            if (flowers[i] == 0 && 
                (i == 0 || flowers[i - 1] == 0) && 
                (i == flowers.length - 1 || flowers[i + 1] == 0)) {
                
                flowers[i] = 1;
                planted++;
                
                if (planted >= n) {
                    return true;
                }
            }
        }
        
        return planted >= n;
    }
    
    // Helper method to visualize the planting process
    public boolean canPlaceFlowersVerbose(int[] flowerbed, int n) {
        System.out.println("=== Flower Planting Simulation ===");
        System.out.println("Initial flowerbed: " + Arrays.toString(flowerbed));
        System.out.println("Need to plant: " + n + " flowers");
        
        if (n == 0) {
            System.out.println("No flowers needed to plant!");
            return true;
        }
        
        int planted = 0;
        
        for (int i = 0; i < flowerbed.length; i++) {
            System.out.printf("Checking position %d: ", i);
            
            if (flowerbed[i] == 1) {
                System.out.println("Already occupied");
                continue;
            }
            
            boolean leftOk = (i == 0 || flowerbed[i - 1] == 0);
            boolean rightOk = (i == flowerbed.length - 1 || flowerbed[i + 1] == 0);
            
            if (leftOk && rightOk) {
                flowerbed[i] = 1;
                planted++;
                System.out.println("PLANTED! Total planted: " + planted);
                System.out.println("Current state: " + Arrays.toString(flowerbed));
                
                if (planted >= n) {
                    System.out.println("SUCCESS: Planted enough flowers!");
                    return true;
                }
            } else {
                System.out.println("Cannot plant (adjacent flowers)");
            }
        }
        
        System.out.println("RESULT: Only planted " + planted + " out of " + n + " needed");
        return planted >= n;
    }
    
    public static void main(String[] args) {
        CanPlaceFlowers solution = new CanPlaceFlowers();
        
        // Test Case 1: Example from problem
        int[] flowerbed1 = {1, 0, 0, 0, 1};
        int n1 = 1;
        boolean result1 = solution.canPlaceFlowers(flowerbed1.clone(), n1);
        System.out.println("Test 1: " + Arrays.toString(new int[]{1, 0, 0, 0, 1}) + 
                          ", n = " + n1 + " → " + result1); // Expected: true
        
        // Test Case 2: Example from problem
        int[] flowerbed2 = {1, 0, 0, 0, 1};
        int n2 = 2;
        boolean result2 = solution.canPlaceFlowers(flowerbed2.clone(), n2);
        System.out.println("Test 2: " + Arrays.toString(new int[]{1, 0, 0, 0, 1}) + 
                          ", n = " + n2 + " → " + result2); // Expected: false
        
        // Test Case 3: Single element
        int[] flowerbed3 = {0};
        int n3 = 1;
        boolean result3 = solution.canPlaceFlowers(flowerbed3.clone(), n3);
        System.out.println("Test 3: " + Arrays.toString(new int[]{0}) + 
                          ", n = " + n3 + " → " + result3); // Expected: true
        
        // Test Case 4: All empty
        int[] flowerbed4 = {0, 0, 0, 0, 0};
        int n4 = 3;
        boolean result4 = solution.canPlaceFlowers(flowerbed4.clone(), n4);
        System.out.println("Test 4: " + Arrays.toString(new int[]{0, 0, 0, 0, 0}) + 
                          ", n = " + n4 + " → " + result4); // Expected: true (can plant at 0,2,4)
        
        // Test Case 5: No flowers needed
        int[] flowerbed5 = {1, 0, 1, 0, 1};
        int n5 = 0;
        boolean result5 = solution.canPlaceFlowers(flowerbed5.clone(), n5);
        System.out.println("Test 5: " + Arrays.toString(new int[]{1, 0, 1, 0, 1}) + 
                          ", n = " + n5 + " → " + result5); // Expected: true
        
        // Test Case 6: Edge case - alternating pattern
        int[] flowerbed6 = {1, 0, 1, 0, 0, 1, 0, 0};
        int n6 = 1;
        boolean result6 = solution.canPlaceFlowers(flowerbed6.clone(), n6);
        System.out.println("Test 6: " + Arrays.toString(flowerbed6) + 
                          ", n = " + n6 + " → " + result6); // Expected: true
        
        // Verbose demonstration for Test Case 1
        System.out.println("\n" + "=".repeat(50));
        int[] demo = {1, 0, 0, 0, 1};
        solution.canPlaceFlowersVerbose(demo, 1);
        
        // Test no-modify version
        System.out.println("\n--- Testing No-Modify Version ---");
        int[] original = {1, 0, 0, 0, 1};
        boolean resultNoModify = solution.canPlaceFlowersNoModify(original, 1);
        System.out.println("Original array unchanged: " + Arrays.toString(original));
        System.out.println("Result: " + resultNoModify);
    }
}