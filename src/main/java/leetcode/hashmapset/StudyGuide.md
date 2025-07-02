# HashMap and Set Pattern - Comprehensive Study Guide

## Learning Path and Prerequisites

### Prerequisites
- Basic understanding of arrays and strings
- Knowledge of Big O notation
- Familiarity with hash functions (conceptually)
- Understanding of generic collections in Java

### Learning Sequence
1. **Core Concepts** → HashMap/HashSet basics and time complexity
2. **Basic Patterns** → Frequency counting, duplicate detection
3. **Intermediate Patterns** → Two Sum, grouping, sliding window
4. **Advanced Patterns** → Prefix sums, design problems
5. **Optimization** → Space-time tradeoffs, performance tuning

---

## Core Concepts Deep Dive

### Hash Table Mechanics

**How HashMap Works Internally:**
```
1. Hash Function: key → hash code (int)
2. Compression: hash code → bucket index (hash % capacity)
3. Collision Resolution: 
   - Java 7-: Linked lists in buckets
   - Java 8+: Trees when bucket size > 8
```

**Load Factor and Resizing:**
- Default load factor: 0.75 (75% full before resize)
- Resize trigger: size ≥ capacity × load_factor
- New capacity: 2 × old_capacity
- All elements must be rehashed

**Visual Example:**
```
HashMap<String, Integer> map with capacity = 4

"cat" → hash=12345 → 12345 % 4 = 1 → bucket[1]
"dog" → hash=67890 → 67890 % 4 = 2 → bucket[2]  
"bat" → hash=11111 → 11111 % 4 = 3 → bucket[3]
"rat" → hash=22221 → 22221 % 4 = 1 → bucket[1] (collision!)

bucket[0]: []
bucket[1]: ["cat"→5, "rat"→8] (linked list or tree)
bucket[2]: ["dog"→3]
bucket[3]: ["bat"→7]
```

### HashSet vs HashMap Relationship
```java
// HashSet is essentially HashMap<E, Object>
public class HashSet<E> {
    private static final Object PRESENT = new Object();
    private HashMap<E, Object> map;
    
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }
}
```

---

## Problem-Solving Methodology

### Step 1: Pattern Recognition

**Frequency/Counting Problems** - Use HashMap<T, Integer>
- Keywords: "count", "frequency", "occurrences", "how many times"
- Examples: Character frequency, word count, element frequency

**Existence/Membership Problems** - Use HashSet<T>
- Keywords: "exists", "contains", "duplicate", "unique", "distinct"
- Examples: Duplicate detection, seen elements, set operations

**Complement/Pair Problems** - Use HashMap<T, Index/Data>
- Keywords: "sum to target", "pair", "complement", "two elements"
- Examples: Two Sum, finding pairs with difference

**Grouping/Categorization Problems** - Use Map<Key, List<Value>>
- Keywords: "group by", "categorize", "anagrams", "same property"
- Examples: Group anagrams, group by property

### Step 2: Choose Data Structure Decision Tree

```
Question: Do I need to store key-value relationships?
├─ YES (Use Map types)
│   ├─ Need counting? → HashMap<T, Integer>
│   ├─ Need grouping? → HashMap<Key, List<Value>>
│   ├─ Need caching? → HashMap<Input, Output>
│   └─ Need ordering? → LinkedHashMap or TreeMap
└─ NO (Use Set types)
    ├─ Just existence checking? → HashSet<T>
    ├─ Need ordering? → LinkedHashSet or TreeSet
    └─ Set operations? → HashSet<T>
```

### Step 3: Implementation Pattern Template

```java
public ReturnType solveProblem(InputType input) {
    // 1. Initialize data structure
    Map<KeyType, ValueType> map = new HashMap<>();
    // or Set<ElementType> set = new HashSet<>();
    
    // 2. First pass: Build the map/set
    for (ElementType element : input) {
        // Process and store in map/set
        KeyType key = extractKey(element);
        ValueType value = extractValue(element);
        
        // Common patterns:
        // Counting: map.put(key, map.getOrDefault(key, 0) + 1);
        // Grouping: map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        // Existence: set.add(element);
    }
    
    // 3. Second pass: Use the map/set to find answer
    for (ElementType element : input) {
        // Use map.get() or set.contains() to check
        if (map.containsKey(target) || set.contains(target)) {
            return result;
        }
    }
    
    return defaultResult;
}
```

