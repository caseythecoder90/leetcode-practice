# Binary Tree Right Side View - Complete Study Guide

## Pattern Overview

**Problem Type:** Binary Tree Level-Based Selection
**Core Pattern:** BFS Level Order Traversal OR DFS with Level Tracking

**Key Insight:** The problem requires identifying and collecting the rightmost visible node at each depth level when viewing the tree from the right side.

## Understanding the Problem

### Visual Concept

Imagine standing on the right side of a binary tree and looking at it. What nodes can you see?

```
Tree:       1              Right Side View
           / \                   ↓
          2   3                  1  (root - always visible)
         /     \                 3  (rightmost at level 1)
        4       5                5  (rightmost at level 2)
         \                       6  (only node at level 3)
          6

Result: [1, 3, 5, 6]
```

**Key Observations:**
- You see exactly ONE node per level
- It's the rightmost node at each level
- Even if a node is on the left subtree, it's visible if it's the only/rightmost at that level

### Tricky Case: Left Node Visible

```
Tree:       1              Right Side View
           / \                   ↓
          2   3                  1  (root)
         /                       3  (rightmost at level 1)
        4                        4  (only node at level 2 - from LEFT subtree!)
         \                       5  (only node at level 3)
          5

Result: [1, 3, 4, 5]
```

**Important:** Nodes from the left subtree CAN be visible if they're the rightmost at their level!

## Solution 1: BFS Step-by-Step Walkthrough

### Algorithm Overview

**Core Idea:** Process tree level by level, capturing only the last node at each level.

**Why BFS Works:**
1. BFS naturally processes nodes level by level
2. We can capture level size to know level boundaries
3. The last node processed in each level is the rightmost
4. We simply track the index and only add the last node to result

### Detailed Example

**Input Tree:**
```
        1
       / \
      2   3
       \   \
        5   4
```

**Expected Output:** [1, 3, 4]

### Execution Trace

**Initial State:**
```
queue: [1]
result: []
```

**Iteration 1 (Level 0):**
```
levelSize = 1
i = 0:
  node = 1
  i == levelSize - 1? YES (0 == 0) → Add 1 to result
  Add children: queue.offer(2), queue.offer(3)

queue after: [2, 3]
result: [1]
```

**Iteration 2 (Level 1):**
```
levelSize = 2
i = 0:
  node = 2
  i == levelSize - 1? NO (0 != 1) → Don't add
  Add child: queue.offer(5)

i = 1:
  node = 3
  i == levelSize - 1? YES (1 == 1) → Add 3 to result
  Add child: queue.offer(4)

queue after: [5, 4]
result: [1, 3]
```

**Iteration 3 (Level 2):**
```
levelSize = 2
i = 0:
  node = 5
  i == levelSize - 1? NO (0 != 1) → Don't add
  No children

i = 1:
  node = 4
  i == levelSize - 1? YES (1 == 1) → Add 4 to result
  No children

queue after: []
result: [1, 3, 4]
```

**Final Result:** [1, 3, 4] ✓

### BFS Implementation Details

```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size(); // CRITICAL: capture before loop

        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();

            // Only add the last node in level
            if (i == levelSize - 1) {
                result.add(node.val);
            }

            // Add children for next level
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    return result;
}
```

**Key Points:**
1. **Capture `levelSize` before loop** - Queue size changes as we add children
2. **Check `i == levelSize - 1`** - Identifies the last (rightmost) node
3. **Add children left-to-right** - Doesn't matter since we only track last

## Solution 2: DFS with Level Tracking

### Understanding DFS Approach

**Different Perspective:**
- BFS: "Process level by level, take last node"
- DFS: "Visit right-first, take first seen node per level"

**Why Right-First DFS Works:**
```
Tree:       1              DFS Order (right-first):
           / \             1 (level 0) → Add to result[0]
          2   3            3 (level 1) → Add to result[1]
         /     \           5 (level 2) → Add to result[2]
        4       5          2 (level 1) → Already have result[1], skip
                           4 (level 2) → Already have result[2], skip

Result: [1, 3, 5]
```

