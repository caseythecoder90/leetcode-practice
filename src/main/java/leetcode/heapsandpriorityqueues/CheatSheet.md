# Heaps and Priority Queues - Cheat Sheet

## Quick Setup Templates

### Basic Heap Creation
```java
// Min Heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max Heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Custom objects
PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]); // Sort by first element
```

## Problem Pattern Templates

### 1. Top K Largest Elements
```java
public int[] topKLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
        result[i] = minHeap.poll();
    }
    return result;
}
```

### 2. Kth Largest Element (QuickSelect Alternative)
```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    
    return minHeap.peek();
}
```

### 3. Two Heap Technique (Median Finding)
```java
class MedianFinder {
    PriorityQueue<Integer> maxHeap; // smaller half
    PriorityQueue<Integer> minHeap; // larger half
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        // Balance heaps
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
    }
}
```

### 4. K-way Merge Template
```java
public int[] merge(int[][] arrays, int k) {
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    
    // Add first element from each array: [value, arrayIndex, elementIndex]
    for (int i = 0; i < arrays.length; i++) {
        if (arrays[i].length > 0) {
            minHeap.offer(new int[]{arrays[i][0], i, 0});
        }
    }
    
    List<Integer> result = new ArrayList<>();
    while (!minHeap.isEmpty()) {
        int[] current = minHeap.poll();
        int value = current[0];
        int arrayIdx = current[1];
        int elemIdx = current[2];
        
        result.add(value);
        
        // Add next element from same array if available
        if (elemIdx + 1 < arrays[arrayIdx].length) {
            minHeap.offer(new int[]{arrays[arrayIdx][elemIdx + 1], arrayIdx, elemIdx + 1});
        }
    }
    
    return result.stream().mapToInt(i -> i).toArray();
}
```

## PriorityQueue API Reference

### Core Operations with Examples

#### 1. Adding Elements
```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

// offer(E e) - Inserts element, returns true if successful
pq.offer(10);           // [10]
pq.offer(5);            // [5, 10] - min heap, 5 becomes root
pq.offer(15);           // [5, 10, 15]
pq.offer(3);            // [3, 5, 15, 10] - 3 becomes new root

// add(E e) - Same as offer(), inherited from Collection
pq.add(7);              // [3, 5, 15, 10, 7]

// Note: Both offer() and add() have O(log n) time complexity
```

#### 2. Removing Elements  
```java
// poll() - Removes and returns head (min/max), returns null if empty
Integer head = pq.poll();    // Returns 3, heap becomes [5, 7, 15, 10]
Integer next = pq.poll();    // Returns 5, heap becomes [7, 10, 15]

// remove() - Same as poll() but throws exception if empty
try {
    Integer removed = pq.remove();  // Returns 7
} catch (NoSuchElementException e) {
    // Handle empty queue
}

// remove(Object o) - Removes specific element, O(n) time
pq.remove(15);          // Removes 15 if present, returns boolean
```

#### 3. Viewing Elements (No Removal)
```java
// peek() - Returns head without removing, null if empty
Integer min = pq.peek();     // Returns smallest element (min heap)
if (pq.peek() != null) {     // Always check for null
    System.out.println("Min: " + pq.peek());
}

// element() - Same as peek() but throws exception if empty
try {
    Integer head = pq.element();
} catch (NoSuchElementException e) {
    // Handle empty queue
}
```

#### 4. Size and Status Operations
```java
// size() - Returns number of elements, O(1)
int count = pq.size();

// isEmpty() - Returns true if no elements, O(1)  
if (!pq.isEmpty()) {
    process(pq.poll());
}

// clear() - Removes all elements, O(n)
pq.clear();
```

#### 5. Bulk Operations
```java
List<Integer> numbers = Arrays.asList(8, 3, 5, 4, 7, 6, 1, 2);

// Constructor with Collection - builds heap in O(n)
PriorityQueue<Integer> pq = new PriorityQueue<>(numbers);

// addAll(Collection c) - Adds all elements, O(n log n)
PriorityQueue<Integer> pq2 = new PriorityQueue<>();
pq2.addAll(numbers);

// toArray() - Returns array representation (not sorted!)
Object[] array = pq.toArray();
Integer[] typedArray = pq.toArray(new Integer[0]);

// Note: toArray() does NOT return sorted array, just heap structure
```

