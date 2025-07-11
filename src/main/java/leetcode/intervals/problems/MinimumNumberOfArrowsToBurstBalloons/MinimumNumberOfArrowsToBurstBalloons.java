package leetcode.intervals.problems.MinimumNumberOfArrowsToBurstBalloons;

import java.util.Arrays;

/**
 * LeetCode 452: Minimum Number of Arrows to Burst Balloons
 * 
 * Pattern: Greedy Point Coverage
 * Key Insight: Sort by end position, greedily place arrows at earliest ending positions
 * 
 * Time: O(n log n) - sorting dominates
 * Space: O(1) - constant extra space
 */
public class MinimumNumberOfArrowsToBurstBalloons {
    
    /**
     * Optimal Solution: Greedy Point Coverage
     * 
     * Strategy:
     * 1. Sort balloons by end position
     * 2. Shoot arrow at end of earliest-ending balloon
     * 3. This arrow hits all overlapping balloons
     * 4. Repeat for remaining balloons
     * 
     * Why sort by end position?
     * - Shooting at end of earliest-ending balloon maximizes overlap coverage
     * - Guarantees we hit the current balloon while potentially hitting others
     */
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        
        // Sort by end position - critical for greedy strategy
        // Use Integer.compare() to avoid integer overflow
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        
        int arrows = 1; // Always need at least one arrow
        int arrowPosition = points[0][1]; // Shoot first arrow at end of first balloon
        
