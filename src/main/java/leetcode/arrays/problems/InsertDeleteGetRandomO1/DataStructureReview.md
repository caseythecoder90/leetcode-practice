# Data Structure Review - HashMap & ArrayList

## HashMap Deep Dive

### Internal Structure
```
HashMap uses an array of buckets (nodes)
Each bucket can contain a linked list or tree (for collisions)

[0] -> Node(key1, val1) -> Node(key7, val7) -> null
[1] -> null  
[2] -> Node(key2, val2) -> null
[3] -> TreeNode(balanced tree for many collisions)
...
```

### Hash Function & Collision Handling
```java
// Simplified hash calculation
int hash = key.hashCode();
int index = hash & (capacity - 1);  // Equivalent to hash % capacity

// Collision handling:
// 1. Separate Chaining (linked list)
// 2. Tree conversion when chain length > 8
// 3. Back to list when size < 6
```

### Time Complexity Analysis
| Operation | Best Case | Average Case | Worst Case | Notes |
|-----------|-----------|--------------|------------|-------|
| Get       | O(1)      | O(1)         | O(n)       | O(log n) with tree |
| Put       | O(1)      | O(1)         | O(n)       | O(log n) with tree |
| Remove    | O(1)      | O(1)         | O(n)       | O(log n) with tree |

### Load Factor & Resizing
- **Default Load Factor:** 0.75
- **Resize Trigger:** When size > capacity × load_factor
- **Resize Cost:** O(n) - rehash all elements
- **Amortized:** Still O(1) due to infrequent resizing

### Memory Overhead
```java
// Each HashMap entry has:
class Node<K,V> {
    final int hash;    // 4 bytes
    final K key;       // 8 bytes (reference)
    V value;           // 8 bytes (reference)
    Node<K,V> next;    // 8 bytes (reference)
}
// Total: ~28 bytes per entry + object headers
```

## ArrayList Deep Dive

### Internal Structure
```java
public class ArrayList<E> {
    private Object[] elementData;  // Backing array
    private int size;              // Current number of elements
    
    // Default initial capacity = 10
}
```

### Growth Strategy
```java
// Growth formula: newCapacity = oldCapacity + (oldCapacity >> 1)
// This means: newCapacity = oldCapacity * 1.5

Example growth: 10 -> 15 -> 22 -> 33 -> 49 -> 73...
```

### Time Complexity Analysis
| Operation | Time Complexity | Notes |
|-----------|----------------|--------|
| get(i) | O(1) | Direct array access |
| set(i, val) | O(1) | Direct array assignment |
| add(val) | O(1) amortized | May trigger resize |
| add(i, val) | O(n) | Shift elements right |
| remove(i) | O(n) | Shift elements left |
| remove(end) | O(1) | No shifting needed |
| size() | O(1) | Simple field access |

### Why Removing from Middle is O(n)
```java
// Removing element at index 2 from [A, B, C, D, E]
Before: [A, B, C, D, E]  (indices: 0,1,2,3,4)
Remove index 2 (C):
Step 1: [A, B, _, D, E]  // Mark for removal
Step 2: [A, B, D, E, _]  // Shift D,E left - O(n) operation!
After:  [A, B, D, E]     // Adjust size

// Elements shifted: n - index - 1
// For middle elements: ~n/2 shifts on average
```

### Memory Layout & Cache Performance
```java
// ArrayList advantages:
// 1. Contiguous memory layout
// 2. CPU cache friendly
// 3. Good spatial locality

Array: [elem0][elem1][elem2][elem3]  // Sequential memory
       ↑ Cache line loads multiple elements at once
```

## Random Class Review

### Key Methods
```java
Random random = new Random();
random.nextInt();           // Any int value
random.nextInt(bound);      // 0 to bound-1 (exclusive)
random.nextDouble();        // 0.0 to 1.0 (exclusive)

// Thread-safe alternative:
ThreadLocalRandom.current().nextInt(bound);
```

### Uniform Distribution Guarantee
```java
// nextInt(n) guarantees each value has probability 1/n
int[] counts = new int[3];
for (int i = 0; i < 30000; i++) {
    counts[random.nextInt(3)]++;
}
// counts will be approximately [10000, 10000, 10000]
```

## Data Structure Combination Strategies

