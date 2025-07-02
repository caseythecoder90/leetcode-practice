# HashMap and Set Problems

## Pattern Overview

HashMap and Set problems leverage the power of hash-based data structures to achieve O(1) average-case lookup, insertion, and deletion operations. These problems often involve counting, frequency analysis, finding duplicates, or checking for existence/membership efficiently.

## When to Use This Pattern

- **Frequency counting**: Count occurrences of elements
- **Duplicate detection**: Finding duplicates or unique elements
- **Complement searches**: Finding pairs that sum to a target (like Two Sum)
- **Set operations**: Union, intersection, difference of collections
- **Membership testing**: Checking if elements exist in a collection
- **Caching/Memoization**: Storing computed results for quick retrieval
- **Grouping/Categorization**: Group elements by some property
- **Range/Window problems**: When you need to track elements in a sliding window

## Key Concepts

### HashMap Properties
- **Average Time Complexity**: O(1) for get, put, remove
- **Worst Case**: O(n) when many hash collisions occur
- **Space Complexity**: O(n) for storing n key-value pairs
- **Null handling**: HashMap allows one null key and multiple null values

### HashSet Properties
- **Average Time Complexity**: O(1) for add, contains, remove
- **Uniqueness**: Automatically handles duplicates
- **No ordering**: Elements are not stored in insertion order
- **Iteration**: O(n) to iterate through all elements

### LinkedHashMap/LinkedHashSet
- **Insertion Order**: Maintains insertion order
- **Slightly more memory**: Due to additional linked list pointers
- **Access Order**: LinkedHashMap can be configured for LRU cache behavior

## Common Techniques and Patterns

### 1. **Frequency Counting Pattern**
```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}
```

### 2. **Complement Search Pattern (Two Sum)**
```java
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (map.containsKey(complement)) {
        return new int[]{map.get(complement), i};
    }
    map.put(nums[i], i);
}
```

### 3. **Set for Duplicates/Uniqueness**
```java
Set<Integer> seen = new HashSet<>();
for (int num : nums) {
    if (!seen.add(num)) {
        return true; // Duplicate found
    }
}
```

### 4. **Grouping by Property**
```java
Map<String, List<String>> groups = new HashMap<>();
for (String word : words) {
    String key = getKey(word); // Some categorization function
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}
```

### 5. **Sliding Window with HashMap**
```java
Map<Character, Integer> window = new HashMap<>();
int left = 0;
for (int right = 0; right < s.length(); right++) {
    char c = s.charAt(right);
    window.put(c, window.getOrDefault(c, 0) + 1);
    
    while (needToShrink()) {
        char leftChar = s.charAt(left);
        window.put(leftChar, window.get(leftChar) - 1);
        if (window.get(leftChar) == 0) {
            window.remove(leftChar);
        }
        left++;
    }
}
```

## Time Complexity Patterns

| Operation | HashMap | HashSet | TreeMap | TreeSet |
|-----------|---------|---------|---------|---------|
| Insert | O(1) avg, O(n) worst | O(1) avg, O(n) worst | O(log n) | O(log n) |
| Search | O(1) avg, O(n) worst | O(1) avg, O(n) worst | O(log n) | O(log n) |
| Delete | O(1) avg, O(n) worst | O(1) avg, O(n) worst | O(log n) | O(log n) |
| Iteration | O(n) | O(n) | O(n) | O(n) |

## Space Complexity Considerations

- **HashMap/HashSet**: O(n) where n is the number of elements
- **Load Factor**: Default 0.75 means resizing occurs when 75% full
- **Memory overhead**: Hash tables require extra space for buckets and collision handling

## Common Problem Categories

### 1. **Array Problems**
- Two Sum, Three Sum
- Contains Duplicate
- Intersection of Arrays
- Majority Element

### 2. **String Problems**
- Valid Anagram
- Group Anagrams
- Longest Substring Without Repeating Characters
- First Unique Character

### 3. **Design Problems**
- LRU Cache
- Design HashMap
- Random Insert/Delete

### 4. **Graph-Related**
- Course Schedule (using indegree map)
- Clone Graph (using visited map)

