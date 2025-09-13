# Sliding Window Pattern - Flashcards

## Core Concepts

### Card 1: Pattern Definition
**Q:** What is the Sliding Window pattern?
**A:** A technique that maintains a window (subarray/substring) that slides through a data structure, typically converting O(n²) nested loops into O(n) single pass by reusing calculations from the previous window position.

---

### Card 2: When to Use
**Q:** What are the key indicators to use Sliding Window?
**A:** 
- Contiguous sequences (subarray/substring)
- Finding longest/shortest/maximum/minimum
- Fixed or variable size windows
- Problems with "consecutive" or "at most k" constraints
- Optimization problems on sequential data

---

### Card 3: Fixed vs Variable Window
**Q:** What's the difference between fixed and variable sliding windows?
**A:** 
- **Fixed:** Window size k is given, slide by removing leftmost and adding rightmost element
- **Variable:** Window size changes based on conditions, expand by moving right pointer, contract by moving left pointer

---

### Card 4: Time Complexity
**Q:** What is the typical time complexity of sliding window algorithms?
**A:** O(n) where n is the array/string length. Each element is visited at most twice (once by right pointer, once by left pointer).

---

### Card 5: Window Size Formula
**Q:** How do you calculate window size given left and right pointers?
**A:** `windowSize = right - left + 1`

---

## Implementation Patterns

### Card 6: Fixed Window Implementation
**Q:** What are the steps for implementing a fixed-size sliding window?
**A:** 
1. Initialize window with first k elements
2. Calculate initial window result
3. Slide: For each new position, remove leftmost element and add new rightmost element
4. Update result after each slide

---

### Card 7: Variable Window Expand/Contract
**Q:** What's the expand and contract strategy for variable windows?
**A:** 
1. Expand: Always move right pointer to include new elements
2. Contract: Move left pointer when window becomes invalid
3. Update result when window is valid
4. Continue until right pointer reaches end

---

### Card 8: HashMap for Frequency
**Q:** When should you use HashMap in sliding window problems?
**A:** 
- Character/element frequency counting
- Pattern matching (anagrams, permutations)
- Tracking distinct elements
- "At most k distinct" problems

---

### Card 9: Shrinkable vs Non-shrinkable
**Q:** What's the difference between shrinkable and non-shrinkable windows?
**A:** 
- **Shrinkable:** Can reduce window size when condition is violated (while loop to shrink)
- **Non-shrinkable:** Maintains maximum valid window size seen so far (single if statement)

---

### Card 10: String Pattern Matching
**Q:** How do you handle string pattern matching with sliding window?
**A:** 
1. Create frequency map of pattern
2. Use window frequency map
3. Track matched characters
4. Expand window and update matches
5. Contract when all matched
6. Record minimum/result when valid

---

## Common Algorithms

### Card 11: Maximum Sum Subarray
**Q:** How do you find maximum sum subarray of size k?
**A:** 
```java
1. Calculate sum of first k elements
2. For i = k to n-1:
   - Add arr[i] (new element)
   - Subtract arr[i-k] (old element)
   - Update max sum
```

---

### Card 12: Longest Substring Without Repeating
**Q:** How to find longest substring without repeating characters?
**A:** 
Use HashSet to track characters in window:
1. Expand right, add characters to set
2. If duplicate found, contract left until duplicate removed
3. Update max length when window valid

---

### Card 13: Minimum Window Substring
**Q:** What's the approach for finding minimum window containing all characters of pattern?
**A:** 
1. Use two hashmaps: pattern frequency and window frequency
2. Expand until all pattern chars included
3. Contract to find minimum while maintaining validity
4. Record minimum window

---

### Card 14: At Most K Distinct
**Q:** How do you find longest substring with at most k distinct characters?
**A:** 
1. Use HashMap to count character frequencies
2. Expand window by adding characters
3. If distinct count > k, contract until ≤ k
4. Update maximum length

---

### Card 15: Fruits into Baskets
**Q:** What sliding window pattern does "Fruits into Baskets" use?
**A:** It's an "at most 2 distinct elements" problem:
- Variable sliding window
- HashMap tracking fruit types
- Contract when more than 2 types
- Find maximum window size

---

## Optimization Techniques

### Card 16: Character Array vs HashMap
**Q:** When should you use int[128] instead of HashMap for character frequency?
**A:** Use array when:
- Dealing with ASCII characters only
- Need better performance (O(1) vs O(1) amortized)
- Fixed character set size
- Memory is not a constraint

---

### Card 17: Deque for Window Maximum
**Q:** How do you efficiently find maximum in sliding window?
**A:** Use monotonic deque:
1. Maintain decreasing order in deque
2. Remove elements outside window
3. Remove smaller elements when adding new
4. Front of deque is always maximum

