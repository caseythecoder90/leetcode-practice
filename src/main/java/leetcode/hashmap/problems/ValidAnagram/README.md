# 242. Valid Anagram

## Problem Description
Given two strings s and t, return true if t is an anagram of s, and false otherwise.

An anagram is a word or phrase formed by rearranging the letters of a different word or phrase, using all the original letters exactly once.

### Constraints:
- 1 <= s.length, t.length <= 5 * 10^4
- s and t consist of lowercase English letters
- Follow-up: Handle Unicode characters

## Approach Analysis

### Approach 1: Frequency Array (Optimal for lowercase English)
**Time Complexity:** O(n) where n is string length
**Space Complexity:** O(1) - fixed size array of 26

**Algorithm:**
1. Use single array, increment for s, decrement for t
2. Check if all frequencies are zero
3. Most efficient for lowercase English letters

### Approach 2: Two Arrays with Comparison
**Time Complexity:** O(n)
**Space Complexity:** O(1)

**Algorithm:**
1. Count frequencies separately for clarity
2. Use Arrays.equals() for comparison
3. Cleaner but slightly less efficient

### Approach 3: HashMap (Unicode Support)
**Time Complexity:** O(n)
**Space Complexity:** O(k) where k is unique characters

**Algorithm:**
1. Build frequency map from first string
2. Decrement counts using second string
3. Handles any character set including Unicode

### Approach 4: Sorting
**Time Complexity:** O(n log n)
**Space Complexity:** O(n) for character arrays

**Algorithm:**
1. Sort both strings
2. Compare sorted results
3. Simple but less efficient

### Approach 5: Early Termination
**Time Complexity:** O(n)
**Space Complexity:** O(1)

**Algorithm:**
1. Track unique characters and completion
2. Can terminate early when all characters matched
3. Optimization for certain inputs

## Key Insights
1. **Length Check First:** Different lengths = not anagrams
2. **Character Frequency:** Anagrams have identical character frequencies
3. **Space-Time Tradeoff:** Array faster for ASCII, HashMap flexible for Unicode
4. **One-Pass Possible:** Can check in single pass with increment/decrement

## Common Pitfalls
1. Forgetting to check lengths first
2. Not handling empty strings
3. Assuming only lowercase (if handling Unicode)
4. Using == instead of Arrays.equals() for array comparison

## Related Problems
- **49. Group Anagrams** - Group multiple anagrams together
- **438. Find All Anagrams in a String** - Sliding window anagram search
- **567. Permutation in String** - Check if anagram exists as substring
- **383. Ransom Note** - Character availability check (subset anagram)
- **387. First Unique Character** - Character frequency analysis

## Problem Progressions

### Easy to Medium
1. **242. Valid Anagram** (Current) - Basic anagram check
2. **383. Ransom Note** - Subset character check
3. **49. Group Anagrams** - Multiple anagram grouping
4. **438. Find All Anagrams** - Sliding window technique

### Advanced Variations
1. **Anagram with spaces/punctuation** - Ignore non-letters
2. **k-Anagram** - Anagram with k character changes allowed
3. **Anagram distance** - Minimum changes to make anagram
4. **Longest anagram subsequence** - DP problem variant

## Interview Tips
1. **Start with constraints**: Ask about character set (ASCII/Unicode)
2. **Optimize for common case**: Array for English, HashMap for Unicode
3. **Mention trade-offs**: Sorting simple but O(n log n), array fast but limited
4. **Consider follow-ups**: Unicode, case-insensitive, ignore spaces