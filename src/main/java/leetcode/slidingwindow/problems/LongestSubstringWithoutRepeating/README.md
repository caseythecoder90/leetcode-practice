# 3. Longest Substring Without Repeating Characters

## Problem Description
Given a string `s`, find the length of the longest substring without repeating characters.

### Examples

**Example 1:**
- Input: `s = "abcabcbb"`
- Output: `3`
- Explanation: The answer is "abc", with the length of 3.

**Example 2:**
- Input: `s = "bbbbb"`
- Output: `1`
- Explanation: The answer is "b", with the length of 1.

**Example 3:**
- Input: `s = "pwwkew"`
- Output: `3`
- Explanation: The answer is "wke", with the length of 3.
- Note: "pwke" is a subsequence, not a substring.

**Example 4:**
- Input: `s = ""`
- Output: `0`
- Explanation: Empty string has no substrings.

### Constraints
- `0 <= s.length <= 5 * 10^4`
- `s` consists of English letters, digits, symbols and spaces

## Solution Approach

### Pattern: Variable-Size Sliding Window with HashSet/HashMap

This is a classic sliding window problem where we maintain a window of unique characters and expand/contract based on duplicates.

### Two Main Approaches

#### Approach 1: HashSet (Clean & Intuitive)
- Use HashSet to track characters in current window
- When duplicate found, shrink from left until duplicate removed
- Track maximum window size

#### Approach 2: HashMap with Index (Optimized)
- Store character → most recent index
- When duplicate found, jump left pointer directly past the duplicate
- Avoids removing characters one by one

### Algorithm Steps (HashSet Approach)

1. **Initialize** HashSet, left pointer at 0, maxLength at 0
2. **For each character** (right pointer):
   - While character exists in set (duplicate):
     - Remove leftmost character and move left pointer
   - Add current character to set
   - Update maximum length
3. **Return** maximum length found

### Algorithm Steps (HashMap Approach)

1. **Initialize** HashMap, left pointer at 0, maxLength at 0
2. **For each character** (right pointer):
   - If character exists in map and index ≥ left:
     - Jump left pointer to index + 1
   - Update character's index in map
   - Update maximum length
3. **Return** maximum length found

## Time & Space Complexity

### HashSet Approach
- **Time:** O(n) - In worst case, each character visited twice
- **Space:** O(min(n, m)) where m is size of character set

### HashMap Approach
- **Time:** O(n) - Each character visited exactly once
- **Space:** O(min(n, m)) where m is size of character set

## Why This Problem is Important

This problem is a **fundamental sliding window problem** that appears frequently in interviews because it:
1. Tests understanding of the sliding window pattern
2. Requires handling duplicates efficiently
3. Has multiple valid approaches with trade-offs
4. Forms the basis for many harder problems

## Common Variations & Follow-ups

### 1. Return the actual substring, not just length
```java
// Track start index when updating maximum
if (currentLength > maxLength) {
    maxLength = currentLength;
    resultStart = left;
}
return s.substring(resultStart, resultStart + maxLength);
```

### 2. At most K distinct characters
```java
// Use HashMap to count character frequencies
// Shrink when distinct count > K
```

### 3. Longest substring with at most 2 repeating characters
```java
// Track count of each character
// Allow up to 2 of same character
```

### 4. Case-insensitive version
```java
// Convert to lowercase or use case-insensitive comparison
```

## Interview Tips

### 1. Start with Clarifications
- What characters can the string contain? (letters, digits, symbols, spaces)
- Is it case-sensitive? (Yes)
- What about empty string? (Return 0)
- Do we need the actual substring or just length? (Just length)

### 2. Explain Your Approach
"I'll use a sliding window with a HashSet to track unique characters. I'll expand the window by moving right pointer and shrink from left when I encounter duplicates."

### 3. Discuss Trade-offs
- **HashSet:** Cleaner code, might shrink multiple times
- **HashMap:** More complex, but can jump directly to remove duplicate
- **Array (for ASCII):** Fastest but limited to specific character set

### 4. Handle Edge Cases
- Empty string → return 0
- Single character → return 1
- All same characters → return 1
- All unique characters → return string length

### 5. Optimization Points
- Early termination if remaining string can't beat current max
- Use array instead of HashSet for ASCII-only strings
- Jump optimization with HashMap

## Common Mistakes to Avoid

### Mistake 1: Not Removing All Characters Up To Duplicate
```java
// WRONG - Only removes one character
if (set.contains(s.charAt(right))) {
    set.remove(s.charAt(left));
    left++;
}

// CORRECT - Removes until duplicate gone
while (set.contains(s.charAt(right))) {
    set.remove(s.charAt(left));
    left++;
}
```

### Mistake 2: Updating Length at Wrong Time
```java
// WRONG - May count duplicate
maxLength = Math.max(maxLength, right - left + 1);
set.add(s.charAt(right));

// CORRECT - Count after adding
set.add(s.charAt(right));
maxLength = Math.max(maxLength, right - left + 1);
```

### Mistake 3: HashMap Index Check
```java
// WRONG - Old index might be outside window
if (map.containsKey(ch)) {
    left = map.get(ch) + 1;
}

// CORRECT - Check if index is in current window
if (map.containsKey(ch) && map.get(ch) >= left) {
    left = map.get(ch) + 1;
}
```

## Related Problems

### Direct Variations:
- **159. Longest Substring with At Most Two Distinct Characters**
- **340. Longest Substring with At Most K Distinct Characters**
- **424. Longest Repeating Character Replacement**

### Similar Pattern:
- **76. Minimum Window Substring**
- **438. Find All Anagrams in a String**
- **567. Permutation in String**

### Progression:
1. **This problem (3)** - Basic unique characters
2. **340. At Most K Distinct** - Generalized version
3. **76. Minimum Window Substring** - Complex pattern matching
4. **992. Subarrays with K Different Integers** - Exact K (harder)

## Key Takeaways

1. **Sliding window eliminates nested loops** - O(n²) → O(n)
2. **HashSet for uniqueness, HashMap for optimization** - Choose based on needs
3. **Shrink until valid, then expand** - Core sliding window pattern
4. **Window size = right - left + 1** - Remember the +1!
5. **This is a must-know problem** - Appears frequently and teaches core concepts