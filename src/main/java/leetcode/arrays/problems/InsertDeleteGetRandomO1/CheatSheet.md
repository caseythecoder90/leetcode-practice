# Insert Delete GetRandom O(1) - Cheat Sheet

## Problem Pattern: Design Data Structure

### Quick Recognition Signs
- "O(1) time complexity" for multiple operations
- "Random" access required
- Insert/Delete operations needed
- Need to maintain uniqueness (set-like behavior)

## Core Technique: HashMap + ArrayList Combination

### Template Structure
```java
public class RandomizedSet {
    private Map<Integer, Integer> valueToIndex;  // value → index mapping
    private List<Integer> values;                // for O(1) random access
    private Random random;
    
    // Constructor, insert, remove, getRandom methods
}
```

### Key Operations Template

#### Insert O(1)
```java
public boolean insert(int val) {
    if (valueToIndex.containsKey(val)) {
        return false;  // Already exists
    }
    
    // Add to end of list and map its index
    values.add(val);
    valueToIndex.put(val, values.size() - 1);
    return true;
}
```

#### Remove O(1) - Swap with Last Technique
```java
public boolean remove(int val) {
    if (!valueToIndex.containsKey(val)) {
        return false;  // Doesn't exist
    }
    
    // Get indices
    int indexToRemove = valueToIndex.get(val);
    int lastElement = values.get(values.size() - 1);
    
    // Swap with last element
    values.set(indexToRemove, lastElement);
    valueToIndex.put(lastElement, indexToRemove);
    
    // Remove last element (O(1))
    values.remove(values.size() - 1);
    valueToIndex.remove(val);
    
    return true;
}
```

#### GetRandom O(1)
```java
public int getRandom() {
    int randomIndex = random.nextInt(values.size());
    return values.get(randomIndex);
}
```

## Data Structure Review

### HashMap Operations
- **Get:** O(1) average, O(n) worst case
- **Put:** O(1) average, O(n) worst case  
- **Remove:** O(1) average, O(n) worst case
- **ContainsKey:** O(1) average, O(n) worst case
- **Space:** O(n)

### ArrayList Operations
- **Get(index):** O(1)
- **Add to end:** O(1) amortized
- **Remove from end:** O(1)
- **Remove from middle:** O(n) - **AVOID THIS!**
- **Set(index):** O(1)
- **Size:** O(1)

### Random Class
- **nextInt(bound):** O(1)
- **Uniform distribution:** Each element has equal probability

## Critical Insights

### Why This Combination Works
1. **HashMap:** Provides O(1) existence checking and index lookup
2. **ArrayList:** Provides O(1) random access and end operations
3. **Index Management:** Keeps both structures synchronized

### The Swap-with-Last Trick
```
Before remove(2):
values: [1, 2, 3, 4]  indices: [0, 1, 2, 3]
map: {1→0, 2→1, 3→2, 4→3}

Step 1 - Swap 2 with last element (4):
values: [1, 4, 3, 4]
map: {1→0, 4→1, 3→2, 4→3}  // Update 4's index

Step 2 - Remove last:
values: [1, 4, 3]
map: {1→0, 4→1, 3→2}  // Remove 2's entry
```

## Common Mistakes & Solutions

### ❌ Wrong Approaches
```java
// WRONG: Only removing from map
public boolean remove(int val) {
    if (map.containsKey(val)) {
        map.remove(val);  // Missing list removal!
        return true;
    }
    return false;
}

// WRONG: Removing from middle of list
public boolean remove(int val) {
    int index = valueToIndex.get(val);
    values.remove(index);  // O(n) operation!
    // Also breaks all indices after this position
}

// WRONG: Using wrong data structures
Set<Integer> set;  // No O(1) random access
Map<Integer, Boolean> map;  // No O(1) random access
```

### ✅ Correct Pattern Recognition
- Need O(1) operations → Think HashMap
- Need random access → Think ArrayList  
- Need to remove efficiently → Think swap-with-last

## Edge Cases Checklist

- [ ] Empty set (getRandom won't be called)
- [ ] Single element
- [ ] Duplicate insertions (return false)
- [ ] Remove non-existent (return false)
- [ ] Integer boundaries (MIN_VALUE, MAX_VALUE)
- [ ] Multiple operations in sequence

## Complexity Summary

| Operation | Time | Space |
|-----------|------|-------|
| Insert    | O(1) | -     |
| Remove    | O(1) | -     |
| GetRandom | O(1) | -     |
| Overall   | O(1) | O(n)  |

## Interview Talking Points

1. **"Why HashMap + ArrayList?"**
   - HashMap: O(1) lookup for existence/index
   - ArrayList: O(1) random access and end operations

2. **"Why swap with last element?"**
   - Removing from middle of ArrayList is O(n)
   - Removing from end is O(1)
   - Swapping maintains O(1) removal

3. **"What about load factor/resizing?"**
   - HashMap resizing: O(n) but amortized O(1)
   - ArrayList resizing: O(n) but amortized O(1)
   - Both maintain average O(1) performance

## Related Problems
- **381.** Insert Delete GetRandom O(1) - Duplicates allowed
- **146.** LRU Cache (similar design pattern)
- **460.** LFU Cache (similar design pattern)

## Quick Implementation Steps
1. Declare HashMap<Integer, Integer> and ArrayList<Integer>
2. Insert: Check existence → Add to end → Map index
3. Remove: Check existence → Swap with last → Remove end → Update map
4. GetRandom: Generate random index → Return element