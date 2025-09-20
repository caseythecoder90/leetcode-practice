# HashMap Pattern - Comprehensive Guide

## Pattern Overview

The HashMap pattern is one of the most versatile and frequently used patterns in coding interviews. It provides efficient O(1) average-case lookup, insertion, and deletion operations, making it ideal for problems involving:

- **Frequency counting**
- **Pattern matching**
- **Bijective mappings**
- **Duplicate detection**
- **Grouping and categorization**
- **Caching and memoization**

## When to Use HashMap Pattern

### Use HashMap When You Need:

1. **Fast Lookups** - O(1) average case retrieval
2. **Frequency Counting** - Track occurrences of elements
3. **Mapping Relationships** - Associate keys with values
4. **Deduplication** - Track seen elements
5. **Grouping** - Categorize items by properties
6. **Pattern Recognition** - Identify structural similarities

### Choose Array Over HashMap When:

1. **Fixed Range** - Keys are within known bounds (e.g., lowercase letters)
2. **Dense Keys** - Most possible keys are used
3. **Memory Critical** - Need minimal overhead
4. **Performance Critical** - Need absolute fastest access

## Core Techniques

### 1. Frequency Counting

Count occurrences of elements in a collection.

```java
// Generic frequency counter
Map<T, Integer> freq = new HashMap<>();
for (T item : collection) {
    freq.put(item, freq.getOrDefault(item, 0) + 1);
}

// Array for fixed range (e.g., lowercase letters)
int[] freq = new int[26];
for (char c : string.toCharArray()) {
    freq[c - 'a']++;
}
```

### 2. Bidirectional Mapping (Bijection)

Maintain one-to-one correspondence between two sets.

```java
Map<K, V> forward = new HashMap<>();
Map<V, K> reverse = new HashMap<>();

// Ensure bijection
if (forward.containsKey(key) && !forward.get(key).equals(value)) {
    return false; // Conflict
}
if (reverse.containsKey(value) && !reverse.get(value).equals(key)) {
    return false; // Conflict
}
```

### 3. Pattern Transformation

Convert structures to canonical form for comparison.

```java
private String getPattern(String s) {
    Map<Character, Integer> map = new HashMap<>();
    StringBuilder pattern = new StringBuilder();
    int id = 0;

    for (char c : s.toCharArray()) {
        if (!map.containsKey(c)) {
            map.put(c, id++);
        }
        pattern.append(map.get(c));
    }

    return pattern.toString();
}
```

### 4. Sliding Window with HashMap

Track elements in a dynamic window.

```java
Map<Character, Integer> window = new HashMap<>();
int left = 0, right = 0;

while (right < s.length()) {
    // Expand window
    char c = s.charAt(right++);
    window.put(c, window.getOrDefault(c, 0) + 1);

    // Shrink window when needed
    while (/* condition */) {
        char d = s.charAt(left++);
        window.put(d, window.get(d) - 1);
        if (window.get(d) == 0) {
            window.remove(d);
        }
    }
}
```

### 5. Grouping and Categorization

Group items by common properties.

```java
Map<String, List<String>> groups = new HashMap<>();

for (String item : items) {
    String key = getKey(item);
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
}
```

## Problems in This Repository

### Implemented Problems

1. **242. Valid Anagram** - Frequency counting
   - Check if two strings are anagrams
   - Key: Character frequency must match

2. **205. Isomorphic Strings** - Bidirectional mapping
   - Check if characters can be substituted to transform strings
   - Key: Maintain bijection between characters

3. **290. Word Pattern** - Pattern matching with words
   - Match pattern characters to words bidirectionally
   - Key: Character-to-word bijection

## Related Problem Categories

### Easy Problems (Foundation)
- **1. Two Sum** - Find pair with target sum
- **217. Contains Duplicate** - Detect duplicates
- **383. Ransom Note** - Character availability check
- **387. First Unique Character** - Find non-repeating character

### Medium Problems (Core Skills)
- **49. Group Anagrams** - Group by anagram pattern
- **128. Longest Consecutive Sequence** - Find sequence in O(n)
- **438. Find All Anagrams** - Sliding window anagrams
- **560. Subarray Sum Equals K** - Prefix sum with HashMap

### Hard Problems (Advanced)
- **76. Minimum Window Substring** - Complex sliding window
- **30. Substring with Concatenation** - Word pattern matching
- **149. Max Points on a Line** - Geometric hashing
- **432. All O(1) Data Structure** - Design problem

