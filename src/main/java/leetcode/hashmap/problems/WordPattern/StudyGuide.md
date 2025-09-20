# Word Pattern - Detailed Study Guide

## Problem Understanding

The Word Pattern problem requires establishing a **bijection** (one-to-one correspondence) between pattern characters and words.

### What is a Bijection?
A bijection means:
- Every pattern character maps to exactly ONE word (injective)
- Every word maps to exactly ONE pattern character (surjective)
- The mapping is consistent throughout the string

## Step-by-Step Walkthrough

### Example 1: "abba" → "dog cat cat dog"
```
Step 1: Split string into words
       ["dog", "cat", "cat", "dog"]

Step 2: Process each character-word pair
       i=0: 'a' → "dog"
            patternToWord: {'a': "dog"}
            wordToPattern: {"dog": 'a'}

       i=1: 'b' → "cat"
            patternToWord: {'a': "dog", 'b': "cat"}
            wordToPattern: {"dog": 'a', "cat": 'b'}

       i=2: 'b' → "cat"
            Check: 'b' already maps to "cat" ✓
            Check: "cat" already maps to 'b' ✓

       i=3: 'a' → "dog"
            Check: 'a' already maps to "dog" ✓
            Check: "dog" already maps to 'a' ✓

Result: TRUE - Pattern matches!
```

### Example 2: "abba" → "dog cat cat fish"
```
Step 1: Split string into words
       ["dog", "cat", "cat", "fish"]

Step 2: Process each character-word pair
       i=0: 'a' → "dog"
            patternToWord: {'a': "dog"}
            wordToPattern: {"dog": 'a'}

       i=1: 'b' → "cat"
            patternToWord: {'a': "dog", 'b': "cat"}
            wordToPattern: {"dog": 'a', "cat": 'b'}

       i=2: 'b' → "cat"
            Check: 'b' already maps to "cat" ✓
            Check: "cat" already maps to 'b' ✓

       i=3: 'a' → "fish"
            Check: 'a' already maps to "dog"
            But we have "fish" here! ✗

Result: FALSE - Pattern broken at index 3
```

## Algorithm Deep Dive

### Two HashMap Approach (Recommended)
```java
Map<Character, String> patternToWord = new HashMap<>();
Map<String, Character> wordToPattern = new HashMap<>();

for (int i = 0; i < pattern.length(); i++) {
    char c = pattern.charAt(i);
    String word = words[i];

    // Check pattern → word consistency
    if (patternToWord.containsKey(c)) {
        if (!patternToWord.get(c).equals(word)) {
            return false;  // Pattern char mapped to different word
        }
    } else {
        patternToWord.put(c, word);
    }

    // Check word → pattern consistency
    if (wordToPattern.containsKey(word)) {
        if (wordToPattern.get(word) != c) {
            return false;  // Word mapped to different pattern char
        }
    } else {
        wordToPattern.put(word, c);
    }
}
```

### Index Mapping Approach (Elegant)
```java
// Key insight: Elements that are the same should have the same last occurrence index
Map<Character, Integer> charIndex = new HashMap<>();
Map<String, Integer> wordIndex = new HashMap<>();

for (int i = 0; i < pattern.length(); i++) {
    Integer charLastIndex = charIndex.put(pattern.charAt(i), i);
    Integer wordLastIndex = wordIndex.put(words[i], i);

    // If last indices don't match, pattern is broken
    if (!Objects.equals(charLastIndex, wordLastIndex)) {
        return false;
    }
}
```

## Visual Pattern Recognition

```
Pattern: a b b a
Words:   dog cat cat dog
Mapping: a→dog, b→cat

Visual Check:
Position:  0   1   2   3
Pattern:   a   b   b   a
Words:    dog cat cat dog
Valid?     ✓   ✓   ✓   ✓

Pattern: a b b a
Words:   dog cat cat fish
Mapping: a→?, b→cat

Visual Check:
Position:  0   1   2   3
Pattern:   a   b   b   a
Words:    dog cat cat fish
Valid?     ✓   ✓   ✓   ✗ (a should map to dog, not fish)
```

## Common Mistakes to Avoid

### Mistake 1: Only Checking One Direction
```java
// WRONG - Only checks pattern → word
Map<Character, String> map = new HashMap<>();
for (int i = 0; i < pattern.length(); i++) {
    char c = pattern.charAt(i);
    String word = words[i];
    if (map.containsKey(c)) {
        if (!map.get(c).equals(word)) return false;
    } else {
        map.put(c, word);
    }
}
// This would incorrectly accept "ab" → "dog dog"
```

### Mistake 2: Not Checking Length First
```java
// WRONG - May cause ArrayIndexOutOfBounds
for (int i = 0; i < pattern.length(); i++) {
    // If words.length < pattern.length, this crashes
    String word = words[i];
}
```

## Practice Problems by Difficulty

### Easy (Start Here)
1. **205. Isomorphic Strings** - Character to character mapping
2. **242. Valid Anagram** - Character frequency matching

### Medium (Next Step)
1. **49. Group Anagrams** - Pattern grouping
2. **890. Find and Replace Pattern** - Multiple pattern matching
3. **451. Sort Characters By Frequency** - Pattern-based sorting

### Hard (Advanced)
1. **291. Word Pattern II** (Premium) - Pattern with backtracking
2. **126. Word Ladder II** - Pattern transformation paths

## Memory Tips
1. **"Bi" in bijection = Two-way**: Always check both directions
2. **Pattern = Template**: Like a cookie cutter, same shape everywhere
3. **Think substitution cipher**: Each letter consistently represents same word

## Interview Tips
1. **Clarify edge cases**: Empty strings? Single character? Extra spaces?
2. **Explain bijection**: Show you understand the two-way mapping requirement
3. **Consider optimization**: Two HashMaps vs. one HashMap + Set
4. **Test thoroughly**: Include cases that break only one direction of mapping