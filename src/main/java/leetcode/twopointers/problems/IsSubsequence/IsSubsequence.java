public class IsSubsequence {

    /**
     * Determines if string s is a subsequence of string t.
     *
     * A subsequence is formed by deleting some (can be none) of the characters
     * without disturbing the relative positions of the remaining characters.
     *
     * Time Complexity: O(n + m) where n = s.length(), m = t.length()
     * Space Complexity: O(1) additional space (excluding input strings)
     *
     * @param s the potential subsequence string
     * @param t the target string
     * @return true if s is a subsequence of t, false otherwise
     */
    public boolean isSubsequence(String s, String t) {
        if (s.isEmpty()) return true;
        if (t.isEmpty()) return false;

        int sIndex = 0;
        int tIndex = 0;

        // Two-pointer approach
        while (sIndex < s.length() && tIndex < t.length()) {
            if (s.charAt(sIndex) == t.charAt(tIndex)) {
                sIndex++;
            }
            tIndex++;
        }

        return sIndex == s.length();
    }
}
