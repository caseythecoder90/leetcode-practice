# Average of Levels in Binary Tree - Complete Study Guide

## Pattern Overview

**Problem Type:** Binary Tree Level-Based Aggregation
**Core Pattern:** BFS Level Order Traversal OR DFS with Level Tracking

**Key Insight:** The problem requires processing nodes grouped by their depth level, computing an aggregate value (average) for each level independently.

## Why This Problem Matters

**Real-World Applications:**
- Computing metrics per hierarchy level in organizational charts
- Analyzing network topology (average bandwidth per hop)
- Game development (average score per difficulty level)
- Data analytics (grouping and averaging by category/depth)

**Interview Frequency:** Medium-High
- Tests understanding of tree traversal patterns
- Demonstrates knowledge of both BFS and DFS approaches
- Shows ability to handle edge cases (overflow, floating point)

## Core Algorithm Pattern: BFS Approach

### The Level Order Traversal Template

**For ANY level-based tree problem:**
1. Initialize queue with root
2. While queue is not empty:
   - Capture level size (number of nodes at current level)
   - Process all nodes in current level
   - Add children to queue for next level
3. Perform level-specific operation (sum, average, collect, etc.)

### Template Structure
```java
List<Result> levelOrderPattern(TreeNode root) {
    List<Result> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size(); // CRITICAL: capture before loop

        // Level-specific variables
        int/long/double aggregate = 0;

        // Process all nodes at current level
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();

            // Perform operation on node
            aggregate += process(node);

            // Add children for next level
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        // Store level result
        result.add(computeResult(aggregate, levelSize));
    }

    return result;
}
```

## Solution 1: BFS Step-by-Step Walkthrough

### Example: [3, 9, 20, null, null, 15, 7]

```
Tree:       3
           / \
          9   20
             /  \
            15   7
```

### Execution Trace

**Initial State:**
```
queue: [3]
result: []
```

**Iteration 1 (Level 0):**
```
levelSize = 1
levelSum = 0

Process node 3:
  levelSum += 3 → levelSum = 3
  Add children: 9, 20

queue after: [9, 20]
average = 3.0 / 1 = 3.0
result: [3.0]
```

**Iteration 2 (Level 1):**
```
levelSize = 2
levelSum = 0

Process node 9:
  levelSum += 9 → levelSum = 9
  No children to add

Process node 20:
  levelSum += 20 → levelSum = 29
  Add children: 15, 7

queue after: [15, 7]
average = 29.0 / 2 = 14.5
result: [3.0, 14.5]
```

**Iteration 3 (Level 2):**
```
levelSize = 2
levelSum = 0

Process node 15:
  levelSum += 15 → levelSum = 15
  No children to add

Process node 7:
  levelSum += 7 → levelSum = 22
  No children to add

queue after: []
average = 22.0 / 2 = 11.0
result: [3.0, 14.5, 11.0]
```

**Final Result:** [3.0, 14.5, 11.0]

### Key Observations

1. **Why capture `levelSize` before the loop?**
   - Queue size changes as we add children
   - Capturing it first ensures we only process current level nodes
   - This separates levels cleanly

2. **Why use `long` for sum?**
   - Node values can be -2^31 to 2^31 - 1
   - Multiple large values could overflow `int`
   - Example: Two nodes with value 2^31 - 1 would overflow

3. **Why cast to `double` before division?**
   - Integer division truncates: 29 / 2 = 14 (not 14.5)
   - Casting ensures floating-point division: (double) 29 / 2 = 14.5

## Solution 2: DFS Approach

### Understanding DFS with Level Tracking

**Key Difference from BFS:**
- BFS processes nodes level-by-level naturally
- DFS visits nodes depth-first, so we must track which level each node belongs to
- We collect data during traversal, then compute results afterward

### Algorithm Flow

1. **Data Collection Phase:**
   - Traverse tree with DFS, passing current level as parameter
   - Maintain two lists: `sums[level]` and `counts[level]`
   - For each node, add its value to `sums[level]` and increment `counts[level]`

2. **Computation Phase:**
   - After traversal, compute average for each level
   - `average[i] = sums[i] / counts[i]`

### Step-by-Step Example

**Same Tree:**
```
        3
       / \
      9   20
         /  \
        15   7
```

**DFS Traversal Order (preorder: root → left → right):**
```
Visit 3 (level 0):
  sums = [3], counts = [1]

Visit 9 (level 1):
  sums = [3, 9], counts = [1, 1]

Visit 20 (level 1):
  sums = [3, 29], counts = [1, 2]

Visit 15 (level 2):
  sums = [3, 29, 15], counts = [1, 2, 1]

Visit 7 (level 2):
  sums = [3, 29, 22], counts = [1, 2, 2]
```

