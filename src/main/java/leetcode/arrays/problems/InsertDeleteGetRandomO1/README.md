# 380. Insert Delete GetRandom O(1)

**Difficulty:** Medium  
**Topics:** Array, Hash Table, Math, Randomized, Design  

## Problem Statement

Implement the `RandomizedSet` class:

- `RandomizedSet()` Initializes the RandomizedSet object.
- `boolean insert(int val)` Inserts an item val into the set if not present. Returns true if the item was not present, false otherwise.
- `boolean remove(int val)` Removes an item val from the set if present. Returns true if the item was present, false otherwise.
- `int getRandom()` Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.

**You must implement the functions of the class such that each function works in average O(1) time complexity.**

## Key Insights

1. **HashMap + ArrayList Combination**: Use HashMap to store value→index mapping for O(1) lookup, and ArrayList for O(1) random access.

2. **Swap-with-Last Technique**: To maintain O(1) removal, swap the element to be removed with the last element, then remove the last element.

3. **Index Management**: Keep the HashMap synchronized with ArrayList indices at all times.

## Approach

### Data Structures Used:
- `Map<Integer, Integer> valueToIndex`: Maps values to their indices in the list
- `List<Integer> values`: Stores the actual values for random access
- `Random random`: For generating random indices

### Operations:

**Insert O(1):**
1. Check if value exists in HashMap
2. If not, add to end of ArrayList and store index in HashMap

**Remove O(1):**
1. Check if value exists in HashMap
2. Get the index of element to remove
3. Swap with last element in ArrayList
4. Update the swapped element's index in HashMap
5. Remove last element from ArrayList and HashMap

**GetRandom O(1):**
1. Generate random index between 0 and list size
2. Return element at that index

## Implementation Details

### Why the Original Solution Failed

The original solution had a critical flaw in the `remove` method:
```java
// WRONG: Only removes from map, not from list
public boolean remove(int val) {
    if (map.containsKey(val)) {
        map.remove(val);  // Only removes from map!
        return true;
    }
    return false;      
}
```

This caused:
1. ArrayList to grow indefinitely with "removed" elements
2. `getRandom()` to potentially select removed elements
3. Recursive calls in `getRandom()` leading to performance degradation

### Correct Remove Implementation

```java
public boolean remove(int val) {
    if (!valueToIndex.containsKey(val)) {
        return false;
    }
    
    // Get the index of element to remove
    int indexToRemove = valueToIndex.get(val);
    int lastElement = values.get(values.size() - 1);
    
    // Swap the element to remove with the last element
    values.set(indexToRemove, lastElement);
    valueToIndex.put(lastElement, indexToRemove);
    
    // Remove the last element
    values.remove(values.size() - 1);
    valueToIndex.remove(val);
    
    return true;
}
```

## Complexity Analysis

- **Time Complexity:** O(1) average for all operations
  - Insert: O(1) - HashMap lookup + ArrayList append
  - Remove: O(1) - HashMap lookup + swap + ArrayList removal from end
  - GetRandom: O(1) - Random index generation + ArrayList access

- **Space Complexity:** O(n) where n is the number of elements in the set
  - HashMap: O(n) for storing value→index mappings
  - ArrayList: O(n) for storing values

## Edge Cases

1. **Empty set:** getRandom() is guaranteed to be called only when set is non-empty
2. **Single element:** All operations should work correctly
3. **Duplicate insertions:** Should return false and not modify the set
4. **Removing non-existent elements:** Should return false
5. **Integer boundaries:** Handle MIN_VALUE and MAX_VALUE correctly

## Test Cases

```java
// Basic functionality
RandomizedSet rs = new RandomizedSet();
rs.insert(1);     // true
rs.remove(2);     // false (not present)
rs.insert(2);     // true
rs.getRandom();   // 1 or 2 randomly
rs.remove(1);     // true
rs.insert(2);     // false (already present)
rs.getRandom();   // 2

// Large number of operations
// Should handle thousands of operations efficiently without recursion issues
```

## Common Mistakes

1. **Not removing from both data structures** in remove operation
2. **Using wrong data structure for random access** (e.g., HashSet doesn't provide O(1) random access)
3. **Not handling index updates** when swapping elements
4. **Using inefficient removal** (removing from middle of ArrayList is O(n))

## Related Problems

- 381. Insert Delete GetRandom O(1) - Duplicates allowed
- 146. LRU Cache
- 460. LFU Cache