# Remove Duplicates from Sorted Array - Flashcards

## Pattern Recognition Cards

**Q: What pattern should you think of when you see "remove duplicates from sorted array in-place"?**

A: **Fast/Slow Two Pointers**
- Fast pointer scans the array
- Slow pointer tracks where to place next unique element
- Sorted property means duplicates are adjacent

---

**Q: Why is the "sorted" property crucial for this problem?**

A: Because **duplicates are adjacent** in a sorted array!
- No need to check against all previous elements
- Just compare `nums[i]` with `nums[i-1]`
- Enables O(n) single-pass solution

---

**Q: What's the mental model for this algorithm?**

A: Think **"compacting"** unique elements to the front, not "removing" duplicates
- We're building a compressed version at the start of the array
- Overwriting positions that contain duplicates or already-processed elements

---

## Implementation Cards

**Q: What are the two pointer roles in Remove Duplicates?**

A:
- **Slow pointer (writeIndex)**: Points to next position for unique element
- **Fast pointer (readIndex)**: Scans through array to find unique elements
- Slow only advances when we find a new unique element

---

**Q: Write the basic structure for Remove Duplicates:**

A:
```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) return nums.length;
    
    int slow = 0; // Points to last unique element
    
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    
    return slow + 1; // Count, not index
}
```

---

**Q: Why do we start the fast pointer at index 1?**

A: Because the **first element is always unique**!
- There's nothing before nums[0] to compare it with
- Starting at 1 avoids index out of bounds errors
- We compare nums[fast] with nums[fast-1] or nums[slow]

---

**Q: Why do we return `slow + 1` instead of `slow`?**

A: Because **slow is an index, but we need the count**
- If slow = 4, we have elements at indices 0,1,2,3,4 = 5 elements total
- The problem asks for k (count of uniques), not the last index

---

## Debugging Cards

**Q: Common mistake: What happens if you forget the edge case check?**

A: **Array bounds exception** or wrong answer for small arrays
```java
// ❌ Wrong: crashes on empty array
for (int fast = 1; fast < nums.length; fast++) {

// ✅ Correct: handle edge cases first  
if (nums.length <= 1) return nums.length;
```

---

**Q: Common mistake: What if you move slow pointer incorrectly?**

A: **Slow pointer should only advance when placing a unique element**
```java
// ❌ Wrong: slow advances every iteration
slow++;
if (nums[fast] != nums[slow-1]) {
    nums[slow] = nums[fast];
}

// ✅ Correct: slow advances only when placing
if (nums[fast] != nums[slow]) {
    slow++;
    nums[slow] = nums[fast];
}
```

---

**Q: How do you trace through `[1,1,2]`?**

A:
```
Initial: [1,1,2], slow=0, fast=1
Step 1: nums[1]=1, nums[0]=1 → same, skip
Step 2: nums[2]=2, nums[0]=1 → different!
        slow++, nums[1]=2 → [1,2,2]
        slow=1
Return: slow+1 = 2
```

---

## Complexity Cards

**Q: Time and space complexity of Two Pointers vs TreeSet approach?**

A:
**Two Pointers:** O(n) time, O(1) space
**TreeSet:** O(n log n) time, O(n) space

Two pointers is better because:
- Exploits sorted property (no need to sort again)
- Single pass through array
- No extra data structures needed

---

**Q: Why is O(n) optimal for this problem?**

A: Because we **must examine every element** at least once to determine if it's a duplicate. Can't do better than O(n) time.

---

## Pattern Extension Cards

**Q: How would you modify this for "Remove Duplicates II" (allow 2 occurrences)?**

A: Compare with element **2 positions back** instead of 1:
```java
if (nums[fast] != nums[slow - 2]) {
    slow++;
    nums[slow] = nums[fast];
}
```

---

**Q: What other problems use the same fast/slow pointer pattern?**

A:
- **Move Zeroes (LC #283)** - Move zeros to end
- **Remove Element (LC #27)** - Remove specific value
- **Partition Array** - Separate elements by condition

All follow: fast scans, slow tracks placement position

---

**Q: How do you recognize when to use fast/slow pointers?**

A: Look for these keywords:
- "In-place modification"
- "Remove/filter elements"
- "Maintain relative order"
- "Return new length"
- Array is sorted (bonus optimization)

---

## Interview Strategy Cards

**Q: What if you think of TreeSet first in an interview?**

A: **Acknowledge it, then optimize:**
"I could use a TreeSet to remove duplicates, but since the array is already sorted, I can use two pointers for O(n) time and O(1) space instead of O(n log n) time and O(n) space."

---

**Q: Key points to mention in interview explanation:**

A:
1. "Since array is sorted, duplicates are adjacent"
2. "Use fast/slow pointers - fast scans, slow places"
3. "Only advance slow when finding unique elements"
4. "Return count (slow+1), not index (slow)"
5. "Modifying in-place is safe because we only overwrite duplicates"

---

**Q: How to debug if your solution fails?**

A:
1. **Trace by hand** with [1,1,2]
2. **Check edge cases**: [], [1], [1,1,1]
3. **Verify return value**: count vs index
4. **Check pointer movement**: slow only advances when placing