# Clone Graph - Study Guide

## Understanding the Problem

### What Are We Really Being Asked?

We need to create a **completely independent copy** of a graph. This means:
- Every node in the original graph gets a NEW node object in the clone
- The connections (edges) between cloned nodes mirror the original
- Modifying the clone should NOT affect the original (and vice versa)

### The Deep Copy Challenge

```
SHALLOW COPY (Wrong):
Original Node 1 ──────────→ Original Node 2
      ↓                           ↓
Clone "Node 1" ───────────→ Original Node 2  ← Still pointing to original!

DEEP COPY (Correct):
Original Node 1 ──────────→ Original Node 2

Clone Node 1 ─────────────→ Clone Node 2     ← All new objects!
```

### Why Graphs Make This Hard

Unlike a simple linked list, graphs can have **cycles**:

```
    1 ——— 2
    |     |
    4 ——— 3

If we naively follow edges:
1 → 2 → 3 → 4 → 1 → 2 → 3 → 4 → 1 → ... (infinite loop!)
```

We need to track which nodes we've already cloned to:
1. Avoid infinite loops
2. Ensure we reuse the same clone when we see a node again

## The HashMap Solution

### Why HashMap is Perfect

The HashMap serves **two purposes**:
1. **Visited tracking**: Have we seen this node before?
2. **Clone storage**: If yes, what's its clone?

```java
Map<Node, Node> map;  // originalNode → clonedNode

// Check if visited:
if (map.containsKey(originalNode)) { ... }

// Get the clone:
Node clone = map.get(originalNode);

// Store new clone:
map.put(originalNode, newClone);
```

### The Algorithm Flow

```
For each node we visit:
┌─────────────────────────────────────────────────┐
│ 1. Already in HashMap?                          │
│    YES → Return the existing clone              │
│    NO  → Continue to step 2                     │
│                                                 │
│ 2. Create a new clone node (just val, no neighbors yet) │
│                                                 │
│ 3. Add to HashMap: original → clone             │
│                                                 │
│ 4. For each neighbor of original:               │
│    - Recursively clone the neighbor             │
│    - Add the cloned neighbor to clone's list    │
│                                                 │
│ 5. Return the clone                             │
└─────────────────────────────────────────────────┘
```

## Detailed Execution Trace (DFS)

### Example Graph
```
    1 ——— 2
    |     |
    4 ——— 3

adjList = [[2,4], [1,3], [2,4], [1,3]]
```

### Step-by-Step Execution

