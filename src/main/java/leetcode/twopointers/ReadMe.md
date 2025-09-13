# Two Pointers Pattern

## Overview

The Two Pointers technique is a powerful algorithmic approach that uses two pointers to traverse data structures, typically arrays or strings. This pattern is particularly effective for solving problems involving pairs, subarrays, or when you need to compare elements from different positions.

## When to Use Two Pointers

**Strong indicators that Two Pointers might be the solution:**
- Problems involving **pairs** of elements that meet certain criteria
- **Sorted arrays** where you need to find combinations
- Problems asking for **target sums** or **differences**
- **Palindrome** detection or manipulation
- **Removing duplicates** or **moving elements**
- **Sliding window** variations where window size changes dynamically
- Problems with **opposite direction** traversal requirements

## Core Two Pointers Patterns

### Pattern 1: Opposite Direction (Converging)
**Use case**: Finding pairs in sorted arrays, palindrome detection
```
left = 0, right = n-1
while (left < right) {
    // Process arr[left] and arr[right]
    // Move pointers based on condition
}
```

### Pattern 2: Same Direction (Fast & Slow)
**Use case**: Removing duplicates, cycle detection, finding middle element
```
slow = 0
for (fast = 0; fast < n; fast++) {
    // Process elements
    // Move slow conditionally
}
```

### Pattern 3: Sliding Window Variable Size
**Use case**: Subarray problems with dynamic window size
```
left = 0
for (right = 0; right < n; right++) {
    // Expand window by including arr[right]
    while (/* shrink condition */) {
        // Shrink window by excluding arr[left]
        left++;
    }
}
```

## Key Techniques and Tips

### 1. **Start with Sorted Data**
Many two-pointer problems become much easier when the array is sorted. Don't hesitate to sort first if the problem allows it.

### 2. **Choose the Right Pattern**
- **Converging pointers**: For finding pairs with target sum/difference
- **Fast/slow pointers**: For in-place modifications or cycle detection
- **Variable sliding window**: For subarray problems with conditions

### 3. **Handle Edge Cases**
- Empty arrays or single element arrays
- All elements the same
- Target not achievable
- Duplicate elements

### 4. **Pointer Movement Strategy**
- **Both pointers move**: In palindrome checking
- **Conditional movement**: Move left if sum too small, right if too large
- **One pointer faster**: In cycle detection or duplicate removal

## Time and Space Complexity

**Time Complexity**: Typically O(n) for single pass, O(n log n) if sorting required
**Space Complexity**: Usually O(1) - constant extra space

This makes Two Pointers an excellent technique for optimizing brute force O(n²) solutions down to O(n).

## Common Problem Types

### 1. **Target Sum Problems**
- Two Sum (sorted array)
- Three Sum
- K-Sum Pairs
- Closest Sum to Target

### 2. **Palindrome Problems**
- Valid Palindrome
- Palindrome construction
- Longest Palindromic Substring

### 3. **Array Manipulation**
- Remove Duplicates
- Move Zeros
- Reverse Array
- Partition Array

### 4. **Subarray Problems**
- Longest Subarray with condition
- Minimum Window Substring
- Container with Most Water

## Problem-Solving Framework

### Step 1: Identify the Pattern
Ask yourself:
- Do I need to find pairs or combinations?
- Is the array sorted or can I sort it?
- Am I looking for a specific sum or condition?
- Do I need to modify the array in place?

### Step 2: Choose Pointer Strategy
- **Converging**: For sorted arrays and target finding
- **Fast/Slow**: For in-place modifications
- **Sliding Window**: For subarray conditions

### Step 3: Define Movement Logic
- When do I move the left pointer?
- When do I move the right pointer?
- When do I move both?
- What are my termination conditions?

### Step 4: Handle Edge Cases
- What if pointers meet?
- What about duplicate values?
- How do I handle empty results?

## Advantages of Two Pointers

1. **Space Efficient**: Usually O(1) extra space
2. **Time Efficient**: Often converts O(n²) to O(n)
3. **Clean Code**: Intuitive and readable solutions
4. **Versatile**: Works for many different problem types
5. **Interview Friendly**: Easy to explain and implement

## Practice Progression

### Beginner Level
1. **Two Sum (Sorted Array)** - Learn basic converging pattern
2. **Valid Palindrome** - Practice string manipulation with pointers
3. **Remove Duplicates** - Master fast/slow pointer technique

### Intermediate Level
1. **Three Sum** - Combine two pointers with iteration
2. **Container with Most Water** - Optimization with two pointers
3. **Longest Substring Without Repeating Characters** - Variable sliding window

### Advanced Level
1. **Minimum Window Substring** - Complex sliding window
2. **Trapping Rain Water** - Advanced pointer movement logic
3. **Palindromic Substrings** - Complex palindrome detection

## Resources for Further Learning

### Essential Problems to Master
1. **Two Sum II (Sorted Array)** - Foundation of converging pointers
2. **Move Zeroes** - Basic in-place manipulation
3. **Container With Most Water** - Optimization insight
4. **3Sum** - Combining patterns
5. **Max Number of K-Sum Pairs** - Practical application

### Key Insights to Remember
- Two pointers often eliminate the need for nested loops
- Sorting can transform a complex problem into a simple two-pointer solution
- The key is identifying WHEN to move which pointer
- Practice visualizing pointer movement on paper first

---

## Directory Structure

```
twopointers/
├── README.md (this file)
├── CheatSheet.md
├── problems/
│   ├── ContainerWithMostWater/
│   ├── MaxNumberOfKSumPairs/
│   ├── MoveZeroes/
│   ├── TwoSumSorted/
│   └── ValidPalindrome/
└── common/
    └── TwoPointerUtils.java
```

Start with basic problems to build intuition, then progress to more complex applications!