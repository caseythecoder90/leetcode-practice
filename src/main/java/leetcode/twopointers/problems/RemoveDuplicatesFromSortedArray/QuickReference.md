# Remove Duplicates from Sorted Array - Quick Reference

## 🚀 Pattern Recognition

**Trigger Words:** "sorted array" + "remove duplicates" + "in-place"
**Pattern:** Fast/Slow Two Pointers
**Time:** O(n) | **Space:** O(1)

## 🎯 Core Template

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) return nums.length;
    
    int slow = 0; // Last unique element position
    
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    
    return slow + 1; // Count of unique elements
}
```

## 🧠 Mental Model

```
Think "COMPACTING" not "REMOVING"

Before: [1,1,1,2,2,3]
After:  [1,2,3,_,_,_]
         ↑     ↑
      uniques  don't care
```

## ⚡ Key Insights

1. **Sorted = Adjacent Duplicates** → Only compare with previous element
2. **First element always unique** → Start fast pointer at index 1
3. **Slow tracks placement** → Only advances when placing unique element
4. **Return count not index** → `slow + 1`, not `slow`

## 🐛 Common Bugs

| Bug | Fix |
|-----|-----|
| Start fast at 0 | Start at 1 (first element always unique) |
| Return `slow` | Return `slow + 1` (count, not index) |
| No edge case check | Handle `length <= 1` first |
| Move slow always | Only move slow when placing element |

## 🔄 Variations

### Allow K Duplicates
```java
// For k=2 (Remove Duplicates II)
if (slow < k || nums[fast] != nums[slow - k]) {
    nums[slow++] = nums[fast];
}
```

### Different Condition
```java
// Remove Element (value = val)
if (nums[fast] != val) {
    nums[slow++] = nums[fast];
}
```

## 📝 Trace Example

**Input:** `[0,0,1,1,1,2]`

```
slow=0, fast=1: nums[1]=0, nums[0]=0 → same, skip
slow=0, fast=2: nums[2]=1, nums[0]=0 → diff, slow++, nums[1]=1
slow=1, fast=3: nums[3]=1, nums[1]=1 → same, skip  
slow=1, fast=4: nums[4]=1, nums[1]=1 → same, skip
slow=1, fast=5: nums[5]=2, nums[1]=1 → diff, slow++, nums[2]=2
slow=2, return 3

Result: [0,1,2,1,1,2] → first 3 elements are unique
```

## 🎪 Related Problems

| Problem | Pattern | Key Difference |
|---------|---------|----------------|
| Move Zeroes | Fast/Slow | Move zeros to end + fill phase |
| Remove Element | Fast/Slow | Remove specific value, not duplicates |  
| Remove Duplicates II | Fast/Slow | Allow k=2 duplicates |

## 💡 Interview Tips

1. **Mention TreeSet alternative:** "Could use TreeSet but two pointers is O(n) vs O(n log n)"
2. **Emphasize sorted property:** "Since sorted, duplicates are adjacent"
3. **Trace through small example:** Shows you understand the mechanics
4. **Handle edge cases:** Empty array, single element, all same elements

## ⚠️ Edge Cases

```java
[]           → return 0
[1]          → return 1  
[1,1,1]      → return 1, array becomes [1,1,1]
[1,2,3]      → return 3, array unchanged
```

## 🏃‍♂️ Practice Progression

1. **Master this problem** - Understand every line
2. **Remove Element (LC #27)** - Same pattern, different condition
3. **Move Zeroes (LC #283)** - Add fill phase after compacting
4. **Remove Duplicates II (LC #80)** - Allow k duplicates

## 🎯 Success Checklist

- [ ] Can implement without looking at template
- [ ] Can trace through [1,1,2] by hand
- [ ] Can explain why sorted property matters
- [ ] Can handle all edge cases
- [ ] Can extend to "allow k duplicates" version