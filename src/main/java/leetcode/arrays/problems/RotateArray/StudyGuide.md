# Array Rotation Pattern - Study Guide

## Pattern Overview

Array rotation problems involve shifting elements in an array by a certain number of positions, either to the left or right. This is a fundamental pattern that appears frequently in interviews.

## Problem Types

### 1. **Right Rotation (Most Common)**
- Rotate array to the right by k steps
- Example: `[1,2,3,4,5]` rotated right by 2 → `[4,5,1,2,3]`

### 2. **Left Rotation**
- Rotate array to the left by k steps
- Example: `[1,2,3,4,5]` rotated left by 2 → `[3,4,5,1,2]`

### 3. **Find Rotation Point**
- Find how many times array was rotated
- Example: `[4,5,6,7,0,1,2]` was rotated 4 times from `[0,1,2,4,5,6,7]`

## Key Insights

### 1. **Modulo Operation is Essential**
```java
k = k % n; // Handle cases where k > array length
```
**Why?** Rotating by array length brings us back to original position.

### 2. **Right vs Left Rotation Relationship**
- Right rotation by k = Left rotation by (n - k)
- This can simplify some problems

### 3. **Index Mapping Formula**
For right rotation by k positions:
```java
newIndex = (currentIndex + k) % n
```

For left rotation by k positions:
```java
newIndex = (currentIndex - k + n) % n
```

## Solution Approaches

### Approach 1: Extra Array (Easiest to Understand)
**Time:** O(n) | **Space:** O(n)

```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    int[] result = new int[n];
    
    for (int i = 0; i < n; i++) {
        result[(i + k) % n] = nums[i];
    }
    
    System.arraycopy(result, 0, nums, 0, n);
}
```

**Pros:** Easy to understand and implement
**Cons:** Uses extra space

### Approach 2: Reverse Method (Most Elegant)
**Time:** O(n) | **Space:** O(1)

```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    
    reverse(nums, 0, n - 1);     // Reverse entire array
    reverse(nums, 0, k - 1);     // Reverse first k elements
    reverse(nums, k, n - 1);     // Reverse remaining elements
}
```

**How it works:**
1. `[1,2,3,4,5,6,7]` rotate right by 3
2. Reverse all: `[7,6,5,4,3,2,1]`
3. Reverse first 3: `[5,6,7,4,3,2,1]`
4. Reverse last 4: `[5,6,7,1,2,3,4]` ✓

**Pros:** O(1) space, elegant algorithm
**Cons:** Requires understanding of the reverse trick

### Approach 3: Cyclic Replacements (Most Complex)
**Time:** O(n) | **Space:** O(1)

```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    int count = 0;
    
    for (int start = 0; count < n; start++) {
        int current = start;
        int prev = nums[start];
        
        do {
            int next = (current + k) % n;
            int temp = nums[next];
            nums[next] = prev;
            prev = temp;
            current = next;
            count++;
        } while (start != current);
    }
}
```

**Pros:** O(1) space, each element touched exactly once
**Cons:** Complex logic, harder to debug

## Common Mistakes & Edge Cases

### 1. **Forgetting Modulo Operation**
```java
// Wrong
int newIndex = i + k;

// Correct
int newIndex = (i + k) % n;
```

### 2. **Not Handling k > n**
```java
// Always do this first
k = k % n;
```

### 3. **Off-by-One Errors in Index Calculation**
```java
// Wrong: index = index - iMax - 1;
// Correct: index = (i + k) % n;
```

### 4. **Edge Cases to Test**
- Empty array: `[]`
- Single element: `[1]`
- k = 0: No rotation
- k = n: Full rotation (back to original)
- k > n: Multiple full rotations

## Problem Variations

### 1. **189. Rotate Array**
Classic right rotation problem

### 2. **61. Rotate List**
Same concept but with linked lists

### 3. **33. Search in Rotated Sorted Array**
Find element in rotated sorted array

### 4. **153. Find Minimum in Rotated Sorted Array**
Find the rotation point

### 5. **396. Rotate Function**
Calculate rotation function values

## Practice Strategy

### Step 1: Master the Basic Pattern
Start with the extra array approach for "189. Rotate Array"

### Step 2: Learn the Reverse Method
This is the most interview-friendly solution

### Step 3: Understand Edge Cases
Focus on k % n and boundary conditions

### Step 4: Apply to Variations
Try linked list rotation and rotated sorted array problems

## Interview Tips

### 1. **Start with Brute Force**
Always mention the extra array approach first

### 2. **Optimize to O(1) Space**
Show the reverse method as optimization

### 3. **Handle Edge Cases**
Always discuss k % n and empty arrays

### 4. **Trace Through Examples**
Walk through small examples step by step

### 5. **Test Your Solution**
Use the failing test case: `nums = [-1], k = 2`

## Debugging Your Original Solution

Your original logic was close! The issue was in this line:
```java
index = index - iMax - 1; // Wrong
```

Should be:
```java
index = (i + k) % n; // Correct
```

**Why modulo works:**
- For `nums = [-1], k = 2, n = 1`
- `index = (0 + 2) % 1 = 0` ✓
- This correctly maps element at position 0 to position 0

## Key Takeaways

1. **Always use modulo** for cyclic behavior
2. **The reverse method** is the most elegant O(1) space solution
3. **Index mapping formula** is `(currentIndex + k) % n` for right rotation
4. **Edge case handling** is crucial (k % n, empty arrays)
5. **Multiple approaches** show deep understanding in interviews

Remember: Array rotation is fundamentally about **cyclic shifting** - elements that "fall off" one end reappear at the other end!