package leetcode.intervals.problems.MergeIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 56: Merge Intervals
 *
 * Given an array of intervals where intervals[i] = [starti, endi],
 * merge all overlapping intervals and return an array of the
 * non-overlapping intervals that cover all the intervals in the input.
 *
 * Approach: Sort + Merge
 * 1. Sort intervals by start time
 * 2. Iterate and merge overlapping intervals
 * 3. Two intervals overlap if: currentStart <= lastEnd
 *
 * Time Complexity: O(n log n) - dominated by sorting
 * Space Complexity: O(n) - for result list
 */
public class MergeIntervals {

    /**
     * Merges all overlapping intervals.
     *
     * Algorithm:
     * 1. Sort by start time: O(n log n)
     * 2. Initialize result with first interval
     * 3. For each remaining interval:
     *    - If overlaps with last merged: extend end time
     *    - Otherwise: add as new interval
     *
     * @param intervals array of intervals [start, end]
     * @return array of merged non-overlapping intervals
     */
    public int[][] merge(int[][] intervals) {
        // Edge case: empty or single interval
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Step 1: Sort by start time
        // Lambda: compare first element (start time) of each interval
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Initialize result list with first interval
        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);

        // Step 3: Iterate through remaining intervals
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] lastMerged = merged.get(merged.size() - 1);

            // Check if current overlaps with last merged interval
            // After sorting: lastStart <= currentStart (guaranteed)
            // So we only check: currentStart <= lastEnd
            if (current[0] <= lastMerged[1]) {
                // Overlapping: merge by extending end time
                // Use Math.max because current might be contained in lastMerged
                lastMerged[1] = Math.max(lastMerged[1], current[1]);
            } else {
                // Not overlapping: add as new interval
                merged.add(current);
            }
        }

        // Step 4: Convert list to array
        return merged.toArray(new int[merged.size()][]);
    }

    /**
     * Alternative implementation: In-place merging (modifies input)
     * Saves space but destroys original array.
     *
     * @param intervals array of intervals to merge
     * @return merged intervals (reuses input array)
     */
    public int[][] mergeInPlace(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Index of last valid merged interval
        int index = 0;

        for (int i = 1; i < intervals.length; i++) {
            // Check overlap with last valid interval
            if (intervals[i][0] <= intervals[index][1]) {
                // Merge: extend end time
                intervals[index][1] = Math.max(intervals[index][1], intervals[i][1]);
            } else {
                // No overlap: move to next position
                index++;
                intervals[index] = intervals[i];
            }
        }

        // Return subarray containing only valid intervals
        return Arrays.copyOf(intervals, index + 1);
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
        MergeIntervals solution = new MergeIntervals();

        // Test Case 1: Basic overlapping intervals
        System.out.println("Test 1: Basic overlapping");
        int[][] test1 = {{1,3},{2,6},{8,10},{15,18}};
        System.out.print("Input:  ");
        printIntervals(test1);
        int[][] result1 = solution.merge(test1);
        System.out.print("Output: ");
        printIntervals(result1);
        System.out.println("Expected: [[1,6],[8,10],[15,18]]\n");

        // Test Case 2: Touching intervals (edge case)
        System.out.println("Test 2: Touching intervals");
        int[][] test2 = {{1,4},{4,5}};
        System.out.print("Input:  ");
        printIntervals(test2);
        int[][] result2 = solution.merge(test2);
        System.out.print("Output: ");
        printIntervals(result2);
        System.out.println("Expected: [[1,5]]\n");

        // Test Case 3: No overlaps
        System.out.println("Test 3: No overlaps");
        int[][] test3 = {{1,2},{3,4},{5,6}};
        System.out.print("Input:  ");
        printIntervals(test3);
        int[][] result3 = solution.merge(test3);
        System.out.print("Output: ");
        printIntervals(result3);
        System.out.println("Expected: [[1,2],[3,4],[5,6]]\n");

        // Test Case 4: All intervals merge into one
        System.out.println("Test 4: All merge into one");
        int[][] test4 = {{1,4},{2,5},{3,6},{4,7}};
        System.out.print("Input:  ");
        printIntervals(test4);
        int[][] result4 = solution.merge(test4);
        System.out.print("Output: ");
        printIntervals(result4);
        System.out.println("Expected: [[1,7]]\n");

        // Test Case 5: Contained intervals
        System.out.println("Test 5: Contained intervals");
        int[][] test5 = {{1,10},{2,5},{6,8}};
        System.out.print("Input:  ");
        printIntervals(test5);
        int[][] result5 = solution.merge(test5);
        System.out.print("Output: ");
        printIntervals(result5);
        System.out.println("Expected: [[1,10]]\n");

        // Test Case 6: Unsorted input
        System.out.println("Test 6: Unsorted input");
        int[][] test6 = {{8,10},{1,3},{2,6},{15,18}};
        System.out.print("Input:  ");
        printIntervals(test6);
        int[][] result6 = solution.merge(test6);
        System.out.print("Output: ");
        printIntervals(result6);
        System.out.println("Expected: [[1,6],[8,10],[15,18]]\n");

        // Test Case 7: Single interval
        System.out.println("Test 7: Single interval");
        int[][] test7 = {{1,5}};
        System.out.print("Input:  ");
        printIntervals(test7);
        int[][] result7 = solution.merge(test7);
        System.out.print("Output: ");
        printIntervals(result7);
        System.out.println("Expected: [[1,5]]\n");

        // Test Case 8: Identical intervals
        System.out.println("Test 8: Identical intervals");
        int[][] test8 = {{1,4},{1,4}};
        System.out.print("Input:  ");
        printIntervals(test8);
        int[][] result8 = solution.merge(test8);
        System.out.print("Output: ");
        printIntervals(result8);
        System.out.println("Expected: [[1,4]]\n");

        // Test in-place version
        System.out.println("Test 9: In-place merge");
        int[][] test9 = {{1,3},{2,6},{8,10},{15,18}};
        System.out.print("Input:  ");
        printIntervals(test9);
        int[][] result9 = solution.mergeInPlace(test9);
        System.out.print("Output: ");
        printIntervals(result9);
        System.out.println("Expected: [[1,6],[8,10],[15,18]]");
    }
}