---

## Detailed Problem Walkthroughs

### Problem 1: Two Sum (LeetCode 1) - HashMap Complement Pattern

**Problem:** Given array of integers and target, return indices of two numbers that add up to target.

**Approach Evolution:**

**Brute Force (O(n²)):**
```java
public int[] twoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[]{i, j};
            }
        }
    }
    return new int[]{-1, -1};
}
```

**HashMap Optimization (O(n)):**
```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>(); // value → index
    
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        
        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }
        
        map.put(nums[i], i);
    }
    return new int[]{-1, -1};
}
```

**Execution Trace:**
```
Input: nums = [2, 7, 11, 15], target = 9

i=0: nums[0]=2, complement=9-2=7
     map.containsKey(7)? false
     map.put(2, 0) → map = {2: 0}

i=1: nums[1]=7, complement=9-7=2  
     map.containsKey(2)? true! → return [0, 1]
     
Final: [0, 1] (indices of 2 and 7)
```

**Key Insights:**
- Store value→index mapping, not index→value
- Check for complement before adding current element
- Single pass through array
- Space-time tradeoff: O(n) space for O(n) time

### Problem 2: Group Anagrams (LeetCode 49) - HashMap Grouping Pattern

**Problem:** Group strings that are anagrams of each other.

**Approach:** Use sorted string as key to group anagrams.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();
    
    for (String str : strs) {
        // Create key by sorting characters
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        
        // Group by this key
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
    }
    
    return new ArrayList<>(groups.values());
}
```

**Execution Trace:**
```
Input: ["eat", "tea", "tan", "ate", "nat", "bat"]

str="eat": chars=['e','a','t'] → sorted=['a','e','t'] → key="aet"
          groups.computeIfAbsent("aet") → creates new ArrayList
          groups = {"aet": ["eat"]}

str="tea": chars=['t','e','a'] → sorted=['a','e','t'] → key="aet"  
          groups.get("aet").add("tea")
          groups = {"aet": ["eat", "tea"]}

str="tan": chars=['t','a','n'] → sorted=['a','n','t'] → key="ant"
          groups = {"aet": ["eat", "tea"], "ant": ["tan"]}

str="ate": key="aet" → groups = {"aet": ["eat", "tea", "ate"], "ant": ["tan"]}
str="nat": key="ant" → groups = {"aet": ["eat", "tea", "ate"], "ant": ["tan", "nat"]}  
str="bat": key="abt" → groups = {"aet": ["eat", "tea", "ate"], "ant": ["tan", "nat"], "abt": ["bat"]}

Result: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
```

**Alternative Key Generation:**
```java
// Using character frequency as key (avoids sorting)
private String getKey(String str) {
    int[] count = new int[26];
    for (char c : str.toCharArray()) {
        count[c - 'a']++;
    }
    return Arrays.toString(count);
}
```

**Time Complexity Analysis:**
- Sorting approach: O(n × k log k) where n = number of strings, k = max string length
- Frequency approach: O(n × k) but higher constant factor due to array operations

### Problem 3: Longest Substring Without Repeating Characters (LeetCode 3) - Sliding Window with HashSet

**Problem:** Find length of longest substring without repeating characters.

**Approach:** Sliding window with HashSet to track characters in current window.

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLength = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char rightChar = s.charAt(right);
        
        // Shrink window until no duplicate
        while (window.contains(rightChar)) {
            window.remove(s.charAt(left));
            left++;
        }
        
        window.add(rightChar);
        maxLength = Math.max(maxLength, right - left + 1);
    }
    
    return maxLength;
}
```

