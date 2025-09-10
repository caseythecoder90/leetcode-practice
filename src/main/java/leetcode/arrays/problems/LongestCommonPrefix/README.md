# LeetCode 14: Longest Common Prefix

## Problem Statement

Write a function to find the longest common prefix string amongst an array of strings. If there is no common prefix, return an empty string "".

**Example 1:**
```
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

**Example 2:**
```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

## Constraints

- 1 <= strs.length <= 200
- 0 <= strs[i].length <= 200  
- strs[i] consists of only lowercase English letters

## Approach

### Solution 1: Vertical Scanning (Optimal)

**Key Insight:** Compare characters column by column across all strings. Stop when we find the first mismatch or reach the end of any string.

**Algorithm:**
1. Handle edge cases (null or empty array)
2. Use the first string as a reference
3. For each character position `i` in the first string:
   - Get the character at position `i`
   - Compare this character with character at position `i` in all other strings
   - If any string is shorter than `i` or has a different character, return prefix up to position `i-1`
4. If we complete the loop, the entire first string is the common prefix

**Time Complexity:** O(S) where S is the sum of all characters in all strings  
**Space Complexity:** O(1) extra space

**Execution Trace for ["flower", "flow", "flight"]:**
```
i=0: Compare 'f' across all strings → all match
i=1: Compare 'l' across all strings → all match  
i=2: Compare 'o' across all strings → 'flight' has 'i' → mismatch!
Return substring(0, 2) = "fl"
```

### Solution 2: Horizontal Scanning (Alternative)

**Key Insight:** Start with first string as prefix, then iteratively shorten it by comparing with each subsequent string.

**Algorithm:**
1. Initialize prefix = first string
2. For each remaining string:
   - While the current string doesn't start with prefix, shorten prefix by one character
   - If prefix becomes empty, return ""
3. Return final prefix

**Time Complexity:** O(S) where S is the sum of all characters  
**Space Complexity:** O(1) extra space

## Edge Cases

1. **Empty array or null:** Return ""
2. **Single string:** Return the string itself
3. **Empty string in array:** Return "" (no common prefix possible)
4. **All strings identical:** Return the common string
5. **No common prefix:** Return ""

## Key Patterns

- **String Processing:** Character-by-character comparison
- **Early Termination:** Stop as soon as mismatch is found
- **Boundary Handling:** Check string lengths to avoid index out of bounds

## Interview Tips

1. **Clarify edge cases** before coding
2. **Choose vertical scanning** for better average performance
3. **Explain the time complexity** clearly
4. **Consider follow-up questions** like binary search approach for very long strings