# Arrays Pattern - Flashcards

## Basic Concepts

**Q: What are the key techniques used in array problems?**
A: 
- Iterating with single and multiple pointers
- Using additional data structures (hash maps, sets)
- Sorting and binary search
- Prefix sums
- Kadane's algorithm for maximum subarray
- In-place manipulation

**Q: What are the common time/space complexities for array operations?**
A:
- Linear scan: O(n) time, O(1) space
- Sorting: O(n log n) time, O(1) or O(n) space depending on algorithm
- Using hash map/set: O(n) time, O(n) space
- Binary search: O(log n) time, O(1) space

## Algorithm Applications

**Q: When should you use a hash map/set approach for array problems?**
A: When you need O(1) lookup time for checking existence of elements, finding complements (like in Two Sum), or tracking visited elements.

**Q: What is Kadane's algorithm and when do you use it?**
A: Kadane's algorithm finds the maximum sum subarray in O(n) time. Use it for problems involving maximum/minimum sum of contiguous subarrays.

**Q: When should you consider sorting an array first?**
A: When the problem involves finding pairs, duplicates, or when you need elements in a specific order. Sometimes sorting can simplify a complex problem.

## Problem-Solving Strategies

**Q: What's the difference between in-place manipulation and using extra space?**
A: In-place manipulation modifies the original array without using additional space (O(1) space), while extra space approaches use additional data structures but may be easier to implement.

**Q: When should you use prefix sums?**
A: When you need to quickly calculate sums of subarrays or when the problem involves range sum queries.

**Q: How do you handle edge cases in array problems?**
A: Always consider: empty arrays, single element arrays, arrays with all same elements, negative numbers, and integer overflow.

## Implementation Tips

**Q: What's a common pattern for avoiding index out of bounds errors?**
A: Always check array bounds before accessing elements, use proper loop conditions (i < arr.length), and be careful with indices when working with subarrays.

**Q: How do you swap elements in an array efficiently?**
A: Use a temporary variable or XOR swapping (for integers only):
```java
// Using temp variable
int temp = arr[i];
arr[i] = arr[j];
arr[j] = temp;
```

**Q: What's the most efficient way to reverse an array?**
A: Use two pointers from start and end, swap elements while moving towards center:
```java
int left = 0, right = arr.length - 1;
while (left < right) {
    swap(arr, left++, right--);
}
```