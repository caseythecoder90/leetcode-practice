# 768. Merge Strings Alternately

## Problem Description

You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with word1. If a string is longer than the other, append the additional letters onto the end of the merged string.

Return the merged string.

## Examples

### Example 1:
```
Input: word1 = "abc", word2 = "pqr"
Output: "apbqcr"
Explanation: The merged string will be merged as so:
word1:  a   b   c
word2:    p   q   r
merged: a p b q c r
```

### Example 2:
```
Input: word1 = "ab", word2 = "pqrs"
Output: "apbqrs"
Explanation: Notice that as word2 is longer, "rs" is appended to the end.
word1:  a   b 
word2:    p   q   r   s
merged: a p b q   r   s
```

### Example 3:
```
Input: word1 = "abcd", word2 = "pq"
Output: "apbqcd"
Explanation: Notice that as word1 is longer, "cd" is appended to the end.
word1:  a   b   c   d
word2:    p   q 
merged: a p b q c   d
```

## Constraints
- 1 <= word1.length, word2.length <= 100
- word1 and word2 consist of lowercase English letters.

## Approach

### Two-Pointer Technique
This problem can be solved using a two-pointer approach:

1. Use two pointers, one for each string
2. Alternate between taking characters from word1 and word2
3. Continue until both pointers reach the end of their respective strings
4. Handle remaining characters from the longer string

### Algorithm Steps
1. Initialize two pointers `i` and `j` to 0
2. Create a StringBuilder for efficient string building
3. While either pointer hasn't reached the end:
   - If `i < word1.length()`, append word1[i] and increment i
   - If `j < word2.length()`, append word2[j] and increment j
4. Return the built string

## Complexity Analysis

- **Time Complexity**: O(n + m) where n and m are the lengths of word1 and word2
- **Space Complexity**: O(n + m) for the result string

## Pattern Category
This problem belongs to the **Two Pointers / Arrays** pattern as it involves iterating through two sequences simultaneously.