# Binary Tree Fundamentals - Key Terms & Concepts

## Table of Contents
1. [Basic Tree Terminology](#basic-tree-terminology)
2. [Tree Measurements](#tree-measurements)
3. [Special Types of Binary Trees](#special-types-of-binary-trees)
4. [Tree Properties & Relationships](#tree-properties--relationships)
5. [Common Tree Operations](#common-tree-operations)
6. [Visual Examples](#visual-examples)

---

## Basic Tree Terminology

### Node Components
- **Node**: A single element containing data and references to child nodes
- **Root**: The topmost node of the tree (no parent)
- **Leaf**: A node with no children (both left and right are null)
- **Internal Node**: A node with at least one child
- **Parent**: A node that has child nodes
- **Child**: A node that has a parent (left child, right child)
- **Sibling**: Nodes that share the same parent

### Example:
```
       A       ← Root
      / \
     B   C     ← B and C are siblings, children of A
    / \   \
   D   E   F   ← D, E, F are leaves
```
- **Root**: A
- **Internal Nodes**: A, B, C
- **Leaves**: D, E, F
- **Siblings**: B & C, D & E
- **Parent of D**: B
- **Children of B**: D, E

---

## Tree Measurements

### 1. Height
**Definition**: The longest path from a node to any leaf
- **Tree Height**: Height from root to deepest leaf
- **Node Height**: Height from that specific node to deepest leaf in its subtree

```java
public int height(TreeNode root) {
    if (root == null) return -1; // or 0, depending on definition
    return Math.max(height(root.left), height(root.right)) + 1;
}
```

### 2. Depth/Level
**Definition**: The distance from root to a specific node
- **Root depth**: 0 (or 1, depending on definition)
- **Level**: Often used interchangeably with depth (sometimes depth + 1)

```java
public int depth(TreeNode root, TreeNode target) {
    if (root == null) return -1;
    if (root == target) return 0;
    
    int leftDepth = depth(root.left, target);
    if (leftDepth != -1) return leftDepth + 1;
    
    int rightDepth = depth(root.right, target);
    if (rightDepth != -1) return rightDepth + 1;
    
    return -1; // not found
}
```

### 3. Size
**Definition**: Total number of nodes in the tree

```java
public int size(TreeNode root) {
    if (root == null) return 0;
    return size(root.left) + size(root.right) + 1;
}
```

### 4. Diameter
**Definition**: The longest path between any two nodes in the tree
- May or may not pass through the root
- Also called "width" of the tree

```java
private int maxDiameter = 0;

public int diameter(TreeNode root) {
    calculateHeight(root);
    return maxDiameter;
}

private int calculateHeight(TreeNode root) {
    if (root == null) return 0;
    
    int leftHeight = calculateHeight(root.left);
    int rightHeight = calculateHeight(root.right);
    
    // Diameter through current node
    maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

### 5. Width (Level Width)
**Definition**: Maximum number of nodes at any level

```java
public int maxWidth(TreeNode root) {
    if (root == null) return 0;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    int maxWidth = 0;
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        maxWidth = Math.max(maxWidth, levelSize);
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
    
    return maxWidth;
}
```

---

## Special Types of Binary Trees

### 1. Full Binary Tree
**Definition**: Every node has either 0 or 2 children (no nodes with exactly 1 child)

```
   Full Binary Tree        NOT Full Binary Tree
       A                        A
      / \                      / \
     B   C                    B   C
    / \ / \                      /
   D E F G                      D
```

**Properties**:
- Number of leaves = (Number of internal nodes + 1)
- If height = h, minimum nodes = 2h + 1, maximum nodes = 2^(h+1) - 1

### 2. Complete Binary Tree
**Definition**: All levels are fully filled except possibly the last, which is filled from left to right

```
   Complete Binary Tree     NOT Complete Binary Tree
        A                         A
      /   \                     /   \
     B     C                   B     C
    / \   /                   / \     \
   D   E F                   D   E     F
```

**Properties**:
- Can be efficiently represented as an array
- Parent of node at index i: (i-1)/2
- Left child of node at index i: 2i + 1
- Right child of node at index i: 2i + 2
- Used in heap implementations

**Check if Complete**:
```java
public boolean isComplete(TreeNode root) {
    if (root == null) return true;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    boolean foundNull = false;
    
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        
        if (node == null) {
            foundNull = true;
        } else {
            if (foundNull) return false; // Found node after null
            queue.offer(node.left);
            queue.offer(node.right);
        }
    }
    
    return true;
}
```

### 3. Perfect Binary Tree
**Definition**: All internal nodes have 2 children AND all leaves are at the same level

```
   Perfect Binary Tree      NOT Perfect Binary Tree
        A                         A
      /   \                     /   \
     B     C                   B     C
    / \   / \                 / \   /
   D   E F   G               D   E F
```

**Properties**:
- Number of nodes = 2^(h+1) - 1 where h is height
- Number of leaves = 2^h
- Extremely balanced

### 4. Balanced Binary Tree
**Definition**: Height difference between left and right subtrees is at most 1 for every node

```
   Balanced Tree           NOT Balanced Tree
       A                        A
      / \                      /
     B   C                    B
    / \                      /
   D   E                    C
                           /
                          D
```

**Check if Balanced**:
```java
public boolean isBalanced(TreeNode root) {
    return checkHeight(root) != -1;
}

private int checkHeight(TreeNode root) {
    if (root == null) return 0;
    
    int leftHeight = checkHeight(root.left);
    if (leftHeight == -1) return -1;
    
    int rightHeight = checkHeight(root.right);
    if (rightHeight == -1) return -1;
    
    if (Math.abs(leftHeight - rightHeight) > 1) return -1;
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

### 5. Degenerate/Skewed Tree
**Definition**: Each internal node has only one child (essentially a linked list)

```
   Left Skewed Tree       Right Skewed Tree
        A                       A
       /                         \
      B                           B
     /                             \
    C                               C
   /                                 \
  D                                   D
```

**Properties**:
- Height = Number of nodes - 1
- Worst case for many tree operations
- Space complexity O(n) for recursion

---

## Tree Properties & Relationships

### Mathematical Properties

1. **Maximum nodes at level i**: 2^i (where root is level 0)
2. **Maximum nodes in tree of height h**: 2^(h+1) - 1
3. **Minimum height for n nodes**: ⌊log₂(n)⌋
4. **Maximum height for n nodes**: n - 1 (skewed tree)

### Binary Search Tree (BST) Properties
**Definition**: For every node, left subtree values < node value < right subtree values

```
   Valid BST              Invalid BST
      5                      5
     / \                    / \
    3   8                  3   8
   / \ / \                / \ / \
  2 4 6 9               2 6 4 9
                          ↑ violates BST property
```

**In-order traversal of BST gives sorted sequence**

### Heap Properties
**Max Heap**: Parent ≥ children for every node
**Min Heap**: Parent ≤ children for every node

```
   Max Heap               Min Heap
      9                      1
     / \                    / \
    7   8                  3   2
   / \ /                  / \ /
  4 5 6                  4 5 6
```

---

## Common Tree Operations

### Traversals

#### 1. Depth-First Search (DFS)
```java
// Preorder: Root → Left → Right
void preorder(TreeNode root) {
    if (root == null) return;
    visit(root);
    preorder(root.left);
    preorder(root.right);
}

// Inorder: Left → Root → Right
void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);
    visit(root);
    inorder(root.right);
}

// Postorder: Left → Right → Root
void postorder(TreeNode root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    visit(root);
}
```

#### 2. Breadth-First Search (BFS)
```java
void levelOrder(TreeNode root) {
    if (root == null) return;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        visit(node);
        
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

### Common Algorithms

#### Find Lowest Common Ancestor (LCA)
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

#### Check if Symmetric
```java
public boolean isSymmetric(TreeNode root) {
    return root == null || isSymmetricHelper(root.left, root.right);
}

private boolean isSymmetricHelper(TreeNode left, TreeNode right) {
    if (left == null && right == null) return true;
    if (left == null || right == null) return false;
    
    return left.val == right.val &&
           isSymmetricHelper(left.left, right.right) &&
           isSymmetricHelper(left.right, right.left);
}
```

---

## Visual Examples

### Comprehensive Example Tree
```
                    10        ← Root (Level 0, Height 3)
                   /  \
                  5    15      ← Level 1, Height 2
                 / \   / \
                3   7 12  20   ← Level 2, Height 1
               /   /     / \
              1   6     18 25  ← Level 3 (Leaves), Height 0
```

**Analysis of this tree**:
- **Size**: 10 nodes
- **Height**: 3 (longest path: 10→15→20→25)
- **Depth of node 6**: 3
- **Width**: 4 (Level 2 has 4 nodes)
- **Diameter**: 6 (path: 1→3→5→10→15→20→25)
- **Type**: Complete? No (Level 3 not filled left-to-right)
- **Type**: Balanced? Yes (height difference ≤ 1 for all nodes)
- **Type**: BST? Yes (satisfies BST property)

### Tree Type Comparison
```
Full Tree:           Complete Tree:       Perfect Tree:
    A                    A                    A
   / \                  / \                  / \
  B   C                B   C                B   C
 / \ / \              / \ /                / \ / \
D E F G              D E F                D E F G

Balanced Tree:       Skewed Tree:
    A                    A
   / \                    \
  B   C                    B
 /                          \
D                            C
                              \
                               D
```

---

## Interview Tips

### Key Questions to Ask Yourself:
1. **What type of traversal?** (Level-order → BFS, Path-based → DFS)
2. **What properties matter?** (Height, balance, completeness)
3. **What's the constraint?** (Time/space complexity)
4. **Edge cases?** (Empty tree, single node, skewed tree)

### Common Mistakes:
- Confusing height vs depth definitions
- Not handling null nodes properly
- Mixing up tree types (complete vs full vs perfect)
- Forgetting to consider skewed trees in complexity analysis

### Quick Reference:
- **Need level processing?** → BFS
- **Need path information?** → DFS
- **Shortest path to leaf?** → BFS with early termination
- **Tree validation?** → DFS with bounds/properties
- **Tree construction?** → Usually DFS with careful ordering