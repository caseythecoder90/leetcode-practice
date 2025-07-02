# HashMap and Set - Cheat Sheet

## Quick Recognition Guide

| Keywords/Patterns | Data Structure | Example Problems |
|-------------------|---------------|------------------|
| "count", "frequency", "occurrences" | HashMap<T, Integer> | Valid Anagram, Top K Frequent |
| "unique", "duplicate", "distinct" | HashSet<T> | Contains Duplicate, Longest Substring |
| "two sum", "pair", "complement" | HashMap<Integer, Integer> | Two Sum, 3Sum |
| "group by", "categorize" | Map<Key, List<Value>> | Group Anagrams, Phone Directory |
| "cache", "memoization" | HashMap<State, Result> | LRU Cache, Fibonacci with memo |
| "sliding window" + counting | Map<T, Integer> | Minimum Window Substring |

## Essential Code Templates

### 1. Frequency Counting Template
```java
public Map<T, Integer> countFrequency(T[] items) {
    Map<T, Integer> freq = new HashMap<>();
    for (T item : items) {
        freq.put(item, freq.getOrDefault(item, 0) + 1);
        // Alternative: freq.merge(item, 1, Integer::sum);
    }
    return freq;
}

// For characters in string
Map<Character, Integer> charCount = new HashMap<>();
for (char c : s.toCharArray()) {
    charCount.put(c, charCount.getOrDefault(c, 0) + 1);
}
```

### 2. Two Sum Pattern Template
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
    return new int[]{-1, -1}; // Not found
}
```

### 3. Duplicate Detection Template
```java
// Find if any duplicates exist
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) { // add() returns false if already exists
            return true;
        }
    }
    return false;
}

// Find the first duplicate
public int findDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (seen.contains(num)) {
            return num;
        }
        seen.add(num);
    }
    return -1; // No duplicate
}
```

### 4. Grouping/Categorization Template
```java
public Map<String, List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();
    
    for (String str : strs) {
        String key = getKey(str); // Your categorization logic
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
    }
    return groups;
}

// Alternative without computeIfAbsent
for (String str : strs) {
    String key = getKey(str);
    if (!groups.containsKey(key)) {
        groups.put(key, new ArrayList<>());
    }
    groups.get(key).add(str);
}
```

### 5. Sliding Window with HashMap Template
```java
public int slidingWindowTemplate(String s, String pattern) {
    Map<Character, Integer> patternCount = new HashMap<>();
    Map<Character, Integer> windowCount = new HashMap<>();
    
    // Build pattern map
    for (char c : pattern.toCharArray()) {
        patternCount.put(c, patternCount.getOrDefault(c, 0) + 1);
    }
    
    int left = 0, matched = 0, result = Integer.MAX_VALUE;
    
    for (int right = 0; right < s.length(); right++) {
        char rightChar = s.charAt(right);
        windowCount.put(rightChar, windowCount.getOrDefault(rightChar, 0) + 1);
        
        // Update matched count logic here
        if (patternCount.containsKey(rightChar) && 
            windowCount.get(rightChar).equals(patternCount.get(rightChar))) {
            matched++;
        }
        
        // Shrink window when valid
        while (matched == patternCount.size()) {
            result = Math.min(result, right - left + 1);
            
            char leftChar = s.charAt(left);
            windowCount.put(leftChar, windowCount.get(leftChar) - 1);
            if (patternCount.containsKey(leftChar) && 
                windowCount.get(leftChar) < patternCount.get(leftChar)) {
                matched--;
            }
            left++;
        }
    }
    return result == Integer.MAX_VALUE ? -1 : result;
}
```

### 6. Set Operations Template
```java
// Union
Set<T> union = new HashSet<>(set1);
union.addAll(set2);

// Intersection
Set<T> intersection = new HashSet<>(set1);
intersection.retainAll(set2);

// Difference (set1 - set2)
Set<T> difference = new HashSet<>(set1);
difference.removeAll(set2);

// Check if subset
boolean isSubset = set1.containsAll(set2);
```

## Common Patterns by Problem Type

### String Problems
```java
// Anagram check
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    
    Map<Character, Integer> count = new HashMap<>();
    for (char c : s.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) + 1);
    }
    for (char c : t.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) - 1);
        if (count.get(c) == 0) count.remove(c);
    }
    return count.isEmpty();
}

