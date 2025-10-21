# Same Tree - Quick Reference Cheat Sheet

## 🎯 Pattern: Tree Comparison with DFS

---

## Problem Statement (One Line)
Check if two binary trees are structurally identical with same node values.

---

## ⚡ Quick Solution
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

## 🔑 Key Insights

| Insight | Explanation |
|---------|-------------|
| **Recursive by nature** | Trees are recursive → comparison is recursive |
| **Three conditions** | Both null, one null, or values match |
| **AND logic** | BOTH subtrees must match (not just one) |
| **Order matters** | Check nulls BEFORE accessing values |
| **Early termination** | Return false immediately on mismatch |

---

## 📋 Algorithm Steps

```
1. Check: Both null? → YES: return true
2. Check: One null?  → YES: return false
3. Check: Values different? → YES: return false
4. Recurse: Compare left AND right subtrees
```

---

## 🎨 Visual Pattern

```
      p        q
      ↓        ↓
    Same? ────────→ Check value
      │              │
      ├──Same────────┘
      │
      ├──→ Compare Left Subtrees
      │         │
      │         └──→ Recursive Call
      │
      └──→ Compare Right Subtrees
                │
                └──→ Recursive Call
```

---

## ⚠️ Common Pitfalls

| ❌ Wrong | ✅ Right | Why |
|----------|----------|-----|
| `if (p == null \|\| q == null) return false;` | `if (p == null && q == null) return true;`<br>`if (p == null \|\| q == null) return false;` | Handle both null first |
| `if (p.val != q.val) return false;`<br>(before null check) | Null checks first | Avoid NullPointerException |
| `isSameTree(left) \|\| isSameTree(right)` | `isSameTree(left) && isSameTree(right)` | BOTH must match |
| Forgetting `return` | `return isSameTree(...) && isSameTree(...)` | Must return result |

---

## 📊 Complexity

| Metric | Value | Explanation |
|--------|-------|-------------|
| **Time** | O(min(n,m)) | Visit each node once, early termination possible |
| **Space** | O(min(h₁,h₂)) | Recursion stack depth = tree height |
| **Best Case** | O(1) | Roots don't match |
| **Worst Case** | O(n) | Must check all nodes |

---

## 🧪 Test Cases Checklist

- [ ] Both trees empty: `(null, null)` → `true`
- [ ] One tree empty: `([1], null)` → `false`
- [ ] Single nodes equal: `([5], [5])` → `true`
- [ ] Single nodes different: `([1], [2])` → `false`
- [ ] Same structure and values: `([1,2,3], [1,2,3])` → `true`
- [ ] Same values, different structure: `([1,2], [1,null,2])` → `false`
- [ ] Different values, same structure: `([1,2,3], [1,2,4])` → `false`

---

## 🎯 Decision Tree

```
Compare(p, q)
    │
    ├─ Both null? YES → true
    │
    ├─ One null? YES → false
    │
    ├─ Values differ? YES → false
    │
    └─ NO → Compare(p.left, q.left) AND Compare(p.right, q.right)
```

---

## 🧠 Memory Aid: "NBC Rule"

- **N**ull: Check null cases first
- **B**oth: Both values must match
- **C**ompare: Compare subtrees with AND

---

## 💬 Interview Script

**"I'll solve this with recursive DFS because:"**
1. Trees are recursive structures
2. I need to compare corresponding nodes
3. Natural fit for recursion

**"My approach has three checks:"**
1. Both null → trees match
2. One null → structure mismatch
3. Values differ → not same

**"I use AND logic for subtrees because BOTH left and right must be identical."**

**"Complexity is O(n) time and O(h) space for recursion stack."**

---

## 🔄 Alternative Approaches

### Iterative BFS (Queue)
```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(p);
    queue.offer(q);
    
    while (!queue.isEmpty()) {
        TreeNode n1 = queue.poll();
        TreeNode n2 = queue.poll();
        
        if (n1 == null && n2 == null) continue;
        if (n1 == null || n2 == null || n1.val != n2.val) 
            return false;
        
        queue.offer(n1.left);
        queue.offer(n2.left);
        queue.offer(n1.right);
        queue.offer(n2.right);
    }
    return true;
}
```
**Use when:** Worried about stack overflow with deep trees

---

## 🔗 Related Patterns

| Problem | Similarity | Difference |
|---------|------------|------------|
| **Symmetric Tree** | Tree comparison | Compare with mirror |
| **Subtree of Another Tree** | Uses isSameTree | Check if subtree exists |
| **Invert Binary Tree** | Tree transformation | Modifies instead of compares |

---

## 📝 Code Template (Fill in the blanks)

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    // Both null case
    if (_____ && _____) return _____;
    
    // One null case
    if (_____ || _____) return _____;
    
    // Compare values and recurse
    return _________ && 
           _________________ && 
           _________________;
}
```

**Answers:**
```java
if (p == null && q == null) return true;
if (p == null || q == null) return false;
return p.val == q.val && 
       isSameTree(p.left, q.left) && 
       isSameTree(p.right, q.right);
```

---

## 🎓 Mastery Checklist

- [ ] Can explain why recursive approach fits naturally
- [ ] Can code solution from memory in under 2 minutes
- [ ] Can identify all edge cases without hints
- [ ] Can explain AND vs OR for subtree comparison
- [ ] Can trace execution for small examples
- [ ] Can explain time and space complexity
- [ ] Can implement iterative version as alternative
- [ ] Ready to apply pattern to Symmetric Tree problem

---

## ⚡ One-Liner Essence

**Two trees are same = same root + same left + same right** 🌲🌲