## Best Practices

### 1. **Choose the Right Data Structure**
```java
// For frequency counting
Map<T, Integer> frequency = new HashMap<>();

// For existence checking
Set<T> exists = new HashSet<>();

// For maintaining insertion order
Map<T, V> ordered = new LinkedHashMap<>();

// For sorted iteration
Map<T, V> sorted = new TreeMap<>();
```

### 2. **Handle Null Values Carefully**
```java
// Safe null handling
Integer value = map.get(key);
if (value != null) {
    // Process value
}

// Or use getOrDefault
int value = map.getOrDefault(key, 0);
```

### 3. **Efficient Iteration**
```java
// Efficient iteration over map
for (Map.Entry<K, V> entry : map.entrySet()) {
    K key = entry.getKey();
    V value = entry.getValue();
}

// When you only need keys or values
for (K key : map.keySet()) { /* process key */ }
for (V value : map.values()) { /* process value */ }
```

### 4. **computeIfAbsent for Complex Values**
```java
// Instead of checking and creating
if (!map.containsKey(key)) {
    map.put(key, new ArrayList<>());
}
map.get(key).add(value);

// Use computeIfAbsent
map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
```

## Common Pitfalls and Solutions

### 1. **Hash Collision Concerns**
- **Problem**: Poor hash function causing O(n) operations
- **Solution**: Use built-in hash functions, avoid custom hashCode() unless necessary

### 2. **Modifying During Iteration**
```java
// ❌ ConcurrentModificationException
for (Integer key : map.keySet()) {
    if (condition) {
        map.remove(key); // WRONG!
    }
}

// ✅ Use iterator
Iterator<Integer> iterator = map.keySet().iterator();
while (iterator.hasNext()) {
    Integer key = iterator.next();
    if (condition) {
        iterator.remove(); // Correct
    }
}
```

### 3. **Memory Leaks with Custom Objects**
- **Problem**: Objects not being garbage collected due to hash references
- **Solution**: Implement proper equals() and hashCode(), remove from maps when done

### 4. **Integer vs int in Generic Collections**
```java
// ❌ Autoboxing issues
Map<Integer, Integer> map = new HashMap<>();
map.put(1, null);
int value = map.get(1); // NullPointerException

// ✅ Safe approach
Integer value = map.get(1);
if (value != null) {
    int intValue = value;
}
```

## Problem-Solving Approach

### Step 1: Identify the Pattern
- [ ] Do you need to count frequencies?
- [ ] Are you looking for existence/membership?
- [ ] Do you need to group items by some property?
- [ ] Are you searching for complements or pairs?

### Step 2: Choose Data Structure
- [ ] HashMap for key-value relationships
- [ ] HashSet for uniqueness/existence
- [ ] LinkedHashMap/LinkedHashSet for order preservation
- [ ] TreeMap/TreeSet for sorted iteration

### Step 3: Consider Edge Cases
- [ ] Empty input collections
- [ ] Single element collections
- [ ] Duplicate elements
- [ ] Null values (if applicable)

### Step 4: Optimize
- [ ] Can you solve in a single pass?
- [ ] Do you need to store all elements or just process them?
- [ ] Is there a space-time tradeoff opportunity?

## Advanced Techniques

### 1. **Rolling Hash for Substring Problems**
```java
// For finding repeated substrings efficiently
long hash = 0, power = 1;
for (int i = 0; i < k; i++) {
    hash = hash * base + s.charAt(i);
    if (i < k - 1) power *= base;
}
```

### 2. **Bidirectional Mapping**
```java
// When you need O(1) lookup in both directions
Map<String, Integer> nameToId = new HashMap<>();
Map<Integer, String> idToName = new HashMap<>();
```

### 3. **Prefix/Suffix Maps**
```java
// For prefix sum or cumulative frequency problems
Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1); // Important for handling subarrays starting from index 0
```

This pattern is fundamental to many algorithmic problems and forms the basis for more complex data structures and algorithms. Master these basics, and you'll find many seemingly complex problems become much more manageable.