**Execution Trace:**
```
Input: s = "abcabcbb"

right=0: rightChar='a', window=∅
         window.contains('a')? false
         window.add('a') → window={'a'}
         maxLength = max(0, 0-0+1) = 1

right=1: rightChar='b', window={'a'}
         window.contains('b')? false  
         window.add('b') → window={'a','b'}
         maxLength = max(1, 1-0+1) = 2

right=2: rightChar='c', window={'a','b'}
         window.contains('c')? false
         window.add('c') → window={'a','b','c'}
         maxLength = max(2, 2-0+1) = 3

right=3: rightChar='a', window={'a','b','c'}
         window.contains('a')? true!
         WHILE LOOP:
           window.remove(s.charAt(0)='a') → window={'b','c'}
           left++ → left=1
           window.contains('a')? false → exit while
         window.add('a') → window={'b','c','a'}
         maxLength = max(3, 3-1+1) = 3

right=4: rightChar='b', window={'b','c','a'}
         window.contains('b')? true!
         WHILE LOOP:
           window.remove(s.charAt(1)='b') → window={'c','a'}
           left++ → left=2
           window.contains('b')? false → exit while
         window.add('b') → window={'c','a','b'}
         maxLength = max(3, 4-2+1) = 3

... continues similarly

Final result: 3 (substring "abc")
```

**Optimized Version with HashMap:**
```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> lastIndex = new HashMap<>();
    int left = 0, maxLength = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char rightChar = s.charAt(right);
        
        if (lastIndex.containsKey(rightChar)) {
            left = Math.max(left, lastIndex.get(rightChar) + 1);
        }
        
        lastIndex.put(rightChar, right);
        maxLength = Math.max(maxLength, right - left + 1);
    }
    
    return maxLength;
}
```

**Key Optimization:** Instead of shrinking window one by one, jump directly to position after the duplicate character.

### Problem 4: Subarray Sum Equals K (LeetCode 560) - Prefix Sum with HashMap

**Problem:** Find total number of continuous subarrays whose sum equals k.

**Approach:** Use prefix sums with HashMap to count occurrences.

**Key Insight:** If prefixSum[j] - prefixSum[i-1] = k, then subarray[i:j] sums to k.

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // Empty prefix
    
    int prefixSum = 0, count = 0;
    
    for (int num : nums) {
        prefixSum += num;
        
        // Check if (prefixSum - k) exists
        int target = prefixSum - k;
        count += prefixCount.getOrDefault(target, 0);
        
        // Add current prefix sum to map
        prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
    }
    
    return count;
}
```

**Execution Trace:**
```
Input: nums = [1, 1, 1], k = 2

Initial: prefixCount = {0: 1}, prefixSum = 0, count = 0

num=1: prefixSum = 0 + 1 = 1
       target = 1 - 2 = -1
       count += prefixCount.getOrDefault(-1, 0) = 0 + 0 = 0
       prefixCount.put(1, 1) → prefixCount = {0: 1, 1: 1}

num=1: prefixSum = 1 + 1 = 2  
       target = 2 - 2 = 0
       count += prefixCount.getOrDefault(0, 0) = 0 + 1 = 1 ✓ (subarray [1,1])
       prefixCount.put(2, 1) → prefixCount = {0: 1, 1: 1, 2: 1}

num=1: prefixSum = 2 + 1 = 3
       target = 3 - 2 = 1  
       count += prefixCount.getOrDefault(1, 0) = 1 + 1 = 2 ✓ (subarray [1,1] starting at index 1)
       prefixCount.put(3, 1) → prefixCount = {0: 1, 1: 1, 2: 1, 3: 1}

