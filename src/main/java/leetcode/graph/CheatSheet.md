# Graph CheatSheet

## Quick Templates

### Graph Representation - Adjacency List

```java
// From edge list to adjacency list (undirected)
Map<Integer, List<Integer>> buildGraph(int n, int[][] edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
        graph.put(i, new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]); // Remove for directed graph
    }
    return graph;
}

// Alternative: Using List of Lists
List<List<Integer>> buildGraph(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    return graph;
}
```

### BFS Template (Shortest Path in Unweighted Graph)

```java
int bfs(Map<Integer, List<Integer>> graph, int start, int target) {
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();

    queue.offer(start);
    visited.add(start);
    int distance = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int node = queue.poll();

            if (node == target) {
                return distance;
            }

            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        distance++;
    }
    return -1; // Target not reachable
}
```

### BFS Template (Multi-Source)

```java
// Used for problems like "Rotting Oranges", "Walls and Gates"
int multiSourceBFS(int[][] grid) {
    Queue<int[]> queue = new LinkedList<>();
    int m = grid.length, n = grid[0].length;

    // Add all sources to queue
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (isSource(grid[i][j])) {
                queue.offer(new int[]{i, j});
            }
        }
    }

    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int steps = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] cell = queue.poll();
            for (int[] dir : directions) {
                int newRow = cell[0] + dir[0];
                int newCol = cell[1] + dir[1];
                if (isValid(newRow, newCol, m, n) && canVisit(grid[newRow][newCol])) {
                    grid[newRow][newCol] = markVisited();
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        steps++;
    }
    return steps;
}
```

### DFS Template (Recursive)

```java
void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
    visited.add(node);

    // Process node here

    for (int neighbor : graph.get(node)) {
        if (!visited.contains(neighbor)) {
            dfs(graph, neighbor, visited);
        }
    }
}
```

### DFS Template (Iterative with Stack)

```java
void dfsIterative(Map<Integer, List<Integer>> graph, int start) {
    Stack<Integer> stack = new Stack<>();
    Set<Integer> visited = new HashSet<>();

    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();

        if (visited.contains(node)) continue;
        visited.add(node);

        // Process node here

        for (int neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                stack.push(neighbor);
            }
        }
    }
}
```

### DFS on Grid (Island Pattern)

```java
int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

void dfsGrid(int[][] grid, int row, int col, boolean[][] visited) {
    int m = grid.length, n = grid[0].length;

    if (row < 0 || row >= m || col < 0 || col >= n) return;
    if (visited[row][col] || grid[row][col] == 0) return;

    visited[row][col] = true;

    for (int[] dir : directions) {
        dfsGrid(grid, row + dir[0], col + dir[1], visited);
    }
}

// Count islands pattern
int countIslands(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    int count = 0;

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1 && !visited[i][j]) {
                dfsGrid(grid, i, j, visited);
                count++;
            }
        }
    }
    return count;
}
```

### Cycle Detection (Directed Graph - DFS)

```java
// Uses three states: unvisited (0), visiting (1), visited (2)
boolean hasCycle(Map<Integer, List<Integer>> graph, int n) {
    int[] state = new int[n]; // 0: unvisited, 1: visiting, 2: visited

    for (int i = 0; i < n; i++) {
        if (state[i] == 0 && hasCycleDFS(graph, i, state)) {
            return true;
        }
    }
    return false;
}

boolean hasCycleDFS(Map<Integer, List<Integer>> graph, int node, int[] state) {
    state[node] = 1; // Mark as visiting

    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (state[neighbor] == 1) {
            return true; // Back edge found - cycle!
        }
        if (state[neighbor] == 0 && hasCycleDFS(graph, neighbor, state)) {
            return true;
        }
    }

    state[node] = 2; // Mark as visited
    return false;
}
```

### Cycle Detection (Undirected Graph - DFS)

```java
boolean hasCycleUndirected(Map<Integer, List<Integer>> graph, int n) {
    boolean[] visited = new boolean[n];

    for (int i = 0; i < n; i++) {
        if (!visited[i] && hasCycleDFS(graph, i, -1, visited)) {
            return true;
        }
    }
    return false;
}

boolean hasCycleDFS(Map<Integer, List<Integer>> graph, int node, int parent, boolean[] visited) {
    visited[node] = true;

    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (!visited[neighbor]) {
            if (hasCycleDFS(graph, neighbor, node, visited)) {
                return true;
            }
        } else if (neighbor != parent) {
            return true; // Visited node that isn't parent = cycle
        }
    }
    return false;
}
```

### Topological Sort (Kahn's Algorithm - BFS)

```java
List<Integer> topologicalSort(int n, int[][] edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] inDegree = new int[n];

    // Build graph and calculate in-degrees
    for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        inDegree[edge[1]]++;
    }

    // Start with nodes having 0 in-degree
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }

    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);

        for (int neighbor : graph.get(node)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }

    // If result size != n, there's a cycle
    return result.size() == n ? result : new ArrayList<>();
}
```

### Topological Sort (DFS)

