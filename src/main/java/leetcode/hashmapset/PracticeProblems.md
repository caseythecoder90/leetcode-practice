# HashMap and Set Pattern - Practice Problems

## Organized by Difficulty and Sub-patterns

### Easy Problems (Foundation Building)

#### Frequency Counting
1. **Valid Anagram** (LeetCode 242)
    - Pattern: Character frequency counting
    - Key Insight: Count characters in both strings, compare frequencies
    - Time: O(n), Space: O(1) for English letters

2. **First Unique Character in a String** (LeetCode 387)
    - Pattern: Frequency counting + linear scan
    - Key Insight: Two-pass solution - count frequencies, then find first with count=1
    - Time: O(n), Space: O(1)

3. **Majority Element** (LeetCode 169)
    - Pattern: Frequency counting with early termination
    - Key Insight: Count frequencies, return when count > n/2
    - Alternative: Boyer-Moore voting algorithm
    - Time: O(n), Space: O(n) [O(1) with Boyer-Moore]

#### Existence/Membership
4. **Contains Duplicate** (LeetCode 217)
    - Pattern: Basic duplicate detection with HashSet
    - Key Insight: Add to set, return false if already exists
    - Time: O(n), Space: O(n)

5. **Missing Number** (LeetCode 268)
    - Pattern: Set difference or mathematical approach
    - Key Insight: Expected sum vs actual sum, or use HashSet
    - Time: O(n), Space: O(1) [mathematical] or O(n) [HashSet]

6. **Happy Number** (LeetCode 202)
    - Pattern: Cycle detection with HashSet
    - Key Insight: Track seen numbers to detect cycles
    - Time: O(log n), Space: O(log n)

#### Basic Mapping
7. **Two Sum** (LeetCode 1)
    - Pattern: Complement search with HashMap
    - Key Insight: Store value→index mapping, look for target-current
    - Time: O(n), Space: O(n)

8. **Roman to Integer** (LeetCode 13)
    - Pattern: Symbol mapping with HashMap
    - Key Insight: Map symbols to values, handle subtraction cases
    - Time: O(n), Space: O(1)

---

### Medium Problems (Core Patterns)

#### Advanced Frequency Counting
9. **Top K Frequent Elements** (LeetCode 347)
    - Pattern: Frequency counting + heap/bucket sort
    - Key Insight: Count frequencies, then find top K using min-heap
    - Time: O(n log k), Space: O(n)

10. **Sort Characters By Frequency** (LeetCode 451)
    - Pattern: Frequency counting + sorting
    - Key Insight: Count frequencies, sort by frequency, rebuild string
    - Time: O(n log k), Space: O(n)

11. **Find All Anagrams in a String** (LeetCode 438)
    - Pattern: Sliding window + frequency matching
    - Key Insight: Use sliding window with character frequency comparison
    - Time: O(n), Space: O(1)

#### Grouping and Categorization
12. **Group Anagrams** (LeetCode 49)
    - Pattern: Grouping by normalized key
    - Key Insight: Use sorted string or frequency signature as key
    - Time: O(n × k log k), Space: O(n × k)

13. **Group Shifted Strings** (LeetCode 249) [Premium]
    - Pattern: Grouping by transformation pattern
    - Key Insight: Normalize strings by shift pattern
    - Time: O(n × k), Space: O(n × k)

#### Array Complement Problems
14. **3Sum** (LeetCode 15)
    - Pattern: Two Sum extension with sorting
    - Key Insight: Fix one element, use Two Sum on remainder
    - Time: O(n²), Space: O(1) excluding result

15. **4Sum** (LeetCode 18)
    - Pattern: Extension of 3Sum
    - Key Insight: Fix two elements, use Two Sum on remainder
    - Time: O(n³), Space: O(1) excluding result

16. **Two Sum II - Input Array Is Sorted** (LeetCode 167)
    - Pattern: Two pointers vs HashMap trade-off
    - Key Insight: Two pointers more space-efficient for sorted input
    - Time: O(n), Space: O(1)

#### Set Operations
17. **Intersection of Two Arrays** (LeetCode 349)
    - Pattern: Set intersection
    - Key Insight: Convert to sets, find intersection
    - Time: O(n + m), Space: O(min(n,m))