        // Process remaining balloons
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > arrowPosition) {
                // Current balloon starts after arrow position - need new arrow
                arrows++;
                arrowPosition = points[i][1]; // Shoot at end of this balloon
            }
            // If points[i][0] <= arrowPosition, current arrow hits this balloon too
        }
        
        return arrows;
    }
    
    /**
     * Alternative Implementation: Explicit Overlap Checking
     * Same logic but more explicit about overlap detection
     */
    public int findMinArrowShotsV2(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        
        int arrows = 1;
        int[] currentGroup = points[0]; // Track the "active" balloon group
        
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] <= currentGroup[1]) {
                // Overlaps with current group - arrow can hit both
                // Update group to intersection (not needed for count, but conceptually clear)
                currentGroup[0] = Math.max(currentGroup[0], points[i][0]);
                currentGroup[1] = Math.min(currentGroup[1], points[i][1]);
            } else {
                // No overlap - need new arrow and new group
                arrows++;
                currentGroup = points[i];
            }
        }
        
        return arrows;
    }
    
    /**
     * Educational: Interval Intersection Approach
     * 
     * This approach explicitly finds the intersection of overlapping balloons
     * and places one arrow in each intersection region.
     */
    public int findMinArrowShotsIntersection(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        
        int arrows = 0;
        int i = 0;
        
        while (i < points.length) {
            // Find the intersection of all overlapping balloons starting from i
            int intersectionStart = points[i][0];
            int intersectionEnd = points[i][1];
            int j = i;
            
            // Extend the group as long as balloons overlap with intersection
            while (j < points.length && points[j][0] <= intersectionEnd) {
                intersectionStart = Math.max(intersectionStart, points[j][0]);
                intersectionEnd = Math.min(intersectionEnd, points[j][1]);
                j++;
            }
            
            // Shoot one arrow in the intersection region
            arrows++;
            i = j; // Move to next non-overlapping balloon
        }
        
        return arrows;
    }
    
    /**
     * Helper method to visualize the algorithm execution
     */
    public static void traceAlgorithm(int[][] points) {
        System.out.println("Input: " + Arrays.deepToString(points));
        
        if (points.length == 0) {
            System.out.println("Empty input - 0 arrows needed");
            return;
        }
        
        // Sort by end position
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        System.out.println("After sorting by end position: " + Arrays.deepToString(points));
        
        int arrows = 1;
        int arrowPosition = points[0][1];
        System.out.println("Arrow 1 at position " + arrowPosition + 
                          " hits balloon " + Arrays.toString(points[0]));
        
        for (int i = 1; i < points.length; i++) {
            System.out.print("Checking balloon " + Arrays.toString(points[i]) + 
                           " (start=" + points[i][0] + " vs arrow=" + arrowPosition + "): ");
            
            if (points[i][0] > arrowPosition) {
                arrows++;
                arrowPosition = points[i][1];
                System.out.println("Need new arrow " + arrows + " at position " + arrowPosition);
            } else {
                System.out.println("Hit by current arrow");
            }
        }
        
        System.out.println("Total arrows needed: " + arrows);
    }
    
    /**
     * Test method with comprehensive test cases
     */
    public static void main(String[] args) {
        MinimumNumberOfArrowsToBurstBalloons solution = new MinimumNumberOfArrowsToBurstBalloons();
        
        // Test Case 1: Example from problem
        int[][] points1 = {{10,16},{2,8},{1,6},{7,12}};
        System.out.println("Test 1: " + Arrays.deepToString(points1));
        System.out.println("Expected: 2, Got: " + solution.findMinArrowShots(points1));
        System.out.println();
        
        // Test Case 2: No overlaps
        int[][] points2 = {{1,2},{3,4},{5,6},{7,8}};
        System.out.println("Test 2: " + Arrays.deepToString(points2));
        System.out.println("Expected: 4, Got: " + solution.findMinArrowShots(points2));
        System.out.println();
        
        // Test Case 3: Adjacent balloons
        int[][] points3 = {{1,2},{2,3},{3,4},{4,5}};
        System.out.println("Test 3: " + Arrays.deepToString(points3));
        System.out.println("Expected: 2, Got: " + solution.findMinArrowShots(points3));
        System.out.println();
        
        // Test Case 4: All overlapping
        int[][] points4 = {{1,6},{2,5},{3,4}};
        System.out.println("Test 4: " + Arrays.deepToString(points4));
        System.out.println("Expected: 1, Got: " + solution.findMinArrowShots(points4));
        System.out.println();
        
        // Test Case 5: Single balloon
        int[][] points5 = {{1,2}};
        System.out.println("Test 5: " + Arrays.deepToString(points5));
        System.out.println("Expected: 1, Got: " + solution.findMinArrowShots(points5));
        System.out.println();
        
        // Test Case 6: Empty input
        int[][] points6 = {};
        System.out.println("Test 6: " + Arrays.deepToString(points6));
        System.out.println("Expected: 0, Got: " + solution.findMinArrowShots(points6));
        System.out.println();
        
        // Test Case 7: Large coordinates (test integer overflow handling)
        int[][] points7 = {{Integer.MIN_VALUE, -1}, {0, Integer.MAX_VALUE}};
        System.out.println("Test 7: Large coordinates");
        System.out.println("Expected: 2, Got: " + solution.findMinArrowShots(points7));
        System.out.println();
        
        // Test Case 8: Complex overlapping pattern
        int[][] points8 = {{3,9},{7,12},{3,8},{6,8},{9,10},{2,9},{0,9},{3,9},{0,6},{2,8}};
        System.out.println("Test 8: Complex pattern");
        System.out.println("Expected: 2, Got: " + solution.findMinArrowShots(points8));
        System.out.println();
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[][] perfTest = {{1,9},{2,8},{3,7},{4,6},{5,5}};
        
        long start = System.nanoTime();
        int result1 = solution.findMinArrowShots(perfTest);
        long greedy = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = solution.findMinArrowShotsV2(perfTest);
        long explicit = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result3 = solution.findMinArrowShotsIntersection(perfTest);
        long intersection = System.nanoTime() - start;
        
        System.out.println("Greedy: " + result1 + " (Time: " + greedy + " ns)");
        System.out.println("Explicit: " + result2 + " (Time: " + explicit + " ns)");
        System.out.println("Intersection: " + result3 + " (Time: " + intersection + " ns)");
        
        // Detailed trace
        System.out.println("\n=== Algorithm Trace ===");
        traceAlgorithm(points1);
        
        // Demonstration of why sorting choice matters
        demonstrateSortingChoice();
    }
    
    /**
     * Demonstration of why sorting by end works better than sorting by start
     */
    public static void demonstrateSortingChoice() {
        System.out.println("\n=== Why Sort by End Position? ===");
        int[][] example = {{1,4},{2,3},{3,6}};
        
        System.out.println("Example: " + Arrays.deepToString(example));
        
        // Sort by start
        int[][] byStart = Arrays.copyOf(example, example.length);
        Arrays.sort(byStart, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted by start: " + Arrays.deepToString(byStart));
        System.out.println("If we shoot at end of first: position 4");
        System.out.println("- Hits [1,4]: YES (1 <= 4 <= 4)");
        System.out.println("- Hits [2,3]: YES (2 <= 4 <= 3) - NO! 4 > 3");
        System.out.println("This doesn't work optimally!");
        
        System.out.println();
        
        // Sort by end
        int[][] byEnd = Arrays.copyOf(example, example.length);
        Arrays.sort(byEnd, (a, b) -> Integer.compare(a[1], b[1]));
        System.out.println("Sorted by end: " + Arrays.deepToString(byEnd));
        System.out.println("If we shoot at end of first: position 3");
        System.out.println("- Hits [2,3]: YES (2 <= 3 <= 3)");
        System.out.println("- Hits [1,4]: YES (1 <= 3 <= 4)");
        System.out.println("One arrow hits both! Then shoot at 6 for [3,6]");
        System.out.println("This gives optimal solution: 2 arrows");
    }
}