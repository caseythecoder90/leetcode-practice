# LeetCode 345: Reverse Vowels of a String

## Problem Statement

Given a string s, reverse only all the vowels in the string and return it.

The vowels are 'a', 'e', 'i', 'o', 'u', and they can appear in both lower and upper cases.

**Example 1:**
```
Input: s = "hello"
Output: "holle"
```

**Example 2:**
```
Input: s = "leetcode"  
Output: "leotcede"
```

**Example 3:**
```
Input: s = "Aa"
Output: "aA"
```

## Constraints

- 1 <= s.length <= 3 * 10^5
- s consists of printable ASCII characters

## Your Solution Analysis ✅

**Your solution is EXCELLENT and already optimal!** Here's why:

### Approach: Two Pointers with Set Lookup
```java
Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
char[] characters = s.toCharArray();
int i = 0, j = characters.length - 1;

while (i < j) {
    while (i < j && !vowels.contains(characters[i])) i++;
    while (i < j && !vowels.contains(characters[j])) j--;
    // swap and move
}
```

**What you did perfectly:**
- ✅ **Optimal time complexity**: O(n) - single pass
- ✅ **Efficient space usage**: O(n) for char array (unavoidable since strings are immutable)
- ✅ **Fast vowel lookup**: Set.contains() is O(1) average
- ✅ **Clean two-pointer logic**: Proper boundary checking
- ✅ **Case handling**: Includes both upper and lower case vowels
- ✅ **Correct swap**: Standard three-step swap pattern

**Execution Trace for "leetcode":**
```
Initial: ['l','e','e','t','c','o','d','e']
         i=0              j=7

Step 1: i=0->1 (skip 'l'), j=7 ('e' is vowel)
        i=1 ('e' is vowel), j=7 ('e' is vowel)
        Swap: ['l','e','e','t','c','o','d','e'] (e↔e, no change)
        i=2, j=6

Step 2: i=2 ('e' is vowel), j=6->5 (skip 'd')  
        i=2 ('e' is vowel), j=5 ('o' is vowel)
        Swap: ['l','e','o','t','c','e','d','e']
        i=3, j=4

Step 3: i=3->4->5 (skip 't','c'), j=4 (i≥j, stop)

Result: "leotcede" ✅
```

## Alternative Approaches (For Interview Practice)

### 1. String indexOf() Method
```java
String vowels = "aeiouAEIOU";
// Use vowels.indexOf(char) == -1 to check if not vowel
```
**Pros**: More readable  
**Cons**: indexOf() is O(10) = O(1) but slower than Set lookup

### 2. Boolean Array (Fastest for Large Inputs)
```java
boolean[] isVowel = new boolean[256]; // ASCII lookup table
```
**Pros**: Fastest lookup O(1) with no hash overhead  
**Cons**: Uses more memory (256 bytes vs ~10 Set entries)

### 3. Stack/List Collection (Educational)
```java
// Collect all vowels, then replace in reverse order
```
**Pros**: Easy to understand conceptually  
**Cons**: O(n) extra space, two passes needed

### 4. Recursive Approach (Educational)
```java
// Recursive two-pointer approach
```
**Pros**: Shows different paradigm  
**Cons**: O(n) call stack space, not practical for large inputs

## Performance Comparison

| Approach | Time | Space | Lookup Cost | Best For |
|----------|------|-------|-------------|----------|
| **Your Set Solution** | O(n) | O(n) | O(1) avg | **General use** |
| String indexOf | O(n) | O(n) | O(10) | Readability |
| Boolean Array | O(n) | O(n+256) | O(1) | Large inputs |
| Stack Collection | O(n) | O(n+vowels) | O(1) | Learning |
| Recursive | O(n) | O(n+stack) | O(1) | Education |

## Interview Strategy 🎯

Your plan to solve problems multiple ways is **excellent** for interviews! Here's how to leverage it:

### 1. Lead with Your Solution (Optimal)
- Start with your two-pointer approach
- Explain the O(n) time, O(n) space complexity
- Walk through the algorithm step by step

### 2. Discuss Alternative Approaches
- **Boolean array**: "For very large inputs, we could use a boolean array for faster lookup"
- **String method**: "We could use indexOf() for more readable code"  
- **Stack approach**: "There's also a two-pass approach using a stack"

### 3. Show Trade-off Awareness
- **Time vs Space**: Your solution balances both well
- **Readability vs Performance**: Set approach is clear and fast
- **Scalability**: Boolean array scales better for huge inputs

## Key Patterns Demonstrated

- ✅ **Two Pointers**: Classic inward-moving pointers
- ✅ **Set Membership**: Fast O(1) lookup for fixed criteria
- ✅ **String Manipulation**: Converting to char array for mutability
- ✅ **Boundary Handling**: Proper i < j checking in loops

## Common Mistakes You Avoided

1. **Forgot case sensitivity** - you handled both cases ✅
2. **Infinite loops** - your boundary checks are perfect ✅
3. **Index out of bounds** - proper i < j in inner loops ✅
4. **Inefficient vowel checking** - Set is optimal ✅
5. **Incorrect swap** - your three-step swap is textbook ✅

## Interview Tips

1. **Start with constraints**: Clarify case sensitivity, input size
2. **Explain two-pointer intuition**: "We want to swap vowels from opposite ends"
3. **Walk through example**: Trace "hello" → "holle" step by step
4. **Discuss alternatives**: Show you know multiple approaches
5. **Analyze complexity**: Time O(n), Space O(n) for char array

## Verdict: Your Solution is Already Optimal! 🌟

Your solution demonstrates:
- **Strong algorithmic thinking**: Chose the right pattern
- **Efficient implementation**: Clean, correct, and optimal
- **Best practices**: Proper variable naming, clear logic flow
- **Pattern mastery**: Two pointers technique executed perfectly

No improvements needed - this is interview-ready code! The multi-approach practice plan is brilliant for building interview confidence.