```
Initial call: cloneGraph(node1)
HashMap: {}

┌─────────────────────────────────────────────────────────────┐
│ dfs(node1):                                                 │
│   - node1 not in map                                        │
│   - Create clone1 (val=1, neighbors=[])                     │
│   - map = {node1 → clone1}                                  │
│   - Process neighbors of node1: [node2, node4]              │
│                                                             │
│   ┌─────────────────────────────────────────────────────┐   │
│   │ dfs(node2):                                         │   │
│   │   - node2 not in map                                │   │
│   │   - Create clone2 (val=2, neighbors=[])             │   │
│   │   - map = {node1→clone1, node2→clone2}              │   │
│   │   - Process neighbors of node2: [node1, node3]      │   │
│   │                                                     │   │
│   │   ┌─────────────────────────────────────────────┐   │   │
│   │   │ dfs(node1):                                 │   │   │
│   │   │   - node1 IS in map! Return clone1         │   │   │
│   │   └─────────────────────────────────────────────┘   │   │
│   │   → clone2.neighbors.add(clone1)                    │   │
│   │                                                     │   │
│   │   ┌─────────────────────────────────────────────┐   │   │
│   │   │ dfs(node3):                                 │   │   │
│   │   │   - node3 not in map                        │   │   │
│   │   │   - Create clone3 (val=3, neighbors=[])     │   │   │
│   │   │   - map = {..., node3→clone3}               │   │   │
│   │   │   - Process neighbors: [node2, node4]       │   │   │
│   │   │                                             │   │   │
│   │   │   dfs(node2): in map! Return clone2         │   │   │
│   │   │   → clone3.neighbors.add(clone2)            │   │   │
│   │   │                                             │   │   │
│   │   │   ┌─────────────────────────────────────┐   │   │   │
│   │   │   │ dfs(node4):                         │   │   │   │
│   │   │   │   - node4 not in map                │   │   │   │
│   │   │   │   - Create clone4 (val=4)           │   │   │   │
│   │   │   │   - map = {..., node4→clone4}       │   │   │   │
│   │   │   │   - Process neighbors: [node1, node3] │   │   │   │
│   │   │   │                                     │   │   │   │
│   │   │   │   dfs(node1): in map! Return clone1 │   │   │   │
│   │   │   │   → clone4.neighbors.add(clone1)    │   │   │   │
│   │   │   │                                     │   │   │   │
│   │   │   │   dfs(node3): in map! Return clone3 │   │   │   │
│   │   │   │   → clone4.neighbors.add(clone3)    │   │   │   │
│   │   │   │                                     │   │   │   │
│   │   │   │   Return clone4                     │   │   │   │
│   │   │   └─────────────────────────────────────┘   │   │   │
│   │   │   → clone3.neighbors.add(clone4)            │   │   │
│   │   │                                             │   │   │
│   │   │   Return clone3                             │   │   │
│   │   └─────────────────────────────────────────────┘   │   │
│   │   → clone2.neighbors.add(clone3)                    │   │
│   │                                                     │   │
│   │   Return clone2                                     │   │
│   └─────────────────────────────────────────────────────┘   │
│   → clone1.neighbors.add(clone2)                            │
│                                                             │
│   ┌─────────────────────────────────────────────────────┐   │
│   │ dfs(node4):                                         │   │
│   │   - node4 IS in map! Return clone4                  │   │
│   └─────────────────────────────────────────────────────┘   │
│   → clone1.neighbors.add(clone4)                            │
│                                                             │
│   Return clone1                                             │
└─────────────────────────────────────────────────────────────┘

Final HashMap:
{node1→clone1, node2→clone2, node3→clone3, node4→clone4}

Final Clone Graph:
clone1.neighbors = [clone2, clone4]
clone2.neighbors = [clone1, clone3]
clone3.neighbors = [clone2, clone4]
clone4.neighbors = [clone1, clone3]

✓ Identical structure to original!
✓ All different objects from original!
```

## Understanding the Code

### DFS Solution Breakdown

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;           // Edge case: empty graph
    return dfs(node, new HashMap<>());       // Start DFS with empty map
}

private Node dfs(Node node, Map<Node, Node> visited) {
    // KEY INSIGHT: This handles cycles!
    // If we've already cloned this node, return the clone
    if (visited.containsKey(node)) {
        return visited.get(node);
    }

    // Create clone with same value, empty neighbor list
    Node clone = new Node(node.val);

    // CRITICAL: Add to map BEFORE recursing on neighbors
    // This ensures if we encounter this node again, we return the same clone
    visited.put(node, clone);

    // Clone each neighbor and add to clone's neighbor list
    for (Node neighbor : node.neighbors) {
        clone.neighbors.add(dfs(neighbor, visited));
    }

    return clone;
}
```

### Why "Add to Map BEFORE Recursing" is Critical

```java
// WRONG ORDER - causes infinite loop or wrong clones:
for (Node neighbor : node.neighbors) {
    clone.neighbors.add(dfs(neighbor, visited));
}
visited.put(node, clone);  // Too late! Neighbors might have looked for this

// CORRECT ORDER:
visited.put(node, clone);  // Mark as visited FIRST
for (Node neighbor : node.neighbors) {
    clone.neighbors.add(dfs(neighbor, visited));  // Now neighbors can find our clone
}
```

## BFS Solution Explained

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;

    Map<Node, Node> visited = new HashMap<>();
    Queue<Node> queue = new LinkedList<>();

    // Step 1: Clone the starting node and enqueue it
    visited.put(node, new Node(node.val));
    queue.offer(node);

    // Step 2: BFS - process nodes level by level
    while (!queue.isEmpty()) {
        Node curr = queue.poll();

        // Step 3: Process each neighbor
        for (Node neighbor : curr.neighbors) {
            // If neighbor hasn't been cloned, clone it and enqueue
            if (!visited.containsKey(neighbor)) {
                visited.put(neighbor, new Node(neighbor.val));
                queue.offer(neighbor);
            }

            // Step 4: Connect current clone to neighbor's clone
            // Note: visited.get(curr) is the clone of curr
            // Note: visited.get(neighbor) is the clone of neighbor
            visited.get(curr).neighbors.add(visited.get(neighbor));
        }
    }

    return visited.get(node);  // Return clone of starting node
}
```

