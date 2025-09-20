# HashMap Pattern - Interview Flashcards

## Core Concepts

### Card 1
**Q: What is the average time complexity for HashMap operations?**
A: O(1) for insert, delete, and lookup operations. Worst case is O(n) due to hash collisions or rehashing.

### Card 2
**Q: When should you use an array instead of HashMap?**
A: When keys have a fixed, small range (e.g., 26 lowercase letters), array is faster with O(1) guaranteed access and less memory overhead.

### Card 3
**Q: What is a bijection in the context of HashMap problems?**
A: A one-to-one correspondence between two sets where each element in the first set maps to exactly one element in the second set, and vice versa.

### Card 4
**Q: How do you handle null safety when comparing HashMap values?**
A: Use `Objects.equals(value1, value2)` which handles null values correctly, avoiding NullPointerException.

### Card 5
**Q: What's the space complexity of using HashMap?**
A: O(n) where n is the number of key-value pairs stored, plus overhead for the underlying array and entry objects.

---

## Problem-Specific Cards

### Valid Anagram (242)

### Card 6
**Q: What's the key insight for solving Valid Anagram?**
A: Anagrams have identical character frequencies. Count frequencies and compare.

### Card 7
**Q: What's the optimal approach for Valid Anagram with lowercase English letters?**
A: Use `int[26]` array. Increment for first string, decrement for second. All values should be zero.
```java
int[] freq = new int[26];
for (int i = 0; i < s.length(); i++) {
    freq[s.charAt(i) - 'a']++;
    freq[t.charAt(i) - 'a']--;
}
```

### Card 8
**Q: How do you handle Unicode characters in Valid Anagram?**
A: Use HashMap instead of array to handle any character set:
```java
Map<Character, Integer> freq = new HashMap<>();
```

### Card 9
**Q: What edge case should you check first in Valid Anagram?**
A: Different lengths - strings with different lengths cannot be anagrams.

### Card 10
**Q: Time/Space complexity for Valid Anagram?**
A: Time: O(n), Space: O(1) for fixed alphabet (array), O(k) for k unique characters (HashMap)

---

### Isomorphic Strings (205)

### Card 11
**Q: What does "isomorphic" mean for strings?**
A: Characters in one string can be replaced to get the other string, with consistent one-to-one mapping.

### Card 12
**Q: Why do you need bidirectional checking for Isomorphic Strings?**
A: To ensure no two different characters map to the same character (injection) and vice versa (surjection).

### Card 13
**Q: What's the index mapping approach for Isomorphic Strings?**
A: Track last occurrence index for each character. If characters map to each other, their occurrence patterns must match.
```java
Integer last1 = map1.put(s.charAt(i), i);
Integer last2 = map2.put(t.charAt(i), i);
if (!Objects.equals(last1, last2)) return false;
```

### Card 14
**Q: Give an example where single-direction mapping fails.**
A: "ab" → "aa". If only checking a→a, b→a, we miss that two different source characters map to same target.

### Card 15
**Q: What's the pattern transformation approach?**
A: Convert both strings to their pattern signature (e.g., "egg" → "011", "add" → "011") and compare.

---

### Word Pattern (290)

### Card 16
**Q: What's the key difference between Word Pattern and Isomorphic Strings?**
A: Word Pattern maps characters to words (not character-to-character), requiring string splitting and word handling.

### Card 17
**Q: What preprocessing is needed for Word Pattern?**
A: Split the string by spaces into words array and check if pattern length equals word count.
```java
String[] words = s.split(" ");
if (pattern.length() != words.length) return false;
```

### Card 18
**Q: Why use two HashMaps for Word Pattern?**
A: One maps pattern→word, other maps word→pattern, ensuring bidirectional consistency (bijection).

### Card 19
**Q: What's a common edge case in Word Pattern?**
A: Pattern "a" with string "dog dog" - same pattern char can't map to different words.

### Card 20
**Q: How do you check bidirectional mapping efficiently?**
A: For each pair, verify both mappings exist and are consistent:
```java
if (!Objects.equals(pToW.get(c), word) ||
    !Objects.equals(wToP.get(word), c)) {
    return false;
}
```

---

## Pattern Recognition Cards

### Card 21
**Q: When do you use frequency counting pattern?**
A: Problems involving anagrams, permutations, character availability, or occurrence counting.

### Card 22
**Q: When do you use bidirectional mapping?**
A: When establishing one-to-one correspondence: isomorphic structures, pattern matching, substitution ciphers.

