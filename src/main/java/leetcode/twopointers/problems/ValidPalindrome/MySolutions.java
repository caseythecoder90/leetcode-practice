import java.util.regex.Pattern;

public class MySolutions {

    /**
     * Solution 1: Two-pointer with built-in Character methods
     * Cleaner than manual char checking but slightly slower
     */
    public boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) return true;

        int left = 0, right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters using Character.isLetterOrDigit
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters (case-insensitive)
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) !=
                    Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    /**
     * Solution 2: Create clean string first, then check palindrome
     * Uses StringBuilder and regular expressions
     * Time: O(n), Space: O(n) for StringBuilder
     */
    public boolean isPalindromeWithCleanString(String s) {
        if (s == null) return false;

        // Remove non-alphanumeric characters and convert to lowercase
        String clean = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Check if palindrome
        int left = 0, right = clean.length() - 1;
        while (left < right) {
            if (clean.charAt(left) != clean.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Solution 3: Using Pattern and Matcher (overkill but shows regex)
     * Less efficient but demonstrates regular expression usage
     */
    public boolean isPalindromeRegex(String s) {
        if (s == null) return false;

        // Compile pattern once for better performance
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        String clean = pattern.matcher(s).replaceAll("").toLowerCase();

        return isStringPalindrome(clean);
    }

    /**
     * Solution 4: Two-pointer without any helper methods
     * Inline character checking for minimal overhead
     */
    public boolean isPalindromeInline(String s) {
        if (s == null) return false;

        int left = 0, right = s.length() - 1;

        while (left < right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);

            // Skip if not alphanumeric
            while (left < right &&
                   !((leftChar >= 'A' && leftChar <= 'Z') ||
                     (leftChar >= 'a' && leftChar <= 'z') ||
                     (leftChar >= '0' && leftChar <= '9'))) {
                left++;
                leftChar = s.charAt(left);
            }

            while (left < right &&
                   !((rightChar >= 'A' && rightChar <= 'Z') ||
                     (rightChar >= 'a' && rightChar <= 'z') ||
                     (rightChar >= '0' && rightChar <= '9'))) {
                right--;
                rightChar = s.charAt(right);
            }

            // Convert to lowercase and compare
            if (left < right) {
                leftChar = (leftChar >= 'A' && leftChar <= 'Z') ? (char)(leftChar + 32) : leftChar;
                rightChar = (rightChar >= 'A' && rightChar <= 'Z') ? (char)(rightChar + 32) : rightChar;

                if (leftChar != rightChar) return false;
                left++;
                right--;
            }
        }
        return true;
    }

    /**
     * Solution 5: Stack-based approach (educational, not efficient)
     * Shows alternative thinking but O(n) space complexity
     */
    public boolean isPalindromeWithStack(String s) {
        if (s == null) return false;

        java.util.Stack<Character> stack = new java.util.Stack<>();
        java.util.Queue<Character> queue = new java.util.LinkedList<>();

        // Filter and store characters
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                char lower = Character.toLowerCase(c);
                stack.push(lower);
                queue.offer(lower);
            }
        }

        // Compare stack and queue (last in first out vs first in first out)
        while (!stack.isEmpty()) {
            if (!stack.pop().equals(queue.poll())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Utility method: Check if a string is a palindrome
     */
    private boolean isStringPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}
