# 875. Koko Eating Bananas

## Problem Description

Koko loves to eat bananas. There are `n` piles of bananas, the `ith` pile has `piles[i]` bananas. The guards have gone and will come back in `h` hours.

Koko can decide her bananas-per-hour eating speed of `k`. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them for that hour and won't eat any more bananas during that hour.

Return the minimum integer `k` such that she can eat all the bananas within `h` hours.

## Examples

**Example 1:**
```
Input: piles = [3,6,7,11], h = 8
Output: 4
Explanation: Koko can eat at speed 4:
- Hour 1: eat 3 bananas from pile 1 (pile becomes [0,6,7,11])
- Hour 2: eat 4 bananas from pile 2 (pile becomes [0,2,7,11])  
- Hour 3: eat 2 bananas from pile 2 (pile becomes [0,0,7,11])
- Hour 4: eat 4 bananas from pile 3 (pile becomes [0,0,3,11])
- Hour 5: eat 3 bananas from pile 3 (pile becomes [0,0,0,11])
- Hour 6: eat 4 bananas from pile 4 (pile becomes [0,0,0,7])
- Hour 7: eat 4 bananas from pile 4 (pile becomes [0,0,0,3])
- Hour 8: eat 3 bananas from pile 4 (pile becomes [0,0,0,0])
Total: 8 hours
```

**Example 2:**
```
Input: piles = [30,11,23,4,20], h = 5
Output: 30
Explanation: Koko must eat at speed 30 to finish in exactly 5 hours (1 pile per hour).
```

**Example 3:**
```
Input: piles = [30,11,23,4,20], h = 6
Output: 23
Explanation: Koko can eat at speed 23 and finish in 6 hours.
```

## Constraints

- `1 <= piles.length <= 10^4`
- `piles[i] <= 10^9`
- `1 <= h <= 10^9`

## Approach Analysis

### Initial Observations

1. **Brute Force Insight**: We could try every possible eating speed from 1 to max(piles), but this would be O(max(piles) × n) which is too slow.

2. **Key Insight**: This is a **binary search on answer** problem because:
    - We want the **minimum** eating speed that works
    - Higher eating speed → less time needed (monotonic property)
    - We can efficiently check if a given speed works

### Pattern Recognition

**Problem Type**: Binary search on answer (find minimum valid)

**Why Binary Search?**
- **Search Space**: Eating speeds from 1 to max(piles)
- **Monotonic Property**: If speed `k` works, then any speed `> k` also works
- **Validation Function**: Can we finish all bananas in `h` hours with speed `k`?

### Decision Tree Visualization

```
Can finish with speed k?
├─ YES → Try smaller speed (k might be optimal)
└─ NO  → Need larger speed (k definitely won't work)

Search Space: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ..., max(piles)]
                     ↑
              Find first TRUE
```

### Approach 1: Binary Search on Answer (Optimal)

**Algorithm Steps:**
1. Define search space: `[1, max(piles)]`
2. Use binary search to find minimum valid eating speed
3. For each candidate speed, check if Koko can finish in time
4. Apply "find minimum valid" template

**Time Complexity:** O(n × log(max(piles)))
- Binary search: O(log(max(piles))) iterations
- Validation: O(n) to check all piles
- Total: O(n × log(max(piles)))

**Space Complexity:** O(1)

### Validation Function Deep Dive

The core challenge is implementing `canFinishInTime(piles, speed, hours)`:

```java
private boolean canFinishInTime(int[] piles, int speed, int hours) {
    long totalHours = 0;
    
    for (int pile : piles) {
        // Time needed for this pile = ceil(pile / speed)
        totalHours += (pile + speed - 1) / speed; // Ceiling division trick
        
        // Early termination optimization
        if (totalHours > hours) return false;
    }
    
    return totalHours <= hours;
}
```

**Key Points:**
- **Ceiling Division**: `(pile + speed - 1) / speed` gives `ceil(pile / speed)` without floating point
- **Early Termination**: Return false as soon as we exceed time limit
- **Long Type**: Use `long` to prevent overflow when summing hours

### Ceiling Division Explanation

Why `(pile + speed - 1) / speed` works:
- **Example**: pile = 7, speed = 3
- **Regular division**: 7 / 3 = 2 (floor)
- **Ceiling division**: (7 + 3 - 1) / 3 = 9 / 3 = 3 ✓
- **Verification**: Need 3 hours to eat 7 bananas at speed 3