#### 6. Searching and Contains
```java
// contains(Object o) - Check if element exists, O(n)
boolean hasElement = pq.contains(5);

// Note: Linear search, not efficient for frequent lookups
// Consider HashMap + PriorityQueue combination if needed
```

### Advanced API Usage

#### Iterator (Use with Caution)
```java
// iterator() - Returns iterator, but NOT in sorted order!
PriorityQueue<Integer> pq = new PriorityQueue<>(Arrays.asList(5, 2, 8, 1));

// This will NOT print in sorted order (1, 2, 5, 8)
for (Integer num : pq) {
    System.out.print(num + " ");  // Might print: 1 2 8 5
}

// To get sorted order, must poll() all elements
while (!pq.isEmpty()) {
    System.out.print(pq.poll() + " ");  // Prints: 1 2 5 8
}
```

#### Capacity Operations
```java
// Constructor with initial capacity - improves performance
PriorityQueue<Integer> pq = new PriorityQueue<>(100);

// Constructor with capacity and comparator
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(100, Collections.reverseOrder());
```

### Common API Patterns and Gotchas

#### Safe Operations Pattern
```java
// Always check before peek/poll to avoid null
if (!pq.isEmpty()) {
    Integer value = pq.peek();    // Safe
    Integer removed = pq.poll();  // Safe
}

// Or handle null explicitly
Integer value = pq.poll();
if (value != null) {
    process(value);
}
```

#### Maintaining Heap Size
```java
// Pattern: Keep only K largest elements
for (int num : array) {
    pq.offer(num);
    if (pq.size() > k) {
        pq.poll();  // Remove smallest when size exceeds K
    }
}
```

#### Converting to Sorted Array
```java
// Wrong way - iterator is not sorted
Integer[] wrong = pq.toArray(new Integer[0]);

// Right way - poll all elements
List<Integer> sorted = new ArrayList<>();
while (!pq.isEmpty()) {
    sorted.add(pq.poll());
}
Integer[] correct = sorted.toArray(new Integer[0]);
```

## Quick Reference

### Essential Operations Summary
```java
heap.offer(element);     // Insert - O(log n)
heap.poll();            // Remove min/max - O(log n), returns null if empty
heap.peek();            // View min/max - O(1), returns null if empty
heap.size();            // Size - O(1)
heap.isEmpty();         // Check empty - O(1)
heap.contains(obj);     // Search - O(n)
heap.remove(obj);       // Remove specific - O(n)
heap.clear();           // Remove all - O(n)
```

### Common Comparators
```java
// Min heap (ascending)
(a, b) -> a - b
Integer::compare

// Max heap (descending)  
(a, b) -> b - a
Collections.reverseOrder()

// Custom objects - sort by distance
(a, b) -> a.distance - b.distance

// Multiple criteria
(a, b) -> {
    if (a.frequency != b.frequency) return b.frequency - a.frequency;
    return a.value - b.value;
}
```

## Problem Recognition Patterns

| Problem Type | Keywords | Approach | Heap Type |
|-------------|----------|----------|-----------|
| Top K Largest | "K largest", "K maximum" | Min heap of size K | Min |
| Top K Smallest | "K smallest", "K minimum" | Max heap of size K | Max |
| Kth Element | "Kth largest", "Kth smallest" | Keep K elements in heap | Min for largest |
| Streaming Median | "median", "running median" | Two heaps | Max + Min |
| Merge K | "merge K sorted", "K lists" | K-way merge | Min |
| Closest K Points | "K closest", "K nearest" | Distance-based heap | Max (for K closest) |

## Time Complexity Quick Guide

| Problem | Approach | Time | Space |
|---------|----------|------|-------|
| Top K from N | Min/Max heap | O(N log K) | O(K) |
| Kth Largest | Min heap | O(N log K) | O(K) |
| Build heap | Heapify | O(N) | O(1) |
| Merge K sorted | K-way merge | O(N log K) | O(K) |
| Streaming median | Two heaps | O(log N) per insert | O(N) |