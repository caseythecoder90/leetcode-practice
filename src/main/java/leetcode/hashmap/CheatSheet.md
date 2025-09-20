# HashMap Pattern - Interview Cheat Sheet

## Quick Decision Tree
```
Need to track frequency? → HashMap/Array
Need bidirectional mapping? → Two HashMaps
Need pattern matching? → Transform to pattern
Need to check duplicates? → HashSet
Need ordering? → LinkedHashMap/TreeMap
```

## Core HashMap Templates

### 1. Frequency Counting
```java
// For lowercase letters
int[] freq = new int[26];
for (char c : s.toCharArray()) {
    freq[c - 'a']++;
}

// For any characters
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}
```

### 2. Bidirectional Mapping (Bijection)
```java
Map<Character, Character> map1 = new HashMap<>();
Map<Character, Character> map2 = new HashMap<>();

for (int i = 0; i < s.length(); i++) {
    char c1 = s.charAt(i);
    char c2 = t.charAt(i);

    if (map1.containsKey(c1)) {
        if (map1.get(c1) != c2) return false;
    } else {
        map1.put(c1, c2);
    }

    if (map2.containsKey(c2)) {
        if (map2.get(c2) != c1) return false;
    } else {
        map2.put(c2, c1);
    }
}
```

### 3. Index Mapping (Elegant Pattern Check)
```java
Map<Character, Integer> index1 = new HashMap<>();
Map<Character, Integer> index2 = new HashMap<>();

for (int i = 0; i < s.length(); i++) {
    Integer last1 = index1.put(s.charAt(i), i);
    Integer last2 = index2.put(t.charAt(i), i);

    if (!Objects.equals(last1, last2)) {
        return false;
    }
}
```

### 4. Pattern Transformation
```java
private String getPattern(String str) {
    Map<Character, Integer> map = new HashMap<>();
    StringBuilder pattern = new StringBuilder();
    int id = 0;

    for (char c : str.toCharArray()) {
        map.putIfAbsent(c, id++);
        pattern.append(map.get(c)).append(",");
    }

    return pattern.toString();
}
```

### 5. Sliding Window with HashMap
```java
Map<Character, Integer> window = new HashMap<>();
Map<Character, Integer> need = new HashMap<>();

int left = 0, right = 0;
int valid = 0;

while (right < s.length()) {
    char c = s.charAt(right);
    right++;

    // Update window
    window.put(c, window.getOrDefault(c, 0) + 1);

    // Check condition and shrink window
    while (/* window needs shrinking */) {
        char d = s.charAt(left);
        left++;

        // Update window
        if (window.get(d) == 1) {
            window.remove(d);
        } else {
            window.put(d, window.get(d) - 1);
        }
    }
}
```

## Common Operations Reference

### HashMap Methods
```java
map.put(key, value)              // Insert/update
map.get(key)                     // Retrieve (null if absent)
map.getOrDefault(key, default)   // Safe retrieve
map.containsKey(key)             // Check existence
map.remove(key)                  // Delete entry
map.putIfAbsent(key, value)      // Only insert if new
map.merge(key, value, BiFunction)// Combine values
map.computeIfAbsent(key, Function)// Lazy initialization
```

### Useful Utilities
```java
// Objects.equals handles null safely
Objects.equals(val1, val2)

// Arrays comparison
Arrays.equals(arr1, arr2)

// String to char array
char[] chars = str.toCharArray()

// Sort array
Arrays.sort(chars)

// Character operations
Character.isLetter(c)
Character.isDigit(c)
Character.toLowerCase(c)
```

## Problem-Specific Solutions

### Valid Anagram (242)
```java
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

### Isomorphic Strings (205)
```java
public boolean isIsomorphic(String s, String t) {
    Map<Character, Integer> sIndex = new HashMap<>();
    Map<Character, Integer> tIndex = new HashMap<>();

    for (int i = 0; i < s.length(); i++) {
        if (!Objects.equals(
            sIndex.put(s.charAt(i), i),
            tIndex.put(t.charAt(i), i))) {
            return false;
        }
    }
    return true;
}
```

### Word Pattern (290)
```java
public boolean wordPattern(String pattern, String s) {
    String[] words = s.split(" ");
    if (pattern.length() != words.length) return false;

    Map<Character, String> pToW = new HashMap<>();
    Map<String, Character> wToP = new HashMap<>();

    for (int i = 0; i < pattern.length(); i++) {
        char c = pattern.charAt(i);
        String w = words[i];

        if (!Objects.equals(pToW.get(c), w) ||
            !Objects.equals(wToP.get(w), c)) {
            return false;
        }

        pToW.put(c, w);
        wToP.put(w, c);
    }
    return true;
}
```

### Group Anagrams (49)
```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();

    for (String str : strs) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);

        map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
    }

    return new ArrayList<>(map.values());
}
```

## Complexity Quick Reference

| Operation | HashMap | Array | Sorting |
|-----------|---------|-------|---------|
| Insert | O(1)* | O(1) | - |
| Lookup | O(1)* | O(1) | O(log n) |
| Delete | O(1)* | O(1) | - |
| Iterate | O(n) | O(n) | O(n) |
| Space | O(n) | O(k) | O(n) |

*Amortized, worst case O(n) due to rehashing

## Common Pitfalls & Solutions

### Pitfall 1: Null Pointer Exception
```java
// WRONG
if (map.get(key) == value)

// CORRECT
if (Objects.equals(map.get(key), value))
```

### Pitfall 2: Integer Comparison
```java
// WRONG (compares references)
Integer a = map.get(key1);
Integer b = map.get(key2);
if (a == b)

// CORRECT
if (Objects.equals(a, b))
```

### Pitfall 3: Concurrent Modification
```java
// WRONG
for (String key : map.keySet()) {
    if (condition) {
        map.remove(key); // ConcurrentModificationException
    }
}

// CORRECT
Iterator<String> it = map.keySet().iterator();
while (it.hasNext()) {
    if (condition) {
        it.remove();
    }
}
```

## Interview Tips

1. **Always check edge cases first**: null, empty, single element
2. **Clarify character set**: ASCII (array[256]) vs Unicode (HashMap)
3. **Consider space-time tradeoff**: Array faster but limited
4. **Test bidirectional**: Many problems need two-way checking
5. **Use proper equals**: Objects.equals() for null safety

## Pattern Recognition

| Pattern | When to Use | Key Technique |
|---------|-------------|---------------|
| Frequency | Count occurrences | HashMap/Array counter |
| Bijection | 1-to-1 mapping | Two HashMaps |
| Grouping | Categorize by property | HashMap of Lists |
| Deduplication | Remove duplicates | HashSet |
| Pattern Match | Same structure | Transform to canonical form |
| Sliding Window | Substring problems | Two pointers + HashMap |