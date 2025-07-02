# Trie Pattern

## Overview

A **Trie** (pronounced "try") is a tree-like data structure that efficiently stores and retrieves strings. It's also called a **prefix tree** because it excels at prefix-based operations. Each node represents a character, and paths from root to nodes represent prefixes of stored words.

## What Problems Does Trie Solve?

- **Auto-complete/suggestions** (like search engines)
- **Spell checkers**
- **IP routing** (longest prefix matching)
- **Word games** (like Boggle, Scrabble)
- **Dictionary lookups** with prefix matching

## Key Operations

| Operation | Description | Time Complexity |
|-----------|-------------|-----------------|
| **Insert** | Add a word to the trie | O(m) where m = word length |
| **Search** | Check if exact word exists | O(m) where m = word length |
| **StartsWith** | Check if any word has given prefix | O(m) where m = prefix length |
| **Delete** | Remove a word from trie | O(m) where m = word length |

## Basic Trie Structure

```java
class TrieNode {
    TrieNode[] children;    // Usually size 26 for lowercase letters
    boolean isEndOfWord;    // Marks complete words
    
    public TrieNode() {
        children = new TrieNode[26];
        isEndOfWord = false;
    }
}
```

## Visual Example

**After inserting: "cat", "car", "card", "care", "careful"**

```
       root
        |
        c
        |
        a
        |
        t(end) --- r
                   |
                 (end) --- d(end) --- e(end)
                                      |
                                      f
                                      |
                                      u
                                      |
                                      l(end)
```

## Core Implementation Template

```java
class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // Insert word into trie
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }
    
    // Search for exact word
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    // Check if any word starts with prefix
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
    // Helper method to find prefix node
    private TrieNode searchPrefix(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }
}
```

## Step-by-Step Walkthrough: Insert "CAT"

1. **Start at root**, word = "cat"
2. **Process 'c'**: index = 2, create children[2] if null, move to children[2]
3. **Process 'a'**: index = 0, create children[0] if null, move to children[0]
4. **Process 't'**: index = 19, create children[19] if null, move to children[19]
5. **Mark end**: Set isEndOfWord = true at final node

## Step-by-Step Walkthrough: Search "CAR"

1. **Start at root**, word = "car"
2. **Process 'c'**: index = 2, children[2] exists? Yes → move to children[2]
3. **Process 'a'**: index = 0, children[0] exists? Yes → move to children[0]
4. **Process 'r'**: index = 17, children[17] exists? Check this
5. **Check end**: If we reach final node, return isEndOfWord value

## Common Optimizations

### 1. Space Optimization
Instead of always using size-26 arrays:
```java
// Use HashMap for sparse character sets
Map<Character, TrieNode> children = new HashMap<>();
```

### 2. Compressed Trie (Radix Tree)
Combine nodes with single children to save space.

### 3. Additional Fields
```java
class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    int wordCount;        // How many words end here
    String word;          // Store the actual word (for some problems)
    List<String> suggestions; // For autocomplete problems
}
```

## Time/Space Complexity Analysis

| Operation | Time | Space |
|-----------|------|-------|
| **Insert** | O(m) | O(ALPHABET_SIZE × N × M) worst case |
| **Search** | O(m) | where N = number of words, M = average length |
| **StartsWith** | O(m) | Typically much less in practice |
| **Delete** | O(m) | |

**Space Note**: In practice, tries share common prefixes, so space usage is often much better than worst case.

## When to Use Tries

**✅ Good for:**
- Prefix-based searches
- Autocomplete systems
- Dictionary/spell-check applications
- Problems with many string lookups
- When you need to find all words with a common prefix

**❌ Not ideal for:**
- Single string operations (use HashMap instead)
- When memory is extremely limited
- Simple exact-match lookups with no prefix requirements

## Common Trie Problem Patterns

### Pattern 1: Basic Implementation
Problems that ask you to implement the trie data structure itself.
- **Example**: LeetCode 208 - Implement Trie

### Pattern 2: Word Search/Formation
Using trie to efficiently search for words in a grid or form words from letters.
- **Example**: Word Search II, Word Break II

### Pattern 3: Autocomplete/Suggestions
Finding words that start with a given prefix.
- **Example**: LeetCode 1268 - Search Suggestions System

### Pattern 4: Shortest Unique Prefix
Finding the shortest prefix that uniquely identifies each word.

## Implementation Tips

1. **Character Mapping**: Always convert characters to indices consistently
   ```java
   int index = ch - 'a';  // For lowercase letters
   ```

2. **Null Checks**: Always check if children[index] is null before accessing

3. **End Markers**: Don't forget to mark word endings with isEndOfWord

4. **Edge Cases**: Handle empty strings, single characters, and duplicate insertions

5. **Memory Management**: Consider using HashMap instead of arrays for sparse alphabets

## Debugging Tips

1. **Print Trie Structure**: Write helper method to visualize the trie
2. **Trace Operations**: Step through insert/search operations manually
3. **Check End Markers**: Verify isEndOfWord is set correctly
4. **Validate Indices**: Ensure character-to-index conversion is correct

## LeetCode 75 Trie Problems

1. **#208 - Implement Trie (Prefix Tree)** [Medium]
    - Pure implementation problem
    - Focus on understanding basic operations

2. **#1268 - Search Suggestions System** [Medium]
    - Autocomplete application
    - Combines trie with lexicographical ordering

## Next Steps

1. **Start with Problem 208** to build foundation
2. **Implement from scratch** - don't just memorize
3. **Trace through examples** manually
4. **Move to Problem 1268** for practical application
5. **Practice variations** once comfortable with basics