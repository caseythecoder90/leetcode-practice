# LeetCode 12: Integer to Roman

## Problem Statement

Roman numerals are represented by seven symbols:
- I = 1
- V = 5
- X = 10
- L = 50
- C = 100
- D = 500
- M = 1000

For example, 2 is written as II, just two ones added together. 12 is written as XII, which is X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. There are six instances where subtraction is used:

- I can be placed before V (5) and X (10) to make 4 and 9
- X can be placed before L (50) and C (100) to make 40 and 90
- C can be placed before D (500) and M (1000) to make 400 and 900

Given an integer, convert it to a roman numeral.

**Example 1:**
```
Input: num = 3
Output: "III"
```

**Example 2:**
```
Input: num = 4
Output: "IV"
```

**Example 3:**
```
Input: num = 9
Output: "IX"
```

**Example 4:**
```
Input: num = 58
Output: "LVIII"
Explanation: L = 50, V = 5, III = 3
```

**Example 5:**
```
Input: num = 1994
Output: "MCMXCIV"
Explanation: M = 1000, CM = 900, XC = 90, IV = 4
```

## Constraints

- 1 <= num <= 3999

## Approach Analysis

### Your Original Solution ✅ (Working Solution)

**Key Strengths:**
- **Correct logic**: Properly separates basic symbols from subtractive cases
- **Smart first digit check**: Uses `numString.charAt(0)` to identify 4 and 9 patterns
- **Complete coverage**: Handles all edge cases correctly
- **Clean structure**: Well-organized with separate mappings

**How it works:**
```java
// Check if number starts with 4 or 9
if (numString.charAt(0) == '4' || numString.charAt(0) == '9') {
    // Use subtractive cases: IV, IX, XL, XC, CD, CM
} else {
    // Use basic symbols: I, V, X, L, C, D, M
}
```

**Time Complexity:** O(1) - maximum 13 symbols possible  
**Space Complexity:** O(1) - fixed size data structures

**Execution Trace for 1994:**
```
1994 → starts with '1' → use basic mapping → M (1000)
 994 → starts with '9' → use subtractive mapping → CM (900)  
  94 → starts with '9' → use subtractive mapping → XC (90)
   4 → starts with '4' → use subtractive mapping → IV (4)
   0 → done
Result: "MCMXCIV"
```

### Optimized Solution: Combined Greedy Approach

**Key Improvements:**
1. **Single mapping**: Combine all values (including subtractive) in descending order
2. **Eliminate digit check**: No need to analyze first digit
3. **Simpler logic**: Single greedy loop
4. **Better performance**: Fewer string operations and map lookups

**Algorithm:**
```java
int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

for (int i = 0; i < values.length; i++) {
    while (num >= values[i]) {
        result.append(symbols[i]);
        num -= values[i];
    }
}
```

**Why this works:**
- **Greedy principle**: Always use the largest possible value
- **Correct ordering**: Subtractive cases (900, 400, 90, 40, 9, 4) are placed before their components
- **Natural flow**: No special case handling needed

**Execution Trace for 1994:**
```
1994 ≥ 1000 → append "M", num = 994
 994 ≥ 900  → append "CM", num = 94  
  94 ≥ 90   → append "XC", num = 4
   4 ≥ 4    → append "IV", num = 0
Result: "MCMXCIV"
```

## Solution Comparison

| Aspect | Original Solution | Optimized Solution |
|--------|-------------------|-------------------|
| **Correctness** | ✅ Perfect | ✅ Perfect |
| **Code Clarity** | Good (clear separation) | Excellent (single flow) |
| **Performance** | Good | Better (fewer operations) |
| **Maintainability** | Good | Excellent (simpler logic) |
| **Space Usage** | More (2 maps + 2 lists) | Less (2 arrays) |

## Key Learning Points

### What You Did Right ✅
1. **Problem Understanding**: Correctly identified the need to handle subtractive cases
2. **Working Solution**: Got a complete, correct implementation
3. **Good Structure**: Clean separation of concerns with meaningful variable names
4. **Edge Case Handling**: Covers all possible inputs correctly

### Optimization Insights 💡
1. **Greedy Algorithm**: When values are ordered correctly, greedy approach works perfectly
2. **Data Structure Choice**: Arrays can be more efficient than Maps for small, fixed datasets
3. **Algorithm Simplification**: Sometimes combining cases leads to simpler code
4. **Pattern Recognition**: This is the same pattern used in making change with coins

## Interview Tips

1. **Start with working solution first** - your approach was perfect
2. **Explain the greedy principle** - always take the largest value possible
3. **Mention the ordering importance** - subtractive cases must come before their components
4. **Discuss trade-offs** - readability vs performance
5. **Handle edge cases** - 1, 3999, and numbers with multiple subtractive patterns

## Progress Recognition 🎉

**This solution shows significant improvement because:**
- You identified the core problem (subtractive cases)
- You implemented a working solution independently  
- Your logic was sound and complete
- You structured the code well with good variable names
- You're thinking about optimization opportunities

**Next Steps:**
- Practice more greedy algorithms
- Look for opportunities to simplify conditional logic
- Consider when arrays might be better than maps
- Keep building on this problem-solving confidence!