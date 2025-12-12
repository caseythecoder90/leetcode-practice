# Evaluate Division

**Difficulty**: Medium
**LeetCode Problem**: [#399 - Evaluate Division](https://leetcode.com/problems/evaluate-division/)

## Problem Description

You are given an array of variable pairs `equations` and an array of real numbers `values`, where `equations[i] = [Ai, Bi]` and `values[i]` represent the equation `Ai / Bi = values[i]`. Each `Ai` or `Bi` is a string that represents a single variable.

You are also given some `queries`, where `queries[j] = [Cj, Dj]` represents the jth query where you must find the answer for `Cj / Dj = ?`.

Return the answers to all queries. If a single answer cannot be determined, return `-1.0`.

**Note**:
- The input is always valid
- Evaluating the queries will not result in division by zero
- There is no contradiction
- Variables that do not occur in the list of equations are undefined

### Examples

**Example 1:**
```
Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0],
       queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
Explanation:
Given: a / b = 2.0, b / c = 3.0
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
note: x is undefined => -1.0
```

**Example 2:**
```
Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0],
       queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
Output: [3.75000,0.40000,5.00000,0.20000]
```

**Example 3:**
```
Input: equations = [["a","b"]], values = [0.5],
       queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
Output: [0.50000,2.00000,-1.00000,-1.00000]
```

## Approach

This problem can be modeled as a **weighted directed graph**:
- **Nodes**: Variables (strings like "a", "b", "c")
- **Edges**: Division relationships with weights
- **Edge weight**: If `a / b = k`, then there's an edge from `a` to `b` with weight `k`, and an edge from `b` to `a` with weight `1/k`

### Solution Strategy

1. **Build the Graph**
   - Use an adjacency list where each variable maps to its neighbors and their weights
   - For each equation `a / b = k`:
     - Add edge: `a → b` with weight `k`
     - Add edge: `b → a` with weight `1/k` (reciprocal)

2. **Process Each Query**
   - For query `c / d = ?`, find a path from `c` to `d`
   - If either variable doesn't exist in the graph, return `-1.0`
   - If `c == d` and `c` exists, return `1.0`
   - Otherwise, use DFS or BFS to find the path and multiply weights

3. **Path Finding with DFS**
   - Start from the source variable
   - Explore all neighbors, multiplying weights along the path
   - Use a visited set to avoid cycles
   - If destination is reached, return the accumulated product
   - If no path exists, return `-1.0`

### Key Insights

- **Graph Construction**: Each equation creates two directed edges (forward and reciprocal)
- **Path Product**: The answer to `a / c` is the product of all edge weights from `a` to `c`
- **Self-Division**: Any variable divided by itself equals `1.0` (if it exists)
- **Undefined Variables**: Variables not in the graph cannot be evaluated

### Algorithm Steps

```
1. Create adjacency list: Map<String, Map<String, Double>>
2. For each equation [A, B] with value V:
   - graph[A][B] = V
   - graph[B][A] = 1/V
3. For each query [C, D]:
   - If C or D not in graph: result = -1.0
   - Else if C == D: result = 1.0
   - Else: DFS(C, D, 1.0, visited) → multiply weights along path
4. Return all results
```

## Complexity Analysis

Let:
- `E` = number of equations
- `Q` = number of queries
- `V` = number of unique variables

### Time Complexity: **O(E + Q × (V + E))**
- **Building graph**: O(E) - process each equation once
- **Processing queries**: O(Q × (V + E))
  - Each query may visit all nodes (V) and edges (E) in worst case
  - DFS traversal: O(V + E) per query
  - Total for all queries: O(Q × (V + E))

### Space Complexity: **O(E + V)**
- **Graph storage**: O(E) - store all edges
- **Visited set**: O(V) - track visited nodes during DFS
- **Recursion stack**: O(V) - worst case depth in DFS

## Variations and Extensions

1. **Union-Find Approach**: Use weighted union-find for better performance with many queries
2. **Floyd-Warshall**: Precompute all pairs shortest paths if queries >> equations
3. **Bidirectional BFS**: Can be faster for finding paths in large graphs

## Related Patterns

- **Graph Traversal**: DFS/BFS for path finding
- **Weighted Graph**: Edge weights represent division ratios
- **Union-Find**: Alternative approach for disjoint set operations

## Tags

`Graph` `DFS` `BFS` `Weighted Graph` `Hash Map` `Path Finding`
