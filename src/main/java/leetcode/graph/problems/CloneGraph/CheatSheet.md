# Clone Graph - CheatSheet

## Problem at a Glance

```
Input:  Reference to a node in a connected undirected graph
Output: Deep copy (clone) of the entire graph
Key:    Use HashMap to map original → clone (handles cycles!)
```

## Quick Solution Template (DFS)

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;
    return dfs(node, new HashMap<>());
}

private Node dfs(Node node, Map<Node, Node> visited) {
    // Already cloned? Return existing clone
    if (visited.containsKey(node)) {
        return visited.get(node);
    }

    // Create clone, add to map BEFORE recursing
    Node clone = new Node(node.val);
    visited.put(node, clone);

    // Clone all neighbors
    for (Node neighbor : node.neighbors) {
        clone.neighbors.add(dfs(neighbor, visited));
    }

    return clone;
}
```

## Alternative: BFS Template

```java
public Node cloneGraph(Node node) {
    if (node == null) return null;

    Map<Node, Node> visited = new HashMap<>();
    Queue<Node> queue = new LinkedList<>();

    visited.put(node, new Node(node.val));
    queue.offer(node);

    while (!queue.isEmpty()) {
        Node curr = queue.poll();

        for (Node neighbor : curr.neighbors) {
            if (!visited.containsKey(neighbor)) {
                visited.put(neighbor, new Node(neighbor.val));
                queue.offer(neighbor);
            }
            visited.get(curr).neighbors.add(visited.get(neighbor));
        }
    }

    return visited.get(node);
}
```

## Key Points to Remember

| Aspect | Details |
|--------|---------|
| **Pattern** | Graph Traversal + HashMap Cloning |
| **HashMap Purpose** | Maps original → clone (tracks visited + stores clones) |
| **Critical Order** | Add to map BEFORE recursing on neighbors |
| **Time** | O(N + E) where N=nodes, E=edges |
| **Space** | O(N) for HashMap |

## The Algorithm in 4 Steps

```
1. If node null → return null
2. If node in map → return its clone (handles cycles!)
3. Create clone, add to map
4. For each neighbor: clone it, add to clone's neighbors
```

## Node Class Definition

```java
class Node {
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
```

## Common Mistakes

| Mistake | Fix |
|---------|-----|
| Forgetting null check | `if (node == null) return null;` |
| Not checking map first | Check `visited.containsKey(node)` before creating |
| Adding to map after recursing | Add to map BEFORE processing neighbors |
| Returning original node | Return `clone`, not `node` |
| Copying neighbor list directly | Must clone each neighbor individually |

## Visual: Why HashMap Matters

```
Without HashMap:        With HashMap:
1 ←→ 2                 1 ←→ 2
↓                      ↓
Clone1 → Clone2        Clone1 ←→ Clone2
         ↓                ↑
         Clone1'          (same Clone1!)

WRONG: Multiple clones   CORRECT: One clone per node
```

## Interview Tips

1. **Start with**: "I'll use a HashMap to track original-to-clone mappings"
2. **Explain cycles**: "The HashMap handles cycles by returning existing clones"
3. **Edge case**: "Empty graph returns null"
4. **Complexity**: "O(N+E) time and O(N) space for the HashMap"

## Quick Debugging Checklist

- [ ] Null check at the start
- [ ] HashMap check before creating new clone
- [ ] Add to HashMap before recursing
- [ ] Return clone (not original)
- [ ] Clone neighbors recursively (not shallow copy)