**Computation Phase:**
```
level 0: 3 / 1 = 3.0
level 1: 29 / 2 = 14.5
level 2: 22 / 2 = 11.0

Result: [3.0, 14.5, 11.0]
```

### DFS Complete Code Walkthrough

```java
public List<Double> averageOfLevelsDFS(TreeNode root) {
    List<Long> sums = new ArrayList<>();
    List<Integer> counts = new ArrayList<>();

    // Phase 1: Collect data
    dfsHelper(root, 0, sums, counts);

    // Phase 2: Compute averages
    List<Double> result = new ArrayList<>();
    for (int i = 0; i < sums.size(); i++) {
        result.add((double) sums.get(i) / counts.get(i));
    }

    return result;
}

private void dfsHelper(TreeNode node, int level,
                       List<Long> sums, List<Integer> counts) {
    if (node == null) return;

    // Initialize this level if first time visiting
    if (level == sums.size()) {
        sums.add(0L);
        counts.add(0);
    }

    // Update this level's sum and count
    sums.set(level, sums.get(level) + node.val);
    counts.set(level, counts.get(level) + 1);

    // Recurse to children with incremented level
    dfsHelper(node.left, level + 1, sums, counts);
    dfsHelper(node.right, level + 1, sums, counts);
}
```

## BFS vs DFS: When to Use Which?

### BFS Advantages
- More intuitive for level-based problems
- Computes results on-the-fly (single pass)
- Natural fit for "process by level" thinking
- Better for very deep trees (less recursion stack usage)

### DFS Advantages
- Can be more space-efficient for wide trees
- Demonstrates alternative problem-solving approach
- Uses recursion (cleaner for some developers)
- Better when you need to collect data before computing

### Space Complexity Comparison

**BFS:**
- Queue size = width of tree at widest level
- Worst case: complete binary tree at second-to-last level
- Example: Tree with 1000 levels, last level has 2^999 nodes
  - Queue would hold ~2^999 nodes (huge!)

**DFS:**
- Recursion stack size = height of tree
- Additional storage for sums/counts = height
- Example: Same tree with 1000 levels
  - Stack would hold ~1000 calls (manageable!)

**Conclusion:** For deep, narrow trees → DFS is better. For wide, shallow trees → BFS is better.

## Edge Cases & Error Handling

### 1. Empty Tree
```java
Input: root = null
Output: []
```
**Handling:** Return empty list immediately

### 2. Single Node
```java
Input: root = [1]
Output: [1.0]
```
**Handling:** Works naturally - one level with one node

### 3. Skewed Tree (All Left or All Right)
```java
Input: root = [1, 2, 3, 4]
        1
       /
      2
     /
    3
   /
  4
Output: [1.0, 2.0, 3.0, 4.0]
```
**Handling:** Each level has one node, average equals node value

### 4. Integer Overflow
```java
Input: root = [2147483647, 2147483647, 2147483647]
           2147483647
          /          \
   2147483647    2147483647

Without long: (2147483647 + 2147483647) overflows!
With long: works correctly
```
**Solution:** Use `long` for sum accumulation

### 5. Negative Values
```java
Input: root = [-10, 5, 15]
         -10
        /   \
       5     15

Level 0: -10 / 1 = -10.0
Level 1: (5 + 15) / 2 = 10.0
Output: [-10.0, 10.0]
```
**Handling:** Works naturally - no special case needed

### 6. Mixed Positive and Negative (Cancellation)
```java
Input: root = [0, -10, 10]
          0
        /   \
      -10    10

Level 1: (-10 + 10) / 2 = 0.0
```
**Handling:** Works correctly - average can be zero

## Time & Space Complexity Analysis

### BFS Approach

**Time Complexity: O(n)**
- Visit each node exactly once: O(n)
- Queue operations (offer/poll): O(1) per operation
- Total: O(n)

**Space Complexity: O(w)**
- w = maximum width of tree
- Queue holds at most one level of nodes
- Result list: O(h) where h = height
- Total dominated by queue: O(w)

**Best case:** Skewed tree → O(1) queue size
**Worst case:** Complete tree → O(n/2) = O(n) queue size at last level

### DFS Approach

**Time Complexity: O(n)**
- Visit each node exactly once: O(n)
- List operations (get/set): O(1) per operation
- Total: O(n)

**Space Complexity: O(h)**
- Recursion stack: O(h)
- Sums list: O(h)
- Counts list: O(h)
- Result list: O(h)
- Total: O(h)

**Best case:** Balanced tree → O(log n)
**Worst case:** Skewed tree → O(n)

## Common Mistakes & How to Avoid Them

