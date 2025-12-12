package leetcode.graph.problems.EvaluateDivision;

import java.util.*;

/**
 * LeetCode 399: Evaluate Division
 *
 * Solution using Weighted Directed Graph with DFS
 *
 * Approach:
 * 1. Build a weighted graph where each equation a/b = k creates:
 *    - Edge from a to b with weight k
 *    - Edge from b to a with weight 1/k (reciprocal)
 * 2. For each query, use DFS to find path and multiply edge weights
 * 3. Handle special cases: undefined variables, self-division
 *
 * Time Complexity: O(E + Q * (V + E))
 *   - E: number of equations
 *   - Q: number of queries
 *   - V: number of unique variables
 * Space Complexity: O(E + V) for graph and visited set
 */
public class EvaluateDivision {

    /**
     * Main solution method
     * @param equations List of variable pairs [A, B] representing A/B
     * @param values Division values where values[i] = equations[i][0] / equations[i][1]
     * @param queries List of query pairs [C, D] to evaluate C/D
     * @return Array of results for each query, -1.0 if cannot be determined
     */
    public double[] calcEquation(List<List<String>> equations, double[] values,
                                 List<List<String>> queries) {
        // Step 1: Build the weighted directed graph
        Map<String, Map<String, Double>> graph = buildGraph(equations, values);

        // Step 2: Process each query
        double[] results = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String dividend = queries.get(i).get(0);
            String divisor = queries.get(i).get(1);

            // Check if variables exist in the graph
            if (!graph.containsKey(dividend) || !graph.containsKey(divisor)) {
                results[i] = -1.0;
            }
            // Self-division: a/a = 1.0
            else if (dividend.equals(divisor)) {
                results[i] = 1.0;
            }
            // Find path using DFS
            else {
                Set<String> visited = new HashSet<>();
                results[i] = dfs(dividend, divisor, graph, visited);
            }
        }

