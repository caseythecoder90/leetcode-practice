# Graph Pattern

## Overview

Graphs are one of the most versatile data structures in computer science, representing relationships between entities. They appear frequently in coding interviews and are essential for solving problems involving networks, connections, paths, and relationships.

**Key Insight**: Most graph problems boil down to traversal (BFS/DFS), finding paths, detecting cycles, or computing some property over connected components.

## Core Concepts

### Graph Representation

#### 1. Adjacency List (Most Common in Interviews)
```java
// Using HashMap for dynamic graphs
Map<Integer, List<Integer>> graph = new HashMap<>();

// Using array of lists for numbered nodes (0 to n-1)
List<List<Integer>> graph = new ArrayList<>();
for (int i = 0; i < n; i++) {
    graph.add(new ArrayList<>());
}
```

#### 2. Adjacency Matrix
```java
int[][] graph = new int[n][n];
// graph[i][j] = 1 means edge from i to j
```

#### 3. Edge List
```java
int[][] edges = {{0, 1}, {1, 2}, {2, 0}};
// Each inner array represents an edge
```

### Graph Types

| Type | Description | Example |
|------|-------------|---------|
| **Directed** | Edges have direction (A → B ≠ B → A) | Following on social media |
| **Undirected** | Edges go both ways (A — B) | Facebook friendships |
| **Weighted** | Edges have costs/weights | Road distances |
| **Unweighted** | All edges are equal | Social connections |
| **Cyclic** | Contains at least one cycle | Circular dependencies |
| **Acyclic** | No cycles (DAG for directed) | Task dependencies |
| **Connected** | Path exists between any two nodes | Network of computers |
| **Disconnected** | Multiple separate components | Islands in ocean |

## When to Use Graph Algorithms

### Use BFS (Breadth-First Search) When:
- Finding **shortest path** in unweighted graphs
- **Level-order** processing (nodes at distance k)
- Finding **minimum steps** to reach a target
- **Spreading/propagation** problems (rotting oranges, gates)
- Problems asking for "minimum number of..."