By going right first, we ensure the rightmost node is visited first at each level!

### Algorithm Flow

1. **Initialize:** Empty result list
2. **DFS Traversal:** Pass current level as parameter
3. **First Visit Check:** If `level == result.size()`, this is first time at this level
4. **Add to Result:** Only add if first visit (which is rightmost due to right-first order)
5. **Recurse Right First:** Ensures right nodes visited before left

### Detailed Example

**Same Tree:**
```
        1
       / \
      2   3
       \   \
        5   4
```

**DFS Traversal (right-first):**

```
Visit 1 (level 0):
  level (0) == result.size() (0)? YES → Add 1
  result = [1]
  Recurse right to 3, then left to 2

Visit 3 (level 1):
  level (1) == result.size() (1)? YES → Add 3
  result = [1, 3]
  Recurse right to 4, then left (null)

Visit 4 (level 2):
  level (2) == result.size() (2)? YES → Add 4
  result = [1, 3, 4]
  Recurse right (null), then left (null)

Visit 2 (level 1):
  level (1) == result.size() (3)? NO → Don't add
  Recurse right to 5, then left (null)

Visit 5 (level 2):
  level (2) == result.size() (3)? NO → Don't add
  (Already have rightmost node 4 at level 2)
```

**Final Result:** [1, 3, 4] ✓

### DFS Implementation

```java
public List<Integer> rightSideViewDFS(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    dfsHelper(root, 0, result);
    return result;
}

private void dfsHelper(TreeNode node, int level, List<Integer> result) {
    if (node == null) return;

    // First time visiting this level = rightmost node (due to right-first)
    if (level == result.size()) {
        result.add(node.val);
    }

    // CRITICAL: Right before left!
    dfsHelper(node.right, level + 1, result);
    dfsHelper(node.left, level + 1, result);
}
```

**Key Points:**
1. **Level tracking** - Pass level as parameter, increment for children
2. **First visit check** - `level == result.size()` means first visit
3. **Right-first recursion** - Ensures rightmost node visited first
4. **No index needed** - Result size naturally tracks which level we're on

## BFS vs DFS: Deep Comparison

### Conceptual Difference

| Aspect | BFS | DFS |
|--------|-----|-----|
| **Mental Model** | "Process all nodes in level, pick last" | "Visit right-first, pick first per level" |
| **Data Structure** | Queue (iterative) | Recursion stack (recursive) |
| **Traversal Order** | 1, 2, 3, 5, 4 | 1, 3, 4, 2, 5 |
| **Selection Logic** | Index-based (last in level) | First-visit-based (right-first) |

### Implementation Comparison

**BFS Characteristics:**
- More intuitive for level-based problems
- Uses explicit queue
- Single pass, computes result on-the-fly
- Easier to visualize and debug

**DFS Characteristics:**
- Clever use of first-visit pattern
- Uses recursion stack
- Requires understanding of traversal order
- More elegant/concise code

### Performance Analysis

**Time Complexity (Both): O(n)**
- Visit each node exactly once
- BFS: Process node + queue operations
- DFS: Process node + recursive calls
- Both are O(1) per node

**Space Complexity:**

**BFS: O(w)** where w = maximum width
- Queue holds at most one level
- Worst case: complete tree's last level
- Example: Tree with height h → width = 2^(h-1)

**DFS: O(h)** where h = height
- Recursion stack depth = height
- Worst case: skewed tree → h = n
- Best case: balanced tree → h = log n

### When to Choose Which?

**Choose BFS when:**
- You're more comfortable with iterative solutions
- The tree is very deep (avoid stack overflow)
- You want clearer level-by-level processing
- You're dealing with wide, shallow trees

**Choose DFS when:**
- You prefer recursive solutions
- The tree is very wide (save queue space)
- You want more concise code
- You're dealing with deep, narrow trees

