# Isomorphic Strings - Detailed Study Guide

## Core Concept: Character Substitution Cipher

Think of isomorphic strings as a **substitution cipher** where each character in the source string consistently maps to exactly one character in the target string.

## Visual Understanding

### Example 1: "egg" → "add" (Isomorphic ✓)
```
Position:  0   1   2
String s:  e   g   g
String t:  a   d   d

Mapping:
e → a (position 0)
g → d (positions 1, 2)

Check: Every 'e' maps to 'a', every 'g' maps to 'd'
Result: TRUE
```

### Example 2: "foo" → "bar" (Not Isomorphic ✗)
```
Position:  0   1   2
String s:  f   o   o
String t:  b   a   r

Attempted Mapping:
f → b (position 0)
o → a (position 1)
o → r (position 2) ✗ Conflict! 'o' already maps to 'a'

Result: FALSE
```

### Example 3: "paper" → "title" (Isomorphic ✓)
```
Position:  0   1   2   3   4
String s:  p   a   p   e   r
String t:  t   i   t   l   e

Mapping:
p → t (positions 0, 2)
a → i (position 1)
e → l (position 3)
r → e (position 4)

Check: All mappings consistent
Result: TRUE
```

## Step-by-Step Algorithm Walkthrough

### Two HashMap Approach

```java
s = "paper", t = "title"

Step 1: i=0, s[0]='p', t[0]='t'
        sToT: {p→t}
        tToS: {t→p}

Step 2: i=1, s[1]='a', t[1]='i'
        sToT: {p→t, a→i}
        tToS: {t→p, i→a}

Step 3: i=2, s[2]='p', t[2]='t'
        Check: p already maps to t ✓
        Check: t already maps to p ✓

Step 4: i=3, s[3]='e', t[3]='l'
        sToT: {p→t, a→i, e→l}
        tToS: {t→p, i→a, l→e}

Step 5: i=4, s[4]='r', t[4]='e'
        sToT: {p→t, a→i, e→l, r→e}
        tToS: {t→p, i→a, l→e, e→r}

Result: TRUE
```

## Pattern Recognition Technique

Transform strings to their "pattern signature":

```
"egg"   → [0, 1, 1]     (first unique: 0, second unique: 1, repeat of second: 1)
"add"   → [0, 1, 1]     (same pattern!)

"foo"   → [0, 1, 1]
"bar"   → [0, 1, 2]     (different pattern - third position introduces new character)

"paper" → [0, 1, 0, 2, 3]
"title" → [0, 1, 0, 2, 3] (same pattern!)
```

## Implementation Deep Dive

### Optimal Array Solution (ASCII only)
```java
public boolean isIsomorphic(String s, String t) {
    int[] sToT = new int[256];  // ASCII mapping s → t
    int[] tToS = new int[256];  // ASCII mapping t → s

    for (int i = 0; i < s.length(); i++) {
        char cs = s.charAt(i);
        char ct = t.charAt(i);

        // First time seeing this pair
        if (sToT[cs] == 0 && tToS[ct] == 0) {
            sToT[cs] = ct;
            tToS[ct] = cs;
        }
        // Check consistency
        else if (sToT[cs] != ct || tToS[ct] != cs) {
            return false;
        }
    }
    return true;
}
```

### Index Mapping (Elegant Solution)
```java
public boolean isIsomorphic(String s, String t) {
    Map<Character, Integer> sIndex = new HashMap<>();
    Map<Character, Integer> tIndex = new HashMap<>();

    for (int i = 0; i < s.length(); i++) {
        // put() returns previous value (null if first occurrence)
        Integer sPrev = sIndex.put(s.charAt(i), i);
        Integer tPrev = tIndex.put(t.charAt(i), i);

        // If both are seeing character for first time, both return null
        // If both have seen before, should return same previous index
        if (!Objects.equals(sPrev, tPrev)) {
            return false;
        }
    }
    return true;
}
```

## Common Edge Cases

### Edge Case 1: Same Character Maps to Different Characters
```
s = "ab"
t = "aa"

a → a (okay)
b → a (conflict! 'a' already mapped from 'a')
Result: FALSE
```

### Edge Case 2: Different Characters Map to Same Character
```
s = "aa"
t = "ab"

First 'a' → 'a' (okay)
Second 'a' → 'b' (conflict! 'a' already maps to 'a')
Result: FALSE
```

### Edge Case 3: Self-Mapping
```
s = "abc"
t = "abc"

Each character maps to itself
Result: TRUE
```

## Memory Techniques

1. **"ISO" = "Same Shape"**: Isomorphic means same structure/pattern
2. **"Two-way street"**: Mapping must work in both directions
3. **"Substitution cipher"**: Like encoding/decoding a secret message
4. **"Pattern matching"**: Focus on the pattern, not the actual characters

## Interview Tips

1. **Start with examples**: Work through 2-3 examples to show understanding
2. **Mention bijection**: Use the term to show theoretical knowledge
3. **Discuss trade-offs**:
   - HashMap: Flexible, handles Unicode
   - Array: Fast for ASCII, O(1) space
   - Index mapping: Elegant but may be less intuitive
4. **Test bidirectional**: Always test both s→t and t→s mappings

## Practice Progression

### Foundation
1. Understand basic character mapping
2. Practice with paper and pencil first
3. Implement two-HashMap solution

### Optimization
1. Try array-based solution for ASCII
2. Implement index mapping approach
3. Create pattern transformation method

### Advanced
1. Handle Unicode characters
2. Solve for multiple strings
3. Find groups of isomorphic strings

## Common Mistakes to Avoid

```java
// MISTAKE 1: Only checking one direction
Map<Character, Character> map = new HashMap<>();
for (int i = 0; i < s.length(); i++) {
    char sc = s.charAt(i);
    char tc = t.charAt(i);
    if (map.containsKey(sc)) {
        if (map.get(sc) != tc) return false;
    } else {
        map.put(sc, tc);
    }
}
// This misses case where different s chars map to same t char

// MISTAKE 2: Not using Objects.equals() for Integer comparison
Integer val1 = map1.get(key);
Integer val2 = map2.get(key);
if (val1 != val2) return false;  // WRONG: compares references
if (!Objects.equals(val1, val2)) return false;  // CORRECT

// MISTAKE 3: Assuming only lowercase letters
// Problem states "any valid ASCII character"
// Use array[256] not array[26]
```