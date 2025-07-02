# HashMap and Set - Flashcards

## Core Concepts

**Q: What is the average time complexity for HashMap operations (get, put, remove)?**
<details>
<summary>Click to reveal answer</summary>

**A: O(1) average case, O(n) worst case**

The average case assumes a good hash function with minimal collisions. Worst case occurs when many elements hash to the same bucket, creating a long chain or requiring extensive probing.
</details>

---

**Q: When should you use HashMap vs HashSet?**
<details>
<summary>Click to reveal answer</summary>

**A:**
- **HashMap**: When you need key-value pairs (counting, mapping, caching)
- **HashSet**: When you only need to track existence/uniqueness of elements

Examples:
- Frequency counting → HashMap<T, Integer>
- Duplicate detection → HashSet<T>
</details>

---

**Q: What's the difference between HashMap and LinkedHashMap?**
<details>
<summary>Click to reveal answer</summary>

**A:**
- **HashMap**: No guaranteed order of elements
- **LinkedHashMap**: Maintains insertion order (or access order if configured)

LinkedHashMap uses ~25% more memory due to additional pointers but provides predictable iteration order.
</details>

---

**Q: How do you safely handle null values when using HashMap?**
<details>
<summary>Click to reveal answer</summary>

**A:**
```java
// ❌ Risk of NullPointerException
int value = map.get(key); // NPE if key doesn't exist

// ✅ Safe approaches
Integer value = map.get(key);
if (value != null) { /* use value */ }

// Or use getOrDefault
int value = map.getOrDefault(key, defaultValue);

// Or use containsKey check
if (map.containsKey(key)) {
    int value = map.get(key);
}
```
</details>

---

## Pattern Recognition

**Q: You need to find if any element appears more than once in an array. What data structure and approach?**
<details>
<summary>Click to reveal answer</summary>

**A: HashSet for O(n) solution**
```java
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) { // add() returns false if already exists
            return true;
        }
    }
    return false;
}
```

Alternative: Sort first O(n log n), but HashSet is better.
</details>

---

**Q: You need to find two numbers in an array that sum to a target. What data structure and approach?**
<details>
<summary>Click to reveal answer</summary>

**A: HashMap to store complements**
```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>(); // value -> index
    
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

Time: O(n), Space: O(n)
</details>

---

**Q: You need to count the frequency of each character in a string. What approach?**
<details>
<summary>Click to reveal answer</summary>

**A: HashMap for character counting**
```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}

// Alternative using merge
for (char c : s.toCharArray()) {
    freq.merge(c, 1, Integer::sum);
}
```
</details>

---

**Q: You need to group strings that are anagrams of each other. What approach?**
<details>
<summary>Click to reveal answer</summary>

**A: HashMap with sorted string as key**
```java
Map<String, List<String>> groups = new HashMap<>();
for (String str : strs) {
    char[] chars = str.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
}
```

Alternative key: frequency map representation
</details>

---

## Advanced Patterns

**Q: How do you implement a sliding window with character frequency tracking?**
<details>
<summary>Click to reveal answer</summary>

**A: Two HashMaps - one for pattern, one for window**
```java
Map<Character, Integer> patternCount = new HashMap<>();
Map<Character, Integer> windowCount = new HashMap<>();

// Build pattern frequency
for (char c : pattern.toCharArray()) {
    patternCount.put(c, patternCount.getOrDefault(c, 0) + 1);
}

int left = 0, matched = 0;
for (int right = 0; right < s.length(); right++) {
    char rightChar = s.charAt(right);
    windowCount.put(rightChar, windowCount.getOrDefault(rightChar, 0) + 1);
    
    if (patternCount.containsKey(rightChar) && 
        windowCount.get(rightChar).equals(patternCount.get(rightChar))) {
        matched++;
    }
    
    // Shrink window when condition met
    while (matched == patternCount.size()) {
        // Process valid window
        char leftChar = s.charAt(left);
        windowCount.put(leftChar, windowCount.get(leftChar) - 1);
        if (patternCount.containsKey(leftChar) && 
            windowCount.get(leftChar) < patternCount.get(leftChar)) {
            matched--;
        }
        left++;
    }
}
```
</details>

---

**Q: How do you handle prefix sum problems with HashMap?**
<details>
<summary>Click to reveal answer</summary>

**A: Store cumulative sum frequencies, check for target differences**
```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // Important: empty prefix
    
    int prefixSum = 0, count = 0;
    for (int num : nums) {
        prefixSum += num;
        // If (prefixSum - k) exists, we found a subarray
        count += prefixCount.getOrDefault(prefixSum - k, 0);
        prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
    }
    return count;
}
```

Key insight: prefixSum[j] - prefixSum[i-1] = k means subarray[i:j] sums to k
</details>

---

## Common Mistakes

**Q: What's wrong with this code for checking anagrams?**
```java
public boolean isAnagram(String s, String t) {
    Map<Character, Integer> count = new HashMap<>();
    for (char c : s.toCharArray()) {
        count.put(c, count.get(c) + 1); // What's wrong here?
    }
    // ... rest of the code
}
```
<details>
<summary>Click to reveal answer</summary>

**A: NullPointerException when character doesn't exist in map**

Fixed version:
```java
for (char c : s.toCharArray()) {
    count.put(c, count.getOrDefault(c, 0) + 1); // Safe
}
```

Always use `getOrDefault()` or check `containsKey()` first.
</details>

---

**Q: What's wrong with modifying a HashMap while iterating?**
```java
for (Integer key : map.keySet()) {
    if (key % 2 == 0) {
        map.remove(key); // What's wrong?
    }
}
```
<details>
<summary>Click to reveal answer</summary>

**A: ConcurrentModificationException**

Fixed version:
```java
Iterator<Integer> iterator = map.keySet().iterator();
while (iterator.hasNext()) {
    Integer key = iterator.next();
    if (key % 2 == 0) {
        iterator.remove(); // Safe
    }
}

