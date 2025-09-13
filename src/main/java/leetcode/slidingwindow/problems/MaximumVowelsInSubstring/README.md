# 1456. Maximum Number of Vowels in a Substring of Given Length

## Problem Description
Given a string `s` and an integer `k`, return the maximum number of vowel letters in any substring of `s` with length `k`.

Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.

### Examples

**Example 1:**
- Input: `s = "abciiidef"`, `k = 3`
- Output: `3`
- Explanation: The substring "iii" contains 3 vowel letters.

**Example 2:**
- Input: `s = "aeiou"`, `k = 2`
- Output: `2`
- Explanation: Any substring of length 2 contains 2 vowels.

**Example 3:**
- Input: `s = "leetcode"`, `k = 3`
- Output: `2`
- Explanation: "lee", "eet" and "ode" contain 2 vowels.

### Constraints
- `1 <= s.length <= 10^5`
- `s` consists of lowercase English letters
- `1 <= k <= s.length`

## Solution Approach

### Pattern: Fixed-Size Sliding Window

This is a classic **fixed-size sliding window** problem where we need to:
1. Initialize a window of size `k`
2. Slide the window one position at a time
3. Track the maximum value seen across all windows

### Algorithm Steps

1. **Initialize the first window (0 to k-1)**
   - Count vowels in the first `k` characters
   - This becomes our initial maximum

2. **Slide the window**
   - For each new position:
     - Add the new character entering the window (right side)
     - Remove the character leaving the window (left side)
     - Update the maximum if current count is higher

3. **Optimization Opportunities**
   - Use a Set for O(1) vowel lookups
   - Early termination if we find a window with all vowels (k vowels)
   - Avoid recounting - just update based on characters entering/leaving

### Time & Space Complexity

- **Time Complexity:** O(n) where n is the length of string
  - We visit each character exactly once
  - Set lookups are O(1)

- **Space Complexity:** O(1)
  - The vowel set is constant size (5 elements)
  - We only use a few integer variables

## Interview Tips

1. **Clarifying Questions:**
   - Are we only considering lowercase vowels? (Yes, per constraints)
   - What if k > s.length? (Not possible per constraints)
   - Can k be 0? (No, k >= 1 per constraints)

2. **Edge Cases to Consider:**
   - String with all vowels
   - String with no vowels
   - k equals string length
   - k = 1

3. **Common Mistakes:**
   - Forgetting to handle the character leaving the window
   - Off-by-one errors with window boundaries
   - Not initializing the first window properly

4. **Follow-up Questions:**
   - What if we need to find the actual substring? (Track start index)
   - What if vowels had different weights? (Use a map instead of set)
   - What if we wanted minimum instead of maximum? (Similar approach)

## Key Insights

This problem demonstrates the power of the sliding window technique for substring problems with fixed-size constraints. The key optimization is recognizing that we don't need to recount all characters in each window - we can maintain a running count by adding/removing one character at a time.

The pattern here is reusable for many similar problems:
- Maximum sum of subarray of size k
- Minimum/maximum of any property in fixed-size windows
- Finding patterns in fixed-size substrings