## Edge Cases & Testing Strategy

### 1. Empty Tree
```
Input: root = null
Output: []

Handling: Early return in both approaches
```

### 2. Single Node
```
Input: root = [1]
Output: [1]

Tree: 1

Handling: Works naturally - level 0 has one node
```

### 3. Only Right Children
```
Input: root = [1, null, 2, null, 3]
Output: [1, 2, 3]

Tree:  1
        \
         2
          \
           3

Handling: Each level has only one node (rightmost by default)
```

### 4. Only Left Children
```
Input: root = [1, 2, null, 3]
Output: [1, 2, 3]

Tree:    1
        /
       2
      /
     3

Handling: Each left node is STILL the rightmost at its level
```

### 5. Left Nodes Visible
```
Input: root = [1, 2, 3, 4, null, null, null, 5]
Output: [1, 3, 4, 5]

Tree:     1
         / \
        2   3
       /
      4
       \
        5

Explanation:
- Level 0: 1 (only node)
- Level 1: 3 (rightmost between 2 and 3)
- Level 2: 4 (only node - from left subtree!)
- Level 3: 5 (only node - from left subtree!)
```

This case demonstrates that "right side view" doesn't mean "only right subtree"!

### 6. Complex Structure
```
Input: root = [1, 2, 3, null, 5, null, 4]
Output: [1, 3, 4]

Tree:     1
         / \
        2   3
         \   \
          5   4

Explanation:
- Level 0: 1
- Level 1: 3 (rightmost between 2 and 3)
- Level 2: 4 (rightmost between 5 and 4)
```

## Common Mistakes & Debugging

### ❌ Mistake 1: Using queue.size() in loop condition

```java
// WRONG!
while (!queue.isEmpty()) {
    for (int i = 0; i < queue.size(); i++) { // BUG: queue.size() changes!
        TreeNode node = queue.poll();
        // ...
    }
}
```

**Problem:** Queue size changes as we add children during iteration!

**Fix:**
```java
int levelSize = queue.size(); // Capture before loop
for (int i = 0; i < levelSize; i++) {
    // Now we process exactly this level's nodes
}
```

### ❌ Mistake 2: Wrong index check

```java
// WRONG!
if (i == 0) { // This gets the FIRST node, not last!
    result.add(node.val);
}
```

**Fix:**
```java
if (i == levelSize - 1) { // Last node in level
    result.add(node.val);
}
```

### ❌ Mistake 3: DFS - Left before Right

```java
// WRONG for right side view!
dfsHelper(node.left, level + 1, result);
dfsHelper(node.right, level + 1, result);
```

**Problem:** This gives LEFT side view!

**Fix:**
```java
// Right first!
dfsHelper(node.right, level + 1, result);
dfsHelper(node.left, level + 1, result);
```

### ❌ Mistake 4: DFS - Wrong level check

```java
// WRONG!
if (level < result.size()) { // This would NEVER add anything after first level!
    result.add(node.val);
}
```

**Fix:**
```java
if (level == result.size()) { // First time at this level
    result.add(node.val);
}
```

## Debugging Tips

### 1. Visualize Level Processing

For BFS, print queue state:
```java
System.out.println("Level " + level + " size: " + levelSize);
System.out.println("Queue before: " + queue);
// ... process level
System.out.println("Queue after: " + queue);
```

### 2. Trace DFS Calls

Add debug output:
```java
private void dfsHelper(TreeNode node, int level, List<Integer> result) {
    if (node == null) return;

    System.out.println("Visiting " + node.val + " at level " + level);
    System.out.println("Result size: " + result.size());

    if (level == result.size()) {
        System.out.println("Adding " + node.val);
        result.add(node.val);
    }

    // ... rest of code
}
```

### 3. Draw the Tree

Always draw the tree structure to visualize:
- Number each level
- Identify which nodes should appear in result
- Verify your traversal order

