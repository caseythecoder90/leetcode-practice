# Reverse Method for Array Rotation - Complete Tutorial

## The Big Picture: Why Reverse Works

### The Bookshelf Analogy (The "Aha!" Moment)

Imagine you have 5 books on a shelf:
```
[Math] [Science] [History] [Art] [Music]
```

You want to rearrange them to put the last 2 books at the front:
```
[Art] [Music] [Math] [Science] [History]
```

**The Reverse Method Approach:**

1. **Flip the entire shelf upside down** → All books are now backwards:
   ```
   [Music] [Art] [History] [Science] [Math] (all upside down)
   ```
   **Key insight:** The books we want at front (`Art`, `Music`) ARE now at front!

2. **Flip the first 2 books right-side up:**
   ```
   [Art] [Music] [History] [Science] [Math] (last 3 still upside down)
   ```
   **Progress:** Our target books are now in correct position AND orientation!

3. **Flip the remaining 3 books right-side up:**
   ```
   [Art] [Music] [Math] [Science] [History] ✓
   ```
   **Success:** Perfect arrangement achieved!

### Why This Works

- **"Flipping upside down"** brings the target items to the front
- **"Fixing orientation"** puts everything in the right order
- **Three simple operations** accomplish a complex rearrangement

**Same concept works with arrays - just replace "flip" with "reverse"!**

## Step 1: Build the Reverse Helper Function

Let's start with the foundation - a function to reverse any portion of an array:

```java
private void reverse(int[] nums, int start, int end) {
    while (start < end) {
        // Swap elements at start and end
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        
        // Move pointers toward center
        start++;
        end--;
    }
}
```

### Test the Helper Function

Let's verify this works:
```java
int[] test = {1, 2, 3, 4, 5};
reverse(test, 0, 4);  // Reverse entire array
// Result: [5, 4, 3, 2, 1] ✓

int[] test2 = {1, 2, 3, 4, 5};  
reverse(test2, 1, 3);  // Reverse middle portion (indices 1-3)
// Result: [1, 4, 3, 2, 5] ✓
```

## Step 2: Understand the Three-Reverse Pattern

For right rotation by k positions, we need to think of the array as two parts:

**Original:** `[---- Part A ----][-- Part B --]`  
**After Rotation:** `[-- Part B --][---- Part A ----]`

**Example:** `[1,2,3,4,5,6,7]` rotate right by 3
- **Part A:** `[1,2,3,4]` (first n-k = 4 elements)
- **Part B:** `[5,6,7]` (last k = 3 elements)
- **Goal:** `[5,6,7,1,2,3,4]` (Part B + Part A)

## Step 3: The Three Reversals

### Reversal 1: Reverse the Entire Array
**Purpose:** This scrambles everything but puts Part B at the beginning (in reverse order)

```java
reverse(nums, 0, nums.length - 1);
```

**Example:** `[1,2,3,4,5,6,7]` → `[7,6,5,4,3,2,1]`

Notice: `[7,6,5]` are the elements that should be at the front (but reversed)

### Reversal 2: Fix Part B (First k Elements)
**Purpose:** Put the first k elements in correct order

```java
reverse(nums, 0, k - 1);
```

**Example:** `[7,6,5,4,3,2,1]` → `[5,6,7,4,3,2,1]`

Notice: Now `[5,6,7]` is correct at the beginning!

### Reversal 3: Fix Part A (Remaining Elements)
**Purpose:** Put the remaining elements in correct order

```java
reverse(nums, k, nums.length - 1);
```

**Example:** `[5,6,7,4,3,2,1]` → `[5,6,7,1,2,3,4]` ✓

Perfect! We've achieved the rotation.

## Step 4: Complete Implementation

```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;  // Handle k > n
    
    // Three reversals
    reverse(nums, 0, n - 1);      // Reverse entire array
    reverse(nums, 0, k - 1);      // Reverse first k elements
    reverse(nums, k, n - 1);      // Reverse remaining elements
}

private void reverse(int[] nums, int start, int end) {
    while (start < end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}
```

## Step 5: Detailed Walkthrough with Different Example

Let's trace `nums = [1,2,3,4,5]`, `k = 2`:

**Expected Result:** `[4,5,1,2,3]`

### Initial State
```
nums = [1,2,3,4,5]
n = 5, k = 2
```

### Reversal 1: Reverse Entire Array
```java
reverse(nums, 0, 4);
```