18. **Intersection of Two Arrays II** (LeetCode 350)
    - Pattern: Frequency-based intersection
    - Key Insight: Count frequencies, take minimum counts
    - Time: O(n + m), Space: O(min(n,m))

#### Sliding Window with HashMap
19. **Longest Substring Without Repeating Characters** (LeetCode 3)
    - Pattern: Sliding window with character tracking
    - Key Insight: Expand window until duplicate, then shrink from left
    - Time: O(n), Space: O(min(m,n)) where m is character set size

20. **Minimum Window Substring** (LeetCode 76)
    - Pattern: Sliding window with frequency matching
    - Key Insight: Expand until valid, then shrink while maintaining validity
    - Time: O(n), Space: O(m) where m is pattern length

21. **Longest Repeating Character Replacement** (LeetCode 424)
    - Pattern: Sliding window with frequency counting
    - Key Insight: Track max frequency in window, check if (window_size - max_freq) ≤ k
    - Time: O(n), Space: O(1)

#### Subarray/Prefix Sum
22. **Subarray Sum Equals K** (LeetCode 560)
    - Pattern: Prefix sum with HashMap
    - Key Insight: Count prefix sums, look for (current_sum - k)
    - Time: O(n), Space: O(n)

23. **Continuous Subarray Sum** (LeetCode 523)
    - Pattern: Prefix sum modulo with HashMap
    - Key Insight: If (sum1 % k) == (sum2 % k), then subarray between has sum divisible by k
    - Time: O(n), Space: O(min(n,k))

---

### Hard Problems (Advanced Applications)

#### Design Problems
24. **LRU Cache** (LeetCode 146)
    - Pattern: HashMap + Doubly Linked List
    - Key Insight: HashMap for O(1) access, DLL for O(1) insertion/deletion
    - Time: O(1) for all operations, Space: O(capacity)

25. **LFU Cache** (LeetCode 460)
    - Pattern: Multiple HashMaps + Doubly Linked Lists
    - Key Insight: Track frequency groups, maintain least frequent group
    - Time: O(1) for all operations, Space: O(capacity)

26. **Design HashMap** (LeetCode 706)
    - Pattern: Hash table implementation from scratch
    - Key Insight: Handle collisions with chaining or open addressing
    - Time: O(1) average, Space: O(n)

27. **Insert Delete GetRandom O(1)** (LeetCode 380)
    - Pattern: HashMap + Dynamic Array
    - Key Insight: Array for random access, HashMap for O(1) lookup
    - Time: O(1) all operations, Space: O(n)

#### Complex String Problems
28. **Substring with Concatenation of All Words** (LeetCode 30)
    - Pattern: Sliding window with word frequency matching
    - Key Insight: Check all possible starting positions, use word-by-word sliding window
    - Time: O(n × m × len), Space: O(m × len)

29. **Minimum Window Substring** (LeetCode 76)
    - Pattern: Advanced sliding window
    - Key Insight: Two-pointer technique with frequency tracking
    - Time: O(n), Space: O(m)

#### Array Transformation
30. **Find All Duplicates in an Array** (LeetCode 442)
    - Pattern: Index marking or HashSet
    - Key Insight: Use array indices as "hash table" when values are in range [1,n]
    - Time: O(n), Space: O(1) [in-place] or O(n) [HashSet]

31. **Find All Numbers Disappeared in an Array** (LeetCode 448)
    - Pattern: Index marking or set difference
    - Key Insight: Mark presence using array indices or find set difference
    - Time: O(n), Space: O(1) [in-place] or O(n) [HashSet]

#### Advanced Algorithms
32. **Word Ladder** (LeetCode 127)
    - Pattern: BFS with word transformation graph
    - Key Insight: Build graph of valid transformations, BFS for shortest path
    - Time: O(M² × N), Space: O(M² × N)

33. **Word Ladder II** (LeetCode 126)
    - Pattern: BFS + backtracking for all shortest paths
    - Key Insight: BFS to find distance, backtracking to reconstruct paths
    - Time: O(M² × N), Space: O(M² × N)

---

## Practice Strategy

