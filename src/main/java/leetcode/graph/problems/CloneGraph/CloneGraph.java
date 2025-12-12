package leetcode.graph.problems.CloneGraph;

import java.util.*;

/**
 * 133. Clone Graph
 *
 * Given a reference of a node in a connected undirected graph,
 * return a deep copy (clone) of the graph.
 *
 * Pattern: Graph Traversal + HashMap Cloning
 *
 * Key Insight: Use a HashMap to map original nodes to their clones.
 * This serves two purposes:
 * 1. Track visited nodes (prevent infinite loops on cycles)
 * 2. Store clones so we can reuse them when nodes appear as neighbors
 *
 * The critical step is adding the clone to the HashMap BEFORE recursing
 * on neighbors - this ensures cycles are handled correctly.
 */
public class CloneGraph {

    /**
     * Node class definition (as provided by LeetCode)
     */
    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    /**
     * Approach 1: DFS with HashMap (Recommended)
     *
     * Time Complexity: O(N + E) where N = nodes, E = edges
     * Space Complexity: O(N) for HashMap + O(N) for recursion stack
     */
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        return dfs(node, new HashMap<>());
    }

    /**
     * DFS helper that clones nodes recursively.
     *
     * @param node    The original node to clone
     * @param visited HashMap mapping original nodes to their clones
     * @return The cloned node
     */
    private Node dfs(Node node, Map<Node, Node> visited) {
        // If we've already cloned this node, return the existing clone
        // This handles cycles and prevents infinite recursion
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        // Create a new clone with the same value (empty neighbors for now)
        Node clone = new Node(node.val);

        // CRITICAL: Add to HashMap BEFORE recursing on neighbors
        // If a neighbor eventually points back to this node, we need
        // the clone to already exist in the map
        visited.put(node, clone);

        // Recursively clone all neighbors and add them to clone's neighbor list
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor, visited));
        }

        return clone;
    }

    /**
     * Approach 2: BFS with HashMap
     *
     * Time Complexity: O(N + E)
     * Space Complexity: O(N) for HashMap + O(N) for queue
     */
    public Node cloneGraphBFS(Node node) {
        if (node == null) {
            return null;
        }

        // HashMap to track original -> clone mapping
        Map<Node, Node> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();

        // Clone the starting node and add to map and queue
        visited.put(node, new Node(node.val));
        queue.offer(node);

        // BFS traversal
        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            // Process each neighbor of the current node
            for (Node neighbor : curr.neighbors) {
                // If neighbor hasn't been cloned yet, clone it
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }

                // Add the cloned neighbor to the current clone's neighbor list
                // visited.get(curr) = clone of current node
                // visited.get(neighbor) = clone of neighbor
                visited.get(curr).neighbors.add(visited.get(neighbor));
            }
        }

        // Return the clone of the starting node
        return visited.get(node);
    }

    /**
     * Approach 3: DFS with HashMap (cleaner single-method version)
     * Uses instance variable for the HashMap
     */
    private Map<Node, Node> visited = new HashMap<>();

    public Node cloneGraphDFSClean(Node node) {
        if (node == null) {
            return null;
        }

        // Already cloned? Return the clone
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        // Create clone and register it
        Node clone = new Node(node.val);
        visited.put(node, clone);

        // Clone neighbors
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(cloneGraphDFSClean(neighbor));
        }

        return clone;
    }

    // ==================== Test Utilities ====================

    /**
     * Build a graph from adjacency list representation
     * adjList[i] contains the neighbors of node (i+1)
     */
    private static Node buildGraph(int[][] adjList) {
        if (adjList == null || adjList.length == 0) {
            return null;
        }

        // Create all nodes first
        Node[] nodes = new Node[adjList.length + 1]; // 1-indexed
        for (int i = 1; i <= adjList.length; i++) {
            nodes[i] = new Node(i);
        }

        // Add neighbors
        for (int i = 0; i < adjList.length; i++) {
            Node node = nodes[i + 1];
            for (int neighborVal : adjList[i]) {
                node.neighbors.add(nodes[neighborVal]);
            }
        }

        return nodes[1]; // Return first node
    }

    /**
     * Convert graph to adjacency list string for verification
     */
    private static String graphToString(Node node) {
        if (node == null) {
            return "[]";
        }

        Map<Integer, List<Integer>> adjList = new TreeMap<>();
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.offer(node);
        visited.add(node);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            List<Integer> neighbors = new ArrayList<>();

            for (Node neighbor : curr.neighbors) {
                neighbors.add(neighbor.val);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }

            Collections.sort(neighbors);
            adjList.put(curr.val, neighbors);
        }

        return adjList.toString();
    }

    /**
     * Verify that clone is a true deep copy (different objects)
     */
    private static boolean isDeepCopy(Node original, Node clone) {
        if (original == null && clone == null) return true;
        if (original == null || clone == null) return false;

        Set<Node> origNodes = new HashSet<>();
        Set<Node> cloneNodes = new HashSet<>();

        collectNodes(original, origNodes);
        collectNodes(clone, cloneNodes);

        // Check no shared references
        for (Node cloneNode : cloneNodes) {
            if (origNodes.contains(cloneNode)) {
                return false; // Found shared reference!
            }
        }
        return true;
    }

    private static void collectNodes(Node node, Set<Node> visited) {
        if (node == null || visited.contains(node)) return;
        visited.add(node);
        for (Node neighbor : node.neighbors) {
            collectNodes(neighbor, visited);
        }
    }

    public static void main(String[] args) {
        CloneGraph solution = new CloneGraph();

        // Test Case 1: Square graph (4 nodes)
        System.out.println("=== Test 1: Square Graph ===");
        int[][] adjList1 = {{2, 4}, {1, 3}, {2, 4}, {1, 3}};
        Node original1 = buildGraph(adjList1);
        Node clone1 = solution.cloneGraph(original1);

        System.out.println("Original: " + graphToString(original1));
        System.out.println("Clone:    " + graphToString(clone1));
        System.out.println("Is deep copy: " + isDeepCopy(original1, clone1));
        System.out.println("Expected: {1=[2, 4], 2=[1, 3], 3=[2, 4], 4=[1, 3]}");

        // Test Case 2: Single node, no neighbors
        System.out.println("\n=== Test 2: Single Node (No Neighbors) ===");
        Node single = new Node(1);
        Node cloneSingle = solution.cloneGraph(single);

        System.out.println("Original: val=" + single.val + ", neighbors=" + single.neighbors.size());
        System.out.println("Clone:    val=" + cloneSingle.val + ", neighbors=" + cloneSingle.neighbors.size());
        System.out.println("Is deep copy: " + (single != cloneSingle));

        // Test Case 3: Empty graph (null)
        System.out.println("\n=== Test 3: Empty Graph (Null) ===");
        Node cloneNull = solution.cloneGraph(null);
        System.out.println("Clone of null: " + cloneNull);
        System.out.println("Expected: null");

        // Test Case 4: Two nodes connected
        System.out.println("\n=== Test 4: Two Connected Nodes ===");
        int[][] adjList4 = {{2}, {1}};
        Node original4 = buildGraph(adjList4);
        Node clone4 = solution.cloneGraph(original4);

        System.out.println("Original: " + graphToString(original4));
        System.out.println("Clone:    " + graphToString(clone4));
        System.out.println("Is deep copy: " + isDeepCopy(original4, clone4));

        // Test Case 5: BFS approach
        System.out.println("\n=== Test 5: BFS Approach ===");
        Node original5 = buildGraph(adjList1);
        Node clone5 = solution.cloneGraphBFS(original5);

        System.out.println("Original: " + graphToString(original5));
        System.out.println("Clone (BFS): " + graphToString(clone5));
        System.out.println("Is deep copy: " + isDeepCopy(original5, clone5));

        // Test Case 6: Linear chain (3 nodes)
        System.out.println("\n=== Test 6: Linear Chain ===");
        int[][] adjList6 = {{2}, {1, 3}, {2}};
        Node original6 = buildGraph(adjList6);
        Node clone6 = solution.cloneGraph(original6);

        System.out.println("Original: " + graphToString(original6));
        System.out.println("Clone:    " + graphToString(clone6));
        System.out.println("Is deep copy: " + isDeepCopy(original6, clone6));

        // Test Case 7: Triangle (3 nodes, all connected)
        System.out.println("\n=== Test 7: Triangle (Fully Connected) ===");
        int[][] adjList7 = {{2, 3}, {1, 3}, {1, 2}};
        Node original7 = buildGraph(adjList7);
        Node clone7 = solution.cloneGraph(original7);

        System.out.println("Original: " + graphToString(original7));
        System.out.println("Clone:    " + graphToString(clone7));
        System.out.println("Is deep copy: " + isDeepCopy(original7, clone7));
    }
}