Final result: 2
```

**Subarrays found:**
- nums[0:1] = [1,1] with sum 2
- nums[1:2] = [1,1] with sum 2

**Why prefixCount.put(0, 1)?**
This handles subarrays that start from index 0. When prefixSum equals k, we need target = prefixSum - k = 0 to exist in the map.

---

## Advanced Techniques

### 1. Rolling Hash for Substring Problems

**Use Case:** Finding repeated substrings efficiently.

```java
public List<String> findRepeatedDnaSequences(String s) {
    Set<String> seen = new HashSet<>();
    Set<String> repeated = new HashSet<>();
    
    for (int i = 0; i <= s.length() - 10; i++) {
        String substring = s.substring(i, i + 10);
        if (!seen.add(substring)) {
            repeated.add(substring);
        }
    }
    
    return new ArrayList<>(repeated);
}
```

**Optimization with Rolling Hash:**
```java
public List<String> findRepeatedDnaSequences(String s) {
    if (s.length() < 10) return new ArrayList<>();
    
    Map<Character, Integer> charMap = Map.of('A', 0, 'C', 1, 'G', 2, 'T', 3);
    Set<Integer> seen = new HashSet<>();
    Set<String> repeated = new HashSet<>();
    
    int hash = 0;
    for (int i = 0; i < s.length(); i++) {
        hash = (hash * 4 + charMap.get(s.charAt(i))) & ((1 << 20) - 1); // Keep last 20 bits
        
        if (i >= 9) {
            if (!seen.add(hash)) {
                repeated.add(s.substring(i - 9, i + 1));
            }
        }
    }
    
    return new ArrayList<>(repeated);
}
```

### 2. Bidirectional HashMap for O(1) Reverse Lookup

**Use Case:** When you need to efficiently map in both directions.

```java
class BiMap<K, V> {
    private Map<K, V> keyToValue = new HashMap<>();
    private Map<V, K> valueToKey = new HashMap<>();
    
    public void put(K key, V value) {
        // Remove existing mappings if any
        if (keyToValue.containsKey(key)) {
            V oldValue = keyToValue.get(key);
            valueToKey.remove(oldValue);
        }
        if (valueToKey.containsKey(value)) {
            K oldKey = valueToKey.get(value);
            keyToValue.remove(oldKey);
        }
        
        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }
    
    public V getValue(K key) { return keyToValue.get(key); }
    public K getKey(V value) { return valueToKey.get(value); }
}
```

### 3. LRU Cache Implementation

**Problem:** Design a data structure that supports get and put in O(1) time with LRU eviction.

```java
class LRUCache {
    private int capacity;
    private Map<Integer, Node> map;
    private Node head, tail;
    
    class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        
        // Dummy head and tail
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            moveToHead(node);
            return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node newNode = new Node(key, value);
            
            if (map.size() >= capacity) {
                Node last = tail.prev;
                removeNode(last);
                map.remove(last.key);
            }
            
            addToHead(newNode);
            map.put(key, newNode);
        }
    }
    
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

**Key Design Decisions:**
- HashMap for O(1) key lookup
- Doubly linked list for O(1) insertion/deletion
- Dummy head/tail nodes to simplify edge cases
- moveToHead() for both get and put operations

---

## Performance Analysis and Optimization

### Time Complexity Summary

| Operation | HashMap | HashSet | TreeMap | TreeSet |
|-----------|---------|---------|---------|---------|
| **Average Case** | | | | |
| Insert/Put | O(1) | O(1) | O(log n) | O(log n) |
| Search/Get | O(1) | O(1) | O(log n) | O(log n) |
| Delete/Remove | O(1) | O(1) | O(log n) | O(log n) |
| **Worst Case** | | | | |
| Insert/Put | O(n) | O(n) | O(log n) | O(log n) |
| Search/Get | O(n) | O(n) | O(log n) | O(log n) |
| Delete/Remove | O(n) | O(n) | O(log n) | O(log n) |

### Space Complexity Considerations

**HashMap Memory Overhead:**
```java
// Rough memory calculation for HashMap<Integer, Integer>
// Each entry: ~32 bytes overhead + 8 bytes (Integer) + 8 bytes (Integer) = ~48 bytes
// Plus bucket array: capacity * 8 bytes (references)
// Total for 1000 entries: ~48KB + overhead

// Compare with int[] array: 1000 * 4 bytes = 4KB
```

**Optimization Strategies:**

1. **Pre-size Collections:**
```java
// ❌ Multiple resizes
Map<String, Integer> map = new HashMap<>(); // default capacity 16
for (int i = 0; i < 1000; i++) {
    map.put("key" + i, i); // Triggers resizes at 12, 24, 48, 96, 192, 384, 768
}

// ✅ Single allocation
Map<String, Integer> map = new HashMap<>(1400); // 1000 / 0.75 ≈ 1334, round up
```

