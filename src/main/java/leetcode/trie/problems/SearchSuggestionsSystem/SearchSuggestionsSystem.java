package leetcode.trie.problems.SearchSuggestionsSystem;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class SearchSuggestionsSystem {

    private static class TrieNode {
        TrieNode[] children;
        List<String> suggestions; // Store up to 3 suggestions at each node

        public TrieNode() {
            children = new TrieNode[26];
            suggestions = new ArrayList<>();
        }

        public void addSuggestion(String word) {
            suggestions.add(word);
            Collections.sort(suggestions); // Keep sorted

            // Keep only top 3 lexicographically smallest
            if (suggestions.size() > 3) {
                suggestions = suggestions.subList(0, 3);
            }
        }
    }

    private static class Trie {
        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode curr = root;

            // Add word to every node along the path
            for (char c : word.toCharArray()) {
                int index = c - 'a';

                if (curr.children[index] == null) {
                    curr.children[index] = new TrieNode();
                }

                curr = curr.children[index];
                curr.addSuggestion(word); // ✅ Add to EVERY node along path
            }
        }

        public List<String> getSuggestions(String prefix) {
            TrieNode curr = root;

            // Navigate to the prefix
            for (char c : prefix.toCharArray()) {
                int index = c - 'a';
                if (curr.children[index] == null) {
                    return new ArrayList<>(); // No suggestions
                }
                curr = curr.children[index];
            }

            return new ArrayList<>(curr.suggestions);
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Sort products first for lexicographical order
        Arrays.sort(products);

        // Build the Trie
        Trie trie = new Trie();
        for (String product : products) {
            trie.insert(product);
        }

        List<List<String>> result = new ArrayList<>();

        // Get suggestions for each prefix
        for (int i = 1; i <= searchWord.length(); i++) {
            String prefix = searchWord.substring(0, i);
            result.add(trie.getSuggestions(prefix));
        }

        return result;
    }

    // Test method
    public static void main(String[] args) {
        SearchSuggestionsSystem solution = new SearchSuggestionsSystem();

        // Test Case 1: Example from problem
        String[] products1 = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        String searchWord1 = "mouse";
        List<List<String>> result1 = solution.suggestedProducts(products1, searchWord1);

        System.out.println("Test 1:");
        System.out.println("Products: " + Arrays.toString(products1));
        System.out.println("Search word: " + searchWord1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: [[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]");
        System.out.println();

        // Test Case 2: Single word
        String[] products2 = {"havana"};
        String searchWord2 = "havana";
        List<List<String>> result2 = solution.suggestedProducts(products2, searchWord2);

        System.out.println("Test 2:");
        System.out.println("Products: " + Arrays.toString(products2));
        System.out.println("Search word: " + searchWord2);
        System.out.println("Result: " + result2);
        System.out.println();

        // Test Case 3: Multiple words with same prefix
        String[] products3 = {"bags", "baggage", "banner", "box", "cloths"};
        String searchWord3 = "bags";
        List<List<String>> result3 = solution.suggestedProducts(products3, searchWord3);

        System.out.println("Test 3:");
        System.out.println("Products: " + Arrays.toString(products3));
        System.out.println("Search word: " + searchWord3);
        System.out.println("Result: " + result3);
        System.out.println();
    }
}

// Alternative approach: Optimized Trie with indices
class OptimizedSolution {

    private static class TrieNode {
        TrieNode[] children;
        List<Integer> indices; // Store indices instead of full strings

        public TrieNode() {
            children = new TrieNode[26];
            indices = new ArrayList<>();
        }

        public void addIndex(int index) {
            indices.add(index);
            // Keep only first 3 (they're already in sorted order)
            if (indices.size() > 3) {
                indices = indices.subList(0, 3);
            }
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Sort products and create index mapping
        String[] sortedProducts = products.clone();
        Arrays.sort(sortedProducts);

        // Build Trie with indices
        TrieNode root = new TrieNode();
        for (int i = 0; i < sortedProducts.length; i++) {
            String product = sortedProducts[i];
            TrieNode curr = root;

            for (char c : product.toCharArray()) {
                int index = c - 'a';
                if (curr.children[index] == null) {
                    curr.children[index] = new TrieNode();
                }
                curr = curr.children[index];
                curr.addIndex(i); // Add sorted index
            }
        }

        // Generate suggestions
        List<List<String>> result = new ArrayList<>();
        TrieNode curr = root;

        for (char c : searchWord.toCharArray()) {
            int index = c - 'a';

            if (curr != null && curr.children[index] != null) {
                curr = curr.children[index];
                List<String> suggestions = new ArrayList<>();

                // Convert indices to actual product names
                for (int idx : curr.indices) {
                    suggestions.add(sortedProducts[idx]);
                }
                result.add(suggestions);
            } else {
                curr = null; // No more matches possible
                result.add(new ArrayList<>());
            }
        }

        return result;
    }
}