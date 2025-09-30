package leetcode.intervals.problems.InsertInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 57: Insert Interval
 *
 * Given a sorted array of non-overlapping intervals and a new interval,
 * insert the new interval and merge if necessary.
 *
 * Approach: Three-Phase Scan
 * 1. Add intervals ending before newInterval (no overlap)
 * 2. Merge intervals overlapping with newInterval
 * 3. Add remaining intervals after newInterval
 *
 * Time Complexity: O(n) - single pass through intervals
 * Space Complexity: O(n) - for result list
 */
public class InsertInterval {

    /**
     * Inserts newInterval into sorted intervals, merging overlaps.
     *
     * Algorithm:
     * Phase 1: Add all intervals ending before newInterval starts
     * Phase 2: Merge all intervals overlapping with newInterval
     * Phase 3: Add all remaining intervals
     *
     * @param intervals sorted array of non-overlapping intervals
     * @param newInterval interval to insert
     * @return merged intervals after insertion
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Phase 1: Add all intervals that end before newInterval starts
        // No overlap: interval ends before newInterval begins
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2: Merge all overlapping intervals with newInterval
        // Overlap: interval.start <= newInterval.end
        while (i < n && intervals[i][0] <= newInterval[1]) {
            // Expand newInterval to include current interval
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        // Add the merged interval
        result.add(newInterval);

        // Phase 3: Add all remaining intervals (after newInterval)
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    /**
     * Alternative implementation with clearer phase separation.
     * Functionally identical but more explicit about three phases.
     */
    public int[][] insertVerbose(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        // Phase 1: Non-overlapping intervals before newInterval
        int i = 0;
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2: Merge overlapping intervals
        // Track merged boundaries
        int mergedStart = newInterval[0];
        int mergedEnd = newInterval[1];

        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            mergedStart = Math.min(mergedStart, intervals[i][0]);
            mergedEnd = Math.max(mergedEnd, intervals[i][1]);
            // Update newInterval for next iteration
            newInterval[1] = mergedEnd;
            i++;
        }

        result.add(new int[]{mergedStart, mergedEnd});

