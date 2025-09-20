# 383. Ransom Note

## Problem Statement
Given two strings `ransomNote` and `magazine`, return `true` if `ransomNote` can be constructed by using the letters from `magazine` and `false` otherwise.

Each letter in `magazine` can only be used once in `ransomNote`.

### Constraints
- `1 <= ransomNote.length, magazine.length <= 10^5`
- `ransomNote` and `magazine` consist of lowercase English letters only

## Approach Analysis

### Key Insights
1. **Character Frequency Problem**: We need to check if magazine has enough of each character
2. **One-to-One Usage**: Each character in magazine can only be used once
3. **Early Optimization**: If ransom note is longer than magazine, it's impossible
4. **Limited Character Set**: Only lowercase letters (26 possible characters)

## Solutions

### 1. Your Original Approach - Build Ransom Note Frequency Map
```java
public boolean canConstructOriginal(String ransomNote, String magazine) {
    Map<Character, Integer> frequencies = new HashMap<>();

    // Count what we need
    for (char curr : ransomNote.toCharArray()) {
        frequencies.put(curr, frequencies.getOrDefault(curr, 0) + 1);
    }

    // Remove what we find in magazine
    for (char curr : magazine.toCharArray()) {
        if (frequencies.isEmpty()) return true;

        if (frequencies.containsKey(curr)) {
            int currValue = frequencies.get(curr);
            if (currValue <= 1)
                frequencies.remove(curr);
            else
                frequencies.put(curr, currValue - 1);
        }
    }

    return frequencies.isEmpty();
}
```
- **Time**: O(n + m) where n = ransom note length, m = magazine length
- **Space**: O(k) where k = unique characters in ransom note (max 26)
- **Pros**: Early exit when all characters found, clear logic
- **Cons**: More map operations than necessary

### 2. Optimized HashMap - Build Magazine Frequency Map
```java
public boolean canConstructOptimized(String ransomNote, String magazine) {
    if (ransomNote.length() > magazine.length()) return false;

    Map<Character, Integer> magazineCount = new HashMap<>();

    // Count available characters
    for (char c : magazine.toCharArray()) {
        magazineCount.put(c, magazineCount.getOrDefault(c, 0) + 1);
    }

    // Try to use them for ransom note
    for (char c : ransomNote.toCharArray()) {
        int count = magazineCount.getOrDefault(c, 0);
        if (count == 0) return false;
        magazineCount.put(c, count - 1);
    }

    return true;
}
```
- **Time**: O(n + m)
- **Space**: O(1) - at most 26 entries in map
- **Improvement**: Cleaner logic, immediate failure on missing character

### 3. Array Solution (Optimal)
```java
public boolean canConstructArray(String ransomNote, String magazine) {
    if (ransomNote.length() > magazine.length()) return false;

    int[] charCount = new int[26];

    // Count available characters
    for (char c : magazine.toCharArray()) {
        charCount[c - 'a']++;
    }

    // Try to use them
    for (char c : ransomNote.toCharArray()) {
        if (--charCount[c - 'a'] < 0) {
            return false;
        }
    }

    return true;
}
```
- **Time**: O(n + m)
- **Space**: O(1) - fixed array of 26
- **Advantages**:
  - No HashMap overhead
  - Direct array access is faster
  - Decrement and check in one operation

### 4. Most Optimal (Using charAt)
```java
public boolean canConstructArrayOptimal(String ransomNote, String magazine) {
    if (ransomNote.length() > magazine.length()) return false;

    int[] charCount = new int[26];

    for (int i = 0; i < magazine.length(); i++) {
        charCount[magazine.charAt(i) - 'a']++;
    }

    for (int i = 0; i < ransomNote.length(); i++) {
        if (--charCount[ransomNote.charAt(i) - 'a'] < 0) {
            return false;
        }
    }

    return true;
}
```
- **Why it's faster**: `charAt(i)` avoids creating a char array

## Performance Comparison

| Solution | Time | Space | Notes |
|----------|------|-------|-------|
| Original HashMap | O(n+m) | O(26) | Early exit optimization |
| Optimized HashMap | O(n+m) | O(26) | Cleaner logic |
| Array Solution | O(n+m) | O(1) | ~3x faster than HashMap |
| Array + charAt | O(n+m) | O(1) | ~4x faster than HashMap |

## When to Use Each Approach

1. **HashMap**: When character set is unknown or large (Unicode)
2. **Array**: When character set is small and known (like lowercase letters)
3. **Your approach**: Good when ransom note is much smaller than magazine (early exit)

## Common Pitfalls
1. Forgetting to check if ransom note is longer than magazine
2. Not handling the case where a character is not in magazine at all
3. Using wrong data structure (HashMap when array would suffice)

## Related Problems
- Valid Anagram (LeetCode 242)
- Group Anagrams (LeetCode 49)
- Find All Anagrams in a String (LeetCode 438)
- Minimum Window Substring (LeetCode 76)