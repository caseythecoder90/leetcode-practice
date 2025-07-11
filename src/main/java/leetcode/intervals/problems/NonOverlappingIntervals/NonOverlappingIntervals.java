package leetcode.intervals.problems.NonOverlappingIntervals;

import java.util.Arrays;

/**
 * LeetCode 435: Non-overlapping Intervals
 * 
 * Pattern: Greedy Activity Selection
 * Key Insight: Sort by end time, greedily select intervals that end earliest
 * 
 * Time: O(n log n) - sorting dominates
 * Space: O(1) - constant extra space
 */
public class NonOverlappingIntervals {
    
    /**
     * Optimal Solution: Greedy Activity Selection
     * 
     * Strategy:
     * 1. Sort intervals by end time
     * 2. Greedily select non-overlapping intervals (keep maximum count)
     * 3. Return total - kept = minimum removals
     * 
     * Why sort by end time?
     * - Choosing intervals that end earliest leaves maximum room for future intervals
     * - This greedy choice leads to optimal solution
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1) {
            return 0;
        }
        
        // Sort by end time - key insight for greedy selection
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        
        int kept = 1; // Always keep the first interval (ends earliest)
        int lastEnd = intervals[0][1];
        
        // Greedily select non-overlapping intervals
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= lastEnd) {
                // No overlap - keep this interval
                kept++;
                lastEnd = intervals[i][1];
            }
            // If overlap, skip this interval (implicit removal)
        }
        
        return intervals.length - kept;
    }
    
    /**
     * Alternative Implementation: Direct Counting of Removals
     * Same logic, but counts removals directly instead of counting kept intervals
     */
    public int eraseOverlapIntervalsV2(int[][] intervals) {
        if (intervals.length <= 1) {
            return 0;
        }
        
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        
        int removals = 0;
        int lastEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < lastEnd) {
                // Overlap detected - remove current interval
                removals++;
            } else {
                // No overlap - update lastEnd
                lastEnd = intervals[i][1];
            }
        }
        
        return removals;
    }
    
    /**
     * Educational: Brute Force Approach (NOT RECOMMENDED)
     * Time: O(2^n) - exponential
     * 
     * This approach tries all possible combinations of intervals to remove.
     * Included only for educational purposes to show why greedy is better.
     */
    public int eraseOverlapIntervalsBruteForce(int[][] intervals) {
        return bruteForceHelper(intervals, 0, Integer.MIN_VALUE);
    }
    
    private int bruteForceHelper(int[][] intervals, int index, int lastEnd) {
        if (index >= intervals.length) {
            return 0;
        }
        
        // Option 1: Remove current interval
        int remove = 1 + bruteForceHelper(intervals, index + 1, lastEnd);
        
        // Option 2: Keep current interval (if no overlap)
        int keep = Integer.MAX_VALUE;
        if (intervals[index][0] >= lastEnd) {
            keep = bruteForceHelper(intervals, index + 1, intervals[index][1]);
        }
        
        return Math.min(remove, keep);
    }
    
    /**
     * Helper method to check if two intervals overlap
     */
    private boolean overlaps(int[] a, int[] b) {
        return a[0] < b[1] && b[0] < a[1];
    }
    
    /**
     * Test method with comprehensive test cases
     */
    public static void main(String[] args) {
        NonOverlappingIntervals solution = new NonOverlappingIntervals();
        
        // Test Case 1: Example from problem
        int[][] intervals1 = {{1,2},{2,3},{3,4},{1,3}};
        System.out.println("Test 1: " + Arrays.deepToString(intervals1));
        System.out.println("Expected: 1, Got: " + solution.eraseOverlapIntervals(intervals1));
        System.out.println();
        
        // Test Case 2: All same intervals
        int[][] intervals2 = {{1,2},{1,2},{1,2}};
        System.out.println("Test 2: " + Arrays.deepToString(intervals2));
        System.out.println("Expected: 2, Got: " + solution.eraseOverlapIntervals(intervals2));
        System.out.println();
        
        // Test Case 3: No overlaps
        int[][] intervals3 = {{1,2},{2,3}};
        System.out.println("Test 3: " + Arrays.deepToString(intervals3));
        System.out.println("Expected: 0, Got: " + solution.eraseOverlapIntervals(intervals3));
        System.out.println();
        
        // Test Case 4: Single interval
        int[][] intervals4 = {{1,2}};
        System.out.println("Test 4: " + Arrays.deepToString(intervals4));
        System.out.println("Expected: 0, Got: " + solution.eraseOverlapIntervals(intervals4));
        System.out.println();
        
        // Test Case 5: Complete overlap
        int[][] intervals5 = {{1,4},{2,3},{3,4}};
        System.out.println("Test 5: " + Arrays.deepToString(intervals5));
        System.out.println("Expected: 1, Got: " + solution.eraseOverlapIntervals(intervals5));
        System.out.println();
        
        // Test Case 6: Complex overlapping pattern
        int[][] intervals6 = {{1,3},{2,4},{3,5},{4,6}};
        System.out.println("Test 6: " + Arrays.deepToString(intervals6));
        System.out.println("Expected: 1, Got: " + solution.eraseOverlapIntervals(intervals6));
        System.out.println();
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[][] largeTest = {{1,100},{2,50},{3,25},{4,12},{5,6}};
        
        long start = System.nanoTime();
        int result1 = solution.eraseOverlapIntervals(largeTest);
        long greedy = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = solution.eraseOverlapIntervalsV2(largeTest);
        long greedyV2 = System.nanoTime() - start;
        
        System.out.println("Greedy V1: " + result1 + " (Time: " + greedy + " ns)");
        System.out.println("Greedy V2: " + result2 + " (Time: " + greedyV2 + " ns)");
        
        // Trace through the algorithm step by step
        System.out.println("\n=== Algorithm Trace ===");
        traceAlgorithm(intervals1);
    }
    
    /**
     * Educational method to trace through the algorithm step by step
     */
    public static void traceAlgorithm(int[][] intervals) {
        System.out.println("Input: " + Arrays.deepToString(intervals));
        
        // Sort by end time
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        System.out.println("After sorting by end time: " + Arrays.deepToString(intervals));
        
        int kept = 1;
        int lastEnd = intervals[0][1];
        System.out.println("Keep first interval: " + Arrays.toString(intervals[0]) + 
                          ", lastEnd = " + lastEnd);
        
        for (int i = 1; i < intervals.length; i++) {
            System.out.print("Checking " + Arrays.toString(intervals[i]) + 
                           " (start=" + intervals[i][0] + " vs lastEnd=" + lastEnd + "): ");
            
            if (intervals[i][0] >= lastEnd) {
                kept++;
                lastEnd = intervals[i][1];
                System.out.println("Keep (no overlap), lastEnd = " + lastEnd);
            } else {
                System.out.println("Skip (overlaps)");
            }
        }
        
        System.out.println("Total kept: " + kept + ", Removals: " + (intervals.length - kept));
    }
}