# Move Zeroes

**LeetCode Problem #283** | **Easy**

## Problem Description

Given an integer array `nums`, move all `0`'s to the end of it while maintaining the relative order of the non-zero elements.

**Note**: You must do this **in-place** without making a copy of the array.

### Examples

**Example 1:**
```
Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
```

**Example 2:**
```
Input: nums = [0]
Output: [0]
```

### Constraints
- `1 <= nums.length <= 10^4`
- `-2^31 <= nums[i] <= 2^31 - 1`

**Follow up**: Could you minimize the total number of operations done?

## Solution Approaches

### Approach 1: Fast/Slow Two Pointers (Optimal)
**Key Insight**: Use two pointers - one to find non-zero elements (fast), another to place them (slow).

**Mental Model**: Think of it as "compacting" the array by moving all non-zero elements to the front, then filling the rest with zeros.

**Algorithm**:
1. **Slow pointer** (`writeIndex`): Points to the position where the next non-zero element should be placed
2. **Fast pointer** (`readIndex`): Scans through the array looking for non-zero elements
3. When fast pointer finds a non-zero element, place it at slow pointer position and advance slow
4. After processing all elements, fill remaining positions with zeros

**Why this works**: The slow pointer always points to the "next available slot" for non-zero elements, ensuring we maintain relative order.

**Time Complexity**: O(n) - single pass through array
**Space Complexity**: O(1) - only using two pointers

### Approach 2: Two-Pass Solution (Also Optimal)
**Algorithm**:
1. First pass: Move all non-zero elements to front
2. Second pass: Fill remaining positions with zeros

### Approach 3: Bubble Sort Style (Suboptimal)
**Algorithm**: Keep swapping zeros with next non-zero element
**Time Complexity**: O(n²) in worst case

## Detailed Walkthrough: Fast/Slow Pointers

### Example: nums = [0,1,0,3,12]

**The Intuition**:
- **Slow pointer** = "Where should I place the next non-zero number?"
- **Fast pointer** = "What number am I currently looking at?"

#### Step-by-Step Execution:

**Initial State:**
```
nums = [0, 1, 0, 3, 12]
slow = 0  (next position to place non-zero element)
fast = 0  (current element being examined)
```

**Iteration 1: fast = 0**
```
nums = [0, 1, 0, 3, 12]
        ↑
      slow/fast

nums[0] = 0 (is zero, skip it)
Action: fast++ only
New state: slow = 0, fast = 1
```

**Iteration 2: fast = 1**
```
nums = [0, 1, 0, 3, 12]
        ↑  ↑
      slow fast

nums[1] = 1 (non-zero!)
Action: nums[slow] = nums[fast], then slow++, fast++
Result: nums[0] = 1
New state: slow = 1, fast = 2

Array becomes: [1, 1, 0, 3, 12]
```

**Iteration 3: fast = 2**
```
nums = [1, 1, 0, 3, 12]
           ↑  ↑
         slow fast

nums[2] = 0 (is zero, skip it)
Action: fast++ only
New state: slow = 1, fast = 3
```

**Iteration 4: fast = 3**
```
nums = [1, 1, 0, 3, 12]
           ↑     ↑
         slow   fast

nums[3] = 3 (non-zero!)
Action: nums[slow] = nums[fast], then slow++, fast++
Result: nums[1] = 3
New state: slow = 2, fast = 4

Array becomes: [1, 3, 0, 3, 12]
```

**Iteration 5: fast = 4**
```
nums = [1, 3, 0, 3, 12]
              ↑     ↑
            slow   fast

nums[4] = 12 (non-zero!)
Action: nums[slow] = nums[fast], then slow++, fast++
Result: nums[2] = 12
New state: slow = 3, fast = 5

Array becomes: [1, 3, 12, 3, 12]
```

**Phase 2: Fill remaining with zeros**
```
fast = 5 (out of bounds, first phase done)
slow = 3 (positions 3 and 4 need to be filled with zeros)

Fill nums[3] = 0: [1, 3, 12, 0, 12]
Fill nums[4] = 0: [1, 3, 12, 0, 0]

Final result: [1, 3, 12, 0, 0]
```

## Why This Pattern is Powerful

### The "Partitioning" Mental Model
Think of the array as having three sections:
1. **[0...slow-1]**: Processed non-zero elements (final positions)
2. **[slow...fast-1]**: Elements we've seen but were zeros (to be overwritten)
3. **[fast...n-1]**: Elements we haven't processed yet

### Key Invariants
1. **All elements before `slow` are non-zero and in their final positions**
2. **All elements between `slow` and `fast` are zeros (that we've skipped)**
3. **`slow` always points to where the next non-zero element should go**

### Why It Maintains Order
- We process elements left to right (fast pointer)
- We place non-zero elements left to right (slow pointer)
- This naturally preserves the relative order of non-zero elements

## Alternative Implementation Styles

### Style 1: Two Separate Loops (Cleaner Logic)
```java
public void moveZeroes(int[] nums) {
    int writeIndex = 0;
    
    // First pass: move all non-zero elements to front
    for (int readIndex = 0; readIndex < nums.length; readIndex++) {
        if (nums[readIndex] != 0) {
            nums[writeIndex] = nums[readIndex];
            writeIndex++;
        }
    }
    
    // Second pass: fill remaining with zeros
    while (writeIndex < nums.length) {
        nums[writeIndex] = 0;
        writeIndex++;
    }
}
```

### Style 2: Single Loop with Swap (More Operations)
```java
public void moveZeroes(int[] nums) {
    int slow = 0;
    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            // Swap elements
            int temp = nums[slow];
            nums[slow] = nums[fast];
            nums[fast] = temp;
            slow++;
        }
    }
}
```

## Common Pitfalls and How to Avoid Them

### Pitfall 1: Forgetting to Fill Zeros
```java
// Wrong - doesn't fill remaining positions with zeros
for (int i = 0; i < nums.length; i++) {
    if (nums[i] != 0) {
        nums[slow++] = nums[i];
    }
}
// Missing: fill nums[slow] to nums[length-1] with zeros
```

### Pitfall 2: Not Understanding Pointer Roles
```java
// Wrong - moving both pointers always
for (int fast = 0; fast < nums.length; fast++) {
    if (nums[fast] != 0) {
        nums[slow] = nums[fast];
        slow++;
    }
    slow++; // BUG: slow should only move when we place an element
}
```

### Pitfall 3: Overcomplicating with Swaps When Not Needed
```java
// Unnecessary complexity
if (nums[fast] != 0) {
    int temp = nums[slow];
    nums[slow] = nums[fast];
    nums[fast] = temp; // We don't need to preserve what was at slow
    slow++;
}
```

## When to Use This Pattern

This fast/slow pointer pattern is perfect for:
- **In-place array modifications** where you need to remove/filter elements
- **Partitioning problems** (move elements satisfying condition to one side)
- **Compacting arrays** (removing gaps)

**Similar problems**: Remove Duplicates, Remove Element, Partition Array

## Key Takeaways

1. **Two pointers can have different speeds and purposes**
2. **Slow pointer tracks "next available position"**
3. **Fast pointer scans for "elements to process"**
4. **Pattern naturally maintains relative order**
5. **Think "compacting" rather than "moving"**

This problem is an excellent introduction to the fast/slow pointer technique because the logic is straightforward once you understand the roles of each pointer!