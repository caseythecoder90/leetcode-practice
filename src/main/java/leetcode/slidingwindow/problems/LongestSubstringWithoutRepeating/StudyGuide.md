# Study Guide: Longest Substring Without Repeating Characters

## The Core Question

**"What's the longest substring where every character appears exactly once?"**

This is THE classic sliding window problem that everyone should master. It appears in interviews constantly and teaches fundamental concepts.

## Visual Walkthrough

Let's trace through `s = "abcabcbb"`:

```
Initial: left = 0, maxLength = 0, charSet = {}

Step 1: right = 0, char = 'a'
[a]bcabcbb
 ↑
 LR
No duplicate, add 'a'
charSet = {a}, length = 1

Step 2: right = 1, char = 'b'
[ab]cabcbb
 ↑↑
 LR
No duplicate, add 'b'
charSet = {a,b}, length = 2

Step 3: right = 2, char = 'c'
[abc]abcbb
 ↑  ↑
 L  R
No duplicate, add 'c'
charSet = {a,b,c}, length = 3 ← MAX so far!

Step 4: right = 3, char = 'a'
[abca]bcbb
 ↑   ↑
 L   R
DUPLICATE 'a' found!
Must remove from left until 'a' is gone:
- Remove 'a' at index 0, left = 1

a[bca]bcbb
  ↑  ↑
  L  R
Add new 'a'
charSet = {b,c,a}, length = 3

Step 5: right = 4, char = 'b'
a[bcab]cbb
  ↑   ↑
  L   R
DUPLICATE 'b' found!
Remove from left:
- Remove 'b' at index 1, left = 2

ab[cab]cbb
   ↑  ↑
   L  R
Add new 'b'
charSet = {c,a,b}, length = 3

Step 6: right = 5, char = 'c'
ab[cabc]bb
   ↑   ↑
   L   R
DUPLICATE 'c' found!
Remove from left:
- Remove 'c' at index 2, left = 3

abc[abc]bb
    ↑  ↑
    L  R
Add new 'c'
charSet = {a,b,c}, length = 3

Continue...
Final max length = 3 ("abc", "bca", or "cab")
```

## Two Approaches Compared

### Approach 1: HashSet (Intuitive)
```java
while (set.contains(currentChar)) {
    set.remove(s.charAt(left));
    left++;
}
```
- **Pros:** Clean, easy to understand
- **Cons:** Might remove multiple characters one by one

### Approach 2: HashMap with Index (Optimized)
```java
if (map.containsKey(currentChar) && map.get(currentChar) >= left) {
    left = map.get(currentChar) + 1;  // Jump directly!
}
```
- **Pros:** Jumps directly past duplicate
- **Cons:** Slightly more complex logic

## The Key Insight

When we find a duplicate, we need to remove ALL characters from the left up to and including the first occurrence of the duplicate. Why?

```
Window: [a b c d e a]
         ↑         ↑
         left      right (duplicate 'a')

We must move left past the first 'a':
New window: [b c d e a]
```

If we only removed one character, we'd still have a duplicate!

## Common Patterns in String Sliding Window

### Pattern 1: Unique Characters (This Problem)
- Use HashSet or HashMap
- Shrink until duplicate removed

### Pattern 2: At Most K Distinct
- Use HashMap with counts
- Shrink when distinct count > K

### Pattern 3: Exact Pattern Match
- Use two HashMaps (pattern and window)
- Compare frequencies

### Pattern 4: Any Permutation
- Use frequency array
- Check if window matches pattern frequency

## Step-by-Step Problem Solving

### 1. Recognize the Pattern
"Longest substring" + "without repeating" → Sliding Window with HashSet!

### 2. Choose Data Structure
- **HashSet:** For checking uniqueness
- **HashMap:** For optimization (store indices)
- **Array:** For ASCII-only (fastest)

### 3. Implement Core Logic
```
1. Expand window (right pointer)
2. Check for duplicate
3. If duplicate: shrink from left until removed
4. Update maximum
```

### 4. Handle Edge Cases
- Empty string
- Single character
- All same characters
- All unique characters

## Common Interview Questions

### Q: "Why use sliding window instead of checking all substrings?"
**A:** "Checking all substrings would be O(n²) or O(n³). Sliding window reuses information from the previous window, achieving O(n) time."

### Q: "Can you optimize further?"
**A:** "Yes! Using HashMap to store indices allows jumping directly past duplicates instead of removing one by one."

### Q: "What if we want the actual substring?"
**A:** "Track the start index when updating maximum length, then use substring(start, start + maxLength)."

### Q: "How would you handle Unicode characters?"
**A:** "HashMap works for any character set. Array approach would need modification for Unicode."

## Debugging Tips

### Add Debug Output
```java
System.out.println("Window: [" + left + "," + right + "]");
System.out.println("Current: " + s.substring(left, right + 1));
System.out.println("Set: " + charSet);
```

### Common Bugs to Check
1. Not removing ALL characters up to duplicate
2. Updating length before adding character
3. Off-by-one in length calculation
4. Not handling empty string

## Variations to Practice

### Easy Variation: All Lowercase Letters
```java
boolean[] seen = new boolean[26];
// Use seen[ch - 'a'] for checking
```

### Medium Variation: At Most Two Repeats
```java
Map<Character, Integer> count = new HashMap<>();
// Allow count up to 2
```

### Hard Variation: K Unique Characters
```java
// Longest substring with exactly K unique characters
// Requires atMost(K) - atMost(K-1) trick
```

## Memory Optimization

### Space Complexity Comparison:
- **HashSet:** O(min(n, m)) where m = charset size
- **HashMap:** O(min(n, m))
- **Array[128]:** O(1) but only for ASCII
- **Array[256]:** O(1) for extended ASCII

### When to Use Each:
- **Interview default:** HashSet (clean and works for all inputs)
- **Performance critical:** Array for ASCII
- **Jump optimization needed:** HashMap with indices

## Practice Checklist

Before moving to harder problems, make sure you can:

✅ Implement HashSet solution from memory  
✅ Implement HashMap optimization  
✅ Explain why sliding window works  
✅ Calculate time/space complexity  
✅ Handle all edge cases  
✅ Trace through an example on paper  
✅ Implement in under 10 minutes  

## Final Tips

1. **This is a MUST-KNOW problem** - It appears everywhere
2. **Start with HashSet** - It's cleaner for interviews  
3. **Mention optimization** - Show you know HashMap approach
4. **Practice variations** - They build on this pattern
5. **Time yourself** - Aim for < 10 minutes in interview