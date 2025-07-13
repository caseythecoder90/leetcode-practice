# Binary Trees Pattern

## Overview

Binary trees are one of the most important data structures in computer science and a cornerstone of coding interviews. This section is organized into two main approaches for tree traversal and problem-solving:

1. **BFS (Breadth-First Search)** - Level-by-level processing
2. **DFS (Depth-First Search)** - Path-based and recursive processing

## When to Use Each Approach

### Use BFS When:
- ✅ **Level-order processing** - "level by level", "maximum level sum"
- ✅ **Tree width/diameter** - Finding widest level or shortest paths
- ✅ **Tree views** - Right side view, left side view
- ✅ **Shortest path problems** - Minimum depth, closest leaf
- ✅ **Level statistics** - Sum/average/max per level

### Use DFS When:
- ✅ **Path problems** - Root-to-leaf paths, path sums
- ✅ **Tree validation** - Check BST properties, tree structure
- ✅ **Height/depth calculations** - Maximum depth, balanced tree
- ✅ **Tree transformation** - Invert tree, serialize/deserialize
- ✅ **Node relationships** - Lowest common ancestor, good nodes

## Directory Structure

```
trees/
├── README.md (this file)
├── bfs/                          # Breadth-First Search
│   ├── README.md                 # BFS concepts and patterns
│   ├── CheatSheet.md             # Quick reference for BFS
│   └── problems/
│       └── MaximumLevelSum/      # LeetCode 1161
│           ├── README.md
│           └── MaximumLevelSum.java
└── dfs/                          # Depth-First Search
    ├── README.md                 # DFS concepts and patterns
    ├── CheatSheet.md             # Quick reference for DFS
    └── problems/
        ├── MaximumDepth/         # Future: LeetCode 104
        ├── LeafSimilarTrees/     # Future: LeetCode 872
        ├── CountGoodNodes/       # Future: LeetCode 1448
        ├── PathSumIII/           # Future: LeetCode 437
        ├── LongestZigZagPath/    # Future: LeetCode 1372
        └── LowestCommonAncestor/ # Future: LeetCode 236
```

## Learning Path

### Phase 1: BFS Fundamentals
1. **Start with BFS** - Read `bfs/README.md` and `bfs/CheatSheet.md`
2. **Practice Maximum Level Sum** - Complete the provided solution
3. **Master the BFS template** - Understand queue size capture technique

### Phase 2: DFS Fundamentals  
4. **Learn DFS basics** - Read `dfs/README.md` and `dfs/CheatSheet.md`
5. **Practice simple recursion** - Maximum depth, leaf collection
6. **Master the DFS templates** - Basic recursion, path tracking, global state

### Phase 3: Advanced Applications
7. **Complex BFS problems** - ZigZag traversal, tree views
8. **Complex DFS problems** - Path sum variations, tree validation
9. **Combined approaches** - Use both BFS and DFS as needed

## LeetCode 75 Tree Problems

### BFS Problems
- **199. Binary Tree Right Side View** (Medium)
- **1161. Maximum Level Sum of a Binary Tree** (Medium) ✅ **Completed**

### DFS Problems  
- **104. Maximum Depth of Binary Tree** (Easy)
- **872. Leaf-Similar Trees** (Easy)
- **1448. Count Good Nodes in Binary Tree** (Medium)
- **437. Path Sum III** (Medium)
- **1372. Longest ZigZag Path in a Binary Tree** (Medium)
- **236. Lowest Common Ancestor of a Binary Tree** (Medium)

## Quick Pattern Recognition

### Problem Keywords → Approach
| Keywords | Approach | Example Problems |
|----------|----------|------------------|
| "level", "level by level" | BFS | Level order traversal, level sum |
| "right side view", "left view" | BFS | Tree views |
| "minimum depth", "shortest" | BFS | Shortest path to leaf |
| "path sum", "root to leaf" | DFS | Path sum problems |
| "maximum depth", "height" | DFS | Tree height calculation |
| "validate", "check property" | DFS | BST validation |
| "count nodes", "good nodes" | DFS | Node counting with conditions |
| "ancestor", "descendant" | DFS | LCA, path relationships |

## Common TreeNode Definition

All problems use this standard TreeNode structure:

```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
```

## Interview Preparation Strategy

### Step 1: Master Templates (Week 1)
- Learn BFS level-order template
- Learn DFS recursive templates
- Practice basic implementations

### Step 2: Pattern Recognition (Week 2)  
- Identify BFS vs DFS problems quickly
- Practice applying correct templates
- Focus on edge cases and complexity analysis

### Step 3: Advanced Problems (Week 3+)
- Combine techniques for complex problems
- Optimize space/time complexity
- Handle special cases and variations

## Next Steps

1. **Complete Maximum Level Sum** - Use the provided solution and study materials
2. **Review BFS CheatSheet** - Memorize the core template and patterns
3. **Move to DFS** - Start with simple problems like Maximum Depth
4. **Build progressively** - Add more problems as you master each pattern

---

**Remember**: Trees are fundamental to many other algorithms (heaps, BSTs, segment trees). Mastering these patterns will help you throughout your interview preparation journey!