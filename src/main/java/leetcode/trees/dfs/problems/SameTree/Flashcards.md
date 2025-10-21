# Same Tree - Flashcards

## Card 1: Problem Recognition
**Q:** What pattern does "Same Tree" belong to?

**A:** Binary Tree DFS / Tree Comparison Pattern
- Comparing two trees node-by-node
- Recursive structure matches tree structure
- Need to check both structure and values

---

## Card 2: When to Use This Pattern
**Q:** When should you recognize this is a tree comparison problem?

**A:** Look for these keywords:
- "Compare two trees"
- "Are trees identical/same"
- "Structurally identical"
- "Same values at corresponding positions"

---

## Card 3: Core Algorithm
**Q:** What are the three conditions to check when comparing tree nodes?

**A:** 
1. **Both null** → Trees are same (return true)
2. **One null, other not** → Trees are different (return false)
3. **Both exist** → Check values and recurse on subtrees

---

## Card 4: Base Cases
**Q:** What are the base cases for `isSameTree(p, q)`?

**A:**
1. `if (p == null && q == null)` → return `true`
2. `if (p == null || q == null)` → return `false`
3. `if (p.val != q.val)` → return `false`

---

## Card 5: Recursive Case
**Q:** What's the recursive case for comparing trees?

**A:**
```java
return isSameTree(p.left, q.left) && 
       isSameTree(p.right, q.right);
```
**Key:** Use AND (&&) because BOTH subtrees must match!

---

## Card 6: Common Mistake - Null Handling
**Q:** What's wrong with this code?
```java
if (p == null || q == null) {
    return false;
}
```

**A:** It doesn't handle the case where BOTH are null! Two empty trees should return true.

**Fix:** Check both null first:
```java
if (p == null && q == null) return true;
if (p == null || q == null) return false;
```

---

## Card 7: Common Mistake - Order of Checks
**Q:** What's wrong with this code?
```java
if (p.val != q.val) return false;
if (p == null && q == null) return true;
```

**A:** Null checks must come BEFORE accessing `.val` to avoid NullPointerException!

**Correct order:**
1. Check nulls
2. Then access values
3. Then recurse

---

## Card 8: Common Mistake - Logic Operator
**Q:** Should you use AND or OR when comparing subtrees? Why?

**A:** Use **AND (&&)** because BOTH left and right subtrees must be identical.

❌ Wrong: `isSameTree(left) || isSameTree(right)` - only one needs to match
✅ Right: `isSameTree(left) && isSameTree(right)` - both must match

---

## Card 9: Time Complexity
**Q:** What's the time complexity of `isSameTree(p, q)`?

**A:** **O(min(n, m))** where n and m are number of nodes
- Visit each node at most once
- Early termination on mismatch
- Best case: O(1) if roots differ
- Worst case: O(min(n,m)) if must check all nodes

---

## Card 10: Space Complexity
**Q:** What's the space complexity and why?

**A:** **O(min(h₁, h₂))** where h₁ and h₂ are heights
- Space used by recursion call stack
- Maximum depth = height of tree
- Balanced tree: O(log n)
- Skewed tree: O(n)

---

## Card 11: Edge Case - Both Empty
**Q:** What should `isSameTree(null, null)` return?

**A:** **true** - Two empty trees are considered identical

---

## Card 12: Edge Case - One Empty
**Q:** What should `isSameTree(node, null)` return?

**A:** **false** - Trees with different structures are not the same

---

## Card 13: Edge Case - Same Values, Different Structure
**Q:** Are these trees the same?
```
p: 1        q:   1
  /              \
 2                2
```

**A:** **No (false)** - Even though values are the same, the structure is different (left vs right child)

---

## Card 14: The NBC Mnemonic
**Q:** What does "NBC" stand for in the Same Tree pattern?

**A:** 
- **N**ull checks first (both, then either)
- **B**oth values must match
- **C**ompare subtrees (left AND right)

---

## Card 15: Complete Solution Template
**Q:** Write the complete `isSameTree` solution from memory.

**A:**
```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    // Both null → same
    if (p == null && q == null) return true;
    
    // One null → different
    if (p == null || q == null) return false;
    
    // Values differ → different
    if (p.val != q.val) return false;
    
    // Check both subtrees
    return isSameTree(p.left, q.left) && 
           isSameTree(p.right, q.right);
}
```

---

## Card 16: Compact Version
**Q:** Can you write a more compact version of `isSameTree`?

**A:**
```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    return p.val == q.val && 
           isSameTree(p.left, q.left) && 
           isSameTree(p.right, q.right);
}
```

---

## Card 17: When Trees Are Different
**Q:** List all the ways two trees can be different.

**A:**
1. Different structure (one has node where other doesn't)
2. Different values (same structure, different node values)
3. Different sizes (one tree is larger)
4. Combination of above

---

## Card 18: Optimization - Early Termination
**Q:** How does early termination optimize this algorithm?

**A:** As soon as we find ANY mismatch (structure or value), we return false immediately without checking remaining nodes. This means best case is O(1) and average case is much better than worst case.

---

## Card 19: Related Problems
**Q:** What similar problems use the same pattern?

**A:**
- **Symmetric Tree (LC 101)** - Compare tree with mirror
- **Subtree of Another Tree (LC 572)** - Uses isSameTree as helper
- **Flip Equivalent Binary Trees (LC 951)** - Comparison with transformations

---

## Card 20: Interview Talking Point
**Q:** How would you explain your approach in an interview?

**A:** "I'll use recursive DFS since trees are recursive structures. I need to check three conditions: if both nodes are null they're same, if one is null they're different, and if both exist I'll compare values and recursively check both subtrees using AND logic since both must match."

---

## Spaced Repetition Schedule

### Day 1: Cards 1-5 (Pattern recognition and core algorithm)
### Day 2: Cards 6-10 (Common mistakes and complexity)
### Day 3: Cards 11-15 (Edge cases and solution)
### Day 4: Cards 16-20 (Optimization and variations)
### Week 2: Review all cards
### Week 4: Review all cards
### Month 2: Review all cards

**Pro tip:** Cover the answer, try to recall it, then check. This active recall strengthens memory much more than passive reading!
