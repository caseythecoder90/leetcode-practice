# Clone Graph - Flashcards

## Problem Understanding

**Q: What is the Clone Graph problem asking?**
A: Given a reference to a node in a connected undirected graph, create and return a **deep copy** (clone) of the entire graph. Each node has a value and a list of neighbors.

**Q: What makes this problem tricky?**
A: Two challenges:
1. **Graph traversal**: Must visit all nodes in a connected graph
2. **Handling cycles**: Graphs can have cycles, which would cause infinite loops if not handled properly

**Q: What is a "deep copy" vs a "shallow copy"?**
A:
- **Shallow copy**: New object but references point to same nested objects
- **Deep copy**: Completely new objects all the way down - modifying the copy doesn't affect the original

```
Shallow: clone.neighbors → [original_node2, original_node3]  // BAD
Deep:    clone.neighbors → [clone_node2, clone_node3]        // GOOD
```

## Algorithm Approach

**Q: What data structure is key to solving Clone Graph?**
A: A **HashMap** that maps `original node → cloned node`. This serves dual purposes:
1. **Visited tracking**: Prevents infinite loops on cycles
2. **Clone storage**: Retrieves existing clones when we revisit a node

**Q: Why can't we just use a visited Set like in other graph problems?**
A: A Set only tells us IF we visited a node. We need the HashMap to also tell us WHICH clone we created, so we can reuse it when the node appears as a neighbor again.

**Q: What's the high-level algorithm for DFS cloning?**
A:
```
1. Base case: if node is null, return null
2. If node already in HashMap, return its clone (handles cycles!)
3. Create new clone node with same value
4. Add original→clone to HashMap BEFORE recursing
5. For each neighbor, recursively clone and add to clone's neighbors
6. Return the clone
```

## Code Understanding

**Q: What does the DFS solution look like?**
A:
```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    return dfs(node, new HashMap<>());
}

private Node dfs(Node node, Map<Node, Node> visited) {
    if (visited.containsKey(node)) {
        return visited.get(node);  // Return existing clone
    }

    Node clone = new Node(node.val);
    visited.put(node, clone);  // Add BEFORE recursing!

    for (Node neighbor : node.neighbors) {
        clone.neighbors.add(dfs(neighbor, visited));
    }

    return clone;
}
```

**Q: Why must we add to the HashMap BEFORE recursing on neighbors?**
A: Because neighbors might eventually lead back to this node (cycle). If we haven't added it to the map yet, we'd create a duplicate clone instead of reusing the one we're currently building.

```
Graph: 1 ↔ 2

Without early map entry:
dfs(1) creates clone1
  dfs(2) creates clone2
    dfs(1) creates clone1' ← WRONG! Duplicate!

With early map entry:
dfs(1) creates clone1, adds to map
  dfs(2) creates clone2, adds to map
    dfs(1) finds clone1 in map ← CORRECT! Reuse!
```

**Q: How does the BFS solution differ?**
A: Same concept but uses a queue instead of recursion:
- Clone starting node, add to map and queue
- While queue not empty: process each neighbor
- If neighbor not cloned, clone it and add to queue
- Connect current clone to neighbor's clone

## Complexity Analysis

**Q: What is the time complexity?**
A: **O(N + E)** where N = number of nodes, E = number of edges.
- We visit each node exactly once
- We process each edge twice (once from each endpoint in undirected graph)

**Q: What is the space complexity?**
A: **O(N)**
- HashMap stores N entries (one per node)
- DFS: recursion stack can go up to N deep
- BFS: queue can hold up to N nodes

## Common Mistakes

**Q: What happens if you forget the null check?**
A: NullPointerException when the input is an empty graph (null node).

**Q: What happens if you don't check the HashMap before creating a clone?**
A: You'll create multiple clones of the same node, breaking the graph structure. Cycles will cause infinite loops or stack overflow.

**Q: What's wrong with this code?**
```java
Node clone = new Node(node.val);
clone.neighbors = node.neighbors;  // What's wrong?
```
A: This is a **shallow copy** of the neighbors list! The clone would point to the ORIGINAL neighbors, not cloned neighbors. Must iterate and clone each neighbor individually.

**Q: What's wrong with this return statement?**
```java
dfs(node, new HashMap<>());
return node;  // What's wrong?
```
A: Returns the **original** node, not the clone! Should return the result of dfs(), which is the cloned node.

## Edge Cases

**Q: What are the edge cases to handle?**
A:
1. **Null input**: Return null (empty graph)
2. **Single node with no neighbors**: Clone just that node
3. **Two nodes connected to each other**: Basic cycle handling
4. **Fully connected graph**: Every node connected to every other

**Q: What does the test format `[[2,4],[1,3],[2,4],[1,3]]` mean?**
A: It's an adjacency list:
- Index 0 (node 1): neighbors are nodes 2 and 4
- Index 1 (node 2): neighbors are nodes 1 and 3
- Index 2 (node 3): neighbors are nodes 2 and 4
- Index 3 (node 4): neighbors are nodes 1 and 3

## Interview Tips

**Q: How should you explain your approach in an interview?**
A:
1. "This is a graph traversal problem with a twist - we need to clone as we traverse"
2. "The challenge is handling cycles without infinite loops"
3. "I'll use a HashMap to map original nodes to their clones"
4. "When I see a node I've already cloned, I return the existing clone"
5. "This gives O(N+E) time and O(N) space"

**Q: What clarifying questions should you ask?**
A:
1. "Is the graph guaranteed to be connected?" (Yes per constraints)
2. "Can there be cycles?" (Yes, it's a general graph)
3. "Are node values unique?" (Yes per constraints)
4. "Can the graph be empty?" (Yes, handle null input)

## Related Patterns

**Q: What other problems use similar techniques?**
A:
- **138. Copy List with Random Pointer**: Same HashMap cloning pattern
- **200. Number of Islands**: Graph traversal with visited tracking
- **207. Course Schedule**: Graph cycle detection
- **Tree serialization/deserialization**: Similar reconstruction logic

**Q: How is this different from tree cloning?**
A: Trees don't have cycles, so you don't need the HashMap for visited tracking. You can simply recurse:
```java
// Tree cloning (no HashMap needed)
Node cloneTree(Node node) {
    if (node == null) return null;
    Node clone = new Node(node.val);
    clone.left = cloneTree(node.left);
    clone.right = cloneTree(node.right);
    return clone;
}
```
Graphs need the HashMap because any node could reference any other node, including ancestors.
