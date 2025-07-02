# Trie Cheat Sheet

## Basic Trie Node Template

```java
class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    
    public TrieNode() {
        children = new TrieNode[26];  // For lowercase a-z
        isEndOfWord = false;
    }
}
```

## Alternative Node (HashMap-based)
```java
class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    
    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}
```

## Complete Trie Implementation

```java
class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
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
    
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
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

## Common Operations Patterns

### Insert Pattern
```java
public void insert(String word) {
    TrieNode current = root;
    for (char ch : word.toCharArray()) {
        // Convert char to index
        int index = ch - 'a';
        
        // Create node if doesn't exist
        if (current.children[index] == null) {
            current.children[index] = new TrieNode();
        }
        
        // Move to next node
        current = current.children[index];
    }
    // Mark word end
    current.isEndOfWord = true;
}
```

### Search Pattern
```java
public boolean search(String word) {
    TrieNode current = root;
    for (char ch : word.toCharArray()) {
        int index = ch - 'a';
        
        // If path doesn't exist, word not found
        if (current.children[index] == null) {
            return false;
        }
        
        current = current.children[index];
    }
    // Word exists only if this node marks end
    return current.isEndOfWord;
}
```

### Prefix Search Pattern
```java
public boolean startsWith(String prefix) {
    TrieNode current = root;
    for (char ch : prefix.toCharArray()) {
        int index = ch - 'a';
        
        if (current.children[index] == null) {
            return false;  // Prefix doesn't exist
        }
        
        current = current.children[index];
    }
    return true;  // Prefix exists (don't need isEndOfWord)
}
```

## Advanced Patterns

### Collect All Words with Prefix
```java
public List<String> wordsWithPrefix(String prefix) {
    List<String> result = new ArrayList<>();
    TrieNode prefixNode = searchPrefix(prefix);
    
    if (prefixNode != null) {
        dfs(prefixNode, new StringBuilder(prefix), result);
    }
    
    return result;
}

private void dfs(TrieNode node, StringBuilder current, List<String> result) {
    if (node.isEndOfWord) {
        result.add(current.toString());
    }
    
    for (int i = 0; i < 26; i++) {
        if (node.children[i] != null) {
            current.append((char) ('a' + i));
            dfs(node.children[i], current, result);
            current.deleteCharAt(current.length() - 1);  // backtrack
        }
    }
}
```

### Delete Word from Trie
```java
public void delete(String word) {
    delete(root, word, 0);
}

private boolean delete(TrieNode current, String word, int index) {
    if (index == word.length()) {
        // Reached end of word
        if (!current.isEndOfWord) {
            return false;  // Word doesn't exist
        }
        current.isEndOfWord = false;
        
        // Delete node only if it has no children
        return !hasChildren(current);
    }
    
    char ch = word.charAt(index);
    int charIndex = ch - 'a';
    TrieNode node = current.children[charIndex];
    
    if (node == null) {
        return false;  // Word doesn't exist
    }
    
    boolean shouldDeleteChild = delete(node, word, index + 1);
    
    if (shouldDeleteChild) {
        current.children[charIndex] = null;
        
        // Delete current node if:
        // 1. It's not end of another word
        // 2. It has no other children
        return !current.isEndOfWord && !hasChildren(current);
    }
    
    return false;
}

private boolean hasChildren(TrieNode node) {
    for (TrieNode child : node.children) {
        if (child != null) return true;
    }
    return false;
}
```

## Quick Reference: Common Index Conversions

```java
// Lowercase letters (a-z)
int index = ch - 'a';          // 'a'=0, 'b'=1, ..., 'z'=25
char ch = (char) ('a' + index); // Convert back

// Uppercase letters (A-Z)  
int index = ch - 'A';          // 'A'=0, 'B'=1, ..., 'Z'=25

// Digits (0-9)
int index = ch - '0';          // '0'=0, '1'=1, ..., '9'=9

// Mixed case + digits (larger alphabet)
// Use HashMap<Character, TrieNode> instead of array
```

## Memory Optimization Tricks

### 1. HashMap for Sparse Alphabets
```java
// Instead of TrieNode[26] children, use:
Map<Character, TrieNode> children = new HashMap<>();

// Access pattern:
if (!current.children.containsKey(ch)) {
    current.children.put(ch, new TrieNode());
}
current = current.children.get(ch);
```

### 2. Store Word at End Node
```java
class TrieNode {
    Map<Character, TrieNode> children;
    String word;  // Store complete word here instead of boolean
    
    public TrieNode() {
        children = new HashMap<>();
        word = null;
    }
}

// Usage:
public boolean search(String word) {
    TrieNode node = searchPrefix(word);
    return node != null && node.word != null;
}
```

## Performance Tips

### Time Complexity Quick Check
- **Insert/Search/StartsWith**: O(m) where m = string length
- **Delete**: O(m)
- **Find all words with prefix**: O(ALPHABET_SIZE^(max_depth))

### Space Optimization
```java
// For lowercase only, fixed size array is faster
TrieNode[] children = new TrieNode[26];

// For variable alphabet, HashMap saves space  
Map<Character, TrieNode> children = new HashMap<>();
```

## Common Gotchas

### 1. Forgetting End-of-Word Marker
```java
// ❌ Wrong - missing isEndOfWord check
public boolean search(String word) {
    TrieNode node = searchPrefix(word);
    return node != null;  // Wrong! This finds prefixes too
}

// ✅ Correct
public boolean search(String word) {
    TrieNode node = searchPrefix(word);
    return node != null && node.isEndOfWord;
}
```

### 2. Character Index Conversion
```java
// ❌ Wrong for mixed case
int index = ch - 'a';  // Fails for uppercase letters

// ✅ Better - handle case
int index = Character.toLowerCase(ch) - 'a';

// ✅ Or use HashMap for flexibility
children.computeIfAbsent(ch, k -> new TrieNode());
```

### 3. Null Pointer Exceptions
```java
// ❌ Dangerous
current = current.children[index];  // Might be null

// ✅ Safe
if (current.children[index] == null) {
    return false;  // Or handle appropriately
}
current = current.children[index];
```

## Testing Template

```java
public static void main(String[] args) {
    Trie trie = new Trie();
    
    // Test basic operations
    trie.insert("apple");
    System.out.println(trie.search("apple"));   // true
    System.out.println(trie.search("app"));     // false
    System.out.println(trie.startsWith("app")); // true
    
    trie.insert("app");
    System.out.println(trie.search("app"));     // true
}
```

## Quick Debugging

```java
// Add this method to visualize trie structure
public void printTrie() {
    printTrie(root, "");
}

private void printTrie(TrieNode node, String prefix) {
    if (node.isEndOfWord) {
        System.out.println("Word: " + prefix);
    }
    
    for (int i = 0; i < 26; i++) {
        if (node.children[i] != null) {
            char ch = (char) ('a' + i);
            printTrie(node.children[i], prefix + ch);
        }
    }
}
```