# Two Pointers Cheat Sheet

## Quick Pattern Recognition

| Problem Keywords | Pattern | Template |
|-----------------|---------|----------|
| "target sum", "pair sum" | Converging | `left=0, right=n-1` |
| "remove duplicates", "move elements" | Fast/Slow | `slow=0, fast++` |
| "longest/shortest subarray" | Sliding Window | `left=0, right++` |
| "palindrome" | Converging | `left=0, right=n-1` |
| "sorted array" + "pairs" | Converging | Sort first if needed |

## Core Templates

### 1. Converging Pointers (Most Common)
```java
public int[] twoSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum == target) {
            return new int[]{left, right};
        } else if (sum < target) {
            left++;  // Need larger sum
        } else {
            right--; // Need smaller sum
        }
    }
    return new int[]{-1, -1}; // Not found
}
```

### 2. Fast & Slow Pointers
```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    
    int slow = 0; // Points to position of last unique element
    
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    return slow + 1; // Length of unique array
}
```

### 3. Variable Sliding Window
```java
public int longestSubarray(int[] nums, int k) {
    int left = 0, maxLength = 0, sum = 0;
    
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right]; // Expand window
        
        // Shrink window if condition violated
        while (sum > k) {
            sum -= nums[left];
            left++;
        }
        
        maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
}
```

### 4. Palindrome Check
```java
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    
    while (left < right) {
        // Skip non-alphanumeric characters
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }
        
        // Compare characters (case insensitive)
        if (Character.toLowerCase(s.charAt(left)) != 
            Character.toLowerCase(s.charAt(right))) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}
```

## Problem-Specific Templates

### K-Sum Pairs (like Max Number of K-Sum Pairs)
```java
public int maxOperations(int[] nums, int k) {
    Arrays.sort(nums); // Essential for two pointers
    int left = 0, right = nums.length - 1, count = 0;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum == k) {
            count++;
            left++;
            right--;
        } else if (sum < k) {
            left++;
        } else {
            right--;
        }
    }
    return count;
}
```

### Container With Most Water
```java
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1, maxWater = 0;
    
    while (left < right) {
        int width = right - left;
        int currentWater = Math.min(height[left], height[right]) * width;
        maxWater = Math.max(maxWater, currentWater);
        
        // Move pointer with smaller height
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    return maxWater;
}
```

### Move Zeroes
```java
public void moveZeroes(int[] nums) {
    int slow = 0; // Position for next non-zero element
    
    // Move all non-zero elements to the front
    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            nums[slow] = nums[fast];
            slow++;
        }
    }
    
    // Fill remaining positions with zeros
    while (slow < nums.length) {
        nums[slow] = 0;
        slow++;
    }
}
```

### Three Sum (Two Pointers + Iteration)
```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    
    for (int i = 0; i < nums.length - 2; i++) {
        // Skip duplicates for first element
        if (i > 0 && nums[i] == nums[i-1]) continue;
        
        int left = i + 1, right = nums.length - 1;
        int target = -nums[i];
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                
                // Skip duplicates
                while (left < right && nums[left] == nums[left+1]) left++;
                while (left < right && nums[right] == nums[right-1]) right--;
                
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }
    return result;
}
```

## Key Decision Points

### When to Move Which Pointer?

#### Converging Pointers:
```java
// Pattern: Finding target sum
if (sum < target) {
    left++;     // Need larger values
} else if (sum > target) {
    right--;    // Need smaller values  
} else {
    // Found target - process and move both
    left++;
    right--;
}
```

#### Fast/Slow Pointers:
```java
// Pattern: In-place modification
if (condition_met) {
    nums[slow] = nums[fast]; // Place element at slow
    slow++;                  // Advance slow position
}
fast++; // Fast always advances
```

### Sorting Decision
```java
// Sort when:
// - Finding pairs with specific sum
// - Need to avoid duplicates easily
// - Problem allows modification of input order

Arrays.sort(nums); // O(n log n) but enables O(n) two-pointer solution
```

## Common Pitfalls & Solutions

### 1. Infinite Loops
```java
// Wrong: Pointers might not converge
while (left <= right) { // Should be left < right
    // ...
}

// Correct: Ensure termination
while (left < right) {
    // ...
    left++; // or right--, ensure progress
}
```

### 2. Array Bounds
```java
// Always check bounds when moving pointers
while (left < nums.length && /* condition */) {
    left++;
}
```

### 3. Duplicate Handling
```java
// Skip duplicates after finding valid pair
while (left < right && nums[left] == nums[left+1]) left++;
while (left < right && nums[right] == nums[right-1]) right--;
```

### 4. Edge Cases
```java
// Handle empty or single element arrays
if (nums == null || nums.length < 2) {
    return /* appropriate default */;
}
```

## Time Complexity Quick Reference

| Pattern | Time | Space | Notes |
|---------|------|-------|-------|
| Converging | O(n) | O(1) | After O(n log n) sort if needed |
| Fast/Slow | O(n) | O(1) | Single pass through array |
| Sliding Window | O(n) | O(1) | Each element visited at most twice |
| Three Sum | O(n²) | O(1) | Two pointers inside O(n) loop |

## Problem Difficulty Progression

### Easy
- Two Sum II (Sorted Array)
- Valid Palindrome
- Move Zeroes
- Remove Duplicates from Sorted Array

### Medium
- Container With Most Water
- 3Sum
- Max Number of K-Sum Pairs
- Longest Substring Without Repeating Characters

### Hard
- Trapping Rain Water
- Minimum Window Substring
- Sliding Window Maximum

## Quick Debugging Checklist

1. ✅ Are pointers initialized correctly?
2. ✅ Is the termination condition right (`<` vs `<=`)?
3. ✅ Do both pointers make progress to avoid infinite loops?
4. ✅ Are array bounds checked before access?
5. ✅ Are duplicates handled properly?
6. ✅ Is the array sorted when required?