# 17. Letter Combinations of a Phone Number

## Problem Description

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

**Phone Keypad Mapping:**
- 2: "abc"
- 3: "def"
- 4: "ghi"
- 5: "jkl"
- 6: "mno"
- 7: "pqrs"
- 8: "tuv"
- 9: "wxyz"

## Examples

**Example 1:**
```
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Example 2:**
```
Input: digits = ""
Output: []
```

**Example 3:**
```
Input: digits = "2"
Output: ["a","b","c"]
```

## Constraints

- `0 <= digits.length <= 4`
- `digits[i]` is a digit in the range `['2', '9']`

## Approach Analysis

### Approach 1: Backtracking (Recommended)

**Intuition:**  
This is a classic backtracking problem. For each digit, we have multiple letter choices. We need to explore all combinations by:
1. Choose a letter for the current digit
2. Recursively process the remaining digits
3. Backtrack by trying the next letter choice

**Decision Tree for "23":**
```
""
├─ a
│  ├─ ad ✓
│  ├─ ae ✓  
│  └─ af ✓
├─ b
│  ├─ bd ✓
│  ├─ be ✓
│  └─ bf ✓
└─ c
   ├─ cd ✓
   ├─ ce ✓
   └─ cf ✓
```

**Time Complexity:** O(3^N × 4^M) where N is digits with 3 letters, M is digits with 4 letters  
**Space Complexity:** O(3^N × 4^M) for storing all combinations

### Approach 2: Iterative Building

**Intuition:**  
Build combinations iteratively. Start with empty result, then for each digit, create new combinations by appending each possible letter to existing combinations.

**Process for "23":**
1. Start: `[""]`
2. Process '2': `["a", "b", "c"]`
3. Process '3': `["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]`

**Time Complexity:** O(3^N × 4^M)  
**Space Complexity:** O(3^N × 4^M)

## Key Insights

1. **Pattern Recognition**: This is a classic "generate all combinations" problem
2. **State Management**: We need to track current position in digits and current combination
3. **Base Case**: When we've processed all digits, add the combination to results
4. **Choices**: For each digit, try all possible letters

## Implementation Notes

- Use a phone mapping (HashMap or array)
- StringBuilder is more efficient for string building than concatenation
- Remember to make copies when adding to result (if using mutable objects)
- Handle edge case of empty input

## Follow-up Questions

1. How would you handle if digits could include '0' and '1'?
2. What if we wanted combinations of a specific length only?
3. How would you optimize for memory if the input could be very long?

## Related Problems

- **22. Generate Parentheses** - Similar backtracking pattern
- **39. Combination Sum** - Backtracking with target constraints
- **46. Permutations** - Generate all arrangements
- **78. Subsets** - Generate all subsets