// First unique character
public int firstUniqChar(String s) {
    Map<Character, Integer> count = new HashMap<>();
    for (char c : s.toCharArray()) {
        count.put(c, count.getOrDefault(c, 0) + 1);
    }
    for (int i = 0; i < s.length(); i++) {
        if (count.get(s.charAt(i)) == 1) {
            return i;
        }
    }
    return -1;
}
```

### Array Problems
```java
// Majority element (Boyer-Moore alternative)
public int majorityElement(int[] nums) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) {
        count.put(num, count.getOrDefault(num, 0) + 1);
        if (count.get(num) > nums.length / 2) {
            return num;
        }
    }
    return -1;
}

// Intersection of two arrays
public int[] intersect(int[] nums1, int[] nums2) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums1) {
        count.put(num, count.getOrDefault(num, 0) + 1);
    }
    
    List<Integer> result = new ArrayList<>();
    for (int num : nums2) {
        if (count.getOrDefault(num, 0) > 0) {
            result.add(num);
            count.put(num, count.get(num) - 1);
        }
    }
    return result.stream().mapToInt(i -> i).toArray();
}
```

### Subarray/Prefix Sum Problems
```java
// Subarray sum equals K
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1); // Important: empty prefix
    
    int prefixSum = 0, count = 0;
    for (int num : nums) {
        prefixSum += num;
        count += prefixCount.getOrDefault(prefixSum - k, 0);
        prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
    }
    return count;
}
```

## Performance Tips

### Memory Optimization
```java
// Use primitive maps when possible (external libraries)
TIntIntHashMap primitiveMap = new TIntIntHashMap(); // GNU Trove

// For small sets, consider bit manipulation
int bitSet = 0;
bitSet |= (1 << value); // Add value
boolean contains = (bitSet & (1 << value)) != 0; // Check

// Initial capacity for known size
Map<String, Integer> map = new HashMap<>(expectedSize * 4 / 3 + 1);
```

### Avoiding Autoboxing
```java
// ❌ Lots of autoboxing
Map<Integer, Integer> map = new HashMap<>();
int value = map.get(key); // NPE risk

// ✅ Better approach
Map<Integer, Integer> map = new HashMap<>();
Integer value = map.get(key);
int result = (value != null) ? value : defaultValue;

// Or use getOrDefault
int result = map.getOrDefault(key, defaultValue);
```

## Debugging Quick Checks

### 1. HashMap Issues
```java
// Print map contents
map.forEach((k, v) -> System.out.println(k + " -> " + v));

// Check if key exists vs null value
boolean hasKey = map.containsKey(key);
boolean hasValue = map.get(key) != null;
```

### 2. HashSet Issues
```java
// Check set contents
set.forEach(System.out::println);

// Verify add operation
boolean added = set.add(element); // false if already existed
```

### 3. Common Gotchas
```java
// String vs char confusion
map.put('a', 1);     // char key
map.put("a", 1);     // String key - different!

// Array as key (doesn't work as expected)
int[] arr = {1, 2, 3};
map.put(arr, value); // Uses object reference, not content
// Use Arrays.toString(arr) or List instead

// equals() and hashCode() consistency
// If a.equals(b), then a.hashCode() must equal b.hashCode()
```

## Memory Complexity Quick Reference

| Data Structure | Space per Element | Load Factor | Resize Threshold |
|---------------|-------------------|-------------|------------------|
| HashMap | ~32 bytes + key/value | 0.75 | 75% capacity |
| HashSet | ~32 bytes + element | 0.75 | 75% capacity |
| LinkedHashMap | ~40 bytes + key/value | 0.75 | 75% capacity |
| TreeMap | ~40 bytes + key/value | N/A | No resizing |

## Problem-Solving Workflow

1. **Identify the operation you need most**: lookup, count, group, detect duplicates
2. **Choose HashMap vs HashSet**: key-value pairs vs just membership
3. **Consider ordering requirements**: HashMap (no order), LinkedHashMap (insertion order), TreeMap (sorted)
4. **Handle edge cases**: empty input, single element, all same elements
5. **Optimize**: single pass vs multiple passes, space vs time tradeoffs