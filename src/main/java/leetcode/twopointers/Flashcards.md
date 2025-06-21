# Two Pointers Pattern - Flashcards

## Core Concepts

**Q: What is the Two Pointers technique and when should you use it?**
A: Two Pointers is an algorithmic approach that uses two pointers to traverse data structures, typically arrays or strings. Use it when you have:
- Problems involving **pairs** of elements that meet certain criteria
- **Sorted arrays** where you need to find combinations
- **Target sums** or **differences** problems
- **Palindrome** detection or manipulation
- **In-place array modifications**
- **Sliding window** variations with dynamic window size

**Q: What are the three main Two Pointers patterns?**
A:
1. **Opposite Direction (Converging)**: `left = 0, right = n-1`, move towards each other
2. **Same Direction (Fast & Slow)**: Both pointers move in same direction at different speeds
3. **Sliding Window Variable Size**: Left pointer shrinks window, right pointer expands

## Pattern Implementation

**Q: How do you implement the Opposite Direction (Converging) pattern?**
A:
```java
left = 0, right = n-1
while (left < right) {
    // Process arr[left] and arr[right]
    if (condition) {
        // Move pointers based on condition
        left++ or right--
    }
}
```
**Use case**: Finding pairs in sorted arrays, palindrome detection

**Q: How do you implement the Same Direction (Fast & Slow) pattern?**
A:
```java
slow = 0
for (fast = 0; fast < n; fast++) {
    // Process elements
    if (condition) {
        arr[slow] = arr[fast];
        slow++;
    }
}
```
**Use case**: Removing duplicates, cycle detection, finding middle element

**Q: How do you implement the Sliding Window Variable Size pattern?**
A:
```java
left = 0
for (right = 0; right < n; right++) {
    // Expand window by including arr[right]
    while (/* shrink condition */) {
        // Shrink window by excluding arr[left]
        left++;
    }
    // Process current window
}
```
**Use case**: Subarray problems with dynamic window size

## Problem Categories

**Q: What are the four main categories of Two Pointers problems?**
A:
1. **Target Sum Problems**: Two Sum (sorted), Three Sum, K-Sum pairs, closest sum to target
2. **Palindrome Problems**: Valid palindrome, palindrome construction, longest palindromic substring
3. **Array Manipulation**: Remove duplicates, move zeros, reverse array, partition array
4. **Subarray Problems**: Longest subarray with condition, minimum window substring, container with most water

## Choosing the Right Pattern

**Q: How do you decide which Two Pointers pattern to use?**
A:
- **Converging pointers**: For sorted arrays and target finding problems
- **Fast/slow pointers**: For in-place modifications or cycle detection
- **Sliding window**: For subarray conditions and dynamic window size

**Q: What questions should you ask to identify Two Pointers problems?**
A:
1. Do I need to find pairs or combinations?
2. Is the array sorted or can I sort it?
3. Am I looking for a specific sum or condition?
4. Do I need to modify the array in place?
5. Am I dealing with subarrays or subsequences?

## Key Techniques

**Q: What are the pointer movement strategies?**
A:
- **Both pointers move**: In palindrome checking (`left++, right--`)
- **Conditional movement**: Move left if sum too small, right if too large
- **One pointer faster**: In cycle detection or duplicate removal
- **Window expansion/contraction**: Right expands, left contracts based on condition

**Q: When should you sort the array first?**
A: Sort when:
- The problem allows modification of input order
- You need to find pairs/triplets with specific sums
- Sorting can eliminate need for nested loops
- The problem becomes much simpler with sorted data

## Time and Space Complexity

**Q: What are the typical time and space complexities for Two Pointers?**
A:
- **Time Complexity**: O(n) for single pass, O(n log n) if sorting required
- **Space Complexity**: Usually O(1) - constant extra space
- **Advantage**: Often converts O(n²) brute force solutions to O(n)

## Common Pitfalls

**Q: What are common mistakes when using Two Pointers?**
A:
1. **Pointer bounds**: Ensure `left < right` to avoid infinite loops
2. **Duplicate handling**: Not properly handling duplicate elements
3. **Movement logic**: Unclear when to move which pointer
4. **Edge cases**: Empty arrays, single elements, all elements same
5. **Target not found**: Not handling case when target is not achievable

## Problem-Solving Framework

**Q: What's the step-by-step approach to solve Two Pointers problems?**
A:
1. **Identify the Pattern**: Determine if it's converging, fast/slow, or sliding window
2. **Choose Pointer Strategy**: Based on problem requirements
3. **Define Movement Logic**: When to move left, right, or both pointers
4. **Handle Edge Cases**: Empty arrays, single elements, no solution cases
5. **Optimize**: Consider if sorting helps simplify the problem

## Advanced Techniques

**Q: How do you handle duplicates in Two Pointers problems?**
A:
```java
// Skip duplicates on left
while (left < right && arr[left] == arr[left + 1]) left++;
// Skip duplicates on right  
while (left < right && arr[right] == arr[right - 1]) right--;
```

**Q: What's the pattern for Three Sum using Two Pointers?**
A:
```java
Arrays.sort(nums);
for (int i = 0; i < nums.length - 2; i++) {
    if (i > 0 && nums[i] == nums[i-1]) continue; // skip duplicates
    int left = i + 1, right = nums.length - 1;
    while (left < right) {
        int sum = nums[i] + nums[left] + nums[right];
        if (sum == target) {
            // Found triplet
            left++; right--;
            // Skip duplicates
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
}
```

## Essential Problems

**Q: What are the must-know Two Pointers problems for each difficulty level?**
A:
**Beginner**: 
- Two Sum (Sorted Array) - Learn basic converging pattern
- Valid Palindrome - Practice string manipulation
- Remove Duplicates - Master fast/slow technique

**Intermediate**:
- Three Sum - Combine two pointers with iteration
- Container with Most Water - Optimization insight
- Longest Substring Without Repeating Characters - Variable sliding window

**Advanced**:
- Minimum Window Substring - Complex sliding window
- Trapping Rain Water - Advanced pointer movement logic
- Palindromic Substrings - Complex palindrome detection

**Q: Why is Two Pointers often better than nested loops?**
A: Two Pointers eliminates the need for nested loops by:
- Using sorted data properties to make intelligent pointer movements
- Reducing time complexity from O(n²) to O(n)
- Maintaining constant space complexity O(1)
- Providing cleaner, more intuitive code