# BST Flashcards

## Card 1: BST Definition
**Q:** What is the Binary Search Tree (BST) property?

**A:** 
For **every node** in a BST:
- All values in the **left subtree** are **less than** the node's value
- All values in the **right subtree** are **greater than** the node's value
- This property holds **recursively** for every subtree

```
Valid:      8           Invalid:    8
           / \                     / \
          3   10                  3   10
         / \    \                / \    \
        1   6   14              1   12  14
```
(12 > 8 but appears in left subtree!)

---

## Card 2: The Golden Rule
**Q:** What is the single most important BST property for solving interview problems?

**A:** 
**In-order traversal of a BST produces values in sorted order.**

This property enables O(n) solutions for many problems that would otherwise require O(n²) or O(n log n) with sorting.

```
BST:    5            In-order: 1 2 3 4 5 6 7
       / \           (Sorted!)
      3   6
     / \   \
    1   4   7
     \
      2
```

---

## Card 3: BST vs Binary Tree
**Q:** What are the key differences between a BST and a regular binary tree?

**A:**

| Aspect | Binary Tree | BST |
|--------|-------------|-----|
| Ordering | No order | Left < Root < Right |
| Search | O(n) | O(h) - can prune |
| In-order | Random | Sorted |
| Use case | Hierarchies | Searching, ordering |

**Key insight:** BST lets you eliminate entire subtrees during search!

---

## Card 4: Search Efficiency
**Q:** Why is BST search O(log n) for balanced trees but O(n) for skewed trees?

**A:** 
- **Balanced BST**: Height h = log n, can eliminate half the nodes at each level
- **Skewed BST**: Height h = n (basically a linked list), must check every node

```
Balanced:      4           Skewed:  1
              / \                    \
             2   6                    2
            / \                        \
           1   3                        3
                                         \
Height = log n                           4
Search = O(log n)            Height = n
                            Search = O(n)
```

---

## Card 5: Min/Max in BST
**Q:** How do you find minimum and maximum values in a BST?

**A:**
- **Minimum**: Go all the way **left**
- **Maximum**: Go all the way **right**

```java
TreeNode findMin(TreeNode root) {
    while (root.left != null) root = root.left;
    return root;
}

TreeNode findMax(TreeNode root) {
    while (root.right != null) root = root.right;
    return root;
}
```

Time: O(h) where h = height

---

## Card 6: BST Search Pattern
**Q:** What's the standard BST search template?

**A:**
```java
TreeNode search(TreeNode root, int target) {
    if (root == null || root.val == target) {
        return root;
    }
    
    // Prune: Only search relevant subtree
    if (target < root.val) {
        return search(root.left, target);   // Go left
    } else {
        return search(root.right, target);  // Go right
    }
}
```

**Key:** Never search both subtrees - use BST property to prune!

---

## Card 7: BST Insert
**Q:** How do you insert a new value into a BST while maintaining the BST property?

**A:**
```java
TreeNode insert(TreeNode root, int val) {
    if (root == null) {
        return new TreeNode(val);  // Found position!
    }
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.right = insert(root.right, val);
    }
    
    return root;
}
```

Time: O(h), Space: O(h) for recursion

---

## Card 8: BST Delete Cases
**Q:** What are the three cases when deleting a node from a BST?

**A:**

**Case 1: Leaf node (no children)**
- Simply remove it

**Case 2: One child**
- Replace node with its child

**Case 3: Two children**
- Replace node with its **inorder successor** (minimum in right subtree)
- Delete the successor from right subtree

```java
if (root.left == null) return root.right;     // Case 1 & 2
if (root.right == null) return root.left;     // Case 2

// Case 3
TreeNode successor = findMin(root.right);
root.val = successor.val;
root.right = delete(root.right, successor.val);
```

---

## Card 9: Validate BST - Common Mistake
**Q:** Why doesn't this BST validation work? How to fix it?

```java
boolean isValidBST(TreeNode root) {
    if (root == null) return true;
    if (root.left != null && root.left.val >= root.val) return false;
    if (root.right != null && root.right.val <= root.val) return false;
    return isValidBST(root.left) && isValidBST(root.right);
}
```

**A:** 
It only checks **immediate children**, not the entire subtree!

```
    10
   /  \
  5   15        This passes the check above
     /  \       but node 6 violates: 6 < 10
    6   20      but it's in the right subtree!
```