### Card 23
**Q: When do you use pattern transformation?**
A: To compare structural similarity by converting to canonical form, useful for grouping or matching.

### Card 24
**Q: What's the sliding window + HashMap pattern?**
A: Use HashMap to track elements in current window, adjust window boundaries while maintaining counts.

### Card 25
**Q: When to use HashMap for grouping?**
A: Group Anagrams, categorize by property, collect items with common characteristics.

---

## Common Mistakes Cards

### Card 26
**Q: What's wrong with `if (map.get(key) == value)`?**
A: Uses reference equality for Integer/String objects. Use `Objects.equals()` for value comparison.

### Card 27
**Q: How to avoid ConcurrentModificationException?**
A: Use Iterator.remove() or collect keys to remove in separate list, don't modify map during iteration.

### Card 28
**Q: Common mistake with array indices for characters?**
A: Forgetting to subtract 'a' for lowercase letters: use `freq[c - 'a']` not `freq[c]`.

### Card 29
**Q: What's wrong with only checking one direction in mapping problems?**
A: Misses cases where multiple source elements map to same target (violates injection property).

### Card 30
**Q: Why check string lengths first in comparison problems?**
A: Early termination for obvious mismatches, prevents index out of bounds errors.

---

## Optimization Cards

### Card 31
**Q: Array vs HashMap for character frequency?**
A: Array: O(1) guaranteed, less memory, fixed range. HashMap: flexible, any character, more memory.

### Card 32
**Q: How to optimize HashMap initialization?**
A: Specify initial capacity if size is known to avoid rehashing: `new HashMap<>(expectedSize)`

### Card 33
**Q: computeIfAbsent vs getOrDefault?**
A: `computeIfAbsent`: lazy initialization of complex objects. `getOrDefault`: simple default values.

### Card 34
**Q: How to optimize string pattern matching?**
A: Transform to canonical form once, compare transformed versions instead of character-by-character.

### Card 35
**Q: Single pass vs multiple passes?**
A: Combine operations when possible. E.g., increment/decrement in same loop for anagram check.

---

## Advanced Technique Cards

### Card 36
**Q: How to handle Unicode in character problems?**
A: Use HashMap<Integer, Integer> with codePoints() instead of char array.

### Card 37
**Q: What's the merge() method used for?**
A: Combines new value with existing value using provided function:
```java
map.merge(key, 1, Integer::sum); // Increment counter
```

### Card 38
**Q: How to track both frequency and order?**
A: Use LinkedHashMap to maintain insertion order while having O(1) operations.

### Card 39
**Q: How to find first non-repeating character?**
A: Two passes: count frequencies, then find first with frequency 1 maintaining order.

### Card 40
**Q: How to group items by computed key?**
A: Use computeIfAbsent with list:
```java
map.computeIfAbsent(getKey(item), k -> new ArrayList<>()).add(item);
```

---

## Interview Strategy Cards

### Card 41
**Q: What to clarify first in HashMap problems?**
A: Character set (ASCII/Unicode), case sensitivity, space/special character handling, null inputs.

### Card 42
**Q: How to explain HashMap choice in interview?**
A: "Need O(1) lookup for frequency counting/mapping. HashMap provides efficient key-value storage."

### Card 43
**Q: What complexity to mention for HashMap?**
A: "Average O(1) for operations, worst case O(n) with collisions. Space O(n) for n elements."

### Card 44
**Q: How to handle unknown character set?**
A: Start with HashMap for flexibility, mention array optimization for known ranges like lowercase letters.

### Card 45
**Q: What test cases to always mention?**
A: Empty input, single element, all same, all different, maximum size, special characters.

---

## Related Problems Cards

### Card 46
**Q: Easy progression from Valid Anagram?**
A: 383. Ransom Note → 49. Group Anagrams → 438. Find All Anagrams → 567. Permutation in String

### Card 47
**Q: Problems similar to Isomorphic Strings?**
A: 890. Find and Replace Pattern, 1153. String Transforms Into Another String

### Card 48
**Q: Problems extending Word Pattern?**
A: 291. Word Pattern II (with backtracking), 890. Find and Replace Pattern

### Card 49
**Q: What sliding window problems use HashMap?**
A: 3. Longest Substring Without Repeating, 76. Minimum Window Substring, 438. Find All Anagrams

### Card 50
**Q: Design problems using HashMap?**
A: 146. LRU Cache, 460. LFU Cache, 432. All O(1) Data Structure