```java
List<Integer> topologicalSortDFS(int n, int[][] edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
    }

    boolean[] visited = new boolean[n];
    boolean[] inStack = new boolean[n]; // For cycle detection
    LinkedList<Integer> result = new LinkedList<>();

    for (int i = 0; i < n; i++) {
        if (!visited[i] && !dfs(graph, i, visited, inStack, result)) {
            return new ArrayList<>(); // Cycle detected
        }
    }
    return result;
}

boolean dfs(Map<Integer, List<Integer>> graph, int node,
            boolean[] visited, boolean[] inStack, LinkedList<Integer> result) {
    visited[node] = true;
    inStack[node] = true;

    for (int neighbor : graph.get(node)) {
        if (inStack[neighbor]) return false; // Cycle
        if (!visited[neighbor] && !dfs(graph, neighbor, visited, inStack, result)) {
            return false;
        }
    }

    inStack[node] = false;
    result.addFirst(node); // Add to front after processing all neighbors
    return true;
}
```

### Union-Find (Disjoint Set Union)

```java
class UnionFind {
    int[] parent;
    int[] rank;
    int components;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return false; // Already connected

        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        components--;
        return true;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int getComponents() {
        return components;
    }
}
```

### Dijkstra's Algorithm (Shortest Path in Weighted Graph)

```java
int[] dijkstra(Map<Integer, List<int[]>> graph, int n, int start) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;

    // Priority queue: {distance, node}
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, start});

    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int d = curr[0], node = curr[1];

        if (d > dist[node]) continue; // Skip outdated entries

        for (int[] neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            int next = neighbor[0], weight = neighbor[1];
            if (dist[node] + weight < dist[next]) {
                dist[next] = dist[node] + weight;
                pq.offer(new int[]{dist[next], next});
            }
        }
    }
    return dist;
}
```

### Bipartite Check (Graph Coloring)

```java
boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] colors = new int[n]; // 0: uncolored, 1: color1, -1: color2

    for (int i = 0; i < n; i++) {
        if (colors[i] == 0 && !bfsColor(graph, i, colors)) {
            return false;
        }
    }
    return true;
}

boolean bfsColor(int[][] graph, int start, int[] colors) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    colors[start] = 1;

    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbor : graph[node]) {
            if (colors[neighbor] == colors[node]) {
                return false; // Same color as neighbor
            }
            if (colors[neighbor] == 0) {
                colors[neighbor] = -colors[node]; // Opposite color
                queue.offer(neighbor);
            }
        }
    }
    return true;
}
```

## Quick Problem Identification

| Problem Type | Key Indicators | Template |
|--------------|----------------|----------|
| **Shortest Path (unweighted)** | "minimum steps", "shortest path" | BFS |
| **Shortest Path (weighted)** | "minimum cost", "cheapest" | Dijkstra |
| **Connected Components** | "groups", "clusters", "islands" | DFS/BFS/Union-Find |
| **Cycle Detection** | "circular", "detect cycle", "valid tree" | DFS with states |
| **Dependencies/Order** | "prerequisites", "schedule", "order" | Topological Sort |
| **Two Groups** | "bipartite", "two teams", "color" | BFS/DFS Coloring |
| **Dynamic Connectivity** | "connect", "union", "same group" | Union-Find |

## Time Complexity Quick Reference

| Algorithm | Time | Space |
|-----------|------|-------|
| BFS/DFS | O(V + E) | O(V) |
| Dijkstra (heap) | O((V + E) log V) | O(V) |
| Topological Sort | O(V + E) | O(V) |
| Union-Find | O(α(n)) per op | O(V) |
| Bipartite Check | O(V + E) | O(V) |

## Common Bugs & Fixes

| Bug | Fix |
|-----|-----|
| Infinite loop | Always use visited set/array |
| Missing edges | Check directed vs undirected (add both directions) |
| Grid bounds error | Check `row >= 0 && row < m && col >= 0 && col < n` |
| Wrong BFS level | Use size variable before inner loop |
| Cycle false positive (undirected) | Track parent node, don't count it |

## Must-Know Problems

1. **200. Number of Islands** (Medium) - Connected components on grid
2. **133. Clone Graph** (Medium) - Graph traversal with cloning
3. **207. Course Schedule** (Medium) - Cycle detection
4. **210. Course Schedule II** (Medium) - Topological sort
5. **994. Rotting Oranges** (Medium) - Multi-source BFS
6. **127. Word Ladder** (Hard) - BFS shortest path
7. **547. Number of Provinces** (Medium) - Union-Find
8. **743. Network Delay Time** (Medium) - Dijkstra

## Pro Tips

1. **Always clarify**: Directed or undirected? Weighted? Can have cycles?
2. **Draw the graph first**: Visualize before coding
3. **Choose right representation**: Adjacency list for sparse, matrix for dense
4. **BFS = shortest path**: When all edges have equal weight
5. **Track visited BEFORE adding to queue**: Prevents duplicates in BFS
6. **Three states for directed cycle detection**: Unvisited, visiting, visited
