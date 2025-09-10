public class ValidPalindrome {

    /**
     * Determines if string s is a valid palindrome after converting to lowercase
     * and removing all non-alphanumeric characters.
     *
     * Uses two-pointer approach to compare characters from both ends.
     *
     * Time Complexity: O(n) where n = s.length()
     * Space Complexity: O(1) additional space
     *
     * @param s the input string to check
     * @return true if s is a valid palindrome, false otherwise
     */
    public boolean isPalindrome(String s) {
        if (s == null) return false;

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !isAlphanumeric(s.charAt(left))) {
                left++;
            }

            // Skip non-alphanumeric characters from right
            while (left < right && !isAlphanumeric(s.charAt(right))) {
                right--;
            }

            // Compare characters (case-insensitive)
            if (left < right) {
                char leftChar = Character.toLowerCase(s.charAt(left));
                char rightChar = Character.toLowerCase(s.charAt(right));

                if (leftChar != rightChar) {
                    return false;
                }

                left++;
                right--;
            }
        }

        return true;
    }

    /**
     * Helper method to check if a character is alphanumeric
     * @param c the character to check
     * @return true if alphanumeric, false otherwise
     */
    private boolean isAlphanumeric(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               (c >= '0' && c <= '9');
    }
}
