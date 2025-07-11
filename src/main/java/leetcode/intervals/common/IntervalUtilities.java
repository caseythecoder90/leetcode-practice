package leetcode.intervals.common;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Common utilities and helper methods for interval problems
 * 
 * This class provides reusable components for interval-based algorithms
 * including overlap detection, sorting strategies, and common patterns.
 */
public class IntervalUtilities {
    
    /**
     * Check if two intervals overlap
     * 
     * @param a First interval [start, end]
     * @param b Second interval [start, end]
     * @return true if intervals overlap, false otherwise
     */
    public static boolean overlaps(int[] a, int[] b) {
        return a[0] < b[1] && b[0] < a[1];
    }
    
    /**
     * Alternative overlap check using negation of non-overlapping condition
     */
    public static boolean overlapsAlt(int[] a, int[] b) {
        return !(a[1] <= b[0] || b[1] <= a[0]);
    }
    
    /**
     * Check if two intervals are adjacent (touching but not overlapping)
     */
    public static boolean adjacent(int[] a, int[] b) {
        return a[1] == b[0] || b[1] == a[0];
    }
    
    /**
     * Merge two overlapping intervals
     * Assumes intervals are overlapping (caller should check first)
     */
    public static int[] merge(int[] a, int[] b) {
        return new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
    }
    
    /**
     * Find intersection of two intervals
     * Returns null if intervals don't overlap
     */
    public static int[] intersection(int[] a, int[] b) {
        if (!overlaps(a, b)) {
            return null;
        }
        return new int[]{Math.max(a[0], b[0]), Math.min(a[1], b[1])};
    }
    
    /**
     * Common comparators for sorting intervals
     */
    public static class Comparators {
        
        /**
         * Sort by start time (ascending)
         * Use for: merge intervals, insert interval
         */
        public static final Comparator<int[]> BY_START = 
            (a, b) -> Integer.compare(a[0], b[0]);
        
        /**
         * Sort by end time (ascending)
         * Use for: activity selection, non-overlapping intervals, point coverage
         */
        public static final Comparator<int[]> BY_END = 
            (a, b) -> Integer.compare(a[1], b[1]);
        
        /**
         * Sort by start time, then by end time
         * Use for: complex interval problems requiring stable ordering
         */
        public static final Comparator<int[]> BY_START_THEN_END = (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        };
        
        /**
         * Sort by end time, then by start time
         * Use for: activity selection with tie-breaking
         */
        public static final Comparator<int[]> BY_END_THEN_START = (a, b) -> {
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            return Integer.compare(a[0], b[0]);
        };
        
        /**
         * Sort by interval length (ascending)
         * Use for: problems where shorter intervals should be processed first
         */
        public static final Comparator<int[]> BY_LENGTH = 
            (a, b) -> Integer.compare(a[1] - a[0], b[1] - b[0]);
    }
    
    /**
     * Validate interval array input
     */
    public static boolean isValidInterval(int[] interval) {
        return interval != null && interval.length == 2 && interval[0] <= interval[1];
    }
    
    /**
     * Validate array of intervals
     */
    public static boolean isValidIntervals(int[][] intervals) {
        if (intervals == null) return false;
        
        for (int[] interval : intervals) {
            if (!isValidInterval(interval)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Convert intervals to string for debugging
     */
    public static String intervalsToString(int[][] intervals) {
        if (intervals == null) return "null";
        if (intervals.length == 0) return "[]";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < intervals.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append("[").append(intervals[i][0]).append(",").append(intervals[i][1]).append("]");
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Calculate total coverage (union) of all intervals
     * Useful for analyzing interval problems
     */
    public static int totalCoverage(int[][] intervals) {
        if (intervals.length == 0) return 0;
        
        // Sort by start time and merge
        Arrays.sort(intervals, Comparators.BY_START);
        
        int totalCoverage = 0;
        int currentStart = intervals[0][0];
        int currentEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= currentEnd) {
                // Overlapping - extend current interval
                currentEnd = Math.max(currentEnd, intervals[i][1]);
            } else {
                // Non-overlapping - add current interval length and start new
                totalCoverage += currentEnd - currentStart;
                currentStart = intervals[i][0];
                currentEnd = intervals[i][1];
            }
        }
        
        // Add the last interval
        totalCoverage += currentEnd - currentStart;
        return totalCoverage;
    }
    
    /**
     * Find the maximum number of non-overlapping intervals
     * Uses greedy activity selection algorithm
     */
    public static int maxNonOverlapping(int[][] intervals) {
        if (intervals.length == 0) return 0;
        
        Arrays.sort(intervals, Comparators.BY_END);
        
        int count = 1; // Always select first interval
        int lastEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= lastEnd) {
                count++;
                lastEnd = intervals[i][1];
            }
        }
        
        return count;
    }
    
    /**
     * Find minimum points needed to cover all intervals
     * Each point must be within at least one interval
     */
    public static int minPointsToCover(int[][] intervals) {
        if (intervals.length == 0) return 0;
        
        Arrays.sort(intervals, Comparators.BY_END);
        
        int points = 1;
        int lastPoint = intervals[0][1]; // Place first point at end of first interval
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > lastPoint) {
                // Current interval not covered by last point
                points++;
                lastPoint = intervals[i][1];
            }
        }
        
        return points;
    }
    
    /**
     * Demo method showing common interval operations
     */
    public static void main(String[] args) {
        System.out.println("=== Interval Utilities Demo ===\n");
        
        // Test intervals
        int[][] testIntervals = {{1,3}, {2,4}, {5,7}, {6,8}};
        System.out.println("Test intervals: " + intervalsToString(testIntervals));
        
        // Overlap detection
        System.out.println("\n--- Overlap Detection ---");
        System.out.println("[1,3] overlaps [2,4]: " + overlaps(new int[]{1,3}, new int[]{2,4}));
        System.out.println("[1,3] overlaps [4,5]: " + overlaps(new int[]{1,3}, new int[]{4,5}));
        System.out.println("[1,3] adjacent [3,5]: " + adjacent(new int[]{1,3}, new int[]{3,5}));
        
        // Merge and intersection
        System.out.println("\n--- Merge and Intersection ---");
        int[] merged = merge(new int[]{1,3}, new int[]{2,4});
        System.out.println("Merge [1,3] and [2,4]: " + Arrays.toString(merged));
        
        int[] intersect = intersection(new int[]{1,4}, new int[]{2,3});
        System.out.println("Intersection [1,4] and [2,3]: " + Arrays.toString(intersect));
        
        // Analysis
        System.out.println("\n--- Analysis ---");
        System.out.println("Total coverage: " + totalCoverage(testIntervals));
        System.out.println("Max non-overlapping: " + maxNonOverlapping(testIntervals));
        System.out.println("Min points to cover: " + minPointsToCover(testIntervals));
        
        // Sorting demonstration
        System.out.println("\n--- Sorting Demonstration ---");
        int[][] sortTest = {{3,6}, {1,4}, {2,5}};
        System.out.println("Original: " + intervalsToString(sortTest));
        
        Arrays.sort(sortTest, Comparators.BY_START);
        System.out.println("By start: " + intervalsToString(sortTest));
        
        Arrays.sort(sortTest, Comparators.BY_END);
        System.out.println("By end: " + intervalsToString(sortTest));
        
        Arrays.sort(sortTest, Comparators.BY_LENGTH);
        System.out.println("By length: " + intervalsToString(sortTest));
    }
}