# Evaluate Division - Study Guide

## Visual Understanding

### Problem Intuition

Think of division relationships as a chain:
- If `a / b = 2.0` and `b / c = 3.0`, then `a / c = 2.0 × 3.0 = 6.0`
- This is like finding a path through connected nodes and multiplying weights

```
Given: a/b = 2.0, b/c = 3.0
Query: a/c = ?

Visual representation:
    2.0        3.0
a -----→ b -----→ c
  ←-----   ←-----
   0.5      0.33

Answer: Follow path a → b → c, multiply: 2.0 × 3.0 = 6.0
```

### Graph Representation

Each equation creates a **bidirectional weighted edge**:

```
Equation: a / b = 2.0

Creates two edges:
- a → b with weight 2.0 (forward)
- b → a with weight 0.5 (reciprocal: 1/2.0)

Graph Structure:
graph = {
    "a": {"b": 2.0},
    "b": {"a": 0.5, "c": 3.0},
    "c": {"b": 0.33}
}
```

## Detailed Walkthrough

### Example 1: Basic Case

**Input**:
```
equations = [["a","b"], ["b","c"]]
values = [2.0, 3.0]
queries = [["a","c"], ["b","a"], ["a","e"], ["a","a"], ["x","x"]]
```

#### Step 1: Build the Graph

Process `equations[0]`: `["a","b"]`, `value = 2.0`
```
graph["a"]["b"] = 2.0
graph["b"]["a"] = 1/2.0 = 0.5
```

Process `equations[1]`: `["b","c"]`, `value = 3.0`
```
graph["b"]["c"] = 3.0
graph["c"]["b"] = 1/3.0 = 0.333...
```

**Final Graph**:
```
a: {b: 2.0}
b: {a: 0.5, c: 3.0}
c: {b: 0.333...}
```

Visual:
```
     2.0         3.0
a ←----→ b ←----→ c
    0.5      0.333
```

#### Step 2: Process Queries

**Query 1: `["a","c"]`** (Find a/c)
```
DFS from "a" to "c":
1. Start at "a", product = 1.0, visited = {}
2. Mark "a" as visited: visited = {"a"}
3. Explore neighbors of "a": {"b": 2.0}
4. Visit "b": product = 1.0 × 2.0 = 2.0, visited = {"a", "b"}
5. Explore neighbors of "b": {"a": 0.5, "c": 3.0}
6. Skip "a" (already visited)
7. Visit "c": product = 2.0 × 3.0 = 6.0
8. Found destination "c"!
Result: 6.0
```

**Query 2: `["b","a"]`** (Find b/a)
```
DFS from "b" to "a":
1. Start at "b", product = 1.0, visited = {}
2. Mark "b" as visited
3. Explore neighbors: {"a": 0.5, "c": 3.0}
4. Visit "a": product = 1.0 × 0.5 = 0.5
5. Found destination "a"!
Result: 0.5
```

**Query 3: `["a","e"]`** (Find a/e)
```
Check: Is "e" in graph? NO
Result: -1.0
```

**Query 4: `["a","a"]`** (Find a/a)
```
Check: Is "a" in graph? YES
Check: source == destination? YES
Result: 1.0 (any number divided by itself is 1)
```

**Query 5: `["x","x"]`** (Find x/x)
```
Check: Is "x" in graph? NO
Result: -1.0 (undefined variable)
```

**Final Output**: `[6.0, 0.5, -1.0, 1.0, -1.0]`

## Code Walkthrough

### Building the Graph

```java
Map<String, Map<String, Double>> graph = new HashMap<>();

for (int i = 0; i < equations.size(); i++) {
    String dividend = equations.get(i).get(0);  // "a"
    String divisor = equations.get(i).get(1);   // "b"
    double value = values[i];                    // 2.0

    // Add forward edge: a → b with weight 2.0
    graph.putIfAbsent(dividend, new HashMap<>());
    graph.get(dividend).put(divisor, value);

    // Add reciprocal edge: b → a with weight 0.5
    graph.putIfAbsent(divisor, new HashMap<>());
    graph.get(divisor).put(dividend, 1.0 / value);
}
```

### DFS Path Finding

