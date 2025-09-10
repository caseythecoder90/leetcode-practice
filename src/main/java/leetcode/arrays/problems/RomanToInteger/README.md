# LeetCode 13: Roman to Integer

## Problem Statement

Roman numerals are represented by seven symbols:
- I = 1
- V = 5  
- X = 10
- L = 50
- C = 100
- D = 500
- M = 1000

For example, 2 is written as II, just two ones added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX.

There are six instances where subtraction is used:
- I can be placed before V (5) and X (10) to make 4 and 9
- X can be placed before L (50) and C (100) to make 40 and 90  
- C can be placed before D (500) and M (1000) to make 400 and 900

Given a roman numeral, convert it to an integer.

**Example 1:**
```
Input: s = "III"
Output: 3
Explanation: III = 3
```

**Example 2:**
```
Input: s = "LVIII"  
Output: 58
Explanation: L = 50, V= 5, III = 3
```

**Example 3:**
```
Input: s = "MCMXC"
Output: 1990
Explanation: M = 1000, CM = 900, XC = 90
```

## Constraints

- 1 <= s.length <= 15
- s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M')
- It is guaranteed that s is a valid roman numeral in the range [1, 3999]

## Approach

### Solution 1: Right-to-Left Traversal (Optimal)

**Key Insight:** When reading from right to left, if the current symbol value is less than the previous symbol value, it must be a subtractive case.

**Algorithm:**
1. Create a mapping of roman symbols to their values
2. Traverse the string from right to left
3. For each character:
   - If current value < previous value → subtract (subtractive case)
   - Otherwise → add (normal case)
4. Update previous value for next iteration

**Time Complexity:** O(n) where n is the length of string  
**Space Complexity:** O(1) - fixed size hash map

**Execution Trace for "MCMXC":**
```
Right to left: C(100) → X(10) → M(1000) → C(100) → M(1000)

i=4: C=100, prev=0 → result=100, prev=100
i=3: X=10, prev=100 → 10<100 → result=100-10=90, prev=10  
i=2: M=1000, prev=10 → 1000>10 → result=90+1000=1090, prev=1000
i=1: C=100, prev=1000 → 100<1000 → result=1090-100=990, prev=100
i=0: M=1000, prev=100 → 1000>100 → result=990+1000=1990, prev=1000

Final result: 1990
```

### Solution 2: Left-to-Right with Look-ahead

**Key Insight:** Check the next character to identify subtractive pairs explicitly.

**Algorithm:**
1. For each character, look at the next character
2. If current < next, it's a subtractive case:
   - Add (next - current) and skip next character
3. Otherwise, add current value normally

**Time Complexity:** O(n)  
**Space Complexity:** O(1)

### Solution 3: Optimized Switch Statement

**Performance Optimization:** Replace hash map with switch statement to eliminate hash map overhead.

## Edge Cases

1. **Single character:** "I" → 1, "M" → 1000
2. **All subtractive:** "IV" → 4, "IX" → 9  
3. **Mixed normal and subtractive:** "XIV" → 14
4. **Maximum value:** "MMMCMXCIX" → 3999
5. **Minimum value:** "I" → 1

## Key Patterns

- **String Processing:** Character-by-character traversal
- **Hash Map Usage:** Quick symbol-to-value lookup
- **Look-ahead/Look-behind:** Handle context-dependent rules
- **Mathematical Logic:** Addition vs subtraction based on position

## Interview Tips

1. **Clarify the rules** - confirm the six subtractive cases
2. **Choose right-to-left approach** - cleaner logic than look-ahead
3. **Handle edge cases** - single characters, all subtractive pairs
4. **Discuss optimizations** - switch vs hash map for performance
5. **Trace through examples** - demonstrate understanding with "MCMXC"

## Common Mistakes

1. **Forgetting subtractive cases** - only handling additive logic
2. **Wrong direction** - left-to-right requires more complex look-ahead
3. **Index bounds** - careful with look-ahead to avoid out-of-bounds
4. **Case sensitivity** - problem guarantees valid input, but good to mention