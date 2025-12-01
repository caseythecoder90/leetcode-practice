# Binary Trees Pattern

## Overview

Binary trees are one of the most important data structures in computer science and a cornerstone of coding interviews. This section is organized into three main categories:

1. **BFS (Breadth-First Search)** - Level-by-level processing
2. **DFS (Depth-First Search)** - Path-based and recursive processing
3. **BST (Binary Search Trees)** - Ordered trees with special properties

## When to Use Each Approach

### Use BFS When:
- ✅ **Level-order processing** - "level by level", "maximum level sum"
- ✅ **Tree width/diameter** - Finding widest level or shortest paths
- ✅ **Tree views** - Right side view, left side view
- ✅ **Shortest path problems** - Minimum depth, closest leaf
- ✅ **Level statistics** - Sum/average/max per level

### Use DFS When:
- ✅ **Path problems** - Root-to-leaf paths, path sums
- ✅ **Tree validation** - Check structure, properties
- ✅ **Height/depth calculations** - Maximum depth, balanced tree
- ✅ **Tree transformation** - Invert tree, serialize/deserialize
- ✅ **Node relationships** - Lowest common ancestor, good nodes

### Use BST-Specific Patterns When:
- ✅ **Binary Search Tree** explicitly mentioned
- ✅ **Sorted order** needed or implied
- ✅ **Search/insert/delete** operations
- ✅ **Range queries** - Values in [low, high]
- ✅ **K-th element** problems
- ✅ **Validation** of BST property

## Directory Structure

```
trees/
├── README.md (this file)
├── BinaryTreeFundamentals.md    # Core tree concepts
├── bfs/                          # Breadth-First Search
│   ├── README.md                 # BFS concepts and patterns
│   ├── CheatSheet.md             # Quick reference for BFS
│   └── problems/
│       └── MaximumLevelSum/      # LeetCode 1161
│           ├── README.md
│           └── MaximumLevelSum.java
├── dfs/                          # Depth-First Search
│   ├── README.md                 # DFS concepts and patterns
│   ├── CheatSheet.md             # Quick reference for DFS
│   └── problems/
│       ├── MaximumDepth/         # Future: LeetCode 104
│       ├── LeafSimilarTrees/     # Future: LeetCode 872
│       ├── CountGoodNodes/       # Future: LeetCode 1448
│       ├── PathSumIII/           # Future: LeetCode 437
│       ├── LongestZigZagPath/    # Future: LeetCode 1372
│       └── LowestCommonAncestor/ # Future: LeetCode 236
└── bst/                          # Binary Search Trees ⭐ NEW!
    ├── README.md                 # BST concepts and properties
    ├── CheatSheet.md             # BST patterns and templates
    ├── Flashcards.md             # BST study cards
    └── problems/
        └── MinimumAbsoluteDifferenceBST/  # LeetCode 530 ✅
            ├── README.md
            ├── StudyGuide.md
            ├── Flashcards.md
            ├── CheatSheet.md
            └── MinimumAbsoluteDifferenceBST.java
```

## Learning Path

### Phase 1: BFS Fundamentals (Week 1)
1. **Start with BFS** - Read `bfs/README.md` and `bfs/CheatSheet.md`
2. **Practice Maximum Level Sum** - Complete the provided solution
3. **Master the BFS template** - Understand queue size capture technique

### Phase 2: DFS Fundamentals (Week 2)
4. **Learn DFS basics** - Read `dfs/README.md` and `dfs/CheatSheet.md`
5. **Practice simple recursion** - Maximum depth, leaf collection
6. **Master the DFS templates** - Basic recursion, path tracking, global state

### Phase 3: BST Mastery (Week 3) ⭐
7. **Understand BST properties** - Read `bst/README.md` - Critical for interviews!
8. **Master in-order traversal** - The key to most BST problems
9. **Practice BST patterns** - Search, validation, range queries
10. **Complete Minimum Absolute Difference** - Your first BST problem!

### Phase 4: Advanced Applications (Week 4+)
11. **Complex BFS problems** - ZigZag traversal, tree views
12. **Complex DFS problems** - Path sum variations, tree validation
13. **Advanced BST problems** - Insert/delete, construction
14. **Combined approaches** - Use multiple techniques as needed

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

### BST Problems ⭐
- **530. Minimum Absolute Difference in BST** (Easy) ✅ **Completed**
- **98. Validate Binary Search Tree** (Medium)
- **700. Search in a Binary Search Tree** (Easy)
- **450. Delete Node in a BST** (Medium)

## Quick Pattern Recognition

### Problem Keywords → Approach
| Keywords | Approach | Example Problems |
|----------|----------|------------------|
| "level", "level by level" | BFS | Level order traversal, level sum |
| "right side view", "left view" | BFS | Tree views |
| "minimum depth", "shortest" | BFS | Shortest path to leaf |
| "path sum", "root to leaf" | DFS | Path sum problems |
| "maximum depth", "height" | DFS | Tree height calculation |
| "ancestor", "descendant" | DFS | LCA, path relationships |
| **"Binary Search Tree", "BST"** | **BST patterns** | **Most BST problems** |
| **"sorted order", "in-order"** | **BST in-order** | **Value comparisons** |
| **"k-th smallest/largest"** | **BST in-order** | **Ordered elements** |
| **"range", "between X and Y"** | **BST range query** | **Range problems** |
| **"validate BST"** | **BST validation** | **Structure check** |

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

### Step 1: Master Templates (Week 1-2)
- Learn BFS level-order template
- Learn DFS recursive templates
- **Learn BST in-order template** ⭐
- Practice basic implementations

### Step 2: Pattern Recognition (Week 3-4)  
- Identify BFS vs DFS vs BST problems quickly
- **Recognize when BST property can be leveraged** ⭐
- Practice applying correct templates
- Focus on edge cases and complexity analysis

### Step 3: Advanced Problems (Week 5+)
- Combine techniques for complex problems
- Optimize space/time complexity
- Handle special cases and variations
- **Master BST modification operations**

## Critical BST Insights 🎯

### Why BST Deserves Special Attention:
1. **Appears frequently** in FAANG interviews
2. **Unique optimization opportunities** - O(log n) vs O(n)
3. **In-order traversal = sorted** - The most important property!
4. **Can prune search space** - Don't need to check all nodes
5. **Multiple patterns** - Search, range, validation, construction

### The BST Golden Rule:
```
BST + In-Order Traversal = Sorted Sequence
```

This single property unlocks solutions to dozens of problems!

## Next Steps

1. **Read BST fundamentals** - Start with `bst/README.md` ⭐ **Start here for BST problems!**
2. **Review your first BST problem** - Minimum Absolute Difference (already completed!)
3. **Master BST CheatSheet** - `bst/CheatSheet.md` for quick reference
4. **Complete BFS basics** - If not already done
5. **Practice DFS patterns** - Essential for all tree problems
6. **Build progressively** - Add more problems as you master each pattern

---

**Remember**: Trees are fundamental to many other algorithms (heaps, BSTs, segment trees, tries). Master BFS, DFS, and especially BST patterns - they appear constantly in interviews!

**Pro Tip**: When you see "Binary Search Tree" in a problem, immediately think: "Can I use in-order traversal? Can I prune my search?"
