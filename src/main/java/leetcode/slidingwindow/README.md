# Sliding Window Pattern

## Overview

The Sliding Window pattern is a powerful technique for solving array/string problems that involve finding or calculating something among all contiguous subarrays or substrings of a given size. It converts nested loops (O(n²) or O(n³)) into a single pass (O(n)) by maintaining a window that slides through the data structure.

## When to Use Sliding Window

**Strong indicators that Sliding Window might be the solution:**
- Finding **longest/shortest** substring or subarray with specific properties
- Problems involving **contiguous** sequences
- Finding **maximum/minimum** sum of subarray of size k
- String problems with **character frequency** requirements
- Problems asking for **all subarrays** of specific size
- **Optimization problems** on sequential data
- Keywords: "window", "consecutive", "subarray", "substring"

## Core Sliding Window Patterns

### Pattern 1: Fixed Window Size
**Use case**: Problems where window size k is given
```java
// Initialize window with first k elements
for (int i = 0; i < k; i++) {
    // Process initial window
}

// Slide the window
for (int i = k; i < n; i++) {
    // Remove leftmost element of previous window
    // Add rightmost element to current window
    // Update result
}
```

### Pattern 2: Variable Window Size (Expand and Contract)
**Use case**: Finding optimal window size that satisfies conditions
```java
int left = 0;
for (int right = 0; right < n; right++) {
    // Expand window by including element at right
    
    while (/* window condition violated */) {
        // Contract window from left
        left++;
    }
    
    // Update result with current valid window
}
```

### Pattern 3: Variable Window with Counter/HashMap
**Use case**: String problems with character requirements
```java
Map<Character, Integer> window = new HashMap<>();
int left = 0, matched = 0;

for (int right = 0; right < s.length(); right++) {
    char c = s.charAt(right);
    window.put(c, window.getOrDefault(c, 0) + 1);
    
    // Check if current character matches requirement
    
    while (matched == required) {
        // Update result
        // Try to shrink window
        char leftChar = s.charAt(left);
        window.put(leftChar, window.get(leftChar) - 1);
        // Update matched count
        left++;
    }
}
```

## Key Concepts

### 1. Window Representation
- **Two pointers**: `left` and `right` boundaries
- **Window size**: `right - left + 1`
- **Window content**: Elements from index `left` to `right` inclusive

### 2. Window Operations
- **Expand**: Move right pointer to include new element
- **Contract**: Move left pointer to exclude element
- **Slide**: Move both pointers maintaining size (fixed window)

### 3. State Management
- Track window properties (sum, count, frequency)
- Update incrementally (add/remove single elements)
- Avoid recalculating entire window

## Time and Space Complexity

**Time Complexity**: 
- Fixed window: O(n) - single pass
- Variable window: O(n) - each element visited at most twice

**Space Complexity**: 
- O(1) for numeric calculations
- O(k) for character/element frequency tracking

## Common Problem Types

### 1. Maximum/Minimum Sum
- Maximum sum subarray of size k
- Minimum sum subarray with sum ≥ target
- Maximum average subarray

### 2. Longest Substring/Subarray
- Longest substring without repeating characters
- Longest substring with at most k distinct characters
- Longest subarray with sum ≤ k

### 3. String Pattern Matching
- Find all anagrams in a string
- Minimum window substring
- Permutation in string

### 4. Count Problems
- Count subarrays with sum equals k
- Count distinct elements in windows of size k
- Number of substrings containing all characters

## Problem-Solving Framework

### Step 1: Identify Window Type
- **Fixed size?** → Use sliding technique
- **Variable size?** → Use expand/contract technique
- **Pattern matching?** → Use frequency map

### Step 2: Define Window State
- What needs to be tracked? (sum, count, frequency)
- How to update when expanding?
- How to update when contracting?

### Step 3: Determine Valid Window
- What makes a window valid?
- When to update the result?
- How to handle edge cases?

### Step 4: Optimization Strategy
- Can we avoid nested loops?
- Can we update incrementally?
- Can we early terminate?

## Common Pitfalls and Solutions

### Pitfall 1: Off-by-One Errors
```java
// WRONG - Incorrect window size
int windowSize = right - left;  

// CORRECT
int windowSize = right - left + 1;
```

### Pitfall 2: Not Handling Empty Window
```java
// Add check for valid window
if (right >= left) {
    // Process window
}
```

### Pitfall 3: Forgetting to Update State When Shrinking
```java
while (/* condition */) {
    // Must update state BEFORE moving pointer
    sum -= arr[left];
    left++;
}
```

## Template Code

### Fixed Size Window Template
```java
public int slidingWindowFixed(int[] arr, int k) {
    int n = arr.length;
    if (n < k) return -1;
    
    int windowSum = 0;
    // Initialize first window
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    
    int maxSum = windowSum;
    
    // Slide window
    for (int right = k; right < n; right++) {
        windowSum = windowSum - arr[right - k] + arr[right];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}
```

### Variable Size Window Template
```java
public int slidingWindowVariable(int[] arr, int target) {
    int left = 0, sum = 0;
    int minLength = Integer.MAX_VALUE;
    
    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];
        
        while (sum >= target) {
            minLength = Math.min(minLength, right - left + 1);
            sum -= arr[left];
            left++;
        }
    }
    
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
}
```

## Practice Progression

### Beginner Level
1. **Maximum Sum Subarray of Size K** - Basic fixed window
2. **Average of Subarrays of Size K** - Simple calculation
3. **First Negative in Every Window** - Track specific elements
4. **Maximum Number of Vowels in a Substring** - Fixed window with counting

### Intermediate Level
1. **Longest Substring Without Repeating Characters** - HashSet tracking
2. **Fruit Into Baskets** - At most 2 distinct elements
3. **Max Consecutive Ones III** - Flip at most k zeros

### Advanced Level
1. **Minimum Window Substring** - Complex character matching
2. **Sliding Window Maximum** - Deque optimization
3. **Substring with Concatenation of All Words** - Multiple pattern matching

## Key Insights to Remember

1. **Sliding window eliminates redundant work** by reusing calculations
2. **Two pointers define the window** boundaries
3. **Expand then contract** for variable size problems
4. **Use HashMap/Array** for frequency counting
5. **Update incrementally** rather than recalculating
6. **Window validity** determines when to update result

## Interview Tips

### How to Recognize
- Look for "contiguous", "consecutive", "subarray", "substring"
- Problems asking for optimal (max/min) continuous sequence
- When brute force requires checking all subarrays

### How to Approach
1. Start with brute force approach
2. Identify repeated work in nested loops
3. Consider what stays same when window slides
4. Implement incremental updates

### Common Follow-ups
- Can you do it in one pass?
- What if we want k largest windows?
- How to handle negative numbers?
- What about circular arrays?

---

## Directory Structure

```
slidingwindow/
├── README.md (this file)
├── CheatSheet.md
├── Flashcards.md
├── ProblemList.md
└── problems/
    └── [Individual problem folders]
```

Master the sliding window pattern to efficiently solve a wide range of array and string problems!