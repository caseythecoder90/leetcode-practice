# 1004. Max Consecutive Ones III

## Problem Description
Given a binary array `nums` and an integer `k`, return the maximum number of consecutive 1's in the array if you can flip at most `k` 0's.

### Examples

**Example 1:**
- Input: `nums = [1,1,1,0,0,0,1,1,1,1,0]`, `k = 2`
- Output: `6`
- Explanation: `[1,1,1,0,0,1,1,1,1,1,1]` - Flip positions 5 and 10

**Example 2:**
- Input: `nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1]`, `k = 3`
- Output: `10`
- Explanation: Flip positions 4, 5, and 9 to get 10 consecutive 1's

### Constraints
- `1 <= nums.length <= 10^5`
- `nums[i]` is either 0 or 1
- `0 <= k <= nums.length`

## Key Insight: Reframing the Problem

The breakthrough for this problem is **reframing what we're looking for**:

Instead of thinking: "Find the longest sequence where we flip at most k zeros"

Think: **"Find the longest subarray that contains at most k zeros"**

This reframing transforms it into a classic sliding window problem!

## Solution Approach

### Pattern: Variable-Size Sliding Window

We use the expand-and-contract pattern:
1. **Expand** the window by moving right pointer
2. **Contract** when we have more than k zeros
3. **Track** the maximum window size seen

### Why This Works

- When we have a subarray with ≤ k zeros, we can flip all those zeros to get all 1's
- The length of such a subarray is our answer
- We want the longest such subarray

### Algorithm Steps

1. **Initialize two pointers** (left = 0, right = 0)
2. **Expand window** by moving right:
   - If we encounter a 0, increment zero count
3. **Contract window** when zeros > k:
   - Move left pointer until zeros ≤ k
   - If left pointer was at a 0, decrement zero count
4. **Track maximum** window size throughout

### The "Budget" Mental Model

Think of `k` as your **budget** for zeros:
- You can "afford" to have k zeros in your window
- When you exceed budget, you must shrink the window
- You want the biggest window you can afford

## Time & Space Complexity

- **Time Complexity:** O(n)
  - Each element is visited at most twice (once by right, once by left)
  - All operations inside the loop are O(1)

- **Space Complexity:** O(1)
  - Only using a few variables regardless of input size

## Common Mistakes & How to Avoid

### Mistake 1: Overcomplicating the Logic
❌ Trying to track which zeros to flip
✅ Just count zeros in the window

### Mistake 2: Wrong Window Size Calculation
❌ `right - left` (off by one)
✅ `right - left + 1`

### Mistake 3: Not Handling k = 0
❌ Assuming we can always flip at least one
✅ Handle k = 0 (find longest existing sequence of 1's)

### Mistake 4: Shrinking Too Much
❌ Shrinking until zeros < k
✅ Shrinking until zeros ≤ k (we can have exactly k zeros)

## Interview Tips

### 1. Start with the Reframing
"I notice that if we can flip k zeros, we're essentially looking for the longest subarray with at most k zeros."

### 2. Explain the Sliding Window Choice
"Since we need a contiguous subarray and want to optimize length, this suggests a sliding window approach."

### 3. Walk Through Small Example
Use the first example to demonstrate:
- How the window expands
- When and why it contracts
- How we track the maximum

### 4. Discuss Edge Cases
- All 1's (return array length)
- All 0's with k ≥ length (return array length)
- k = 0 (find longest existing 1's sequence)

### 5. Mention Optimization
"We could optimize slightly by not shrinking the window and just maintaining max size, but this approach is clearer."

## Related Problems

### Similar Sliding Window Problems:
- **424. Longest Repeating Character Replacement** - Same pattern, different constraint
- **159. Longest Substring with At Most Two Distinct Characters** - Similar expand/contract
- **340. Longest Substring with At Most K Distinct Characters** - Generalization

### Progression of Difficulty:
1. **485. Max Consecutive Ones** - No flips allowed (easier)
2. **487. Max Consecutive Ones II** - Flip at most one zero (medium)
3. **1004. Max Consecutive Ones III** - This problem (harder)

## Key Takeaways

1. **Reframing is powerful** - "Flip k zeros" → "Allow k zeros"
2. **Sliding window eliminates nested loops** - O(n²) → O(n)
3. **The "budget" model** helps visualize the constraint
4. **Two pointers can be moved independently** - Not always in lockstep
5. **Maximum tracking** doesn't require storing the actual subarray