2. **Use Primitive Collections (External Libraries):**
```java
// Using Eclipse Collections or GNU Trove
TIntIntHashMap primitiveMap = new TIntIntHashMap(); // No boxing overhead
```

3. **Consider Alternative Data Structures:**
```java
// For small sets of integers (< 64), use bit manipulation
long bitSet = 0L;
bitSet |= (1L << value); // Add value
boolean contains = (bitSet & (1L << value)) != 0; // Check

// For frequency counting with known range
int[] frequency = new int[26]; // For lowercase letters
frequency[c - 'a']++; // Much faster than HashMap<Character, Integer>
```

### Collision Handling Deep Dive

**Java 8+ Collision Resolution:**
```java
// Bucket transitions:
// 0-7 elements: Linked list
// 8+ elements: Red-black tree (TreeNode)
// 6 or fewer after removal: Back to linked list

// This means worst-case search becomes O(log n) instead of O(n)
```

**Hash Function Quality:**
```java
// Good hash functions minimize collisions
public class Person {
    String name;
    int age;
    
    // ❌ Poor hash function
    @Override
    public int hashCode() {
        return age; // Many people same age!
    }
    
    // ✅ Better hash function  
    @Override
    public int hashCode() {
        return Objects.hash(name, age); // Combines both fields
    }
}
```

---

## Common Pitfalls and Debug Strategies

### Pitfall 1: Mutating Keys After Insertion

```java
class MutableKey {
    int value;
    MutableKey(int value) { this.value = value; }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof MutableKey && ((MutableKey) obj).value == this.value;
    }
    
    @Override
    public int hashCode() { return value; }
}

// ❌ Dangerous pattern
MutableKey key = new MutableKey(5);
map.put(key, "value");
key.value = 10; // Changes hash code!
map.get(key); // Returns null! Key is "lost"
```

**Solution:** Use immutable keys or don't modify keys after insertion.

### Pitfall 2: Concurrent Modification

```java
// ❌ Will throw ConcurrentModificationException
for (String key : map.keySet()) {
    if (condition) {
        map.remove(key); // Modifying while iterating!
    }
}

// ✅ Safe approaches
// Option 1: Use iterator
Iterator<String> it = map.keySet().iterator();
while (it.hasNext()) {
    String key = it.next();
    if (condition) {
        it.remove(); // Safe removal
    }
}

// Option 2: Collect keys first
Set<String> toRemove = new HashSet<>();
for (String key : map.keySet()) {
    if (condition) toRemove.add(key);
}
toRemove.forEach(map::remove);

// Option 3: Use removeIf (Java 8+)
map.entrySet().removeIf(entry -> condition);
```

### Pitfall 3: Null Handling Confusion

```java
Map<String, Integer> map = new HashMap<>();
map.put("key", null); // Valid in HashMap

// ❌ NullPointerException risk
int value = map.get("key"); // NPE! null cannot be unboxed to int

// ❌ Confusing containsKey vs null value
map.put("exists", null);
map.get("exists") == null; // true, but key exists!
map.get("missing") == null; // also true, but key doesn't exist!

// ✅ Proper null handling
Integer value = map.get("key");
if (value != null) {
    int intValue = value; // Safe unboxing
}

// ✅ Distinguish between missing key and null value
if (map.containsKey("key")) {
    Integer value = map.get("key"); // Could be null
} else {
    // Key doesn't exist
}
```

### Pitfall 4: Array/Object Equality

```java
// ❌ Arrays don't work as expected for keys
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
map.put(arr1, "value1");
map.put(arr2, "value2");
System.out.println(map.size()); // 2, not 1! Different object references

// ✅ Use List or Arrays.toString()
List<Integer> list1 = Arrays.asList(1, 2, 3);
List<Integer> list2 = Arrays.asList(1, 2, 3);
map.put(list1, "value1");
map.put(list2, "value2"); 
System.out.println(map.size()); // 1, as expected

// Or
map.put(Arrays.toString(arr1), "value1");
map.put(Arrays.toString(arr2), "value2");
System.out.println(map.size()); // 1
```

---

## Interview Preparation Strategy

### Must-Know Problems (Practice Order)

