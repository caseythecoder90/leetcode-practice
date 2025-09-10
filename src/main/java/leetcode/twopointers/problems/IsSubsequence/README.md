# 392. Is Subsequence

## Problem Description

Given two strings `s` and `t`, return `true` if `s` is a subsequence of `t`, or `false` otherwise.

A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).

## Examples

### Example 1:
```
Input: s = "abc", t = "ahbgdc"
Output: true
Explanation: "abc" appears in "ahbgdc" as "a_b_gdc" with characters deleted (h,d,c)
```

### Example 2:
```
Input: s = "axc", t = "ahbgdc"
Output: false
Explanation: "a_x_c" doesn't appear in order because t doesn't have 'x'
```

### Example 3:
```
Input: s = "", t = "anything"
Output: true
Explanation: Empty string is always a subsequence
```

### Example 4:
```
Input: s = "a", t = ""
Output: false
Explanation: Non-empty string cannot be subsequence of empty string
```

## Constraints

- `0 <= s.length <= 100`
- `0 <= t.length <= 10⁴`
- `s` and `t` consist only of lowercase English letters.

## Approach Analysis

### Brute Force (Not Recommended)
- Time: O(2^m * n) where m = t.length()
- This would involve trying all possible subsequences of t and checking if any matches s
- Extremely inefficient

### Two-Pointer Approach (Optimal)
- Use two pointers, one for each string
- Increment both when characters match, only increment t's pointer when they don't
- Time: O(n + m), Space: O(1)
- This is the standard and most efficient approach

### Follow-up: Multiple Queries (k ≥ 10⁹)
When dealing with many incoming s strings that need to be checked against the same t:

#### Option 1: Pre-processing with positions
- Store positions of each character in t during preprocessing
- Use binary search for each character in s to find next valid position
- Preprocessing: O(m log m), Each query: O(n log m)

#### Option 2: Advanced Preprocessing (like KMP)
- Create a "next position" array for faster traversal
- Allows jumping over characters when possible
- Preprocessing: O(m), Each query: O(n + m) worst case but faster in practice

## Common Mistakes

1. **Not handling empty strings correctly**
   - Empty s should always return true
   - Empty t with non-empty s should return false

2. **Incorrect loop conditions**
   - Must advance t pointer even when characters don't match
   - Must check s pointer reaches s.length(), not t pointer reaches t.length()

3. **Inefficient implementations**
   - Building strings unnecessarily (like StringBuilder in original solution)
   - Using substring operations repeatedly

## Edge Cases to Test

1. `s = ""`, `t = anything` → `true`
2. `s = "a"`, `t = ""` → `false`
3. `s = "abc"`, `t = "abc"` → `true`
4. `s = "abc"`, `t = "abcdf"` → `true`
5. `s = "abc"`, `t = "bacdf"` → `false`
6. `s = "a"`, `t = "b"` → `false`
7. `s = "a"`, `t = "a"` → `true`

## Time Complexity Analysis

- **Best Case**: All characters in s appear early in t
  - Time: O(n + m) where n = s.length(), m = t.length()
- **Worst Case**: Characters appear late or not at all
  - Time: O(n + m) where n = s.length(), m = t.length()
- **Space**: O(1) additional space (not counting input strings)

The two-pointer approach is optimal and cannot be improved for single queries, as we must examine each character at least once.
