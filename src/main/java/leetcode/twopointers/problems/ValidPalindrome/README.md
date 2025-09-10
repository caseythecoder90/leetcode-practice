# 125. Valid Palindrome

## Problem Description

Given a string `s`, return `true` if it is a palindrome, or `false` otherwise.

A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.

## Examples

### Example 1:
```
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: After cleaning: "amanaplanacanalpanama"
This reads the same forward and backward.
```

### Example 2:
```
Input: s = "race a car"
Output: false
Explanation: After cleaning: "raceacar"
This does not read the same backward ("racaecar").
```

### Example 3:
```
Input: s = " "
Output: true
Explanation: After cleaning: "" (empty string)
Empty strings are considered palindromes.
```

### Example 4:
```
Input: s = "0P"
Output: false
Explanation: After cleaning: "0p"
This does not read the same backward ("p0").
```

## Constraints

- `1 <= s.length <= 2 * 10⁵`
- `s` consists only of printable ASCII characters.

## Character Classification

### Alphanumeric Characters:
- **Letters**: a-z, A-Z (case-insensitive after conversion)
- **Digits**: 0-9
- **Non-alphanumeric**: All other characters (punctuation, spaces, symbols) are ignored

### ASCII Character Ranges:
- Lowercase letters: 97-122 ('a'-'z')
- Uppercase letters: 65-90 ('A'-'Z')
- Digits: 48-57 ('0'-'9')

## Approach Analysis

### Optimal Solution: Two-Pointer Technique
- Use two pointers starting from both ends
- Skip non-alphanumeric characters
- Compare alphanumeric characters (case-insensitive)
- Time: O(n), Space: O(1)

### Alternative Approach: String Cleaning
- Remove non-alphanumeric characters and convert to lowercase
- Check if cleaned string equals its reverse
- Time: O(n), Space: O(n) for new string

### Inefficient Approach: String Reversal
- Create cleaned string
- Create reverse of cleaned string
- Compare the two strings
- Time: O(n), Space: O(n)

## Edge Cases to Test

1. **Empty string**: `""` → `true`
2. **Single space**: `" "` → `true`
3. **Single character**: `"a"` → `true`
4. **Only non-alphanumeric**: `":,"` → `true`
5. **Mixed case**: `"Aa"` → `true`
6. **Numbers only**: `"12321"` → `true`
7. **Mixed alphanumeric**: `"A1b2c"` → `false`
8. **Very long string**: Up to 2×10⁵ characters
9. **All uppercase**: `"ABA"` → `true`
10. **Punctuation at ends**: `"!racecar!"` → `true`

## Common Mistakes

1. **Not handling empty string correctly**: Should return true
2. **Case sensitivity**: Must convert ALL characters to lowercase, not just uppercase
3. **Non-alphanumeric removal**: Don't remove them, just skip them during comparison
4. **Pointer movement**: Must move both pointers after successful comparison
5. **Boundary conditions**: Don't cause index out of bounds errors

## Algorithm Walkthrough

### Example 1: "A man, a plan, a canal: Panama"
```
Cleaned: "amanaplanacanalpanama"
left=0, right=20: both 'a' → match, move pointers
left=1, right=19: both 'm' → match, move pointers
left=2, right=18: both 'a' → match, move pointers
...
Eventually all characters match → true
```

### Example 2: "race a car"
```
Cleaned: "raceacar"
left=0, right=7: 'r' vs 'r' → match, move pointers
left=1, right=6: 'a' vs 'a' → match, move pointers
left=2, right=5: 'c' vs 'c' → match, move pointers
left=3, right=4: 'e' vs 'a' → NO match! → false
```

## Time Complexity Analysis

- **Time**: O(n) - We process each character at most twice (once from each side)
- **Space**: O(1) - Only using constant extra space for pointers
- **Worst Case**: String with all non-alphanumeric characters → O(n) time
- **Best Case**: Very short strings or early mismatches

## Why Two-Pointers is Optimal

1. **In-place processing**: No need to create additional strings
2. **Efficient skipping**: Skip invalid characters as we go
3. **Early termination**: Can return false as soon as we find mismatch
4. **Memory efficient**: Perfect for very long strings (2×10⁵ constraint)

## Interview Tips

1. **Character classification**: Know ASCII ranges or use built-in methods
2. **Case conversion**: Use Character.toLowerCase() or manual arithmetic
3. **Edge cases**: Test with empty strings, single characters, all punctuation
4. **Performance**: Two-pointer approach is optimal for this problem
5. **Clarity vs Performance**: Built-in methods are clearer than manual char checking

## Related Problems

- **9. Palindrome Number**: Similar but for numbers only
- **234. Palindrome Linked List**: Two-pointers in a linked structure
- **680. Valid Palindrome II**: Can delete at most one character
