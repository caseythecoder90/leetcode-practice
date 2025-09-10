# 3Sum - Detailed Study Guide

## Step-by-Step Execution Trace

Let's trace through the optimal two-pointer solution with the example: `nums = [-1, 0, 1, 2, -1, -4]`

### Initial Setup
```
Original array: [-1, 0, 1, 2, -1, -4]
After sorting:  [-4, -1, -1, 0, 1, 2]
                  0   1   2  3  4  5  (indices)
```

### Iteration-by-Iteration Breakdown

#### i = 0: nums[i] = -4
```
Target sum: 0 - (-4) = 4
Array: [-4, -1, -1, 0, 1, 2]
        i   left            right
        0    1              5

Pointer movements:
left=1, right=5: sum = -1 + 2 = 1 < 4 → left++
left=2, right=5: sum = -1 + 2 = 1 < 4 → left++  
left=3, right=5: sum = 0 + 2 = 2 < 4 → left++
left=4, right=5: sum = 1 + 2 = 3 < 4 → left++
left=5, right=5: left >= right, exit inner loop

Result: No triplets found
```

#### i = 1: nums[i] = -1
```
Target sum: 0 - (-1) = 1
Array: [-4, -1, -1, 0, 1, 2]
            i   left       right
            1    2         5

Pointer movements:
left=2, right=5: sum = -1 + 2 = 1 = 1 ✓ FOUND TRIPLET!
  → Add [-1, -1, 2] to result
  → Skip duplicates: left=2 has duplicate at left+1=3? No (nums[2]=-1, nums[3]=0)
  → Skip duplicates: right=5 has duplicate at right-1=4? No (nums[5]=2, nums[4]=1)  
  → Move: left=3, right=4

left=3, right=4: sum = 0 + 1 = 1 = 1 ✓ FOUND TRIPLET!
  → Add [-1, 0, 1] to result
  → Skip duplicates: left=3, right=4 (no duplicates)
  → Move: left=4, right=3

left=4, right=3: left >= right, exit inner loop

Current result: [[-1, -1, 2], [-1, 0, 1]]
```

#### i = 2: nums[i] = -1
```
Skip because nums[2] == nums[1] (both are -1)
This prevents duplicate triplets starting with -1
```

#### i = 3: nums[i] = 0
```
Target sum: 0 - 0 = 0
Array: [-4, -1, -1, 0, 1, 2]
                    i  left right
                    3   4    5

Pointer movements:
left=4, right=5: sum = 1 + 2 = 3 > 0 → right--
left=4, right=4: left >= right, exit inner loop

Result: No new triplets
```

### Final Result: `[[-1, -1, 2], [-1, 0, 1]]`

## Understanding the Duplicate Skipping Logic

### Why We Need the Final `left++; right--`

Let's examine this with a detailed example: `[-2, 0, 0, 2, 2]`

```
Sorted array: [-2, 0, 0, 2, 2]
               0  1  2  3  4

i = 0: nums[i] = -2, target = 2
left = 1, right = 4: sum = 0 + 2 = 2 ✓ FOUND!

Step-by-step after finding the triplet:
1. Add [-2, 0, 2] to result
2. Skip left duplicates: 
   - nums[left] = nums[1] = 0
   - nums[left+1] = nums[2] = 0 → SAME! 
   - left moves from 1 to 2
3. Skip right duplicates:
   - nums[right] = nums[4] = 2
   - nums[right-1] = nums[3] = 2 → SAME!
   - right moves from 4 to 3
4. Current positions: left=2, right=3
   - We're now pointing at: nums[2]=0, nums[3]=2
   - But we ALREADY used this combination (0, 2)!
5. CRITICAL: We need left++ and right-- to move PAST these values
   - After increment: left=3, right=2
   - Now left >= right, so we exit

WITHOUT the final increment:
- We'd be stuck at positions [2, 3] forever
- The while loop would never terminate!
```

## Visual Memory Aid

Think of the duplicate skipping as a **two-phase process**:

```
Phase 1: Skip to BOUNDARY of duplicates
   while (nums[left] == nums[left + 1]) left++;
   └─ Moves TO the last occurrence of the value

Phase 2: Move PAST the boundary  
   left++; right--;
   └─ Moves BEYOND the last occurrence to find NEW values
```

## Common Edge Cases & How to Handle

### 1. All Same Values
```
Input: [0, 0, 0, 0, 0]
- Only valid triplet: [0, 0, 0]
- Duplicate skipping prevents multiple [0, 0, 0] entries
```

### 2. Two Elements Same, Third Different
```  
Input: [-1, -1, 2]
- Valid triplet: [-1, -1, 2]
- No duplicate issues since array is small
```

### 3. No Valid Triplets
```
Input: [1, 2, 3]
- All positive numbers, can't sum to 0
- Algorithm efficiently determines no solutions exist
```

## Interview Simulation

**Interviewer:** "Walk me through your approach for 3Sum."

**Your Response:**
1. "I'll use the two-pointer technique after sorting the array"
2. "The key insight is to fix the first element and find pairs for the remaining two"
3. "For each fixed element, I need pairs that sum to its negative value"
4. "The sorted array allows me to use two pointers moving toward each other"
5. "I handle duplicates at three levels: first element, left pointer, and right pointer"

**Interviewer:** "Why do you increment both pointers after finding a valid triplet?"

**Your Response:**
"Great question! There are two parts to this:
1. The while loops skip OVER duplicate values
2. But they stop AT the boundary of duplicates  
3. I need to move PAST that boundary to find new combinations
4. Without the final increment, I'd be stuck checking the same values forever"

## Complexity Analysis Deep Dive

### Time Complexity: O(n²)
- **Outer loop:** O(n) iterations
- **Inner two-pointer scan:** O(n) for each outer iteration  
- **Sorting:** O(n log n) - dominated by the O(n²) nested loops
- **Total:** O(n log n) + O(n²) = O(n²)

### Space Complexity: O(1)
- **Excluding output:** No extra space used
- **Including output:** O(k) where k is the number of valid triplets
- **Worst case:** O(n²) triplets possible (though rare in practice)

## Practice Variations

Once you master this problem, try these variations:
1. **3Sum Closest:** Find triplet closest to a target (not necessarily 0)
2. **4Sum:** Extend to four numbers
3. **3Sum Smaller:** Count triplets with sum less than target
4. **3Sum with Multiplicity:** Handle duplicate counting differently

## Key Takeaways for Interviews

1. **Pattern Recognition:** Anytime you see "find pairs/triplets", consider sorting + two pointers
2. **Duplicate Handling:** Always clarify if duplicates are allowed in input/output
3. **Edge Cases:** Empty arrays, all same values, no valid combinations
4. **Optimization:** Explain why sorting is beneficial (enables two-pointer technique)
5. **Alternative Approaches:** Be ready to discuss hash-based solutions and their tradeoffs