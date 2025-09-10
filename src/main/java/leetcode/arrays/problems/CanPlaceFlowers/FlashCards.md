# Can Place Flowers - Flash Cards

## Card 1: Problem Statement
**Q: What is the "Can Place Flowers" problem asking?**

<details>
<summary>Click for answer</summary>

Given a flowerbed array (0s and 1s) and integer n, determine if you can plant n new flowers without violating the no-adjacent-flowers rule.
- 0 = empty plot
- 1 = occupied plot  
- Flowers cannot be planted in adjacent plots

</details>

---

## Card 2: Planting Conditions
**Q: When can you plant a flower at position i?**

<details>
<summary>Click for answer</summary>

A flower can be planted at position i if ALL three conditions are met:
1. `flowerbed[i] == 0` (current position is empty)
2. `(i == 0 || flowerbed[i-1] == 0)` (left is empty or doesn't exist)
3. `(i == length-1 || flowerbed[i+1] == 0)` (right is empty or doesn't exist)

</details>

---

## Card 3: Algorithm Pattern
**Q: What algorithmic approach works best for this problem?**

<details>
<summary>Click for answer</summary>

**Greedy Algorithm**
- Make locally optimal choices (plant whenever possible)
- Never need to backtrack or reconsider decisions
- Greedy choice property: planting early never hurts future options

</details>

---

## Card 4: Why Greedy Works
**Q: Why is the greedy approach optimal for flower placement?**

<details>
<summary>Click for answer</summary>

**Greedy Choice Property**: If you can plant at position i, it's always optimal to do so because:
- Planting at i blocks positions i-1 and i+1
- NOT planting at i still blocks these positions if they have flowers
- Therefore, planting when possible never reduces future opportunities

</details>

---

## Card 5: Time Complexity
**Q: What is the time and space complexity?**

<details>
<summary>Click for answer</summary>

**Time Complexity**: O(n) - single pass through array with possible early termination
**Space Complexity**: O(1) - only using constant extra space (if in-place modification allowed)

</details>

---

## Card 6: Edge Cases
**Q: What are the key edge cases to consider?**

<details>
<summary>Click for answer</summary>

1. **Single element**: `[0]` can plant 1, `[1]` can plant 0
2. **Boundaries**: positions 0 and n-1 have fewer neighbors to check
3. **All empty**: `[0,0,0,0]` can plant at alternating positions (0,2,...)
4. **n = 0**: always return true (no flowers needed)
5. **Already full**: `[1,0,1,0,1]` cannot plant any more

</details>

---

## Card 7: Common Mistakes
**Q: What are common implementation mistakes?**

<details>
<summary>Click for answer</summary>

1. **Boundary errors**: Not checking if i-1 or i+1 are valid indices
2. **Forgetting to update**: Not setting `flowerbed[i] = 1` after planting
3. **Wrong condition logic**: Incorrect adjacency checking
4. **Missing early exit**: Not returning true when enough flowers planted
5. **Off-by-one**: Array indexing mistakes

</details>

---

## Card 8: Step-by-Step Process
**Q: Walk through the algorithm steps.**

<details>
<summary>Click for answer</summary>

```
1. Initialize planted count = 0
2. For each position i in flowerbed:
   a. Check if position i is empty (flowerbed[i] == 0)
   b. Check if left neighbor is safe (empty or doesn't exist)
   c. Check if right neighbor is safe (empty or doesn't exist)
   d. If all conditions met:
      - Plant flower (set flowerbed[i] = 1)
      - Increment planted count
      - If planted >= n, return true
3. Return planted >= n
```

</details>

---

## Card 9: Example Trace
**Q: Trace through [1,0,0,0,1], n=1**

<details>
<summary>Click for answer</summary>

```
Initial: [1,0,0,0,1], need 1 flower

i=0: flowerbed[0]=1 (occupied) → skip
i=1: flowerbed[1]=0, but left neighbor is 1 → cannot plant
i=2: flowerbed[2]=0, left=0, right=0 → CAN PLANT!
     Update: [1,0,1,0,1], planted=1
     planted >= n (1) → return true

Result: true
```

</details>

---

## Card 10: Boundary Condition Code
**Q: How do you handle array boundaries in the condition check?**

<details>
<summary>Click for answer</summary>

```java
boolean canPlant = flowerbed[i] == 0 && 
                   (i == 0 || flowerbed[i-1] == 0) &&
                   (i == length-1 || flowerbed[i+1] == 0);
```

- `i == 0`: No left neighbor exists
- `i == length-1`: No right neighbor exists
- Use short-circuit evaluation to avoid index out of bounds

</details>

---

## Card 11: Alternative Approaches
**Q: Are there other ways to solve this problem?**

<details>
<summary>Click for answer</summary>

**Yes, but greedy is optimal:**
1. **Brute Force**: Try all possible combinations (exponential time)
2. **Dynamic Programming**: Overkill for this problem
3. **Mathematical**: Count gaps and calculate maximum plantable flowers
4. **Two-pass**: First count possible spots, then check if enough

**Greedy is best**: Simple, optimal, and efficient O(n) solution

</details>

---

## Card 12: Interview Talking Points
**Q: What should you discuss in an interview?**

<details>
<summary>Click for answer</summary>

1. **Clarify requirements**: Adjacent rule, in-place modification allowed?
2. **Explain greedy choice**: Why planting early is always optimal  
3. **Walk through examples**: Demonstrate understanding with traces
4. **Handle edge cases**: Boundary conditions, n=0, single element
5. **Analyze complexity**: Time O(n), Space O(1)
6. **Discuss optimizations**: Early termination when target reached

</details>

---

## Quick Review Summary

🌸 **Pattern**: Greedy Algorithm  
🌸 **Key Insight**: Plant whenever possible (locally optimal = globally optimal)  
🌸 **Condition**: Empty position with empty/non-existent neighbors  
🌸 **Complexity**: O(n) time, O(1) space  
🌸 **Edge Cases**: Boundaries, n=0, single element  
🌸 **Common Mistake**: Forgetting to update array after planting