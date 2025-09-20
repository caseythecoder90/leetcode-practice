# 205. Isomorphic Strings

## Problem Description
Given two strings s and t, determine if they are isomorphic. Two strings are isomorphic if the characters in s can be replaced to get t. All occurrences of a character must be replaced with another character while preserving the order of characters.

### Constraints:
- No two characters may map to the same character
- A character may map to itself
- 1 <= s.length <= 5 * 10^4
- t.length == s.length
- s and t consist of any valid ASCII character

## Approach Analysis

### Approach 1: Two HashMaps (Bidirectional Mapping)
**Time Complexity:** O(n) where n is the length of the strings
**Space Complexity:** O(k) where k is the number of unique characters

**Algorithm:**
1. Maintain two maps: s→t and t→s
2. For each position, check bidirectional consistency
3. If any mapping conflicts, return false

### Approach 2: HashMap + HashSet
**Time Complexity:** O(n)
**Space Complexity:** O(k)

**Algorithm:**
1. Use one HashMap for s→t mapping
2. Use HashSet to track already mapped characters in t
3. Ensures no two characters in s map to same character in t

### Approach 3: Array Mapping (Optimized for ASCII)
**Time Complexity:** O(n)
**Space Complexity:** O(1) - fixed size array of 256

**Algorithm:**
1. Use fixed-size arrays for ASCII characters (256 elements)
2. Faster than HashMap for ASCII-only strings
3. Direct index access without hashing

### Approach 4: Index Mapping
**Time Complexity:** O(n)
**Space Complexity:** O(k)

**Algorithm:**
1. Track last occurrence index for each character
2. Characters that map to each other should have same occurrence pattern
3. Elegant one-pass solution

### Approach 5: Pattern Transformation
**Time Complexity:** O(n)
**Space Complexity:** O(n)

**Algorithm:**
1. Transform both strings to their pattern representation
2. Compare patterns for equality
3. Example: "egg" → "0,1,1," and "add" → "0,1,1,"

## Key Insights
1. **Bijection Property:** Must be one-to-one mapping between characters
2. **Order Preservation:** Relative order of characters must be maintained
3. **Character Frequency:** Same characters must map to same target consistently

## Common Pitfalls
1. Only checking s→t mapping without t→s
2. Not handling the case where multiple source chars map to same target
3. Assuming only lowercase letters (problem allows any ASCII)

## Related Problems
- **290. Word Pattern** - Similar concept with words instead of characters
- **890. Find and Replace Pattern** - Multiple string pattern matching
- **242. Valid Anagram** - Character frequency comparison
- **49. Group Anagrams** - Pattern-based grouping

## Problem Variations
1. **Case Insensitive:** Make the comparison case-insensitive
2. **Unicode Support:** Handle full Unicode character set
3. **Multiple Strings:** Check if multiple strings are all isomorphic to each other
4. **Minimum Transformations:** Find minimum operations to make strings isomorphic