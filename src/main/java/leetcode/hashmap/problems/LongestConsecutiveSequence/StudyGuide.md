# Longest Consecutive Sequence - Study Guide

## Understanding the Problem

You need to find the longest sequence of consecutive integers in an unsorted array, in O(n) time.

## Why Your Sorting Approach Doesn't Work for O(n)

Your solution works correctly but uses `Arrays.sort()` which is O(n log n). The problem specifically requires O(n) time complexity.

## The HashSet Solution - Step by Step

### Core Insight
Instead of sorting, we use a HashSet to:
1. Check if a number exists in O(1) time
2. Only build sequences from their starting points

### Detailed Walkthrough with Example

Let's trace through `nums = [100, 4, 200, 1, 3, 2]`:

#### Step 1: Build the HashSet
```
numSet = {1, 2, 3, 4, 100, 200}
```

#### Step 2: Iterate through the set

**Processing 1:**
- Is (1 - 1 = 0) in set? No → This is a sequence start!
- Start building: 1 → 2 → 3 → 4 → 5 (not in set, stop)
- Sequence length: 4
- Update max: 4

**Processing 2:**
- Is (2 - 1 = 1) in set? Yes → Not a sequence start, skip

**Processing 3:**
- Is (3 - 1 = 2) in set? Yes → Not a sequence start, skip

**Processing 4:**
- Is (4 - 1 = 3) in set? Yes → Not a sequence start, skip

**Processing 100:**
- Is (100 - 1 = 99) in set? No → This is a sequence start!
- Start building: 100 → 101 (not in set, stop)
- Sequence length: 1
- Max stays: 4

**Processing 200:**
- Is (200 - 1 = 199) in set? No → This is a sequence start!
- Start building: 200 → 201 (not in set, stop)
- Sequence length: 1
- Max stays: 4

**Result:** 4

## Why This is O(n)

### Time Complexity Breakdown
1. **Building HashSet**: O(n) - iterate through array once
2. **Main loop**: O(n) total
   - Outer loop: visits each number once
   - Inner while loop: Each number is visited at most once across ALL iterations
   - Why? A number is only counted once - when building from its sequence start

### Key Point: Each Number Visited At Most Twice
- Once to check if it's a sequence start
- Once when being counted in a sequence (only if part of a sequence being built)

## Common Mistakes to Avoid

### Mistake 1: Not Checking for Sequence Starts
```java
// WRONG - This is O(n²)
for (int num : numSet) {
    int current = num;
    int count = 1;
    while (numSet.contains(current + 1)) {
        current++;
        count++;
    }
    max = Math.max(max, count);
}
```
This builds sequences from EVERY number, not just starts!

### Mistake 2: Not Using HashSet
```java
// WRONG - This is O(n²) due to array search
for (int i = 0; i < nums.length; i++) {
    if (!contains(nums, nums[i] - 1)) { // O(n) search
        // ...
    }
}
```

## Visual Representation

```
Array: [100, 4, 200, 1, 3, 2]

After HashSet creation:
{1, 2, 3, 4, 100, 200}

Sequence identification:
1 → 2 → 3 → 4     (length 4) ✓ Longest
100               (length 1)
200               (length 1)

Only 1, 100, and 200 are sequence starts!
```

## Practice Problems to Solidify Understanding

1. **What if array has duplicates?**
   - `[1, 2, 2, 3, 4]` → HashSet handles this automatically

2. **What if all numbers are consecutive?**
   - `[1, 2, 3, 4, 5]` → Only processes from 1, finds length 5

3. **What if no consecutive numbers?**
   - `[10, 5, 100]` → Each is a sequence of length 1

## Key Takeaways

1. **HashSet for O(1) lookups** is crucial for O(n) time
2. **Identifying sequence starts** prevents redundant work
3. **Each element is processed at most twice** total
4. **Space-time tradeoff**: We use O(n) space to achieve O(n) time

## Interview Tips

- Start by explaining the sorting approach and why it's O(n log n)
- Introduce the HashSet optimization
- Emphasize the "sequence start" insight
- Walk through a small example
- Analyze time complexity carefully

## Code Pattern to Remember

```java
// 1. Create HashSet
Set<Integer> set = new HashSet<>(Arrays.asList(nums));

// 2. Find sequences only from starts
for (int num : set) {
    if (!set.contains(num - 1)) {  // Is this a start?
        // Build sequence from here
        int current = num;
        int length = 1;
        while (set.contains(current + 1)) {
            current++;
            length++;
        }
        max = Math.max(max, length);
    }
}
```