# Array Rotation Pattern - Cheat Sheet

## Quick Pattern Recognition
- **Keywords:** "rotate", "shift", "cyclic", "circular"
- **Input:** Array + number of positions to rotate
- **Output:** Modified array or find rotation properties

## Essential Formulas

### Right Rotation by k positions
```java
newIndex = (currentIndex + k) % n
```

### Left Rotation by k positions
```java
newIndex = (currentIndex - k + n) % n
```

### Always normalize k first
```java
k = k % n; // Handle k > array length
```

## Three Core Solutions

### 1. Extra Array (Beginner Friendly)
```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    int[] temp = new int[n];
    
    for (int i = 0; i < n; i++) {
        temp[(i + k) % n] = nums[i];
    }
    
    System.arraycopy(temp, 0, nums, 0, n);
}
```
**Time:** O(n) | **Space:** O(n)

### 2. Reverse Method (Interview Standard)
```java
public void rotate(int[] nums, int k) {
    k = k % nums.length;
    reverse(nums, 0, nums.length - 1);  // Reverse all
    reverse(nums, 0, k - 1);            // Reverse first k
    reverse(nums, k, nums.length - 1);  // Reverse rest
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
**Time:** O(n) | **Space:** O(1)

### 3. Cyclic Replacement (Advanced)
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
**Time:** O(n) | **Space:** O(1)

## Common Patterns & Variations

### Find Rotation Count
```java
public int findRotation(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n - 1; i++) {
        if (nums[i] > nums[i + 1]) {
            return i + 1;
        }
    }
    return 0; // Array not rotated
}
```

### Check if Array is Rotation of Another
```java
public boolean isRotation(int[] arr1, int[] arr2) {
    if (arr1.length != arr2.length) return false;
    
    String s1 = Arrays.toString(arr1);
    String s2 = Arrays.toString(arr2) + Arrays.toString(arr2);
    
    return s2.contains(s1);
}
```

### Rotate String (Same Concept)
```java
public boolean rotateString(String s, String goal) {
    return s.length() == goal.length() && 
           (s + s).contains(goal);
}
```

## Critical Edge Cases

```java
// Empty array
if (nums.length == 0) return;

// Single element
if (nums.length == 1) return;

// k is 0 or multiple of n
k = k % nums.length;
if (k == 0) return;

// k larger than array length
k = k % nums.length; // This handles it
```

## Test Cases to Verify

```java
// Basic case
nums = [1,2,3,4,5,6,7], k = 3 → [5,6,7,1,2,3,4]

// k > length
nums = [1,2,3], k = 4 → [3,1,2] (same as k=1)

// k = length (no change)
nums = [1,2,3], k = 3 → [1,2,3]

// Single element
nums = [-1], k = 2 → [-1]

// Two elements
nums = [1,2], k = 1 → [2,1]
```

## Memory Tricks

### Reverse Method Visualization
For right rotation by k:
1. **R**everse **A**ll → scrambles everything
2. **R**everse first **k** → puts last k in correct order
3. **R**everse **R**est → puts remaining in correct order

Example: `[1,2,3,4,5]` rotate right by 2
1. Reverse all: `[5,4,3,2,1]`
2. Reverse first 2: `[4,5,3,2,1]`
3. Reverse last 3: `[4,5,1,2,3]` ✓

### Index Formula Memory
- **Right rotation:** Move each element k positions to the **right**
- **Formula:** `(i + k) % n` (add k, wrap around)
- **Left rotation:** Move each element k positions to the **left**
- **Formula:** `(i - k + n) % n` (subtract k, handle negatives)

## Quick Debugging

```java
// Add this to verify your solution
private void printArray(int[] nums, String label) {
    System.out.println(label + ": " + Arrays.toString(nums));
}

// Use like this:
printArray(nums, "Before rotation");
rotate(nums, k);
printArray(nums, "After rotation");
```

## Interview Strategy

1. **Clarify** direction (left vs right) and edge cases
2. **Start** with extra array approach (shows understanding)
3. **Optimize** to reverse method (shows advanced knowledge)
4. **Test** with provided examples and edge cases
5. **Discuss** time/space trade-offs

## Related LeetCode Problems

- **189. Rotate Array** (this problem)
- **61. Rotate List** (linked list version)
- **33. Search in Rotated Sorted Array**
- **153. Find Minimum in Rotated Sorted Array**
- **796. Rotate String**