**Trace:**
- `start=0, end=4`: swap `nums[0]=1` with `nums[4]=5` → `[5,2,3,4,1]`
- `start=1, end=3`: swap `nums[1]=2` with `nums[3]=4` → `[5,4,3,2,1]`
- `start=2, end=2`: `start >= end`, stop

**Result:** `[5,4,3,2,1]`

### Reversal 2: Reverse First k=2 Elements
```java
reverse(nums, 0, 1);
```

**Trace:**
- `start=0, end=1`: swap `nums[0]=5` with `nums[1]=4` → `[4,5,3,2,1]`
- `start=1, end=0`: `start >= end`, stop

**Result:** `[4,5,3,2,1]`

### Reversal 3: Reverse Remaining Elements (from k to end)
```java
reverse(nums, 2, 4);
```

**Trace:**
- `start=2, end=4`: swap `nums[2]=3` with `nums[4]=1` → `[4,5,1,2,3]`
- `start=3, end=3`: `start >= end`, stop

**Final Result:** `[4,5,1,2,3]` ✓

## Step 6: Handle Edge Cases

```java
public void rotate(int[] nums, int k) {
    // Handle edge cases
    if (nums == null || nums.length <= 1) return;
    
    int n = nums.length;
    k = k % n;  // Essential for k > n
    
    // If k is 0, no rotation needed
    if (k == 0) return;
    
    // Three reversals
    reverse(nums, 0, n - 1);
    reverse(nums, 0, k - 1);  
    reverse(nums, k, n - 1);
}
```

## Step 7: Visual Memory Aid

### The Bookshelf Memory Trick

Always remember the **"Bookshelf Rearrangement"**:

1. **Flip entire shelf upside down** → Brings target books to front (but upside down)
2. **Fix the front books** → Gets the moved books right-side up
3. **Fix the remaining books** → Gets the rest right-side up

**Array Pattern:**
```
Original:     [1 2 3 4 | 5 6 7]  (want to move last 3 to front)
Step 1:       [7 6 5 4 3 2 1]    ("flip shelf upside down")
Step 2:       [5 6 7 | 4 3 2 1]  ("fix first 3 books")
Step 3:       [5 6 7 | 1 2 3 4]  ("fix remaining books") ✓
```

**Why it sticks:** You can physically visualize rearranging books on a shelf!

## Step 8: Practice Problems

Try these by hand to solidify understanding:

1. `[1,2,3]`, `k=1` → should get `[3,1,2]`
2. `[1,2]`, `k=1` → should get `[2,1]`
3. `[1,2,3,4,5,6]`, `k=4` → should get `[3,4,5,6,1,2]`

## Why This Method is Interview Gold

### ✅ **Advantages:**
- **O(1) space complexity** (no extra arrays)
- **Easy to explain** (three simple steps)
- **Hard to mess up** (reverse function is straightforward)
- **Shows algorithmic thinking** (transform complex problem into simple operations)
- **Widely applicable** (reverse technique used in many problems)

### 🎯 **Interview Impact:**
- **Demonstrates optimization skills** (space complexity improvement)
- **Shows pattern recognition** (seeing rotation as partition + reverse)
- **Easy to code correctly** under pressure
- **Impresses interviewers** with elegant solution

## Common Mistakes to Avoid

1. **Forgetting `k = k % n`** → fails when k > array length
2. **Wrong boundary indices** → `reverse(nums, 0, k)` instead of `reverse(nums, 0, k-1)`
3. **Not handling k=0 case** → unnecessary operations
4. **Off-by-one in reverse function** → using `<=` instead of `<`

## Final Implementation with Comments

```java
public void rotate(int[] nums, int k) {
    if (nums == null || nums.length <= 1) return;
    
    int n = nums.length;
    k = k % n;  // Handle k > n case
    if (k == 0) return;  // No rotation needed
    
    // Three-step reverse method
    reverse(nums, 0, n - 1);      // 1. Reverse entire array
    reverse(nums, 0, k - 1);      // 2. Reverse first k elements  
    reverse(nums, k, n - 1);      // 3. Reverse remaining elements
}

private void reverse(int[] nums, int start, int end) {
    while (start < end) {
        // Swap elements and move toward center
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}
```

**Congratulations!** You now have one of the most elegant array manipulation techniques in your toolbox. This reverse method will serve you well in many different problems beyond just rotation!