// Or collect keys to remove first
Set<Integer> toRemove = new HashSet<>();
for (Integer key : map.keySet()) {
    if (key % 2 == 0) {
        toRemove.add(key);
    }
}
toRemove.forEach(map::remove);
```
</details>

---

**Q: Why doesn't this work for using arrays as HashMap keys?**
```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
map.put(arr1, "value1");
map.put(arr2, "value2");
System.out.println(map.size()); // What prints?
```
<details>
<summary>Click to reveal answer</summary>

**A: Prints 2, not 1**

Arrays use reference equality, not content equality for hashing.

Solutions:
```java
// Use List instead
List<Integer> list1 = Arrays.asList(1, 2, 3);
List<Integer> list2 = Arrays.asList(1, 2, 3);
map.put(list1, "value1");
map.put(list2, "value2");
System.out.println(map.size()); // Prints 1

// Or use Arrays.toString() as key
map.put(Arrays.toString(arr1), "value1");
map.put(Arrays.toString(arr2), "value2");
System.out.println(map.size()); // Prints 1
```
</details>

---

## Performance Questions

**Q: When might HashMap performance degrade to O(n)?**
<details>
<summary>Click to reveal answer</summary>

**A: When hash collisions are frequent**

Causes:
- Poor hash function implementation
- Many elements mapping to same bucket
- Adversarial input designed to cause collisions

Java 8+ improvement: Buckets convert to trees when chain length > 8, giving O(log n) worst case instead of O(n).
</details>

---

**Q: How do you optimize HashMap for known size?**
<details>
<summary>Click to reveal answer</summary>

**A: Set initial capacity to avoid resizing**
```java
// If you know you'll have ~1000 elements
int expectedSize = 1000;
Map<String, Integer> map = new HashMap<>(expectedSize * 4 / 3 + 1);

// Or use slightly larger power of 2
Map<String, Integer> map = new HashMap<>(2048); // Next power of 2 after 1000*4/3
```

Default load factor is 0.75, so capacity should be expectedSize / 0.75.
</details>

---

**Q: What's the memory overhead of HashMap vs array?**
<details>
<summary>Click to reveal answer</summary>

**A: Significant overhead for small objects**

HashMap overhead per entry:
- ~32 bytes for internal structure
- References to key and value objects
- Hash bucket arrays

Array: Just the object references (~8 bytes each on 64-bit JVM)

Rule of thumb: HashMap uses 3-4x more memory than array for primitive-like data.
</details>

---

## Interview Scenarios

**Q: "Find the first non-repeating character in a string"**
<details>
<summary>Click to reveal answer</summary>

**A: Two-pass solution with HashMap**
```java
public int firstUniqChar(String s) {
    // Pass 1: Count frequencies
    Map<Character, Integer> count = new HashMap<>();
    for (char c : s.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) + 1);
    }
    
    // Pass 2: Find first with count = 1
    for (int i = 0; i < s.length(); i++) {
        if (count.get(s.charAt(i)) == 1) {
            return i;
        }
    }
    return -1;
}
```

Time: O(n), Space: O(k) where k is number of unique characters
</details>

---

**Q: "Design a data structure that supports insert, delete, and getRandom in O(1)"**
<details>
<summary>Click to reveal answer</summary>

**A: HashMap + ArrayList combination**
```java
class RandomizedSet {
    private List<Integer> nums;
    private Map<Integer, Integer> indices; // value -> index in nums
    
    public RandomizedSet() {
        nums = new ArrayList<>();
        indices = new HashMap<>();
    }
    
    public boolean insert(int val) {
        if (indices.containsKey(val)) return false;
        
        indices.put(val, nums.size());
        nums.add(val);
        return true;
    }
    
    public boolean remove(int val) {
        if (!indices.containsKey(val)) return false;
        
        int index = indices.get(val);
        int lastElement = nums.get(nums.size() - 1);
        
        // Swap with last element
        nums.set(index, lastElement);
        indices.put(lastElement, index);
        
        // Remove last element
        nums.remove(nums.size() - 1);
        indices.remove(val);
        return true;
    }
    
