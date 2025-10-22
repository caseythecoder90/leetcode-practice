# Sum Root to Leaf Numbers (LeetCode 129) - Flashcards

## Core Concepts

### Card 1: Problem Pattern
**Q:** What pattern does "Sum Root to Leaf Numbers" belong to?

**A:** **Binary Tree DFS with Path Accumulation**
- Traverse all root-to-leaf paths using DFS
- Accumulate state (build number) as you traverse
- Aggregate results at leaf nodes
- Similar to Path Sum problems

---

### Card 2: The Key Formula
**Q:** How do you build a number from digits while traversing?

**A:** **`newNumber = oldNumber * 10 + currentDigit`**

Example: Building 123
- Start: 0
- See 1: `0 * 10 + 1 = 1`
- See 2: `1 * 10 + 2 = 12`
- See 3: `12 * 10 + 3 = 123`

This is the CORE insight of the entire problem!

---

### Card 3: Leaf Node Detection
**Q:** How do you detect a leaf node?

**A:** **Both children must be null**
```java
if (node.left == null && node.right == null) {
    // This is a leaf!
}
```

**Common mistake:** Using OR `||` instead of AND `&&`
- A node with one child is NOT a leaf!

---

### Card 4: When to Add to Sum
**Q:** At which nodes should you add the accumulated number to the total sum?

**A:** **ONLY at leaf nodes!**

- ❌ NOT at every node
- ❌ NOT at internal nodes
- ✅ ONLY when both children are null

**Why?** Only leaves represent complete root-to-leaf paths.

---

### Card 5: Initial Call
**Q:** What initial value should you pass for the accumulated number?

**A:** **Start with 0**
```java
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);  // Start with 0
}
```

**Why?** Haven't seen any digits yet, so number is 0.

---

## Algorithm Understanding

### Card 6: Base Cases
**Q:** What are the two base cases for the DFS function?

**A:**
1. **Null node:** `if (node == null) return 0;`
   - Contributes nothing to sum

2. **Leaf node:** `if (node.left == null && node.right == null) return currentNum;`
   - Return the complete number

---

### Card 7: Recursive Case
**Q:** How do you handle non-leaf nodes?

**A:** **Sum the results from both subtrees**
```java
int leftSum = dfs(node.left, currentNum);
int rightSum = dfs(node.right, currentNum);
return leftSum + rightSum;
```

Both children get the SAME accumulated number as their starting point.

---

### Card 8: State Passing
**Q:** How do you pass the accumulated number through recursion?

**A:** **Via function parameter**
```java
private int dfs(TreeNode node, int currentNum) {
    // currentNum is passed down to children
    return dfs(node.left, currentNum) + dfs(node.right, currentNum);
}
```

Each recursive call receives the number built so far.

---

### Card 9: Building vs Returning
**Q:** When do you BUILD the number vs RETURN it?

**A:**
- **Build:** At EVERY node
  ```java
  currentNum = currentNum * 10 + node.val;
  ```

- **Return:** Only at LEAVES
  ```java
  if (isLeaf) return currentNum;
  ```

---

## Examples and Execution

### Card 10: Simple Example Trace
**Q:** Trace through tree [1,2,3]. What are the paths and sum?

**A:**
```
Tree:     1
         / \
        2   3

Path 1: 1->2
  At 1: num = 0 * 10 + 1 = 1
  At 2: num = 1 * 10 + 2 = 12
  Leaf! Return 12

Path 2: 1->3
  At 1: num = 0 * 10 + 1 = 1
  At 3: num = 1 * 10 + 3 = 13
  Leaf! Return 13

Sum: 12 + 13 = 25 ✓
```

---

### Card 11: Complex Example
**Q:** For tree [4,9,0,5,1], what are all paths?

**A:**
```
Tree:      4
          / \
         9   0
        / \
       5   1

Path 1: 4->9->5 = 495
Path 2: 4->9->1 = 491
Path 3: 4->0 = 40

Sum: 495 + 491 + 40 = 1026
```

---

### Card 12: Single Node
**Q:** What's the output for a single node tree [7]?

**A:** **7**

- Root is also a leaf
- Number = 7
- Return 7

---

## Common Mistakes

### Card 13: Mistake - Wrong Aggregation
**Q:** What's wrong with this code?
```java
private int dfs(TreeNode node, int num) {
    if (node == null) return 0;
    num = num * 10 + node.val;
    int sum = num;  // Add at every node
    sum += dfs(node.left, num);
    sum += dfs(node.right, num);
    return sum;
}
```

**A:** **Adds at every node, not just leaves!**

This would count:
- The number 1
- The number 12
- The number 13
Instead of just 12 and 13

**Fix:** Only return `num` at leaves.

---

### Card 14: Mistake - Leaf Detection
**Q:** What's wrong with this leaf check?
```java
if (node.left == null || node.right == null)
```

**A:** **Using OR (||) instead of AND (&&)!**