```java
private double dfs(String start, String end,
                   Map<String, Map<String, Double>> graph,
                   Set<String> visited) {
    // Base case: reached destination
    if (start.equals(end)) {
        return 1.0;
    }

    visited.add(start);

    // Explore all neighbors
    Map<String, Double> neighbors = graph.get(start);
    for (Map.Entry<String, Double> neighbor : neighbors.entrySet()) {
        String next = neighbor.getKey();
        double weight = neighbor.getValue();

        if (visited.contains(next)) continue;

        // Recursively find path from neighbor to end
        double result = dfs(next, end, graph, visited);

        if (result != -1.0) {
            // Found a path! Multiply current weight with result
            return weight * result;
        }
    }

    // No path found
    return -1.0;
}
```

## Execution Trace: Complex Example

**Input**:
```
equations = [["a","b"], ["b","c"], ["c","d"]]
values = [2.0, 3.0, 4.0]
query = ["a","d"]
```

**Graph**:
```
     2.0         3.0         4.0
a ←----→ b ←----→ c ←----→ d
    0.5      0.333      0.25
```

**DFS Trace for `a → d`**:

```
Call Stack:                     Product:        Visited:

dfs("a", "d", 1.0)             1.0             {"a"}
├─ explore neighbor "b"
│  dfs("b", "d", 2.0)          2.0             {"a", "b"}
│  ├─ explore neighbor "c"
│  │  dfs("c", "d", 6.0)       6.0             {"a", "b", "c"}
│  │  ├─ explore neighbor "d"
│  │  │  dfs("d", "d", 24.0)   24.0            {"a", "b", "c", "d"}
│  │  │  └─ FOUND! return 1.0
│  │  └─ return 4.0 × 1.0 = 4.0
│  └─ return 3.0 × 4.0 = 12.0
└─ return 2.0 × 12.0 = 24.0

Final Result: 24.0
```

**Verification**: `a/d = (a/b) × (b/c) × (c/d) = 2.0 × 3.0 × 4.0 = 24.0` ✓

## Common Pitfalls and Edge Cases

### 1. Forgetting Reciprocal Edges
```
❌ WRONG: Only adding a → b
graph.get("a").put("b", 2.0);

✓ CORRECT: Adding both directions
graph.get("a").put("b", 2.0);
graph.get("b").put("a", 0.5);  // 1/2.0
```

### 2. Self-Division
```
Query: ["a", "a"]

❌ WRONG: Running DFS (unnecessary)
✓ CORRECT: Check if variable exists, return 1.0
if (graph.containsKey(start) && start.equals(end)) {
    return 1.0;
}
```

### 3. Undefined Variables
```
Query: ["x", "y"] where x doesn't exist

❌ WRONG: Returning 0.0 or null
✓ CORRECT: Return -1.0 for undefined variables
if (!graph.containsKey(start) || !graph.containsKey(end)) {
    return -1.0;
}
```

### 4. Cycle Detection
```
Graph: a ↔ b ↔ c (with cycles)

❌ WRONG: Infinite recursion
✓ CORRECT: Use visited set to prevent revisiting nodes
if (visited.contains(next)) continue;
```

## Optimization: Union-Find Alternative

For scenarios with many queries, Union-Find with path compression can be more efficient:

```java
// Each node stores: parent and weight to parent
Map<String, String> parent = new HashMap<>();
Map<String, Double> weight = new HashMap<>();

// Find with path compression
String find(String x) {
    if (!parent.get(x).equals(x)) {
        String original = parent.get(x);
        parent.put(x, find(original));
        weight.put(x, weight.get(x) * weight.get(original));
    }
    return parent.get(x);
}

// Query becomes O(α(n)) amortized
```

## Time Complexity Analysis

### DFS Approach (Implemented Solution)

**Per Query**:
- Worst case: Visit all V nodes and E edges
- Time: O(V + E)

**Total**:
- Build graph: O(E)
- Process Q queries: O(Q × (V + E))
- Overall: **O(E + Q × (V + E))**

### Union-Find Approach

**Per Query**: O(α(n)) amortized (inverse Ackermann function, nearly constant)

**Total**: **O(E × α(n) + Q × α(n))** where α(n) ≈ constant

**Trade-off**: Union-Find is faster for many queries but more complex to implement with weights.

## Interview Tips

1. **Clarify the problem**: Confirm that division relationships are transitive
2. **Start with graph intuition**: Explain the weighted directed graph model
3. **Handle edge cases first**: Undefined variables, self-division
4. **Optimize if asked**: Mention Union-Find for query-heavy scenarios
5. **Test thoroughly**: Include cycles, missing variables, and reciprocals

## Practice Variations

1. Implement using BFS instead of DFS
2. Add support for multiplication queries (a × b)
3. Handle equations with contradictions (detect inconsistencies)
4. Implement the Union-Find approach
5. Support updates (adding new equations dynamically)
