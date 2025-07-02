# Trie Pattern - Flashcards

## Basic Concepts

**Q: What is a Trie and why is it called a "Prefix Tree"?**
A: A Trie is a tree-like data structure where each node represents a character, and paths from root to nodes represent prefixes of stored words. It's called a prefix tree because it excels at prefix-based operations like finding all words that start with a given prefix.

**Q: What are the three core operations every trie should support?**
A:
1. **Insert** - Add a word to the trie
2. **Search** - Check if a complete word exists
3. **StartsWith** - Check if any word has the given prefix

**Q: What is the time complexity of basic trie operations?**
A: O(m) for insert, search, and startsWith operations, where m is the length of the word/prefix being processed.

**Q: What fields does a basic TrieNode need?**
A:
- `TrieNode[] children` (or HashMap) - to store child nodes
- `boolean isEndOfWord` - to mark complete words

## Implementation Details

**Q: How do you convert a character to an array index for lowercase letters?**
A: `int index = ch - 'a';` This gives 'a'=0, 'b'=1, ..., 'z'=25

**Q: When should you use a TrieNode array vs HashMap for children?**
A:
- **Array**: When alphabet is fixed and small (like a-z), faster access
- **HashMap**: When alphabet is large/sparse or contains mixed characters, saves space

**Q: What's the difference between search() and startsWith()?**
A:
- **search()**: Returns true only if the exact word exists (checks `isEndOfWord`)
- **startsWith()**: Returns true if any word has the given prefix (doesn't need `isEndOfWord`)

**Q: How do you mark the end of a word during insertion?**
A: After processing all characters, set `current.isEndOfWord = true` at the final node.

## Common Patterns

**Q: What's the standard pattern for traversing a trie during search/insert?**
A:
```java
TrieNode current = root;
for (char ch : word.toCharArray()) {
    int index = ch - 'a';
    // Handle null check and navigation
    current = current.children[index];
}
```

**Q: How do you find all words with a given prefix?**
A:
1. Navigate to the prefix node using startsWith logic
2. From that node, perform DFS to collect all paths that lead to `isEndOfWord = true`
3. Build strings as you traverse and backtrack

**Q: What's the pattern for deleting a word from a trie?**
A: Use recursive deletion that removes nodes only if:
1. They're not the end of another word (`isEndOfWord = false`)
2. They have no other children
   Work bottom-up to avoid breaking other words.

## Problem-Solving Strategy

**Q: When should you consider using a trie vs other data structures?**
A: Use trie when:
- Need prefix-based searches
- Building autocomplete/suggestions
- Multiple string lookups with shared prefixes
- Word formation/validation problems

**Q: What are common optimizations for trie space usage?**
A:
1. Use HashMap instead of fixed arrays for sparse alphabets
2. Compressed tries (radix trees) - combine single-child paths
3. Store the complete word at end nodes instead of reconstructing

**Q: How do you handle edge cases in trie implementation?**
A: Always check:
- Empty strings
- Null inputs
- Duplicate insertions
- Array bounds when using character indices
- Null pointer exceptions when traversing

## Advanced Concepts

**Q: What additional information might you store in TrieNode for advanced problems?**
A:
- `int wordCount` - how many words end at this node
- `String word` - store the complete word
- `List<String> suggestions` - for autocomplete features
- `int frequency` - for ranking/sorting words

**Q: How would you implement autocomplete that returns the top K suggestions?**
A:
1. Store word frequencies in nodes
2. Navigate to prefix node
3. Use DFS with priority queue/heap to collect top K words
4. Sort by frequency/lexicographical order as needed

**Q: What's the space complexity of a trie in worst case vs typical case?**
A:
- **Worst case**: O(ALPHABET_SIZE × N × M) where N=number of words, M=average length
- **Typical case**: Much better due to shared prefixes, often O(total characters in all unique prefixes)

## Debugging and Testing

**Q: What's a good way to visualize and debug a trie?**
A: Create a print method that does DFS and prints all words:
```java
private void printWords(TrieNode node, StringBuilder current) {
    if (node.isEndOfWord) {
        System.out.println(current.toString());
    }
    for (int i = 0; i < 26; i++) {
        if (node.children[i] != null) {
            current.append((char)('a' + i));
            printWords(node.children[i], current);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```

**Q: What are the most common bugs in trie implementation?**
A:
1. Forgetting to check `isEndOfWord` in search (returns true for prefixes)
2. Not handling null children properly (NullPointerException)
3. Wrong character-to-index conversion
4. Not marking `isEndOfWord = true` during insertion

**Q: How do you test a trie implementation thoroughly?**
A: Test cases should include:
- Basic insert/search/startsWith
- Empty string handling
- Overlapping words ("cat", "cats", "car")
- Non-existent words and prefixes
- Single character words
- Duplicate insertions