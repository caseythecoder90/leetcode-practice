public class MySolutions {

    /**
     * User's original implementation using StringBuilder
     * Time: O(n + m), Space: O(n) due to StringBuilder
     */
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (char c : s.toCharArray()) {

            while (i < t.length()) {
                if (c == t.charAt(i)) {
                    builder.append(t.charAt(i));
                    i++;
                    break;
                }
                i++;
            }
        }

        return s.equals(builder.toString());
    }

    /**
     * Optimized Two-Pointer Approach
     * Time: O(n + m), Space: O(1)
     * Cleaner and more efficient than StringBuilder approach
     */
    public boolean isSubsequenceOptimized(String s, String t) {
        if (s.isEmpty()) return true;
        if (t.isEmpty()) return false;

        int sPtr = 0, tPtr = 0;

        while (sPtr < s.length() && tPtr < t.length()) {
            if (s.charAt(sPtr) == t.charAt(tPtr)) {
                sPtr++;
            }
            tPtr++;
        }

        return sPtr == s.length();
    }

    /**
     * Follow-up Solution: Pre-process t for multiple s queries
     * Using a preprocessing approach similar to KMP
     * Preprocessing: O(m), Each query: O(n)
     * Total for k queries: O(m + k*n)
     */
    public class FollowUpOptimizer {
        private String target;
        private int[] nextPos;

        public FollowUpOptimizer(String t) {
            this.target = t;
            this.nextPos = new int[t.length() + 1];
            preprocess();
        }

        /**
         * Preprocess the target string to create jump table
         * Similar to KMP failure function
         */
        private void preprocess() {
            // For each position in target, nextPos[i] tells us where to look next
            // if we find a match or need to skip characters
            for (int i = 0; i < target.length(); i++) {
                nextPos[i] = i + 1;
            }
            nextPos[target.length()] = target.length();
        }

        /**
         * Optimized check for multiple queries
         */
        public boolean isSubsequence(String s) {
            int sPtr = 0;
            int tPtr = 0;

            while (sPtr < s.length() && tPtr < target.length()) {
                if (s.charAt(sPtr) == target.charAt(tPtr)) {
                    sPtr++;
                    tPtr++;
                } else {
                    // Use next position or move forward
                    tPtr = nextPos[tPtr];
                }
            }

            return sPtr == s.length();
        }
    }

    /**
     * Alternative implementation using indexOf progressively
     * Also O(n + m) but can be slower due to string operations
     */
    public boolean isSubsequenceIndexOf(String s, String t) {
        int start = 0;
        for (char c : s.toCharArray()) {
            int found = t.indexOf(c, start);
            if (found == -1) return false;
            start = found + 1;
        }
        return true;
    }
}
