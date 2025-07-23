# In-Place Array Modification - Cheat Sheet

## Pattern Recognition

**When to use this pattern:**
- ✅ "Remove elements" in-place
- ✅ "Move/partition elements"
- ✅ "Filter array" without extra space
- ✅ Return new length or modified array

**Keywords to watch for:**
- "in-place", "constant extra space"
- "remove all occurrences"
- "move elements to end/front"
- "maintain relative order" (or not!)

## Two Main Approaches

### 1. Fast/Slow Pointers (Same Direction)
**Use when:** Order preservation matters OR beginner-friendly solution needed

```java
int slow = 0;
for (int fast = 0; fast < nums.length; fast++) {
    if (condition) {
        nums[slow] = nums[fast];
        slow++;
    }
}
return slow; // or fill remaining positions
```

**Mental Model:** "Collect good elements at the front"
- `slow` = next position to place good element
- `fast` = current element being examined

### 2. Converging Pointers (Opposite Direction)
**Use when:** Order doesn't matter AND want to minimize operations

```java
int left = 0, right = nums.length - 1;
while (left <= right) {
    if (badCondition) {
        nums[left] = nums[right];
        right--;
    } else {
        left++;
    }
}
return right + 1; // or left
```

**Mental Model:** "Swap bad elements with good elements from end"
- Replace bad elements with good ones from the end
- Shrink valid region from the right

## Problem Templates

### Template 1: Remove Element (LeetCode #27)
```java
public int removeElement(int[] nums, int val) {
    int slow = 0;
    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != val) {
            nums[slow++] = nums[fast];
        }
    }
    return slow;
}
```

### Template 2: Move Zeroes (LeetCode #283)
```java
public void moveZeroes(int[] nums) {
    int slow = 0;
    // Phase 1: Move non-zeros to front
    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            nums[slow++] = nums[fast];
        }
    }
    // Phase 2: Fill rest with zeros
    while (slow < nums.length) {
        nums[slow++] = 0;
    }
}
```

### Template 3: Remove Duplicates (LeetCode #26)
```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    
    int slow = 1; // First element always stays
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[fast-1]) {
            nums[slow++] = nums[fast];
        }
    }
    return slow;
}
```

### Template 4: Partition Array (Order doesn't matter)
```java
public int partition(int[] nums, Condition condition) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        if (!condition(nums[left])) {
            // Swap with element from right
            swap(nums, left, right);
            right--;
        } else {
            left++;
        }
    }
    return left; // Number of elements satisfying condition
}
```

## Decision Tree

```
Is this an in-place array modification problem?
├── Yes
│   ├── Does order matter?
│   │   ├── Yes → Use Fast/Slow Pointers
│   │   └── No → Consider Converging Pointers (more efficient)
│   └── Do I need to fill remaining positions?
│       ├── Yes → Add fill phase after main logic
│       └── No → Just return new length
└── No → Consider other patterns
```

## Common Variations

| Problem Type | Key Insight | Approach |
|-------------|-------------|----------|
| **Remove specific value** | Skip target value | Fast/Slow or Converging |
| **Move zeros to end** | Need fill phase | Fast/Slow + Fill |
| **Remove duplicates** | Compare with previous | Fast/Slow |
| **Partition by condition** | Order doesn't matter | Converging |
| **Keep only unique** | Track seen elements | Fast/Slow + Set |

## Edge Cases Checklist

- [ ] **Empty array**: `[]`
- [ ] **Single element**: `[x]`
- [ ] **All elements match condition**: `[val,val,val]`
- [ ] **No elements match condition**: `[1,2,3]` remove `val=4`
- [ ] **Already partitioned**: `[1,2,0,0]` move zeros to end

## Time/Space Complexity

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| **Fast/Slow** | O(n) | O(1) | Always single pass |
| **Converging** | O(n) | O(1) | May be fewer operations |
| **With Fill Phase** | O(n) | O(1) | Two passes maximum |

## Debugging Checklist

When your solution isn't working:

1. **✅ Pointer movement**: Are you advancing pointers correctly?
    - `slow` only advances when placing an element
    - `fast` always advances each iteration

2. **✅ Boundary conditions**: `left <= right` vs `left < right`?
    - Use `<=` for converging when you need to process middle element

3. **✅ Return value**: What does the problem ask for?
    - New length? Modified array? Count of operations?

4. **✅ Fill phase**: Do remaining positions need specific values?
    - Move Zeroes: Need to fill with 0s
    - Remove Element: Don't need to fill (just return length)

## Practice Progression

### Beginner
1. **Remove Element (#27)** - Basic fast/slow
2. **Remove Duplicates (#26)** - Fast/slow with comparison
3. **Move Zeroes (#283)** - Fast/slow with fill phase

### Intermediate
1. **Remove Duplicates II (#80)** - Allow up to 2 duplicates
2. **Partition Array (#905)** - Even/odd partitioning
3. **Sort Colors (#75)** - Three-way partitioning

### Advanced
1. **Next Permutation (#31)** - Complex in-place modification
2. **Wiggle Sort (#280)** - Specific ordering requirements
3. **Custom comparator problems** - Domain-specific conditions

## Quick Reference: When to Use Which

```java
// Fast/Slow: When order matters or logic is complex
int slow = 0;
for (int fast = 0; fast < n; fast++) {
    if (keep(nums[fast])) {
        nums[slow++] = nums[fast];
    }
}

// Converging: When order doesn't matter and want efficiency  
int left = 0, right = n - 1;
while (left <= right) {
    if (shouldRemove(nums[left])) {
        nums[left] = nums[right--];
    } else {
        left++;
    }
}
```

Remember: **The key insight is recognizing that you can overwrite elements you've already processed or elements you want to remove!**