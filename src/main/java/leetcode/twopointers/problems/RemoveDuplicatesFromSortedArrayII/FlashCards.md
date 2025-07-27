# Remove Duplicates from Sorted Array II - Flashcards

## Pattern Recognition

**Q: What signals indicate this is a fast/slow pointer problem?**  
**A:**
- "Sorted array" + "in-place modification"
- "At most K occurrences" constraint
- Must maintain relative order
- O(1) space requirement

**Q: How do you generalize "at most K duplicates" problems?**  
**A:** Compare `nums[fast]` with `nums[slow - k]`. If different, the element is valid to include.

## Core Algorithm

**Q: Why do we start slow pointer at index 2 instead of 0?**  
**A:** The first 2 elements are always valid (at most 2 duplicates allowed), so we can skip checking them.

**Q: What is the key comparison that makes this algorithm work?**  
**A:** `if (nums[fast] != nums[slow - 2])` - this ensures we never have more than 2 duplicates because if they're equal, we already have 2 copies.

**Q: Why doesn't this algorithm need explicit counting?**  
**A:** The comparison `nums[fast] != nums[slow - 2]` implicitly handles counting. If they're equal, adding nums[fast] would create a 3rd duplicate.

## Implementation Details

**Q: What's the base case and why?**  
**A:** `if (nums.length <= 2) return nums.length;` because arrays with ≤2 elements automatically satisfy "at most 2 duplicates".

**Q: How do the pointers move in this algorithm?**  
**A:**
- `fast` always increments (scans every element)
- `slow` only increments when we place a valid element
- `slow` tracks where to place the next valid element

**Q: What does the algorithm return and why?**  
**A:** Returns `slow` which represents the length of the valid array (elements 0 to slow-1 are the result).

## Example Tracing

**Q: Trace through [1,1,1,2,2,3] step by step.**  
**A:**
- fast=2: nums[2]=1, nums[0]=1, equal → skip (would create 3rd duplicate)
- fast=3: nums[3]=2, nums[1]=1, different → place 2 at slow=2
- fast=4: nums[4]=2, nums[2]=2, equal → skip (would create 3rd duplicate)
- fast=5: nums[5]=3, nums[3]=2, different → place 3 at slow=3
- Result: [1,1,2,3], length=4

**Q: Why does [0,0,1,1,1,1,2,3,3] become [0,0,1,1,2,3,3]?**  
**A:** The algorithm keeps first 2 zeros, first 2 ones, then 2, then first 2 threes. The middle 1's get skipped because they would create 3+ duplicates.

## Edge Cases & Debugging

**Q: What happens if the array has no duplicates?**  
**A:** Every element passes the `nums[fast] != nums[slow-2]` test, so all elements are kept. The algorithm works correctly.

**Q: What happens if all elements are the same?**  
**A:** Only the first 2 elements are kept, rest are skipped. Returns 2.

**Q: Common bug: What happens if you use `nums[slow-1]` instead of `nums[slow-2]`?**  
**A:** This would only allow at most 1 duplicate (like the original Remove Duplicates problem), not 2.

## Complexity Analysis

**Q: What are the time and space complexities?**  
**A:**
- **Time**: O(n) - single pass through array
- **Space**: O(1) - only using two pointers

**Q: How does this compare to using a frequency map approach?**  
**A:**
- Map approach: O(n) space, O(n log n) time if using TreeMap
- Two pointers: O(1) space, O(n) time - much more efficient

## Variations & Extensions

**Q: How would you modify this for "at most 3 duplicates"?**  
**A:** Change the comparison to `if (nums[fast] != nums[slow - 3])` and start slow at index 3.

**Q: How would you modify this for "at most K duplicates"?**  
**A:**
```java
if (nums.length <= k) return nums.length;
int slow = k;
for (int fast = k; fast < nums.length; fast++) {
    if (nums[fast] != nums[slow - k]) {
        nums[slow++] = nums[fast];
    }
}
return slow;
```

**Q: What similar problems use this exact pattern?**  
**A:**
- Remove Duplicates from Sorted Array I (K=1)
- Remove Element (different condition)
- Move Zeroes (place zeros at end)

## Interview Strategy

**Q: How do you approach this problem in an interview?**  
**A:**
1. Recognize it's a fast/slow pointer problem from keywords
2. Identify constraint: "at most 2 duplicates"
3. Derive comparison: need to check against element 2 positions back
4. Handle edge case: arrays ≤ 2 elements
5. Trace through example to verify
6. Discuss time/space complexity

**Q: What if interviewer asks for the brute force first?**  
**A:** "I could use a frequency map to count occurrences, but that would use O(n) extra space. The two-pointer technique does it in O(1) space."