---

### Card 18: At Most K to Exactly K
**Q:** How do you convert "at most k" solution to "exactly k"?
**A:** `exactlyK(k) = atMostK(k) - atMostK(k-1)`

---

### Card 19: Early Termination
**Q:** When can you terminate sliding window early?
**A:** 
- Window size equals array length (found maximum possible)
- Remaining elements less than required
- Target impossible to achieve
- All elements processed

---

### Card 20: Circular Array Handling
**Q:** How do you handle sliding window on circular array?
**A:** 
1. **Method 1:** Process array twice, use modulo for index
2. **Method 2:** Append array to itself virtually
3. Track window boundaries carefully to avoid duplicates

---

## Edge Cases

### Card 21: Common Edge Cases
**Q:** What edge cases should you always check?
**A:** 
- Empty input array/string
- Window size larger than array
- Window size = 1
- All elements identical
- No valid window exists
- Negative numbers (for sum problems)

---

### Card 22: Invalid Window Handling
**Q:** How do you handle cases where no valid window exists?
**A:** 
- Return -1 for length/size problems
- Return empty string for substring problems
- Return 0 for count problems
- Return Integer.MAX_VALUE for minimum problems

---

## Problem Recognition

### Card 23: Keywords to Identify
**Q:** What keywords suggest sliding window approach?
**A:** 
- "contiguous subarray"
- "consecutive elements"
- "substring"
- "window"
- "at most/at least k"
- "maximum/minimum sum"
- "longest/shortest"

---

### Card 24: Sliding Window vs Two Pointers
**Q:** When to use Sliding Window vs Two Pointers?
**A:** 
- **Sliding Window:** Contiguous sequences, window of elements
- **Two Pointers:** Can skip elements, finding pairs, not necessarily contiguous

---

### Card 25: Sliding Window vs Dynamic Programming
**Q:** When is Sliding Window preferred over DP?
**A:** 
- **Sliding Window:** Current decision only depends on window state
- **DP:** Need to consider all previous subproblems
- **Use SW when:** Can maintain running state, no complex dependencies

---

## Interview Strategy

### Card 26: Problem Approach Steps
**Q:** What's the systematic approach for sliding window problems?
**A:** 
1. Identify if sliding window applies
2. Determine fixed vs variable window
3. Define what makes window valid
4. Choose data structures (counter, map, set)
5. Implement expand/contract logic
6. Handle edge cases

---

### Card 27: Complexity Analysis
**Q:** How do you analyze sliding window complexity?
**A:** 
- **Time:** Usually O(n), can be O(nk) if k operations per window
- **Space:** O(1) for counters, O(k) for k distinct elements, O(min(n,k)) for hashmaps

---

### Card 28: Common Mistakes
**Q:** What are common sliding window implementation mistakes?
**A:** 
- Off-by-one in window size calculation
- Not updating state when shrinking
- Incorrect shrinking condition
- Forgetting to handle empty window
- Wrong initialization of result variable

---

### Card 29: Optimization Discussion
**Q:** What optimizations can you discuss in interviews?
**A:** 
- Array vs HashMap for frequency
- Early termination conditions
- Space optimization (reuse variables)
- Monotonic deque for min/max
- Preprocessing for pattern matching

---

### Card 30: Testing Strategy
**Q:** What test cases should you always verify?
**A:** 
- Minimum input (empty, single element)
- Window size = array size
- No valid window case
- All elements same
- Typical case with clear answer
- Maximum constraints

---

## Advanced Concepts

### Card 31: Multiple Windows
**Q:** How do you handle problems requiring multiple sliding windows?
**A:** 
- Track separate left/right pointers for each window
- Can be parallel (same array) or sequential
- Update states independently
- Combine results based on problem requirements

---

### Card 32: 2D Sliding Window
**Q:** How does sliding window work on 2D arrays?
**A:** 
- Window is a rectangle of fixed size
- Slide horizontally first, then move down
- Use prefix sums for O(1) window sum calculation
- Complexity: O(mn) for m×n matrix

---

### Card 33: Window with Additional Constraints
**Q:** How do you handle windows with multiple constraints?
**A:** 
- Track each constraint separately
- Window valid only when ALL constraints satisfied
- Shrink when ANY constraint violated
- Use composite conditions for validity

---

### Card 34: Dynamic Window Size
**Q:** When does window size change dynamically?
**A:** 
- Binary search on window size
- Window size depends on element values
- Adaptive algorithms
- Multi-pass with different window sizes

---

### Card 35: Memory-Efficient Sliding Window
**Q:** How do you optimize space in sliding window?
**A:** 
- Use bit manipulation for boolean states
- Circular buffer for fixed windows
- Two-pointer without storing window
- Compress frequency maps
- Use primitive arrays over objects