### Search Space Optimization

**Initial Bounds:**
- **Lower bound**: 1 (minimum possible speed)
- **Upper bound**: max(piles) (eat largest pile in 1 hour)

**Can we optimize bounds?**
- **Better lower bound**: `totalBananas / h` (average speed needed)
- **Better upper bound**: Still max(piles) since we need at least this speed for the largest pile

## Step-by-Step Example

**Input**: piles = [3,6,7,11], h = 8

### Search Process:

1. **Initial**: left = 1, right = 11
2. **mid = 6**: Can finish in time?
    - Pile 3: ceil(3/6) = 1 hour
    - Pile 6: ceil(6/6) = 1 hour
    - Pile 7: ceil(7/6) = 2 hours
    - Pile 11: ceil(11/6) = 2 hours
    - Total: 6 hours ≤ 8 ✓ → right = 6

3. **mid = 3**: Can finish in time?
    - Pile 3: ceil(3/3) = 1 hour
    - Pile 6: ceil(6/3) = 2 hours
    - Pile 7: ceil(7/3) = 3 hours
    - Pile 11: ceil(11/3) = 4 hours
    - Total: 10 hours > 8 ✗ → left = 4

4. **mid = 5**: Can finish in time?
    - Pile 3: ceil(3/5) = 1 hour
    - Pile 6: ceil(6/5) = 2 hours
    - Pile 7: ceil(7/5) = 2 hours
    - Pile 11: ceil(11/5) = 3 hours
    - Total: 8 hours ≤ 8 ✓ → right = 5

5. **mid = 4**: Can finish in time?
    - Pile 3: ceil(3/4) = 1 hour
    - Pile 6: ceil(6/4) = 2 hours
    - Pile 7: ceil(7/4) = 2 hours
    - Pile 11: ceil(11/4) = 3 hours
    - Total: 8 hours ≤ 8 ✓ → right = 4

6. **left = right = 4** → Answer: 4

## Edge Cases

### Case 1: h equals number of piles
```java
piles = [30,11,23,4,20], h = 5
// Must eat one pile per hour, so speed = max(piles) = 30
```

### Case 2: Very large h
```java
piles = [3,6,7,11], h = 1000000
// Can eat very slowly, minimum speed = 1
```

### Case 3: Single pile
```java
piles = [1000000000], h = 2
// Need ceil(1000000000 / speed) ≤ 2
// Minimum speed = 500000000
```

### Case 4: All piles same size
```java
piles = [5,5,5,5], h = 8
// Each pile needs ceil(5/speed) hours
// Total: 4 × ceil(5/speed) ≤ 8
// Need ceil(5/speed) ≤ 2, so speed ≥ 3
```

## Implementation Notes

### Avoiding Integer Overflow
- Use `long` for total hours calculation
- Piles can be up to 10^9, and we might have 10^4 piles
- Total hours could exceed `int` range

### Early Termination Optimization
```java
if (totalHours > hours) return false; // Stop early if already exceeded
```

### Alternative Ceiling Division
```java
// Method 1: Using integer arithmetic (preferred)
int hoursNeeded = (pile + speed - 1) / speed;

// Method 2: Using floating point (works but slower)
int hoursNeeded = (int) Math.ceil((double) pile / speed);
```

## Related Problems

**Similar Binary Search on Answer:**
- **1011. Capacity To Ship Packages Within D Days** - Find minimum ship capacity
- **410. Split Array Largest Sum** - Minimize maximum subarray sum
- **1482. Minimum Number of Days to Make m Bouquets** - Find minimum days to make bouquets
- **1283. Find the Smallest Divisor Given a Threshold** - Find smallest divisor

**Pattern Recognition:**
All these problems ask for "minimum value such that condition X is satisfied" and have monotonic validation functions.

## Complexity Analysis Summary

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| **Brute Force** | O(max(piles) × n) | O(1) | Try every speed 1 to max(piles) |
| **Binary Search** | O(n × log(max(piles))) | O(1) | Optimal solution |

**Why Binary Search is Better:**
- Reduces search space from max(piles) to log(max(piles))
- For max(piles) = 10^9, this is 30 vs 1 billion iterations!
- Time limit constraints make brute force infeasible