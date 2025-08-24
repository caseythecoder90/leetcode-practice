# Insert Delete GetRandom O(1) - Flash Cards

## Problem Recognition Cards

### Card 1: Problem Pattern
**Q:** What design pattern do you use when you need O(1) insert, delete, and random access?

**A:** HashMap + ArrayList combination
- HashMap: O(1) lookup and existence checking
- ArrayList: O(1) random access and end operations

---

### Card 2: Key Insight
**Q:** Why can't you just remove from the middle of an ArrayList?

**A:** Removing from middle is O(n) because all subsequent elements need to shift left. Instead, swap the element with the last element, then remove from the end (O(1)).

---

## Data Structure Cards

### Card 3: HashMap Time Complexity
**Q:** What are the time complexities for HashMap operations?

**A:**
- Get: O(1) average, O(n) worst
- Put: O(1) average, O(n) worst
- Remove: O(1) average, O(n) worst
- ContainsKey: O(1) average, O(n) worst

---

### Card 4: ArrayList Time Complexity
**Q:** What are the time complexities for ArrayList operations?

**A:**
- Get(index): O(1)
- Add to end: O(1) amortized
- Remove from end: O(1)
- Remove from middle: O(n) ⚠️
- Set(index): O(1)

---

### Card 5: Random.nextInt()
**Q:** What's the time complexity of `Random.nextInt(bound)` and what does it guarantee?

**A:** 
- Time: O(1)
- Guarantee: Uniform distribution - each element has equal probability

---

## Implementation Cards

### Card 6: Insert Template
**Q:** Write the O(1) insert method signature and key steps.

**A:**
```java
public boolean insert(int val) {
    if (valueToIndex.containsKey(val)) return false;
    values.add(val);
    valueToIndex.put(val, values.size() - 1);
    return true;
}
```

---

### Card 7: Remove Template
**Q:** Write the O(1) remove method using swap-with-last technique.

**A:**
```java
public boolean remove(int val) {
    if (!valueToIndex.containsKey(val)) return false;
    
    int indexToRemove = valueToIndex.get(val);
    int lastElement = values.get(values.size() - 1);
    
    values.set(indexToRemove, lastElement);
    valueToIndex.put(lastElement, indexToRemove);
    
    values.remove(values.size() - 1);
    valueToIndex.remove(val);
    return true;
}
```

---

### Card 8: GetRandom Template
**Q:** Write the O(1) getRandom method.

**A:**
```java
public int getRandom() {
    int randomIndex = random.nextInt(values.size());
    return values.get(randomIndex);
}
```

---

### Card 9: Data Structure Declaration
**Q:** What data structures do you declare for this problem?

**A:**
```java
private Map<Integer, Integer> valueToIndex;  // value → index
private List<Integer> values;                // for random access
private Random random;                       // for randomization
```

---

## Conceptual Understanding Cards

### Card 10: Swap-with-Last Visualization
**Q:** Trace through removing element 2 from [1,2,3,4] with map {1→0, 2→1, 3→2, 4→3}.

**A:**
1. **Before:** values=[1,2,3,4], map={1→0, 2→1, 3→2, 4→3}
2. **Swap 2 with last (4):** values=[1,4,3,4], update map: 4→1
3. **Remove last:** values=[1,4,3], remove 2 from map
4. **Final:** values=[1,4,3], map={1→0, 4→1, 3→2}

---

### Card 11: Why This Combination?
**Q:** Why use HashMap + ArrayList instead of other combinations?

**A:**
- **HashMap alone:** No O(1) random access
- **ArrayList alone:** No O(1) existence checking
- **HashSet:** No O(1) random access
- **HashMap + ArrayList:** Gets best of both worlds!

---

### Card 12: Index Synchronization
**Q:** What's the most critical aspect of maintaining the HashMap + ArrayList combo?

**A:** **Index synchronization!** The HashMap must always reflect the correct indices of elements in the ArrayList. Every operation that changes positions must update both structures.

---

## Common Mistake Cards

### Card 13: Wrong Remove Implementation
**Q:** What's wrong with this remove implementation?
```java
public boolean remove(int val) {
    if (map.containsKey(val)) {
        map.remove(val);
        return true;
    }
    return false;
}
```

**A:** It only removes from the HashMap but not from the ArrayList! This causes:
- ArrayList grows indefinitely
- getRandom() might return "removed" elements
- Leads to infinite recursion/performance issues

---

### Card 14: Wrong Data Structure Choice
**Q:** What's wrong with using `Map<Integer, Boolean>` instead of `Map<Integer, Integer>`?

**A:** You need to store the **index** of each element, not just existence (Boolean). The index is crucial for the swap-with-last technique in O(1) removal.

---

### Card 15: ArrayList Middle Removal
**Q:** What's wrong with this approach?
```java
int index = valueToIndex.get(val);
values.remove(index);  // Remove from middle
```

**A:** 
- `remove(index)` from ArrayList is O(n)
- Breaks all indices after the removed position
- Need to update many HashMap entries
- Use swap-with-last instead!

---

## Edge Case Cards

### Card 16: Edge Cases Checklist
**Q:** What edge cases should you test?

**A:**
- Empty set (getRandom not called when empty)
- Single element set
- Duplicate insertions (should return false)
- Remove non-existent element (should return false)
- Integer boundaries (MIN_VALUE, MAX_VALUE)
- Sequence of operations

---

### Card 17: Boundary Values
**Q:** How do you handle `Integer.MIN_VALUE` and `Integer.MAX_VALUE`?

**A:** They work normally! HashMap and ArrayList can handle all integer values. No special handling needed - the data structures handle these values naturally.

---

## Complexity Cards

### Card 18: Time Complexity Summary
**Q:** What are the time complexities for all operations?

**A:**
- Insert: O(1) average
- Remove: O(1) average
- GetRandom: O(1)
- Space: O(n) where n = number of elements

---

### Card 19: Why Average Case?
**Q:** Why do we say "average" O(1) for HashMap operations?

**A:** 
- **Average case:** O(1) due to good hash distribution
- **Worst case:** O(n) if all elements hash to same bucket
- **Amortized:** Resizing operations are O(n) but infrequent
- In practice, performs as O(1)

---

## Problem Variant Cards

### Card 20: Duplicates Allowed Variant
**Q:** How would you modify this for "Insert Delete GetRandom O(1) - Duplicates allowed"?

**A:** 
- Use `Map<Integer, Set<Integer>>` to store value → set of indices
- ArrayList remains the same
- More complex remove logic (remove one index from set)
- Same O(1) performance maintained

---

### Card 21: Related Problems
**Q:** What other problems use similar design patterns?

**A:**
- **LRU Cache (146):** HashMap + Doubly Linked List
- **LFU Cache (460):** Multiple HashMaps + Doubly Linked Lists  
- **Design Twitter (355):** HashMap + Lists for feeds
- All involve combining data structures for O(1) operations

---

## Quick Review Cards

### Card 22: 30-Second Summary
**Q:** Explain this problem solution in 30 seconds.

**A:** Need O(1) insert/delete/random. Use HashMap (value→index) + ArrayList (values). Insert adds to end. Remove swaps with last element then removes end. GetRandom picks random index. Key is maintaining index synchronization between structures.

---

### Card 23: Interview Red Flags
**Q:** What are the red flags that indicate you're on the wrong track?

**A:**
- Using only one data structure
- Removing from middle of ArrayList
- Not handling index updates after swaps
- Recursive calls in getRandom
- Not checking existence before operations