## Related Problems & Variations

### Similar Level-Selection Problems

1. **102. Binary Tree Level Order Traversal**
   - Return all nodes per level, not just rightmost
   - Direct application of BFS pattern

2. **637. Average of Levels in Binary Tree**
   - Compute average per level instead of selecting rightmost
   - Same level-processing pattern

3. **515. Find Largest Value in Each Tree Row**
   - Find max instead of rightmost per level
   - Track max during level processing

4. **107. Binary Tree Level Order Traversal II**
   - Same as 102 but bottom-up
   - Reverse result or build backwards

### Left Side View Variation

**Problem:** Return nodes visible from the left side

**Solution 1 (BFS):** Same code, but check `i == 0` instead of `i == levelSize - 1`

**Solution 2 (DFS):** Same code, but recurse LEFT first instead of right:
```java
dfsHelper(node.left, level + 1, result); // Left first!
dfsHelper(node.right, level + 1, result);
```

### Boundary Traversal

**Problem:** Return boundary nodes (left edge + leaves + right edge)

**Approach:** Combine left side view + leaf collection + right side view (reversed)

## Interview Strategy

### Recognition (30 seconds)

**Keywords:** "right side", "view from", "visible from", "level"

**Pattern:** Level-based selection → BFS or DFS with level tracking

### Planning (1-2 minutes)

1. **Clarify:** "Return rightmost node at each level?"
2. **Choose approach:** BFS (more intuitive) or DFS (more elegant)
3. **Edge cases:** null root, single node, left nodes visible

### Implementation (5-8 minutes)

**BFS Path:**
1. Queue initialization
2. Level-by-level processing
3. Index check for last node
4. Return result

**DFS Path:**
1. Recursive helper with level
2. First-visit check
3. Right-first recursion
4. Return result

### Verification (2 minutes)

**Test Cases:**
- Given example
- Single node
- Only left children (to verify left nodes can be visible)
- Empty tree

### Follow-up Discussion (1-2 minutes)

**Mention:**
- Both approaches and trade-offs
- Space complexity: O(w) vs O(h)
- Left side view variation
- Can extend to boundary traversal

## Time & Space Complexity Summary

### BFS Approach

**Time: O(n)**
- Visit each node once
- Queue operations: O(1) amortized

**Space: O(w)**
- w = maximum width of tree
- Queue holds one level at a time
- Result: O(h) where h = height

**Total space dominated by queue: O(w)**

### DFS Approach

**Time: O(n)**
- Visit each node once
- Recursive calls: O(1) per node

**Space: O(h)**
- h = height of tree
- Recursion stack: O(h)
- Result: O(h)

**Total space: O(h)**

### Comparison

| Tree Type | Height (h) | Width (w) | Better Approach |
|-----------|-----------|-----------|-----------------|
| Balanced | log n | n/2 | DFS (O(log n) vs O(n)) |
| Skewed | n | 1 | BFS (O(1) vs O(n)) |
| Complete | log n | 2^(log n) | DFS (O(log n) vs O(n)) |

## Key Takeaways

1. **"Right side view" = rightmost node per level** - Not just right subtree!
2. **BFS: Last node in level** - Use index to identify
3. **DFS: First visited node per level (right-first)** - Clever traversal order
4. **Both O(n) time** - Different space trade-offs
5. **Level-based pattern** - Template applies to many similar problems
6. **Left nodes can be visible** - Don't assume only right subtree

## Practice Progression

1. **Master BFS first** - More intuitive for level problems
2. **Then try DFS** - Understand the right-first trick
3. **Solve left side view** - Apply same pattern
4. **Try related problems** - 102, 637, 515
5. **Time yourself** - Aim for 8-10 minutes total

This problem is excellent for understanding level-based tree traversal patterns and demonstrates how the same problem can be solved with different approaches. Mastering both BFS and DFS solutions showcases algorithm versatility and deep tree traversal knowledge!
