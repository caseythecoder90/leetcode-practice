# Heaps and Priority Queues

## Pattern Overview

Heaps and Priority Queues are fundamental data structures for efficiently managing ordered collections where you need quick access to the minimum or maximum element. They're essential for solving problems involving "top K", "Kth largest/smallest", and scheduling problems.

## When to Use This Pattern

- **Top K problems**: Finding K largest/smallest elements
- **Kth element problems**: Finding the Kth largest/smallest element
- **Merge problems**: Merging K sorted lists/arrays
- **Sliding window maximum/minimum**: Maintaining extremes in a moving window
- **Scheduling problems**: Task scheduling with priorities
- **Streaming data**: Processing continuous data streams where you need to maintain order

## Key Concepts

### Heap Properties
- **Max Heap**: Parent node ≥ all children (root is maximum)
- **Min Heap**: Parent node ≤ all children (root is minimum)
- **Complete Binary Tree**: All levels filled except possibly the last, filled left to right
- **Time Complexities**: Insert O(log n), Extract O(log n), Peek O(1)

### Priority Queue in Java
```java
// Min heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
```

## Common Techniques

### 1. **Top K Pattern**
Use a min heap of size K to find K largest elements:
- If heap size < K, add element
- If element > heap.peek(), remove min and add element

### 2. **Two Heap Technique**
For finding median or balancing:
- Max heap for smaller half
- Min heap for larger half
- Keep sizes balanced (differ by at most 1)

### 3. **Heap + HashMap**
For problems requiring both ordering and lookup:
- Heap for ordering
- HashMap for O(1) existence checks

### 4. **K-way Merge**
For merging multiple sorted structures:
- Use min heap with (value, source_index) pairs
- Always process the minimum element next

## Time Complexity Patterns

| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Build heap from array | O(n) | O(1) |
| Insert/Delete | O(log n) | O(1) |
| Find min/max | O(1) | O(1) |
| Extract min/max | O(log n) | O(1) |
| Top K from n elements | O(n log k) | O(k) |

## Common Pitfalls

1. **Heap vs Array**: Don't use arrays for dynamic top K problems
2. **Min vs Max**: Be clear about which heap type you need
3. **Comparator Direction**: `(a, b) -> a - b` is min heap, `(a, b) -> b - a` is max heap
4. **Empty Heap**: Always check `!heap.isEmpty()` before `peek()` or `poll()`
5. **Size Management**: In top K problems, maintain heap size carefully

## Must-Know Problems

### Easy
- Kth Largest Element in an Array
- Last Stone Weight
- Find Kth Largest Integer in the Array

### Medium
- Top K Frequent Elements
- Kth Largest Element in a Stream
- Find Median from Data Stream
- K Closest Points to Origin
- Meeting Rooms II

### Hard
- Merge k Sorted Lists
- Sliding Window Maximum
- Find Median from Data Stream (follow-up)

## External Resources

- [Heap Data Structure - GeeksforGeeks](https://www.geeksforgeeks.org/heap-data-structure/)
- [Priority Queue in Java - Oracle Docs](https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html)
- [Heap Sort Algorithm](https://www.geeksforgeeks.org/heap-sort/)