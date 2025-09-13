# Study Guide: Maximum Number of Vowels in a Substring

## Problem Understanding

We need to find the maximum number of vowels in ANY substring of length exactly `k`. This is a classic sliding window problem because:
- We have a **fixed window size** (k)
- We need to check **all possible windows**
- We want to find the **maximum** across all windows

## Solution Walkthrough

### Step 1: Initialize the First Window

Let's trace through `s = "abciiidef"`, `k = 3`:

```
Initial window: [a b c] i i i d e f
                 ^ ^ ^
                 
Count vowels:
- 'a' is vowel → count = 1
- 'b' is not vowel → count = 1
- 'c' is not vowel → count = 1

Initial max = 1
```

### Step 2: Slide the Window

Now we slide the window one position at a time:

```
Window 1: a [b c i] i i d e f
            -----
Remove 'a' (vowel): count = 1 - 1 = 0
Add 'i' (vowel): count = 0 + 1 = 1
Current max = max(1, 1) = 1

Window 2: a b [c i i] i d e f
              -------
Remove 'b' (not vowel): count = 1
Add 'i' (vowel): count = 1 + 1 = 2
Current max = max(1, 2) = 2

Window 3: a b c [i i i] d e f
                -------
Remove 'c' (not vowel): count = 2
Add 'i' (vowel): count = 2 + 1 = 3
Current max = max(2, 3) = 3

Window 4: a b c i [i i d] e f
                  -------
Remove 'i' (vowel): count = 3 - 1 = 2
Add 'd' (not vowel): count = 2
Current max = max(3, 2) = 3

Window 5: a b c i i [i d e] f
                    -------
Remove 'i' (vowel): count = 2 - 1 = 1
Add 'e' (vowel): count = 1 + 1 = 2
Current max = max(3, 2) = 3

Window 6: a b c i i i [d e f]
                      -------
Remove 'i' (vowel): count = 2 - 1 = 1
Add 'f' (not vowel): count = 1
Current max = max(3, 1) = 3

Result: 3
```

## Key Insights

### 1. Window Sliding Mechanics
```
For window at position i (where i >= k):
- Character entering window: s[i]
- Character leaving window: s[i - k]
```

### 2. Early Termination Optimization
If we find a window with `k` vowels (all characters are vowels), we can return immediately since this is the maximum possible.

### 3. Three Implementation Approaches

#### Approach 1: HashSet (Your Original)
```java
Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
if (vowels.contains(ch)) { ... }
```
- **Pros:** Clean, readable, idiomatic Java
- **Cons:** Slight overhead from Set operations

#### Approach 2: Boolean Array
```java
boolean[] isVowel = new boolean[128];
isVowel['a'] = isVowel['e'] = isVowel['i'] = isVowel['o'] = isVowel['u'] = true;
if (isVowel[ch]) { ... }
```
- **Pros:** Fastest lookup (direct array access)
- **Cons:** Uses more memory (128 bytes)

#### Approach 3: Bit Manipulation
```java
int vowelBits = 0;
vowelBits |= 1 << ('a' - 'a');  // Set bit 0
vowelBits |= 1 << ('e' - 'a');  // Set bit 4
// Check: if ((vowelBits & (1 << (ch - 'a'))) != 0)
```
- **Pros:** Memory efficient, shows advanced knowledge
- **Cons:** Less readable, marginal performance gain

## Common Mistakes & How to Avoid Them

### Mistake 1: Window Boundary Errors
```java
// WRONG: Off-by-one error
for (int i = k + 1; i < s.length(); i++) {
    // Missing one window!
}

// CORRECT:
for (int i = k; i < s.length(); i++) {
    // Processes all windows
}
```

### Mistake 2: Forgetting to Remove Old Character
```java
// WRONG: Only adding new character
if (vowels.contains(s.charAt(i))) {
    currentCount++;
}

// CORRECT: Add new AND remove old
if (vowels.contains(s.charAt(i))) currentCount++;
if (vowels.contains(s.charAt(i - k))) currentCount--;
```

### Mistake 3: Not Handling Initial Window
```java
// WRONG: Starting with empty window
int count = 0;
for (int i = 0; i < s.length(); i++) {
    // Incorrect for first k characters
}

// CORRECT: Initialize first window separately
int count = 0;
for (int i = 0; i < k; i++) {
    if (vowels.contains(s.charAt(i))) count++;
}
```

## Interview Communication Script

Here's how to explain your solution in an interview:

1. **Identify the Pattern:**
   "This is a fixed-size sliding window problem. We need to examine all substrings of length k."

2. **Explain the Approach:**
   "I'll use the sliding window technique:
   - First, count vowels in the initial window of size k
   - Then slide the window by removing the leftmost character and adding a new rightmost character
   - Track the maximum count seen"

3. **Discuss Complexity:**
   "Time complexity is O(n) since we visit each character once.
   Space complexity is O(1) - just the vowel set and a few variables."

4. **Mention Optimizations:**
   "We can optimize by:
   - Early termination if we find k vowels
   - Using an array lookup instead of a Set for faster access"

## Practice Problems

Similar sliding window problems to practice:

1. **Fixed Window:**
   - Maximum sum subarray of size k
   - Find all anagrams in a string (LeetCode 438)
   - Permutation in string (LeetCode 567)

2. **Variable Window:**
   - Longest substring without repeating characters (LeetCode 3)
   - Minimum window substring (LeetCode 76)
   - Longest substring with at most k distinct characters (LeetCode 340)

## Final Interview Tips

1. **Start Simple:** Your original solution is perfect for interviews. It's clear and correct.

2. **Show Depth:** After implementing the basic solution, mention possible optimizations:
   - "We could use an array for O(1) lookups"
   - "We could add early termination"

3. **Test Thoroughly:** Always test with:
   - All vowels: "aeiou", k=3
   - No vowels: "bcdfg", k=2
   - k = 1 and k = s.length()

4. **Think Aloud:** Verbalize your thought process:
   - "I'm using a Set for vowel lookup because it's clean and O(1)"
   - "I need to handle both entering and leaving characters"
   - "Let me trace through a small example to verify..."