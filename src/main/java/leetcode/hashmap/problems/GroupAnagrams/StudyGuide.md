# Group Anagrams - Detailed Study Guide

## Understanding Your Implementation Issues

Let's debug your original code step by step:

### Issue 1: Arrays.sort() Returns Void
```java
// Your code:
int[] sortedStr = Arrays.sort(strs[i].toCharArray());  // ❌ COMPILE ERROR

// What happens:
// 1. Arrays.sort() modifies array in-place and returns void
// 2. You can't assign void to int[]
// 3. Also, toCharArray() returns char[], not int[]

// Fix:
char[] charArray = strs[i].toCharArray();
Arrays.sort(charArray);  // Sorts in place
String sortedStr = new String(charArray);
```

### Issue 2: Stream Collectors Typo
```java
// Your code:
.collect(Collectors.joinging(""));  // ❌ Typo: "joinging"

// Should be:
.collect(Collectors.joining(""));

// But even with fix, wrong approach because sortedStr isn't int[]
```

### Issue 3: Merge Function Logic Error
```java
// Your code:
strsToIndexes.merge(sortedStrCopy, new ArrayList<>(List.of(i)),
    (oldList, newList) -> {
        oldList.add(newList);  // ❌ Adding List<Integer> as element
        return oldList;
    });

// Problem: newList is List<Integer>, not Integer
// You're adding [i] to list, not i

// Better approach:
strsToIndexes.computeIfAbsent(sortedStr, k -> new ArrayList<>()).add(i);
```

## Step-by-Step Solution Walkthrough

### Example: ["eat", "tea", "tan", "ate", "nat", "bat"]

```
Step 1: Process each string
"eat" → sort → "aet" → map["aet"] = ["eat"]
"tea" → sort → "aet" → map["aet"] = ["eat", "tea"]
"tan" → sort → "ant" → map["ant"] = ["tan"]
"ate" → sort → "aet" → map["aet"] = ["eat", "tea", "ate"]
"nat" → sort → "ant" → map["ant"] = ["tan", "nat"]
"bat" → sort → "abt" → map["abt"] = ["bat"]

Step 2: Extract values
map.values() = [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
```

## Visual Representation

```
Original Array:
[0]"eat" [1]"tea" [2]"tan" [3]"ate" [4]"nat" [5]"bat"

Sorting Process:
"eat" → ['e','a','t'] → ['a','e','t'] → "aet"
"tea" → ['t','e','a'] → ['a','e','t'] → "aet"
"tan" → ['t','a','n'] → ['a','n','t'] → "ant"

HashMap Building:
Key     | Values
--------|------------------
"aet"   | ["eat", "tea", "ate"]
"ant"   | ["tan", "nat"]
"abt"   | ["bat"]
```

## Different Key Generation Strategies

### 1. Sorting (O(K log K) per string)
```java
char[] chars = str.toCharArray();
Arrays.sort(chars);
String key = new String(chars);
// "eat" → "aet"
```

### 2. Frequency Count (O(K) per string)
```java
int[] freq = new int[26];
for (char c : str.toCharArray()) {
    freq[c - 'a']++;
}
// "eat" → "a1e1t1" or [1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0]
```

### 3. Prime Product (O(K) per string)
```java
int[] primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
long key = 1;
for (char c : str.toCharArray()) {
    key *= primes[c - 'a'];
}
// "eat" → 2(a) * 11(e) * 71(t) = 1562
```

## Why Your Index Approach Works (When Fixed)

Your idea to map to indices is valid! Here's why:

**Advantages:**
- Preserves original array order in output
- Could save memory if strings are large

**Trade-offs:**
- Extra step to convert indices to strings
- More complex code

**Fixed Version:**
```java
Map<String, List<Integer>> strsToIndexes = new HashMap<>();

// Build index map
for (int i = 0; i < strs.length; i++) {
    char[] chars = strs[i].toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);

    // Clean way to add to list
    strsToIndexes.computeIfAbsent(key, k -> new ArrayList<>()).add(i);
}

// Convert indices to strings
List<List<String>> result = new ArrayList<>();
for (List<Integer> indices : strsToIndexes.values()) {
    List<String> group = new ArrayList<>();
    for (int idx : indices) {
        group.add(strs[idx]);
    }
    result.add(group);
}
```

## Common HashMap Methods for This Problem

```java
// computeIfAbsent - Create list if key doesn't exist
map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

// getOrDefault - Get existing or default value
List<String> list = map.getOrDefault(key, new ArrayList<>());
list.add(value);
map.put(key, list);

// merge - Combine values (more complex for lists)
map.merge(key, new ArrayList<>(List.of(value)),
    (oldList, newList) -> {
        oldList.addAll(newList);  // Note: addAll, not add
        return oldList;
    });
```

## Edge Cases to Consider

1. **Empty String:** `[""]` → `[[""]]`
2. **Single Character:** `["a"]` → `[["a"]]`
3. **All Same Anagrams:** `["abc","bca","cab"]` → `[["abc","bca","cab"]]`
4. **No Anagrams:** `["a","b","c"]` → `[["a"],["b"],["c"]]`

## Performance Comparison

| Approach | Time per String | Space | Pros | Cons |
|----------|----------------|-------|------|------|
| Sorting | O(K log K) | O(K) | Simple, intuitive | Slower for long strings |
| Frequency | O(K) | O(1)* | Fast, no sorting | Complex key generation |
| Prime | O(K) | O(1) | Elegant math | Overflow risk |
| Your Index | O(K log K) | O(N) | Preserves order | Extra conversion step |

*O(1) for the frequency array itself, O(K) for key string

## Interview Discussion Points

1. **Why sorting works:** "Anagrams have the same characters, just rearranged. Sorting gives us a canonical form."

2. **Optimization:** "For very long strings, we could use frequency counting to avoid O(K log K) sorting."

3. **Your approach:** "I initially thought about mapping to indices to preserve array positions, which works but adds complexity."

4. **Trade-offs:** "Direct string mapping is simpler, but index mapping could be useful if we need to maintain original ordering."

## Key Takeaways

1. **Arrays.sort() modifies in place** - Returns void, not the array
2. **computeIfAbsent is cleaner** - Better than merge for list initialization
3. **Your instinct was good** - Index mapping is valid, just needs proper implementation
4. **Simpler is often better** - Direct string mapping reduces complexity

## Practice Problems
1. Start: **242. Valid Anagram** (single pair check)
2. Current: **49. Group Anagrams** (grouping)
3. Next: **438. Find All Anagrams in a String** (sliding window)
4. Advanced: **30. Substring with Concatenation** (complex grouping)