        return results;
    }

    /**
     * Build weighted directed graph from equations
     * Each equation a/b = k creates two edges:
     *   - a → b with weight k
     *   - b → a with weight 1/k
     */
    private Map<String, Map<String, Double>> buildGraph(List<List<String>> equations,
                                                         double[] values) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String dividend = equations.get(i).get(0);
            String divisor = equations.get(i).get(1);
            double value = values[i];

            // Add forward edge: dividend → divisor
            graph.putIfAbsent(dividend, new HashMap<>());
            graph.get(dividend).put(divisor, value);

            // Add reciprocal edge: divisor → dividend
            graph.putIfAbsent(divisor, new HashMap<>());
            graph.get(divisor).put(dividend, 1.0 / value);
        }

        return graph;
    }

    /**
     * DFS to find path from start to end and calculate product of edge weights
     * @param current Current node in traversal
     * @param target Target node to reach
     * @param graph Weighted adjacency list
     * @param visited Set of visited nodes to avoid cycles
     * @return Product of edge weights along path, or -1.0 if no path exists
     */
    private double dfs(String current, String target,
                      Map<String, Map<String, Double>> graph,
                      Set<String> visited) {
        // Base case: reached the target
        if (current.equals(target)) {
            return 1.0;
        }

        // Mark current node as visited
        visited.add(current);

        // Explore all neighbors
        Map<String, Double> neighbors = graph.get(current);
        for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
            String neighbor = entry.getKey();
            double weight = entry.getValue();

            // Skip if already visited (avoid cycles)
            if (visited.contains(neighbor)) {
                continue;
            }

            // Recursively search from neighbor
            double result = dfs(neighbor, target, graph, visited);

            // If path found, multiply current edge weight with result
            if (result != -1.0) {
                return weight * result;
            }
        }

        // No path found from this node
        return -1.0;
    }

    /**
     * Alternative BFS implementation (not used in main solution)
     * Can be used instead of DFS for iterative approach
     */
    private double bfs(String start, String end,
                      Map<String, Map<String, Double>> graph) {
        Queue<Pair> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new Pair(start, 1.0));
        visited.add(start);

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            String node = current.node;
            double product = current.value;

            // Found the target
            if (node.equals(end)) {
                return product;
            }

            // Explore neighbors
            Map<String, Double> neighbors = graph.get(node);
            for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
                String neighbor = entry.getKey();
                double weight = entry.getValue();

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(new Pair(neighbor, product * weight));
                }
            }
        }

        return -1.0;
    }

    /**
     * Helper class for BFS approach
     */
    private static class Pair {
        String node;
        double value;

        Pair(String node, double value) {
            this.node = node;
            this.value = value;
        }
    }

    /**
     * Test cases
     */
    public static void main(String[] args) {
        EvaluateDivision solution = new EvaluateDivision();

        // Test Case 1: Basic example with multiple queries
        System.out.println("Test Case 1:");
        List<List<String>> equations1 = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("b", "c")
        );
        double[] values1 = {2.0, 3.0};
        List<List<String>> queries1 = Arrays.asList(
            Arrays.asList("a", "c"),  // Expected: 6.0 (2.0 * 3.0)
            Arrays.asList("b", "a"),  // Expected: 0.5 (1/2.0)
            Arrays.asList("a", "e"),  // Expected: -1.0 (e undefined)
            Arrays.asList("a", "a"),  // Expected: 1.0 (self-division)
            Arrays.asList("x", "x")   // Expected: -1.0 (x undefined)
        );
        double[] result1 = solution.calcEquation(equations1, values1, queries1);
        System.out.println("Input: equations = " + equations1 + ", values = " + Arrays.toString(values1));
        System.out.println("Queries: " + queries1);
        System.out.println("Output: " + Arrays.toString(result1));
        System.out.println("Expected: [6.0, 0.5, -1.0, 1.0, -1.0]\n");

        // Test Case 2: More complex relationships
        System.out.println("Test Case 2:");
        List<List<String>> equations2 = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("b", "c"),
            Arrays.asList("bc", "cd")
        );
        double[] values2 = {1.5, 2.5, 5.0};
        List<List<String>> queries2 = Arrays.asList(
            Arrays.asList("a", "c"),   // Expected: 3.75 (1.5 * 2.5)
            Arrays.asList("c", "b"),   // Expected: 0.4 (1/2.5)
            Arrays.asList("bc", "cd"), // Expected: 5.0
            Arrays.asList("cd", "bc")  // Expected: 0.2 (1/5.0)
        );
        double[] result2 = solution.calcEquation(equations2, values2, queries2);
        System.out.println("Input: equations = " + equations2 + ", values = " + Arrays.toString(values2));
        System.out.println("Queries: " + queries2);
        System.out.println("Output: " + Arrays.toString(result2));
        System.out.println("Expected: [3.75, 0.4, 5.0, 0.2]\n");

        // Test Case 3: Single equation
        System.out.println("Test Case 3:");
        List<List<String>> equations3 = Arrays.asList(
            Arrays.asList("a", "b")
        );
        double[] values3 = {0.5};
        List<List<String>> queries3 = Arrays.asList(
            Arrays.asList("a", "b"),  // Expected: 0.5
            Arrays.asList("b", "a"),  // Expected: 2.0 (1/0.5)
            Arrays.asList("a", "c"),  // Expected: -1.0 (c undefined)
            Arrays.asList("x", "y")   // Expected: -1.0 (both undefined)
        );
        double[] result3 = solution.calcEquation(equations3, values3, queries3);
        System.out.println("Input: equations = " + equations3 + ", values = " + Arrays.toString(values3));
        System.out.println("Queries: " + queries3);
        System.out.println("Output: " + Arrays.toString(result3));
        System.out.println("Expected: [0.5, 2.0, -1.0, -1.0]\n");

        // Test Case 4: Chain of divisions
        System.out.println("Test Case 4:");
        List<List<String>> equations4 = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("b", "c"),
            Arrays.asList("c", "d")
        );
        double[] values4 = {2.0, 3.0, 4.0};
        List<List<String>> queries4 = Arrays.asList(
            Arrays.asList("a", "d"),  // Expected: 24.0 (2.0 * 3.0 * 4.0)
            Arrays.asList("d", "a"),  // Expected: 0.04166... (1/24.0)
            Arrays.asList("a", "c"),  // Expected: 6.0 (2.0 * 3.0)
            Arrays.asList("b", "d")   // Expected: 12.0 (3.0 * 4.0)
        );
        double[] result4 = solution.calcEquation(equations4, values4, queries4);
        System.out.println("Input: equations = " + equations4 + ", values = " + Arrays.toString(values4));
        System.out.println("Queries: " + queries4);
        System.out.println("Output: " + Arrays.toString(result4));
        System.out.println("Expected: [24.0, 0.041666..., 6.0, 12.0]\n");

        // Test Case 5: Disconnected components
        System.out.println("Test Case 5:");
        List<List<String>> equations5 = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("c", "d")
        );
        double[] values5 = {2.0, 3.0};
        List<List<String>> queries5 = Arrays.asList(
            Arrays.asList("a", "c"),  // Expected: -1.0 (no path)
            Arrays.asList("b", "d"),  // Expected: -1.0 (no path)
            Arrays.asList("a", "b"),  // Expected: 2.0
            Arrays.asList("c", "d")   // Expected: 3.0
        );
        double[] result5 = solution.calcEquation(equations5, values5, queries5);
        System.out.println("Input: equations = " + equations5 + ", values = " + Arrays.toString(values5));
        System.out.println("Queries: " + queries5);
        System.out.println("Output: " + Arrays.toString(result5));
        System.out.println("Expected: [-1.0, -1.0, 2.0, 3.0]\n");
    }
}
