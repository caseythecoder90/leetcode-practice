# 1071. Greatest Common Divisor of Strings

## Problem Description

For two strings s and t, we say "t divides s" if and only if s = t + t + t + ... + t + t (i.e., t is concatenated with itself one or more times).

Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.

## Examples

### Example 1:
```
Input: str1 = "ABCABC", str2 = "ABC"
Output: "ABC"
Explanation: "ABC" divides "ABCABC" (ABC + ABC) and divides "ABC" (ABC)
```

### Example 2:
```
Input: str1 = "ABABAB", str2 = "ABAB"
Output: "AB"
Explanation: "AB" divides "ABABAB" (AB + AB + AB) and divides "ABAB" (AB + AB)
```

### Example 3:
```
Input: str1 = "LEET", str2 = "CODE"
Output: ""
Explanation: No common divisor string exists
```

## Constraints
- 1 <= str1.length, str2.length <= 1000
- str1 and str2 consist of English uppercase letters

## Key Insights

### Mathematical Connection
This problem cleverly applies the concept of **Greatest Common Divisor (GCD)** from mathematics to strings:

1. **String Division**: A string `t` divides string `s` if `s` can be formed by concatenating `t` multiple times
2. **GCD of Strings**: The longest string that divides both input strings
3. **Length Relationship**: If a GCD string exists, its length must be GCD(len(str1), len(str2))

### Critical Observation
For two strings to have a common divisor string:
- `str1 + str2` must equal `str2 + str1`
- If this condition fails, no common divisor exists

## Approach

### Algorithm Steps
1. **Quick Check**: Verify if `str1 + str2 == str2 + str1`
   - If false, return empty string (no common divisor possible)
2. **Find GCD Length**: Calculate `gcd(str1.length, str2.length)`
3. **Extract Result**: Return substring of str1 from index 0 to GCD length

### Why This Works
- The GCD of lengths gives us the maximum possible length of any common divisor
- The concatenation check ensures that both strings are built from the same repeating pattern
- If both conditions are met, the prefix of length GCD(lengths) is guaranteed to be the answer

## Complexity Analysis

- **Time Complexity**: O(n + m) where n and m are string lengths
  - String concatenation and comparison: O(n + m)
  - GCD calculation: O(log(min(n, m)))
- **Space Complexity**: O(n + m) for string concatenation

## Pattern Category
This problem combines **String Manipulation** with **Mathematical GCD** concepts, demonstrating how number theory can apply to string problems.