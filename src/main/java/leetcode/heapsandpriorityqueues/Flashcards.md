# Heaps and Priority Queues Pattern - Flashcards

## Core Concepts

**Q: What is a heap and what are its key properties?**
A: A heap is a complete binary tree with these properties:
- **Max Heap**: Parent node ≥ all children (root is maximum)
- **Min Heap**: Parent node ≤ all children (root is minimum)  
- **Complete Binary Tree**: All levels filled except possibly the last, filled left to right
- **Time Complexities**: Insert O(log n), Extract O(log n), Peek O(1)

**Q: When should you use the Heaps and Priority Queues pattern?**
A: Use this pattern for:
- **Top K problems**: Finding K largest/smallest elements
- **Kth element problems**: Finding the Kth largest/smallest element
- **Merge problems**: Merging K sorted lists/arrays
- **Sliding window maximum/minimum**: Maintaining extremes in a moving window
- **Scheduling problems**: Task scheduling with priorities
- **Streaming data**: Processing continuous data streams while maintaining order

## Java Implementation

**Q: How do you create min heap and max heap in Java?**
A:
```java
// Min heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
```

**Q: What are the key operations and their time complexities for heaps?**
A:
| Operation | Time Complexity | Space Complexity |
|-----------|----------------|------------------|
| Build heap from array | O(n) | O(1) |
| Insert/Delete | O(log n) | O(1) |
| Find min/max | O(1) | O(1) |
| Extract min/max | O(log n) | O(1) |
| Top K from n elements | O(n log k) | O(k) |

## Common Techniques

**Q: What is the Top K pattern and how do you implement it?**
A: Use a min heap of size K to find K largest elements:
1. If heap size < K, add element
2. If element > heap.peek(), remove min and add element
```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int num : nums) {
    if (minHeap.size() < k) {
        minHeap.offer(num);
    } else if (num > minHeap.peek()) {
        minHeap.poll();
        minHeap.offer(num);
    }
}
```

**Q: What is the Two Heap Technique and when do you use it?**
A: Use two heaps for finding median or balancing problems:
- **Max heap** for smaller half of numbers
- **Min heap** for larger half of numbers  
- Keep sizes balanced (differ by at most 1)
- Median is either the top of larger heap or average of both tops

**Q: What is the K-way Merge technique?**
A: For merging multiple sorted structures:
1. Use min heap with (value, source_index) pairs
2. Always process the minimum element next
3. Add next element from the same source to heap

## Problem Categories

**Q: What are the difficulty levels and representative problems for heaps?**
A:
**Easy**: 
- Kth Largest Element in an Array
- Last Stone Weight
- Find Kth Largest Integer in the Array

**Medium**:
- Top K Frequent Elements
- Kth Largest Element in a Stream  
- Find Median from Data Stream
- K Closest Points to Origin
- Meeting Rooms II

**Hard**:
- Merge k Sorted Lists
- Sliding Window Maximum

**Q: How do you handle the "Top K Frequent Elements" problem?**
A:
1. Count frequency using HashMap
2. Use min heap of size K with custom comparator based on frequency
3. For each entry, if heap size < K add it, else if frequency > heap.peek() frequency, replace

## Advanced Techniques

**Q: What is the Heap + HashMap combination and when do you use it?**
A: Use when you need both ordering and O(1) lookup:
- **Heap** for maintaining order
- **HashMap** for O(1) existence checks or frequency counting
- Common in problems like "Top K Frequent Elements"

**Q: How do you handle streaming data with heaps?**
A: 
1. Use heap to maintain running order of elements
2. For Kth largest in stream: maintain min heap of size K
3. For median in stream: use two heap technique
4. Add new elements and rebalance as needed

## Common Pitfalls

**Q: What are the top 5 pitfalls to avoid when using heaps?**
A:
1. **Heap vs Array**: Don't use arrays for dynamic top K problems - heaps are more efficient
2. **Min vs Max**: Be clear about which heap type you need for your problem
3. **Comparator Direction**: `(a, b) -> a - b` is min heap, `(a, b) -> b - a` is max heap
4. **Empty Heap**: Always check `!heap.isEmpty()` before `peek()` or `poll()`
5. **Size Management**: In top K problems, maintain heap size carefully

**Q: What's the difference between `peek()`, `poll()`, and `offer()` in PriorityQueue?**
A:
- `peek()`: Returns top element without removing it (O(1))
- `poll()`: Removes and returns top element (O(log n))  
- `offer()`: Adds element to heap (O(log n))
- Always check if heap is empty before `peek()` or `poll()`

## Problem-Solving Strategy

**Q: How do you decide between min heap vs max heap?**
A:
- **Min heap**: When you need K largest elements (keep smallest of the K largest)
- **Max heap**: When you need K smallest elements (keep largest of the K smallest)
- **For Kth largest**: Use min heap of size K
- **For Kth smallest**: Use max heap of size K

**Q: When should you use heaps instead of sorting?**
A:
- When you only need partial ordering (top K, not all elements)
- When dealing with streaming/dynamic data
- When K << N (much smaller), heap approach is O(n log k) vs sorting O(n log n)
- When you need to maintain order as elements are added/removed