### Why HashMap + ArrayList for This Problem?

| Requirement | HashMap | ArrayList | Combined |
|-------------|---------|-----------|----------|
| O(1) Lookup | ✅ | ❌ | ✅ |
| O(1) Random Access | ❌ | ✅ | ✅ |
| O(1) Insert | ✅ | ✅* | ✅ |
| O(1) Remove | ✅ | ❌** | ✅*** |

*At end only  
**From middle is O(n)  
***With swap-to-end trick

### Alternative Combinations & Why They Don't Work

#### HashMap + LinkedList
```java
// Problems:
// 1. No O(1) random access (need to traverse)
// 2. No index-based operations
// 3. getRandom() would be O(n)
```

#### HashSet Only  
```java
// Problems:
// 1. No O(1) random access
// 2. Would need to convert to array: O(n)
// 3. No way to get element by index
```

#### ArrayList Only
```java
// Problems:
// 1. contains() is O(n) 
// 2. remove(Object) is O(n) for search + O(n) for removal
// 3. No efficient existence checking
```

## Performance Comparison Table

| Data Structure | Access | Search | Insert | Delete | Random Access |
|----------------|--------|--------|--------|--------|---------------|
| HashMap        | O(1)*  | O(1)*  | O(1)*  | O(1)*  | ❌            |
| ArrayList      | O(1)   | O(n)   | O(1)** | O(n)   | O(1)          |
| LinkedList     | O(n)   | O(n)   | O(1)   | O(1)   | ❌            |
| HashSet        | ❌     | O(1)*  | O(1)*  | O(1)*  | ❌            |
| TreeMap        | O(log n) | O(log n) | O(log n) | O(log n) | ❌ |

*Average case  
**At end only

## Memory & Performance Considerations

### Memory Usage Comparison
```java
// For 1000 integers:
ArrayList<Integer>: 
- Array: 1000 * 8 bytes = 8KB (references)
- Integer objects: 1000 * 16 bytes = 16KB  
- Total: ~24KB

HashMap<Integer, Integer>:
- Entries: 1000 * 28 bytes = 28KB
- Integer objects: 2000 * 16 bytes = 32KB
- Bucket array: ~2KB
- Total: ~62KB

Combined (HashMap + ArrayList): ~86KB
```

### Performance Characteristics
```java
// Cache performance:
ArrayList.get(i):     // Excellent - sequential access
HashMap.get(key):     // Good - hash computation + array access
LinkedList.get(i):    // Poor - pointer traversal

// Branch prediction:
ArrayList operations: // Predictable access patterns
HashMap operations:   // Less predictable due to hashing
```

## Best Practices & Gotchas

### HashMap Best Practices
```java
// 1. Choose good initial capacity to avoid resizing
Map<String, Integer> map = new HashMap<>(expectedSize * 4/3);

// 2. Implement proper hashCode() and equals() for custom keys
public class CustomKey {
    @Override
    public int hashCode() { /* distribute evenly */ }
    @Override 
    public boolean equals(Object obj) { /* consistent with hashCode */ }
}

// 3. Consider concurrent alternatives for multithreading
ConcurrentHashMap<K, V> threadSafeMap = new ConcurrentHashMap<>();
```

### ArrayList Best Practices
```java
// 1. Size appropriately to avoid repeated resizing
List<String> list = new ArrayList<>(expectedSize);

// 2. Prefer add() over set() for growing
list.add(element);        // Good - grows size
list.set(index, element); // Requires index < size

// 3. Use subList() carefully (it's a view, not a copy)
List<String> sub = list.subList(1, 3); // Changes affect original!
```

### Common Anti-patterns
```java
// DON'T: Remove while iterating forward
for (int i = 0; i < list.size(); i++) {
    if (shouldRemove(list.get(i))) {
        list.remove(i); // Skips elements!
    }
}

// DO: Remove while iterating backward
for (int i = list.size() - 1; i >= 0; i--) {
    if (shouldRemove(list.get(i))) {
        list.remove(i); // Safe
    }
}

// OR: Use iterator
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    if (shouldRemove(it.next())) {
        it.remove(); // Safe
    }
}
```

This comprehensive review should help you understand the underlying data structures and make informed decisions when combining them for optimal performance!