### Week 1: Foundation (Easy Problems)
**Goals:** Master basic HashMap/HashSet operations
- Day 1-2: Problems 1-3 (Frequency counting)
- Day 3-4: Problems 4-6 (Existence/membership)
- Day 5-7: Problems 7-8 (Basic mapping)

**Focus Areas:**
- `getOrDefault()` vs `containsKey()`
- When to use HashMap vs HashSet
- Handling null values safely

### Week 2: Core Patterns (Medium Problems 9-16)
**Goals:** Advanced frequency counting and complement search
- Day 1-2: Problems 9-11 (Advanced frequency)
- Day 3-4: Problems 12-13 (Grouping)
- Day 5-7: Problems 14-16 (Complement problems)

**Focus Areas:**
- Sliding window with frequency tracking
- Grouping techniques (key generation)
- Multiple sum problems patterns

### Week 3: Advanced Techniques (Medium Problems 17-23)
**Goals:** Set operations and sliding window mastery
- Day 1-2: Problems 17-18 (Set operations)
- Day 3-5: Problems 19-21 (Sliding window)
- Day 6-7: Problems 22-23 (Prefix sum)

**Focus Areas:**
- Optimizing sliding window algorithms
- Prefix sum with HashMap techniques
- Space-time tradeoffs

### Week 4: Mastery (Hard Problems)
**Goals:** Complex applications and design problems
- Day 1-3: Problems 24-27 (Design problems)
- Day 4-5: Problems 28-29 (Complex string problems)
- Day 6-7: Problems 30-33 (Advanced algorithms)

**Focus Areas:**
- System design with hash tables
- Combining multiple data structures
- Performance optimization

---

## Problem-Specific Hints

### For Sliding Window Problems (3, 76, 424)
```java
// Template structure
Map<Character, Integer> window = new HashMap<>();
int left = 0, matched = 0;

for (int right = 0; right < s.length(); right++) {
    // Expand window
    char rightChar = s.charAt(right);
    // Update window and matched count
    
    while (/* shrink condition */) {
        // Update result if needed
        // Shrink window from left
        left++;
    }
}
```

### For Complement Problems (1, 15, 18)
```java
// Two Sum pattern
Map<Integer, Integer> map = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (map.containsKey(complement)) {
        // Found pair
        return new int[]{map.get(complement), i};
    }
    map.put(nums[i], i);
}
```

### For Prefix Sum Problems (560, 523)
```java
// Prefix sum pattern
Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1); // Important: handle subarrays starting from index 0

int prefixSum = 0, result = 0;
for (int num : nums) {
    prefixSum += num;
    int target = prefixSum - k; // What we're looking for
    result += prefixCount.getOrDefault(target, 0);
    prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
}
```

### For Design Problems (146, 460, 380)
```java
// Combine HashMap with other data structures
class DataStructure {
    private Map<Key, Node> map; // For O(1) lookup
    private OtherStructure structure; // For O(1) ordered operations
    
    // Design invariants:
    // 1. Map and structure stay synchronized
    // 2. All operations maintain data structure properties
    // 3. Handle edge cases (empty, capacity limits)
}
```

---

## Common Interview Questions

### Conceptual Questions
1. **"When would you use HashMap vs TreeMap?"**
    - HashMap: O(1) operations, no ordering
    - TreeMap: O(log n) operations, sorted ordering

2. **"How does HashMap handle collisions?"**
    - Java 8+: Linked lists convert to red-black trees when bucket size > 8
    - Reduces worst-case from O(n) to O(log n)

3. **"What's the load factor and why does it matter?"**
    - Default 0.75 balances space vs time
    - Lower = less collisions but more memory
    - Higher = more collisions but less memory

### Coding Questions
4. **"Implement a HashMap from scratch"**
    - Array of buckets (linked lists)
    - Hash function with modulo
    - Handle resizing when load factor exceeded

5. **"Find the first non-repeating character in a stream"**
    - Use HashMap + Queue/LinkedList
    - Remove repeated characters from queue head

6. **"Design a data structure for autocomplete"**
    - Trie for prefix matching
    - HashMap at each node for children

---

## Success Metrics

### By End of Week 1
- [ ] Solve 8/8 easy problems within time limits
- [ ] Explain time/space complexity for each solution
- [ ] Identify the pattern used in each problem

### By End of Week 2
- [ ] Solve 8/8 medium problems (set