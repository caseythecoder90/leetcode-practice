# Sliding Window Pattern - Quick Reference Cheat Sheet

## Pattern Recognition Checklist
- [ ] Contiguous subarray/substring
- [ ] Fixed or variable window size
- [ ] Maximum/minimum/longest/shortest
- [ ] Specific sum/product/count
- [ ] Character frequency requirements
- [ ] "At most k" constraints

## Core Templates

### 1. Fixed Size Window (Size = k)
```java
public int fixedWindow(int[] arr, int k) {
    int n = arr.length;
    int windowSum = 0;
    
    // Build first window
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    int result = windowSum;
    
    // Slide window
    for (int i = k; i < n; i++) {
        windowSum += arr[i] - arr[i - k];  // Add new, remove old
        result = Math.max(result, windowSum);
    }
    return result;
}
```

### 2. Variable Size - Shrinkable Window
```java
public int variableWindow(int[] arr, int target) {
    int left = 0, sum = 0, result = 0;
    
    for (int right = 0; right < arr.length; right++) {
        sum += arr[right];  // Expand window
        
        while (sum > target) {  // Shrink if invalid
            sum -= arr[left++];
        }
        
        result = Math.max(result, right - left + 1);
    }
    return result;
}
```

### 3. Variable Size - Non-Shrinkable Window
```java
public int nonShrinkableWindow(int[] arr, int k) {
    int left = 0, maxLen = 0;
    
    for (int right = 0; right < arr.length; right++) {
        // Add arr[right] to window
        
        if (/* window invalid */) {
            // Remove arr[left] from window
            left++;  // Maintain window size
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

### 4. String Pattern with HashMap
```java
public String minWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>();
    Map<Character, Integer> have = new HashMap<>();
    
    // Build frequency map for pattern
    for (char c : t.toCharArray()) {
        need.put(c, need.getOrDefault(c, 0) + 1);
    }
    
    int left = 0, matched = 0;
    int minLen = Integer.MAX_VALUE, start = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        have.put(c, have.getOrDefault(c, 0) + 1);
        
        if (need.containsKey(c) && 
            have.get(c).equals(need.get(c))) {
            matched++;
        }
        
        while (matched == need.size()) {  // Valid window
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            
            char leftChar = s.charAt(left);
            have.put(leftChar, have.get(leftChar) - 1);
            if (need.containsKey(leftChar) && 
                have.get(leftChar) < need.get(leftChar)) {
                matched--;
            }
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : 
           s.substring(start, start + minLen);
}
```

### 5. At Most K Distinct Elements
```java
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> count = new HashMap<>();
    int left = 0, maxLen = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        count.put(c, count.getOrDefault(c, 0) + 1);
        
        while (count.size() > k) {
            char leftChar = s.charAt(left);
            count.put(leftChar, count.get(leftChar) - 1);
            if (count.get(leftChar) == 0) {
                count.remove(leftChar);
            }
            left++;
        }
        
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

## Quick Decision Tree

```
Is window size fixed?
├── YES → Fixed Window Template
│   └── Slide by adding new and removing old
└── NO → Variable Window
    ├── Can shrink window? 
    │   ├── YES → Expand & Contract Template
    │   └── NO → Non-shrinkable Template
    └── String with pattern?
        ├── YES → HashMap Frequency Template
        └── NO → Simple Counter Template
```

## Common Operations Reference

### Window Size Calculation
```java
int windowSize = right - left + 1;
```

### Expand Window (move right)
```java
// Add element at right
windowSum += arr[right];
windowMap.put(s.charAt(right), ...);
right++;
```

### Contract Window (move left)
```java
// Remove element at left
windowSum -= arr[left];
windowMap.put(s.charAt(left), ...);
left++;
```

### Check Window Validity
```java
// For sum problems
while (windowSum > target) { ... }

// For distinct elements
while (map.size() > k) { ... }

// For pattern matching
while (matched == required) { ... }
```

## Problem Type → Template Mapping

| Problem Type | Template | Key Data Structure |
|-------------|----------|-------------------|
| Max sum subarray of size k | Fixed Window | Running sum |
| Longest substring with k distinct | Variable Shrinkable | HashMap counter |
| Minimum window substring | Variable Shrinkable | Two HashMaps |
| Max consecutive ones with k flips | Variable Non-shrinkable | Zero counter |
| Find all anagrams | Fixed Window | HashMap comparison |
| Smallest subarray with sum ≥ k | Variable Shrinkable | Running sum |

## Common Tricks & Optimizations

### 1. Character Array for ASCII
```java
int[] count = new int[128];  // Faster than HashMap for ASCII
```

### 2. Sliding Window Maximum - Use Deque
```java
Deque<Integer> dq = new ArrayDeque<>();  // Monotonic deque
```

### 3. Circular Array - Double the Array
```java
for (int i = 0; i < 2 * n; i++) {
    int idx = i % n;  // Circular index
}
```

### 4. At Most K → Exactly K
```java
exactlyK = atMostK(k) - atMostK(k - 1);
```

### 5. Early Termination
```java
if (right - left + 1 == n) break;  // Found max possible
```

## Time Complexity Quick Reference

| Operation | Complexity |
|-----------|------------|
| Fixed window slide | O(n) |
| Variable window expand/contract | O(n) |
| With HashMap operations | O(n) |
| With sorting in window | O(n log k) |
| Nested sliding windows | O(n²) |

## Space Complexity Quick Reference

| Data Structure | Complexity |
|---------------|------------|
| Simple counters | O(1) |
| HashMap for k distinct | O(k) |
| Character frequency array | O(26) or O(128) |
| Deque for window maximum | O(k) |

## Red Flags & Edge Cases

⚠️ **Empty input** → Return 0 or empty result
⚠️ **Window larger than array** → Check k ≤ n
⚠️ **All same elements** → May affect distinct count
⚠️ **Negative numbers** → May affect sum comparisons
⚠️ **Single element** → Window size = 1
⚠️ **No valid window** → Return -1 or empty

## Interview Quick Tips

1. **Start simple**: Explain brute force first
2. **Identify optimization**: "We're recalculating the entire window"
3. **Draw the window**: Visualize on whiteboard
4. **State complexity**: Usually O(n) time, O(1) or O(k) space
5. **Handle edge cases**: Empty, single element, window > array
6. **Test with example**: Walk through small input

## One-Line Reminders

- 📍 Window size = right - left + 1
- 📍 Expand right, contract left
- 📍 Fixed: slide by add new, remove old
- 📍 Variable: expand always, contract when invalid
- 📍 HashMap for frequency, Set for unique
- 📍 At most k = atMost(k) - atMost(k-1) for exactly k