**Level 1: Fundamentals**
1. **Contains Duplicate** (217) - Basic HashSet usage
2. **Valid Anagram** (242) - Character frequency counting
3. **Two Sum** (1) - Complement search pattern
4. **Intersection of Two Arrays** (349) - Set operations

**Level 2: Intermediate**
5. **Group Anagrams** (49) - Grouping by key
6. **Top K Frequent Elements** (347) - HashMap + Heap
7. **First Unique Character** (387) - Frequency + iteration
8. **Longest Substring Without Repeating Characters** (3) - Sliding window

**Level 3: Advanced**
9. **Subarray Sum Equals K** (560) - Prefix sum technique
10. **LRU Cache** (146) - HashMap + LinkedList design
11. **Design HashMap** (706) - Implement from scratch
12. **Word Pattern** (290) - Bidirectional mapping

### Problem-Solving Templates

**Template 1: Frequency Counting**
```java
public ReturnType frequencyProblem(InputType input) {
    Map<ElementType, Integer> freq = new HashMap<>();
    
    // Build frequency map
    for (ElementType element : input) {
        freq.put(element, freq.getOrDefault(element, 0) + 1);
    }
    
    // Process based on frequencies
    for (Map.Entry<ElementType, Integer> entry : freq.entrySet()) {
        if (entry.getValue() meets condition) {
            // Process this element
        }
    }
    
    return result;
}
```

**Template 2: Sliding Window with HashMap**
```java
public int slidingWindow(String s, String pattern) {
    Map<Character, Integer> patternMap = buildFrequencyMap(pattern);
    Map<Character, Integer> windowMap = new HashMap<>();
    
    int left = 0, matched = 0, result = 0;
    
    for (int right = 0; right < s.length(); right++) {
        // Expand window
        char rightChar = s.charAt(right);
        windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) + 1);
        
        if (patternMap.containsKey(rightChar) && 
            windowMap.get(rightChar).equals(patternMap.get(rightChar))) {
            matched++;
        }
        
        // Contract window
        while (matched == patternMap.size()) {
            // Update result
            result = Math.max(result, right - left + 1);
            
            char leftChar = s.charAt(left);
            windowMap.put(leftChar, windowMap.get(leftChar) - 1);
            if (patternMap.containsKey(leftChar) && 
                windowMap.get(leftChar) < patternMap.get(leftChar)) {
                matched--;
            }
            left++;
        }
    }
    
    return result;
}
```

**Template 3: Two Sum Variants**
```java
public List<List<Integer>> twoSumAllPairs(int[] nums, int target) {
    Map<Integer, List<Integer>> valueToIndices = new HashMap<>();
    List<List<Integer>> result = new ArrayList<>();
    
    // Build value to indices mapping
    for (int i = 0; i < nums.length; i++) {
        valueToIndices.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
    }
    
    Set<Integer> processed = new HashSet<>();
    for (int num : nums) {
        if (processed.contains(num)) continue;
        
        int complement = target - num;
        if (valueToIndices.containsKey(complement)) {
            // Handle same number vs different numbers
            if (num == complement) {
                List<Integer> indices = valueToIndices.get(num);
                if (indices.size() >= 2) {
                    // Add all pairs from same value
                    for (int i = 0; i < indices.size(); i++) {
                        for (int j = i + 1; j < indices.size(); j++) {
                            result.add(Arrays.asList(indices.get(i), indices.get(j)));
                        }
                    }
                }
            } else if (num < complement) { // Avoid duplicates
                List<Integer> indices1 = valueToIndices.get(num);
                List<Integer> indices2 = valueToIndices.get(complement);
                for (int i : indices1) {
                    for (int j : indices2) {
                        result.add(Arrays.asList(i, j));
                    }
                }
            }
        }
        processed.add(num);
    }
    
    return result;
}
```

### Interview Tips

**Time Management:**
- Start with brute force approach (shows problem understanding)
- Identify optimization opportunities (O(n²) → O(n) with HashMap)
- Code the optimized solution
- Test with edge cases