        // Phase 3: Non-overlapping intervals after newInterval
        while (i < intervals.length) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    // Helper method to print intervals
    private static void printIntervals(int[][] intervals) {
        System.out.print("[");
        for (int i = 0; i < intervals.length; i++) {
            System.out.print("[" + intervals[i][0] + "," + intervals[i][1] + "]");
            if (i < intervals.length - 1) System.out.print(",");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        InsertInterval solution = new InsertInterval();

        // Test Case 1: Basic insertion with merge
        System.out.println("Test 1: Basic merge");
        int[][] test1 = {{1,3},{6,9}};
        int[] new1 = {2,5};
        System.out.print("Intervals: ");
        printIntervals(test1);
        System.out.println("New: [" + new1[0] + "," + new1[1] + "]");
        int[][] result1 = solution.insert(test1, new1);
        System.out.print("Output: ");
        printIntervals(result1);
        System.out.println("Expected: [[1,5],[6,9]]\n");

        // Test Case 2: Multiple overlaps
        System.out.println("Test 2: Multiple overlaps");
        int[][] test2 = {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] new2 = {4,8};
        System.out.print("Intervals: ");
        printIntervals(test2);
        System.out.println("New: [" + new2[0] + "," + new2[1] + "]");
        int[][] result2 = solution.insert(test2, new2);
        System.out.print("Output: ");
        printIntervals(result2);
        System.out.println("Expected: [[1,2],[3,10],[12,16]]\n");

        // Test Case 3: Empty intervals
        System.out.println("Test 3: Empty intervals");
        int[][] test3 = {};
        int[] new3 = {5,7};
        System.out.print("Intervals: ");
        printIntervals(test3);
        System.out.println("New: [" + new3[0] + "," + new3[1] + "]");
        int[][] result3 = solution.insert(test3, new3);
        System.out.print("Output: ");
        printIntervals(result3);
        System.out.println("Expected: [[5,7]]\n");

        // Test Case 4: Insert at beginning
        System.out.println("Test 4: Insert at beginning");
        int[][] test4 = {{3,5},{6,9}};
        int[] new4 = {1,2};
        System.out.print("Intervals: ");
        printIntervals(test4);
        System.out.println("New: [" + new4[0] + "," + new4[1] + "]");
        int[][] result4 = solution.insert(test4, new4);
        System.out.print("Output: ");
        printIntervals(result4);
        System.out.println("Expected: [[1,2],[3,5],[6,9]]\n");

        // Test Case 5: Insert at end
        System.out.println("Test 5: Insert at end");
        int[][] test5 = {{1,2},{3,5}};
        int[] new5 = {6,8};
        System.out.print("Intervals: ");
        printIntervals(test5);
        System.out.println("New: [" + new5[0] + "," + new5[1] + "]");
        int[][] result5 = solution.insert(test5, new5);
        System.out.print("Output: ");
        printIntervals(result5);
        System.out.println("Expected: [[1,2],[3,5],[6,8]]\n");

        // Test Case 6: Completely contained
        System.out.println("Test 6: New interval contained");
        int[][] test6 = {{1,5}};
        int[] new6 = {2,3};
        System.out.print("Intervals: ");
        printIntervals(test6);
        System.out.println("New: [" + new6[0] + "," + new6[1] + "]");
        int[][] result6 = solution.insert(test6, new6);
        System.out.print("Output: ");
        printIntervals(result6);
        System.out.println("Expected: [[1,5]]\n");

        // Test Case 7: New interval contains all
        System.out.println("Test 7: New interval contains all");
        int[][] test7 = {{2,3},{4,5},{6,7}};
        int[] new7 = {1,10};
        System.out.print("Intervals: ");
        printIntervals(test7);
        System.out.println("New: [" + new7[0] + "," + new7[1] + "]");
        int[][] result7 = solution.insert(test7, new7);
        System.out.print("Output: ");
        printIntervals(result7);
        System.out.println("Expected: [[1,10]]\n");

        // Test Case 8: Touching intervals (edge case)
        System.out.println("Test 8: Touching intervals");
        int[][] test8 = {{1,3},{6,9}};
        int[] new8 = {3,6};
        System.out.print("Intervals: ");
        printIntervals(test8);
        System.out.println("New: [" + new8[0] + "," + new8[1] + "]");
        int[][] result8 = solution.insert(test8, new8);
        System.out.print("Output: ");
        printIntervals(result8);
        System.out.println("Expected: [[1,9]]\n");

        // Test Case 9: Single existing interval, no overlap before
        System.out.println("Test 9: No overlap, insert before");
        int[][] test9 = {{5,7}};
        int[] new9 = {1,2};
        System.out.print("Intervals: ");
        printIntervals(test9);
        System.out.println("New: [" + new9[0] + "," + new9[1] + "]");
        int[][] result9 = solution.insert(test9, new9);
        System.out.print("Output: ");
        printIntervals(result9);
        System.out.println("Expected: [[1,2],[5,7]]\n");

        // Test Case 10: Single existing interval, no overlap after
        System.out.println("Test 10: No overlap, insert after");
        int[][] test10 = {{1,2}};
        int[] new10 = {5,7};
        System.out.print("Intervals: ");
        printIntervals(test10);
        System.out.println("New: [" + new10[0] + "," + new10[1] + "]");
        int[][] result10 = solution.insert(test10, new10);
        System.out.print("Output: ");
        printIntervals(result10);
        System.out.println("Expected: [[1,2],[5,7]]\n");

        // Test verbose version
        System.out.println("Test 11: Verbose implementation");
        int[][] test11 = {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] new11 = {4,8};
        System.out.print("Intervals: ");
        printIntervals(test11);
        System.out.println("New: [" + new11[0] + "," + new11[1] + "]");
        int[][] result11 = solution.insertVerbose(test11, new11);
        System.out.print("Output: ");
        printIntervals(result11);
        System.out.println("Expected: [[1,2],[3,10],[12,16]]");
    }
}