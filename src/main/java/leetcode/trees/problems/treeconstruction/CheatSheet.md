# Tree Construction from Traversals - Cheat Sheet

## Quick Recognition

**Keywords**: construct, build tree, preorder, inorder, postorder, given two arrays

**Pattern Type**: Tree Construction

**Time**: O(n) | **Space**: O(n)

---

## The Two Problems

### 105: Preorder + Inorder → Tree
```java
class Solution {
    private int preIdx = 0;
    private Map<Integer, Integer> inMap = new HashMap<>();
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return build(preorder, 0, inorder.length - 1);
    }
    
    private TreeNode build(int[] pre, int inStart, int inEnd) {
        if (inStart > inEnd) return null;
        
        int rootVal = pre[preIdx++];
        TreeNode root = new TreeNode(rootVal);
        int idx = inMap.get(rootVal);
        
        root.left = build(pre, inStart, idx - 1);      // LEFT first
        root.right = build(pre, idx + 1, inEnd);       // RIGHT second
        
        return root;
    }
}
```

### 106: Inorder + Postorder → Tree
```java
class Solution {
    private int postIdx;
    private Map<Integer, Integer> inMap = new HashMap<>();
    
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIdx = postorder.length - 1;  // START FROM END
        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return build(postorder, 0, inorder.length - 1);
    }
    
    private TreeNode build(int[] post, int inStart, int inEnd) {
        if (inStart > inEnd) return null;
        
        int rootVal = post[postIdx--];
        TreeNode root = new TreeNode(rootVal);
        int idx = inMap.get(rootVal);
        
        root.right = build(post, idx + 1, inEnd);      // RIGHT first!
        root.left = build(post, inStart, idx - 1);     // LEFT second!
        
        return root;
    }
}
```

---

## Key Differences at a Glance

| Aspect | Preorder + Inorder | Inorder + Postorder |
|--------|-------------------|---------------------|
| **Root location** | `preorder[0]` (first) | `postorder[n-1]` (last) |
| **Index start** | `preIdx = 0` | `postIdx = n - 1` |
| **Index update** | `preIdx++` | `postIdx--` |
| **Build order** | LEFT then RIGHT | RIGHT then LEFT |

---

## Core Algorithm Steps (Universal)

```
1. Create HashMap: value → inorder index (O(1) lookup)
2. Recursive helper(inStart, inEnd):
   a. Base case: if inStart > inEnd return null
   b. Get root from preorder/postorder (update index!)
   c. Find root in inorder using HashMap
   d. Recursively build left subtree
   e. Recursively build right subtree
   f. Return root
```

---

## Why It Works

### Preorder: Root → Left → Right
```
[3, 9, 20, 15, 7]
 ↑  ↑  ↑_______ 
root L  R (entire right subtree)

Index moves naturally: root(3) → left(9) → right(20) → ...
```

### Postorder: Left → Right → Root
```
[9, 15, 7, 20, 3]
 ↑______  ↑   ↑
 L (entire) R  root

Index moves backwards: root(3) → right(20) → left(9) → ...
Must process RIGHT before LEFT!
```

### Inorder: Left → Root → Right
```
[9, 3, 15, 20, 7]
 ←L→ ↑ ←―――R―――→

Everything left of root → left subtree
Everything right of root → right subtree
```

---

## Critical Points

### ✅ Must Have
1. **HashMap for inorder** - O(1) lookups
2. **Global index pointer** - tracks position in pre/post
3. **Correct recursion order**:
    - Preorder: LEFT → RIGHT
    - Postorder: RIGHT → LEFT (!!!)
4. **Boundary checks** - `inStart > inEnd`

### ❌ Common Mistakes
1. Wrong recursion order (LEFT first for postorder)
2. Forgetting to increment/decrement index
3. Off-by-one in boundaries
4. Not using HashMap (O(n²) solution)

---

## Template Code

```java
class Solution {
    private int index;  // or preorderIndex / postorderIndex
    private Map<Integer, Integer> inorderMap = new HashMap<>();
    
    public TreeNode buildTree(int[] arr1, int[] inorder) {
        // Initialize index (0 for preorder, n-1 for postorder)
        index = /* 0 or arr1.length - 1 */;
        
        // Build HashMap
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return helper(arr1, 0, inorder.length - 1);
    }
    
    private TreeNode helper(int[] arr, int inStart, int inEnd) {
        if (inStart > inEnd) return null;
        
        // Get root (increment or decrement index)
        int rootVal = arr[index /* ++ or -- */];
        TreeNode root = new TreeNode(rootVal);
        
        // Find in inorder
        int inorderIndex = inorderMap.get(rootVal);
        
        // Build subtrees (order depends on traversal type)
        root./* left/right */ = helper(arr, /* bounds */);
        root./* right/left */ = helper(arr, /* bounds */);
        
        return root;
    }
}
```

---

## Traversal Visual Reference

```
       3
      / \
     9   20
        /  \
       15   7

Preorder:  [3, 9, 20, 15, 7]    Root → L → R
Inorder:   [9, 3, 15, 20, 7]    L → Root → R  
Postorder: [9, 15, 7, 20, 3]    L → R → Root
```

---

## Edge Cases Checklist

- [ ] Empty arrays → `null`
- [ ] Single node → works naturally
- [ ] Left skewed: `[3,2,1]` pre, `[1,2,3]` in
- [ ] Right skewed: `[1,2,3]` pre, `[1,2,3]` in
- [ ] All values unique (guaranteed by problem)

---

## Quick Interview Tips

1. **Recognize immediately**: Two traversals + inorder = tree construction
2. **Ask clarification**: Which two traversals? (Usually obvious from parameter names)
3. **State approach**: "Use HashMap for O(1) inorder lookups, recursively partition"
4. **Watch recursion order**: Preorder=left first, Postorder=right first
5. **Test edge cases**: Empty, single node, skewed tree

---

## Why These Specific Combinations?

**Need inorder + one other:**
- Inorder alone: ambiguous
- Preorder alone: ambiguous
- Postorder alone: ambiguous
- **Inorder + Preorder**: ✅ unique tree
- **Inorder + Postorder**: ✅ unique tree
- Preorder + Postorder: ambiguous (unless full binary tree)

**Inorder is special** because it's the only one that clearly separates left/right subtrees!

---

## Complexity

- **Time**: O(n) - visit each node once, O(1) HashMap lookup
- **Space**: O(n) - HashMap O(n) + recursion stack O(h)
    - Best case stack: O(log n) balanced tree
    - Worst case stack: O(n) skewed tree