### Use DFS (Depth-First Search) When:
- **Detecting cycles** in graphs
- **Path finding** with constraints
- **Exploring all possibilities** (backtracking on graphs)
- **Connected components** counting
- **Topological sorting** (Kahn's algorithm uses BFS too)
- **Island counting** and grid exploration

### Use Union-Find When:
- **Dynamic connectivity** queries
- **Finding connected components** efficiently
- **Detecting cycles** in undirected graphs
- **Kruskal's MST** algorithm
- Problems asking "are X and Y connected?"

### Use Dijkstra's When:
- **Shortest path** in weighted graphs (non-negative weights)
- **Minimum cost** to reach destination
- Problems involving distances/costs

### Use Bellman-Ford When:
- **Shortest path** with possible **negative weights**
- **Detecting negative cycles**

### Use Topological Sort When:
- **Task scheduling** with dependencies
- **Course prerequisites**
- **Build systems** (determining compilation order)
- Any DAG ordering problem

## Common Graph Patterns

### Pattern 1: Graph Traversal (BFS/DFS)
Basic exploration of all reachable nodes.

**When to Use**: Visiting all nodes, checking connectivity, exploring paths.

### Pattern 2: Shortest Path
Finding minimum distance/steps between nodes.

**When to Use**: Unweighted → BFS, Weighted → Dijkstra/Bellman-Ford.

### Pattern 3: Cycle Detection
Determining if a graph contains cycles.

**When to Use**: Deadlock detection, valid tree check, dependency validation.

### Pattern 4: Connected Components
Grouping nodes that are reachable from each other.

**When to Use**: Island counting, network clusters, friend groups.

### Pattern 5: Topological Sort
Linear ordering of nodes respecting dependencies.

**When to Use**: Task scheduling, build order, course prerequisites.

### Pattern 6: Union-Find (Disjoint Set)
Efficient component tracking with union and find operations.

**When to Use**: Dynamic connectivity, grouping, cycle detection in undirected graphs.

## Problem Identification Keywords

| Keywords | Pattern | Example Problems |
|----------|---------|------------------|
| "shortest path", "minimum steps" | BFS | Word Ladder, Shortest Path in Binary Matrix |
| "all paths", "any path exists" | DFS | All Paths From Source to Target |
| "connected components", "groups" | DFS/Union-Find | Number of Islands, Friend Circles |
| "cycle detection", "circular" | DFS/Union-Find | Course Schedule, Detect Cycle |
| "dependencies", "prerequisites" | Topological Sort | Course Schedule II, Build Order |
| "minimum cost path", "weighted" | Dijkstra | Network Delay Time, Cheapest Flights |
| "bipartite", "two groups" | BFS/DFS coloring | Is Graph Bipartite, Possible Bipartition |
| "reachable", "connected" | BFS/DFS | Keys and Rooms, Employee Importance |

## Directory Structure

```
graph/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
└── problems/
    └── [Individual problem directories]
```

## Learning Path

### Phase 1: Graph Fundamentals (Week 1)
1. **Master graph representations** - Adjacency list vs matrix
2. **Implement BFS from scratch** - Understand queue-based traversal
3. **Implement DFS from scratch** - Understand stack/recursion-based traversal
4. **Practice basic traversal** - Number of Islands, Clone Graph

### Phase 2: Path Finding (Week 2)
5. **BFS for shortest paths** - Word Ladder, Shortest Path in Binary Matrix
6. **DFS for path existence** - All Paths From Source to Target
7. **Understand when to use each** - Practice pattern recognition

### Phase 3: Cycle Detection & Topological Sort (Week 3)
8. **Cycle detection techniques** - Course Schedule, Detect Cycle
9. **Topological sorting** - Course Schedule II, Alien Dictionary
10. **Understand DAGs** - Directed Acyclic Graphs properties

### Phase 4: Advanced Topics (Week 4+)
11. **Union-Find data structure** - Connected components, redundant edges
12. **Dijkstra's algorithm** - Weighted shortest paths
13. **Bipartite graphs** - Two-coloring problems
14. **Graph construction** - Building graphs from problem descriptions

## LeetCode Graph Problems by Difficulty

### Easy
- **733. Flood Fill** - Basic DFS/BFS on grid
- **997. Find the Town Judge** - In-degree/out-degree
- **1971. Find if Path Exists in Graph** - Basic connectivity

### Medium
- **200. Number of Islands** - Connected components (grid)
- **133. Clone Graph** - Graph traversal with cloning
- **207. Course Schedule** - Cycle detection
- **210. Course Schedule II** - Topological sort
- **547. Number of Provinces** - Connected components
- **994. Rotting Oranges** - Multi-source BFS
- **417. Pacific Atlantic Water Flow** - Multi-source DFS
- **1584. Min Cost to Connect All Points** - Minimum Spanning Tree

### Hard
- **127. Word Ladder** - BFS shortest path
- **269. Alien Dictionary** - Topological sort from constraints
- **332. Reconstruct Itinerary** - Eulerian path
- **787. Cheapest Flights Within K Stops** - Modified Dijkstra/BFS

## Time and Space Complexity

| Algorithm | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| BFS | O(V + E) | O(V) |
| DFS | O(V + E) | O(V) |
| Dijkstra (binary heap) | O((V + E) log V) | O(V) |
| Bellman-Ford | O(V × E) | O(V) |
| Union-Find (optimized) | O(α(n)) per operation | O(V) |
| Topological Sort | O(V + E) | O(V) |

Where V = vertices (nodes), E = edges, α = inverse Ackermann function (nearly constant)

## Common Mistakes to Avoid

1. **Forgetting visited set** - Leads to infinite loops in cyclic graphs
2. **Wrong graph construction** - Directed vs undirected edge handling
3. **Off-by-one in grid problems** - Boundary checking
4. **Not handling disconnected graphs** - May need to start traversal from multiple sources
5. **Confusing BFS and DFS use cases** - BFS for shortest path, DFS for complete exploration

## Tips for Mastering Graphs

1. **Always draw the graph** - Visualize before coding
2. **Start with representation** - Decide adjacency list vs matrix first
3. **Check for edge cases** - Empty graph, single node, disconnected components
4. **Use consistent template** - Master one BFS and one DFS template
5. **Practice grid-as-graph** - Many grid problems are graph problems in disguise

---

**Remember**: Graphs are about relationships. When you see a problem involving connections, dependencies, or networks, think graphs!

**Pro Tip**: In interviews, always clarify: Is the graph directed or undirected? Weighted or unweighted? Can there be cycles? This information determines your approach.
