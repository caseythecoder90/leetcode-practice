# Binary Search Pattern

## Overview

Binary search is a fundamental algorithm that efficiently searches sorted data by repeatedly dividing the search space in half. This pattern extends beyond just finding elements in sorted arrays to solving optimization problems where we search for the "best" answer in a monotonic space.

## Key Techniques

- Classic binary search on sorted arrays
- Binary search on answer (finding minimum/maximum valid value)
- Peak finding in mountain arrays
- Search in rotated sorted arrays
- Template-based implementation to avoid bugs

## When to Use Binary Search

### Direct Binary Search
- Array is already sorted
- Need to find specific element or insertion position
- Time complexity must be O(log n)

### Binary Search on Answer
- Problem asks for "minimum value that satisfies condition X"
- Problem asks for "maximum value that satisfies condition Y"
- Can define a validation function that has monotonic behavior
- Answer space forms a sorted range

### Pattern Recognition Keywords
- "Find minimum/maximum..."
- "Smallest/largest value such that..."
- "Can we achieve X within Y constraint?"
- "First/last position where..."

## Problem Categories

### 1. Classic Search (Find Element)
- **374. Guess Number Higher or Lower** - Basic binary search
- **704. Binary Search** - Standard implementation
- **35. Search Insert Position** - Handle element not found

### 2. Search on Answer (Optimization)
- **875. Koko Eating Bananas** - Find minimum eating speed
- **1011. Capacity To Ship Packages** - Find minimum ship capacity
- **410. Split Array Largest Sum** - Minimize maximum subarray sum

### 3. Peak Finding
- **162. Find Peak Element** - Find any peak in array
- **852. Peak Index in Mountain Array** - Find peak in mountain

### 4. Complex Variations
- **33. Search in Rotated Sorted Array** - Handle rotation
- **2300. Successful Pairs of Spells and Potions** - Count valid pairs

## Common Time/Space Complexities

- **Time**: O(log n) for search space of size n
- **Space**: O(1) for iterative, O(log n) for recursive
- **Answer search**: O(log(answer_range) × validation_time)

## Core Insights

### Monotonic Property
The key to binary search problems is identifying **monotonic behavior**:
- If condition is true for value X, it's true for all values > X (or < X)
- This creates a "sorted" decision space we can binary search

### Template Selection
Choose the right template based on problem type:
- **Find exact match**: Use `left <= right` loop
- **Find minimum valid**: Use `left < right` with `right = mid`
- **Find maximum valid**: Use `left < right` with `left = mid`

### Avoiding Infinite Loops
- Always ensure search space shrinks
- Use correct mid calculation for template
- Be careful with boundary updates

## Learning Strategy

### Beginner Level
1. Master classic binary search on sorted arrays
2. Understand why binary search works (elimination principle)
3. Practice boundary conditions and edge cases

### Intermediate Level
4. Learn "binary search on answer" pattern
5. Practice defining validation functions
6. Solve optimization problems (minimize/maximize)

### Advanced Level
7. Handle complex scenarios (rotated arrays, peaks)
8. Combine binary search with other algorithms
9. Optimize validation functions for better time complexity

## Common Pitfalls

### 1. Infinite Loops
**Problem**: Loop never terminates
**Solution**: Ensure search space always shrinks, use correct mid calculation

### 2. Off-by-One Errors
**Problem**: Missing answer by one position
**Solution**: Carefully choose boundary updates (`mid`, `mid+1`, `mid-1`)

### 3. Integer Overflow
**Problem**: `(left + right) / 2` overflows
**Solution**: Use `left + (right - left) / 2`

### 4. Wrong Template
**Problem**: Using exact match template for min/max problems
**Solution**: Match template to problem requirements

## Resources for Further Learning

### Essential Problems to Master
1. **704. Binary Search** - Learn the foundation
2. **35. Search Insert Position** - Handle not found cases
3. **875. Koko Eating Bananas** - Master answer search pattern
4. **162. Find Peak Element** - Understand peak finding
5. **33. Search in Rotated Sorted Array** - Handle edge cases

### LeetCode 75 Binary Search Problems
- **374. Guess Number Higher or Lower** (Easy)
- **2300. Successful Pairs of Spells and Potions** (Medium)
- **162. Find Peak Element** (Medium)
- **875. Koko Eating Bananas** (Medium)

### Practice Strategy
1. Start with sorted array problems to build intuition
2. Move to answer search problems (optimization)
3. Practice template selection and boundary handling
4. Time yourself - binary search should be quick to implement

---

## Directory Structure

```
binarysearch/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── problems/
│   ├── KokoEatingBananas/
│   ├── GuessNumberHigherOrLower/
│   ├── FindPeakElement/
│   └── SuccessfulPairsOfSpellsAndPotions/
└── common/
    └── BinarySearchTemplates.java
```

Start with classic binary search problems to master the fundamentals, then progress to answer search for optimization problems!