    public int getRandom() {
        return nums.get((int)(Math.random() * nums.size()));
    }
}
```

Key insight: Use ArrayList for O(1) random access, HashMap for O(1) lookups.
</details>

---

**Q: "Check if two strings are anagrams"**
<details>
<summary>Click to reveal answer</summary>

**A: Multiple approaches with HashMap**
```java
// Approach 1: Count and compare
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    
    Map<Character, Integer> count = new HashMap<>();
    
    // Add counts for s
    for (char c : s.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) + 1);
    }
    
    // Subtract counts for t
    for (char c : t.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) - 1);
        if (count.get(c) == 0) {
            count.remove(c);
        }
    }
    
    return count.isEmpty();
}

// Approach 2: Two frequency maps
public boolean isAnagram(String s, String t) {
    return getFrequencyMap(s).equals(getFrequencyMap(t));
}

// Approach 3: Sort strings (not using HashMap)
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    
    char[] sChars = s.toCharArray();
    char[] tChars = t.toCharArray();
    Arrays.sort(sChars);
    Arrays.sort(tChars);
    return Arrays.equals(sChars, tChars);
}
```

HashMap approach: Time O(n), Space O(k)
Sort approach: Time O(n log n), Space O(1)
</details>

---

## Edge Cases & Testing

**Q: What edge cases should you test for HashMap/Set problems?**
<details>
<summary>Click to reveal answer</summary>

**A: Comprehensive edge case list**

**Input size:**
- Empty collection
- Single element
- Two elements
- Large input (performance)

**Content patterns:**
- All elements the same
- All elements unique
- Mix of duplicates and uniques

**Special values:**
- Null values (if allowed)
- Empty strings
- Zero values
- Negative numbers

**Boundary conditions:**
- First/last elements
- Middle elements
- Elements not in collection

**Example test cases for "contains duplicate":**
```java
// Edge cases
assert !containsDuplicate(new int[]{}); // empty
assert !containsDuplicate(new int[]{1}); // single
assert containsDuplicate(new int[]{1,1}); // immediate duplicate
assert !containsDuplicate(new int[]{1,2}); // no duplicate
assert containsDuplicate(new int[]{1,2,1}); // distant duplicate
```
</details>

---

**Q: How do you debug HashMap-related issues?**
<details>
<summary>Click to reveal answer</summary>

**A: Systematic debugging approach**

**1. Print map contents:**
```java
System.out.println("Map contents:");
map.forEach((k, v) -> System.out.println(k + " -> " + v));
```

**2. Check key existence vs null values:**
```java
System.out.println("Contains key: " + map.containsKey(key));
System.out.println("Value: " + map.get(key));
System.out.println("Is value null: " + (map.get(key) == null));
```

**3. Verify hash/equals methods:**
```java
Object key1 = ...;
Object key2 = ...;
System.out.println("Keys equal: " + key1.equals(key2));
System.out.println("Hash codes: " + key1.hashCode() + " vs " + key2.hashCode());
```

**4. Track modifications:**
```java
int sizeBefore = map.size();
map.put(key, value);
int sizeAfter = map.size();
System.out.println("Size changed: " + (sizeBefore != sizeAfter));
```

**5. Common issues to check:**
- Using wrong key type (String vs char)
- Modifying mutable keys after insertion
- Not handling null values properly
- Off-by-one errors in indexing
</details>

---

## Summary Review

**Q: What's the decision tree for choosing between HashMap, HashSet, TreeMap, TreeSet?**
<details>
<summary>Click to reveal answer</summary>

**A: Decision flow chart**

```
Do you need key-value pairs?
├─ YES: Map types
│   ├─ Need sorted order? → TreeMap O(log n)
│   ├─ Need insertion order? → LinkedHashMap O(1)
│   └─ Just need fast access? → HashMap O(1)
└─ NO: Set types
    ├─ Need sorted order? → TreeSet O(log n)
    ├─ Need insertion order? → LinkedHashSet O(1)
    └─ Just need fast access? → HashSet O(1)
```

**Performance summary:**
- HashMap/HashSet: O(1) average, best for most cases
- TreeMap/TreeSet: O(log n), when you need sorting
- LinkedHashMap/LinkedHashSet: O(1) + predictable iteration order
</details>

---

**Q: Name 5 classic problems that use HashMap/HashSet patterns**
<details>
<summary>Click to reveal answer</summary>

**A: Essential problems to master**

1. **Two Sum** - HashMap for complement lookup
2. **Group Anagrams** - HashMap for categorization
3. **Longest Substring Without Repeating Characters** - HashSet for sliding window
4. **Valid Anagram** - HashMap for frequency counting
5. **Contains Duplicate** - HashSet for duplicate detection

**Bonus problems:**
- Top K Frequent Elements (HashMap + Heap)
- First Unique Character (HashMap frequency)
- Intersection of Two Arrays (HashSet operations)
- Subarray Sum Equals K (HashMap prefix sums)
- LRU Cache (LinkedHashMap or HashMap + doubly linked list)
</details>