## Common Patterns and Solutions

### Pattern 1: Frequency Matching
```
Problems: Anagrams, permutations, character availability
Solution: Count frequencies and compare
```

### Pattern 2: Structural Mapping
```
Problems: Isomorphic structures, pattern matching
Solution: Bidirectional HashMap or pattern transformation
```

### Pattern 3: Seen/Unseen Tracking
```
Problems: Duplicates, unique elements, first occurrence
Solution: HashSet or HashMap with indices
```

### Pattern 4: Running Computation
```
Problems: Prefix sums, running products, cumulative counts
Solution: HashMap to store intermediate results
```

### Pattern 5: Window Management
```
Problems: Substring problems, sliding windows
Solution: HashMap to track window contents
```

## Time and Space Complexity

| Operation | Average | Worst Case |
|-----------|---------|------------|
| Insert | O(1) | O(n) |
| Delete | O(1) | O(n) |
| Lookup | O(1) | O(n) |
| Iterate | O(n) | O(n) |

**Space Complexity**: O(n) for n elements

**Note**: Worst case occurs during rehashing or with poor hash function causing collisions.

## Best Practices

### 1. Choose the Right Data Structure
- **HashMap**: General purpose, any key type
- **Array**: Fixed range, dense keys (e.g., 26 letters)
- **LinkedHashMap**: Maintain insertion order
- **TreeMap**: Sorted keys, O(log n) operations

### 2. Handle Edge Cases
```java
// Always check for null/empty
if (s == null || s.isEmpty()) return result;

// Check lengths for comparison problems
if (s.length() != t.length()) return false;
```

### 3. Use Proper Equality Checks
```java
// Use Objects.equals for null safety
Objects.equals(map.get(key), expectedValue)

// For arrays
Arrays.equals(arr1, arr2)
```

### 4. Optimize for Common Cases
```java
// Use array for ASCII characters
int[] freq = new int[128]; // All ASCII

// Use HashMap for Unicode
Map<Character, Integer> freq = new HashMap<>();
```

### 5. Consider Memory vs Speed Tradeoffs
- Arrays: Fast, fixed memory, limited key range
- HashMap: Flexible, more memory, handles any key

## Interview Tips

### 1. Problem Analysis
- Identify if you need counting, mapping, or grouping
- Determine key range (fixed like ASCII or variable)
- Consider if order matters (LinkedHashMap vs HashMap)

### 2. Implementation Strategy
- Start with brute force, optimize with HashMap
- Explain space-time tradeoffs
- Mention collision handling if relevant

### 3. Common Optimizations
- Array instead of HashMap for fixed range
- Single pass instead of multiple
- Early termination when possible

### 4. Testing Approach
- Empty input
- Single element
- Duplicates
- All same/all different elements
- Large inputs for performance

## Study Path

### Week 1: Foundation
1. Master frequency counting (Valid Anagram)
2. Understand HashSet for duplicates
3. Practice basic HashMap operations

### Week 2: Core Patterns
1. Learn bidirectional mapping (Isomorphic Strings)
2. Practice pattern transformation (Word Pattern)
3. Implement grouping problems

### Week 3: Advanced Techniques
1. Sliding window with HashMap
2. Prefix sum problems
3. Design problems using HashMap

### Week 4: Competition Level
1. Complex pattern matching
2. Multiple HashMaps coordination
3. Optimization techniques

## Debugging HashMap Problems

### Common Bugs
1. **Null Pointer**: Not handling absent keys
2. **Reference Equality**: Using == for Integer/String
3. **Modification During Iteration**: ConcurrentModificationException
4. **Hash Collision**: Not considering worst case

### Debugging Strategies
```java
// Print HashMap contents
System.out.println("Map: " + map);

// Check specific key
System.out.println("Key exists: " + map.containsKey(key));
System.out.println("Value: " + map.get(key));

// Verify size
System.out.println("Size: " + map.size());
```

## Additional Resources

### Similar Patterns to Study
- **Two Pointers**: Often combined with HashMap
- **Sliding Window**: HashMap tracks window state
- **Dynamic Programming**: HashMap for memoization
- **Graph Traversal**: HashMap for visited tracking

### Next Steps
1. Solve all Easy HashMap problems on LeetCode
2. Practice daily with one HashMap problem
3. Implement HashMap from scratch for deep understanding
4. Study Java HashMap source code