### ❌ Mistake 1: Using queue.size() in loop condition
```java
// WRONG - queue size changes during iteration!
while (!queue.isEmpty()) {
    for (int i = 0; i < queue.size(); i++) { // BUG!
        TreeNode node = queue.poll();
        // Adding children changes queue.size()
    }
}
```
**Fix:** Capture size before loop
```java
int levelSize = queue.size();
for (int i = 0; i < levelSize; i++) {
    // Now we process exactly levelSize nodes
}
```

### ❌ Mistake 2: Integer overflow
```java
// WRONG - can overflow!
int levelSum = 0;
levelSum += node.val; // What if multiple 2^31 - 1 values?
```
**Fix:** Use long for accumulation
```java
long levelSum = 0;
levelSum += node.val; // Safe!
```

### ❌ Mistake 3: Integer division
```java
// WRONG - truncates decimals!
int average = levelSum / levelSize; // 29 / 2 = 14, not 14.5
```
**Fix:** Cast to double before division
```java
double average = (double) levelSum / levelSize; // 29.0 / 2 = 14.5
```

### ❌ Mistake 4: Not initializing lists in DFS
```java
// WRONG - IndexOutOfBoundsException!
private void dfsHelper(TreeNode node, int level, List<Long> sums) {
    sums.set(level, sums.get(level) + node.val); // Level might not exist!
}
```
**Fix:** Check and initialize
```java
if (level == sums.size()) {
    sums.add(0L); // Create new level entry
}
```

## Debugging Tips

### 1. Visualize the Tree
Draw the tree structure to understand levels:
```
Level 0:           3
                  / \
Level 1:         9   20
                    /  \
Level 2:          15    7
```

### 2. Trace Queue State (BFS)
```java
System.out.println("Queue before level: " + queue);
System.out.println("Level size: " + levelSize);
System.out.println("Queue after level: " + queue);
```

### 3. Trace Sums and Counts (DFS)
```java
System.out.println("Level " + level +
                   ": sum=" + sums.get(level) +
                   ", count=" + counts.get(level));
```

### 4. Verify Level Boundaries
```java
System.out.println("Processing level " + currentLevel +
                   " with " + levelSize + " nodes");
```

## Related Problems & Variations

### Similar Level-Based Problems

1. **102. Binary Tree Level Order Traversal**
   - Return list of lists (all nodes per level, not average)
   - Direct application of same pattern

2. **107. Binary Tree Level Order Traversal II**
   - Same as 102 but bottom-up
   - Hint: Reverse result or use deque

3. **515. Find Largest Value in Each Tree Row**
   - Find max instead of average per level
   - Track max during level processing

4. **199. Binary Tree Right Side View**
   - Return rightmost node per level
   - Use index to identify last node

5. **103. Binary Tree Zigzag Level Order Traversal**
   - Alternate left-to-right and right-to-left
   - Add flag to reverse alternate levels

### Pattern Variations

**Aggregation Types:**
- Sum per level
- Average per level (this problem)
- Max/Min per level
- Count per level
- Specific element per level (first, last, etc.)

## Interview Strategy

### Recognition (30 seconds)
**Keywords:** "level", "each level", "average", "binary tree"
**Pattern:** Level-based aggregation → BFS or DFS with level tracking

### Approach (1-2 minutes)
1. Identify this as level order traversal problem
2. Choose BFS (more natural) or DFS (alternative)
3. Plan aggregation: sum nodes, count nodes, compute average
4. Consider edge cases: overflow, null root

### Implementation (5-7 minutes)
**BFS Path:**
1. Initialize queue and result list
2. Process levels with captured size
3. Accumulate sum with long
4. Compute and store average

**DFS Path:**
1. Create helper with level parameter
2. Initialize sums/counts lists
3. Collect data during traversal
4. Compute averages after traversal

### Verification (2 minutes)
**Test Cases:**
- Given examples
- Single node
- Overflow scenario
- Negative values

### Optimization Discussion (1 minute)
- Mention space complexity difference
- BFS: O(w), DFS: O(h)
- Choose based on tree shape if known

## Key Takeaways

1. **BFS template for level problems** - Capture level size before processing
2. **Use long for sum** - Prevent integer overflow
3. **Cast to double for division** - Ensure accurate averages
4. **DFS alternative exists** - Track level during traversal
5. **Space complexity differs** - BFS: O(w), DFS: O(h)
6. **Pattern applies broadly** - Same template for many level-based problems

## Practice Progression

1. **Master BFS approach first** - More intuitive for level-based problems
2. **Then try DFS** - Understand alternative perspective
3. **Solve related problems** - 102, 107, 515, 199
4. **Experiment with variations** - Max/min per level, zigzag, etc.
5. **Time yourself** - Aim for 5-7 minute implementation

This problem is a fundamental pattern in tree algorithms and appears frequently in technical interviews. Understanding both approaches demonstrates problem-solving versatility and deep knowledge of tree traversal techniques!
