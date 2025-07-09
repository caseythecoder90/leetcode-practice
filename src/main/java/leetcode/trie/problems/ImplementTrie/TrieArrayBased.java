package leetcode.trie.problems.ImplementTrie;

/**
 * LeetCode 208: Implement Trie (Prefix Tree)
 *
 * Array-based implementation using fixed-size arrays for children.
 * Most memory efficient for dense tries with many common prefixes.
 *
 * Time Complexity:
 * - Insert: O(m) where m is length of word
 * - Search: O(m) where m is length of word
 * - StartsWith: O(m) where m is length of prefix
 *
 * Space Complexity: O(ALPHABET_SIZE * N * M) where N is number of nodes, M is average depth
 */
public class TrieArrayBased {

    private static final int ALPHABET_SIZE = 26;

    private TrieNode root;

    /** Initialize your data structure here. */
    public TrieArrayBased() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            int index = c - 'a';

            // Create new node if path doesn't exist
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }

            current = current.children[index];
        }

        // Mark end of word
        current.isEndOfWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    /**
     * Helper method to search for a prefix in the trie
     * @param prefix the prefix to search for
     * @return the node where the prefix ends, or null if prefix doesn't exist
     */
    private TrieNode searchPrefix(String prefix) {
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            int index = c - 'a';

            if (current.children[index] == null) {
                return null; // Prefix doesn't exist
            }

            current = current.children[index];
        }

        return current;
    }

    /**
     * TrieNode class for array-based implementation
     */
    private static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        public TrieNode() {
            children = new TrieNode[ALPHABET_SIZE];
            isEndOfWord = false;
        }
    }

    /**
     * Test cases for the Trie implementation
     */
    public static void main(String[] args) {
        TrieArrayBased trie = new TrieArrayBased();

        // Test Case 1: Basic operations
        System.out.println("=== Test Case 1: Basic Operations ===");
        trie.insert("apple");
        System.out.println("search('apple'): " + trie.search("apple"));     // Expected: true
        System.out.println("search('app'): " + trie.search("app"));         // Expected: false
        System.out.println("startsWith('app'): " + trie.startsWith("app")); // Expected: true

        trie.insert("app");
        System.out.println("search('app'): " + trie.search("app"));         // Expected: true

        // Test Case 2: Multiple words with common prefixes
        System.out.println("\n=== Test Case 2: Common Prefixes ===");
        trie.insert("application");
        trie.insert("apply");
        trie.insert("appreciate");

        System.out.println("search('app'): " + trie.search("app"));                     // Expected: true
        System.out.println("search('apple'): " + trie.search("apple"));                 // Expected: true
        System.out.println("search('application'): " + trie.search("application"));     // Expected: true
        System.out.println("search('apply'): " + trie.search("apply"));                 // Expected: true
        System.out.println("search('appreciate'): " + trie.search("appreciate"));       // Expected: true

        System.out.println("startsWith('app'): " + trie.startsWith("app"));             // Expected: true
        System.out.println("startsWith('appl'): " + trie.startsWith("appl"));           // Expected: true
        System.out.println("startsWith('applic'): " + trie.startsWith("applic"));       // Expected: true

        // Test Case 3: Non-existent words and prefixes
        System.out.println("\n=== Test Case 3: Non-existent ===");
        System.out.println("search('banana'): " + trie.search("banana"));               // Expected: false
        System.out.println("search('applica'): " + trie.search("applica"));             // Expected: false
        System.out.println("startsWith('ban'): " + trie.startsWith("ban"));             // Expected: false
        System.out.println("startsWith('xyz'): " + trie.startsWith("xyz"));             // Expected: false

        // Test Case 4: Edge cases
        System.out.println("\n=== Test Case 4: Edge Cases ===");
        trie.insert("a");
        trie.insert("");  // Empty string (edge case)
        System.out.println("search('a'): " + trie.search("a"));                         // Expected: true
        System.out.println("search(''): " + trie.search(""));                           // Expected: true
        System.out.println("startsWith(''): " + trie.startsWith(""));                   // Expected: true

        // Test Case 5: Overlapping words
        System.out.println("\n=== Test Case 5: Overlapping Words ===");
        TrieArrayBased newTrie = new TrieArrayBased();
        newTrie.insert("cat");
        newTrie.insert("cats");
        newTrie.insert("caterpillar");

        System.out.println("search('cat'): " + newTrie.search("cat"));                  // Expected: true
        System.out.println("search('cats'): " + newTrie.search("cats"));                // Expected: true
        System.out.println("search('caterpillar'): " + newTrie.search("caterpillar"));  // Expected: true
        System.out.println("startsWith('cat'): " + newTrie.startsWith("cat"));          // Expected: true
        System.out.println("search('cate'): " + newTrie.search("cate"));                // Expected: false
    }
}