### BFS Execution Trace

```
Graph:  1 ——— 2
        |     |
        4 ——— 3

Initial:
  visited = {node1 → clone1}
  queue = [node1]

Iteration 1: Process node1
  - neighbors: [node2, node4]
  - node2 not visited: create clone2, add to queue
  - node4 not visited: create clone4, add to queue
  - clone1.neighbors = [clone2, clone4]
  visited = {node1→clone1, node2→clone2, node4→clone4}
  queue = [node2, node4]

Iteration 2: Process node2
  - neighbors: [node1, node3]
  - node1 already visited: use existing clone1
  - node3 not visited: create clone3, add to queue
  - clone2.neighbors = [clone1, clone3]
  visited = {..., node3→clone3}
  queue = [node4, node3]

Iteration 3: Process node4
  - neighbors: [node1, node3]
  - node1 already visited: use existing clone1
  - node3 already visited: use existing clone3
  - clone4.neighbors = [clone1, clone3]
  queue = [node3]

Iteration 4: Process node3
  - neighbors: [node2, node4]
  - node2 already visited: use existing clone2
  - node4 already visited: use existing clone4
  - clone3.neighbors = [clone2, clone4]
  queue = []

Done! Return clone1
```

## Common Mistakes and How to Avoid Them

### Mistake 1: Not Handling Null Input

```java
// WRONG - crashes on null input
public Node cloneGraph(Node node) {
    return dfs(node, new HashMap<>());
}

// CORRECT
public Node cloneGraph(Node node) {
    if (node == null) return null;  // Handle empty graph
    return dfs(node, new HashMap<>());
}
```

### Mistake 2: Creating Multiple Clones of Same Node

```java
// WRONG - doesn't check if already cloned
private Node dfs(Node node, Map<Node, Node> visited) {
    Node clone = new Node(node.val);  // Always creates new!
    // ...
}

// CORRECT - check first!
private Node dfs(Node node, Map<Node, Node> visited) {
    if (visited.containsKey(node)) {
        return visited.get(node);  // Return existing clone
    }
    Node clone = new Node(node.val);
    // ...
}
```

### Mistake 3: Returning Original Instead of Clone

```java
// WRONG - returns original node, not clone
public Node cloneGraph(Node node) {
    if (node == null) return null;
    dfs(node, new HashMap<>());
    return node;  // This is the ORIGINAL, not the clone!
}

// CORRECT
public Node cloneGraph(Node node) {
    if (node == null) return null;
    return dfs(node, new HashMap<>());  // Return the clone
}
```

### Mistake 4: Shallow Copy of Neighbors List

```java
// WRONG - copies references to original neighbors
Node clone = new Node(node.val);
clone.neighbors = node.neighbors;  // Points to ORIGINAL neighbors!

// CORRECT - create new list, add cloned neighbors
Node clone = new Node(node.val);  // neighbors defaults to empty ArrayList
for (Node neighbor : node.neighbors) {
    clone.neighbors.add(dfs(neighbor, visited));  // Add CLONES
}
```

## Mental Model: Think of it as "Visit and Clone"

```
When you visit a node for the FIRST time:
1. 📝 Create its clone
2. 📌 Pin it to the map (original → clone)
3. 🔄 Visit all neighbors (which creates their clones)
4. 🔗 Link clone to cloned neighbors

When you visit a node AGAIN:
1. 📌 Find it in the map
2. ✅ Return the existing clone
3. 🛑 Don't recurse (prevents infinite loop)
```

## Practice Questions

1. **Why can't we just copy the Node constructor?**
   - Answer: We'd be copying references to original neighbors, not creating new nodes

2. **What happens if we don't use a HashMap?**
   - Answer: Infinite loop on cycles, or duplicate clones of the same node

3. **DFS vs BFS - when would you prefer one over the other?**
   - Answer: DFS is simpler to code; BFS avoids stack overflow on deep graphs

4. **Could we use the node values as keys instead of the node objects?**
   - Answer: Yes, since values are unique. But using objects is more general and works even if values weren't unique.

## Key Takeaways

1. **HashMap is essential**: Maps original nodes to their clones
2. **Clone BEFORE recursing**: Prevents infinite loops on cycles
3. **Return clones, not originals**: The whole point is creating independent copies
4. **Edge case**: Empty graph (null input) should return null
5. **This pattern applies to many cloning problems**: Lists with random pointers, trees with parent pointers, etc.
