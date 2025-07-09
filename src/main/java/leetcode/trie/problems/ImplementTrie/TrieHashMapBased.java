package leetcode.trie.problems.ImplementTrie;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 208: Implement Trie (Prefix Tree)
 *
 * HashMap-based implementation using HashMap for children nodes.
 * More memory efficient for sparse tries with diverse character sets.
 * Supports any character set, not just lowercase letters.
 *
 * Time Complexity:
 * - Insert: O(m) where m is length of word
 * - Search: O(m) where m is length of word
 * - StartsWith: O(m) where m is length of prefix
 *
 * Space Complexity: O(N * M) where N is number of unique prefixes, M is average length
 * More space efficient than array approach when alphabet is large or sparse.
 */
public class TrieHashMapBased {

    private TrieNode root;

    /** Initialize your data structure here. */
    public TrieHashMapBased() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            // Create new node if path doesn't exist
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
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
            if (!current.children.containsKey(c)) {
                return null; // Prefix doesn't exist
            }
            current = current.children.get(c);
        }

        return current;
    }

    /**
     * Additional utility method: Get all words with given prefix
     * Useful for autocomplete functionality
     */
    public java.util.List<String> getWordsWithPrefix(String prefix) {
        java.util.List<String> result = new java.util.ArrayList<>();
        TrieNode prefixNode = searchPrefix(prefix);

        if (prefixNode != null) {
            collectAllWords(prefixNode, prefix, result);
        }

        return result;
    }

    /**
     * Helper method to collect all words starting from a given node
     */
    private void collectAllWords(TrieNode node, String currentWord, java.util.List<String> result) {
        if (node.isEndOfWord) {
            result.add(currentWord);
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectAllWords(entry.getValue(), currentWord + entry.getKey(), result);
        }
    }

    /**
     * Additional utility method: Count total words in trie
     */
    public int countWords() {
        return countWordsHelper(root);
    }

    private int countWordsHelper(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;

        for (TrieNode child : node.children.values()) {
            count += countWordsHelper(child);
        }

        return count;
    }

    /**
     * Additional utility method: Check if trie is empty
     */
    public boolean isEmpty() {
        return root.children.isEmpty();
    }

    /**
     * TrieNode class for HashMap-based implementation
     */
    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    /**
     * Test cases for the HashMap-based Trie implementation
     */
    public static void main(String[] args) {
        TrieHashMapBased trie = new TrieHashMapBased();

        // Test Case 1: Basic operations (same as LeetCode example)
        System.out.println("=== Test Case 1: Basic Operations ===");
        trie.insert("apple");
        System.out.println("search('apple'): " + trie.search("apple"));     // Expected: true
        System.out.println("search('app'): " + trie.search("app"));         // Expected: false
        System.out.println("startsWith('app'): " + trie.startsWith("app")); // Expected: true

        trie.insert("app");
        System.out.println("search('app'): " + trie.search("app"));         // Expected: true

        // Test Case 2: Mixed character sets (advantage of HashMap approach)
        System.out.println("\n=== Test Case 2: Mixed Characters ===");
        TrieHashMapBased mixedTrie = new TrieHashMapBased();
        mixedTrie.insert("Hello");       // Uppercase
        mixedTrie.insert("hello");       // Lowercase
        mixedTrie.insert("hello-world"); // With hyphen
        mixedTrie.insert("hello_world"); // With underscore
        mixedTrie.insert("hello123");    // With numbers

        System.out.println("search('Hello'): " + mixedTrie.search("Hello"));             // Expected: true
        System.out.println("search('hello'): " + mixedTrie.search("hello"));             // Expected: true
        System.out.println("search('hello-world'): " + mixedTrie.search("hello-world")); // Expected: true
        System.out.println("search('hello_world'): " + mixedTrie.search("hello_world")); // Expected: true
        System.out.println("search('hello123'): " + mixedTrie.search("hello123"));       // Expected: true

        System.out.println("startsWith('hello'): " + mixedTrie.startsWith("hello"));     // Expected: true
        System.out.println("startsWith('Hello'): " + mixedTrie.startsWith("Hello"));     // Expected: true

        // Test Case 3: Utility methods
        System.out.println("\n=== Test Case 3: Utility Methods ===");
        TrieHashMapBased utilTrie = new TrieHashMapBased();
        System.out.println("isEmpty(): " + utilTrie.isEmpty());              // Expected: true
        System.out.println("countWords(): " + utilTrie.countWords());        // Expected: 0

        utilTrie.insert("cat");
        utilTrie.insert("cats");
        utilTrie.insert("car");
        utilTrie.insert("card");
        utilTrie.insert("care");
        utilTrie.insert("careful");

        System.out.println("isEmpty(): " + utilTrie.isEmpty());              // Expected: false
        System.out.println("countWords(): " + utilTrie.countWords());        // Expected: 6

        // Test Case 4: Autocomplete functionality
        System.out.println("\n=== Test Case 4: Autocomplete ===");
        System.out.println("Words with prefix 'car': " + utilTrie.getWordsWithPrefix("car"));
        // Expected: [car, card, care, careful]

        System.out.println("Words with prefix 'cat': " + utilTrie.getWordsWithPrefix("cat"));
        // Expected: [cat, cats]

        System.out.println("Words with prefix 'care': " + utilTrie.getWordsWithPrefix("care"));
        // Expected: [care, careful]

        System.out.println("Words with prefix 'dog': " + utilTrie.getWordsWithPrefix("dog"));
        // Expected: []

        // Test Case 5: Edge cases
        System.out.println("\n=== Test Case 5: Edge Cases ===");
        TrieHashMapBased edgeTrie = new TrieHashMapBased();
        edgeTrie.insert("");  // Empty string
        edgeTrie.insert("a"); // Single character

        System.out.println("search(''): " + edgeTrie.search(""));           // Expected: true
        System.out.println("search('a'): " + edgeTrie.search("a"));         // Expected: true
        System.out.println("startsWith(''): " + edgeTrie.startsWith(""));   // Expected: true
        System.out.println("countWords(): " + edgeTrie.countWords());       // Expected: 2

        // Test Case 6: Performance comparison hint
        System.out.println("\n=== Test Case 6: Performance Notes ===");
        System.out.println("HashMap approach is better for:");
        System.out.println("- Large or diverse character sets");
        System.out.println("- Sparse tries (few words with many different prefixes)");
        System.out.println("- Unicode characters, special symbols, etc.");
        System.out.println("\nArray approach is better for:");
        System.out.println("- Dense tries with many common prefixes");
        System.out.println("- Small, fixed character sets (like 26 lowercase letters)");
        System.out.println("- When memory usage is critical");
    }
}