**Fix:** Pass min/max ranges:
```java
boolean isValidBST(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;
    if ((min != null && root.val <= min) || 
        (max != null && root.val >= max)) return false;
    
    return isValidBST(root.left, min, root.val) &&
           isValidBST(root.right, root.val, max);
}
```

---

## Card 10: In-Order with Previous Value
**Q:** What's the template for BST problems requiring consecutive value comparison?

**A:**
```java
private Integer prev = null;  // Instance variable!
private int result;

private void inorder(TreeNode node) {
    if (node == null) return;
    
    inorder(node.left);           // Visit left
    
    if (prev != null) {           // Process with previous
        result = compute(node.val, prev);
    }
    prev = node.val;              // Update for next
    
    inorder(node.right);          // Visit right
}
```

**Must use instance variable** - parameters reset each call!

---

## Card 11: Range Query Pattern
**Q:** How do you efficiently query values in a range [low, high] in a BST?

**A:**
```java
void rangeQuery(TreeNode root, int low, int high) {
    if (root == null) return;
    
    // Smart pruning based on BST property
    if (root.val >= low) {
        rangeQuery(root.left, low, high);  // May have >= low
    }
    
    if (root.val >= low && root.val <= high) {
        process(root);  // In range!
    }
    
    if (root.val <= high) {
        rangeQuery(root.right, low, high);  // May have <= high
    }
}
```

**Key:** Prune entire subtrees that can't contain values in range!

Time: O(log n + k) where k = number of nodes in range

---

## Card 12: Build Balanced BST
**Q:** How do you build a balanced BST from a sorted array?

**A:**
```java
TreeNode sortedArrayToBST(int[] nums, int left, int right) {
    if (left > right) return null;
    
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    
    root.left = sortedArrayToBST(nums, left, mid - 1);
    root.right = sortedArrayToBST(nums, mid + 1, right);
    
    return root;
}
```

**Strategy:** Pick middle element as root to ensure balance

Time: O(n), Space: O(log n) for recursion

---

## Card 13: Kth Smallest Element
**Q:** How do you find the k-th smallest element in a BST?

**A:**
Use in-order traversal with a counter:

```java
private int count = 0;
private int result;

public int kthSmallest(TreeNode root, int k) {
    inorder(root, k);
    return result;
}

private void inorder(TreeNode node, int k) {
    if (node == null) return;
    
    inorder(node.left, k);
    
    count++;
    if (count == k) {
        result = node.val;
        return;
    }
    
    inorder(node.right, k);
}
```

Time: O(k) if you stop early, O(n) worst case

---

## Card 14: Inorder Successor
**Q:** What is the inorder successor of a node in a BST and how do you find it?

**A:**
**Definition:** The next node in in-order traversal (next larger value)

**Two cases:**

1. **Node has right subtree:** Successor = minimum in right subtree
```java
if (node.right != null) {
    return findMin(node.right);
}
```

2. **No right subtree:** Successor = first ancestor for which node is in left subtree
```java
// Track parent pointers or search from root
```

---

## Card 15: BST Time Complexities
**Q:** What are the time complexities for common BST operations?

**A:**

**Balanced BST (h = log n):**
- Search: O(log n)
- Insert: O(log n)
- Delete: O(log n)
- Min/Max: O(log n)
- Range Query: O(log n + k)

**Skewed BST (h = n):**
- All operations: O(n)

**Always:**
- In-order traversal: O(n)

---

## Card 16: When NOT to Use BST Property
**Q:** When should you treat a BST as a regular binary tree?

**A:**
When the problem:
- Requires checking **all nodes** regardless of value
- Doesn't involve **searching, ordering, or ranges**
- Asks about **structure** rather than values

Examples:
- Maximum depth
- Symmetric tree check
- Level order traversal
- Path sum (all paths)

These problems don't benefit from BST pruning.

---

## Card 17: Common BST Mistakes
**Q:** What are the most common mistakes in BST problems?

**A:**

1. ❌ **Only validating immediate children** (not whole subtree)
2. ❌ **Searching both subtrees** (not pruning)
3. ❌ **Using parent instead of prev** in in-order
4. ❌ **Using parameters for persistent state** (use instance variables)
5. ❌ **Collecting all values and sorting** (ignoring in-order property)
6. ❌ **Forgetting null checks** for first node in traversal

---

## Card 18: BST Problem Keywords
**Q:** What keywords indicate a BST-specific approach?

**A:**

