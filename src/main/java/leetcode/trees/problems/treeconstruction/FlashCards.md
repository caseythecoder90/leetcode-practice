# Tree Construction from Traversals - Flashcards

## Basic Concepts

### Q: What are the three DFS traversal orders?
**A:**
- **Preorder**: Root → Left → Right
- **Inorder**: Left → Root → Right
- **Postorder**: Left → Right → Root

---

### Q: Why do we need TWO traversals to reconstruct a unique tree?
**A:** A single traversal is ambiguous - the same traversal can represent multiple different tree structures. Two traversals (with one being inorder) provide enough information to uniquely determine the tree.

---

### Q: Why must one of the traversals be inorder?
**A:** Inorder is the only traversal that clearly separates left and right subtrees. When you find the root in the inorder array, everything to its left belongs to the left subtree and everything to its right belongs to the right subtree.

---

### Q: What combinations of traversals can uniquely construct a tree?
**A:**
- ✅ Preorder + Inorder
- ✅ Postorder + Inorder
- ❌ Preorder + Postorder (ambiguous without additional constraints)
- ❌ Any single traversal (ambiguous)

---

## Problem 105: Preorder + Inorder

### Q: In preorder + inorder construction, where is the root located?
**A:** The root is the **first element** of the preorder array. Preorder visits root first.

---

### Q: How do you initialize the preorder index?
**A:** `preorderIndex = 0` (start from the beginning)

---

### Q: How do you update the preorder index?
**A:** Increment it: `preorder[preorderIndex++]`

---

### Q: What order do you build subtrees for preorder + inorder?
**A:** Build **LEFT subtree first**, then **RIGHT subtree**. This matches the order that preorder visits nodes.

---

### Q: Given preorder=[3,9,20,15,7] and inorder=[9,3,15,20,7], what is the root and where do you split?
**A:**
- Root: 3 (first in preorder)
- Find 3 in inorder at index 1
- Left subtree: [9] (inorder[0:0])
- Right subtree: [15,20,7] (inorder[2:4])

---

## Problem 106: Inorder + Postorder

### Q: In inorder + postorder construction, where is the root located?
**A:** The root is the **last element** of the postorder array. Postorder visits root last.

---

### Q: How do you initialize the postorder index?
**A:** `postorderIndex = postorder.length - 1` (start from the end)

---

### Q: How do you update the postorder index?
**A:** Decrement it: `postorder[postorderIndex--]`

---

### Q: What order do you build subtrees for inorder + postorder?
**A:** Build **RIGHT subtree first**, then **LEFT subtree**. This is opposite from preorder! Postorder processes right subtree before left when going backwards.

---

### Q: Why must you build RIGHT before LEFT for postorder + inorder?
**A:** Because postorder is Left→Right→Root. When processing backwards from the end, you encounter the right subtree's nodes before the left subtree's nodes.

---

### Q: Given postorder=[9,15,7,20,3] and inorder=[9,3,15,20,7], what is the root?
**A:** Root is 3 (last in postorder). Find it at index 1 in inorder to split left [9] and right [15,20,7].

---

## Algorithm Details

### Q: Why do we use a HashMap for inorder indices?
**A:** To achieve O(1) lookup time when finding the root's position in the inorder array. Without it, we'd have O(n) lookup leading to O(n²) total complexity.

---

### Q: What is the HashMap's key and value?
**A:**
- **Key**: The node value from inorder array
- **Value**: The index of that value in inorder array

---

### Q: What is the base case for the recursive helper?
**A:** `if (inStart > inEnd) return null` - When the range is invalid, there are no nodes to process.

---

### Q: What are the parameters for the recursive helper function?
**A:** Typically: `helper(int[] array, int inorderStart, int inorderEnd)` where inorderStart and inorderEnd define the current subtree's range in the inorder array.

---

### Q: How do you calculate the boundaries for the left subtree?
**A:**
- Start: `inorderStart` (beginning of current range)
- End: `inorderIndex - 1` (one before the root)

---

### Q: How do you calculate the boundaries for the right subtree?
**A:**
- Start: `inorderIndex + 1` (one after the root)
- End: `inorderEnd` (end of current range)

---

## Key Differences

### Q: What are the 4 key differences between problems 105 and 106?
**A:**
1. **Root location**: First (preorder) vs Last (postorder)
2. **Index initialization**: 0 vs length-1
3. **Index update**: ++ vs --
4. **Recursion order**: Left→Right vs Right→Left

---

### Q: For preorder + inorder, write the recursion calls in order.
**A:**
```java
root.left = helper(preorder, inStart, inorderIndex - 1);
root.right = helper(preorder, inorderIndex + 1, inEnd);
```

---

### Q: For inorder + postorder, write the recursion calls in order.
**A:**
```java
root.right = helper(postorder, inorderIndex + 1, inEnd);
root.left = helper(postorder, inStart, inorderIndex - 1);
```
Note: RIGHT comes first!

---

## Complexity Analysis

### Q: What is the time complexity and why?
**A:** **O(n)** where n is the number of nodes. We visit each node exactly once, and HashMap lookups are O(1).

---

