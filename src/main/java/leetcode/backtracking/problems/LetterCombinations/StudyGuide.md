# Letter Combinations of a Phone Number - Study Guide

## Problem Statement

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

**Example:**
- Input: digits = "23"
- Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]

## The Mapping
```
2 -> "abc"
3 -> "def"
4 -> "ghi"
5 -> "jkl"
6 -> "mno"
7 -> "pqrs"
8 -> "tuv"
9 -> "wxyz"
```

## Solution Approach: Backtracking

### Complete Solution
```java
class Solution {
    private List<String> combinations = new ArrayList<>();
    private Map<Character, String> letters = Map.of(
        '2', "abc",
        '3', "def",
        '4', "ghi",
        '5', "jkl",
        '6', "mno",
        '7', "pqrs",
        '8', "tuv",
        '9', "wxyz"
    );
    private String phoneDigits;

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return combinations;
        }
        phoneDigits = digits;
        backtrack(0, new StringBuilder());
        return combinations;
    }

    public void backtrack(int index, StringBuilder path) {
        // Base case: we've built a complete combination
        if (path.length() == phoneDigits.length()) {
            combinations.add(path.toString());
            return;
        }

        // Get all possible letters for current digit
        String possibleLetters = letters.get(phoneDigits.charAt(index));
        
        // Try each possible letter
        for (char letter : possibleLetters.toCharArray()) {
            path.append(letter);                    // Make choice
            backtrack(index + 1, path);             // Explore
            path.deleteCharAt(path.length() - 1);   // Undo choice (BACKTRACK)
        }
    }
}
```

## Understanding the Backtracking Process

### The Core Pattern
Backtracking follows this three-step pattern:
1. **Make a choice** - Add a letter to our current path
2. **Explore** - Recursively try to complete the combination
3. **Undo the choice** - Remove the letter to try other possibilities

### Why We Need the "Undo" Step

The crucial insight is that we're using a **shared StringBuilder object** across all recursive calls. This means:

- When we call `backtrack(index + 1, path)`, we're passing the same `StringBuilder` reference
- After the recursive call returns, we need to restore the `StringBuilder` to its previous state
- This allows us to try the next letter at the current position

### Visual Trace Through "23"

Let's trace through the execution step by step:

```
backtrack(0, "")  // Start with empty path
│
├── Try letter 'a' (from digit '2')
│   │ path.append('a') → path = "a"
│   │ backtrack(1, "a")
│   │   │
│   │   ├── Try letter 'd' (from digit '3')
│   │   │   │ path.append('d') → path = "ad"
│   │   │   │ backtrack(2, "ad")
│   │   │   │   │ path.length() == phoneDigits.length()
│   │   │   │   │ Add "ad" to combinations
│   │   │   │   │ return
│   │   │   │ path.deleteCharAt() → path = "a"  // BACKTRACK
│   │   │
│   │   ├── Try letter 'e' (from digit '3')
│   │   │   │ path.append('e') → path = "ae"
│   │   │   │ backtrack(2, "ae")
│   │   │   │   │ Add "ae" to combinations
│   │   │   │   │ return
│   │   │   │ path.deleteCharAt() → path = "a"  // BACKTRACK
│   │   │
│   │   └── Try letter 'f' (from digit '3')
│   │       │ path.append('f') → path = "af"
│   │       │ backtrack(2, "af")
│   │       │   │ Add "af" to combinations
│   │       │   │ return
│   │       │ path.deleteCharAt() → path = "a"  // BACKTRACK
│   │
│   │ path.deleteCharAt() → path = ""  // BACKTRACK
│
├── Try letter 'b' (from digit '2')
│   │ path.append('b') → path = "b"
│   │ backtrack(1, "b")
│   │   │ (similar process for 'd', 'e', 'f')
│   │
│   │ path.deleteCharAt() → path = ""  // BACKTRACK
│
└── Try letter 'c' (from digit '2')
    │ path.append('c') → path = "c"
    │ backtrack(1, "c")
    │   │ (similar process for 'd', 'e', 'f')
    │
    │ path.deleteCharAt() → path = ""  // BACKTRACK
```

### What Happens Without Backtracking?

If we didn't have the `path.deleteCharAt(path.length() - 1)` line:

```
backtrack(0, "")
├── Try 'a': path = "a"
│   └── Try 'd': path = "ad" → Add "ad"
│   └── Try 'e': path = "ade" → Add "ade" (WRONG!)
│   └── Try 'f': path = "adef" → Add "adef" (WRONG!)
├── Try 'b': path = "adefb" → (COMPLETELY WRONG!)
```

The path would keep growing and never reset, leading to incorrect combinations.

## Key Concepts to Remember

### 1. Shared State
- The `StringBuilder path` is shared across all recursive calls
- Changes made in deeper recursive calls affect the same object
- We must manually undo changes when backtracking

### 2. Decision Tree
- Each digit represents a level in our decision tree
- Each letter for that digit represents a branch
- We explore all branches systematically

### 3. Base Case
- When `path.length() == phoneDigits.length()`, we have a complete combination
- Add it to our results and return (backtrack)

### 4. State Management
- **Before recursion**: Make a choice (add letter)
- **After recursion**: Undo the choice (remove letter)
- This ensures clean state for trying the next option

## Time and Space Complexity

**Time Complexity**: O(3^N × 4^M)
- N = number of digits that map to 3 letters (2,3,4,5,6,8)
- M = number of digits that map to 4 letters (7,9)
- We generate all possible combinations

**Space Complexity**: O(3^N × 4^M)
- Space for storing all combinations
- Plus O(N) for recursion stack depth

## Alternative Approaches

### Using String Instead of StringBuilder
```java
public void backtrack(int index, String path) {
    if (path.length() == phoneDigits.length()) {
        combinations.add(path);
        return;
    }
    
    String possibleLetters = letters.get(phoneDigits.charAt(index));
    for (char letter : possibleLetters.toCharArray()) {
        backtrack(index + 1, path + letter);  // No explicit backtracking needed
    }
}
```

**Pros**: No need for explicit backtracking (strings are immutable)
**Cons**: Less efficient due to string creation overhead

## Practice Questions

1. What would happen if we forgot the base case?
2. Why do we use `index + 1` in the recursive call?
3. How would the solution change if we wanted to return combinations in lexicographical order?
4. What's the difference between using `StringBuilder` vs `String` for the path?

## Summary

The backtracking technique is powerful for exploring all possible combinations systematically. The key insight is understanding when and why we need to "undo" our choices to maintain clean state for exploring alternative paths. The `path.deleteCharAt(path.length() - 1)` line is what makes the backtracking work correctly by ensuring our shared state is properly managed.