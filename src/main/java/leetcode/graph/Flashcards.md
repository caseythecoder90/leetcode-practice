# Graph Pattern - Flashcards

## Core Concepts

**Q: What is a graph and what are its two main components?**
A: A graph is a data structure consisting of:
1. **Vertices (Nodes)**: The entities/objects
2. **Edges**: The connections/relationships between vertices

Graphs model relationships between things - social networks, road maps, dependencies, etc.

**Q: What are the two main ways to represent a graph in code?**
A:
1. **Adjacency List**: Each node stores a list of its neighbors
   - Space: O(V + E)
   - Best for: Sparse graphs, most interview problems
   ```java
   Map<Integer, List<Integer>> graph = new HashMap<>();
   ```

2. **Adjacency Matrix**: 2D array where matrix[i][j] indicates edge from i to j
   - Space: O(V²)
   - Best for: Dense graphs, quick edge lookup
   ```java
   int[][] graph = new int[n][n];
   ```

**Q: What's the difference between directed and undirected graphs?**
A:
- **Directed**: Edges have direction (A → B doesn't mean B → A)
  - Example: Twitter follows, web page links
  - Add edges one way: `graph.get(a).add(b);`

- **Undirected**: Edges go both ways (A — B means both can reach each other)
  - Example: Facebook friendships, roads
  - Add edges both ways: `graph.get(a).add(b); graph.get(b).add(a);`

**Q: What is a weighted graph vs an unweighted graph?**
A:
- **Unweighted**: All edges are equal (or have implicit weight of 1)
  - Use BFS for shortest path
- **Weighted**: Edges have costs/distances associated with them
  - Use Dijkstra (non-negative weights) or Bellman-Ford (negative weights allowed)

## BFS vs DFS

**Q: When should you use BFS vs DFS?**
A:
**Use BFS when:**
- Finding **shortest path** in unweighted graphs
- **Level-by-level** processing (distance = k from start)
- **Minimum steps** to reach target
- Spreading/propagation problems

**Use DFS when:**
- **Detecting cycles**
- Exploring **all paths**
- **Connected components**
- **Topological sorting**
- **Backtracking** on graphs

**Q: What is the BFS template for graphs?**
A:
```java
void bfs(Map<Integer, List<Integer>> graph, int start) {
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();

    queue.offer(start);
    visited.add(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        // Process node

        for (int neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);  // Mark BEFORE adding to queue!
                queue.offer(neighbor);
            }
        }
    }
}
```
Key: Mark visited BEFORE adding to queue to prevent duplicates.

**Q: What is the DFS template for graphs?**
A:
```java
void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
    visited.add(node);
    // Process node

    for (int neighbor : graph.get(node)) {
        if (!visited.contains(neighbor)) {
            dfs(graph, neighbor, visited);
        }
    }
}
```
Can also be done iteratively with a Stack.

**Q: How do you track distance/levels in BFS?**
A: Use the "size trick" - process all nodes at current level before incrementing:
```java
int distance = 0;
while (!queue.isEmpty()) {
    int size = queue.size();  // Capture size BEFORE loop
    for (int i = 0; i < size; i++) {
        int node = queue.poll();
        // All nodes in this loop are at 'distance' from start
    }
    distance++;
}
```

## Cycle Detection

**Q: How do you detect a cycle in a directed graph?**
A: Use **three states**: unvisited (0), visiting (1), visited (2)
```java
boolean hasCycle(int node, int[] state, Map<Integer, List<Integer>> graph) {
    state[node] = 1;  // Mark as visiting

    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) return true;  // Back edge = cycle!
        if (state[neighbor] == 0 && hasCycle(neighbor, state, graph)) return true;
    }

    state[node] = 2;  // Mark as visited
    return false;
}
```
A back edge to a "visiting" node indicates a cycle.

**Q: How do you detect a cycle in an undirected graph?**
A: Track the **parent node** to avoid false positives:
```java
boolean hasCycle(int node, int parent, boolean[] visited, Map<Integer, List<Integer>> graph) {
    visited[node] = true;

    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            if (hasCycle(neighbor, node, visited, graph)) return true;
        } else if (neighbor != parent) {
            return true;  // Visited node that isn't parent = cycle
        }
    }
    return false;
}
```

## Topological Sort

**Q: What is topological sort and when do you use it?**
A: A linear ordering of vertices in a DAG (Directed Acyclic Graph) where for every edge u→v, u comes before v.

**Use for:**
- Task scheduling with dependencies
- Course prerequisites
- Build order
- Any "order respecting dependencies" problem

**Q: What are the two approaches for topological sort?**
A:
1. **Kahn's Algorithm (BFS)**: Process nodes with in-degree 0 first
   ```java
   // Start with all nodes having in-degree 0
   // Remove them, decrease neighbors' in-degrees
   // Repeat until empty
   ```

2. **DFS-based**: Post-order traversal, add to front of result
   ```java
   // DFS through graph
   // After visiting all neighbors, add node to front of list
   ```

If result size < total nodes, there's a cycle (no valid ordering exists).

**Q: What is Kahn's algorithm template?**
A:
```java
List<Integer> topSort(int n, int[][] edges) {
    int[] inDegree = new int[n];
    // Calculate in-degrees and build graph...

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }

    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        for (int neighbor : graph.get(node)) {
            if (--inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    return result.size() == n ? result : new ArrayList<>();  // Empty = cycle
}
```

## Union-Find

**Q: What is Union-Find and when do you use it?**
A: Union-Find (Disjoint Set Union) is a data structure for tracking elements partitioned into disjoint sets.

**Use for:**
- Dynamic connectivity ("are X and Y connected?")
- Finding connected components
- Detecting cycles in undirected graphs
- Kruskal's MST algorithm

**Q: What are the two key operations in Union-Find?**
A:
1. **Find(x)**: Returns the representative (root) of x's set
2. **Union(x, y)**: Merges the sets containing x and y

With **path compression** and **union by rank**, both operations run in nearly O(1) time - O(α(n)) where α is the inverse Ackermann function.

**Q: What is the Union-Find template?**
A:
```java
class UnionFind {
    int[] parent, rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }

    boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false;  // Already connected

        if (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else { parent[py] = px; rank[px]++; }
        return true;
    }
}
```

## Shortest Path Algorithms

**Q: Which shortest path algorithm should you use?**
A:
| Scenario | Algorithm | Time Complexity |
|----------|-----------|-----------------|
| Unweighted graph | BFS | O(V + E) |
| Weighted, non-negative | Dijkstra | O((V+E) log V) |
| Weighted, negative allowed | Bellman-Ford | O(V × E) |
| All pairs shortest path | Floyd-Warshall | O(V³) |

**Q: What is Dijkstra's algorithm template?**
A:
```java
int[] dijkstra(int n, Map<Integer, List<int[]>> graph, int start) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;

    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);
    pq.offer(new int[]{0, start});  // {distance, node}

    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int d = curr[0], node = curr[1];

        if (d > dist[node]) continue;  // Skip outdated entries

        for (int[] edge : graph.get(node)) {  // {neighbor, weight}
            int next = edge[0], weight = edge[1];
            if (dist[node] + weight < dist[next]) {
                dist[next] = dist[node] + weight;
                pq.offer(new int[]{dist[next], next});
            }
        }
    }
    return dist;
}
```

## Grid as Graph

**Q: How do you treat a 2D grid as a graph?**
A: Each cell is a node, adjacent cells (up/down/left/right) are neighbors.
```java
int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};

void explore(int[][] grid, int row, int col) {
    for (int[] dir : directions) {
        int newRow = row + dir[0];
        int newCol = col + dir[1];
        if (isValid(newRow, newCol)) {
            // Process neighbor
        }
    }
}

boolean isValid(int row, int col) {
    return row >= 0 && row < m && col >= 0 && col < n;
}
```

**Q: What's the pattern for counting islands/connected components on a grid?**
A:
```java
int countIslands(int[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 1) {
                dfs(grid, i, j);  // Mark all connected cells
                count++;
            }
        }
    }
    return count;
}
```

## Common Mistakes

**Q: What are the most common mistakes in graph problems?**
A:
1. **Forgetting visited check** → Infinite loops
2. **Wrong edge direction** → Not handling directed vs undirected properly
3. **BFS level counting** → Must capture queue size before inner loop
4. **Cycle detection in undirected** → Must track parent to avoid false positives
5. **Grid boundary checks** → Always validate row/col bounds
6. **Not handling disconnected graphs** → May need to start traversal from all nodes

## Problem-Solving Strategy

**Q: How do you approach a graph problem in an interview?**
A:
1. **Clarify the graph type**: Directed? Weighted? Cyclic?
2. **Choose representation**: Adjacency list (usually) or matrix
3. **Identify the pattern**: Traversal, shortest path, cycle, connectivity, ordering
4. **Select algorithm**: BFS, DFS, Union-Find, Dijkstra, Topological Sort
5. **Handle edge cases**: Empty graph, single node, disconnected components
6. **Consider complexity**: V vertices, E edges → O(V + E) for traversal