### Q: What is the space complexity and why?
**A:** **O(n)**
- HashMap: O(n) to store all node indices
- Recursion stack: O(h) where h is height
    - Best case (balanced): O(log n)
    - Worst case (skewed): O(n)
- Total: O(n)

---

### Q: What happens to time complexity if you don't use a HashMap?
**A:** It becomes **O(n²)** because finding the root in inorder would take O(n) time, and you do this for every node.

---

## Common Mistakes

### Q: What happens if you forget to increment/decrement the index?
**A:** You'll keep using the same root value for every node, creating an incorrect tree. The index MUST be updated after each use.

---

### Q: What happens if you use LEFT→RIGHT order for postorder + inorder?
**A:** The tree will be constructed incorrectly because you're not processing nodes in the order they appear when traversing the postorder array backwards.

---

### Q: What's wrong with `root.left = helper(preorder, inStart, inorderIndex)`?
**A:** Off-by-one error! The left subtree should end at `inorderIndex - 1`, not include the root at `inorderIndex`.

---

### Q: Why can't you use preorder + postorder to uniquely construct a tree?
**A:** Both traversals visit nodes in orders that don't separate left and right subtrees. Without inorder, you can't determine which nodes belong to which subtree.

---

## Edge Cases

### Q: How do you handle an empty tree?
**A:** The base case `if (inStart > inEnd) return null` handles this naturally. For the initial call, if arrays are empty, it immediately returns null.

---

### Q: What happens with a single-node tree?
**A:**
- Root is found in preorder/postorder
- inorderIndex equals both inStart and inEnd
- Both recursive calls hit base case (inStart > inEnd)
- Only the root node is created

---

### Q: What does a left-skewed tree look like in preorder and inorder?
**A:**
- Preorder: [3, 2, 1]
- Inorder: [1, 2, 3]
  Each node only has a left child.

---

### Q: What does a right-skewed tree look like in preorder and inorder?
**A:**
- Preorder: [1, 2, 3]
- Inorder: [1, 2, 3]
  Each node only has a right child.

---

## Problem Recognition

### Q: What keywords indicate a tree construction problem?
**A:** "construct", "build", "reconstruct", "given preorder/inorder/postorder", "create binary tree from"

---

### Q: How quickly should you recognize this pattern?
**A:** Within 30 seconds of reading the problem. The mention of two traversal arrays is the immediate giveaway.

---

### Q: What should you state in your approach?
**A:**
1. "Use HashMap for O(1) inorder lookups"
2. "Use global index pointer for preorder/postorder"
3. "Recursively partition using inorder"
4. "Build LEFT→RIGHT for preorder, RIGHT→LEFT for postorder"

---

## Visualization

### Q: Draw the tree for preorder=[1,2,4,5,3,6] and inorder=[4,2,5,1,3,6]
**A:**
```
       1
      / \
     2   3
    / \   \
   4   5   6
```

---

### Q: For the tree above, what is the postorder traversal?
**A:** [4, 5, 2, 6, 3, 1] (Left → Right → Root)

---

## Application

### Q: You're given preorder=[3,9,20,15,7] and inorder=[9,3,15,20,7]. Walk through finding the root and splitting.
**A:**
1. Root = 3 (preorder[0])
2. Find 3 in inorder → index 1
3. Left subtree: inorder[0:0] = [9]
4. Right subtree: inorder[2:4] = [15,20,7]
5. Recurse on both parts

---

### Q: For postorder=[9,15,7,20,3] and inorder=[9,3,15,20,7], which subtree do you process first?
**A:** **Right subtree** [15,20,7] because when going backwards through postorder, you encounter right subtree nodes before left subtree nodes.

---

## Code Snippets

### Q: Write the HashMap initialization code.
**A:**
```java
Map<Integer, Integer> inorderMap = new HashMap<>();
for (int i = 0; i < inorder.length; i++) {
    inorderMap.put(inorder[i], i);
}
```

---

### Q: Write the base case for the recursive helper.
**A:**
```java
if (inStart > inEnd) {
    return null;
}
```

---

### Q: Write the code to get root and find it in inorder (for preorder).
**A:**
```java
int rootVal = preorder[preorderIndex++];
TreeNode root = new TreeNode(rootVal);
int inorderIndex = inorderMap.get(rootVal);
```

---

### Q: Write the code to get root and find it in inorder (for postorder).
**A:**
```java
int rootVal = postorder[postorderIndex--];
TreeNode root = new TreeNode(rootVal);
int inorderIndex = inorderMap.get(rootVal);
```

---

## Interview Strategy

### Q: What's your 10-minute implementation plan?
**A:**
1. (1 min) Clarify which traversals
2. (2 min) Explain approach with example
3. (1 min) Set up HashMap and index
4. (4 min) Write recursive helper
5. (2 min) Test with example and edge case

---

### Q: What should you test your solution with?
**A:**
1. Given example from problem
2. Single node
3. Left-skewed tree
4. Right-skewed tree
5. Empty tree (if applicable)

---

### Q: What's the most important thing to remember for interviews?
**A:** **Recursion order matters!**
- Preorder + Inorder: LEFT → RIGHT
- Postorder + Inorder: RIGHT → LEFT

This single detail is the most common mistake in interviews.