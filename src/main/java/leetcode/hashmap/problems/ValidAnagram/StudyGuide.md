# Valid Anagram - Detailed Study Guide

## Understanding Anagrams

An **anagram** is a rearrangement of letters. Think of it as having the same "building blocks" (letters) but in different order.

### Visual Examples
```
"anagram" → "nagaram" ✓
a: 3, n: 1, g: 1, r: 1, m: 1

"rat" → "car" ✗
r: 1, a: 1, t: 1  vs  c: 1, a: 1, r: 1
(different letters!)
```

## Core Insight: Frequency Counting

The key insight is that anagrams have **identical character frequencies**.

## Step-by-Step Algorithm Walkthrough

### Optimal Approach: Single Array Counter

```java
s = "anagram", t = "nagaram"

Initial: frequency[26] = [0, 0, ..., 0]

Processing:
i=0: s[0]='a', t[0]='n'
     frequency['a'-'a']++ → frequency[0] = 1
     frequency['n'-'a']-- → frequency[13] = -1

i=1: s[1]='n', t[1]='a'
     frequency['n'-'a']++ → frequency[13] = 0
     frequency['a'-'a']-- → frequency[0] = 0

i=2: s[2]='a', t[2]='g'
     frequency['a'-'a']++ → frequency[0] = 1
     frequency['g'-'a']-- → frequency[6] = -1

... continue for all characters ...

Final check: All frequencies should be 0
```

## Visual Frequency Table

```
String: "anagram"
+---+---+---+---+---+---+---+
| a | n | a | g | r | a | m |
+---+---+---+---+---+---+---+

Frequency Count:
a: 3, g: 1, m: 1, n: 1, r: 1

Array representation (indices 0-25 for a-z):
[3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, ...]
 a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  ...
```

## Implementation Patterns

### Pattern 1: Increment/Decrement (Most Efficient)
```java
int[] freq = new int[26];
for (int i = 0; i < s.length(); i++) {
    freq[s.charAt(i) - 'a']++;  // Add from s
    freq[t.charAt(i) - 'a']--;  // Subtract from t
}
// All should be zero if anagram
```

### Pattern 2: Build and Verify
```java
Map<Character, Integer> map = new HashMap<>();
// Build frequency map from s
for (char c : s.toCharArray()) {
    map.put(c, map.getOrDefault(c, 0) + 1);
}
// Verify against t
for (char c : t.toCharArray()) {
    if (!map.containsKey(c) || map.get(c) == 0) {
        return false;
    }
    map.put(c, map.get(c) - 1);
}
```

### Pattern 3: Sort and Compare
```java
char[] sArr = s.toCharArray();
char[] tArr = t.toCharArray();
Arrays.sort(sArr);
Arrays.sort(tArr);
return Arrays.equals(sArr, tArr);
// "rat" → "art", "car" → "acr" (not equal)
```

## Edge Cases and Testing

### Edge Case 1: Different Lengths
```
s = "ab", t = "abc"
Return false immediately (different lengths can't be anagrams)
```

### Edge Case 2: Same String
```
s = "abc", t = "abc"
Result: true (string is anagram of itself)
```

### Edge Case 3: Single Character
```
s = "a", t = "a" → true
s = "a", t = "b" → false
```

### Edge Case 4: Repeated Characters
```
s = "aab", t = "abb"
Frequency of 'a': 2 vs 1 (not equal)
Frequency of 'b': 1 vs 2 (not equal)
Result: false
```

## Unicode Support Implementation

```java
public boolean isAnagramUnicode(String s, String t) {
    if (s.length() != t.length()) return false;

    Map<Integer, Integer> codePointCount = new HashMap<>();

    // Count code points (handles emojis, multi-byte chars)
    s.codePoints().forEach(cp ->
        codePointCount.merge(cp, 1, Integer::sum)
    );

    // Decrement for t
    t.codePoints().forEach(cp ->
        codePointCount.merge(cp, -1, Integer::sum)
    );

    // All counts should be zero
    return codePointCount.values().stream()
        .allMatch(count -> count == 0);
}
```

## Common Mistakes to Avoid

```java
// MISTAKE 1: Forgetting length check
public boolean isAnagram(String s, String t) {
    // Missing: if (s.length() != t.length()) return false;
    int[] freq = new int[26];
    // ... rest of code
}

// MISTAKE 2: Wrong array comparison
int[] arr1 = new int[26];
int[] arr2 = new int[26];
// WRONG: if (arr1 == arr2)
// RIGHT: if (Arrays.equals(arr1, arr2))

// MISTAKE 3: Not handling negative indices
char c = s.charAt(i);
// WRONG: frequency[c]++ (if c > 127, ArrayIndexOutOfBounds)
// RIGHT: frequency[c - 'a']++ (for lowercase only)

// MISTAKE 4: Creating new strings unnecessarily
// INEFFICIENT:
String sorted1 = new String(Arrays.sort(s.toCharArray()));
// BETTER:
char[] arr = s.toCharArray();
Arrays.sort(arr);
```

## Performance Comparison

| Approach | Time | Space | Best For |
|----------|------|-------|----------|
| Frequency Array | O(n) | O(1) | ASCII/lowercase |
| HashMap | O(n) | O(k) | Unicode/large charset |
| Sorting | O(n log n) | O(n) | Simple implementation |
| Two Arrays | O(n) | O(1) | Clear logic |

## Practice Problems

### Foundation Level
1. **383. Ransom Note** - Can construct from available letters
2. **387. First Unique Character** - Find non-repeating character

### Intermediate Level
1. **49. Group Anagrams** - Group strings by anagram
2. **438. Find All Anagrams in String** - Sliding window anagrams
3. **567. Permutation in String** - Check substring anagram

### Advanced Level
1. **30. Substring with Concatenation** - Complex anagram matching
2. **76. Minimum Window Substring** - Anagram with extra characters allowed

## Memory Techniques

1. **"Anagram = Same Ingredients"**: Like a recipe with same ingredients, different dish
2. **"Frequency = Fingerprint"**: Each word has unique frequency fingerprint
3. **"Sort = Normalize"**: Sorting gives canonical form
4. **"Balance = Zero"**: Increment/decrement should balance to zero

## Interview Script

```
"I'll solve this using frequency counting since anagrams have identical character frequencies.

First, I'll check if lengths are equal - different lengths can't be anagrams.

Then I'll use a single frequency array:
- Increment for characters in s
- Decrement for characters in t
- All counts should be zero for valid anagram

This gives us O(n) time and O(1) space for lowercase English.

For Unicode support, I'd switch to a HashMap to handle any character set."
```

## Quick Reference Implementation

```java
// Optimal for lowercase English
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;

    int[] freq = new int[26];
    for (int i = 0; i < s.length(); i++) {
        freq[s.charAt(i) - 'a']++;
        freq[t.charAt(i) - 'a']--;
    }

    for (int f : freq) {
        if (f != 0) return false;
    }

    return true;
}
```