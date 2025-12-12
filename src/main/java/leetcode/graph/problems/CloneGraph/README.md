# 133. Clone Graph

## Problem Description

**Difficulty**: Medium
**Topics**: Hash Table, Depth-First Search, Breadth-First Search, Graph
**Link**: [LeetCode 133](https://leetcode.com/problems/clone-graph/)

Given a reference of a node in a **connected undirected graph**, return a **deep copy** (clone) of the graph.

Each node in the graph contains:
- A value (`int`)
- A list of its neighbors (`List<Node>`)

```java
class Node {
    public int val;
    public List<Node> neighbors;
}
```

## Examples

### Example 1: Square Graph
```
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]

Visual:
    1 ——— 2
    |     |
    |     |
    4 ——— 3

Node 1's neighbors: [2, 4]
Node 2's neighbors: [1, 3]
Node 3's neighbors: [2, 4]
Node 4's neighbors: [1, 3]
```

### Example 2: Single Node (No Neighbors)
```
Input: adjList = [[]]
Output: [[]]

Visual:
    (1)    ← Node with val=1, no neighbors

The graph has one node with value 1 and no neighbors.
```

### Example 3: Empty Graph
```
Input: adjList = []
Output: []

No nodes exist in the graph.
```

## Constraints

- Number of nodes: `[0, 100]`
- `1 <= Node.val <= 100`
- `Node.val` is unique for each node
- No repeated edges and no self-loops
- Graph is connected (all nodes reachable from given node)

## Why This Problem Is Tricky

This problem combines two challenges:

1. **Graph Traversal**: Need to visit every node in the graph
2. **Deep Cloning with Cycles**: Must handle circular references without infinite loops

The key insight: **You can't just create nodes as you go** because neighbors might reference nodes you haven't created yet, or nodes you've already created (cycles!).

## Pattern Recognition

This is a **Graph Traversal + HashMap Cloning** problem:
- Use DFS or BFS to traverse the graph
- Use a HashMap to map `original node → cloned node`
- The HashMap serves dual purpose: tracks visited nodes AND stores clones

## Solution Approaches

### Approach 1: DFS with HashMap (Recommended)

**Key Idea**: As we traverse, maintain a map from original nodes to their clones. When we encounter a node:
- If already cloned → return the clone (handles cycles!)
- If not cloned → create clone, recursively clone neighbors

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    return dfs(node, new HashMap<>());
}

private Node dfs(Node node, Map<Node, Node> visited) {
    // If already cloned, return the clone (handles cycles)
    if (visited.containsKey(node)) {
        return visited.get(node);
    }

    // Create clone (don't add neighbors yet)
    Node clone = new Node(node.val);
    visited.put(node, clone);

    // Recursively clone all neighbors
    for (Node neighbor : node.neighbors) {
        clone.neighbors.add(dfs(neighbor, visited));
    }

    return clone;
}
```

**Complexity**:
- Time: O(N + E) where N = nodes, E = edges
- Space: O(N) for HashMap + O(N) for recursion stack

### Approach 2: BFS with HashMap

**Key Idea**: Same concept but using a queue for iterative traversal.

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;

    Map<Node, Node> visited = new HashMap<>();
    Queue<Node> queue = new LinkedList<>();

    // Clone the starting node
    visited.put(node, new Node(node.val));
    queue.offer(node);

    while (!queue.isEmpty()) {
        Node curr = queue.poll();

        // Process all neighbors
        for (Node neighbor : curr.neighbors) {
            // If neighbor not cloned yet, clone it
            if (!visited.containsKey(neighbor)) {
                visited.put(neighbor, new Node(neighbor.val));
                queue.offer(neighbor);
            }
            // Add cloned neighbor to current clone's neighbor list
            visited.get(curr).neighbors.add(visited.get(neighbor));
        }
    }

    return visited.get(node);
}
```

**Complexity**:
- Time: O(N + E)
- Space: O(N) for HashMap + O(N) for queue

## Which Approach to Choose?

| Approach | Pros | Cons | Best For |
|----------|------|------|----------|
| **DFS** | Cleaner code, intuitive recursion | Stack overflow on deep graphs | Most interviews, cleaner explanation |
| **BFS** | No stack overflow risk, level-order | More verbose | Very deep graphs |

**Recommendation**: Start with DFS - it's more intuitive and interviewers expect it.

## The Critical Insight: Why HashMap?

```
Without HashMap (WRONG):

Original:  1 ←→ 2
           ↑
           Creates: Clone1 with neighbor Clone2
           Creates: Clone2 with neighbor Clone1'  ← DIFFERENT Clone1!

Result: Infinite loop or disconnected clones!

With HashMap (CORRECT):

Original:  1 ←→ 2
           ↑
           Creates: Clone1, stores in map
           Creates: Clone2, stores in map
           Clone1.neighbors gets Clone2 (from map)
           Clone2.neighbors gets Clone1 (from map - SAME Clone1!)

Result: Properly connected clone graph!
```

## Common Mistakes

1. **Forgetting null check**: Empty graph returns `null`
2. **Creating duplicate clones**: Must check HashMap before creating new node
3. **Shallow copy of neighbors**: Must clone neighbor references, not copy them
4. **Not handling cycles**: HashMap is essential for cycle detection

## Key Insights

1. **HashMap = visited set + clone storage**: Dual purpose!
2. **Clone node BEFORE recursing**: Prevents infinite loops
3. **Add to HashMap BEFORE processing neighbors**: Critical for cycle handling
4. **Return clone from HashMap**: Not the original node

## Related Problems

- [138. Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/) - Similar cloning pattern
- [200. Number of Islands](https://leetcode.com/problems/number-of-islands/) - Basic graph traversal
- [207. Course Schedule](https://leetcode.com/problems/course-schedule/) - Graph cycle detection
- [261. Graph Valid Tree](https://leetcode.com/problems/graph-valid-tree/) - Graph structure validation

## Interview Tips

1. **Clarify**: "Is the graph guaranteed to be connected?" (Yes, per constraints)
2. **Explain the challenge**: "The tricky part is handling cycles without infinite loops"
3. **State your strategy**: "I'll use a HashMap to track original-to-clone mappings"
4. **Walk through an example**: Draw the square graph and show the cloning process
