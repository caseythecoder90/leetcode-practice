# Flashcards: Minimum Absolute Difference in BST

## Card 1: Problem Recognition
**Q:** What are the key indicators that a problem uses the "BST + In-Order + Previous Tracking" pattern?

**A:** 
- Tree is a **Binary Search Tree** (BST)
- Need to compare **consecutive values** or find relationships between values
- Keywords: "minimum difference", "closest values", "validate BST", "k-th element"
- Problem involves **differences, ranges, or ordering**

---

## Card 2: Core Insight
**Q:** Why can we solve this in O(n) instead of O(n²)?

**A:** 
Because **in-order traversal of a BST produces sorted values**. The minimum difference MUST be between consecutive values in sorted order, so we only need to compare adjacent pairs, not all pairs.

---

## Card 3: Instance Variables vs Parameters
**Q:** Why must `prev` be an instance variable instead of a method parameter?

**A:** 
Instance variables **persist across all recursive calls**, allowing each node to see the previous node's value. Parameters are **reset in each recursive call** and cannot maintain state across the entire traversal.

```java
// ❌ WRONG: prev resets in each call
private void inorder(TreeNode node, Integer prev) { ... }

// ✅ CORRECT: prev persists across all calls
private Integer prev = null;
private void inorder(TreeNode node) { ... }
```

---

## Card 4: Common Mistake - Parent vs Previous
**Q:** What's the difference between comparing with "parent" vs "previous" in this problem?

**A:**
- **Parent**: The node that called the current recursive call (structural relationship)
- **Previous**: The node that came before in in-order traversal (sorted order relationship)

We need **previous** because that's the adjacent value in sorted order!

```
Tree:    4          Parent relationships     In-order sequence
        / \         1's parent: 2            1 → 2 → 3 → 4 → 6
       2   6        3's parent: 2            
      / \           
     1   3          Previous relationships:
                    2's prev: 1
                    3's prev: 2
```

---

## Card 5: Template Structure
**Q:** What is the template for BST in-order traversal with previous value tracking?

**A:**
```java
class Solution {
    private int result = Integer.MAX_VALUE;
    private Integer prev = null;
    
    public int solve(TreeNode root) {
        inorder(root);
        return result;
    }
    
    private void inorder(TreeNode node) {
        if (node == null) return;
        
        inorder(node.left);        // 1. Left subtree
        
        if (prev != null) {        // 2. Process current
            result = Math.min(result, node.val - prev);
        }
        prev = node.val;           // 3. Update prev
        
        inorder(node.right);       // 4. Right subtree
    }
}
```

---

## Card 6: Why Check null?
**Q:** Why do we check `if (prev != null)` before comparing?

**A:** 
The **first node** visited in in-order traversal has no previous node to compare with. The null check prevents NullPointerException and ensures we only compare when there's actually a previous value.

---

## Card 7: In-Order Traversal Order
**Q:** What is the order of operations in in-order traversal and why?

**A:**
1. **Left subtree** - Process smaller values first
2. **Current node** - Process in sorted order
3. **Right subtree** - Process larger values last

This order guarantees we process values from smallest to largest in a BST.

---

## Card 8: Time Complexity
**Q:** What is the time complexity and why is it optimal?

**A:** 
**O(n)** where n is the number of nodes.
- Must visit every node to ensure we find the minimum
- Each node visited exactly once
- Constant work per node
- Cannot do better than O(n) - must examine all values

---

## Card 9: Space Complexity
**Q:** What is the space complexity and what does it depend on?

**A:**
**O(h)** where h is the tree height (recursion stack depth).
- **Balanced BST**: O(log n) - height is logarithmic
- **Skewed BST**: O(n) - height equals number of nodes
- No additional data structures needed (just a few variables)

---

## Card 10: Edge Case - First Node
**Q:** How does the algorithm handle the first node in traversal?

**A:**
The first node has `prev == null`, so the comparison is skipped. After processing, `prev` is set to that node's value, making it available for the next node's comparison.

```java
if (prev != null) {  // Skips first node
    minimum = Math.min(minimum, node.val - prev);
}
prev = node.val;  // Now available for next node
```

---

## Card 11: Math.abs() Necessity
**Q:** Do we need `Math.abs()` when calculating the difference? Why or why not?

**A:**
**No!** In-order traversal of BST guarantees values are in **increasing order**, so `node.val` is always greater than `prev`. The difference `node.val - prev` is always positive.

