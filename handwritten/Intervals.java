import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.util.ArrayList;
public class Intervals {

    public int[][] mergeIntervals(int[][] intervals) {
        System.out.println(Arrays.deepToString(intervals));
        if (intervals.length <= 1) return intervals;

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> result = new ArrayList<>();

        int[] current = intervals[0];
        for (int i = 1; i < intervals.length; i++ ) {
            if (current[1] > intervals[i][0]) {
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                result.add(current);
                current = intervals[i];
            }
        }
        result.add(current);
        return result.toArray(new int[result.size()][]);

    }

    



    public static void main(String[] args) {

        int[][] intervals = {
            {3, 8}, {0, 3}, {2, 5}, {9, 16} 
        };

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        System.out.println(Arrays.deepToString(intervals));

        Intervals intervalsObject = new Intervals();
        var result = intervalsObject.mergeIntervals(intervals);
        System.out.println("Result of merging: \n" + Arrays.deepToString(result));
        
    }
}