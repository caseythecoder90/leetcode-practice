# 49. Group Anagrams

## Problem Description
Given an array of strings strs, group the anagrams together. You can return the answer in any order.

### Constraints:
- 1 <= strs.length <= 10^4
- 0 <= strs[i].length <= 100
- strs[i] consists of lowercase English letters

## Approach Analysis

### Approach 1: Sort String as Key (Recommended)
**Time Complexity:** O(N * K log K) where N is number of strings, K is max length
**Space Complexity:** O(N * K)

**Algorithm:**
1. For each string, sort its characters
2. Use sorted string as HashMap key
3. Group original strings with same sorted key

**Why This Works:** Anagrams have identical sorted forms

### Approach 2: Character Frequency as Key
**Time Complexity:** O(N * K)
**Space Complexity:** O(N * K)

**Algorithm:**
1. Count character frequencies for each string
2. Create unique frequency signature as key
3. Group strings with same frequency signature

**Advantage:** Avoids sorting, theoretically faster for long strings

### Approach 3: Index Mapping (Your Original Approach)
**Time Complexity:** O(N * K log K)
**Space Complexity:** O(N)

**Algorithm:**
1. Map sorted strings to indices
2. Collect strings at those indices
3. Build result from index groups

**Note:** More complex but demonstrates good problem-solving thinking

### Approach 4: Prime Number Product
**Time Complexity:** O(N * K)
**Space Complexity:** O(N * K)

**Algorithm:**
1. Assign prime numbers to each letter
2. Product of primes uniquely identifies anagram group
3. Use product as HashMap key

**Risk:** Overflow for very long strings

## Common Mistakes in Your Original Code

1. **Arrays.sort() returns void:**
```java
// WRONG
int[] sortedStr = Arrays.sort(strs[i].toCharArray());

// CORRECT
char[] charArray = strs[i].toCharArray();
Arrays.sort(charArray);
String sortedStr = new String(charArray);
```

2. **Stream type mismatch:**
```java
// WRONG - sortedStr is not an int[]
Arrays.stream(sortedStr).collect(Collectors.joining(""));

// CORRECT - Use String constructor
new String(charArray);
```

3. **Merge function issue:**
```java
// WRONG - newList is already a List
(oldList, newList) -> {
    oldList.add(newList);  // This adds List as element
    return oldList;
}

// CORRECT - Use computeIfAbsent
map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
```

## Key Insights
1. **Anagram Property:** Same characters, different order → same sorted form
2. **HashMap Grouping:** Use canonical form (sorted) as key
3. **computeIfAbsent:** Cleaner than merge for list initialization
4. **Direct Mapping:** Simpler to map sorted string → list of originals

## Related Problems
- **242. Valid Anagram** - Check if two strings are anagrams
- **438. Find All Anagrams in a String** - Sliding window anagrams
- **266. Palindrome Permutation** - Similar character frequency concept
- **890. Find and Replace Pattern** - Pattern matching (similar grouping)

## Interview Tips
1. **Start Simple:** Mention sorting approach first
2. **Optimize If Asked:** Discuss frequency counting for O(N*K)
3. **Handle Edge Cases:** Empty strings, single characters
4. **Space vs Time:** Sorting is O(K log K), frequency is O(K) but needs more code