**Communication Strategy:**
```
1. "I notice this problem involves [frequency counting/duplicate detection/complement search]"
2. "This suggests using a HashMap/HashSet because..."
3. "Let me trace through a small example to verify the approach"
4. "The time complexity is O(n) because we iterate once, and HashMap operations are O(1)"
5. "Space complexity is O(k) where k is the number of unique elements"
```

**Edge Cases to Discuss:**
- Empty input
- Single element
- All elements the same
- No solution exists
- Multiple valid solutions

---

## Advanced Topics

### Custom Hash Functions

**Polynomial Rolling Hash (for strings):**
```java
public class RollingHash {
    private static final long BASE = 31;
    private static final long MOD = 1_000_000_007;
    
    public long computeHash(String s) {
        long hash = 0, power = 1;
        for (char c : s.toCharArray()) {
            hash = (hash + (c - 'a' + 1) * power) % MOD;
            power = (power * BASE) % MOD;
        }
        return hash;
    }
    
    public long rollingHash(String s, int windowSize) {
        if (s.length() < windowSize) return -1;
        
        long hash = 0, power = 1;
        
        // Compute initial hash
        for (int i = 0; i < windowSize; i++) {
            hash = (hash + (s.charAt(i) - 'a' + 1) * power) % MOD;
            if (i < windowSize - 1) power = (power * BASE) % MOD;
        }
        
        // Roll the hash
        for (int i = windowSize; i < s.length(); i++) {
            // Remove leftmost character
            hash = (hash - (s.charAt(i - windowSize) - 'a' + 1) + MOD) % MOD;
            hash = (hash * modInverse(BASE, MOD)) % MOD;
            
            // Add rightmost character
            hash = (hash + (s.charAt(i) - 'a' + 1) * power) % MOD;
        }
        
        return hash;
    }
}
```

### Memory-Efficient Alternatives

**BitSet for Boolean Sets:**
```java
// Instead of HashSet<Integer> for small ranges
BitSet seen = new BitSet(1000); // Much more memory efficient
seen.set(42); // Add 42
boolean contains = seen.get(42); // Check
seen.clear(42); // Remove
```

**Array-based "HashMap" for Known Range:**
```java
// For counting characters a-z
int[] charCount = new int[26]; // vs HashMap<Character, Integer>
charCount[c - 'a']++; // Much faster than map.put()
```

**Bloom Filter for Approximate Membership:**
```java
// When false positives OK but false negatives not acceptable
// Uses multiple hash functions and bit array
// Space efficient: ~10 bits per element for 1% false positive rate
```

---

## Summary and Next Steps

### Key Takeaways

1. **HashMap/HashSet provide O(1) average-case operations** - essential for interview problems
2. **Pattern recognition is crucial** - frequency counting, duplicate detection, complement search, grouping
3. **Space-time tradeoffs** - often trade O(n) space for O(n) time improvement
4. **Edge case handling** - null values, empty inputs, concurrent modification
5. **Java 8+ improvements** - collision resolution with trees, better worst-case performance

### Practice Progression

**Week 1:** Master basic patterns
- Frequency counting problems
- Duplicate detection
- Simple complement search

**Week 2:** Intermediate techniques
- Sliding window with HashMap
- Grouping and categorization
- Set operations

**Week 3:** Advanced applications
- Prefix sum techniques
- Design problems (LRU cache)
- Custom hash functions

**Week 4:** Integration and optimization
- Combine with other patterns (heap, sliding window)
- Performance optimization
- Mock interviews

### Further Study

**Books:**
- "Effective Java" by Joshua Bloch (Item 9: Obey the general contract when overriding equals)
- "Introduction to Algorithms" by CLRS (Chapter 11: Hash Tables)

**Practice Platforms:**
- LeetCode: HashMap tag, explore all difficulties
- HackerRank: Hash Tables section
- GeeksforGeeks: Hashing articles

**Advanced Topics to Explore:**
- Consistent hashing (distributed systems)
- Cuckoo hashing (guaranteed O(1) worst case)
- Robin Hood hashing (open addressing optimization)
- Locality-sensitive hashing (nearest neighbor search)

The HashMap and Set pattern forms the foundation for many advanced algorithms and system design concepts. Master these basics, and you'll find complex problems become much more manageable!