**Strong indicators:**
- "Binary Search Tree" / "BST"
- "sorted order"
- "in-order"
- "validate BST"

**Look for opportunities:**
- "minimum/maximum" → Go left/right
- "k-th smallest/largest" → In-order + count
- "difference" → In-order + prev
- "range" / "between X and Y" → Range query
- "closest to target" → BST search

---

## Card 19: Instance Variables vs Parameters
**Q:** Why must prev be an instance variable in BST in-order problems?

**A:**

**Parameters reset** in each recursive call - can't maintain state across entire traversal:
```java
void inorder(TreeNode node, Integer prev) {
    inorder(node.left, ???);  // What to pass?
    // prev doesn't persist between left and right calls
}
```

**Instance variables persist** across all calls:
```java
private Integer prev = null;  // Shared state!
void inorder(TreeNode node) {
    inorder(node.left);  // prev automatically available
    // prev is visible in all recursive calls
}
```

---

## Card 20: BST Pattern Recognition
**Q:** Given a problem, how do you decide which BST pattern to use?

**A:**

**Decision tree:**

1. **Need sorted values?** → In-order traversal pattern
2. **Searching for specific value?** → BST search pattern
3. **Range or bounds involved?** → Range query pattern
4. **Validating structure?** → Validation pattern
5. **Building or modifying?** → Construction/modification pattern

**Quick test:** "Can I prune entire subtrees?" 
- Yes → Use BST property
- No → Treat as regular tree

---

## Card 21: BST vs Sorted Array
**Q:** What are the trade-offs between BST and sorted array?

**A:**

| Operation | Sorted Array | Balanced BST |
|-----------|-------------|--------------|
| Search | O(log n) | O(log n) |
| Insert | O(n) - shift | O(log n) |
| Delete | O(n) - shift | O(log n) |
| Min/Max | O(1) | O(log n) |
| Range | O(log n + k) | O(log n + k) |
| Space | O(n) | O(n) |

**Use BST when:** Need frequent insertions/deletions
**Use Array when:** Data is static or rarely changes

---

## Card 22: Self-Balancing BSTs
**Q:** What are self-balancing BSTs and when do they matter?

**A:**

**Problem:** Regular BST can become skewed (O(n) operations)

**Solution:** Self-balancing trees maintain O(log n) height

**Types:**
- **AVL Tree**: Strict balancing (height diff ≤ 1)
- **Red-Black Tree**: Looser balancing (faster inserts)
- **Splay Tree**: Moves frequently accessed to root

**Interview note:** Usually not asked to implement, but good to mention for follow-ups about guaranteed O(log n) operations.

---

## Card 23: BST Memory Aid
**Q:** What's a good way to remember BST properties?

**A:**

**Acronym: BST-SORT**

- **B**inary property: Left < Root < Right
- **S**orted: In-order gives sorted sequence
- **T**raversal: Use in-order for most problems
- **S**earch: Can prune entire subtrees
- **O**perations: O(h) for balanced trees
- **R**ange: Easy to query efficiently
- **T**rack: Use instance variables for state

---

## Card 24: Convert BST to Greater Tree
**Q:** How would you convert a BST to a "Greater Tree" where each node's value becomes the sum of itself plus all greater values?

**A:**

**Insight:** Reverse in-order (Right → Root → Left) processes values from largest to smallest!

```java
private int sum = 0;

public TreeNode convertBST(TreeNode root) {
    if (root == null) return null;
    
    convertBST(root.right);  // Process larger values first
    
    sum += root.val;         // Add current to running sum
    root.val = sum;          // Update node
    
    convertBST(root.left);   // Process smaller values
    
    return root;
}
```

Example: `[4,1,6,0,2,5,7]` → `[18,21,15,21,19,20,7]`

---

## Card 25: Two Pointer on BST
**Q:** Can you use two-pointer techniques on a BST? How?

**A:**

**Yes!** Convert to sorted array via in-order, then use two pointers:

```java
// Find two elements that sum to target
public boolean findTarget(TreeNode root, int k) {
    List<Integer> list = new ArrayList<>();
    inorder(root, list);
    
    int left = 0, right = list.size() - 1;
    while (left < right) {
        int sum = list.get(left) + list.get(right);
        if (sum == k) return true;
        if (sum < k) left++;
        else right--;
    }
    return false;
}
```

**Trade-off:** O(n) space but simple implementation. Advanced: Use iterators for O(h) space.