This would treat nodes with one child as leaves:
```
    1
   /
  2    ← Would incorrectly be treated as leaf!
   \
    3
```

**Fix:** Use `&&` to ensure BOTH children are null.

---

### Card 15: Mistake - String Manipulation
**Q:** Why should you avoid this approach?
```java
String num = "";
// ...
num = num + node.val;
// ...
return Integer.parseInt(num);
```

**A:** **String manipulation is slower and more error-prone**

- String concatenation is O(n) per operation
- Parsing has overhead
- More complex code
- **Better:** Use `num * 10 + digit` (O(1) math operation)

---

### Card 16: Mistake - Missing Parameter
**Q:** What's wrong with this function signature?
```java
private int dfs(TreeNode node) {  // Only node parameter
```

**A:** **No way to track accumulated number!**

Must include parameter to pass accumulated value:
```java
private int dfs(TreeNode node, int currentNum)
```

---

## Edge Cases

### Card 17: Edge Case - Tree with Zeros
**Q:** How do you handle zeros in the tree?

**A:** **Zero is a valid digit - no special handling needed!**

Example: [1,0,0]
```
    1
   / \
  0   0
```
Paths: 10 and 10
Sum: 20

The formula `num * 10 + 0` works correctly.

---

### Card 18: Edge Case - Skewed Tree
**Q:** What's the output for [1,2,null,3]?
```
  1
 /
2
 \
  3
```

**A:** **123**

Path: 1->2->3
- At 1: 1
- At 2: 12
- At 3: 123 (leaf!)

---

### Card 19: Edge Case - Deep Tree
**Q:** What's the maximum tree depth according to constraints?

**A:** **Depth ≤ 10**

- Maximum possible number: 9999999999 (10 digits)
- Still fits in 32-bit integer (2^31 - 1 ≈ 2.1 billion)
- No overflow concerns with given constraints

---

## Complexity

### Card 20: Time Complexity
**Q:** What's the time complexity and why?

**A:** **O(n)** where n = number of nodes

- Visit each node exactly once
- Constant work per node (one multiplication, one addition)
- Doesn't matter if tree is balanced or skewed

---

### Card 21: Space Complexity
**Q:** What's the space complexity for the recursive approach?

**A:** **O(h)** where h = height of tree

- Recursion stack depth = tree height
- **Best case:** O(log n) for balanced tree
- **Worst case:** O(n) for skewed tree (linked list)

Not counting output space (the final sum integer).

---

### Card 22: Space for Iterative
**Q:** What's the space complexity for iterative approach with stack?

**A:** **O(h)** - same as recursive!

- Stack stores (node, number) pairs
- Maximum stack size = tree height
- Same asymptotic complexity as recursion

---

## Implementation Details

### Card 23: Complete Recursive Solution
**Q:** Write the complete recursive solution from memory.

**A:**
```java
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

private int dfs(TreeNode node, int currentNum) {
    if (node == null) {
        return 0;
    }

    currentNum = currentNum * 10 + node.val;

    if (node.left == null && node.right == null) {
        return currentNum;
    }

    return dfs(node.left, currentNum) + dfs(node.right, currentNum);
}
```

---

### Card 24: Iterative Approach Key Idea
**Q:** How would you solve this iteratively?

**A:** **Use stack with (node, number) pairs**

```java
Stack<Pair> stack = new Stack<>();
stack.push(new Pair(root, root.val));

while (!stack.isEmpty()) {
    Pair current = stack.pop();
    if (isLeaf(current.node)) {
        sum += current.num;
    } else {
        // Push children with updated numbers
        if (current.node.left != null) {
            stack.push(new Pair(left, current.num * 10 + left.val));
        }
        // Same for right
    }
}
```

---

## Interview Strategy

### Card 25: How to Approach in Interview
**Q:** What's the step-by-step interview strategy?

**A:**
1. **Clarify:** Recursion OK? Answer fits in 32-bit int?
2. **Identify Pattern:** Root-to-leaf DFS with accumulation
3. **Explain Number Building:** Use `num * 10 + digit`
4. **Explain Leaf Detection:** Both children null
5. **Code:** Write clean recursive solution
6. **Test:** Single node, simple tree, edge cases
7. **Discuss:** Complexity and alternative approaches

---

### Card 26: What to Say First
**Q:** What should you say when you see this problem?

**A:**
"This is a DFS path accumulation problem. I'll traverse all root-to-leaf paths and build numbers as I go using the formula `num * 10 + digit`. When I reach a leaf, I'll add that complete number to my sum. The key is to pass the accumulated number through recursion parameters."

---

### Card 27: Explaining Number Building
**Q:** How do you explain the number building formula to an interviewer?

**A:**
"To build a number from digits, I multiply the current number by 10 and add the new digit. For example, to build 123: start with 0, see 1 → 1, see 2 → (1*10 + 2) = 12, see 3 → (12*10 + 3) = 123. This is more efficient than string concatenation."

