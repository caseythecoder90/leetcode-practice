# Cyclic Replacement for Array Rotation - Detailed Explanation

## Core Concept

The cyclic replacement approach moves each element **directly to its final position** by following a "chain" of displacements. Think of it like a game of musical chairs where each person moves to their final seat by following a path.

## The Algorithm

```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    int count = 0;  // Track how many elements we've placed
    
    for (int start = 0; count < n; start++) {
        int current = start;
        int prev = nums[start];  // Element to be placed
        
        do {
            int next = (current + k) % n;  // Where current element should go
            int temp = nums[next];         // Save what's currently there
            nums[next] = prev;             // Place our element
            prev = temp;                   // This becomes our next element to place
            current = next;                // Move to next position
            count++;                       // One more element placed
        } while (start != current);       // Continue until we complete the cycle
    }
}
```

## Step-by-Step Walkthrough

Let's trace through `nums = [1,2,3,4,5,6]` with `k = 2` (rotate right by 2):

**Expected Result:** `[5,6,1,2,3,4]`

### Understanding the Movement Pattern

First, let's see where each element should go:
- `nums[0] = 1` should go to position `(0 + 2) % 6 = 2`
- `nums[1] = 2` should go to position `(1 + 2) % 6 = 3`
- `nums[2] = 3` should go to position `(2 + 2) % 6 = 4`
- `nums[3] = 4` should go to position `(3 + 2) % 6 = 5`
- `nums[4] = 5` should go to position `(4 + 2) % 6 = 0`
- `nums[5] = 6` should go to position `(5 + 2) % 6 = 1`

### Cycle Detection

Notice the movement pattern forms **cycles**:
- **Cycle 1:** 0 → 2 → 4 → 0 (elements 1, 3, 5)
- **Cycle 2:** 1 → 3 → 5 → 1 (elements 2, 4, 6)

### Execution Trace

**Initial state:** `nums = [1,2,3,4,5,6]`, `count = 0`

#### First Cycle (start = 0)

**Iteration 1:**
```
start = 0, current = 0, prev = 1
next = (0 + 2) % 6 = 2
temp = nums[2] = 3
nums[2] = 1  →  nums = [1,2,1,4,5,6]
prev = 3, current = 2, count = 1
```

**Iteration 2:**
```
current = 2, prev = 3  
next = (2 + 2) % 6 = 4
temp = nums[4] = 5
nums[4] = 3  →  nums = [1,2,1,4,3,6]
prev = 5, current = 4, count = 2
```

**Iteration 3:**
```
current = 4, prev = 5
next = (4 + 2) % 6 = 0  
temp = nums[0] = 1
nums[0] = 5  →  nums = [5,2,1,4,3,6]
prev = 1, current = 0, count = 3
```

**Check:** `start (0) == current (0)` → **Cycle complete!**

After first cycle: `nums = [5,2,1,4,3,6]`, `count = 3`

#### Second Cycle (start = 1)

**Iteration 1:**
```
start = 1, current = 1, prev = 2
next = (1 + 2) % 6 = 3
temp = nums[3] = 4  
nums[3] = 2  →  nums = [5,2,1,2,3,6]
prev = 4, current = 3, count = 4
```

**Iteration 2:**
```
current = 3, prev = 4
next = (3 + 2) % 6 = 5
temp = nums[5] = 6
nums[5] = 4  →  nums = [5,2,1,2,3,4]  
prev = 6, current = 5, count = 5
```

**Iteration 3:**
```
current = 5, prev = 6
next = (5 + 2) % 6 = 1
temp = nums[1] = 2
nums[1] = 6  →  nums = [5,6,1,2,3,4]
prev = 2, current = 1, count = 6
```

**Check:** `start (1) == current (1)` → **Cycle complete!**

**Final result:** `nums = [5,6,1,2,3,4]` ✓
**count = 6 = n** → **All elements processed!**

## Why Do We Need Multiple Cycles?

The number of cycles depends on `gcd(n, k)` (greatest common divisor):

- **gcd(6, 2) = 2** → We need **2 cycles**
- **gcd(5, 2) = 1** → We need **1 cycle**
- **gcd(4, 2) = 2** → We need **2 cycles**

### Mathematical Insight

Each cycle processes `n / gcd(n, k)` elements. Since we have `gcd(n, k)` cycles, we process all `n` elements exactly once.

## Visual Representation

For `nums = [1,2,3,4,5,6]`, `k = 2`:

```
Cycle 1: Position Flow
0 → 2 → 4 → 0
1   3   5   (back to 1)

Cycle 2: Position Flow
1 → 3 → 5 → 1  
2   4   6   (back to 2)
```

## Key Advantages

1. **Each element moved exactly once** → Optimal
2. **O(1) space complexity** → No extra arrays
3. **Mathematically elegant** → Based on cycle theory

## Key Challenges

1. **Cycle detection** → Need to know when to start a new cycle
2. **Complex logic** → Harder to debug than other approaches
3. **Mathematical background** → Requires understanding of gcd relationship

## When to Use This Approach

- **Space is critical** → O(1) space required
- **Performance is critical** → Each element touched exactly once
- **You want to show advanced knowledge** → Demonstrates deep algorithmic understanding

## Comparison with Other Approaches

| Approach | Time | Space | Complexity | Elegance |
|----------|------|-------|------------|----------|
| Extra Array | O(n) | O(n) | Low | Low |
| Reverse Method | O(n) | O(1) | Medium | High |
| Cyclic Replacement | O(n) | O(1) | High | Medium |

## Common Pitfalls

1. **Forgetting the outer loop** → Some cycles might not be processed
2. **Not tracking count** → Might not know when all elements are processed
3. **Infinite loops** → If cycle detection logic is wrong
4. **Edge cases** → When k = 0 or k = n

## Practice Exercise

Try tracing through:
- `nums = [1,2,3,4,5]`, `k = 2` → Should have 1 cycle
- `nums = [1,2,3,4]`, `k = 2` → Should have 2 cycles

The cyclic replacement approach is like solving a puzzle where you need to follow each element's journey to its final destination, making sure no element gets left behind!