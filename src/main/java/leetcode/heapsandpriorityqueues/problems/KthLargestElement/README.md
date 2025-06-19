# Kth Largest Element in an Array

**LeetCode Problem #215** | **Medium**

## Problem Description

Given an integer array `nums` and an integer `k`, return the `kth` largest element in the array.

Note that it is the `kth` largest element in the sorted order, not the `kth` distinct element.

Can you solve it without sorting?

### Examples

**Example 1:**
```
Input: nums = [3,2,1,5,6,4], k = 2
Output: 5
Explanation: The 2nd largest element is 5
```

**Example 2:**
```
Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
Output: 4
Explanation: The 4th largest element is 4
```

### Constraints
- `1 <= k <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

## Solution Approaches

### Approach 1: Min Heap (Recommended)
**Key Insight**: Maintain a min heap of size K containing the K largest elements seen so far. The root of this heap will be the Kth largest element.

**Algorithm:**
1. Create a min heap
2. For each element in the array:
   - Add element to heap
   - If heap size > k, remove the smallest element (heap root)
3. Return heap root (Kth largest element)

**Why this works**: The min heap keeps the K largest elements, with the smallest of these K elements at the root. This smallest element among the K largest is exactly the Kth largest overall.

**Time Complexity**: O(N log K) where N is array length
**Space Complexity**: O(K)

### Approach 2: QuickSelect (Optimal)
**Key Insight**: Use the partition logic from quicksort to find the Kth largest element without fully sorting.

**Algorithm:**
1. Choose a pivot and partition array
2. If pivot position == N-K, return pivot value
3. If pivot position > N-K, search left subarray
4. If pivot position < N-K, search right subarray

**Time Complexity**: O(N) average, O(N²) worst case
**Space Complexity**: O(1)

### Approach 3: Sorting (Simple but not optimal)
**Algorithm:**
1. Sort the array in descending order
2. Return element at index K-1

**Time Complexity**: O(N log N)
**Space Complexity**: O(1) or O(N) depending on sort implementation

## When to Use Each Approach

- **Min Heap**: When K is small relative to N, or when you need to find multiple Kth elements
- **QuickSelect**: When you need optimal average-case time complexity and can handle worst-case O(N²)
- **Sorting**: When the array is small or when you need the fully sorted array for other purposes

## Implementation Notes

### Min Heap Approach
```java
// Template structure
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) {
        minHeap.poll();
    }
}
return minHeap.peek();
```

### Common Pitfalls
1. **Off-by-one errors**: Remember Kth largest means K-1 index in sorted array
2. **Empty heap**: Always check heap size before polling
3. **Heap size management**: Maintain exactly K elements in heap
4. **Min vs Max heap**: Use min heap for Kth largest, max heap for Kth smallest

## Related Problems
- **Kth Largest Element in a Stream** (LeetCode #703)
- **Top K Frequent Elements** (LeetCode #347)
- **Find K Closest Elements** (LeetCode #658)
- **Kth Smallest Element in a Sorted Matrix** (LeetCode #378)

## Tags
`Array` `Divide and Conquer` `Sorting` `Heap (Priority Queue)` `Quickselect`