```java
// ❌ Unnecessary
Math.abs(node.val - prev)

// ✅ Sufficient
node.val - prev
```

---

## Card 12: Similar Problems
**Q:** What other LeetCode problems use the same pattern?

**A:**
- **783. Minimum Distance Between BST Nodes** (identical)
- **501. Find Mode in BST** (track consecutive duplicates)
- **98. Validate Binary Search Tree** (check increasing order)
- **285. Inorder Successor in BST** (find next larger value)
- **230. Kth Smallest Element in BST** (count to k-th element)

---

## Card 13: Alternative Approach
**Q:** What's an alternative (less optimal) approach to solve this problem?

**A:**
**Collect all values, then sort and compare:**
```java
public int getMinimumDifference(TreeNode root) {
    List<Integer> values = new ArrayList<>();
    collectValues(root, values);
    Collections.sort(values);  // Wasteful!
    
    int min = Integer.MAX_VALUE;
    for (int i = 1; i < values.size(); i++) {
        min = Math.min(min, values.get(i) - values.get(i-1));
    }
    return min;
}
```
**Why less optimal?** O(n log n) time for sorting, O(n) space. We're wasting the BST's sorted property!

---

## Card 14: Interview Red Flags
**Q:** What are common mistakes that show misunderstanding of this pattern?

**A:**
1. ❌ Comparing all pairs of nodes → O(n²)
2. ❌ Using method parameters for `prev` → doesn't persist
3. ❌ Comparing with parent instead of previous → wrong values
4. ❌ Forgetting null check on first node → NullPointerException
5. ❌ Collecting and sorting values → wasting BST property
6. ❌ Using Math.abs() → unnecessary in BST

---

## Card 15: Pattern Name
**Q:** What should you call this pattern when explaining to an interviewer?

**A:**
"**BST In-Order Traversal with Previous Value Tracking**" or simply "**In-Order with State Tracking**"

Key points to mention:
- Leverages BST property (in-order = sorted)
- Uses instance variable for state persistence
- O(n) time, O(h) space
- Only compares consecutive values in sorted order

---

## Card 16: When NOT to Use This Pattern
**Q:** When would this pattern NOT work?

**A:**
- Tree is **not a BST** (in-order won't be sorted)
- Need to compare **non-consecutive values** specifically
- Tree structure prevents in-order traversal
- Problem requires **all pairs** comparison for some other reason

In these cases, might need different approach (like comparing all pairs, using heap, etc.)

---

## Card 17: Iterative Alternative
**Q:** Can this be solved iteratively? How?

**A:**
**Yes!** Use an explicit stack for in-order traversal:

```java
public int getMinimumDifference(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    Integer prev = null;
    int minimum = Integer.MAX_VALUE;
    
    while (!stack.isEmpty() || curr != null) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        if (prev != null) {
            minimum = Math.min(minimum, curr.val - prev);
        }
        prev = curr.val;
        curr = curr.right;
    }
    return minimum;
}
```

---

## Card 18: Follow-up - K Closest Values
**Q:** How would you modify this to find the K pairs with smallest differences?

**A:**
Use a **max heap** of size K to track the K smallest differences:

```java
private PriorityQueue<Integer> maxHeap = 
    new PriorityQueue<>((a, b) -> b - a);
private int k;

// In inorder processing:
if (prev != null) {
    int diff = node.val - prev;
    if (maxHeap.size() < k) {
        maxHeap.offer(diff);
    } else if (diff < maxHeap.peek()) {
        maxHeap.poll();
        maxHeap.offer(diff);
    }
}
```

---

## Card 19: Trace Example
**Q:** Trace the execution for tree `[4,2,6,1,3]`. What's the minimum?

**A:**
```
Tree:       4
           / \
          2   6
         / \
        1   3

In-order sequence: 1 → 2 → 3 → 4 → 6

Comparisons:
1. Node 2: |2-1| = 1  (minimum = 1)
2. Node 3: |3-2| = 1  (minimum = 1)
3. Node 4: |4-3| = 1  (minimum = 1)
4. Node 6: |6-4| = 2  (minimum = 1)

Answer: 1
```

---

## Card 20: Memory Aid
**Q:** What's a good acronym to remember the pattern?

**A:**
**PINT** - Previous, Inorder, Null-check, Track

- **P**revious value as instance variable
- **I**norder traversal (left → node → right)
- **N**ull-check before using prev
- **T**rack/update prev after processing

Or visualize: **BST → In-Order → Sorted → Adjacent Pairs**