---

### Card 28: Testing Strategy
**Q:** What test cases should you walk through?

**A:**
1. **Single node:** [5] → 5
2. **Simple tree:** [1,2,3] → 25
3. **With zeros:** [1,0] → 10
4. **Skewed:** [1,2,null,3] → 123
5. **Your example:** [4,9,0,5,1] → 1026

---

## Related Problems

### Card 29: Prerequisite Problems
**Q:** What simpler problems should you master first?

**A:**
1. **112. Path Sum** (Easy)
   - Check if any path sums to target
   - Same DFS pattern, simpler accumulation

2. **257. Binary Tree Paths** (Easy)
   - Return all root-to-leaf paths
   - Similar path traversal

---

### Card 30: Similar Problems
**Q:** What problems use the same pattern?

**A:**
- **113. Path Sum II** - Collect all paths with target sum
- **437. Path Sum III** - Paths can start/end anywhere
- **988. Smallest String Starting From Leaf** - Build strings instead
- **124. Binary Tree Maximum Path Sum** (Hard) - Optimization variant

---

## Memory Aids

### Card 31: The "BAL" Mnemonic
**Q:** What does "BAL" stand for in this problem?

**A:**
- **B**uild the number at each node
- **A**dd to sum only at leaves
- **L**eft + Right sums returned up tree

---

### Card 32: Three Key Checks
**Q:** What three checks must your solution have?

**A:**
1. **Null check:** `if (node == null) return 0;`
2. **Build number:** `num = num * 10 + node.val;`
3. **Leaf check:** `if (node.left == null && node.right == null)`

---

### Card 33: One-Line Summary
**Q:** Summarize the algorithm in one sentence.

**A:**
"Use DFS to traverse all root-to-leaf paths, building numbers via `num * 10 + digit` at each node, and sum the complete numbers at leaf nodes."

---

## Advanced Understanding

### Card 34: Why DFS Not BFS?
**Q:** Could you use BFS instead of DFS?

**A:** **Yes, but DFS is more natural**

- BFS would need to track (node, accumulated number) pairs in queue
- DFS naturally follows paths from root to leaf
- Both have same time/space complexity
- DFS code is simpler and more intuitive

---

### Card 35: Tail Recursion?
**Q:** Is this solution tail recursive?

**A:** **No, it's not tail recursive**

```java
return dfs(node.left, num) + dfs(node.right, num);
```

The addition happens AFTER the recursive calls return, so there's work after recursion. Not eligible for tail call optimization.

---

### Card 36: Can You Modify the Tree?
**Q:** Should you modify the tree nodes during traversal?

**A:** **No - use parameters to pass state**

❌ **Don't:** Store accumulated values in tree nodes
✅ **Do:** Pass state through function parameters

Keeps the solution clean and doesn't mutate input.

---

### Card 37: Path Collection Variant
**Q:** How would you modify this to return all paths AND their numbers?

**A:**
```java
List<Pair<List<Integer>, Integer>> result = new ArrayList<>();

void dfs(TreeNode node, int num, List<Integer> path) {
    path.add(node.val);
    num = num * 10 + node.val;

    if (isLeaf(node)) {
        result.add(new Pair(new ArrayList<>(path), num));
    }

    dfs(node.left, num, path);
    dfs(node.right, num, path);

    path.remove(path.size() - 1);  // Backtrack
}
```

---

## Quick Recall

### Card 38: The Template
**Q:** Fill in the blanks:
```java
public int sumNumbers(TreeNode root) {
    return dfs(root, ___);
}

private int dfs(TreeNode node, int num) {
    if (node == null) return ___;

    num = ___ * 10 + ___;

    if (node.left == null && node.right == null) {
        return ___;
    }

    return dfs(___, num) + dfs(___, num);
}
```

**A:**
```java
public int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

private int dfs(TreeNode node, int num) {
    if (node == null) return 0;

    num = num * 10 + node.val;

    if (node.left == null && node.right == null) {
        return num;
    }

    return dfs(node.left, num) + dfs(node.right, num);
}
```

---

### Card 39: Debugging Checklist
**Q:** What should you check if your solution fails?

**A:**
1. ✓ Are you using `&&` for leaf detection?
2. ✓ Are you building number at EVERY node?
3. ✓ Are you returning number ONLY at leaves?
4. ✓ Are you starting with `currentNum = 0`?
5. ✓ Is null check before accessing node?
6. ✓ Are you using `+` to sum left and right results?

---

### Card 40: Interview Confidence
**Q:** What makes this a medium-difficulty problem?

**A:**
- **Requires understanding:** DFS pattern + state accumulation
- **Key insight:** Mathematical number building
- **Tricky part:** Knowing when to aggregate (leaves only)
- **Not too hard:** Straightforward recursion once you get the pattern

With practice: 10-15 minutes to solve confidently! 🎯

---
