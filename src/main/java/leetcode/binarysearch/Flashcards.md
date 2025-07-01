# Binary Search Pattern - Flashcards

## Core Concepts

**Q: What is binary search and when should you use it?**
A: Binary search is an algorithm that finds a target value in a **sorted space** by repeatedly dividing the search interval in half. Use it when:
- Array/space is sorted or has monotonic property
- Need O(log n) time complexity
- Looking for minimum/maximum value that satisfies a condition
- Can eliminate half the search space each iteration

**Q: What are the three main types of binary search problems?**
A:
1. **Classic Search**: Find exact element in sorted array
2. **Answer Search**: Find minimum/maximum valid value in range
3. **Peak Finding**: Find peak element using comparison with neighbors

**Q: What is the key insight that makes binary search work?**
A: **Elimination principle** - each comparison allows us to eliminate half of the remaining search space because we can determine which half cannot contain the answer.

## Pattern Recognition

**Q: How do you recognize a binary search problem?**
A: Look for these keywords and patterns:
- "Find minimum/maximum value such that..."
- "First/last position where condition is true"
- "Can we achieve X within Y constraint?"
- Array is sorted or answer space has monotonic property
- Need better than O(n) time complexity

**Q: What is "binary search on answer" and when do you use it?**
A: It's when you binary search over the **answer space** rather than array indices. Use when:
- Problem asks for minimum/maximum valid value
- You can define a validation function with monotonic behavior
- Answer range can be determined (min_possible to max_possible)

**Q: What does "monotonic property" mean in binary search context?**
A: If a condition is true for value X, then it's true for all values greater than X (or less than X, depending on problem). This creates a "sorted" decision space: `[false, false, false, TRUE, TRUE, TRUE]`

## Templates

**Q: What's the difference between `left <= right` and `left < right` loop conditions?**
A:
- **`left <= right`**: Used for exact match problems, returns -1 if not found
- **`left < right`**: Used for min/max valid answer, guarantees finding an answer

**Q: When do you use ceiling division for mid calculation?**
A: Use `mid = left + (right - left + 1) / 2` when finding **maximum valid** answer. This prevents infinite loops when `left = mid` assignment is used.

**Q: How do you avoid integer overflow in binary search?**
A: Use `mid = left + (right - left) / 2` instead of `mid = (left + right) / 2`

**Q: What's the standard template for finding minimum valid answer?**
A:
```java
while (left < right) {
    int mid = left + (right - left) / 2;
    if (isValid(mid)) {
        right = mid; // mid might be answer
    } else {
        left = mid + 1; // mid definitely not answer
    }
}
return left;
```

## Problem-Specific Applications

**Q: In Koko Eating Bananas, what is the search space and validation function?**
A:
- **Search space**: [1, max(piles)] - possible eating speeds
- **Validation function**: `canFinish(speed, hours)` - can Koko finish all bananas within given hours at this speed?
- **Monotonic property**: Higher speed → can finish faster

**Q: How do you handle ceiling division without floating point arithmetic?**
A: Use `(a + b - 1) / b` instead of `Math.ceil((double)a / b)`. For Koko: `(pile + speed - 1) / speed`

**Q: In peak finding problems, how do you decide which direction to search?**
A: Compare `nums[mid]` with `nums[mid + 1]`:
- If `nums[mid] > nums[mid + 1]` → peak is on left side (including mid)
- If `nums[mid] < nums[mid + 1]` → peak is on right side

## Edge Cases & Debugging

**Q: What are the most common binary search bugs and how do you fix them?**
A:
1. **Infinite loop**: Use correct mid calculation for template
2. **Off-by-one errors**: Carefully choose `mid`, `mid+1`, or `mid-1` for boundaries
3. **Integer overflow**: Use `left + (right - left) / 2`
4. **Wrong template**: Match template to problem type (exact vs min/max)

**Q: How do you test your binary search implementation?**
A:
1. Test with empty array or impossible case
2. Test with single element
3. Test when target is at boundaries (first/last position)
4. Test when target doesn't exist
5. Trace through a small example manually

**Q: What boundary conditions should you always check?**
A:
- Array length 0 or 1
- Target at first or last position
- Target doesn't exist in array
- All elements are the same

## Time & Space Complexity

**Q: What's the time complexity of binary search variants?**
A:
- **Classic binary search**: O(log n)
- **Answer search**: O(log(range) × validation_time)
- **Peak finding**: O(log n)
- **Search in rotated array**: O(log n)

**Q: What's the space complexity difference between iterative and recursive binary search?**
A:
- **Iterative**: O(1) space
- **Recursive**: O(log n) space due to call stack

## Advanced Applications

**Q: How do you handle duplicates in binary search?**
A: Use modified templates:
- **First occurrence**: When found, continue searching left (`right = mid - 1`)
- **Last occurrence**: When found, continue searching right (`left = mid + 1`)

**Q: How does binary search work on rotated sorted arrays?**
A:
1. Find which half is properly sorted using `nums[left] <= nums[mid]`
2. Check if target is in the sorted half
3. Search the appropriate half

**Q: What's the approach for "find closest element" problems?**
A: Use classic binary search, then check the elements at positions where search ended to find the closest one.

## Problem-Solving Strategy

**Q: What are the key steps to solve any binary search problem?**
A:
1. **Identify the pattern**: Direct search vs answer search vs peak finding
2. **Define search space**: Array indices vs answer range
3. **Choose template**: Based on problem requirements
4. **Implement validation**: For answer search problems
5. **Handle edge cases**: Empty input, single element, boundaries

**Q: How do you design a validation function for answer search?**
A:
1. Function should return boolean: "Is this answer valid?"
2. Should have monotonic property
3. Should be efficient (affects overall time complexity)
4. Test with boundary values to ensure correctness

**Q: What's the mental model for binary search?**
A: Think of it as **intelligent elimination**:
- Each comparison eliminates half the possibilities
- Search space continuously shrinks
- The answer (if exists) is always within current [left, right] bounds
- Stop when search space cannot be divided further

## Common Patterns Summary

**Q: What are the key binary search patterns in LeetCode?**
A:
1. **Find in sorted array** (374, 704)
2. **Answer search - minimum** (875 Koko, 1011 Ship packages)
3. **Answer search - maximum** (1482 Bouquets)
4. **Peak finding** (162 Find peak)
5. **Search with modifications** (33 Rotated array, 2300 Spells & potions)