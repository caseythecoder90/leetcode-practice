# Binary Search Cheat Sheet

## Quick Templates

### Template 1: Classic Binary Search (Find Exact Match)
```java
public int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1; // Not found
}
```

### Template 2: Find Minimum Valid (Answer Search)
```java
public int findMinimumValid(/* parameters */) {
    int left = minPossible, right = maxPossible;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (isValid(mid)) {
            right = mid; // mid might be answer, keep it
        } else {
            left = mid + 1; // mid invalid, search right
        }
    }
    
    return left; // left == right
}
```

### Template 3: Find Maximum Valid (Answer Search)
```java
public int findMaximumValid(/* parameters */) {
    int left = minPossible, right = maxPossible;
    
    while (left < right) {
        int mid = left + (right - left + 1) / 2; // Ceiling division
        
        if (isValid(mid)) {
            left = mid; // mid valid, might be answer
        } else {
            right = mid - 1; // mid invalid, search left
        }
    }
    
    return left;
}
```

### Template 4: Find First/Last Occurrence
```java
// Find FIRST occurrence (leftmost)
public int findFirst(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    int result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;
            right = mid - 1; // Continue searching left
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}

// Find LAST occurrence (rightmost)
public int findLast(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    int result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;
            left = mid + 1; // Continue searching right
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

## Pattern-Specific Templates

### Koko Eating Bananas Pattern
```java
public int minEatingSpeed(int[] piles, int h) {
    int left = 1, right = Arrays.stream(piles).max().getAsInt();
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (canFinish(piles, mid, h)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    
    return left;
}

private boolean canFinish(int[] piles, int speed, int hours) {
    long totalHours = 0;
    for (int pile : piles) {
        totalHours += (pile + speed - 1) / speed; // Ceiling division
    }
    return totalHours <= hours;
}
```

### Peak Finding Pattern
```java
public int findPeakElement(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[mid + 1]) {
            right = mid; // Peak is on left side (including mid)
        } else {
            left = mid + 1; // Peak is on right side
        }
    }
    
    return left;
}
```

### Rotated Array Search Pattern
```java
public int searchRotated(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) return mid;
        
        if (nums[left] <= nums[mid]) { // Left half sorted
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else { // Right half sorted
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    return -1;
}
```

## Common Utility Functions

### Ceiling Division (Avoid Floating Point)
```java
// Instead of Math.ceil((double)a / b)
int ceilDivide(int a, int b) {
    return (a + b - 1) / b;
}
```

### Check if Array is Sorted
```java
boolean isSorted(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < nums[i-1]) return false;
    }
    return true;
}
```

### Find Search Bounds for Answer Search
```java
// For minimization problems
int[] getSearchBounds(int[] nums) {
    int min = 1; // or Arrays.stream(nums).min().getAsInt()
    int max = Arrays.stream(nums).max().getAsInt();
    return new int[]{min, max};
}
```

## Quick Decision Tree

### Choose Your Template:
```
Is the array/space already sorted?
├─ YES: Looking for exact element?
│  ├─ YES: Use Template 1 (Classic)
│  └─ NO: Looking for first/last occurrence?
│     ├─ YES: Use Template 4 (First/Last)
│     └─ NO: Use Template 2/3 (Min/Max Valid)
└─ NO: Can you define a validation function?
   ├─ YES: Use Template 2/3 (Answer Search)
   └─ NO: Not a binary search problem
```

### Template Selection by Problem Type:
| Problem Type | Template | Key Insight |
|-------------|----------|-------------|
| Find element in sorted array | Template 1 | Direct search |
| Find minimum valid answer | Template 2 | Monotonic condition |
| Find maximum valid answer | Template 3 | Monotonic condition |
| Find first/last occurrence | Template 4 | Continue after found |
| Peak finding | Peak pattern | Compare with neighbors |
| Rotated array | Rotation pattern | Identify sorted half |

## Template Differences Summary

| Template | Loop Condition | Mid Calculation | When to Use |
|----------|---------------|-----------------|-------------|
| **Classic** | `left <= right` | `left + (right - left) / 2` | Find exact match |
| **Min Valid** | `left < right` | `left + (right - left) / 2` | Find minimum answer |
| **Max Valid** | `left < right` | `left + (right - left + 1) / 2` | Find maximum answer |

## Common Mistakes & Quick Fixes

| Mistake | Quick Fix |
|---------|-----------|
| Infinite loop | Check mid calculation and boundary updates |
| Off-by-one | Verify boundary conditions with small examples |
| Integer overflow | Use `left + (right - left) / 2` |
| Wrong template | Match problem type to template selection |

## Time Complexity Quick Reference

- **Classic binary search**: O(log n)
- **Answer search**: O(log(range) × validation_